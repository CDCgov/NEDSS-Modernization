//Fix NBS Context issues by disabling Backspace(8) and Enter(13) for appropriate PAM elements
if (typeof window.event != 'undefined')
  document.onkeydown = function()
    {
		var t=event.srcElement.type;
		if(t == '' || t == 'undefined') {
			return;
		}
		var kc=event.keyCode;
		return ((kc != 8 && kc != 13) || ( t == 'text' &&  kc != 13 ) ||
				(t == 'textarea') || ( t == 'submit' &&  kc == 13) || ( t == 'image' &&  kc == 13));
		
		return preventF12(event);
    }

function parent_disable() {
	if(newwindow && !newwindow.closed)
		newwindow.focus();
	if(newwindow && newwindow.closed)
		getElementByIdOrByName("blockparent").style.display = "none";				
}

function labTestLoincLinkSearchReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    var labTestCd = getElementByIdOrByName("labTest");
    	
    if( labTestCd != null && labTestCd.innerHTML.length == 0) {
        errors[index++] = "Lab Test Code is required";
        getElementByIdOrByName("labTst").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("labTst").style.color="black";
    }
    
    if(isError) {
        displayGlobalErrorMessage(errors);
    }
    
    return isError;
}

function LDLabTestSearchReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    var labTestCd = getElementByIdOrByName("srchLabTest");
    var labTestDesc = getElementByIdOrByName("srchLabTestDesc");
    var labId = getElementByIdOrByName("srchLabId");
    var testType = getElementByIdOrByName("testType");

    if( (labTestCd != null && labTestCd.value.length == 0) && (labTestDesc != null && labTestDesc.value.length == 0) ) {
        errors[index++] = "Lab Test Code or Description is required";
        getElementByIdOrByName("labCode").style.color="990000";
        getElementByIdOrByName("labDes").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("labCode").style.color="black";
        getElementByIdOrByName("labDes").style.color="black";
    }
    
    if( labId != null && labId.value.length == 0) {
        errors[index++] = "Lab Name is required";
        getElementByIdOrByName("lab").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("lab").style.color="black";
    }
    
    if( testType != null && testType.value.length == 0) {
        errors[index++] = "Test Type is required";
        getElementByIdOrByName("test").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("test").style.color="black";
    }
    
    if(isError) {
        displayGlobalErrorMessage(errors);
    }
    return isError;	
}

function LDLabResultSearchReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var labTestCd = getElementByIdOrByName("srchLabResult");
    var labTestDesc = getElementByIdOrByName("srchLabResultDesc");
    var labId = getElementByIdOrByName("srchLabId");
	
    if((labTestCd != null && labTestCd.value.length == 0) && (labTestDesc != null && labTestDesc.value.length == 0) ) {
        errors[index++] = "Lab Result Code or Lab Result Description is required";
        getElementByIdOrByName("resCode").style.color="990000";
        getElementByIdOrByName("resDesc").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("resCode").style.color="black";
        getElementByIdOrByName("resDesc").style.color="black";
    }
    
    if(labId != null && labId.value.length == 0) {
        errors[index++] = "Lab Name is required";
        getElementByIdOrByName("nameLab").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("nameLab").style.color="black";
    }
	
	if(isError) {
        displayGlobalErrorMessage(errors);    
    }
    
    return isError;	
}
	

function LabTestLoincLinkCreateReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index= 0;
	var isError = false;
	
	var labTestCd = getElementByIdOrByName("loinc");
	if(labTestCd != null && labTestCd.innerHTML.length == 0) {
		errors[index++] = "LOINC Code is required";
		getElementByIdOrByName("lCode").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("lCode").style.color="black";
	}

	if(isError) {
		displayErrors("srtDataFormEntryErrors", errors);
	}
	return isError;	
}	

function LabResultSnomedLinkCreateReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var labResultCd = getElementByIdOrByName("labResult");
    var labId = getElementByIdOrByName("labId");
	
    if( labResultCd != null && labResultCd.innerHTML.length == 0) {
        errors[index++] = "Lab Result Code is required";
        getElementByIdOrByName("resCd").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("resCd").style.color="black";
    }
	
    if (isError) {
        displayGlobalErrorMessage(errors);
    }

    return isError;	
}	

	
function LDLabTestReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var labTestCd = getElementByIdOrByName("selection.labTestCd");
    var labTestDesc = getElementByIdOrByName("selection.labTestDescTxt");	
    if( labTestCd != null && labTestCd.value.length == 0) {
        errors[index++] = "Lab Test Code is required";
        getElementByIdOrByName("labTest").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("labTest").style.color="black";
    }
    
    if( labTestDesc != null && labTestDesc.value.length == 0) {
        errors[index++] = "Lab Test Description is required";
        getElementByIdOrByName("desc").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("desc").style.color="black";
    }
    
    if(isError) {
        displayErrors("srtDataFormEntryErrors", errors);
    }
    return isError;	
}

function LDLabResultReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var isError = false;
    var index = 0;
    
    var labResultCd = getElementByIdOrByName("selection.labResultCd");
    var labResultDesc = getElementByIdOrByName("selection.labResultDescTxt");

    if(labResultCd != null && labResultCd.value.length == 0) {
        errors[index++] = "Lab Result Code is required";
        getElementByIdOrByName("resCd").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("resCd").style.color="black";
    }
    
    if(labResultDesc != null && labResultDesc.value.length == 0) {
        errors[index++] = "Lab Result Description is required";
        getElementByIdOrByName("resDes").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("resDes").style.color="black";
    }

	if (isError) {
        displayErrors("srtDataFormEntryErrors", errors);
    }

    return isError;	
}

var newwindow = null;
function searchLoinc()
{	
    $j(".infoBox").hide();
        	
    var urlToOpen = "/nbs/LabTestLoincLink.do?method=loincSearchLoad";	
    var params="left=100, top=50, width=800, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
    var block = getElementByIdOrByName("blockparent");
    block.style.display = "block";				
    newwindow = window.open(urlToOpen,'LoincSearch', params);
    newwindow.focus();
    block.style.display = "none";	
}

function searchSnomed()
{
    $j(".infoBox").hide();
    
    var urlToOpen = "/nbs/ExistingResultsSnomedLink.do?method=SnomedSearchLoad";	
    var params="left=100, top=50, width=800, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
    var rvct = getElementByIdOrByName("blockparent");
    rvct.style.display = "block";				
    newwindow = window.open(urlToOpen,'SnomedSearch', params);
    newwindow.focus();
    rvct.style.display = "none";	
}

function CodeValGenReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;

    var code= getElementByIdOrByName("selection.code");
    var sDesc= getElementByIdOrByName("selection.codeShortDescTxt");
    var desc= getElementByIdOrByName("selection.codeDescTxt");
    var assignAuth=getElementByIdOrByName("selection.assigningAuthorityDescTxt");
    var codesys=getElementByIdOrByName("csa");	

	if( code != null && code.value.length == 0) {
		errors[index++] =  "Code is required";
		getElementByIdOrByName("cod").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("cod").style.color="black";
	}
	
	if( desc != null && desc.value.length == 0) {
        errors[index++] = "Code Description is required";
        getElementByIdOrByName("descCod").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("descCod").style.color="black";
    }
	
	if( sDesc != null && sDesc.value.length == 0) {
		errors[index++] = "Code Short Description is required";
		getElementByIdOrByName("srtDesCod").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("srtDesCod").style.color="black";
	}
	
	/*if( assignAuth != null && assignAuth.value.length == 0) {
        errors[index++] = "Assigning Authority Description is required";
        getElementByIdOrByName("reqaa").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("reqaa").style.color="black";
	}

	if( codesys != null && codesys.value.length == 0) {
        errors[index++] = "Coding System Description is required";
        getElementByIdOrByName("reqcs").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("reqcs").style.color="black";
	}*/
	
	if(isError) {
		displayErrors("srtDataFormEntryErrors", errors);
	}
	return isError;	
}

