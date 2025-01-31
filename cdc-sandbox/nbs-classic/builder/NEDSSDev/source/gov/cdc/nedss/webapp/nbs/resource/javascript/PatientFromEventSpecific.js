
function isOtherRaceChecked()
{
	//return true for "checked" return false for others
	var isChecked = getElementByIdOrByName("otherRaceCd").checked;
	if (isChecked)
		getElementByIdOrByName("raceDescTxt").disabled = false;
	else
	{
		getElementByIdOrByName("raceDescTxt").value = "";
		getElementByIdOrByName("raceDescTxt").disabled = true;
	}
}

/**
 *  clears the pre populated data from the patient tab
 *  @param 
 *  @return 
 *
 */
function removePatientPrePropulatedData(){
	var divNodes = document.getElementsByTagName("DIV");
	//using generate id, cannot get node from getelementbyid
	var patientDivNode = null;
	for (var i=0;i<divNodes.length;i++){
		if(getCorrectAttribute(divNodes.item(i),"nid",divNodes.item(i).nid)=="Patient"){
			patientDivNode=divNodes.item(i);
			break;
		}
			
	}
	var inputNodes = patientDivNode.getElementsByTagName("input");
	var selectNodes = patientDivNode.getElementsByTagName("select");
	var textareaNodes = patientDivNode.getElementsByTagName("textarea");
	for (var i=0;i<inputNodes.length;i++){
		if (inputNodes[i].type == "hidden" && inputNodes[i].name!=null && inputNodes[i].name!="" && getCorrectAttribute(inputNodes[i], "mode",inputNodes[i].mode)=="batch-entry") {
			inputNodes[i].value="";
			//initialize the history box
			updateBatchEntryHistoryBox(inputNodes[i].name);
		
		} else if(inputNodes[i].type == "checkbox"){
			inputNodes[i].checked=false;
		} else if(inputNodes[i].id != "defaultState" && inputNodes[i].id != "patientLocalId" && inputNodes[i].id != "DEM215" && inputNodes[i].id != "ContextAction" && inputNodes[i].type != "button"){
			inputNodes[i].value="";
		} 
	}
	for (var i=0;i<selectNodes.length;i++){
		selectNodes[i].value="";
		if(selectNodes[i].onchange) {
			selectNodes[i].onchange();
		}
	}
	for (var i=0;i<textareaNodes.length;i++){
			textareaNodes[i].value="";
			
	}
	 
	populateDefaultState("cntyCd", "Address");
	setDefaultStateFlagToTrue();
		
}

/**
 *  updates the patient information on the event tab with data that may have changed in patient tab
 *  @param 
 *  @return 
 *
 */
function updatePatientFromEventHeaderInfo(){
	//get data from the patient tab
	var lastNameNode = getElementByIdOrByName("DEM102");
	var firstNameNode = getElementByIdOrByName("DEM104");
	var dobNode = getElementByIdOrByName("DEM115");
	

	
	var sexNode = getElementByIdOrByName("DEM113");
	var varOptions = sexNode.options;

	if (sexNode.selectedIndex != -1)
	{
		sexNode = varOptions[sexNode.selectedIndex].text;
	}
	else
	{
		sexNode = "";
	}
	
	//get the top group
	var topGroupNode = getElementByIdOrByName("top-group");
	if(!topGroupNode)
		return;
	
	var topGroupSpanNodes = document.getElementsByTagName("span");
	for (var i=0;i<topGroupSpanNodes.length;i++){
	
			if(topGroupSpanNodes[i].id=="topGroupPatientFirstName")
				setText(topGroupSpanNodes[i],firstNameNode.value);
			else if(topGroupSpanNodes[i].id=="topGroupPatientLastName")
				setText(topGroupSpanNodes[i],lastNameNode.value);
			else if(topGroupSpanNodes[i].id=="dateOfBirth")
				setText(topGroupSpanNodes[i],dobNode.value);
			else if(topGroupSpanNodes[i].id=="patientCurrentSex")
				setText(topGroupSpanNodes[i],sexNode);
			
	}
	
}

