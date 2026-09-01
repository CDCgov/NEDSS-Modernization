Feature: Classic NBS - User can view and manage data in NBS Templates

  Background:
    Given I am logged in as secure user and stay on classic
    And Navigate to Template Library

  # Needs test template to import
  @skip
  Scenario: Import template
    When Click on Import in Template Library
    And Click on Choose File in Template Library

  Scenario: Filter results in template library
    When Click filter button in Template Library
    And Enter filter text "COVID" in the input
    And Click OK button to filter
    Then all template names should contain the text "COVID"

  Scenario: View rule in template library
    When Click a template in template library
    And Click View Rules button in Template view
    Then Verify rules listed in the results page
