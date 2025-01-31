

function unknownRaceAOD(){
	var node = getElementByIdOrByName("unknownRaceDate");
	var testNode = getElementByIdOrByName("unknownRace");
	
	
	var trNode = node.parentNode;
	while(trNode.nodeName!="TR")
	{
			trNode = trNode.parentNode;
	}
	
	if(testNode.checked){
		//hide the entire tr
		trNode.className="visible";
	}else{
		//show the entire tr
		trNode.className="none";
	}
}

function unknownRaceAODView(){
	var node = getElementByIdOrByName("unknownRaceDate");
	var testNode = getElementByIdOrByName("unknownRace");
	
	
	var trNode = node.parentNode;
	while(trNode.nodeName!="TR")
	{
			trNode = trNode.parentNode;
	}
	
	if(testNode.innerText!=""){
		//hide the entire tr
		trNode.className="visible";
	}else{
		//show the entire tr
		trNode.className="none";
	}
}

/* Rules for displaying Calculated DOB for MPR
 * (1) If dateofbirth is entered for Master Patient, calculateDOB = CurrentDate - PatientDOB  
 * (2) Also, note that calculate age and units are not to be persisted anywhere, but should be displaying on the fly based
 *   on the above criteria.
 */



function updatePatientDOBInfo(contextAction) {
	
	//var today = new Date();
	//var formatToday = (today.getMonth()+1) + "/" + (today.getDate()) + "/" + (today.getYear());
	
        
	var dobNode = getElementByIdOrByName("patientDOB");
	var serverDate = getElementByIdOrByName("today") == null ? "" : getElementByIdOrByName("today").value;
	
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	
	var currentAgeNode = getElementByIdOrByName("currentAge");	
	var currentAgeUnitsNode = getElementByIdOrByName("currentAgeUnits");	
	
	//var tdErrorCell = getTdErrorCell(element);			
	//var labelList = new Array();
	//tdErrorCell.className = "none";
	


		if((dobNode.value.length > 0) && (isDate(dobNode.value))){
			
			calcDOBNode.value = dobNode.value;		
			

		} 

	
	//figure out the current age and units, don't show if calc dob is empty

	if(calcDOBNode.value.length > 0 && dobNode.value.length > 0)	{

			
		
		var asOfDate = new Date(serverDate);
		var calcDOBDate = new Date(calcDOBNode.value);

		var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
		//alert('reportedAgeMilliSec' + reportedAgeMilliSec);
		
		
		if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec > 0 && CompareDateStrings(calcDOBNode.value, "12/31/1875") != -1){
			
			
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30.41; 
			var reportedAgeYears = reportedAgeMonths/12;
			
			
			if(isLeapYear(calcDOBDate.getFullYear())) reportedAgeMonths = Math.floor(reportedAgeDays)/30.5;

               //alert("age" + reportedAgeMonths  + "   " + reportedAgeDays + "   " + reportedAgeYears );
			if(Math.ceil(reportedAgeDays)<=28){
				
				currentAgeNode.innerText=Math.floor(reportedAgeDays)+'  ';
				currentAgeUnitsNode.innerText="Days";				
				
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
                    
				// As per lisa if days are  greater than 28 and lessthan 31 it should be one month 
				if(Math.ceil(reportedAgeDays) > 28 && Math.ceil(reportedAgeDays) < 31)
				reportedAgeMonths = reportedAgeMonths + 1;
				
				currentAgeNode.innerText=Math.floor(reportedAgeMonths) + '  ';
				currentAgeUnitsNode.innerText="Months";		
				
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
				currentAgeNode.innerText=yearDiff + '  ';//Math.floor(reportedAgeYears);

				currentAgeUnitsNode.innerText="Years";
                    
                    //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
				if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && reportedAgeYears > 1 && reportedAgeYears < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
				{
				    currentAgeNode.innerText="11" + '  ';
				    currentAgeUnitsNode.innerText="Months";
				}				

			}
					
		} else {
			
				//tdErrorCell.innerText = makeErrorMsg('ERR004', labelList.concat(element.fieldLabel));
				//tdErrorCell.className = "error";
			currentAgeNode.innerText="" + '  ';
			currentAgeUnitsNode.innerText="";
		    if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec == 0)
		    {
		      	currentAgeNode.innerText="0" + '  ';
		      	currentAgeUnitsNode.innerText="Days";	
		    }			
			
		}
	}
	//clear reported age and units if calc dob is blank
	else	{
		if(contextAction.value == "AddExtended") {
		
			calcDOBNode.value="";
		}
		currentAgeNode.innerText="" + '  ';
		currentAgeUnitsNode.innerText="";
	}

} //updatePatientDOBInfo()

