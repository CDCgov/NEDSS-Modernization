Feature: Patient Search by Id

  Background:
    Given I am logged in as "superuser" and password "@test"

  Scenario: ID - Search by Account Number
    When I navigate to id section
    Then I search by id type as "Account number" and id as "2776129"
    Then I should see Results with the "account number" as "2776129"

  Scenario: ID - Alternate person number
    When I navigate to id section
    Then I search by id type as "Alternate person number" and id as "1234"
    Then I should see Results with the "ALTERNATE PERSON NUMBER" as "1234"