@codedvalues @options
Feature: List Coded Values REST API

  Scenario: I can fetch all the addressTypes sorted with REST
    When I am retrieving all the "addressTypes"
    Then there are options available
    And the option named "House" is included

  Scenario: I can fetch all the addressUses sorted with REST
    When I am retrieving all the "addressUses"
    Then there are options available
    And the option named "Birth Place" is included

  Scenario: I can fetch all the assigningAuthorities sorted with REST
    When I am retrieving all the "assigningAuthorities"
    Then there are options available
    And the option named "MT" is included

  Scenario: I can fetch all the degrees sorted with REST
    When I am retrieving all the "degrees"
    Then there are options available
    And the option named "LPN" is included

  Scenario: I can fetch all the detailedEthnicities sorted with REST
    When I am retrieving all the "detailedEthnicities"
    Then there are options available
    And the option named "Latin American" is included

  Scenario: I can fetch all the educationLevels sorted with REST
    When I am retrieving all the "educationLevels"
    Then there are options available
    And the option named "Doctoral Degree" is included

  Scenario: I can fetch all the ethnicGroups sorted with REST
    When I am retrieving all the "ethnicGroups"
    Then there are options available
    And the option named "Not Hispanic or Latino" is included

  Scenario: I can fetch all the ethnicityUnknownReasons sorted with REST
    When I am retrieving all the "ethnicityUnknownReasons"
    Then there are options available
    And the option named "Refused to answer" is included

  Scenario: I can fetch all the genderUnknownReasons sorted with REST
    When I am retrieving all the "genderUnknownReasons"
    Then there are options available
    And the option named "Refused" is included

  Scenario: I can fetch all the maritalStatuses sorted with REST
    When I am retrieving all the "maritalStatuses"
    Then there are options available
    And the option named "Married" is included

  Scenario: I can fetch all the identificationTypes sorted with REST
    When I am retrieving all the "identificationTypes"
    Then there are options available
    And the option named "Medicare number" is included

  Scenario: I can fetch all the nameTypes sorted with REST
    When I am retrieving all the "nameTypes"
    Then there are options available
    And the option named "Legal" is included

  Scenario: I can fetch all the phoneTypes sorted with REST
    When I am retrieving all the "phoneTypes"
    Then there are options available
    And the option named "Email Address" is included

  Scenario: I can fetch all the phoneUses sorted with REST
    When I am retrieving all the "phoneUses"
    Then there are options available
    And the option named "Home" is included

  Scenario: I can fetch all the prefixes sorted with REST
    When I am retrieving all the "prefixes"
    Then there are options available
    And the option named "Mr." is included

  Scenario: I can fetch all the preferredGenders sorted with REST
    When I am retrieving all the "preferredGenders"
    Then there are options available
    And the option named "Male" is included

  Scenario: I can fetch all the raceCategories sorted with REST
    When I am retrieving all the "raceCategories"
    Then there are options available
    And the option named "Multi-Race" is included

  Scenario: I can fetch all the suffixes sorted with REST
    When I am retrieving all the "suffixes"
    Then there are options available
    And the option named "Jr." is included
    And there are 7 options included
    And the 1st option is "Esquire"
    And the 7th option is "V / The Fifth"