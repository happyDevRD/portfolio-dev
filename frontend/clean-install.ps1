# Reinstala dependencias desde cero (corrige ajv incompleto / "Cannot find module dist/ajv.js").
# Recomendado: Node 20 LTS (https://nodejs.org/). Node 24 puede dar avisos con Angular 17.
$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

Write-Host "Limpiando cache de npm..." -ForegroundColor Yellow
npm cache clean --force

Write-Host "Eliminando node_modules y caché de Angular..." -ForegroundColor Yellow
if (Test-Path "node_modules") {
    Remove-Item -Recurse -Force "node_modules"
}
if (Test-Path ".angular") {
    Remove-Item -Recurse -Force ".angular"
}

Write-Host "npm ci..." -ForegroundColor Yellow
npm ci
if ($LASTEXITCODE -ne 0) {
    Write-Host "npm ci fallo; probando npm install..." -ForegroundColor Yellow
    npm install
}

# Cache npm corrupto o extraccion incompleta puede dejar ajv sin .js en dist/ (solo .d.ts / .map).
Write-Host "Asegurando ajv@8.12.0 completo (cache limpia + --force)..." -ForegroundColor Yellow
npm install ajv@8.12.0 --save-exact --force --no-audit --no-fund

if (-not (Test-Path $ajvMain)) {
    Write-Host ""
    Write-Host "[ERROR] No existe $ajvMain" -ForegroundColor Red
    Write-Host "Prueba: instalar Node 20 LTS, cerrar el IDE, y volver a ejecutar este script." -ForegroundColor Yellow
    Write-Host "Si usas antivirus, excluye la carpeta del proyecto del analisis en tiempo real." -ForegroundColor Yellow
    exit 1
}

Write-Host "Listo. Prueba: npm start" -ForegroundColor Green
