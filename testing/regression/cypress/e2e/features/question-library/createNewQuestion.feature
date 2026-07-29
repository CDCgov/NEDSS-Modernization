@skip-if-disabled-is-int
Feature: Page Builder - User can view existing question library here.

  Background:
    Given I am logged in as secure user
    When User navigates to Question Library and views the Question library

  Scenario: Add new question for published with draft status page
    When Click on Create New button
    And select LOCAL as your option
    And enter unique id if not system will autogenerate it own
    And enter unique name
    And select a subgroup Clinical Information or any from dropdown
    And enter Description as same as unique name or any
    And select field type as Value Set
    And verify you have more set of questions available
    And select a value set Assigning Authority from drop down or click on search value set
    And select a default value Centers for Disease Control and Prevention
    And enter Question Label
    And enter Tool Tip
    And select Display Type as Single or multiple select or code lookup from dropdown
    And enter default label in report
    And enter RDB column name as RDB_DBO.Investigation
    And verify Data mart column name auto populates same as RDB column name 'RDB_DBO.Investigation
    And toggle messaging to included
    And enter message variable id as AA12345
    And enter message label as Assigning Authority
    And select Code system name as Entity Code from dropdown
    And toggle to required in message
    And elect HL7 data type from dropdown as CE or any
    Then verify next 2 sections are disabled and has default value as OBX-3.0 and Group 2
    When enter Administrative comments test
    Then verify Create and apply to page button is enabled
    When user clicks on create and apply to page button
    Then verify user sees success message and question is added with all the selections made by the user on Edit draft page
    When navigate to page library page
    Then verify question added is in the list by searching the unique id
