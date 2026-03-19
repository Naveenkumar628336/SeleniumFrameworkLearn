package com.automationlearn.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestRunner — connects feature files to step definitions
 *
 * @CucumberOptions:
 * features  → where .feature files are
 * glue      → where step definitions are
 * plugin    → report formats to generate
 * monochrome → clean console output
 * tags      → run specific scenarios only
 */
@CucumberOptions(
    // Location of feature files
    features = "src/test/resources/features",

    // Location of step definitions and hooks
    glue = "com.automationlearn.stepdefinitions",

    // Report plugins
    plugin = {
        "pretty",                           // console output
        "html:reports/cucumber-report.html", // HTML report
        "json:reports/cucumber-report.json"  // JSON report
    },

    // Clean console output
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // AbstractTestNGCucumberTests handles all execution
    // No code needed here!
}