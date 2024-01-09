@condition_search
Feature: I can search for conditions

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can search for conditions that are not in use
    Given a condition exists
    When i search for the condition "excluding" in use
    Then the condition is returned

  Scenario: I can search for conditions and includes ones that are in use
    Given a condition exists
    And I have a page named "a condition test"
    And the page is related to the condition
    When i search for the condition "including" in use
    Then the condition is returned

  @condition_search_sort
  Scenario: I can sort conditions when searching for them
    Given a condition exists with "<field>" set to "<value>"
    And a condition exists with "<field>" set to "<value2>"
    When i search a condition with sort "<field>" "<direction>"
    Then the conditions are returned sorted by "<field>" "<direction>"
    Examples:
      | field               | value        | value2        | direction  |
      | conditionShortNm    | AAASortTest  | ZZZSortTest   | ascending  |
      | conditionShortNm    | AAAASortTest | ZZZZSortTest  | descending |
      | id                  | AAASortTest  | ZZZSortTest   | ascending  |
      | id                  | AAAASortTest | ZZZZSortTest  | descending |
      | progAreaCd          | STD          | GCD           | ascending  |
      | progAreaCd          | STD          | GCD           | descending |
      | familyCd            | AAASortTest  | ZZZSortTest   | ascending  |
      | familyCd            | AAASortTest  | ZZZSortTest   | descending |
      | coinfection_grp_cd  | AAASortTest  | ZZZSortTest   | ascending  |
      | coinfection_grp_cd  | AAASortTest  | ZZZSortTest   | descending |
      | investigationFormCd | formSortTest | form2SortTest | ascending  |
      | investigationFormCd | formSortTest | form2SortTest | descending |
      | nndInd              | T            | F             | ascending  |
      | nndInd              | T            | F             | descending |
      | statusCd            | A            | I             | ascending  |
      | statusCd            | A            | I             | descending |