function isLeapYear(varyear)
{
       var leapyear = false;
       if((varyear% 4) == 0) leapyear = true;
       if((varyear% 100) == 0) leapyear = false;
       if((varyear% 400) == 0) leapyear = true;
       return leapyear;
       
}

function displayCalcAge() {

	var dobNode = getElementByIdOrByName("patientDOB");
	var serverDate = getElementByIdOrByName("today") == null ? "" : getElementByIdOrByName("today").value;
	
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	var reportedAgeNode = getElementByIdOrByName("hiddenReportedAge");
	var reportedAgeUnit = getElementByIdOrByName("hiddenReportedAgeUnit");
		
	var currentAgeNode = getElementByIdOrByName("currentAge");	
	var currentAgeUnitsNode = getElementByIdOrByName("currentAgeUnits");
	
	var currentAgeFileSummaryNode = getElementByIdOrByName("currentAgeFileSummary");	
	var currentAgeUnitsFileSummaryNode = getElementByIdOrByName("currentAgeUnitsFileSummary");
	       
         //alert("displayCalcAge dobNode " + dobNode.value + "  serverDate : " + serverDate.value + " calcDOBNode : " + calcDOBNode.value   );

		if(dobNode != null &&  (dobNode.value.length > 0) && (isDate(dobNode.value))){	
			calcDOBNode.value = dobNode.value;
		} 

	
	//figure out the current age and units, don't show if calc dob is empty

	if(calcDOBNode.value.length > 0)	{

		//alert('calc dob node is not empty');		
		
		var asOfDate = new Date(serverDate);
		var calcDOBDate = new Date(calcDOBNode.value);
		

		var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
		//alert('reportedAgeMilliSec' + reportedAgeMilliSec);
		
		
		if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec > 0 && CompareDateStrings(calcDOBNode.value, "12/31/1875") != -1){
			
			
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30.41; 
			var reportedAgeYears = reportedAgeMonths/12;
			
			if(isLeapYear(calcDOBDate.getFullYear())) reportedAgeMonths = Math.floor(reportedAgeDays)/30.5;

               //alert("age" + reportedAgeMonths  + "   " + reportedAgeDays + "   " + reportedAgeYears );
			if(Math.ceil(reportedAgeDays)<=28){
				currentAgeNode.innerText=Math.floor(reportedAgeDays);
				currentAgeUnitsNode.innerText="Days";
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=Math.floor(reportedAgeDays);
					currentAgeUnitsFileSummaryNode.innerText="Days";
				}
				
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
				// As per lisa if days are  greater than 28 and lessthan 31 it should be one month 
				if(Math.ceil(reportedAgeDays) > 28 && Math.ceil(reportedAgeDays) < 31)
				reportedAgeMonths = reportedAgeMonths + 1;
				
				currentAgeNode.innerText=Math.floor(reportedAgeMonths);
				currentAgeUnitsNode.innerText="Months";	
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=Math.floor(reportedAgeMonths);
					currentAgeUnitsFileSummaryNode.innerText="Months";
				}
				
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
				currentAgeNode.innerText=yearDiff;//Math.floor(reportedAgeYears);
				currentAgeUnitsNode.innerText="Years";
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=yearDiff;
					currentAgeUnitsFileSummaryNode.innerText="Years";
				}
                    
                    //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
				if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && reportedAgeYears > 1 && reportedAgeYears < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
				{
				    currentAgeNode.innerText="11";
				    currentAgeUnitsNode.innerText="Months";
				    if(currentAgeFileSummaryNode!=null){
						currentAgeFileSummaryNode.innerText="11";
						currentAgeUnitsFileSummaryNode.innerText="Months";
					}
				}				

			}
					
		} else {
			
			currentAgeNode.innerText="";
			currentAgeUnitsNode.innerText="";
		    if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec == 0)
		    {
		      	currentAgeNode.innerText="0";
		      	currentAgeUnitsNode.innerText="Days";	
		      	if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText="0";
					currentAgeUnitsFileSummaryNode.innerText="Days";
				}
		    }			
			
		}
	}

} //diaplayCalcAge()

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

