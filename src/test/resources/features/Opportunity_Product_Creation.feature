@OpportunityProductCreation @Regression
Feature: Opportunity Product Creation

  Background:
    Given Admin has already logged into the application

@OpportunityProductCreation-Print
    Scenario: Creating the Product on the Print Opportunity Type
      When user creates a new Pipeline for "Test_Advertiser_Mar24_1305" Advertiser
        | Element Name | Values |
        | Year.TextBox | 2024   |

      And User searches object name as "Pipeline" and record name as "Test_Advertiser_Mar24_1305 2024" and lands on record
      And User navigates to New Opportunity  for "Print" type
      And User updates the Opportunity entries
        | Element Name                   | Values                     |
        | Brand.SingleInputDropdown      | No Brand                   |
        | Advertiser.SingleInputDropdown | Test_Advertiser_Mar24_1305 |
        | Title.SingleInputDropdown      | ALLRECIPES                 |
        | Issue.SingleInputDropdown      | SPRING 2024 ALLRECIPES     |

      And User clicks "Next" Button
      And User clicks "Next" Button
      And User updates the Opportunity entries
        | Element Name                                      | Values |
        | Planning Agency.Lookup                            | BPN    |
        | Opp Estimate.TextBox                              | 10001  |
        | Foundry/Content Strategy involved?.SelectDropdown | Yes    |
      And User clicks "Create" Button
      And  user confirms the creation of Opportunity Type "Print"
      And User searches object name as "Pipeline" and record name as "Test_Advertiser_Mar24_1305 2024" and lands on record
      And user creates the Product for the "Print" Opportunity

  @OpportunityProductCreationFromPipelineDirectly-Print
  Scenario: Creating the Product on the Print Opportunity Type
    When User searches object name as "Pipeline" and record name as "Test_Advertiser_Mar24_1305 2024" and lands on record
    And user creates the Product for the "Print" Opportunity


  @OpportunityProductCreationFromPipelineDirectly-DirectMedia
  Scenario: Creating the Product on the Print Opportunity Type
    When User searches object name as "Pipeline" and record name as "Test_Advertiser_Mar24_1305 2024" and lands on record
    And user creates the Product for the "Direct Media" Opportunity


  @OpportunityProductCreation-Digital
    Scenario:Creating the Product on the Digital Opportunity Type
    When user creates a new Pipeline for "Test_Advertiser_Mar24_1305" Advertiser
      | Element Name | Values |
      | Year.TextBox | 2024   |
    Given User searches object name as "Pipeline" and record name as "Test_Advertiser_Mar24_1305 2024" and lands on record
    When User navigates to New Opportunity  for "Digital" type
    And User updates the Opportunity entries
      | Element Name                   | Values                     |
      | Brand.SingleInputDropdown      | No Brand                   |
      | Advertiser.SingleInputDropdown | Test_Advertiser_Mar24_1305 |

    And User clicks "Next" Button
    And User clicks "Next" Button
    And User updates the Opportunity entries
      | Element Name                                      | Values                    |
      | Opportunity Name                                  | A_Test_Digital_{TimeStamp} |
      | Planning Agency.Lookup                            | BPN                       |
      | Sales Category.SingleInputDropdown                | Auto                      |
      | Sub Category.SingleInputDropdown                  | Energy                    |
      | Order Type.SelectDropdown                         | Direct IO                 |
      | Campaign Start Date.Date                          | 05/01/2024                |
      | Campaign End Date.Date                            | 08/01/2024                |
      | RFP Due Date.Date                                 | 08/01/2024                |
      | Foundry/Content Strategy involved?.SelectDropdown | Yes                       |
      | Opp Estimate.TextBox                              | 10001                     |
    And User clicks "Create" Button
    Then user confirms the creation of Opportunity Type "Digital"
    And User searches object name as "Pipeline" and record name as "Test_Advertiser_Mar24_1305 2024" and lands on record
    And user creates the Product for the "Digital" Opportunity


  @OpportunityProductCreationFromPipelineDirectly-Digital
  Scenario: Creating the Product on the Print Opportunity Type
    When User searches object name as "Pipeline" and record name as "AQUASANA 2024" and lands on record
    And user creates the Product for the "Digital" Opportunity