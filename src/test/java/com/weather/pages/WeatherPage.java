package com.weather.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object Model representing the SkyCast Weather Application.
 * Encapsulates DOM locators, element interactions, and synchronization logic.
 */
public class WeatherPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators using stable IDs and CSS selectors
    private final By cityInput = By.id("city-input");
    private final By searchBtn = By.id("search-btn");
    private final By clearBtn = By.id("clear-btn");
    private final By errorMessage = By.id("error-message");
    private final By errorText = By.id("error-text");
    private final By weatherDisplay = By.id("weather-display");
    private final By cityName = By.id("city-name");
    private final By temperature = By.id("temperature");
    private final By condition = By.id("condition");
    private final By humidity = By.id("humidity");
    private final By windSpeed = By.id("wind-speed");
    private final By forecastSection = By.id("forecast-section");
    private final By forecastCards = By.cssSelector("#forecast-container .forecast-card");
    private final By forecastDates = By.cssSelector("#forecast-container .forecast-date");
    private final By forecastTemps = By.cssSelector("#forecast-container .forecast-temp");
    private final By forecastConditions = By.cssSelector("#forecast-container .forecast-condition");
    private final By demoNotice = By.id("demo-notice");

    public WeatherPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the weather website.
     *
     * @param url file:/// or http:// URL of the website
     */
    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput));
    }

    /**
     * Enters a city name into the input field and clicks the Search button.
     *
     * @param city City name to search
     */
    public void searchCity(String city) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(cityInput));
        input.clear();
        if (city != null && !city.isEmpty()) {
            input.sendKeys(city);
        }
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        button.click();
    }

    /**
     * Clears the search input box using the Clear button.
     */
    public void clickClear() {
        wait.until(ExpectedConditions.elementToBeClickable(clearBtn)).click();
    }

    /**
     * Checks if the main weather results card is visible.
     */
    public boolean isWeatherDisplayVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(weatherDisplay)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the displayed city name from the current weather card.
     */
    public String getDisplayedCity() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cityName)).getText().trim();
    }

    /**
     * Waits until the city title displays the expected city name.
     */
    public void waitForCity(String expectedCity) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cityName, expectedCity));
    }

    /**
     * Gets the displayed temperature text.
     */
    public String getTemperature() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(temperature)).getText().trim();
    }

    /**
     * Gets the displayed weather condition text.
     */
    public String getCondition() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(condition)).getText().trim();
    }

    /**
     * Gets the displayed humidity text.
     */
    public String getHumidity() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(humidity)).getText().trim();
    }

    /**
     * Gets the displayed wind speed text.
     */
    public String getWindSpeed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(windSpeed)).getText().trim();
    }

    /**
     * Checks if the error message banner is visible.
     */
    public boolean isErrorMessageVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the error banner message text.
     */
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorText)).getText().trim();
    }

    /**
     * Checks if the 5-day forecast section is visible.
     */
    public boolean isForecastSectionVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(forecastSection)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits until all 5 forecast cards are rendered with non-empty text.
     */
    public void waitForForecastCards() {
        wait.until(driver -> {
            List<WebElement> elements = driver.findElements(forecastCards);
            if (elements.size() != 5) {
                return false;
            }
            List<WebElement> dates = driver.findElements(forecastDates);
            return dates.size() == 5 && dates.stream().allMatch(e -> {
                String txt = e.getText().trim();
                if (txt.isEmpty()) {
                    txt = e.getAttribute("textContent").trim();
                }
                return !txt.isEmpty();
            });
        });
    }

    /**
     * Gets the count of rendered forecast cards.
     */
    public int getForecastCardCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(forecastSection));
        waitForForecastCards();
        List<WebElement> cards = driver.findElements(forecastCards);
        return cards.size();
    }

    /**
     * Gets list of forecast dates for all 5 days.
     */
    public List<String> getForecastDates() {
        waitForForecastCards();
        return driver.findElements(forecastDates)
                .stream()
                .map(e -> {
                    String text = e.getText().trim();
                    return text.isEmpty() ? e.getAttribute("textContent").trim() : text;
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets list of forecast temperatures for all 5 days.
     */
    public List<String> getForecastTemperatures() {
        waitForForecastCards();
        return driver.findElements(forecastTemps)
                .stream()
                .map(e -> {
                    String text = e.getText().trim();
                    return text.isEmpty() ? e.getAttribute("textContent").trim() : text;
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets list of forecast conditions for all 5 days.
     */
    public List<String> getForecastConditions() {
        waitForForecastCards();
        return driver.findElements(forecastConditions)
                .stream()
                .map(e -> {
                    String text = e.getText().trim();
                    return text.isEmpty() ? e.getAttribute("textContent").trim() : text;
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets the disclaimer notice verifying demo data labeling.
     */
    public String getDemoNoticeText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(demoNotice)).getText().trim();
    }
}
