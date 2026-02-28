
Feature: Patient Search by Race / Ethnicity

  Background:
    Given I am logged in as secure user

  Scenario: Race / Ethnicity - Search by no data
    When I navigate to race section
    Then I search by ethnicity as "unknown" and race "Asian"

  Scenario: Race / Ethnicity - Search by - Hispanic or Latino and no data in race
    When I navigate to race section
    Then I search by ethnicity as "Hispanic or Latino" and race not selected

  Scenario: Race / Ethnicity - Search race by - Asian
    When I navigate to race section
    Then I search by ethnicity not selected and race "Asian"

  Scenario: Race / Ethnicity - Search by Not Hispanic or Latino Ethnicity
    When I navigate to race section
    Then I search by ethnicity as "Not Hispanic or Latino" and race not selected

  Scenario: Race / Ethnicity - Search by Unknown Ethnicity
    When I navigate to race section
    Then I search by ethnicity as "unknown" and race not selected

  Scenario: Race / Ethnicity - Search by American Indian or Alaska Native Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "American Indian or Alaska Native"

  Scenario: Race / Ethnicity - Search by Black or African American
    When I navigate to race section
    Then I search by ethnicity not selected and race "Black or African American"

  Scenario: Race / Ethnicity - Search by Native Hawaiian or Pacific Islander Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "Native Hawaiian or Other Pacific Islander"

  Scenario: Race / Ethnicity - Search by White Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "White"

  Scenario: Race / Ethnicity – Search by Not Asked Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "not asked"

  Scenario: Race / Ethnicity – Search by Other Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "Other"

  Scenario: Race / Ethnicity – Search by American Refused to Answer Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "Refused to answer"

  Scenario: Race / Ethnicity – Search by Unknown Race
    When I navigate to race section
    Then I search by ethnicity not selected and race "Unknown"

  Scenario: Race / Ethnicity – Search by combining Ethnicity Race
    When I navigate to race section
    Then I search by ethnicity as "Hispanic or Latino" and race "Black or African American"

  

  
