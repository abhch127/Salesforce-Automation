@Clone
Feature: Clone a Print Opportunity

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Cloning a Print Opportunity
    When user clones a Print Opportunity
      | Element Name                           | Values                           |
      | ClonePopup.Stage.Select                | 10% - Proposal Submitted         |
      | ClonePopup.FoundryInvolved.Select      | Yes                              |
      | ClonePopup.AvailableIssues.DuellistBox | DECEMBER/JANUARY 2022 ALLRECIPES |
