@update_page_rule
Feature: Update page rule

    Background:
        Given pages exist

    Scenario: I can update a page rule
        Given I am an admin user
        When I make a request to update a rule to a page
        Then A rule is updated

    Scenario: I cannot update a page rule
        Given I am not logged in
        When I make a request to update a rule to a page
        Then A no credentials found exception is thrown