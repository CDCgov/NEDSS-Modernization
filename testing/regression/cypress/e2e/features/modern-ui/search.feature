Feature: Classic NBS - Modern Search - User can search and sort for patients

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Search patient and sort by id, name, dob, sex, address, phone, id, and email
    Given I am on the modernized Patient Search page    
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then Verify top Search result by "80871"
    Then Sort Search results by "Sort Patient ID"
    Then Verify top Search result is not "80871"
    Then Verify top Search result is not "Abbottnsyci"
    Then Sort Search results by "Sort Patient name"
    Then Verify top Search result by "Ratkezmefu"
    Then Verify top Search result is not "05/16/1961"
    Then Sort Search results by "Sort DOB/Age"
    Then Verify top Search result by "05/16/1961"    
    Then Sort Search results by "Sort Current sex"
    Then Verify top Search result is not "Male"
    Then Sort Search results by "Sort Current sex"
    Then Verify top Search result by "Male"
    Then Sort Search results by "Sort Address"    
    Then Verify top Search result is not "90 SE Panda, unit 40606"
    Then Sort Search results by "Sort Address"
    Then Verify top Search result by "90 SE Panda, unit 40606"
    Then Sort Search results by "Sort Phone"
    Then Verify top Search result by "732-721-2970"
    Then Sort Search results by "Sort Phone"
    Then Verify top Search result is not "732-721-2970"
    Then Sort Search results by "Sort ID"
    Then Verify top Search result by "986-37-4923"
    Then Sort Search results by "Sort ID"
    Then Verify top Search result is not "986-37-4923"
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