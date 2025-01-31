///////////////////////////////////////////////////////////////////////////////////////////
// Hardcoded Rules for the STD page created for the 4.5 Release
// The property file NBSFixedRule.xml is used to assign the rules to the page
// STD was designed from the Foxpro Client/Server app STD*MIS.
// Hence the many overlapping hardcoded rules..
////////////////////////////////////////////////////////////////////////////////////////////


//we have a few globals so that we can revert the user to the previous value
//if they have data in sections that need to be cleared
//these values get set in stdUpdatePageState()
var G_STD_Initial_Followup = "";
var G_STD_Patient_Followup = "";
var G_STD_FF_Disposition = "";
var G_STD_Ix_Status = "";
var alertForCloseToOpenAlert = 0;


function stdUpdatePageState() 
{
  var theInitFollowup = $j('#NBS140 :selected').val();
  if (theInitFollowup != null)
    G_STD_Initial_Followup = theInitFollowup;
  var theSurveillancePatientFollowup = $j('#NBS151 :selected').val();
  if (theSurveillancePatientFollowup != null)
    G_STD_Patient_Followup = theSurveillancePatientFollowup;
  var theFieldFollowupDisposition = $j('#NBS173 :selected').val();
  if (theFieldFollowupDisposition != null)
    G_STD_FF_Disposition = theFieldFollowupDisposition; //set the global to enable revert
  var thePatientInterviewStatus = $j('#NBS192 :selected').val();
  if (thePatientInterviewStatus != null)
    G_STD_Ix_Status = thePatientInterviewStatus;
  var theProcessStage = $j('#NBS115 :selected').val();
  var isCongenitalSyphilis = isConditionCongenitalSyphilis();
  

  // if blank or a closure reason....Only initial followup section enabled
  if (theInitFollowup == null || theInitFollowup == "" || theInitFollowup == "AC" || theInitFollowup == "BFP"
     || theInitFollowup == "DEC" || theInitFollowup == "II" || theInitFollowup == "NPP"
     || theInitFollowup == "PC" || theInitFollowup == "RDV" || theInitFollowup == "RSC" || theInitFollowup == "OTH") {
	  
    stdSurveillanceSectionDisabled(); //Surveillance
    stdFieldFollowupSectionDisabled();//Field Followup
   // pgSubSectionDisabled('NBS_INV_STD_UI_22'); 
    pgSubSectionDisabled('NBS_INV_STD_UI_29'); //Case Assignment
    pgSubSectionDisabled('NBS_INV_STD_UI_32'); //Case Closure
    stdFixedRuleInitialFollowupDisableMiscFields();
    pgEnableElement('NBS141'); //enable if disabled
    G_STD_FF_Disposition = ""; //no FF disposition
    G_STD_Ix_Status = ""; //no interview status
    G_STD_Patient_Followup = ""; //no surveillance patient followup
    if (theInitFollowup != null && theInitFollowup != "") {
      stdSetCurrentProcessStage("NF");
      stdClosedInvestigationStatus();
      //required date closed.
      pgRequireElement('NBS141'); //initial followup date closed
      stdSetDateInvestigationClosed('NBS141');
    }
    return;
  }

  if (theInitFollowup == "SF" && theSurveillancePatientFollowup != "FF") { //Surveillance Followup
    stdSurveillanceSectionEnabled();
    stdFieldFollowupSectionDisabled();//Field Followup 
    pgSubSectionDisabled('NBS_INV_STD_UI_29'); //Case Assignment
    pgSubSectionDisabled('NBS_INV_STD_UI_32'); //Case Closure
    stdFixedRuleInitialFollowupDisableMiscFields();

    stdSetCurrentProcessStage('SF');
    var surveillanceDateClosed = $j("#NBS147").val();
    if (surveillanceDateClosed != null && surveillanceDateClosed == "")
      stdOpenInvestigationStatus();
    else if (theSurveillancePatientFollowup != "")
      stdClosedInvestigationStatus(surveillanceDateClosed);
    else if (theSurveillancePatientFollowup == "")
      stdOpenInvestigationStatus();

    G_STD_FF_Disposition = ""; //no FF
    G_STD_Ix_Status = ""; //no interview
    G_STD_Patient_Followup = ""; //no surveillance patient followup
    return;
  }
  if (theInitFollowup == "SF" && theSurveillancePatientFollowup == "FF") { //Surveillance Followup
    stdSurveillanceSectionEnabled();
    stdFieldFollowupSectionEnabledFromSurveillance();
    stdSetCurrentProcessStage('FF');
    if (theFieldFollowupDisposition != null && theFieldFollowupDisposition != "") {
      stdProcessFieldDisposition();
      if (isCongenitalSyphilis)
    	  pgSubSectionEnabled('NBS_INV_STD_UI_32'); //Case Closure
      else
    	  pgSubSectionEnabled('NBS_INV_STD_UI_29'); //Case Assignment
      // These values are in NBS_INV_STD_UI_29 subsection , but only need to be enabled if NBS192 = U
      if($j('#NBS192').val() != 'U'){
    	  pgDisableElement('NBS276');
    	  pgDisableElement('NBS277');
      }
      //stdSetReadonlyDate('NBS189'); //CA Initial Assign Date
      if (thePatientInterviewStatus != null && thePatientInterviewStatus == "A") {
        stdSetCurrentProcessStage('AI');
        stdOpenInvestigationStatus();
      } else if (isCongenitalSyphilis || (thePatientInterviewStatus != null && thePatientInterviewStatus != "" && thePatientInterviewStatus != "A")) {
        var theCaseClosureDate = $j('#NBS196').val();
        if (theCaseClosureDate != null && theCaseClosureDate != "") {
          stdSetCurrentProcessStage('CC');
          stdClosedInvestigationStatus(theCaseClosureDate);
        } else {
          stdSetCurrentProcessStage('OC');
          stdOpenInvestigationStatus();
        }
      }
    } else {
      pgSubSectionDisabled('NBS_INV_STD_UI_29'); //Case Assignment
      stdOpenInvestigationStatus();
      G_STD_Ix_Status = ""; //no patient interview status

    }
    if (theFieldFollowupDisposition != null && theFieldFollowupDisposition != ""
       && thePatientInterviewStatus != null && thePatientInterviewStatus != ""
       && thePatientInterviewStatus != "A") { //awaiting
      pgSubSectionEnabled('NBS_INV_STD_UI_32'); //Case Closure
    } else {
    	if (isCongenitalSyphilis==false)
    		pgSubSectionDisabled('NBS_INV_STD_UI_32'); //Case Closure
    }
    stdFixedRuleInitialFollowupEnableMiscFields();
    return;
  }

  if (theInitFollowup == "FF") { //Field Followup
    stdSurveillanceSectionDisabled(); 
    stdFieldFollowupSectionEnabled();
    pgRequireNotElement('NBS141'); //Initial Followup date closed
    pgDisableElement('NBS141');
    pgRequireNotElement('NBS148L'); //Surveillance Provider Contact
    pgRequireNotElement('NBS149L'); //Surveillance Exam Reason
    pgRequireNotElement('NBS150L'); //Surveillance Provider Diagnosis
    pgRequireNotElement('NBS151L'); //Surveillance Patient Followup

    stdSetCurrentProcessStage("FF"); //current process state = Field Followup
    stdProcessFieldDisposition();

    var theDispositionDate = $j('#NBS174').val();
    var theCaseClosureDate = $j('#NBS196').val();
    if ((theDispositionDate == null || theDispositionDate == "") &&
      (theCaseClosureDate == null || theCaseClosureDate == "")) {
    	var theInvestigationStatus = $j("#INV109").val();
        if (theInvestigationStatus != "O" && alertForCloseToOpenAlert > -1) {
        	alertForCloseToOpenAlert = 1;
        	var coinfectionId = getElementByIdOrByName("coinfectionId");//.innerText;
        	if(typeof coinfectionId !== "undefined") {
        		alert("You are moving a co-infection investigation from Closed to Open status. Co-infection question data will be copied from any associated OPEN co-infection investigations to this investigation upon Submit. Any co-infection data entered by the user on this investigation will be overwritten by data from open co-infections upon submit. If you need to edit co-infection questions on this investigation, click Submit and then go back into edit mode to make any needed changes.");
        	}
 		}
        stdOpenInvestigationStatus();
    }
    if (theFieldFollowupDisposition != null && theFieldFollowupDisposition != "") {
        if (isCongenitalSyphilis)
      	    pgSubSectionEnabled('NBS_INV_STD_UI_32'); //Case Closure
        else 
        	pgSubSectionEnabled('NBS_INV_STD_UI_29'); //Case Assignment
   // These values are in NBS_INV_STD_UI_29 subsection , but only need to be enabled if NBS192 = U
      if($j('#NBS192').val() != 'U'){
    	  pgDisableElement('NBS276');
    	  pgDisableElement('NBS277');
      }
      //stdSetReadonlyDate('NBS189'); //CA Initial Assign Date
      if (thePatientInterviewStatus == "A") {
        stdSetCurrentProcessStage("AI"); //current process state = Awaiting Interview
        stdOpenInvestigationStatus();

      } else if (thePatientInterviewStatus != null && thePatientInterviewStatus != "" && thePatientInterviewStatus != "A") {
	        if (theCaseClosureDate == null || theCaseClosureDate == "") {
	          stdSetCurrentProcessStage("OC"); //current process state = Open Case
	          stdOpenInvestigationStatus();
        }
      } else if (isCongenitalSyphilis) 
          if (theCaseClosureDate == null || theCaseClosureDate == "") {
            stdOpenInvestigationStatus();
          }
    } else {
        if (isCongenitalSyphilis)
        	pgSubSectionDisabled('NBS_INV_STD_UI_32'); //Case Closure
        else
        	pgSubSectionDisabled('NBS_INV_STD_UI_29'); //Case Assignment
        G_STD_Ix_Status = ""; //no interview status
    }
    if (theFieldFollowupDisposition != null && theFieldFollowupDisposition != ""
       && thePatientInterviewStatus != null && thePatientInterviewStatus != ""
       && thePatientInterviewStatus != "A") //awaiting
      pgSubSectionEnabled('NBS_INV_STD_UI_32'); //Case Closure
    else {
    	if (isCongenitalSyphilis == false)
             pgSubSectionDisabled('NBS_INV_STD_UI_32'); //Case Closure
    }
    if (theCaseClosureDate != null && theCaseClosureDate != "") {
      stdSetCurrentProcessStage('CC');
      stdClosedInvestigationStatus(theCaseClosureDate);
    }
    stdFixedRuleInitialFollowupEnableMiscFields();
    return
  }
  //alert("stdFixedRuleInitialFollowupEntry() Send OOJ");
  return;
}

//If Diagnosis Field (NBS136)  is present - get the options to display
//from the backend
function stdSetupDiagnosisOptions() 
{
  // see if case diagnosis is present
  var elementId = 'NBS136';
  var diagElement = getElementByIdOrByName(elementId);
  if (diagElement == null || typeof(diagElement) == 'undefined') {
    return;
  }
  var curSelection = $j('#' + elementId + ' :selected').text(); 
  var curVal = $j('#' + elementId + ' :selected').val();

  if (curVal != null && curVal != "")
    stdDiagnosisRelatedFields(); // some fields enabled or disabled depending on Diagnosis

  JPageForm.getDwrStdDiagnosis(elementId, function (data) {
    if (data.length > 0) { // user can select or change selection
      DWRUtil.removeAllOptions(elementId);
      DWRUtil.addOptions(elementId, data, "key", "value");
      getElementByIdOrByName(elementId + "L").className = "InputFieldLabel";
      $j("#" + elementId).attr('disabled', false);
      $j("#" + elementId + "_textbox").attr('disabled', false);
      $j("#" + elementId + "_button").attr('disabled', false);
      if (typeof(curSelection) != 'undefined' && curSelection != null && curSelection != "") {
        $j('#' + elementId).val(curVal);
        AutocompleteSynch('NBS136_textbox', 'NBS136');
      }
      // provider diagnosis needs to get the same list if present
      elementId = 'NBS150'
        diagElement = getElementByIdOrByName(elementId);
      if (diagElement != null) {
        curSelection = $j('#' + elementId + ' :selected').text();
        curVal = $j('#' + elementId + ' :selected').val();
        DWRUtil.removeAllOptions(elementId);
        DWRUtil.addOptions(elementId, data, "key", "value");
        $j('#' + elementId).append('<option value="000">Not Infected</option>');
        $j('#' + elementId).append('<option value="999">Unknown</option>');
        getElementByIdOrByName(elementId + "L").className = "InputFieldLabel";
        $j("#" + elementId).attr('disabled', false);
        $j("#" + elementId + "_textbox").attr('disabled', false);
        $j("#" + elementId + "_button").attr('disabled', false);
        if (typeof(curSelection) != 'undefined' && curSelection != null && curSelection != "") {
          $j('#' + elementId).val(curVal);
          AutocompleteSynch('NBS150_textbox', 'NBS150');
        }

      }
      // provider diagnosis is in two subsections
      elementId = 'NBS166'
        diagElement = getElementByIdOrByName(elementId);
      if (diagElement != null) {
        curSelection = $j('#' + elementId + ' :selected').text();
        curVal = $j('#' + elementId + ' :selected').val();
        DWRUtil.removeAllOptions(elementId);
        DWRUtil.addOptions(elementId, data, "key", "value");
        $j('#' + elementId).append('<option value="000">Not Infected</option>');
        $j('#' + elementId).append('<option value="999">Unknown</option>');
        $j("#" + elementId + "L").addClass("InputFieldLabel");
        $j("#" + elementId).attr('disabled', false);
        $j("#" + elementId + "_textbox").attr('disabled', false);
        $j("#" + elementId + "_button").attr('disabled', false);
        if (typeof(curSelection) != 'undefined' && curSelection != null && curSelection != "") {
          $j('#' + elementId).val(curVal);
          AutocompleteSynch('NBS166_textbox', 'NBS166');
        }
      }
    }
  });
}

//If Diagnosis Field (NBS136) changes, update other fields depending on the change
//For Congenital Syphilis, the processing differs..there is no Interview section and case closure is enabled.
function stdDiagnosisRelatedFields() 
{
  //see if case diagnosis is present
  var elementId = 'NBS136';
  var diagElement = getElementByIdOrByName(elementId);
  if (diagElement == null || typeof(diagElement) == 'undefined') {
    return;
  }
  var curSelection = $j('#' + elementId + ' :selected').text();
  var curVal = $j('#' + elementId + ' :selected').val();
  //per requirements... diagnosis not defaulted on condition
  // also STD*MIS says 000 is Not Infected and 999 is Unknown
  if (curVal == null || curVal == "" || curVal == "000" || curVal == "999")
    curVal = stdConditionToDiagnosis();
  if (curVal == null || curVal == "")
    return;


  var dgFirstChar = curVal.charAt(0);
  if (curVal != null & dgFirstChar == "7") { //any Syphillis
    $j("#NBS151 option[value='NPP']").remove(); //Not Program Priority
  } else {
    $j("#NBS151 option[value='90213003']").remove(); //BFP
  }
  return;
}

