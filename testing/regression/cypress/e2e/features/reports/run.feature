Feature: Run report

    Background:
        Given I am logged in as secure user
        Given I navigate to list reports
        
    Scenario: I can run and export a report
        When I click on the "Expand Subsections" link
        # first click doesn't show anything, but changes to "collapse"
        When I click on the "Expand Subsections" link
        When I click on the "Run" link
        Then I should see a "heading" labelled "SR10: Multi-Year Line Graph of Disease Cases"
        When I click the "Run" button
        Then I should see "The Condition Code is required." validation error
        When I select value "AIDS" in the "Condition Code" field
        When I click the "Run" button
        Then I should see a loading indicator
        Then I should see a "heading" labelled "Your report has opened in a new tab."
        When I click the "Refine report" button
        Then I should see a "heading" labelled "SR10: Multi-Year Line Graph of Disease Cases"
        When I select value "Anthrax" in the "Condition Code" field
        When I click the "Export" button
        Then I should see a loading indicator
        Then I should see a "heading" labelled "Your report has downloaded."

    Scenario: I can run a report for library nbs_sr_02
        When I navigate to "Public" report with reportUid: 1 and dataSourceUid: 1
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario Outline: I can run a report for library with condition code
        When I navigate to "Public" report with reportUid: <reportUid> and dataSourceUid: <dataSourceUid>
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        Examples:
            | name       | reportUid | dataSourceUid |
            | sr_05      | 2         | 1             |
            | sr_07      | 3         | 1             |
            | sr_10      | 6         | 1             |
            | sr_11      | 7         | 1             |
            | sr_13      | 23        | 1             |

    Scenario Outline: I can run a report for library with date range and condition code
        When I navigate to "Public" report with reportUid: <reportUid> and dataSourceUid: <dataSourceUid>
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        Examples:
            | name       | reportUid | dataSourceUid |
            | sr_08      | 4         | 1             |
            | sr_09      | 5         | 1             |

    Scenario: I can run a report for library nbs_sr_12
        When I navigate to "Public" report with reportUid: 8 and dataSourceUid: 1
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario Outline: I can run a report for library <library>
        When I navigate to "Public" report with reportUid: <reportUid> and dataSourceUid: <dataSourceUid>
        And I enter From Month: "1" and From Year: "2025"
        And I enter To Month: "12" and To Year: "2025"
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        Examples:
            | library   | reportUid | dataSourceUid |
            | nbs_sr_19 | 10066724  | 15            |
            | nbs_sr_20 | 10066726  | 15            |

    Scenario: I can run a report for library nbs_custom
        When I navigate to "Template" report with reportUid: 10066768 and dataSourceUid: 30
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select the column "AST Result"
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario: I can run a report for library pa_05 (case close date)
        When I navigate to "Public" report with reportUid: 10066745 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "950 - AIDS" from the "DIAGNOSIS_CD" dropdown menu
        And I select "Fulton LocalUser" from the "INVESTIGATOR_INTERVIEW_QC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario: I can run a report for library pa_05 (interview assign date)
        When I navigate to "Public" report with reportUid: 10066744 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "950 - AIDS" from the "DIAGNOSIS_CD" dropdown menu
        And I select "Fulton LocalUser" from the "INVESTIGATOR_INTERVIEW_QC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."


    Scenario: I can run a report for library qa_01
        When I navigate to "Public" report with reportUid: 10066751 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario Outline: I can run a report for library with date range and diagnosis
        When I navigate to "Public" report with reportUid: <reportUid> and dataSourceUid: <dataSourceUid>
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "100 - Chancroid" from the "DIAGNOSIS_CD" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        Examples:
            | name       | reportUid | dataSourceUid |
            | qa_03      | 10066753  | 23            |
            | qa_04      | 10066754  | 23            |
            | qa_06      | 10066755  | 23            |
            | qa_07 (30) | 10066756  | 23            |
            | qa_07 (60) | 10066757  | 23            |
            | qa_07 (90) | 10066758  | 23            |
            | qa_10      | 10066759  | 23            |
            | pa_03      | 10066749  | 23            |

    Scenario: I can run a report for library qa_05
        When I navigate to "Public" report with reportUid: 10066762 and dataSourceUid: 25
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "PKS PKS" from the "Provider QEC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario: I can run a report for library potntl_dup_inv_sum
        When I navigate to "Template" report with reportUid: 10066763 and dataSourceUid: 22
        And I type 999 into the "Duplicate Investigations Time Frame" field
        And I select "Anthrax" from the "Disease Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."


    Scenario: I can run a report for library pa_01 (HIV, Closed Date)
        When I navigate to "Public" report with reportUid: 10066735 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "950 - AIDS" from the "DIAGNOSIS_CD" dropdown menu
        And I select "Fulton LocalUser" from the "INVESTIGATOR_INTERVIEW_QC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."


    Scenario: I can run a report for library pa_01 (STD, Closed Date)
        When I navigate to "Public" report with reportUid: 10066733 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "100 - Chancroid" from the "DIAGNOSIS_CD" dropdown menu
        And I select "Fulton LocalUser" from the "INVESTIGATOR_INTERVIEW_QC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."


    Scenario: I can run a report for library pa_01 (HIV, Interview Assign Date)
        When I navigate to "Public" report with reportUid: 10066734 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "950 - AIDS" from the "DIAGNOSIS_CD" dropdown menu
        And I select "Fulton LocalUser" from the "INVESTIGATOR_INTERVIEW_QC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."


    Scenario: I can run a report for library pa_01 (STD, Interview Assign Date)
        When I navigate to "Public" report with reportUid: 10066732 and dataSourceUid: 23
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "100 - Chancroid" from the "DIAGNOSIS_CD" dropdown menu
        And I select "Fulton LocalUser" from the "INVESTIGATOR_INTERVIEW_QC" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."
