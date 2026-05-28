Feature: Create Report API Validation

  Background:
    Given I am logged in as secure user

  Scenario: Create report with valid request body
    When I send a POST request to /nbs/api/report/configuration with a valid report
    Then the response status should be 200
    Then the response should contain a ReportId

  Scenario: Create report with missing dataSourceId
    When I send a POST request to /nbs/api/report/configuration with missing dataSourceId
    Then the response status should be 422
    Then the response should contain validation error for "dataSourceId"

  Scenario: Create report with missing libraryId
    When I send a POST request to /nbs/api/report/configuration with missing libraryId
    Then the response status should be 422
    Then the response should contain validation error for "libraryId"

  Scenario: Create report with missing reportTitle
    When I send a POST request to /nbs/api/report/configuration with missing reportTitle
    Then the response status should be 422
    Then the response should contain validation error for "reportTitle"

  Scenario: Create report with missing sectionCode
    When I send a POST request to /nbs/api/report/configuration with missing sectionCode
    Then the response status should be 422
    Then the response should contain validation error for "sectionCode"

  Scenario: Create report with missing ownerId
    When I send a POST request to /nbs/api/report/configuration with missing ownerId
    Then the response status should be 422
    Then the response should contain validation error for "ownerId"

  Scenario: Create report with missing group
    When I send a POST request to /nbs/api/report/configuration with missing group
    Then the response status should be 422
    Then the response should contain validation error for "group"