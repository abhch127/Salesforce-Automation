@AccountApproval
Feature: New Account Approval

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Advertiser Account Approval
    When user clicks on "HomePage GearIcon"
    And user clicks on "Setup Option"
    Then user switches to "New Setup Tab"
    And user waits for "Setup Page Global Search TextBox"
    And user searches for "Surfina Adams" in "Setup Page Global Search TextBox"
    Then user waits for "Surfina Adams User Details Page to Load"
    And user switches to "Surfina Adams" frame
    And user clicks on "Login Button"
    Then user waits for "Surfina Adams Home Page to Load"
    And user searches for "New Account" in "User HomePage Global Search TextBox"
    Then user waits for "New Account Detail Page to Load"
    And user clicks on "Approve Bread Crumb"
    And user clicks on "Mark as Current Account Approval Status Button"
    Then user waits for "Page to Refresh"
    And user scrolls to "Account Status"
    And "Account Status" must be set to "A"
