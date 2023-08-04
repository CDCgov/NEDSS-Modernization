@documents_requiring_review
Feature: I can find documents requiring review for a particular patient

    Background:
        Given I have a patient

    Scenario: I can find lab reports requiring review for a patient
        Given I have the authorities: "FIND-PATIENT,VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "STD"
        And the patient has an unprocessed lab report
        When I search for documents requiring review for the patient
        Then the lab report requiring review is returned

    Scenario: I can not find lab reports requiring review without being logged in
        When I search for documents requiring review for the patient
        Then an access denied is thrown

    Scenario: I can not find lab reports requiring review without proper permissions
        Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
        When I search for documents requiring review for the patient
        Then an access denied is thrown