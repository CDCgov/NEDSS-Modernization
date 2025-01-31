
function PersonSearchSubmit(){
	//return false if no problems

	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	var errorText=""; 
	var atLeastOneDemoSearchCriteria = false;
	var actSearchCriteria = false;
	var atLeastOneStatusCode = false; 
	var frm = getElementByIdOrByName("nedssForm");	
	
	
	
	if(getElementByIdOrByName("personSearch.lastName") != null && isEmpty(getElementByIdOrByName("personSearch.lastName").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.firstName")!=null && isEmpty(getElementByIdOrByName("personSearch.firstName").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.birthTimeMonth") !=null && isEmpty(getElementByIdOrByName("personSearch.birthTimeMonth").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.birthTimeDay") !=null && isEmpty(getElementByIdOrByName("personSearch.birthTimeDay").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.birthTimeYear") !=null && isEmpty(getElementByIdOrByName("personSearch.birthTimeYear").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.beforeBirthTime") !=null && isEmpty(getElementByIdOrByName("personSearch.beforeBirthTime").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.afterBirthTime") !=null && isEmpty(getElementByIdOrByName("personSearch.afterBirthTime").value) == false) atLeastOneDemoSearchCriteria = true;
	
	if(getElementByIdOrByName("personSearch.currentSex")!=null && isEmpty(getElementByIdOrByName("personSearch.currentSex").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.streetAddr1")!=null && isEmpty(getElementByIdOrByName("personSearch.streetAddr1").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.cityDescTxt")!=null && isEmpty(getElementByIdOrByName("personSearch.cityDescTxt").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.state")!=null && isEmpty(getElementByIdOrByName("personSearch.state").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.zipCd")!=null && isEmpty(getElementByIdOrByName("personSearch.zipCd").value) == false) atLeastOneDemoSearchCriteria = true;
	
	if(getElementByIdOrByName("personSearch.localID")!=null && isEmpty(getElementByIdOrByName("personSearch.localID").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.typeCd") !=null && isEmpty(getElementByIdOrByName("personSearch.typeCd").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.phoneNbrTxt")!=null && isEmpty(getElementByIdOrByName("personSearch.phoneNbrTxt").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.ageReported")!=null && isEmpty(getElementByIdOrByName("personSearch.ageReported").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.raceCd")!=null && isEmpty(getElementByIdOrByName("personSearch.raceCd").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.ethnicGroupInd")!=null && isEmpty(getElementByIdOrByName("personSearch.ethnicGroupInd").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.rootExtensionTxt")!=null && isEmpty(getElementByIdOrByName("personSearch.rootExtensionTxt").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.typeCd")!=null && isEmpty(getElementByIdOrByName("personSearch.typeCd").value) == false) atLeastOneDemoSearchCriteria = true;	
	if(getElementByIdOrByName("personSearch.role") !=null && isEmpty(getElementByIdOrByName("personSearch.role").value) == false) atLeastOneDemoSearchCriteria = true;
	if(getElementByIdOrByName("personSearch.sSN") !=null && isEmpty(getElementByIdOrByName("personSearch.sSN").value) == false) atLeastOneDemoSearchCriteria = true;

	if(getElementByIdOrByName("personSearch.actId")!=null && isEmpty(getElementByIdOrByName("personSearch.actId").value) == false) actSearchCriteria = true;	
	if(getElementByIdOrByName("personSearch.actType")!=null && isEmpty(getElementByIdOrByName("personSearch.actType").value) == false) actSearchCriteria = true;	

	var activeStatus = getElementByIdOrByName("personSearch.active");
	
	var inactiveStatus = getElementByIdOrByName("personSearch.inActive");
	var supercededStatus = getElementByIdOrByName("personSearch.superceded");
	var personSearchSelected = getElementByIdOrByName("personSearch.select");
	var isEntitySearch = false;
	if(activeStatus!= null ) 
	{
		if(activeStatus.checked==false && (inactiveStatus!=null && inactiveStatus.checked==false) && (supercededStatus!=null && supercededStatus.checked==false))
			atLeastOneStatusCode=false
		else
			atLeastOneStatusCode = true;
	}
	if(inactiveStatus!=null && (inactiveStatus.checked) == true) 
	{
		atLeastOneStatusCode = true;
	}
	if(supercededStatus!=null && (supercededStatus.checked) == true)
	{
	 	atLeastOneStatusCode = true;
	
	 }
	
	
	// If it is for Entity Search
	if ((activeStatus != null) && (inactiveStatus==null) && (supercededStatus==null))
	{
		activeStatus.value = true;
		atLeastOneStatusCode = true;
		isEntitySearch = true;	
	}
	
	/*
	if(frm.personSearchactive!=null )
	{
		if(frm.personSearchactive.checked     == true) atLeastOneStatusCode = true;
	
		if(frm.personSearchinActive.checked   == true) atLeastOneStatusCode = true;
		if(frm.personSearchsuperceded.checked == true) atLeastOneStatusCode = true;
		//	} 
		//	else{ //create frm active and set to true
		//	var statusNode = document.createElement("input");
		//	statusNode.setAttribute("type", "hidden");
		//	statusNode.setAttribute("value", "true");
		//	statusNode.setAttribute("name", "personSearch.active");
		//	var formNode = getElementByIdOrByName("nedssForm");
		//	formNode.appendChild(statusNode);
		//	atLeastOneStatusCode=true;
	}*/
	
	if(inactiveStatus==null && activeStatus==null && supercededStatus==null)
	{
		var statusNode = document.createElement("input");
		statusNode.setAttribute("type", "hidden");
		statusNode.setAttribute("value", "true");
		statusNode.setAttribute("name", "personSearch.active");
		var formNode = getElementByIdOrByName("nedssForm");
		formNode.appendChild(statusNode);
		atLeastOneStatusCode=true;
		
	}
	
	// do the appropriate error message
	var tdErrorCell = getTdErrorCell( getElementByIdOrByName("personSearch.superceded") );
	if(tdErrorCell==null)
	{
		atLeastOneStatusCode = true;
	}
	if( atLeastOneStatusCode == false) 
	{
	
		
		if(tdErrorCell!=null)
		{
			errorText = "Please select at least one status code and try again.";
			
			tdErrorCell.innerText = errorText;
			tdErrorCell.className = "error";
			if( tdErrorCell.innerText == "" )
				  tdErrorCell.innerText = errorText;
			else 
			{
				  tdErrorCell.innerText = tdErrorCell.innerText;
	        }
		}
		else
		{
			atLeastOneStatusCode = true;
		}
	}
	else if(tdErrorCell==null)
	{
		//
		
	}
	else
	{
		if(!isEntitySearch)
			tdErrorCell.innerText = "";
	}
	

	if( atLeastOneDemoSearchCriteria == false &&
		actSearchCriteria == false)
	{
		errorText = "Please enter at least one item to search on and try again.\n";
		errorTD.innerText = errorText;
		if( errorTD.innerText == "" ){
		  errorTD.innerText = errorText;
		} else {
		  errorTD.innerText = errorTD.innerText;
		}
		errorTD.className = "error";

		showErrorStatement=false;
		return true;

	} else if( atLeastOneDemoSearchCriteria == true &&
		actSearchCriteria == true )
	{
		errorText = "Find Patient cannot be executed using criteria from both the Demographic and Event based fields. Please clear out criteria from either the Demographic or Event based fields and try again.\n";
		errorTD.innerText = errorText;
		if( errorTD.innerText == "" ){
			errorTD.innerText = errorText;
		}else {
			errorTD.innerText = errorTD.innerText;
		}
		errorTD.className = "error";

		showErrorStatement=false;
		return true;

	} else if(atLeastOneStatusCode==false) {
		showErrorStatement=false;
		return true;
	}
	else
		return false;
	
}

function roundOff(element)
{
	if(element!=null && element.value!="")
	{

	   var elementValue = element.value;
	   if(elementValue.length==1)
	   {
	     elementValue = "0"+elementValue;
	   }
	   element.value = elementValue;
	}
}


function flipDate(_selectBox){
	
	var _date=getElementByIdOrByName("fulldate");
	var _dateRange = getElementByIdOrByName("betweendate");
	var _birthTimeMonth = getElementByIdOrByName("personSearch.birthTimeMonth");
	var _birthTimeDay = getElementByIdOrByName("personSearch.birthTimeDay");
	var _birthTimeYear = getElementByIdOrByName("personSearch.birthTimeYear");
	var _afterBirthTime = getElementByIdOrByName("personSearch.afterBirthTime");
	var _beforeBirthTime = getElementByIdOrByName("personSearch.beforeBirthTime");
	
	
	if(_selectBox.value=="="){
	        _afterBirthTime.value = "";
	        _beforeBirthTime.value = "";
		_date.className="visible";
		_dateRange.className="none";
	} else {
	        _birthTimeMonth.value = "";
	        _birthTimeDay.value = "";
	        _birthTimeYear.value = "";
	        
		_date.className="none";
		_dateRange.className="visible";
	}

}


function flipDateOnload(selectBox){
	
	var _selectBox = getElementByIdOrByName(selectBox);
	var _date=getElementByIdOrByName("fulldate");
	var _dateRange = getElementByIdOrByName("betweendate");
	
	if(_selectBox.value=="="){
		_date.className="visible";
		_dateRange.className="none";
	} else {
		_date.className="none";
		_dateRange.className="visible";
	}

}



validationArray[0] = PersonSearchSubmit;
