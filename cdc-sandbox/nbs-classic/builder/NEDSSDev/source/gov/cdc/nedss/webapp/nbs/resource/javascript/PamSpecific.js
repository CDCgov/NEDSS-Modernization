//Fix NBS Context issues by disabling Backspace(8) and Enter(13) for appropriate PAM elements
if (typeof window.event != 'undefined')
  document.onkeydown = function()
    {
		var t=event.srcElement.type;
		if(t == '' || t == 'undefined' || t == 'button') {
			return;
		}
		var kc=event.keyCode;
		return ((kc != 8 && kc != 13) || ( t == 'text' &&  kc != 13 ) ||
				(t == 'textarea') || ( t == 'submit' &&  kc == 13) || ( t == 'image' &&  kc == 13));
		
		return preventF12(event);
    }

var newwindow = null;

function parent_disable() 
{
    if(newwindow && !newwindow.closed) {
        newwindow.focus();
    }
    if(newwindow && newwindow.closed) {
        getElementByIdOrByName("pamview").style.display = "none";
    }
}    

function getPamPage(target)
{
    document.forms[0].target="";		
    document.forms[0].action =target;
}

function validatePAMJurisdiction()
{
    var jurisd = getElementByIdOrByName("INV107");
    if(jurisd == null || (jurisd != null && jurisd.value == "")) {
        return "valid";
    }
    var NBSSecJurisdictionParseString = getElementByIdOrByName("NBSSecurityJurisdictions");
    if(NBSSecJurisdictionParseString.value == "") {
        return "valid";
    }
    var items = NBSSecJurisdictionParseString.value.split("|");
    var containsJurisdiction = false;
    var confirmMsg = "If you save the Investigation, you will not be able to view the data because it is outside your permitted Program Area/Jurisdiction. Select OK to continue, or Cancel to not continue.";
    if (items.length > 1) {
        for (var i=0; i < items.length; i++)
        {
            if (items[i]!=""  && items[i] == jurisd.value ) {
                containsJurisdiction = true;
            }
        }
    }
    if(!containsJurisdiction) {
        if(confirm(confirmMsg)) {
            return "true";
        }
        else {
            return "false";
        }
    }

    return "valid";
}

/**
    Function used by the popup calendar widget to populate
    the date selected in the text box.
*/
function getCalDate(obj, anchor)
{
    var cal = new CalendarPopup();
    //cal.showNavigationDropdowns(); // dropdowns for year & month
    
    // do not allow dates starting from the next day. 
    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate()+1);
    cal.addDisabledDates(formatDate(tomorrow, "yyyy-MM-dd"), null);
    
    cal.showYearNavigation(); // << and >> arrows to navigate year & month
    var newObj = getElementByIdOrByName(obj);
    cal.select(newObj,anchor,'MM/dd/yyyy');
}

/** Create a new notification from a PAM module */
function createPamNotification(publicHealthCaseUid)
{   
	if(!checkIfNotificationExists(publicHealthCaseUid)){
		var urlToOpen =  "/nbs/PamAction.do?method=createNotification&publicHealthCaseUid="+publicHealthCaseUid;
	    var divElt = getElementByIdOrByName("pamview");
	    var o = new Object();
	    o.opener = self;
	    var dlgStyle = "scroll:no;scrollbars:no;status:no;resizable:yes;help:no;dialog Height:700px;dialogWidth:840px;";
	    //window.showModalDialog(urlToOpen,o, dlgStyle);
	    
	    var modWin = openWindow(urlToOpen, o,dlgStyle, divElt, "");
	    
		}	
    return false;
}

function createNotifications(comments)
{
    JPamForm.updateNotifications(comments, function(data)
    {
        if(data.length == 1) {
            displayGlobalErrorMessage(data[0]);  
        }
        else {
            displayGlobalSuccessMessage(data[0]);             
        }
        var notifications = data[1];
        if(notifications != null) {
            var notif = getElementByIdOrByName("notificationSection");
            notif.innerHTML = notifications;
            var notifExists = getElementByIdOrByName("NotificationExists");
            notifExists.value="true";
            
            // set the current value of notification data in the patient summary part of view JSP.
            var tr0 = $j(notif).find("tbody tr").get(0);
            var td5 = $j(tr0).find("td").get(5); // 6th cell contains notification status
            $j("#patientSummaryJSP_view_notificationStatus").html($j(td5).html());
        }
    });
}

function displayNotifications(parentClass) 
{
	var childClass = parentClass.replace("parent", "child");
	var tableId = "notificationHistoryTable";
	var tableElt = $j("#" + tableId);
	var parentRowElt = $j(tableElt).find("." + parentClass).get(0);
	
	var imgSrc = $j($j(parentRowElt).find("img").get(0)).attr("src");
	if (imgSrc.indexOf("minus_sign.gif") >= 0) {
		$j($j(parentRowElt).find("img").get(0)).attr("src", "plus_sign.gif");
		$j($j(parentRowElt).find("img").get(0)).attr("alt", "Expand");
		
	}
	else if (imgSrc.indexOf("plus_sign.gif") >= 0) {
		$j($j(parentRowElt).find("img").get(0)).attr("src", "minus_sign.gif");
		$j($j(parentRowElt).find("img").get(0)).attr("alt", "Collapse");
		
	}
	
	var childRowsElts = $j(tableElt).find("." + childClass);
	for (var i = 0; i < childRowsElts.length; i++) {
		var singleChildRow = $j(childRowsElts[i]);
		if ($j(singleChildRow).css("display") == "none") {
			$j(singleChildRow).removeClass("none");
		}
		else {
			$j(singleChildRow).addClass("none");
		}
	}

}

