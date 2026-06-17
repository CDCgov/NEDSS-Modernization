Feature: Run reports

  Background:
    Given I am logged in as secure user
    Given I navigate to reports


  Scenario: I can run a report
    When I navigate to report with reportUid: 6 and dataSourceUid: 1
    Then I navigate to report with reportUid: 6 and dataSourceUid: 1
