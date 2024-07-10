Feature: Page Builder - User can view existing business rules logic here.

  Background:
    Given I am logged in as "superuser" and password "@test"
    When User navigates to Business Rules and views the Business Rules

  Scenario: Verify in (Edit Mode) Business Rules landing page is accessible
    Then Application will display the Business Rules landing page

  Scenario Outline: Verify the data element for Logic if Function column equal to Date validation
    And Logic column is populated
    Then Logic will display possible values as "<Logic>"
    Examples:
      | Logic                    |
      | show Less than or        |
      | show Less or equal to or |
      | show Greater than or     |
      | show Greater or equal to |
