@AccountCreation
Feature: New Account Creation

  Background: 
    Given Admin has already logged into the application

  @AC-1
  Scenario: Advertiser Account Creation
    When User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown       | Advertiser                  |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |

  @Regression
  Scenario: Advertiser Account Approval
    When "Surfina Adams" approves the account

  @AC-2
  Scenario: Agency Account Creation
    When User creates new account for "Agency" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown       | Agency                      |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
      | Oracle Credit Hold.SingleInputDropdown            | Y                           |
      | Account Approval Status.SingleInputDropdown       | Prospect                    |
    When "Surfina Adams" approves the account

  @Regression
  Scenario: Brand Account Creation
    When User creates new account for "Brand" Record type
      | Element Name                                | Values                      |
      | NewAccount.AccountName                      | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown | Brand                       |
      | Account Approval Status.SingleInputDropdown | Prospect                    |
