@ContractIO
Feature: Opportunity validation

  Background: 
    Given Admin has already logged into the application

  @ContractIO_Creation
  Scenario: ContractIO Creation
   Given User creates new account for "Advertiser" Record type
      | Element Name                      | Values                      |
      | NewAccount.AccountName            | Test_Advertiser_{TimeStamp} |
      | NewAccount.BillingCity.TextBox    | Burlington                  |
      | NewAccount.BillingZip.TextBox     |                       27215 |
      | NewAccount.Type.Dropdown          | Advertiser                  |
      | NewAccount.BillingState.Dropdown  | North Carolina              |
      | NewAccount.CreditStatus.Dropdown  | Cash with Order             |
      | NewAccount.BillingStreet.TextArea | 786 Boone Station Drive     |
    And "Surfina Adams" approves the account
    And user creates a Pipeline
      | Element Name                  | Values |
      | NewPipelinePopup.Year.TextBox |   2021 |
    And User creates new Opportunity for "Print" type
      | Element Name                     				| Values                      |
      | Select an Advertiser.SingleInputDropdown   	    | Test_Advertiser_Sep28_0838  |
      | Stage.SelectDropdown   	  						| 10% - Proposal Submitted                    |
      |Available Titles.DuellistBox 					| ALLRECIPES|
      |Opp Estimate for ALLRECIPES.TextBox				|1000|
      |Available Issues.DuellistBox						| DECEMBER/JANUARY 2022 ALLRECIPES|
    When User creates new ContractIO for "Advertiser" account
      | Element Name                | Values            |
      |Title.SelectDropdown 		| ALLRECIPES		|
      |Start Date.Date				| Jan 1, 2021 		|
      |End Date.Date  				| Dec 31, 2021 		|
      |Customer IO Number.TextBox 	| 00724 			|
      |ContractIO.BillTo.Dropdown 	| Agency_Choice 	|
      |ContractIO.Agency			| Test agency account |
      |ContractIO.Opportunity		| 0 				|
      |ContractIO.BillToAddress		| 2 				|
      
      
      
      
     
      
      
      
    
      
  