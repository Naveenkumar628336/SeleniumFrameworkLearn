package com.automationlearn.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automationlearn.base.BaseTest;
import com.automationlearn.pages.internet.AlertsPage;
import com.automationlearn.pages.internet.IFramePage;
import com.automationlearn.pages.internet.WindowsPage;
import com.automationlearn.pages.internet.DropdownPage;

public class TheInternetTest extends BaseTest {

    // Base URL for all tests in this class
    String baseUrl = "https://the-internet.herokuapp.com";

    // ── Test 1: Simple Alert ──────────────────────────────
    // Scenario: Click button → alert appears → read text → click OK
    @Test(description = "Handle simple JS alert")
    public void testSimpleAlert() {

        // Go to alerts page
        navigateTo(baseUrl + "/javascript_alerts");

        // Create page object
        AlertsPage alertsPage = new AlertsPage();

        // Click button — this triggers the alert popup
        alertsPage.clickJsAlert();

        // Read alert text BEFORE accepting
        // Why before? Once you accept — alert is gone
        String alertText = alertsPage.getAlertText();
        System.out.println("Alert says: " + alertText);

        // Click OK on the alert
        alertsPage.acceptAlert();

        // After accepting — result text appears on page
        // Verify result contains expected message
        String result = alertsPage.getResultText();
        Assert.assertTrue(result.contains("You successfully clicked an alert"),
            "Alert was not handled! Result: " + result);

        System.out.println("✅ Simple alert passed! Result: " + result);
    }

    // ── Test 2: Confirm — Click OK ────────────────────────
    // Scenario: Click button → confirm appears → click OK
    @Test(description = "Accept confirm alert")
    public void testConfirmAccept() {

        navigateTo(baseUrl + "/javascript_alerts");
        AlertsPage alertsPage = new AlertsPage();

        alertsPage.clickJsConfirm();

        // Click OK — result should say "Ok"
        alertsPage.acceptConfirm();

        String result = alertsPage.getResultText();
        Assert.assertTrue(result.contains("Ok"),
            "Confirm OK not handled! Result: " + result);

        System.out.println("✅ Confirm OK passed! Result: " + result);
    }

    // ── Test 3: Confirm — Click Cancel ───────────────────
    // Scenario: Click button → confirm appears → click Cancel
    @Test(description = "Dismiss confirm alert")
    public void testConfirmDismiss() {

        navigateTo(baseUrl + "/javascript_alerts");
        AlertsPage alertsPage = new AlertsPage();

        alertsPage.clickJsConfirm();

        // Click Cancel — result should say "Cancel"
        alertsPage.dismissConfirm();

        String result = alertsPage.getResultText();
        Assert.assertTrue(result.contains("Cancel"),
            "Confirm Cancel not handled! Result: " + result);

        System.out.println("✅ Confirm Cancel passed! Result: " + result);
    }

    // ── Test 4: Prompt Alert ──────────────────────────────
    // Scenario: Click button → prompt appears → type text → click OK
    @Test(description = "Type text in prompt alert")
    public void testPromptAlert() {

        navigateTo(baseUrl + "/javascript_alerts");
        AlertsPage alertsPage = new AlertsPage();

        alertsPage.clickJsPrompt();

        // Type text in prompt and click OK
        alertsPage.typeInPromptAndAccept("Hello Selenium!");

        // Result should contain what we typed
        String result = alertsPage.getResultText();
        Assert.assertTrue(result.contains("Hello Selenium!"),
            "Prompt text not in result! Result: " + result);

        System.out.println("✅ Prompt passed! Result: " + result);
        
     // ── Test 5: iFrame ────────────────────────────────────
     // Scenario: Go to page → switch into iFrame → type text
//                → read text → verify it matches
    }
     @Test(description = "Type and read text inside iFrame")
     public void testIFrame() {

         // Go to iFrame practice page
         navigateTo(baseUrl + "/iframe");

         // Create page object
         IFramePage iframePage = new IFramePage();

         // Type text inside the iFrame editor
         // Internally this does:
         // 1. switchTo().frame()
         // 2. find element inside
         // 3. type text
         // 4. switchTo().defaultContent()
         iframePage.typeTextInEditor("Testing iFrame with Selenium!");

         // Read back the text we just typed
         String actualText = iframePage.getEditorText();
         System.out.println("Text in iFrame: " + actualText);

         // Verify text matches what we typed
         Assert.assertEquals(actualText, "Testing iFrame with Selenium!",
             "iFrame text does not match!");

         System.out.println("✅ iFrame test passed!");
     }
  // ── Test: Select by Visible Text ─────────────────────
  // Scenario: Open dropdown → select Option 1 by text
//             → verify Option 1 is selected
  @Test(description = "Select dropdown option by visible text")
  public void testDropdownSelectByText() {

      // Go to dropdown practice page
      navigateTo(baseUrl + "/dropdown");

      // Create page object
      DropdownPage dropdownPage = new DropdownPage();

      // Check how many options exist
      System.out.println("Total options: " + dropdownPage.getTotalOptions());

      // Select Option 1 by its visible text
      dropdownPage.selectByText("Option 1");

      // Verify Option 1 is now selected
      String selected = dropdownPage.getSelectedOptionText();
      Assert.assertEquals(selected, "Option 1",
          "Option 1 was not selected! Found: " + selected);

      System.out.println("✅ Selected by text: " + selected);
  }

