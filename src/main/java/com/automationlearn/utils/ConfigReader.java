package com.automationlearn.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    // This runs ONCE when class is loaded
    static {
        try {
            FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/config.properties"
            );
            properties.load(fis);
            fis.close();
            System.out.println("✅ Config loaded!");
        } catch (IOException e) {
            System.out.println("❌ Config file not found!");
            e.printStackTrace();
        }
    }

    public static String getBrowser()          { return properties.getProperty("browser"); }
 //   public static boolean isHeadless()         { return Boolean.parseBoolean(properties.getProperty("headless")); }
    public static boolean isHeadless() {
        // Check system property first
        // CI/CD passes: -Dheadless=true
        String sysHeadless = System.getProperty("headless");
        if (sysHeadless != null && !sysHeadless.isEmpty()) {
            return Boolean.parseBoolean(sysHeadless);
        }
        // Fall back to config.properties
        return Boolean.parseBoolean(
            properties.getProperty("headless"));
    }
    public static int getImplicitWait()        { return Integer.parseInt(properties.getProperty("implicit.wait")); }
    public static int getExplicitWait()        { return Integer.parseInt(properties.getProperty("explicit.wait")); }
    public static int getPageLoadTimeout()     { return Integer.parseInt(properties.getProperty("page.load.timeout")); }
    public static String getSauceDemoUrl()     { return properties.getProperty("saucedemo.url"); }
    public static String getDemoQAUrl()        { return properties.getProperty("demoqa.url"); }
    public static String getTheInternetUrl()   { return properties.getProperty("the.internet.url"); }
    public static String getOrangeHRMUrl()     { return properties.getProperty("orangehrm.url"); }
    public static String getReportsPath()      { return properties.getProperty("reports.path"); }
    public static String getScreenshotsPath()  { return properties.getProperty("screenshots.path"); }
}