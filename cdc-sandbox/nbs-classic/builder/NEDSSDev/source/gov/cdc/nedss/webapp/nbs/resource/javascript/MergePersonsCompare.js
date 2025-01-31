function clickentityTop1()
{
	var frm = getElementByIdOrByName("nedssForm");
	//alert(frm.entityTop1.name);
	frm.entityBottom1.checked=frm.entityTop1.checked;
	frm.entityTop2.checked=!frm.entityTop1.checked;
	frm.entityBottom2.checked=!frm.entityTop1.checked;	
}

function clickentityTop2()
{
	var frm = getElementByIdOrByName("nedssForm");	
	frm.entityBottom2.checked=frm.entityTop2.checked;
	frm.entityBottom1.checked=!frm.entityTop2.checked;
	frm.entityTop1.checked=!frm.entityTop2.checked;
}

function clickentityBottom1()
{
	var frm = getElementByIdOrByName("nedssForm");
	frm.entityTop1.checked=frm.entityBottom1.checked
	frm.entityTop2.checked=!frm.entityBottom1.checked
	frm.entityBottom2.checked=!frm.entityBottom1.checked
}

function clickentityBottom2()
{
	var frm = getElementByIdOrByName("nedssForm");
	frm.entityTop2.checked=frm.entityBottom2.checked;
	frm.entityTop1.checked=!frm.entityBottom2.checked;
	frm.entityBottom1.checked=!frm.entityBottom2.checked;
}




function MergePersonsCompareSubmit()
{
	var frm = getElementByIdOrByName("nedssForm");	
	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	var errorText="";

	if ((!frm.entityTop1.checked) && (!frm.entityTop2.checked))
	{

		errorText = "Please select one of the two records as the surviving record.\n";
		errorTD.innerText = errorText;
		errorTD.setAttribute("className", "error");
		return true;	
	}
	else
	{
		return false;
	}
	

}


validationArray[0] = MergePersonsCompareSubmit;



