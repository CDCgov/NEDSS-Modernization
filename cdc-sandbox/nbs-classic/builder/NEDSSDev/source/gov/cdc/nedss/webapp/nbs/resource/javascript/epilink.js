function validateEpilink()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;

	var sev= getElementByIdOrByName("epilinkId1");
	var sev2= getElementByIdOrByName("epilinkId2");

	if(sev != null && sev.value.length == 0) {
        errors[index++] = "Current Epi Link ID is required";
        getElementByIdOrByName("epilinkId1").style.color="990000";
		isError = true;
	}
	else{
		getElementByIdOrByName("epilinkId1").style.color="black";
	}
	if(sev2 != null && sev2.value.length == 0) {
        errors[index++] = "New Epi-Link ID is required";
        getElementByIdOrByName("epilinkId2").style.color="990000";
		isError = true;
	}
	else{
		getElementByIdOrByName("epilinkId2").style.color="black";
	}
	if(sev != null && sev.value.length > 20 ) {
        errors[index++] = "Current Epi Link ID format should be alphanumeric and less than 20 digits in length.";
        getElementByIdOrByName("epilinkId1").style.color="990000";
		isError = true;
	}
	if(sev2 != null && sev2.value.length > 20) {
        errors[index++] = "New Epi Link ID format should be alphanumeric and less than 20 digits in length.";
        getElementByIdOrByName("epilinkId2").style.color="990000";
		isError = true;
	}

    if(isError) {
        displayErrors("epilinkFormEntryErrors", errors);
	}
	return isError;
}


function backtomain(){

	document.forms[0].action ="/nbs/EpiLinkAdmin.do?method=returnLink&focus=systemAdmin7";
     
	}
function cancelForm(){

   	document.forms[0].action ="${epiLinkIdForm.attributeMap.submit}";
	document.forms[0].submit();
   }
	


function submitForm(){
	
	 var confirmMsg="Are you sure you want to merge all investigations with this Current Epi-Link ID into the New Epi-Link Group?  This is not reversible.  Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg))
        {
        	if(validateEpilink()) {
        		return false;
	        } else {
	        	document.forms[0].action ="/nbs/EpiLinkAdmin.do?method=submitMergeEpilink";
				document.forms[0].submit();
	        }
    }
	
}