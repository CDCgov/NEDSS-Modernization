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
    When I search by dob as "05/16/1961"
    Then I should see "Ratkeyklkb, Caden Benjamin, Esquire"
    When I search by dob as "03/07/1910"
    Then I should see no result found text

  Scenario: Basic Info - Search by Sex
    When I search by sex as "Male"
    Then I should see Results with the sex "Male"
    When I search by sex as "Female"
    Then I should see Results with the sex "Female"

  Scenario: Basic Info - Search by Patient ID
    When I search by patient id as "95136"
    Then I should see Results with the patient id "95136"

  Scenario: Search by Multiple Criteria
    When I fill last name as "Ratkeyklkb"
    When I fill first name as "Caden"
    When I search by dob as "05/16/1961"
    Then I should see "Ratkeyklkb, Caden Benjamin, Esquire"

  Scenario: Search for Deleted Patients
    When I fill last name as "a"
    When I select for Deleted patient
    Then I should see "a"

  Scenario: Search for Superseded Patients
    When I fill last name as "a"
    When I select for Superseded patient
    Then I should see "a"
  
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
    Then I sort by "Patient name (A-Z)"
    Then I verify the sort of patient name

  Scenario: Viewing Search Results in Table View
    When I search by last name as "test"
    //When I view the results in the "Table" view
    Then I should see the following columns displayed for each patient:
      | Patient iD   |
      | Patient name |
      | DOB/Age      |
      | Current sex  |
      | Address      |
      | Phone        |
      | ID           |
      | Email        |

    
  