function alertActiveConfirm(element) {

	var indicator = getElementByIdOrByName("userProfile.theUser_s.status").checked;
	var confirmMsg="The user you are making inactive may have one or more alerts associated to them. Please review existing Alert triggers to ensure this user is not mapped to any active alerts.";
	if(indicator==false) {
		if (confirm(confirmMsg)) {
				return true;
			} else {
				getElementByIdOrByName("Active").checked=true;
				return false;
		}
	}
}
function ReportingFacilityCheck(){
	
	//alert("call ReportingFacilityCheck");
	var labelList = new Array();
	var errorText = "Can you see this?";
	var external= getElementByIdOrByName("External");
	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("Org-ReportingOrganizationUID")) ;
	var facilityDetails = getElementByIdOrByName("Org-ReportingOrganizationUID");	
	
	if(external.checked && !facilityDetails.value>0)
	{
		errorText = makeErrorMsg('ERR141', labelList.concat("Reporting Facility"));
		tdErrorCell.className = "error";
		tdErrorCell.innerText = errorText;
		return true;
	}
	return false;
   }

function getTdErrorCell ( element ) {
   if( element )
   {
				// VL: The following finds <td> cell where error text needs to be displayed.
				var trNode = element.parentNode;

				while(trNode.nodeName!="TR" || !getCorrectAttribute(trNode,"rowID",trNode.rowID))
				{
					
					trNode = trNode.parentNode;
					if(trNode==null)
						return null;
				}


				var tableNode = trNode.parentNode;
				while(tableNode.nodeName!="TABLE")
				{
						tableNode = tableNode.parentNode;
				}

				var rowID = getCorrectAttribute(trNode,"rowID",trNode.rowID);
				
				var innerTdNodes = tableNode.getElementsByTagName( "td" );
				
				
				var tdErrorNode;
				
				// find matching <td> cell where error text needs to be displayed.
				
				for (var i=0; i < innerTdNodes.length; i++) {
					if(getCorrectAttribute(innerTdNodes.item(i),"rowID",innerTdNodes.item(i).rowID) == rowID)
						return innerTdNodes.item(i);
				}
				
				
    }
}

function wade(){
	var node = getElementByIdOrByName("userProfile.theRealizedRole_s[i].seqNum");
	//alert(getElementByIdOrByName("BatchEntryAddButtonRole").value);
	if(node.value=="" && getElementByIdOrByName("BatchEntryAddButtonRole").value=="Add Role")
		node.value = "b"+increment; 
		
	return false;
}

function AtleastOne(){
	//alert("call AtleastOne");
	
	var labelList = new Array();
	var errorText = "";
	var paa = getElementByIdOrByName("userProfile.theUser_s.paa");
	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("paaProgramArea")) ;
	var paaProgramAreaList = getElementByIdOrByName("paaProgramArea");
	//alert("paaProgramAreaList.nodeValue: " + paaProgramAreaList.nodeValue);
	//alert("paaProgramAreaList.nodeName: " + paaProgramAreaList.nodeName);
	if(paaProgramAreaList.nodeName == "SPAN"){
		//alert('node is readonly');
		return false;
	}
	
	var programAreasSelected = $j('#paaProgramArea').val();
	
	if(paa.checked && (programAreasSelected.length==0 || (programAreasSelected.length==1 && programAreasSelected[0]=="")))
	{
		errorText = "Please select at least one Program Area.";
		tdErrorCell.className = "error";
		tdErrorCell.innerText = errorText;
		return true;
	}
	return false;
	
   }


batchEntryValidationArray[0] = wade;

validationArray[0] = ReportingFacilityCheck;
validationArray[1] = AtleastOne;





