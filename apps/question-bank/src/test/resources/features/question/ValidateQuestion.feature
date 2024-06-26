@validate_question
Feature: Validate question

    Scenario: I can use a valid unique field
        Given I am an admin user
        And No questions exist
        When I validate unique field "<unique-field>"
        Then return valid

        Examples:
                | unique-field       |
                | uniqueId           |
                | uniqueName         |
                | rdbColumnName      |
                | dataMartColumnName |


    Scenario: I cannot use invalid unique field
        Given I am an admin user
        And A text question exists
        When I validate unique field "<unique-field>"
        Then return not valid

        Examples:
                 | unique-field       |
                 | uniqueId           |
                 | uniqueName         |
                 | rdbColumnName      |
                 | dataMartColumnName |

    Scenario: I cannot validate non unique field
        Given I am an admin user
        And A text question exists
        When I validate unique field "non-unique"
        Then a validate unique question exception is thrown

    Scenario: I cannot validate rdbColumnName with invalid subgroup
        Given I am an admin user
        And A text question exists
        When I validate rdbColumnName with invalid subgroup
        Then a validate unique question exception is thrown

    Scenario: I cannot validate unique field without logging in
        Given I am not logged in
        When I validate unique field "uniqueId"
        Then a no credentials found exception is thrown

    Scenario: I cannot validate unique field without permissions
        Given I am a user without permissions
        When I validate unique field "uniqueId"
        Then an accessdenied exception is thrown