/** Transfer Ownership of investigation for a PAM */
function transferPamOwnership()
{
    var divElt = getElementByIdOrByName("pamview");
    divElt.style.display = "block";		
    var o = new Object();
    o.opener = self;
    //window.showModalDialog("/nbs/PamAction.do?method=transferOwnershipLoad", o, GetDialogFeatures(850, 500, false));
    
    var URL = "/nbs/PamAction.do?method=transferOwnershipLoad";
    var modWin = openWindow(URL, o, GetDialogFeatures(850, 500, false, false), divElt, "");
    
    return false;
}

function pamTOwnership(jurisd,exportFacility,comment ) {
    document.forms[0].action ="/nbs/PamAction.do?method=transferOwnershipSubmit&INV107=" + jurisd+ '&exportFacility=' + exportFacility + '&comment=' + comment;
    document.forms[0].submit();
}
/** Share PAM case with other facility*/
function sharePamCaseLoad()
{
    var divElt = getElementByIdOrByName("pamview");
    divElt.style.display = "block";		
    var o = new Object();
    o.opener = self;
   // window.showModalDialog("/nbs/PamAction.do?method=sharePamCaseLoad", o, GetDialogFeatures(700, 400, false));
    
    var URL = "/nbs/PamAction.do?method=sharePamCaseLoad";
    
    var modWin = openWindow(URL, o,GetDialogFeatures(700, 400, false, true), divElt, "");
    
    return false;
}

function sharePamCaseSubmit(exportFacility,comment ) {
    document.forms[0].action ="/nbs/PamAction.do?method=sharePamCaseSubmit&exportFacility=" + exportFacility + '&comment=' + comment;
    document.forms[0].submit();
}

function checkAsianRaces()
{
  JPamForm.clearDetailsAsian(function(data) {
      DWRUtil.removeAllOptions("DEM243");
      DWRUtil.addOptions("DEM243", data, "key", "value");
    });
    var asianRace = getElementByIdOrByName("pamClientVO.asianRace").checked;
    if(asianRace)
    {
        getElementByIdOrByName("DEM243").className="";
        getElementByIdOrByName("asian-multi").className="";
    }
    else 
    {
        getElementByIdOrByName("DEM243").className="none";
        getElementByIdOrByName("asian-multi").className="none";
        
        // reset the selected values displayed
        var selectBox = getElementByIdOrByName("DEM243");
        for (i = 0; i < selectBox.options.length; i++) {
            selectBox.options[i].selected = false;
        }
        displaySelectedOptions(selectBox, "DEM243-selectedValues");
    }
}

function checkHawaiianRaces()
{
  JPamForm.clearDetailsHawaii(function(data) {
      DWRUtil.removeAllOptions("DEM245");
      DWRUtil.addOptions("DEM245", data, "key", "value");
    });

    var hawaiianRace= getElementByIdOrByName("pamClientVO.hawaiianRace").checked;
    if(hawaiianRace){
        getElementByIdOrByName("DEM245").className="";
        getElementByIdOrByName("hawaiian-multi").className="";
    }
    else 
    {
        getElementByIdOrByName("DEM245").className="none";
        getElementByIdOrByName("hawaiian-multi").className="none";
        
        // reset the selected values displayed
        var selectBox = getElementByIdOrByName("DEM245");
        for (i = 0; i < selectBox.options.length; i++) {
            selectBox.options[i].selected = false;
        }
        displaySelectedOptions(selectBox, "DEM245-selectedValues");
    }
}
        
function getSelectedOptsString(opts)
{
    var returnVar = "";         
    for (var i= 0; i< opts.length; i++)
    {
        if (opts[i].selected) {
            returnVar = returnVar + opts[i].value + ",";
        }
    }
   
    if(returnVar.length == 0) {
        returnVar = "abcxyz";    
    } 
    
    returnVar = returnVar.substring(0,(returnVar.length-1)); 
    //alert(returnVar);
    return returnVar;        
}

/** 
    Function called from the window.onload of create/edit of TB and Varicella PAMs. It gathers all the 
    HTML select elements with fireRule function defined in the onchange event. It then proceeds to
    create a new onKeyDown event to all those select boxes found. The onKeyDown event is programmed to look 
    for the 'tab key' press, i.e., with a keyCode = 9. If tab key press is detected, the  
    MoveFocusToNextField() function to move the focus to the next valid element is called.
*/
function attachMoveFocusFunctionToTabKey()
{ 
    // attach onKeyDown events for all the select boxes that has a fireRule call assigned to it.
    var selectElts = $j("select[onchange]");
    for (var i = 0; i < selectElts.length; i++)
    {
        var onchangeEvt = "" + $j(selectElts[i]).attr("onchange");
        if (onchangeEvt.indexOf("fireRule") != -1)
        {
            var tmp = onchangeEvt.substring(onchangeEvt.indexOf("fireRule"), onchangeEvt.length);
            var fn = tmp.substring(0, tmp.indexOf(")")+1);
            var fnParamsList = fn.substring(fn.indexOf("(")+1, fn.indexOf(")"));
            var fnParamsArr = fnParamsList.split(",");
            
            if (fnParamsArr.length >= 2) {
                // fnParamsArr[0] is the HTML id of the select element
                // fnParamsArr[1] is the reference to the select element
                
                // attach the fireRule call to the onkeydown event for this select box
                $j(selectElts[i]).bind("keydown", function(e){
                    //alert("calling fireRuleAndChangeFocusOnTabKey with params.." + fnParamsArr[0] + ";" + fnParamsArr[1]);
                    var varKey = null;
                    if(window.event!=null) {
                        varKey = window.event.keyCode;
                        // look for tab key. i.e., keyCode = 9
                        if (varKey == "9") {
                            if (window.event.shiftKey) {
                                // if the shiftKey is pressed, return true so that
                                // default tab focus is carried out. i.e., the
                                // focus is moved to the valid HTML element that precedes
                                // this element
                                return true;
                            }
                            else {
                                // if shift key is not pressed, then call the
                                // MoveFocusToNextField() by passing the current element 
                                // (select elt) and supress the default browser operation
                                MoveFocusToNextField(this);
                                return false;   
                            }
                        }
                    }
                });
            }
        }
    }
}

