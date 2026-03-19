Feature: DemoQA Web Table
  As a user
  I want to manage records in web table
  So that I can add and search employees

  Scenario: Search existing record in table
    Given I am on the DemoQA web tables page
    When I search for "Cierra"
    Then the table should contain "Cierra"

  Scenario: Add new record to table
    Given I am on the DemoQA web tables page
    When I click the Add button
    And I fill the form with following details
      | firstName | lastName | email           | age | salary | department |
      | Ravi      | Kumar    | ravi@test.com   | 30  | 50000  | QA         |
    And I submit the form
    Then the table should contain "Ravi"