Feature: Report API Validation

  Background:
    Given I am logged in as secure user

  Scenario: Execute report with valid request body
    When I send a POST request to /nbs/api/report/execute with a valid report execution request
    Then the response status should be 200
    And the response should contain a report result

  Scenario: Execute report with missing reportUid
    When I send a POST request to /nbs/api/report/execute with missing reportUid
    Then the response status should be 422
    And the response should contain validation error for reportUid

  Scenario: Execute report with missing dataSourceUid
    When I send a POST request to /nbs/api/report/execute with missing dataSourceUid
    Then the response status should be 422
    And the response should contain validation error for dataSourceUid

  Scenario: Execute report with missing isExport
    When I send a POST request to /nbs/api/report/execute with missing isExport
    Then the response status should be 422
    And the response should contain validation error for isExport

  Scenario: Execute report with invalid reportUid type
    When I send a POST request to /nbs/api/report/execute with reportUid as string
    Then the response status should be 422
    And the response should contain validation error for reportUid

  Scenario: Execute report with invalid dataSourceUid type
    When I send a POST request to /nbs/api/report/execute with dataSourceUid as string
    Then the response status should be 422
    And the response should contain validation error for dataSourceUid

  Scenario: Execute report with invalid isExport type
    When I send a POST request to /nbs/api/report/execute with isExport as string
    Then the response status should be 422
    And the response should contain validation error for isExport

  Scenario: Execute report with invalid BasicFilter structure
    When I send a POST request to /nbs/api/report/execute with invalid BasicFilter
    Then the response status should be 422
    And the response should contain validation error for filters

  Scenario: Execute report with invalid AdvancedFilter structure
    When I send a POST request to /nbs/api/report/execute with invalid AdvancedFilter
    Then the response status should be 422
    And the response should contain validation error for filters