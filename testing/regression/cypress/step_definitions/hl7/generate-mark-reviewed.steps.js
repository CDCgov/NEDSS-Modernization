import { When, Then, attach, Given } from "@badeball/cypress-cucumber-preprocessor";
import { searchPage } from "cypress/e2e/pages/search.page";
import { faker } from "@faker-js/faker";
import 'cypress-xpath';

// Utility functions
const getRandomLetter = () => {
  const letters = 'abcdefghijklmnopqrstuvwxyz';
  const randomIndex = Math.floor(Math.random() * letters.length);
  return letters[randomIndex];
};

const generateRandomSSN = () => Array(9).fill().map(() => faker.number.int(9)).join('');
const generateTimestamp = () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  return `${year}${month}${day}${hours}${minutes}`;
};

const capitalizeFirstLetter = (string) => string.charAt(0).toUpperCase() + string.slice(1);

const generateRandomLastName = () => {
  let randomLastName = faker.person.lastName().toLowerCase() + getRandomLetter() + getRandomLetter() + getRandomLetter() + getRandomLetter() + getRandomLetter();
  randomLastName = capitalizeFirstLetter(randomLastName).replace(/[^0-9a-z]/gi, '');
  return randomLastName;
};

const formatSSN = (ssn) => {
  const ssnString = ssn.toString();
  return `${ssnString.slice(0, 3)}-${ssnString.slice(3, 5)}-${ssnString.slice(5)}`;
};

const replacePlaceholders = (message, replacements) => {
  return Object.entries(replacements).reduce((modifiedMessage, [placeholder, replacement]) => {
    return modifiedMessage.replaceAll(placeholder, replacement);
  }, message);
};

