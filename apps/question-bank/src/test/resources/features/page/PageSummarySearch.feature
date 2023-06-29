@page_summary_search
Feature: Search for page summary

    Background: Pages exist
        Given pages exist

    Scenario: I can find all page summaries
        Given I am an admin user
        When I get all page summaries
        Then page summaries are returned

    Scenario: I can sort page summaries
        Given I am an admin user
        When I get all page summaries and sort by "<sort-field>" "<direction>"
        Then page summaries are returned sorted by "<sort-field>" "<direction>"

        Examples:
            | sort-field | direction |
            | id         | ASC       |
            | id         | DESC      |
            | name       | ASC       |
            | name       | DESC      |
            | eventType  | ASC       |
            | eventType  | DESC      |
            | status     | ASC       |
            | status     | DESC      |
            | lastUpdate | ASC       |
            | lastUpdate | DESC      |


    Scenario: I cannot get all page summaries without logging in
        Given I am not logged in
        When I get all page summaries
        Then a no credentials found exception is thrown

    Scenario: I cannot get all page summaries without proper permission
        Given I am a user without permissions
        When I get all page summaries
        Then an accessdenied exception is thrown
