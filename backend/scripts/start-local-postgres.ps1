# Levanta PostgreSQL local con Docker (misma config que application-local.yml).
# Requisito: Docker Desktop instalado y en ejecución; `docker` debe funcionar en esta consola.
$ErrorActionPreference = "Stop"
Set-Location (Split-Path -Parent $PSScriptRoot)
Write-Host "Iniciando Postgres (docker compose) en $(Get-Location)..."
docker compose up -d
if ($LASTEXITCODE -ne 0) {
    Write-Error "Fallo docker compose. ¿Está Docker Desktop abierto? Prueba: docker version"
    exit $LASTEXITCODE
}
Write-Host "Listo. Arranca el backend con: mvn spring-boot:run `"-Dspring-boot.run.profiles=local`""
