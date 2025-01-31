<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN JSP PAGE GENERATE ###- - -->
    <html lang="en">
    <head>
    <title>NBS:Find Patient</title>
    <%@ include file="/jsp/tags.jsp" %>
    <%@ include file="/jsp/resources.jsp" %> 
    <%@ page import="java.util.*" %>
     
        <script language="JavaScript">      
        //   if (typeof window.event != 'undefined')
  document.onkeydown = function(event)
    {
		//var t=event.srcElement.type;
		if(event!=null){
			var t=event.target.type;
			if(t == '' || t == 'undefined' || t == 'button') {
				return;
			}
			var kc=event.keyCode;
			//alert(kc + '  ' +  t);
			if(t == 'text' && kc == 13) {
				searchPatient();
			}		
    	}
		
		return preventF12(event);
    }
	
	function clearForm(){
		
		 var merge = "<%=request.getAttribute("MergePatient")%>";
		   // if(merge!="true"){
	
		//Remove the validation bar
		if(document.getElementById("errorMessages")!=null)
			document.getElementById("errorMessages").remove();
		
		//Remove the red color on each label
		var length = document.getElementsByClassName("InputFieldLabel").length

		for(i=0; i<length;i++){
			document.getElementsByClassName("InputFieldLabel")[i].setAttribute("style","")
		}
		
		//Remove the red border on each dropdown
		var length2 = document.getElementsByTagName("input").length

		for(i=0; i<length2;i++){
			var element = document.getElementsByTagName("input")[i];
			if(element!=null && element.getAttribute("name")!=null && element.getAttribute("name").indexOf("textbox")!=-1)
				element.setAttribute("style","");
		}
		
		if(getElementByIdOrByName("tabs0head0").getAttribute("class")=="ongletTextEna"
			
			||  (merge=="true"))
			{//Patient Search tab active
				
		 
		 //Remove the red color on the tab
		  setErrorTabProperties("");
		  document.getElementById("tabs0head0").setAttribute("class","ongletTextEna");
		
		//Last Name
		getElementByIdOrByName("DEM102O_textbox").value="Starts With";
		document.getElementById("DEM102O").selectedIndex="0";
		document.getElementById("DEM102").value="";
		
		//First Name
		getElementByIdOrByName("DEM104O_textbox").value="Starts With";
		document.getElementById("DEM104O").selectedIndex="0";
		document.getElementById("DEM104").value="";
		
		//Date of Birth
		getElementByIdOrByName("DEM105O_textbox").value="Equal";
		document.getElementById("DEM105O").selectedIndex="1";
		document.getElementById("patientDOB1").value="";
		document.getElementById("patientDOB2").value="";
		document.getElementById("patientDOB3").value="";
		flipDate(document.getElementById("DEM105O"))
		
		//Current Sex
		getElementByIdOrByName("DEM114_textbox").value="";
		document.getElementById("DEM114").selectedIndex="-1";
		
		
		//Street address
		getElementByIdOrByName("DEM106O_textbox").value="Contains";
		document.getElementById("DEM106O").selectedIndex="0";
		document.getElementById("DEM106").value="";
			
		//City	
		getElementByIdOrByName("DEM107O_textbox").value="Contains";
		document.getElementById("DEM107O").selectedIndex="0";
		document.getElementById("DEM107").value="";
		
		//State
		getElementByIdOrByName("DEM108O_textbox").value="";
		document.getElementById("DEM108O").selectedIndex="-1";
		
		//Zip
		document.getElementById("DEM109").value="";
		
		//Parent ID(s)
		document.getElementById("DEM229").value="";
		
		//Id Type
		
		getElementByIdOrByName("DEM222_textbox").value="";
		document.getElementById("DEM222").selectedIndex="-1";
		
		//Id Number
		document.getElementById("DEM223").value="";
		
		//Phone
		document.getElementById("DEM238").value="";
		
		//Email
		document.getElementById("DEM182").value="";
		
		//Ethnicity
		getElementByIdOrByName("DEM221_textbox").value="";
		document.getElementById("DEM221").selectedIndex="-1";
		
		//Race
		getElementByIdOrByName("DEM176_textbox").value="";
		document.getElementById("DEM176").selectedIndex="-1";
		
		//Include records that are
		document.getElementById("recordAct").checked=true;
		document.getElementById("recordDel").checked=false;
		document.getElementById("recordSup").checked=false;
		
		}else{//Event Search tab active
		
		//Remove the red color on the tab
		setErrorTabProperties("");
		document.getElementById("tabs0head1").setAttribute("class","ongletTextEna");
			
		//Event Type
		getElementByIdOrByName("ETYPE_textbox").value="";
		document.getElementById("ETYPE").selectedIndex="-1";
		
		//Unassigned values
		document.getElementById("caseStatusUnassigned").checked=false;
		document.getElementById("notificationStatusUnassigned").checked=false;
		document.getElementById("currentProcessStateUnassigned").checked=false;
		
		//Investigator
		clearProvider('INV210');
		clearValueSelected("investigatorSelected");
		showHideSections();
		}		
		
	}
	
	function setCheckBoxesToTrue(){
		/* var allEnabledSearchIpElts = $j("#patientSearchByEvent").find(':input:enabled');
        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
        	   if ( $j(allEnabledSearchIpElts[i]).attr("type") == 'checkbox'){
        		   $j(allEnabledSearchIpElts[i]).attr("checked",'checked') ;
        	   }
        		   
        } */
     	$j('#ELECTRONIC').attr('checked', true);
		
		$j('#MANUAL').attr('checked', false);
		
		$j('#EXTERNALUSER').attr('checked', true);
		$j('#INTERNALUSER').attr('checked', true);
		$j('#newInitial').attr('checked', true);
		$j('#update').attr('checked', false);
		$j('#processState').attr('checked', false);
		$j('#unProcessState').attr('checked', true);
		$j("#caseStatusUnassigned").attr('checked','');
		$j("#currentProcessStateUnassigned").attr('checked','');
		$j("#notificationStatusUnassigned").attr('checked','');
		
	}
	
	function clearCodeResult()

{

	var resultDescription = getElementByIdOrByName("resultDescription");

	var resultCodeId = getElementByIdOrByName("codeResultId");

	var resultDescriptionId = getElementByIdOrByName("resultDescriptionId");

	resultDescription.innerText= "";

	resultDescription.textContent= "";

	resultCodeId.value = null;

	resultDescriptionId.value = null;

	$j($j("#resultDescription").parent().parent()).find(":input").val("");



	$j("#numericResult_text").parent().find(":input").attr("disabled", false);

	$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");

	$j("#numericResultL").parent().find("span[title]").css("color", "#000");



	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);

	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");

	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
	



	$j("#textResult_text").parent().find(":input").attr("disabled", false);

	$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");

	$j("#textResultL").parent().find("span[title]").css("color", "#000");

	

	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);

	$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");

	$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
	//$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	
	

	

	$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);

	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");

	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	
	setOrganismToContains();
	enableInputOrganism();
}
        
	
function setResultedTestToEqual(){
	if(document.getElementById("testDescription").textContent!=""){
		getElementByIdOrByName("resultOperatorList_textbox").value="Equal";
		document.getElementById("resultOperatorList").selectedIndex="1";
	}
}

function setOrganismToEqual(){
	if(document.getElementById("resultDescription").textContent!=""){
		getElementByIdOrByName("codedResultOrganismOperatorList_textbox").value="Equal";
		document.getElementById("codedResultOrganismOperatorList").selectedIndex="1";
	}
}

function setResultedTestToContains(){
	getElementByIdOrByName("resultOperatorList_textbox").value="Contains";
	document.getElementById("resultOperatorList").selectedIndex="0";
}

function setOrganismToContains(){
		getElementByIdOrByName("codedResultOrganismOperatorList_textbox").value="Contains";
	document.getElementById("codedResultOrganismOperatorList").selectedIndex="0";
}


function disableInputResultedTest(){
	if(document.getElementById("testDescription").textContent!=""){
		document.getElementById("divResultedText").style.display="none";
		document.getElementById("INV211Text").value="";
	}
}

function enableInputResultedTest(){

	document.getElementById("divResultedText").style.display="";
}

function disableInputOrganism(){
	if(document.getElementById("resultDescription").textContent!=""){
	document.getElementById("divOrganismText").style.display="none";
	document.getElementById("INV212Text").value="";
	}
}

function enableInputOrganism(){

	document.getElementById("divOrganismText").style.display="";
}

function searchLabResultedTest() {
		//$j(".infoBox").hide();
		var urlToOpen = "/nbs/LabResultedTestLink.do?method=labTestSearchLoad";
		var params = "left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
		var o = new Object();
		//o.condiionvalueselected="true";
		o.opener = self;
		//var modWin = window.showModalDialog(urlToOpen, o, "dialogWidth: " + 760
		//		+ "px;dialogHeight: " + 500
		//		+ "px;status: no;unadorned: yes;scroll: yes;help: no;"
		//		+ (true ? "resizable: yes;" : ""));
		
		var dialogFeatures = "dialogWidth: " + 790
		+ "px;dialogHeight: " + 520
		+ "px;status: no;unadorned: yes;scroll: yes;scrollbars: yes;help: no;"
		+ (true ? "resizable: yes;" : "");
		
		
		var modWin = openWindow(urlToOpen, o, dialogFeatures, null, "");
		setResultedTestToEqual();
		disableInputResultedTest();
	}
	
	
	function searchCodeResult() {
		//$j(".infoBox").hide();
		var urlToOpen = "/nbs/LabResultedTestLink.do?method=resultCodeSearchLoad";
		var params = "left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
		var o = new Object();
		o.opener = self;
	//	var modWin = window.showModalDialog(urlToOpen, o, "dialogWidth: " + 760
		//		+ "px;dialogHeight: " + 500
		//		+ "px;status: no;unadorned: yes;scroll: yes;help: no;"
		//		+ (true ? "resizable: yes;" : ""));
		
		var dialogFeatures = "dialogWidth: " + 770
		+ "px;dialogHeight: " + 500
		+ "px;status: no;unadorned: yes;scroll: yes;help: no;"
		+ (true ? "resizable: yes;" : "");
		
		var modWin = openWindow(urlToOpen, o, dialogFeatures, null, "");
		setOrganismToEqual();
		disableInputOrganism();
	}
	
	
/*
function enableDisableCodedResult(){

if(getElementByIdOrByName("ESR298").value=="")
	getElementByIdOrByName("codedResultRow").show();
else
	getElementByIdOrByName("codedResultRow").hide();
}
*/

/*
function enableDisableOrganism(){


if(getElementByIdOrByName("ESR299").value=="")
	getElementByIdOrByName("organismRow").show();
else
	getElementByIdOrByName("organismRow").hide();



}*/

function clearTestedResult()
{
	var testDescription = getElementByIdOrByName("testDescription");
	var testCodeId = getElementByIdOrByName("testCodeId");
	var testDescriptionId = getElementByIdOrByName("testDescriptionId");
	testDescription.innerText= "";
	testDescription.textContent= "";
	testCodeId.value = null;
	testDescriptionId.value = null;
	$j($j("#testDescription").parent().parent()).find(":input").val("");
	setResultedTestToContains();
	enableInputResultedTest();
	
}

function getDWRProvider(identifier)
{
 dwr.util.setValue(identifier, "");
 var code = $(identifier+"Text");
 var codeValue= code.value;

  JBaseForm.getDwrInvestigatorDetails(codeValue,identifier, function(data) {
       dwr.util.setEscapeHtml(false);
       if(data.indexOf('$$$$$') != -1) {
	         var code = $(identifier+"Text");
	         code.value = "";
	         dwr.util.setValue(identifier, "");
	         dwr.util.setValue(identifier+"Error", "");

	         dwr.util.setValue("investigator.personUid", data.substring(0,data.indexOf('$$$$$')));
	         dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$')+5));

	        getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
	        getElementByIdOrByName(identifier+"Icon").style.visibility="hidden";
	        getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
	        getElementByIdOrByName("clear"+identifier).className="";
	        getElementByIdOrByName(identifier+"SearchControls").className="none";

	        // enable the date assigned to investigator field
	        if (identifier == "INV180") {
	            enabledDateAssignedToInvestigation();
	        }
	        if (getElementByIdOrByName("attributeMap."+identifier+"Uid") != null) {
	        	getElementByIdOrByName("attributeMap."+identifier+"Uid").value = data.substring(0,data.indexOf('$$$$$'));
	        	if (identifier == "NBS139" || identifier == "NBS145" || identifier == "NBS161" || identifier == "NBS186" || identifier == "NBS197") {
				stdUpdateCurrentProvider(identifier);
			}
			if (identifier == "NBS186") { //Interviewer
				pgRequireElement("NBS187"); //Date
			}
	        }
       } else {
           dwr.util.setValue(identifier+"Error", data);
            getElementByIdOrByName(identifier+"Text").style.visibility="visible";
            getElementByIdOrByName(identifier+"Icon").style.visibility="visible";
            getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
            getElementByIdOrByName("clear"+identifier).className="none";
            getElementByIdOrByName(identifier+"SearchControls").className="visible";
       }
    });
}


