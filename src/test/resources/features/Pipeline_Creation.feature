@PipelineCreation
Feature: New Pipeline Creation

  Background: 
    Given Admin has already logged into the application
    And User creates new account for "Advertiser" Record type
      | Element Name                                      | Values                      |
      | NewAccount.AccountName                            | Test_Advertiser_{TimeStamp} |
      | Account Record Sub Type.SingleInputDropdown       | Advertiser                  |
      | Billing City.TextBox                              | Burlington                  |
      | Billing Zip/Postal Code.TextBox                   |                       27215 |
      | Billing State/Province.SingleInputDropdown        | North Carolina              |
      | Billing Street.TextBox                            | 786 Boone Station Drive     |
      | Copy Billing Address to Shipping Address.Checkbox | Y                           |
      | Credit Status.SingleInputDropdown                 | Cash with Order             |

  @Pipeline1
  Scenario: Adding a Pipeline to an account
    When "Surfina Adams" approves the account
    Then user creates a Pipeline
      | Element Name | Values |
      | Year.TextBox |   2021 |

  @Pipeline2
  Scenario: Error message validation while adding another pipeline for the same year
    When user creates a Pipeline
      | Element Name | Values |
      | Year.TextBox |   2021 |
    And user creates a Pipeline
      | Element Name | Values |
      | Year.TextBox |   2021 |
    Then an error message "existing Pipeline with the same Advertiser for the selected FY" should be displayed

  @Pipeline3
  Scenario: Error message must be displayed when Data Steward tries to create New Pipeline when the Account is in Approved status
    When "Surfina Adams" approves the account
    And navigate to profile "Surfina Adams"
    And "Surfina Adams" tries to create a Pipeline
		Then an error message "You can create a pipeline ONLY on a prospect record" should be displayed