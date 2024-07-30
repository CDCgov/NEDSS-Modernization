@simple-search-redirect
Feature: Searching from the NBS home page

  Background:
    Given I am logged in

  Scenario: Patient simple search parameters are forwarded
    Given I want a simple search for a "Date Of Birth" of "2000-01-01"
    And I want a simple search for a "First name" of "Firstly"
    And I want a simple search for a "Last name" of "Lastly"
    And I want a simple search for a "Gender" of "F"
    And I want a simple search for a "Patient ID" of "100056"
    When I perform a search from the NBS Home screen
    Then I am redirected to Advanced Search
    And the search parameters include a "Date of Birth" of "2000-01-01"
    And the search parameters include a "First Name" of "Firstly"
    And the search parameters include a "Last Name" of "Lastly"
    And the search parameters include a "Gender" of "F"
    And the search parameters include a "Patient ID" of "100056"
    And the search type is patients
    And the user Id is present in the redirect
    And the token is present in the redirect

  Scenario Outline: Event simple search parameters are forwarded
    Given I want a simple search for a <event-type> with the ID "2783"
    When I perform a search from the NBS Home screen
    Then I am redirected to Advanced Search
    And the search parameters include a <event-type> with the ID "2783"
    And the search type is <search-type>

    Examples:
      | event-type          | search-type    |
      | ABCS Case ID        | investigations |
      | City/County Case ID | investigations |
      | Investigation ID    | investigations |
      | Notification ID     | investigations |
      | State Case ID       | investigations |
      | Accession Number ID | lab-reports    |
      | Lab ID              | lab-reports    |

  Scenario: I do not have a session id
    Given I am not logged in
    And I perform a search from the NBS Home screen
    Then I am not allowed due to insufficient permissions
