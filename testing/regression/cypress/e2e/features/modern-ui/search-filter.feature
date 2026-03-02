Feature: Classic NBS - Modern Search - User can search and filter for patients

  Background:
    Given I am logged in as secure user and stay on classic
    Given I am on the modernized Patient Search page
    Given I fill input id "name.last" with text "sin"

  Scenario: Search by patient contains filter with Patient ID    
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-id" with "630"
    Then I verify unique search row contains "63000"

  Scenario: Search patient by exact filter with Patient ID    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-id" with "63000"
    Then I verify unique search row contains "63000"

  Scenario: Search patient by exact Patient Name    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-name" with "Singh, Surma"
    Then I verify unique search row contains "Singh, Surma"

  Scenario: Search patient by partial Patient Name    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-name" with "Singh"
    Then I verify unique search row contains "Singh, Surma"

  Scenario: Search patient by exact Patient exact Last Name    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-name" with "Singh"
    Then I verify unique search row contains "Singh, Surma"

  Scenario: Search patient by exact Patient DOB    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-ageOrDateOfBirth" with "01/01/1990"
    Then I verify unique search row contains "01/01/1990"

  Scenario: Search patient by partial Patient DOB, year only    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-ageOrDateOfBirth" with "1990"
    Then I verify unique search row contains "01/01/1990"

  Scenario: Search patient by exact Gender    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-sex" with "M"
    Then I verify unique search row contains "Male"

  Scenario: Search patient by exact Patient Address    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-address" with "123 Main St."
    Then I verify unique search row contains "Home"
    Then I verify unique search row contains "123 Main St."
    Then I verify unique search row contains "Atlanta, GA 30024"

  Scenario: Search patient by partial Patient Address    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-address" with "123 Main"
    Then I verify unique search row contains "Home"
    Then I verify unique search row contains "123 Main St."
    Then I verify unique search row contains "Atlanta, GA 30024"

  Scenario: Search patient by exact Patient Phone    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-phone" with "232-322-2222"
    Then I verify all search rows contains "232-322-2222"

  Scenario: Search patient by partial Patient Phone    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-phone" with "-2222"
    Then I verify all search rows contains "232-322-2222"    

  Scenario: Search patient by exact ID    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-identification" with "3453453533"
    Then I verify unique search row contains "3453453533"

  Scenario: Search patient by partial ID    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-identification" with "3533"
    Then I verify unique search row contains "3453453533"    

  Scenario: Search patient by exact Email    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-email" with "fdsfs@dsds.com"
    Then I verify unique search row contains "fdsfs@dsds.com"

  Scenario: Search patient by partial Email    
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-email" with "fdsfs@dsds"
    Then I verify unique search row contains "fdsfs@dsds.com"
