@AccountApproval
Feature: New Account Approval

  Background: 
    Given Admin has already logged into the application

  @Account_Rejection_Brand
  Scenario: Brand Account Approval
    Given User creates new account for "Brand" Record type
      | Element Name                                | Values                      |
      | NewAccount.AccountName                      | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown | Brand                       |
      | Account Approval Status.SingleInputDropdown | Prospect                    |
    When "Surfina Adams" rejects the account

  @AA-1
  Scenario: Error message must be displayed when anyone expect Admin or Data Stewards tries to Approve an Account
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
    When "Rosemary Garcia" tries to "Approve" an account

  @AA-2
  Scenario: Error message must be displayed when anyone expect Admin or Data Stewards tries to Rejects an Account
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
    When "Grace Simpson" tries to "Reject" an account
