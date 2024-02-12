@login
  Feature: NBS Authentication Support

    Scenario: I am able to authenticate with an NBS username
      Given the user "Existing" "McExistface" exists as "existing"
      When I log in as "existing"
      Then the logged in user has the username "existing"
      And the logged in user has a token

      Scenario: I am unable to authenticate with an invalid NBS user
        When I log in as "non-existing"
        Then I am not allowed due to insufficient permissions
