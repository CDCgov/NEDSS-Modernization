
function PermissionSetSubmit(){
	//return false if no problems
	
		var errorTD = getElementByIdOrByName("errorRange");
		errorTD.setAttribute("className", "none");
		var errorText="";
	
		var inputs = document.getElementsByTagName("input");
		
		var bAtLeastOne=false;
		for(i=0;i<inputs.length;i++)
		{
			if(inputs.item(i).type=="checkbox" && inputs.item(i).checked)
			{
				bAtLeastOne=true;
				break;
			}
		}
		
		
		if( bAtLeastOne == false ) {
			
			errorText = "Please check at least one permission and try again.\n";
					
			setAttributeClass(errorTD, "error");
			setText(errorTD,errorText);
			
		} 
		
		
	return !(bAtLeastOne);
}

validationArray[0] = PermissionSetSubmit;
