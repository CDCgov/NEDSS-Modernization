@skip-if-disabled-is-int
Feature: Page Builder - User can view existing question library here.

  Background:
    Given I am logged in as secure user
    When User navigates to Question Library and views the Question library

  Scenario: Add new question for published with draft status page
    Then Click on Create New button
    And select LOCAL as your option
    Then enter unique id if not system will autogenerate it own
    Then enter unique name
    Then select a subgroup Clinical Information or any from dropdown
    Then enter Description as same as unique name or any
    Then select field type as Value Set
    Then verify you have more set of questions available
    Then select a value set Assigning Authority from drop down or click on search value set
    Then select a default value Centers for Disease Control and Prevention
    Then enter Question Label
    Then enter Tool Tip
    Then select Display Type as Single or multiple select or code lookup from dropdown
    Then enter default label in report
    Then enter RDB column name as RDB_DBO.Investigation
    Then verify Data mart column name auto populates same as RDB column name 'RDB_DBO.Investigation
    Then toggle messaging to included
    Then enter message variable id as AA12345
    Then enter message label as Assigning Authority
    Then select Code system name as Entity Code from dropdown
    Then toggle to required in message
    Then select HL7 data type from dropdown as CE or any
    And verify next 2 sections are disabled and has default value as OBX-3.0 and Group 2
    Then enter Administrative comments test
    Then verify Create and apply to page button is enabled
    When user clicks on create and apply to page button
    Then verify user sees success message and question is added with all the selections made by the user on Edit draft page
    Then navigate to page library page
    And verify question added is in the list by searching the unique id
