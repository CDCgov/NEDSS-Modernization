@create_read_only_comments
Feature: Create Read Only Comments

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can create a read only comments static element
        Given I am logged in
        And I can "LDFAdministration" any "System"
        And I create a read only comments element request with "<comments>"
        When I send a read only comments element request
        Then a read only comments element is created
    
        Examples:
            | comments |
            | hello comments test |
            | creating static element |
        
