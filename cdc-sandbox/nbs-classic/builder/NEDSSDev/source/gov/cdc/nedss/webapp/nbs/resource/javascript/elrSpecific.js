function getELRActivityResult()
{
 	
 	clearErrorMessages();
 	
	var errorTD = getElementByIdOrByName("error1");
	errorTD.setAttribute("className", "none");
	
	fromDateNode = getElementByIdOrByName("fromDate");
	strFromDate =  fromDateNode.getAttribute("value");		
	
	fromTimeNode = getElementByIdOrByName("fromTime");
	strFromTime = fromTimeNode.getAttribute("value"); 
	
	toDateNode = getElementByIdOrByName("toDate");
	strToDate =  toDateNode.getAttribute("value");
	
	toTimeNode = getElementByIdOrByName("toTime");
	strToTime = toTimeNode.getAttribute("value");
	
	successNode = getElementByIdOrByName("SuccessMigrated");
	strSuccess = successNode.checked;
        messageTypeNode = getElementByIdOrByName("MessageType");
	
	successMigratedDetailsNode = getElementByIdOrByName("SuccessMigratedDetails");
	strSuccessMigratedDetailsNode = successMigratedDetailsNode.checked;
	
	
	unsuccessfullyMigratedNode = getElementByIdOrByName("UnsuccessfullyMigrated");
	strUnsuccessfullyMigratedNode= unsuccessfullyMigratedNode.checked;

	var labelList = new Array();
        var errorMessage = "";
	var errorText = "";
	

	//check if it is in mm/dd/yyyy format for fromDateNode
           if (!isDate( fromDateNode.value ) &&(fromDateNode.fieldLabel))
                {
    		   	clearErrorMessages();
			setTopErrorMsg();
		   	var tdErrorCell = getTdErrorCell(fromDateNode);
                   	errorText = makeErrorMsg('ERR003', labelList.concat(fromDateNode.fieldLabel));	
                    	tdErrorCell.innerText = errorText;
                    	tdErrorCell.className = "error";    
                     	return true;
                    }//if
	//check if it is in mm/dd/yyyy format for toDateNode
           if (!isDate( toDateNode.value ) &&(toDateNode.fieldLabel))
                {
					clearErrorMessages();
					setTopErrorMsg();
    		   	 	var tdErrorCell = getTdErrorCell(toDateNode);                   
                    	errorText = makeErrorMsg('ERR003', labelList.concat(toDateNode.fieldLabel));	
                    	tdErrorCell.innerText = errorText;
                    	tdErrorCell.className = "error";    
                     	return true;
                    }//if
		
	// do the time hh:mm format validation
	
	if(strFromTime.search(/^((0[0-9]|1[0-9]|2[0-4]):(0[0-9]|[1-5][0-9]|60))?$/)<0){
		clearErrorMessages();
		setTopErrorMsg();
		var tdErrorCell = getTdErrorCell(fromTimeNode);                   
		errorText = makeErrorMsg('ERR019', labelList.concat("From Time"));	
		tdErrorCell.innerText = errorText;
		tdErrorCell.className = "error";    
		return true;
	
	}	
	
	if(strToTime.search(/^((0[0-9]|1[0-9]|2[0-4]):(0[0-9]|[1-5][0-9]|60))?$/)<0){
		clearErrorMessages();
		setTopErrorMsg();
		var tdErrorCell = getTdErrorCell(toTimeNode);                   
		errorText = makeErrorMsg('ERR019', labelList.concat(toTimeNode.fieldLabel));	
		tdErrorCell.innerText = errorText;
		tdErrorCell.className = "error";    
		return true;
		
	}	
	
	if ((CompareDateStrings(toDateNode.value, fromDateNode.value) == -1))
        {
			clearErrorMessages(); 
			setTopErrorMsg();              
			var tdErrorCell = getTdErrorCell(fromDateNode);
			var errorText = makeErrorMsg('ERR006', labelList.concat(fromDateNode.fieldLabel).concat(toDateNode.fieldLabel));
			tdErrorCell.innerText = errorText;
			tdErrorCell.className = "error";
			return true;
        }
	//check to see if at least one status is checked
	if((!(successNode.checked)) && (!(successMigratedDetailsNode.checked)) && (!(unsuccessfullyMigratedNode.checked)))  
        {
			clearErrorMessages();
			setTopErrorMsg(); 
			var tdErrorCell = getTdErrorCell(messageTypeNode) ;	
			errorText = makeErrorMsg('ERR001', labelList.concat("Type of records to be displayed")); 	 
			tdErrorCell.innerText = errorText;
			tdErrorCell.className = "error"; 
			return true;
			
		}
	
	
	
	
	
	sortResultsNode = getElementByIdOrByName("sortResults");	
	strSortResults = sortResultsNode.getAttribute("value");
	
	lastNameNode = getElementByIdOrByName("lastName");	
	strLastName = lastNameNode.getAttribute("value");
	
	msgIdNode = getElementByIdOrByName("msgId");
	strMsgId = msgIdNode.getAttribute("value");
	
	accessionNumberNode = getElementByIdOrByName("accessionNumber");
	strAccessionNumber = accessionNumberNode.getAttribute("value");
	
	observationIDNode = getElementByIdOrByName("observationID");
	strObservationID = observationIDNode.getAttribute("value");
	
	reportingFacilityNode = getElementByIdOrByName("reportingFacility");
	strReportingFacility = reportingFacilityNode.getAttribute("value");
	
	
	if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
		{
			//if no popup is open execute the following
			ChildWindowHandle = window.open("/nbs/ELRActivityLogSubmit.do?" + 
					"strFromDate=" + strFromDate + 
					"&strFromTime=" + strFromTime + 
					"&strToDate=" +	strToDate + 
					"&strToTime=" + strToTime + 
					"&strSuccess="+strSuccess + 
					"&strSuccessMigratedDetailsNode=" + strSuccessMigratedDetailsNode +
					"&strUnsuccessfullyMigratedNode=" + strUnsuccessfullyMigratedNode +
					"&strSortResults=" + strSortResults +
					"&strLastName=" + strLastName +
					"&strMsgId=" + strMsgId +
					"&strAccessionNumber=" + strAccessionNumber +
					"&strObservationID=" + strObservationID +		 
					"&strReportingFacility=" + strReportingFacility, "reportPage","height=600,width=800,status=no,menubar=no,titlebar=no,toolbar=no,scrollbars=yes,resizable=yes");
	
			
		}
		else{
		//there is an active pop up then close it and reopen a new child window		
		 ChildWindowHandle.close();
		 ChildWindowHandle = window.open("/nbs/ELRActivityLogSubmit.do?" + 
		 					"strFromDate=" + strFromDate + 
		 					"&strFromTime=" + strFromTime + 
		 					"&strToDate=" +	strToDate + 
		 					"&strToTime=" + strToTime + 
		 					"&strSuccess="+strSuccess + 
		 					"&strSuccessMigratedDetailsNode=" + strSuccessMigratedDetailsNode +
		 					"&strUnsuccessfullyMigratedNode=" + strUnsuccessfullyMigratedNode +
		 					"&strSortResults=" + strSortResults +
		 					"&strLastName=" + strLastName +
		 					"&strMsgId=" + strMsgId +
		 					"&strAccessionNumber=" + strAccessionNumber +
		 					"&strObservationID=" + strObservationID +		 
					"&strReportingFacility=" + strReportingFacility, "reportPage","height=600,width=800,status=no,menubar=no,titlebar=no,toolbar=no,scrollbars=yes,resizable=yes");
		 
		}
	
	
}	




