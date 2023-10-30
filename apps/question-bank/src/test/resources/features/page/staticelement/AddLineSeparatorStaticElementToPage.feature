@create_line_separator
Feature: Create Line Separator

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section
    
    Scenario: I can create a line separator static element
        Given I am logged in And I can "LDFAdministration" any "System"
        When I send an add line separator request
        Then a line separator is created

    Scenario: I cannot create a line separator static element without logging in
        Given I am not logged in
        When I send an add line separator request
        Then a no credentials found exception is thrown

    Scenario: I cannot create a line separator static element without having admin permissions
        Given I am logged in
        When I send an add line separator request
        Then an accessdenied exception is thrown