function fireRuleAndChangeFocusOnTabKey(fieldId, element)
{
    var varKey = null;
    if(window.event!=null) {
        varKey = window.event.keyCode;
        if (varKey == "9") {
            if (window.event.shiftKey) {
                fireRule(fieldId, element, false);
            }
            else {
                fireRule(fieldId, element, true);
            }
        }
    }
}

/**
 * refreshSelects(): there's a bug with select-multiple in IE11. This method fix that bug.
 */

function refreshSelects(){
	var selectElements = document.getElementsByTagName("select");

	for(i=0;i<selectElements.length;i++){
		if(selectElements[i].type=="select-multiple")
			selectElements[i].style.zoom = selectElements[i].style.zoom ? "" : 1;
	}
}

function fireRule(field, newValue, updateCursorFocus)
{
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    if(actionMode == 'Preview') { 
        return;
    }
    
    var newValueValue = "abcxyz";
    if(newValue.type == 'select-multiple') {                
        newValueValue = getSelectedOptsString(newValue.options);    
    } 
    else {
        if (newValue.value != null && newValue.value != "") {
            newValueValue = newValue.value;
        }               
    }
    
    var fieldAndValue = field + ':' + newValueValue;
    JPamForm.fireRule(fieldAndValue, function(data) {
    	if (data.length == 0) return;
        for (var i = 0; i < data.length; i++) {
            updateFormField(data[i]);
        }

        // move the focus to the first valid field that follows the field that fired this rule.
         if (updateCursorFocus == true) {
            MoveFocusToNextField(newValue);
        }
         refreshSelects();
    });
}

function updateFormField(formField)
{
    // alert("Form Field is changed" + formField.fieldId + ", " +
    // "value is " + formField.string);
    // alert(formField.state.disabled);

    var currentField = $(formField.fieldId);
    var currentFieldTxtBox = $(formField.fieldAutoCompId);
    var currentFieldBtn = $(formField.fieldAutoCompBtn);
    var currentFieldLabelID = formField.fieldId +"L";
    var currentFieldLabel="";
    //alert("currentFieldLabelID. :"+currentFieldLabelID);
    //if(formField.fieldId == "DEM128"){
            
    currentFieldLabel = getElementByIdOrByName(currentFieldLabelID);
    //alert("currentFieldLabel inside IF :"+currentFieldLabel);
    //}

    // Sets enabled/disabled status, option values, and selected value //
    if (formField.fieldType == "Coded" && currentField != null) 
    {
        // alert("Made it into drop-down handler");
        var optionsList = formField.state.values;
        var Lcurrent = currentField.options.length;
        var Lnew = optionsList.length;
        // var newListLarger = Lnew > Lcurrent;
        if (Lnew > Lcurrent) {
            for (var i = 0; i < Lcurrent; i++) {
                currentField.options[i].text = optionsList[i].value;
                currentField.options[i].value = optionsList[i].key;
                currentField.options[i].selected = false;
            }
            for (var i = Lcurrent; i < Lnew; i++) {
                currentField.options[i] = new Option(optionsList[i].value,
                optionsList[i].key);
            }
        }
        else {
            //alert("Is smaller");
            for (var i = 0; i < Lnew; i++) {
                currentField.options[i].text = optionsList[i].value;
                currentField.options[i].value = optionsList[i].key;
                currentField.options[i].selected = false;
            }
            //Temporary Fix for TUB114, Countries multiselect (Need to revisit !!)
            if((formField.fieldId != "TUB228") && (formField.fieldId != "TUB229") && (formField.fieldId != "INV153") && (formField.fieldId != "INV154") && (formField.fieldId != "INV156")){
                currentField.options.length = Lnew;
            }                    
        }

        // dwr.util.removeAllOptions(formField.fieldId);
        // dwr.util.addOptions(formField.fieldId, formField.state.values, "key", "value" );
        // alert(formField.fieldId+"  " +formField.fieldAutoCompId+" "+formField.state.multiSelVals);
        // alert(formField.value);
        if(formField.value!=null && formField.value!=""){
            dwr.util.setValue(formField.fieldId, formField.value);
            dwr.util.setValue(formField.fieldAutoCompId, formField.value);
            autocompTxtValuesForJSPByElement(formField.fieldId);
         // alert("Here if");
         }else if(formField.state.multiSelVals != null && formField.state.multiSelVals !="" ){
         
            //alert("Here else");

             dwr.util.setValue(formField.fieldId, formField.state.multiSelVals);
             displaySelectedOptions(getElementByIdOrByName(formField.fieldId), formField.fieldId+'-selectedValues');
         }else if((formField.state.multiSelVals==null || formField.state.multiSelVals=="")){

              dwr.util.setValue(formField.fieldId, "");
	      dwr.util.setValue(formField.fieldAutoCompId, "");
         }

    }

    // Set main control state/value //              
    if (currentField != null) 
    {
        currentField.disabled = formField.state.disabled;
        if(currentField.disabled) {
            if(currentField.type == 'select-multiple') {					 
                for (var i=0; i<currentField.options.length; i++) {                             
                    currentField.options[i].selected = false;		
                }
            displaySelectedOptions(getElementByIdOrByName(formField.fieldId), formField.fieldId+'-selectedValues');
            }
            else {
                dwr.util.setValue(formField.fieldId, "");
            }
                         
            dwr.util.setValue(formField.fieldAutoCompId, "");
        }
        //alert("currentFieldLabel In here :"+currentFieldLabel);
        //alert("fieldId In here :"+formField.fieldId);
        //if(formField.fieldId == "DEM128"){
        //alert("currentFieldLabel enabling:"+currentFieldLabel);
        //alert("formField.state.disabled :" +formField.state.disabled);
        //currentFieldLabel.disabled = formField.state.disabled;

        if(currentFieldLabel != null) {
            currentFieldLabel.className = formField.state.disabledString;
        }    
        // }            
        
        if (formField.fieldType == "Coded") {
            if(currentFieldTxtBox != null && currentFieldBtn != null) {
                currentFieldTxtBox.disabled = formField.state.disabled;
                currentFieldBtn.disabled = formField.state.disabled;
            }
        }
    }
            
    //*** Defect15152:This is a temporary fix for "TUB202". Needs to be reworked.
    if((formField.fieldId == "TUB106" || formField.fieldId == "TUB202") && currentField.disabled == false ){
    }
    else
    {
        if(formField.fieldType != "Coded"){
            dwr.util.setValue(formField.fieldId, formField.value);
            dwr.util.setValue(formField.fieldAutoCompId, formField.value);
        }
    }

    if( getElementByIdOrByName(formField.fieldId).type == 'select-multiple'){
        // alert(formField.fieldId);
        var selString = formField.fieldId + '-selectedValues';
        //alert(selString);
        //displaySelectedOptions(formField.fieldId, selString);
    }
}