function systemSelectedAge(){
	if( getElementByIdOrByName("userEnteredData")!=null){
	 getElementByIdOrByName("userEnteredData").value="true";
	}

}
function userSelectedAge(){
	if( getElementByIdOrByName("userEnteredData")!=null){
	 getElementByIdOrByName("userEnteredData").value="";
	}

}

/**
 *  transfer dob to calc dob if there is a dob
 *	calculate the reported age and units based on the as of date and calc dob
 *	THIS IS FOR EDITABLE 
 *  @param 
 *  @return 
 *
 */

function calculateReportedAge(){	
		
	var dobNode = getElementByIdOrByName("DEM115");
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	
	var asOfDateNode = getElementByIdOrByName("DEM215");
	if(asOfDateNode==null)
		asOfDateNode = getElementByIdOrByName("DEM207");
		
	var reportedAgeNode = getElementByIdOrByName("DEM216");
	var reportedAgeUnitsNode = getElementByIdOrByName("DEM218");
	
//	alert("dobNode:" + dobNode.value);
//	alert("asOfDateNode:" + asOfDateNode.value);
//	alert("reportedAgeNode:" + reportedAgeNode.value);
//	alert("reportedAgeUnitsNode:" + reportedAgeUnitsNode.value);
	
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
	if(getElementByIdOrByName("userEnteredData") ==null && getElementByIdOrByName("DEM216")!= null && getElementByIdOrByName("DEM216").value!=""){
		return false;
	}
	if( getElementByIdOrByName("userEnteredData")!=null){
	        if( getElementByIdOrByName("userEnteredData").value!="true"){
			return false;
		}
	}
	
	//figure out the reported age and units
	//don't show if calc dob is empty
	if (dobNode!=null && dobNode.value!="" && asOfDateNode!=null && asOfDateNode.value!="")	{
		//alert("should reset reportedAgeNode");
		reportedAgeNode.value="";
		reportedAgeUnitsNode.value="";
		
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
				    setText(currentAgeNode,"11");
				    setText(currentAgeUnitsNode,"Months");
				}					
			}
					
		} else {
			reportedAgeNode.value="";
			reportedAgeUnitsNode.value="";
		}
	}
	
	//alert("dobNode.value in calc:" + dobNode.value);
	//alert("calcDOBNode.value in calc:" + calcDOBNode.value);
	//alert("reportedAgeNode.value in calc:" + reportedAgeNode.value);
	
	//for autocomplete, populate the autocomplete text box
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

/**
 *  calc DOB logic for submit
 *  @param 
 *  @return 
 *
 */
function updatePatientFromEventCalcDOBInfo(){
	var dobNode = getElementByIdOrByName("DEM115");
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	var asOfDateNode = getElementByIdOrByName("DEM215");
	if(asOfDateNode==null)
		asOfDateNode = getElementByIdOrByName("DEM207");
	var reportedAgeNode = getElementByIdOrByName("DEM216");
	var reportedAgeUnitsNode = getElementByIdOrByName("DEM218");
	
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
		//for autocomplete, populate the autocomplete text box
		reportedAgeUnitsNode.onclick();
	}
	//alert("dobNode.value in calc:" + dobNode.value);
	//alert("calcDOBNode.value in calc:" + calcDOBNode.value);
	//alert("reportedAgeNode.value in calc:" + reportedAgeNode.value);
	//return false because we aren't doing any validation check here
	return false;
}







///////////////////////////////////////////////////////////
//	AS OF DATES


