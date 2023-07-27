@add_tab_steps
Feature: Create Tab Service

  Scenario: Create a new tab successfully
     Given a tab creation request:
       | Page ID | Name      | Visible |
       | 1000376 | Local Tab | T       |
    Then the service should create the tab successfully

  Scenario: Attempt to create a new tab with hidden functionality
    Given a tab creation request:
      | Page ID | Name      | Visible |
      | 1000376 | Local Tab | F       |
    Then the service should create the tab successfully

  Scenario: Attempt to create a new tab with missing data
   Given a tab creation request:
     | Page ID | Name | Visible |
     | 1000376 |      | T       |
    Then the service should throw an AddTabException
