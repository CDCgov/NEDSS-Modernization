/*
function RecFacReqFields() {
	$j(".infoBox").hide(); 

	var errors = new Array();
	var index = 0;
	var isError = false;
	var systemNm = getElementByIdOrByName("selection.receivingSystemNm");
	var shortNM = getElementByIdOrByName("selection.receivingSystemShortName");
	var recSysOid = getElementByIdOrByName("selection.receivingSystemOid");
	var recSysOwner = getElementByIdOrByName("selection.receivingSystemOwner");
	var recSysOwnerOid = getElementByIdOrByName("selection.receivingSystemOwnerOid");
	var sender = getElementByIdOrByName("selection.sendingIndCd");
	var receipient = getElementByIdOrByName("selection.receivingIndCd");
	var transfer = getElementByIdOrByName("selection.allowTransferIndCd");
	var jurDerive = getElementByIdOrByName("selection.jurDeriveIndCd");
	var reportType = getElementByIdOrByName("selection.reportType");
	var eventType = getElementByIdOrByName("reportTypeField").value;
	
	if (reportType != null && reportType.value.length == 0) {
		errors[index++] = "Report Type is required";
		getElementByIdOrByName("reportTypeLabel").style.color = "990000";
		isError = true;
	}
	if (eventType == 'PHC236') {
		
		if (shortNM != null && shortNM.value.length == 0) {			
			errors[index++] = "Display Name is required";
			getElementByIdOrByName("sysshrtNM").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("sysshrtNM").style.color = "black";
		}
		if (systemNm != null && systemNm.value.length == 0) {
			errors[index++] = "Application Name is required";
			getElementByIdOrByName("systemNM").style.color = "990000";
			isError = true;
		} else {

			getElementByIdOrByName("systemNM").style.color = "black";
		}
		if (recSysOwner != null && recSysOwner.value.length == 0) {
			errors[index++] = "Facility Name is required";
			getElementByIdOrByName("systemOwner").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("systemOwner").style.color = "black";
		}
		if (recSysOwnerOid != null && recSysOwnerOid.value.length == 0) {
			errors[index++] = "Facility OID or Identifier is required";
			getElementByIdOrByName("systemOwnerOId").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("systemOwnerOId").style.color = "black";
		}
		

		if (recSysOid != null && recSysOid.value.length == 0) {
			errors[index++] = "Application OID is required";
			getElementByIdOrByName("systemOid").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("systemOid").style.color = "black";
		}
		
		if (sender != null && sender.value.length == 0) {
			errors[index++] = "Sending System is required";
			getElementByIdOrByName("sendSystem").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("sendSystem").style.color = "black";
		}

		if (receipient != null && receipient.value.length == 0) {
			errors[index++] = "Receiving System is required";
			getElementByIdOrByName("receivSystem").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("receivSystem").style.color = "black";
		}

		/*if (transfer.disabled == false && transfer != null
				&& transfer.value.length == 0) {
			errors[index++] = "Allows for Transfers is required";
			getElementByIdOrByName("transferRecSys").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("transferRecSys").style.color = "black";
		}

		if (jurDerive != null && jurDerive.value.length == 0) {
			errors[index++] = "Use System Derived Jurisdiction is required";
			getElementByIdOrByName("jurDerive").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("jurDerive").style.color = "black";
		}*/
		
/*
	}

	if (eventType == '11648804') {
		if (shortNM != null && shortNM.value.length == 0) {
			errors[index++] = "Display Name is required";
			getElementByIdOrByName("sysshrtNM").style.color = "990000";
			isError = true;
		} else {

			getElementByIdOrByName("sysshrtNM").style.color = "black";
		}
		if (recSysOwner != null && recSysOwner.value.length == 0) {
			errors[index++] = "Facility Name is required";
			getElementByIdOrByName("systemOwner").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("systemOwner").style.color = "black";
		}
		if (recSysOwnerOid != null && recSysOwnerOid.value.length == 0) {
			errors[index++] = "Facility OID or Identifier is required";
			getElementByIdOrByName("systemOwnerOId").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("systemOwnerOId").style.color = "black";
		}

		if (sender != null && sender.value.length == 0) {
			errors[index++] = "Sending System is required";
			getElementByIdOrByName("sendSystem").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("sendSystem").style.color = "black";
		}

	}
	if (isError) {
		displayErrors("receivingFacErrors", errors);
	}
	return isError;
}*/
function cancelFilter(key) {
	key1 = key.substring(key.indexOf("(") + 1, key.indexOf(")"));
	JTriggerCodeForm.getAnswerArray(key1, function(data) {
		revertOldSelections(key, data);
	});
}