function getDWRCounties(state, elementId)
{
    var state1 = state.value;
    if(state1 == null) {
        state1= state;
    }
	
    JPamForm.getDwrCountiesForState(state1, function(data) {
        DWRUtil.removeAllOptions(elementId);
        getElementByIdOrByName(elementId + "_textbox").value="";
        DWRUtil.addOptions(elementId, data, "key", "value" );
    });    
}

function getDWRCitites(state)
{
    var stateCode = state.value;
    JPamForm.getDwrCityList(stateCode, function(data) {
        DWRUtil.removeAllOptions("cityList");
        DWRUtil.addOptions("cityList", data, "value", "key" );
        // dwr.util.setValue("name", data);
    });
}

function checkRacesOptions()
{
    var hawaiianRace= getElementByIdOrByName("pamClientVO.hawaiianRace").checked;
    if(hawaiianRace) {
        getElementByIdOrByName("hawaiianlist").className="";
        getElementByIdOrByName("hawaiian-multi").className="";
    }
    else {
        getElementByIdOrByName("DEM245").className="none";
        getElementByIdOrByName("hawaiian-multi").className="none";
    }
    
    var asianRace = getElementByIdOrByName("pamClientVO.asianRace").checked;
    if(asianRace) {
        getElementByIdOrByName("asianlist").className="";
        getElementByIdOrByName("asian-multi").className="";
    } else {
        getElementByIdOrByName("DEM243").className="none";
        getElementByIdOrByName("asian-multi").className="none";
    }
}

function pamTabCount() 
{
    var disabledTabHandleClass = ".ongletTextDis";
    var errorTabHandleClass    = ".ongletTextErr";
    var enabledTabHandleClass  = ".ongletTextEna";
    var pamTabCount = 0;
    pamTabCount = $j(disabledTabHandleClass).length + $j(errorTabHandleClass).length + $j(enabledTabHandleClass).length;
    return pamTabCount;      
}

function onLoadReportedAgeCalc()
{
    var reportedAgeNode = getElementByIdOrByName("DEM216");
    var reportedAgeUnitsNode = getElementByIdOrByName("DEM218");

    if(reportedAgeNode.value == "" && reportedAgeUnitsNode.value == "") {
        calculateReportedAge();
    }
}


function calculateReportedAge() 
{
    var reportedAgeNode = getElementByIdOrByName("DEM216");
    var reportedAgeUnitsNode = getElementByIdOrByName("DEM218");

    var dobNode = getElementByIdOrByName("DEM115");
    // FIXME : make sure it is DEM115
    var calcDOBNode = getElementByIdOrByName("DEM115");

    var asOfDateNode = getElementByIdOrByName("DEM207");
    if(asOfDateNode == null) {
        // FIXME : make sure it is DEM215
        asOfDateNode = getElementByIdOrByName("DEM215");
    }

    //alert("dobNode:" + dobNode.value);
    //alert("asOfDateNode:" + asOfDateNode.value);
    //alert("reportedAgeNode:" + reportedAgeNode.value);
    //alert("reportedAgeUnitsNode:" + reportedAgeUnitsNode.value);

    if(dobNode.value!="" && isDate(dobNode.value)){
        calcDOBNode.value = dobNode.value;
    }

    var calcDOBDate = new Date(calcDOBNode.value);

    //figure out the reported age and units
    //don't show if calc dob is empty
    if (dobNode!=null && dobNode.value!="" && asOfDateNode!=null && asOfDateNode.value!="") 
    {
        var asOfDate = new Date(asOfDateNode.value);
        //alert("should reset reportedAgeNode");
        reportedAgeNode.value="";
        reportedAgeUnitsNode.value="";

        var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
        if(!window.isNaN(reportedAgeMilliSec))
        {
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
            } else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)  {
                reportedAgeNode.value=Math.floor(reportedAgeMonths);
                reportedAgeUnitsNode.value="M";
            } else  {
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
        autocompTxtValuesForJSP();
    }

    //alert("dobNode.value in calc:" + dobNode.value);
    //alert("calcDOBNode.value in calc:" + calcDOBNode.value);
    //alert("reportedAgeNode.value in calc:" + reportedAgeNode.value);
}

