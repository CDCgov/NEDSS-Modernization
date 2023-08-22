Feature: Create condition

    Scenario: Condition Code already exists
        Given ConditionCd already exists
        When I send a create condition request
        Then A condition creation exception is thrown

    Scenario: Condition name already exists
        Given A condition name already exists
        When I send a create condition request
        Then A condition creation exception is thrown

    Scenario: I can create a condition
        Given I am an admin user and a condition does not exist
        When I send a create condition request
        Then the condition is created

    Scenario: I cannot create a condition without logging in
        Given I am not logged in
        When I send a create condition request
        Then a no credentials found exception is thrown

    Scenario: I cannot create a condition without proper permission
        Given I am a user without permissions
        When I send a create condition request
        Then an accessdenied exception is thrown
