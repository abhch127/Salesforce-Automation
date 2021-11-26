@Functional @PayerCheckbox
Feature: Payer Checkbox Validation for Advertiser

  Background: 
    Given Admin has already logged into the application

  @PayerCheckbox1
  Scenario: Payer checkbox is auto-selected when Account is created with both Billing and Shipping address
    When User creates new account for "Advertiser" Record type
      | Element Name                                | Values                      |
      | NewAccount.AccountName                      | Test_Advertiser_{TimeStamp} |
      | No Website.Checkbox                         | Y                           |
      | Account Record Sub Type.SingleInputDropdown | Advertiser                  |
      | Billing City.TextBox                        | Burlington                  |
      | Billing Zip/Postal Code.TextBox             |                       27215 |
      | Billing State/Province.SingleInputDropdown  | North Carolina              |
      | Billing Street.TextBox                      | 786 Boone Station Drive     |
      | Shipping City.TextBox                       | Des Moines                  |
      | Shipping Zip/Postal Code.TextBox            |                       50309 |
      | Shipping State/Province.SingleInputDropdown | Illinois                    |
      | Shipping Street.TextBox                     | 220 SW 9th St               |
      | Credit Status.SingleInputDropdown           | Cash with Order             |
    Then "Payer?" checkbox must be "enabled"

  @PayerCheckbox2
  Scenario: Payer checkbox is disabled when Account is created only with shipping address
    When User creates new account for "Advertiser" Record type
      | Element Name                                | Values                      |
      | NewAccount.AccountName                      | Test_Advertiser_{TimeStamp} |
      | No Website.Checkbox                         | Y                           |
      | Account Record Sub Type.SingleInputDropdown | Advertiser                  |
      | Shipping City.TextBox                       | Des Moines                  |
      | Shipping Zip/Postal Code.TextBox            |                       50309 |
      | Shipping State/Province.SingleInputDropdown | Illinois                    |
      | Shipping Street.TextBox                     | 220 SW 9th St               |
      | Credit Status.SingleInputDropdown           | Cash with Order             |
    Then "Payer?" checkbox must be "disabled"
