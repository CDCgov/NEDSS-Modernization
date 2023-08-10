Feature: Save Page as Draft

    Scenario: Page does not exist cannot save as draft
        Given I am an admin user
        When I save as draft a page that does not exist
        Then A page update exception should be thrown

    Scenario: Page exists can save as draft
        Given I am an admin user
        And pages exist
        When I save a page as draft
        Then A page state should change