@patient-file-laboratory-reports
Feature: Patient File Laboratory Report

  Background: I am logged in and can find patient
    Given I am logged in
    And there is a program area named "Extraterrestrial"
    And there is a jurisdiction named "Romford"
    And I can "view" any "ObservationLabReport" for Extraterrestrial in Romford
    And there is an organization named "Hellmouth"
    And there is an organization named "Darkplace Hospital"
    And I have a patient

  Scenario: I cannot retrieve Laboratory Reports for a patient not the subject of any Laboratory Reports
    When I view the laboratory reports for the patient
    Then no values are returned

  Scenario: I can retrieve Laboratory Reports for a patient
    Given the patient has a lab report reported by Darkplace Hospital
    And the laboratory report is for Extraterrestrial within Romford
    And the lab report has an Acid-Fast Stain test with a coded result of abnormal
    And the lab report was ordered by the Hellmouth facility
    And there is a provider named "Dean" "Learner"
    And the lab report was ordered by the provider
    And the lab report is for a pregnant patient
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report for "Extraterrestrial" within "Romford"
    And the patient file has the laboratory report reported at "Darkplace Hospital"
    And the patient file has the laboratory report ordered by "Dean" "Learner"
    And the patient file has the laboratory report ordered at "Hellmouth"
    And the patient file has the laboratory report as not electronic

  Scenario: I can retrieve electronic Laboratory Reports for a patient
    Given the patient has a laboratory report
    And the laboratory report is electronic
    And the laboratory report was received on 01/01/2000 at 01:20:33
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report received on 01/01/2000 at 01:20:33
    And the patient file has the laboratory report as electronic

  Scenario:I can view Resulted Tests of Laboratory Reports for a patient
    Given the patient has a laboratory report
    And the laboratory report has an Aldolase test with a coded result of above threshold
    And the laboratory report has a Digoxin test with a numeric result of "1013" (drop)
    And the laboratory report has a Paracentesis test with a text result of "yikes!"
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report containing an "Aldolase" test with the result "above threshold"
    And the patient file has the laboratory report containing a "Digoxin" test with the result "=1013 (drop)"
    And the patient file has the laboratory report containing a "Paracentesis" test with the result "yikes!"

  Scenario: I can retrieve Laboratory Reports with associated investigations for a patient
    Given the patient is a subject of an investigation for Extraterrestrial within Romford
    And the investigation is for the Mumps condition
    And I can "view" any "investigation" for Extraterrestrial in Romford
    And the patient has a laboratory report
    And the laboratory report is associated with the investigation
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report associated with the investigation

  Scenario: I can retrieve Laboratory Reports with tests ordered for a patient
    Given the patient has a laboratory report
    And the laboratory report has an ordered test with a specimen from the Cornea
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report containing an ordered test with a specimen from the "Cornea"

  Scenario: I can retrieve Laboratory Reports with specimen tests ordered for a patient
    Given the patient has a laboratory report
    And the laboratory report has an ordered test with a Calculus (=Stone) specimen from the Right Naris
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report containing an ordered test with a "Calculus (=Stone)" specimen from the "Right Naris"

  Scenario: I cannot retrieve Laboratory Report associated investigations for a patient without permissions
    Given the patient is a subject of an investigation
    And the patient has a laboratory report
    And the laboratory report is associated with the investigation
    When I view the laboratory reports for the patient
    Then the patient file has the laboratory report is not associated with any investigations
