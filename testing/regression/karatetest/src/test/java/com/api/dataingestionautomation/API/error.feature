Feature:test


  Background:
    * callonce read('common.feature')
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }

  @checkstatus @ignore
  Scenario: validate the error message of the posted HL7 messages in the DI system
    * header Authorization = 'Bearer ' + token
    * def newerrorurl = checkerrorurl + id
    * print newerrorurl
    * url newerrorurl
    When method get
    Then status 200
    * def errorresponse = response.errorStackTraceShort
    * print errorresponse