function getDWROrganization(identifier)
    {
	 dwr.util.setValue(identifier, "");
	 var code = $(identifier+"Text");
     var codeValue= code.value;

     JBaseForm.getDwrOrganizationDetails(codeValue,identifier, function(data) {
           dwr.util.setEscapeHtml(false);
           if(data.indexOf('$$$$$') != -1) {
        	   var code = $(identifier+"Text");
             code.value = "";
             dwr.util.setValue(identifier, "");
             dwr.util.setValue(identifier+"Error", "");
             if (getElementByIdOrByName("attributeMap."+identifier+"Uid") != null) {
 	        	getElementByIdOrByName("attributeMap."+identifier+"Uid").value = data.substring(0,data.indexOf('$$$$$'));
             }
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
		JBaseForm.clearDWROrganization(identifier);
}


 /**
  *setTitle(): change the page title from Find Patient to Find Event when the user clicks on the Event Search tab and viceversa
  */
	function setTitle(){
	  
	  var merge = "<%=request.getAttribute("MergePatient")%>";
	    if(merge!="true"){
		getElementByIdOrByName("ETYPE_textbox").onfocus=function(){
			getElementByIdOrByName("pageTop").textContent="Find Event";
		};
		
	    }	
		getElementByIdOrByName("DEM102O_textbox").onfocus=function(){
			getElementByIdOrByName("pageTop").textContent="Find Patient";
			//$j("#labResultsEventId").hide();
		};
		
		//if($j('#ETYPE').attr('value') == "LR")  $j("#labResultsEventId").show()
		//else $j("#labResultsEventId").hide();
	}

	function hideSections(){
		$j("#tab04").hide();	
		$j("#tab06").hide();
		$j("#tab07").hide();
		
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

  	JBaseForm.clearDWRInvestigator(identifier);
  }
  function setEventIDtToEqual(){
		getElementByIdOrByName("EIDOP_textbox").value="Equal";
		document.getElementById("EIDOP").selectedIndex="1";
	}
	function setEventDateToEqual(){
		
		getElementByIdOrByName("DateOp_textbox").value="Equal";
		getElementByIdOrByName("DateOp").value="=";
		document.getElementById("DateOp").selectedIndex="1";
	}
		
  function setDefaultValuesOnEventTypeChange(){
	  /*  getElementByIdOrByName("ESR200").value=" "; 
	  getElementByIdOrByName("ESR200_textbox").value=" ";
		getElementByIdOrByName("ESR100").value=" ";
		getElementByIdOrByName("ESR100_textbox").value=" ";
		getElementByIdOrByName("EventProviderFacility").value=" ";
		getElementByIdOrByName("EventProviderFacility_textbox").value=" ";
		getElementByIdOrByName("EventProviderFacility_textbox").value=" ";
		getElementByIdOrByName("COND_textbox").value=" ";
		$j('#COND').attr('value' ,""); 					$j('#COND_textbox').attr('value' ,"");
		$j('#JURISD').attr('value' ,"");  				$j('#JURISD_textbox').attr('value' ,"");
    	$j('#PROGRAMAREA').attr('value',"");			$j('#PROGRAMAREA_textbox').attr('value',"");
    	$j('#PROGRAMAREALAB').attr('value', "");    	$j('#PROGRAMAREALAB_textbox').attr('value', "");
    	$j('#PROGRAMAREAMORB').attr('value',"");    	$j('#PROGRAMAREAMORB_textbox').attr('value',"");
    	$j('#PROGRAMAREACASE').attr('value',"");    	$j('#PROGRAMAREACASE_textbox').attr('value',"");
    	$j('#PROGRAMAREAINVEST').attr('value',"");    	$j('#PROGRAMAREAINVEST_textbox').attr('value',""); 
    	$j('#JURISD').attr('value',"");    	       		$j('#JURISD_textbox').attr('value',"");
    	$j('#pregnantList').attr('value',"");    		$j('#pregnantList_textbox').attr('value',"");
    	$j('#ESR200').attr('value',"") ;				$j('#ESR200_textbox').attr('value',"") ;
    	$j('#CreatedBy').attr('value',"");    			$j('#CreatedBy_textbox').attr('value',"");
    	$j('#UpdatedBy').attr('value', "");    			$j('#UpdatedBy_textbox').attr('value', "");
    	$j('#InvestigationStatus').attr('value',"");    $j('#InvestigationStatus_textbox').attr('value',"");
    	$j('#EventProviderFacility').attr('value',"");	$j('#EventProviderFacility_textbox').attr('value',"");	
    	//$j("#INV209").text().length==0 
    	$j("#notificationStatusList").attr('value',"");
    	$j("#caseStatusList").attr('value',"");
    	$j("#currentProcessList").attr('value',"");
    	$j("#outbreakNameList").attr('value',"");    	$j("#outbreakNameList_textbox").attr('value',""); */
    	clearEvent();
    	/*if($j('#tabs0head1').attr('class') == "ongletTextEna" && $j('#ETYPE').attr('value') == "LR"){
    		$j("#labResultsEventId").show();    	
    	}else{
    		$j("#labResultsEventId").hide();
    	}*/
    	
    	setEventDataTypeForLR();
    	checkEventStatusUpdateInv();
  }  
  
  function setEventDataTypeForLR(){
	  
	  if($j('#ETYPE').attr('value') == "LR"){
		    document.getElementById("DateOp").size = "5";
			$j("#DateOp option[value='6M']").showOption();
			$j("#DateOp option[value='30D']").showOption();
			$j("#DateOp option[value='7D']").showOption();
	        setSelectedIndex(getElementByIdOrByName("ESR200"),"DPH");
			var esrSelected=$j("#ESR200 option:selected").text();
			getElementByIdOrByName("ESR200_textbox").value=esrSelected;
			setSelectedIndex(getElementByIdOrByName("DateOp"),"6M");
			var betSelected=$j("#DateOp option:selected").text();
			getElementByIdOrByName("DateOp_textbox").value=betSelected;
			getElementByIdOrByName('to_date').value="";
			getElementByIdOrByName('from_date').value="";
			$j('#to_date').attr('disabled', 'disabled');
			$j('#from_date').attr('disabled', 'disabled');
		
		} else {
			$j('#DateOp option:selected').removeAttr('selected');
			document.getElementById("DateOp").size = "2";
			$j("#DateOp option[value='6M']").hideOption();
			$j("#DateOp option[value='30D']").hideOption();
			$j("#DateOp option[value='7D']").hideOption();
			setSelectedIndex(getElementByIdOrByName("ESR200"),"");
			var esrSelected=$j("#ESR200 option:selected").text();
			getElementByIdOrByName("ESR200_textbox").value=esrSelected;
			setSelectedIndex(getElementByIdOrByName("DateOp"),"=");
			var betSelected=$j("#DateOp option:selected").text();
			getElementByIdOrByName("DateOp_textbox").value=betSelected;
			getElementByIdOrByName('to_date').value="";
			getElementByIdOrByName('from_date').value="";
			$j('#to_date').attr('disabled', 'disabled');
			$j('#from_date').attr('disabled', 'disabled');
		} 
  }
  
  
  function formatDate(dat) {
	 var month = dat.getMonth() + 1;
	 var day =  dat.getDate();
	 var year = dat.getFullYear();
     if (month < 10)   month = '0' + month;
	 if (day < 10)     day = '0' + day;
     return month+"/"+day+"/"+year;
	}
  
  
  /**
	* checkEventStatusUpdateInv: this method implements the Jira user story ND-24520. If the Event type is Investigation, the Event type Update will be automatically checked.
	*/
	
	function checkEventStatusUpdateInv(){
	
		if($j('#ETYPE').attr('value') == "I"){ //Morb report 
			$j('#update').attr('checked', true);
		}

	}
	
  /* 
	function setValueDateDropdown(){
	
		/* getElementByIdOrByName("ESR200_textbox").value="     (Select Date Type)";
		getElementByIdOrByName("ESR200").value=" "; 
		
	} 
	function setValueTypeDropdown(){

		getElementByIdOrByName("ESR100_textbox").value=" ";
		getElementByIdOrByName("EventProviderFacility_textbox").value=" ";
		getElementByIdOrByName("ESR100").value=" ";
		getElementByIdOrByName("EventProviderFacility").value=" ";
		getElementByIdOrByName('MANUAL').checked = false;
		getElementByIdOrByName('ELECTRONIC').checked = false;
	}  */
	
	
	function clearValueSelected(id){
		document.getElementById(id).getElementsByTagName("input")[0].value='';
	}
	
	function clearCurrentProcessingStatusValues(){
		document.getElementById("currentProcessingStatusValues").value='';
		document.getElementById("currentProcessStateList-selectedValues").innerText = ""
	}
	function clearNotificationValues(){
		document.getElementById("notificationValues").value='';
		document.getElementById("notificationStatusList-selectedValues").innerText = ""
	}
	function clearCaseStatusValues(){
		document.getElementById("caseStatusValues").value='';
		document.getElementById("caseStatusList-selectedValues").innerText = ""
	}
	
	function clearConditionValues(){
		document.getElementById("COND").value='';
		document.getElementById("COND-selectedValues").innerText = ""
	}
	function clearPaValues(){
		document.getElementById("PROGRAMAREA").value='';
		document.getElementById("PROGRAMAREA-selectedValues").innerText = ""
		document.getElementById("PROGRAMAREALAB").value='';
		document.getElementById("PROGRAMAREALAB-selectedValues").innerText = ""
		document.getElementById("PROGRAMAREAMORB").value='';
		document.getElementById("PROGRAMAREAMORB-selectedValues").innerText = ""
		document.getElementById("PROGRAMAREACASE").value='';
		document.getElementById("PROGRAMAREACASE-selectedValues").innerText = ""
		document.getElementById("PROGRAMAREAINVEST").value='';
		document.getElementById("PROGRAMAREAINVEST-selectedValues").innerText = ""
			
	}
	function clearJurisdictionValues(){
		document.getElementById("JURISD").value='';
		document.getElementById("JURISD-selectedValues").innerText = ""
	}
	function clearOutbreakValues(){
		document.getElementById("outbreakNameList").value='';
		document.getElementById("outbreakNameList-selectedValues").innerText = ""
	}
	

	function initializeMultiSelects(){
		
		   
		//Notification Status
		var notificationValues = document.getElementById("notificationStatusList-selectedValues").textContent;
		notificationValues=notificationValues.substring(17);
		var notificationUnassignedChecked = document.getElementById("notificationStatusUnassigned").checked;		
		if(notificationUnassignedChecked)
			notificationValues+=", UNASSIGNED";
			
		document.getElementById("notificationValues").value=notificationValues;
		
		var notificationCodedValues = $j("#notificationStatusList").val();
		document.getElementById("notificationCodedValues").value = 	notificationCodedValues;
		
		//Case Status
		var caseStatusValues = document.getElementById("caseStatusList-selectedValues").textContent;
		caseStatusValues=caseStatusValues.substring(17);
		var caseStatusUnassignedChecked = document.getElementById("caseStatusUnassigned").checked;		
		if(caseStatusUnassignedChecked)
			caseStatusValues+=", UNASSIGNED";
			
		document.getElementById("caseStatusValues").value=caseStatusValues;
	
		var caseStatusCodedValues = $j("#caseStatusList").val();
		document.getElementById("caseStatusCodedValues").value = 	caseStatusCodedValues;
		
		//CurrentProcessingStatus
		var currentProcessingStatusValues = document.getElementById("currentProcessStateList-selectedValues").textContent;
		currentProcessingStatusValues=currentProcessingStatusValues.substring(17);
		var currentProcessingStatusUnassignedChecked = document.getElementById("currentProcessStateUnassigned").checked;		
		if(currentProcessingStatusUnassignedChecked)
			currentProcessingStatusValues+=", UNASSIGNED";
			
		document.getElementById("currentProcessingStatusValues").value=currentProcessingStatusValues;
		
		var currentProcessCodedValues = $j("#currentProcessList").val();
		document.getElementById("currentProcessCodedValues").value = currentProcessCodedValues;
		
		//ProviderFacility Selected
		var providerFacilitySelected = document.getElementById("EventProviderFacility").value;
		document.getElementById("providerFacilityValueSelected").value=providerFacilitySelected;
		
		
		
		//Investigator selected
		var investigatorSelected = document.getElementById("investigatorSelected").getElementsByTagName("input")[0].value;
		document.getElementById("investigatorValueSelected").value=investigatorSelected;

		//Investigation Description Selected
		var investigationDescSelected = document.getElementById("INV210").innerHTML;
		document.getElementById("investigatiorDescValueSelected").value=investigationDescSelected;
		
		//Reporting Facility selected
		var reportingFacilitySelected = document.getElementById("reportingFacilitySelected").getElementsByTagName("input")[0].value;
		document.getElementById("reportingFacilityValueSelected").value=reportingFacilitySelected;
		//reportingFacilityDescSelected = document.getElementById("INV185").innerHTML;
		
		//ReportingFacility Description Selected
		var reportingFacilityDescSelected = document.getElementById("INV185").innerHTML;
		document.getElementById("reportingFacilityDescValueSelected").value=reportingFacilityDescSelected;
		
		//Reporting Provider selected
		var reportingProviderSelected = document.getElementById("reportingProviderSelected").getElementsByTagName("input")[0].value;
		document.getElementById("reportingProviderValueSelected").value=reportingProviderSelected;
		//reportingFacilityDescSelected = document.getElementById("INV208").innerHTML;
		
		//Reporting Provider Description Selected
		var reportingProviderDescSelected = document.getElementById("INV208").innerHTML;
		document.getElementById("reportingProviderDescValueSelected").value=reportingProviderDescSelected;
		
		//Ordering Facility selected
		var orderingFacilitySelected = document.getElementById("orderingFacilitySelected").getElementsByTagName("input")[0].value;
		document.getElementById("orderingFacilityValueSelected").value=orderingFacilitySelected;
		//reportingFacilityDescSelected = document.getElementById("INV185").innerHTML;
		
		//OrderingFacility Description Selected
		var orderingFacilityDescSelected = document.getElementById("INV186").innerHTML;
		document.getElementById("orderingFacilityDescValueSelected").value=orderingFacilityDescSelected;
		
		//Ordering Provider selected
		var orderingProviderSelected = document.getElementById("orderingProviderSelected").getElementsByTagName("input")[0].value;
		document.getElementById("orderingProviderValueSelected").value=orderingProviderSelected;
		//reportingFacilityDescSelected = document.getElementById("INV208").innerHTML;
		
		//Ordering Provider Description Selected
		var orderingProviderDescSelected = document.getElementById("INV209").innerHTML;
		document.getElementById("orderingProviderDescValueSelected").value=orderingProviderDescSelected;
		
		//process status
		
		document.getElementById("processState").value="false";
		document.getElementById("unProcessState").value="false";
		if(document.getElementById("processState").checked) document.getElementById("processState").value="true";
		if(document.getElementById("unProcessState").checked) document.getElementById("unProcessState").value="true";
		
		//Event Status

		var newInitial = document.getElementById("newInitial").checked;

		if(newInitial)
			document.getElementById("eventStatusInitialValueSelected").value="true";
		else
			document.getElementById("eventStatusInitialValueSelected").value="false";

		var update = document.getElementById("update").checked;
		if(update)
			document.getElementById("eventStatusUpdateValueSelected").value="true";
		else
			document.getElementById("eventStatusUpdateValueSelected").value="false";
		
		var electronic = document.getElementById("ELECTRONIC").checked;
		if(electronic)
			document.getElementById("electronicValueSelected").value="true";
		else
			document.getElementById("electronicValueSelected").value="false";
		var manual = document.getElementById("MANUAL").checked;
		if(manual)
			document.getElementById("manualValueSelected").value="true";
		else
			document.getElementById("manualValueSelected").value="false";
		
		var internal = document.getElementById("INTERNALUSER").checked;
		if(internal)
			document.getElementById("internalValueSelected").value="true";
		else
			document.getElementById("internalValueSelected").value="false";
		var external = document.getElementById("EXTERNALUSER").checked;
		if(external)
			document.getElementById("externalValueSelected").value="true";
		else
			document.getElementById("externalValueSelected").value="false";
		
		var external = document.getElementById("testDescription").checked;
		
		var testDescription = document.getElementById("testDescription").innerHTML;
		document.getElementById("testDescriptionValueSelected").value=testDescription;
		var testDescriptionCode = document.getElementById("INV211Text").value;
		document.getElementById("descriptionWithCodeValueSelected").value=testDescriptionCode;
		
		var resultDescription = document.getElementById("resultDescription").innerHTML;
		document.getElementById("resultDescriptionValueSelected").value=resultDescription;
		var resultDescriptionCode = document.getElementById("INV212Text").value;
		document.getElementById("codeResultOrganismValueSelected").value=resultDescriptionCode;
		
		var DocCreateSelected = $j("#CreatedBy option:selected").text();
		document.getElementById("documentCreateFullNameValueSelected").value=DocCreateSelected;
		var DocUpdateSelected =$j("#UpdatedBy option:selected").text();
		document.getElementById("documentUpdateFullNameValueSelected").value=DocUpdateSelected;
		
	}
       function searchPatient() {
    	   var merge = "<%=request.getAttribute("MergePatient")%>";
    	   
    	   if(merge!="true")

				initializeMultiSelects();
		     	
		
					var allEnabledSearchIpElts = $j("#patientSearchByDetails").find(':input:enabled');
					var atleastOne = false;
					var erroronTab1=false;
					var erroronTab2=false;
					var validDob = true;
					var errorMsgArray = new Array(); 
				   // alert(allEnabledSearchIpElts.length);
			        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
			            if ($j(allEnabledSearchIpElts[i]).attr("id") != '' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM102O' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM104O' && 
			             $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM105O' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM106O' &&
			             $j(allEnabledSearchIpElts[i]).attr("id") != 'DEM107O' && $j(allEnabledSearchIpElts[i]).attr("type") != 'checkbox' 
			            && $j(allEnabledSearchIpElts[i]).attr("type") != 'radio' && $j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
			            && $j(allEnabledSearchIpElts[i]).attr("type") != 'button' && jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) !="" ) {
							  //$j(allEnabledSearchIpElts[i]).attr("type");
							  
							 // alert($j(allEnabledSearchIpElts[i]).attr("id")); 
								atleastOne = true;
								
								//alert($j(allEnabledSearchIpElts[i]).attr("id") + " :  " + $j(allEnabledSearchIpElts[i]).attr("type") + $j(allEnabledSearchIpElts[i]).attr("value"));
							 
							 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {
							            if(!(12 >= $j(allEnabledSearchIpElts[i]).attr("value") && $j(allEnabledSearchIpElts[i]).attr("value") >= 1)){
							            errorText = "Month of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to 12. Please correct the data and try again."; 
										errorMsgArray.push(errorText);
										$j("#patientDOB1").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										 erroronTab1= true; 
										}
								
								if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
								   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
								       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
								  		    errorMsgArray.push(errorText);
									            $j("#patientDOB1").focus();
									            getElementByIdOrByName("DEM105L").style.color="#CC0000";
									             erroronTab1= true; 
								}
								
							 }
						
							 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {
							        
							            var month = getElementByIdOrByName("personSearch.birthTimeMonth").value;
							                var year = getElementByIdOrByName("personSearch.birthTimeYear").value;
							                var days = "";
							                var element = $j(allEnabledSearchIpElts[i]).attr("value");
							        
							               if(month!=null && month !="" && year !=null && year !="")
							                {
							                    days = getDaysWithMonthAndYear(stripOffZero(month),year);
							                }
							                
							                else if(month!=null && month!=""){
							                 days = getDaysWithMonth(stripOffZero(month));
							                 }
							                else{
							                 days = "31";
							                 }
							                  //alert(element);
							                  
							                // alert(days);
							               
									 if(element > 31 || element < 1 )
									{
									    errorText = "Day of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to 31. Please correct the data and try again.";
                                        //errorText = "Date of Birth must be in the format of mm/dd/yyyy. Please correct the data and try again.";
										errorMsgArray.push(errorText);
										$j("#patientDOB2").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										 erroronTab1= true; 
									}
									else if ( days >= element && element >= 1) {
										//return true;
									}else {
									   errorText = "Day of Birth must be a 2-digit number that is greater than or equal to 1 and less than or equal to "+days+". Please correct the data and try again.";
									   //errorText = "Date of Birth must be in the format of mm/dd/yyyy. Please correct the data and try again.";
										errorMsgArray.push(errorText);
										$j("#patientDOB2").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										 erroronTab1= true; 
									}
							            
							           
								
								if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
								   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
								       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
								  		    errorMsgArray.push(errorText);
									            $j("#patientDOB1").focus();
									            getElementByIdOrByName("DEM105L").style.color="#CC0000";
									             erroronTab1= true; 
								}		
							 }
							
							 if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB3' && $j(allEnabledSearchIpElts[i]).attr("value") != "") {
							   
							    var thisYear = new Date().getFullYear();
							    var element = $j(allEnabledSearchIpElts[i]).attr("value");							
									 
							
									 if (!(thisYear >= element && element >= 1875)) {
									   errorText = "Year of Birth must be greater than 1875 and less than or equal to the current year. Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB3").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
									}
									
									if($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB1' && $j(allEnabledSearchIpElts[i]).attr("value") == "" ||
									   $j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOB2' && $j(allEnabledSearchIpElts[i]).attr("value") == ""){
									       errorText = "DOB is not a valid date. The date must be greater than 12/31/1875 and less than or equal to today's date.Please correct the data and try again.";
											    errorMsgArray.push(errorText);
											    $j("#patientDOB1").focus();
											    getElementByIdOrByName("DEM105L").style.color="#CC0000";
											     erroronTab1= true; 
								}	
														 
										
							 }
							//alert("1.4");	
							 //alert($j(allEnabledSearchIpElts[i]).attr("id") + $j(allEnabledSearchIpElts[i]).attr("value"));
							 
							 if(($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOBBet1'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
							     (getElementByIdOrByName("patientDOBBet2") != null && getElementByIdOrByName("patientDOBBet2").value == "")){
							     errorText = "Both DOB Begin Date and DOB End Date must be entered. Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB2").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
							 
							 }
							// alert("1.5");	
							if(($j(allEnabledSearchIpElts[i]).attr("id") == 'patientDOBBet2'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
							     (getElementByIdOrByName("patientDOBBet1") != null && getElementByIdOrByName("patientDOBBet1").value == "")){
							      errorText = "Both DOB Begin Date and DOB End Date must be entered. Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB1").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
							 
							 }
							 //alert("1.6");	
							 
							 //id type and id value validation starts here....
							  if(($j(allEnabledSearchIpElts[i]).attr("id") == 'DEM222'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
							     (getElementByIdOrByName("DEM223") != null && getElementByIdOrByName("DEM223").value == "")){
							     errorText = "ID Value must have a value. Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB2").focus();
									   getElementByIdOrByName("DEM223L").style.color="#CC0000";
									     getElementByIdOrByName("DEM222L").style.color="black";
									      erroronTab1= true; 
							 
							 }
							 //alert("1.7");	
							//if(($j(allEnabledSearchIpElts[i]).attr("id") == 'DEM223'  && $j(allEnabledSearchIpElts[i]).attr("value") != "") &&
							     //(getElementByIdOrByName("DEM222") != null &&getElementByIdOrByName("DEM222").value == "")){
							      //errorText = "ID Type was have a value. Please correct the data and try again.";
									   //errorMsgArray.push(errorText);
									   //$j("#patientDOB1").focus();
									   //getElementByIdOrByName("DEM222L").style.color="red";
									     //getElementByIdOrByName("DEM223L").style.color="black";
									      //erroronTab1= true; 
							 
							 //}	
							// alert("1.8");							 
								
								if($j(allEnabledSearchIpElts[i]).attr("id") == 'DEM115') {
									if (!isDate( $j(allEnabledSearchIpElts[i]).attr("value") ) ) {
										errorText = "Date of Birth must be in the format of mm/dd/yyyy. Please correct the data and try again.";
										errorMsgArray.push(errorText);
										$j("#DEM115").focus();
										getElementByIdOrByName("DEM105L").style.color="#CC0000";
										
									}
								}
			            }
			        }
			         //alert("2");
			        if((getElementByIdOrByName("patientDOBBet1") != null && 
							 getElementByIdOrByName("patientDOBBet1").value != "") && 
							 (getElementByIdOrByName("patientDOBBet2") != null &&
							  getElementByIdOrByName("patientDOBBet2").value != null) ){
							  
							  var date1= getElementByIdOrByName("patientDOBBet1").value;
							  var date2= getElementByIdOrByName("patientDOBBet2").value;
							   if(CompareDateStrings(date1, date2) >= 0)
								    {
								       errorText = "DOB Begin Date must be less than the  DOB End Date . Please correct the data and try again.";
									   errorMsgArray.push(errorText);
									   $j("#patientDOB1").focus();
									   getElementByIdOrByName("DEM105L").style.color="#CC0000";
									    erroronTab1= true; 
								    }
							 
							 }	
					 //alert("2.5");
					 if(merge!="true")
					if(atleastOne && $j("#ETYPE").attr("value")!="") {
			        	errorText = "Find Patient cannot be executed using criteria from both the Demographic and Event based fields. Please clear out criteria from either the Demographic or Event based fields and try again.";
			        	errorMsgArray.push(errorText);
			        	 erroronTab1= true; 
			        	//$j("#DEM102").focus();	
			        	 getElementByIdOrByName("ESR101L").style.color="black";
			        	 $j(".fieldName").css('color','black');
			        	
			        }
					//var atleastOneEvent = false;
					if(merge!="true")
					if($j("#ETYPE").attr("value")!=""){
				        // atleastOne = true;
				         erroronTab2= true; 
				         if ($j('#COND option:selected').text() != "" 
				        		|| ($j('#PROGRAMAREA option:selected').text() != "" || $j('#PROGRAMAREALAB option:selected').text() != "" || $j('#PROGRAMAREAMORB option:selected').text() != "" || $j('#PROGRAMAREACASE option:selected').text() != "" || $j('#PROGRAMAREAINVEST option:selected').text() != "")
				        		|| $j('#JURISD option:selected').text() != ""
				        		|| $j('#pregnantList').attr('value') != ""
				        		|| $j('#ESR200').attr('value') != "" 
			        			|| $j('#ESR100').attr('value') != "" 
				        		|| (($j('#ELECTRONIC').attr('checked')==true && !$j('#ELECTRONIC').attr('disabled') )
				        		|| ($j('#MANUAL').attr('checked')==true && !$j('#MANUAL').attr('disabled') ))
				        		|| (($j('#processState').attr('checked')==true && !$j('#processState').attr('disabled') )
				        		|| ($j('#unProcessState').attr('checked')==true && !$j('#unProcessState').attr('disabled') ))
				        		|| (($j('#INTERNALUSER').attr('checked')==false  && !$j('#INTERNALUSER').attr('disabled')
				        		|| $j('#EXTERNALUSER').attr('checked')==false && !$j('#EXTERNALUSER').attr('disabled') ))
				        		|| ($j('#newInitial').attr('checked')==true || $j('#update').attr('checked')==true)
				        		||$j("#caseStatusUnassigned").attr('checked') != false
				        		||$j("#notificationStatusUnassigned").attr('checked') != false
				        		||$j("#currentProcessStateUnassigned").attr('checked') != false
				        		|| $j('#CreatedBy').attr('value') != ""
				        		|| $j('#UpdatedBy').attr('value') != ""
				        		|| $j('#InvestigationStatus').attr('value') != ""
				        		|| $j('#EventProviderFacility').attr('value') != ""	
				        		|| $j("#INV210").text().length!=0
				        		|| $j("#testDescription").text().length!=0 
				        		|| $j("#resultDescription").text().length!=0 
				        		|| $j('#INV211Text').attr('value') != ""
				        		|| $j('#INV212Text').attr('value') != ""
				        		|| $j("#notificationStatusList option:selected").text() != ""
				        		||  $j("#caseStatusList option:selected").text() != ""
				        		||  $j("#currentProcessList option:selected").text() != ""
				        		|| $j("#outbreakNameList option:selected").text() != "" ) 
				        	{
				        	 
				        	 atleastOne = true;
				        		//atleastOneEvent = true;
				        	}
				        }/* 
					||($j("#notificationStatusList option:selected").length > 0 || $j("#notificationStatusList option:selected").attr('value') != "" ||  $j("#notificationStatusList option:selected").attr('value') != undefined )
	        		||($j("#caseStatusList option:selected").length > 0 || $j("#caseStatusList option:selected").attr('value') != "" ||  $j("#caseStatusList option:selected").attr('value') != undefined )
	        		||($j("#currentProcessList option:selected").length > 0 || $j("#currentProcessList option:selected").attr('value') != "" ||  $j("#currentProcessList option:selected").attr('value') != undefined ) */
			       
	        		if($j("#ESR100").attr("value")!="" && $j("#ESR101").attr("value")=="") {
			        	errorText = " Please enter an Event ID or de-select Event ID Type from the search criteria.";
			        	errorMsgArray.push(errorText);  
			        	$j("#ESR101").focus();
			        	//atleastOne = true;
			        	
			        	 getElementByIdOrByName("ESR101L").style.color="#CC0000";	
			        	 erroronTab2= true;        	
			        	       
			        } 
	        		 
	        		  
			        if($j("#ESR200").attr("value")!="") {
			        	
			        	 if($j("#DateOp").attr("value")=="=" && $j("#from_date").attr("value")=="") {
					        	errorText = " Please enter a date value for the date criteria selected.";
					        	errorMsgArray.push(errorText);  
					        	$j("#from_date").focus();
					        	//atleastOne = true;
					        	
					        	 getElementByIdOrByName("dateLabel").style.color="#CC0000";	
					        	 erroronTab2= true;        	
					        	       
					        } 
			        	 else if($j("#DateOp").attr("value")=="BET" && $j("#from_date").attr("value")=="" && $j("#to_date").attr("value")=="") {
					        	errorText = " Please enter a 'From Date' and 'To Date' for the date criteria selected.";
					        	errorMsgArray.push(errorText);  
					        	$j("#from_date").focus();
					        	//atleastOne = true;
					        	
					        	 getElementByIdOrByName("dateLabel").style.color="#CC0000";	
					        	 erroronTab2= true;        	
					        	       
					        }
			        	 else if($j("#DateOp").attr("value")=="BET" && $j("#from_date").attr("value")=="") {
					        	errorText = " Please enter a 'From Date' for the date criteria selected.";
					        	errorMsgArray.push(errorText);  
					        	$j("#from_date").focus();
					        	//atleastOne = true;
					        	
					        	 getElementByIdOrByName("dateLabel").style.color="#CC0000";	
					        	 erroronTab2= true;        	
					        	       
					        } 
			        	 else if($j("#DateOp").attr("value")=="BET" && $j("#to_date").attr("value")=="") {
					        	errorText = " Please enter a 'To Date' for the date criteria selected.";
					        	errorMsgArray.push(errorText);  
					        	$j("#to_date").focus();
					        	//atleastOne = true;
					        	
					        	 getElementByIdOrByName("dateLabel").style.color="#CC0000";	
					        	 erroronTab2= true;        	
					        	       
					        } 
			        	 else if($j("#DateOp").attr("value")=="BET" && $j("#from_date").attr("value")!="" && $j("#to_date").attr("value")!=""){
			        		  var date1= getElementByIdOrByName("from_date").value;
							  var date2= getElementByIdOrByName("to_date").value;
							   if(CompareDateStrings(date1, date2) >= 0)
								    {
								       errorText = "'From Date' must be less than 'To Date'.";
									   errorMsgArray.push(errorText);
									   $j("#from_date").focus();
									   getElementByIdOrByName("dateLabel").style.color="#CC0000";
									    erroronTab2= true; 
								    }
			        	 }
			        } 
			        //Validation for provider and facility
			        if(merge!="true"){
if($j("#EventProviderFacility").attr("value")!="" && ($j("#INV185").text().length==0 && $j("#INV186").text().length==0 && $j("#INV208").text().length==0 && $j("#INV209").text().length==0 && $j("#INV187").text().length==0 )) {			        	
						if($j("#EventProviderFacility").attr("value")=='SF')//Sending Facility
							errorText = "Please enter a Sending Facility or de-select Sending Facility Type from the search criteria."
						else
							errorText = " Please enter a Provider or Facility or de-select Event Provider/Facility Type from the search criteria.";			        	errorMsgArray.push(errorText);  
			        	
			        	//atleastOne = true;
			        	
			        	 getElementByIdOrByName("ProviderFacility").style.color="#CC0000";	
			        	 erroronTab2= true;        	
			        	       
			        }  
			       //alert(getElementByIdOrByName("recordAct").checked + getElementByIdOrByName("recordDel").checked);
			      //  alert("deep");
			         if(getElementByIdOrByName("recordAct").checked == 0 && getElementByIdOrByName("recordDel").checked == 0
			             && getElementByIdOrByName("recordSup").checked == 0) {
			        	errorText = " Please select at least one status code and try again.";
			        	errorMsgArray.push(errorText);  
			        	$j("#recordAct").focus();
			        	 erroronTab1= true; 
			        	atleastOne = true;       	       
			        }		 
			       /*  if((!atleastOne) && atleastOneEvent) {
			        	errorText = "Please enter at least one item to search.";
			        	errorMsgArray.push(errorText);
			        	$j("#DEM102").focus();	        	
			         }
			        if((!atleastOneEvent ) && atleastOne){
			        	errorText = "Please enter at least one item to search.";
			        	errorMsgArray.push(errorText);
			        } */
			        
			        }     
					//Operators
					
					if(getElementByIdOrByName("DEM102O_textbox").value==""||
					getElementByIdOrByName("DEM104O_textbox").value==""||
					getElementByIdOrByName("DEM105O_textbox").value==""||
					getElementByIdOrByName("DEM106O_textbox").value==""||
					getElementByIdOrByName("DEM107O_textbox").value==""||
					(merge!="true" && getElementByIdOrByName("EIDOP_textbox").value=="")||
					(merge!="true" && getElementByIdOrByName("DateOp_textbox").value=="")){
						
						errorText = "Please select a valid operator value.";
			        	errorMsgArray.push(errorText);
						
					}
					else
			       if(!atleastOne){
			        	errorText = "Please enter at least one item to search.";
			        	errorMsgArray.push(errorText);
			        }
				   
				   
			          
		        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
				     if($j(allEnabledSearchIpElts[i]).attr("type") == 'button')
				        $j(allEnabledSearchIpElts[i]).attr("disabled","disabled");
		        }	
		     // display the errors in the error messages bar.
		    // alert(errorMsgArray.length);
                if(errorMsgArray!=null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
                    var tabElts = new Array(); 
                    
                    if(erroronTab1){                
				    tabElts.push(getElementByIdOrByName("tabs0tab0"));
				    }  
                    if(erroronTab2){                
				    tabElts.push(getElementByIdOrByName("tabs0tab1"));
				    }
				    setErrorTabProperties(tabElts);  
				    handleRefineSearch();
				    selectTabonRefineSearch(tabElts);
                    return false;
                }
		     //  alert("3");
		      
		      if("<c:out value="${formHref}"/>" != null){
		    	  var url = "<c:out value="${formHref}"/>";
		    	  if(url.indexOf("?")!=-1)
		    	    document.personSearchForm.action =   url+"&ContextAction=Submit";
		    	  else
		         	 document.personSearchForm.action =    url+"?ContextAction=Submit";

		          }else{		        
				document.personSearchForm.action ="/nbs/FindPatient1.do?ContextAction=Submit";
				}
				
				isFormSubmission = true;
				blockUiDuringFormSubmission();          
				document.personSearchForm.submit();
				return true;
			}
			
	function toggleSearch(rad) {
		var type = rad.id;   
		//alert(type);
		if(type == 'pat') {
			//clearEvent();
			$j("#patientSearchByEvent").hide();				
			$j("#patientSearchByDetails").show();			
		}
		if(type == 'evt') {
			//clearPatient();
			$j("#patientSearchByDetails").hide();
			$j("#patientSearchByEvent").show();			
		}		
	}
	
	function clearPatient() {
        var allEnabledSearchIpElts = $j("#patientSearchByDetails").find(':input:enabled');
        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
            if ($j(allEnabledSearchIpElts[i]).attr("type") != 'button') {
                $j(allEnabledSearchIpElts[i]).val("");
            }
        }	
	}

	function clearEvent() {
        var allEnabledSearchIpElts = $j("#patientSearchByEvent").find(':input:enabled');
      
        for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
             if ($j(allEnabledSearchIpElts[i]).attr("type") != 'button'
            		 &&  $j(allEnabledSearchIpElts[i]).attr("id") != 'EIDOP' 
            	   	 &&  $j(allEnabledSearchIpElts[i]).attr("id") != 'DateOp'
            		 &&  $j(allEnabledSearchIpElts[i]).attr("id") != 'ETYPE'
            		 &&  ($j(allEnabledSearchIpElts[i]).attr("id") != 'ESR200' && $j(allEnabledSearchIpElts[i]).attr("id") != 'DateOp'&& $j(allEnabledSearchIpElts[i]).attr("id") != 'from_date' && $j(allEnabledSearchIpElts[i]).attr("id") != 'to_date')
            		 &&  $j(allEnabledSearchIpElts[i]).attr("id") != 'resultOperatorList'  
            		 &&  $j(allEnabledSearchIpElts[i]).attr("id") != 'codedResultOrganismOperatorList'){   
                		$j(allEnabledSearchIpElts[i]).val("");
            	}
        }
        
   
        
        	clearTestedResult();
        	clearCodeResult();
        	setEventIDtToEqual();
        	//setEventDateToEqual();
        	clearOrganization('INV185');
        	clearValueSelected("reportingFacilitySelected");
        	clearOrganization('INV186');
        	clearValueSelected('orderingFacilitySelected');
        	clearProvider('INV209');
        	clearValueSelected('orderingProviderSelected');
        	clearProvider('INV208'); 
        	clearValueSelected("reportingProviderSelected");
        	clearProvider('INV187');
        	clearValueSelected('sendingFacilitySelected');
        	clearProvider('INV210');
        	initializeMultiSelects();
        	clearValueSelected("investigatorSelected");
        	clearCurrentProcessingStatusValues();
        	clearNotificationValues();
        	clearCaseStatusValues();
        	clearConditionValues();
        	clearPaValues();
        	clearJurisdictionValues();
        	clearOutbreakValues();
        	//clearEventDateType();
        	showHideProviderFacility();
		showHideEventId();
		setCheckBoxesToTrue();
	}
	
	 function showHideEventFields() {          
		 <%-- setSelectedIndex(getElementByIdOrByName("ESR200"),"<%=request.getAttribute("DateOp")%>"); --%>
		 if($j('#ESR200').attr('value') == ""){
				//$j('#ESR101TR').hide();	
				//$j('#ESR101TR').attr('disabled', 'disabled');
				getElementByIdOrByName("dateLabel").style.color="black";
				$j('#from_date').attr('disabled', 'disabled');
				$j('#from_dateIcon').attr('tabIndex', '-1');
				$j('#to_date').attr('disabled', 'disabled');
				$j('#to_dateIcon').attr('tabIndex', '-1');
				
				$j('#resCode').attr('disabled', 'disabled');
				getElementByIdOrByName('to_date').value="";
				getElementByIdOrByName('from_date').value="";
		}
		else {
				$j('#from_date').attr('disabled', '');
				$j('#resCode').attr('disabled', '');
				$j('#from_dateIcon').attr('tabIndex', '0');
				$j('#to_dateIcon').attr('tabIndex', '0');
				$j('#from_date').focus();	
				showHideEventDateFields();
				
		}
	 
	 }
	 
	 function showHideEventDateFields(){
			
			var merge = "<%=request.getAttribute("MergePatient")%>";
		    if(merge!="true"){
			if(($j('#DateOp').attr('value') == "BET") && ($j('#ESR200').attr('value') != "")){
				$j('#to_date').attr('disabled', '');
				$j('#to_dateIcon').attr('tabIndex', '0');
				$j('#from_date').attr('disabled', '');
				
			} else if((($j('#DateOp').attr('value') == "6M") || ($j('#DateOp').attr('value') == "30D") || ($j('#DateOp').attr('value') == "7D"))  && ($j('#ESR200').attr('value') != "")){
				
				$j('#to_date').attr('disabled', 'disabled');
				$j('#from_date').attr('disabled', 'disabled');
				getElementByIdOrByName('to_date').value="";
				getElementByIdOrByName('from_date').value="";
			} else{
				$j('#from_date').attr('disabled', '');
				$j('#from_dateIcon').attr('tabIndex', '0');
				$j('#to_date').attr('disabled', 'disabled');
				$j('#to_dateIcon').attr('tabIndex', '-1');
				
				getElementByIdOrByName('to_date').value="";
			}
		    }
		}
	function showHideEventId() {
		//$j('#ESR101').val('');
		
		if($j('#ESR100').attr('value') == ""){
			//$j('#ESR101TR').hide();	
			//$j('#ESR101TR').attr('disabled', 'disabled');
			getElementByIdOrByName("ESR101L").style.color="black";
			$j('#ESR101').attr('disabled', 'disabled');
			$j('#ESR101L').attr('disabled', 'disabled');
			}
		else {
			//$j('#ESR101TR').show();	
			//$j('#ESR101TR').attr('disabled', '');
			$j('#ESR101').attr('disabled', '');
			$j('#ESR101L').attr('disabled', '');
			$j('#ESR101').focus();	
			}
	}

	function clearEventIdDropdown(){
		document.getElementsByName("personSearch.actId")[0].clear()
	}
	   
	function clearEventDateDropdown(){
		document.getElementsByName("personSearch.dateFrom")[0].clear();
		document.getElementsByName("personSearch.dateTo")[0].clear();
		
	}
	
	// jQuery Function to Hide/Show Dropdown values : IE and Firefox
	(function ($) {
		 $.fn.showHideDropdownOptions = function(value, canShowOption) { 

		         $(this).find('option[value="' + value + '"]').map(function () {
		            return $(this).parent('span').length === 0 ? this : null;
		        }).wrap('<span>').hide();

		        if (canShowOption) 
		            $(this).find('option[value="' + value + '"]').unwrap().show();
		        else 
		            $(this).find('option[value="' + value + '"]').hide();

		}

		 $.fn.showOption = function() {
			
			         var opt = this;
			         if( $(this).parent().get(0).tagName == "SPAN" ) {
			             var span = $(this).parent().get(0);
			             $(span).replaceWith(opt);
			             $(span).remove();
			         }
			         opt.disabled = false;
			         $(opt).show();
			     
			
			 return this;
			 }
		 $.fn.hideOption = function() {
			 this.each(function() {
			     if( this.nodeName == "OPTION" ) {
			         var opt = this;
			          if( $(this).parent().get(0).tagName == "SPAN" ) {
			             var span = $(this).parent().get(0);
			             $(span).hide();
			         } else { 
			         //   $(opt).wrap("span").hide();
			             opt.wrap("span").hide();
			         } 
			   
			     }
			 });
			 return this;
			 }

		})(jQuery);
		
	function showHideSections() {
		var merge = "<%=request.getAttribute("MergePatient")%>";
   	 
		if(merge!="true"){

		//setValueDateDropdown();
		//setValueTypeDropdown();
		var newType = getElementByIdOrByName('ETYPE').value;
		if(previousType != newType){
			setDefaultValuesOnEventTypeChange();
		}
		
		if($j('#ETYPE').attr('value') == "MR"){ //Morb report 
			//$j('#ESR101TR').hide();	
			//$j('#ESR101TR').attr('disabled', 'disab	led');
			$j('#ETYPE_textbox').attr('value','Morbidity Report');
			getElementByIdOrByName("ETYPE_textbox").value="Morbidity Report";
			//Show hide Program Area 
			$j('#PALL').attr('disabled', 'disabled').hide();
			$j('#PAMORB').attr('disabled', '').show();
			$j('#PACASE').attr('disabled', 'disabled').hide();
			$j('#PAINVE').attr('disabled', 'disabled').hide();
			$j('#PALAB').attr('disabled', 'disabled').hide();
			
			$j('#enterymethod').attr('disabled', 'disabled').show();
			$j('#enteredBy').attr('disabled', '').show();
			$j('#eventState').attr('disabled', '').show();
			$j('#processCondition').attr('disabled', '').hide();
			
			
			//$j('#ELECTRONIC').attr('checked', '');
			$j('#ELECTRONIC').hide();
			$j("#ele").hide();
			$j("#manu").show();
			$j('#MANUAL').attr('disabled', 'disabled').show();
			//$j('#ELECTRONIC').attr('disabled', true);
			//$j('#MANUAL').attr('checked', true);
			$j('#MANUAL').attr('style', '');
			//$j('#EXTERNALUSER').attr('checked', true);
			//$j('#INTERNALUSER').attr('checked', true);
			///$j('#newInitial').attr('checked', true);
			//$j('#update').attr('checked', true);
			
			
			$j('#tab04').show();
			$j('#ELECTRONIC').attr('disabled', 'disabled');
			
			
			$j('#EXTERNALUSER').attr('disabled', '');
			
			$j('#conditionTR').show();
			$j("#JURISD option[value='999999']").hideOption();
			// //Remove values from the Facility/Provider Dropdown
			$j("#EventProviderFacility option[value='SF']").hideOption();
			$j("#EventProviderFacility option[value='OE']").hideOption();
			$j("#EventProviderFacility option[value='RP']").hideOption();
			$j("#EventProviderFacility option[value='RE']").showOption();
			$j("#EventProviderFacility option[value='OP']").showOption();
			/* $j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#reportingFacility').show();
			$j('#reportingFacilitySelected').show();
			$j('#orderingProvider').show();
			$j('#orderingProviderSelected').show();

			 */
			 
			 $j('#docCreatedBy').show();
			 $j('#docUpdatedBy').show(); 
				
			 $j("#tab07").hide();//Investigation Criteria Section
			 $j("#tab06").hide();//Lab report criteria section
		//	$j('#ESR101L').attr('disabled', 'disabled');
		
			//Remove values from the Date Dropdown
			$j("#ESR200 option[value='DR']").showOption();
			$j("#ESR200 option[value='DPH']").hideOption();
			$j("#ESR200 option[value='SCD']").hideOption();
			$j("#ESR200 option[value='ICLD']").hideOption();			
			$j("#ESR200 option[value='ICD']").hideOption();
			$j("#ESR200 option[value='ISD']").hideOption();
			$j("#ESR200 option[value='LCD']").hideOption();
			$j("#ESR200 option[value='LUD']").showOption();
			$j("#ESR200 option[value='MCD']").showOption();
			$j("#ESR200 option[value='NCD']").hideOption();
			
			// Remove values from the Event ID Type Dropdowns
			$j("#ESR100 option[value='P10000']").hideOption();
			$j("#ESR100 option[value='P10009']").hideOption();
			$j("#ESR100 option[value='P10008']").hideOption();
			$j("#ESR100 option[value='P10010']").hideOption();
			$j("#ESR100 option[value='P10001']").hideOption();
			$j("#ESR100 option[value='P10002']").hideOption();
			$j("#ESR100 option[value='P10003']").showOption();
			$j("#ESR100 option[value='P10013']").hideOption();
			$j("#ESR100 option[value='P10004']").hideOption();
			$j("#ESR100 option[value='P10005']").hideOption();
			$j("#ESR100 option[value='P10006']").hideOption();
			
			/* <option value="P10000">ABCs Case ID</option>
			<option value="P10009">Accession Number ID</option>
			<option value="P10008">City/County Case ID</option>
			<option value="P10010">Document ID</option>
			<option value="P10001">Investigation ID</option>
			<option value="P10002">Lab ID</option>
			<option value="P10003">Morbidity Report ID</option>
			<option value="P10013">Notification ID</option>
			<option value="P10004">State Case ID</option>
			<option value="P10005">Treatment ID</option>
			<option value="P10006">Vaccination ID</option></select></div></td> */
			
			}
		else
	
			if($j('#ETYPE').attr('value') == "CR"){//Case Report. TODO: change for the correct value once the value set has been added
				$j('#ETYPE_textbox').attr('value','Case Report');
				getElementByIdOrByName("ETYPE_textbox").value="Case Report";
				$j('#tab04').show();
				$j('#conditionTR').show();
			$j('#enterymethod').attr('disabled', '').show();
			$j('#enteredBy').attr('disabled', 'disabled').hide();
			$j('#eventState').attr('disabled', '').show();
			$j('#processCondition').attr('disabled', 'disabled').hide();
			
			//$j('#ELECTRONIC').attr('checked', true);
			$j('#ELECTRONIC').attr('disabled', 'disabled').show();
			//$j('#MANUAL').attr('checked', '');
			//$j('#EXTERNALUSER').attr('checked', '');
			//$j('#INTERNALUSER').attr('checked', '');
			//$j('#newInitial').attr('checked', true);
			//$j('#update').attr('checked', true);
			
			$j('#docCreatedBy').hide();
			$j('#docUpdatedBy').hide();
			$j('#MANUAL').attr('disabled', 'disabled').hide();
			$j('#manu').hide();
			$j('#ele').show();
			
			$j("#JURISD option[value='999999']").hideOption();
			//Show hide Program Area 
			$j('#PALL').attr('disabled', 'disabled').hide();
			$j('#PAMORB').attr('disabled', 'disabled').hide();
			$j('#PACASE').attr('disabled', '').show();
			$j('#PAINVE').attr('disabled', 'disabled').hide();
			$j('#PALAB').attr('disabled', 'disabled').hide();
			
			
			
			// //Remove values from the Facility/Provider Dropdown
			$j("#EventProviderFacility option[value='SF']").showOption();
			$j("#EventProviderFacility option[value='OP']").hideOption();
			$j("#EventProviderFacility option[value='RE']").hideOption();
			$j("#EventProviderFacility option[value='RP']").hideOption();
			$j("#EventProviderFacility option[value='OE']").hideOption();
			
			/* $j('#reportingFacility').show();
			$j('#reportingFacilitySelected').show();
			$j('#reportingProvider').show();
			$j('#reportingProviderSelected').show();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
		
			$j('#documentCreatedBy').hide(); */
			$j("#tab06").hide();
			$j("#tab07").hide();
			//$j('#docCreatedBy').hide();
		//	$j('#ESR101L').attr('disabled', 'disabled');
		

			//Remove values from the Date Dropdown
			$j("#ESR200 option[value='DR']").showOption();
			$j("#ESR200 option[value='DPH']").showOption();
			$j("#ESR200 option[value='SCD']").hideOption();
			$j("#ESR200 option[value='ICLD']").hideOption();			
			$j("#ESR200 option[value='ICD']").hideOption();
			$j("#ESR200 option[value='ISD']").hideOption();
			$j("#ESR200 option[value='LCD']").hideOption();
			$j("#ESR200 option[value='LUD']").showOption();
			$j("#ESR200 option[value='MCD']").hideOption();
			$j("#ESR200 option[value='NCD']").hideOption();
			
			// Remove values from the Event ID Type Dropdowns
			$j("#ESR100 option[value='P10000']").hideOption();
			$j("#ESR100 option[value='P10009']").hideOption();
			$j("#ESR100 option[value='P10008']").hideOption();
			$j("#ESR100 option[value='P10010']").showOption();
			$j("#ESR100 option[value='P10001']").hideOption();
			$j("#ESR100 option[value='P10002']").hideOption();
			$j("#ESR100 option[value='P10003']").hideOption();
			$j("#ESR100 option[value='P10013']").hideOption();
			$j("#ESR100 option[value='P10004']").hideOption();
			$j("#ESR100 option[value='P10005']").hideOption();
			$j("#ESR100 option[value='P10006']").hideOption();
			
		
			}
		else

			if($j('#ETYPE').attr('value') == "I"){//Investigation. TODO: change for the correct value once the value set has been added
				
			$j('#ETYPE_textbox').attr('value','Investigation');
			getElementByIdOrByName("ETYPE_textbox").value="Investigation";
			$j('#tab04').show();
			$j('#ELECTRONIC').attr('disabled', 'disabled');
			$j('#MANUAL').attr('disabled', 'disabled');
			$j('#EXTERNALUSER').attr('disabled', 'disabled');
			$j('#INTERNALUSER').attr('disabled', 'disabled');
			$j('#processState').attr('disabled', 'disabled');
			$j('#unProcessState').attr('disabled', 'disabled');
			$j('#newInitial').attr('disabled', '');
			$j('#update').attr('disabled', '');
			//$j("#JURISD option[value='999999']").hideOption();
			if($j("#JURISD option[value='999999']").val()!=undefined && $j("#JURISD option[value='999999']").val()!=null)
				$j("#JURISD option[value='999999']").showOption();
			
			$j('#conditionTR').show();
			$j('#enterymethod').attr('disabled', 'disabled').hide();
			$j('#enteredBy').attr('disabled', 'disabled').hide();
			$j('#eventState').attr('disabled', '').show();
			$j('#processCondition').attr('disabled', 'disabled').hide();

		//$j('#ELECTRONIC').attr('checked', '');
			//$j('#MANUAL').attr('checked', '');
			//$j('#EXTERNALUSER').attr('checked', '');
			//$j('#INTERNALUSER').attr('checked', '');
			//$j('#newInitial').attr('checked', true);
			//$j('#update').attr('checked', true);
			
			$j('#docCreatedBy').show();
			$j('#docUpdatedBy').show();
			//Show hide Program Area 
			$j('#PALL').attr('disabled', 'disabled').hide();
			$j('#PAMORB').attr('disabled', 'disabled').hide();
			$j('#PACASE').attr('disabled', 'disabled').hide();
			$j('#PAINVE').attr('disabled', '').show();
			$j('#PALAB').attr('disabled', 'disabled').hide();
			
			// //Remove values from the Facility/Provider Dropdown
			$j("#EventProviderFacility option[value='SF']").hideOption();
			$j("#EventProviderFacility option[value='OE']").hideOption();
			$j("#EventProviderFacility option[value='RP']").showOption();
			$j("#EventProviderFacility option[value='RE']").showOption();
			$j("#EventProviderFacility option[value='OP']").hideOption();
			/* 
			$j('#reportingFacility').show();
			$j('#reportingFacilitySelected').show();
			$j('#reportingProvider').show();
			$j('#reportingProviderSelected').show();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
			
			$j('#documentCreatedBy').show(); */
			$j("#tab06").hide();//Lab report criteria section
			$j("#tab07").show();//Investigation crieria section
			//$j('#docCreatedBy').show();
			
			//Remove values from the Date Dropdown
			$j("#ESR200 option[value='DR']").showOption();
			$j("#ESR200 option[value='DPH']").hideOption();
			$j("#ESR200 option[value='SCD']").hideOption();
			$j("#ESR200 option[value='ICLD']").showOption();			
			$j("#ESR200 option[value='ICD']").showOption();
			$j("#ESR200 option[value='ISD']").showOption();
			$j("#ESR200 option[value='LCD']").hideOption();
			$j("#ESR200 option[value='LUD']").showOption();
			$j("#ESR200 option[value='MCD']").hideOption();
			$j("#ESR200 option[value='NCD']").showOption();
			
			// Remove values from the Event ID Type Dropdowns
			$j("#ESR100 option[value='P10000']").showOption();
			$j("#ESR100 option[value='P10009']").hideOption();
			$j("#ESR100 option[value='P10008']").showOption();
			$j("#ESR100 option[value='P10010']").hideOption();
			$j("#ESR100 option[value='P10001']").showOption();
			$j("#ESR100 option[value='P10002']").hideOption();
			$j("#ESR100 option[value='P10003']").hideOption();
			$j("#ESR100 option[value='P10013']").showOption();
			$j("#ESR100 option[value='P10004']").showOption();
			$j("#ESR100 option[value='P10005']").hideOption();
			$j("#ESR100 option[value='P10006']").hideOption();
			$j("#DateOp option[value='6M']").hideOption();
			$j("#DateOp option[value='30D']").hideOption();
			$j("#DateOp option[value='7D']").hideOption();
			document.getElementById("DateOp").size = "2";
			
		//	$j('#ESR101L').attr('disabled', 'disabled');
			}
						
		else
		if($j('#ETYPE').attr('value') == "LR"){//Lab report. TODO: change for the correct value once the value set has been added
			$j('#ETYPE_textbox').attr('value','Laboratory Report');
			getElementByIdOrByName("ETYPE_textbox").value="Laboratory Report";
			$j('#conditionTR').hide();
			//$j('#ELECTRONIC').attr('checked', true);
			$j('#ELECTRONIC').attr('disabled','').show();
			//$j('#MANUAL').attr('checked', true);
			$j('#ele').show();
			$j('#manu').show();
			$j('#MANUAL').attr('disabled','').show();
			$j('#EXTERNALUSER').attr('disabled', '');
			$j('#INTERNALUSER').attr('disabled', '');
			//$j('#EXTERNALUSER').attr('checked', true);
			//$j('#INTERNALUSER').attr('checked', true);
			$j('#newInitial').attr('disabled', '');
			$j('#update').attr('disabled', '');
			$j('#processState').attr('disabled', '');
			$j('#unProcessState').attr('disabled', '');
			$j('#MANUAL').attr('style', 'margin-left: 2.75px;');
			$j("#tab06").show();//Lab crieria section
			$j("#JURISD option[value='999999']").hideOption();
			//Show hide Program Area 
			$j('#PALL').attr('disabled', 'disabled').hide();
			$j('#PAMORB').attr('disabled', 'disabled').hide();
			$j('#PACASE').attr('disabled', 'disabled').hide();
			$j('#PAINVE').attr('disabled', 'disabled').hide();
			$j('#PALAB').attr('disabled', '').show();
			
			$j('#enterymethod').attr('disabled', '').show();
			$j('#enteredBy').attr('disabled', '').show();
			$j('#eventState').attr('disabled', '').show();
			$j('#processCondition').attr('disabled', '').show();
			
			$j('#docCreatedBy').show();
			$j('#docUpdatedBy').show();
			// //Remove values from the Facility/Provider Dropdown
			$j("#EventProviderFacility option[value='SF']").hideOption();
			$j("#EventProviderFacility option[value='OE']").showOption();
			$j("#EventProviderFacility option[value='RP']").hideOption();
			$j("#EventProviderFacility option[value='RE']").showOption();
			$j("#EventProviderFacility option[value='OP']").showOption();
			/* 
			$j('#reportingFacility').show();
			$j('#reportingFacilitySelected').show();
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#orderingFacility').show();
			$j('#orderingFacilitySelected').show();
			$j('#orderingProvider').show();
	
			$j('#orderingProviderSelected').show(); */
			$j("#tab07").hide();//Investigation crieria section
		
			$j('#tab04').show();		
//	$j('#ESR101L').attr('disabled', 'disabled');		//Remove values from the Date Dropdown
			$j("#ESR200 option[value='DR']").showOption();
			$j("#ESR200 option[value='DPH']").showOption();
			$j("#ESR200 option[value='SCD']").showOption();
			$j("#ESR200 option[value='ICLD']").hideOption();			
			$j("#ESR200 option[value='ICD']").hideOption();
			$j("#ESR200 option[value='ISD']").hideOption();
			$j("#ESR200 option[value='LCD']").showOption();
			$j("#ESR200 option[value='LUD']").showOption();
			$j("#ESR200 option[value='MCD']").hideOption();
			$j("#ESR200 option[value='NCD']").hideOption();
		
					
			// Remove values from the Event ID Type Dropdowns
			$j("#ESR100 option[value='P10000']").hideOption();
			$j("#ESR100 option[value='P10009']").showOption();
			$j("#ESR100 option[value='P10008']").hideOption();
			$j("#ESR100 option[value='P10010']").hideOption();
			$j("#ESR100 option[value='P10001']").hideOption();
			$j("#ESR100 option[value='P10002']").showOption();
			$j("#ESR100 option[value='P10003']").hideOption();
			$j("#ESR100 option[value='P10013']").hideOption();
			$j("#ESR100 option[value='P10004']").hideOption();
			$j("#ESR100 option[value='P10005']").hideOption();
			$j("#ESR100 option[value='P10006']").hideOption();
			
			document.getElementById("resultOperatorList").selectedIndex="0";
			document.getElementById("codedResultOrganismOperatorList").selectedIndex="0";	
			
		}
		else {
			//$j('#ESR101TR').show();	
			//$j('#ESR101TR').attr('disabled', '');

			//$j('#ELECTRONIC').attr('disabled', '');
			/* $j('#reportingFacility').show();
			$j('#reportingFacilitySelected').show();
			$j('#reportingProvider').show();
			$j('#reportingProviderSelected').show();
			$j('#orderingFacility').show();
			$j('#orderingFacilitySelected').show();
			$j('#orderingProvider').show();
			$j('#orderingProviderSelected').show();
			$j('#docCreatedBy').show();
			$j('#docUpdatedBy').show();
			
			// //Remove values from the Facility/Provider Dropdown
			$j("#EventProviderFacility option[value='OF']").showOption();
			$j("#EventProviderFacility option[value='RP']").showOption();
			$j("#EventProviderFacility option[value='RF']").showOption();
			$j("#EventProviderFacility option[value='OP']").showOption();
			$j("#ESR200 option[value='DR']").showOption();
			$j("#ESR200 option[value='DPH']").showOption();
			$j("#ESR200 option[value='SCD']").showOption();
			$j("#ESR200 option[value='ICLD']").showOption();			
			$j("#ESR200 option[value='ICD']").showOption();
			$j("#ESR200 option[value='LCD']").showOption();
			$j("#ESR200 option[value='LUD']").showOption();
			$j("#ESR200 option[value='MCD']").showOption();
			$j("#ESR200 option[value='NCD']").showOption();
		//	$j('#ESR200').showHideDropdownOptions('NCD', true);
		//	$j('#ESR101L').attr('disabled', '');
		
			// Remove values from the Event ID Type Dropdowns
			$j("#ESR100 option[value='P10000']").showOption();
			$j("#ESR100 option[value='P10009']").showOption();
			$j("#ESR100 option[value='P10008']").showOption();
			$j("#ESR100 option[value='P10010']").showOption();
			$j("#ESR100 option[value='P10001']").showOption();
			$j("#ESR100 option[value='P10002']").showOption();
			$j("#ESR100 option[value='P10003']").showOption();
			$j("#ESR100 option[value='P10013']").showOption();
			$j("#ESR100 option[value='P10004']").showOption();
			$j("#ESR100 option[value='P10005']").showOption();
			$j("#ESR100 option[value='P10006']").showOption();
			 */
			//Show hide Program Area 
				$j('#PALL').attr('disabled', '').show();
				$j('#PAMORB').attr('disabled', 'disabled').hide();
				$j('#PACASE').attr('disabled', 'disabled').hide();
				$j('#PAINVE').attr('disabled', 'disabled').hide();
				$j('#PALAB').attr('disabled', 'disabled').hide();
				
				/* $j('#ELECTRONIC').attr('checked', '');
				
				$j('#MANUAL').attr('checked', '');
				$j('#EXTERNALUSER').attr('checked', '');
				$j('#INTERNALUSER').attr('checked', '');
				$j('#newInitial').attr('checked', '');
				$j('#update').attr('checked', ''); */
				
			$j(".fieldName").css('color','black');	
			/*  setValueDateDropdown();
				setValueTypeDropdown(); */
			setDefaultValuesOnEventTypeChange();
			//showHideProviderFacility();
			hideSections();
			showHideEventFields();
			setCheckBoxesToTrue();
			 //showHideProviderFacility();
		}
		previousType = getElementByIdOrByName('ETYPE').value;
		showHideProviderFacility();
		}
		//setEventDataTypeForLR();
	}
	function setDefaultValuesOnProviderFacilityTypeChange(){
    	clearOrganization('INV186');
    	clearValueSelected('orderingFacilitySelected');
    	clearProvider('INV209');
    	clearValueSelected('orderingProviderSelected');
    	clearProvider('INV208');
    	clearValueSelected("reportingProviderSelected");
    	clearProvider('INV187');        
    	clearValueSelected('sendingFacilitySelected');
    	clearOrganization('INV185');
		clearValueSelected("reportingFacilitySelected");
    	
	}
	function maintainEventProviderFacilityOnRefine(){
		
		  var merge = "<%=request.getAttribute("MergePatient")%>";
		    if(merge!="true"){

		if(!setProvider){
		
			//reporting facility 
			var reportingFacilityDescSelected = document.getElementById("reportingFacilityDescValueSelected").value;
			document.getElementById("INV185").innerHTML = reportingFacilityDescSelected;
			var reportingFacilitySelected = document.getElementById("reportingFacilityValueSelected").value;
			document.getElementById("reportingFacilitySelected").getElementsByTagName("input")[0].value = reportingFacilitySelected;
			$j("#clearINV185").attr('class','');
			$j("#INV185SearchControls").attr('class','none');

			// Reporting Provider 
			var reportingProviderDescSelected = document.getElementById("reportingProviderDescValueSelected").value;
			document.getElementById("INV208").innerHTML = reportingProviderDescSelected;
			var reportingProviderSelected = document.getElementById("reportingProviderValueSelected").value;
			document.getElementById("reportingProviderSelected").getElementsByTagName("input")[0].value = reportingProviderSelected;
			$j("#clearINV208").attr('class','');
			$j("#INV208SearchControls").attr('class','none');
			
			//Ordering Facility
			var orderingFacilityDescSelected = document.getElementById("orderingFacilityDescValueSelected").value;
			document.getElementById("INV186").innerHTML = orderingFacilityDescSelected;
			var orderingFacilitySelected = document.getElementById("orderingFacilityValueSelected").value;
			document.getElementById("orderingFacilitySelected").getElementsByTagName("input")[0].value = orderingFacilitySelected;
			$j("#clearINV186").attr('class','');
			$j("#INV186SearchControls").attr('class','none');
			
			//Ordering Provider
			var orderingProviderDescSelected = document.getElementById("orderingProviderDescValueSelected").value;
			document.getElementById("INV209").innerHTML = orderingProviderDescSelected;
			var orderingProviderSelected = document.getElementById("orderingProviderValueSelected").value;
			document.getElementById("orderingProviderSelected").getElementsByTagName("input")[0].value = orderingProviderSelected;
			$j("#clearINV209").attr('class','');
			$j("#INV209SearchControls").attr('class','none');
			
			//Investigator 
			if(document.getElementById("investigatiorDescValueSelected").value != undefined && document.getElementById("investigatiorDescValueSelected").value != ""){
			var investigatorDescSelected = document.getElementById("investigatiorDescValueSelected").value;
			document.getElementById("INV210").innerHTML = investigatorDescSelected;
			var investigatorSelected = document.getElementById("investigatorValueSelected").value;
			document.getElementById("investigatorSelected").getElementsByTagName("input")[0].value = investigatorSelected;
			$j("#clearINV210").attr('class','');
			$j("#INV210SearchControls").attr('class','none');
			}
			
			//Notification status selected values
			if($j("#notificationValues").attr('value') != "" && $j("#notificationValues").attr('value')!= undefined){
				var notificationValues = document.getElementById("notificationValues").value;
				if(notificationValues.indexOf("UNASSIGNED")!= -1){
					notificationValues = notificationValues.replace(", UNASSIGNED",'');
					$j("#notificationStatusUnassigned").attr('checked',true);
				}
				else
					$j("#notificationStatusUnassigned").attr('checked','');
				if(notificationValues.indexOf("ed Values:")!= -1){
					notificationValues = notificationValues.replace("ed Values:",'');
					
				}
				
				$j("#notificationStatusList-selectedValues").append(notificationValues);
				
				var notificationSelectedCodeValues = document.getElementById("notificationCodedValues").value; 
				var arrayNotification = notificationSelectedCodeValues.split(",");
				$j("#notificationStatusList").val(arrayNotification);
				
			
			}
			
			//Case Status selected values
			if($j("#caseStatusValues").attr('value') != "" && $j("#caseStatusValues").attr('value')!= undefined){
				var caseValues = document.getElementById("caseStatusValues").value;
				if(caseValues.indexOf("UNASSIGNED")!= -1){
					caseValues = caseValues.replace(", UNASSIGNED",'');
					$j("#caseStatusUnassigned").attr('checked',true);
				}
				else{
					$j("#caseStatusUnassigned").attr('checked','');
				}
				if(caseValues.indexOf("ed Values:")!= -1){
					caseValues = caseValues.replace("ed Values:",'');
					
				}
				$j("#caseStatusList-selectedValues").append(caseValues);
				var caseStatusSelectedCodeValues = document.getElementById("caseStatusCodedValues").value; 
				var arrayNotification = caseStatusSelectedCodeValues.split(",");
				$j("#caseStatusList").val(arrayNotification);
			}
			//Current Processing status values
			if($j("#currentProcessingStatusValues").attr('value') != "" && $j("#currentProcessingStatusValues").attr('value')!= undefined){
				
				var processValues = document.getElementById("currentProcessingStatusValues").value;
				if(processValues.indexOf("UNASSIGNED")!= -1){
					processValues = processValues.replace(", UNASSIGNED",'');
					$j("#currentProcessStateUnassigned").attr('checked',true);
				}
				else 
					$j("#currentProcessStateUnassigned").attr('checked','');
				if(processValues.indexOf("ed Values:")!= -1){
					processValues = processValues.replace("ed Values:",'');
					
				
				}$j("#currentProcessStateList-selectedValues").append(processValues);
				var currentProcessSelectedCodeValues = document.getElementById("currentProcessCodedValues").value; 
				var arrayNotification = currentProcessSelectedCodeValues.split(",");
				$j("#currentProcessList").val(arrayNotification);
			}
			
			//New Initial 

			if(document.getElementById("eventStatusInitialValueSelected").value=="true")
				document.getElementById("newInitial").checked=true;
			else
				document.getElementById("newInitial").checked='';

			if(document.getElementById("eventStatusUpdateValueSelected").value=="true")
				 document.getElementById("update").checked=true;
			else
				document.getElementById("update").checked='';
			
			//Manual Electronic

			if(document.getElementById("electronicValueSelected").value=="true")
				document.getElementById("ELECTRONIC").checked=true;
			else
				document.getElementById("ELECTRONIC").checked='';

			if(document.getElementById("manualValueSelected").value=="true")
				 document.getElementById("MANUAL").checked=true;
			else
				document.getElementById("MANUAL").checked='';
			

			//Internal and external user
			
			if(document.getElementById("internalValueSelected").value=="true")
				document.getElementById("INTERNALUSER").checked=true;
			else
				document.getElementById("INTERNALUSER").checked='';

			if(document.getElementById("externalValueSelected").value=="true")
				 document.getElementById("EXTERNALUSER").checked=true;
			else
				document.getElementById("EXTERNALUSER").checked='';
			
			//Resulted Test Description
			if($j("#resultDescriptionValueSelected").attr('value') != "" && $j("#resultDescriptionValueSelected").attr('value')!= undefined){
				var resultDescription = document.getElementById("resultDescriptionValueSelected").value;
				document.getElementById("resultDescription").innerHTML =resultDescription;
				
				document.getElementById("divOrganismText").style.display="none";
				document.getElementById("INV212Text").value="";
				
			}
			if($j("#codeResultOrganismValueSelected").attr('value') != "" && $j("#codeResultOrganismValueSelected").attr('value')!= undefined){
				var resultDescriptionCode = document.getElementById("codeResultOrganismValueSelected").value;
				document.getElementById("INV212Text").value = resultDescriptionCode;
				
			}
			
			//Coded Result and organism 
			if($j("#testDescriptionValueSelected").attr('value') != "" && $j("#testDescriptionValueSelected").attr('value')!= undefined){
				var testDescription = document.getElementById("testDescriptionValueSelected").value;
				document.getElementById("testDescription").innerHTML=testDescription;
				
				document.getElementById("divResultedText").style.display="none";
				document.getElementById("INV211Text").value="";
				
			}
			if($j("#descriptionWithCodeValueSelected").attr('value') != "" && $j("#descriptionWithCodeValueSelected").attr('value')!= undefined){
				var testDescriptionCode = document.getElementById("descriptionWithCodeValueSelected").value;
				document.getElementById("INV211Text").value = testDescriptionCode;
				
			}
		}
	}
	}
	var previousProviderFacility;
	var setProvider = false;
	function showHideProviderFacility(){
		
		
		var setProvider = false;
		var newProviderFacility = getElementByIdOrByName('EventProviderFacility').value;
		if(previousProviderFacility != newProviderFacility){
			setDefaultValuesOnProviderFacilityTypeChange();
			setProvider = true;
		
			$j("#INV185Error").html("");
			$j("#INV208Error").html("");
			$j("#INV186Error").html("");
			$j("#INV209Error").html("");
			$j("#INV187Error").html("");
		}
		
		if($j('#EventProviderFacility').attr('value') == "OP"){
			
			/* $j('#providerFacility').hide();
			$j('#providerFacilitySelected').hide(); */

			$j('#reportingFacility').hide();
			$j('#reportingFacilitySelected').hide();
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').show();
			$j('#orderingProviderSelected').show();
			$j('#sendingFacility').hide();
			$j('#sendingFacilitySelected').hide();
			
		}
		else if($j('#EventProviderFacility').attr('value') == "OE"){
			/* $j('#providerFacility').hide();
			$j('#providerFacilitySelected').hide(); */

			$j('#reportingFacility').hide();
			$j('#reportingFacilitySelected').hide();
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#orderingFacility').show();
			$j('#orderingFacilitySelected').show();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
			$j('#sendingFacility').hide();
			$j('#sendingFacilitySelected').hide();
		}
		else if($j('#EventProviderFacility').attr('value') == "RE"){
			/* $j('#providerFacility').hide();
			$j('#providerFacilitySelected').hide(); */

			$j('#reportingFacility').show();
			$j('#reportingFacilitySelected').show();
		/* 	if(!setProvider){
				var providerFacilityDescSelected = document.getElementById("reportingFacilityDescValueSelected").value;
				document.getElementById("INV185").innerHTML = providerFacilityDescSelected;
				
				var providerFacilitySelected = document.getElementById("reportingFacilityValueSelected").value;
				document.getElementById("reportingFacilitySelected").getElementsByTagName("input")[0].value = providerFacilitySelected;
				
				$j("#clearINV185").attr('class','');
				$j("#INV185SearchControls").attr('class','none');
				
			} */
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
			$j('#sendingFacility').hide();
			$j('#sendingFacilitySelected').hide();
		}
		else if($j('#EventProviderFacility').attr('value') == "RP"){
			/* $j('#providerFacility').hide();
			$j('#providerFacilitySelected').hide(); */

			$j('#reportingFacility').hide();
			$j('#reportingFacilitySelected').hide();
			$j('#reportingProvider').show();
			$j('#reportingProviderSelected').show();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
			$j('#sendingFacility').hide();
			$j('#sendingFacilitySelected').hide();
		}
		else if($j('#EventProviderFacility').attr('value') == "SF"){
			/* $j('#providerFacility').hide();
			$j('#providerFacilitySelected').hide(); */

			$j('#reportingFacility').hide();
			$j('#reportingFacilitySelected').hide();
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
			$j('#sendingFacility').show();
			$j('#sendingFacilitySelected').show();
			
		}
		else{
			/* $j('#providerFacility').show();
			$j('#providerFacilitySelected').show();
 */
 			$j('#sendingFacility').hide();
			$j('#sendingFacilitySelected').hide();
			$j('#reportingFacility').hide();
			$j('#reportingFacilitySelected').hide();
			$j('#reportingProvider').hide();
			$j('#reportingProviderSelected').hide();
			$j('#orderingFacility').hide();
			$j('#orderingFacilitySelected').hide();
			$j('#orderingProvider').hide();
			$j('#orderingProviderSelected').hide();
		}
		previousProviderFacility = getElementByIdOrByName('EventProviderFacility').value;
	}
	function flipDate(_selectBox){
	
	var _date=getElementByIdOrByName("fulldate");
	var _dateRange = getElementByIdOrByName("betweendate");
	var _birthTimeMonth = getElementByIdOrByName("personSearch.birthTimeMonth");
	var _birthTimeDay = getElementByIdOrByName("personSearch.birthTimeDay");
	var _birthTimeYear = getElementByIdOrByName("personSearch.birthTimeYear");
	var _afterBirthTime = getElementByIdOrByName("personSearch.afterBirthTime");
	var _beforeBirthTime = getElementByIdOrByName("personSearch.beforeBirthTime");
	
	
	if(_selectBox.value=="="){
	        _afterBirthTime.value = "";
	        _beforeBirthTime.value = "";
		_date.className="visible";
		_dateRange.className="none";
	} else {
	        _birthTimeMonth.value = "";
	        _birthTimeDay.value = "";
	        _birthTimeYear.value = "";
	        
		_date.className="none";
		_dateRange.className="visible";
	}

}

