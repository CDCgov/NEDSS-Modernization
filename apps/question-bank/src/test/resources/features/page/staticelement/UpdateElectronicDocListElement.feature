@update_electronic_list
Feature: Update Electronic Doc List Element

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can update the electronic doc list element
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create an original electronic document list with "admin comments"
        When I send a original electronic document list request
        And I update a electronic doc list with "adminComments" of "new comments"
        Then I send an update electronic doc list request
        And the electronic doc list should have "adminComments" of "new comments"
