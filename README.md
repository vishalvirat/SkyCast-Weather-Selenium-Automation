# Implement Selenium Tests for City Search and Forecast Display on a Weather Website

A complete, production-grade automated testing solution featuring **SkyCast Weather Portal** (HTML5/CSS3/JavaScript) and a comprehensive **Selenium WebDriver (Java) + JUnit 5** test suite built with **Maven** and **Selenium Manager**.

---

## 🌟 Project Overview

This project satisfies all requirements for building and validating a local, interactive weather web application:
1. **Weather Website (SkyCast)**:
   - Modern, responsive UI with glassmorphism styling and clear layout.
   - City search bar with validation and quick-selection chips.
   - Multi-city support: **Bengaluru**, **Ballari**, **Mumbai**, **Delhi**, and **Chennai**.
   - Detailed current weather metrics: Temperature (°C), Weather Condition, Humidity (%), Wind Speed (km/h), Feels Like (°C), and Air Quality Index (AQI).
   - **5-Day Extended Forecast** displaying dates, conditions, temperatures, and icons.
   - User-friendly error messaging for unsupported cities and empty inputs.
   - Clear and prominent **Demo Data Disclaimer Notice** confirming data is simulated for testing.
   - **Zero external API dependencies**: runs 100% locally with no API keys, accounts, or paid services.

2. **Automated Selenium Test Suite**:
   - Built on **Selenium WebDriver 4.26+** (leveraging **Selenium Manager** for automatic ChromeDriver/EdgeDriver resolution).
   - Structured using the industry-standard **Page Object Model (POM)** pattern (`WeatherPage.java`).
   - Powered by **JUnit 5 (Jupiter)** and **AssertJ** for expressive, descriptive assertions.
   - Comprehensive test cases covering all 10 required functional, validation, and regression scenarios.
   - Cross-browser support for **Google Chrome** and **Microsoft Edge** with configurable headless/headed modes.

---

## 📁 Project Directory Structure

```text
d:\st_project\
├── pom.xml                                      # Maven build configuration & dependencies
├── README.md                                    # Documentation and Windows 11 execution guide
├── website\                                     # Frontend Web Application
│   ├── index.html                               # Responsive weather portal layout with stable IDs
│   ├── css\
│   │   └── style.css                            # Modern responsive CSS design
│   └── js\
│       └── app.js                               # Application logic, realistic demo dataset & validation
└── src\
    └── test\
        └── java\
            └── com\
                └── weather\
                    ├── pages\
                    │   └── WeatherPage.java     # Page Object Model with explicit WebDriverWait
                    └── tests\
                        ├── BaseTest.java        # WebDriver initialization, browser selection & teardown
                        └── WeatherWebsiteTest.java # All 10 JUnit 5 automated test cases
```

---

## 🧪 Automated Test Suite Coverage

| Test Case | Method | Description |
|---|---|---|
| **TC-01** | `testSearchBengaluruSuccessfully()` | Searches for Bengaluru and verifies weather display is visible and title matches. |
| **TC-02** | `testSearchBallariSuccessfully()` | Searches for Ballari and verifies weather card display. |
| **TC-03** | `testSearchMumbaiSuccessfully()` | Searches for Mumbai and verifies weather card display. |
| **TC-04** | `testVerifyDisplayedCityMatchesRequestedCity()` | Verifies the city header exactly matches the queried city (Delhi). |
| **TC-05** | `testVerifyTemperatureAndConditionsVisible()` | Validates that temperature (°C), condition, humidity (%), and wind speed (km/h) are visible and non-empty. |
| **TC-06** | `testVerifyFiveDayForecastDisplayed()` | Asserts the 5-day forecast section is visible and contains exactly 5 forecast cards. |
| **TC-07** | `testVerifyForecastDatesAndTemperaturesNotEmpty()` | Checks all 5 forecast cards to confirm date and temperature strings are present and formatted correctly. |
| **TC-08** | `testSearchUnsupportedCityDisplaysErrorMessage()` | Submits an unsupported city (e.g. `Tokyo`) and verifies the error banner appears while weather details remain hidden. |
| **TC-09** | `testSubmitEmptySearchValidation()` | Submits an empty search query and verifies the validation warning is triggered. |
| **TC-10** | `testSearchSecondCityUpdatesResults()` | Searches for Bengaluru, then executes a second search for Mumbai, verifying the displayed city and metrics update dynamically. |
| **TC-11** | `testVerifyDemoDataNotice()` | *(Bonus)* Asserts that the website displays a clear disclaimer stating the data is simulated demo data and not live weather. |

---

## 💻 Prerequisites on Windows 11

Ensure the following tools are installed:
1. **Java Development Kit (JDK 17 or higher)**:
   - Check version: `java -version`
   - If not installed, install via winget:
     ```powershell
     winget install EclipseAdoptium.Temurin.17.JDK
     ```
2. **Apache Maven (3.8 or higher)**:
   - Check version: `mvn -version`
   - If not installed, install via winget:
     ```powershell
     winget install Apache.Maven
     ```
3. **Web Browser**:
   - Google Chrome or Microsoft Edge (pre-installed on Windows 11).

---

## 🚀 How to Run on Windows 11

### 1. View and Test the Website Locally

You can open the website directly in any browser with no web server needed:

#### Option A: Direct File Open (PowerShell)
```powershell
Start-Process "d:\st_project\website\index.html"
```

#### Option B: Via Python HTTP Server (Optional)
If you have Python installed:
```powershell
cd d:\st_project\website
python -m http.server 8080
```
Then visit: `http://localhost:8080`

---

### 2. Run the Selenium Automation Tests

Navigate to the project directory in PowerShell or Command Prompt:
```powershell
cd d:\st_project
```

#### Run All Tests in Headless Chrome (Default)
```powershell
mvn clean test
```

#### Run Tests in Microsoft Edge
```powershell
mvn test -Dbrowser=edge
```

#### Run Tests with Visual Browser Window (Headed Mode)
To observe Selenium interact with the browser in real-time:
```powershell
mvn test -Dheadless=false
```

#### Run a Specific Test Case
```powershell
# Run only Test Case 1 (Bengaluru search)
mvn test -Dtest=WeatherWebsiteTest#testSearchBengaluruSuccessfully

# Run only Test Case 10 (City update)
mvn test -Dtest=WeatherWebsiteTest#testSearchSecondCityUpdatesResults
```

---

## 📊 Viewing Test Reports

After running `mvn test`, Maven Surefire generates detailed HTML and XML reports in:
- Text Reports: `d:\st_project\target\surefire-reports\`
- To generate a browsable HTML site report:
  ```powershell
  mvn surefire-report:report-only
  Start-Process "d:\st_project\target\site\surefire-report.html"
  ```

---

## 🎯 Locator Strategy & Design Principles

- **Stable Element IDs**: Every interactive element uses dedicated HTML IDs (`#city-input`, `#search-btn`, `#clear-btn`, `#error-message`, `#city-name`, `#temperature`, `#condition`, `#humidity`, `#wind-speed`, `#forecast-section`, `#forecast-container`).
- **Explicit Synchronization**: Uses `WebDriverWait` with `ExpectedConditions` (`elementToBeClickable`, `visibilityOfElementLocated`) avoiding flaky tests or arbitrary sleep pauses.
- **Selenium Manager Integration**: No manual download of `chromedriver.exe` or `msedgedriver.exe` required; Selenium 4 automatically fetches and pairs the exact compatible driver version.
- **Offline & Cross-Platform Resilient**: Tests resolve the website's absolute URI via Java NIO `Paths.get("website", "index.html").toUri()`, ensuring tests run without external network flakiness.
