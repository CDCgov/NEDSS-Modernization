Feature: Read Condition

  Scenario: Get all conditions as an admin
    Given I am an admin user
    When I request to retrieve all conditions
    Then Conditions successfully return


  Scenario: Search for a condition that exists as an admin
    Given I am an admin user
    When I search for a condition that exists
    Then A condition should be returned


  Scenario: Search for a condition that does not exist
    Given I am an admin user
    When I search for a condition that does not exist
    Then A condition should not be returned