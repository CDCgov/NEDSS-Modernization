	
function findNameForValue(list, code)		{

	var name = "";
	var wholeList = getElementByIdOrByName(list);
	if (wholeList != null)	{
		var items = wholeList.value.split(codeSetPairDelimiter);
		for (var i=0; i < items.length-1; i++) {
			var nameValue = items[i].split(codeSetNVDelimiter);
			if (nameValue.length > 1)	{
				var codeValue = nameValue[0];
				var codeName = nameValue[1];
				if (codeValue == code)	{
					name = codeName;
				}
			}
		}
	}

	return name;
}

/**
 *  updates the patient information on the event tab with data that may have changed in patient tab
 *  @param 
 *  @return 
 *
 */
function updatePatientFromEventHeaderInfo(){
	//get data from the patient tab
	var lastNameNode = getElementByIdOrByName("entity.lastNm");
	var firstNameNode = getElementByIdOrByName("entity.firstNm");
	var dobNode = getElementByIdOrByName("entity.DOB");
	var sexNode = getElementByIdOrByName("entity.sex");
	var ssnNode = getElementByIdOrByName("entity.ssn");
	var streetAddr1 = getElementByIdOrByName("entity.streetAddr1");
	var city = getElementByIdOrByName("entity.city");
	var county = getElementByIdOrByName("cntyCd");
	var state = getElementByIdOrByName("entity.state");
	var zip = getElementByIdOrByName("entity.zip");
	

	var stateDesc = findNameForValue("liststateDesc",state.value);
	
	var noOtherAddressField = true;
	
	
	var countyDesc = findNameForCounty(county);
	var sexDesc = findNameForValue("listsexDesc",sexNode.value);
	
	var topGroupNode = getElementByIdOrByName("top-group");
	var tempStr = "";

	if(! streetAddr1.value =="")
	  tempStr = streetAddr1.value ;
	
	if(! city.value ==""){
	   if(streetAddr1.value !=="")
	   	tempStr = tempStr + ", ";
	  tempStr= tempStr + city.value ; 
	 } 	
	
	if(!zip.value ==""){
	   if(!tempStr =="")	
		tempStr= tempStr + " " ; 
	   tempStr = tempStr+zip.value;
	 }  
	
	if(tempStr!="")
		noOtherAddressField=false;
	//show default state only if other address info has been entered by user
	if(noOtherAddressField && state.value == getElementByIdOrByName("defaultState").value)
		stateDesc="";
		
		
	if(!stateDesc ==""){
	   if(!tempStr =="")	
		tempStr= tempStr + ", " ; 
	   tempStr = tempStr +stateDesc;
	}   	   
	
	
	
	if(!topGroupNode)
		return;
	
	var topGroupSpanNodes = document.getElementsByTagName("span");
	for (var i=0;i<topGroupSpanNodes.length;i++){
	                if(topGroupSpanNodes[i].id=="patientName"){
				topGroupSpanNodes[i].innerText = firstNameNode.value + " " + lastNameNode.value;
				}
			else if(topGroupSpanNodes[i].id=="dateOfBirth")
				topGroupSpanNodes[i].innerText = dobNode.value;
			else if(topGroupSpanNodes[i].id=="patientCurrentSex")
				topGroupSpanNodes[i].innerText = sexDesc;
			else if(topGroupSpanNodes[i].id=="ssn")
				topGroupSpanNodes[i].innerText = ssnNode.value;				
			else if(topGroupSpanNodes[i].id=="streetAddr1")	
				topGroupSpanNodes[i].innerText = tempStr;	
	}
	
}

/**

  * This is method used to get county description text from county 
  * dropdown values.
  * @param value is select dropdown ElementById
 */

function findNameForCounty(county)		{
	var name = "";	
	if (county != null)
	{
	    var value = county.value;
		for (var v=0; v < county.options.length; v++)	
		{		  
			if (county.options[v].value == value) {
				name = county.options[v].text;
			}
		}		  
	}
	return name;
}



tabSwitchFunctionArray[0] = updatePatientFromEventHeaderInfo;


/**
 *  DOB logic for tab switch
 *  @param 
 *  @return 
 *
 */