function patientFromEventAddPopulateAsOfDates() {

	var asOfDate = getElementByIdOrByName("DEM215").value;
	var tmpAsOfDate = asOfDate;
	
	
	
	//Sex and Birth AOD
	var currentSexValue = getElementByIdOrByName("DEM216").value;	
	var dobValue = getElementByIdOrByName("DEM113").value;
	var dob = getElementByIdOrByName("DEM115").value;
	if(currentSexValue.length > 0 || dobValue.length > 0 || dob.length > 0) {
	
		//alert('sex and dob aod');		
		getElementByIdOrByName("patient.thePersonDT.asOfDateSex_s").value = tmpAsOfDate;
	}
	
	
	//Mortality AOD
	var isThePersonDeceasedValue = getElementByIdOrByName("DEM127").value;		
	
	if(isThePersonDeceasedValue.length > 0) {
	
		getElementByIdOrByName("patient.thePersonDT.asOfDateMorbidity_s").value = tmpAsOfDate;
	}
	
	//MaritalStatusAOD
	
	var maritalStatusValue = getElementByIdOrByName("DEM140").value;
	
	if(maritalStatusValue.length > 0) {
	
		getElementByIdOrByName("patient.thePersonDT.asOfDateGeneral_s").value = tmpAsOfDate;
	}
	
	
	//EthnicityAOD
	var ethnicityValue = getElementByIdOrByName("DEM150").value;
	if(ethnicityValue.length > 0) {
		getElementByIdOrByName("patient.thePersonDT.asOfDateEthnicity_s").value = tmpAsOfDate;
	}
	
	
	
	//general comments AOD

	var generalCommentsValue = getElementByIdOrByName("DEM196").value;

	if(generalCommentsValue.length != 0) {		
		
		getElementByIdOrByName("patient.thePersonDT.asOfDateAdmin_s").value = tmpAsOfDate;
	
	}	

	
}//patientFromEventAddPopulateAsOfDates

function patientFromEventEditPopulateAsOfDates() {

	
	//Sex and Birth AOD
	var currentSexValue = getElementByIdOrByName("DEM216").value;	
	var dobValue = getElementByIdOrByName("DEM113").value;
	
	if(currentSexValue.length == 0 && dobValue.length == 0)
		getElementByIdOrByName("patient.thePersonDT.asOfDateSex_s").value = "";
	
	
	//Mortality AOD
	var isThePersonDeceasedValue = getElementByIdOrByName("DEM127").value;		
	
	if(isThePersonDeceasedValue.length == 0)
		getElementByIdOrByName("patient.thePersonDT.asOfDateMorbidity_s").value = "";
	
	//MaritalStatusAOD
	
	var maritalStatusValue = getElementByIdOrByName("DEM140").value;
	
	if(maritalStatusValue.length == 0)
		getElementByIdOrByName("patient.thePersonDT.asOfDateGeneral_s").value = "";
	
	
	//EthnicityAOD
	var ethnicityValue = getElementByIdOrByName("DEM150").value;
	if(ethnicityValue.length == 0)
		getElementByIdOrByName("patient.thePersonDT.asOfDateEthnicity_s").value = "";
	
	
	
	//general comments AOD

	var generalCommentsValue = getElementByIdOrByName("DEM196").value;

	if(generalCommentsValue.length == 0)	
		getElementByIdOrByName("patient.thePersonDT.asOfDateAdmin_s").value = "";

        //ssn comments AOD
	
		var ssnValue = getElementByIdOrByName("DEM133").value;
	        if(ssnValue.length == 0)	
		getElementByIdOrByName("patientSSNAsOfDate").value = "";
	
}//patientFromEventEditPopulateAsOfDates


