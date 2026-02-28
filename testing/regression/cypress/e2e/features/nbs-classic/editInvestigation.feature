Feature: EDit Open Investigation

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Accessing and editing and Open Investigation
    When Click on Open Investigation in the main menu bar
    Then Should land on the Open Investigation Queue page
    Then Click and view an Investigation
    Then Click Edit button in Open Investigation
    Then Edit info in the form in Open Investigation
    Then Click submit on edit page in Open Investigation
