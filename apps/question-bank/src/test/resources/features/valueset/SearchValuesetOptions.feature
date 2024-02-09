@valueset-options-search
Feature: Valueset options search

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"


  Scenario: I can search for valueset options
    Given I have a valueset named "<query>"
    When I search for valuesets with query "<query>"
    Then "<query>" is in the valueset search results
    And the valueset options are sorted by "<sort field>" "<sort direction>"

    Examples:
      | query       | sort field  | sort direction |
      | search test | name        | asc            |
      | search test | name        | desc           |
      | search test | type        | asc            |
      | search test | type        | desc           |
      | search test | description | asc            |
      | search test | description | desc           |
      | search test | code        | asc            |
      | search test | code        | desc           |