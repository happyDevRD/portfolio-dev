# Script para levantar el proyecto completo (Backend + Frontend)

$JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$MAVEN = "C:\Users\Usuario\maven\apache-maven-3.9.6\bin\mvn.cmd"
$ROOT = $PSScriptRoot

Write-Host ""
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "   Portfolio Dev - Iniciando proyecto  " -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Verificar Java
if (-not (Test-Path "$JAVA_HOME\bin\java.exe")) {
    Write-Host "[ERROR] No se encontro Java en: $JAVA_HOME" -ForegroundColor Red
    exit 1
}

# Verificar Maven
if (-not (Test-Path $MAVEN)) {
    Write-Host "[ERROR] No se encontro Maven en: $MAVEN" -ForegroundColor Red
    exit 1
}

$env:JAVA_HOME = $JAVA_HOME
$env:PATH = "$JAVA_HOME\bin;$env:PATH"

# Levantar Backend en una nueva ventana de PowerShell
Write-Host "[1/2] Iniciando Backend (Spring Boot)..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", `
    "`$env:JAVA_HOME='$JAVA_HOME'; `$env:PATH='$JAVA_HOME\bin;' + `$env:PATH; Set-Location '$ROOT\backend'; & '$MAVEN' spring-boot:run"

Start-Sleep -Seconds 3

# Levantar Frontend en una nueva ventana de PowerShell
Write-Host "[2/2] Iniciando Frontend (Angular)..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", `
    "Set-Location '$ROOT\frontend'; npm start"

Write-Host ""
Write-Host "======================================" -ForegroundColor Green
Write-Host "  Servicios iniciados correctamente   " -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Green
Write-Host ""
Write-Host "  Frontend:  http://localhost:4200" -ForegroundColor White
Write-Host "  Backend:   http://localhost:8080/api" -ForegroundColor White
Write-Host "  Swagger:   http://localhost:8080/swagger-ui.html" -ForegroundColor White
Write-Host ""
Write-Host "Cierra las ventanas de PowerShell para detener los servicios." -ForegroundColor Gray
