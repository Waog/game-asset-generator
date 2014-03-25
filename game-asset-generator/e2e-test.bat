REM Windows script for running e2e tests
REM You have to run server and capture some browser first
REM
REM Requirements:
REM - NodeJS (http://nodejs.org/)
REM - Karma (npm install -g karma)

set BASE_DIR=%~dp0
"%BASE_DIR%node_modules\.bin\karma" start "%BASE_DIR%\config\karma.conf.js"

timeout 100