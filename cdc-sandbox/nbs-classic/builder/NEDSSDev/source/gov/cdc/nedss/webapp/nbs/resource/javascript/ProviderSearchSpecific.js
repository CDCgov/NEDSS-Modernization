
function ProviderSearchSubmit(){
	//return false if no problems

	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	errorTD.setAttribute("class", "none");
	var errorText=""; 
	var atLeastOneSearchCriteria = false;
	var atLeastOneStatusCode = false; 
	var frm = getElementByIdOrByName("nedssForm");	
	
	
	
	if(getElementByIdOrByName("providerSearch.lastName") != null && isEmpty(getElementByIdOrByName("providerSearch.lastName").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("providerSearch.firstName")!=null && isEmpty(getElementByIdOrByName("providerSearch.firstName").value) == false) atLeastOneSearchCriteria = true;
	
	if(getElementByIdOrByName("providerSearch.streetAddr1")!=null && isEmpty(getElementByIdOrByName("providerSearch.streetAddr1").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("providerSearch.cityDescTxt")!=null && isEmpty(getElementByIdOrByName("providerSearch.cityDescTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("providerSearch.state")!=null && isEmpty(getElementByIdOrByName("providerSearch.state").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("providerSearch.zipCd")!=null && isEmpty(getElementByIdOrByName("providerSearch.zipCd").value) == false) atLeastOneSearchCriteria = true;
		
	if(getElementByIdOrByName("providerSearch.phoneNbrTxt")!=null && isEmpty(getElementByIdOrByName("providerSearch.phoneNbrTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("providerSearch.rootExtensionTxt")!=null && isEmpty(getElementByIdOrByName("providerSearch.rootExtensionTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("providerSearch.typeCd")!=null && isEmpty(getElementByIdOrByName("providerSearch.typeCd").value) == false) atLeastOneSearchCriteria = true;	

	var activeStatus = getElementByIdOrByName("providerSearch.active");
	
	var inactiveStatus = getElementByIdOrByName("providerSearch.inActive");
	var supercededStatus = getElementByIdOrByName("providerSearch.superceded");
	var personSearchSelected = getElementByIdOrByName("providerSearch.select");
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
	
		
	if(inactiveStatus==null && activeStatus==null && supercededStatus==null)
	{
		var statusNode = document.createElement("input");
		statusNode.setAttribute("type", "hidden");
		statusNode.setAttribute("value", "true");
		statusNode.setAttribute("name", "providerSearch.active");
		var formNode = getElementByIdOrByName("nedssForm");
		formNode.appendChild(statusNode);
		atLeastOneStatusCode=true;
		
	}
	
	// do the appropriate error message
	var tdErrorCell = getTdErrorCell( getElementByIdOrByName("providerSearch.superceded") );
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
			tdErrorCell.textContent = errorText;
			
			tdErrorCell.className = "error";
			tdErrorCell.setAttribute("class","error");
			
			if( tdErrorCell.innerText == "" ){
				  tdErrorCell.innerText = errorText;
				  tdErrorCell.textContent = errorText;
				  
				  
			}
			else 
			{
				  tdErrorCell.innerText = tdErrorCell.innerText;
				  tdErrorCell.textContent = tdErrorCell.innerText;
				  
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
		if(!isEntitySearch){
			tdErrorCell.innerText = "";
			tdErrorCell.textContent = "";
			
		}
	}
	
	if( atLeastOneSearchCriteria == false ) 
	{
		errorText = "Please enter at least one item to search on and try again.\n";
		errorTD.innerText = errorText;
		errorTD.textContent = errorText;
		
		if( errorTD.innerText == "" ){
					  errorTD.innerText = errorText;
					  errorTD.textContent = errorText;
					  }
					else {
					  errorTD.innerText = errorTD.innerText;
					  errorTD.textContent = errorTD.innerText;
					  }
					errorTD.className = "error";
					errorTD.setAttribute("class","error");
					
		showErrorStatement=false;					
	}
	 
	if(atLeastOneStatusCode==false || atLeastOneSearchCriteria==false)
		return true;
	else
		return false;
	
}

validationArray[0] = ProviderSearchSubmit;
