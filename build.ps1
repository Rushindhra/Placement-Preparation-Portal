$ErrorActionPreference = "Stop"

$app = Split-Path -Parent $MyInvocation.MyCommand.Path
$tomcat = Split-Path -Parent (Split-Path -Parent $app)
$servletApi = Join-Path $tomcat "lib\servlet-api.jar"
$mysqlJar = Get-ChildItem -Path (Join-Path $app "WEB-INF\lib") -Filter "mysql-connector-j-*.jar" | Select-Object -First 1
$bcryptJar = Get-ChildItem -Path (Join-Path $app "WEB-INF\lib") -Filter "jbcrypt-*.jar" | Select-Object -First 1

if (!(Test-Path $servletApi)) {
  throw "Could not find servlet-api.jar at $servletApi"
}
if ($null -eq $mysqlJar) {
  throw "Missing MySQL Connector/J jar in WEB-INF\lib"
}
if ($null -eq $bcryptJar) {
  throw "Missing jbcrypt jar in WEB-INF\lib"
}

$sources = Get-ChildItem -Path (Join-Path $app "WEB-INF\classes") -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
$classpath = "$servletApi;$($mysqlJar.FullName);$($bcryptJar.FullName)"

javac -encoding UTF-8 -cp $classpath -d (Join-Path $app "WEB-INF\classes") $sources
Write-Host "Compilation complete. Restart Tomcat, then open /PlacementPortal/login"
