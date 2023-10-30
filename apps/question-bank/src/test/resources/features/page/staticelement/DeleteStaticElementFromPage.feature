@delete_static_element
Feature: Delete Static Element 

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section name "sub-section" in the 1st section

    Scenario: I can delete a static element
        Given I am logged in And I can "LDFAdministration" any "System"
        And I send an add line separator request
        When I send a delete line separator request
        Then a line separator is deleted