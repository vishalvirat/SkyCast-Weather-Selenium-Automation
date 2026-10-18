/**
 * SkyCast Weather Portal - Application Logic
 * Realistic sample data for Bengaluru, Ballari, Mumbai, Delhi, and Chennai.
 * Clearly labeled as demo data for automation testing.
 */

// Simulated realistic demo weather dataset
const WEATHER_DATABASE = {
  bengaluru: {
    name: "Bengaluru",
    state: "Karnataka",
    current: {
      temperature: "26°C",
      condition: "Partly Cloudy",
      humidity: "64%",
      windSpeed: "13 km/h",
      feelsLike: "27°C",
      airQuality: "Good (AQI 48)",
      icon: "⛅"
    },
    forecast: [
      { date: "Day 1 (Tomorrow)", temp: "27°C", condition: "Scattered Showers", icon: "🌦️" },
      { date: "Day 2",            temp: "25°C", condition: "Thunderstorms",      icon: "⛈️" },
      { date: "Day 3",            temp: "26°C", condition: "Light Rain",         icon: "🌧️" },
      { date: "Day 4",            temp: "28°C", condition: "Partly Cloudy",      icon: "⛅" },
      { date: "Day 5",            temp: "29°C", condition: "Mostly Sunny",       icon: "🌤️" }
    ]
  },
  ballari: {
    name: "Ballari",
    state: "Karnataka",
    current: {
      temperature: "33°C",
      condition: "Sunny & Dry",
      humidity: "42%",
      windSpeed: "16 km/h",
      feelsLike: "35°C",
      airQuality: "Moderate (AQI 72)",
      icon: "☀️"
    },
    forecast: [
      { date: "Day 1 (Tomorrow)", temp: "34°C", condition: "Clear Sky",          icon: "☀️" },
      { date: "Day 2",            temp: "33°C", condition: "Sunny",              icon: "☀️" },
      { date: "Day 3",            temp: "32°C", condition: "Hot & Breezy",       icon: "🌤️" },
      { date: "Day 4",            temp: "31°C", condition: "Passing Clouds",     icon: "⛅" },
      { date: "Day 5",            temp: "33°C", condition: "Sunny",              icon: "☀️" }
    ]
  },
  mumbai: {
    name: "Mumbai",
    state: "Maharashtra",
    current: {
      temperature: "31°C",
      condition: "Humid & Hazy",
      humidity: "78%",
      windSpeed: "19 km/h",
      feelsLike: "37°C",
      airQuality: "Moderate (AQI 88)",
      icon: "🌤️"
    },
    forecast: [
      { date: "Day 1 (Tomorrow)", temp: "32°C", condition: "Coastal Breeze",     icon: "🌤️" },
      { date: "Day 2",            temp: "31°C", condition: "Partly Cloudy",      icon: "⛅" },
      { date: "Day 3",            temp: "30°C", condition: "Humid with Clouds",  icon: "🌥️" },
      { date: "Day 4",            temp: "32°C", condition: "Sunny Intervals",    icon: "🌤️" },
      { date: "Day 5",            temp: "31°C", condition: "Breezy & Humid",     icon: "⛅" }
    ]
  },
  delhi: {
    name: "Delhi",
    state: "National Capital Territory",
    current: {
      temperature: "29°C",
      condition: "Haze",
      humidity: "51%",
      windSpeed: "9 km/h",
      feelsLike: "30°C",
      airQuality: "Poor (AQI 185)",
      icon: "🌫️"
    },
    forecast: [
      { date: "Day 1 (Tomorrow)", temp: "30°C", condition: "Smoky Haze",         icon: "🌫️" },
      { date: "Day 2",            temp: "31°C", condition: "Warm & Sunny",       icon: "☀️" },
      { date: "Day 3",            temp: "29°C", condition: "Mild Foggy Morning", icon: "🌫️" },
      { date: "Day 4",            temp: "28°C", condition: "Clear Sky",          icon: "☀️" },
      { date: "Day 5",            temp: "27°C", condition: "Sunny & Pleasant",   icon: "🌤️" }
    ]
  },
  chennai: {
    name: "Chennai",
    state: "Tamil Nadu",
    current: {
      temperature: "32°C",
      condition: "Tropical Breeze",
      humidity: "82%",
      windSpeed: "22 km/h",
      feelsLike: "39°C",
      airQuality: "Good (AQI 45)",
      icon: "🌊"
    },
    forecast: [
      { date: "Day 1 (Tomorrow)", temp: "33°C", condition: "Humid & Sunny",      icon: "☀️" },
      { date: "Day 2",            temp: "32°C", condition: "Passing Coastal Rain", icon: "🌦️" },
      { date: "Day 3",            temp: "31°C", condition: "Overcast",           icon: "☁️" },
      { date: "Day 4",            temp: "32°C", condition: "Scattered Showers",  icon: "🌧️" },
      { date: "Day 5",            temp: "33°C", condition: "Warm & Breezy",      icon: "🌤️" }
    ]
  }
};

