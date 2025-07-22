@patient-file @patient-merge-history
Feature: Patient Merge History

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"

  Scenario: View merge history for a non-existing patient
    When I view the merge history for patient 99999
    Then the request succeeds