function patientFromEventAddRequiredAsOfDate(){
	
	var isError = false;
	var labelList = new Array();
	//general comments
	if(getElementByIdOrByName("DEM215").value=="" &&  (getElementByIdOrByName("DEM196").value!="" ||
	//name
	getElementByIdOrByName("DEM102").value!="" || getElementByIdOrByName("DEM104").value!="" || getElementByIdOrByName("DEM105").value!="" || getElementByIdOrByName("DEM107").value!="" ||
	//sex and birth
	getElementByIdOrByName("DEM115").value!="" || getElementByIdOrByName("DEM216").value!="" || getElementByIdOrByName("DEM113").value!="" ||
	//mortality
	getElementByIdOrByName("DEM127").value!="" ||
	//marital
	getElementByIdOrByName("DEM140").value!="" ||
	//SSN
	getElementByIdOrByName("DEM133").value!="" ||
	//address
	getElementByIdOrByName("DEM159").value!="" || getElementByIdOrByName("DEM160").value!="" || getElementByIdOrByName("DEM161").value!="" || getElementByIdOrByName("DEM162").value!="" || getElementByIdOrByName("DEM163").value!="" || getElementByIdOrByName("DEM167").value!="" ||
	//telephone
	getElementByIdOrByName("DEM177").value!="" || getElementByIdOrByName("DEM181").value!="" || getElementByIdOrByName("DEM178").value!="" || getElementByIdOrByName("DEM182").value!="" ||
	//ethnicity
	getElementByIdOrByName("DEM150").value!="" ||
	//race
	getElementByIdOrByName("DEM152").checked || getElementByIdOrByName("DEM153").checked || getElementByIdOrByName("DEM154").checked || getElementByIdOrByName("DEM155").checked || getElementByIdOrByName("DEM156").checked || getElementByIdOrByName("DEM157").checked
	)){
			var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM215"));
			var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM215"),"fieldLabel",getElementByIdOrByName("DEM215").fieldLabel)));           
			
			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			isError=true;
	}
	
	return isError;
}

function patientFromEventEditRequiredAsOfDate(){
	var isError = false;
	var labelList = new Array();
	//general comments section
	if(getElementByIdOrByName("DEM205").value=="" &&  getElementByIdOrByName("DEM196").value!=""){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM205"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM205"),"fieldLabel",getElementByIdOrByName("DEM205").fieldLabel)));           

		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
 	// name section
	if(getElementByIdOrByName("DEM206").value=="" &&  (getElementByIdOrByName("DEM102").value!="" || getElementByIdOrByName("DEM104").value!="" || getElementByIdOrByName("DEM105").value!="" || getElementByIdOrByName("DEM107").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM206"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM206"),"fieldLabel",getElementByIdOrByName("DEM206").fieldLabel)));           
		
		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// sex and birth section
	if(getElementByIdOrByName("DEM207").value=="" &&  (getElementByIdOrByName("DEM115").value!="" || getElementByIdOrByName("DEM216").value!="" || getElementByIdOrByName("DEM113").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM207"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM207"),"fieldLabel",getElementByIdOrByName("DEM207").fieldLabel)));           
		
		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// mortality section
	if(getElementByIdOrByName("DEM208").value=="" &&  (getElementByIdOrByName("DEM127").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM208"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM208"),"fieldLabel",getElementByIdOrByName("DEM208").fieldLabel)));           
		
		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// marital section
	if(getElementByIdOrByName("DEM209").value=="" &&  (getElementByIdOrByName("DEM140").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM209"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM209"),"fieldLabel",getElementByIdOrByName("DEM209").fieldLabel)));           
		
		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// SSN section
	if(getElementByIdOrByName("DEM210").value=="" &&  (getElementByIdOrByName("DEM133").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM210"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM210"),"fieldLabel",getElementByIdOrByName("DEM210").fieldLabel)));           

		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// Address section
	if(getElementByIdOrByName("DEM213").value=="" &&  (getElementByIdOrByName("DEM159").value!="" || getElementByIdOrByName("DEM160").value!="" || getElementByIdOrByName("DEM161").value!="" || getElementByIdOrByName("DEM162").value!="" || getElementByIdOrByName("DEM163").value!="" || getElementByIdOrByName("DEM167").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM213"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM213"),"fieldLabel",getElementByIdOrByName("DEM213").fieldLabel)));           

		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// telephone section
	if(getElementByIdOrByName("DEM214").value=="" &&  (getElementByIdOrByName("DEM177").value!="" || getElementByIdOrByName("DEM181").value!="" || getElementByIdOrByName("DEM178").value!="" || getElementByIdOrByName("DEM182").value!="")){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM214"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM214"),"fieldLabel",getElementByIdOrByName("DEM214").fieldLabel)));           

		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	// ethnicity section
		if(getElementByIdOrByName("DEM212").value=="" &&  (getElementByIdOrByName("DEM150").value!="")){
			var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM212"));
			var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM212"),"fieldLabel",getElementByIdOrByName("DEM212").fieldLabel)));           

			setText(tdErrorCell,errorText);
			setAttributeClass(tdErrorCell,"error");
			isError=true;
	}
	// race section
	if(getElementByIdOrByName("DEM217").value=="" &&  (getElementByIdOrByName("DEM152").checked || getElementByIdOrByName("DEM153").checked || getElementByIdOrByName("DEM154").checked || getElementByIdOrByName("DEM155").checked || getElementByIdOrByName("DEM156").checked || getElementByIdOrByName("DEM157").checked)){
		var tdErrorCell = getTdErrorCell(getElementByIdOrByName("DEM217"));
		var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("DEM217"),"fieldLabel",getElementByIdOrByName("DEM217").fieldLabel)));           

		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		isError=true;
	}
	
	return isError;
}

