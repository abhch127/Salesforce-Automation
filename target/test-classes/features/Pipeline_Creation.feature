@PipelineCreation
Feature: New Pipeline Creation

  Background: 
    Given Admin has already logged into the application

  @Regression
  Scenario: Adding a Pipeline to an account
    When user creates a Pipeline
      | Element Name                  | Values |
      | NewPipelinePopup.Year.TextBox |   2021 |
