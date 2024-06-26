@create_line_separator
Feature: Create Line Separator

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section
    
    Scenario: I can create a line separator static element
        Given I am logged in 
        And I can "LDFAdministration" any "System" 
        And I create an add line separator request with "<adminComments>"
        When I send an add line separator request
        Then a line separator is created

    Examples:
            | adminComments      |
            | some comments      |
            | test comments      |