// Main function
When("I Generate HL7 messages to api and mark as review", () => {
  let currentMessage;
  let authToken;
  let messageID;
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
    Cypress.env("authToken", authToken);          

    cy.readFile('cypress/fixtures/try.json', 'utf8').then(jsonData => {
      const formatHL7 = (hl7String) => {
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

      const randomData = {
        randomFirstName: faker.person.firstName(),
        randomLastName: generateRandomLastName(),
        fakeSSN: generateRandomSSN(),
        fakeEmail: faker.internet.email(),
        fakeStreetAddress: faker.address.streetAddress(),
        fakeState: faker.address.stateAbbr(),
        fakeCity: faker.address.city(),
        fakeBuildingNumber: faker.address.buildingNumber(),
        fakeDOB: `19${faker.number.int(9)}${faker.number.int(9)}`,
        faketimestamp: generateTimestamp()
      };

      const replacements = {
        'PawnlandFirstName': randomData.randomFirstName,
        'PawnlandLastName': randomData.randomLastName,
        'patient.email@example.com': randomData.fakeEmail,
        '900000011': randomData.fakeSSN,
        '1965': randomData.fakeDOB,
        'Joneshaven': randomData.fakeCity,
        '9315 Gonzalez Mountains': randomData.fakeStreetAddress,
        'unit 19028': 'unit ' + randomData.fakeBuildingNumber,
        'NY': randomData.fakeState,
        '202407111207': randomData.faketimestamp,
      };

      let modifiedmsg = jsonData[0].data;
      let modifiedData = replacePlaceholders(modifiedmsg, replacements);
      const formattedSSN = formatSSN(randomData.fakeSSN);

      cy.log(`${randomData.randomLastName}, ${randomData.randomFirstName} ${formattedSSN}`);
      currentMessage = formatHL7(modifiedData);
      Cypress.env("currentMessage", currentMessage);
      Cypress.env("fakeFormattedSSN", formattedSSN);
      Cypress.env("fakeDOB", randomData.fakeDOB);
      Cypress.env("fakeFullName", `${randomData.randomLastName}, ${randomData.randomFirstName}`);      
      Cypress.env("randomData", randomData);
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
        const checkStatusUrl = `${checkstatusurl}${messageID}`;

        const checkStatusRequest = () => {
          cy.request({
            method: "GET",
            url: checkStatusUrl,
            headers: {
              Authorization: `Bearer ${authToken}`,
              "clientid": clientid,
              "clientsecret": clientsecret
            }
          }).then((response) => {

              function createNotication() {
                cy.get("input[name=createInvestigation]").first().click();
                cy.get("select[name=ccd]").select("Hepatitis A, acute", { force: true });                
                cy.get("input[name=Submit]").first().click();                
                cy.get('#DEM196').invoke('text', 'Investigation is needed');
                cy.get("select[id=INV163]").select("Confirmed", { force: true });                
                cy.get("input[name=SubmitTop]").first().click();             
                cy.window().then( win => {
                    win.createNotifications('Comment');
                })                
              }

              function navigateAndSearchDocuments() {
                let fakeFullName = Cypress.env("fakeFullName");
                let fakeRandomData = Cypress.env("randomData");
                let fakeFormattedSSN = Cypress.env("fakeFormattedSSN");

                // Navigate to home
                cy.get("a").contains("Home").click();

                // Navigate to 'Documents Requiring Review'
                cy.contains('Documents Requiring Review').click();

                // Click the table header to sort
                cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/thead/tr/th[5]/img").click();

                // Search for the random last name
                cy.get("#SearchText1").type(fakeRandomData.randomLastName);
                cy.get("#b2SearchText1").first().click();

                // Click the first result
                cy.xpath("/html/body/div[2]/form/div/table[2]/tbody/tr/td/table/tbody/tr/td[2]/a").click();

                // Mark as reviewed and transfer ownership
                cy.get("input[name=markReviewd]").first().click();
                cy.get("input[name=TransferOwn]").first().click();
                cy.get("input[name=Submit]").first().click();

                // Return to 'Documents Requiring Review'
                cy.contains('Return to Documents Requiring Review').click();

                // Navigate back to home
                cy.contains('Home').click();

                // Advanced search
                cy.get('#homePageAdvancedSearch').click();
                cy.get('#lastName').type(fakeRandomData.randomLastName);
                cy.get('#firstName').type(fakeRandomData.randomFirstName);
                cy.get("#identificationType").select('Social Security');
                cy.get("input[name='identification']").type(fakeFormattedSSN);
                cy.get('button').contains("Search").click();
                cy.get('button').contains("List").click();
              }            
                          
              function ClickAdvancedSearch() { 
                let fakeFullName = Cypress.env("fakeFullName");
                let fakeRandomData = Cypress.env("randomData");
                let fakeFormattedSSN = Cypress.env("fakeFormattedSSN");    
                cy.contains('Home').click();

                // Advanced search
                cy.get('#homePageAdvancedSearch').click();
                cy.get('#lastName').type(fakeRandomData.randomLastName);
                cy.get('#firstName').type(fakeRandomData.randomFirstName);
                cy.get("#identificationType").select('Social Security');
                cy.get("input[name='identification']").type(fakeFormattedSSN);                            
                cy.get('button').contains("Search").click();
                cy.get('button').contains("List").click();
                cy.get("body").then($body => {
                let fakeFullName = Cypress.env("fakeFullName");
                let fakeRandomData = Cypress.env("randomData");
                let fakeFormattedSSN = Cypress.env("fakeFormattedSSN");    
                  if ($body.find("#legalName").length > 0) {
                    cy.get("a").contains(fakeFullName).then($button => {
                      if ($button.is(':visible')) {
                            cy.contains(fakeFormattedSSN).scrollIntoView().should("be.visible")
                            cy.get("a").contains(fakeFullName).scrollIntoView().click({ force: true });
                            cy.get("a").contains("Events").click({ force: true });                            
                            cy.get("#classic a").first().click();       
                            createNotication();
                            cy.get("#successMessages").contains("A Notification has been created for this Investigation.").scrollIntoView().should("be.visible");                             
                      }
                    });
                  } else {
                    cy.wait(20000).then(ClickAdvancedSearch);
                  }
                });                
              }
              
            cy.wait(2000);
            expect(response.status).to.eq(200);

            if (response.body.nbsInfo.nbsInterfaceStatus === "QUEUED" || response.body.nbsInfo.nbsInterfacePipeLineStatus === "IN PROGRESS") {
              cy.wait(20000).then(checkStatusRequest);
            } else if (response.body.nbsInfo.nbsInterfaceStatus === "Success" && response.body.nbsInfo.nbsInterfacePipeLineStatus === "COMPLETED") {              
                    navigateAndSearchDocuments();
                    cy.wait(2000);
                    cy.get("body").then($body => {
                      if ($body.find("#legalName").length > 0) {
                        cy.get("a").contains(fakeFullName).then($button => {
                          if ($button.is(':visible')) {
                                cy.contains(fakeFormattedSSN).scrollIntoView().should("be.visible")
                                cy.get("a").contains(fakeFullName).scrollIntoView().click({ force: true });
                                cy.get("a").contains("Events").click({ force: true });
                                cy.get("#classic a").first().click();   
                                createNotication();
                          }
                        });
                      } else {
                        cy.wait(20000).then(ClickAdvancedSearch);
                      }
                    });
            }
          });
        };
        checkStatusRequest();
      });
    });
  });
});
