@create_read_only_participants_list
Feature: Create Read Only Participants List

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section
    
    Scenario: I can create a read only participants list static element
        Given I am logged in
        And I can "LDFAdministration" any "System"
        And I create a read only participants list request with "admin comments"
        When I send a read only participants list request
        Then a read only participants list element is created
