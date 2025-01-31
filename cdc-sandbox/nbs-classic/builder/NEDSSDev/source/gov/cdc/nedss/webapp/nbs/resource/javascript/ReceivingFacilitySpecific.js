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
	var allowForTransfer = getElementByIdOrByName("selection.allowTransferIndCd").value;
	var derivedJurisdiction = getElementByIdOrByName("selection.jurDeriveIndCd").value;
	
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
		if (recSysOid != null && recSysOid.value.length == 0) {
			errors[index++] = "Application OID is required";
			getElementByIdOrByName("systemOid").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("systemOid").style.color = "black";
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

		if (receipient != null && receipient.value.length == 0) {
			errors[index++] = "Receiving System is required";
			getElementByIdOrByName("receivSystem").style.color = "990000";
			isError = true;
		} else {
			getElementByIdOrByName("receivSystem").style.color = "black";
		}

		if(getElementByIdOrByName("selection.receivingIndCd").value=="Y")
			if (allowForTransfer != null && allowForTransfer.length == 0) {
			errors[index++] = "Allows for Transfers is required";
			getElementByIdOrByName("transferRecSys").style.color = "990000";
			isError = true;
			}else {
			getElementByIdOrByName("transferRecSys").style.color = "black";
		}
	
		if (derivedJurisdiction != null && derivedJurisdiction.length == 0) {
				errors[index++] = "Use System Derived Jurisdiction is required";
				getElementByIdOrByName("jurDerive").style.color = "990000";
				isError = true;
				}else {
			getElementByIdOrByName("jurDerive").style.color = "black";
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
}
function cancelFilter(key) {
	key1 = key.substring(key.indexOf("(") + 1, key.indexOf(")"));
	JReceivingFacilityForm.getAnswerArray(key1, function(data) {
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

function selectfilterCriteria() {
	document.forms[0].action = '/nbs/ReceivingSystem.do?method=filterReceivingSystemsSubmit';
	document.forms[0].submit();
}

function clearFilter() {

	document.forms[0].action = '/nbs/ReceivingSystem.do?method=manageLoad&initLoad=true';
	document.forms[0].submit();
}


function onKeyUpValidate()
{      	  
       	if(getElementByIdOrByName("SearchText1").value != ""){
     		getElementByIdOrByName("b1SearchText1").disabled=false;
     		getElementByIdOrByName("b2SearchText1").disabled=false;
     	   }else if(getElementByIdOrByName("SearchText1").value == ""){
     		getElementByIdOrByName("b1SearchText1").disabled=true;
     		getElementByIdOrByName("b2SearchText1").disabled=true;
     	   }
     	   
     	if(getElementByIdOrByName("SearchText2").value != ""){
     		getElementByIdOrByName("b1SearchText2").disabled=false;
     		getElementByIdOrByName("b2SearchText2").disabled=false;
     	   }else if(getElementByIdOrByName("SearchText2").value == ""){
     		getElementByIdOrByName("b1SearchText2").disabled=true;
     		getElementByIdOrByName("b2SearchText2").disabled=true;
     	}	   
}

//the following code for Queue
function attachIcons() {
	$j("#parent thead tr th a").each(function(i) {
		
		if($j(this).html() == 'Application Name'){         
            $j(this).parent().append($j("#fApplicationName"));
        }
		if($j(this).html() == 'Display Name'){         
		    $j(this).parent().append($j("#fDisplayName"));
		}

		if ($j(this).html() == 'Facility Name')
			$j(this).parent().append($j("#owner"));
		if ($j(this).html() == 'Sender')
			$j(this).parent().append($j("#sen"));
		if ($j(this).html() == 'Recipient')
			$j(this).parent().append($j("#reci"));
		if ($j(this).html() == 'Transfer')
			$j(this).parent().append($j("#trans"));
		if ($j(this).html() == 'Report Type')
			$j(this).parent().append($j("#reportType"));

	});
	$j("#parent").before($j("#whitebar"));
	$j("#parent").before($j("#removeFilters"));
}

function displayTooltips() {
	$j(".sortable a").each(function(i) {

		var headerNm = $j(this).html();
		
		 var INV001 = getElementByIdOrByName("SearchText1") == null ? "" : getElementByIdOrByName("SearchText1").value;
         var INV002 = getElementByIdOrByName("SearchText2") == null ? "" : getElementByIdOrByName("SearchText2").value;
         
         
         
		if (headerNm == 'Facility Name') {
			_setAttributes(headerNm, $j(this), $j("#INV147"));
		} else if (headerNm == 'Sender') {
			_setAttributes(headerNm, $j(this), $j("#INV100"));
		} else if (headerNm == 'Recipient') {
			_setAttributes(headerNm, $j(this), $j("#INV163"));
		} else if (headerNm == 'Transfer') {
			_setAttributes(headerNm, $j(this), $j("#NOT118"));
		} else if (headerNm == 'Report Type') {
			_setAttributes(headerNm, $j(this), $j("#DEM102"));
		} else if (headerNm == 'Application Name') {
			_setAttributes(headerNm, $j(this), INV001);
		} else if (headerNm == 'Display Name') {
			_setAttributes(headerNm, $j(this), INV002);
		}
	});
}

function _handlePatient(headerNm, link, colId) {
	var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
	var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';

	var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
	if (sortSt != null && sortSt.indexOf(headerNm) != -1) {
		if (sortSt != null && sortSt.indexOf("descending") != -1) {
			link.after(htmlDesc);
		} else {
			link.after(htmlAsc);
		}
	}

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
	//toolTip = colId.html() == null ? "" : colId.html();
	if (colId != null && colId.length > 0) {
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

function disableTransfer() {
	var recSysVal = getElementByIdOrByName("receivSys") == null ? "" : getElementByIdOrByName("receivSys").value;
	if (recSysVal == 'Y') {
		document.forms[0].allowTransfer.disabled = false;
		document.forms[0].allowTransfer_textbox.disabled = false;
		document.forms[0].allowTransfer_button.disabled = false;
		getElementByIdOrByName('transferRecSys').style.color = "black";

	} else {
		document.forms[0].allowTransfer.disabled = true;
		document.forms[0].allowTransfer_textbox.disabled = true;
		document.forms[0].allowTransfer_button.disabled = true;
		getElementByIdOrByName('transferRecSys').style.color = "#666666";
		setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);
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

function disableRecSys() {
	var sendSysVal = getElementByIdOrByName("receivSys") == null ? ""
			: getElementByIdOrByName("receivSys").value;
	if (sendSysVal == 'Y') {
		document.forms[0].receivSys_textbox.disabled = false;
		document.forms[0].receivSys_textbox.disabled = false;
		document.forms[0].receivSys_button.disabled = false;
		getElementByIdOrByName('receivSystem').style.color = "black";

	} else {
		document.forms[0].receivSys.disabled = true;
		document.forms[0].receivSys_textbox.disabled = true;
		document.forms[0].receivSys_button.disabled = true;
		getElementByIdOrByName('receivSystem').style.color = "#666666";
	}
}

function disablejurDerive() {
	document.forms[0].jurDeriveIndCd.disabled = true;
	document.forms[0].jurDeriveIndCd_textbox.disabled = true;
	document.forms[0].jurDeriveIndCd_button.disabled = true;
	getElementByIdOrByName('jurDerive').style.color = "#666666";

}
function recFecOnLoad() {

	autocompTxtValuesForJSP();
	var actionMode = getElementByIdOrByName("actionMode") == null ? ""
			: getElementByIdOrByName("actionMode").value;
	if (getElementByIdOrByName("actionMode") == 'null') {
		actionMode = "";
	}
	if (StartsWith(actionMode, "Create") || StartsWith(actionMode, "Edit")) {
		var fieldset = new Array(); 
		// pdg 2012-04-19 
		// changed from "="
		if (actionMode == "Edit") {
			getActionDropdown();
		}
		fieldset[0] = "result";
		fieldset[1] = "iaeLink";
		var count = 0;
		while (count < 3) {
			if (getElementByIdOrByName(fieldset[count]) != null) {
				var searchFldSet = getElementByIdOrByName(fieldset[count]);
				var input = searchFldSet.getElementsByTagName("input");

				for ( var i = 0; i < input.length; i++) {
					input[i].setAttribute("disabled", "true");
				}
				var select = searchFldSet.getElementsByTagName("select");
				for ( var i = 0; i < select.length; i++) {
					select[i].setAttribute("disabled", "true");
				}
				var href = searchFldSet.getElementsByTagName("a");
				for ( var i = 0; i < href.length; i++) {
					href[i].setAttribute("disabled", "true");
					href[i].removeAttribute("href");
				}
				var imgs = searchFldSet.getElementsByTagName("img");
				for ( var i = 0; i < imgs.length; i++) {
					imgs[i].setAttribute("disabled", "true");
					var cls = imgs[i].getAttribute("id");
					if (cls != null && cls != "queueIcon") {
						var srcimg = imgs[i].getAttribute("src");
						disabledEditImg = "page_white_edit_disabled.gif";
						disabledViewImg = "page_white_text_disabled.gif";
						if (srcimg.endsWith("page_white_text.gif")) {
							imgs[i].setAttribute("src", disabledViewImg);
							imgs[i].setAttribute("alt", "View disabled");
							imgs[i].setAttribute("title", "View disabled");
							
							
						} else {
							imgs[i].setAttribute("src", disabledEditImg);
							imgs[i].setAttribute("alt", "Edit disabled");
							imgs[i].setAttribute("title", "Edit disabled");
							
							
						}

					}

				}

			}
			count = count + 1;
		}

	}
}

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
			getElementByIdOrByName('transferRecSys').style.color = "#666666";	
			setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);
		}

		if(getElementByIdOrByName("jurDeriveIndCd_textbox").value==""){
			getElementByIdOrByName("jurDeriveIndCd_textbox").value ="Yes";
			$j("#jurDeriveIndCd").val("Y");
			}
		
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
		getElementByIdOrByName('transferRecSys').style.color = "#666666";
		getElementByIdOrByName("allowTransfer_textbox").value ="";		
		setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);	
		
		setSelectedIndex(document.forms[0].allowTransfer, "", document.forms[0].allowTransfer_textbox);

		document.forms[0].receivSys.disabled = false;
		document.forms[0].receivSys_textbox.disabled = true;
		document.forms[0].receivSys_button.disabled = true;
		getElementByIdOrByName('receivSystem').style.color = "#666666";
		setSelectedIndex(document.forms[0].receivSys, "", document.forms[0].receivSys_textbox);				

		document.forms[0].jurDeriveIndCd.disabled = false;
		document.forms[0].jurDeriveIndCd_textbox.disabled = true;
		document.forms[0].jurDeriveIndCd_button.disabled = true;
		getElementByIdOrByName('jurDerive').style.color = "#666666";
		getElementByIdOrByName("jurDeriveIndCd_textbox").value ="";	
				
		setSelectedIndex(document.forms[0].jurDeriveIndCd, "", document.forms[0].jurDeriveIndCd_textbox);
		
		getElementByIdOrByName('systemNMreq').style.display = "none";
		getElementByIdOrByName('systemOidreq').style.display = "none";
		
		getElementByIdOrByName('systemOwnerreq').style.display = "";
		getElementByIdOrByName('systemOwnerOIdreq').style.display = "";

	}
}
