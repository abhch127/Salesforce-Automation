@Opportunity
Feature: Opportunity validation

  Background: 
    Given Admin has already logged into the application

  @Opportunity_Creation
  Scenario: Print Opportunity Creation
    When User creates new Opportunity for "Print" type

      | Element Name                      | Values                      |
      | Select an Advertiser.SingleInputDropdown   	  | Test_Advertiser_Sep28_0838  |
      | Stage.SelectDropdown   	  | 10% - Proposal Submitted                    |
      |Available Titles.DuellistBox | ALLRECIPES|
      |Opp Estimate for ALLRECIPES.TextBox|1000|
      |Available Issues.DuellistBox| DECEMBER/JANUARY 2022 ALLRECIPES|