function patientFromEventDOBLessThanAsOfDateValidation(){
	var labelList = new Array();
	var dobNode = getElementByIdOrByName("DEM115");
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	var asOfDateNode = getElementByIdOrByName("DEM215");
	if(asOfDateNode==null)
		asOfDateNode = getElementByIdOrByName("DEM207");
	var asOfDate = new Date(asOfDateNode.value);
	var calcDOBDate = new Date(calcDOBNode.value);
	if(asOfDate<calcDOBDate){
		var tdErrorCell = getTdErrorCell(dobNode);
		var errorText = makeErrorMsg('ERR006', labelList.concat(getCorrectAttribute(dobNode,"fieldLabel",dobNode.fieldLabel)).concat(getCorrectAttribute(asOfDateNode,"fieldLabel",asOfDateNode.fieldLabel)));           

		setText(tdErrorCell,errorText);
		setAttributeClass(tdErrorCell,"error");
		return true;
	}
	return false;
}



function patientFromEventBatchAsOfDateValidation(){
	//only do for edit
	if(getElementByIdOrByName("patient.entityIdDT_s[i].asOfDate_s")!=null){
		var isError = false;
		var labelList = new Array();
		if(getElementByIdOrByName("patient.entityIdDT_s[i].asOfDate_s").value=="" &&  (getElementByIdOrByName("patient.entityIdDT_s[i].typeCd").value!="" || getElementByIdOrByName("patient.entityIdDT_s[i].assigningAuthorityCd").value!="" || getElementByIdOrByName("patient.entityIdDT_s[i].rootExtensionTxt").value!="" )){
				var tdErrorCell = getTdErrorCell(getElementByIdOrByName("patient.entityIdDT_s[i].asOfDate_s"));
				var errorText = makeErrorMsg('ERR112', labelList.concat(getCorrectAttribute(getElementByIdOrByName("patient.entityIdDT_s[i].asOfDate_s"),"fieldLabel",getElementByIdOrByName("patient.entityIdDT_s[i].asOfDate_s").fieldLabel)));           

				setText(tdErrorCell,errorText);
				setAttributeClass(tdErrorCell,"error");
				isError=true;
		}
		return isError;
	} else
		return false;
}

function patientFromEventAsOfDateValidation(){
	//do populateAsOfDates function when this is add situation, can tell this because DEM215 will exist only on add
	var isError = false;
	if(getElementByIdOrByName("DEM215")!=null){
		patientFromEventAddPopulateAsOfDates();
		isError = patientFromEventAddRequiredAsOfDate();
	}else{
		patientFromEventEditPopulateAsOfDates();
		isError = patientFromEventEditRequiredAsOfDate();
	}
	isError = patientFromEventDOBLessThanAsOfDateValidation()||isError;
	return isError;
}

