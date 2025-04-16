import { When, attach, Given } from "@badeball/cypress-cucumber-preprocessor";
import "cypress-xpath";
import UtilityFunctions from "@pages/utilityFunctions.page";
import { faker } from "@faker-js/faker";

Given("I login for HL7 API generate token", () => {
  const clientid = Cypress.env("DI_CLIENT_ID");
  const clientsecret = Cypress.env("DI_SECRET");
  const baseUrl = Cypress.env("DI_API");

  cy.request({
    method: "POST",
    url: `${baseUrl}/auth/token`,
    headers: {
      "Content-Type": "text/plain",
      clientid: clientid,
      clientsecret: clientsecret,
    },
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
  let currentMessage;
  let messageID;
  let fakeRandomData;

  const authToken = Cypress.env("authTokenAPI");
  const clientid = Cypress.env("DI_CLIENT_ID");
  const clientsecret = Cypress.env("DI_SECRET");
  const baseUrl = Cypress.env("DI_API");

  cy.readFile(`cypress/fixtures/${messageCondition}.json`, "utf8").then((jsonData) => {
    const randomData = {
      randomFirstName: faker.person.firstName(),
      randomLastName: UtilityFunctions.generateRandomLastName(),
      fakeSSN: UtilityFunctions.generateRandomSSN(),
      fakeEmail: faker.internet.email(),
      fakeStreetAddress: faker.location.streetAddress(),
      fakeState: faker.location.state({abbreviated: true}),
      fakeCity: faker.location.city(),
      fakeBuildingNumber: faker.location.buildingNumber(),
      fakeDOB: `19${faker.number.int(9)}${faker.number.int(9)}`,
      faketimestamp: UtilityFunctions.generateTimestamp(),
    };

    const replacements = {
      PawnlandFirstName: randomData.randomFirstName,
      PawnlandLastName: randomData.randomLastName,
      // 'patient.email@example.com': randomData.fakeEmail,
      SSNHOLDER: randomData.fakeSSN,
      BIRTHYEAR: randomData.fakeDOB,
      PATIENTCITY: randomData.fakeCity,
      STREETADDRESS: randomData.fakeStreetAddress,
      PATIENTUNITADDRESS: "unit " + randomData.fakeBuildingNumber,
      PATIENTSTATEADDRESS: randomData.fakeState,
      UUIDTIMESTAMP: randomData.faketimestamp,
    };

    let modifiedmsg = jsonData[0].data;
    let modifiedData = UtilityFunctions.replacePlaceholders(
      modifiedmsg,
      replacements
    );
    const formattedSSN = UtilityFunctions.formatSSN(randomData.fakeSSN);
    cy.log(
      `${randomData.randomLastName}, ${randomData.randomFirstName} ${formattedSSN}`
    );
    currentMessage = UtilityFunctions.formatHL7(modifiedData);
    fakeRandomData = randomData;
    Cypress.env("currentMessage", currentMessage);
    Cypress.env(
      "fakeFullName",
      `${randomData.randomLastName}, ${randomData.randomFirstName}`
    );
    Cypress.env("randomData", randomData);

    cy.request({
      method: "POST",
      url: `${baseUrl}/elrs`,
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
      const checkStatusUrl = `${baseUrl}/elrs/status-details/${messageID}`;

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

          const nbsInfo = response.body[0].nbsInfo;
          const validatedInfo = response.body[0].validatedInfo;

          if (["QUEUED", "IN PROGRESS"].includes(nbsInfo.nbsInterfaceStatus) || 
              nbsInfo.nbsInterfacePipeLineStatus === "IN PROGRESS") {
            cy.wait(20000).then(checkStatusRequest);
          } else if (nbsInfo.nbsInterfaceStatus === "Success" && 
                     nbsInfo.nbsInterfacePipeLineStatus === "COMPLETED") {
            UtilityFunctions.checkELRActivityLog(fakeRandomData);
          } else if (validatedInfo.validatedPipeLineStatus === "FAILED") {
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

When("I Check the HL7 transport uid", () => {
  UtilityFunctions.checkTransportRequest();
});
