@create_page_rule
Feature: Create page rule

    Background:
        Given pages exist

    Scenario: I can create a page rule
        Given I am an admin user
        When I make a request to add a rule to a page
        Then A rule is created

    Scenario: I cannot add a page rule without logging in
        Given I am not logged in
        When I make a request to add a rule to a page
        Then A no credentials found exception is thrown

