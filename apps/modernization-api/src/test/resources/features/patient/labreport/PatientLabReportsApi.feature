@patient_labreports_api
Feature: Patient Lab Reports API

  Background: I am logged in and can find patient
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: The api return a blank packet if i do not have permissions
    And the patient has a lab report
    When the patient lab report api are retrieved
    Then I am not allowed due to insufficient permissions

  Scenario: The api returns an empty array when the patient has no lab reports
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    When the patient lab report api are retrieved
    Then no values are returned

  Scenario: The api returns results if the patient has lab reports
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And the patient has a lab report
    When the patient lab report api are retrieved
    Then lab reports are returned

  Scenario: The api returns detailed results the patient has lab reports
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And the "lab-creator" user exists
    And the patient has a lab report reported by Northside Hospital
    And the lab report was created by lab-creator on 01/01/2000
    And the lab report has an Acid-Fast Stain test with a coded result of abnormal
    And the lab report was ordered by the Emory University Hospital facility
    And there is a provider named "Dean" "Learner"
    And the lab report was ordered by the provider
    And the lab report is for a pregnant patient
    And the lab report was entered externally
    And the lab report was filled by "307947"
    And the lab report has not been processed
    And the lab report is electronic
    When the patient lab report api are retrieved
    Then lab reports are returned
    Then the 1st labreport has a "jurisdiction" of "Out of system"
    Then the 1st labreport has a "programArea" of "STD"
    Then the 1st labreport has a "reportingFacility" of "Northside Hospital"
    Then the 1st labreport has a "orderingProvider" of "Learner"
    Then the 1st labreport has a "orderingFacility" of "Emory University Hospital"
    Then the 1st labreport has a "receivedDate" of 01/01/2000 at 00:00:00
    Then the lab report has an Acid-Fast Stain test with a coded result of abnormal


  Scenario: The api returns associated investigation
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And the "lab-creator" user exists
    And the patient has a lab report reported by Northside Hospital
    And the lab report was created by lab-creator on 01/01/2000
    And the patient is a subject of an investigation
    And the investigation was started on 04/05/1974
    And the investigation is for the Mumps condition
    And the laboratory report is associated with the investigation
    When the patient lab report api are retrieved
    Then lab reports are returned
    Then the 1st labreport has an "associatedInvestigationCondition" of "Mumps"


  Scenario: The api returns multiple lab reports for a patient
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And the patient has a lab report reported by Piedmont Hospital
    And the patient has a lab report reported by Northside Hospital
    And the patient is a subject of an investigation
    And the investigation is related to ABCs Case "1013673"
    When the patient lab report api are retrieved
    Then lab reports are returned
    Then the 1st labreport has a "reportingFacility" of "Piedmont Hospital"
    Then the 2nd labreport has a "reportingFacility" of "Northside Hospital"
