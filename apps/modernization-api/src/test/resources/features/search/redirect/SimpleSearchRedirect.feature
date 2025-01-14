@simple-search-redirect
Feature: Searching from the NBS home page

  Background:
    Given I am logged in

  Scenario: NBS home page search redirects to Patient search
    Given I want a simple search for a "Date Of Birth" of "2000-01-07"
    And I want a simple search for a "First name" of "Firstly"
    And I want a simple search for a "Last name" of "%Lastly"
    And I want a simple search for a "Gender" of "F"
    And I want a simple search for a "Patient ID" of "100056"
    When I perform a search from the NBS Home screen
    Then I am redirected to Advanced Search
    And the search parameters include a date of birth equal to 01/07/2000
    And the search parameters include a "First Name" that starts with "Firstly"
    And the search parameters include a "Last Name" that contains "Lastly"
    And the search parameters include a "Gender" of "F"
    And the search parameters include a "Patient ID" of "100056"
    And the search type is patients
    And the user Id is present in the redirect
    And the token is present in the redirect

  Scenario Outline: NBS home page search redirects to Investigation and Laboratory search
    Given I want a simple search for an <event-type> with the ID "<value>"
    When I perform a search from the NBS Home screen
    Then I am redirected to Advanced Search
    And the search parameters include a <event-type> with the ID "<value>"

    Examples:
      | event-type          | value |
      | ABCS Case ID        | 10000 |
      | City/County Case ID | 10001 |
      | Investigation ID    | 10002 |
      | Notification ID     | 10003 |
      | State Case ID       | 10004 |
      | Accession Number ID | 10005 |
      | Lab ID              | 10006 |
      | Vaccination ID      | 10007 |
      | Treatment ID        | 10008 |
      | Morbidity Report ID | 10009 |
      | Document ID         | 10010 |

  Scenario: NBS home page search is not redirected when a user is not logged in
    Given I am not logged in
    And I want a simple search for a "First name" of "Firstly"
    And I perform a search from the NBS Home screen
    Then I am not allowed due to insufficient permissions
