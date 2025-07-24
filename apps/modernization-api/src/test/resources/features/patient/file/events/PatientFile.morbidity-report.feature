@patient-file-morbidity-reports
Feature: Patient File Morbidity Report

  Background: I am logged in and can find patient
    Given I am logged in
    And there is a program area named "Extraterrestrial"
    And there is a jurisdiction named "Romford"
    And I can "view" any "ObservationMorbidityReport" for Extraterrestrial in Romford
    And there is an organization named "Hellmouth"
    And there is an organization named "Darkplace Hospital"
    And I have a patient

  Scenario: I cannot retrieve Morbidity Reports for a patient not the subject of any Morbidity Reports
    When I view the morbidity reports for the patient
    Then no values are returned

  Scenario: I can retrieve Morbidity Reports for a patient
    Given the patient has a morbidity report reported by Darkplace Hospital
    And the morbidity report is for Extraterrestrial within Romford
    And the morbidity report was added on 04/25/2010 at 11:15:17
    And the morbidity report was received on 03/29/2010 at 02:51:07
    And the morbidity report was reported on 07/11/1989
    And the morbidity report is for the condition Diphtheria
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report within "Romford"
    And the patient file has the morbidity report added on 04/25/2010 at 11:15:17
    And the patient file has the morbidity report received on 03/29/2010 at 02:51:07
    And the patient file has the morbidity report reported on 07/11/1989
    And the patient file has the morbidity report for the condition "Diphtheria"

  Scenario: I can retrieve Morbidity Reports for a patient ordered by a provider
    Given the patient has a morbidity report
    And there is a provider named "Dean" "Learner"
    And the morbidity report was ordered by the provider
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report ordered by "Dean" "Learner"

  Scenario: I can retrieve Morbidity Reports for a patient reported by a provider
    Given the patient has a morbidity report
    And there is a provider named "Liz" "Asher"
    And the morbidity report was reported by the provider
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report reported by "Liz" "Asher"

  Scenario:I can view Resulted Tests of Morbidity Reports for a patient
    Given the patient has a morbidity report
    And the morbidity report has an Aldolase test with a coded result of above threshold
    And the morbidity report has a Digoxin test with a numeric result of "1013" (drop)
    And the morbidity report has a Paracentesis test with a text result of "yikes!"
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report containing an "Aldolase" test with the result "above threshold"
    And the patient file has the morbidity report containing a "Digoxin" test with the result "=1013 (drop)"
    And the patient file has the morbidity report containing a "Paracentesis" test with the result "yikes!"

  Scenario: I can retrieve Morbidity Reports with associated investigations for a patient
    Given the patient is a subject of an investigation for Extraterrestrial within Romford
    And the investigation is for the Mumps condition
    And I can "view" any "investigation" for Extraterrestrial in Romford
    And the patient has a morbidity report
    And the morbidity report is associated with the investigation
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report associated with the investigation

  Scenario: I can view treatments of Morbidity Reports for a patient
    Given I can "view" any "treatment"
    And the patient has a morbidity report
    And the morbidity report includes a Laser Surgery treatment
    And the morbidity report includes the custom "Chicken Soup" treatment
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report contains the "Laser Surgery" treatment
    Then the patient file has the morbidity report contains the "Chicken Soup" treatment

  Scenario: I cannot view treatments of Morbidity Reports for a patient without the proper permissions
    Given the patient has a morbidity report
    And the morbidity report includes a Laser Surgery treatment
    And the morbidity report includes the custom "Chicken Soup" treatment
    When I view the morbidity reports for the patient
    Then the patient file has the morbidity report does not contain treatments
