# Run Selenium Test Suite with Automatic Environment Configuration
# Usage:
#   .\run-tests.ps1                  # Runs in headless Chrome
#   .\run-tests.ps1 -Browser edge    # Runs in Microsoft Edge
#   .\run-tests.ps1 -Headless $false # Opens visible browser window

param (
    [string]$Browser = "chrome",
    [bool]$Headless = $true,
    [string]$TestFilter = ""
)

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " SkyCast Weather Automation Test Suite Runner" -ForegroundColor Cyan
Write-Host "==========================================================" -ForegroundColor Cyan

# Set JAVA_HOME and Maven path if not already in system environment
if (-not $env:JAVA_HOME) {
    $defaultJava = "C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot"
    if (Test-Path $defaultJava) {
        $env:JAVA_HOME = $defaultJava
        Write-Host "[OK] Using JAVA_HOME: $defaultJava" -ForegroundColor Green
    }
}

$mavenBin = "C:\Users\DELL\tools\maven\apache-maven-3.9.9\bin"
if (Test-Path $mavenBin) {
    if ($env:PATH -notlike "*$mavenBin*") {
        $env:PATH = "$mavenBin;" + $env:PATH
    }
}

if ($env:JAVA_HOME -and (Test-Path "$env:JAVA_HOME\bin")) {
    if ($env:PATH -notlike "*$env:JAVA_HOME\bin*") {
        $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
    }
}

Write-Host "Browser   : $Browser" -ForegroundColor Yellow
Write-Host "Headless  : $Headless" -ForegroundColor Yellow
if ($TestFilter) {
    Write-Host "Test Filter: $TestFilter" -ForegroundColor Yellow
}

$cmd = "mvn test -Dbrowser=$Browser -Dheadless=$Headless"
if ($TestFilter) {
    $cmd += " -Dtest=$TestFilter"
}

Write-Host "`nExecuting: $cmd`n" -ForegroundColor DarkGray
Invoke-Expression $cmd
