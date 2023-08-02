@condition_create
Feature: Create condition

    Scenario: ConditionCd already exists
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
