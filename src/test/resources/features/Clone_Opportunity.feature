@Functional @Clone
Feature: Clone a Print Opportunity

  Background: 
    Given Admin has already logged into the application

  @Clone1
  Scenario: Cloning a Print Opportunity
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
    When user clones a "Print" Opportunity
      | Element Name                     | Values                           |
      | Stage.SelectDropdown             | 10% - Proposal Submitted         |
      | Foundry Involved?.SelectDropdown | Yes                              |
      | Available Issues.DuellistBox     | OCTOBER/NOVEMBER 2021 ALLRECIPES |

  @Clone2
  Scenario: Cloning a Digital Opportunity
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
    And user creates a Pipeline
      | Element Name | Values |
      | Year.TextBox |   2021 |
    When User creates new Opportunity for "Digital" type
      | Element Name                             | Values                   |
      | Opp Name.TextBox                         | OPP-DGI-TEST             |
      | Select an Advertiser.SingleInputDropdown | {AccountName}            |
      | Opp Estimate.TextBox                     |                     4500 |
      | Campaign Start Date.Date                 | Jan 1, 2021              |
      | Campaign End Date.Date                   | Dec 31, 2021             |
      | Stage.SelectDropdown                     | 10% - Proposal Submitted |
      | Order Type.SingleInputDropdown           | Direct IO                |
      | Contextual.DuellistBox                   | Food & Drink             |
    When user clones a "Digital" Opportunity
      | Element Name                          | Values                   |
      | Opportunity Name.TextBox              | OPP-DGI-CLONE            |
      | Stage.SelectDropdown                  | 10% - Proposal Submitted |
      | Foundry Involved?.SingleInputDropdown | Yes                      |
      | Opp Estimate.TextBox                  |                     4500 |
      | Order Type.SingleInputDropdown        | Direct IO                |
      | Campaign Start Date.Date              | Jan 1, 2021              |
      | Campaign End Date.Date                | Dec 31, 2021             |