function isMultipleBirthChanged()
{
	var multipleBirthValue = getElementByIdOrByName("personthePersonDTmultipleBirthInd").value;
	if (multipleBirthValue == "Y")
		getElementByIdOrByName("personthePersonDTbirthOrderNbr").disabled = false;
	else
	{
		getElementByIdOrByName("personthePersonDTbirthOrderNbr").value = "";
		getElementByIdOrByName("personthePersonDTbirthOrderNbr").disabled = true;
	}
}



function populateAsOfDates(type) {

	var asOfDate = getElementByIdOrByName("generalAsOfDate").value;
	var tmpAsOfDate = asOfDate;
	var atLeastOneSearchCriteria = false;
	var tdErrorCell = getTdErrorCell(getElementByIdOrByName("asOfDateServer"));			
	//tdErrorCell.setAttribute("className", "none");
	var labelList = new Array();
	var errorText=""; 	
	
	//Name AOD
	var firstNmValue = getElementByIdOrByName("person.firstNm").value;	
	var lastNmValue = getElementByIdOrByName("person.lastNm").value;
	var middleNmValue = getElementByIdOrByName("person.middleNm").value;
	var suffix = getElementByIdOrByName("person.nmSuffix").value;
	
	if(firstNmValue.length > 0 || lastNmValue.length > 0 || middleNmValue.length > 0 || suffix.length > 0) {		
		
		//alert('name aod ' + tmpAsOfDate);
		//getElementByIdOrByName("person.asOfDate_s").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	
	//Sex and Birth AOD
	var currentSexValue = getElementByIdOrByName("person.thePersonDT.currSexCd").value;	
	var dobValue = getElementByIdOrByName("person.thePersonDT.birthTime_s").value;
	
	if(currentSexValue.length > 0 || dobValue.length > 0) {
	
		//alert('sex and dob aod');		
		getElementByIdOrByName("person.thePersonDT.asOfDateSex_s").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	
	//Mortality AOD
	var isThePersonDeceasedValue = getElementByIdOrByName("person.thePersonDT.deceasedIndCd").value;	
	var deceasedDateValue = getElementByIdOrByName("person.thePersonDT.deceasedTime_s").value;	
	
	if(isThePersonDeceasedValue.length > 0 || deceasedDateValue.length > 0) {
	
		getElementByIdOrByName("person.thePersonDT.asOfDateMorbidity_s").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	//MaritalStatusAOD
	
	var maritalStatusValue = getElementByIdOrByName("person.thePersonDT.maritalStatusCd").value;
	
	if(maritalStatusValue.length > 0) {
	
		getElementByIdOrByName("person.thePersonDT.asOfDateGeneral_s").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	//ID Info AOD
	/*
	var SSNValue = getElementByIdOrByName("DEM133").value;
	var typeValue = getElementByIdOrByName("person.entityIdDT_s[i].typeCd").value;
	var assigningAuthorityValue = getElementByIdOrByName("person.entityIdDT_s[i].assigningAuthorityCd").value;
	var idValue = getElementByIdOrByName("person.entityIdDT_s[i].rootExtensionTxt").value;
	
	if(SSNValue.length > 0 ||typeValue.length > 0 || assigningAuthorityValue.length > 0 || idValue.length > 0 ) {
	
		getElementByIdOrByName("DEM210").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	*/
	
	//EthnicityAOD
	var ethnicityValue = getElementByIdOrByName("person.thePersonDT.ethnicGroupInd").value;
	if(ethnicityValue.length > 0) {
		getElementByIdOrByName("person.thePersonDT.asOfDateEthnicity_s").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	
	//RaceAOD (Unknown)
	var isChecked = getElementByIdOrByName("unknownRace").checked;
	if(isChecked) {
		//alert(tmpAsOfDate);
		getElementByIdOrByName("unknownRaceDate").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;	
	}
	
	
	//RaceAOD (AmericanIndian or Alaska Native)
	isChecked = getElementByIdOrByName("1002-5").checked;	
	if(isChecked) {		
		//alert(tmpAsOfDate);
		getElementByIdOrByName("1002-5date").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}

	//RaceAOD (Asian)
	isChecked = getElementByIdOrByName("2028-9").checked;	
	if(isChecked) {		
		getElementByIdOrByName("2028-9date").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	//RaceAOD (BlackorAfricanAmerican)
	isChecked = getElementByIdOrByName("2054-5").checked;	
	if(isChecked) {		
		getElementByIdOrByName("2054-5date").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	//RaceAOD (NativeHawaiian)
	isChecked = getElementByIdOrByName("2076-8").checked;	
	if(isChecked) {		
		getElementByIdOrByName("2076-8date").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	//RaceAOD (White)
	isChecked = getElementByIdOrByName("2106-3").checked;	
	if(isChecked) {		
		getElementByIdOrByName("2106-3date").value = tmpAsOfDate;
		atLeastOneSearchCriteria = true;
	}
	
	//RaceAOD (Other)
	//isChecked = getElementByIdOrByName("otherRaceCd").checked;	
	//if(isChecked) {		
		//alert('race other ' + tmpAsOfDate);
	//	getElementByIdOrByName("otherRaceCdDate").value = tmpAsOfDate;
	//	atLeastOneSearchCriteria = true;
	//}
	
	
	//Basic Demographic data AOD

	var generalCommentsValue = getElementByIdOrByName("person.thePersonDT.description").value;

	if(generalCommentsValue.length > 0) {
		atLeastOneSearchCriteria = true;
	}
	if(type== "true" || generalCommentsValue.length > 0) {		
	
		//alert(tmpAsOfDate);		
		getElementByIdOrByName("person.thePersonDT.asOfDateAdmin_s").value = tmpAsOfDate;
		
	}	
	
	if( (atLeastOneSearchCriteria == true) && (getElementByIdOrByName("asOfDateServer").value.length == 0)) 
	{
		//alert(atLeastOneSearchCriteria);	
		
		errorText = makeErrorMsg("ERR112", labelList.concat(getElementByIdOrByName("asOfDateServer").fieldLabel));
		
		if( tdErrorCell.innerText == "" )
			tdErrorCell.innerText = errorText;
		else {
			tdErrorCell.innerText = errorText;
		}
		tdErrorCell.className = "error";
		
		return true;
		
	}	
	
	return false;
	
}//populateAsOfDates


function saveExtendedAODs() {

	var atLeastOne = false;
	var errorLabels = new Array();

	//Administrative Information AOD
	var generalCommentsValue = getElementByIdOrByName("GenComments").value;

	if(generalCommentsValue.length == 0) {		
		
		getElementByIdOrByName("AsofDateAdmin").value = "";
	
	} else if(generalCommentsValue.length > 0 && (getElementByIdOrByName("AsofDateAdmin").value.length == 0)) {
		
		atLeastOne = true;
		$j("#AsofDateAdmin").focus();
		getElementByIdOrByName("AsofDateAdminL").style.color="#CC0000";
		var errHref = "<a href=\"javascript: getElementByIdOrByName('AsofDateAdmin').focus()\">Administrative Information As Of</a> is a required field.";
		errorLabels.push(errHref);
		
	}
	if(!atLeastOne)
     getElementByIdOrByName("AsofDateAdminL").style.color="black";
	
	
	
	//sex and birth AOD	
		
	var atLeastOne1 = false;
	var currentSex = getElementByIdOrByName("CurrSex");
	var birthSex = getElementByIdOrByName("BirSex");
	var dob = getElementByIdOrByName("patientDOB");
	var multipleBirth = getElementByIdOrByName("MulBir");
	var orderBirth = getElementByIdOrByName("BirOrder");
	var birthCity = getElementByIdOrByName("BirCity");
	var birthState = getElementByIdOrByName("BirState");
	var birthCountry =getElementByIdOrByName("BirCntry");
	var birthCounty =getElementByIdOrByName("BirCnty");
	
	if( (currentSex != null && currentSex.value.length == 0) && (birthSex != null && birthSex.value.length == 0) && 
	    (dob != null && dob.value.length == 0) && (multipleBirth != null && multipleBirth.value.length == 0) && 
	    (birthCity != null && birthCity.value.length == 0) && (birthState != null && birthState.value.length == 0) && 
	    (birthCountry != null && birthCountry.value.length == 0) && (orderBirth != null && orderBirth.value.length == 0) &&
	    (birthCounty != null && birthCounty.value.length == 0)) 
	{

		getElementByIdOrByName("AsofDateSB").value = "";
	
	} else if(getElementByIdOrByName("AsofDateSB").value.length == 0){		

		atLeastOne1 = true;
		$j("#AsofDateSB").focus();
		getElementByIdOrByName("AsofDateSBL").style.color="#CC0000";
		var errHref = "<a href=\"javascript: getElementByIdOrByName('AsofDateSB').focus()\">Sex and Birth Information As Of</a> is a required field.";
		errorLabels.push(errHref);
	}
	if(!atLeastOne1){
	
     getElementByIdOrByName("AsofDateSBL").style.color="black";
     }

	
	
	//mortality AOD
	var atLeastOne2 = false;
	var patientDeceased = getElementByIdOrByName("PatDeceased");
	var dod = getElementByIdOrByName("DateOfDeath");
	var deathCity = getElementByIdOrByName("DeathCity");
	var deathState = getElementByIdOrByName("DeathState");
	var deathCountry =getElementByIdOrByName("DeathCntry");
	var deathCounty =getElementByIdOrByName("DeathCnty");
	
	if((patientDeceased != null && patientDeceased.value.length == 0) && (dod != null && dod.value.length == 0) && 
	   (deathCity != null && deathCity.value.length == 0) && (deathState != null && deathState.value.length == 0) &&
	   (deathCountry != null && deathCountry.value.length == 0) && (deathCounty != null && deathCounty.value.length == 0)  ) {		

		getElementByIdOrByName("AsofDateMorb").value = "";
	
	} else if(getElementByIdOrByName("AsofDateMorb").value.length == 0){		
	
		atLeastOne2 = true;
		$j("#AsofDateMorb").focus();
		getElementByIdOrByName("AsofDateMorbL").style.color="#CC0000";
		var errHref = "<a href=\"javascript: getElementByIdOrByName('AsofDateMorb').focus()\">Mortality Information As Of</a> is a required field.";
		errorLabels.push(errHref);
	}
	if(!atLeastOne2)
     getElementByIdOrByName("AsofDateMorbL").style.color="black";
	
	//general AOD
	var atLeastOne3 = false;
		var maidenName = getElementByIdOrByName("MomMNm");
		var adults =  getElementByIdOrByName("NumAdults");
		var children =  getElementByIdOrByName("NumChild");
		var education =  getElementByIdOrByName("HLevelEdu");
		var occupation =  getElementByIdOrByName("PriOccup");
		var marital =  getElementByIdOrByName("MarStatus");
		var primaryLang =  getElementByIdOrByName("PriLang");
		var speakEnglish =  getElementByIdOrByName("SpeaksEnglishCd");
		var eHARSId =  getElementByIdOrByName("eHARSID");
		var eHARSNotPresent = true;  //if no permission - user won't see State Case Id
		if (eHARSId != null && eHARSId.value.length > 0)
			eHARSNotPresent = false;
	
	if( (speakEnglish != null && speakEnglish.value.length == 0) && eHARSNotPresent && (maidenName != null && maidenName.value.length == 0) && (adults != null && adults.value.length == 0) && (children != null && children.value.length == 0) && (education != null && education.value.length == 0) && (occupation != null && occupation.value.length == 0) && (marital != null && marital.value.length == 0) && (primaryLang != null && primaryLang.value.length == 0)) 
	{
	
		getElementByIdOrByName("AsOfDateGenInfo").value = "";

	} else if(getElementByIdOrByName("AsOfDateGenInfo").value.length == 0){		
	
		atLeastOne3 = true;
		getElementByIdOrByName("AsOfDateGenInfoL").style.color="#CC0000";
		var errHref = "<a href=\"javascript: getElementByIdOrByName('AsOfDateGenInfo').focus()\">General Information As Of</a> is a required field.";
		errorLabels.push(errHref);
	}
	if(!atLeastOne3)
     getElementByIdOrByName("AsOfDateGenInfoL").style.color="black";
	

	
	
	
	//ethnicity AOD
	
	
	var atLeastOne4 = false;
	var ethnicity = getElementByIdOrByName("INV2002");
	if(ethnicity != null && ethnicity.value.length == 0) {
	
		getElementByIdOrByName("AsOfDateEth").value = "";

	} else if(getElementByIdOrByName("AsOfDateEth").value.length == 0){		
	
		atLeastOne4 = true;
		$j("#AsOfDateEth").focus();
		getElementByIdOrByName("AsOfDateEthL").style.color="#CC0000";
		var errHref = "<a href=\"javascript: getElementByIdOrByName('AsOfDateEth').focus()\">Ethnicity Information As Of</a> is a required field.";
		errorLabels.push(errHref);
	}
	if(!atLeastOne4)
     getElementByIdOrByName("AsOfDateEthL").style.color="black";
	
	if (errorLabels.length > 0) {
		        displayErrors('PatientSubmitErrorMessages', errorLabels); 
		        return true;
		}			
	
	
	//hide error messages;
	$j('#PatientSubmitErrorMessages').css("display", "none");
	return false;


}//saveExtendedAODs



function PersonSubmit(){
	
	//to store as of dates when corresponding fields are entered by user.(ONLY FOR ADD BASIC)		
	
			
	var contextAction = getElementByIdOrByName("ContextAction");		
	
	//alert(contextAction.value);
	
	updatePatientDOBInfo(contextAction);
	
	if(getElementByIdOrByName("patientBasic") != null && contextAction.value == "AddExtended") {
		//alert(contextAction.value);
		return populateAsOfDates("true");
		
	
	}
	if(getElementByIdOrByName("patientBasic") != null && contextAction.value == "Submit") {
		
		return populateAsOfDates("false");		
	}

	if(getElementByIdOrByName("patientExtended") != null) {
		//alert('inside add extended');
		return saveExtendedAODs();		
	}

		
	//return false if no problems
		var errorTD = getElementByIdOrByName("errorRange");
		errorTD.setAttribute("className", "none");
		var errorText="";
	
		var atLeastOneFieldEntered = false;
		var allAreEmpty = true;
	
	    var frm = getElementByIdOrByName("nedssForm");
	
	
		var inputNodes = frm.getElementsByTagName("input");
		var selectNodes = frm.getElementsByTagName("select");
		var checkNodes = frm.getElementsByTagName("checkbox");
		
		for (var i=0; i < inputNodes.length; i++) 
		{
			if(inputNodes[i].type=="text" || inputNodes[i].type=="checkbox" && (inputNodes[i].value!="" && inputNodes[i].value!=null)){
				atLeastOneFieldEntered = true;
				break;				 
			}
				
		}				
		if(atLeastOneFieldEntered==false){
			for (var i=0; i < selectNodes.length; i++) 
			{
					if(selectNodes[i].value!="" && selectNodes[i].value!=null){
						atLeastOneFieldEntered = true;
						break;
					}					
			}
		}	
		
		// do the appropriate error message
	
		if( atLeastOneFieldEntered == false ) {
			
			errorText = "You must enter data in at least one field.\n";
			
			errorTD.innerText = errorText;
			errorTD.setAttribute("className", "error");
			
		} 


		
		
		if(atLeastOneFieldEntered==false)
			return true;
		else
			return false;
	
}

validationArray[0] = PersonSubmit;






function submitToExtended()
{
     var contextAction = getElementByIdOrByName("ContextAction");
	 contextAction.value="AddExtended";
	 submitForm();
}

function showErrorCell(element) {

	var tdErrorCell ="";
	var labelList = new Array();
	var errorText=""; 	
	

	errorText = makeErrorMsg("ERR112", labelList.concat(element.fieldLabel));
	tdErrorCell = getTdErrorCell(element);
	//tdErrorCell.innerText = errorText;
	if( tdErrorCell.innerText == "" )
		tdErrorCell.innerText = errorText;
	else {
		tdErrorCell.innerText = errorText;
	}
	tdErrorCell.className = "error";
}

function enableOrDisable(fromBoolField, toTextField, fromFieldValuesList) {
	var fromBoolNode = getElementByIdOrByName(fromBoolField);
	var toNode = $j("#" + toTextField);
	//alert("Selected value:" + fromBoolNode.value);
	var matchFlag = "";
	if (fromBoolNode == null) {
		return;
	}
	var fromFieldValuesList = fromFieldValuesList.split("|");
	for ( var i = 0; i < fromFieldValuesList.length; i++) {
		if (fromBoolNode.value == fromFieldValuesList[i]) {
			matchFlag = 'true';
		}
	}
	if (matchFlag != "true") {
		//not Yes - hide target
		$j("#" + toTextField + "L").css("color", "#666666");
		$j(toNode).parent().parent().find(":input").val("");
		$j(toNode).parent().parent().find(":input").attr("disabled", true);
		$j(toNode).parent().parent().find("img").attr("disabled", true);
		$j(toNode).parent().parent().find("img").attr("tabIndex", "-1");
		
	} else {
		//show target
		$j("#" + toTextField + "L").css("color", "#000");
		$j(toNode).parent().parent().find(":input").attr("disabled", false);
		$j(toNode).parent().parent().find("img").attr("disabled", false);
		$j(toNode).parent().parent().find("img").attr("tabIndex", "0");
		
	}
}