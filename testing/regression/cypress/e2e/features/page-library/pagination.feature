Feature: Page Builder - User can view existing page library pagination here.

    Background:
        Given I am logged in as secure user
        When User navigates to Page Library and views the Page library

    Scenario: User checks for 10 rows of pages listed in the library
        Then User should see by default 10 rows of pages listed in the library

    Scenario: User selects for 20 rows to show in the Page library
        And User select "20" left footer of the page to show the list of pages
        Then User should see only "20" rows in the library and for each subsequent list where applicable

    Scenario: Verify the selection of 20 is displaying 10 rows after redirect back to Page Library
        And User select "20" left footer of the page to show the list of pages
        Then User navigates to the Create page
        And User navigates to Page Library and views the Page library
        Then User should see only "10" rows in the library and for each subsequent list where applicable

    Scenario: User selects for 30 rows to show in the Page library
        And User select "30" left footer of the page to show the list of pages
        Then User should see only "30" rows in the library and for each subsequent list where applicable

    Scenario: Verify the selection of 30 is displaying 10 rows after redirect back to Page Library
        And User select "30" left footer of the page to show the list of pages
        Then User navigates to the Create page
        And User navigates to Page Library and views the Page library
        Then User should see only "10" rows in the library and for each subsequent list where applicable

    Scenario: User selects for 50 rows to show in the Page library
        And User select "50" left footer of the page to show the list of pages
        Then User should see only "50" rows in the library and for each subsequent list where applicable

    Scenario: Verify the selection of 50 is displaying 10 rows after redirect back to Page Library
        And User select "50" left footer of the page to show the list of pages
        Then User navigates to the Create page
        And User navigates to Page Library and views the Page library
        Then User should see only "10" rows in the library and for each subsequent list where applicable

    Scenario: User selects for 100 rows to show in the Page library
        And User select "100" left footer of the page to show the list of pages
        Then User should see only "100" rows in the library and for each subsequent list where applicable

    Scenario: Verify the selection of 100 is displaying 10 rows after redirect back to Page Library
        And User select "100" left footer of the page to show the list of pages
        Then User navigates to the Create page
        And User navigates to Page Library and views the Page library
        Then User should see only "10" rows in the library and for each subsequent list where applicable

    Scenario: User advances to the next page/pages in the library
        And User click 2 in the pagination section bottom right
        Then User should see the subsequent rows listed in the library for the number of rows selected and Same results when paginating pages 3, 4, and 5

    Scenario: User advances to the next list of pages in the library using the Next (link)
        And User click the Next link bottom right
        Then User should see the subsequent row of pages listed in the library
        When User click the Next link bottom right
        Then User should see the next list in sequence