function revertOldSelections(name, value) {
	if (value == null) {
		$j("input[@name=" + name + "][type='checkbox']").attr('checked', true);
		$j("input[@name=" + name + "][type='checkbox']").parent().parent()
				.find('INPUT.selectAll').attr('checked', true);
		return;
	}

	//step1: clear all selections
	$j("input[@name=" + name + "][type='checkbox']").attr('checked', false);
	$j("input[@name=" + name + "][type='checkbox']").parent().parent().find(
			'INPUT.selectAll').attr('checked', false);

	//step2: check previous selections from the form
	for ( var i = 0; i < value.length; i++) {
		$j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr('checked',
				true);
	}
	//step3: if all are checked, automatically check the 'select all' checkbox
	if (value.length == $j("input[@name=" + name + "][type='checkbox']")
			.parent().length)
		$j("input[@name=" + name + "][type='checkbox']").parent().parent()
				.find('INPUT.selectAll').attr('checked', true);
}


function clearFilter() {

	document.forms[0].action = "/nbs/TriggerCodes.do?method=resultsLoad&initLoad=true&context=removefilters";
	document.forms[0].submit();
}


function _setAttributes(headerNm, link, colId) {

	var imgObj = link.parent().find("img");
	var toolTip = "";
	var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
	var orderCls = "SortAsc.gif";
	var altOrderCls = "Sort Ascending";
	var sortOrderCls = "FilterAndSortAsc.gif";
	var altSortOrderCls = "Filter Applied with Sort Ascending";
	if (sortSt != null && sortSt.indexOf("descending") != -1) {
		orderCls = "SortDesc.gif";
		altOrderCls = "Sort Descending";
		sortOrderCls = "FilterAndSortDesc.gif";
		altSortOrderCls = "Filter Applied with Sort Descending";
	}
	var filterCls = "Filter.gif";
	var altFilterCls = "Filter Applied";
	toolTip = colId.html() == null ? "" : colId.html();
	if (toolTip.length > 0) {
		link.attr("title", toolTip);
		imgObj.attr("src", filterCls);
		imgObj.attr("alt", altFilterCls);
		if (sortSt != null && sortSt.indexOf(headerNm) != -1){
			imgObj.attr("src", sortOrderCls);
			imgObj.attr("alt", altSortOrderCls);
	}
	} else {
		if (sortSt != null && sortSt.indexOf(headerNm) != -1) {
			imgObj.attr("src", orderCls);
			imgObj.attr("alt", altOrderCls);
		}
	}
}



