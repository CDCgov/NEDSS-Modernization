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
  let NBSresponse;
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


      function capitalizeFirstLetter(string) {
          return string.charAt(0).toUpperCase() + string.slice(1);
      }

      formattedMessages = jsonData;
      hl7Messages = jsonData;
      let randomWord1 = faker.random.word().toLowerCase();
      let randomWord2 = faker.random.word().toLowerCase();
      let randomWord3 = faker.random.word().toLowerCase();
      let randomWord4 = faker.random.word().toLowerCase();
      let randomWord5 = faker.random.word().toLowerCase();

      let randomFirstName = faker.person.firstName();
      let randomLastName =  faker.person.lastName().toLowerCase() + randomWord4[0] + randomWord1[0] + randomWord2[0] + randomWord3[0] + randomWord5[1];
      randomLastName = capitalizeFirstLetter(randomLastName);
      randomLastName = randomLastName.replace(/[^0-9a-z]/gi, '');
      
      // Modify the HL7 message
      cy.log(randomLastName);
      expect(randomLastName).to.eq(randomLastName);
      
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

      let modifiedmsg = hl7Messages[0].data.replaceAll('PawnlandFirstName', randomFirstName);
      let modifiedData = modifiedmsg.replaceAll('PawnlandLastName', randomLastName);
      let modifiedData2 = modifiedData.replaceAll('patient.email@example.com', fakeEmail);
      let modifiedData3 = modifiedData2.replaceAll('900000011', fakeSSN);
      let modifiedData4 = modifiedData3.replaceAll('1965', fakeDOB);
      let modifiedData5 = modifiedData4.replaceAll('Joneshaven', fakeCity);      
      let modifiedData6 = modifiedData5.replaceAll('9315 Gonzalez Mountains', fakeStreetAddress);
      let modifiedData7 = modifiedData6.replaceAll('unit 19028', 'unit ' + fakeBuildingNumber);    
      let modifiedData8 = modifiedData7.replaceAll('NY', fakeState);
      let modifiedData9 = modifiedData8.replaceAll('202407111207', faketimestamp);

      function formatSSN(ssnwwe) {
        // Ensure the input is a string
        const ssnString = ssnwwe.toString();

        // Extract parts of the SSN
        const part1 = ssnString.slice(0, 3);
        const part2 = ssnString.slice(3, 5);
        const part3 = ssnString.slice(5, 9);

        // Combine parts with dashes
        const formattedSSN = `${part1}-${part2}-${part3}`;

        return formattedSSN;
      }

      const formattedSSN = formatSSN(fakeSSN);
      expect("SSN:" + formattedSSN).to.eq("SSN:" + formattedSSN);
      expect("Street Address:" + fakeStreetAddress).to.eq("Street Address:" +  fakeStreetAddress);
      cy.log("SSN:" + formattedSSN);
      cy.log("EMAIL:" + fakeEmail);
      cy.log("DOB:" + fakeDOB);
      cy.log(randomLastName + ", " + randomFirstName);

      currentMessage = formatHL7(modifiedData9);
      Cypress.env("currentMessage", currentMessage);
      Cypress.env("fakeSSN", formattedSSN);
      Cypress.env("fakeDOB", fakeDOB);
      Cypress.env("fakeFullName", randomLastName + ", " + randomFirstName);
      
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
          
          function checkStatusRequest() {
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
                if (response.body.nbsInfo.nbsInterfaceStatus === "QUEUED" || response.body.nbsInfo.nbsInterfacePipeLineStatus === "IN PROGRESS") {                  
                  NBSresponse = response.body;                  
                  cy.log(NBSresponse.nbsInfo.nbsInterfaceStatus);
                  cy.wait(20000);
                  checkStatusRequest();   
                } else if(response.body.nbsInfo.nbsInterfaceStatus === "Success" && response.body.nbsInfo.nbsInterfacePipeLineStatus === "COMPLETED") {
                  let fakeSSN = Cypress.env().fakeSSN;
                  let fakeFullName = Cypress.env().fakeFullName;
                  cy.wait(1000);    
                  cy.get("a").contains("Home").click();
                  cy.wait(500);
                  // cy.visit("https://app.int1.nbspreview.com/nbs/HomePage.do?method=loadHomePage");
                  
                  // Navigate to Documents Requiring Review
                  cy.contains('Documents Requiring Review').click();
                  
                  cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/thead/tr/th[5]/img").click();
                  cy.get("#SearchText1").type(randomLastName);
                  cy.get("#b2SearchText1").first().click();
                  cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/tbody/tr/td[2]/a").click();

                  cy.get("input[name=markReviewd]").first().click();
                  cy.get("input[name=TransferOwn]").first().click();
                  cy.get("input[name=Submit]").first().click();
                  cy.wait(1000);
                  cy.contains('Return to Documents Requiring Review').click();
                  cy.wait(1000);
                  cy.contains('Home').click();
                  cy.wait(1000);
                  cy.get('#homePageAdvancedSearch').click();
                  cy.get('#lastName').type(randomLastName);
                  cy.get('#firstName').type(randomFirstName);

                  cy.get("#identificationType").select('Social Security');
                  cy.get("input[name='identification']").type(formattedSSN);
                  cy.get('button').contains("Search").click();
                  cy.get('button').contains("List").click();

                  cy.contains(fakeSSN).scrollIntoView().should("be.visible");
                  cy.wait(1000);
                  cy.get("a").contains(fakeFullName).scrollIntoView().click({force: true});
                  cy.get("a").contains("Events").click({force: true});
                  cy.get("td").contains("Fulton County").scrollIntoView().should("be.visible");
                  cy.get("td").contains("HEP").scrollIntoView().should("be.visible");
                  cy.get("#classic a").first().click();                  
                }
              })
          }
          checkStatusRequest();
      });
    });
  });
});
