@CreateOpportunity

Feature: Opportunity Creation

  Background:
    Given Admin has already logged into the application

    @CreateOpportunity-Print  @regression
    Scenario: Create Print Opportunity and Validate
      Given User searches object name as "Pipeline" and record name as "KRAFT HEINZ COMPANY, THE 2025" and lands on record
      When User creates "Print" Opportunity and Validate
        | Element Name                       | Values                   |
        | Brand                              | CLASSICO PRODUCTS        |
        | Advertiser                         | KRAFT HEINZ COMPANY, THE |
        | Title                              | ALLRECIPES               |
        | Issue                              | SPRING 2025 ALLRECIPES   |
        | Planning Agency                    | BPN                      |
        | Opp Estimate                       | 10001                    |
        | Foundry/Content Strategy involved? | Yes                      |
        | Sales Category                      | Auto                    |
        | Sub Category                        | Other - Auto            |

  @CreateOpportunity-Digital
  Scenario: Create Digital Opportunity and Validate
    Given User searches object name as "Pipeline" and record name as "AQUASANA 2024" and lands on record
    And User creates "Digital" Opportunity and Validate
      | Element Name                       | Values                     |
      | Brand                              | No Brand                   |
      | Advertiser                         | AQUASANA                   |
      | Opportunity Name                   | A_Test_Digital_{TimeStamp} |
      | Planning Agency                    | BPN                        |
      | Sales Category                     | Auto                       |
      | Sub Category                       | Energy                     |
      | Order Type                         | Direct IO                  |
      | Campaign Start Date                | 05/01/2024                 |
      | Campaign End Date                  | 08/01/2024                 |
      | RFP Due Date                       | 08/01/2024                 |
      | Foundry/Content Strategy involved? | Yes                        |
      | Opp Estimate                       | 10001                      |

  @CreateOpportunity-F360
    Scenario: Create F360 Opp and Validate
      Given User searches object name as "Pipeline" and record name as "AQUASANA 2024" and lands on record
      When User creates "F360" Opportunity and Validate
        | Element Name  | Values     |
        | Brand         | No Brand   |
        | Advertiser    | AQUASANA   |
        | Opp Estimate  | 10001      |
        | Close Date    | 06/30/2024 |
        | Execute Month | 06/30/2024 |

  @CreateOpportunity-DirectMedia
  Scenario: Create Print Opportunity and Validate
    Given User searches object name as "Pipeline" and record name as "KRAFT HEINZ COMPANY, THE 2024" and lands on record
    When User creates "Direct Media" Opportunity and Validate
      | Element Name                       | Values                   |
      | Brand                              | CLASSICO PRODUCTS        |
      | Advertiser                         | KRAFT HEINZ COMPANY, THE |
      | Title                              | ALLRECIPES               |
      | Issue                              | SPRING 2025 ALLRECIPES   |
      | Planning Agency                    | BPN                      |
      | Opp Estimate                       | 10001                    |
      | Foundry/Content Strategy involved? | Yes                      |
      | Sales Category                      | Auto                    |
      | Sub Category                        | Other - Auto            |

  @CreateOpportunity-Programmatic
  Scenario: Create Digital Opportunity and Validate
    Given User searches object name as "Pipeline" and record name as "AQUASANA 2024" and lands on record
    And User creates "Programmatic" Opportunity and Validate
      | Element Name                      | Values                          |
      | Brand                             | No Brand                        |
      | Advertiser                        | AQUASANA                        |
      | Opportunity Name                  | A_Test_Programmatic_{TimeStamp} |
      | Planning Agency                   | BPN                             |
      | Order Type                        | Fixed                           |
      | Campaign Start Date               | 05/01/2024                      |
      | Campaign End Date                 | 08/01/2024                      |
      | Advertiser Campaign Name          | Test data for Adv Campaign Name |
