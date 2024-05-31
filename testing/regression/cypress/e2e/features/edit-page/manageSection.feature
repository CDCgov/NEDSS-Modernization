Feature: Page Builder - User can verify manage page here.

  Background:
    Given I am logged in as "superuser" and password ""
    When User navigates to Edit page and views Manage section pop-up window

  Scenario Outline: Manage sections modal to display key elements
    Then User will see the following by "<Content>" "<Type>" "<Description>"
    Examples:
      | Content         | Type    | Description                      |
      | Manage sections | title   | main title                       |
      | Add new section | button  | to add new section               |
      | Patient         | heading | tab heading                      |
      | Six dots        | icon    | with other available sections    |
      | Pencil          | icon    | for editing                      |
      | Trash can       | icon    | for deleting a section           |
      | Cross-eye       | icon    | for not visible / visible        |
      | Close           | button  | to close the pop-up modal window |
