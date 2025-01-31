function getProvider(identifier) 
{
 	//alert('before clear: ' + identifier);
	clearProvider(identifier);
	//alert('After clear: ' + identifier);
	var urlToOpen = "/nbs/Provider.do?method=searchLoad&identifier="+identifier;
	var params="left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var contactTracing = getElementByIdOrByName("ContactTracingLoad");
	contactTracing.style.display = "block";	
	 var o = new Object();
	    o.opener = self;
	//var modWin = window.showModalDialog(urlToOpen,o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
     //       700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
        //    (true ? "resizable: yes;" : ""));
	
	var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " + 
    700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
    (true ? "resizable: yes;" : "");
	var modWin = openWindow(urlToOpen, o,dialogFeatures, contactTracing, "");
	
	
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
    if (identifier == "CON137") {
        disabledDateAssignedToInvestigation();
    }	

    JCTContactForm.clearDWRInvestigator(identifier);
}

function getDWRProvider(identifier)
{
 dwr.util.setValue(identifier, "");
 var code = $(identifier+"Text");
 var codeValue= code.value;
 JCTContactForm.getDwrInvestigatorDetails(codeValue,identifier, function(data) {
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
	        if (identifier == "CON137") {
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
function genProviderAutocomplete(txt_id, div_id)
{
    new Ajax.Autocompleter(txt_id, div_id, "/nbs/getAutocompleterChoices", 
            {paramName: "value",parameters : "type=investigator"});
}
function disabledDateAssignedToInvestigation()
{
    // disable the date assigned to investigation field (CON138).
    getElementByIdOrByName("CON138").value="";
    getElementByIdOrByName("CON138").disabled=true;
    getElementByIdOrByName("CON138L").className = "InputDisabledLabel";
}
function enabledDateAssignedToInvestigation()
{
    getElementByIdOrByName("CON138").disabled=false;
    getElementByIdOrByName("CON138L").className ="InputFieldLabel";
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
		
		JCTContactForm.clearDWROrganization(identifier);
}
function genOrganizationAutocomplete(txt_id, div_id){
    new Ajax.Autocompleter(txt_id, div_id, "/nbs/getAutocompleterChoices", {paramName: "value",parameters : "type=organization"});
  }
function getDWROrganization(identifier)
{
 dwr.util.setValue(identifier, "");
 var code = $(identifier+"Text");
 var codeValue= code.value;
 JCTContactForm.getDwrOrganizationDetails(codeValue,identifier, function(data) {
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
       var contactTracing = getElementByIdOrByName("ContactTracingLoad");
       contactTracing.style.display = "block";
        var o = new Object();
       	    o.opener = self;
       	//var modWin = window.showModalDialog(urlToOpen,o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
         //          700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
         //   (true ? "resizable: yes;" : ""));
       
       	var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " + 
        700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
        (true ? "resizable: yes;" : "");
       	var modWin = openWindow(urlToOpen, o,dialogFeatures, null, "");
       	
      // newwindow = window.open(urlToOpen,'Organization', params);
      // newwindow.focus();
	 
	 
}

function getDWRCounties(state, elementId)
{
    var state1 = state.value;
    if(state1 == null) {
        state1= state;
    }
	
    JCTContactForm.getDwrCountiesForState(state1, function(data) {
        DWRUtil.removeAllOptions(elementId);
        getElementByIdOrByName(elementId + "_textbox").value="";
        DWRUtil.addOptions(elementId, data, "key", "value" );
    });    
}
function getDWRCitites(state)
{
    var stateCode = state.value;
    JCTContactForm.getDwrCityList(stateCode, function(data) {
        DWRUtil.removeAllOptions("cityList");
        DWRUtil.addOptions("cityList", data, "value", "key" );
        // dwr.util.setValue("name", data);
    });
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

function ctCreateLoad(multiselects)
{          
   
   // var actionMode =getElementByIdOrByName("actionMode") == null ? "" :getElementByIdOrByName("actionMode").value;
    //if(actionMode == 'Preview') return;  
   // alert("actionMode : "+ actionMode);

    
    onLoadReportedAgeCalc();
    
    // AutoComplete Stuff
    //autocompTxtValuesForJSP();

    // update multi-select results span to display selected options
    var selectEltIdsArray = multiselects.split("|");
   // alert("selectEltIdsArray : "+selectEltIdsArray);
    for (var i = 0; i < selectEltIdsArray.length; i++) 
    {
        var selectElt = getElementByIdOrByName(selectEltIdsArray[i]); 
        if(selectElt != null && !(selectElt.isDisabled) && selectElt.type == 'select-multiple')
        {
            var valuesDisplaySpanId = selectEltIdsArray[i] + "-selectedValues";
            displaySelectedOptions(selectElt, valuesDisplaySpanId);    
        }
    }
    
    
    
    // get error tabs
    var errorTabsPresent = false;
    JCTContactForm.getErrorTabs(function(data) 
    {
    
        if (data.length > 0) 
        {
           //alert("data.length:" + data.length);
           errorTabsPresent = true;           
           handleErrorTabs(data);
          
        }
        
        // if there are no error tabs, get the tab is selected in view mode 
	    // and automatically select it
	    if (errorTabsPresent == false) 
	    {
	        JCTContactForm.getTabId(function(data) {
	            if(data!=null && data!="" && data == '5') {
	                    data = '0'
	            }                               
	        
	            if(data!=null && data!="")
	            {
	                selectTab(0,pamTabCount(),data,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
	            }
	            else
	            {
	                selectTab(0,pamTabCount(),0,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
	            }
	        });
	    }    
    });
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


function pamTabCount() 
{
    var disabledTabHandleClass = ".ongletTextDis";
    var errorTabHandleClass    = ".ongletTextErr";
    var enabledTabHandleClass  = ".ongletTextEna";
    var pamTabCount = 0;
    pamTabCount = $j(disabledTabHandleClass).length + $j(errorTabHandleClass).length + $j(enabledTabHandleClass).length;
    return pamTabCount;      
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
function isLeapYear(varyear)
{
   var leapyear = false;
   if((varyear% 4) == 0) leapyear = true;
   if((varyear% 100) == 0) leapyear = false;
   if((varyear% 400) == 0) leapyear = true;
   return leapyear;
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
    JCTContactForm.fireRule(fieldAndValue, function(data) {
    	if (data.length == 0) return;
        for (var i = 0; i < data.length; i++) {
            updateFormField(data[i]);
        }

        // move the focus to the first valid field that follows the field that fired this rule.
         if (updateCursorFocus == true) {
            MoveFocusToNextField(newValue);
        }
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

function handleErrorTabs(data)
{
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    if (data.length > 0) 
    {
        var firstErrorTab = data[0];
        //alert("firstErrorTab = " + firstErrorTab);
        for (var i = 0; i < data.length; i++)
        {
            // update the firstErrorTab if required 
            if (data[i] < firstErrorTab) {
                firstErrorTab = data[i];
            }
            
            // get the struts layout tab index & change the header background color 
            var index = getStrutsLayoutTabIdForTabOrderId(data[i], actionMode);
            var tabHeader = getElementByIdOrByName("tabs0head" + index);
            tabHeader.className='ongletTextErr';
        }
        
        // automatically select the first error tab.
        var firstTabIndex = getStrutsLayoutTabIdForTabOrderId(firstErrorTab, actionMode);
        selectTab(0,pamTabCount(),firstTabIndex,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
    }
}

function getStrutsLayoutTabIdForTabOrderId(tabOrderId, actionMode)
{
	return (tabOrderId -1);
}

function validateContactJurisdiction()
{
var jurisd = getElementByIdOrByName("CON134");
    if(jurisd == null || (jurisd != null && jurisd.value == "")) {
	return "valid";
    }
    var NBSSecJurisdictionParseString = getElementByIdOrByName("NBSSecurityJurisdictions");
    var items = NBSSecJurisdictionParseString.value.split("|");
    var containsJurisdiction = false;
    var confirmMsg = "If you submit the Contact Record, you will not be able to view the Contact Record because it is outside your permitted Jurisdiction. Select OK to continue, or Cancel to not 	continue.";
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
    return "true";
}		


   


