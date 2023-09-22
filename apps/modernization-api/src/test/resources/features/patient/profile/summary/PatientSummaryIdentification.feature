@patient-summary-identification
Feature: Patient Summary Identification

    Background:
        Given I have a patient
        And the patient has identification

    Scenario: I can retrieve a patient's summary identification
        Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
        When a patient summary is requested by patient identifier
        And patient summary identifications are requested by patient summary
        Then the patient summary identifications are found

    Scenario: I can not retrieve a patient's summary identification without permission
        Given I have the authorities: "NOTHING" for the jurisdiction: "ALL" and program area: "STD"
        When patient summary identifications are requested by patient summary
        Then the patient summary identifications are not accessible

    Scenario: Patient summary identifications are ordered by as of date
        Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
        And the patient has identification
        And the patient has identification
        And the patient has identification
        When a patient summary is requested by patient identifier
        And patient summary identifications are requested by patient summary
        Then the summary identifications are in the correct order

    Scenario: Patient summary identifications are limited to two entries
        Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
        And the patient has identification
        And the patient has identification
        And the patient has identification
        When a patient summary is requested by patient identifier
        And patient summary identifications are requested by patient summary
        Then only two summary identifications are returned
