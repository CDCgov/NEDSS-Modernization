@simple-search-redirect
Feature: Searching from the NBS home page

  Background:
    Given I am logged in

  Scenario: NBS home page search redirects to Patient search
    Given I want a simple search for a "Date Of Birth" of "2000-01-07"
    And I want a simple search for a "First name" of "Firstly"
    And I want a simple search for a "Last name" of "Lastly"
    And I want a simple search for a "Gender" of "F"
    And I want a simple search for a "Patient ID" of "100056"
    When I perform a search from the NBS Home screen
    Then I am redirected to Advanced Search
    And the search parameters include a "Date of Birth" of "01/07/2000"
    And the search parameters include a "First Name" of "Firstly"
    And the search parameters include a "Last Name" of "Lastly"
    And the search parameters include a "Gender" of "F"
    And the search parameters include a "Patient ID" of "100056"
    And the search type is patients
    And the user Id is present in the redirect
    And the token is present in the redirect

  Scenario: NBS home page search is not redirected when a user is not logged in
    Given I am not logged in
    And I want a simple search for a "First name" of "Firstly"
    And I perform a search from the NBS Home screen
    Then I am not allowed due to insufficient permissions
