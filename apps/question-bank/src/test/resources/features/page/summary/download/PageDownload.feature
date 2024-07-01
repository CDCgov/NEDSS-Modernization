@page-summary-search @download-summary
Feature: Download Page Summary

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a Investigation page named "filtering one"
    And I have a Investigation page named "filtering two"
    And I have a Investigation page named "filtering three"
    And I have a Vaccination page named "vaccination page"

  @download-summary-csv-all  @download-summary
  Scenario: I download a csv of all page summaries
    When I download page summaries as a csv
    Then all summaries are present in the csv
    And the csv has the following headers:
      | Event Type            |
      | Page Name             |
      | Page State            |
      | Related Conditions(s) |
      | Last Updated          |
      | Last Updated By       |

  @download-summary-pdf-all  @download-summary
  Scenario: I download a pdf of all page summaries
    When I download page summaries as a pdf
    Then all summaries are present in the pdf
    And the pdf has the following headers:
      | Event Type            |
      | Page Name             |
      | Page State            |
      | Related Conditions(s) |
      | Last Updated          |
      | Last Updated By       |

  @download-summary-csv-filtered  @download-summary
  Scenario: I download a csv of page summaries with a filter
    Given I am looking for page summaries that contain "filtering"
    And I am looking for page summaries sorted by "event type" ascending
    When I download page summaries as a csv
    Then 3 summaries are present in the csv

  @download-summary-pdf-filtered  @download-summary
  Scenario: I download a pdf of page summaries with a filter
    Given I am looking for page summaries that contain "filtering"
    And I am looking for page summaries sorted by "event type" ascending
    When I download page summaries as a pdf
    Then 3 summaries are present in the pdf
