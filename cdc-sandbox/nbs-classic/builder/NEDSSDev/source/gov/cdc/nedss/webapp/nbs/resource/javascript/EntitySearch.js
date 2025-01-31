var whichEntity="";
var typeOfEntity="";
var labIndicator="";
var labProgAreaCode ="";
var targetResultedTest ="";
var morbIndicator = false;
/**
 * Description:	initializes the child window and then opens a new browser instance of child window for specified url
 *
 * param @type	name of of the nested type on the page
 */
function openEntitySearchWindow(type, table,VO){
    
    //alert("searching for type" + type + "Table name : " + table );
    
	var x = 10;	//screen.availWidth;
	var y = 10;	//screen.availHeight;
	
	// *** make popup screen size more dynamic ***
	
	// save the height set in Globals.js
	var oPopUpHeight = popUpHeight;
	
	// get available height and width
	var availW = window.screen.availWidth;
	var availH = window.screen.availHeight;
	
	// the ideal window height plus space at top and bottom
	var reqH = 810;

	// if available is more than ideal use ideal else available
	if (availH > reqH) {
		popUpHeight = reqH;
	} else {
		popUpHeight = availH;
	}
	
	// leave a little space at the bottom, doesn't apparently work on IE
	popUpHeight -= (y+10);
	
	// if the height is less that that set in Globals.js use the default
	if (popUpHeight < oPopUpHeight) {
		popUpHeight = oPopUpHeight;  
	}
	
	//alert(popUpHeight+":"+y+":"+availH);

	typeOfEntity=type;
	whichEntity=table;
	labIndicator="";
	//entity-table-orderingProvider.personUid
	var entityLabel = getEntityTitle(whichEntity);

	var labId;
	var clia;
	if (getElementByIdOrByName("labId") != null){
		labId = getElementByIdOrByName("labId").value;
	}else{
		labId = "";
	}

	if (getElementByIdOrByName("clia") != null){
		clia = getElementByIdOrByName("clia").value;
	}else{
		clia = "";
	}


	if(type=="person"){

	ChildWindowHandle = window.open("/nbs/LoadFindPatient3.do?ContextAction=EntitySearch&mode=new&PersonName=" +entityLabel+"&VO="+VO+  "","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="labpatient"){

		ChildWindowHandle = window.open("/nbs/LoadLabSpecificPatient.do?ContextAction=EntitySearch&mode=new&PersonName=" +entityLabel+"&VO="+VO+  "","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}

	if(type=="organizationReportLab"){
	entitySearchClear(table);
	var tableNode = getElementByIdOrByName(table);
	var errorNode = getTdErrorCell(tableNode);
	errorNode.className="error";
	errorNode.innerText ="";

	var labelList = new Array();
		var confirmMsg = makeErrorMsg('ERR124', labelList.concat(""));
		var labId = getElementByIdOrByName("labId");
		var orderedTxt = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt");
		//alert("orderedTxt"+ orderedTxt.value);
		var TestNameText = getElementByIdOrByName("OrderedDisplay");
		//alert("TestNameText.value 123is :" + TestNameText.value);
		var returnVariable = true;

		if(TestNameText!= null)
		{
		//alert("TestNameText.value:"+TestNameText.value);
			var spanNodes = TestNameText.getElementsByTagName("span");
			for(var i=0;i<spanNodes.length;i++)
			{
				//alert("spanNodes.item(i).innerHTML is :"+ spanNodes.item(i).innerHTML);
				if(trim(labId.value)!= "" && (trim(spanNodes.item(i).innerHTML!= "")))
				{
					var returnVariable = confirm(confirmMsg);
					showErrorStatement= returnVariable;
				}

				spanNodes.item(i).innerHTML = "&nbsp;&nbsp;";
			}
			TestNameText.className = "none";
			TestNameText.value = "";
		}

		else
		{
			if(trim(labId.value)!= "" && (trim(orderedTxt.value)!=""))
			{
				var returnVariable = confirm(confirmMsg);
				showErrorStatement= returnVariable;
			}
		}

		if(returnVariable)
		{
		//	alert(getElementByIdOrByName("programAreaCd1"));
		//	alert(getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value);

			if(getElementByIdOrByName("programAreaCd1")!= null)
			{
				//alert("1");
				labProgAreaCode =getElementByIdOrByName("programAreaCd1").value;
			}
			else
			{
				//alert("2 1");
				labProgAreaCode = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;
				//alert("3");
			}

			//labProgAreaCode = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;
			targetResultedTest = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt\" header=\"Resulted Test Name\" parent=\"Test Result\" isNested=\"yes\" validate=\"required\" fieldLabel=\"Resulted Test Name\" errorCode=\"ERR001\" onchange=\"getOrganismIndicator();";
			labIndicator = "true"
			ChildWindowHandle = window.open("/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		}
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
}
	if(type=="organizationReportMorb"){
		entitySearchClear(table);
		var tableNode = getElementByIdOrByName(table);
		var errorNode = getTdErrorCell(tableNode);
		errorNode.className="error";
		errorNode.innerText ="";
		morbIndicator = true;
		labProgAreaCode = getElementByIdOrByName("morbidityReport.theObservationDT.progAreaCd").value;
		targetResultedTest ="labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt";

		labIndicator = "true"
		ChildWindowHandle = window.open("/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="organization"){
		if (getElementByIdOrByName("labId") != null)
		{
			entitySearchClear(table);
			var tableNode = getElementByIdOrByName(table);
			var errorNode = getTdErrorCell(tableNode);
			errorNode.className="error";
			errorNode.innerText ="";
		}
		ChildWindowHandle = window.open("/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}


	if(type=="provider"){
	
		if (getElementByIdOrByName("labId") != null)
		{
			entitySearchClear(table);
			var tableNode = getElementByIdOrByName(table);
			var errorNode = getTdErrorCell(tableNode);
			errorNode.className="error";
			errorNode.innerText ="";
		}
	
		ChildWindowHandle = window.open("/nbs/LoadFindProvider3.do?ContextAction=EntitySearch&mode=new&PersonName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="OrderedTestName"){

		ChildWindowHandle = window.open("/nbs/LoadFindLabReportTestName3.do?type=Ordered&clia=" + clia + "&labId=" +labId + "&ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=yes,titlebar=yes,toolbar=yes,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="ResultedTestName"){
		//civil00013664 - Retain OrganismName(Required Filed) for MICROORGIDENTIFIED RT Search
		//clearDropDownsSearchClear();
		ChildWindowHandle = window.open("/nbs/LoadFindLabReportTestName3.do?type=Resulted&clia=" + clia + "&labId=" +labId + "&ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="DrugName"){
//		var method;
		if (getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.methodCd").value != null || !getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.methodCd").value != ""){
//alert("method-->1");
			method = getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.methodCd").value;
//alert("method-->2");
		}
//alert("method-->3");
		ChildWindowHandle = window.open("/nbs/LoadFindLabReportTestName3.do?type=Drug&clia=" + clia + "&labId=" +labId + "&method=" + method + "&ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="OrganismName"){

		ChildWindowHandle = window.open("/nbs/LoadFindLabReportTestName3.do?type=Organism&clia=" + clia + "&labId=" +labId + "&ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
		//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}
	if(type=="testName"){

			ChildWindowHandle = window.open("/nbs/FindTestName1.do?ContextAction=EntitySearch&mode=new&OrganizationName=" +entityLabel+"","getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
			//var timeoutInt = window.setTimeout("PopupPassToChildWindow('"+ innerItems + "',-1)", 1000);
	}

	




}


/**
 * Description:	initializes the child window and then opens a new browser instance of child window for specified url
 *
 * param @type	name of of the nested type on the page
 */

function entitySearchPersonSelect(uid){

	var parent = window.opener;
	var parentDoc = parent.document;

	if(parentDoc.getElementById("dateAssignedToInvestigation") != null && parent.whichEntity=="entity-table-investigator.personUid") {
		//alert('Clearing off dateAssigned Field value !!');
		var dateAssignedNode = parentDoc.getElementById("dateAssignedToInvestigation");
		if(dateAssignedNode.value.length > 0)
			dateAssignedNode.value="";
	}


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
	var localID=""
	var personSearchResult="";
	var reporterSearchResult="";
	var entitystateValue ="";
	var city="";
	var state="";
	var zip="";
	var address1="";
	var address2="";
	var county="";
	var ethnicity="";
	var DOB="";
	var tempCounty;
	var unknown="";
	var american="";
	var asian="";
	var black="";
	var native="";
	var white="";
	var other="";
	var entityAsOfDate="";
	var entityAge="";
	var entityAgeUnit="";
	var entitySex="";
	var entityMaritalStatus="";
	var entitySSN="";
	/*var entityIdTypeCd="";
	var entityRootExtensionTxt="";
	var entityAssigningAuthorityCd="";
	var entityAssigningAuthorityDescTxt="";*/



	for (var i=0; i < inputNodes.length; i++){
	   //alert(inputNodes.item(i).name + " ======  " + inputNodes.item(i).value);
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
			if(inputNodes.item(i).name=="entity.localID")
			{
				localID = inputNodes.item(i).value;
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

			if(inputNodes.item(i).name=="entity.county")
			{
				county = inputNodes.item(i).value;
				//alert("this is input county " + county);
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
			/*if(inputNodes.item(i).name=="entity.entityIds")
			{
				entityIds = inputNodes.item(i).value;
			}*/
						
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
//				else
//					personSearchResult = (personSearchResult + "<br>");
				if(state != "")
					personSearchResult = (personSearchResult + " " + state);
				if(zip != "")
					personSearchResult = (personSearchResult + " " + zip);
				
				if(email != "")
					personSearchResult = (personSearchResult + "<br/> " + email);
				
				if(phone != "")
					personSearchResult = (personSearchResult + "<br>" +phone);
				if(phone != "" && ext != "")
					personSearchResult = (personSearchResult + " <b>Ext.</b> ");
				if(ext != "")
					personSearchResult = (personSearchResult +ext);
				/*if(entityIds != "")
					personSearchResult = personSearchResult+entityIds;*/
			}
			if (inputNodes.item(i).name=="entity.reporterPersonSearchResult")
			{
				reporterSearchResult = firstname;
				if(lastname != "")
					reporterSearchResult = (reporterSearchResult + " " + lastname);
				if(phone != "")
					reporterSearchResult = (reporterSearchResult + "<br>" +phone);
				if(phone != "" && ext != "")
					reporterSearchResult = (reporterSearchResult + " Ext. ");
				if(ext != "")
					reporterSearchResult = (reporterSearchResult + ext);
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
	var parseString = "";

	var spans = entityTable.getElementsByTagName("span");
	for(var i=0;i<spans.length;i++){
		if(spans.item(i).id=="entity.lastNm")
		{
			spans.item(i).innerHTML = lastname;
			parseString += lastname + " ^";
		}
		else if(spans.item(i).id=="entity.firstNm")
		{
			spans.item(i).innerHTML = firstname;
			parseString += firstname + " ^";
		}
		else if(spans.item(i).id=="entity.emailAddress")
		{
			spans.item(i).innerHTML = email;
			parseString += email + " ^";
		}
		else if(spans.item(i).id=="entity.phoneNbrTxt")
		{
			spans.item(i).innerHTML = phone;
			parseString += phone + " ^";
		}
		else if(spans.item(i).id=="entity.extensionTxt")
		{
			spans.item(i).innerHTML = ext;
			parseString += ext + " ^";
		}
		else if(spans.item(i).id=="entity.localID")
		{
			spans.item(i).innerHTML = localID;
			parseString += localID + " ^";
		}

		else if(spans.item(i).id=="entity.city")
		{
			spans.item(i).innerHTML = city;
			parseString += city + " ^";
		}
		else if(spans.item(i).id=="entity.state")
		{
			spans.item(i).innerHTML = state;
			parseString += state + " ^";
		}
		else if(spans.item(i).id=="entity.zip")
		{
			spans.item(i).innerHTML = zip;
			parseString += zip + " ^";
		}
		else if(spans.item(i).id=="entity.address1")
		{
			spans.item(i).innerHTML = address1;
			parseString += address1 + " ^";
		}
		else if(spans.item(i).id=="entity.address2")
		{
			spans.item(i).innerHTML = address2;
			parseString += address2 + " ^";
		}
		else if(spans.item(i).id=="entity.suffix")
		{
			spans.item(i).innerHTML = suffix;
			parseString += suffix + " ^";
		}
		else if(spans.item(i).id=="entity.degree")
		{
			spans.item(i).innerHTML = degree;
			parseString += degree + " ^";
		}
		else if(spans.item(i).id=="entity.completePersonSearchResult")
		{
			spans.item(i).innerHTML = personSearchResult;
			parseString += personSearchResult + " ^";
		}
		else if(spans.item(i).id=="entity.reporterPersonSearchResult")
		{
			spans.item(i).innerHTML = reporterSearchResult;
			parseString += reporterSearchResult + " ^";

		}


		// check for empty condition
		if(spans.item(i).innerHTML=="")
			spans.item(i).innerHTML ="&nbsp;&nbsp;";
	}

	var spanNode = parentDoc.getElementById("patient.ID");

		if(spanNode != null){
		var newB = parentDoc.createElement("b");
		var newText =parentDoc.createTextNode("Patient ID: ");
		var newText21 =parentDoc.createTextNode(localID);
		newB.appendChild(newText);
		spanNode.innerText="";
		spanNode.appendChild(newB);
		//spanNode.appendChild(": ");
		spanNode.appendChild(newText21);

		spanNode.className="visible";
	}


	var inputs = entityTable.getElementsByTagName("input");
	var selects = entityTable.getElementsByTagName("select");


	for(var i=0;i<selects.length;i++){
	//alert(selects.item(i).id);
		if(selects.item(i).id == "entity.state")
		{
			selects.item(i).value=entitystateValue;
			//selects.item(i).onchange();
		}
		if(selects.item(i).id == "entity.county")
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

	}

	for(var i=0;i<inputs.length;i++){
		if(inputs.item(i).type=="hidden"  && getCorrectAttribute(inputs.item(i),"mode",inputs.item(i).mode)=="uid")
		{
			inputs.item(i).value=personUID;
			//alert("the UID is ------ " + inputs.item(i).value);
			if(inputs.item(i).name =='investigator.personUid')
			{
				var trNode = parentDoc.getElementById("dateAssignedToInvestigation-tdCell");
				if(trNode != null)
					trNode.className="visible";
			}
		}
		else if(inputs.item(i).type=="hidden")
		{
			inputs.item(i).value=personSearchResult;
		}
		else if(inputs.item(i).type=="text")
		{
			//alert(inputs.item(i).id  + "  === " );
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
			}
			else if(inputs.item(i).id=="entity.phoneNbrTxt2")
			{
				inputs.item(i).value=TELE2;
			}
			else if(inputs.item(i).id=="entity.phoneNbrTxt3")
			{
				inputs.item(i).value=TELE3;
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
			}
			else if(inputs.item(i).id=="entity.ssn2")
			{
				inputs.item(i).value=SSN2;
			}
			else if(inputs.item(i).id=="entity.ssn3")
			{
				inputs.item(i).value=SSN3;
			}
			else if(inputs.item(i).id=="entity.personUID")
			{
				//alert("ersonUID is :"+ personUID);
				inputs.item(i).value=personUID;
			}
		}
		else if(inputs.item(i).type=="checkbox"){
		 	if(inputs.item(i).id=="tba")
			{
				if(unknown=='Y')
					inputs.item(i).checked=true;
			}
			else if(inputs.item(i).id=="1002-5")
			{
				if(american=='Y')
					inputs.item(i).checked=true;

			}
			else if(inputs.item(i).id=="2028-9")
			{
				if(asian=='Y')
					inputs.item(i).checked=true;

			}
			else if(inputs.item(i).id=="2054-5")
			{
				if(black=='Y')
					inputs.item(i).checked=true;
			}
			else if(inputs.item(i).id=="2076-8")
			{
				if(native=='Y')
					inputs.item(i).checked=true;
			}
			else if(inputs.item(i).id=="2106-3")
			{
				if(white=='Y')
					inputs.item(i).checked=true;
			}
			else if(inputs.item(i).id=="otherRaceCd")
			{
				if(other=='Y')
					inputs.item(i).checked=true;
			}
		}
	}



	window.close();
}


function entitySearchPersonSelectWait(type, identifier){
	//alert("type = " + type +   "   identifier = " + identifier);

	var timeoutInt = window.setTimeout("entitySearchPersonSelect('"+ type +"','"+ identifier + "',false)", 1000);
}


/**
 * Description:	initializes the child window and then opens a new browser instance of child window for specified url
 *
 * param @type	name of of the nested type on the page
 */
function entitySearchOrganizationSelect(uid){
//alert("in 1 -->" + uid);
		var parent = window.opener;
		labIndicator = "";
		var parentDoc = parent.document;
		var reportLabTest = parent.labIndicator;
		targetResultedTest = "";
		var targetResultedTestVal = parent.targetResultedTest;

		labProgAreaCode ="";
		var  programAreaCode = parent.labProgAreaCode;
		morbIndicator = "";
		var morbIndicatorTest = parent.morbIndicator;

		var entityTable = parentDoc.getElementById(parent.whichEntity);
		//alert(parent.whichEntity);

		var tdNode = getElementByIdOrByName(uid);
		var inputNodes = tdNode.getElementsByTagName("input");

		var name="";
		var localID="";
		var city="";
		var state="";
		var zip="";
		var county="";
		var UID="";
		var phone="";
		var ext="";
		var address1="";
		var address2="";
		var orgSearchResult = "";
		var entityEntityId="";
		var entityEntityIdType="";
		var abcEntityId="";
		var abcEntityIdType="";
		//var entityIds="";


		for (var i=0; i < inputNodes.length; i++)
		{
		    //alert(inputNodes.item(i).name + " ---->  " + inputNodes.item(i).value);
			if(inputNodes.item(i).name!=null){
				if(inputNodes.item(i).name=="entity.name")
				    name = inputNodes.item(i).value;
			    if(inputNodes.item(i).name=="entity.city")
					city = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.state")
					state = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.zip")
					zip = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.county")
					county = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.localID")
					localID = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.address1")
					address1 = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.address2")
					address2 = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.UID")
					UID = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.phoneNbrTxt")
					phone = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.extensionTxt")
					ext = inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.entityId")
					entityEntityId= inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.entityIdType")
					entityEntityIdType= inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.abcLabHospitalID")
					abcEntityId= inputNodes.item(i).value;
				if(inputNodes.item(i).name=="entity.abcLabHospitalIDType")
					abcEntityIdType= inputNodes.item(i).value;
				/*if(inputNodes.item(i).name=="entity.entityIds")
				{
					entityIds = inputNodes.item(i).value;
				}*/
				
				if(inputNodes.item(i).name=="entity.completeOrgSearchResult")
					
				{
					orgSearchResult = name;
					if(address1 != "")
						orgSearchResult = (orgSearchResult + "<br>" +address1);
					if(address2 != "")
						orgSearchResult = (orgSearchResult + "<br>" +address2);
					if(city != "")
						orgSearchResult = (orgSearchResult + "<br>" +city);
					if(city != "" && state !="")
						orgSearchResult = (orgSearchResult + ", ");
					if(state != "")
						orgSearchResult = (orgSearchResult + " " + state);
					if(zip != "")
						orgSearchResult = (orgSearchResult + " " + zip);
						
					if(phone != "")
						orgSearchResult = (orgSearchResult + "<br>" +phone);
					if(phone != "" && ext != "")
						orgSearchResult = (orgSearchResult + " <b>Ext.</b> ");
					if(ext != "")
						orgSearchResult = (orgSearchResult  +ext);
					/*if(entityIds != "")
						orgSearchResult = orgSearchResult+entityIds;*/
				}

			}
		}

//alert("2 -->" + UID);
		var parseString = "";
		var spans = entityTable.getElementsByTagName("span");
//alert("3");
		for(var i=0;i<spans.length;i++)
		{
            if(spans.item(i).id=="entity.name")
			{

				spans.item(i).innerHTML = name;
				parseString += name + " ^";
			}
			else if(spans.item(i).id=="entity.city")
			{
				spans.item(i).innerHTML = city;
				parseString += city + " ^";
			}
			else if(spans.item(i).id=="entity.state")
			{
				spans.item(i).innerHTML = state;
				parseString += state + " ^";
			}
			else if(spans.item(i).id=="entity.zip")
			{
				spans.item(i).innerHTML = zip;
				parseString += zip + " ^";
			}
			else if(spans.item(i).id=="entity.county")
			{
				spans.item(i).innerHTML = county;
				parseString += county + " ^";
			}
			else if(spans.item(i).id=="entity.localID")
			{
				spans.item(i).innerHTML = localID;
				parseString += localID + " ^";
			}

			else if(spans.item(i).id=="entity.UID")
			{
				spans.item(i).innerHTML = UID;
				parseString += UID + " ^";
			}
			else if(spans.item(i).id=="entity.phoneNbrTxt")
			{
				spans.item(i).innerHTML = phone;
				parseString += phone + " ^";
			}
			else if(spans.item(i).id=="entity.extensionTxt")
			{
				spans.item(i).innerHTML = ext;
				parseString += ext + " ^";
			}

			else if(spans.item(i).id=="entity.address1")
			{
				spans.item(i).innerHTML = address1;
				parseString += address1 + " ^";
			}
			else if(spans.item(i).id=="entity.address2")
			{
				spans.item(i).innerHTML = address2;
				parseString += address2 + " ^";
			}
			else if(spans.item(i).id=="entity.completeOrgSearchResult")
			{
				spans.item(i).innerHTML = orgSearchResult;
				parseString += orgSearchResult + " ^";
			}
			else if(spans.item(i).id=="entity.entityId")
			{

				spans.item(i).innerHTML = entityEntityId;
				parseString += orgSearchResult + " ^";
			}
			else if(spans.item(i).id=="entity.entityIdType")
			{
				spans.item(i).innerHTML = entityEntityIdType;
				parseString += orgSearchResult + " ^";
			}
			else if(spans.item(i).id=="entity.abcLabHospitalID")
			{

				spans.item(i).innerHTML = abcEntityId;
				parseString += abcEntityId + " ^";
			}
			else if(spans.item(i).id=="entity.abcLabHospitalIDType")
			{

			    spans.item(i).innerHTML = abcEntityIdType;
				parseString += abcEntityIdType + " ^";
			}
			// check for empty condition
			if(spans.item(i).innerHTML=="")
				spans.item(i).innerHTML ="&nbsp;&nbsp;";

		}

//alert("5666666666");
		var inputs = entityTable.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++){
			if(inputs.item(i).type=="hidden" && getCorrectAttribute(inputs.item(i),"mode",inputs.item(i).mode)=="uid")
			{
				inputs.item(i).value=UID;
			}
			else if(inputs.item(i).type=="hidden")
			{
				if (inputs.item(i).name=="reportingSourceDetails"){
					inputs.item(i).value=orgSearchResult;
				}else{
					inputs.item(i).value=orgSearchResult;
				}


		    }
		    
		    // AK - 7/27/04
		    // civil00011710 - Update the hidden variable only for reporting lab searches.
		    if ( parent.whichEntity == "entity-table-Org-ReportingOrganizationUID") {
    			var hiddenReportingSourceDetail = parentDoc.getElementById("Org-ReportingOrganizationDetails");
    			if(hiddenReportingSourceDetail!= null)
                		{
    				        //alert("the old details is ------ " + hiddenReportingSourceDetail.value);
                			hiddenReportingSourceDetail.value = parseString.substring(0, parseString.lastIndexOf("^") );
    				        //alert("the new details is ------ " + hiddenReportingSourceDetail.value);
                		}
            }

		}
//alert("reportLabTest - " + reportLabTest);
	if(reportLabTest)
	{
		var OrgUID = UID;
		var code ="";
		var type = "entitySearchRoute";
		var targetResulted ="";
		var targetOrdered =""
		var conditionCode = "";
		var dropdownChecker = "true";
		//alert("morbIndicator is "+ morbIndicatorTest);
		var win = window.opener;
		var doc = win.document;
		if(morbIndicatorTest)
		{
			var targetResulted = "labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt";
			conditionCode = doc.getElementById("morbidityReport.theObservationDT.cd").value;
			var targetOrdered =  "";
			if(conditionCode=="")
				dropdownChecker = false;
		}
		else
		{
			if(programAreaCode ==""){
				dropdownChecker = false;
			}
			targetResulted = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";
			targetOrdered = "proxy.observationVO_s[0].theObservationDT.txt";
			
			var TestNameText = doc.getElementById("OrderedDisplay");

			if(doc.getElementById("conditionCode")!=null)
				conditionCode = doc.getElementById("conditionCode").value;
			if(TestNameText!= null)
			{
				TestNameText.className = "none";
				TestNameText.value = "";
			}
			var lab277 = doc.getElementById("LAB277");
			if(lab277!=null && lab277.checked){
				var etable = doc.getElementById("entity-table-Org-OrderingFacilityUID");
				var spans = etable.getElementsByTagName("span");
				for(var i=0;i<spans.length;i++)
				{
					if(spans.item(i).id=="entity.completeOrgSearchResult")
				        {
						//var orgSearchResult = "orgSearchResult";
				                spans.item(i).innerHTML = orgSearchResult;
				        }
				}
				var inputs = etable.getElementsByTagName("input");
				for(var i=0;i<inputs.length;i++){
				if(inputs.item(i).type=="hidden" && getCorrectAttribute(inputs.item(i),"mode",inputs.item(i).mode)=="uid")
				{
					inputs.item(i).value="UID";
				}
				}
			}

		}
		//purge target options
		if(target!=null)
		{
			var node=doc.getElementById(target);
			if(node!=null){
				node.className="none";
				var removeOpt = node.firstChild;
				while(removeOpt != null){
					var temp = removeOpt.nextSibling;
					node.removeChild(removeOpt);
					removeOpt= temp;
				}
				if(doc.getElementById(node.name + "_textbox")!=null)     
					doc.getElementById(node.name + "_textbox").value="";
				node.className="none";
			}
		}	
		//purge target options
		if(targetOrdered!=null && targetOrdered!="")
		{
			node=doc.getElementById(targetOrdered);
			if(node!=null){
				node.className="none";
				var removeOpt = node.firstChild;
				while(removeOpt != null){
					var temp = removeOpt.nextSibling;
					node.removeChild(removeOpt);
					removeOpt= temp;
				}
				if(doc.getElementById(node.name + "_textbox")!=null)     
					doc.getElementById(node.name + "_textbox").value="";
				node.className="none";
			}
		}	
		//purge target options
		if(targetResulted!=null && targetResulted!="")
		{
			node=doc.getElementById(targetResulted);
			if(node!=null){
				node.className="none";
				var removeOpt = node.firstChild;
				while(removeOpt != null){
					var temp = removeOpt.nextSibling;
					node.removeChild(removeOpt);
					removeOpt= temp;
				}
				if(doc.getElementById(node.name + "_textbox")!=null)     
					doc.getElementById(node.name + "_textbox").value="";
				node.className="none";
			}
		}
		//getLabTestData(labCode, progAreaCode,labTestType, target);
		//labProgAreaCdTest;
		var target = targetResultedTestVal;
		//alert("labProgAreaCdTest is :" + labProgAreaCdTest);
		var x = screen.availWidth;
		var y = screen.availHeight;

//		//alert("type="+ type + '\n;code=' + code + '\n;targetResulted=' + targetResulted + '\n;targetOrdered=' + targetOrdered +'\n&;target=' + target + '\n;programAreaCode=' + programAreaCode + '\n;UID=' + UID);
		var popupWindow  = window.open("/nbs/nedssCodeLookup?type="+ type + '&amp;code=' + code + '&amp;targetResulted=' + targetResulted + '&amp;targetOrdered=' + targetOrdered +'&amp;target=' + target + '&amp;conditionCode=' + conditionCode + '&amp;programAreaCode=' + programAreaCode + '&amp;UID=' + UID+'&amp;dropdownChecker='+dropdownChecker,"getData2","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		var updateNode = getElementByIdOrByName(targetResulted);
//alert("a-"+ updateNode);
		var updateNodeOrdered = getElementByIdOrByName(targetOrdered);
//alert("a-"+ updateNodeOrdered);
		//popupWindow.close();
		//window.close();
}
	else
	{
		window.close();
	}
//alert("done 1");
}

function getLabCodeLookup(type, code, target, targetOrdered, targetResulted,programAreaCode, UID){

	//if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	//{
		var x = screen.availWidth;
		var y = screen.availHeight;

		 window.open("/nbs/nedssCodeLookup?type="+ type + '&amp;code=' + code + '&amp;targetResulted=' + targetResulted + '&amp;targetOrdered=' + targetOrdered +'&amp;target=' + target + '&amp;programAreaCode=' + programAreaCode + '&amp;UID=' + UID,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		var updateNode = getElementByIdOrByName(targetResulted);
		var updateNodeOrdered = getElementByIdOrByName(targetOrdered);
	//}
}

function getTreatmentCodeLookup(type, code, target, targetOrdered, targetResulted,programAreaCode, UID){

	//if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	//{
		var x = screen.availWidth;
		var y = screen.availHeight;
		if(type == "entity-table-Org-ReportingOrganizationUID")
		{
			code = getElementByIdOrByName("entity-codeLookupText-Org-ReportingOrganizationUID").value;
			UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
		}
		else if(type == "entity-table-Prov-entity.entityProvUID")
		{
			code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.entityProvUID").value;
			UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
		}
		window.open("/nbs/nedssCodeLookup?type="+ type + '&amp;code=' + code + '&amp;targetResulted=' + targetResulted + '&amp;targetOrdered=' + targetOrdered +'&amp;target=' + target + '&amp;programAreaCode=' + programAreaCode + '&amp;UID=' + UID+'&amp;dropdownChecker=false',"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		var updateNode = getElementByIdOrByName(targetResulted);
		var updateNodeOrdered = getElementByIdOrByName(targetOrdered);
	//}
}



/**
 * Description:	initializes the child window and then opens a new browser instance of child window for specified url
 *
 * param @type	name of of the nested type on the page
 */
function entitySearchlabReportTestNameSelect(uid, type){

	var parent = window.opener;
	var parentDoc = parent.document;

	if(parentDoc.getElementById("dateAssignedToInvestigation") != null && parent.whichEntity=="entity-table-investigator.personUid") {
		var dateAssignedNode = parentDoc.getElementById("dateAssignedToInvestigation");
		if(dateAssignedNode.value.length > 0)
			dateAssignedNode.value="";
	}

		if (type == "ordered")
		{
			//alert("in");
			//alert("1-->" + parentDoc.getElementById("proxy.observationVO_s[0].theObservationDT.txt"))
			if(parentDoc.getElementById("proxy.observationVO_s[0].theObservationDT.txt") != null)
			{
				var testName= parentDoc.getElementById("proxy.observationVO_s[0].theObservationDT.txt_ac_table");

				//alert("TestBName:" + testName.value);
				//alert("testName class:" + testName.className);
				testName.className= "none";

				var TestNameText = parentDoc.getElementById("OrderedDisplay");
				//alert("TestNameText :" + TestNameText.className+"TestNameText");
				TestNameText.className ="";
			}
		}

		if (type == "resulted")
		{
			if(parentDoc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt") != null)
			{
				var oldOrgName = parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName");
				//alert("oldOrgName is :" + oldOrgName.value);
				oldOrgName.value = "";
				var hiddenCD = parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd");
				if(hiddenCD!= null)
				{
					//alert("hiddenCd"+ hiddenCD.value);
					hiddenCD.value = "";
				}
				
				/*
				    Ajith Kallambella 12/15/2004
				    Ref : Know Issue Alert ID 00010 ( 10/19/2004 )
				    
				    
				    BatchEntry ignores all Document nodes with className = "none" and therefore such items are
				    not preserved and not included as the form data for the Submit event. 
				    This was causing the organism details to disappear on form Submit. 
				    
				    Setting the className = "none" is unnecessary and should not be done unless there is a need
				    to not submit this piece of information to the action class.
				    
				*/
				/*
				var searchResultRT  = parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].searchResultRT");
					if(searchResultRT != null)
					{
						alert("CLASS NAME(searchResultRT) IS !!! "+ searchResultRT.className );
						searchResultRT.className  = "none";
				}
				var cdSystemCdRT = parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].cdSystemCdRT");
					if(cdSystemCdRT!= null)
					{
						alert("CLASS NAME(cdSystemCdRT) IS !!! "+ cdSystemCdRT.className );
						cdSystemCdRT.className  = "none";
				}
				*/
				var testName= parentDoc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt_ac_table");

				//alert("TestBName:" + testName.value);
				//alert("testName class:" + testName.className);
				testName.className= "none";

				var TestNameText = parentDoc.getElementById("ResultedDisplay");
				//alert("TestNameText :" + TestNameText.className+"TestNameText");
				TestNameText.className ="";

			}
			//alert(parentDoc.getElementById("testNamec"))
			if(parentDoc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt") != null)
			{
				//alert("2");
				var testName= parentDoc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt_ac_table");

				//alert("TestBName:" + testName.value);
				//alert("testName class:" + testName.className);
				testName.className= "none";

				var TestNameText = parentDoc.getElementById("ResultedDisplay");
				//alert("TestNameText :" + TestNameText.className+"TestNameText");
				TestNameText.className ="";
			}
		}

		if (type == "drug")
		{
			if(parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt") != null)
			{
				var drugName= parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt");	
				drugName.value="";
				var testName= parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_ac_table");

				//alert("testName class:" + testName.className);
				testName.className= "none";

				var TestNameText = parentDoc.getElementById("DrugDisplay");
				//alert("TestNameText :" + TestNameText.className+"TestNameText");

				TestNameText.className ="";
			}
		}

		if (type == "organism")
		{
			if(parentDoc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName") != null)
			{
			
				var testName= parentDoc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName_ac_table");
				testName.className= "none";

				var TestNameText = parentDoc.getElementById("OrganismDisplay");
				TestNameText.className ="";
			}
			if(parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName") != null)
			{
				var testName= parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_ac_table");
				testName.className= "none";

				var TestNameText = parentDoc.getElementById("OrganismDisplay");
				TestNameText.className ="";
			}
		}

	var entityTable = parentDoc.getElementById(parent.whichEntity);
	var tdNode = getElementByIdOrByName(uid);

	var inputNodes = tdNode.getElementsByTagName("input");


	var lastname="";
		var lastname1="";
	var firstname="";
	var email="";
	var phone="";
	var ext="";
	var personUID="";
	var localID=""
	var cdSystemCd="";
	var organismInd = "";

	for (var i=0; i < inputNodes.length; i++){
		if(inputNodes.item(i).name!=null){

			if(inputNodes.item(i).name=="cdSystemCd")
			{
				cdSystemCd = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="name")
			{
				lastname = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="code")
			{
				personUID = inputNodes.item(i).value;
			}
			if(inputNodes.item(i).name=="organismInd")
			{
				organismInd = inputNodes.item(i).value;
			}
		}
	}

	var parseString = "";

	var spans = entityTable.getElementsByTagName("span");
	for(var i=0;i<spans.length;i++){
		if(spans.item(i).id=="OrderedDisplay")
		{
			spans.item(i).innerHTML = lastname;
			parseString += lastname + " ^";
			//alert("tested " + parseString);
		}
		if(spans.item(i).id=="ResultedDisplay")
		{
			spans.item(i).innerHTML = lastname;
			parseString += lastname + " ^";
		}
		if(spans.item(i).id=="DrugDisplay")
		{
			spans.item(i).innerHTML = lastname;
			parseString += lastname + " ^";
		}
		if(spans.item(i).id=="OrganismDisplay")
		{
			spans.item(i).innerHTML = lastname;
			parseString += lastname + " ^";
		}
		// check for empty condition
		if(spans.item(i).innerHTML=="")
			spans.item(i).innerHTML ="&nbsp;&nbsp;";


	}




	var inputs = entityTable.getElementsByTagName("input");
	var test;
	var debugString = " ";
	
	for(var i=0;i<inputs.length;i++){
		if(inputs.item(i).type=="hidden"  && getCorrectAttribute(inputs.item(i),"mode",inputs.item(i).mode)=="uid")
		{
			inputs.item(i).value=personUID;
			//alert("the UID is ------ " + inputs.item(i).value);
			if(inputs.item(i).name =='investigator.personUid')
			{
				var trNode = parentDoc.getElementById("dateAssignedToInvestigation-tdCell");
				trNode.className="visible";
			}
		}
		else if(inputs.item(i).type=="hidden")
		{

			// Lab Ordered Test
			if (inputs.item(i).name=="proxy.observationVO_s[0].theObservationDT.searchResultOT")
			{
				inputs.item(i).value = lastname;
			}
			if (inputs.item(i).name=="proxy.observationVO_s[0].theObservationDT.hiddenCd")
			{
				inputs.item(i).value = personUID;
			}
			if (inputs.item(i).name=="proxy.observationVO_s[0].theObservationDT.cdSystemCdOT")
			{
				inputs.item(i).value = cdSystemCd;
			}

			// Lab Resulted Test
			if (inputs.item(i).name=="resultedTest[i].theIsolateVO.theObservationDT.searchResultRT")
			{
				inputs.item(i).value = lastname;
			}
			if (inputs.item(i).name=="resultedTest[i].theIsolateVO.theObservationDT.hiddenCd")
			{
				inputs.item(i).value = personUID;
			}
			if (inputs.item(i).name=="resultedTest[i].theIsolateVO.theObservationDT.cdSystemCdRT")
			{
				inputs.item(i).value = cdSystemCd;
			}


			// Lab Result Drug
			if (inputs.item(i).name=="resultedTest[i].susceptibility[j].theObservationDT.searchResultRT")
			{
				inputs.item(i).value = lastname;
			}
			if (inputs.item(i).name=="resultedTest[i].susceptibility[j].theObservationDT.hiddenCd")
			{
				inputs.item(i).value = personUID;
			}
			if (inputs.item(i).name=="resultedTest[i].susceptibility[j].theObservationDT.cdSystemCdRT")
			{
				inputs.item(i).value = cdSystemCd;
			}

			// Lab organism
			if (inputs.item(i).name=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].searchResultRT")
			{
				inputs.item(i).value = personUID;
			}
			if (inputs.item(i).name=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd")
			{
				inputs.item(i).value = lastname;
				//have new code, remove the code that might have been set by the drop-down
				parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").value = "";
			}
			if (inputs.item(i).name=="resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].cdSystemCdRT")
			{
				inputs.item(i).value = cdSystemCd;
			}





			// Morbidity Resulted  Test
			if (inputs.item(i).name=="labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT")
			{
				inputs.item(i).value = lastname;
			}
			if (inputs.item(i).name=="labResults_s[i].observationVO_s[1].theObservationDT.hiddenCd")
			{
				inputs.item(i).value = personUID;
			}
			if (inputs.item(i).name=="labResults_s[i].observationVO_s[1].theObservationDT.cdSystemCdRT")
			{
				inputs.item(i).value = cdSystemCd;
			}


			// Morbidity Organism
			if (inputs.item(i).name=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].searchResultRT")
			{
				inputs.item(i).value = personUID;
			}
			if (inputs.item(i).name=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd")
			{
				inputs.item(i).value = lastname;
			}
			if (inputs.item(i).name=="labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].cdSystemCdRT")
			{
				inputs.item(i).value = cdSystemCd;
			}





			if (inputs.item(i).name=="organismInd")
			{
				inputs.item(i).value = organismInd;
				var win = window.opener;
				var doc = win.document;
				var morbType = doc.getElementById("morbtype");
				if(morbType!= null)
				{
				}
				else
				{
					var susceptiblityInd = doc.getElementById("susceptiblityInd");
					var isolateTrackInd = doc.getElementById("IsolateTrackInd");
					if(organismInd=="Y")
					{
						susceptiblityInd.className = "visible";
						isolateTrackInd.className = "visible";
					}
					else
					{
						susceptiblityInd.className = "none";
						isolateTrackInd.className = "none";
					}
				}
				if(organismInd=='Y')
				{

					//alert("CONDITION ONE IS TRUE!");
					var codeResultInd = doc.getElementById("codedResultIndicator");
 					codeResultInd.className = "none";
 					var OrganismInd = doc.getElementById("OrganismIndicator");
 					if(OrganismInd==null)
						OrganismInd = doc.getElementById("organismIndicator");
					
 					OrganismInd.className = "visible";
 					//morb
 					if(doc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1"))
 						doc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1").value="Y";
					//lab
					if(doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1"))
						doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1").value="Y";
 				}
 				else
 				{

					//alert("CONDITION TWO IS TRUE!");
					var codeResultInd = doc.getElementById("codedResultIndicator");
 					codeResultInd.className = "visible";
 					var OrganismInd = doc.getElementById("OrganismIndicator");
 					
 					if(OrganismInd==null)
						OrganismInd = doc.getElementById("organismIndicator");
 					
 					OrganismInd.className = "none";
 					//////////////////////////////////////////////////////////////clear suscept


						if(doc.getElementById("index")!= null){

							var index = doc.getElementById("index").value;
							if(index!=""){

								var hiddenNode = doc.getElementById("nestedElementsHiddenFieldSusceptibility");

								hiddenNode.value=hiddenNode.value.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");



								//initialize conditional
									if(doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")){
										doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
										doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
									}
									//can't do this because we are in child window context
									//updateBatchEntryHistoryBox("Susceptibility");
							} else { // this is uncommited batch

								if(doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")){
									doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
									doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
								}
								doc.getElementById("nestedElementsHiddenFieldSusceptibility").value="";
								//updateBatchEntryHistoryBox("Susceptibility");
							}
						}


					/////////////////////////////////////////////////////////////////////////////////

 					//morb
 					if(doc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1"))
 						doc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1").value="N";
 					//morb
					if(doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1"))
						doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1").value="N";
 				}
			}
			if (inputs.item(i).value=="")
			{
				inputs.item(i).value = parseString;
				//alert(parseString);
			}
		}
        debugString += "\n" + inputs.item(i).name + " = " + inputs.item(i).value ;
	}
    //alert(debugString);
    window.close();
	}







/**
 * Description:	initializes the child window and then opens a new browser instance of child window for specified url
 *
 * param @type	name of of the nested type on the page
 */
function entitySearchClear(aTableId){

	var ent = getEntityTitle(aTableId)
	var tableNode = getElementByIdOrByName(aTableId);
	var spanNodes = tableNode.getElementsByTagName("span");
	var hiddenNodes = tableNode.getElementsByTagName("input");
	var textAreaNodes = tableNode.getElementsByTagName("textarea");
	var parentDoc = parent.document;
	var testName= parentDoc.getElementById("TestName");
	if(parentDoc.getElementById("patient.thePersonDT.localId") != null)
	  {
	    parentDoc.getElementById("patient.thePersonDT.localId").value = ""; 
	  }

	if (ent == "Test"){
		if(parentDoc.getElementById("proxy.observationVO_s[0].theObservationDT.txt") != null)
		{
			parentDoc.getElementById("proxy.observationVO_s[0].theObservationDT.txt_ac_table").className ="visible";
			//testName.value="";
		}
	}
	
	if ((aTableId == "entity-table-Resulted Test Name") || aTableId == "entity-table-ResultedTestName"){
	clearDropDownsSearchClear();
	var isolateTrackInd = getElementByIdOrByName("IsolateTrackInd") == null ? "" : getElementByIdOrByName("IsolateTrackInd");
	isolateTrackInd.className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code") == null ? "" : getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code_textbox") == null ? "" : getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code_textbox").value="";
	if(isolateTrackInd != "")
		trackIsolate();
	//////////////////////////////////////////////////////////////clear suscept
				if(getElementByIdOrByName("index")!= null){

					var index = getElementByIdOrByName("index").value;
					if(index!=""){
						//alert("index ==============  " + index);
						var hiddenNode = getElementByIdOrByName("nestedElementsHiddenFieldSusceptibility");
						//alert("before ===   " + hiddenNode.value);
						hiddenNode.value=hiddenNode.value.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");

						//alert("after ===   " + hiddenNode.value);

						//initialize conditional
							if(getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")){
								getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
								getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
							}
							updateBatchEntryHistoryBox("Susceptibility");
					} else { // this is uncommited batch
						if(getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")){
							getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
							getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
						}
						getElementByIdOrByName("nestedElementsHiddenFieldSusceptibility").value="";
						updateBatchEntryHistoryBox("Susceptibility");
					}
			}


			/////////////////////////////////////////////////////////////////////////////////


		//clear out the desc txt so that will not appear when updating edit
		if(parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd") != null)
		{
			parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd").value = "";
			//testName.value="";
		}

		if(parentDoc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt") != null)
		{

			parentDoc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt").className ="none";
			parentDoc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt").value ="";
			parentDoc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt_ac_table").className="visible";
			//testName.value="";
		}
		if(parentDoc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt") != null)
		{
			parentDoc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt_ac_table").className="visible";
			//parentDoc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt").className ="visible";
			parentDoc.getElementById("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt").value = "";
			//testName.value="";
		}

	}
	if (ent == "Drug Test"){

		if(parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt") != null)
		{
			parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_ac_table").className="visible";
			//parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").className ="";
			parentDoc.getElementById("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").value ="";
			//testName.value="";
		}
	}
	if (aTableId == "entity-table-Organism Test"){

		if(parentDoc.getElementById("OrganismName") != null)
		{
			parentDoc.getElementById("OrganismName").className ="";
			//testName.value="";
		}
	}

	if (aTableId == "entity-table-Organism Name"){
	
		if(parentDoc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName") != null)
		{	
			parentDoc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName_ac_table").className="visible";
			//parentDoc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName").className ="";
			parentDoc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName").value = "";
			//testName.value="";
		}
		if(parentDoc.getElementById("OrganismName") != null)
		{
			parentDoc.getElementById("OrganismName").className ="";
			//testName.value="";
		}
	}
	if (aTableId == "entity-table-orderedTest"){
			if(parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName") != null)
			{
				parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_ac_table").className ="visible";
				parentDoc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").value = "";
				//testName.value="";
			}

	}

	for(var i=0;i<spanNodes.length;i++)
	{
		spanNodes.item(i).innerHTML = "&nbsp;&nbsp;";
	}

	for(var i=0;i<textAreaNodes.length;i++)
	{
		textAreaNodes.item(i).innerHTML = "&nbsp;&nbsp;";
	}

	if(aTableId == "entity-table-Org-ReportingOrganizationUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Reporting Facility selected.";
		}
		var TestNameText = getElementByIdOrByName("OrderedDisplay");
		//alert("TestNameText.value 123is :" + TestNameText.value);
		if(TestNameText!= null)
		{
		//alert("TestNameText.value:"+TestNameText.value);
			var spanNodes = TestNameText.getElementsByTagName("span");
			for(var i=0;i<spanNodes.length;i++)
			{
				//alert("spanNodes.item(i).innerHTML is :"+ spanNodes.item(i).innerHTML);
				spanNodes.item(i).innerHTML = "&nbsp;&nbsp;";
			}
			TestNameText.className = "none";
			TestNameText.value = "";
		}
		if(getElementByIdOrByName("LAB277")!= null)
		{
			var lab277 = getElementByIdOrByName("LAB277");
			if(lab277.checked)
			{
				copyData();
			}
		var hiddenClia = getElementByIdOrByName("labId");
		if(hiddenClia != "")
		{
			hiddenClia.value = "";
		}
		clearDoubleBatchEnteries();
		}
	}
	if(aTableId == "entity-table-Org-HospitalUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Hospital selected.";
		}
	}
	if(aTableId == "entity-table-Org-OrderingFacilityUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Ordering Facility selected.";
		}
	}
	if(aTableId == "entity-table-Prov-entity.entityProvUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Ordering Provider selected.";
		}
	}
	if(aTableId == "entity-table-Prov-entity.providerUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Provider selected.";
		}
	}
	if(aTableId == "entity-table-Prov-entity.reporterUID")
	{
		for(var i=0;i<spanNodes.length;i++)
		{
			spanNodes.item(i).innerHTML = "&nbsp;&nbsp;There is no Reporter selected.";
		}
	}
	if(parentDoc.getElementById("LAB277") != null)
	{
			var lab277 = getElementByIdOrByName("LAB277");
			lab277.checked = false;
			var entitytable = getElementByIdOrByName("entity-table-Org-OrderingFacilityUID");
			var hiddenNodes = entitytable.getElementsByTagName("input");
			for(var i=0;i<hiddenNodes.length;i++)
			{
				if(hiddenNodes.item(i).type=="button")
				{
				  hiddenNodes.item(i).disabled=false;
				}
				if(hiddenNodes.item(i).type=="text")
				{
				  hiddenNodes.item(i).disabled=false;
				}
			}

	}
	var hiddenNodes = tableNode.getElementsByTagName("input");
	var selects = tableNode.getElementsByTagName("select");
	for(var i=0;i<selects.length;i++)
	{
		 selects.item(i).value="";
	}
	for(var i=0;i<hiddenNodes.length;i++)
	{

		if(hiddenNodes.item(i).type=="hidden")
		{
			// don't default the org indicator
			if(hiddenNodes.item(i).name!="labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1" && hiddenNodes.item(i).name!="resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1"){
				hiddenNodes.item(i).value="";
			}
			if(hiddenNodes.item(i).name =='investigator.personUid')
			{

				var trNode = getElementByIdOrByName("dateAssignedToInvestigation-tdCell");
				trNode.className="none";
				var dateAssignedNode = getElementByIdOrByName("dateAssignedToInvestigation");
				dateAssignedNode.value="";
			}

		}
		if(hiddenNodes.item(i).type=="text")
		{

		  hiddenNodes.item(i).value="";

		}

		if(hiddenNodes.item(i).type=="checkbox")
		{

		  hiddenNodes.item(i).checked=false;

		}

	}

	var spanNode = parentDoc.getElementById("patient.ID");
	if(spanNode != null){
	  	spanNode.show="false";//show="false"
		spanNode.innerText="";
	}
	if(aTableId == "entity-table-Patient.personUid")
	{
		getDataToPopulate("entity.county");
	}

	var errorNode = getTdErrorCell(tableNode);
	errorNode.className="error";
	errorNode.innerText ="";

}
function getDataToPopulate(target)
{
	if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	{
		var x = screen.availWidth;
		var y = screen.availHeight;
		if(getElementByIdOrByName("defaultState")!=null || getElementByIdOrByName("defaultState")!="")
			var selectedCd = getElementByIdOrByName("defaultState").value;
		if(getElementByIdOrByName("entity.state")!=null)
			getElementByIdOrByName("entity.state").value = selectedCd;
			//synch the type ahead text box with drop down
			var varOptions = getElementByIdOrByName("entity.state").options;
			getElementByIdOrByName("stateCd_textbox").value = varOptions[getElementByIdOrByName("entity.state").selectedIndex].text;
		
		//purge target options
		var targetNode = getElementByIdOrByName(target);
		var removeOpt = targetNode.firstChild;

			 while(removeOpt != null){
				  var temp = removeOpt.nextSibling;
				  targetNode.removeChild(removeOpt);
				  removeOpt= temp;
			 }
		getElementByIdOrByName("cntyCd_textbox").value="";
		targetNode.className="none";
		
		ChildWindowHandle = window.open("/nbs/dynamicSelect?elementName="+target + '&amp;inputCd=' + selectedCd ,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		
	}
	else
	{
		dataToPopulateWait(target);
	}


}
function dataToPopulateWait(target)
{
	var timeoutInt = window.setTimeout("getDataToPopulate(" + "'"+ target +"')", 1000);
}


function getEntityTitle(table) {

	if(table == 'entity-table-resultedTest[i].thePerformingLabVO.theOrganizationDT.uid') {
		//alert('wahoo !! found');
		return 'Performing Lab';
	}
	var separator = '.';
	var searchString1 = table.split(separator);
	for(var i=0; i<searchString1.length; i++) {

		var subSeparator = '-';
		var searchString2 = searchString1[0].split(subSeparator);

		for(var j=0; i<searchString2.length; j++) {

			var initCapMaker = searchString2[2].substring(0,1);
			var capValue = initCapMaker.toUpperCase();
			var finalValue = searchString2[2].replace(searchString2[2].substring(0,1), capValue);
			var spaceMaker = getUpperCase(finalValue);
			return spaceMaker;
		}
	}

}
function CancelEntitySearch(){

	/*var confirmMsg = "Are you sure you want to cancel?";
	if (confirm(confirmMsg)){ */
		window.close();
	/*} else
		return;*/
}
function getUpperCase(entity) {
	var entity1 = entity;
	var appender = "";
	for(var i=0; i<entity1.length;i++) {
		var j = entity1.substring(i,i+1);
		if( i>0 && j.toUpperCase() == j) {
		  appender = appender + ' '+ j;
		 //alert('found capitals at position ' + i +' and the letter is: '+ j);
		} else {
		  appender = appender + j;
		}
	}//for
	//alert(appender);
	return appender;

}

function openWindowForCompare(){
	var x = 100;	//screen.availWidth;
	var y = 100;    //screen.availHeight;
	var z = 0;
	var firstComapre = "";
	var secondCompare = "";
		var inputs = document.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++)
		{
		  if(inputs.item(i).id=="compare" && inputs.item(i).checked==true)
		  {
		     z++;
		     if(z=="1")
		     firstCompare = inputs.item(i).value;
		     if(z=="2")
		     secondCompare = inputs.item(i).value;
		  }
		}
		//alert(firstCompare);
		//alert(secondCompare);


	window.open("/nbs/ComparePatients2.do?ContextAction=Compare1&firstPatient="+firstCompare+"&secondPatient="+secondCompare,"getData","left=" + x + ", top=" + y + ", width=" + popUpWidth + ", height=" + popUpHeight + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");

}


function entitySearchPersonVOSelect(uid){
	entitySearchPersonSelect(uid);
	var x = 100;	//screen.availWidth;
	var y = 100;    //screen.availHeight;
	window.open("/nbs/jsp/EntitySearchVOLoader.jsp","getVO","left=" + x + ", top=" + y + ", width=" + 100 + ", height=" + 100 + ", menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes");
}
function clearDropDownsSearchClear(){
	var doc = window.document;
	var updateNode = doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt");
	//purge target options
	
	if(updateNode != null)
	{
	getElementByIdOrByName(updateNode.name + "_textbox").value="";
	updateNode.className="none";
	}
	// Coded result
	var doc = window.document;	
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code");
	if(updateNodeOrdered != null)
	{
		updateNodeOrdered.value = "";	
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";
	}

	// Organism
	if (doc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName") != null)
	{
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName");
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";
		
			doc.getElementById("organismIndicator").className="none";
			doc.getElementById("codedResultIndicator").className="visible";
			doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="none";
			doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
			doc.getElementById("susceptiblityInd").className="none";
			doc.getElementById("IsolateTrackInd").className="none";
	}

	// Organism (in Morb Report)
	if (doc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName") != null)
	{
		var updateNodeOrdered = doc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName");
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";
		
		doc.getElementById("organismIndicator").className="none";
		doc.getElementById("codedResultIndicator").className="visible";
	}

	// Error message for soft required field
	if (doc.getElementById("codedResultIndicatorMessage") != null)
	{
		doc.getElementById("codedResultIndicatorMessage").className="none";
		if(doc.getElementById("morbtype") != null)
		{
					
			var testName= doc.getElementById("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");
			getElementByIdOrByNameNode(testName.name + "_textbox",doc).value="";
			testName.value = "";
			doc.getElementById(testName.name + "").value="";
			testName.className= "none";
			var TestNameText = doc.getElementById("OrganismDisplay");
			TestNameText.className ="";
		}	
	}
	var morbType = doc.getElementById("morbtype");
	
	if(morbType==null)
	{
		// Numeric Result value
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue");
		getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	
		// Units
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd");
		updateNodeOrdered.value = "";
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";
		
		// Text Result
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt");
		getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	
		// Reference Range Low
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].lowRange");
		getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	
		// Reference Range High
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].highRange");
		getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	
		// Result Status
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.statusCd");
		updateNodeOrdered.value = "";
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";
	
		// Result Status
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code");
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";	
	}	
	
}		
function updateUnkWeightPert(){
  var unknownWeight = getElementByIdOrByName("proxy.observationVO_s[64].obsValueNumericDT_s[0].numericValue1_s");
  var unknownWeightCheckBox = getElementByIdOrByName("unknownWeightCheckBox");
  var unknownG = getElementByIdOrByName("proxy.observationVO_s[63].obsValueNumericDT_s[0].numericValue1_s");
  var unknownLbs = getElementByIdOrByName("proxy.observationVO_s[61].obsValueNumericDT_s[0].numericValue1_s");
  var unknownOunce = getElementByIdOrByName("proxy.observationVO_s[62].obsValueNumericDT_s[0].numericValue1_s");
  if(unknownWeightCheckBox.checked)
  {
	  unknownWeight.value = -1;
  }
  else {
	  unknownWeight.value = "";
	  unknownG.disabled = false;
  	  unknownLbs.disabled = false;
	  unknownOunce.disabled = false;
  	  return;
  }
  unknownG.disabled = true;
  unknownLbs.disabled = true;
  unknownOunce.disabled = true;
  unknownG.value = "";
  unknownLbs.value = "";
  unknownOunce.value = "";
}

function PRT107Changes(){
  var PRT107 = getElementByIdOrByName("proxy.observationVO_s[59].obsValueCodedDT_s[0].code");

  var motherAge = getElementByIdOrByName("proxy.observationVO_s[60].obsValueNumericDT_s[0].numericValue1_s");
  var unknownWeight = getElementByIdOrByName("proxy.observationVO_s[64].obsValueNumericDT_s[0].numericValue1_s");
  var unknownG = getElementByIdOrByName("proxy.observationVO_s[63].obsValueNumericDT_s[0].numericValue1_s");
  var unknownLbs = getElementByIdOrByName("proxy.observationVO_s[61].obsValueNumericDT_s[0].numericValue1_s");
  var unknownOunce = getElementByIdOrByName("proxy.observationVO_s[62].obsValueNumericDT_s[0].numericValue1_s");
  var unknownWeightCheckBox = getElementByIdOrByName("unknownWeightCheckBox");

  var Q107Dependenta =getElementByIdOrByName("Dependent107a");
  var Q107Dependentb =getElementByIdOrByName("Dependent107b");
  
  if(PRT107.value =='Y'){
  	Q107Dependenta.className = "visible";
  	Q107Dependentb.className = "visible";
   }
  else{
    	 Q107Dependenta.className = "none";
	 Q107Dependentb.className = "none";
 
  	if(motherAge!=null)
	  	motherAge.value = "";
  	if(unknownWeight!=null)
	  	unknownWeight.value = "";
  	if(unknownG!=null)
	  	unknownG.value = "";
  	if(unknownLbs!=null){
  		unknownLbs.value = "";
  		unknownLbs.disabled = false;
  	}	
  	if(unknownOunce!=null){
  		unknownOunce.value = "";
  		unknownOunce.disabled = false;
  	}	
  	if(unknownWeightCheckBox!=null)
  		unknownWeightCheckBox.checked=false;
  	if(unknownWeight!=null)
  		unknownWeight.disabled = false;
  	if(unknownG!=null)
  		unknownG.disabled = false;
  }
}


function PRT107AndPRT112(){
PRT107Changes();
 var unknownWeightCheckBox = getElementByIdOrByName("unknownWeightCheckBox");
 var unknownG = getElementByIdOrByName("proxy.observationVO_s[63].obsValueNumericDT_s[0].numericValue1_s");
 var unknownLbs = getElementByIdOrByName("proxy.observationVO_s[61].obsValueNumericDT_s[0].numericValue1_s");
 var unknownOunce = getElementByIdOrByName("proxy.observationVO_s[62].obsValueNumericDT_s[0].numericValue1_s");
 var unknownWeight = getElementByIdOrByName("proxy.observationVO_s[64].obsValueNumericDT_s[0].numericValue1_s");
 if(unknownWeightCheckBox!=null && unknownWeightCheckBox.checked)
  {
	  unknownWeight.value = -1;
  	  unknownG.disabled = true;
    	  unknownLbs.disabled = true;
  	  unknownOunce.disabled = true;
  }
  else if(unknownWeightCheckBox!=null){
  	  unknownWeight.value = "";
  	  if (unknownG != null) { //gst: fix prob in legacy contact
  	  	unknownG.disabled = false;
    	  	unknownLbs.disabled = false;
  	  	unknownOunce.disabled = false;
  	  }
  }
 }


function loadErrorMessage(){
	var checkErrorConditions = getElementByIdOrByName("deleteError");
	if(checkErrorConditions!=null){
		if(checkErrorConditions.value==""){
				return;
		}
		else{
			alert(checkErrorConditions.value);
		}
	}
}