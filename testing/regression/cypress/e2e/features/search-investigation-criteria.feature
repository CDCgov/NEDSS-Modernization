@skip-if-disabled-is-int
Feature: Investigation Search by criteria

  Background:
    Given I am logged in as secure user
    Given I navigate the event investigation
    Given I click criteria tab

  Scenario: Basic Info - Search by Condition
    When I select an investigation status for event investigation
    Then I should see Results with the text "closed"
    Then I should see "closed"

  Scenario: Basic Info - Search by Outbreak name
    When I select outbreak name for event investigation
    Then I should see Condition Results with the link "Hepatitis C, acute"

  Scenario: Basic Info - Search by case study name
    When I select case study for event investigation
    Then I should see Condition Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by current processing status
    When I select investigation current processing status for event investigation
    Then I should see Condition Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by notification status
    When I select notification status for event investigation
    Then I should see no result found text