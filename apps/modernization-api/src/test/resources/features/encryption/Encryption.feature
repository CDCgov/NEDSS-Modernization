@parameter_encryption
Feature: Sensitive Information can be encrypted for use in URL query parameters

  Background: I am logged in
    Given I am logged in

  Scenario: Information can be encrypted and then decrypted
    Given I want to encrypt the payload
      """
      {
        "identifier": 839,
        "value": "value",
        "enabled": false
      }
      """
    When I encrypt the payload
    Then I receive an encrypted payload
    And I want to decrypt the encrypted payload
    And I decrypt the payload
    And I receive the decrypted payload
        """
      {
        "identifier": 839,
        "value": "value",
        "enabled": false
      }
      """

  Scenario: Invalid information cannot be encrypted
    Given I want to encrypt the payload "invalid payload"
    When I encrypt the payload
    Then I made a bad request

    Scenario: Invalid information cannot be decrypted
      Given I want to decrypt the payload "invalid payload"
      When I decrypt the payload
      Then I made a bad request
