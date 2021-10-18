@AssignmentCase
Feature: Include F360 in Assignment Case

  Background: 
    Given Admin has already logged into the application

  @Test1
  Scenario: Title and Issue fields are not mandatory for F360 Assignment Type
    When user creates a new Account Assignment Request
      | Element Name                           | Values    |
      | Type of Assignment.SingleInputDropdown | F360      |
      | Assignment Reason.TextBox              | Test      |
      | Account Name.SearchBox                 | NETFLIX   |
      | Status.SingleInputDropdown             | New       |
      | Effective Start Date.Date              | 11/1/2021 |
      | My Split %.TextBox                     |       100 |
    Then verify the non-mandatory field "Title"
    And verify the non-mandatory field "Start Issue Name"
    When Clicked on "Save" button
    Then Case should be created successfully

  @Test2
  Scenario: My Split% can be set to only 100% on New Case: Account Assignment Request. An error message is thrown if any value greater than the 100% split is set for My Split%
    When user creates a new Account Assignment Request
      | Element Name                           | Values     |
      | Type of Assignment.SingleInputDropdown | F360       |
      | Assignment Reason.TextBox              | Test       |
      | Account Name.SearchBox                 | NETFLIX    |
      | My Split %.TextBox                     |        105 |
      | Status.SingleInputDropdown             | New        |
      | Effective Start Date.Date              | 11/1/2021  |
      | Effective End Date.Date                | 11/30/2021 |
    When Clicked on "Save" button
    Then an error message "The Sum of Splits is not 100%" should be displayed

  @Test3
  Scenario: Split% can be only set to a total of 100% for Requested Members. An error message is thrown if any member is added with the Split% greater than a total of 100%
    When user creates a new Account Assignment Request
      | Element Name                           | Values     |
      | Type of Assignment.SingleInputDropdown | F360       |
      | Assignment Reason.TextBox              | Test       |
      | Account Name.SearchBox                 | CHEVROLET  |
      | My Split %.TextBox                     |         50 |
      | Status.SingleInputDropdown             | New        |
      | Effective Start Date.Date              | 11/1/2021  |
      | Effective End Date.Date                | 11/30/2021 |
    When Clicked on "Save" button
    Then Case should be created successfully
    And User navigates to "Related" tab
    When User adds a New Request Members
      | Element Name        | Values      |
      | User.SearchBox      | Rajesh Balu |
      | Split %.TextBox     |          60 |
      | Type.SelectDropdown | Seller      |
    Then an error message "The Sum of Splits is not 100%" should be displayed

  @Test4
  Scenario: Split% can be only set to a total of 100% for Requested Members. A error message is thrown if any member is added with the Split% lesser than a total of 100% while in the process of Approving the case.
    When user creates a new Account Assignment Request
      | Element Name                           | Values     |
      | Type of Assignment.SingleInputDropdown | F360       |
      | Assignment Reason.TextBox              | Test       |
      | Account Name.SearchBox                 | CHEVROLET  |
      | My Split %.TextBox                     |         50 |
      | Status.SingleInputDropdown             | New        |
      | Effective Start Date.Date              | 11/1/2021  |
      | Effective End Date.Date                | 11/30/2021 |
    When Clicked on "Save" button
    And Case should be created successfully
    And User navigates to "Related" tab
    When User adds a New Request Members
      | Element Name        | Values      |
      | User.SearchBox      | Rajesh Balu |
      | Split %.TextBox     |          40 |
      | Type.SelectDropdown | Seller      |
    When Clicked on "mark-complete" button
    Then an error message "The Sum of Splits is not 100%" should be displayed

  @Test5
  Scenario: Approve a F360 Assignment Type Case with 100% Split
    When user creates a new Account Assignment Request
      | Element Name                           | Values    |
      | Type of Assignment.SingleInputDropdown | F360      |
      | Assignment Reason.TextBox              | Test      |
      | Account Name.SearchBox                 | NETFLIX   |
      | Status.SingleInputDropdown             | New       |
      | Effective Start Date.Date              | 11/1/2021 |
      | My Split %.TextBox                     |       100 |
    When Clicked on "Save" button
    Then Case should be created successfully
    And User should be able to approve the Case
      | Element Name          | Values           |
      | Status.SelectDropdown | Closed: Approved |
