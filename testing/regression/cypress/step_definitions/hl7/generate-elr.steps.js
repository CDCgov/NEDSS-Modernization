import { When, Then, attach, Given } from "@badeball/cypress-cucumber-preprocessor";
import 'cypress-xpath';
import UtilityFunctions from "@pages/utilityFunctions.page";
import { faker } from "@faker-js/faker";

Given("I login for HL7 API generate token", () => {
  const clientid = Cypress.env("clientid");
  const clientsecret = Cypress.env("clientsecret");
  const authurl = Cypress.env("authurl");

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
    const authTokenReponse = response.body;
    cy.wrap(authTokenReponse).as("authTokenAPI");
    Cypress.env("authTokenAPI", authTokenReponse);
  });
});

When("I Generate HL7 {string} messages to api", (string) => {  
  let messageCondition = string;
  let fakeFullName;  
  let currentMessage;  
  let messageID;
  let NBSresponse;
  let fakeFormattedSSN;
  let fakeRandomData;
  const authToken = Cypress.env("authTokenAPI");
  const clientid = Cypress.env("clientid");
  const clientsecret = Cypress.env("clientsecret");
  const apiurl = Cypress.env("apiurl");
  const checkstatusurl = Cypress.env("checkstatusurl");
  const authurl = Cypress.env("authurl");

    cy.readFile('cypress/fixtures/hepb.json', 'utf8').then(jsonData => {

      const randomData = {
        randomFirstName: faker.person.firstName(),
        randomLastName: UtilityFunctions.generateRandomLastName(),
        fakeSSN: UtilityFunctions.generateRandomSSN(),
        fakeEmail: faker.internet.email(),
        fakeStreetAddress: faker.address.streetAddress(),
        fakeState: faker.address.stateAbbr(),
        fakeCity: faker.address.city(),
        fakeBuildingNumber: faker.address.buildingNumber(),
        fakeDOB: `19${faker.number.int(9)}${faker.number.int(9)}`,
        faketimestamp: UtilityFunctions.generateTimestamp()
      };

      const replacements = {
        'PawnlandFirstName': randomData.randomFirstName,
        'PawnlandLastName': randomData.randomLastName,
        // 'patient.email@example.com': randomData.fakeEmail,
        '888888888': randomData.fakeSSN,
        '1965': randomData.fakeDOB,
        'Joneshaven': randomData.fakeCity,
        '9315 Gonzalez Mountains': randomData.fakeStreetAddress,
        'unit 19028': 'unit ' + randomData.fakeBuildingNumber,
        'NY': randomData.fakeState,
        'UUIDTIMESTAMP': randomData.faketimestamp,
      };

      let modifiedmsg = jsonData[0].data;
      let modifiedData = UtilityFunctions.replacePlaceholders(modifiedmsg, replacements);
      const formattedSSN = UtilityFunctions.formatSSN(randomData.fakeSSN);            
      cy.log(`${randomData.randomLastName}, ${randomData.randomFirstName} ${formattedSSN}`);
      fakeFormattedSSN = formattedSSN;
      currentMessage = UtilityFunctions.formatHL7(modifiedData);
      fakeFullName = `${randomData.randomLastName}, ${randomData.randomFirstName}`;
      fakeRandomData = randomData;
      Cypress.env("currentMessage", currentMessage);      
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
            cy.wait(2000);
            expect(response.status).to.eq(200);

            if (response.body.nbsInfo.nbsInterfaceStatus === "QUEUED" || response.body.nbsInfo.nbsInterfacePipeLineStatus === "IN PROGRESS") {
              cy.wait(20000).then(checkStatusRequest);
            } else if (response.body.nbsInfo.nbsInterfaceStatus === "Success" && response.body.nbsInfo.nbsInterfacePipeLineStatus === "COMPLETED") {              
              UtilityFunctions.checkELRActivityLog(fakeRandomData);
            } else if (response.body.validatedInfo.validatedPipeLineStatus === "FAILED") {              
              expect(response.status).to.eq(424);
            } else {              
              checkStatusRequest();
            }
          });
        };
        checkStatusRequest();
      });
    });
});
