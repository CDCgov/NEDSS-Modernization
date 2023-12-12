@users
Feature: User Options REST API

  Background:
    Given there is a user for "Tommy" "Oliver"
    And there is a user for "Tyler" "Durden"
    And there is a user for "Tywin" "Lanister"
    And there is a user for "Howard" "Moon"
    And there is a user for "Vince" "Nior"
    And there is a user for "Tommy" "Callahan III"
    And there is a user for "Tracy" "Turnblad"
    And there is a user for "Tommy" "Pickles"

  Scenario: I can find specific users
    When I am trying to find users that start with "tom"
    Then there are options available
    And the option named "Tommy Oliver" is included
    And the option named "Tommy Callahan III" is included
    And the option named "Tommy Pickles" is not included

  Scenario: I can find a specific number of users
    When I am trying to find at most 4 users that start with "t"
    Then there are 4 options included

  Scenario: I cannot find specific users that do not exist
    When I am trying to find users that start with "zzzzzzzzz"
    Then there aren't any options available
