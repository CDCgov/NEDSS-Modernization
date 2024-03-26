@source_question_finder
Feature: Source Question Finder 

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And the page has a question named "testquestion" in the 1st sub-section

    Scenario: I can find coded source questions with enable
      Given I create a source question request with "Enable"
      When I send a source question request
      Then Source questions are returned

    Scenario: I can find coded source questions with disable
      Given I create a source question request with "Enable"
      When I send a source question request
      Then Source questions are returned
    
    Scenario: I can find date source questions with date compare
      Given the page has a date question named "datetest" in the 1st sub-section
      And I create a source question request with "Date"
      When I send a source question request
      Then Source questions are returned