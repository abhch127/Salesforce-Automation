@Regression
Feature: New Pipeline Creation

  Background: 
    Given Admin has already logged into the application

  @PipelineCreation
  Scenario: Adding a Pipeline to a Account
    When user creates a Pipeline
      | Element Name             | Value |
      | AccountPage.Year.TextBox |  2021 |