function stdConditionToDiagnosis() {
  var theConditionCd = $j("#headerConditionCode").text();
  if (theConditionCd == null)
    return ("");
  if (theConditionCd != null && (theConditionCd.indexOf("10311") != -1)) //is Syph Primary
    return ("710");
  if (theConditionCd != null && (theConditionCd.indexOf("10312") != -1)) //is Syph Secondary
    return ("720");
  if (theConditionCd != null && (theConditionCd.indexOf("10313") != -1)) //is Syph Early Latent
    return ("730");
  if (theConditionCd != null && (theConditionCd.indexOf("10315") != -1)) //is Syph Unknown Latent
    return ("740");
  if (theConditionCd != null && (theConditionCd.indexOf("10314") != -1)) //is Syph Late Latent
    return ("745");
  if (theConditionCd != null && (theConditionCd.indexOf("10318") != -1)) //is Syph Not Neuro
    return ("750");
  if (theConditionCd != null && (theConditionCd.indexOf("10316") != -1)) //is Syph Congenital
    return ("790");
  if (theConditionCd != null && (theConditionCd.indexOf("10320") != -1)) //is Syphilis, Unknown Duration or Late
	return ("755");
  if (theConditionCd != null && (theConditionCd.indexOf("10280") != -1)) //is Gon
    return ("300");
  if (theConditionCd != null && (theConditionCd.indexOf("10273") != -1)) //is Chancroid
    return ("100");
  if (theConditionCd != null && (theConditionCd.indexOf("10276") != -1)) //is GI
    return ("500");
  if (theConditionCd != null && (theConditionCd.indexOf("10274") != -1)) //is Chlamydia
    return ("200");
  if (theConditionCd != null && (theConditionCd.indexOf("10309") != -1)) //is PID
    return ("490");
  if (theConditionCd != null && (theConditionCd.indexOf("10307") != -1)) //is NGU
    return ("400");
  if (theConditionCd != null && (theConditionCd.indexOf("900") != -1)) //is HIV
    return ("900");
  if (theConditionCd != null && (theConditionCd.indexOf("10560") != -1)) //is AIDS
    return ("950");
  return ("");
}

function isConditionCongenitalSyphilis() {
	var theConditionCd = $j("#headerConditionCode").text();
	if (theConditionCd != null && (theConditionCd.indexOf("10316") != -1)) //CS
		return true;
	else
		return false;
}

//Check the referral basis for conflicts with other fields
function stdRuleCheckReferralBasisOnSubmit() {
  var i = 0;
  var errorElts = new Array();
  var errorMsgs = new Array();
  //see if referral basis is not present, return no errors...
  var referralBasisElement = getElementByIdOrByName('NBS110');
  var patientInterviewStatusLabel =  getElementByIdOrByName('NBS192L');
  var referralBasisLabel =  getElementByIdOrByName('NBS110L');
  if (referralBasisElement == null || typeof(referralBasisElement) == 'undefined') {
    return { elements : errorElts, labels : errorMsgs };
  }
  if (patientInterviewStatusLabel == null || typeof(patientInterviewStatusLabel) == 'undefined') {
	return { elements : errorElts, labels : errorMsgs }; //Congenital Syphilis
  }
  if (isConditionCongenitalSyphilis()) {
	return { elements : errorElts, labels : errorMsgs }; //Congenital Syphilis
  }
  
  var theReferralBasis = $j('#NBS110 :selected').val();
  //check if Interview Status conflicts with Referral Basis
  var thePatientInterviewStatus = $j('#NBS192 :selected').val();
  if (thePatientInterviewStatus != null && thePatientInterviewStatus == 'N') { //no Cluster Interview done
    if (theReferralBasis != null && (theReferralBasis == 'T1' || theReferralBasis == 'T2')) { //positive test or morbidity report
      var theReferralBasisText = $j('#NBS110 :selected').text();
      var thePatientInterviewStatusText = $j('#NBS192 :selected').text();
      //We only build one anchor link because referral basis is read only
      var a2Str = buildErrorAnchorLink(patientInterviewStatusLabel, "Patient Interview Status");  
      var errHtmlStr =  "The Referral Basis of " + theReferralBasisText + " conflicts with " + a2Str + " of " + thePatientInterviewStatusText;
      errorMsgs[i] = errHtmlStr;
      errorElts[i] = getElementByIdOrByName('NBS192');
      $j('#NBS192L').css("color", "990000");
      i++;
    } else
      $j('#NBS192L').css("color", "black"); //clear if color present
  }
  return { elements : errorElts, labels : errorMsgs };
}


//onLoad enable and disable fields in the Reported as OOJ Contact based on the Referral Basis
function stdReferalBasisExposureFreqEnabReq() {
  //see if referral basis is present
  var refBasElement = getElementByIdOrByName('NBS110');
  if (refBasElement == null || typeof(refBasElement) == 'undefined') {
    return;
  }
  var theReferralBasis = refBasElement.value;
  if (theReferralBasis == null || theReferralBasis == "") {
    return;
  }
  if (theReferralBasis == "T1" || theReferralBasis == "T2") {
  	pgSubSectionHidden('NBS_INV_STD_UI_9');
        //pgSubSectionDisabled('NBS_INV_STD_UI_9');
    return;
  }
  
  if (isConditionCongenitalSyphilis()) {
    return; //Congenital Syphilis
  }
  
  pgSubSectionShown('NBS_INV_STD_UI_9');
  // Reported as OOJ Contact
  var theReferralBasisOOJ = $j('#NBS270 :selected').val();
  if (theReferralBasisOOJ != null && theReferralBasisOOJ != "") {
    pgSubSectionEnabled('NBS_INV_STD_UI_9'); //Reported as OOJ Contact
     pgRequireElement('NBS111L');
     pgEnableElement('NBS127');
    //pgSubSectionDisabled('NBS_INV_STD_UI_37'); //Pregnant Information disabled for Out of Jurisdiction Partner Cluster (OOJ/PC)
  } else {
    pgSubSectionDisabled('NBS_INV_STD_UI_9');
    pgRequireNotElement('NBS111L');
    return;
  }

  var rbFirstChar = theReferralBasis.charAt(0);
  if (rbFirstChar == "A" || rbFirstChar == "S" || rbFirstChar == "C") {
    pgDisableElement('NBS118'); //see reported as OOJ Contact section
    pgDisableElement('NBS119');
    pgDisableElement('NBS120');
    pgDisableElement('NBS121');
    pgDisableElement('NBS122');
    pgDisableElement('NBS123');
    pgRequireNotElement('NBS118'); //if required.. unrequire
    pgRequireNotElement('NBS119');
    pgRequireNotElement('NBS120');
    pgRequireNotElement('NBS121');
    pgRequireNotElement('NBS122');
    pgRequireNotElement('NBS123');
    pgEnableElement('NBS124'); //Relationship
    pgRequireElement('NBS124L');
    return;
  }
  if (theReferralBasis == 'P1') { //partner sex
    pgEnableElement('NBS118');
    pgEnableElement('NBS119');
    pgEnableElement('NBS120');
    pgRequireElement('NBS118');
    pgRequireElement('NBS119');
    pgRequireElement('NBS120');
    pgDisableElement('NBS121');
    pgDisableElement('NBS122');
    pgDisableElement('NBS123');
    pgRequireNotElement('NBS121');
    pgRequireNotElement('NBS122');
    pgRequireNotElement('NBS123');
  } else if (theReferralBasis == 'P2') { //partner needle
    pgEnableElement('NBS121');
    pgEnableElement('NBS122');
    pgEnableElement('NBS123');
    pgRequireElement('NBS121');
    pgRequireElement('NBS122');
    pgRequireElement('NBS123');
    pgDisableElement('NBS118');
    pgDisableElement('NBS119');
    pgDisableElement('NBS120');
    pgRequireNotElement('NBS118');
    pgRequireNotElement('NBS119');
    pgRequireNotElement('NBS120');
  } else if (theReferralBasis == 'P3') { //partner both
    pgEnableElement('NBS118');
    pgEnableElement('NBS119');
    pgEnableElement('NBS120');
    pgRequireElement('NBS118');
    pgRequireElement('NBS119');
    pgRequireElement('NBS120');
    pgEnableElement('NBS121');
    pgEnableElement('NBS122');
    pgEnableElement('NBS123');
    pgRequireElement('NBS121');
    pgRequireElement('NBS122');
    pgRequireElement('NBS123');
  }
  return;
}

//Enable or Disable and Require other fields in the Surveillance section when Date Closed is entered/cleared
function stdSurveillanceDateClosedEntered() 
{
  var theSurveillanceDateClosed = $j('#NBS147').val();
  if (theSurveillanceDateClosed == null)
    return;

  if (theSurveillanceDateClosed == "") {
    //pgDisableElement('NBS148'); //provider contact
    //pgDisableElement('NBS149'); //exam reason
    //pgDisableElement('NBS150'); //provider diagnosis
    //pgDisableElement('NBS151'); //provider followup
    //could be required .. unrequire..
    pgRequireNotElement('NBS148L'); //provider contact
    pgRequireNotElement('NBS149L'); //exam reason
    pgRequireNotElement('NBS150L'); //provider diagnosis
    pgRequireNotElement('NBS151L'); //provider followup
    return;
  }

  //assume Date Closed entered.. enable and require..
  pgSubSectionEnabled('NBS_INV_STD_UI_17'); //Surveillance Information
  //pgEnableElement('NBS148'); //provider contact
  //pgEnableElement('NBS149'); //exam reason
  //pgEnableElement('NBS150'); //provider diagnosis
  //pgEnableElement('NBS151'); //provider followup
  //require..
  pgRequireElement('NBS148L'); //provider contact
  pgRequireElement('NBS149L'); //exam reason
  pgRequireElement('NBS150L'); //provider diagnosis
  pgRequireElement('NBS151L'); //provider followup

  if (window.event != null && window.event.type != 'load') {
    MoveFocusToNextField(getElementByIdOrByName('NBS147'));
  }

  return;

}

// validate Case Status on submit
function stdRuleCheckCaseStatusOnSubmit() {
        var i = 0;
        var errorElts = new Array();
        var errorMsgs = new Array();
          //check if case status conflicts with other settings
          var caseStatusElement = getElementByIdOrByName('INV163');
        if (caseStatusElement == null || typeof(caseStatusElement) == 'undefined') {
              return {elements : errorElts, labels : errorMsgs}
          }
        var theCaseStatus =   $j('#INV163 :selected').val();
        //check if Case Status conflicts with Disposition
        var theDisposition = $j('#NBS173 :selected').val();
        var theInterviewStatus = $j('#NBS192 :selected').val();
        if (theDisposition != null && theDisposition != "") {
            if (theCaseStatus == 'U' && (theDisposition == 'C' || theDisposition == 'D' || theDisposition == 'F' || theDisposition == '1' || theDisposition == '2' || theDisposition == '3' || theDisposition == '5' || theDisposition == '6' ))  {
                var theCaseStatusText = $j('#INV163 :selected').text();
                var theDispositionText = $j('#NBS173 :selected').text();
    		var a2Str = buildErrorAnchorLink(getElementByIdOrByName('INV163L'), "Case Status");   
    		var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS173L'), "Disposition");      		
                errorMsgs[i] = "The " + a1Str + " of " + theDispositionText + " is not consistent with a " + a2Str + " of " + theCaseStatusText + ". Please correct the data and try again.";
                errorElts[i] = getElementByIdOrByName('NBS173');
                $j('#NBS173L').css("color", "990000");
                i++;
             } else if (theCaseStatus == 'P' && (theDisposition == 'A' || theDisposition == 'B' || theDisposition == 'F' || theDisposition == '3' || theDisposition == '6'))  {
                 var a2Str = buildErrorAnchorLink(getElementByIdOrByName('INV163L'), "Case Status");   
    		 var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS173L'), "Disposition");   
                 var theCaseStatusText = $j('#INV163 :selected').text();
                 var theDispositionText = $j('#NBS173 :selected').text();
                 errorMsgs[i] = "The " + a1Str + " of " + theDispositionText + " is not consistent with a " + a2Str + " of " + theCaseStatusText + ". Please correct the data and try again.";
                 errorElts[i] = getElementByIdOrByName('NBS173');
                 $j('#NBS173L').css("color", "990000");
                 i++;
             } else if (theCaseStatus == 'C' && (theDisposition == 'A' || theDisposition == 'B' || theDisposition == 'F' || theDisposition == '3' || theDisposition == '4' || theDisposition == '6' || theDisposition == '7'))  {
                var theCaseStatusText = $j('#INV163 :selected').text();
                var theDispositionText = $j('#NBS173 :selected').text();
                var a2Str = buildErrorAnchorLink(getElementByIdOrByName('INV163L'), "Case Status");   
    		var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS173L'), "Disposition");   
                errorMsgs[i] = "The " + a1Str + " of " + theDispositionText + " is not consistent with a " + a2Str + " of " + theCaseStatusText + ". Please correct the data and try again.";
                errorElts[i] = getElementByIdOrByName('NBS173');
                $j('#NBS173L').css("color", "990000");
                i++;
             } else if (theCaseStatus == 'N' && (theDisposition == 'C' || theDisposition == 'D' || theDisposition == '1' || theDisposition == '2' || theDisposition == '5'))  {
                var theCaseStatusText = $j('#INV163 :selected').text();
                var theDispositionText = $j('#NBS173 :selected').text();
    		var a2Str = buildErrorAnchorLink(getElementByIdOrByName('INV163L'), "Case Status");   
    		var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS173L'), "Disposition");                   
                errorMsgs[i] = "The " + a1Str + " of " + theDispositionText + " is not consistent with a " + a2Str + " of " + theCaseStatusText + ". Please correct the data and try again.";
                errorElts[i] = getElementByIdOrByName('NBS173');
                $j('#NBS173L').css("color", "990000");
                i++;
               }
           }
           if (theInterviewStatus != null && theInterviewStatus != "") {
               if (theCaseStatus == 'U' && theInterviewStatus == 'I')  {
                var theCaseStatusText = $j('#INV163 :selected').text();
                var a1Str = buildErrorAnchorLink(getElementByIdOrByName('INV163L'), "Case Status");   
    		var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS192L'), "Patient Interview Status");   
                var theInterviewStatusText = $j('#NBS192 :selected').text(); //Error 193
                errorMsgs[i] = "The "+ a1Str + " cannot be " + theCaseStatusText + " if the Patient has been interviewed.  Please correct the " + a1Str + "  or " + a2Str + " and try again.";
                   errorElts[i] = getElementByIdOrByName('INV163');
                   $j('#INV163L').css("color", "990000");
                   i++;
               }
           }
           if (i == 0)
               $j('#NBS173L').css("color", "black"); //clear if color present
         return {elements : errorElts, labels : errorMsgs}
    }

   //If Initial Follow-Up changes - enable or disable related fields
    function stdFixedRuleInitialFollowupEntry() {
        var theInitFollowup = $j('#NBS140 :selected').val();
        var theInitFollowupText = $j('#NBS140 :selected').text();
        //if changing to immediate closure - check if data needs to be manually cleared..
        if (theInitFollowup == null || theInitFollowup == "" || theInitFollowup == "AC" || theInitFollowup == "BFP" || theInitFollowup == "II"
            || theInitFollowup == "NPP" || theInitFollowup == "PC"    || theInitFollowup == "RSC" || theInitFollowup == "DEC" || theInitFollowup == "OTH" || theInitFollowup == "RDV") {
            if (G_STD_Initial_Followup != null && (G_STD_Initial_Followup == "SF" || G_STD_Initial_Followup == "FF")) {
                stdClearInitiallyAssignedFields();
                var sectionsWithData = stdFindSectionsWithData("SF FF CA CC");
                if (sectionsWithData != "") {

                    if (theInitFollowup == "")
                    alert("Initial Follow-up cannot be cleared because response(s) exist in the following section(s) " + sectionsWithData + " Please clear the response(s) and try again.");
                else
                    alert("Initial Follow-up cannot be changed to " +theInitFollowupText +" because response(s) exist in the following section(s): " + sectionsWithData  + ". Please clear the response(s) and try again.");
                if (G_STD_Initial_Followup != null && G_STD_Initial_Followup != "")
                          $j('#NBS140').val(G_STD_Initial_Followup);
                    else
                          alert("Initial Logic validity check closure error: no existing value but data in " + sectionsWithData);
                    return;
                }
            } //if was SF or FF
        } //if initial closure or blank
        if (theInitFollowup == "FF" && G_STD_Initial_Followup == "SF") {
            stdClearInitiallyAssignedFields();
            var sectionsWithData = stdFindSectionsWithData("SF CA CC");
            if (sectionsWithData != "") {
            alert("Initial Follow-up cannot be changed to " +theInitFollowupText +" because response(s) exist in the following section(s): " + sectionsWithData  + ". Please clear the response(s) and try again.");
            if (G_STD_Initial_Followup != null && G_STD_Initial_Followup != "")
                $j('#NBS140').val(G_STD_Initial_Followup);
            else
                alert("Initial Logic validity check change to Field Followup error: no existing value but data in " + sectionsWithData);
                return;
            }
        } //going from surveillance to field
       if (theInitFollowup == "SF" && G_STD_Initial_Followup == "FF") {
            var sectionsWithData = stdFindSectionsWithData("FF CA CC");
            if (sectionsWithData != "") {
            alert("Initial Follow-up cannot be changed to " +theInitFollowupText +" because response(s) exist in the following section(s): " + sectionsWithData  + ". Please clear the response(s) and try again.");
            if (G_STD_Initial_Followup != null && G_STD_Initial_Followup != "")
                $j('#NBS140').val(G_STD_Initial_Followup);
            else
                alert("Initial Logic validity check change to Surveillance error: no existing value but data in " + sectionsWithData);
                return;
            }
        }
        if (theInitFollowup != "SF"){
            pgRequireNotElement("NBS145L");
            pgRequireNotElement("NBS146");
        }
        //passed validity checks - update page state
        stdUpdatePageState();
    }

     //If Initial Follow-Up changes - enable or disable related fields
     function stdResetPageStateOnLoad() {
             //stdSetReadonlyDate('INV2006');  //date closed
             stdDiagnosisRelatedFields();
            stdUpdatePageState();

          //  if((window.event!=null && window.event.type == 'load') || (e!=null)) {This is not necessary because it is called only from <FixedRule onload=""/>. It is necessary to comment this line for being working on firefox
            
                    stdFixedRuleInitialFollowupCodesetCheck();
                    stdEnableFieldsOnLoadBasedOnReferalBasis();
                    stdEnableAndRequireBasedOnPatientInterviewStatus();
                    stdHideFieldsOnLoad();
                    stdSetDefaultInitialFollowupToProcessingDecision();
                    stdDisableMiscFieldsOnLoad();
          //   }
}

