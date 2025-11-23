Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$userID = ""
$APIToken = ""
$jobUrl = ""

#Get the config
$authString = "${UserID}:$APIToken"
$headers = @{ Authorization = 'Basic ' + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes($authString)) }
$config = (Invoke-WebRequest -Uri "$joburl/config.xml" -Headers $Headers).Content


#Get the crumb and cookie
$JenkinsRoot = $jobUrl -replace '/job/.+$'
$JenkinsDomain = ($JenkinsRoot -replace "https://") -replace ":.+$"
$WebSession = New-Object Microsoft.PowerShell.Commands.WebRequestSession
$result = Invoke-WebRequest ($JenkinsRoot + '/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)') -Headers $Headers
if ($result.Headers.Keys -contains "Set-Cookie") {
	Write-Host "Cookie!"
	$cookieResponse = ($result.Headers["Set-Cookie"] -split ";")[0] -split "="
	$cookie = New-Object System.Net.Cookie
	$cookie.Name = $cookieResponse[0]
	$cookie.Value = $cookieResponse[1]
	$cookie.Domain = $JenkinsDomain	
	$WebSession.Cookies.Add($cookie);
}
$crumbArray = $result.Content -split ':'
$crumb = @{$crumbArray[0] = $crumbArray[1] }

#Post the result back (replacing the description with the current date/time)
$config = $config -replace "description>.*</","description>$(Get-Date)</"
Invoke-WebRequest -Method POST -Uri "$joburl/config.xml" -Headers ($headers + $crumb) -Body $config -WebSession $WebSession