function searchLabTest()
{		
    $j(".infoBox").hide();
    
    var urlToOpen = "/nbs/LabTestLoincLink.do?method=labTestSearchLoad";	
    var params="left=100, top=50, width=800, height=450, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
    var block = getElementByIdOrByName("blockparent");
    block.style.display = "block";			
    newwindow = window.open(urlToOpen,'LoincSearch', params);
    newwindow.focus();
    block.style.display = "none";
}

function searchLabResultCd() 
{	
    $j(".infoBox").hide();
    
    var urlToOpen = "/nbs/ExistingResultsSnomedLink.do?method=LabResultCdSearchLoad";	
    var params="left=100, top=50, width=800, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
    var rvct = getElementByIdOrByName("blockparent");
    rvct.style.display = "block";				
    newwindow = window.open(urlToOpen,'LabResultSearch', params);
    newwindow.focus();
    rvct.style.display = "none";
}

function LabResultSnomedReqFlds() 
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;
	
	var labSnomedCd = getElementByIdOrByName("snomed");
		
	if(labSnomedCd != null && labSnomedCd.innerHTML.length == 0) {
		errors[index++] = "SNOMED Code is required";
		getElementByIdOrByName("snomCd").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("snomCd").style.color="black";
	}
	
	if (isError) {
		displayErrors("srtDataFormEntryErrors", errors);
	}
	return isError;	
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

function searchLoincCond() {		
		var errorTD = getElementByIdOrByName("error");
		errorTD.setAttribute("className", "none");
		var urlToOpen = "/nbs/LoinctoConditionLink.do?method=loincSearchLoad";	
		var params="left=100, top=50, width=650, height=450, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
		var block = getElementByIdOrByName("blockparent");
		block.style.display = "block";			
		newwindow = window.open(urlToOpen,'LoincSearch', params);
		newwindow.focus();
}

function LoincCondLinkSearchReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var loincCd= getElementByIdOrByName("loinc");
    var conditionCd= getElementByIdOrByName("srchCondition");
    
    if((loincCd.innerHTML.length==0) && (conditionCd!= null && conditionCd.value.length == 0)) {
        errors[index++] = "Loinc Code or Condition is required";
        getElementByIdOrByName("codeLoinc").style.color="990000";
        getElementByIdOrByName("con").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("codeLoinc").style.color="black";
        getElementByIdOrByName("con").style.color="black";
    }
	
    if(isError) {
        displayGlobalErrorMessage(errors);
    }
    
    return isError;	
}


function LoincCondLinkCreateReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;

    var loincCd= getElementByIdOrByName("loinc1");
    var conditionCd= getElementByIdOrByName("condition");
	
    if (loincCd!= null && loincCd.innerHTML.length == 0) {
        errors[index++] = "LOINC Code is required";
        getElementByIdOrByName("loin").style.color="990000";
        isError = true;		
	}
	else {
        getElementByIdOrByName("loin").style.color="black";
	}
	
    if (conditionCd!= null && conditionCd.value.length == 0) {
        errors[index++] = "Condition is required";
        getElementByIdOrByName("cond").style.color="990000";
        isError = true;	
	}
	else {
        getElementByIdOrByName("cond").style.color="black";
	}

    if (isError) {
        displayErrors("srtDataFormEntryErrors", errors);
    }

	return isError;	
}

function snomedSearchReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;
	
	var cd  = getElementByIdOrByName("srchSnomed").value;
	var desc  = getElementByIdOrByName("snomedCd").value;
	
	if(cd.length == 0 && desc.length == 0) {	
		errors[index++] = "SNOMED Code or SNOMED Description is required";
		getElementByIdOrByName("snomedCode").style.color="990000";
		getElementByIdOrByName("descS").style.color="990000";
		isError = true;
	}
	else {
        getElementByIdOrByName("snomedCode").style.color="black";
        getElementByIdOrByName("descS").style.color="black";
	}
	
	if (isError) {
		displayGlobalErrorMessage(errors);
	}

	return isError;
}
function snomedCreateReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;
	
	var snomedCd = getElementByIdOrByName("selection.snomedCd");
	var sourceConId= getElementByIdOrByName("selection.sourceConceptId");
	var sourceVerId= getElementByIdOrByName("selection.sourceVersionId");
	var snomedDesc= getElementByIdOrByName("selection.snomedDescTx");
	
	if(snomedCd != null && snomedCd.value.length == 0)
	{
		errors[index++] = "SNOMED Code is required";
		getElementByIdOrByName("sCode").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("sCode").style.color="black";
	}
	
	if(snomedDesc != null && snomedDesc.value.length == 0)
    {
        errors[index++] = "SNOMED Description is required";
        getElementByIdOrByName("sDesc").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("sDesc").style.color="black";
    }
	
	if(sourceConId != null && sourceConId.value.length == 0)
	{
		errors[index++] = "Source Concept ID is required";
		getElementByIdOrByName("sConId").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("sConId").style.color="black";
	}
	
	if(sourceVerId != null && sourceVerId.value.length == 0)
	{
		errors[index++] = "Source Version ID is required";
		getElementByIdOrByName("sVerId").style.color="990000";
		isError = true;
	}
	else {
	   getElementByIdOrByName("sConId").style.color="black";
	}
	
	if(isError){
		displayErrors("srtDataFormEntryErrors", errors);
	}

	return isError;
}

function SnomedCondLinkSearchReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var snomedCd= getElementByIdOrByName("snomed");
    var conditionCd= getElementByIdOrByName("srchCondition");

    if((snomedCd.innerHTML.length == 0) && (conditionCd!= null && conditionCd.value.length == 0)) {
        errors[index++] = "SNOMED Code or Condition is required";
        getElementByIdOrByName("codeS").style.color="990000";
        getElementByIdOrByName("con").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("codeS").style.color="black";
        getElementByIdOrByName("con").style.color="black";
    }

    if(isError) {
        displayGlobalErrorMessage(errors);
    }

	return isError;	
}

function LabReqFlds() {
    
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
	var isError = false;

	var labId = getElementByIdOrByName("labId");
	var labDesc = getElementByIdOrByName("labDesc");	
	var cdSysCd = getElementByIdOrByName("cdSysCd");
	var cdSysDesc = getElementByIdOrByName("cdSysDesc");
	var assignAuthCd = getElementByIdOrByName("assignAuthCd");	
	var assignAuthDesc = getElementByIdOrByName("assignAuthDesc");
	var eLab = getElementByIdOrByName("eLab");
	
	if( labId != null && labId.value.length == 0) {
		errors[index++] = "Lab ID is required";
		getElementByIdOrByName("abc").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("abc").style.color="black";
	}
	
	if( labDesc != null && labDesc.value.length == 0) {
		errors[index++] = "Lab Name is required";
		getElementByIdOrByName("labDescen").style.color="990000";
		isError = true;		
	}
	else {
       getElementByIdOrByName("labDescen").style.color="black";
    }
	
	if( cdSysCd != null && cdSysCd.value.length == 0) {
		errors[index++] = "Coding System is required";
		getElementByIdOrByName("cdSysCode").style.color="990000";
		isError = true;		
	}
	else {
       getElementByIdOrByName("cdSysCode").style.color="black";
    }
    
	if( cdSysDesc != null && cdSysDesc.value.length == 0) {
		errors[index++] = "Coding System Description is required";
		getElementByIdOrByName("cdSysDesci").style.color="990000";
		isError = true;		
	}
	else {
       getElementByIdOrByName("cdSysDesci").style.color="black";
    }
    
	if( assignAuthCd != null && assignAuthCd.value.length == 0) {
		errors[index++] = "Assigning Authority is required";
		getElementByIdOrByName("assignAuCD").style.color="990000";
		isError = true;		
	}
	else {
       getElementByIdOrByName("assignAuCD").style.color="black";
    }
    
	if( assignAuthDesc != null && assignAuthDesc.value.length == 0) {
		errors[index++] = "Assigning Authority Description is required";
		getElementByIdOrByName("AssignDes").style.color="990000";
		isError = true;		
	}
	else {
       getElementByIdOrByName("AssignDes").style.color="black";
    }	
	
	if( eLab != null && eLab.value.length == 0) {
        errors[index++] = "Electronic Lab is required";
		getElementByIdOrByName("eLabo").style.color="990000";
		isError = true;		
	}
	else {
       getElementByIdOrByName("eLabo").style.color="black";
    }	
	
	if(isError) {
		displayErrors("srtDataFormEntryErrors", errors);
	}
	return isError;
}	
function SnomedCondLinkCreateReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;

	var snomedCd= getElementByIdOrByName("snomed");
	var conditionCd= getElementByIdOrByName("condition");
	
    if(snomedCd!= null && snomedCd.innerHTML.length == 0) {
        errors[index++] = "SNOMED Code is required";
		getElementByIdOrByName("codeSnow").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("codeSnow").style.color="black";
	}
	
    if(conditionCd!= null && conditionCd.value.length == 0){
        errors[index++] = "Condition is required";
        getElementByIdOrByName("cond").style.color="990000";
		isError = true;	
	}
	else {
	    getElementByIdOrByName("cond").style.color="black";
	}
	
	if(isError) {
	   displayErrors("srtDataFormEntryErrors", errors);	
	}
	
	return isError;	
}

