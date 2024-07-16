import { When, Then, attach, Given } from "@badeball/cypress-cucumber-preprocessor";
import { searchPage } from "cypress/e2e/pages/search.page";
import { faker } from "@faker-js/faker";
import 'cypress-xpath';


When("I Generate HL7 messages to api and mark as review", () => {
  let currentMessage;
  let formattedMessages;
  let hl7Messages;
  let hl7Message;
  let authToken;
  let messageID;
  let formattedArray;

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

      const formatHL7 = function(hl7String) {
          const formattedFields = [];
          const segments = hl7String.split(/\r\n|\r|\n/);
          
          segments.forEach(segment => {
              const fields = segment.split('|');
              
              for (let i = 1; i < fields.length; i++) {
                  const components = fields[i].split('^');
                  
                  if (components.length > 1) {
                      components.forEach((component, j) => {
                          formattedFields.push(`${fields[0]}.${i}.${j + 1} - ${component}`);
                      });
                  } else {
                      formattedFields.push(`${fields[0]}.${i} - ${fields[i]}`);
                  }
              }
          });
          
          return formattedFields;
      };


      formattedMessages = jsonData;
      hl7Messages = jsonData;
      const randomFirstName = faker.person.firstName();
      const randomLastName = faker.person.lastName();
      // Modify the HL7 message      
      let ssn1 = faker.number.int(9).toString();
      let ssn2 = faker.number.int(9).toString();
      let ssn3 = faker.number.int(9).toString();
      let ssn4 = faker.number.int(9).toString();
      let ssn5 = faker.number.int(9).toString();
      let ssn6 = faker.number.int(9).toString();
      let ssn7 = faker.number.int(9).toString();
      let ssn8 = faker.number.int(9).toString();
      let ssn9 = faker.number.int(9).toString();
      let fakeSSN = ssn1 + ssn2 + ssn3 + ssn4 + ssn5 + ssn6 + ssn7 + ssn8 + ssn9;
      let fakeEmail = faker.internet.email();
      let fakeStreetAddress = faker.address.streetAddress();
      let fakeState = faker.address.stateAbbr();
      let fakeCity = faker.address.city();
      let fakeBuildingNumber = faker.address.buildingNumber();
      let fakeDOB = "19" + faker.number.int(9).toString() + faker.number.int(9).toString();      

      const now = new Date();      
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');      
      const faketimestamp = `${year}${month}${day}${hours}${minutes}`;

      let modifiedmsg = hl7Messages[0].data.replaceAll('Lisa', randomFirstName);
      let modifiedData = modifiedmsg.replaceAll('Guerra', randomLastName);
      let modifiedData2 = modifiedData.replaceAll('LisaGuerra46@hotmail.com', fakeEmail);
      let modifiedData3 = modifiedData2.replaceAll('900000011', fakeSSN);
      let modifiedData4 = modifiedData3.replaceAll('1965', fakeDOB);
      let modifiedData5 = modifiedData4.replaceAll('Joneshaven', fakeCity);      
      let modifiedData6 = modifiedData5.replaceAll('9315 Gonzalez Mountains', fakeStreetAddress);
      let modifiedData7 = modifiedData6.replaceAll('unit 19028', 'unit ' + fakeBuildingNumber);    
      let modifiedData8 = modifiedData7.replaceAll('NY', fakeState);
      let modifiedData9 = modifiedData8.replaceAll('202407111207', faketimestamp);

      currentMessage = formatHL7(modifiedData9);
      Cypress.env("currentMessage", currentMessage);
      Cypress.env("fakeSSN", fakeSSN);
      Cypress.env("fakeDOB", fakeDOB);
      Cypress.env("fakeFullName", randomFirstName + " " + randomLastName);
      
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
        body: modifiedData9
      }).then((response) => {        
          messageID = response.body;          
          let checkStatusUrl = checkstatusurl + messageID;
          cy.wait(115000);
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
            console.log(nbsInterfaceId);            
            console.log(NBSresponse);               
            cy.visit("https://app.int1.nbspreview.com/nbs/HomePage.do?method=loadHomePage");
            
            // Navigate to Documents Requiring Review
            cy.contains('Documents Requiring Review').click();            
            
            cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/thead/tr/th[5]/img").click();
         
            cy.get("#SearchText1").type(randomLastName);
            cy.get("#b2SearchText1").click();
            
            cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/tbody/tr/td[2]/a").click();            
            
            cy.get("input[name=markReviewd]").first().click();   
            cy.get("input[name=TransferOwn]").first().click();       
            cy.get("input[name=Submit]").first().click();       

            cy.contains('Return to Documents Requiring Review').click();
            cy.contains('Home').click();            
            // Search and validate final name
            cy.get('#DEM104').type(randomFirstName);
            
            cy.get('#DEM102').type(randomLastName);
            
            cy.wait(25000); // Wait for processing
            cy.get('tr:nth-child(8) input:nth-child(1)').click();

        });
      });
    });
  });
});
