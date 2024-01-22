@patient-profile-identifications @web-interaction
  Feature: Patient Profile Identifications

    Background:
      Given I am logged in
      And I can "Find" any "Patient"
      And I have a patient

    Scenario: I cannot retrieve identifications for a patient
      When I view the Patient Profile Identification
      Then the profile has no associated identifications

    Scenario: I can retrieve identifications for a patient
      Given the patient can be identified with a Medicare Number of "1009"
      And the patient can be identified with a Driver's License Number of "1013"
      And the patient can be identified with an Account Number of "7907"
      And the patient can be identified with a Social Security of "52129"
      And the patient can be identified with a Driver's License Number of "983257"
      And I want at most 3 results
      When I view the Patient Profile Identification
      Then the patient's identification includes 3 entries
      And the patient has 5 identification entries

    Scenario: I can only retrieve valid identifications for a patient
      Given the patient can be identified with a Medicare Number of "1009"
      And the patient can be identified with a Driver's License Number of "1013"
      And the patient can be identified with an Account Number without a value
      When I view the Patient Profile Identification
      Then the patient has 3 identification entries
      And the patient's identifications include a Medicare Number of "1009"
      And the patient's identifications include a Driver's License Number of "1013"
      And the patient's identifications includes an Account Number
