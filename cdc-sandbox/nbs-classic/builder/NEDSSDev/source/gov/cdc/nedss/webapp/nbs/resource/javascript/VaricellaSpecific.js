function varicellaCreateLoad(multiselects){          
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    
    if(actionMode == 'Preview') return;         

	onLoadReportedAgeCalc();  
	onLoadIllnessOnsetAgeCalc();
	
	var errorTabsPresent = false;
	JPamForm.getErrorTabs(function(data) 
    {
        if (data.length > 0) {
            errorTabsPresent = true;
            handleErrorTabs(data);
        }
        
        if (errorTabsPresent == false)
	    {
	       JPamForm.getTabId(function(data) {
	            if(data!=null && data!="" && data == '2') {
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
  
    // update multi-select results span to display selected options
    var selectEltIdsArray = multiselects.split("|");
    for (var i = 0; i < selectEltIdsArray.length; i++) 
    {
        var selectElt = getElementByIdOrByName(selectEltIdsArray[i]); 
        if(selectElt != null && !(selectElt.isDisabled) && selectElt.type == 'select-multiple')
        {
            var valuesDisplaySpanId = selectEltIdsArray[i] + "-selectedValues";
            displaySelectedOptions(selectElt, valuesDisplaySpanId);    
        }
    }   
}   

function getStrutsLayoutTabIdForTabOrderId(tabOrderId, actionMode)
{
    // CREATE MODE Tab order from left to right. 
    // Patient(1), Varicella(2), LDF1(3)
    // Value in parantheses represents tab order id for each tab in server
    var createModeTabs = new Array();
    createModeTabs["1"] = 0;
    createModeTabs["2"] = 1;
    // if tab_order_id is greater than 2, return tab_order_id - 1;
    
    
    // EDIT MODE Tabs order from left to right
    // Patient(1), Varicella(2), Supp Info, LDF1(3)
    // Value in parantheses represents tab order id for each tab in server
    var editModeTabs = new Array();
    editModeTabs["1"] = 0;
    editModeTabs["2"] = 1;
    // if tab_order_id is greater than 2, return tab_order_id
    
    if (actionMode == 'CREATE_SUBMIT') {
        if (tabOrderId <= 2) {
            return (createModeTabs["" + tabOrderId]); 
        }
        else {
            return (tabOrderId-1);
        }
    }
    else if (actionMode == 'EDIT_SUBMIT' || actionMode == 'Edit') {
        if (tabOrderId <= 2) {
            return (editModeTabs["" + tabOrderId]); 
        }
        else {
            return (tabOrderId);
        }
    }
    else {
        // unsupported action mode. return 0, the index to the first tab
        return 0; 
    }
}

function handleErrorTabs(data)
{
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    //alert("actionMode = " + actionMode);
    
    //alert("data.length = " + data.length);
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
            //alert("firstErrorTab after update = " + firstErrorTab);
            
            // get the struts layout tab index & change the header background color 
            var index = getStrutsLayoutTabIdForTabOrderId(data[i], actionMode);
            //alert("index = " + index);
            var tabHeader = getElementByIdOrByName("tabs0head" + index);
            tabHeader.className='ongletTextErr';
        }
        
        // automatically select the first error tab.
        var firstTabIndex = getStrutsLayoutTabIdForTabOrderId(firstErrorTab, actionMode);
        //alert("firstTabIndex = " + firstTabIndex);
        selectTab(0,pamTabCount(),firstTabIndex,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
    }
}

function displayDeathWS(dropDownElementId, resultSpanRowId)
{
	var dropDownElement = getElementByIdOrByName(dropDownElementId);
	
	 if (dropDownElement != null) {
        var optionSelected = null;
        if (dropDownElement.selectedIndex >= 0) {
            optionSelected = dropDownElement.options[dropDownElement.selectedIndex].value;
        }
        if (optionSelected == "Y") {
        	if (getElementByIdOrByName("resultSpanRowId") != null) {
	            getElementByIdOrByName("resultSpanRowId").style.display="block";
        	}
        	
        }else
        {
        	if (getElementByIdOrByName("resultSpanRowId") != null) {
	            getElementByIdOrByName("resultSpanRowId").style.display="none";
        	}
        	
        }
	 }
}
