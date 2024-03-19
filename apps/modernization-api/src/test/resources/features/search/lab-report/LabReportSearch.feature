@lab_report_search
Feature: Lab report search

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And I can "view" any "ObservationLabReport" for "ARBO" within all jurisdictions
    And the "lab-creator" user exists
    And the "lab-updater" user exists
    And I have a patient
    And the patient has a "first name" of "Monterey"
    And the patient has a lab report
    And the patient has a lab report reported by Northside Hospital
    And the lab report was ordered by the Emory University Hospital facility
    And the lab report is for ARBO within Gwinnett County
    And the lab report is for a pregnant patient
    And the lab report was entered externally
    And the lab report was filled by "307947"
    And the lab report was received on 08/11/2017
    And the lab report has not been processed
    And the lab report has an Acid-Fast Stain test with a coded result of abnormal
    And lab reports are available for search
    And I am searching for the Lab Report

  Scenario: I can search for Lab Reports for a specific patient
    Given I have another patient
    And the patient has a lab report
    And the lab report is available for search
    And I am searching for the Lab Report
    And I add the lab report criteria for "patient id"
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

  Scenario: I can search for Lab Reports created by a specific user
    Given the patient has a lab report
    And the lab report was created by lab-creator on 01/27/2011
    And the lab report is available for search
    And I am searching for the Lab Report
    And I want to find lab reports created by lab-creator
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

  Scenario: I can search for Lab Reports updated by a specific user
    Given the patient has a lab report
    And the lab report was updated by lab-updater on 02/13/2013
    And the lab report is available for search
    And I am searching for the Lab Report
    And I want to find lab reports updated by lab-updater
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

  Scenario: I can search for NEW Lab Reports
    Given the lab report was updated by lab-updater on 02/13/2013
    And the lab report is available for search
    And I am searching for the Lab Report
    And I want to find new lab reports
    When I search for lab reports
    Then the Lab Report search results do not contain the lab report
    And there is only one lab report search result

  Scenario: I can search for updated Lab Reports
    Given the lab report was updated by lab-updater on 02/13/2013
    And the lab report is available for search
    And I am searching for the Lab Report
    And I want to find updated lab reports
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

  Scenario: I can search for Lab Reports ordered by a specific provider
    Given the patient has a lab report
    And there is a provider named "Emilio" "Lizardo"
    And the lab report was ordered by the provider
    And the lab report is available for search
    And I am searching for the Lab Report
    And I want to find lab reports ordered by the provider
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

  Scenario Outline: I can search for Lab Reports
    Given I add the lab report criteria for "<field>"
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

    Examples:
      | field                          |
      | program area                   |
      | jurisdiction                   |
      | pregnancy status               |
      | accession number               |
      | lab id                         |
      | date of report                 |
      | Date Received by Public Health |
      | Date of specimen collection    |
      | entry method                   |
      | entered by                     |
      | processing status              |
      | Ordering Facility              |
      | Reporting Facility             |
      | resulted test                  |
      | coded result                   |


  Scenario Outline: I can find a lab report by many fields in the laboratory report
    Given I add the lab report criteria for "<field>"
    And I add the lab report criteria for "<field2>"
    And I add the lab report criteria for "<field3>"
    When I search for lab reports
    Then the Lab Report search results contain the lab report
    And there is only one lab report search result

    Examples:
      | field                          | field2        | field3       |
      | program area                   | jurisdiction  | lab id       |
      | jurisdiction                   | resulted test | lab id       |
      | pregnancy status               | jurisdiction  | lab id       |
      | accession number               | jurisdiction  | entry method |
      | lab id                         | jurisdiction  | entry method |
      | Date of Report                 | jurisdiction  | lab id       |
      | Date Received by Public Health | jurisdiction  | lab id       |
      | Date of specimen collection    | jurisdiction  | lab id       |
      | entry method                   | jurisdiction  | lab id       |
      | entered by                     | jurisdiction  | lab id       |
      | processing status              | jurisdiction  | lab id       |
      | Ordering Facility              | jurisdiction  | lab id       |
      | Reporting Facility             | jurisdiction  | lab id       |
      | resulted test                  | jurisdiction  | lab id       |
      | coded result                   | jurisdiction  | lab id       |
      | patient id                     | resulted test | lab id       |
