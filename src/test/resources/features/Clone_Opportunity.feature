@Clone
Feature: Clone a Print Opportunity

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Cloning a Print Opportunity
    When user clones a "Print" Opportunity
      | Element Name                     | Values                           |
      | Stage.SelectDropdown             | 10% - Proposal Submitted         |
      | Foundry Involved?.SelectDropdown | Yes                              |
      | Available Issues.DuellistBox     | OCTOBER/NOVEMBER 2021 ALLRECIPES |
