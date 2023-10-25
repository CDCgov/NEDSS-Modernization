@page_summary_search
Feature: Search for page summary

    Background: Pages exist
        Given pages exist

    Scenario: I can find all page summaries
        Given I am an admin user
        When I get all page summaries
        Then page summaries are returned

    Scenario Outline: I can sort page summaries
        Given I am an admin user
        When I get all page summaries and sort by "<sort-field>" "<direction>"
        Then page summaries are returned sorted by "<sort-field>" "<direction>"

        Examples:
            | sort-field   | direction |
            | id           | ASC       |
            | id           | DESC      |
            | name         | ASC       |
            | name         | DESC      |
            | eventType    | ASC       |
            | eventType    | DESC      |
            | status       | ASC       |
            | status       | DESC      |
            | lastUpdate   | ASC       |
            | lastUpdate   | DESC      |
            | lastUpdateBy | ASC       |
            | lastUpdateBy | DESC      |

    Scenario: An exception is thrown when trying to sort by an unsupported field
        Given I am an admin user
        When I get all page summaries and sort by "bad field" "ASC"
        Then a query exception is thrown

    Scenario: I cannot get all page summaries without logging in
        Given I am not logged in
        When I get all page summaries
        Then a no credentials found exception is thrown

    Scenario: I cannot get all page summaries without proper permission
        Given I am a user without permissions
        When I get all page summaries
        Then an accessdenied exception is thrown

    Scenario Outline: I can search for page summaries
        Given I am an admin user
        When I search for summaries by "<search text>"
        Then page summaries are returned that match "<search text>"

        Examples:
            | search text |
            | brucellosis |
            | meningitis  |

    Scenario Outline: I can sort page summaries when searching
        Given I am an admin user
        When I search for summaries by "" and sort by "<sort-field>" "<direction>"
        Then page summaries are returned sorted by "<sort-field>" "<direction>"

        Examples:
            | sort-field   | direction |
            | id           | ASC       |
            | id           | DESC      |
            | name         | ASC       |
            | name         | DESC      |
            | eventType    | ASC       |
            | eventType    | DESC      |
            | status       | ASC       |
            | status       | DESC      |
            | lastUpdate   | ASC       |
            | lastUpdate   | DESC      |
            | lastUpdateBy | ASC       |
            | lastUpdateBy | DESC      |

    Scenario: I cannot search page summaries without logging in
        Given I am not logged in
        When I search for summaries by "test"
        Then a no credentials found exception is thrown

    Scenario: I cannot get search page summaries without proper permission
        Given I am a user without permissions
        When I search for summaries by "test"
        Then an accessdenied exception is thrown

