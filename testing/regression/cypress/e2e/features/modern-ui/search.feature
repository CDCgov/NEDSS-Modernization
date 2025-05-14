Feature: Classic NBS - Modern Search - User can search and sort for patients

  Background:
    Given I am logged in as secure user and stay on classic
    Given I am on the modernized Patient Search page
    Given I select input id "name.lastOperator" with type "Contains"
    Given I feel input id "name.last" with text "rat"
    
  Scenario: Search patient and sort by id, name, dob, sex, address, phone, id, and email    
    And Click on Search in Patient Search pane
    Then Verify top Search result by "78907"
    Then Sort Search results by "Sort Patient ID"
    Then Verify top Search result is not "78907"
    Then Verify top Search result by "Zzzratkeyklkb"
    Then Sort Search results by "Sort Patient name"
    Then Sort Search results by "Sort Patient name"
    Then Verify top Search result by "Aaaratkey"
    Then Verify top Search result is not "05/16/1977"
    Then Sort Search results by "Sort DOB/Age"
    Then Sort Search results by "Sort DOB/Age"
    Then Verify top Search result by "05/16/1977"    
    Then Sort Search results by "Sort Current sex"
    Then Verify top Search result is not "Male"
    Then Sort Search results by "Sort Current sex"
    Then Verify top Search result by "Male"
    Then Sort Search results by "Sort Address"
    Then Sort Search results by "Sort Address"
    Then Verify top Search result is not "Aaa Zzanda, unit 40606"
    Then Sort Search results by "Sort Address"
    Then Verify top Search result by "Aaa Zzanda, unit 40606"
    Then Sort Search results by "Sort Phone"
    Then Verify top Search result by "732-721-2970"
    Then Sort Search results by "Sort Phone"
    Then Verify top Search result is not "732-721-2970"
    Then Sort Search results by "Sort ID"
    Then Verify top Search result by "986-37-4923"
    Then Sort Search results by "Sort ID"
    Then Verify top Search result is not "986-37-4923"
    Then Sort Search results by "Sort Email"
    Then Sort Search results by "Sort Email"
    Then Verify top Search result by "LisaGuerra46@hotmail.com"
    Then Sort Search results by "Sort Email"
    Then Verify top Search result is not "LisaGuerra46@hotmail.com"
    Then Verify top Search result by "CadenRatkeyklkb79@hotmail.com"

  Scenario: Search verify profile matches search
    Given I am on the modernized Patient Search page
    Then I feel input id "name.last" with text "Ratkeyklkb"
    And Click on Search in Patient Search pane
    Then I click first patient Search results to view profile          