@parallel=true
Feature: Scenarios that can be reused by other features

  @common
  Scenario: Allow users to create a new token when correct header info is added
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }
    Given url tokenurl
    When method POST
    Then status 200
    * def token = response