param(
  [parameter(Mandatory=$true)][string]$Token,
  [parameter(Mandatory=$true)][string]$MINIONID
)

# User-set variables 
$JENKINS_USER="myuser"
$JENKINS_API_TOKEN=$Token
$JENKINS_LABELS = "test"
$JENKINS_URL="https://jenkins.example.org"

# Auto-set variables
$NODE_NAME=$MINIONID.ToLower()

$bytes = [System.Text.Encoding]::ASCII.GetBytes("${JENKINS_USER}:${JENKINS_API_TOKEN}")
$base64 = [System.Convert]::ToBase64String($bytes)
$basicAuthValue = "Basic $base64"
$headers = @{ Authorization = $basicAuthValue;  }

# Set properties of new node
$hash=@{
    name="${NODE_NAME}";
    nodeDescription="Windows node";
    numExecutors="1";
    remoteFS="E:\JA";
    labelString="${JENKINS_LABELS}";
    mode="NORMAL";
    ""=@(
            "hudson.slaves.JNLPLauncher";
            'hudson.slaves.RetentionStrategy$Always'
        );
    launcher=@{ 
        "stapler-class"="hudson.slaves.JNLPLauncher";
        "\$class"="hudson.slaves.JNLPLauncher";
        "workDirSettings"=@{
          "disabled"="true";
          "workDirPath"="";
          "internalDir"="remoting";
          "failIfWorkDirIsMissing"="false"
        };
        "tunnel"="";
        "vmargs"=""
      
    };
    "retentionStrategy"=@{
        "stapler-class"= 'hudson.slaves.RetentionStrategy$Always';
        '$class'= 'hudson.slaves.RetentionStrategy$Always'
    };
    "nodeProperties"=@{
        "stapler-class-bag"= "true";
        "hudson-slaves-EnvironmentVariablesNodeProperty"=@{
            "env"=@(
                @{
                    "key"="JENKINS_HOME";
                    "value"="E:\JA"
                }
            )
        };
        "hudson-tools-ToolLocationNodeProperty"=@{
            "locations"=@(
                #@{
                #    "key"= 'hudson.plugins.git.GitTool$DescriptorImpl@Default';
                #    "home"= "/usr/bin/git"
                #};
            )
        }
    }
}

$JSON_OBJECT = $hash | convertto-json  -Depth 5

# Create new node on Jenkins
(Invoke-WebRequest -Headers $headers -ContentType "application/x-www-form-urlencoded" -Method POST -Body "json=${JSON_OBJECT}" -Uri "${JENKINS_URL}/computer/doCreateItem?name=${NODE_NAME}&type=hudson.slaves.DumbSlave" -UseBasicParsing).statuscode