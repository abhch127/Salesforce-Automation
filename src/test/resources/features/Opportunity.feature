@Regression @Opportunity
Feature: Opportunity validation

  Background: 
    Given Admin has already logged into the application

  @OpportunityCreation-Print
  Scenario: Print Opportunity Creation
    Given user searches object name as "Pipeline" and record name as "KRAFT HEINZ COMPANY, THE 2024" and lands on record
    When User navigates to New Opportunity  for "Print" type
    And User updates the Opportunity entries
      | Element Name                   | Values                   |
      | Brand.SingleInputDropdown      | CLASSICO PRODUCTS        |
      | Advertiser.SingleInputDropdown | KRAFT HEINZ COMPANY, THE |
      | Title.SingleInputDropdown      | ALLRECIPES               |
      | Issue.SingleInputDropdown      | SPRING 2024 ALLRECIPES   |

    And User clicks "Next" Button
    And User clicks "Next" Button
    And User updates the Opportunity entries
      | Element Name                                      | Values |
      | Planning Agency.Lookup                            | BPN    |
      | Opp Estimate.TextBox                              | 10001  |
      | Foundry/Content Strategy involved?.SelectDropdown | Yes    |
    And User clicks "Create" Button
    Then user confirms the creation of Opportunity Type "Print"


  @OpportunityCreation-Digital
  Scenario: Digital Opportunity creation
    Given user searches object name as "Pipeline" and record name as "AQUASANA 2024" and lands on record
    When User navigates to New Opportunity  for "Digital" type
    And User updates the Opportunity entries
      | Element Name                   | Values   |
      | Brand.SingleInputDropdown      | No Brand |
      | Advertiser.SingleInputDropdown | AQUASANA |

    And User clicks "Next" Button
    And User clicks "Next" Button
    And User updates the Opportunity entries
      | Element Name                                      | Values                   |
      | Opportunity Name.TextBox                          | Test_Digital_{TimeStamp} |
      | Planning Agency.Lookup                            | BPN                      |
      | Sales Category.SingleInputDropdown                | Auto                     |
      | Sub Category.SingleInputDropdown                  | Energy                   |
      | Order Type.SelectDropdown                         | Direct IO                |
      | Campaign Start Date.Date                          | 05/01/2024               |
      | Campaign End Date.Date                            | 08/01/2024               |
      | RFP Due Date.Date                                 | 08/01/2024               |
      | Foundry/Content Strategy involved?.SelectDropdown | Yes                      |
      | Opp Estimate.TextBox                              | 10001                    |
    And User clicks "Create" Button

    Then user confirms the creation of Opportunity Type "Digital"

  @OpportunityCreation-F360
  Scenario: F360 Opportunity creation
    Given user searches object name as "Pipeline" and record name as "AQUASANA 2024" and lands on record
    When User navigates to New Opportunity  for "F360" type
    And User updates the Opportunity entries
      | Element Name                             | Values     |
      | Select a Brand.SingleInputDropdown       | --None--   |
      | Select an Advertiser.SingleInputDropdown | AQUASANA   |
      | Opp Estimate.TextBox                     | 10001      |
      | Close Date.Date                          | 06/30/2024 |
      | Execute Month.Date                       | 06/30/2024 |
    And User clicks "Save" Button

    Then user confirms the creation of Opportunity Type "F360"