function flipDateOnload(selectBox){
	
	var _selectBox = getElementByIdOrByName(selectBox);
	var _date=getElementByIdOrByName("fulldate");
	
	var _dateRange = getElementByIdOrByName("betweendate");
	
	if(_selectBox.value=="="){
		_date.className="visible";
		_dateRange.className="none";
	} else {
		_date.className="none";
		_dateRange.className="visible";
	}

}

function handleAdvancedSearch() {
 
    var atleastOne = false;
     var allEnabledSearchIpElts = $j("#patSearch4").find(':input:enabled');
      for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
       if ( $j(allEnabledSearchIpElts[i]).attr("type") != 'checkbox' 
       && $j(allEnabledSearchIpElts[i]).attr("type") != 'radio' && $j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
       && $j(allEnabledSearchIpElts[i]).attr("type") != 'button'){
			
			//alert($j(allEnabledSearchIpElts[i]).attr("id") + " :  " + $j(allEnabledSearchIpElts[i]).attr("type") + $j(allEnabledSearchIpElts[i]).attr("value"));            
			              atleastOne = true;   
			   
			   
			            }//if ends
     
     
    var sectionId = "#patSearch3";
    var sectionHead = $j(sectionId).find("table.sectHeader").get(0);
    var sectionBody = $j(sectionId).find("div.sectBody").get(0);

    if (!atleastOne) {
        $j(sectionBody).hide();
        $j(sectionHead).find("a.toggleIconHref").html(plusIcon);
    } 
    else {
        $j(sectionBody).show();
        $j(sectionHead).find("a.toggleIconHref").html(minusIcon);
    }
    
    updateSectionsTogglerHandle("#" + $j(
                                        $j(
                                            sectionId                                 
                                        ).parent().get(0)
                                      ).attr("id"));
	
	     }//for ends
	}

