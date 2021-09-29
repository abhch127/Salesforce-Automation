@Opportunity
Feature: Opportunity validation

  Background: 
    Given Admin has already logged into the application

  @Opportunity_Creation
  Scenario: Print Opportunity Creation
    When User creates new Opportunity for "Print" type
      | Element Name               | Values                     |
      | Select an Advertiser.Input | Test_Advertiser_Sep28_0838 |