function isLeapYear(varyear)
{
   var leapyear = false;
   if((varyear% 4) == 0) leapyear = true;
   if((varyear% 100) == 0) leapyear = false;
   if((varyear% 400) == 0) leapyear = true;
   return leapyear;
}

function checkMaxLength(sTxtBox)
{
    maxlimit = 2000;
    if (sTxtBox.value.length > maxlimit)
    {
        sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
    }
}

function swallowEnter()
{
    if(event.keyCode==13){
        event.keyCode = null;
        return false;
    }
}

function isNumeric(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    var varKeys = [ 0, 8, 9, 16, 35, 36, 37, 38, 39, 40, 46];
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isInteger(c)) s += c;
         pTextbox.value = s;
    }
}        

function displayInvHistory(oSwitchImage) 
{
    var tbodyElts = invHistoryTable.getElementsByTagName("tbody");
    var tbody = tbodyElts[0];
    var trNodes = tbody.getElementsByTagName("tr");

    if(oSwitchImage.src.search(/minus_sign.gif/)==-1){
    	oSwitchImage.src = "minus_sign.gif";
    	oSwitchImage.alt = "Collapse";
        
    }
    else {
    	oSwitchImage.src = "plus_sign.gif";
    	oSwitchImage.alt = "Expand";
        
    }

    for (var i=0; i < trNodes.length; i++) 
    {
        if (i != 0) 
        {
            if(trNodes[i].style.display == "none") {
                trNodes[i].style.display = "";
            } 
            else {
                trNodes[i].style.display = "none";
            }
        }
    }
}

function genProviderAutocomplete(txt_id, div_id)
{
    new Ajax.Autocompleter(txt_id, div_id, "/nbs/getAutocompleterChoices", 
            {paramName: "value",parameters : "type=investigator"});
}

function enabledDateAssignedToInvestigation()
{
	
		if(getElementByIdOrByName("INV110")!=null){
	 	   getElementByIdOrByName("INV110").disabled=false;
  			getElementByIdOrByName("INV110").value= formatDate(new Date(), "MM/dd/yyyy") ;
		}
		if(getElementByIdOrByName("INV110L")!=null){
	   	 getElementByIdOrByName("INV110L").className ="InputFieldLabel";
		}
}

function disabledDateAssignedToInvestigation()
{
    // disable the date assigned to investigation field (INV110).
		if(getElementByIdOrByName("INV110")!=null){
			getElementByIdOrByName("INV110").value="";
		}
		    if(getElementByIdOrByName("INV110")!=null){
			getElementByIdOrByName("INV110").disabled=true;
		}
		if(getElementByIdOrByName("INV110L")!=null){
			getElementByIdOrByName("INV110L").className = "InputDisabledLabel";
		}
    
    
}

function getProvider(identifier) 
{
 	//alert('before clear: ' + identifier);
	clearProvider(identifier);
	//alert('After clear: ' + identifier);
	var urlToOpen = "/nbs/Provider.do?method=searchLoad&identifier="+identifier;
	var params="left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var pview;
	if((identifier == "INV207" || identifier == "INV225" || identifier == "INV247") && getElementByIdOrByName("pamview")!=null){
		  pview = getElementByIdOrByName("pamview");	
	}
	if(identifier == "INV207" && getElementByIdOrByName("blockparent")!=null){
		  pview = getElementByIdOrByName("blockparent");	
		 urlToOpen = "/nbs/Provider.do?method=searchLoad&identifier="+identifier+"-1";
	}
	pview.style.display = "block";
	 var o = new Object();
		    o.opener = self;
		//var modWin = window.showModalDialog(urlToOpen,o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
	         //   700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
        //    (true ? "resizable: yes;" : ""));
		
		var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " + 
        700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
        (true ? "resizable: yes;" : "");
		var modWin = openWindow(urlToOpen, o,dialogFeatures, pview, "");
	//newwindow = window.open(urlToOpen,'Provider', params);
	//newwindow.focus();
}

function clearProvider(identifier)
{
	var code = $(identifier+"Text");
	code.value = "";
	dwr.util.setValue(identifier, "");
	dwr.util.setValue(identifier+"Error", "");
	getElementByIdOrByName(identifier+"Text").style.visibility="visible";
	getElementByIdOrByName(identifier+"Icon").style.visibility="visible";
	getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
	getElementByIdOrByName("clear"+identifier).className="none";
	getElementByIdOrByName(identifier+"SearchControls").className="visible";

    // if the identifier is investigator 
    if (identifier == "INV207") {
        disabledDateAssignedToInvestigation();
    }	
if(identifier == "INV207" && getElementByIdOrByName("blockparent")!=null){
	JProgramAreaForm.clearDWRInvestigator(identifier);
}else{
	JPamForm.clearDWRInvestigator(identifier);
}
}

