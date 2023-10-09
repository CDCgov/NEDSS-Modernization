@delete_page_rule
Feature: Delete page rule

    Background:
        Given pages exist

    Scenario: I cannot delete a page rule
        Given I am not logged in
        When I make a request to delete a rule to a page
        Then A no credentials found exception is thrown