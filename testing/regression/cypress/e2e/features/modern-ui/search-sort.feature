# need more patients
@skip-broken
Feature: Classic NBS - Modern Search - User can search and sort for patients

  Background:
    Given I am logged in as secure user and stay on classic
    And I am on the modernized Patient Search page
    And I select input id "name.lastOperator" with type "Contains"
    And I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane

  Scenario: Sort by Patient ID - ascending and descending
    Then Verify top Search result by "78907"
    When Sort Search results by "Sort Patient ID"
    Then Verify top Search result is not "78907"

  Scenario: Sort by Patient Name - ascending and descending
    When Sort Search results by "Sort Patient name"
    Then Verify top Search result by "Zzzratkeyklkb"
    When Sort Search results by "Sort Patient name"
    Then Verify top Search result by "Aaaratkey"

  Scenario: Sort by Date of Birth - ascending and descending
    When Sort Search results by "Sort DOB/Age"
    Then Verify top Search result is not "05/16/1977"
    When Sort Search results by "Sort DOB/Age" 
    Then Verify top Search result by "05/16/1977"

  Scenario: Sort by Current Sex - ascending and descending
    When Sort Search results by "Sort Current sex"
    Then Verify top Search result is not "Male"
    When Sort Search results by "Sort Current sex"
    Then Verify top Search result by "Male"

  Scenario: Sort by Address - ascending and descending      
    When Sort Search results by "Sort Address"
    Then Verify top Search result by "Aaa Zzanda, unit 40606"
    When Sort Search results by "Sort Address"
    Then Verify top Search result by "998 Estrada Crest Apt. 193"

  Scenario: Sort by Phone - ascending and descending
    When Sort Search results by "Sort Phone"
    Then Verify top Search result by "732-207-5470"    
    When Sort Search results by "Sort Phone"
    Then Verify top Search result by "---"
    Then Verify top Search result is not "732-207-5470"

  Scenario: Sort by SSN ID - ascending and descending
    When Sort Search results by "Sort ID"
    Then Verify top Search result by "123-45-6789"
    When Sort Search results by "Sort ID"
    Then Verify top Search result is not "123-45-6789"

  Scenario: Sort by Email - ascending and descending
    When Sort Search results by "Sort Email"    
    Then Verify top Search result by "Aaaalice@gmail.com"
    When Sort Search results by "Sort Email"
    Then Verify top Search result is not "Aaaalice@gmail.com"
    Then Verify top Search result by "Ratkeyklkb77@gmail.com"
