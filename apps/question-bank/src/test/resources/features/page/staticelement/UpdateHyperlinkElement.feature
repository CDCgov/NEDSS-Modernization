@update_hyperlink
Feature: Update Hyperlink Element

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can update the hyperlink element
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create a hyperlink request with "some label" and "www.test.com"
        When I send a hyperlink request
        And I update a hyperlink with "label" of "new label"
        And I update a hyperlink with "link" of "www.new.com"
        Then I send an update hyperlink request
        And the hyperlink should have "label" of "new label"
        And the hyperlink should have "link" of "www.new.com"
