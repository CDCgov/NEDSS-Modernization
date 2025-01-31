function ldfsOnLoad() {
	startCountdown();
	autocompTxtValuesForJSP();

	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	if(actionMode == 'Create' || actionMode == 'Edit') {
		var searchFldSet = getElementByIdOrByName("result");
		var input = searchFldSet.getElementsByTagName("input");
		for(var i = 0; i < input.length; i++)	{
			if(input[i].getAttribute("type") == 'button' || input[i].getAttribute("type") == 'submit') {

				input[i].setAttribute("disabled","true");
			}				
		}
		var select = searchFldSet.getElementsByTagName("select");
		for(var i = 0; i < select.length; i++)	{
			select[i].setAttribute("disabled","true");
		}
		var href = searchFldSet.getElementsByTagName("a");
		for(var i = 0; i < href.length; i++)	{
			href[i].setAttribute("disabled","true");
			href[i].removeAttribute("href");
			href[i].setAttribute("onclick","#");

		}	
		var href = getElementByIdOrByName("ldfLink").getElementsByTagName("a");
		for(var i = 0; i < href.length; i++)	{
			href[i].setAttribute("disabled","true");
			href[i].removeAttribute("href");
		}
		var imgs = searchFldSet.getElementsByTagName("img");
		for(var i = 0; i < imgs.length; i++)	{
			imgs[i].setAttribute("disabled","true");
		}											
		//Remove links for Preview buttons on top
		var preview1 = getElementByIdOrByName("preview1");		 	
		preview1.setAttribute("onclick","#");
		var preview2 = getElementByIdOrByName("preview2");		 	
		preview2.setAttribute("onclick","#");
		
	}
	
}
function deleteLocalField(element, url) {
	var confirmMsg="If you continue with the Delete action, you will delete the information. Select OK to continue, or Cancel to not continue.";
	if (confirm(confirmMsg)) {
		element.href =url;
	}
}

function LDFReqFlds() 
{
    hideGlobalErrorMessage();
        
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var labl = getElementByIdOrByName("label");
    var typeCd = getElementByIdOrByName("typeCd");
    var codeset = getElementByIdOrByName("codeset");
    var tabId = getElementByIdOrByName("tabId");
    var sectionId = getElementByIdOrByName("sectionId");
    var subSectionId = getElementByIdOrByName("subSectionId");
    var displayOrder = getElementByIdOrByName("orderNbr");
    var dmColumn = getElementByIdOrByName("datamartColumnNm");     
    var displayWidth =  getElementByIdOrByName("fieldSize");
    var linkurl = getElementByIdOrByName("linkUrl"); 
    
    // Type
    if( typeCd != null && typeCd.value.length == 0)  {
        getElementByIdOrByName("r-typeCd").style.color="990000";
        errors[index++] = "Type is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-typeCd").style.color="black";
    }
    
    // Label 
    if( labl != null && labl.value.length == 0)  {
        getElementByIdOrByName("r-label").style.color="990000";
        errors[index++] = "Label is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-label").style.color="black";
    }
    
    // TabId
    if( tabId != null && tabId.value.length == 0)  {
        if((typeCd != null && typeCd.value != '1010'  && typeCd.value.length > 0)) {
            getElementByIdOrByName("r-tabId").style.color="990000";
            errors[index++] = "Tab is required";
            isError = true;
        } else if(typeCd == null) {
	       	if(getElementByIdOrByName("trTab").className == "") {
	            getElementByIdOrByName("r-tabId").style.color="990000";
	            errors[index++] = "Tab is required";
	            isError = true;
	       	}
        	
        }
    }
    else {
        getElementByIdOrByName("r-tabId").style.color="black";
    }
    
    // Section
    if( sectionId != null && sectionId.value.length == 0)  {
        if(typeCd == null || (typeCd != null && typeCd.value != '1010')) {
            if(sectionId.options.length > 1) {
                getElementByIdOrByName("r-sectionId").style.color="990000";
                errors[index++] = "Section is required";
                isError = true;     
            }
        }
    }
    else {
        getElementByIdOrByName("r-sectionId").style.color="black";
    }
    
    
    // Subsection
    if( subSectionId != null && subSectionId.value.length == 0)  {
        if(typeCd == null || (typeCd != null && typeCd.value != '1010')) {
            if(subSectionId.options.length > 1) {
                getElementByIdOrByName("r-subSectionId").style.color="990000";
                errors[index++] = "Subsection is required";
                isError = true; 
            }
        }
    }
    else {
        getElementByIdOrByName("r-subSectionId").style.color="black";
    }
    
    // Display Order
    if( displayOrder != null && displayOrder.value.length == 0)  {
        if(typeCd == null || (typeCd != null && typeCd.value != '1010' && typeCd.value.length > 0)) {
            getElementByIdOrByName("r-displayOrder").style.color="990000";
            errors[index++] = "Display Order is required";    
            isError = true;     
        }
    }
    else {
        getElementByIdOrByName("r-displayOrder").style.color="black";
    }
    
    // Datamart Column
    if(dmColumn != null && dmColumn.value.length == 0) {
        if(typeCd != null && (typeCd.value == '1007' || typeCd.value == '1008' || typeCd.value == '1009' || typeCd.value == '1013')) {
            getElementByIdOrByName("r-datamartColumnNm").style.color="990000";
            errors[index++] = "Datamart Column Name is required";
            isError = true;     
        }
    }
    else {
        getElementByIdOrByName("r-datamartColumnNm").style.color="black";
    }
    
    // Display Width
    if(displayWidth != null && (displayWidth.value.length > 0) && (displayWidth.value < 2 || displayWidth.value > 35) ) {
        errors[index++] = "Display Width should be a numeric entry between 2 - 35 characters";
        getElementByIdOrByName("r-fieldSize").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("r-fieldSize").style.color="black";
    }
    
    if( typeCd != null && (typeCd.value == '1007' || typeCd.value == '1013') & codeset != null && codeset.value.length == 0)  {
        getElementByIdOrByName("r-codeset").style.color="990000";
        errors[index++] = "SRT Code Set is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-codeset").style.color="black";
    }
    
    if( typeCd != null && typeCd.value == '1003' && (linkurl != null && linkurl.value.length == 0))  {
        getElementByIdOrByName("r-linkurl").style.color="990000";
        errors[index++] = "Link URL is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-linkurl").style.color="black";
    }   
    
    if(isError) {
        displayErrors("localFieldDataFormEntryErrors", errors);
    }
    
    return isError; 
}

