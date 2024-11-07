Feature: View Open Investigation

Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Accessing and viewing an Open Investigation
    When I click on "Open Investigation" in the menu bar
    Then I should land on the "Open Investigation Queue" page
    Then I click and view an Investigation