function updatePatientFromEventDOBInfo(element){
	
	var dobNode = getElementByIdOrByName("DOB");	
	var asOfDateNode = getElementByIdOrByName("entity.asofdate");
	var reportedAgeNode = getElementByIdOrByName("entity.age");
	var reportedAgeUnitsNode = getElementByIdOrByName("entity.ageUint");
	
	var labelList = new Array();
	var errorMessage = "";
	var errorText = "";	
	tdErrorCell = getTdErrorCell(element) ;
	tdErrorCell.innerText = "";
	
	if (isblank(element.value)){	
		return true;
	}	

	if (!isDate( element.value ) ) {

		if( !element.fieldLabel )

			errorText = makeErrorMsg('ERR011', labelList);
		else {					

			errorText = makeErrorMsg('ERR003', labelList.concat(element.fieldLabel));					
		}


		if( tdErrorCell.innerText == "" )
		  tdErrorCell.innerText = errorText;
		else
		  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		tdErrorCell.className = "error";

		return false;

	}
	if ((CompareDateStrings(element.value, "12/31/1875") == -1) ||   (CompareDateStringToToday(element.value) == 1))  
	{
	
		errorText = makeErrorMsg('ERR004', labelList.concat(element.fieldLabel));

		if( tdErrorCell.innerText == "" )
		  tdErrorCell.innerText = errorText;
		else
		  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
		tdErrorCell.className = "error";

		return false;
	} 
	
	//figure out the reported age and units
	//don't show if calc dob is empty
	if(dobNode.value!="")	{
	
		var asOfDate = new Date(asOfDateNode.value);
		var dobDate = new Date(dobNode.value);
		//reported age = as of date - calc dob
		var reportedAgeMilliSec = asOfDate.getTime() - dobDate.getTime();
		if(!window.isNaN(reportedAgeMilliSec)){
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30;
			var reportedAgeYears = reportedAgeMonths/12;
			if(Math.ceil(reportedAgeDays)<=28){
				reportedAgeNode.value=Math.floor(reportedAgeDays);
				reportedAgeUnitsNode.value="D";
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
				reportedAgeNode.value=Math.floor(reportedAgeMonths);
				reportedAgeUnitsNode.value="M";
			} else	{
				reportedAgeNode.value=Math.floor(reportedAgeYears);
				reportedAgeUnitsNode.value="Y";
			}
					
		} else {
			reportedAgeNode.value="";
			reportedAgeUnitsNode.value="";
		}
	}
	//clear reported age and units if calc dob is blank
	else	{
		reportedAgeNode.value="";
		reportedAgeUnitsNode.value="";
	}
}

/**
 * Description:	initializes the child window and then opens a new browser instance of child window for specified url
 *
 * param @type	name of of the nested type on the page
 */

