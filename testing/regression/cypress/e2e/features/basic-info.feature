Feature: Patient Search by Basic Info

  Background:
    Given I am logged in as secure user

  Scenario: Basic Info - Search by Last Name
    When I search by last name as "Ratkeyklkb"
    Then I should see Results with the last name "Ratkeyklkb"

  Scenario: Basic Info - Search by First Name
    When I search by first name as "Caden"
    Then I should see Results with the first name "Caden"

  Scenario: Basic Info - Search by DOB
    When I search by dob as "10/16/1967"
    Then I should see no result found text
    When I search by dob as "05/16/1977"
    Then I should see "Caden Ratkeyklkb"

  Scenario: Basic Info - Search by Sex
    When I search by sex as "Male"
    Then I should see Results with the sex "Male"
    When I search by sex as "Female"
    Then I should see Results with the sex "Female"
    When I search by sex as "Other"
    Then I should see Results with the sex "No Data"

  Scenario: Basic Info - Search by Patient ID
    When I search by patient id as "95136"
    Then I should see Results with the patient id "95136"

  Scenario: Search by Multiple Criteria
    When I fill last name as "Ratkeyklkb"
    When I fill first name as "Caden"
    When I search by dob as "05/16/1977"
    Then I should see "Caden Ratkeyklkb"

  Scenario: Search for Deleted Patients
    When I fill last name as "Green Sonum Allen"
    When I select for Deleted patient
    Then I should see "Green Sonum Allen"

  Scenario: Search for Superseded Patients
    When I fill last name as "Deeanna Denesik"
    When I select for Superseded patient
    Then I should see "Deeanna Denesik"
  
  Scenario: Search with Invalid Data
    When I search by dob as "76/5"
    Then I should see error message "Please enter a valid date (mm/dd/yyyy) using only numeric characters (0-9) or choose a date from the calendar by clicking on the calendar icon."

  Scenario: Clear Search Criteria
    When I fill last name as "Zollie"
    When I fill first name as "Bittlestone"
    When I fill dob as "04/02/1991"
    And click on clear all button
    Then I last name should be ""
    When I first name should be ""
    When I dob should be ""

  Scenario: Search result sorting
    When I search by sex as "Male"
    Then I should see Results with the sex "Male"
    Then I sort by "Patient name (A-Z)"
    Then I verify the sort of patient name
    
  