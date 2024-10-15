Feature: User can view and manage data in classic NBS Templates

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Template Library

  Scenario: Import template
    And Click on Import in Template Library
    Then Click on Choose File in Template Library