function stdFixedRuleInitialFollowupDisableMiscFields() 
{
  pgDisableElement('NBS143'); //Notifiable
  pgRequireNotElement('NBS143L');
  //pgDisableElement('NBS216'); //Pregnant at Exam
  pgRequireNotElement('NBS216L');
  pgRequireNotElement('INV147'); //Investigation start date
}	
 
function stdFixedRuleInitialFollowupEnableMiscFields() 
{
    var curSex = $j('#DEM113 :selected').val();
    if (curSex == "F") 
      pgEnableElement('NBS216'); //Pregnant at Exam
    if (curSex == "M") 
      pgDisableElement('NBS216'); //Pregnant at Exam
  pgEnableElement('NBS143'); //Notifiable
  pgRequireElement('NBS143L');
  pgRequireElement('INV147'); //Investigation start date
}
      
//associated codeset can change if Referral Basis is T1 or T2
function stdFixedRuleInitialFollowupCodesetCheck()
{
  //see if Referral Basis is present
  var referralBasisEle = getElementByIdOrByName('NBS110');
  if (referralBasisEle == null || typeof(referralBasisEle) == 'undefined')
    return;
  var theReferralBasis = referralBasisEle.value;
  if (theReferralBasis == null)
    return;
  // If hidden field present came from View File
  var theReferralBasisOOJ = $j('#NBS270 :selected').val();
  if (theReferralBasisOOJ != null && theReferralBasisOOJ != "")
    if (theReferralBasis != 'T1' && theReferralBasis != 'T2' && (theReferralBasisOOJ == null || theReferralBasisOOJ == "")) {
      return;
    }

  var curVal = "";
  JPageForm.getDwrStdInitialFollowup(theReferralBasis, function (data) {
    if (data.length > 0) { //user can select or change selection
      // Save the current selection for Initial Follow-up
      curVal = $j('#NBS140 :selected').val();
      // Need to reset if we reload the options
      if (typeof(curVal) == 'undefined' || curVal == null || curVal == "") {
        var theProcessingDecision = $j("#headerProcessingDecision").text();
        //alert("theProcessingDecision "+theProcessingDecision);
        if (theProcessingDecision != null && theProcessingDecision != "") {
          curVal = theProcessingDecision;
        }
      }
      DWRUtil.removeAllOptions('NBS140');
      DWRUtil.addOptions('NBS140', data, "key", "value");
      $j("#NBS140 option[value='']").remove(); //remove blank option
      //alert(curVal);
      if (typeof(curVal) != 'undefined' && curVal != null && curVal != "") {
        $j('#NBS140').val(curVal);
        //   getElementByIdOrByName('NBS140').value=curVal;
        //alert(getElementByIdOrByName('NBS140').value);
        AutocompleteSynch('NBS140_textbox', 'NBS140');
      }
    }

  });
}

function stdFixedRuleSurveillancePatientFollowupEntry() 
{
  var theSurveillancePatientFollowup = $j('#NBS151 :selected').val();
  if (theSurveillancePatientFollowup == null) {
    alert("theSurveillancePatientFollowup == null")
    return;
  }
  //see if the user is changing from FF and sections have data that needs to be manually cleared
  if (theSurveillancePatientFollowup != "FF" && G_STD_Patient_Followup == "FF") {
    var ffHasData = stdDoesFieldFollowupHaveData();
    var caHasData = stdDoesCaseAssignmentHaveData();
    var ccHasData = stdDoesCaseClosureHaveData();
    if (ffHasData) {
      if (ffHasData && caHasData && ccHasData)
        alertMsg = "Field Followup, Case Assignment and Case Closure sections";
      else if (ffHasData && caHasData)
        alertMsg = "Field Followup and Case Assignment sections";
      else
        alertMsg = "Field Followup section";

      alert("Note: You must clear data from the " + alertMsg + " before changing Patient Follow-up from Field Followup.");
      //cancel change..
      $j('#NBS151').val("FF");
      AutocompleteSynch('NBS151_textbox', 'NBS151');
      return;
    } //if ffHasData
  }
  stdUpdatePageState();
} //function

//If the disposition has a value then the Case Assignment is Enabled
//Note - If clearing need to clear CA and may also need to clear CC
function stdFixedRuleFieldFollowupDispositionEntry() 
{
  //if the disposition was cleared we may need to clear CA section
  var theDisposition = $j('#NBS173 :selected').val();
  if (theDisposition == null || theDisposition == "") {
    var caseAssignmentHasData = stdDoesCaseAssignmentHaveData();
    var caseClosureHasData = stdDoesCaseClosureHaveData();
    if (caseAssignmentHasData || caseClosureHasData) {
      var alertMsg = "";
      if (caseAssignmentHasData && caseClosureHasData)
        alertMsg = "Case Assignment and Case Closure sections are cleared.";
      else if (caseAssignmentHasData)
        alertMsg = "Case Assignment section is cleared.";
      else if (caseClosureHasData)
        alertMsg = "Case Closure section is cleared.";
      alert("Note: You can not clear the Disposition until all data in the " + alertMsg);
      if (G_STD_FF_Disposition != null && G_STD_FF_Disposition != "")
        $j('#NBS173').val(G_STD_FF_Disposition);
      else
        alert("Disposition Logic Error: no existing value but data in " + alertMsg);
      return;
    } //CA or CC has data
  } //blank
 
  stdCheckDispositionValue(); //this replaces standard rules which caused an issue firing before this rule.
  
  stdUpdatePageState();

}

function stdFieldFollowupDispositionDateEntry() 
{
  stdUpdatePageState();
}

function stdProcessFieldDisposition() 
{
  var theDisposition = $j('#NBS173 :selected').val();
  if (theDisposition == null)
    return;
  if (theDisposition == "") {
      pgRequireNotElement('NBS170');
      pgRequireNotElement('NBS171');
      pgRequireNotElement('NBS172');
      return;
   }
  
  //Field Disposition is not blank - set to closed
  //except for CongenitalSyph  which is only closed when case closed filled in
  if (isConditionCongenitalSyphilis()==false) {
  	stdClosedInvestigationStatus();
  	stdSetDateInvestigationClosed('NBS174'); //use Disposition Date if present
  }
  
  var theReferralBasis = $j('#NBS110 :selected').val();
  if ((theReferralBasis != null && theReferralBasis != "" && theReferralBasis != "T1" && theReferralBasis != "T2") &&
    (theDisposition == "A" || theDisposition == "B" || theDisposition == "C" || theDisposition == "D"
       || theDisposition == "E" || theDisposition == "F")) {
    pgRequireElement('NBS170'); //exam date
    pgRequireElement('NBS171'); //provider
    pgRequireElement('NBS172'); //facility
  } else {
    pgRequireNotElement('NBS170');
    pgRequireNotElement('NBS171');
    pgRequireNotElement('NBS172');
  }
}

function stdFixedRulePatientInterviewStatusEntry() 
{
	var ixNode = getElementByIdOrByName("NBS192");
	if (ixNode == null || typeof(ixNode) == 'undefined') {
	    return;
	}
	
	if (isConditionCongenitalSyphilis()) {
		return;
	}
  //if the interview status is changed from a value to awaiting or cleared
  //and the case closure section has data - let the user know they have to clear CC section
  var thePatientInterviewStatus = $j('#NBS192 :selected').val();
  var theCaseClosedDate = $j('#NBS196').val();
  if (G_STD_Ix_Status != null &&
    G_STD_Ix_Status != "" &&
    thePatientInterviewStatus != null &&
    (thePatientInterviewStatus == "A" || thePatientInterviewStatus == "") &&
    stdDoesCaseClosureHaveData()) {
    //changed to Awaiting and CC section has data
    var alertMsg = "";
    if (thePatientInterviewStatus == "A" && theCaseClosedDate != null && theCaseClosedDate != "")
      alertMsg = "The case has been closed.  The Patient Interview Status cannot be changed to Awaiting.  You must re-open the case by clearing the Case Closure information to make this change";
    else if (thePatientInterviewStatus == "" && theCaseClosedDate != null && theCaseClosedDate != "")
      alertMsg = "The case has been closed.  The Patient Interview Status cannot be cleared.  You must re-open the case by clearing the Case Closure information to make this change";
    else
      alertMsg = "The Patient Interview Status cannot be changed because of entries in the Case Closure subsection. Please clear the Case Closure information to make this change";
    alert(alertMsg);
    //cancel change.
    if (G_STD_Ix_Status != null && G_STD_Ix_Status != "")
      $j('#NBS192').val(G_STD_Ix_Status);
    else
      alert("Patient Interview Status Logic Error: no existing value but data in Case Closure section.");
    return;
  }
  stdEnableAndRequireBasedOnPatientInterviewStatus();
  stdUpdatePageState(); //patient interview status is a key field
}

//if any fields in the section have data return true
//else return false
function stdDoesFieldFollowupHaveData() 
{
	if (pgSubSectionHasData('NBS_INV_STD_UI_22') || pgSubSectionHasData('NBS_INV_STD_UI_24') || pgSubSectionHasData('NBS_INV_STD_UI_23'))
  		return true;
  	return false;
}

//if any fields in the section have data return true
//else return false
function stdDoesCaseAssignmentHaveData() 
{
  return (pgSubSectionHasData('NBS_INV_STD_UI_29'));
}

//if any fields in the section have data return true
//else return false
function stdDoesCaseClosureHaveData() {
  return (pgSubSectionHasData('NBS_INV_STD_UI_32'));
}

function stdDoesSurveillanceHaveData() {
  if (pgSubSectionHasData('NBS_INV_STD_UI_16') || pgSubSectionHasData('NBS_INV_STD_UI_17'))
  	return true;
  return false;
}

function stdFindSectionsWithData(theSectionsToCheck) 
{
  var surveillanceHasData = false;
  if (theSectionsToCheck.indexOf("SF") != -1)
    surveillanceHasData = stdDoesSurveillanceHaveData();
  var fieldFollowupHasData = false;
  if (theSectionsToCheck.indexOf("FF") != -1)
    fieldFollowupHasData = stdDoesFieldFollowupHaveData();
  var caseAssignmentHasData = false;
  if (theSectionsToCheck.indexOf("CA") != -1)
    caseAssignmentHasData = stdDoesCaseAssignmentHaveData();
  var caseClosureHasData = false;
  if (theSectionsToCheck.indexOf("CC") != -1)
    caseClosureHasData = stdDoesCaseClosureHaveData();

  var secHasDataCount = 0
    if (surveillanceHasData)
      ++secHasDataCount;
    if (fieldFollowupHasData)
      ++secHasDataCount;
    if (caseAssignmentHasData)
      ++secHasDataCount;
    if (caseClosureHasData)
      ++secHasDataCount;
    if (secHasDataCount == 0)
      return (""); //no data was found in specified sections

    var sectionsStr = "";
  if (surveillanceHasData) {
    sectionsStr = "Surveillance";
    --secHasDataCount;
    if (secHasDataCount == 1)
      sectionsStr = sectionsStr + " and ";
    else if (secHasDataCount > 1)
      sectionsStr = sectionsStr + ", ";
  }

  if (fieldFollowupHasData) {
    sectionsStr = sectionsStr + "Field Follow-up Information";
    --secHasDataCount;
    if (secHasDataCount == 1)
      sectionsStr = sectionsStr + " and ";
    else if (secHasDataCount > 1)
      sectionsStr = sectionsStr + ", ";
  }
  if (caseAssignmentHasData) {
    sectionsStr = sectionsStr + "Case Assignment";
    --secHasDataCount;
    if (secHasDataCount == 1)
      sectionsStr = sectionsStr + " and ";
    else if (secHasDataCount > 1)
      sectionsStr = sectionsStr + ", ";
  }
  if (caseClosureHasData) {
    sectionsStr = sectionsStr + "Case Closure";
  } 
  return sectionsStr; 
}

