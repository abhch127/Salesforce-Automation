@AccountCreation
Feature: New Account Creation

  Background: 
    Given Admin has already logged into the application

  @AC-2
  Scenario: Advertiser Account Creation
    When User creates new account for "Advertiser" Record type
      | Element Name                               | Values                      |
      | NewAccount.AccountName                     | Test_Advertiser_{TimeStamp} |
      | Billing City.TextBox                       | Burlington                  |
      | Billing Zip/Postal Code.TextBox            |                       27215 |
      | Account Record Sub Type.SingleInputDropdown| Advertiser                  |
      | Billing State/Province.SingleInputDropdown | North Carolina              |
      | Credit Status.SingleInputDropdown          | Cash with Order             |
      | Billing Street.TextBox                     | 786 Boone Station Drive     |

  @Regression
  Scenario: Advertiser Account Approval
    When "Surfina Adams" approves the account
    
  @AC-1
  Scenario: Agency Account Creation
    When User creates new account for "Agency" Record type
      | Element Name                              		| Values                      |
      | NewAccount.AccountName                     		| Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown     | Agency                      |
      | Billing City.TextBox                      		| Burlington                  |
      | Billing Zip/Postal Code.TextBox            		|                       27215 |
      | Billing State/Province.SingleInputDropdown 		| North Carolina              |
      | Credit Status.SingleInputDropdown          		| Cash with Order             |
      | Billing Street.TextBox                     		| 786 Boone Station Drive     |
      | Oracle Credit Hold.SingleInputDropdown    		| Y                           |
      | Account Approval Status.SingleInputDropdown	    | Prospect                    | 
     When "Surfina Adams" approves the account
      
  @Regression
  Scenario: Brand Account Creation
    When User creates new account for "Brand" Record type
      | Element Name                              		| Values                      |
      | NewAccount.AccountName                     		| Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown     | Brand                       |
      | Account Approval Status.SingleInputDropdown	    | Prospect                    | 
    
    
