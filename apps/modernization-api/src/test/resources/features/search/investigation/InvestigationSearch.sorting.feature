@investigation_search
Feature: Investigation Search Sorting

  Background:
    Given I am logged in
    And I can "Find" any "Patient"
    And I can "View" any "Investigation" for "STD" within all jurisdictions
    And I can "View" any "Investigation" for "ARBO" within all jurisdictions

  Scenario: I can find Investigations ordered by the patient's last name ascending
    Given I have a patient
    And the patient has the legal name "Ben" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Beverly" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "last name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "last name" of "Denbrough"
    And the 2nd investigation search result has a "last name" of "Hanscom"
    And the 3rd investigation search result has a "last name" of "Marsh"

  Scenario: I can find Investigations ordered by the patient's last name Descending
    Given I have a patient
    And the patient has the legal name "Ben" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Beverly" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "last name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "last name" of "Marsh"
    And the 2nd investigation search result has a "last name" of "Hanscom"
    And the 3rd investigation search result has a "last name" of "Denbrough"

  Scenario: I can find Investigations ordered by the patient's first name ascending
    Given I have a patient
    And the patient has the legal name "Beverly" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Ben" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "first name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Ben"
    And the 2nd investigation search result has a "first name" of "Beverly"
    And the 3rd investigation search result has a "first name" of "Bill"

  Scenario: I can find Investigations ordered by the patient's first name Descending
    Given I have a patient
    And the patient has the legal name "Beverly" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Ben" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "first name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Bill"
    And the 2nd investigation search result has a "first name" of "Beverly"
    And the 3rd investigation search result has a "first name" of "Ben"

  Scenario: I can find Investigations ordered by the patient's legal name ascending
    Given I have a patient
    And the patient has the legal name "Torrhen" "Stark"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Lyanna" "Stark"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Talisa" "Maegyr"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "legal name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Talisa"
    And the 2nd investigation search result has a "first name" of "Lyanna"
    And the 3rd investigation search result has a "first name" of "Torrhen"

  Scenario: I can find Investigations ordered by the patient's legal name Descending
    Given I have a patient
    And the patient has the legal name "Torrhen" "Stark"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Lyanna" "Stark"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the legal name "Talisa" "Maegyr"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "legal name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Torrhen"
    And the 2nd investigation search result has a "first name" of "Lyanna"
    And the 3rd investigation search result has a "first name" of "Talisa"

  Scenario: I can find Investigations ordered by the patient's birthday ascending
    Given I have a patient
    And the patient was born on 09/29/1980
    And the patient is a subject of an investigation
    And I have another patient
    And the patient was born on 09/24/2013
    And the patient is a subject of an investigation
    And I have another patient
    And the patient was born on 04/05/1974
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "birthday" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "birthday" of "1974-04-05"
    And the 2nd investigation search result has a "birthday" of "1980-09-29"
    And the 3rd investigation search result has a "birthday" of "2013-09-24"

  Scenario: I can find Investigations ordered by the patient's birthday descending
    Given I have a patient
    And the patient was born on 09/29/1980
    And the patient is a subject of an investigation
    And I have another patient
    And the patient was born on 09/24/2013
    And the patient is a subject of an investigation
    And I have another patient
    And the patient was born on 04/05/1974
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "birthday" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "birthday" of "2013-09-24"
    And the 2nd investigation search result has a "birthday" of "1980-09-29"
    And the 3rd investigation search result has a "birthday" of "1974-04-05"

  Scenario: I can find Investigations ordered by the patient's sex ascending
    Given I have a patient
    And the patient has a "sex" of "F"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "U"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "M"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "sex" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "sex" of "F"
    And the 2nd investigation search result has a "sex" of "M"
    And the 3rd investigation search result has a "sex" of "U"

  Scenario: I can find Investigations ordered by the patient's sex descending
    Given I have a patient
    And the patient has a "sex" of "F"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "U"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "M"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "sex" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "sex" of "U"
    And the 2nd investigation search result has a "sex" of "M"

  Scenario: I can find Investigations ordered by the patient's id ascending
    Given I have a patient
    And the patient has a "local id" of "PSN10000120GA01"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "local id" of "PSN10000320GA01"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "local id" of "PSN10000220GA01"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "patientId" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "patientId" of "120"
    And the 2nd investigation search result has a "patientId" of "220"
    And the 3rd investigation search result has a "patientId" of "320"

  Scenario: I can find Investigations ordered by the patient's id descending
    Given I have a patient
    And the patient has a "local id" of "PSN10000120GA01"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "local id" of "PSN10000320GA01"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "local id" of "PSN10000220GA01"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "patientId" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "patientId" of "320"
    And the 2nd investigation search result has a "patientId" of "220"
    And the 3rd investigation search result has a "patientId" of "120"

  Scenario: I can find Investigations ordered by the patient's condition ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is for the Pertussis condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the Brucellosis condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the Mumps condition
    And investigations are available for search
    And I want search results sorted by "condition" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "condition" of "Brucellosis"
    And the 2nd investigation search result has a "condition" of "Mumps"
    And the 3rd investigation search result has a "condition" of "Pertussis"

  Scenario: I can find Investigations ordered by the patient's condition descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is for the Pertussis condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the Brucellosis condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the Mumps condition
    And investigations are available for search
    And I want search results sorted by "condition" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "condition" of "Pertussis"
    And the 2nd investigation search result has a "condition" of "Mumps"
    And the 3rd investigation search result has a "condition" of "Brucellosis"

  Scenario: I can find Investigations ordered by the investigation status ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was closed on 09/17/2018
    And I have another patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "status" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "status" of "C"
    And the 2nd investigation search result has a "status" of "O"
    And the 3rd investigation search result has a "status" of "O"

  Scenario: I can find Investigations ordered by the investigation status descending
    Given I have a patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was closed on 09/17/2018
    And I have another patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "status" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "status" of "O"
    And the 2nd investigation search result has a "status" of "O"
    And the 3rd investigation search result has a "status" of "C"

  Scenario: I can find Investigations ordered by the notification status ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of UNASSIGNED
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of APPROVED
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of COMPLETED
    And investigations are available for search
    And I want search results sorted by "notification" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "notification" of "APPROVED"
    And the 2nd investigation search result has a "notification" of "COMPLETED"
    And the 3rd investigation search result has a "notification" of "UNASSIGNED"

  Scenario: I can find Investigations ordered by the notification status descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of UNASSIGNED
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of APPROVED
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of COMPLETED
    And investigations are available for search
    And I want search results sorted by "notification" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "notification" of "UNASSIGNED"
    And the 2nd investigation search result has a "notification" of "COMPLETED"
    And the 3rd investigation search result has a "notification" of "APPROVED"

  Scenario: I can find Investigations ordered by the investigation's start date ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation was started on 09/29/1980
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 09/24/2013
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 04/05/1974
    And investigations are available for search
    And I want search results sorted by "startDate" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "start date" of "1974-04-05"
    And the 2nd investigation search result has a "start date" of "1980-09-29"
    And the 3rd investigation search result has a "start date" of "2013-09-24"

  Scenario: I can find Investigations ordered by the patient's start date descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation was started on 09/29/1980
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 09/24/2013
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 04/05/1974
    And investigations are available for search
    And I want search results sorted by "startDate" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "start date" of "2013-09-24"
    And the 2nd investigation search result has a "start date" of "1980-09-29"
    And the 3rd investigation search result has a "start date" of "1974-04-05"

  Scenario: I can find Investigations ordered by the investigation id ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "investigationId" "asc"
    When I search for investigations

  Scenario: I can find Investigations ordered by the investigation id descending
    Given I have a patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "investigationId" "desc"
    When I search for investigations

    Scenario: I can find Investigations ordered by the investigator ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "investigator" "asc"
    When I search for investigations

  Scenario: I can find Investigations ordered by the investigator descending
    Given I have a patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And I have another patient
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "investigator" "desc"
    When I search for investigations

  Scenario: I can find Investigations ordered by the jurisdiction ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is for ARBO within Dekalb County
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for ARBO within Gwinnett County
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for ARBO within Dekalb County
    And investigations are available for search
    And I want search results sorted by "jurisdiction" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "jurisdiction" of "Dekalb County"
    And the 2nd investigation search result has a "jurisdiction" of "Dekalb County"
    And the 3rd investigation search result has a "jurisdiction" of "Gwinnett County"

  Scenario: I can find Investigations ordered by the jurisdiction descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is for ARBO within Dekalb County
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for ARBO within Gwinnett County
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for ARBO within Dekalb County
    And investigations are available for search
    And I want search results sorted by "jurisdiction" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "jurisdiction" of "Gwinnett County"
    And the 2nd investigation search result has a "jurisdiction" of "Dekalb County"
    And the 3rd investigation search result has a "jurisdiction" of "Dekalb County"
