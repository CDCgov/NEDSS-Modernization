import { When } from "@badeball/cypress-cucumber-preprocessor";
import "cypress-xpath";
import UtilityFunctions from "@pages/utilityFunctions.page";

When("I Seed HL7 {string} messages to api", (string) => {
  
  let currentMessage;
  let messageID;
  let fakeRandomData;
  const authToken = Cypress.env("authTokenAPI");
  const clientid = Cypress.env("DI_CLIENT_ID");
  const clientsecret = Cypress.env("DI_SECRET");
  const baseUrl = Cypress.env("DI_API");

  cy.readFile("cypress/fixtures/seed.json", "utf8").then((jsonData) => {
    const randomData = {
      randomFirstName: "Caden",
      randomLastName: "Ratkeyklkb",
      fakeSSN: "123456789",    
      fakeDOB: "1977",
      faketimestamp: UtilityFunctions.generateTimestamp(),
    };

    const replacements = {
      PawnlandFirstName: randomData.randomFirstName,
      PawnlandLastName: randomData.randomLastName,   
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
