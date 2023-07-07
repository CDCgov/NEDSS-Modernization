@condition_create
Feature: Create condition

    Background: condition setup
        Given: No conditions exist

    Scenario: I can create a condition
        Given I am an admin user
        When I send a create "<condition>" condition request
        Then the "<condition>" condition is created
        Examples:
            | condition |
            | text      |
    
    Scenario: I cannot create a condition which already exists
        Given I am an admin user
        When I send a create "<condition>" condition duplicate request
        Then a condition creation exception is thrown
        Examples:
            | condition |
            | text      |