/*function srtOnLoad() {
		
	/*var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	//alert(actionMode);
	if(actionMode ==  "Edit" ||  actionMode == "Create") {
        getElementByIdOrByName("search").className="none";
	   getElementByIdOrByName("result").className="none";
	    getElementByIdOrByName("srtLink").className="none";
	}	

}*/

	

	function srtOnLoad(){	
			
			autocompTxtValuesForJSP();
			var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
			if(getElementByIdOrByName("actionMode") == 'null'){
				actionMode ="";
			}
			if(StartsWith(actionMode, "Create") || StartsWith(actionMode, "Edit")) {
				var fieldset=new Array(); 
				fieldset[0] = "search";
                fieldset[1] = "result";
				fieldset[2] = "srtLink";
                var count = 0;
				while(count < 3) {
					        if( getElementByIdOrByName(fieldset[count]) != null) {
											var searchFldSet = getElementByIdOrByName(fieldset[count]);  
										//	alert(searchFldSet);
											var input = searchFldSet.getElementsByTagName("input");				

											for(var i = 0; i < input.length; i++)	{
												input[i].setAttribute("disabled","true");
											}
											var select = searchFldSet.getElementsByTagName("select");
											for(var i = 0; i < select.length; i++)	{
												select[i].setAttribute("disabled","true");
											}
											var href = searchFldSet.getElementsByTagName("a");
											for(var i = 0; i < href.length; i++)	{
												href[i].setAttribute("disabled","true");
												href[i].setAttribute("style","color:#666666");//links are not greyed out with new browsers
												href[i].removeAttribute("href");
											}	
											var imgs = searchFldSet.getElementsByTagName("img");
											for(var i = 0; i < imgs.length; i++)	{
												imgs[i].setAttribute("disabled","true");
											}											
											
							}	
							count = count +1 ;	
				       }
		
			}
		}

function srtLinkLoad() {

	autocompTxtValuesForJSP();
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	if(actionMode == 'Create' || actionMode == 'Edit') {
		var searchFldSet = getElementByIdOrByName("search");
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
		var href = getElementByIdOrByName("srtLink").getElementsByTagName("a");
		for(var i = 0; i < href.length; i++)	{
			href[i].setAttribute("disabled","true");
			href[i].removeAttribute("href");
		}	
		var imgs = searchFldSet.getElementsByTagName("img");
		for(var i = 0; i < imgs.length; i++)	{
			imgs[i].setAttribute("disabled","true");
		}											
	}	
}

