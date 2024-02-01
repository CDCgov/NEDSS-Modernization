@providers
Feature: User Options REST API

  Background:
    Given there is a provider for "Tommy" "Oliver"
    And there is a provider for "Tyler" "Durden"
    And there is a provider for "Tywin" "Lanister"
    And there is a provider for "Howard" "Moon"
    And there is a provider for "Vince" "Nior"
    And there is a provider for "Tommy" "Callahan III"
    And there is a provider for "Tracy" "Turnblad"
    And there is a provider for "Tommy" "Pickles"

  Scenario: I can find specific providers
    When I am trying to find providers that start with "tom"
    Then there are options available
    And the option named "Tommy Oliver" is included
    And the option named "Tommy Callahan III" is included
    And the option named "Tommy Pickles" is not included

  Scenario: I can find a specific number of providers
    When I am trying to find at most 4 providers that start with "t"
    Then there are 4 options included

  Scenario: I cannot find specific providers that do not exist
    When I am trying to find providers that start with "zzzzzzzzz"
    Then there aren't any options available
