@search_page_rule
Feature: search page rule

    Background:
        Given pages exist

    Scenario: I can search for page rule
        Given I am an admin user
        When I search for page rule
        Then page rules are returned

