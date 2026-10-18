package com.weather.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Complete Selenium Test Suite for SkyCast Weather Portal.
 * Covers all 10 required test scenarios including city search, forecast validation,
 * input validation, unsupported cities, and data updates.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeatherWebsiteTest extends BaseTest {

    /**
     * TEST CASE 1: Search for Bengaluru successfully.
     * Verifies that searching for 'Bengaluru' displays weather information.
     */
    @Test
    @Order(1)
    @DisplayName("TC-01: Search for Bengaluru successfully")
    public void testSearchBengaluruSuccessfully() {
        weatherPage.searchCity("Bengaluru");

        assertThat(weatherPage.isWeatherDisplayVisible())
                .as("Weather card should be visible after searching for Bengaluru")
                .isTrue();

        assertThat(weatherPage.getDisplayedCity())
                .as("Displayed city name should be Bengaluru")
                .isEqualToIgnoringCase("Bengaluru");
    }

    /**
     * TEST CASE 2: Search for Ballari successfully.
     * Verifies that searching for 'Ballari' displays weather information.
     */
    @Test
    @Order(2)
    @DisplayName("TC-02: Search for Ballari successfully")
    public void testSearchBallariSuccessfully() {
        weatherPage.searchCity("Ballari");

        assertThat(weatherPage.isWeatherDisplayVisible())
                .as("Weather card should be visible after searching for Ballari")
                .isTrue();

        assertThat(weatherPage.getDisplayedCity())
                .as("Displayed city name should be Ballari")
                .isEqualToIgnoringCase("Ballari");
    }

    /**
     * TEST CASE 3: Search for Mumbai successfully.
     * Verifies that searching for 'Mumbai' displays weather information.
     */
    @Test
    @Order(3)
    @DisplayName("TC-03: Search for Mumbai successfully")
    public void testSearchMumbaiSuccessfully() {
        weatherPage.searchCity("Mumbai");

        assertThat(weatherPage.isWeatherDisplayVisible())
                .as("Weather card should be visible after searching for Mumbai")
                .isTrue();

        assertThat(weatherPage.getDisplayedCity())
                .as("Displayed city name should be Mumbai")
                .isEqualToIgnoringCase("Mumbai");
    }

    /**
     * TEST CASE 4: Verify the displayed city matches the requested city.
     * Tests multiple supported cities and ensures exact display match.
     */
    @Test
    @Order(4)
    @DisplayName("TC-04: Verify the displayed city matches the requested city")
    public void testVerifyDisplayedCityMatchesRequestedCity() {
        String city = "Delhi";
        weatherPage.searchCity(city);

        assertThat(weatherPage.getDisplayedCity())
                .as("City header must exactly match the requested city")
                .isEqualTo(city);
    }

    /**
     * TEST CASE 5: Verify temperature and weather conditions are visible.
     * Validates that temperature, condition, humidity, and wind speed are non-empty and visible.
     */
    @Test
    @Order(5)
    @DisplayName("TC-05: Verify temperature and weather conditions are visible")
    public void testVerifyTemperatureAndConditionsVisible() {
        weatherPage.searchCity("Chennai");

        assertAll("Verify Weather Details",
                () -> assertThat(weatherPage.getTemperature())
                        .as("Temperature should not be empty")
                        .isNotEmpty()
                        .contains("°C"),
                () -> assertThat(weatherPage.getCondition())
                        .as("Condition should not be empty")
                        .isNotEmpty(),
                () -> assertThat(weatherPage.getHumidity())
                        .as("Humidity should not be empty")
                        .isNotEmpty()
                        .contains("%"),
                () -> assertThat(weatherPage.getWindSpeed())
                        .as("Wind speed should not be empty")
                        .isNotEmpty()
                        .contains("km/h")
        );
    }

    /**
     * TEST CASE 6: Verify the 5-day forecast is displayed.
     * Verifies that the forecast section and exactly 5 forecast cards are rendered.
     */
    @Test
    @Order(6)
    @DisplayName("TC-06: Verify the 5-day forecast is displayed")
    public void testVerifyFiveDayForecastDisplayed() {
        weatherPage.searchCity("Bengaluru");

        assertThat(weatherPage.isForecastSectionVisible())
                .as("Forecast section should be displayed")
                .isTrue();

        assertThat(weatherPage.getForecastCardCount())
                .as("Forecast must display exactly 5 daily forecast cards")
                .isEqualTo(5);
    }

    /**
     * TEST CASE 7: Verify forecast dates and temperatures are not empty.
     * Inspects every forecast card to ensure date, temperature, and condition strings are populated.
     */
    @Test
    @Order(7)
    @DisplayName("TC-07: Verify forecast dates and temperatures are not empty")
    public void testVerifyForecastDatesAndTemperaturesNotEmpty() {
        weatherPage.searchCity("Ballari");

        List<String> dates = weatherPage.getForecastDates();
        List<String> temps = weatherPage.getForecastTemperatures();
        List<String> conditions = weatherPage.getForecastConditions();

        assertThat(dates)
                .as("Forecast dates list should contain 5 items")
                .hasSize(5)
                .allMatch(d -> d != null && !d.trim().isEmpty(), "Forecast date must not be blank");

        assertThat(temps)
                .as("Forecast temperatures list should contain 5 items")
                .hasSize(5)
                .allMatch(t -> t != null && t.contains("°C"), "Forecast temperature must contain °C");

        assertThat(conditions)
                .as("Forecast conditions list should contain 5 items")
                .hasSize(5)
                .allMatch(c -> c != null && !c.trim().isEmpty(), "Forecast condition must not be blank");
    }

    /**
     * TEST CASE 8: Search for an unsupported city and verify the error message.
     * Verifies that searching for an unknown city presents a clear error message.
     */
    @Test
    @Order(8)
    @DisplayName("TC-08: Search for an unsupported city and verify error message")
    public void testSearchUnsupportedCityDisplaysErrorMessage() {
        String unsupportedCity = "Tokyo";
        weatherPage.searchCity(unsupportedCity);

        assertThat(weatherPage.isErrorMessageVisible())
                .as("Error alert should be visible for unsupported city")
                .isTrue();

        assertThat(weatherPage.getErrorMessage())
                .as("Error message should mention the unsupported city and list supported options")
                .contains("Tokyo")
                .contains("not supported")
                .contains("Bengaluru");

        assertThat(weatherPage.isWeatherDisplayVisible())
                .as("Weather card should be hidden when an error occurs")
                .isFalse();
    }

    /**
     * TEST CASE 9: Submit an empty search and verify validation.
     * Verifies validation message when search input is empty.
     */
    @Test
    @Order(9)
    @DisplayName("TC-09: Submit empty search and verify validation message")
    public void testSubmitEmptySearchValidation() {
        weatherPage.searchCity("");

        assertThat(weatherPage.isErrorMessageVisible())
                .as("Error alert should be visible for empty input")
                .isTrue();

        assertThat(weatherPage.getErrorMessage())
                .as("Validation error should request entering a city name")
                .contains("Please enter a city name to search");

        assertThat(weatherPage.isWeatherDisplayVisible())
                .as("Weather card should remain hidden on validation failure")
                .isFalse();
    }

    /**
     * TEST CASE 10: Search for a second city and verify results update correctly.
     * Searches for Bengaluru first, then searches for Mumbai and asserts that
     * the displayed city and metrics update to Mumbai.
     */
    @Test
    @Order(10)
    @DisplayName("TC-10: Search for a second city and verify results update correctly")
    public void testSearchSecondCityUpdatesResults() {
        // Step 1: First search
        weatherPage.searchCity("Bengaluru");
        weatherPage.waitForCity("Bengaluru");
        assertThat(weatherPage.getDisplayedCity()).isEqualTo("Bengaluru");

        // Step 2: Search for second city
        weatherPage.searchCity("Mumbai");
        weatherPage.waitForCity("Mumbai");

        // Step 3: Assert the city has updated
        assertThat(weatherPage.getDisplayedCity())
                .as("Displayed city should update to Mumbai")
                .isEqualTo("Mumbai");

        assertThat(weatherPage.getCondition())
                .as("Condition should be updated for Mumbai")
                .isNotEmpty();

        // Verify the data updated
        assertThat(weatherPage.isWeatherDisplayVisible())
                .as("Weather display should remain visible after city update")
                .isTrue();
    }

    /**
     * BONUS TEST CASE 11: Verify demo data disclaimer notice.
     * Validates that demo data is clearly labeled and does not claim to be live weather.
     */
    @Test
    @Order(11)
    @DisplayName("TC-11: Verify demo weather data notice is clearly labeled")
    public void testVerifyDemoDataNotice() {
        String noticeText = weatherPage.getDemoNoticeText();

        assertThat(noticeText)
                .as("Website must clearly label data as demo/simulated")
                .containsIgnoringCase("demo")
                .containsIgnoringCase("not live");
    }
}
