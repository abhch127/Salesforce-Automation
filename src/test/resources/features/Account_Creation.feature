@AccountCreation
Feature: New Account Creation

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Advertiser Account Creation
    When User creates new account for "Advertiser" Record type
      | Element Name                      | Values                      |
      | NewAccount.AccountName            | Test_Advertiser_{TimeStamp} |
      | NewAccount.BillingCity.TextBox    | Burlington                  |
      | NewAccount.BillingZip.TextBox     |                       27215 |
      | NewAccount.Type.Dropdown          | Advertiser                  |
      | NewAccount.BillingState.Dropdown  | North Carolina              |
      | NewAccount.CreditStatus.Dropdown  | Cash with Order             |
      | NewAccount.BillingStreet.TextArea | 786 Boone Station Drive     |

  @Regression
  Scenario: Advertiser Account Approval
    When "Surfina Adams" approves the account

  @Regression
  Scenario: Adding a Pipeline to an account
    When user creates a Pipeline
      | Element Name                  | Values |
      | NewPipelinePopup.Year.TextBox |   2021 |