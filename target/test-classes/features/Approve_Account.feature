@AccountApproval
Feature: New Account Approval

  Background: 
    Given Admin has already logged into the application

  @Account_Rejection_Brand
  Scenario: Brand Account Approval
   Given User creates new account for "Brand" Record type
      | Element Name                              		| Values                      |
      | NewAccount.AccountName                     		| Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown     | Brand                       |
      | Account Approval Status.SingleInputDropdown	    | Prospect                    | 
  	When "Surfina Adams" rejects the account
      
  