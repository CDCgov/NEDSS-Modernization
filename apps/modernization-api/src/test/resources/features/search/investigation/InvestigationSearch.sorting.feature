@investigation_search
Feature: Investigation Search Sorting

  Background:
    Given I am logged in
    And I can "Find" any "Patient"
    And I can "View" any "Investigation" for "STD" within all jurisdictions
    And I can "View" any "Investigation" for "ARBO" within all jurisdictions

  Scenario: I can find Investigations ordered by the patient's last name ascending
    Given I have a patient
    And the patient has the "legal" name "Ben" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Beverly" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "last name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "last name" of "Denbrough"
    And the 2nd investigation search result has a "last name" of "Hanscom"
    And the 3rd investigation search result has a "last name" of "Marsh"

  Scenario: I can find Investigations ordered by the patient's last name Descending
    Given I have a patient
    And the patient has the "legal" name "Ben" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Beverly" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "last name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "last name" of "Marsh"
    And the 2nd investigation search result has a "last name" of "Hanscom"
    And the 3rd investigation search result has a "last name" of "Denbrough"

  Scenario: I can find Investigations ordered by the patient's birthday ascending
    Given I have a patient
    And the patient has a "birthday" of "1980-09-29"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "2013-09-24"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "1974-04-05"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "birthday" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "birthday" of "1974-04-05"
    And the 2nd investigation search result has a "birthday" of "1980-09-29"
    And the 3rd investigation search result has a "birthday" of "2013-09-24"

  Scenario: I can find Investigations ordered by the patient's birthday descending
    Given I have a patient
    And the patient has a "birthday" of "1980-09-29"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "2013-09-24"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "1974-04-05"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "birthday" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "birthday" of "2013-09-24"
    And the 2nd investigation search result has a "birthday" of "1980-09-29"
    And the 3rd investigation search result has a "birthday" of "1974-04-05"

  Scenario: I can find Investigations ordered by the patient's first name ascending
    Given I have a patient
    And the patient has the "legal" name "Beverly" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Ben" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "first name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Ben"
    And the 2nd investigation search result has a "first name" of "Beverly"
    And the 3rd investigation search result has a "first name" of "Bill"

  Scenario: I can find Investigations ordered by the patient's first name Descending
    Given I have a patient
    And the patient has the "legal" name "Beverly" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Ben" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "first name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Bill"
    And the 2nd investigation search result has a "first name" of "Beverly"
    And the 3rd investigation search result has a "first name" of "Ben"

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

  Scenario: I can find Investigations ordered by the patient's local_id ascending
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
    And I want search results sorted by "local_id" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "local id" of "120"
    And the 2nd investigation search result has a "local id" of "220"
    And the 3rd investigation search result has a "local id" of "320"

  Scenario: I can find Investigations ordered by the patient's local_id descending
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
    And I want search results sorted by "local_id" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "local id" of "320"
    And the 2nd investigation search result has a "local id" of "220"
    And the 3rd investigation search result has a "local id" of "120"

  Scenario: I can find Investigations ordered by the investigation id ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a investigation id of CAS10000000GA03
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigation id of CAS10000000GA01
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigation id of CAS10000000GA02
    And investigations are available for search
    And I want search results sorted by "investigation id" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "investigation id" of "CAS10000000GA01"
    And the 2nd investigation search result has a "investigation id" of "CAS10000000GA02"
    And the 3rd investigation search result has a "investigation id" of "CAS10000000GA03"

  Scenario: I can find Investigations ordered by the investigation id descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a investigation id of CAS10000000GA03
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigation id of CAS10000000GA01
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigation id of CAS10000000GA02
    And investigations are available for search
    And I want search results sorted by "investigation id" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "investigation id" of "CAS10000000GA03"
    And the 2nd investigation search result has a "investigation id" of "CAS10000000GA02"
    And the 3rd investigation search result has a "investigation id" of "CAS10000000GA01"

  Scenario: I can find Investigations ordered by the investigation's start date ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation was started on 1980-09-29
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 2013-09-24
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 1974-04-05
    And investigations are available for search
    And I want search results sorted by "start date" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "start date" of "1974-04-05"
    And the 2nd investigation search result has a "start date" of "1980-09-29"
    And the 3rd investigation search result has a "start date" of "2013-09-24"

  Scenario: I can find Investigations ordered by the patient's start date descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation was started on 1980-09-29
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 2013-09-24
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation was started on 1974-04-05
    And investigations are available for search
    And I want search results sorted by "start date" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "start date" of "2013-09-24"
    And the 2nd investigation search result has a "start date" of "1980-09-29"
    And the 3rd investigation search result has a "start date" of "1974-04-05"

  Scenario: I can find Investigations ordered by the patient's condition ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is for the CCC condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the AAA condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the BBB condition
    And investigations are available for search
    And I want search results sorted by "condition" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "condition" of "AAA"
    And the 2nd investigation search result has a "condition" of "BBB"
    And the 3rd investigation search result has a "condition" of "CCC"

  Scenario: I can find Investigations ordered by the patient's condition descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is for the CCC condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the AAA condition
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation is for the BBB condition
    And investigations are available for search
    And I want search results sorted by "condition" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "condition" of "CCC"
    And the 2nd investigation search result has a "condition" of "BBB"
    And the 3rd investigation search result has a "condition" of "AAA"

  Scenario: I can find Investigations ordered by the investigator ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a investigator of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigator of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigator of BBB
    And investigations are available for search
    And I want search results sorted by "investigator" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "investigator" of "AAA"
    And the 2nd investigation search result has a "investigator" of "BBB"
    And the 3rd investigation search result has a "investigator" of "CCC"

  Scenario: I can find Investigations ordered by the investigator descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a investigator of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigator of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a investigator of BBB
    And investigations are available for search
    And I want search results sorted by "investigator" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "investigator" of "CCC"
    And the 2nd investigation search result has a "investigator" of "BBB"
    And the 3rd investigation search result has a "investigator" of "AAA"

  Scenario: I can find Investigations ordered by the jurisdiction ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a jurisdiction of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a jurisdiction of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a jurisdiction of BBB
    And investigations are available for search
    And I want search results sorted by "jurisdiction" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "jurisdiction" of "AAA"
    And the 2nd investigation search result has a "jurisdiction" of "BBB"
    And the 3rd investigation search result has a "jurisdiction" of "CCC"

  Scenario: I can find Investigations ordered by the jurisdiction descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a jurisdiction of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a jurisdiction of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a jurisdiction of BBB
    And investigations are available for search
    And I want search results sorted by "jurisdiction" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "jurisdiction" of "CCC"
    And the 2nd investigation search result has a "jurisdiction" of "BBB"
    And the 3rd investigation search result has a "jurisdiction" of "AAA"

  Scenario: I can find Investigations ordered by the investigation status ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a processing status of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a processing status of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a processing status of BBB
    And investigations are available for search
    And I want search results sorted by "notification" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "notification" of "AAA"
    And the 2nd investigation search result has a "notification" of "BBB"
    And the 3rd investigation search result has a "notification" of "CCC"

  Scenario: I can find Investigations ordered by the investigation status descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a processing status of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a processing status of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a processing status of BBB
    And investigations are available for search
    And I want search results sorted by "notification" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "notification" of "CCC"
    And the 2nd investigation search result has a "notification" of "BBB"
    And the 3rd investigation search result has a "notification" of "AAA"

  Scenario: I can find Investigations ordered by the notification status ascending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of BBB
    And investigations are available for search
    And I want search results sorted by "notification" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "notification" of "AAA"
    And the 2nd investigation search result has a "notification" of "BBB"
    And the 3rd investigation search result has a "notification" of "CCC"

  Scenario: I can find Investigations ordered by the notification status descending
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of CCC
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of AAA
    And I have another patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of BBB
    And investigations are available for search
    And I want search results sorted by "notification" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "notification" of "CCC"
    And the 2nd investigation search result has a "notification" of "BBB"
    And the 3rd investigation search result has a "notification" of "AAA"
