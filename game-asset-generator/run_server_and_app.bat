REM set working directory
set BASE_DIR=%~dp0
set BASE_DRIVE=%~d0
%BASE_DRIVE%
cd %BASE_DIR%

REM open website
START "" "http://localhost:8000/app/index.html"

REM run server (hopefully before the website started).
node scripts\web-server.js

timeout 100