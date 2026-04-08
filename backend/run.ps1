# Arranca Spring Boot con el mismo Java/Maven que start.ps1 (no requiere mvn en PATH).
# Uso: .\run.ps1   o   .\run.ps1 "-Dspring-boot.run.profiles=supabase"
$ErrorActionPreference = "Stop"

$JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$MAVEN = "C:\Users\Usuario\maven\apache-maven-3.9.6\bin\mvn.cmd"

if (-not (Test-Path "$JAVA_HOME\bin\java.exe")) {
    Write-Host "[ERROR] No se encontro Java en: $JAVA_HOME" -ForegroundColor Red
    exit 1
}
if (-not (Test-Path $MAVEN)) {
    Write-Host "[ERROR] No se encontro Maven en: $MAVEN" -ForegroundColor Red
    Write-Host "Edita JAVA_HOME y MAVEN en este script o anade Maven al PATH del sistema." -ForegroundColor Yellow
    exit 1
}

$env:JAVA_HOME = $JAVA_HOME
$env:PATH = "$JAVA_HOME\bin;$env:PATH"
Set-Location $PSScriptRoot

& $MAVEN "spring-boot:run" @args
