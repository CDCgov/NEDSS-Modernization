@create_page_rule
Feature: Create page rule

    Background:
        Given pages exist

    Scenario: I cannot add a page rule without logging in
        Given I am not logged in
        When I make a request to add a rule to a page
        Then A no credentials found exception is thrown