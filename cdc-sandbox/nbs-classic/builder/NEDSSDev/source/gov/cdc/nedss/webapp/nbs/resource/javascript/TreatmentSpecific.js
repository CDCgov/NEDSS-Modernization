
function TreatmentSubmit(){
	//return false if no problems
	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	var errorText="";
	var labelList = new Array();
	
	var atLeastOneField = false;

	var entitySearchGiver = getElementByIdOrByName("Prov-entity.entityProvUID");
	var entitySearchOrgGiver = getElementByIdOrByName("Org-ReportingOrganizationUID");
	//alert("submit");
	var errorText = "";

/*	var TRT111 = getElementByIdOrByName("treatmentVO.theTreatmentDT.cdDescTxt");
		
		var controllerNode = getElementByIdOrByName("TRT111");
		var errorText = "";
	if(controllerNode.offsetWidth!=0 && controllerNode.value==""){
	   	
		errorText = makeErrorMsg('ERR140', labelList);
		errorTD.innerText = errorText;
			
		errorTD.setAttribute("className", "error");
		showErrorStatement=false;
		document.location.href="#top";
			return true;
	}
	*/

	if(entitySearchGiver!=null && isEmpty(entitySearchGiver.value) == false) atLeastOneField = true;
	if(entitySearchOrgGiver!=null && isEmpty(entitySearchOrgGiver.value) == false) atLeastOneField = true;
	

	if(atLeastOneField == false){
		errorText = makeErrorMsg('ERR139');		
		/*errorTD.innerText = errorText;
		errorTD.setAttribute("className", "error");
		showErrorStatement=false;
		document.location.href="#top"; */
		
		//alert(labelList);
		var facilityProvider = getElementByIdOrByName("Org-ReportingOrganizationUID");
		var tdErrorCell = getTdErrorCell(facilityProvider) ;
		
		if( tdErrorCell.innerText == "" )
		  tdErrorCell.innerText = errorText;
		else
		  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		tdErrorCell.className = "error";

		return true;
		

	}else
		return false;
	
}

function TRT111()
{
	var TRT111 = getElementByIdOrByName("treatmentVO.theTreatmentDT.cdDescTxt");
	//alert("trt111"+ TRT111);
	var controllerNode = getElementByIdOrByName("TRT111");
	//alert("controllerNode" + controllerNode);
	//var HEP149node = getElementByIdOrByName("supplemental_s[20].obsValueNumericDT_s[0].numericValue1");
        var errorText = "";
   	if(controllerNode.offsetWidth!=0 && controllerNode.value==""){
   	
		var labelList = new Array();
		var tdErrorCell = getTdErrorCell( controllerNode) ;
		labelList[0] = "Custom Treatment";
		errorText = makeErrorMsg('ERR023',labelList);
		tdErrorCell.innerText = errorText;
		tdErrorCell.setAttribute("className", "error");
		return true;
	}else
		return false;
	
}



function codeLookup(button, type)
{
	
	var orgSearchResult = "";
	var parseString ="";
	var code = "";
	var labelList = new Array();
	var errorText = "";
	var errorNode = getTdErrorCell(button);
	var targetOrdered = "";
	var targetResulted="";
	var programAreaCode = "";

	setAttributeClass(errorNode,"error");
	var UID = "";
	var reportUID = "";
	var programAreaCode ="";
	var target ="";
	
	//alert("button :" + button.value + "\ntype :" + type);
		
	
	var entityTable = getElementByIdOrByName(type);
	
	if(type == "entity-table-Org-ReportingOrganizationUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Org-ReportingOrganizationUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
		
		var batchInserted = getElementByIdOrByName("nestedElementsHiddenFieldTest Result");
		
		//alert("batchInserted" + batchInserted);
		//var valueofBatch = batchInserted.value;
		//alert("valueofBatch" + valueofBatch);
		
		if(batchInserted!= null)
		{
			var valueofBatch = batchInserted.value;
			var deletedBatch= valueofBatch.replace(/statusCd~^/g, "statusCd"+ nvDelimiter+"I");
			var deletedBatch2 = deletedBatch.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");
			
			batchInserted.value = deletedBatch2;
			//alert("after = " + batchInserted.value);
			updateBatchEntryHistoryBox("Test Result");
			resetBatchEntryInputElements("Test Result");
		}
		reportUID = (getElementByIdOrByName("Org-ReportingOrganizationUID")).value;
		var programLabel = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd");
		//alert("programLabel is :" + programLabel);
		if(programLabel!= null)
		{
			programAreaCode = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;
			//alert("programAreaCode is :" + programAreaCode);
		}
		//alert("programAreaCode " + programAreaCode);
		//var labId= getElementByIdOrByName("labId").value;
		//alert("labId is :" + labId);
	
		//alert("UID Value is :" + reportUID);
	}
	if(type == "entity-table-Org-HospitalUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Org-HospitalUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
	}
	else if(type == "entity-table-Org-OrderingFacilityUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Org-OrderingFacilityUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
	}
	else if(type == "entity-table-Prov-entity.providerUID") 
	{
		code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.providerUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
	}
	else if(type == "entity-table-Prov-entity.reporterUID") 
	{
		code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.reporterUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
	}
	else if(type == "entity-table-Prov-entity.entityProvUID") 
	{
		code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.entityProvUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
	}
	//alert("Code = " +code);
	var valid = new RegExp(/^[a-z0-9-]+$/i);
	if (code != null && code.length>0 && !valid.test(code) ){
		setText(errorNode,makeErrorMsg('ERR104', labelList.concat("Code Lookup")));
		setAttributeClass(errorNode,"error");
	} else {	
	
		var codeLength = code.length;
		var spans = entityTable.getElementsByTagName("span");
		for(var i=0;i<spans.length;i++)
			{
				if((spans.item(i).id=="entity.completeOrgSearchResult") || 
					(spans.item(i).id=="entity.completePersonSearchResult") ||
					(spans.item(i).id=="entity.reporterPersonSearchResult"))
					{	
					if(codeLength==0)
					{
						var uidToBeCleared = getElementByIdOrByName(UID);
						//alert(uidToBeCleared.value);
						uidToBeCleared.value = "";
						setText(spans.item(i),"");
						setText(errorNode,makeErrorMsg('ERR101', labelList.concat("Code Lookup")));
						setAttributeClass(errorNode,"error");
					}
					else
					{
						setText(errorNode,"");
						getTreatmentCodeLookup(type, code, target, targetOrdered,targetResulted, UID, reportUID, programAreaCode );
					}

			}
		}
    }
}


validationArray[0] = TreatmentSubmit;
validationArray[1] = TRT111;