  // ── Test: Select by Value ─────────────────────────────
  // Scenario: Open dropdown → select Option 2 by value
//             → verify Option 2 is selected
  @Test(description = "Select dropdown option by value")
  public void testDropdownSelectByValue() {

      navigateTo(baseUrl + "/dropdown");
      DropdownPage dropdownPage = new DropdownPage();

      // Select Option 2 using its HTML value attribute
      // In HTML: <option value="2">Option 2</option>
      dropdownPage.selectByValue("2");

      String selected = dropdownPage.getSelectedOptionText();
      Assert.assertEquals(selected, "Option 2",
          "Option 2 was not selected! Found: " + selected);

      System.out.println("✅ Selected by value: " + selected);
  }

  // ── Test: Select by Index ─────────────────────────────
  // Scenario: Open dropdown → select by index position
//             → verify correct option is selected
  @Test(description = "Select dropdown option by index")
  public void testDropdownSelectByIndex() {

      navigateTo(baseUrl + "/dropdown");
      DropdownPage dropdownPage = new DropdownPage();

      // Index 1 = "Option 1" (index 0 = "Please select an option")
      dropdownPage.selectByIndex(1);

      String selected = dropdownPage.getSelectedOptionText();
      Assert.assertEquals(selected, "Option 1",
          "Index 1 should select Option 1! Found: " + selected);

      System.out.println("✅ Selected by index: " + selected);
  }
//── Test: New Window Basic ────────────────────────────
//Scenario: Click link → new window opens
//          → switch to new window → verify text
//          → close new window → switch back
@Test(description = "Handle new browser window")
public void testNewWindow() {

   // Go to windows practice page
   navigateTo(baseUrl + "/windows");

   // Create page object
   // Constructor saves original window handle automatically
   WindowsPage windowsPage = new WindowsPage();

   // Verify we are on original window
   System.out.println("Current title before click: "
           + windowsPage.getCurrentWindowTitle());

   // Click link — this opens a NEW window/tab
   windowsPage.clickNewWindowLink();

   // At this point — 2 windows are open
   // But Selenium is still on Window 1
   // We need to switch manually

   // Switch to the new window
   windowsPage.switchToNewWindow();

   // Now Selenium is on Window 2
   // Verify the heading on new window
   String headingText = windowsPage.getHeadingText();
   System.out.println("New window heading: " + headingText);

   Assert.assertEquals(headingText, "New Window",
       "New window heading does not match!");

   System.out.println("✅ New window verified: " + headingText);

   // Close the new window
   windowsPage.closeCurrentWindow();

   // IMPORTANT — after closing window
   // Selenium is on a closed window — must switch back!
   windowsPage.switchToOriginalWindow();

   // Verify we are back on original window
   System.out.println("✅ Back to original: "
           + windowsPage.getCurrentWindowTitle());
}

//── Test: Verify Original Window After Switch ─────────
//Scenario: Switch to new window → come back
//          → verify original page is still intact
@Test(description = "Verify original window after switching back")
public void testSwitchBackToOriginal() {

   navigateTo(baseUrl + "/windows");
   WindowsPage windowsPage = new WindowsPage();

   // Open new window
   windowsPage.clickNewWindowLink();

   // Switch to new window
   windowsPage.switchToNewWindow();

   // Verify new window
   Assert.assertEquals(windowsPage.getHeadingText(), "New Window",
       "Not on new window!");

   // Close new window and switch back
   windowsPage.closeCurrentWindow();
   windowsPage.switchToOriginalWindow();

   // Verify original window title
   String originalTitle = windowsPage.getCurrentWindowTitle();
   Assert.assertTrue(originalTitle.contains("The Internet"),
       "Not back on original window! Title: " + originalTitle);

   System.out.println("✅ Original window intact: " + originalTitle);
}
}