//codeLookup functionality
function quickentry(button, name, entityType)
{
	var quickCode = getElementByIdOrByName(name.replace(/-table-/,"-codeLookupText-")) == null ? "" : getElementByIdOrByName(name.replace(/-table-/,"-codeLookupText-")).value;
	
	
	var uid = name.replace(/entity-table-/,"");
	
	
	
	var labelList = new Array();
	var errorText = "";
	var errorNode = getTdErrorCell(button);	
	errorNode.className="error";

	var entityTable = getElementByIdOrByName(name);
	
	var codeLength = trimSpaces(quickCode).length;
	
	var spans = entityTable.getElementsByTagName("span");
	
	for(var i=0;i<spans.length;i++) {

		if(codeLength==0)
		{
			
			if (entityTable != null)
			{
				var spansOrd = entityTable.getElementsByTagName("span");

				var inputs = entityTable.getElementsByTagName("input");

				for(var j2=0;j2<inputs.length;j2++) {

					if(inputs.item(j2).type=="hidden" && getCorrectAttribute(inputs.item(j2), "mode",inputs.item(j2).mode)=="uid")
					{
					   inputs.item(j2).value="";
					}
				}
			}

			spans.item(i).innerHTML = "";
			if(spans.item(i).textContent!=undefined)
				spans.item(i).textContent="";
			
			errorNode.innerText = makeErrorMsg('ERR101', labelList.concat("Code Lookup"));
			
			if(errorNode.textContent!=undefined)
				errorNode.textContent=makeErrorMsg('ERR101', labelList.concat("Code Lookup"));
			
			errorNode.className="error";
		}		
		else
		{
		
			//alert('name: ' + name);
			//alert('entityType: ' + entityType);
			//alert('quickCode: ' + quickCode);
			//alert('UID: ' + uid);
			

			errorNode.innerText ="";

			if(errorNode.textContent!=undefined)
				errorNode.textContent="";
			
			var x = screen.availWidth;
			var y = screen.availHeight;

			window.open("/nbs/codeLookup?name="+ name + '&amp;quickCode=' + quickCode + '&amp;UID=' + uid + '&amp;entityType=' + entityType,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");

		}
	}	

}

function quickEntryClear(aTableId) {

	var tableNode = getElementByIdOrByName(aTableId);
	var spanNodes = tableNode.getElementsByTagName("span");
	var hiddenNodes = tableNode.getElementsByTagName("input");
	var textAreaNodes = tableNode.getElementsByTagName("textarea");

	for(var i=0;i<spanNodes.length;i++)
	{
		spanNodes.item(i).innerHTML = "&nbsp;&nbsp;";
	}
	
	for(var i=0;i<textAreaNodes.length;i++)
	{
		textAreaNodes.item(i).innerHTML = "&nbsp;&nbsp;";
	}


	if(aTableId == "entity-table-investigator.personUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Investigator selected.";
		}
	}
	if(aTableId == "entity-table-reportingOrg.organizationUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Reporting Source selected.";
		}
	}
	if(aTableId == "entity-table-reporter.personUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Reporter selected.";
		}
	}
	if(aTableId == "entity-table-physician.personUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Physician selected.";
		}
	}
	if(aTableId == "entity-table-hospitalOrg.organizationUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Hospital selected.";
		}
	}
	
	//vaccine specific
	
	if(aTableId == "entity-table-vaccineGiver.personUid") {
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Provider selected.";
		}
	}
	
	if(aTableId == "entity-table-vaccineGivenby.organizationUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Organization selected.";
		}
	}
	if(aTableId == "entity-table-vaccineManufacturer.organizationUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Vaccine Manufacturer selected.";
		}
	}
	if(aTableId == "entity-table-abcInvestigator.personUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no ABCs Investigator selected.";
		}
	}

	if(aTableId == "entity-table-labHospitalOrg.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no ABCs Culture Hospital selected.";
		}
	}
	if(aTableId == "entity-table-treatmentHospitalOrg.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no ABCs Treatment Hospital selected.";
		}
	}

	if(aTableId == "entity-table-patientTransferHospitalOrg.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no ABCs Transfer Hospital selected.";
		}
	}	
	
	if(aTableId == "entity-table-daycareFacilityOrg.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Day Care Facility selected.";
		}
	}
	if(aTableId == "entity-table-chronicCareFacility.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Chronic Care Facility selected.";
		}
	}
	if(aTableId == "entity-table-birthHospital.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Birth Hospital selected.";
		}
	}
	if(aTableId == "entity-table-admittedHospital.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Admitted Hospital selected.";
		}
	}
	if(aTableId == "entity-table-collegeOrg.organizationUid")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no College Organization selected.";
		}
	}
	


	

	var errorNode = getTdErrorCell(tableNode);
	errorNode.className="error";
	errorNode.innerText ="";
	
	for(var i=0;i<hiddenNodes.length;i++) {

		//alert('type= ' + hiddenNodes.item(i).name +', value= '+ hiddenNodes.item(i).value);
		
		if(hiddenNodes.item(i).type=="hidden") {
			
			hiddenNodes.item(i).value="";
			
			if(hiddenNodes.item(i).name =='investigator.personUid')
			{

				var trNode = getElementByIdOrByName("dateAssignedToInvestigation-tdCell");
				trNode.className="none";
				var dateAssignedNode = getElementByIdOrByName("dateAssignedToInvestigation");
				if(dateAssignedNode != null)
					dateAssignedNode.value="";
			}			
		
		}
		if(hiddenNodes.item(i).type=="text") {

		  	hiddenNodes.item(i).value="";

		}
		if(hiddenNodes.item(i).type=="checkbox"){

		  	hiddenNodes.item(i).checked=false;
		}	
	}

}

function changeQuickEntryType(name) {
	
	var aTableId = "entity-table-" + name;
	//alert(aTableId);
	
	var tableNode = getElementByIdOrByName(aTableId);

	if(tableNode != null) {
	
		var spanNodes = tableNode.getElementsByTagName("span");
		var inputs = tableNode.getElementsByTagName("input");

		var finalVal = "";

		for(var a=0;a<inputs.length;a++) {

			if(inputs.item(a).type == 'hidden' && inputs.item(a).value != "") {
				finalVal = inputs.item(a).value;
			}
		}	

		//alert('finalVal:' + finalVal);
		for(var i=0;i<spanNodes.length;i++) {

			if(spanNodes.item(i).id != "")
			spanNodes.item(i).innerHTML = finalVal;
		}
	
	
	}
	
}