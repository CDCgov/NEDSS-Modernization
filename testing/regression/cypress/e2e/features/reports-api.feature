Feature: Report API Validation

  Background:
    Given I am logged in as secure user

  Scenario: Run report with valid request body
    When I send a POST request to /nbs/api/report/run with a valid report execution request
    Then the response status should be 200
    Then the response should contain a report result

  Scenario: Run report with missing reportUid
    When I send a POST request to /nbs/api/report/run with missing reportUid
    Then the response status should be 422
    Then the response should contain validation error for "reportUid"

  Scenario: Run report with missing dataSourceUid
    When I send a POST request to /nbs/api/report/run with missing dataSourceUid
    Then the response status should be 422
    Then the response should contain validation error for "dataSourceUid"

  Scenario: Run report with invalid reportUid type
    When I send a POST request to /nbs/api/report/run with reportUid as string
    Then the response status should be 422
    Then the response should contain validation error for "reportUid"

  Scenario: Run report with invalid dataSourceUid type
    When I send a POST request to /nbs/api/report/run with dataSourceUid as string
    Then the response status should be 422
    Then the response should contain validation error for "dataSourceUid"

  Scenario: Run report with invalid isExport type
    When I send a POST request to /nbs/api/report/run with isExport as string
    Then the response status should be 422
    Then the response should contain validation error for "isExport"

  Scenario: Run report with invalid BasicFilter structure
    When I send a POST request to /nbs/api/report/run with invalid BasicFilter
    Then the response status should be 422
    Then the response should contain validation error for "filters"

  Scenario: Run report with invalid AdvancedFilter structure
    When I send a POST request to /nbs/api/report/run with invalid AdvancedFilter
    Then the response status should be 422
    Then the response should contain validation error for "filters"