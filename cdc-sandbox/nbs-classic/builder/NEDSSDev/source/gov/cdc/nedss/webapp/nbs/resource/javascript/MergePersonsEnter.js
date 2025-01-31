function MergePersonsEnterSubmit()
{
	var frm = getElementByIdOrByName("nedssForm");	
	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	var errorText="";

	if ((frm.personId1.value == "") || (frm.personId2.value == ""))
	{

		errorText = "Please select two person records to compare.\n";
		errorTD.innerText = errorText;
		errorTD.setAttribute("className", "error");
		return true;	
		//return false;
	}
	else if (frm.personId1.value == frm.personId2.value)	
	{
		errorText = "Please select two different person records to compare.\n";
		errorTD.innerText = errorText;
		errorTD.setAttribute("className", "error");
		return true;	
	}
	else
	{
		return false;
	}

}


validationArray[0] = MergePersonsEnterSubmit;