function handleRefineSearch() {
      if("<%=request.getAttribute("DSFileTab")%>" != null){
                   $j('#DEM102').focus();
		     var tab = "<%=request.getAttribute("DSFileTab")%>";
		     if(tab == 2){
		     	$j('#ESR101TR').show();	
				$j('#ESR101').focus();
			 	var tabElts = new Array();            
                                 
			  	tabElts.push(getElementByIdOrByName("tabs0tab1"));
				 
				selectTabonRefineSearch(tabElts);
		   	}else{
		   		var tabElts = new Array();            
		     	tabElts.push(getElementByIdOrByName("tabs0tab0"));
				selectTabonRefineSearch(tabElts);
		   	}
	 }
	 if(getElementByIdOrByName("patientDOBBet1") != null && getElementByIdOrByName("patientDOBBet1").value != ""){
	    var _date=getElementByIdOrByName("fulldate");
	   var _dateRange = getElementByIdOrByName("betweendate");
	    _date.className="none";
		_dateRange.className="visible";
	 }
	 
	 
	 
	// setSelectedIndex(getElementByIdOrByName("DEM102O"),"=");
	
	
	}
	

function selectTabonRefineSearch(tabElements)
{
    var topTabHandles = getTabHandles(_top);
    var bottomTabHandles = getTabHandles(_bottom);
    var firstTopErrorTabId;
    // prepare a look up array for later use
    var errorTabsLookupArray = new Array();
    for (var i = 0; i < tabElements.length; i++) {
        var tabBodyId = $j(tabElements[i]).attr("id");
        var tabHandleId = tabHandleIdPrefix + tabBodyId.replace(tabBodyIdPrefix, "");
        //alert("asso array : b4 : " + tabHandleId + "errorTabsLookupArray[tabHandleId] = " + errorTabsLookupArray[tabHandleId]);
        errorTabsLookupArray[tabHandleId] = "Filled";
        //alert("asso array : after : " + tabHandleId + "errorTabsLookupArray[tabHandleId] = " + errorTabsLookupArray[tabHandleId]);
    } 
    

    // assign error class to top tab handles
    for (var i = 0; i < topTabHandles.length; i++) {
        var tabHandleId = $j(topTabHandles[i]).attr("id");
        
        //alert("errorTabsLookupArray[tabHandleId] for tabHandleId:" + tabHandleId + " = " + errorTabsLookupArray[tabHandleId]);
        
        if (errorTabsLookupArray[tabHandleId] != null && 
                errorTabsLookupArray[tabHandleId] != undefined &&
                errorTabsLookupArray[tabHandleId] != "") {
	        if (firstTopErrorTabId == null) {
	            //alert("First error tab handle id, body id = " + tabHandleId + ", " + tabBodyId);
	            firstTopErrorTabId = tabHandleId;
	            
	        }
	        else {
	          
	        }
        }
        else {
         
            
        }
    }
    
  
    
    // go to the first errror tab...
    if (firstTopErrorTabId != null) {
    	//getElementByIdOrByName(firstTopErrorTabId).classNameErrorStdLayout = new Object();
    	//jump to first error tab
    	$j("#" + firstTopErrorTabId).click();    
    }

    
}

