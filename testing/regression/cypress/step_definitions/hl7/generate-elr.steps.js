import { When, Then, attach, Given } from "@badeball/cypress-cucumber-preprocessor";
import { faker } from "@faker-js/faker";
import 'cypress-wait-until';
import '@cypress/xpath';

When("I Generate a HL7 messages", () => {
  let formattedMessages
  let hl7Messages;
  let hl7Message;
  let authToken;
  let messageID;
  cy.request({
    method: "POST",
    url: "https://dataingestion.int1.nbspreview.com/api/auth/token",
    headers: {
      "Content-Type": "text/plain",      
      "clientid": Cypress.env('clientid'),
      "clientsecret": Cypress.env('clientsecret')
    }
  }).then((response) => {
    expect(response.status).to.eq(200);
    attach(`Response: ${JSON.stringify(response.body)}`);
    authToken = response.body;
    cy.wrap(authToken).as("authToken");
    cy.log("Stored Token:", authToken);
    Cypress.env("authToken", authToken);
          
    cy.readFile('cypress/fixtures/try.json', 'utf8').then(jsonData => {  

      formattedMessages = jsonData;            
      hl7Messages = jsonData;      
      const randomFirstName = faker.person.firstName();
      const randomLastName = faker.person.lastName();    
      // Modify the HL7 message
      const modifiedmsg = hl7Messages[0].data.replace('LinkLogic', randomFirstName);
      const modifiedData = modifiedmsg.replace('datateam', randomLastName);      
      console.log(randomFirstName, randomLastName);
      cy.request({
        method: "POST",
        url: "https://dataingestion.int1.nbspreview.com/api/elrs",
        headers: {
          "Content-Type": "text/plain",
          Authorization: `Bearer ${authToken}`,
          "clientid": Cypress.env('clientid'),
          "clientsecret": Cypress.env('clientsecret')
          "msgType": 'HL7'
        },
        body: modifiedData
      }).then((response) => {        
          messageID = response.body;          
          let checkStatusUrl = "https://dataingestion.int1.nbspreview.com/api/elrs/status-details/" + messageID;                    
        cy.request({
          method: "GET",
          url: checkStatusUrl,
          headers: {            
            Authorization: `Bearer ${authToken}`,
            "clientid": Cypress.env('clientid'),
            "clientsecret": Cypress.env('clientsecret')
          }
        }).then((response) => {
            expect(response.status).to.eq(200);
            const NBSresponse = response.body.nbsInfo.nbsInterfaceStatus || 'N/A';
            const NBSerrorresponse = response.body.error_message || 'N/A';
            const handleFailureOrQueued = NBSresponse === 'Failure' || NBSresponse === 'QUEUED';
            const Success = NBSresponse === 'Success';
            const isNotSuccess = NBSerrorresponse === 'Provided UUID is not present in the database. Either provided an invalid UUID or the injected message failed validation.';
            const nbsInterfaceId = response.body.nbsInfo.nbsInterfaceStatus;
        });
      });
    });
  });
});
