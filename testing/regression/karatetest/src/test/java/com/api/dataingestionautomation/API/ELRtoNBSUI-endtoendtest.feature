@ValidateFeature
@parallel=false
Feature: Read various HL7 messages from JSON file and Post them using a Data Ingestion API

  Background:
    * callonce read('common.feature')
    * header Authorization = 'Bearer ' + token
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }
    * def Thread = Java.type('java.lang.Thread')
    * def config = karate.call('classpath:karate-config.js')
    * def finalmessages = []
    * def appendToFinalMessages = function(msg){ karate.get('finalmessages').push(msg); }

  Scenario: Read HL7 messages from JSON file and post each message into DI service
    * def messages = read('try.json')
    * def results = call read('@postMessage') messages
    * def responses = $results[*].response
    * print 'Initial UUID Responses:', responses
    * def transformedResponses = karate.map(responses, function(item){ return { ID: item.ID, modifiedData: item.modifiedData, 'NBS Response': item.NBSresponse }; })
    * print 'Transformed Responses:', karate.toJson(transformedResponses)
    * eval Thread.sleep(130000)
    * def statusResults = call read('@checkstatus') transformedResponses
    * karate.forEach(statusResults, function(item){ appendToFinalMessages(item.finalMessage); })
    * def transformStatusResults = read('classpath:transformStatusResults.js')
    * def finalMessages = transformStatusResults(statusResults)
    * print 'Final Messages:', finalMessages
    * def finalMessagesFormatted = finalMessages.map(function(msg){ return 'ID: ' + msg.ID + '\nModifiedData: ' + msg.modifiedData + '\nNBS Response: ' + msg['NBS Response']; }).join('\n\n')
    * print finalMessagesFormatted
    * karate.write(finalMessagesFormatted, 'output/finalmessagesFormatted.txt')


  @postMessage @ignore
  Scenario: Post HL7 messages into Data service
    * header Content-Type = 'text/plain'
    * header msgType = 'HL7'
    * url apiurl
    * def hl7Message = data
    * def modifiedData = data.replace('LinkLogic', randomFirstName).replace('datateam', randomLastName)
    * request modifiedData
    * method post
    * status 200
    * def ID = response
    * print randomFirstName
    * print randomLastName
    * def result = { 'ID': '#(ID)', 'modifiedData': '#(modifiedData)' }
    * print result
    * karate.set('response', result)


  @checkstatus @ignore
  Scenario: Validate the status of the posted HL7 messages
    * header Authorization = 'Bearer ' + token
    * def newurl = checkstatusurl + ID
    * url newurl
    * method get
    * status 200
    * def NBSresponse = response.status
    * def NBSresponse = response.status ? response.status : 'N/A'
    * def NBSerrorresponse = response.error_message ? response.error_message : 'N/A'
    * def handleFailureOrQueued = NBSresponse == 'Failure' || NBSresponse == 'QUEUED'
    * def Success = NBSresponse == 'Success'
    * def isNotSuccess = NBSerrorresponse == 'Provided UUID is not present in the database. Either provided an invalid UUID or the injected message failed validation.'
    * eval Thread.sleep(5000)
    * def finalMessage =  NBSresponse
    * def errorFeatureResult = isNotSuccess ? karate.call('error.feature', { id: ID }) : null
    * if (isNotSuccess) finalMessage += ', Test Case failed at DI validation'
    * if (errorFeatureResult != null) finalMessage += ', ' + errorFeatureResult.errorresponse.replace("null", "N/A")
    * if (handleFailureOrQueued) finalMessage += ', Test Case failed at NBS validation'
    * if (Success) finalMessage += ', Test Case Passed'
    * karate.set('finalMessage', finalMessage)
    * print randomFirstName
    * print randomLastName
    * call read('classpath:com/api/dataingestionautomation/API/ValidatedataonNBSscreens.feature') { firstName: '#(randomFirstName)', lastName: '#(randomLastName)' }