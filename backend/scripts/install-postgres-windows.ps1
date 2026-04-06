# Alternativa a Docker: instala PostgreSQL en Windows con winget (ejecutar en PowerShell).
# Tras instalar, crea un usuario/clave o usa los que definas y actualiza application-local.yml si no son postgres/postgres.
# Servicio por defecto suele escuchar en localhost:5432.
#
# winget (no requiere admin en muchos equipos):
#   winget search postgresql
#   winget install --id PostgreSQL.PostgreSQL.16 --accept-package-agreements --accept-source-agreements
#
# El instalador gráfico pide contraseña del superusuario; úsala en application-local.yml.
Write-Host "Si winget no encuentra el paquete, instala desde https://www.postgresql.org/download/windows/"
Write-Host "Luego ajusta application-local.yml (url, username, password) si difieren de postgres/postgres."