function setSelectedIndex(s, v, t) {
    for ( var i = 0; i < s.options.length; i++ ) {
    	var optionValue = s.options[i].value;
        if ( optionValue == v ) {
            s.options[i].selected = true;
            if (!(t==undefined) && !(t=="")) {
            	t.value = optionValue;
            }
        } else {
            s.options[i].selected = false;        
        }
    }
}
/*
function getActionDropdown() {
	var eventType = getElementByIdOrByName("reportTypeField").value;

	if (eventType == 'PHC236') {

		// pdg 2012-04-19
		// added conditional logic
		var rec = document.forms[0].receivSys.value;
		
		if (rec == "N" || rec == "") {
			document.forms[0].allowTransfer.disabled = false;
			document.forms[0].allowTransfer.value = "";
			document.forms[0].allowTransfer_textbox.disabled = true;
			document.forms[0].allowTransfer_button.disabled = true;
			getElementByIdOrByName('transferRecSys').style.color = "gray";	
			setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);
		}
		
		getElementByIdOrByName("jurDeriveIndCd_textbox").value ="Yes";

		document.forms[0].receivSys.disabled = false;
		document.forms[0].receivSys_textbox.disabled = false;
		document.forms[0].receivSys_button.disabled = false;
		getElementByIdOrByName('receivSystem').style.color = "black";

		document.forms[0].jurDeriveIndCd.disabled = false;
		document.forms[0].jurDeriveIndCd_textbox.disabled = false;
		document.forms[0].jurDeriveIndCd_button.disabled = false;
		getElementByIdOrByName('jurDerive').style.color = "black";
		
		getElementByIdOrByName('systemNMreq').style.display = "";
		getElementByIdOrByName('systemOidreq').style.display = "";
		
		getElementByIdOrByName('systemOwnerreq').style.display = "";
		getElementByIdOrByName('systemOwnerOIdreq').style.display = "";
		

	} else if (eventType == '11648804') {
		document.forms[0].allowTransfer.disabled = false;
		document.forms[0].allowTransfer_textbox.disabled = true;
		document.forms[0].allowTransfer_button.disabled = true;
		getElementByIdOrByName('transferRecSys').style.color = "gray";
		getElementByIdOrByName("allowTransfer_textbox").value ="";		
		setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);	
		
		setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);

		document.forms[0].receivSys.disabled = false;
		document.forms[0].receivSys_textbox.disabled = true;
		document.forms[0].receivSys_button.disabled = true;
		getElementByIdOrByName('receivSystem').style.color = "gray";
		setSelectedIndex(document.forms[0].receivSys, "", document.forms[0].receivSys_textbox);				

		document.forms[0].jurDeriveIndCd.disabled = false;
		document.forms[0].jurDeriveIndCd_textbox.disabled = true;
		document.forms[0].jurDeriveIndCd_button.disabled = true;
		getElementByIdOrByName('jurDerive').style.color = "gray";
		getElementByIdOrByName("jurDeriveIndCd_textbox").value ="";	
				
		setSelectedIndex(document.forms[0].jurDeriveIndCd, "", document.forms[0].jurDeriveIndCd_textbox);
		
		getElementByIdOrByName('systemNMreq').style.display = "none";
		getElementByIdOrByName('systemOidreq').style.display = "none";
		
		getElementByIdOrByName('systemOwnerreq').style.display = "";
		getElementByIdOrByName('systemOwnerOIdreq').style.display = "";

	}
}
*/


/**
 * triggerCodesReqFlds:
 */

function triggerCodesReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var codingSystemId = getElementByIdOrByName("codingSystem");
    var codeId = getElementByIdOrByName("code");
    var displayNameId = getElementByIdOrByName("displayName");
    var defaultConditionId = getElementByIdOrByName("defaultCondition");
    
	
    if((codingSystemId != null && codingSystemId.value.length == 0) && (codeId != null && codeId.value.length == 0)
    		&& (displayNameId != null && displayNameId.value.length == 0) && (defaultConditionId != null && defaultConditionId.value.length == 0)) {
        errors[index++] = "At least one search criteria is required to submit a search";
        getElementByIdOrByName("reqCodingSystem").style.color="990000";
        getElementByIdOrByName("reqCode").style.color="990000";
        getElementByIdOrByName("reqDisplayName").style.color="990000";
        getElementByIdOrByName("reqDefaultCondition").style.color="990000";
        
        isError = true;		
    }
    else {
    	removeReqColorTriggerCodes();
    }
    
	if(isError) {
        displayGlobalErrorMessage(errors);    
    }
    
    return isError;	
}

/**
 * removeReqColorTriggerCodes:
 */

function removeReqColorTriggerCodes(){
	
	getElementByIdOrByName("reqCodingSystem").style.color="black";
    getElementByIdOrByName("reqCode").style.color="black";
    getElementByIdOrByName("reqDisplayName").style.color="black";
    getElementByIdOrByName("reqDefaultCondition").style.color="black";

}

/**
 * triggerCodesClearData:
 */

function triggerCodesClearData()
{
	removeReqColorTriggerCodes();
	
	if(document.getElementById("addNew")!=null && document.getElementById("addNew")!=undefined)
		document.getElementById("addNew").hide();
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var codingSystemId = getElementByIdOrByName("codingSystem");
    var codeId = getElementByIdOrByName("code");
    var displayNameId = getElementByIdOrByName("displayName");
    var defaultConditionId = getElementByIdOrByName("defaultCondition");
    
    getElementByIdOrByName("codingSystem_textbox").value="";
    getElementByIdOrByName("codingSystem").selectedIndex="0";
    getElementByIdOrByName("code").value="";
    getElementByIdOrByName("srchCriteria").value="Contains";
    getElementByIdOrByName("srchCriteria").selectedIndex="0";
    getElementByIdOrByName("displayName").value="";
    getElementByIdOrByName("defaultCondition_textbox").value="";
    getElementByIdOrByName("defaultCondition").selectedIndex="0";
    
    return isError;	
}


