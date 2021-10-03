@ContractIO
Feature: Opportunity validation

  Background: 
    Given Admin has already logged into the application

  @ContractIO_Creation
  Scenario: ContractIO Creation
    When User creates new ContractIO for "Advertiser" account
      | Element Name                      | Values                      |
      |Title.SelectDropdown | ALLRECIPES|
      |Start Date.Date | Jan 1, 2021 |
      |End Date.Date  | Dec 31, 2021 |
      |Customer IO Number.TextBox | 00724 |
      |ContractIO.BillTo.Dropdown | Agency_Choice |
      |ContractIO.Agency| Test agency account |
      |ContractIO.Opportunity| 0 |
      |ContractIO.BillToAddress| 2 |
      
      
      
      
     
      
      
      
    
      
  