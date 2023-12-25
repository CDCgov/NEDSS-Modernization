@group_subsection
Feature: Group Subsection

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "whatever" in the 1st section
        And A text question exists
        And A date question exists

    Scenario: I can group a subsection of question elements
        Given I am an admin user
        And i add a list of questions to a subsection
        When I send a group subsection request
        Then the subsection is grouped


    Scenario: I cannot group a subsection contains static elements
        Given I am an admin user
        And i add a list of questions and a static element to a subsection
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
