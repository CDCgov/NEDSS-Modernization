import { When, Then, attach, Given } from "@badeball/cypress-cucumber-preprocessor";
import { searchPage } from "cypress/e2e/pages/search.page";
import { faker } from "@faker-js/faker";
import '@cypress/xpath';

When("I Generate HL7 messages to api and mark as review", () => {
  let formattedMessages
  let hl7Messages;
  let hl7Message;
  let authToken;
  let messageID;
  const clientid = Cypress.env()["env"].clientid;
  const clientsecret = Cypress.env()["env"].clientsecret;
  const apiurl = Cypress.env()["env"].apiurl;
  const checkstatusurl = Cypress.env()["env"].checkstatusurl;
  const authurl = Cypress.env()["env"].authurl;

  cy.request({
    method: "POST",
    url: authurl,
    headers: {
      "Content-Type": "text/plain",      
      "clientid": clientid,
      "clientsecret": clientsecret
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
        url: apiurl,
        headers: {
          "Content-Type": "text/plain",
          Authorization: `Bearer ${authToken}`,
         "clientid": clientid,
         "clientsecret": clientsecret,
          "msgType": 'HL7'
        },
        body: modifiedData
      }).then((response) => {        
          messageID = response.body;          
          let checkStatusUrl = checkstatusurl + messageID;
        cy.request({
          method: "GET",
          url: checkStatusUrl,
          headers: {            
            Authorization: `Bearer ${authToken}`,
            "clientid": clientid,
           "clientsecret": clientsecret
          }
        }).then((response) => {
            expect(response.status).to.eq(200);
            const NBSresponse = response.body.nbsInfo.nbsInterfaceStatus || 'N/A';
            const NBSerrorresponse = response.body.error_message || 'N/A';
            const handleFailureOrQueued = NBSresponse === 'Failure' || NBSresponse === 'QUEUED';
            const Success = NBSresponse === 'Success';
            const isNotSuccess = NBSerrorresponse === 'Provided UUID is not present in the database. Either provided an invalid UUID or the injected message failed validation.';
            const nbsInterfaceId = response.body.nbsInfo.nbsInterfaceStatus;            
            cy.visit("https://app.int1.nbspreview.com/nbs/HomePage.do?method=loadHomePage");
            
            cy.wait(110000);
            // Navigate to Documents Requiring Review
            cy.contains('Documents Requiring Review').click();            
            

            cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/thead/tr/th[5]/img").click();
         
            cy.get("#SearchText1").type(randomLastName);
            cy.get("#b2SearchText1").click();
            
            
            cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/tbody/tr/td[2]/a").click();            
            

            // Finalize the test
            cy.get("input[name=markReviewd]").first().click();
            // cy.get('#successMessages').should('have.text', 'The Lab Report has been successfully marked as Reviewed.');
            cy.contains('Return to Documents Requiring Review').click();
            cy.contains('Home').click();            

            // Search and validate final name
            cy.get('#DEM104').type(randomFirstName);
            cy.screenshot();
            cy.get('#DEM102').type(randomLastName);
            cy.screenshot();
            cy.wait(20000); // Wait for processing
            cy.get('tr:nth-child(8) input:nth-child(1)').click();

            cy.window().then(win => {                   
              win.close(); // Close the driver/browser
            });
        });
      });
    });
  });
});