function getCodingSystemCodes(assignAuth)
{
	  getElementByIdOrByName("codingSystem").innerHTML="";
	  JSRTForm.getCodingSystemCodes(assignAuth, function(data) {  	
	  if(data.length == '1') {
		document.forms[0].csa.disabled = true;  	
		document.forms[0].csa_textbox.disabled = true;
		document.forms[0].csa_button.disabled = true;
		document.forms[0].csa_textbox.value = "";
	  } else {
		document.forms[0].csa.disabled = false;  	
		document.forms[0].csa_textbox.disabled = false;  
	  	document.forms[0].csa_button.disabled = false;
	  	var codesysDesc=getElementByIdOrByName("csa");
		document.forms[0].csa_textbox.focus();
	  	
	  }
	  
	  DWRUtil.removeAllOptions("csa");  	
	  DWRUtil.addOptions("csa", data, "key", "value" ); 
	});
}
function disableSRTFlds() {
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	if(actionMode == 'Create') {
	    //document.forms[0].csa.disabled = true;
	    //document.forms[0].csa_textbox.disabled = true;
	    //document.forms[0].csa_button.disabled = true;
	}
	
}
function CodeValGenMetaReqFlds() {

	$j(".infoBox").hide();
	
	var errors = new Array();
	var index = 0;
	var isError = false;
	
	var msg="Please enter required field(s) and try again.";
	var code= getElementByIdOrByName("selection.code");
	var sDesc= getElementByIdOrByName("selection.codeShortDescTxt");
	var desc= getElementByIdOrByName("selection.codeDescTxt");
	var assignAuth=getElementByIdOrByName("selection.assigningAuthorityDescTxt");
	var codesys=getElementByIdOrByName("codeSysCd");
	var codesysDesc=getElementByIdOrByName("codeSystemDescTxt");

	if( code != null && code.value.length == 0) {
		errors[index++] =  "Code is required";
		getElementByIdOrByName("cod").style.color="990000";
		isError = true;		
	} else {
	   getElementByIdOrByName("cod").style.color="black";
	}
	
	if( sDesc != null && sDesc.value.length == 0) {
		errors[index++] = "Code Short Description is required";
		getElementByIdOrByName("srtDesCod").style.color="990000";
		isError = true;		
	}else {
        getElementByIdOrByName("srtDesCod").style.color="black";
    }
    
	if( desc != null && desc.value.length == 0) {
		errors[index++] = "Code Description is required";
		getElementByIdOrByName("descCod").style.color="990000";
		isError = true;		
	}else {
        getElementByIdOrByName("descCod").style.color="black";
    }
	
	if( assignAuth != null && assignAuth.value.length == 0) {
		errors[index++] = "Assigning Authority Description is required";
		getElementByIdOrByName("reqaa").style.color="990000";
		isError = true;		
	}else {
	   getElementByIdOrByName("reqaa") == null ? "" : getElementByIdOrByName("reqaa").style.color="black";
	}
	
	if( codesys != null && codesys.value.length == 0) {
		errors[index++] = "Coding System  is required";
		getElementByIdOrByName("reqcs").style.color="990000";
		isError = true;		
	}else {
	    getElementByIdOrByName("reqcs") == null ? "" : getElementByIdOrByName("reqcs").style.color="black";
	}
	
	if( codesysDesc != null && codesysDesc.value.length == 0) {
		errors[index++] = "Coding System Description is required";	
		getElementByIdOrByName("reqcsd").style.color="990000";
		isError = true;		
	}else {
	   getElementByIdOrByName("reqcsd") == null ? "" : getElementByIdOrByName("reqcsd").style.color="black";
	}
	
	if(isError) {
		displayErrors("srtDataFormEntryErrors", errors);
	}
	return isError;	
}


function codeSetSelectReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var codeSetNm = getElementByIdOrByName("srchCodeSet");
    var codeSetDesc = getElementByIdOrByName("srchCodeSetDes");
	
    if((codeSetNm != null && codeSetNm.value.length == 0) && (codeSetDesc != null && codeSetDesc.value.length == 0) ) {
        errors[index++] = "Code Set Name or Code Set Description is required";
        getElementByIdOrByName("CodeSetNm").style.color="990000";
        getElementByIdOrByName("CodeSetDes").style.color="990000";
        isError = true;		
    }
    else {
        getElementByIdOrByName("CodeSetNm").style.color="black";
        getElementByIdOrByName("CodeSetDes").style.color="black";
    }
    
  
	if(isError) {
        displayGlobalErrorMessage(errors);    
    }
    
    return isError;	
}
	





function CodeSetReqFlds()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;

    var codeSetNm= getElementByIdOrByName("selection.valueSetTypeCd");
    var vadsValSetCd= getElementByIdOrByName("selection.valueSetCode");
    var codeSetSrtDesc=getElementByIdOrByName("selection.valueSetNm");
    var codeSetDesc = getElementByIdOrByName("selection.codeSetDescTxt");
    
	if( codeSetNm != null && codeSetNm.value.length == 0) {
		errors[index++] =  "Value Set Type is required";
		getElementByIdOrByName("ValSt").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("ValSt").style.color="black";
	}
	
	if( vadsValSetCd != null && vadsValSetCd.value.length == 0) {
        errors[index++] = "Value Set Code is required";
        getElementByIdOrByName("ValSC").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("ValSC").style.color="black";
    }
	
	if( codeSetSrtDesc != null && codeSetSrtDesc.value.length == 0) {
        errors[index++] = "Value Set Name is required";
        getElementByIdOrByName("ValSN").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("ValSN").style.color="black";
    }
	if( codeSetDesc != null && codeSetDesc.value.length == 0) {
        errors[index++] = "Value Set Description is required";
        getElementByIdOrByName("ValSD").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("ValSD").style.color="black";
    }
	
	
		
	if(isError) {
		displayErrors("srtDataFormEntryErrors", errors);
	}
	return isError;	
}

function closeImportPopup(){
	self.close();
	var opener = getDialogArgument();		
	var invest = getElementByIdOrByNameNode("valueSet", opener.document)
	invest.style.display = "none";	
	
}

function ConceptCodeReqFlds(mode)
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;
    
    var phinStdConCd= getElementByIdOrByName("codeValGnSelection.conceptTypeCd");
    var code= getElementByIdOrByName("codeValGnSelection.conceptCode");
    var cdDescTxt=getElementByIdOrByName("codeValGnSelection.conceptNm");
    var cdSrtDescTxt = getElementByIdOrByName("codeValGnSelection.conceptPreferredNm");
    
    
    var conceptStatusCd = getElementByIdOrByName("valLSC");
    var effectiveFromTime = getElementByIdOrByName("codeValGnSelection.effectiveFromTime");
    var effectiveToTime = getElementByIdOrByName("codeValGnSelection.effectiveToTime");
    var codeDescTxt = getElementByIdOrByName("codeValGnSelection.codeDescTxt");
    var codeShortDescTxt = getElementByIdOrByName("codeValGnSelection.codeShortDescTxt");
    var localCode = getElementByIdOrByName("codeValGnSelection.code");
    var codeSysName = getElementByIdOrByName("CodeSNF_DD");
    
    var cdSysDescTxt = "";
    // jaya sudha has commented for disable the concept type