function entitySearchlabPatientSelect(uid){	


	var parent = window.opener;
	var parentDoc = parent.document;

	var entityTable = parentDoc.getElementById(parent.whichEntity);	
	var tdNode = getElementByIdOrByName(uid);	
	var inputNodes = tdNode.getElementsByTagName("input");
	
	var lastname="";
	var firstname="";
	var middlename="";
	var suffix="";
	var degree="";
	var email="";
	var phone="";
	var ext="";
	var personUID="";
	var entityPersonParentUID="";	
	var localID=""
	var personSearchResult="";
	var reporterSearchResult="";
	var entitystateValue ="";
	var city="";
	var state="";
	var countyList="";
	var zip="";
	var address1="";
	var address2="";
	var county="";
	var ethnicity="";
	var DOB="";
	var entityDateOfDeath="";	
	var tempCounty;
	var unknown="";
	var american="";
	var asian="";
	var black="";
	var native="";
	var white="";
	var other="";
	var otherDesc="";
	var entityAsOfDate="";
	var entityAge="";
	var entityAgeUnit="";
	var entitySex="";
	var entityMaritalStatus="";
	var entitySSN="";
	var displayLocalID="";
	

	
	for (var i=0; i < inputNodes.length; i++){
		if(inputNodes.item(i).name!=null){
			if(inputNodes.item(i).name=="entity.asofdate")
			{
				entityAsOfDate = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.age")
			{
				
				entityAge = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.ageUnit")
			{
				
				entityAgeUnit = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.sex")
			{
				entitySex = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.maritalStatus")
			{
				entityMaritalStatus = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.ssn")
			{
				entitySSN = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.lastNm")
			{
				lastname = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.firstNm")
			{	
				firstname = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.middleNm")
			{
				middlename = inputNodes.item(i).value;				

			}
			if(inputNodes.item(i).name=="entity.DOB")
			{
				DOB = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.dateOfDeath")
			{
				entityDateOfDeath = inputNodes.item(i).value;
			}				
			if(inputNodes.item(i).name=="entity.emailAddress")
			{	
				email = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.phoneNbrTxt")
			{		   
				phone = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.extensionTxt")
			{	
				ext = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.personUID")
			{	
				personUID = inputNodes.item(i).value;
			}	
			if(inputNodes.item(i).name=="entity.personParentUID")
			{			     
				entityPersonParentUID= inputNodes.item(i).value;
			}			
			if(inputNodes.item(i).name=="entity.localID")
			{
				localID = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.displayLocalId")
			{
			      	displayLocalID = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.city")
			{
				city = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.state")
			{
				state = inputNodes.item(i).value;				
			}
			if(inputNodes.item(i).name=="entity.stateValue")
			{
			  
				entitystateValue = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.countyList")
			{			  
				countyList = inputNodes.item(i).value;
			}							 
			if(inputNodes.item(i).name=="entity.county")
			{
				county = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.ethnicity")
			{
				ethnicity = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.zip")
			{
				zip = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.address1")
			{
				address1 = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.address2")
			{
				address2 = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.suffix")
			{
				suffix = inputNodes.item(i).value;				
			}
			if(inputNodes.item(i).name=="entity.degree")
			{
				degree = inputNodes.item(i).value;
			}

			
			if(inputNodes.item(i).name=="entity.unknown")
			{
				unknown = inputNodes.item(i).value;				
			}
			
			if(inputNodes.item(i).name=="entity.american")
			{ 			   
				american = inputNodes.item(i).value;		
			}
			if(inputNodes.item(i).name=="entity.asian")
			{
				asian = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.black")
			{
				black = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.native")
			{
				native = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.white")
			{
				white = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="entity.other")
			{
				other = inputNodes.item(i).value;
				
			}		
			if(inputNodes.item(i).name=="entity.otherDesc")
			{
				otherDesc = inputNodes.item(i).value;
				
			}
			if(inputNodes.item(i).name=="entity.completePersonSearchResult")
			{
				personSearchResult = firstname;
				if(lastname != "")
					personSearchResult = (personSearchResult + " " + lastname);
				if(suffix != "")
					personSearchResult = (personSearchResult + ", " + suffix);
				if(degree != "")
					personSearchResult = (personSearchResult + ", " + degree);
				if(address1 != "")
					personSearchResult = (personSearchResult + "<br>" +address1);
				if(address2 != "")
					personSearchResult = (personSearchResult + "<br>" +address2);
				if(city != "")
					personSearchResult = (personSearchResult + "<br>" +city);
				if(city != "" && state !="")
					personSearchResult = (personSearchResult + ", ");
				else
					personSearchResult = (personSearchResult + "<br>");	
				if(state != "")
					personSearchResult = (personSearchResult + " " + state);
				if(zip != "")
					personSearchResult = (personSearchResult + " " + zip);
				if(phone != "")
					personSearchResult = (personSearchResult + "<br>" +phone);
				if(phone != "" && ext != "")
					personSearchResult = (personSearchResult + " ext ");
				if(ext != "")
					personSearchResult = (personSearchResult + "<br>" +ext);
			}
			if (inputNodes.item(i).name=="entity.reporterPersonSearchResult")
			{
				reporterSearchResult = firstname;
				if(lastname != "")
					reporterSearchResult = (reporterSearchResult + " " + lastname);
				if(phone != "")
					reporterSearchResult = (reporterSearchResult + "<br>" +phone);
				if(phone != "" && ext != "")
					reporterSearchResult = (reporterSearchResult + " ext ");
				if(ext != "")
					reporterSearchResult = (reporterSearchResult + "<br>" +ext);
			}
			
		}
	}
	
	var SSN1="";
	var SSN2="";
	var SSN3="";
	var TELE1="";
	var TELE2="";
	var TELE3="";
	if(entitySSN != "")
	{	   
	   SSN1 = entitySSN.substring(0,3);
	   SSN2 = entitySSN.substring(4,6);
	   SSN3 = entitySSN.substring(7,11);	   
	}
	if(phone != "")
	{
	   TELE1 = phone.substring(0,3);
	   TELE2 = phone.substring(4,7);
	   TELE3 = phone.substring(8,12);
	}
	
	var spans = entityTable.getElementsByTagName("span");
	for(var i=0;i<spans.length;i++){
		if(spans.item(i).id=="entity.lastNm")
		{
			spans.item(i).innerHTML = lastname;
		}
		else if(spans.item(i).id=="entity.firstNm")
		{	
			spans.item(i).innerHTML = firstname;
		}
		else if(spans.item(i).id=="entity.emailAddress")
		{	
			spans.item(i).innerHTML = email;
		}
		else if(spans.item(i).id=="entity.phoneNbrTxt")
		{	
			spans.item(i).innerHTML = phone;
		}
		else if(spans.item(i).id=="entity.extensionTxt")
		{	
			spans.item(i).innerHTML = ext;	
		}
		else if(spans.item(i).id=="entity.localID")
		{	
			spans.item(i).innerHTML = localID;	
		}
		else if(spans.item(i).id=="entity.displayLocalId")
		{	
			spans.item(i).innerHTML = displayLocalID;	
		}
		else if(spans.item(i).id=="entity.city")
		{	
			spans.item(i).innerHTML = city;	
		}
		else if(spans.item(i).id=="entity.state")
		{	
			spans.item(i).innerHTML = state;	
		}
		else if(spans.item(i).id=="entity.zip")
		{	
			spans.item(i).innerHTML = zip;	
		}
		else if(spans.item(i).id=="entity.address1")
		{	
			spans.item(i).innerHTML = address1;	
		}
		else if(spans.item(i).id=="entity.address2")
		{	
			spans.item(i).innerHTML = address2;	
		}
		else if(spans.item(i).id=="entity.suffix")
		{	
			spans.item(i).innerHTML = suffix;	
		}
		else if(spans.item(i).id=="entity.degree")
		{	
			spans.item(i).innerHTML = degree;	
		}
		else if(spans.item(i).id=="entity.completePersonSearchResult")
		{	
			spans.item(i).innerHTML = personSearchResult; 
		}
		else if(spans.item(i).id=="entity.reporterPersonSearchResult")
		{	
			spans.item(i).innerHTML = reporterSearchResult; 
		}
		

		// check for empty condition
		if(spans.item(i).innerHTML=="")
			spans.item(i).innerHTML ="&nbsp;&nbsp;";
	}
	
	var spanNode = getElementByIdOrByNameNode("patient.ID", parentDoc);
			
		if(spanNode != null){
		//alert("displayLocalID: " + displayLocalID);
		var newB = parentDoc.createElement("b");
		var newText =parentDoc.createTextNode("Patient ID: ");
		var newText21 =parentDoc.createTextNode(displayLocalID);
		newB.appendChild(newText);
		spanNode.innerText="";
		spanNode.appendChild(newB);
		//spanNode.appendChild(": ");
		spanNode.appendChild(newText21);
		
		spanNode.className="visible";
	}
	var localIdHiddenNode = getElementByIdOrByNameNode("patient.thePersonDT.localId", parentDoc);
	if(localIdHiddenNode!=null)
	localIdHiddenNode.value=localID;
			
	var entityCountyNode = getElementByIdOrByNameNode("entity.county", parentDoc);

	var inputs = entityTable.getElementsByTagName("input");
	var selects = entityTable.getElementsByTagName("select");

	
	for(var i=0;i<selects.length;i++){
		if(getElementByIdOrByNameNode(selects.item(i).name +"_textbox", parentDoc))
			getElementByIdOrByNameNode(selects.item(i).name +"_textbox", parentDoc).value="";
		
		
		if(selects.item(i).id == "entity.state")
		{    
			selects.item(i).value=entitystateValue;
			var varOptions = selects.item(i).options;
			if(selects.item(i).selectedIndex>-1){			
				getElementByIdOrByNameNode(selects.item(i).name +"_textbox",parentDoc).value=varOptions[selects.item(i).selectedIndex].text;//fatima
				//parentDoc.getElementById(selects.item(i).name +"_textbox").value=varOptions[selects.item(i).selectedIndex].text;
				createCountyDropDown(countyList, entityCountyNode, parentDoc);
			}else{
				getElementByIdOrByNameNode(selects.item(i).name +"_textbox",parentDoc).value="";
				//parentDoc.getElementById(selects.item(i).name +"_textbox").value="";
				if(entityCountyNode!=null)
				{
				
					var removeOpt = entityCountyNode.firstChild;

					while(removeOpt != null){
					  var temp = removeOpt.nextSibling;
					  entityCountyNode.removeChild(removeOpt);
					  removeOpt= temp;
					}
					entityCountyNode.className="none";
				}
			}
		}
		if(selects.item(i).id == "entity.county")
		{
			selects.item(i).value = county;
			var varOptions = selects.item(i).options;
			if(selects.item(i).selectedIndex>-1)
				getElementByIdOrByNameNode(selects.item(i).name +"_textbox",parentDoc).value=varOptions[selects.item(i).selectedIndex].text;
				//parentDoc.getElementById(selects.item(i).name +"_textbox").value=varOptions[selects.item(i).selectedIndex].text;
			else
				getElementByIdOrByNameNode(selects.item(i).name +"_textbox",parentDoc).value="";
				//parentDoc.getElementById(selects.item(i).name +"_textbox").value="";
			
		}
		if(selects.item(i).id == "cntyCd")
		{				
			selects.item(i).value = county;
		}					
		if(selects.item(i).id == "entity.suffix")
		{	
			selects.item(i).value=suffix;			
		}
		if(selects.item(i).id == "entity.ethnicity")
		{	
			selects.item(i).value=ethnicity;	
		}
		if(selects.item(i).id == "entity.sex")
		{	
			selects.item(i).value=entitySex;			
		}
		if(selects.item(i).id == "entity.ageUnit")
		{	
			selects.item(i).value=entityAgeUnit;	
		}
		if(selects.item(i).id == "entity.maritalStatus")
		{
			selects.item(i).value=entityMaritalStatus;	
		}
		if(selects.item(i).id == "entity.personUID")
		{
				selects.item(i).value= personUID;	
		}
		if (selects.item(i).onclick){
			selects.item(i).onclick();
		}
	
	}
	
	for(var i=0;i<inputs.length;i++){
		if(inputs.item(i).type=="hidden")
		{
			if(getCorrectAttribute(inputs.item(i), "mode",inputs.item(i).mode)=="uid")
			inputs.item(i).value=personUID;
			
			if(inputs.item(i).id=="entity.personParentUID")
			{
			   inputs.item(i).value=entityPersonParentUID;
			}
		}
		else if(inputs.item(i).type=="text")
		{

			if(inputs.item(i).id== "entity.lastNm"){
				inputs.item(i).value=lastname;
			}
			if(inputs.item(i).id== "entity.suffix"){
				inputs.item(i).value=suffix;
			}
			else if(inputs.item(i).id== "entity.firstNm"){
				inputs.item(i).value=firstname;
			}
			else if(inputs.item(i).id== "entity.middleNm"){
				inputs.item(i).value=middlename;
			}
			else if(inputs.item(i).id== "entity.DOB"){
				inputs.item(i).value=DOB;
			}
		    	else if(inputs.item(i).id == "entity.dateOfDeath")
		    	{	
			      inputs.item(i).value=entityDateOfDeath;			
		     	}					
			else if(inputs.item(i).id== "entity.emailAddress"){
				inputs.item(i).value=email;
			}
			else if(inputs.item(i).id== "entity.phoneNbrTxt"){
				inputs.item(i).value=phone;
			}
			else if(inputs.item(i).id== "entity.extensionTxt"){
				inputs.item(i).value=ext;
			}
			else if(inputs.item(i).id=="entity.streetAddr1")
			{	
				inputs.item(i).value=address1;
			}
			else if(inputs.item(i).id=="entity.city")
			{	
				inputs.item(i).value=city;
			}
			else if(inputs.item(i).id=="entity.phoneNbrTxt1")			
			{	
				inputs.item(i).value= TELE1;
				if(TELE1!=null && TELE1.length > 0)
				inputs.item(i).onchange();		
			}
			else if(inputs.item(i).id=="entity.phoneNbrTxt2")
			{
				inputs.item(i).value=TELE2;
				if(TELE2!=null && TELE2.length > 0)
				inputs.item(i).onchange();						
				
			}
			else if(inputs.item(i).id=="entity.phoneNbrTxt3")
			{	
				inputs.item(i).value=TELE3;
				if(TELE3!=null && TELE3.length > 0)
				inputs.item(i).onchange();						
			}
			else if(inputs.item(i).id=="entity.zip")
			{	
				inputs.item(i).value=zip;
			}
			else if(inputs.item(i).id =="entity.age")
			{
				
				inputs.item(i).value=entityAge;
			}
			else if(inputs.item(i).id=="entity.ssn1")
			{
				inputs.item(i).value=SSN1;
				if(SSN1!=null && SSN1.length!="")
				inputs.item(i).onchange();					
			}
			else if(inputs.item(i).id=="entity.ssn2")
			{
				inputs.item(i).value=SSN2;
				if(SSN2!=null && SSN2.length!="")
				inputs.item(i).onchange();					
			}
			else if(inputs.item(i).id=="entity.ssn3")
			{
				inputs.item(i).value=SSN3;
				if(SSN3!=null && SSN3.length!="")
				inputs.item(i).onchange();					
			}
			else if(inputs.item(i).id=="entity.personUID")
			{
				inputs.item(i).value=personUID;
			}
			// other race desc text 
			else if(inputs.item(i).id=="DEM151")
			{
				inputs.item(i).value=otherDesc;
				
			}
		}
		else if(inputs.item(i).type=="checkbox"){		
		 	if(inputs.item(i).id=="DEM152")
			{	
				if(unknown=="Y")
					inputs.item(i).checked=true;
				else
				        inputs.item(i).checked=false;      
			}
			else if(inputs.item(i).id=="DEM153")
			{	

				if(american=="Y")
				{
					inputs.item(i).checked=true;
				}
				else
				        inputs.item(i).checked=false;

			}
			else if(inputs.item(i).id=="DEM154")
			{

				if(asian=="Y")
				{
					inputs.item(i).checked=true;
				}
				else 
				        inputs.item(i).checked=false;  

			}
			else if(inputs.item(i).id=="DEM155")
			{	
				if(black=="Y")
					inputs.item(i).checked=true;
				else 
				        inputs.item(i).checked=false;		
			}
			else if(inputs.item(i).id=="DEM156")
			{
				if(native=="Y")
					inputs.item(i).checked=true;
				else 
				        inputs.item(i).checked=false;    
			}
			else if(inputs.item(i).id=="DEM157")
			{
				if(white=="Y")
					inputs.item(i).checked=true;
				else
				        inputs.item(i).checked=false;
			}
			else if(inputs.item(i).id=="DEM158")
			{	
				var visibleFlag = getElementByIdOrByNameNode("raceDescTxt", parentDoc);
				if(other=="Y")
				{
					inputs.item(i).checked=true;
					visibleFlag.setAttribute("className", "visible");
				}
				else
				{
					inputs.item(i).checked=false;
					visibleFlag.setAttribute("className", "none");
				}	
			}
		}
	}
	var dobNode = getElementByIdOrByNameNode("entity.DOB", parentDoc);
	var calcDOBNode = getElementByIdOrByNameNode("calcDOB", parentDoc);
	var asOfDateNode = getElementByIdOrByNameNode("entity.asofdate", parentDoc);

	var reportedAgeNode = getElementByIdOrByNameNode("entity.age", parentDoc);
	var reportedAgeUnitsNode = getElementByIdOrByNameNode("entity.ageUnit", parentDoc);
		
   entitySearchCalcDOBInfo(dobNode,  dobNode,  asOfDateNode,  reportedAgeNode, reportedAgeUnitsNode);


	window.close();
}


function entitySearchCalcDOBInfo(dobNode,  calcDOBNode,  asOfDateNode,  reportedAgeNode, reportedAgeUnitsNode){
	

	
	
	//if dob node is not empty set the calc dob to dob
	if(dobNode.value!="" && isDate(dobNode.value)){
		calcDOBNode.value = dobNode.value;
		//calcDOB changed change the reported age	
	}
	var asOfDate = new Date(asOfDateNode.value);
	var calcDOBDate = new Date(calcDOBNode.value);
	
	//check for error condition
	if(asOfDate<calcDOBDate)
		return;
	
	//figure out the reported age and units
	//dont show if calc dob is empty
	if (calcDOBNode!=null && calcDOBNode.value!="" && asOfDateNode!=null)	{
		
		//reported age = as of date - calc dob
		var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
		if(!window.isNaN(reportedAgeMilliSec)){
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30.41;
			var reportedAgeYears = reportedAgeMonths/12;
			
			if(isLeapYear(calcDOBDate.getFullYear())) reportedAgeMonths = Math.floor(reportedAgeDays)/30.5;

			if(Math.ceil(reportedAgeDays)<=28){
				reportedAgeNode.value=Math.floor(reportedAgeDays);
				reportedAgeUnitsNode.value="D";
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
				reportedAgeNode.value=Math.floor(reportedAgeMonths);
				reportedAgeUnitsNode.value="M";
			} else	{
				// get rough estimated year age
				var yearDiff = asOfDate.getFullYear() - calcDOBDate.getFullYear();
				//need to determine whether birthday has happened 
				if(asOfDate.getMonth()<calcDOBDate.getMonth())
					yearDiff = yearDiff-1;
				else if(asOfDate.getMonth()==calcDOBDate.getMonth()){
					if(asOfDate.getDate()<calcDOBDate.getDate())
						yearDiff = yearDiff-1;
				}
				reportedAgeNode.value=yearDiff;//Math.floor(reportedAgeYears);
				reportedAgeUnitsNode.value="Y";
                    //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
				if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && reportedAgeYears > 1 && reportedAgeYears < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
				{
				    currentAgeNode.innerText="11";
				    currentAgeUnitsNode.innerText="Months";
				}					
			}
					
		} else {
			reportedAgeNode.value="";
			reportedAgeUnitsNode.value="";
		}
	}
	//clear reported age and units if calc dob is blank
	else	{
		
		reportedAgeNode.value="";
		reportedAgeUnitsNode.value="";
	}
	reportedAgeUnitsNode.onclick();
}

function isLeapYear(varyear)
{
       var leapyear = false;
       if((varyear% 4) == 0) leapyear = true;
       if((varyear% 100) == 0) leapyear = false;
       if((varyear% 400) == 0) leapyear = true;
       return leapyear;
       
}



function updatePatientFromEventCalcDOBInfo(){
	var dobNode = getElementByIdOrByName("entity.DOB");
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	var asOfDateNode = getElementByIdOrByName("entity.asofdate");

	var reportedAgeNode = getElementByIdOrByName("entity.age");
	var reportedAgeUnitsNode = getElementByIdOrByName("entity.ageUnit");
	
	if(dobNode.value=="" && reportedAgeNode.value==""){
		calcDOBNode.value="";
		
	} else if(dobNode.value!="" && isDate(dobNode.value)){
				calcDOBNode.value = dobNode.value;
		//calcDOB changed change the reported age	
	} else if(asOfDateNode.value!="" && reportedAgeNode.value!="") {
		var asOfDate = new Date(asOfDateNode.value);
		if(calcDOBNode.value=="")
			var calcDOBDate = new Date();
		else
			var calcDOBDate = new Date(calcDOBNode.value);
		// calcDOB = AOD - reported age
		if(reportedAgeUnitsNode.value=="Y"){
			// convert years into milliseconds
			//alert("asOfDate.getTime() = "+ asOfDate.getTime() +" reportedAgeNode.value*31104000000 =  " + reportedAgeNode.value*31104000000);
			
			
			var newYear = asOfDate.getFullYear() - reportedAgeNode.value;
			//alert(newYear);
			//var newDate = asOfDate.getTime() - (reportedAgeNode.value*31104000000);
			
			calcDOBNode.value = DateToString(new Date(asOfDate.setFullYear(newYear)));
			//alert(calcDOBNode.value);
			
		}else if(reportedAgeUnitsNode.value=="M"){
		
			
			var newMonth = (asOfDate.getMonth()) - (reportedAgeNode.value % 12);
			if(newMonth<1){
				newMonth = 12 - (Math.abs(newMonth)%12);
				
			}
			// figure out the year
			if((asOfDate.getMonth() - reportedAgeNode.value) < 0){
				var newYear = asOfDate.getFullYear() - (Math.ceil(reportedAgeNode.value/12));
				asOfDate = new Date(asOfDate.setFullYear(newYear));
			}
			//var newDate = asOfDate.getTime() - (reportedAgeNode.value*2592000000);
						
			calcDOBNode.value = DateToString(new Date(asOfDate.setMonth(newMonth)));
		}else if(reportedAgeUnitsNode.value=="D"){
			var newDate = asOfDate.getTime() - (reportedAgeNode.value*86400000);
						
			calcDOBNode.value = DateToString(new Date(newDate));
		
		}else if(reportedAgeUnitsNode.value=="W"){
					var newDate = asOfDate.getTime() - (reportedAgeNode.value*604800000);
								
					calcDOBNode.value = DateToString(new Date(newDate));
		
		}else if(reportedAgeUnitsNode.value=="H"){
					var newDate = asOfDate.getTime() - (reportedAgeNode.value*3600000);

					calcDOBNode.value = DateToString(new Date(newDate));
		}
	}
	
	//return false because we arent doing any validation check here
	return false;
}

function createCountyDropDown(countyStr, countyNode, parentDoc)
{
       var countyItems;
       if(countyStr!=null && countyStr!="")
       countyItems = countyStr.split("|");
       
       
	   var x=0;
	   var y=0;
	   
	  
	   if(countyNode!=null)
	   {
		/*
			y = countyNode.options.length;       
		    for(x=y-1; x>-1; x--)
		    {
		        countyNode.remove(x);
		    }
		    */
		var removeOpt = countyNode.firstChild;
		    
		while(removeOpt != null){
		  var temp = removeOpt.nextSibling;
		  countyNode.removeChild(removeOpt);
		  removeOpt= temp;
		}
		countyNode.className="none";
	    }
	   
	    	
		
	    
	    
	    
	    if(countyItems!=null && countyItems.length>0 && countyNode!=null)
	    { 
	        var line="";
	        var nameVal="";
	        var cd="";
		   var desc="";
		   var elem;
		   var tnode;
		    y = countyItems.length;
		    for(x=0; x<y; x++)
		    {
		        
		        line=countyItems[x];
		        nameVal=line.split("$");	        
		        cd= nameVal[0];
		        desc= nameVal[1];
		        elem = parentDoc.createElement("option");
		        elem.setAttribute("value", cd);
		        tnode= parentDoc.createTextNode(desc);
		        elem.appendChild(tnode);
		        countyNode.appendChild(elem);
		    }
	    }       
     
}

function leftNavPopulateAsOfDates() {

	var asOfDate = getElementByIdOrByName("entity.asofdate").value;
	var tmpAsOfDate = asOfDate;
	
	//Sex and Birth AOD
	var currentSexValue = getElementByIdOrByName("entity.sex").value;	
	var dobValue = getElementByIdOrByName("entity.DOB").value;
	var age = getElementByIdOrByName("entity.age").value;
	if(currentSexValue.length > 0 || dobValue.length > 0 || age.length > 0) {
	
		getElementByIdOrByName("patient.thePersonDT.asOfDateSex_s").value = tmpAsOfDate;

	}
	
	
	//Mortality AOD
	var deceasedTimeValue = getElementByIdOrByName("entity.dateOfDeath").value;		
	
	if(deceasedTimeValue.length > 0) {
	
		getElementByIdOrByName("patient.thePersonDT.asOfDateMorbidity_s").value = tmpAsOfDate;
	}
	
	//MaritalStatusAOD
	
	var maritalStatusValue = getElementByIdOrByName("entity.maritalStatus").value;
	
	if(maritalStatusValue.length > 0) {
	
		getElementByIdOrByName("patient.thePersonDT.asOfDateGeneral_s").value = tmpAsOfDate;
	}
	
	
	//EthnicityAOD
	var ethnicityValue = getElementByIdOrByName("entity.ethnicity").value;
	if(ethnicityValue.length > 0) {
		getElementByIdOrByName("patient.thePersonDT.asOfDateEthnicity_s").value = tmpAsOfDate;
	}
	
	
	
	//general comments AOD

	var generalCommentsValue = getElementByIdOrByName("patient.thePersonDT.description").value;

	if(generalCommentsValue.length != 0) {		
		
		getElementByIdOrByName("patient.thePersonDT.asOfDateAdmin_s").value = tmpAsOfDate;
	}
		
   //phone AOD
   
   var phoneValue = getElementByIdOrByName("entity.phoneNbrTxt").value;


	if(phoneValue.length > 0) {		
		
		getElementByIdOrByName("telephoneAsOfDate").value = tmpAsOfDate;   
	
	}	

return false;	
}//leftNavPopulateAsOfDates

validationArray[1] = updatePatientFromEventCalcDOBInfo;
validationArray[2] =leftNavPopulateAsOfDates;
