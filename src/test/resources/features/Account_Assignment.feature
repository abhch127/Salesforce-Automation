@AccountAssignment
Feature: Creation of Account Assignments

  Background: 
    Given Admin has already logged into the application

  @Regression-AccountAssignment
  Scenario: Account assignment for different Record Types
    Given User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Type.SingleInputDropdown                          | Advertiser                  |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
    And "Surfina Adams" approves the account
    When user creates Account assignment for "Title" as Record type
      | Element Name                  | Values                   |
      | 1.Select.CheckBox             | Y                        |
      | 1.Lead.CheckBox               | Y                        |
      | 1.Rep.Select                  | User                     |
      | 1.Rep.SearchBox               | Surfina Adams            |
      | 1.Split Representation.Select | Advertiser               |
      | 1.Split.TextBox               |                      100 |
      | 1.Start Issue.Select          | NOVEMBER 2021 EATINGWELL |
      | 1.End Issue.Select            | DECEMBER 2021 EATINGWELL |
