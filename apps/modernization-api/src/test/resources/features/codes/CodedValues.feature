@coded-values
Feature: Value Sets for common data inputs are retrievable

  Background:
    Given A user exists

  Scenario: I can retrieve Marital Statuses
    When I want to retrieve the "Marital Status" value set
    Then a value set is returned

  Scenario: I can retrieve Education Levels
    When I want to retrieve the "Highest Level of Education" value set
    Then a value set is returned

  Scenario: I can retrieve Race Categories
    When I want to retrieve the "Race (Category)" value set
    Then a value set is returned

  Scenario: I can retrieve Ethnic Groups
    When I want to retrieve the "Ethnicity" value set
    Then a value set is returned

  Scenario: I can retrieve Spanish Origin (detailed ethnicities)
    When I want to retrieve the "Spanish Origin (detailed ethnicity)" value set
    Then a value set is returned

  Scenario: I can retrieve Ethnicity Unknown Reasons
    When I want to retrieve the "Ethnicity Reason Unknown" value set
    Then a value set is returned

  Scenario: I can retrieve Unknown Gender Reasons
    When I want to retrieve the "Gender Reason Unknown" value set
    Then a value set is returned

  Scenario: I can retrieve Transgender (preferred gender)
    When I want to retrieve the "Transgender (preferred gender)" value set
    Then a value set is returned

  Scenario: I can retrieve Primary Occupations
    When I want to retrieve the "Primary Occupation" value set
    Then a value set is returned

  Scenario: I can retrieve Primary Languages
    When I want to retrieve the "Primary Language" value set
    Then a value set is returned

  Scenario: I can retrieve Detailed Races by ROOT
    When I want to retrieve the "Detailed Race" value set by "ROOT"
    Then a grouped value set is returned

  Scenario: I can retrieve Countries
    When I want to retrieve the "Country" value set
    Then a value set is returned

  Scenario: I can retrieve States
    When I want to retrieve the "State" value set
    Then a value set is returned

  Scenario: I can retrieve Counties by ROOT
    When I want to retrieve the "County" value set by "ROOT"
    Then a grouped value set is returned

  Scenario: I can retrieve Genders
    When I want to retrieve the "Gender" value set
    Then a value set is returned
