REM set working directory
set BASE_DIR=%~dp0
set APP_DIR=%BASE_DIR%app
set TARGET_DIR="D:\cloud\Google Drive\Dokumente\Webspace\assetGenerator"

echo %APP_DIR%
echo %TARGET_DIR%

XCOPY %APP_DIR% %TARGET_DIR% /S /E /H /R /Y /V

timeout 100