function functionalityNotImpleted(){
	
	alert("Functionality not implemented yet!");
}

/**
 * viewTriggerCode: this method shows the trigger code view at the bottom
 * @param nbsUid
 */
function viewTriggerCode(nbsUid)
{
		document.forms[0].action ="/nbs/TriggerCodes.do?method=viewTriggerCode&"+nbsUid+"#"+"expAlg";
		document.forms[0].submit();
}
/**
* disableElementsTriggerCodes:
*/

function disableElementsTriggerCodes(){

	document.getElementsByClassName("grayButtonBar")[0].setAttribute("disabled","");
	if(document.getElementsByClassName("grayButtonBar")[1]!=null && document.getElementsByClassName("grayButtonBar")[1]!=undefined)
		document.getElementsByClassName("grayButtonBar")[1].setAttribute("disabled","");
	//document.getElementsByClassName("grayButtonBar")[2].setAttribute("disabled","");
	getElementByIdOrByName("codingSystem_button").setAttribute("disabled","");//img
	getElementByIdOrByName("defaultCondition_button").setAttribute("disabled","");//img
	if(document.getElementById("addNewCode2")!=null && document.getElementById("addNewCode2")!=undefined)
		document.getElementById("addNewCode2").setAttribute("disabled","");
	document.getElementById("manageLink").setAttribute("disabled","");//Return link
	document.getElementById("manageLink").setAttribute("style","cursor: none; pointer-events: none;color:#737373");//Return link
	if(document.getElementById("removeFilters")!=null && document.getElementById("removeFilters") != undefined){
		document.getElementById("removeFilters").setAttribute("disabled","");
		document.getElementById("removeFilters").setAttribute("style","cursor: none; pointer-events: none;");//Return link
	}
	document.getElementById("subsec1").setAttribute("disabled","");//Disable search table
	//disable icons in table:
	
	
	var length;
	
	if(document.getElementsByClassName("dtTable")[0]!=null && document.getElementsByClassName("dtTable")[0]!=undefined){
		length = document.getElementsByClassName("dtTable")[0].getElementsByTagName("img").length;
	
		var elements = document.getElementsByClassName("dtTable")[0].getElementsByTagName("img");

		var disabledEditImg = "page_white_edit_disabled.gif";
		var disabledViewImg = "page_white_text_disabled.gif";
							
		for(var i=0; i<length; i++){
					var srcimg = elements[i].getAttribute("src");
			if (srcimg.endsWith("page_white_text.gif")) {
				elements[i].setAttribute("src", disabledViewImg);
				elements[i].setAttribute("alt", "View disabled");
				elements[i].setAttribute("title", "View disabled");
				
				
			} else 
			if (srcimg.endsWith("page_white_edit.gif")){
				elements[i].setAttribute("src", disabledEditImg);
				elements[i].setAttribute("alt", "Edit disabled");
				elements[i].setAttribute("title", "Edit disabled");
				
				
			}	
	
			elements[i].setAttribute("disabled","");
			elements[i].setAttribute("style","cursor: none; pointer-events: none;");
	
		}
	}
	
	var length;
	
	if(document.getElementById("result")!=null){
		
		length = document.getElementById("result").getElementsByTagName("a").length;
		var elements = document.getElementById("result").getElementsByTagName("a");
	
		for(var i=0; i<length; i++){
			elements[i].setAttribute("disabled","");
			elements[i].setAttribute("style","cursor: none; pointer-events: none;");
		}
	}
}
function disableEnablePrintDownload(){
	
		if(getElementByIdOrByName("Print")!=null && getElementByIdOrByName("Print")!=undefined)
		getElementByIdOrByName("Print").disabled = true;
		
		if(getElementByIdOrByName("Print2")!=null && getElementByIdOrByName("Print2")!=undefined)
			getElementByIdOrByName("Print2").disabled = true;
		
		if(getElementByIdOrByName("Download")!=null && getElementByIdOrByName("Download")!=undefined)
			getElementByIdOrByName("Download").disabled = true;
		
		if(getElementByIdOrByName("Download2")!=null && getElementByIdOrByName("Download2")!=undefined)
			getElementByIdOrByName("Download2").disabled = true;
	
}

