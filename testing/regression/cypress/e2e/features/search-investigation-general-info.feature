# investigation search not modernized yet
@skip-broken
@skip-if-disabled-is-int
Feature: Investigation Search by general search

  Background:
    Given I am logged in as secure user
    Given I navigate the event investigation

  Scenario: Basic Info - Search by Condition
    When I select a condition for event investigation
    Then I should see Condition Results with the link "Hepatitis B virus infection, Chronic"

  Scenario: Basic Info - Search by Program Area
    When I select a program area for event investigation
    Then I should see Condition Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by Jurisdiction
    When I select a jurisdiction for event investigation
    Then I should see Results with the text "Cobb County"

  Scenario: Basic Info - Search by Pregnancy
    When I select a pregnancy for event investigation
    Then I should see Condition Results with the link "Hepatitis A, acute"

  Scenario: Basic Info - Search by Event edited by user
    When I select a user edited by for event investigation
    Then I should see Condition Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by Event created by user
    When I select a user created by for event investigation
    Then I should see Condition Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by Event date range
    When I select a date event range for event investigation
    Then I should see Condition Results with the link "Acute flaccid myelitis"

  Scenario: Basic Info - Search by Event id type
    When I select a event id type for event investigation
    Then I should see Condition Results with the link "Pertussis"

  Scenario: Basic Info - Search by Event Facility
    When I select a facility for event investigation
    Then I should see Results with the link "Samson, Sam"

  Scenario: Basic Info - Search by Event Provider
    When I select a provider for event investigation
    Then I should see Results with the text "John Xerogeanes"
