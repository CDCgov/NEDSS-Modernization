Feature: Classic NBS - User can view and manage data in NBS Valueset

  Background:
    Given I am logged in as secure user and stay on classic
    And Navigate to Value Set Library

  Scenario: Add new Value Set LOCAL
    When Click on Add new in Value Set Library
    And Fill the details to create new "LOCAL" Value Set
    And Click submit button to create Value Set
    Then I should see a green success message containing text "has been successfully added to the system"
    
  Scenario: Add new Value Set PHIN
    When Click on Add new in Value Set Library
    And Fill the details to create new "PHIN" Value Set
    And Click submit button to create Value Set
    Then I should see a green success message containing text "has been successfully added to the system"

  Scenario: Filter navigate result page
    When Click filter button in Value Set library
    And Enter filter text in the input in Value Set library
    And Click OK button to filter in Value Set library

  Scenario: Collapse or Expand subsections
    When Click on a Value set in Value Set library
    And Click Collapse Subsections to collapse the sections in Value Set library
    And Click Expand Subsections to expand the sections in Value Set library

  Scenario: Add new concept in Value Set
    When Click on a Value set in Value Set library
    And Click on Add new in Value Set Concept section
    And Fill the details to create new concept Value Set
    And Click submit button to create new concept in Value Set

  Scenario: Make inactive
    When Click on a Value set in Value Set library
    And Click on Make Inactive button to inactive the value set