function getDWRProvider(identifier)
{
	dwr.util.setValue(identifier, "");
 var code = $(identifier+"Text");
 var codeValue= code.value;
 if (getElementByIdOrByName("blockparent")!=null){
	var form = JProgramAreaForm;
}else{
var form = JPamForm;
}

 form.getDwrInvestigatorDetails(codeValue,identifier, function(data) {
       dwr.util.setEscapeHtml(false);
       if(data.indexOf('$$$$$') != -1) {
	         var code = $(identifier+"Text");
	         code.value = "";
	         dwr.util.setValue(identifier, "");
	         dwr.util.setValue(identifier+"Error", "");
	
	         dwr.util.setValue("investigator.personUid", data.substring(0,data.indexOf('$$$$$')));
	         dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$')+5));
	
	        getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
	        //getElementByIdOrByName("investigatorIcon").style.visibility="hidden";
	        getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
	        getElementByIdOrByName("clear"+identifier).className="";
	        getElementByIdOrByName(identifier+"SearchControls").className="none";
	        
	        // enable the date assigned to investigator field
	        if (identifier == "INV207") {
	            enabledDateAssignedToInvestigation();
	        }
       } else {
           dwr.util.setValue(identifier+"Error", data);
            getElementByIdOrByName(identifier+"Text").style.visibility="visible";
            //getElementByIdOrByName("investigatorIcon").style.visibility="visible";
            getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
            getElementByIdOrByName("clear"+identifier).className="none";
            getElementByIdOrByName(identifier+"SearchControls").className="visible";
       }
    });
}

function cleanupPatientRacesViewDisplay()
{
    var container = getElementByIdOrByName("patientRacesViewContainer");
    if (container != null) {
        var value = container.innerHTML;
        value = jQuery.trim(value);
        var loc = value.lastIndexOf(",");
        if (loc != -1) {
            if (loc + value.substring(loc, value.length).length == value.length) {
                container.innerHTML = value.substring(0, loc);
            }
        }       
    }
}

function cleanupPatientRacesViewDisplay2()
{
    var container = getElementByIdOrByName("patientRacesViewContainer2");
    if (container != null) {
        var value = container.innerHTML;
        value = jQuery.trim(value);
        var loc = value.lastIndexOf(",");
        if (loc != -1) {
            if (loc + value.substring(loc, value.length).length == value.length) {
                container.innerHTML = value.substring(0, loc);
            }
        }       
    }
}


function genAutocomplete(txt_id, div_id)
{
    new Ajax.Autocompleter(txt_id, div_id, "/nbs/getAutocompleterChoices", {paramName: "value",parameters : "type=city"});
}

function disablePrintLinks() {
	$j("a[href]:not([href^=#])").removeAttr('href');	
}

 function genOrganizationAutocomplete(txt_id, div_id){
     new Ajax.Autocompleter(txt_id, div_id, "/nbs/getAutocompleterChoices", {paramName: "value",parameters : "type=organization"});
   }
   
function getDWROrganization(identifier)
    {
	 dwr.util.setValue(identifier, "");
	 var code = $(identifier+"Text");
     var codeValue= code.value;
     JPamForm.getDwrOrganizationDetails(codeValue,identifier, function(data) {
           dwr.util.setEscapeHtml(false);
           if(data.indexOf('$$$$$') != -1) {
        	   var code = $(identifier+"Text");
             code.value = "";
             dwr.util.setValue(identifier, "");
             dwr.util.setValue(identifier+"Error", "");
             
             dwr.util.setValue("organization.organizationUid", data.substring(0,data.indexOf('$$$$$')));
             dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$')+5));

             getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
             getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
             getElementByIdOrByName("clear"+identifier).className="";
             getElementByIdOrByName(identifier+"SearchControls").className="none";
           } else {

        	   dwr.util.setValue(identifier+"Error", data);
               getElementByIdOrByName(identifier+"Text").style.visibility="visible";
               getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
               getElementByIdOrByName("clear"+identifier).className="none";
               getElementByIdOrByName(identifier+"SearchControls").className="visible";
           }
        });
    } 
                      
 function getReportingOrg(identifier){
	 clearOrganization(identifier);
	 var urlToOpen = "/nbs/PamOrganization.do?method=searchOrganization&identifier="+identifier;
        var params="left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
        var varicella = getElementByIdOrByName("pamview");
        varicella.style.display = "block";
         var o = new Object();
	  o.opener = self;
	  //var modWin = window.showModalDialog(urlToOpen,o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
	 //           700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
      //      (true ? "resizable: yes;" : ""));
	  
	  var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " + 
      700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
      (true ? "resizable: yes;" : "");
	  
	  var modWin = openWindow(urlToOpen, o,dialogFeatures, varicella, "");
       // newwindow = window.open(urlToOpen,'Organization', params);
       // newwindow.focus();
	 
	 
 }
 
 function clearOrganization(identifier){
	 var code = $(identifier+"Text");
		code.value = "";
		dwr.util.setValue(identifier, "");
		dwr.util.setValue(identifier+"Error", "");
		getElementByIdOrByName(identifier+"Text").style.visibility="visible";
		getElementByIdOrByName(identifier+"Icon").style.visibility="visible";
		getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
		getElementByIdOrByName("clear"+identifier).className="none";
		getElementByIdOrByName(identifier+"SearchControls").className="visible";	
		
		JPamForm.clearDWROrganization(identifier);
}
   
 
function updateHospitalInformationFields(dropDownElementId, resultSpanId)
{
    var dropDownElement = getElementByIdOrByName(dropDownElementId);
    var codeClearButtonId = resultSpanId + "CodeClearButton";
    var searchButtonId =  resultSpanId + "Icon";
    var quickCodesearchTextBoxId = resultSpanId + "Text";
    var quickCodeLookupButtonId = resultSpanId + "CodeLookupButton";
    var searchControlsSpanId = resultSpanId + "SearchControls";
    var clearControlSpanId = "clear" + resultSpanId;
    var hospitalInformationLabel = resultSpanId + "HospitalInformationLabel";
    var hospitalSelectedLabel = resultSpanId + "HospitalSelectedLabel"; 
    
    if (dropDownElement != null) {
        var optionSelected = null;
        if (dropDownElement.selectedIndex >= 0) {
            optionSelected = dropDownElement.options[dropDownElement.selectedIndex].value;
        }
    
	    if (optionSelected == "Y") {
	        // enable all relevant elements
	        if (getElementByIdOrByName(codeClearButtonId) != null) {
	            getElementByIdOrByName(codeClearButtonId).disabled = false;
	        }
	        if (getElementByIdOrByName(searchButtonId) != null) {
	            getElementByIdOrByName(searchButtonId).disabled = false;
	        }
	        if (getElementByIdOrByName(quickCodesearchTextBoxId) != null) {
	            getElementByIdOrByName(quickCodesearchTextBoxId).disabled = false;
	        }
	        if (getElementByIdOrByName(quickCodeLookupButtonId) != null) {
	            getElementByIdOrByName(quickCodeLookupButtonId).disabled = false;
	        }
	        if (getElementByIdOrByName(hospitalInformationLabel) != null) {
	            getElementByIdOrByName(hospitalInformationLabel).className = "InputFieldLabel";
	        }
	        if (getElementByIdOrByName(hospitalSelectedLabel) != null) {
	            getElementByIdOrByName(hospitalSelectedLabel).className = "InputFieldLabel";
	        }
	    }
	    else {
	        // disable all relevant elements
	        if (getElementByIdOrByName(codeClearButtonId) != null) {
	            getElementByIdOrByName(codeClearButtonId).disabled = true;
	        }
	        if (getElementByIdOrByName(searchButtonId) != null) {
	            getElementByIdOrByName(searchButtonId).disabled = true;
	        }
	        if (getElementByIdOrByName(quickCodesearchTextBoxId) != null) {
	            getElementByIdOrByName(quickCodesearchTextBoxId).disabled = true;
	        }
	        if (getElementByIdOrByName(quickCodeLookupButtonId) != null) {
	            getElementByIdOrByName(quickCodeLookupButtonId).disabled = true;
	        }
	        if (getElementByIdOrByName(resultSpanId) != null) {
	            getElementByIdOrByName(resultSpanId).innerHTML = "";
	        }
	        if (getElementByIdOrByName(hospitalInformationLabel) != null) {
	            getElementByIdOrByName(hospitalInformationLabel).className = "InputDisabledLabel";
	        }
	        if (getElementByIdOrByName(hospitalSelectedLabel) != null) {
	            getElementByIdOrByName(hospitalSelectedLabel).className = "InputDisabledLabel";
	        }
	        
	        clearOrganization(resultSpanId);
	    }    
    }    
} 
function onLoadIllnessOnsetAgeCalc()
{
	var illnessOnsetAgeNode = getElementByIdOrByName("INV143");
    var illnessOnsetAgeUnitsNode = getElementByIdOrByName("INV144");
    var dobNode = getElementByIdOrByName("DEM115");
    var illnessOnsetDate = getElementByIdOrByName("INV137");
    var defaultAgeAtIllness = "";
    if(illnessOnsetAgeNode.value == "" && illnessOnsetAgeUnitsNode.value == "")
    {
    	calculateIllnessOnsetAge();
    		
    }
}

