Feature: Patient Search by Id

  Background:
    Given I am logged in as "superuser" and password ""

  Scenario: ID - Search by Account Number
    When I navigate to id section
    Then I search by id type as "Account number" and id as "2776129"
    Then I should see Results with the "account number" as "2776129"
