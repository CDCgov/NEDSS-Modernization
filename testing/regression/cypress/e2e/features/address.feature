
Feature: Patient Search by Address

  Background:
    Given I am logged in as secure user
    Given I click address tab

  Scenario: Address - Search by City    
    When I search for "Atlanta" city
    Then I should see Results with the last name "Singh"

  Scenario: Address - Search by State
    Then I select "Georgia" state
    Then I should see Results with for text "GA"

  Scenario: Address - Search by Zip Code
    When I search for "30024" zip code
    Then I should see Results with the last name "Singh" 

  Scenario: Address - Search by Street address
    When I search for "123 Main St." Street address
    Then I should see Results with the last name "Singh"

  Scenario: CNFT1-1282 Address - Search with No Matching Address Records
    When I search for "$%(5" Street address
    Then I should see "No results found"

  Scenario: CNFT1-1283 Address – Search by Multiple Address Criteria
    When I search for Street Address "123 Main St." City "Atlanta" State "Georgia" Zip code "30024"
    Then I should see "Singh, Surma"
