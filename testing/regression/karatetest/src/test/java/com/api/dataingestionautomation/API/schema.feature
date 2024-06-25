@parallel=false
Feature: post a hl7 message and validate if XML record is generated  for that record.

  Background:
    * header Content-Type = 'text/plain'
    * header msgType = 'HL7'
    * header validationActive = 'true'
    * callonce read('common.feature')
    * header Authorization = 'Bearer ' + token
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }
    * def Thread = Java.type('java.lang.Thread')
    * def oldfirstname = 'LinkLogic'
    * def oldlastname = 'datateam'
    * def DbUtils = Java.type('com.api.dataingestionautomation.API.DbUtils')
    * def FakerHelper = Java.type('com.api.dataingestionautomation.API.FakerHelper')
    * def randomFirstName = FakerHelper.getRandomFirstName()
    * def randomLastName = FakerHelper.getRandomLastName()
    * def XsdValidator = Java.type('com.api.dataingestionautomation.API.XsdValidator')

    * def pollForDbStatus =
"""
function() {
  var maxAttempts = 20;
  var interval = 20000;
  for (var i = 0; i < maxAttempts; i++) {
    var xmlPayloadUpdated = dba.readRows('select top 1 nbs_interface_uid, record_status_cd  from NBS_interface where  nbs_interface_uid = \'' + nbs_id + '\'');
    var finalstatusupdated = xmlPayloadUpdated[0].record_status_cd;
    if (finalstatusupdated == 'Success') {
      return finalstatusupdated;
    }
    karate.log('Waiting for status to be Success, attempt: ', i + 1);
    java.lang.Thread.sleep(interval);
  }
  throw new Error('Timed out waiting for status to be Success');
}
"""

  @endtoendsmoke
  Scenario Outline: post a hl7 message and validate if XML record is generated  for that record and also its validated against XSD
    * def hl7Message = data
    * def modifiedmsg = hl7Message.replace(oldfirstname, randomFirstName)
    * def modifiedData = modifiedmsg.replace(oldlastname, randomLastName)
    * url apiurl
    And request modifiedData
    When method POST
    Then status 200
    * def elr_raw_id = db.readRows('select id, payload from elr_raw where id = \'' + response + '\'')
    And retry until karate.sizeOf(elr_raw_id) > 0
    And match elr_raw_id[0].id == response
    And match elr_raw_id[0].payload == modifiedData
    * def elr_raw_validated_id = db.readRows('select raw_message_id, id, validated_message from elr_validated where raw_message_id = \'' + response + '\'')
    And retry until karate.sizeOf(elr_raw_validated_id) > 0
    And match elr_raw_validated_id[0].raw_message_id == response
    And eval Thread.sleep(1500)
    * def xmlPayload = dba.readRows('select top 1 nbs_interface_uid, payload, record_status_cd  from NBS_interface order by nbs_interface_uid desc')
    * def xml = xmlPayload[0].payload
    * def nbs_id = xmlPayload[0].nbs_interface_uid
    * print nbs_id
    * def comment = function(x){ var regex = /<!--\s*raw_message_id\s*=\s*(.*?)\s*-->/; var match = x.match(regex); return match ? match[1] : ''; }
    * def result = comment(xml)
    * print 'id: ', xmlPayload[0].nbs_interface_uid
    * print xml
    * print result
    * print elr_raw_validated_id[0].id
    * print elr_raw_validated_id[0].raw_message_id
    * print response
    * def xsdBytes = read('classpath:com/api/dataingestionautomation/API/PHDC.xsd')
    * def xsd = new java.lang.String(xsdBytes)
    And match elr_raw_validated_id[0].id == result
    * def resultxsd = XsdValidator.validateXmlWithXsd(xml, xsd)
    * match resultxsd == true
    * print resultxsd
    * print xsdBytes
    * print xsd
    * def finalstatusupdated = call pollForDbStatus {}
    * assert finalstatusupdated == 'Success'
    * print finalstatusupdated



    Examples:
      | read('dupdata.json') |