Feature: Patient Search by Basic Info

  Background:
    Given I am logged in as "superuser" and password ""

  Scenario: Basic Info - Search by Last Name
    When I search by last name as "Althorp"
    Then I should see Results with the last name "Althorp"

  Scenario: Basic Info - Search by First Name
    When I search by first name as "Vivienne"
    Then I should see Results with the first name "Vivienne"

  Scenario: Basic Info - Search by DOB
    When I search by dob as "10/23/1965"
    Then I should see No Results found text

  Scenario: Basic Info - Search by Sex
    When I search by sex as "Male"
    Then I should see Results with the sex "Male"

  Scenario: Basic Info - Search by Patient ID
    When I search by patient id as "95136"
    Then I should see Results with the patient id "95136"

  Scenario: Search by Multiple Criteria
    When I fill last name as "Bittlestone"
    When I fill first name as "Zollie"
    When I search by dob as "04/02/1991"
    Then I should see "Zollie Polack Bittlestone, Esquire"


