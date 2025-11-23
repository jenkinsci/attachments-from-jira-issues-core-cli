REM This script will build AudioSDK for Win32, Win64, Linux32, Linux64, and Mac_OSX
REM The command is:

REM call buildAll.bat <1option#> <2option#> <3option#> <4option#>

REM where 1option# is the Product
REM where 2option# is branches or tags source
REM where 3option# is the branch or tag source name
REM where 4option# is the version


REM WE need to build Windows First and then Linux and Mac because of doxigen

SET _Product=%1

IF "%2"=="tags" (SET _BuildSource=%2) ELSE (SET _BuildSource="branches")

SET _BuildTagBranch=%3
SET _ReleaseBuild=%3
SET _Version=%4

SET _ReleaseBuild=%_ReleaseBuild:_= %
for /f %%a in ("%_ReleaseBuild%") do set _Release=%%a

SET _SVNMainPath=svn+ssh://xx.xx.xx/xx/Production/xx/xx/

SET _SVNCompletePath=%_SVNMainPath%/%_BuildSource%/%_BuildTagBranch%


SET _DataDrive=E

SET _TargetFolder=%_BuildTagBranch%

SET _WINTgtDirPkg=%_DataDrive%:\%_TargetFolder%\Package
SET _WINTgtDirDecoder=%_DataDrive%:\%_TargetFolder%\Package

SET _TracDir=%_DataDrive%:\Trac\%_Product%\v%_Version%
SET _MainPackageFolder=Package
SET _PackageDir=%_DataDrive%:\%_TargetFolder%\%_MainPackageFolder%

SET _PSCP=c:\putty\pscp
SET _PLINK=c:\putty\plink

SET _TracLogin=xxxxxxxx
SET _TracPasswd=xxxxxxxx

SET _TracServer=10.45.xx.xx
SET _TracParentFolder=/Trac/%_Product%/htdocs
SET _TracMkdirScript=/Trac/TracMkDir
SET _TracModConfScript=/Trac/TracModConf
SET _TracVersion=%_Version:.=_%

SET _Login=xxxxxxxx
SET _Passwd=xxxxxxx

SET _Linux64Server=10.45.xx.xx
SET _LinuxParentFolder=/xx/xx/xx

SET _LinuxMainPath=/software/projects/xx
SET _LinuxBuildScript=%_LinuxParentFolder%/buildLinux_64_32
SET _LinuxPkgScript=%_LinuxParentFolder%/buildLinux_pkg

SET _MACServer=10.45.xx.xx
SET _MacParentFolder=/xx/xx/xx

SET _MacMainPath=/xx/xx/xx/xx
SET _MacBuildScript=%_MacParentFolder%/buildMac
SET _MacPkgScript=%_MacParentFolder%/buildMac_pkg

E:
cd %_DataDrive%:\

REM ---------------------------------
REM ---- Build LINUX ---------------

%_PLINK% %_Linux64Server% -l %_Login% -pw %_Passwd% %_LinuxBuildScript% "%_Product% %_BuildSource% %_BuildTagBranch% %_Version%"
