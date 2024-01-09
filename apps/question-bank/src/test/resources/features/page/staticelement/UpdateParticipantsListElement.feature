@update_participants_list
Feature: Update Participants List

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can update a participants list element
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create a read only participants list request with "admin comments"
        When I send a read only participants list request
        And I update a participants list with "adminComments" of "new comments"
        Then I send an update participants list request
        And the participants list should have "adminComments" of "new comments"
