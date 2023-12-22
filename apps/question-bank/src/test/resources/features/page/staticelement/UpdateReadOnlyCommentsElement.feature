@update_read_only_comments
Feature: Update Read Only Comments Element

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can update a read only comments element
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create a read only comments element request with "old comments"
        When I send a read only comments element request
        And I update a read only comments with "commentsText" of "new comments"
        Then I send an update read only comments request
        And the read only comments should have "commentsText" of "new comments"