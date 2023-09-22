@patient-summary
Feature: Patient summary

    Background:
        Given I have a patient

    Scenario: I can retrieve a patient's summary
        Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
        When a patient summary is requested by patient identifier
        Then the summary is found

    Scenario: I can not retrieve a patient's summary without permissions
        Given I have the authorities: "NOTHING" for the jurisdiction: "ALL" and program area: "STD"
        When a patient summary is requested by patient identifier
        Then the summary is not accessible