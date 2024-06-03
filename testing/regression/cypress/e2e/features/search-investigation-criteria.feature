Feature: Investigation Search by criteria

  Background:
    Given I am logged in as "superuser" and password ""
    Given I navigate the event investigation
    Given I click criteria tab

  Scenario: Basic Info - Search by Condition
    When I select a investigation status for event investigation
    Then I should see Results with the text "CLOSED"

  Scenario: Basic Info - Search by Outbreak name
    When I select outbreak name for event investigation
    Then I should see Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by case study name
    When I select case study for event investigation
    Then I should see Results with the link "2019 Novel Coronavirus"

  Scenario: Basic Info - Search by current processing status
    When I select investigation current processing status for event investigation
    Then I should see Results with the link "AIDS"

  Scenario: Basic Info - Search by notification status
    When I select notification status status for event investigation
    Then I should see No Results found text