
function ObservationSubmit(){	
	
	var errorNode = getElementByIdOrByName("atLeastOne");	
	if(errorNode!=null)
		errorNode.className = "none";
	
	var atLeastOne = false;	
	
	var opFirst = getElementByIdOrByName("orderingProvider.personUid");	
	if(opFirst==null || opFirst.value==null)
		atLeastOne=true;
	
	if(opFirst!=null && opFirst.value!=null && opFirst.value!="") 
		atLeastOne=true;
		
	var orderingProvider = getElementByIdOrByName("orderingProvider.personUid");	
	if(orderingProvider!=null && orderingProvider.value!=null && orderingProvider.value!="") atLeastOne=true;

	var orderingFacility = getElementByIdOrByName("orderingFacility.organizationUID");	
	if(orderingFacility!=null && orderingFacility.value!=null && orderingFacility.value!="") atLeastOne=true;

	var reportingLab = getElementByIdOrByName("reportingLab.organizationUID");	
	if(reportingLab!=null && reportingLab.value!=null && reportingLab.value!="") atLeastOne=true;
	


	if (atLeastOne==false){
		if(errorNode!=null)
			errorNode.className = "visible";
	}

	
	return !atLeastOne;
}

//UpdateResultTestName
function UpdateResultTestName(src){
	
	var resultSelectNode = getElementByIdOrByName("resultedTest[i].theResultedTestVO.theObservationDT.cd");
	if(resultSelectNode != null) {
		//remove the empty option
		//alert(resultSelectNode.type);
		if(resultSelectNode.type == 'select-one') {			
			resultSelectNode.options[0] = null;
			resultSelectNode.value = src.options[src.selectedIndex].value;
			//add default to the target node
			resultSelectNode.setAttribute("default",src.options[src.selectedIndex].value);
		}
	}	
	

}
//TransferEntity
function TransferEntity() {
	
	var reportingLab = getElementByIdOrByName("reportingLab.organizationUID-values");			
	
	if(reportingLab.value.length > 0) {		
		var reportingLabValue = reportingLab.value.split("^");			
		//alert(reportingLabValue.length);
		var reportingLabUID = getElementByIdOrByName("reportingLab.organizationUID").value;					
		var performingLabTable = getElementByIdOrByName("entity-table-resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid");	
		var spans = performingLabTable.getElementsByTagName("span");	
		for(var i=0;i<spans.length;i++)	{
			if(spans.item(i).id=="entity.name")
			{
				spans.item(i).innerHTML = reportingLabValue[0];			
			} 
			else if(spans.item(i).id=="entity.localID")
			{	
				spans.item(i).innerHTML = reportingLabValue[1];
			}
			else if(spans.item(i).id=="entity.phoneNbrTxt")
			{	
				spans.item(i).innerHTML = reportingLabValue[2];
			}
			else if(spans.item(i).id=="entity.extensionTxt")
			{	
				spans.item(i).innerHTML = reportingLabValue[3];				
			}
		}//for	
		var inputs = performingLabTable.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++){
			//if(inputs.item(i).type=="hidden"  && inputs.item(i).mode=="uid")
			if(inputs.item(i).getAttribute("type")=="hidden" && inputs.item(i).getAttribute("mode")=="uid")
			{	
				inputs.item(i).value=reportingLabUID;		
			}
		}//for		
	} else {
		//alert('in else');
		var performingLabTable = getElementByIdOrByName("entity-table-resultedTest[i].thePerformingLabVO.theOrganizationDT.organizationUid");	
		var spans = performingLabTable.getElementsByTagName("span");	
		for(var i=0;i<spans.length;i++)	{
			if(spans.item(i).id=="entity.name")
			{
				spans.item(i).innerHTML = "";			
			} 
			else if(spans.item(i).id=="entity.localID")
			{	
				spans.item(i).innerHTML = "";
			}
			else if(spans.item(i).id=="entity.phoneNbrTxt")
			{	
				spans.item(i).innerHTML = "";
			}
			else if(spans.item(i).id=="entity.extensionTxt")
			{	
				spans.item(i).innerHTML = "";				
			}
		}//for	
		var inputs = performingLabTable.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++){
			//if(inputs.item(i).type=="hidden"  && inputs.item(i).mode=="uid")
			if(inputs.item(i).getAttribute("type")=="hidden" && inputs.item(i).getAttribute("mode")=="uid")
			{	
				inputs.item(i).value="";		
			}
		}//for		
	}
}//TransferEntity
function labJurisdictionCheck()
{
	var element = getElementByIdOrByName("orderedTest.theObservationDT.jurisdictionCd");
	if(element.type == "select-one" && element.value!="")
	{
		var NBSSecJurisdictionParseString = getElementByIdOrByName("NBSSecurityJurisdictions");
		var items = NBSSecJurisdictionParseString.value.split("|");
		var containsJurisdiction = false;				
		var confirmMsg = "Once you save this Observation, you will not be able to view it because it is outside your Program Area/Jurisdiction." ;
	
		if (items.length > 1)	
		{
			for (var i=0; i < items.length; i++) 
			{
				if (items[i]!=""  && items[i] == element.value )
				{
				  containsJurisdiction = true;
				}					
			}
		}
	
		 if(containsJurisdiction)
		 {
			var ContextAction= getElementByIdOrByName("ContextAction");					 
			ContextAction.value = "Submit";				 
			return false;
		 }
		 else
		 {
			var ContextAction= getElementByIdOrByName("ContextAction");					
			ContextAction.value = "SubmitNoViewAccess";
			var returnVariable = confirm(confirmMsg);	
			showErrorStatement= returnVariable;		
	
			return !returnVariable;
		 }					
	}
	else
		return false;
}

validationArray[0] = ObservationSubmit;
validationArray[1] = labJurisdictionCheck;
tabValidationArray[0] = TransferEntity;
tabValidationArray[1] = ObservationSubmit;