@Clone
Feature: Clone a Print Opportunity

  Background: 
#    Given Admin has already logged into the application

  @Regression
  Scenario: Cloning a Print Opportunity
    When user clones a "Print" Opportunity
      | Element Name                 | Values                    |
      | Stage.Select                 | 10% - Proposal Submitted  |
      | Foundry Involved?.Select     | Yes                       |
      | Available Issues.DuellistBox | NOVEMBER 2021 FOOD & WINE |
