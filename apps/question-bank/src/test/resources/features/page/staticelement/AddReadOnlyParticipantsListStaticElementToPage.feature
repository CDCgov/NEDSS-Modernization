@create_read_only_participants_list
Feature: Create Read Only Participants List

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section
    
    Scenario: I can create a read only participants list static element
        Given I am an admin user
        When I send a read only participants list request
        Then a read only participants list element is created

    Scenario: I cannot create a read only participants list static element without logging in
        Given I am not logged in
        When I send a read only participants list request
        Then a no credentials found exception is thrown

    Scenario: I cannot create a read only participants list static element without permissions
        Given I am a user without permissions
        When I send a read only participants list request
        Then an accessdenied exception is thrown