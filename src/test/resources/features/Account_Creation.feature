@AccountCreation
Feature: New Account Creation

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Advertiser Account Creation
    When User creates new account for "Advertiser" Record type
      | Element Name                               | Values                      |
      | NewAccount.AccountName                     | Test_Advertiser_{TimeStamp} |
      | Billing City.TextBox                       | Burlington                  |
      | Billing Zip/Postal Code.TextBox            |                       27215 |
      | Type.SingleInputDropdown                   | Advertiser                  |
      | Billing State/Province.SingleInputDropdown | North Carolina              |
      | Credit Status.SingleInputDropdown          | Cash with Order             |
      | Billing Street.TextBox                     | 786 Boone Station Drive     |

  @Regression
  Scenario: Advertiser Account Approval
    When "Surfina Adams" approves the account
