Feature: Classic NBS - User can view and manage data in NBS Questions

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Value Set Library

  Scenario: Add new Value Set LOCAL
    And Click on Add new in Value Set Library
    Then Fill the details to create new "LOCAL" Value Set
    Then Click submit button to create Value Set