function selectTime(radioBUtton)
{
	dateNode = getElementByIdOrByName("Date");
	isToday = dateNode.checked;
	//alert(isToday);

	fromTimeNode = getElementByIdOrByName("FromTime");
	toTimeNode = getElementByIdOrByName("ToTime");
	

	if(isToday)
	{
		//alert("disable the from and to!");
		fromTimeNode.disabled=true;
		toTimeNode.disabled=true;
	}
	else
	{
		//alert("enable the from and to!");
		fromTimeNode.disabled=false;
		toTimeNode.disabled=false;
	}	
		

}



function getRefresh(){
 	clearErrorMessages();
	fromDateNode = getElementByIdOrByName("fromDate");
	fromDateNode.value = "";
	
	defaultDateNode = getElementByIdOrByName("defaultDate");
	fromDateNode.value = defaultDateNode.getAttribute("value");
	
	fromTimeNode = getElementByIdOrByName("fromTime");
	fromTimeNode.value="";
	
	defaultTimeNode = getElementByIdOrByName("defaultTime");
	fromTimeNode.value = defaultTimeNode.getAttribute("value");
	
	
	
	toDateNode = getElementByIdOrByName("toDate");
	toDateNode.value = "";
		
	defaultDateNode = getElementByIdOrByName("defaultTomorrowDate");
	toDateNode.value = defaultDateNode.getAttribute("value");
		
	toTimeNode = getElementByIdOrByName("toTime");
	toTimeNode.value="";
		
	defaultTimeNode = getElementByIdOrByName("defaultTime");
	toTimeNode.value = defaultTimeNode.getAttribute("value");
	
	
	sortResultsNode = getElementByIdOrByName("sortResults");	
	sortResultsNode.value = "getStatusCd";
		
		
		
	lastNameNode = getElementByIdOrByName("lastName");	
	lastNameNode.value = "";
		
	msgIdNode = getElementByIdOrByName("msgId");
	msgIdNode.value ="";
		
		
	accessionNumberNode = getElementByIdOrByName("accessionNumber");
	accessionNumberNode.value = "";
		
	observationIDNode = getElementByIdOrByName("observationID");
	observationIDNode.value="";
		
	reportingFacilityNode = getElementByIdOrByName("reportingFacility");
	reportingFacilityNode.value = "";
	
	
	successNode = getElementByIdOrByName("SuccessMigrated");
	successNode.checked = true;
	successNode.disabled = false;
	
	successMigratedDetailsNode = getElementByIdOrByName("SuccessMigratedDetails");
	successMigratedDetailsNode.checked = false;
	successMigratedDetailsNode.disabled = true;
		
		
	unsuccessfullyMigratedNode = getElementByIdOrByName("UnsuccessfullyMigrated");
	unsuccessfullyMigratedNode.checked=true;
	
	
	

}

