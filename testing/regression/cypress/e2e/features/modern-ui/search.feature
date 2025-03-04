Feature: Classic NBS - Modern Search - User can search and sort

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Search patient and sort
    Given I am on the modernized Patient Search page    
    Then I feel input id "name.first" with text "d"
    And Click on Search in Patient Search pane    
    Then Sort Search results by "Sort Patient ID"
    Then Sort Search results by "Sort Patient name"
    Then Sort Search results by "Sort DOB/Age"
    Then Sort Search results by "Sort Current sex"
    Then Sort Search results by "Sort Address"
    Then Sort Search results by "Sort Phone"
    Then Sort Search results by "Sort ID"
    Then Sort Search results by "Sort Email"

  Scenario: Search veify profile matches search
    Given I am on the modernized Patient Search page    
    Then I feel input id "name.last" with text "Ratkeyklkb"
    And Click on Search in Patient Search pane  
    Then I click first patient Search results to view profile