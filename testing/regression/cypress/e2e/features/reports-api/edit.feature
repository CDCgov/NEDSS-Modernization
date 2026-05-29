Feature: Admin Edit Report API Validation

  Background:
    Given I am logged in as secure user

  Scenario: Edit report with valid request body
    When I send a PUT request to the edit endpoint with a valid report
    Then the response status should be 200
    Then the response should contain a ReportId

  Scenario: Edit report with missing dataSourceId
    When I send a PUT request to the edit endpoint with missing dataSourceId
    Then the response status should be 422
    Then the response should contain validation error for "dataSourceId"

  Scenario: Edit report with missing libraryId
    When I send a PUT request to the edit endpoint with missing libraryId
    Then the response status should be 422
    Then the response should contain validation error for "libraryId"

  Scenario: Edit report with missing reportTitle
    When I send a PUT request to the edit endpoint with missing reportTitle
    Then the response status should be 422
    Then the response should contain validation error for "reportTitle"

  Scenario: Edit report with missing sectionCode
    When I send a PUT request to the edit endpoint with missing sectionCode
    Then the response status should be 422
    Then the response should contain validation error for "sectionCode"

  Scenario: Edit report with missing ownerId
    When I send a PUT request to the edit endpoint with missing ownerId
    Then the response status should be 422
    Then the response should contain validation error for "ownerId"

  Scenario: Edit report with missing group
    When I send a PUT request to the edit endpoint with missing group
    Then the response status should be 422
    Then the response should contain validation error for "group"