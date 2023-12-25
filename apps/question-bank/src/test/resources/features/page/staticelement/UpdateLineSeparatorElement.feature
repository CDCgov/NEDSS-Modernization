@update_line_separator
Feature: Update Line Separator Element

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can update the line separator element
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create an add line separator request with "admin comments"
        When I send an add line separator request
        And I update a line separator with "adminComments" of "new comments"
        Then I send an update line separator request
        And the line separator should have "adminComments" of "new comments"