/**
 * setDropdowns: method that set the value selected in the dropdowns
 * 
 */
function setDropdowns(){
	
	//Coding System
	
	var codingSystem = document.getElementById("codingSystem");
	
	if(codingSystem!=null && codingSystem!=undefined){
		
		var selected = codingSystem.selectedIndex;
		
		if(selected>=0){
			var text = codingSystem.options[selected].textContent;
			getElementByIdOrByName("codingSystem_textbox").value=text;
		}
	}
	
	//Coding System 2
	
	codingSystem = document.getElementById("codingSystem2");
	
	if(codingSystem!=null && codingSystem!=undefined){
		
		var selected = codingSystem.selectedIndex;
		
		if(selected>=0){
			var text = codingSystem.options[selected].textContent;
			getElementByIdOrByName("codingSystem2_textbox").value=text;
		}
	}
	
	
	//Default Condition in search
	
	var defaultCondition = document.getElementById("defaultCondition");
	if(defaultCondition!=null && defaultCondition!=undefined){
		
		var selected = defaultCondition.selectedIndex;
		
		if(selected>=0){
			var text = defaultCondition.options[selected].textContent;
			getElementByIdOrByName("defaultCondition_textbox").value=text;
		}
	}
	
	//Default Condition in edit
	
	var defaultCondition2 = document.getElementById("defaultCondition2");
	if(defaultCondition2!=null && defaultCondition2!=undefined){
		
		var selected = defaultCondition2.selectedIndex;
		
		if(selected>=0){
			var text = defaultCondition2.options[selected].textContent;
			getElementByIdOrByName("defaultCondition2_textbox").value=text;
		}
	}
	
	//Display Name in search
	
	var displayName = document.getElementById("srchCriteria");
	if(displayName!=null && displayName!=undefined){
		
		var selected = displayName.selectedIndex;
		
		if(selected==-1)
			selected=0;
		
		if(selected>=0){
			var text = displayName.options[selected].textContent;
			getElementByIdOrByName("srchCriteria_textbox").value=text;
		}
	}
	
}


function requiredFields(){
	
	var codingSystem = "";
	
	if(getElementByIdOrByName("codingSystem2_textbox")!=null && getElementByIdOrByName("codingSystem2_textbox")!=undefined)
		codingSystem = getElementByIdOrByName("codingSystem2_textbox").value.trim();
	else
		codingSystem="edit";
	
	var code = "";
	
	if(getElementByIdOrByName("codeColumn2")!=undefined && getElementByIdOrByName("codeColumn2")!=null)
		code=getElementByIdOrByName("codeColumn2").value.trim();
	else
		code="edit";
	
	var displayName = getElementByIdOrByName("displayNm2").value.trim();
	//var defaultCondition = getElementByIdOrByName("defaultCondition2_textbox").value;
	var version = getElementByIdOrByName("versionId").value.trim();
	var errText = "";
	var buff="";
	var validationError=false;
	
	
	if(codingSystem==""){
		errText="Coding System is required";
		buff="<ul><li>"+errText+"</li></ul>";
	}
	if(code==""){
		errText="Code is required";
		buff+="<ul><li>"+errText+"</li></ul>";
	}
	if(displayName==""){
		errText="Display Name is required";
		buff+="<ul><li>"+errText+"</li></ul>";
	}
	if(version==""){
		errText="Version ID is required";
		buff+="<ul><li>"+errText+"</li></ul>";
	}

	document.getElementById("requiredFieldsErrors2").setAttribute("style","display:none")
	
	if(errText=="")
		document.getElementById("requiredFieldsErrors").setAttribute("style","display:none")
	else{
		document.getElementById("requiredFieldsErrors").setAttribute("style","display:")
		validationError=true;
	}
	
	buff=buff.replace('/&lt;','<').replace('/&gt;','>');
	$j("#errorText").html("");
	$j("#errorText").append(buff);
	
	return validationError;
}

