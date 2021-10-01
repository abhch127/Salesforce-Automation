@AccountAssignment
Feature: Creation of Account Assignments

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Account assignment for different Record Types
    When user selects "Title" as Record type
      | Element Name                  | Values                   |
      | 1.Select.CheckBox             | Y                        |
      | 1.Lead.CheckBox               | Y                        |
      | 1.Rep.Select                  | User                     |
      | 1.Rep.SearchBox               | Surfina Adams            |
      | 1.Split Representation.Select | Advertiser               |
      | 1.Split.TextBox               |                      100 |
      | 1.Start Issue.Select          | NOVEMBER 2021 EATINGWELL |
      | 1.End Issue.Select            | DECEMBER 2021 EATINGWELL |
