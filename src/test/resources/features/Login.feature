# Login.feature
# This file is written in plain English
# Business team can read and understand this

Feature: SauceDemo Login
  As a user
  I want to login to SauceDemo
  So that I can access the products

  # ── Scenario 1: Valid Login ──────────────────────────
  Scenario: Login with valid credentials
    Given I am on the SauceDemo login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see the products page

  # ── Scenario 2: Invalid Login ─────────────────────────
  Scenario: Login with wrong password
    Given I am on the SauceDemo login page
    When I enter username "standard_user"
    And I enter password "wrong_password"
    And I click the login button
    Then I should see an error message

  # ── Scenario 3: Locked User ───────────────────────────
  Scenario: Login with locked out user
    Given I am on the SauceDemo login page
    When I enter username "locked_out_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see an error message

  # ── Scenario Outline: Multiple users ──────────────────
  # Scenario Outline = run same scenario with different data
  # Like @DataProvider but in plain English!
  Scenario Outline: Login with multiple users
    Given I am on the SauceDemo login page
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see "<result>"

    # Examples table provides data for each run
    Examples:
      | username          | password     | result        |
      | standard_user     | secret_sauce | products page |
      | locked_out_user   | secret_sauce | error message |
      | standard_user     | wrong_pass   | error message |