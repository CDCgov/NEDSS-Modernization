
Feature: Patient Search by Race / Ethnicity

  Background:
    Given I am logged in as "superuser" and password ""

  Scenario: Race / Ethnicity - Search by no data
    When I navigate to race section
    Then I search by ethnicity as "unknown" and race "Asian"

  Scenario: Race / Ethnicity - Search by - Hispanic or Latino and no data in race
    When I navigate to race section
    Then I search by ethnicity as "Hispanic or Latino" and race not select

  Scenario: Race / Ethnicity - Search race by - Asian
    When I navigate to race section
    Then I search by ethnicity not select and race "Asian"
