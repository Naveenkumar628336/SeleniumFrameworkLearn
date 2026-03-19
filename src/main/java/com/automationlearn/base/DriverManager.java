package com.automationlearn.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.automationlearn.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverManager — manages WebDriver lifecycle using ThreadLocal.
 *
 * WHY THREADLOCAL?
 * When running tests in PARALLEL, each thread needs its OWN WebDriver instance.
 * ThreadLocal ensures each thread gets its own isolated copy.
 * Without this, parallel tests would share one browser → chaos!
 */
public class DriverManager {
	// Each thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Private constructor - utility class, no instantiation
    private DriverManager() {}

    /**
     * Returns the WebDriver for the current thread.
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    /** Launch browser and store it in ThreadLocal for this thread */
    public static void initDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        boolean headless = ConfigReader.isHeadless();
        WebDriver driver;
        switch (browser) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions ffopts = new FirefoxOptions();
			if(headless) ffopts.addArguments("--headless");
			driver = new FirefoxDriver(ffopts);
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions edgeopts = new EdgeOptions();
			if(headless) edgeopts.addArguments("--headless");
			driver = new EdgeDriver(edgeopts);
			break;
		case "chrome":
		default:
		    WebDriverManager.chromedriver().setup();
		    ChromeOptions opts = new ChromeOptions();

		    if (ConfigReader.isHeadless()) {
		        // Headless settings for CI/CD server
		        opts.addArguments("--headless=new");
		        opts.addArguments("--no-sandbox");
		        opts.addArguments("--disable-dev-shm-usage");
		        opts.addArguments("--disable-gpu");
		        opts.addArguments("--window-size=1920,1080");
		        // Required for CI servers
		        opts.addArguments("--remote-allow-origins=*");
		    }

		    opts.addArguments("--start-maximized");
		    opts.addArguments("--disable-notifications");
		    driver = new ChromeDriver(opts);
		    break;		
		} 
        
        // Apply timeout settings from config
        driver.manage().timeouts()
        .implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
    driver.manage().timeouts()
        .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
    driver.manage().window().maximize();

    driverThreadLocal.set(driver); // Store for THIS thread
}

/** Quit browser + remove from ThreadLocal to prevent memory leak */
public static void quitDriver() {
    WebDriver driver = driverThreadLocal.get();
    if (driver != null) {
        driver.quit();
        driverThreadLocal.remove(); // Critical! Prevents memory leak
    }
        
        

    }
}