function clearErrorMessages(){
	var inputNodes = document.getElementsByTagName("input");
		var selectNodes = document.getElementsByTagName("select");
		var textareaNodes = document.getElementsByTagName("textarea");


	for(var i=0; i < inputNodes.length; i++) {
			if ( (inputNodes.item(i).type == "text") ||
			     (inputNodes.item(i).type == "checkbox") ||
			    (inputNodes.item(i).type == "hidden")	) {
			    
				eraseErrorCellForElement(inputNodes.item(i));
			}
		}
		for(var i=0; i<selectNodes.length; i++ ) {
			eraseErrorCellForElement(selectNodes.item(i));
		}
		for(var i=0; i<textareaNodes.length; i++ ) {
			eraseErrorCellForElement(textareaNodes.item(i));
		}
      var errorTD = getElementByIdOrByName("error1");
	errorTD.setAttribute("className", "none");
	errorTD.innerText = "";
}

function setTopErrorMsg(){
var errorTD = getElementByIdOrByName("error1");
errorTD.setAttribute("className", "none");
var errorText = makeErrorMsg('ERR015');
errorTD.innerText = errorText;
errorTD.className = "error";
}



function toggle1(oCheckbox){

	
	var element = getElementByIdOrByName("SuccessMigratedDetails");	
	
	if (oCheckbox.checked){
		element.checked = false;
		element.disabled = true;	
	}
	else {		
		element.disabled = false;	
	}
	
}

function toggle2(oCheckbox){

	
	var element = getElementByIdOrByName("SuccessMigrated");	
	
	if (oCheckbox.checked){
		element.checked = false;
		element.disabled = true;	
	}
	else {
		element.disabled = false;	
	}
	
}

function defaultCheckbox(){

	var checkbx1 = getElementByIdOrByName("SuccessMigrated");
	var checkbx2 = getElementByIdOrByName("SuccessMigratedDetails");
	var checkbx3 = getElementByIdOrByName("UnsuccessfullyMigrated");
	
	checkbx1.checked = true;
	checkbx2.disabled = true;
	checkbx2.checked = false;
	checkbx3.checked = true;


}
