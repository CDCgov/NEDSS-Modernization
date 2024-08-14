Feature: Page Builder - User can view existing question library sorting here.

    Background:
        Given I am logged in as secure user
        When User navigates to Question Library and views the Question library

    Scenario: User list Type in descending and ascending order
        And User click the up or down arrow in the "Type" column
        Then In "Type" column "types" are listed in descending order
        And User click the up or down arrow in the "Type" column again
        Then In "Type" column "types" are listed in ascending order

    Scenario: User list Unique ID in descending and ascending order
        And User click the up or down arrow in the "Unique ID" column
        Then In "Unique ID" column "IDs" are listed in descending order
        And User click the up or down arrow in the "Unique ID" column again
        Then In "Unique ID" column "IDs" are listed in ascending order

    Scenario: User list Label in descending and ascending order
        And User click the up or down arrow in the "Label" column
        Then In "Label" column "labels" are listed in descending order
        And User click the up or down arrow in the "Type" column again
        Then In "Label" column "labels" are listed in ascending order

    Scenario: User list Subgroup in descending and ascending order
        And User click the up or down arrow in the "Subgroup" column
        Then In "Subgroup" column "subgroups" are listed in descending order
        And User click the up or down arrow in the "Type" column again
        Then In "Subgroup" column "subgroups" are listed in ascending order