function pgSubSectionHasData(elementId){
    var subSectionId ="#"+elementId;
    if (subSectionId == null || typeof(subSectionId) == 'undefined')
    	return false;
    var subSectionBody = $j(subSectionId).find("tbody");
    if (subSectionBody == null)
    	return false;
    for(var ii = 0; ii < subSectionBody.length; ii++){
        var subSectionTBody = $j(subSectionBody.get(ii));
        var subSectionsInput = subSectionTBody.find("INPUT");
        for (var i = 0; i < subSectionsInput.length; i++) {
            if($j(subSectionsInput.get(i)).attr("id")!=null &&
                    $j(subSectionsInput.get(i)).attr("id").length > 0)
            {
                var id = "#"+ $j(subSectionsInput.get(i)).attr("id");
                if($j(id).attr("type") != 'button') {
                    var someVal = $j(id).val();
                    if (someVal != null && someVal != "")
                        return true; // field has data
                } else if (id.indexOf("Icon") != -1) {
                    id = id.replace("Icon", "");
                    idStr = id.replace("#", "");
                    if( getElementByIdOrByName(idStr) != null){
                        var idText = $j(id).text();
                        if (idText != null && idText != "")
                            return true; //participation has data
                    }
                } 
            }
        }

        var subSectionsSelect = $j(subSectionBody.get(ii)).find("SELECT");
        for (var i = 0; i < subSectionsSelect.length; i++) {
            if($j(subSectionsSelect.get(i)).attr("id")!=null &&
                $j(subSectionsSelect.get(i)).attr("id").length > 0)
            {
                var id = "#"+ $j(subSectionsSelect.get(i)).attr("id");
                var str =$j(subSectionsSelect.get(i)).attr("id")+"_textbox";
                if( getElementByIdOrByName(str) != null){
                    //getElementByIdOrByName($j(subSectionsSelect.get(i)).attr("id")).value="";
                    var someText = getElementByIdOrByName(str).value;
                    if (someText != null && someText != "")
                        return true; // select has a value

                }
            }
        }
    }
    return false; //found no data in the subsection
}

function stdClosedInvestigationStatus(theClosedDate) 
{
  var theInvestigationStatus = $j("#INV109").val();
  if (theInvestigationStatus != "C") {
    $j("#INV109").val("C");
    AutocompleteSynch('INV109_textbox', 'INV109');
    alertForCloseToOpenAlert = -1;
  }
  //update header Investigation Status
  var investigationStatusHeader = "Closed (" + $j('#NBS115 :selected').text() + ")";
  $j('#headerInvestigationStatus').text(investigationStatusHeader);
  if (theClosedDate == null || theClosedDate == "undefined")
    return;
  var curClosedDate = $j("#INV2006").val();
  if (curClosedDate != theClosedDate)
    $j("#INV2006").val(theClosedDate);

}

function stdOpenInvestigationStatus() 
{
  var theInvestigationStatus = $j("#INV109").val();
  if (theInvestigationStatus != "O") {
    $j("#INV109").val("O");
    AutocompleteSynch('INV109_textbox', 'INV109');
    var processStageEle = getElementByIdOrByName('NBS115');
    var caseClosureDateClosed = $j("#NBS196").val();
    if((processStageEle.value == 'OC' || processStageEle.value == 'AI' || processStageEle.value == 'FF' || processStageEle.value == 'SF') && alertForCloseToOpenAlert == 0) {
    	alertForCloseToOpenAlert = 1;
    	var coinfectionId = getElementByIdOrByName("coinfectionId");//.innerText;
    	if(typeof coinfectionId !== "undefined") {
    		alert("You are moving a co-infection investigation from Closed to Open status. Co-infection question data will be copied from any associated OPEN co-infection investigations to this investigation upon Submit. Any co-infection data entered by the user on this investigation will be overwritten by data from open co-infections upon submit. If you need to edit co-infection questions on this investigation, click Submit and then go back into edit mode to make any needed changes.");
    	}
	}
  }
  //clear the investigation closed date if set
  var theInvestigationDateClosed = $j("#INV2006").val();
  if (theInvestigationDateClosed != null && theInvestigationDateClosed != "") {
    $j("#INV2006").val("");
  }
  var investigationStatusHeader = "Open (" + $j('#NBS115 :selected').text() + ")";
  $j('#headerInvestigationStatus').text(investigationStatusHeader);
}
 
function stdSetCurrentProcessStage(processStageVal) 
{
  var processStageEle = getElementByIdOrByName('NBS115');
  if (processStageEle == null || typeof(processStageEle) == 'undefined')
    return;
  var theCurrentProcessStage = $j('#NBS115 :selected').val();
  if (theCurrentProcessStage == null || theCurrentProcessStage != processStageVal) {
    $j("#NBS115").val(processStageVal);
    AutocompleteSynch('NBS115_textbox', 'NBS115');
    //update investigation status
    var investigationStatusHeader = $j('#INV109 :selected').text() + "(" + $j('#NBS115 :selected').text() + ")";
    $j('#headerInvestigationStatus').text(investigationStatusHeader);
  }
}

function stdSetDateInvestigationClosed(fromDateElementId) 
{
  //if Date Closed in the Case Closure sectionhas a value, we always use that
  var caseClosureDateClosed = $j("#NBS196").val();
  if (caseClosureDateClosed != null && caseClosureDateClosed != "") {
    $j("#INV2006").val(caseClosureDateClosed);
    return;
  }
  //surveillance date closed or disposition date
  var theFromDate = $j("#" + fromDateElementId).val();
  if (theFromDate != null && theFromDate != "") {
    $j("#INV2006").val(theFromDate);
    return;
  }
  return;
}

function stdInitialFollowupDateClosedEntered() 
{
  var theInitFollowup = $j('#NBS140 :selected').val();
  if (theInitFollowup == null || theInitFollowup == "")
    return;
  //if we have an immediate closure reason - use this date
  if (theInitFollowup == "AC" || theInitFollowup == "BFP" || theInitFollowup == "II"
     || theInitFollowup == "NPP" || theInitFollowup == "PC" || theInitFollowup == "RSC" || theInitFollowup == "OTH"
     || theInitFollowup == "DEC" || theInitFollowup == "RDV")
    stdSetDateInvestigationClosed('NBS141');
  return;
}
 
function stdCaseClosureDateClosedEntered() 
{
  //if Date Closed in the Case Closure section has a value, we always use it for Investigation Close date
  var caseClosureDateClosed = $j("#NBS196").val();
  if (caseClosureDateClosed != null && caseClosureDateClosed != "") {
    $j("#INV2006").val(caseClosureDateClosed);
    stdClosedInvestigationStatus();
    $j('#NBS115').val("CC"); //current process state = Case Closed
    AutocompleteSynch('NBS115_textbox', 'NBS115');
    pgRequireElement('NBS197L');
  } else
    pgRequireNotElement('NBS197L');
  stdUpdatePageState(); //case closure date is a key field
}
 

function stdProcessInterviewerEntry() 
{
	
  var ixNode = getElementByIdOrByName("NBS192");
  if (ixNode == null || typeof(ixNode) == 'undefined') {
	    return;
  }
  var theInterviewer = $j('#NBS186').text();
  if (theInterviewer != null && theInterviewer != "") {
    pgRequireElement('NBS187');
    var thePatientInterviewStatus = $j('#NBS192 :selected').val();
    if (thePatientInterviewStatus == null || thePatientInterviewStatus == "") {
      $j('#NBS192').val("A"); //if blank.. set to awaiting interview
      AutocompleteSynch('NBS192_textbox', 'NBS192');
      $j('#NBS187').focus();
      stdUpdatePageState(); //Interviewer is another key field
    }
  } else
    pgRequireNotElement('NBS187');
  return;
}

function stdEnableFieldsOnLoadBasedOnReferalBasis() 
{
  theReferralBasis = $j('#NBS110 :selected').val();
  if (theReferralBasis == null)
    return;
  var rbFirstChar = theReferralBasis.charAt(0);
  if (rbFirstChar != "A" && rbFirstChar != "C" && rbFirstChar != "P" && rbFirstChar != "S")
    pgDisableElement('NBS135');

}

function stdSetReadonlyDate(fieldId) 
{
  $j('#' + fieldId).attr('readonly', true);
  $j('#' + fieldId + 'Icon').attr('disabled', true);
  $j('#' + fieldId + 'L').attr('disabled', true);
  if($j('#' + fieldId)!=null && $j('#' + fieldId)!=undefined)
	  $j('#' + fieldId).attr('disabled', true);//Fatima: for date input box
  $j('#' + fieldId + 'L').css("color","#666666");//Fatima
  
}
 
//Select doesn't have readonly so we have to disable it and enable it on the submit..
function stdSetReadonlySelect(elementId) 
{
  //disable any inputs or images associated with the element

  disableAllBrowsers($j("#" + elementId).parent().parent().find(":input"));
  $j("#" + elementId).parent().parent().find("img").attr("disabled", true);
  $j("#" + elementId).parent().parent().find("img").attr("tabIndex", "-1");
  
  $j("#" + elementId).parent().parent().find("checkbox").attr("disabled", true);
}

function stdBehavorialRiskAssessedEntry() 
{
  var theBehavorialRiskAssessed = $j('#NBS229 :selected').val();
  if (theBehavorialRiskAssessed!=null && theBehavorialRiskAssessed == "1") {
    pgSubSectionEnabled('NBS_INV_STD_UI_42'); //Sex Partners
    pgSubSectionEnabled('NBS_INV_STD_UI_43'); //Sex Behavior
    pgSubSectionEnabled('NBS_INV_STD_UI_44'); //Risk Behavior
    pgSubSectionEnabled('NBS_INV_STD_UI_45'); //Drug Use Past 12 months
    var noDrugUse = $j('#NBS233 :selected').val();
    if (noDrugUse != "N") {
          pgDisableElement('NBS237');
	  pgDisableElement('NBS235');
	  pgDisableElement('NBS238');
	  pgDisableElement('NBS239');
	  pgDisableElement('NBS234');
	  pgDisableElement('NBS236');
	  pgDisableElement('NBS240');
    }
    
    var theOtherDrug = $j('#NBS240 :selected').val();
    if (theOtherDrug != "Y") {
      pgDisableElement('STD300');
    }
    var injDrugUse = $j('#STD114 :selected').val(); //injection drug use
    if (injDrugUse != "Y") {
      pgDisableElement('NBS232');  //shared equipment
    }      
    var theSex = $j('#DEM113 :selected').val();
    if (theSex == null || theSex!= 'F')
    	pgDisableElement('STD113'); //Female sex with MSM
  } else {
    pgSubSectionDisabled('NBS_INV_STD_UI_42'); //Sex Partners
    pgSubSectionDisabled('NBS_INV_STD_UI_43'); //Sex Behavior
    pgSubSectionDisabled('NBS_INV_STD_UI_44'); //Risk Behavior
    pgSubSectionDisabled('NBS_INV_STD_UI_45'); //Drug Use Past 12 months
  }
  return;
}

function stdCheckDrugUseSubsectionOnSubmit() 
{
   var i = 0;
   var errorElts = new Array();
   var errorMsgs = new Array();
   // see if indicated drug use - if so, one drug should be yes..
   var noneElement = getElementByIdOrByName('NBS233');
   if (noneElement == null || typeof(noneElement) == 'undefined') {
     return {elements : errorElts, labels : errorMsgs}
   }
   var theNoneDrugUse =   $j('#NBS233 :selected').val();
   var theOtherDrug = $j('#NBS240 :selected').val();
   if (theNoneDrugUse != null && theNoneDrugUse == 'N') { // none == No
     var theMeth = $j('#NBS234 :selected').val();
     var theCrack = $j('#NBS235 :selected').val();
     var theNitrate = $j('#NBS236 :selected').val();
     var theCocaine = $j('#NBS237 :selected').val();
     var theEDmeds = $j('#NBS238 :selected').val();
     var theHeroin = $j('#NBS239 :selected').val();
     if (theMeth != "Y" && theCrack != "Y" && theNitrate != "Y" && theCocaine != "Y"
       && theEDmeds!= "Y" && theHeroin != "Y" && theOtherDrug != "Y") {
       errorMsgs[i] ="If None is No, at least one drug use must be set to Yes"
         errorElts[i] = getElementByIdOrByName('NBS233');
       $j('#NBS233L').css("color", "990000");
       i++;
     } else $j('#NBS233L').css("color", "black"); // clear if color present
   }
   // May not be required.
   var theOtherSpecified = $j('#NBS241').val(); // text field
   if (theOtherDrug == "Y" && (theOtherSpecified == null || theOtherSpecified == "")) {
     errorMsgs[i] ="If Other Drug Use is Yes, please specify the other drug."
       errorElts[i] = getElementByIdOrByName('NBS241');
     $j('#NBS241L').css("color", "990000");
     i++;

   } else $j('#NBS241L').css("color", "black"); // clear if color present
   return {elements : errorElts, labels : errorMsgs}
}

function stdDrugUseNoneEnableDisable() 
{
  // see if indicated drug use - if so, one drug should be yes..
  var noneElement = getElementByIdOrByName('NBS233');
  if (noneElement == null || typeof(noneElement) == 'undefined') {
    return;
  }
  var theNoneDrugUse =   $j('#NBS233 :selected').val();
  if (theNoneDrugUse == null || theNoneDrugUse == "")
    return

  if (theNoneDrugUse == 'N') { // none == No
    pgEnableElement('NBS234'); // Meth
    pgEnableElement('NBS235'); // Crack
    pgEnableElement('NBS236'); // Nitrate
    pgEnableElement('NBS237'); // Cocaine
    pgEnableElement('NBS238'); // EDMeds
    pgEnableElement('NBS239'); // Herion
    pgEnableElement('NBS240'); // OtherDrug
  } else if (theNoneDrugUse == 'Y' || theNoneDrugUse == 'R' || theNoneDrugUse == 'D' || theNoneDrugUse == 'ASKU') {
    pgDisableElement('NBS234'); // Meth
    pgDisableElement('NBS235'); // Crack
    pgDisableElement('NBS236'); // Nitrate
    pgDisableElement('NBS237'); // Cocaine
    pgDisableElement('NBS238'); // EDMeds
    pgDisableElement('NBS239'); // Herion
    pgDisableElement('NBS240'); // OtherDrug
  }
  return;
}

function stdEnableAndRequireBasedOnPatientInterviewStatus() {
	
  var ixNode = getElementByIdOrByName("NBS192");
  if (ixNode == null || typeof(ixNode) == 'undefined') {
	    return;
  }	
	
  var thePatientInterviewStatus = $j('#NBS192 :selected').val();
  if (thePatientInterviewStatus == null)
    return;
  var theCurrentSex = $j('#DEM113 :selected').val();
  if (thePatientInterviewStatus == "I") { // Interviewed
	  pgRequireElement("NBS223L");
	  pgRequireElement("NBS225L");
	  pgRequireElement("NBS227L");
	  pgRequireElement("STD119L");
	  pgRequireElement("NBS242L");
	  pgRequireElement("NBS244L");
	  pgRequireElement("NBS129L");
	  pgRequireElement("NBS131L");
	  pgRequireElement("NBS133L");
	  pgRequireElement("STD117L");
	  if (theCurrentSex == "F") {
	        pgEnableElement('NBS218');
	        //pgRequireElement('NBS218L'); // pregnant at interview
	        pgEnableElement('NBS221');
	        //pgRequireElement('NBS221L'); // pregnant in the last 12 months
    	  }
  } else {
  	if (theCurrentSex == "M") {
      		pgDisableElement('NBS218');	// pregnant at interview
      		pgDisableElement('NBS221');
      	}
      pgRequireNotElement('NBS218L');
      pgRequireNotElement('NBS221L'); // pregnant in the last 12 months

      pgRequireNotElement("NBS223L");
      pgRequireNotElement("NBS225L");
      pgRequireNotElement("NBS227L");
      pgRequireNotElement("STD119L");
      pgRequireNotElement("NBS242L");
      pgRequireNotElement("NBS244L");
      pgRequireNotElement("NBS129L");
      pgRequireNotElement("NBS131L");
      pgRequireNotElement("NBS133L");
      pgRequireNotElement("STD117L");
      pgRequireNotElement("NBS187");
  }

}

