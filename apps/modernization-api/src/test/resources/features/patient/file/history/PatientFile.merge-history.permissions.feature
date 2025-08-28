@patient-file @patient-merge-history
Feature: Permissions for viewing the merge history of a patient

  Scenario: I cannot view a patient's merge history without logging in
    Given I am not logged in at all
    When I view the merge history for patient 99999
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a patient's merge history without permission
    Given I am logged into NBS
    When I view the merge history for patient 99999
    Then I am not allowed due to insufficient permissions
