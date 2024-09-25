import {
  When,
  Then,
  attach,
  Given,
} from "@badeball/cypress-cucumber-preprocessor";
import "cypress-xpath";
import UtilityFunctions from "@pages/utilityFunctions.page";
import { faker } from "@faker-js/faker";

When("I Seed HL7 {string} messages to api", (string) => {
  
  let messageCondition = string;
  let fakeFullName;
  let currentMessage;
  let messageID;
  let NBSresponse;
  let fakeFormattedSSN;
  let fakeRandomData;
  const authToken = Cypress.env("authTokenAPI");
  const clientid = Cypress.env("DI_CLIENT_ID");
  const clientsecret = Cypress.env("DI_SECRET");
  const apiurl = Cypress.env("apiurl");
  const checkstatusurl = Cypress.env("checkstatusurl");
  const authurl = Cypress.env("authurl");
  cy.readFile("cypress/fixtures/hepbseedaddress.json", "utf8").then((jsonData) => {
    const randomData = {
      randomFirstName: "Caden",
      randomLastName: "Ratkeyklkb",
      fakeSSN: "123456789",    
      fakeDOB: "1977",
      faketimestamp: UtilityFunctions.generateTimestamp(),
    };

    const replacements = {
      PawnlandFirstName: "Caden",
      PawnlandLastName: "Ratkeyklkb",
      // 'patient.email@example.com': randomData.fakeEmail,
      SSNHOLDER: "123456789",
      BIRTHYEAR: "1977",
      PATIENTCITY: "Cullen",
      STREETADDRESS: "90 SE Panda",
      PATIENTUNITADDRESS: "unit 5566",
      PATIENTSTATEADDRESS: "KY",
      UUIDTIMESTAMP: randomData.faketimestamp,
    };
    
    let modifiedmsg = jsonData[0].data;
    let modifiedData = UtilityFunctions.replacePlaceholders(
      modifiedmsg,
      replacements
    );
    const formattedSSN = UtilityFunctions.formatSSN(randomData.fakeSSN);
    cy.log(
      `${modifiedData.randomLastName}, ${modifiedData.randomFirstName} ${formattedSSN}`
    );
    fakeFormattedSSN = formattedSSN;
    currentMessage = UtilityFunctions.formatHL7(modifiedData);
    fakeFullName = `${randomData.randomLastName}, ${randomData.randomFirstName}`;
    fakeRandomData = randomData;
    
    Cypress.env("currentMessage", currentMessage);
    Cypress.env(
      "fakeFullName",
      `${randomData.randomLastName}, ${randomData.randomFirstName}`
    );
    Cypress.env("randomData", randomData);

    cy.request({
      method: "POST",
      url: apiurl,
      headers: {
        "Content-Type": "text/plain",
        Authorization: `Bearer ${authToken}`,
        clientid: clientid,
        clientsecret: clientsecret,
        msgType: "HL7",
      },
      body: modifiedData,
    }).then((response) => {
      messageID = response.body;
      const checkStatusUrl = `${checkstatusurl}${messageID}`;

      const checkStatusRequest = () => {
        cy.request({
          method: "GET",
          url: checkStatusUrl,
          headers: {
            Authorization: `Bearer ${authToken}`,
            clientid: clientid,
            clientsecret: clientsecret,
          },
        }).then((response) => {
          cy.wait(2000);
          expect(response.status).to.eq(200);

          if (
            response.body.nbsInfo.nbsInterfaceStatus === "QUEUED" ||
            response.body.nbsInfo.nbsInterfacePipeLineStatus === "IN PROGRESS"
          ) {
            cy.wait(20000).then(checkStatusRequest);
          } else if (
            response.body.nbsInfo.nbsInterfaceStatus === "Success" &&
            response.body.nbsInfo.nbsInterfacePipeLineStatus === "COMPLETED"
          ) {
            UtilityFunctions.checkELRActivityLog(fakeRandomData);
          } else if (
            response.body.validatedInfo.validatedPipeLineStatus === "FAILED"
          ) {
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