function calculateIllnessOnsetAge() {
	var illnessOnsetAgeNode = getElementByIdOrByName("INV143");
    var illnessOnsetAgeUnitsNode = getElementByIdOrByName("INV144");
    
    var dobNode = getElementByIdOrByName("DEM115");
    // FIXME : make sure it is DEM115
    var calcDOBNode = getElementByIdOrByName("DEM115");
    var illnessOnsetDateNode = getElementByIdOrByName("INV137");
    
    if(dobNode.value!="" && isDate(dobNode.value)){
        calcDOBNode.value = dobNode.value;
    }
    var calcDOBDate = new Date(calcDOBNode.value);
	if((dobNode.value!=null && dobNode.value!= "")&& (illnessOnsetDateNode.value!=null && illnessOnsetDateNode.value!= "") )
	{
        var illnessOnsetDate = new Date(illnessOnsetDateNode.value);

        //alert("should reset reportedAgeNode");
        illnessOnsetAgeNode.value="";
        illnessOnsetAgeUnitsNode.value="";

        var illnessOnsetAgeMilliSec = illnessOnsetDate.getTime() - calcDOBDate.getTime();
        if(!window.isNaN(illnessOnsetAgeMilliSec)){
            var illnessOnsetAgeSeconds = illnessOnsetAgeMilliSec/1000;
            var illnessOnsetAgeMinutes = illnessOnsetAgeSeconds/60;
            var illnessOnsetAgeHours = illnessOnsetAgeMinutes/60;
            var illnessOnsetAgeDays = illnessOnsetAgeHours/24;
            var illnessOnsetAgeMonths = illnessOnsetAgeDays/30.41;
            var illnessOnsetAgeYears = illnessOnsetAgeMonths/12;

            if(isLeapYear(calcDOBDate.getFullYear())) illnessOnsetAgeMonths = Math.floor(illnessOnsetAgeDays)/30.5;

            if(Math.ceil(illnessOnsetAgeDays)<=31){
            	illnessOnsetAgeNode.value=Math.ceil(illnessOnsetAgeDays);
            	illnessOnsetAgeUnitsNode.value="D";
            } else if(Math.ceil(illnessOnsetAgeDays)>31 && illnessOnsetAgeYears<1)  {
            	st=calcDOBDate.valueOf();en=illnessOnsetDate.valueOf();diff=en-st-(-0);diffdate=new Date();diffdate.setTime(diff);monthdiff=diffdate.getMonth();
            	
            	illnessOnsetAgeNode.value=monthdiff;
            	illnessOnsetAgeUnitsNode.value="M";
            } else  {
                // get rough estimated year age
                var yearDiff = illnessOnsetDate.getFullYear() - calcDOBDate.getFullYear();
                //need to determine whether birthday has happened
                if(illnessOnsetDate.getMonth()<calcDOBDate.getMonth())
                    yearDiff = yearDiff-1;
                else if(illnessOnsetDate.getMonth()==calcDOBDate.getMonth()){
                    if(illnessOnsetDate.getDate()<calcDOBDate.getDate())
                        yearDiff = yearDiff-1;
                }
                illnessOnsetAgeNode.value=yearDiff;//Math.floor(reportedAgeYears);
                illnessOnsetAgeUnitsNode.value="Y";
            //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
                if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && yearDiff > 1 && yearDiff < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
                {
                    currentAgeNode.innerText="11";
                    currentAgeUnitsNode.innerText="Months";
                }
            }

        } else {
        	illnessOnsetAgeNode.value="";
        	illnessOnsetAgeUnitsNode.value="";
        }
        autocompTxtValuesForJSP();
	}
}


