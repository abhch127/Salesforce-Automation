@Functional @ContractIO
Feature: Opportunity validation

  Background: 
    Given Admin has already logged into the application

  @ContractIO_Creation
  Scenario: ContractIO Creation
    Given User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | No Website.Checkbox                               | Y                           |
      | Account Record Sub Type.SingleInputDropdown       | Advertiser                  |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |
    And "Surfina Adams" approves the account
    And user creates a Pipeline
      | Element Name | Values |
      | Year.TextBox |   2021 |
    And User creates new Opportunity for "Print" type
      | Element Name                             | Values                           |
      | Select an Advertiser.SingleInputDropdown | {AccountName}                    |
      | Stage.SelectDropdown                     | 10% - Proposal Submitted         |
      | Available Titles.DuellistBox             | ALLRECIPES                       |
      | Opp Estimate for ALLRECIPES.TextBox      |                             1000 |
      | Available Issues.DuellistBox             | DECEMBER/JANUARY 2022 ALLRECIPES |
    When User creates new ContractIO for "Advertiser" account
      | Element Name               | Values         |
      | Title.SelectDropdown       | ALLRECIPES     |
      | Start Date.Date            | Jan 1, 2021    |
      | End Date.Date              | Dec 31, 2021   |
      | Customer IO Number.TextBox |          00724 |
      | ContractIO.BillTo.Dropdown | Agency         |
      | ContractIO.Agency          | BRIGANCE MEDIA |
      | ContractIO.Opportunity     |              0 |
      | ContractIO.BillToAddress   |              2 |
