Feature: I can search for concepts

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can search for concepts within a valueset
    Given I have a valueset named "test value set"
    And the value set has a concept named "first concept" with value "val1"
    And the value set has a concept named "second concept" with value "val2"
    When I search for concepts in "test value set" sorted by "<sort>" "<order>"
    Examples:
      | sort          | order      |
      | code          | ascending  |
      | code          | descending |
      | display       | ascending  |
      | display       | descending |
      | conceptCode   | ascending  |
      | conceptCode   | descending |
      | effectiveDate | ascending  |
      | effectiveDate | descending |