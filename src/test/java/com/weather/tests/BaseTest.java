package com.weather.tests;

import com.weather.pages.WeatherPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Base test class managing WebDriver lifecycle and environment configuration.
 * Uses Selenium Manager to resolve browser drivers automatically.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected WeatherPage weatherPage;
    protected String websiteUrl;

    @BeforeEach
    public void setUp() {
        // Resolve path to the local website index.html
        Path websitePath = Paths.get(System.getProperty("user.dir"), "website", "index.html");
        websiteUrl = websitePath.toUri().toString();

        // Browser selection via system property (-Dbrowser=chrome or -Dbrowser=edge, default: chrome)
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        if ("edge".equals(browser)) {
            EdgeOptions options = new EdgeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--window-size=1920,1080", "--disable-gpu", "--no-sandbox");
            File edgeExe = new File("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
            if (edgeExe.exists()) {
                options.setBinary(edgeExe);
            }
            driver = new EdgeDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--window-size=1920,1080", "--disable-gpu", "--no-sandbox", "--remote-allow-origins=*");
            File chromeExe = new File("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            if (chromeExe.exists()) {
                options.setBinary(chromeExe);
            }
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        weatherPage = new WeatherPage(driver);
        weatherPage.open(websiteUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}
