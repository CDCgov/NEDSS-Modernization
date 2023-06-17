@coded-values
Feature: Value Sets for common data inputs are retrievable

  Background:
    Given A user exists

  Scenario Outline: I can retrieve a value set for <name>
    When I want to retrieve the <name> value set
    Then a value set is returned

    Examples:
      | name                                  |
      | "Marital Status"                      |
      | "Highest Level of Education"          |
      | "Race (Category)"                     |
      | "Ethnicity"                           |
      | "Spanish Origin (detailed ethnicity)" |
      | "Ethnicity Reason Unknown"            |
      | "Gender Reason Unknown"               |
      | "Transgender (preferred gender)"      |
      | "Primary Occupation"                  |
      | "Name Type"                           |
      | "Name Prefix"                         |
      | "Name Suffix"                         |
      | "Degree"                              |
      | "Primary Language"                    |
      | "Gender"                              |
      | "Address Type"                        |
      | "Address Use"                         |
      | "Country"                             |
      | "State"                               |


  Scenario Outline: I can retrieve Detailed Races by <parent>
    When I want to retrieve the <name> value set by <parent>
    Then a grouped value set is returned

    Examples:
      | name            | parent |
      | "Detailed Race" | "ROOT" |
      | "County"        | "ROOT" |