// DOM Elements
const cityInput = document.getElementById("city-input");
const searchBtn = document.getElementById("search-btn");
const clearBtn = document.getElementById("clear-btn");
const searchForm = document.getElementById("search-form");
const errorMessage = document.getElementById("error-message");
const errorText = document.getElementById("error-text");
const welcomeState = document.getElementById("welcome-state");
const weatherDisplay = document.getElementById("weather-display");

// Weather Details DOM
const cityNameElem = document.getElementById("city-name");
const currentDateElem = document.getElementById("current-date");
const weatherIconElem = document.getElementById("weather-icon");
const temperatureElem = document.getElementById("temperature");
const conditionElem = document.getElementById("condition");
const humidityElem = document.getElementById("humidity");
const windSpeedElem = document.getElementById("wind-speed");
const feelsLikeElem = document.getElementById("feels-like");
const airQualityElem = document.getElementById("air-quality");
const forecastContainer = document.getElementById("forecast-container");
const cityChips = document.querySelectorAll(".city-chip");

/**
 * Normalizes input city string by trimming and converting to lowercase.
 */
function normalizeCityKey(city) {
  return city ? city.trim().toLowerCase() : "";
}

/**
 * Display error banner with given message and hide results.
 */
function showError(message) {
  errorText.textContent = message;
  errorMessage.classList.remove("hidden");
  weatherDisplay.classList.add("hidden");
  welcomeState.classList.add("hidden");
}

/**
 * Hide error banner.
 */
function hideError() {
  errorMessage.classList.add("hidden");
  errorText.textContent = "";
}

/**
 * Renders weather and 5-day forecast for the given city data.
 */
function renderWeather(data) {
  hideError();
  welcomeState.classList.add("hidden");

  // Update current weather elements
  cityNameElem.textContent = data.name;
  currentDateElem.textContent = new Date().toLocaleDateString("en-US", {
    weekday: "long",
    year: "numeric",
    month: "short",
    day: "numeric"
  });
  weatherIconElem.textContent = data.current.icon;
  temperatureElem.textContent = data.current.temperature;
  conditionElem.textContent = data.current.condition;
  humidityElem.textContent = data.current.humidity;
  windSpeedElem.textContent = data.current.windSpeed;
  feelsLikeElem.textContent = data.current.feelsLike;
  airQualityElem.textContent = data.current.airQuality;

  // Clear and populate 5-day forecast cards
  forecastContainer.innerHTML = "";
  data.forecast.forEach((day, index) => {
    const card = document.createElement("div");
    card.className = "forecast-card";
    card.setAttribute("data-test", `forecast-card-${index + 1}`);

    card.innerHTML = `
      <span class="forecast-date" data-test="forecast-date">${day.date}</span>
      <span class="forecast-icon" aria-hidden="true">${day.icon}</span>
      <span class="forecast-temp" data-test="forecast-temp">${day.temp}</span>
      <span class="forecast-condition" data-test="forecast-condition">${day.condition}</span>
    `;

    forecastContainer.appendChild(card);
  });

  // Make weather display visible
  weatherDisplay.classList.remove("hidden");
}

/**
 * Main search handler.
 */
function performSearch() {
  const query = cityInput.value;
  const trimmed = query.trim();

  // Validate empty input
  if (!trimmed) {
    showError("Please enter a city name to search.");
    return;
  }

  const key = normalizeCityKey(trimmed);
  const cityData = WEATHER_DATABASE[key];

  if (cityData) {
    renderWeather(cityData);
  } else {
    showError(`City '${trimmed}' is not supported. Please choose from Bengaluru, Ballari, Mumbai, Delhi, or Chennai.`);
  }
}

// Event Listeners
searchBtn.addEventListener("click", performSearch);

searchForm.addEventListener("submit", (e) => {
  e.preventDefault();
  performSearch();
});

clearBtn.addEventListener("click", () => {
  cityInput.value = "";
  hideError();
  weatherDisplay.classList.add("hidden");
  welcomeState.classList.remove("hidden");
  cityInput.focus();
});

// Quick-select chips
cityChips.forEach((chip) => {
  chip.addEventListener("click", () => {
    const city = chip.getAttribute("data-city");
    cityInput.value = city;
    performSearch();
  });
});
