@patient-file @patient-merge-history
Feature: Patient Merge History

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"

  Scenario: View merge history for a merged patient
    Given the "merge-creator" user exists
    And patient "John" "Smith" with id 2000 was merged into patient "Jane" "Doe" with id 2001 by merge-creator
    When I view the merge history for patient 2001
    Then the request succeeds
    And the patient file merge history has an entry with legal name "Smith, John"
    And the patient file merge history has an entry with merged by user "merge-creator"