// Check the min and max values of a numeric field allowing an unknown value such as 99
// Alert the user if the value is not in the range
function stdCheckFieldMinMaxUnk(pTextbox, pMinVal, pMaxVal, pUnk) 
{
  if (pTextbox.value == "") {
    return;
  }
  //if decimal point - getting stored as zero
  if (pMinVal == 0 && pMaxVal == 0) {
    return;
  }
  var varVal = pTextbox.value;
  if (varVal < pMinVal && varVal != pUnk) {
    alert("Number of Weeks is out of range. Weeks must be greater than or equal " + pMinVal + " or " + pUnk + " for unknown");
    pTextbox.focus();
    return;
  }
  if (varVal > pMaxVal && varVal != pUnk) {
    alert("Number of Weeks is out of range. Weeks must be less than or equal " + pMaxVal + ", or " + pUnk + " (indicating unknown).");
    pTextbox.focus();
    return;
  }
}

function stdEnableDisabledFieldsOnSubmit() 
{
  $j("#INV109").parent().parent().find(":input").removeAttr('disabled').css("color","#000");
  $j("#NBS115").parent().parent().find(":input").removeAttr('disabled').css("color","#000");;
}

function stdSurveillanceSectionEnabled() 
{
  pgSubSectionEnabled('NBS_INV_STD_UI_16'); //Surveillance Case Assignment
  pgSubSectionEnabled('NBS_INV_STD_UI_17'); //Surveillance Information
  pgRequireNotElement('NBS141'); //Initial Followup date closed
  pgDisableElement('NBS141');
  pgRequireElement('NBS145L');
  pgRequireElement('NBS146');
}

function stdFieldFollowupSectionEnabledFromSurveillance() 
{
  stdFieldFollowupSectionEnabled();
  var ssProviderDiagnosis = $j('#NBS150 :selected').val();
  var ffProviderDiagnosis = $j('#NBS166 :selected').val();
  if (ssProviderDiagnosis != null && ssProviderDiagnosis != "") {
    if (ffProviderDiagnosis == null || ffProviderDiagnosis == "") {
      //alert('updating to ' + ssProviderDiagnosis);
      $j('#NBS166').val(ssProviderDiagnosis);
      AutocompleteSynch('NBS166_textbox', 'NBS166');
    }
  }
  var ssExamReason = $j('#NBS149 :selected').val();
  var ffExamReason = $j('#NBS165 :selected').val();
  if (ssExamReason != null && ssExamReason != "") {
    if (ffExamReason == null || ffExamReason == "") {
      //alert('updating to ' + ssExamReason);
      $j('#NBS165').val(ssExamReason);
      AutocompleteSynch('NBS165_textbox', 'NBS165');
    }
  }
}

function stdFieldFollowupSectionEnabled()
{
  var dispoNode = getElementByIdOrByName("NBS173");
  if (dispoNode == null || typeof(dispoNode) == 'undefined') {
    return;
  }
  
  if (dispoNode.disabled) {
  	pgSubSectionEnabled('NBS_INV_STD_UI_24'); //Field Followup Disposition
  	stdCheckDispositionValue();
  }
  pgSubSectionEnabled('NBS_INV_STD_UI_22'); //Field Followup Case Assignment
  pgSubSectionEnabled('NBS_INV_STD_UI_23'); //Field Follow-up Exam Information
  //pgSubSectionEnabled('NBS_INV_STD_UI_25'); //OOJ Field Record Sent To Information
  pgRequireElement('NBS161L');
  pgRequireElement('NBS162');
  //stdSetReadonlyDate('NBS164'); //FF Initial Assign Date 
}

function stdPopOojDueDate2WksPrior()
{
  var d = new Date();
  d.setDate(d.getDate() - 14);
  $j("#NBS181").val(DateToString(d));
}

function stdSurveillanceSectionDisabled()
{
  pgSubSectionDisabled('NBS_INV_STD_UI_16'); //Surveillance Case Assignment
  pgSubSectionDisabled('NBS_INV_STD_UI_17'); //Surveillance Information
}

function stdFieldFollowupSectionDisabled() 
{
  pgRequireNotElement('NBS161L');
  pgRequireNotElement('NBS162');
  pgSubSectionDisabled('NBS_INV_STD_UI_22'); //Field Followup Case Assignment
  pgSubSectionDisabled('NBS_INV_STD_UI_23'); //Field Followup Exam Info
  pgSubSectionDisabled('NBS_INV_STD_UI_24'); //Field Followup Case Dispo
  pgSubSectionDisabled('NBS_INV_STD_UI_25'); //OOJ Field Record Sent to Information
}

function stdUpdateCurrentProvider(identifierFromDwr) 
{
  stdDetermineCurrentProvider();
  //if the Interviewer was entered .. process it
  if (identifierFromDwr != null && identifierFromDwr == 'NBS186') {
    stdProcessInterviewerEntry();
  }
}
// The current provider is the last provider working on the case
function stdDetermineCurrentProvider() 
{
  var selectedText = "";
  selectedText = $j('#NBS197').html();
  if (selectedText != null && selectedText != "") {
    stdUpdateCurrentInvestigator(selectedText, 'NBS197');
    return;
  }
  selectedText = $j('#NBS186').html();
  if (selectedText != null && selectedText != "") {
    stdUpdateCurrentInvestigator(selectedText, 'NBS186');
    return;
  }

  selectedText = $j('#NBS161').html();
  if (selectedText != null && selectedText != "") {
    stdUpdateCurrentInvestigator(selectedText, 'NBS161');
    return;
  }

  selectedText = $j('#NBS145').html();
  if (selectedText != null && selectedText != "") {
    stdUpdateCurrentInvestigator(selectedText, 'NBS145');
    return;
  }

  selectedText = $j('#NBS139').html();
  if (selectedText != null && selectedText != "") {
    stdUpdateCurrentInvestigator(selectedText, 'NBS139');
    return;
  }
}

function stdUpdateCurrentInvestigator(curInvestigatorHtml,curInvestigatorId) {
  // update the header and the INV180 Investigator with the current
  // investigator
  $j('#INV180').html(curInvestigatorHtml);
  var curInvestigatorText = curInvestigatorHtml;
  // just put the name in the header, leave off the address
  if (curInvestigatorText.indexOf("</TD></TR>") != -1) {
	    curInvestigatorText = curInvestigatorText.substring(113, curInvestigatorText.indexOf("</TD></TR>"));
	  }
  $j('#headerCurrentInvestigator').html(curInvestigatorText);
  var uidVal = getElementByIdOrByName("attributeMap."+curInvestigatorId+"Uid").value;
  if(uidVal!=null && !uidVal.indexOf("|")==-1){
    uidVal = uidVal.substring(0, uidVal.indexOf("|"));
  }
  // alert('uidVal =' +uidVal);
  JPageForm.getDwrInvestigatorDetailsByUid(uidVal, 'INV180',function(data)    {
  });
}

function stdCheckPartnersInInterviewPeriodOnSubmit() {
  var i = 0;
  var errorElts = new Array();
  var errorMsgs = new Array();

  // see if any of Partner fields set to yes but no value in field
  var FemaleEle = getElementByIdOrByName('NBS129');
  if (FemaleEle == null || typeof(FemaleEle) == 'undefined') {
    return {elements : errorElts, labels : errorMsgs}
  }
  // if this is disabled, return
  var isDisabled = $j('#NBS129').attr('disabled');
  if (isDisabled) {
    return {elements : errorElts, labels : errorMsgs}
  }

  var theFemale =   $j('#NBS129 :selected').val();
  var theFemaleNumber = $j('#NBS130').val();
  if (theFemale != null && theFemale == "Y" && (theFemaleNumber == null || theFemaleNumber == 0)) {
    var theFemaleNumberEle = getElementByIdOrByName('NBS130');
    var a2Str = buildErrorAnchorLink(theFemaleNumberEle, "Female Number");  
    errorMsgs[i] ="You have indicated Yes for Female. " +a2Str + " must be greater than 0.  Please correct the data and try again."
    errorElts[i] = getElementByIdOrByName('NBS130');
    $j('#NBS130L').css("color", "990000");
    i++;
  } else $j('#NBS130L').css("color", "black"); // clear if color present
  var theMale =   $j('#NBS131 :selected').val();
  var theMaleNumber = $j('#NBS132').val();
  if (theMale != null && theMale == "Y" && (theMaleNumber == null || theMaleNumber == 0)) {
    var theMaleNumberEle = getElementByIdOrByName('NBS132');
    var a2Str = buildErrorAnchorLink(theMaleNumberEle, "Male Number");  
    errorMsgs[i] ="You have indicated Yes for Male. " +a2Str + " must be greater than 0.  Please correct the data and try again."
     errorElts[i] = getElementByIdOrByName('NBS132');
    $j('#NBS132L').css("color", "990000");
    i++;
  } else $j('#NBS132L').css("color", "black"); // clear if color present
  var theTrans =   $j('#NBS133 :selected').val();
  var theTransNumber = $j('#NBS134').val();
  if (theTrans != null && theTrans == "Y" && (theTransNumber == null || theTransNumber == 0)) {
     var theTransNumberEle = getElementByIdOrByName('NBS134');
    var a2Str = buildErrorAnchorLink(theTransNumberEle, "Transgender Number");  
    errorMsgs[i] ="You have indicated Yes for Transgender. " + a2Str + " must be greater than 0.  Please correct the data and try again."
    errorElts[i] = getElementByIdOrByName('NBS134');
    $j('#NBS134L').css("color", "990000");
    i++;
  } else $j('#NBS134L').css("color", "black"); // clear if color present

  return {elements : errorElts, labels : errorMsgs}
}

function stdCheckPartnersPastYearOnSubmit() {
  var i = 0;
  var errorElts = new Array();
  var errorMsgs = new Array();

  // see if any of Partner fields set to yes but no value in field
  var FemaleEle = getElementByIdOrByName('NBS223');
  if (FemaleEle == null || typeof(FemaleEle) == 'undefined') {
    return {elements : errorElts, labels : errorMsgs}
  }
  // if this is disabled, return
  var isDisabled = $j('#NBS223').attr('disabled');
  if (isDisabled) {
    return {elements : errorElts, labels : errorMsgs}
  }

  var theFemale =   $j('#NBS223 :selected').val();
  var theFemaleNumber = $j('#NBS224').val();
  if (theFemale != null && theFemale == "Y" && (theFemaleNumber == null || theFemaleNumber == 0)) {
    var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS224'), "Female Number");  
    errorMsgs[i] ="You have indicated Yes for Female. " + a2Str + " must be greater than 0.  Please correct the data and try again."
      errorElts[i] = getElementByIdOrByName('NBS224');
    $j('#NBS224L').css("color", "990000");
    i++;
  } else $j('#NBS224L').css("color", "black"); // clear if color present
  var theMale =   $j('#NBS225 :selected').val();
  var theMaleNumber = $j('#NBS226').val();
  if (theMale != null && theMale == "Y" && (theMaleNumber == null || theMaleNumber == 0)) {
    var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS226'), "Male Number");  
    errorMsgs[i] ="You have indicated Yes for Male. " + a2Str + " must be greater than 0.  Please correct the data and try again."
    errorElts[i] = getElementByIdOrByName('NBS226');
    $j('#NBS226L').css("color", "990000");
    i++;
  } else $j('#NBS226L').css("color", "black"); // clear if color present
  var theTrans =   $j('#NBS227 :selected').val();
  var theTransNumber = $j('#NBS228').val();
  if (theTrans != null && theTrans == "Y" && (theTransNumber == null || theTransNumber == 0)) {
    var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS228'), "Transgender Number");  
    errorMsgs[i] ="You have indicated Yes for Transgender. " + a2Str + " must be greater than 0.  Please correct the data and try again."  
    errorElts[i] = getElementByIdOrByName('NBS228');
    $j('#NBS228L').css("color", "990000");
    i++;
  } else $j('#NBS228L').css("color", "black"); // clear if color present

  return {elements : errorElts, labels : errorMsgs}

}

// This is a problematic temporary function to hide certain fields for the 4.5a testing
function stdHideFieldsOnLoad() {
  // partially disable investigation status
	$j("#INV109").parent().parent().find("img").attr("disabled", true);
	$j("#INV109").parent().parent().find("img").attr("tabIndex", "-1");
	  
   disableAllBrowsers($j('#INV109').parent().parent().find(":input"));
  $j('#INV109L').css("color","#666666");
  var investigationStatusLabel = getElementByIdOrByName("INV109L");
  if (investigationStatusLabel == null || typeof(investigationStatusLabel) == 'undefined') {
    return;
  }
  
  //investigationStatusLabel.style.color="#666666";
  investigationStatusLabel.className = "InputDisabledLabel";

  // partially disable current process stage
  $j("#NBS115").parent().parent().find("img").attr("disabled", true);
  $j("#NBS115").parent().parent().find("img").attr("tabIndex", "-1");
  
  disableAllBrowsers( $j('#NBS115').parent().parent().find(":input"));
  $j('#NBS115L').css("color","#666666");
  var processStageLabel = getElementByIdOrByName("NBS115L");
  if (processStageLabel == null || typeof(processStageLabel) == 'undefined') {
    return;
  }
  //processStageLabel.style.color="#666666";
  processStageLabel.className = "InputDisabledLabel";

  // partially disable referral basis
  $j("#NBS110").parent().parent().find("img").attr("disabled", true);
  $j("#NBS110").parent().parent().find("img").attr("tabIndex", "-1");
  

  disableAllBrowsers( $j('#NBS110').parent().parent().find(":input"));
  $j('#NBS110L').css("color","#666666");
  var referralBasisLabel = getElementByIdOrByName("NBS110L");
  if (referralBasisLabel == null || typeof(referralBasisLabel) == 'undefined') {
    return;
  }
  //referralBasisLabel.style.color="#666666";
  referralBasisLabel.className = "InputDisabledLabel";

  stdHideParticipation('INV180');
  stdHideParticipation('NBS163');
  stdHideParticipation('NBS188');
  return;
}

