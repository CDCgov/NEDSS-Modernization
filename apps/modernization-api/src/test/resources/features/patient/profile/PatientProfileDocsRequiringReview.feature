@documents_requiring_review @documents
Feature: I can find documents requiring review for a particular patient

    Background:
        Given I have a patient

    Scenario Outline: I can find documents requiring review for a patient
        Given I have the authorities: "<permission>" for the jurisdiction: "ALL" and program area: "STD"
        And the patient has an unprocessed "<document type>"
        When I search for documents requiring review for the patient
        Then the "<document type>" requiring review is returned
        Examples:
            | document type    | permission                                                                           |
            | lab report       | FIND-PATIENT,VIEW-OBSERVATIONLABREPORT                                               |
            | morbidity report | FIND-PATIENT,VIEW-OBSERVATIONLABREPORT,VIEW-OBSERVATIONMORBIDITYREPORT               |
            | document         | FIND-PATIENT,VIEW-OBSERVATIONLABREPORT,VIEW-OBSERVATIONMORBIDITYREPORT,VIEW-DOCUMENT |

    Scenario Outline: I can not find documents requiring review without the proper permission
        Given I have the authorities: "<permission>" for the jurisdiction: "ALL" and program area: "STD"
        And the patient has an unprocessed "<document type>"
        When I search for documents requiring review for the patient
        Then no "<document type>" are returned
        Examples:
            | document type    | permission                                   |
            | lab report       | FIND-PATIENT,VIEW-OBSERVATIONMORBIDITYREPORT |
            | morbidity report | FIND-PATIENT,VIEW-OBSERVATIONLABREPORT       |
            | document         | FIND-PATIENT,VIEW-OBSERVATIONMORBIDITYREPORT |


    Scenario: I can not find lab reports requiring review without being logged in
        When I search for documents requiring review for the patient
        Then a credentials not found exception is thrown

    Scenario: I can not find lab reports requiring review without any permissions
        Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
        When I search for documents requiring review for the patient
        Then an access denied exception is thrown