function changeType(type) {
	
	
	if(type.value != null && (type.value != '1007' && type.value != '1013')) {
		getElementByIdOrByName("trCodeset").className='none';	
		getElementByIdOrByName("codeset").value='';
		getElementByIdOrByName("codeset_textbox").value='';	
	}
	if(type.value != null && type.value != '1008') {
		getElementByIdOrByName("trDataType").className='none';
		getElementByIdOrByName("dataType").value='';
		getElementByIdOrByName("dataType_textbox").value='';

		getElementByIdOrByName("trFieldSize").className='none';
		getElementByIdOrByName("fieldSize").value='';	
	}
	if(type.value != null && type.value != '1003') {
		getElementByIdOrByName("trLinkurl").className='none';
		getElementByIdOrByName("linkUrl").value='';
		getElementByIdOrByName("trTooltip").className='visible';
	}
	if(type.value != null && type.value == '1003') {
		getElementByIdOrByName("trLinkurl").className='visible';
		getElementByIdOrByName("trTooltip").className='none';
	}
		
	
	
	//Single-Select or Multi-Select			
	if(type.value != null && (type.value == '1007' || type.value == '1013')) {
		getElementByIdOrByName("trCodeset").className='visible';
		getElementByIdOrByName("trTab").className='visible';
		getElementByIdOrByName("trSection").className='visible';
		getElementByIdOrByName("trSubSection").className='visible';
		getElementByIdOrByName("trRequiredInd").className='visible';
		getElementByIdOrByName("trOrderNbr").className='visible';			
		getElementByIdOrByName("trLabel").className='visible';
		getElementByIdOrByName("trDMColumn").className='visible';
		getElementByIdOrByName("trTooltip").className='visible';
		
		//User entered text, number, or date, show the validation type	
	} else if(type.value != null && type.value == '1008') {
		getElementByIdOrByName("trDataType").className='visible';
		getElementByIdOrByName("trTab").className='visible';
		getElementByIdOrByName("trSection").className='visible';	
		getElementByIdOrByName("trSubSection").className='visible';									
		getElementByIdOrByName("trRequiredInd").className='visible';
		getElementByIdOrByName("trOrderNbr").className='visible';			
		getElementByIdOrByName("trLabel").className='visible';
		getElementByIdOrByName("trDMColumn").className='visible';
		getElementByIdOrByName("trTooltip").className='visible';
		getElementByIdOrByName("trFieldSize").className='visible';
					
	} else if(type.value != null && type.value == '1010') {

		getElementByIdOrByName("trSection").className='none';
		getElementByIdOrByName("sectionId").value='';
		getElementByIdOrByName("sectionId_textbox").value='';

		getElementByIdOrByName("trSubSection").className='none';
		getElementByIdOrByName("subSectionId").value='';
		getElementByIdOrByName("subSectionId_textbox").value='';

		getElementByIdOrByName("trTab").className='none';			
		getElementByIdOrByName("tabId").value='';
		getElementByIdOrByName("tabId_textbox").value='';	

		getElementByIdOrByName("trDMColumn").className='none';
		getElementByIdOrByName("datamartColumnNm").value='';

		getElementByIdOrByName("trOrderNbr").className='none';
		getElementByIdOrByName("orderNbr").value='';	

		getElementByIdOrByName("trTooltip").className='none';
		getElementByIdOrByName("toolTip").value='';		

		getElementByIdOrByName("trDataType").className='none';
		getElementByIdOrByName("dataType").value='';
		
		getElementByIdOrByName("trRequiredInd").className='none';
		getElementByIdOrByName("selection.uiMetadata.requiredInd").checked=false;

		//do nothing
	} else {
		getElementByIdOrByName("trTab").className='visible';
		getElementByIdOrByName("trSection").className='visible';
		getElementByIdOrByName("trSubSection").className='visible';		
		getElementByIdOrByName("trRequiredInd").className='visible';
		getElementByIdOrByName("trOrderNbr").className='visible';
		getElementByIdOrByName("trLabel").className='visible';
		if(type.value != null && type.value == '1009') {
			getElementByIdOrByName("trDMColumn").className='visible';
			getElementByIdOrByName("selection.uiMetadata.requiredInd").className='visible';			
		} else {
			getElementByIdOrByName("trDMColumn").className='none';
			getElementByIdOrByName("datamartColumnNm").value='';		
			
			getElementByIdOrByName("trRequiredInd").className='none';
			getElementByIdOrByName("selection.uiMetadata.requiredInd").checked=false;
			
		}
	}
isAllowFutureDate(null);
}