// This routine is called from the index.jsp just before the submit form
function stdSpecialSubmitCheck() {

  // see if this is an STD page - has a Referral Basis
  var referralEle = getElementByIdOrByName('NBS110');
  if (referralEle == null || typeof(referralEle) == 'undefined') {
    return;
  }
  // enable disabled selects on the way out so they are not deleted..

  enableAllBrowsers($j('#INV109').parent().parent().find(":input"));
  enableAllBrowsers($j('#NBS115').parent().parent().find(":input"));
  enableAllBrowsers($j('#NBS110').parent().parent().find(":input"));
  enableAllBrowsers($j('#INV2006').parent().parent().find(":input"));
  //gst 2016-10-20 #9332
 // enableAllBrowsers($j('#NBS112').parent().parent().find(":input"));
 // enableAllBrowsers($j('#NBS113').parent().parent().find(":input"));
 // enableAllBrowsers($j('#NBS114').parent().parent().find(":input"));

  var selectedFieldFollowup = $j('#NBS161S').text();
  if (selectedFieldFollowup != null && selectedFieldFollowup != "") {
    var dateFieldFollowupAssigned =  $j('#NBS162').val();
    var dateFieldFollowupInitiallyAssigned = $j('#NBS163').val();
    if (dateFieldFollowupAssigned != null && dateFieldFollowupAssigned != "" && dateFieldFollowupInitiallyAssigned == "")
      $j('#NBS164').val(dateFieldFollowupAssigned);
  }

  var selectedInterviewer = $j('#NBS186S').text();
  if (selectedInterviewer != null && selectedInterviewer != "") {
    var dateInterviewerAssigned =  $j('#NBS187').val();
    var dateInterviewerInitiallyAssigned = $j('#NBS189').val();
    if (dateInterviewerAssigned != null && dateInterviewerAssigned != "" && dateInterviewerInitiallyAssigned == "")
      $j('#NBS189').val(dateInterviewerAssigned);
  }
  
  //$j(":input").attr("disabled", false);
}

function stdHideParticipation(identifierId) 
{
  var curPart = $j('#' + identifierId).text();
  if (curPart != null && curPart != "") {
    $j("#clear" + identifierId).hide(); // keep hidden
    $j('#' + identifierId + 'L').text("");
  } else {
    $j('#' + identifierId + 'L').text("");
    $j('#' + identifierId + "Text").hide();
    $j('#' + identifierId + "Icon").hide();
    $j('#' + identifierId + "CodeLookupButton").hide();
    $j('#' + identifierId + "SearchControls").hide();
  }
  $j("#" + identifierId + "S").css("color", "#666666");
}

function stdSetDefaultInitialFollowupToProcessingDecision() 
{
  var theProcessingDecision = $j("#headerProcessingDecision").text();
  var theInitialFollowup = $j('#NBS140 :selected').val();
  if (theProcessingDecision != null && theProcessingDecision != "" && (theInitialFollowup == null || theInitialFollowup == "")) {
    $j('#NBS140').val(theProcessingDecision);
    //$j('#NBS140 option[value= "' +theProcessingDecision+ '"]').attr('selected','selected');
    AutocompleteSynch('NBS140_textbox', 'NBS140');
  }
}

function stdDisableMiscFieldsOnLoad() 
{
  var theOtherDrug = $j('#NBS240 :selected').val();
  if (theOtherDrug != "Y") {
    pgDisableElement('NBS241'); //specified
  }
}

function stdOtherDrugEntry() 
{
  var theOtherDrug = $j('#NBS240 :selected').val();
  if (theOtherDrug == "Y") {
    pgEnableElement('NBS241'); //specified
  } else {
    pgDisableElement('NBS241'); //specified
  }
}

function stdCheckDispositionValue() 
{
  theDispo = $j('#NBS173 :selected').val()
  if (theDispo == null)
  	return;

  if (theDispo != "") {
    pgEnableElement('NBS174');
    pgEnableParticipationElement('NBS175');
    pgEnableParticipationElement('NBS176');
    pgRequireElement('NBS174');
    pgRequireElement('NBS175L');
    pgRequireElement('NBS176L');
    pgRequireElement('NBS176L');
    var curSex = $j('#DEM113 :selected').val();
    if (curSex == "F") {
      pgEnableElement('NBS216'); //Pregnant at Exam
      //if (theDispo == 'A' || theDispo == 'B' || theDispo == 'C' || theDispo == 'E' || theDispo == 'F')
      //   pgRequireElement('NBS216L');
    }
    if (curSex == "M") {
        pgDisableElement('NBS216'); //Pregnant at Exam
        pgRequireNotElement('NBS216L');
        pgDisableElement('NBS217'); //weeks
        pgRequireNotElement('NBS217');
        pgDisableElement('NBS218'); //Preg at Interview
        pgRequireNotElement('NBS218');    
        pgDisableElement('NBS221'); //Preg last 12 mos
        pgRequireNotElement('NBS221');
    }
  } else { //dispo is blank
    pgRequireNotElement('NBS174');
    pgRequireNotElement('NBS175L');
    pgRequireNotElement('NBS176L');
    pgDisableElement('NBS174');
    pgDisableParticipationElement('NBS175');
    pgDisableParticipationElement('NBS176');
    //pgDisableElement('NBS216'); //Pregnant at Exam
    pgRequireNotElement('NBS216L');
    //pgDisableElement('NBS217'); //weeks
    pgRequireNotElement('NBS217');
  }
  // check if OOJ selected
  if (theDispo == 'K') {
    pgSubSectionEnabled('NBS_INV_STD_UI_25'); //OOJ Field Record Sent To Information
    pgRequireElement('NBS179L');
  } else {
    pgRequireNotElement('NBS179L');
    pgSubSectionDisabled('NBS_INV_STD_UI_25'); //OOJ Field Record Sent To Information
  }
}

function stdClearInitiallyAssignedFields()
{
  var initAsnEle = getElementByIdOrByName('NBS163');
  if (initAsnEle == null || typeof(initAsnEle) == 'undefined') {
    return;
  }
      var initiallyAssignedText = $j('#NBS163').text();
      var initiallyAssignedUid = $j('#NBS163UID').val();
      var initiallyAssignedDate = $j('#NBS164').val();
    $j('#NBS163').text("");
    $j('#NBS163UID').val("");
    $j('#NBS164').val("");
    //still gets error, put it back
    if (stdFindSectionsWithData("FF") != "") {
        $j('#NBS163').text(initiallyAssignedText);
        $j('#NBS163UID').val(initiallyAssignedUid);
        $j('#NBS164').val(initiallyAssignedDate);
    }
    
}

function ixsHideParticipation(identifierId) 
{
  var curPart = $j('#' + identifierId).text();
  if (curPart != null && curPart != "") {
    $j("#clear" + identifierId).hide(); // keep hidden
    $j('#' + identifierId + 'L').text("");
  } else {
      $j('#' + identifierId + 'L').text("");
      $j('#' + identifierId + "Text").hide();
      $j('#' + identifierId + "Icon").hide();
      $j('#' + identifierId + "CodeLookupButton").hide();
      $j('#' + identifierId + "SearchControls").hide();
  }
  // $j("#" + identifierId + "S").css("color", "#666666");
}

function ixsLockDownIntervieweeToSubject() 
{
  DWRUtil.removeAllOptions('IXS103');
  $j('#IXS103').append('<option value="SUBJECT">Subject of Investigation</option>');
  $j('#IXS103').val("SUBJECT");
  AutocompleteSynch('IXS103_textbox', 'IXS103');
}
function ixsValidateInterviewerOnSubmit()
{
   var i = 0;
   var errorElts = new Array();
   var errorMsgs = new Array();
   // Interviewer is required for an Interview
   var interviewEle = getElementByIdOrByName('IXS102');
   if (interviewEle != null) {
   	var interviewerText = $j('#IXS102').text();
   	if (interviewerText == null || interviewerText == '') { // no interviewer specified
   	   var a1Str = buildErrorAnchorLink(getElementByIdOrByName('IXS102Text'), "Interviewer");
       	   errorMsgs[i] ="The " +a1Str+ " is a required field.";
           errorElts[i] = interviewEle;
           $j('#IXS102L').css("color", "990000");
           i++;
     	} else $j('#IXS102L').css("color", "black"); // clear if color present
   }
   return {elements : errorElts, labels : errorMsgs}
}
function conStdGetNamedDuringInterview(fieldId, theForm)
{
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    if(actionMode == 'Preview') { 
            return;
    }
    theForm.getNamedInterviews(fieldId, function(data) {
        dwr.util.removeAllOptions(fieldId);  
        dwr.util.addOptions(fieldId,data,"key","value"); 
        var lastOptionVal = $j('#' + fieldId + " option:last").val();
        if (lastOptionVal == 'selected') { //last option tells us which is selected..
        	var lastOptionText = $j('#' + fieldId + " option:last").text();
        	$j('#' + fieldId + " option:last").remove();
        	$j('#' + fieldId).val(lastOptionText);
        	AutocompleteSynch(fieldId+ '_textbox', fieldId);
        	//alert("newLastOption val and text = " + $j('#' + fieldId + " option:last").val() + " " + $j('#' + fieldId + " option:last").text());
        }
    	
    });
} 

function conStdCheckSpecialSubmitRules()
{
   var i = 0;
   var errorElts = new Array();
   var errorMsgs = new Array();
   // if referral basis is cohort, named must be 999999 Initiated without Interview
   var referralBasisEle = getElementByIdOrByName('CON144');
   var namedDuringEle = getElementByIdOrByName('CON143');
   if (referralBasisEle != null && namedDuringEle != null && $j('#CON144 :selected').val() == 'C1') {
  	var a1Str = buildErrorAnchorLink(getElementByIdOrByName('CON144'), "Referral Basis");
  	var a2Str = buildErrorAnchorLink(getElementByIdOrByName('CON143'), "Named");
   	var theNamedDuring = $j('#CON143 :selected').val();
   	if (theNamedDuring == null || theNamedDuring != '999999') { // Initiated w/out Interview
       	   errorMsgs[i] ="If the Referral Basis is Cohort, then Named must be 'initiated w/out interview'"
           errorElts[i] = getElementByIdOrByName('CON143');
           $j('#CON143L').css("color", "990000");
           i++;
     	} else $j('#CON143L').css("color", "black"); // clear if color present
   }

   return {elements : errorElts, labels : errorMsgs}
}

