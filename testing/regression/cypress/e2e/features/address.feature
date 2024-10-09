
Feature: Patient Search by Address

  Background:
    Given I am logged in as secure user
    Given I click address tab

  Scenario: Address - Search by City    
    When I search for "Cullen" city
    Then I should see Results with the last name "Ratkeyklkb"

  Scenario: Address - Search by State
    Then I select "Kentucky" state
    Then I should see Results with for text "KY"

  Scenario: Address - Search by Zip Code
    When I search for "42437" zip code
    Then I should see Results with the last name "Ratkeyklkb" 

  Scenario: Address - Search by Street address
    When I search for "90 SE Panda" Street address
    Then I should see Results with the last name "Ratkeyklkb"

  Scenario: CNFT1-1282 Address - Search with No Matching Address Records
    When I search for "$%(5" Street address
    Then I should see "No result found"

  Scenario: CNFT1-1283 Address – Search by Multiple Address Criteria
    When I search for Street Address "90 SE Panda" City "Cullen" State "Kentucky" Zip code "42437"
    Then I should see "Ratkeyklkb, Caden Benjamin, Esquire"
