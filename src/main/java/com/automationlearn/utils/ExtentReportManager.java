package com.automationlearn.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest
        = new ThreadLocal<>();

    public static void createReport() {
        // Only create report ONCE
        // If already created — skip
        if (extentReports != null) {
            System.out.println("Report already exists — skipping");
            return;
        }

        ExtentSparkReporter sparkReporter =
            new ExtentSparkReporter("reports/ExtentReport.html");

        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("Test Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat(
            "dd/MM/yyyy HH:mm:ss");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("OS",
            System.getProperty("os.name"));
        extentReports.setSystemInfo("Java",
            System.getProperty("java.version"));
        extentReports.setSystemInfo("Browser",
            ConfigReader.getBrowser());
        extentReports.setSystemInfo("Tester", "Automation QA");

        System.out.println("✅ Extent Report created!");
    }

    public static void createTest(String testName,
                                   String description) {
        ExtentTest test = extentReports
            .createTest(testName, description);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println(
                "✅ Report saved: reports/ExtentReport.html");
        }
    }
}