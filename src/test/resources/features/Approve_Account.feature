@Regression @ApproveReject
Feature: New Account Approval

  Background: 
    Given Admin has already logged into the application

  @AccountApproval
  Scenario: Print Opportunity Creation
    Given User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown       | Advertiser                  |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
    And "Surfina Adams" approves the account

  @AccountRejection
  Scenario: Brand Account Approval
    Given User creates new account for "Brand" Record type
      | Element Name                                | Values                      |
      | NewAccount.AccountName                      | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown | Brand                       |
      | Account Approval Status.SingleInputDropdown | Prospect                    |
    When "Surfina Adams" rejects the account

  @ErrorValidation-1
  Scenario: Error message must be displayed when anyone expect Admin or Data Stewards tries to Approve an Account
    When User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown       | Advertiser                  |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
    When "Rosemary Garcia" tries to "Approve" an account

  @ErrorValidation-2
  Scenario: Error message must be displayed when anyone expect Admin or Data Stewards tries to Rejects an Account
    When User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown       | Advertiser                  |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
    When "Grace Simpson" tries to "Reject" an account
