@parameter_encryption
Feature: An API exists to encrypt and decrypt query parameters

  Background: I am logged in
    Given A user exists
    And I have authenticated as a user

  @encrypt
  Scenario: I can encrypt an object
    Given I send a request to the encryption endpoint
    Then I receive an encrypted string

  @decrypt
  Scenario: I can decrypt an object
    Given I send a request to the encryption endpoint
    And I receive an encrypted string
    When I send a request to the decryption endpoint
    Then I receive the original object

  Scenario: I cannot encrypt if I am not logged in
    Given I have not authenticated as a user
    When I send a request to the encryption endpoint
    Then I get a 401 unauthorized response

  Scenario: I cannot decrypt if I am not logged in
    Given I have not authenticated as a user
    When I send a request to the decryption endpoint
    Then I get a 401 unauthorized response
