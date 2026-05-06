Feature: Export Report API Validation

  Background:
    Given I am logged in as secure user

  Scenario: Export report with missing reportUid
    When I send a POST request to /nbs/api/report/export with missing reportUid
    Then the export response status should be 422
    Then the export response should contain validation error for "reportUid"

  Scenario: Export report with missing dataSourceUid
    When I send a POST request to /nbs/api/report/export with missing dataSourceUid
    Then the export response status should be 422
    Then the export response should contain validation error for "dataSourceUid"

  Scenario: Export report with negative reportUid
    When I send a POST request to /nbs/api/report/export with negative reportUid
    Then the export response status should be 422
    Then the export response should contain validation error for "reportUid"

  Scenario: Export report with negative dataSourceUid
    When I send a POST request to /nbs/api/report/export with negative dataSourceUid
    Then the export response status should be 422
    Then the export response should contain validation error for "dataSourceUid"

  Scenario: Export report with invalid reportUid type
    When I send a POST request to /nbs/api/report/export with reportUid as string
    Then the export response status should be 422
    Then the export response should contain serialization error for "reportUid"

  Scenario: Export report with invalid dataSourceUid type
    When I send a POST request to /nbs/api/report/export with dataSourceUid as string
    Then the export response status should be 422
    Then the export response should contain serialization error for "dataSourceUid"

  Scenario: Export report with invalid isExport type
    When I send a POST request to /nbs/api/report/export with isExport as string
    Then the export response status should be 422
    Then the export response should contain serialization error for "isExport"

  Scenario: Export report with invalid basic filters
    When I send a POST request to /nbs/api/report/export with invalid basic filters
    Then the export response status should be 422
    Then the export response should contain validation error for "basicFilters"

  Scenario: Export report with an invalid advanced filter
    When I send a POST request to /nbs/api/report/export with an invalid advanced filter
    Then the export response status should be 422
    Then the export response should contain validation error for "advancedFilter"