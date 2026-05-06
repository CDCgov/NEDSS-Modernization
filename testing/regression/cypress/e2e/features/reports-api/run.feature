Feature: Run Report API Validation

  Background:
    Given I am logged in as secure user

  Scenario: Run report with missing reportUid
    When I send a POST request to /nbs/api/report/run with missing reportUid
    Then the response status should be 422
    Then the response should contain validation error for "reportUid"

  Scenario: Run report with missing dataSourceUid
    When I send a POST request to /nbs/api/report/run with missing dataSourceUid
    Then the response status should be 422
    Then the response should contain validation error for "dataSourceUid"

  Scenario: Run report with negative reportUid
    When I send a POST request to /nbs/api/report/run with negative reportUid
    Then the response status should be 422
    Then the response should contain validation error for "reportUid"

  Scenario: Run report with negative dataSourceUid
    When I send a POST request to /nbs/api/report/run with negative dataSourceUid
    Then the response status should be 422
    Then the response should contain validation error for "dataSourceUid"

  Scenario: Run report with invalid reportUid type
    When I send a POST request to /nbs/api/report/run with reportUid as string
    Then the response status should be 422
    Then the response should contain serialization error for "reportUid"

  Scenario: Run report with invalid dataSourceUid type
    When I send a POST request to /nbs/api/report/run with dataSourceUid as string
    Then the response status should be 422
    Then the response should contain serialization error for "dataSourceUid"

  Scenario: Run report with invalid isExport type
    When I send a POST request to /nbs/api/report/run with isExport as string
    Then the response status should be 422
    Then the response should contain serialization error for "isExport"

  Scenario: Run report with invalid basic filters
    When I send a POST request to /nbs/api/report/run with invalid basic filters
    Then the response status should be 422
    Then the response should contain validation error for "basicFilters"

  Scenario: Run report with an invalid advanced filter
    When I send a POST request to /nbs/api/report/run with an invalid advanced filter
    Then the response status should be 422
    Then the response should contain validation error for "advancedFilter"