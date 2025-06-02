@echo off
cd /d "%~dp0"

REM ==== Database credentials ====
SET DB_NAME=smartlocker
SET DB_USER=admin
SET DB_PASSWORD=admin
SET PGPASSWORD=%DB_PASSWORD%

echo ---------------------------------------------
echo Running all create.sql files in sql\* recursively...
echo ---------------------------------------------

REM ==== Run each create.sql file ====
for /R sql %%F in (create.sql) do (
    echo Executing: %%F
    psql -U %DB_USER% -d %DB_NAME% -f "%%F"
)

echo ---------------------------------------------
echo All SQL scripts executed.
pause