function populateWait(target, parentID)
{
	var timeoutInt = window.setTimeout("populateDefaultState(" + "'"+ target +"','"+ parentID + "')", 500);
}

function populateDefaultState(target, parentID){
//alert("It is here");
	if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	{
	    //alert("It is here also");
		var x = screen.availWidth;
		var y = screen.availHeight; 

		var selectedCd = getElementByIdOrByName("defaultState").value;
		getElementByIdOrByName("DEM162").value = selectedCd;
		//alert(selectedCd);
		var sParent = "";
		if(selectedCd.parent)
			sParent = selectedCd .parent;
			

		ChildWindowHandle = window.open("/nbs/dynamicSelect?elementName="+target + '&amp;inputCd=' + selectedCd +'&amp;batchName=' + sParent,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		//self.focus();
		var updateNode = getElementByIdOrByName(target);
		getElementByIdOrByName("stateCd_textbox").value=getElementByIdOrByName("DEM162").options[getElementByIdOrByName("DEM162").selectedIndex].text;
		
		
	}
	else
	{
		populateWait(target, parentID);
	}
}

function defaultcountryCd(form)
{
   var state = "";
   var timeOut = window.setTimeout("defaultcountry(" + "'"+ form +"')", 1500);	      
   if(form == "basic")
   {
    
    	state = getElementByIdOrByName("stateCd");
    	if(state!=null && state.value !=null && state.value!="")
	{
		getElementByIdOrByName("countryCd").value="840";
		getElementByIdOrByName("hiddenCountryCd").value="840";
		getElementByIdOrByName("countryCd").disabled = true;
		
	}
	else
	{
		getElementByIdOrByName("countryCd").value="";
		getElementByIdOrByName("countryCd").disabled=false;
	}
   }
   else if(form == "extended")
   {
	state = getElementByIdOrByName("address[i].thePostalLocatorDT_s.stateCd");
	if(state!=null && state.value !=null && state.value!="")
	{
		getElementByIdOrByName("address[i].thePostalLocatorDT_s.cntryCd").value="840";
		getElementByIdOrByName("address[i].thePostalLocatorDT_s.cntryCd").disabled=true;
	}
	else
	{
		getElementByIdOrByName("address[i].thePostalLocatorDT_s.cntryCd").value="";
		getElementByIdOrByName("address[i].thePostalLocatorDT_s.cntryCd").disabled=false;
	}
   }
   else if(form == "event")
   {
      if(getElementByIdOrByName("countryCd")!=null && getElementByIdOrByName("countryCd").value == "840")
      {
                getElementByIdOrByName("hiddenCountryCd").value="840";
		getElementByIdOrByName("countryCd").disabled = true;
      }
   }
   
}

function setDefaultStateFlagToTrue()
{
   //alert("setting the flag true");
   if(getElementByIdOrByName('defaultStateFlag')!=null)
   {
     //alert("In the setDefaultStateFlag :"+getElementByIdOrByName('defaultStateFlag').value);
     getElementByIdOrByName('defaultStateFlag').value = true;
   }
   if(getElementByIdOrByName('defaultStateFlagEx')!=null)
   {
     //alert("In the setDefaultStateFlag 2:"+getElementByIdOrByName('defaultStateFlagEx').value);
     getElementByIdOrByName('defaultStateFlagEx').value = true;
   }
}
batchEntryValidationArray[0] = patientFromEventBatchAsOfDateValidation;

tabSwitchFunctionArray[0] = updatePatientFromEventHeaderInfo;
//tabSwitchFunctionArray[1] = updatePatientFromEventCalcDOBInfo;


validationArray[0] = calculateReportedAge;
validationArray[1] = updatePatientFromEventCalcDOBInfo;
validationArray[2] = patientFromEventAsOfDateValidation;
