@group_subsection
Feature: Group Subsection

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section in the 1st section

    Scenario: I can group a subsection of question elements
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And the page has a question named "firstquestion" in the 1st sub-section
        When I send a group subsection request
        Then the subsection is grouped


    Scenario: I cannot group a subsection contains static elements
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And the page has a question named "firstquestion" in the "whatever" sub-section
        And I create an add line separator request with "admin comments"
        When I send a group subsection request
        Then An Update SubSection Exception is thrown


    Scenario: I cannot group a subsection without logging in
        Given I am not logged in
        When I send a group subsection request
        Then a no credentials found exception is thrown

    Scenario: I cannot group a section without permissions
        Given I am a user without permissions
        When I send a group subsection request
        Then an accessdenied exception is thrown