function setSelectedIndex(s, v) {
 
    for ( var i = 0; i < s.options.length; i++ ) {
   
        if ( s.options[i].value == v ) {
        
            s.options[i].selected = true;
            
            return;
        }
    }
}

/** 
* Block the UI using the blockUI JQuery plugin
*/
function blockUiDuringFormSubmission()
{
    var saveMsg = '<div class="submissionStatusMsg" style="height:90px"> <div class="header"> Loading Search Results </div>' +  
        '<div class="body"> <img src="saving_data.gif" alt="Saving data" title="Saving data"/> Your search results are being loaded. Please wait ... </div> </div>';         
	$j.blockUI({  
	    message: saveMsg,  
	    css: {  
	        top:  ($j(window).height() - 500) /2 + 'px', 
	        left: ($j(window).width() - 500) /2 + 'px', 
	        width: '500px',
	        height: '90px'
	    }  
	});
}

function displayMultiselectOptions()
{
	displaySelectedOptions(getElementByIdOrByName('COND'), 'COND-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('PROGRAMAREA'), 'PROGRAMAREA-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('PROGRAMAREALAB'), 'PROGRAMAREALAB-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('PROGRAMAREAMORB'), 'PROGRAMAREAMORB-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('PROGRAMAREACASE'), 'PROGRAMAREACASE-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('PROGRAMAREAINVEST'), 'PROGRAMAREAINVEST-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('JURISD'), 'JURISD-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('outbreakNameList'), 'outbreakNameList-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('caseStatusList'), 'caseStatusList-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('notificationStatusList'), 'notificationStatusList-selectedValues');
	displaySelectedOptions(getElementByIdOrByName('currentProcessStateList'), 'currentProcessStateList-selectedValues');
	}
/* /**
 * checkPermissions: 

	 */
	
  function checkPermissions(){
	 
	 var permissionLab = ${fn:escapeXml(personSearchForm.attributeMap.permissionLab)};
	 var permissionMorb = ${fn:escapeXml(personSearchForm.attributeMap.permissionMorb)};
	 var permissionCase = ${fn:escapeXml(personSearchForm.attributeMap.permissionCase)};
	 var permissionInvestigation = ${fn:escapeXml(personSearchForm.attributeMap.permissionInvestigation)};
	 var showLabReportOption=${fn:escapeXml(personSearchForm.attributeMap.showLabReportOption)};

	 if(permissionLab==false)
		 $j("#ETYPE option[value='LR']").hideOption();
	 if(showLabReportOption==false)
		 $j("#ETYPE option[value='LR']").hideOption();
	 if(permissionMorb==false)
		 $j("#ETYPE option[value='MR']").hideOption();
	 if(permissionCase==false)
		 $j("#ETYPE option[value='CR']").hideOption();
	 if(permissionInvestigation==false)
		 $j("#ETYPE option[value='I']").hideOption(); 
	 
 }   
  var previousType;
  var reportingFacilityDescSelected;
 function setReportType(){
	 
	 var reportType= "<%=request.getAttribute("EventTypeCd")%>";
	 $j("#ETYPE").attr('value',reportType);
	 previousType = reportType;
	 //$j("#ETYPE").attr('value',reportType)
 }
 
               
          </script>  
        
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
     </style>
    </head>
      
      
     <% 
    int subSectionIndex = 0;

    String tabId = "";
      String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Contact Investigation"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 

  
    <body class onload="startCountdown();autocompTxtValuesForJSP();handleRefineSearch();handleAdvancedSearch();checkPermissions();setReportType();showHideSections();showHideEventId();showHideEventFields();setTitle();maintainEventProviderFacilityOnRefine();addRolePresentationToTabsAndSections();displayMultiselectOptions();">
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/LoadFindPatient1.do">
                <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>                 
                    <!-- For create/edit mode only -->                   
                        <!-- top button bar -->
                        <% if (request.getAttribute("confirmationMergeMessage") != null) { %>
									  <div class="infoBox success">
									  	<%=(String)request.getAttribute("confirmationMergeMessage")%>
									  </div>    
						           <% } %>
                      
                      	 
                         <div class="grayButtonBar">
                         <table role="presentation" width="100%">
					   		 <tr>
					   		 	<td align="left" width="80%">
					   		 	</td>
					   		 
					   		 	<td align="right" >
					   		 	<input type="button" name="Clear" value="Clear" onclick="clearForm()" />
					   		 	</td>
								<td align="right" style="width:10px; padding-left: 5px">
					   		 	<input type="button" name="Submit" value="Submit" onclick="searchPatient()"/>
					   		 	</td>
					   		 </tr> 				   		
					   		
					   	</table>	
					   	</div> 
					<div id="labResultsEventId" class="infoBox info" style="text-align:left;">
                     Please indicate search criteria to limit the number of records returned. A search resulting in a large data set can cause extended wait times for query results and could affect performance.
                    </div> 		
                         
                          
                         <table role="presentation" width="100%" >
                         <tr>
                         <td height="10">&nbsp;
                         </td>
                         </tr>
                         </table>
                        
                        
                      <!-- Page Errors -->
                       <%@ include file="/jsp/feedbackMessagesBar.jsp" %>       
                    
                     <!-- Error Messages using Action Messages-->
				    <div id="globalFeedbackMessagesBar" class="screenOnly">
				        <logic:messagesPresent name="error_messages">
				        <div class="infoBox errors" id="errorMessages">
				            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				            <ul>
				                <html:messages id="msg" name="error_messages">
				                    <li> <bean:write name="msg" /> </li>
				                </html:messages>
				            <ul>
				        </div>
				    </logic:messagesPresent>
				    </div> 
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer"> 	 
         	 
		 
      		 <layout:tab key="Patient Search">     
      			<jsp:include page="PatientSearch.jsp"/>
             </layout:tab>    	 
		 
		 <logic:notEqual name="MergePatient" value="true">
     		 <layout:tab key="Event Search">     
      		<jsp:include page="EventSearch.jsp"/>
             	</layout:tab>  
          </logic:notEqual>                 
        
    	  </layout:tabs>  
	  
	   <div class="grayButtonBar">
                         <table role="presentation" width="100%">
					   		 <tr>
					   		 	<td align="left" width="80%">
					   		 	</td>
								<td align="right" >
					   		 	<input type="button" name="Clear" value="Clear" onclick="clearForm()"/>
					   		 	</td>
					   		 	<td align="right" style="width:10px; padding-left: 5px">
					   		 	<input type="button" name="Submit" value="Submit" onclick="searchPatient()"/>
					   		 	</td>
					   		 </tr> 
					   	</table>	 		
                         
                         </div>              
	    	
               </html:form>
          </div> <!-- Container Div -->
    </body>
     
</html>
<SCRIPT LANGUAGE="JavaScript">
		function startCountdown() {
			var sessionTimeout = <%= request.getSession().getMaxInactiveInterval()%>
			min = sessionTimeout / 60;
			sec = 0;				
			getTimerCountDown();
		}
	</SCRIPT>
	