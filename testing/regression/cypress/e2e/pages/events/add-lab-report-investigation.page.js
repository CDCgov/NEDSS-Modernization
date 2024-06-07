 class AddLabReportInvestigation {
  addLabReport() {  	
  	cy.wait(1000)
	  cy.get("#NBS_LAB365Text").type("1");
	  cy.get("#NBS_LAB365CodeLookupButton").click();
	  cy.get("input[name=INV108_textbox]").type("A");
	  cy.get("input[name=INV107_textbox]").type("F");  
	  cy.get("input[name=INV178_textbox]").type("No");
	  cy.get("input[name=NBS_LAB220_textbox]").type("Dengue virus - Result");
	  cy.get("img[name=NBS_LAB220_button]").click();
	  cy.get("input[name=NBS_LAB220_textbox]").type("Dengue virus - Result");
	  cy.get("input[name=NBS_LAB280_textbox]").type("abnormal");
	  cy.get("input[id=NBS_LAB364]").type("1");
	  cy.get("input[name=LAB115_textbox]").type("(arb_u)");
	  cy.get("textarea[id=NBS_LAB208]").type("postive");
	  cy.get("tr#AddButtonToggleRESULTED_TEST_CONTAINER input").click();
	  cy.wait(500);
	  cy.get("input[name=SubmitAndCreateInvestiation]").first().click();
	  cy.get("img[name=ccd_button]").click();  
	  cy.get("option[value=11065]").click();
	  cy.get("input[name=ccd_textbox]").type("AIDS");
	  cy.get("#Submit").first().click();
	  cy.get("#SubmitTop").click();  
	  cy.contains("Investigation has been successfully saved in the system.");

	  let investigationId;
	  cy.get('td[class="border1"]').then(($selectedElement) => {
	    investigationId = $selectedElement[0].children[1].innerText.trim();
	  })
	  let patientId;
	  cy.get('span[class="valueTopLine"]').then(($selectedElement) => {
	    patientId = $selectedElement[5].next().innerText.trim();
	  })

	  let firstName;
	  let lastName;
	  let programArea;
	  let investigationStatus;
	  let investigationStartDate;
	  let investigationCloseDate; 
	  let reportingOrganization;

	  cy.get('table[class="subSect"] tr td').then(($selectedElement) => { 
	    firstName = $selectedElement[7].innerText.trim();
	    lastName = $selectedElement[11].innerText.trim();
	    programArea = $selectedElement[157].innerText.trim()
	    investigationStatus = $selectedElement[161].innerText.trim();
	    investigationStartDate = $selectedElement[165].innerText.trim();
	    investigationCloseDate = $selectedElement[167].innerText.trim();
	    reportingOrganization = $selectedElement[211].innerText.trim().replace(/\n/g, ',').split(',');
	  })
	  cy.get('td[id="tabs0head1"]').click({multiple: true});
	  let investigationInfo = [];
	  cy.get('table[class="subSect"] tr td span').each(($selectedElement) => {
      let obj = {}
      let span = $selectedElement[0]
      if (span.id) {
        let inputId = span.id.trim();
        if (inputId[inputId.length-1] === "L") {        	
          investigationInfo.push(span.innerText.trim());
        } else {
          let inputValue = span.innerText.trim();          
          investigationInfo.push(inputValue);
        }
      }
	  })
	  cy.get('body').each(($selectedElement) => {
	  	labReportGlobal = investigationInfo;	  	
	  })
	  cy.wait(500);
  }
}

export default new AddLabReportInvestigation();