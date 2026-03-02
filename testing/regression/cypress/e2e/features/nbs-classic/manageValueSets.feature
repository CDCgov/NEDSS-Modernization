Feature: Classic NBS - User can view and manage data in NBS Valueset

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Value Set Library

  Scenario: Add new Value Set LOCAL
    And Click on Add new in Value Set Library
    Then Fill the details to create new "LOCAL" Value Set
    Then Click submit button to create Value Set

  Scenario: Add new Value Set PHIN
    And Click on Add new in Value Set Library
    Then Fill the details to create new "PHIN" Value Set
    Then Click submit button to create Value Set

  Scenario: Filter navigate result page
    And Click filter button in Value Set library
    Then Enter filter text in the input in Value Set library
    And Click OK button to filter in Value Set library

  Scenario: Collapse or Expand subsections
    And Click on a Value set in Value Set library
    Then Click Collapse Subsections to collapse the sections in Value Set library
    And Click Expand Subsections to expand the sections in Value Set library

  Scenario: Add new concept in Value Set
    And Click on a Value set in Value Set library
    Then Click on Add new in Value Set Concept section
    And Fill the details to create new concept Value Set
    Then Click submit button to create new concept in Value Set

  Scenario: Make inactive
    And Click on a Value set in Value Set library
    Then Click on Make Inactive button to inactive the value set
