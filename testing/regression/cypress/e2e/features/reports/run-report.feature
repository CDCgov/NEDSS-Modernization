Feature: Run reports

  Background:
    Given I am logged in as secure user
    Given I navigate to reports


  Scenario: I can run a report for library nbs_sr_08
    When I navigate to report with reportUid: "4" and dataSourceUid: "1"
    Then I enter "04/28/2025" to the From date
    Then I enter "04/28/2026" to the To date
    Then I select "AIDS" from the "Condition" dropdown menu
    Then I click on the "Run" button
    Then I see confirmation the report has run

  Scenario: I can run a report for library nbs_sr_11
    When I navigate to report with reportUid: "8" and dataSourceUid: "1"
    Then I select "AIDS" from the "Condition" dropdown menu
    Then I select "Appling County" from the "County" dropdown menu
    Then I click on the "Run" button
    Then I see confirmation the report has run

  Scenario: I can run a report for library nbs_custom
    When I navigate to report with reportUid: "10066768" and dataSourceUid: "30"
    Then I enter "04/28/2025" to the From date
    Then I enter "04/28/2026" to the To date
    Then I select the column "AST Result"
    Then I click on the "Run" button
    Then I see confirmation the report has run
