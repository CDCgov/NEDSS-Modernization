Feature: Classic NBS - Modern Search - User can search and verify patient data matches profile

  Background:
    Given I am logged in as secure user and stay on classic
    And I am on the modernized Patient Search page
    And I select input id "name.lastOperator" with type "Contains"
    And I fill input id "name.last" with text "sin"
    
  # comparison logic has bad selectors
  @skip-broken
  Scenario: Search verify profile matches search
    And Click on Search in Patient Search pane
    Then I click first patient Search results to view profile          
