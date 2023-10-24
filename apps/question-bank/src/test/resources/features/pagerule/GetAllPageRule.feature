@get_all_page_rule
Feature: get all page rule

    Background:
        Given pages exist

    Scenario: I can get all page rules
        Given I am an admin user
        When I get all page rules
        Then page rules are returned

    Scenario: I cannot get page rules without logging in
        Given I am not logged in
        When I get all page rules
        Then A no credentials found exception is thrown

