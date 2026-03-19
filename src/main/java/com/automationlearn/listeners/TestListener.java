package com.automationlearn.listeners;

import com.automationlearn.utils.ExtentReportManager;
import com.automationlearn.utils.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

// Implements BOTH ITestListener and ISuiteListener
// ISuiteListener → listens at suite level (all tests together)
// ITestListener  → listens at individual test level
public class TestListener
    implements ITestListener, ISuiteListener {

    // ── ISuiteListener — Suite level ──────────────────────

    // Called ONCE when entire suite starts
    // This is the right place to create report
    @Override
    public void onStart(ISuite suite) {
        System.out.println("=== Suite Started: "
            + suite.getName() + " ===");
        ExtentReportManager.createReport();
    }

    // Called ONCE when entire suite finishes
    // This is the right place to save report
    @Override
    public void onFinish(ISuite suite) {
        System.out.println("=== Suite Finished ===");
        ExtentReportManager.flushReport();
    }

    // ── ITestListener — Test level ────────────────────────

    // Called before each <test> block starts
    // We do NOT create report here anymore
    @Override
    public void onStart(ITestContext context) {
        System.out.println("--- Test Block: "
            + context.getName() + " ---");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Nothing needed here
    }

    // Before each @Test method
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        System.out.println("\n→ Test: " + testName);
        ExtentReportManager.createTest(testName, description);
    }

    // After @Test passes
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ PASSED: "
            + result.getMethod().getMethodName());
        ExtentReportManager.getTest()
            .log(Status.PASS, "Test Passed ✅");
    }

    // After @Test fails
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("❌ FAILED: " + testName);

        // Log error
        ExtentReportManager.getTest()
            .log(Status.FAIL,
                "Test Failed ❌ : "
                + result.getThrowable().getMessage());

        // Take screenshot
        String screenshotPath =
            ScreenshotUtils.takeScreenshot(testName);

        // Attach to report
        try {
            ExtentReportManager.getTest()
                .addScreenCaptureFromPath(
                    screenshotPath, "Failure Screenshot");
        } catch (Exception e) {
            System.out.println("Screenshot attach failed: "
                + e.getMessage());
        }
    }

    // After @Test skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭️ SKIPPED: "
            + result.getMethod().getMethodName());
        ExtentReportManager.getTest()
            .log(Status.SKIP, "Test Skipped ⏭️");
    }
}