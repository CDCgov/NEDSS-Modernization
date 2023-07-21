@condition_create
Feature: Create condition
    When attempting to create a condition

    Scenario: ConditionCd already exists
        Given ConditionCd already exists
        When Create condition
        Then A condition creation exception is thrown

    Scenario: Condition name already exists
        Given A condition name already exists
        When Create condition
        Then a condition creation exception is thrown

        Scenario: I am admin and condition does not exist
            Given I am an admin and a condition does not exist
            When Create condition
            Then The Condition is created
