Feature: Distinct Patient Phone Numbers Filter
 
  Scenario: I can update a patient's existing phone
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has multiple phones
    When a patient phone list is retrieved
    Then the patient phone list is distinct