function calculateIllnessDuration() {

    var illnessOnsetAgeNode = getElementByIdOrByName("INV139");
    var illnessOnsetAgeUnitsNode = getElementByIdOrByName("INV140");
    
    var onSetNode = getElementByIdOrByName("INV137");
    // FIXME : make sure it is INV137
    var calcOnSetNode = getElementByIdOrByName("INV137");
    var illnessEndDateNode = getElementByIdOrByName("INV138");
    
    if(onSetNode.value!="" && isDate(onSetNode.value)){
        calcOnSetNode.value = onSetNode.value;
    }
    var calcOnSetDate = new Date(calcOnSetNode.value);
	if((onSetNode.value!=null && onSetNode.value!= "")&& (illnessEndDateNode.value!=null && illnessEndDateNode.value!= "") )
	{
        var illnessEndDate = new Date(illnessEndDateNode.value);

        //alert("should reset reportedAgeNode");
        illnessOnsetAgeNode.value="";
        illnessOnsetAgeUnitsNode.value="";

        var illnessOnsetAgeMilliSec = illnessEndDate.getTime() - calcOnSetDate.getTime();
        if(!window.isNaN(illnessOnsetAgeMilliSec)){
            var illnessOnsetAgeSeconds = illnessOnsetAgeMilliSec/1000;
            var illnessOnsetAgeMinutes = illnessOnsetAgeSeconds/60;
            var illnessOnsetAgeHours = illnessOnsetAgeMinutes/60;
            var illnessOnsetAgeDays = illnessOnsetAgeHours/24;
            var illnessOnsetAgeMonths = illnessOnsetAgeDays/30.41;
            var illnessOnsetAgeYears = illnessOnsetAgeMonths/12;

            if(isLeapYear(calcOnSetDate.getFullYear())) illnessOnsetAgeMonths = Math.floor(illnessOnsetAgeDays)/30.5;


	    //alert('illnessOnsetAgeDays: ' + illnessOnsetAgeDays + ', Math.ceil(illnessOnsetAgeDays): ' + Math.ceil(illnessOnsetAgeDays));

            if(Math.ceil(illnessOnsetAgeDays)<=31){
            	illnessOnsetAgeNode.value=Math.ceil(illnessOnsetAgeDays);
            	illnessOnsetAgeUnitsNode.value="D";
            } else if(Math.ceil(illnessOnsetAgeDays)>31 && illnessOnsetAgeYears<1)  {            
           	st=calcOnSetDate.valueOf();en=illnessEndDate.valueOf();diff=en-st-(-0);diffdate=new Date();diffdate.setTime(diff);monthdiff=diffdate.getMonth();
        	
        	illnessOnsetAgeNode.value= monthdiff;
            	illnessOnsetAgeUnitsNode.value="M";
            } else  {
                // get rough estimated year age
                var yearDiff = illnessEndDate.getFullYear() - calcOnSetDate.getFullYear();
                //need to determine whether birthday has happened
                if(illnessEndDate.getMonth()<calcOnSetDate.getMonth())
                    yearDiff = yearDiff-1;
                else if(illnessEndDate.getMonth()==calcOnSetDate.getMonth()){
                    if(illnessEndDate.getDate()<calcOnSetDate.getDate())
                        yearDiff = yearDiff-1;
                }
                illnessOnsetAgeNode.value=yearDiff;//Math.floor(reportedAgeYears);
                illnessOnsetAgeUnitsNode.value="Y";
            //this is only for leap year, if OnSet date is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
                if(calcOnSetDate.getMonth() == 1 && calcOnSetDate.getDate()==29 && yearDiff > 1 && yearDiff < 1.1 && isLeapYear(calcOnSetDate.getFullYear()))
                {
                    currentAgeNode.innerText="11";
                    currentAgeUnitsNode.innerText="Months";
                }
            }

        } else {
        	illnessOnsetAgeNode.value="";
        	illnessOnsetAgeUnitsNode.value="";
        }
        autocompTxtValuesForJSP();
	}
	

}


   	 	function reloadInvs(filler){
   	         	//alert("Inside reload :" +filler.value);      
   	 	         JPamForm.callChildForm(filler.value, function(data) { 	   
   	 	         }); 	         	
   	 	         setTimeout("reldPage()", 1000);
   		  }
   		  
   		  function reldPage() {
   	 	  	  document.forms[0].action ="/nbs/LoadManageCtAssociation.do?method=manageContactsSubmit";
   			  document.forms[0].submit(); 
   		  }
   		  
   		function clearErrors()
   		{
   		  JPamForm.clearErrorList();
   		}