function isNumeric(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(isInteger(c)) s += c;
         pTextbox.value = s;
    }
}

function nospaces(pTextbox)
{
    var varVal = pTextbox.value;
    var y = 0; var s = ""; var c = "";
    y = varVal.length;
    for(x=0; x<y; x++) {
        c = varVal.substr(x, 1);
        if(rdbColValidation(c)) s += c;        
         pTextbox.value = s;
    }
}

function rdbColValidation(pString)
{
    var varPattern = /[^a-zA-Z0-9_]+/;
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
        return true;
    return false;
}

function isAllowFutureDate(pString)
{
	 if(pString != null && pString.value == 'Date')
	 {
		 getElementByIdOrByName("trFutureDate").className='visible';
	 }
	 else
		 getElementByIdOrByName("trFutureDate").className='none'; 
		 
}

/* Used for HomePage LDFs*/
function LDFReqFlds1() 
{
    hideGlobalErrorMessage();
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var labl = getElementByIdOrByName("label");
    var typeCd = getElementByIdOrByName("typeCd");
    var displayOrder = getElementByIdOrByName("orderNbr");
    var linkurl = getElementByIdOrByName("linkUrl"); 
    // Type
    if( typeCd != null && typeCd.value.length == 0)  {
        getElementByIdOrByName("r-typeCd").style.color="990000";
        errors[index++] = "Type is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-typeCd").style.color="black";
    }
    
    // Label 
    if( labl != null && labl.value.length == 0)  {
        getElementByIdOrByName("r-label").style.color="990000";
        errors[index++] = "Label is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-label").style.color="black";
    }
    // Display Order
    if( displayOrder != null && displayOrder.value.length == 0)  {
        if(typeCd == null || (typeCd != null && typeCd.value != '1010' && typeCd.value.length > 0)) {
            getElementByIdOrByName("r-displayOrder").style.color="990000";
            errors[index++] = "Display Order is required";    
            isError = true;     
        }
    }
    else {
        getElementByIdOrByName("r-displayOrder").style.color="black";
    }
    if( typeCd != null && typeCd.value == '1003' && (linkurl != null && linkurl.value.length == 0))  {
        getElementByIdOrByName("r-linkurl").style.color="990000";
        errors[index++] = "Link URL is required";
        isError = true;     
    }
    else {
        getElementByIdOrByName("r-linkurl").style.color="black";
    }   
    
    if(isError) {
        displayErrors("localFieldDataFormEntryErrors", errors);
    }
    
    return isError; 
}
function changeType1(type) {
	
	if(type.value != null && type.value != '1003') {
		getElementByIdOrByName("trLinkurl").className='none';
		getElementByIdOrByName("linkUrl").value='';
		getElementByIdOrByName("trTooltip").className='visible';
	}
	if(type.value != null && type.value == '1003') {
		getElementByIdOrByName("trLinkurl").className='visible';
		getElementByIdOrByName("trTooltip").className='none';
	}
	getElementByIdOrByName("trOrderNbr").className='visible';
	getElementByIdOrByName("trLabel").className='visible';
}