function crStdSetupReferralBasisOptions(sourcePatientDispo, sourcePatientInterview, sourcePatientCurrentSexCd, sourcePatientConditionCd)
{
	
    	var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    	if(actionMode == 'Preview') { 
            return;
    	}
    	
   //alert("sourceDispo="+sourcePatientDispo+" sourceSex="+sourcePatientCurrentSexCd+" sourceCond="+sourcePatientConditionCd);

	 // make sure referral basis is present
	var elementId = 'CON144';
	var rbEle = getElementByIdOrByName(elementId);
	if (rbEle  == null || typeof(rbEle) == 'undefined') {
		return;
  	}

	var patientInterviewed = false;
	if (sourcePatientInterview != null && sourcePatientInterview == 'I')
		patientInterviewed = true;
	var patientInfected = false;
	if (sourcePatientDispo != null && 
		(sourcePatientDispo == 'C' || sourcePatientDispo == 'D' || sourcePatientDispo == 'E'))
		patientInfected = true;
		
	if (sourcePatientDispo != null && 
		(sourcePatientDispo == '1' || sourcePatientDispo == '2' || sourcePatientDispo == '5'))//HIV
		patientInfected = true;
	var patientFemale = false;
	if (sourcePatientCurrentSexCd != null && sourcePatientCurrentSexCd == 'F') //female
		patientFemale = true;
	var isSyphilis = false;
	var isCongenital = false;
	if (sourcePatientConditionCd != null) {
		isSyphilis = isConditionSyphilis(sourcePatientConditionCd);
		if (sourcePatientConditionCd.indexOf("10316") != -1) { //CS
			isCongenital = true;
		}
		var curVal =  $j('#CON144 :selected').val(); //check current value of referral basis
		if (curVal != null && curVal == 'M1') {
			pgHideElement("NBS143");
		}
	}
		
	var namedDuringSelected = $j('#CON143 :selected').val();
	var initiatedWithOutInterview = false;
	if (namedDuringSelected == '999999')
		initiatedWithOutInterview = true;
	var namedAtInterview = false;
	if (namedDuringSelected != null && namedDuringSelected != '' && namedDuringSelected != '999999')
		namedAtInterview = true;
	var otherKnown = false;
	var namingBetweenSelected = $j('#CON141 :selected').val();
	if (namingBetweenSelected != null && namingBetweenSelected == 'OTHPAT')
		otherKnown = true;
	var codeSetName = "";
	//alert("patientInfected= " + patientInfected  + " patientInterviewed = " + patientInterviewed + " namedAtInterview = " + namedAtInterview +  " otherKnown = " + otherKnown);
	if (patientInfected && patientInterviewed && namedAtInterview && !otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_INFPAT_NM_IX';
	else if (patientInfected && patientInterviewed && initiatedWithOutInterview && !otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NM_NOTATIX';
	else if (!patientInfected && patientInterviewed && namedAtInterview && !otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NONINFPAT_NM_IX';
	else if (!patientInfected && patientInterviewed && initiatedWithOutInterview && !otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NM_NOTATIX';
	else if (patientInfected && !patientInterviewed && initiatedWithOutInterview && !otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NM_NOTATIX';
	else if (!patientInfected && !patientInterviewed && initiatedWithOutInterview && !otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NM_NOTATIX';
	else if (patientInfected && patientInterviewed && otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_INFPAT_NM_OTHINFPAT_IX';
	else if (!patientInfected && patientInterviewed && otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NONINFPAT_NM_OTHINFPAT_IX';
	else if (patientInfected && !patientInterviewed && otherKnown)
		codeSetName = 'STD_CR_REFERRAL_BASIS_NONINFPAT_NM_OTHINFPAT_IX';
	else if (!patientInfected && !patientInterviewed && otherKnown)	
		codeSetName = 'STD_CR_REFERRAL_BASIS_NONINFPAT_NM_OTHINFPAT_IX';
	if (codeSetName == "") {
      		return;
	}
	//alert("Cond="+sourcePatientConditionCd+" codeset="+codeSetName +" patientFemale="+patientFemale+" isCongenital="+isCongenital+" isSyph="+isSyphilis);

	JBaseForm.getCodedValue(codeSetName, function(data) {
	   if (data.length == 0)
		alert("Error STD Contact Record Codeset: " + codeSetName + " was not found!");
	   if (data.length > 0) {
	     	//save selected value if present
	     	var curVal =  $j('#CON144 :selected').val();
	     	
        	dwr.util.removeAllOptions(elementId);  
        	dwr.util.addOptions(elementId,data,"key","value"); 
        	if (curVal != null && curVal != "" && curVal != 'undefined') {
        		$j('#' + elementId).val(curVal);
		        AutocompleteSynch('CON144_textbox', 'CON144');
		}
        //special logic for Congenital Syphilis
        if (isCongenital) {
        	$j("#CON144 option[value='M1']").remove();
        } else if (!isSyphilis) {
        	$j("#CON144 option[value='M1']").remove();
        	$j("#CON144 option[value='M2']").remove();
        } else if (!patientFemale) {
        	$j("#CON144 option[value='M1']").remove();
        	$j("#CON144 option[value='M2']").remove();
        } else {
        	$j("#CON144 option[value='M2']").remove();
        }
		AutocompleteSynch('CON144_textbox', 'CON144');
		
      	   }
    
    	});
    
}
function conStdNamedBetweenUpdate() {
  conStdCheckReferralBasisOptions();
}

function conStdNamedDuringUpdate() {
  conStdCheckReferralBasisOptions();
}
 
function crStdSetupProcessingDecisionOptions(codeSetName, elementId)
{
        var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
	if(actionMode == 'Preview') { 
	        return;
    	}	
       	JBaseForm.getCodedValueNoBlnk(codeSetName, function(data) {
       	   //save selected value if present
  	   var curVal = $j('#' + elementId + ' :selected').val();
       	   if (data.length == 0)
       		alert("Error STD Contact Record Processing Decision Codeset: " + codeSetName + " was not found!");
       	   if (data.length > 0) {
               	dwr.util.removeAllOptions(elementId);  
               	dwr.util.addOptions(elementId,data,"key","value"); 
               	if (curVal != null && curVal != "") {
       		        $j('#' + elementId).val(curVal);
       		        AutocompleteSynch(elementId + '_textbox', elementId);
       		}
           }
           conStdCheckFieldFollowupSectionIfPresent(curVal); //never present on Edit
    	  });
}

function conStdUpdateDispositionSection() {
        var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
	if(actionMode == 'Preview') { 
	        return;
    	}	
	
	var curVal = $j('#CON145 :selected').val();
	
	//If the referral basis is M1 and an investigation exists, don't let the user choose FF and create another Syphilis, congenital
	//var rscExists = false;
	//$j('#CON145 option').each(function(){
	//    if (this.value == 'RSC') {
	//        rscExists = true;
	//    }
	//});
	//var referralBasisVal =  $j('#CON144 :selected').val();
	//if (rscExists && referralBasisVal == 'M1' && curVal == 'FF') {
	//	alert("If the Referral Basis is M1 - Congenital Infant Followup and an Investigation already exists for the contact child - you cannot choose Field Follow-up and create another investigation");
	//	$j("#CON145").find('option').attr("selected","") ; //clear selected
	//	return;
	//}
	
	
	
	
	conStdCheckFieldFollowupSectionIfPresent(curVal);
	
	
	if (curVal == null || curVal == undefined || (curVal != 'SR' && curVal != 'RSC')) {
		pgDisableElement("CON146");
		pgDisableParticipationElement("CON137");
		pgDisableElement("CON138");
		pgDisableElement("CON140");
		pgDisableParticipationElement("CON147");
		pgDisableElement("NBS135");
		$j('#CON114').text("");  //clear dispo span
		var labelNode = getElementByIdOrByName("CON114L");
		labelNode.style.color="#666666";
		return;
	}
	pgEnableElement("CON146");
	var initiateDate = $j('#CON146').text();
	if (initiateDate == "")
		$j('#CON146').val(getConStdCurrentDate());//default initiate followup date
	//pgEnableParticipationElement("CON137");
	pgEnableElement("CON138");
	var assignedDate = $j('#CON138').text();
	if (assignedDate == "")
		$j('#CON138').val(getConStdCurrentDate()); //default date assigned
	if (curVal == 'SR') {
		var invText = $j('#CON137').text();
		if (invText== null || invText == "")
			conStdGetDefaultProvider('CON137');
		pgDisableElement("CON140"); //dispo date
		pgDisableParticipationElement("CON147"); //dispo by
		pgDisableElement("NBS135"); //source/spread
	}
	if (curVal == 'RSC') {
		pgEnableElement("CON140");
		//pgEnableParticipationElement("CON147");
		var labelNode = getElementByIdOrByName("CON114L");
		labelNode.style.color="#000";
		var dispoCd = $j('#CON114cd').val();
		if (dispoCd != null && dispoCd != "") {
			conStdSetDispositionText(dispoCd); //restore dispo span
			pgEnableElement("NBS135"); //enable source/spread
		} else {
			pgDisableElement("NBS135");
		}
		var invText = $j('#CON137').text();
		if (invText== null || invText == "")
			conStdGetDefaultProvider('CON137');
		var dispoDate = $j('#CON140').text();
		if (dispoDate  == "")
			$j('#CON140').val(getConStdCurrentDate()); //default date assigned			
		var dispoByText = $j('#CON147').text(); 
		if (dispoByText == null || dispoByText == "")
			conStdGetDefaultProvider('CON147');
	}
	
	
}

function conStdCheckFieldFollowupSectionIfPresent(procDecision) {
  // see if Field Followup tab is present
  var invStartDateEle = getElementByIdOrByName('INV147');
  if (invStartDateEle == null || typeof(invStartDateEle) == 'undefined') {
    return;
  }
  if (procDecision == null || procDecision != "FF") {
  
  	pgDisableElement("INV147");
  	pgRequireNotElement("INV147");
  	//pgDisableElement("NBS191");
  	//pgRequireNotElement("NBS191");
  	pgDisableParticipationElement("CONINV180");
  	pgDisableElement("INV110");
  	pgRequireNotElement("INV110");
  	pgDisableElement("NBS142");
  	pgRequireNotElement("NBS142L");
	pgDisableElement("NBS143");
	pgRequireNotElement("NBS143L");
	return;
  }
  pgEnableElement("INV147");
  pgRequireElement("INV147");
  //pgEnableElement("NBS191");
  //pgRequireElement("NBS191");
  pgEnableParticipationElement("CONINV180");
  pgEnableElement("INV110");
  pgRequireElement("INV110");
  pgEnableElement("NBS142");
  //if internet info elicited - require internet followup
  var infoElicited = $j('#NBS127 :selected').val();
  if (infoElicited != null && infoElicited == 'Y')
  	pgRequireElement("NBS142L");
  conStdCongenitalHideNotifiable(); //for Congenital NBS143 should be hidden
  conStdCheckReferralBasisOptions(); 
  return;

}

function getConStdCurrentDate() {
	var todayDT = new Date();
	var dd = todayDT.getDate();
	var mm = todayDT.getMonth()+1;//January is 0!
	var yyyy = todayDT.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	return (mm+'/'+dd+'/'+yyyy);
}



function conStdSetDispositionText(dispoCd) {
	if (dispoCd == null || dispoCd == "")
		return retText;
	  JBaseForm.getCodeShortDescTxt(dispoCd, "FIELD_FOLLOWUP_DISPOSITION_STD", function(retData) {  //tbd change to FIELD_FOLLOWUP_DISPOSITION_STDHIV
	  	if (retData != null)
	  		$j('#CON114').text(retData);

  	});
}
		
function calculateTotalSexPartnersOnSubmit(){

	if (getElementByIdOrByName('STD120')==null || getElementByIdOrByName('STD120')==undefined)
		return; //congenital syph page
	
	var v1 = parseInt($j("#NBS224").val(),10);
	var v2 = parseInt($j("#NBS226").val(),10);
	var v3 = parseInt($j("#NBS228").val(),10);
	var totalNumber=0;
	if ( !isNaN(v1) ) {
        totalNumber = totalNumber+v1;
    }
    if (!isNaN(v2)) {
        totalNumber = totalNumber+v2;
    }
    if (!isNaN(v3)) {
        totalNumber = totalNumber+v3;
    }
	//alert(totalNumber);
    if(totalNumber == 0){
		totalNumber='';
		}
	//alert(getElementByIdOrByName('STD120').value);
	getElementByIdOrByName('STD120').value=totalNumber;
	//If Female, Male and Transgender are all No, then set STD120 = 0 (Per STD Program Feedback on 5/15/2014 via communication with John Abellera.)
	var v4 = $j("#NBS223").val();
	var v5 = $j("#NBS225").val();
	var v6 = $j("#NBS227").val();
	if(v4 == 'N' && v5 == 'N' && v6 == 'N'){
		getElementByIdOrByName('STD120').value=0;
	}
	return;
}

function sexPartnersValidationOnSubmit(){
   var i = 0;
   var j = 0
   var errorElts = new Array();
   var errorMsgs = new Array();
    //past 12 months
	var v1 = parseInt($j("#NBS224").val(),10);
	var v2 = parseInt($j("#NBS226").val(),10);
	var v3 = parseInt($j("#NBS228").val(),10);
	
	var v4 = parseInt($j("#NBS130").val(),10);
	var v5 = parseInt($j("#NBS132").val(),10);
	var v6 = parseInt($j("#NBS134").val(),10);
	
	if ( !isNaN(v1) &&   !isNaN(v4) && v4>v1) {
		   var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS130'), "Female Interview Period Partners");
		   var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS224'), "Female Partners");
		   errorMsgs[i++] ="Number of " + a1Str + " must be equal to or less than the number of " + a2Str + " in Past Year.  Please correct the data and try again.";
           errorElts[j++] = getElementByIdOrByName('NBS224');
		   errorElts[j++] = getElementByIdOrByName('NBS130');
           $j('#NBS224L').css("color", "990000");
           $j('#NBS130L').css("color", "990000");
 	} else{
 		$j('#NBS224L').css("color", "black");
 		$j('#NBS130L').css("color", "black");// clear if color present
 	}
	if ( !isNaN(v2) &&   !isNaN(v5) && v5>v2) {
		   var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS132'), "Male Interview Period Partners");
		   var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS226'), "Male Partners");
		   errorMsgs[i++] ="Number of " + a1Str + " must be equal to or less than the number of " + a2Str + " in Past Year.  Please correct the data and try again.";
		   errorElts[j++] = getElementByIdOrByName('NBS226');
		   errorElts[j++] = getElementByIdOrByName('NBS132');
		   $j('#NBS226L').css("color", "990000");
		   $j('#NBS132L').css("color", "990000");
	} else{
		$j('#NBS226L').css("color", "black");
		$j('#NBS132L').css("color", "black");// clear if color present
	}
	if ( !isNaN(v3) &&   !isNaN(v6) && v6>v3) {
		   var a1Str = buildErrorAnchorLink(getElementByIdOrByName('NBS134'), "Transgender Interview Period Partners");
		   var a2Str = buildErrorAnchorLink(getElementByIdOrByName('NBS228'), "Transgender Partners");
   		   errorMsgs[i++] ="Number of " + a1Str + " must be equal to or less than the number of " + a2Str + " in Past Year.  Please correct the data and try again.";
		   errorElts[j++] = getElementByIdOrByName('NBS228');
		   errorElts[j++] = getElementByIdOrByName('NBS134');
		  $j('#NBS228L').css("color", "990000");
		  $j('#NBS134L').css("color", "990000");
	} else{
		$j('#NBS228L').css("color", "black");
		$j('#NBS134L').css("color", "black");// clear if color present
	}
	   return {elements : errorElts, labels : errorMsgs}
}

function conStdGetDefaultProvider(identifier) {
	var zzForm = JPageForm;
	if (identifier.startsWith("CON"))
		zzForm = JCTContactForm;
	zzForm.getDwrSpecifiedInvestigator("default", identifier, function (data) {
		dwr.util.setEscapeHtml(false);
		//alert("data=" + data);
		if (data.indexOf('$$$$$') != -1) {
			var code = $j(identifier + "Text");
			code.value = "";
			dwr.util.setValue(identifier, "");
			dwr.util.setValue(identifier + "Error", "");

			dwr.util.setValue("investigator.personUid", data.substring(0, data.indexOf('$$$$$')));
			dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$') + 5));

			getElementByIdOrByName(identifier + "Text").style.visibility = "hidden";
			getElementByIdOrByName(identifier + "Icon").style.visibility = "hidden";
			getElementByIdOrByName(identifier + "CodeLookupButton").style.visibility = "hidden";
			getElementByIdOrByName("clear" + identifier).className = "";
			getElementByIdOrByName(identifier + "SearchControls").className = "none";

		} else {
			dwr.util.setValue(identifier + "Error", data);
			getElementByIdOrByName(identifier + "Text").style.visibility = "visible";
			getElementByIdOrByName(identifier + "Icon").style.visibility = "visible";
			getElementByIdOrByName(identifier + "CodeLookupButton").style.visibility = "visible";
			getElementByIdOrByName("clear" + identifier).className = "none";
			getElementByIdOrByName(identifier + "SearchControls").className = "visible";
		}
	});
}

function calculateSTD888(){
	var v1 = $j("#NBS223").val();
	var v2 = $j("#NBS225").val();
	var v3 = $j("#NBS227").val();
	// STD888 = NULL if all three are NULL 
	if((v1 == '' || v1 == null) && (v2 == '' || v2 == null) && (v3 == '' || v3 == null)){
		getElementByIdOrByName('STD888').value='';
	}
	// STD888= Yes if all three of the responses for sex partners = Refused. 
	else if(v1 == 'R' && v2 == 'R' && v3 == 'R'){
		getElementByIdOrByName('STD888').value='Y';
	}
	// STD888 = No for any other combination of responses. 
	else{
		getElementByIdOrByName('STD888').value='N';
	}
}

function calculateSTD999(){
	var v1 = $j("#NBS223").val();
	var v2 = $j("#NBS225").val();
	var v3 = $j("#NBS227").val();
	//STD999 = NULL if all three responses are NULL.  
	if((v1 == '' || v1 == null) && (v2 == '' || v2 == null) && (v3 == '' || v3 == null)){
		getElementByIdOrByName('STD999').value='';
	}
	
	//STD999='Y' if at least one response is Unknown and the others are NOT Yes (i.e., No, Unknown, Refused, Null)
	else if(v1 == 'U' && v2 == 'U' && v3 == 'U'){
		getElementByIdOrByName('STD999').value='Y';
	}else if(v1 == 'U' &&  (v2 == '' || v2 == null || v2 != 'Y') &&  (v3 == '' || v3 == null || v3 != 'Y')){
		getElementByIdOrByName('STD999').value='Y';
	}else if(v2 == 'U' &&  (v1 == '' || v1 == null || v1 !='Y') &&  (v3 == '' || v3 == null || v3 != 'Y')){
		getElementByIdOrByName('STD999').value='Y';
	}else if(v3 == 'U' &&  (v1 == '' || v1 == null || v1 !='Y') &&  (v2 == '' || v2 == null || v2 != 'Y')){
		getElementByIdOrByName('STD999').value='Y';
	}	
	//STD999='Y' if only response is Refused (e.g., all Refused or may be a combo of Refused and Null, i.e., some may not be answered). 	
	else if(v1 == 'R' && v2 == 'R' && v3 == 'R'){
		getElementByIdOrByName('STD999').value='Y';
	}else if((v1 == '' || v1 == null) && v2 == 'R' && (v3 == '' || v3 == null)){
		getElementByIdOrByName('STD999').value='Y';
	}else if((v2 == '' || v2 == null) && v1 == 'R' && (v3 == '' || v3 == null)){
		getElementByIdOrByName('STD999').value='Y';
	}else if((v1 == '' || v1 == null) && v3 == 'R' && (v2 == '' || v2 == null)){
		getElementByIdOrByName('STD999').value='Y';
	}
	//Any combination of Y, N or U.  Did not refuse to answer the questions
	else
		getElementByIdOrByName('STD999').value='N';
}


function csMotherInfantRequired()
{
  var o = $j("#CS000").val(); 
  var exp = $j("#INV364").val();
  if( o == "I" ){ 
    pgSubSectionDisabled('GA27100');
    pgRequireNotElement("INV364");
    pgDisableElements(["CS002","CS003","CS004", "INV364"]);
    pgDisableParticipationElement("INV182");
    pgRequireElements(["CS048L", "CS051L", "CS054L", "CS062L", "CS063L", "CS064L", "CS065L", "CS070L"]);

  } else if( o == "M") {
    pgDisableElements(["CS005","CS006", "CS070"]);
    pgDisableParticipationElement("CS007");
    pgDisableParticipationElement("CS008");
    pgDisableParticipationElement("CS009");
    pgSubSectionDisabledGrey("GA26100");
    pgRequireElements(["INV364"]);
    pgEnableElements(["INV364"]);
  }
  if( $j.trim($j("#INV111").val()).length == 0 ){
    $j("#INV111").val( formatDate( new Date(), "MM/dd/yyyy")  );
  }
  $j("#INV364").val(exp);
  var stKey = $j.trim($j("#CS017S").text()); 
  var countyKey = $j.trim($j("#CS019S").text());
  if( stKey.length > 0 && countyKey.length > 0){
    JPageForm.getDwrCountiesForState(stKey, function(data) {
      var cArr = $j.grep(data, function(e) { return (e.key == countyKey); });
      dwr.util.setValue("DEM165",  cArr[0].value);
    });
  }
  $j("#CS015S").html( $j("#CS016").val() );//residence county FIPS span
  $j("#CS014S").html( $j("#AR109").val() );//reporting county FIPS span
  //reporting state onChange
  $j("#CS016").change( function(e){
    $j("#CS014S").html( "" );
    $j("#CS014").val(""); //reporting county FIPS hidden
    $j("#CS015S").html( $j("#CS016").val() );
  });
  $j("#AR109").change( function(e){
    $j("#CS014S").html( $j("#AR109").val() );
  });
}

function csDarkfieldTest()
{
  var o = $j("#CS000").val();
  var d = $j("#CS053").val();
  var c = $j("#CS070").val();
  var errorElts = new Array();
  var errorMsgs = new Array();
  
  var cc = $j("#caseClassCd").val();
  
  if( d == "1" && c != "2"){
    var a1Str = buildErrorAnchorLink(getElementByIdOrByName('CS053L'), "Dark Field");
    errorMsgs.push ("The " + a1Str + " results indicate confirmed case.");
    errorElts.push( getElementByIdOrByName("CS053") );
  }
  if( d != "1" && c == "2"){
    var a1Str = buildErrorAnchorLink(getElementByIdOrByName('CS053L'), "Dark Field/DFA");
    errorMsgs.push("The " + a1Str + " not consistent with classification.");
    errorElts.push( getElementByIdOrByName("CS053") );
  }
 
  if( (o == "I") && ( ( cc == 'N' && c != '1') || ( cc == 'C' && c != '2') || ( cc == 'P' && c != '4') ||  ( cc == 'U' && c != '9') ) ){
    var a1Str = buildErrorAnchorLink(getElementByIdOrByName('CS070L'), "Classification");
    var msg = 'The ' + a1Str + ' is not consistent with Case Status (' + cc + ') ';
    errorMsgs.push(msg )
    errorElts.push( getElementByIdOrByName("CS053") );
  } 
  return {elements : errorElts, labels : errorMsgs}
}

function validateVitalStatus()
{
  var v = $j("#CS071").val();
  var d = $j("#DEM128").val();
  if( (v == "2" && d == "") || (v == "1" && d != "") ){
    return { elements : [$j("#CS071")], labels : ["Vital Status is not consistent with Date of Death from Patients File"] };
  }
  return {elements : [], labels : []};  
}

function stdConInternetInfoElicitedRequireInternetFollowup() {
  // see if Field Followup tab is present
  var internetFollowup = getElementByIdOrByName('NBS142');
  if (internetFollowup == null || typeof(internetFollowup) == 'undefined') {
    return;
  }
  var procDecis = $j('#CON145 :selected').val();
  if (procDecis == null || procDecis != "FF")
  	return;
  //Only on create for a STD Contact is the field even present 
  //if internet info elicited - require internet followup
  var infoElicited = $j('#NBS127 :selected').val();
  if (infoElicited != null) {
  	if (infoElicited == 'Y')
  		pgRequireElement("NBS142L");
  	else
		pgRequireNotElement("NBS142L");
  }
}  

//These functions are here for old pages and have been deleted from the fixed rules..
function stdInterviewerEntryRequireDate() {
  return true;
}

function enableDisable900Info() {
	return;
}

function stdSurveillanceSynchFieldFollowupExamReason(examReasonElement) {
	return;
}

function stdSynchProviderDiagnosis(providerDiagnosisEle) {
	return;
}
//Any Contact cases started with Field Followup need to be dispositioned
//before closing this case.
function checkIfContactInvsDispositioned() {		
	var i = 0;
	var j = 0
	var errorElts = new Array();
	var errorMsgs = new Array();
	var dateClosed = $j("#NBS196").val();
	var contactNamedTable = getElementByIdOrByName('contactNamedByPatListID');
	if (dateClosed == null || dateClosed == "" || contactNamedTable == null) {
		$j('#NBS196L').css("color", "black");// clear if color present
		return {elements : errorElts, labels : errorMsgs}
	}
	//empty?
	if (contactNamedTable.rows[1].cells[1] == null || contactNamedTable.rows[1].cells[1].innerHTML == null)
		return {elements : errorElts, labels : errorMsgs};	
		
	var allDispoed = true;
	for (var r=1, n = contactNamedTable.rows.length; r < n; r++) {
		var contactRecId =  contactNamedTable.rows[r].cells[1].innerHTML;
		var contactInvId =  contactNamedTable.rows[r].cells[5].innerHTML;
		var contactDispo = contactNamedTable.rows[r].cells[4].innerHTML;
		if (contactRecId != null && contactRecId.indexOf("Field Follow") != -1) {
		    	if (contactInvId != null && contactInvId.indexOf("CAS") != -1) {
		    		if (contactDispo == null || contactDispo.length < 2) {
		    			var errLink = buildErrorAnchorLink(getElementByIdOrByName('NBS196'), "Date Closed");  
		    			errorMsgs[i++] = "A Contact's Investigation " + contactInvId + " has not been dispositioned. Please disposition the contact's Investigation before entering a " +errLink +".";
		    			errorElts[j++] = getElementByIdOrByName('NBS196');
		    			$j('#NBS196L').css("color", "990000");
				} 
			}
		}

	}
	if (i==0)
		$j('#NBS196L').css("color", "black");// clear if color present
	return {elements : errorElts, labels : errorMsgs}    		
}

function calculateAgeAtVaccination() {
	
	 pgCalculateReportedAge('DEM115','VAC105','VAC106','VAC103','VAC103'); 
	
}
	
function isConditionSyphilis(theConditionCd) {
	  if (theConditionCd == null)
		    return ("");
	  if (theConditionCd.indexOf("10311") != -1) //is Syph Primary
		    return true;
      if (theConditionCd.indexOf("10312") != -1) //is Syph Secondary
		    return true;
      if (theConditionCd.indexOf("10313") != -1) //is Syph Early Latent
		    return true;
      if (theConditionCd.indexOf("10315") != -1) //is Syph Unknown Latent
		    return true;
      if (theConditionCd.indexOf("10314") != -1) //is Syph Late Latent
		    return true;
      if (theConditionCd.indexOf("10318") != -1) //is Syph Not Neuro
		    return true;
      if (theConditionCd.indexOf("10316") != -1) //is Syph Congenital
		    return true;
      if (theConditionCd.indexOf("10320") != -1) //is Syph, Unknown Duration or Late
		    return true;
      if (theConditionCd.indexOf("700") != -1) //is Syph Unknown
		    return true;
    return false;
}	

function conStdCongenitalHideNotifiable() {
	  var dispoCd = $j('#CON145 :selected').val();
	  if (dispoCd != 'FF')
	  	return;
	  var referralBasisVal =  $j('#CON144 :selected').val();
	  if (referralBasisVal != "M1") {
		    pgUnhideElement("NBS143"); //could be hidden
		  	pgEnableElement("NBS143");
		  	pgRequireElement("NBS143L");
	   }
	  if (referralBasisVal == "M1") {
		    pgRequireNotElement("NBS143L");
		    pgDisableElement("NBS143");
		    pgHideElement("NBS143");
	  }
	  return;
}

//if checkbox checked then populates From paticipation data to To participation data
function populateParticipationFromOne(triggerCheckBoxId, fromId, toId,parentDoc){
	//ND-25420 //ND-23349
	if(parentDoc == null || parentDoc == 'undefined'){
		parentDoc = document;
	}
	
	JPageForm.clearDWROrganization(toId);
	
	if(getElementByIdOrByNameNode(toId+"CodeLookupButton", parentDoc)!=undefined){
		
		if(getElementByIdOrByNameNode("pageClientVO.answer(NBS_LAB267)", parentDoc).checked==false){
			getElementByIdOrByNameNode(toId+"Icon", parentDoc).disabled=false;
			getElementByIdOrByNameNode(toId+"CodeClearButton", parentDoc).disabled=false;
			getElementByIdOrByNameNode(toId+"Text", parentDoc).disabled=false;
			getElementByIdOrByNameNode(toId+"L", parentDoc).disabled=false;
			getElementByIdOrByNameNode(toId+"S", parentDoc).disabled=false;
	
			getElementByIdOrByNameNode(toId+"CodeLookupButton", parentDoc).disabled=false;
			
			$j("#"+toId+"L").css("color", "#000");
		  	$j("#"+toId+"S").css("color", "#000");
		  	$j("#"+toId+"CodeLookupButton").css("color", "#000");
		  	$j("#"+toId+"Icon").css("color", "#000");
		  	$j("#"+toId+"CodeClearButton").css("color", "#000");
		}
	}
  	
	if(triggerCheckBoxId!=null && getElementByIdOrByNameNode(triggerCheckBoxId.name, parentDoc).checked){
		var uid = getElementByIdOrByNameNode("attributeMap."+fromId+"Uid", parentDoc).value;

		JPageForm.getDwrOrganizationDetailsByUid(uid,toId, function(data) {
		});

		getElementByIdOrByNameNode("attributeMap."+toId+"Uid", parentDoc).value = uid;
		var reportingLab = getElementByIdOrByNameNode(fromId, parentDoc).innerHTML;

		pgDisableParticipationElement(toId, parentDoc);
		getElementByIdOrByNameNode(toId, parentDoc).innerHTML=reportingLab;
	}
}


/**
 * pgCheckNumberAndUnitsFieldsFormatOnSubmit: this method has been created as part of the Jira user stories:
 * ND-27264, ND-27272 and ND-27268. It consists of validating that any of the pairs <number, units> fields, like
 * Reported Age and Reported Age Units has either both values or none of them. If one of the fields contains a value
 * but the other field doesn't, it will display a validation error on submission of the investigation page (from edit submit or create submit investigation).
 * This method can be reuse with any other combination of field number (input text) and field units (select). We just need
 * to call the method with the question identifier of the fields.
 * @param idNumber
 * @param idNumberUnits
 * @returns {___anonymous104447_104488}
 */
function pgCheckNumberAndUnitsFieldsFormatOnSubmit(idNumber, idNumberUnits)

{

	if($j("#"+idNumber)!=null && $j("#"+idNumber)!=undefined && $j("#"+idNumber).length>0 &&
	$j("#"+idNumberUnits)!=null && $j("#"+idNumberUnits)!=undefined && $j("#"+idNumberUnits).length>0){
		
	   var errorElts = new Array();
	   var errorMsgs = new Array();
	
	   var txtidtbElts=[];
	   var txtidtbEltsLabelNodes=[];
	   
	 
	   var number = $j("#"+idNumber);
	   var units = $j("#"+idNumberUnits);
	
	   //var numberL = $j("#"+idNumber);
	   var unitsL = $j("#"+idNumberUnits+"L");
	   
	   var typeText = "input[type=text]";
	   var typeUnits = "select"
		   
		   
	   txtidtbElts.push(number);
	   txtidtbElts.push(units);
	   
	   txtidtbEltsLabelNodes.push(number);
	   txtidtbEltsLabelNodes.push(unitsL);//it's a select, we need the L to find the _textbox
	   
	
	   var j = 0;
	
	   for (var i = 0; i < txtidtbElts.length; i++)
	
	   	{
	
	   	if($j(txtidtbElts[i]).parent().parent().css("display")!="none"){
	
	   		
	   		var typeElement = typeText;
	   		if(i==1)
	   			typeElement = typeUnits;
	   		
	   		
	   		var	txtidtbEltsNode="";
	   		
	   		if(typeElement=="select")
	   			txtidtbEltsNode=$j(txtidtbEltsLabelNodes[i]).parent().children("span");
	   		else
	   			txtidtbEltsNode=$j(txtidtbElts[i]).parent().children(typeElement);
	
			var txtidtbEltsLabelId=$j(txtidtbElts[i]).parent().children(typeElement).attr("id")+"L";
	
			var txtidtbEltsLabel=getElementByIdOrByName(txtidtbEltsLabelId).innerHTML;
	
			var txtidtbEltsId=$j(txtidtbElts[i]).parent().children(typeElement).attr("id");
	
			var txtidtbEltsValue=$j(txtidtbElts[i]).parent().children(typeElement).attr("value");
	
			var txtidtbEltsValue2;
			var txtidtbEltsLabel2;
			
			if(i==0){
				txtidtbEltsValue2=$j(txtidtbElts[i+1]).parent().children(typeUnits).attr("value");
				var txtidtbEltsLabelId2=$j(txtidtbElts[i+1]).parent().children(typeUnits).attr("id")+"L";
				txtidtbEltsLabel2=getElementByIdOrByName(txtidtbEltsLabelId2).innerHTML;
			}
			else
				if(i==1){
					txtidtbEltsValue2=$j(txtidtbElts[i-1]).parent().children(typeText).attr("value");
					var txtidtbEltsLabelId2=$j(txtidtbElts[i-1]).parent().children(typeText).attr("id")+"L";
					txtidtbEltsLabel2=getElementByIdOrByName(txtidtbEltsLabelId2).innerHTML;
				}
	   		
	
	   		
	
			if(txtidtbEltsValue == '' && (txtidtbEltsValue2!=null && txtidtbEltsValue2!=undefined && txtidtbEltsValue2!='')) {//The elements that we are checking (the value of i, whether is 0 or 1, doesn't have the value, but the other element does have a value, then we need to create an error message for the element we are validating (i)
	
			
	
					var a2Str = buildErrorAnchorLink(txtidtbEltsNode[0], txtidtbEltsLabel);  
	
					var errHtmlStr;
					
					errHtmlStr =  a2Str + " is required if "+txtidtbEltsLabel2+" contains data.";//TODO" Update this text according to the Jira user story??
			
						
					errorMsgs[j] = errHtmlStr;
	
					errorElts[j] = getElementByIdOrByName(txtidtbEltsLabelId);
	
					j++;
	
	
	
					$j("#"+txtidtbEltsLabelId).css("color", "990000");
	
	
	
				} else {
	
					var theColorIs = $j("#"+txtidtbEltsLabelId).css("color");
	
					if (theColorIs == "990000" || theColorIs=="rgb(153, 0, 0)") //reset to black only if red
	
						$j("#"+txtidtbEltsLabelId).css("color", "black"); //clear color if fields not present
	
					}
	
				}
	   	}
}



   //only need the labels in the batch  

   return {elements : errorElts, labels : errorMsgs}  



}
