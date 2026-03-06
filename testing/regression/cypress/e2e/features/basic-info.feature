Feature: Patient Search by Basic Info

  Background:
    Given I am logged in as secure user

  Scenario: Basic Info - Search by Last Name
    When I search by last name as "Singh"
    Then I should see Results with the last name "Singh"

  Scenario: Basic Info - Search by First Name
    When I search by first name as "Surma"
    Then I should see Results with the first name "Surma"

  Scenario: Basic Info - Search by DOB
    When I search by dob as "01/01/1990"
    Then I should see "Singh, Surma"
    When I search by dob as "03/07/1910"
    Then I should see no result found text

  Scenario: Basic Info - Search by Sex
    When I search by sex as "Male"
    Then I should see Results with the sex "Male"
    When I search by sex as "Female"
    Then I should see Results with the sex "Female"

  Scenario: Basic Info - Search by Patient ID
    When I search by patient id as "30024"
    Then I should see Results with the patient id "30024"

  Scenario: Search by Multiple Criteria
    When I fill last name as "Singh"
    When I fill first name as "Surma"
    When I search by dob as "01/01/1990"
    Then I should see "Singh, Surma"

  # Need deleted patient
  @skip-broken
  Scenario: Search for Deleted Patients
    When I fill last name as "a"
    When I select for Deleted patient
    Then I should see "a"

  # Need superseded patient
  @skip-broken
  Scenario: Search for Superseded Patients
    When I fill last name as "a"
    When I select for Superseded patient
    Then I should see "a"
  
  # cypress says error message is clipped, so not visible
  @skip-broken
  Scenario: Search with Invalid Data
    When I fill dob as "76/5/2000"
    Then I should see error message "The Date of birth should have a month between 1 (January) and 12 (December)."

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
    Then I sort by "Patient name"
    Then I verify the sort of patient name

  Scenario: Viewing Search Results in Table View
    When I search by last name as "test"
    Then I should see the following columns displayed for each patient:
      | Patient ID   |
      | Patient name |
      | DOB/Age      |
      | Current sex  |
      | Address      |
      | Phone        |
      | ID           |
      | Email        |
  