//    if(mode != null && mode == 'CREATE'){
//	    if(phinStdConCd.value == 'PHIN'){
//	    	cdSysDescTxt = getElementByIdOrByName("CodeSNF_DD");
//	    }
//	    else if(phinStdConCd.value == 'LOCAL')
//	    	cdSysDescTxt = getElementByIdOrByName("CodeSNF_TEXT");
//	}
//	if( phinStdConCd != null && phinStdConCd.value.length == 0) {
//		errors[index++] =  "Concept Type is required";
//		getElementByIdOrByName("Phin").style.color="990000";
//		isError = true;		
//	}
//	else {
//	   getElementByIdOrByName("Phin").style.color="black";
//	}
	
    
    // jayasudha done the chnages regarding validation on new fields .
    
    
    if( localCode != null && localCode.value.length == 0) {
        errors[index++] = "Local Code is required";
        getElementByIdOrByName("localCode").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("localCode").style.color="black";
    }
    
    if( codeDescTxt != null && codeDescTxt.value.length == 0) {
        errors[index++] = "Long Display Name is required";
        getElementByIdOrByName("longDisplayName").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("longDisplayName").style.color="black";
    }
    
    if( codeShortDescTxt != null && codeShortDescTxt.value.length == 0) {
        errors[index++] = "Short Display Name (UI Display) is required";
        getElementByIdOrByName("shorDisplayName").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("shorDisplayName").style.color="black";
    }
    
   	 if( effectiveToTime != null && effectiveToTime.value.length!=0 && effectiveToTime.value.length<10) {
   		 errors[index++] = "Effective To Time must be in the format of mm/dd/yyyy";
   		 getElementByIdOrByName("efecToTime").style.color="990000";
   	     isError = true;     
   	 }
    
    if( effectiveFromTime != null && effectiveFromTime.value.length == 0) {
        errors[index++] = "Effective From Time is required";
        getElementByIdOrByName("efecFromTime").style.color="990000";
        isError = true;     
    }else
    	 if( effectiveFromTime != null && effectiveFromTime.value.length!=10) {
    		 errors[index++] = "Effective From Time must be in the format of mm/dd/yyyy";
    		 getElementByIdOrByName("efecFromTime").style.color="990000";
    	     isError = true;     
    	 }
    else {
        getElementByIdOrByName("efecFromTime").style.color="black";
    }
    if( effectiveToTime != null && effectiveToTime.value.length != 0) {
    	if(CompareDateStrings(effectiveFromTime.value, effectiveToTime.value) > 0){
    		errors[index++] = "'Effective From Time' must be less than 'Effective To Time'.";
    		getElementByIdOrByName("efecFromTime").style.color="990000";
    		getElementByIdOrByName("efecToTime").style.color="990000";
    		isError = true; 
    	}
		else {
        getElementByIdOrByName("efecFromTime").style.color="black";
        getElementByIdOrByName("efecToTime").style.color="black";
    }
	
    }
   
	
    if( conceptStatusCd.value == "") {
        errors[index++] = "Status Code is required";
        getElementByIdOrByName("statusCode").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("statusCode").style.color="black";
    }
    
    
    /// end jayasudha
    
    
    
	if( code != null && code.value.length == 0) {
        errors[index++] = "Concept Code is required";
        getElementByIdOrByName("Ccode").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("Ccode").style.color="black";
    }
	
	if( cdDescTxt != null && cdDescTxt.value.length == 0) {
        errors[index++] = "Concept Name is required";
        getElementByIdOrByName("Cname").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("Cname").style.color="black";
    }
	if( cdSrtDescTxt != null && cdSrtDescTxt.value.length == 0) {
        errors[index++] = "Preferred Concept Name is required";
        getElementByIdOrByName("PCname").style.color="990000";
        isError = true;     
    }
    else {
        getElementByIdOrByName("PCname").style.color="black";
    }
	
	// jaya sudha commented the code system name is enabled 
	
	
		if( codeSysName != null && codeSysName.value == "") {
	        errors[index++] = "Code System Name is required";
	        getElementByIdOrByName("CSname").style.color="990000";
	        isError = true;     
	    }
	    else {
	        getElementByIdOrByName("CSname").style.color="black";
	    }
//	
		
	if(isError) {
		displayErrors("errorBlock", errors);
	}
	return isError;	
	}
function reloadCodeset(){
 	alert("reload");      
    //setTimeout("reldPage()", 1000);
	document.forms[0].action = "/nbs/ManageCodeSet.do?method=updateCodeValGenCode";
	document.forms[0].submit();
    //alert("done");     
}

function reldPage()
{
	alert("insideChild");
	//document.forms[0].action = "${SRTAdminManageForm.attributeMap.submit}";
}

