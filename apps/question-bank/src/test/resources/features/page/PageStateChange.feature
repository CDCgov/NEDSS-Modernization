@page
Feature: Save Page as Draft

    Background:
        Given pages exist

    Scenario: Page does not exist cannot save as draft
        Given I am an admin user
        When I save as draft a page that does not exist
        Then A page update exception should be thrown

    Scenario: Page exists can save as draft
        Given I am an admin user
        When I save a page as draft
        Then A page state should change

    Scenario: I cannot save a page as draft without being logged in
        Given I am not logged in
        When I save a page as draft
        Then a no credentials found exception is thrown

    Scenario: I cannot save a page as draft without proper permissions
        Given I am a user without permissions
        When I save a page as draft
        Then an accessdenied exception is thrown
