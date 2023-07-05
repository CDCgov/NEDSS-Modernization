@concept_search
Feature: I can search for concepts associated with the given value set

    Scenario: I can list all concepts for a value set
        Given I am an admin user
        When I search for concepts for value set "<valueSet>"
        Then I find concepts for value set "<valueSet>"

        Examples:
            | valueSet            |
            | CODE_SYSTEM         |
            | 900_RESULT_PROVIDED |

    Scenario: I cannot search for concepts without logging in
        Given I am not logged in
        When I search for concepts for value set "CODE_SYSTEM"
        Then a no credentials found exception is thrown

    Scenario: I cannot search for concepts without proper permission
        Given I am a user without permissions
        When I search for concepts for value set "CODE_SYSTEM"
        Then an accessdenied exception is thrown
