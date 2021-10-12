@AssignmentCase
Feature: Include F360 in Assignment Case

  Background: 
    Given Admin has already logged into the application

  Scenario: Title and Issue fields should not be displayed for F360 Assignment Type
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

  Scenario: Check if the maximium allowed split is 100%
    When user creates a new Account Assignment Request
      | Element Name                           | Values    |
      | Type of Assignment.SingleInputDropdown | F360      |
      | Assignment Reason.TextBox              | Test      |
      | Account Name.SearchBox                 | CRAZY     |
      | Status.SingleInputDropdown             | New       |
      | Effective Start Date.Date              | 11/1/2021 |
      | My Split %.TextBox                     |       105 |
    When Clicked on "Save" button
    Then an error message should be displayed

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
    And Case should be created successfully
    Then User should be able to approve the Case
      | Element Name          | Values           |
      | Status.SelectDropdown | Closed: Approved |
