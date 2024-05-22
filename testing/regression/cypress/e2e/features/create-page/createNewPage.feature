Feature: User can verify create new page here.

    Background:
        Given I am logged in as "superuser" and password ""
        When User navigates to Page Library and views the Page library

    Scenario: User accesses Add new page (from the Page Library)
        And User views the Create new page button
        When User clicks the Create new page button
        Then Add new page with the required field Event Type displays for user selection

    Scenario Outline: Select Investigation to display additional fields
        And User selects Event Type - Investigation
        Then Additional required fields and other information displays for user selection by "<Items required>" "<Type>"
        Examples:
            | Items required                               | Type                   |
            | Condition(s)                                 | Heading, drop-down box |
            | Can't find the condition you're looking for? | Text                   |
            | Create a new condition here                  | Link                   |
            | Page name                                    | Heading, text field    |
            | Event type                                   | Heading, drop-down box |
            | Template                                     | Heading, drop-down box |
            | Can't find the template you're looking for?  | Heading and Link       |
            | Import a new template here                   | Heading and Link       |
            | Page description                             | Heading, text fields   |
            | Data mart name                               | Heading, text fields   |
            | Cancel                                       | Buttons                |
            | Create page                                  | Buttons                |

    Scenario: Selecting Contact Record and clicking Create page button redirects to the classic design of Add page
        And User selects "Contact Record" from Event Type "CON"
        Then Rectangular yellow box appears with the message: "CONTACT RECORD event type is not supported by the modern page design. Please click \"Create page\" to continue in classic design mode."
        When User clicks Create page button
        Then Application redirects to the classic design of Add page to continue

    Scenario: Selecting Interview and clicking Create page button redirects to the classic design of Add page
        And User selects "Interview" from Event Type "IXS"
        Then Rectangular yellow box appears with the message: "INTERVIEW event type is not supported by the modern page design. Please click \"Create page\" to continue in classic design mode."
        When User clicks Create page button
        Then Application redirects to the classic design of Add page to continue

    Scenario: Selecting Lab Isolate Tracking and clicking Create page button redirects to the classic design of Add page
        And User selects "Lab Isolate Tracking" from Event Type "ISO"
        Then Rectangular yellow box appears with the message: "LAB ISOLATE TRACKING event type is not supported by the modern page design. Please click \"Create page\" to continue in classic design mode."
        When User clicks Create page button
        Then Application redirects to the classic design of Add page to continue

    Scenario: Selecting Lab Report and clicking Create page button redirects to the classic design of Add page
        And User selects "Lab Report" from Event Type "LAB"
        Then Rectangular yellow box appears with the message: "LAB REPORT event type is not supported by the modern page design. Please click \"Create page\" to continue in classic design mode."
        When User clicks Create page button
        Then Application redirects to the classic design of Add page to continue
