@PipelineCreation
Feature: New Pipeline Creation

  Background: 
    Given Admin has already logged into the application

  @Pipeline
  Scenario: Adding a Pipeline to an account
   When User creates new account for "Advertiser" Record type
      | Element Name                               | Values                      |
      | NewAccount.AccountName                     | Test_Advertiser_{TimeStamp} |
      | Billing City.TextBox                       | Burlington                  |
      | Billing Zip/Postal Code.TextBox            |                       27215 |
      | Account Record Sub Type.SingleInputDropdown| Advertiser                  |
      | Billing State/Province.SingleInputDropdown | North Carolina              |
      | Credit Status.SingleInputDropdown          | Cash with Order             |
      | Billing Street.TextBox                     | 786 Boone Station Drive     | 
   When "Surfina Adams" approves the account
   When user creates a Pipeline
      | Element Name | Values |
      | Year.TextBox |   2021 |
