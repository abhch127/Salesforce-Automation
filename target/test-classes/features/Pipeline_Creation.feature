@Regression
Feature: New Pipeline Creation

  Background: 
    Given Admin has already logged into the application

  @PipelineCreation
  Scenario: Adding a Pipeline to a Account
    #    When user searches for an existing "Account" "Test_Advertiser_Sep02_1658" in "User HomePage Global Search TextBox"
    #    When user searches for "New Account" in "User HomePage Global Search TextBox"
    #    And user waits for "Account Detail Page to Load"
    #    And user clicks on "Create New Pipeline Button"
    #    Then user waits for "New Pipeline for the Account Popup"
    #    Then user waits for "Parent Advertiser Clear button"
    #    And user clears existing value from "Year TextBox"
    #    And user enters "2022" in "Year Textbox"
    #    And user clicks on "Pipeline Popup Save Button"
    #    Then user waits for "Pipeline Detail Page to Load"
    When user creates a Pipeline
      | Element Name                  | Value                      |
      | AccountPage.Year.TextBox      |                       2021 |
