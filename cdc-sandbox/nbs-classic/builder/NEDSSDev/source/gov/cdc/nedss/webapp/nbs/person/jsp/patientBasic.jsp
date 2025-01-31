<?xml version="1.0" encoding="UTF-8"?>

       <!-- patientBasic.jsp for adding patients - extended still using xsp -->
    <html lang="en">
    <head>
    <title>NBS:Add Patient - Basic</title>
    <%@ include file="/jsp/resources.jsp" %> 
    <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
    <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
    <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
    <%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
    <%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
    <%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <%@ page isELIgnored ="false" %>
    <%@ page import="java.util.*" %>
    <%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO"%>
    <%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT"%>
    <%@ page import="gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm"%>
    <%@ page import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JDSPersonForm.js"></SCRIPT>  
    <SCRIPT Language="JavaScript" Type="text/javascript" SRC="PersonSpecific.js"></SCRIPT> 

     
  
     <script language="JavaScript">
              
        var gBatchEntryUpdateSeq = "";
        var gBatchEntryFieldsDisabled = false;
              
        function cancelForm(){
        	var cancel = '${fn:escapeXml(cancelButtonHref)}';
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
            	document.forms[0].action =cancel.replaceAll("&amp;","&");
                document.forms[0].submit();
            } else {
                return false;
            }
        }
        function saveForm() 
        {
        	 
        	if (checkForValidationErrorsOnSubmit()) {
                    return false;
          } else {
        	     blockUIDuringFormSubmission();
        	     var saveForm = '${fn:escapeXml(formHref)}';
        	     document.forms[0].action =saveForm.replaceAll("&amp;","&")+"?ContextAction=Submit";
                 document.forms[0].submit();
          }
        }
        function addExtendedForm() 
        {
          if (checkForValidationErrorsOnSubmit()) {
                    return false;
          } else {
                    document.forms[0].action ='<%=request.getAttribute("addButtonHref")%>';
                    document.forms[0].submit();
          }
      
        }
 
        function checkForValidationErrorsOnSubmit()
        {
        	var errorLabels = new Array();
        	var errorElts = new Array();
        	
        	
        	//  check for dangling repeating item data
        	var assigningAuthorityTxtNode = getElementByIdOrByName('assigningAuthority_textbox');
		var idValueNode = getElementByIdOrByName('idValue');
		var typeDescTxtNode = getElementByIdOrByName('typeID_textbox');
        
        	typeId = jQuery.trim(typeDescTxtNode.value);
        	assignAuth = jQuery.trim(assigningAuthorityTxtNode.value);
        	idVal = jQuery.trim(idValueNode.value);
			// validate information as of date required field
        	
        	var asofDate = getElementByIdOrByName("DEM209");
        	var asofDateValue = jQuery.trim(asofDate.value);
        	if (asofDateValue == null || asofDateValue == "") {
        		getElementByIdOrByName("DEM209L").style.color="#CC0000";
			var errHref = "<a href=\"javascript: getElementByIdOrByName('DEM209').focus()\">Information As of Date</a> is a required field.";
        		errorLabels.push(errHref);
        	} else getElementByIdOrByName("DEM209L").style.color="black";
        	
        	var censusTract = getElementByIdOrByName('DEM168');
        	if(censusTract.value!=null && censusTract.value!=''){
	        	var re = /^(\d{4}|\d{4}.(?!99)\d{2})$/;
	        	if(!re.test(censusTract.value)){
	        		getElementByIdOrByName("DEM168L").style.color="#CC0000";
	        		var errHref = "<a href=\"javascript: getElementByIdOrByName('DEM168').focus()\">Census Tract</a> should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.";
	        		errorLabels.push(errHref);
         		} else getElementByIdOrByName("DEM168L").style.color="black";
         	}
        	
        	if ( (typeId != null && typeId != "") || (assignAuth != null && assignAuth != "") ||
        		(idVal != null && idVal != "") ) {
        		errorLabels.push("Data has been entered in the Indentification Subsection. Please press Add ID or clear the data and submit again.");
        	}
        	
        	//grabbing the calendar icon beside date field
        	var dateElts = $j('img[src="calendar.gif"]');
            var j = 0;
            for (var i = 0; i < dateElts.length; i++)
            	{
            	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
            	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
            	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
            	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
            	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
            	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
            	 //expression to match required date format
                dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
                if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
                    //highlight field label red if format error
                	$j("#"+dateEltsLabelId).css("color", "990000");
                	var theErrEleId = dateEltsLabelId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
                }
                else if (dateEltsValue.match(dateFormat)) {
					var dtArray = dateEltsValue.match(dateFormat);
					var dtMonth = dtArray[1];
					var dtDay = dtArray[3];
					var dtYear = dtArray[5];
					if ((dtMonth < 1 || dtMonth > 12)
							|| (dtDay < 1 || dtDay > 31)
							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
						var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
						errorElts.push(dateEltsId);
						errorLabels.push(errHtmlStr);
						$j("#" + dateEltsLabelId).css("color", "990000");
					} else {
						$j("#" + dateEltsLabelId).css("color", "black");
					}
                }
                else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
            	}
        		
        	if (errorLabels.length > 0) {
		        displayErrors('PatientSubmitErrorMessages', errorLabels); 
		        return true;
		}

        	
        	
		$j('#PatientSubmitErrorMessages').css("display", "none");
		return false;
        }
        
        
        function initializePatientBasic()
	{
	    	var today = new Date();
		var formatToday = (today.getMonth()+1) + "/" + (today.getDate()) + "/" + (today.getFullYear());
		getElementByIdOrByName("today").value = formatToday;
		
		
		
		var element=getElementByIdOrByName("DEM167_textbox");

		
		element.value = "United States";
		
		
		AutocompleteComboBox('DEM167_textbox','DEM167',true);    	
	}

        
        
        // The items filled in on the search screen get passed in request
        function populateFromSearchCriteria()
	{
	
	
		//FIRST NAME
		var searchFirstName ="<%=request.getAttribute("firstNameOne")%>";
		if (searchFirstName != 'null' && searchFirstName != "")
			getElementByIdOrByName("DEM104").value = searchFirstName;
		//LAST NAME	
		var searchLastName ="<%=request.getAttribute("lastNameOne")%>";
		if (searchLastName != 'null' & searchLastName != "")
			getElementByIdOrByName("DEM102").value = searchLastName;
		
		//SEX
	        var searchCurrSexCd ='<%=request.getAttribute("currSexCd")%>';
		if (searchCurrSexCd != 'null' && searchCurrSexCd!= "")
			getElementByIdOrByName("DEM113").value = searchCurrSexCd;
		//DOB

		var searchBirthTime ='<%=request.getAttribute("birthTime")%>';
		if (searchBirthTime != 'null' && searchBirthTime != "") {
			var dobNode = getElementByIdOrByName("patientDOB");
			dobNode.value = searchBirthTime;
			updatePatientDOBInfo(dobNode);
		}	

		var searchCurrentAge ='<%=request.getAttribute("currentAge")%>';
		var searchCurrentAgeUnitCd ='<%=request.getAttribute("currentAgeUnitCd")%>';
		if (searchCurrentAge != 'null' && searchCurrentAgeUnitCd != null) {
			getElementByIdOrByName("currentAge").text = searchCurrentAge;
			getElementByIdOrByName("listcurrentAgeUnits").text = searchCurrentAgeUnitCd;
		}

		var searchAddressOne = '<%=request.getAttribute("addressOne")%>';
		if (searchAddressOne != 'null')
			getElementByIdOrByName("DEM159").value = searchAddressOne;		
		
		var searchCityOne = '<%=request.getAttribute("cityOne")%>';
		if (searchCityOne != 'null')
			getElementByIdOrByName("DEM161").value = searchCityOne;	
		
		var searchStateOne = '<%=request.getAttribute("stateOne")%>';
		if (searchStateOne != 'null') 
			getElementByIdOrByName("DEM162").value = searchStateOne;
		
		var searchZipOne = '<%=request.getAttribute("zipOne")%>';
		if (searchZipOne != 'null')
			getElementByIdOrByName("DEM163").value = searchZipOne;
		
		var searchAddressCounties = '<%=request.getAttribute("AddressCounties")%>';
		if (searchAddressCounties != 'null')
			getElementByIdOrByName("DEM165").value = searchAddressCounties;
			
		// Telephone isn't passed..
		//var searchTelephone = '<%=request.getAttribute("telephoneOne")%>';

			
		var searchEthnicity = '<%=request.getAttribute("CDM-spanishOrigin")%>';
		if (searchEthnicity != 'null')
			getElementByIdOrByName("DEM155").value = searchEthnicity;

			var searchAsian = '${fn:escapeXml(asianController)}';
		if (searchAsian != 'null' && searchAsian != '') {
			$j('#asianRace').attr('checked', true);
		}

      		var searchAmericanIndian = '${fn:escapeXml(americanIndianController)}';
		if (searchAmericanIndian != 'null' && searchAmericanIndian != '') {
			$j('#americanIndianRace').attr('checked', true);
		}
		
		var searchAfrican = '${fn:escapeXml(africanAmericanController)}';
		if (searchAfrican != 'null' && searchAfrican!='') {
			$j('#africanRace').attr('checked', true);
		}
		
		var searchHawaiian = '${fn:escapeXml(hawaiianController)}';
		if (searchHawaiian != 'null' && searchHawaiian!='') {
			$j('#hawaiianRace').attr('checked', true);
		}

		var searchWhite = '${fn:escapeXml(whiteController)}';
		if (searchWhite != 'null' && searchWhite!='') {
			$j('#whiteRace').attr('checked', true);
		}		
		var searchUnknown = '${fn:escapeXml(unknownRace)}';
		if (searchUnknown != 'null' && searchUnknown!='') {
			$j('#unknownRace').attr('checked', true);
		}
		var searchOtherRace = '<%=request.getAttribute("otherRace")%>';
		if (searchOtherRace != 'null') {
			$j('otherRace').attr('checked', true);
		}
		var searchRefusedToAnswer = '<%=request.getAttribute("refusedToAnswer")%>';
		if (searchRefusedToAnswer != 'null') {
			$j('refusedToAnswer').attr('checked', true);
		}		
		
		
        }
        
        // if an ID is present we will have a DWR so we do this last and not in 
        // the populateFromSearchCriteria function
        function pullInIdFromSearch()
        {
        	
        	var typeOne = '<%=request.getAttribute("typeOne")%>';
		var idValueOne = '<%=request.getAttribute("idValueOne")%>';
		if (typeOne == 'null' || idValueOne == 'null')
			return;
			
		//mark it selected
		$j("#typeID").val( typeOne).attr('selected',true);
		$j("#idValue").val( idValueOne);
		AutocompleteComboBox('typeID_textbox','typeID',true,true);
		if (IdSubSectionBatchValidateFunction())
			writeBatchIdEntry('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
        	
        }	
        	
        	
        	
        	
        	
        	
        	
        //
	// hide or unhide field based on value
	// fromBoolField - must be Y,N,UNK field
	// toTextField - text field to enable or disable
	//
	function hideOrShowTarget(fromBoolField,toTextField) 
	{
		 var fromBoolNode = getElementByIdOrByName(fromBoolField);
		 var toNode = $j("#"+toTextField);   
		//alert("Selected value:" + fromBoolNode.value);
		if (fromBoolNode==null) {
			return;
		}
	
		if (fromBoolNode.value!="Y") {
			//not Yes - hide target
				$j("#" + toTextField+"L").css("color", "#666666");
				$j(toNode).parent().parent().find(":input").val("");
				$j(toNode).parent().parent().find(":input").attr("disabled", true);
				$j(toNode).parent().parent().find("img").attr("disabled", true);
				$j(toNode).parent().parent().find("img").attr("tabIndex", "-1");
			} else {
				//show target
				$j("#" + toTextField+"L").css("color", "#000");
				$j(toNode).parent().parent().find(":input").attr("disabled", false);
				$j(toNode).parent().parent().find("img").attr("disabled", false);
				$j(toNode).parent().parent().find("img").attr("tabIndex", "0");
			}

	}
        

        function IdSubSectionBatchValidateFunction() 
        {
        	// type and value are required, assigning authority is not
        	var errorLabels = new Array();
        	
        	var typeDescTxtNode = getElementByIdOrByName('typeID_textbox');
        	var typeId = jQuery.trim(typeDescTxtNode.value);
        	
        	var idValueNode = getElementByIdOrByName('idValue');
        	var idValue = jQuery.trim(idValueNode.value);
        	
        	if (typeId == 'null' || typeId == "")
        		errorLabels.push("Type is a required field.");
        	if (idValue == 'null' || idValue == "")
        		errorLabels.push("ID Value is a required field.");
        	if (errorLabels.length > 0) {
        		displayErrors('IdSubSectionerrorMessages', errorLabels); 
        		return false;
        	}
		$j('#IdSubSectionerrorMessages').css("display", "none");
		return true;
		
        }
        
        function getBatchAnswerMapFromScreen()
        {
                var ansMap = {};
        	var typeDescNode = getElementByIdOrByName('typeID');
        	var typeDescTxtNode = getElementByIdOrByName('typeID_textbox');
        	if (typeDescNode != null) {
        		var typeID = typeDescNode.value;
        		ansMap['typeID'] = typeID;
        		var typeIDTxt = typeDescTxtNode.value;
        		ansMap['typeIDTxt'] = typeIDTxt;
        		//alert("typeID=" + typeID + " TypeIDTxt=" + typeIDTxt);
        	}
        	
        	
        	
        	var assigningAuthorityNode = getElementByIdOrByName('assigningAuthority');
        	var assigningAuthorityTxtNode = getElementByIdOrByName('assigningAuthority_textbox');
        	if (assigningAuthorityNode != null) {
        		var assigningAuthority = assigningAuthorityNode.value;
        		ansMap['assigningAuthority'] = assigningAuthority;
        		var assigningAuthorityTxt = assigningAuthorityTxtNode.value;
        		ansMap['assigningAuthorityTxt'] = assigningAuthorityTxt;
        		//alert("assigningAuthority="+assigningAuthority +"Txt="+assigningAuthorityTxt);
        	}
        	
        	
        	var idValueNode = getElementByIdOrByName('idValue');
        	if (idValueNode != null) {
        		var idValue = idValueNode.value;
        		ansMap['idValue']=idValue;
        		//alert("value=" + idValue);
        	}
        	        
        
        	return ansMap;
        }
        
        
        
        function writeBatchIdEntry( subSectNm,pattern,questionbody)
        {
        	var t = getElementByIdOrByName("IdSubSection");
       	 	//t.style.display = "block";
        	for (var i = 0; i < 4; i++){
        	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
        	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
         	}

        	
        	var seqNum = 0;
        	var ansMap = getBatchAnswerMapFromScreen();
        	       	
        	
         	dwr.engine.beginBatch();
         	
         	var batchentry = { subSectNm:subSectNm, answerMap:ansMap }; 
         	JDSPersonForm.setBatchAnswerFromAdd(batchentry);
         	
         	
         	rewriteBatchIdHeader(subSectNm, pattern, questionbody);
         	
         	clearBatchEntryFields(subSectNm);
         	
        	dwr.engine.endBatch();
        	
        }
        
        
    	function removePSIDOptionIfNoPermission(){

    		JDSPersonForm.checkPermissionSTDHIVProgramArea(function(data){
    			
    			if(!data)//doesn't have permissions
    				$j("#typeID option[value='PSID']").remove();
    		});

    	}
    	
        
        function rewriteBatchIdHeader(subSectNm, pattern, questionbody) 
  	{

            //get all rows of data
	    JDSPersonForm.getAllBatchAnswer(subSectNm,function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows(questionbody, { filter:function(tr) {
				return (tr.id != pattern);
		}});	
		
		if (answer.length == 0) {
			//no rows - display 'No Data has been entered'
		 	$('nopattern' +subSectNm).style.display = "";
		 	return;
		 }
		

		for (var i = 0; i < answer.length; i++){
			ans = answer[i];
			id = ans.id;
			//alert("id=" + id);
			dwr.util.cloneNode(pattern, { idSuffix:id }); 
		      //pull the data for each row
		      for (var key in ans.answerMap) {
		      	if (key == 'typeIDTxt' || key == 'assigningAuthorityTxt' || key == 'idValue') {
		      		var val = ans.answerMap[key];
		      		//alert("node=table" + key + id + ' val='+val);
				dwr.util.setValue("table" + key + id, val);
				 
			}
		     }
		     //clear display = 'none'
		     $(pattern + id).style.display = ""; 
		     //hide No Data Entered
		     $('nopattern' +subSectNm).style.display = "none";
		}		
			
	    }); //all rows of data

    	}
     
        function clearBatchEntryFields (subSectNm)
  	{
  		gBatchEntryUpdateSeq = "";
  		$j($j("#typeID").parent().parent()).find(":input").val("");
		$j($j("#assigningAuthority").parent().parent()).find(":input").val("");
     		$j("#idValue").parent().find(":input").val("");
     		$j($j("#typeID").parent().parent()).find(":input").focus();
     		
        }
        
       function disableBatchEntryFields ()
	{
	  	gBatchEntryFieldsDisabled = true;	
	  	$j($j("#typeID").parent().parent()).find(":input").attr("disabled", true);
	  	$j($j("#typeID").parent().parent()).find("img").attr("disabled", true);
	  	$j($j("#typeID").parent().parent()).find("img").attr("tabIndex", "-1");
	  	
	  	$j("#typeIDL").parent().find("span[title]").css("color", "#666666");
		$j($j("#assigningAuthority").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#assigningAuthority").parent().parent()).find("img").attr("disabled", true);
		$j($j("#assigningAuthority").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#assigningAuthorityL").parent().find("span[title]").css("color", "#666666");
	     	$j("#idValue").parent().find(":input").attr("disabled", true);
	     	$j("#idValueL").parent().find("span[title]").css("color", "#666666");
	    	$j("#AddButtonToggleIdSubSection").hide();
	    	$j("#AddNewButtonToggleIdSubSection").show();
		$j("#UpdateButtonToggleIdSubSection").hide();
        }
        
       function enableBatchEntryFields ()
	{
	  	gBatchEntryFieldsDisabled = false;	
	  	$j($j("#typeID").parent().parent()).find(":input").attr("disabled", false);
	  	$j($j("#typeID").parent().parent()).find("img").attr("disabled", false);
	  	$j($j("#typeID").parent().parent()).find("img").attr("tabIndex", "0");
	  	
	  	$j("#typeIDL").parent().find("span[title]").css("color", "#000");
		$j($j("#assigningAuthority").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#assigningAuthority").parent().parent()).find("img").attr("disabled", false);
		$j($j("#assigningAuthority").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#assigningAuthorityL").parent().find("span[title]").css("color", "#000");
	     	$j("#idValue").parent().find(":input").attr("disabled", false);
	     	$j("#idValueL").parent().find("span[title]").css("color", "#000");
	     	$j("#AddButtonToggleIdSubSection").show();
		$j("#AddNewButtonToggleIdSubSection").hide();
		$j("#UpdateButtonToggleIdSubSection").hide();
		clearBatchEntryFields ('idSubSection');
        }
      
        
        function deleteClicked(entryId,subSection,pattern,questionBody)
        {
        	if (gBatchEntryFieldsDisabled == true) enableBatchEntryFields();
        	//alert("Delete Entry Id=" +entryId + " subSection=" + subSection + " pattern=" + pattern + "questionBody=" + questionBody); 
		var beIdStr = entryId.match(/\d+/)[0];
		
		if (beIdStr == 'null' || beIdStr == "")
			return;
			
		//alert("Deleting " + beIdStr);
		var batchentry = { subsecNm:subSection, id:beIdStr };
		JDSPersonForm.deleteBatchAnswer(batchentry);

		rewriteBatchIdHeader(subSection, pattern, questionBody);
	     	$j("#AddButtonToggleIdSubSection").show();
		$j("#AddNewButtonToggleIdSubSection").hide();
		$j("#UpdateButtonToggleIdSubSection").hide();
		clearBatchEntryFields (subSection);		

        } 
        
        function editClicked(entryId,subSection)
        {
       	var t = getElementByIdOrByName("IdSubSection");
   	 	//t.style.display = "block";
   		for (var i = 0; i < 4; i++){
   	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
   	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
      		}
	     
         if (gBatchEntryFieldsDisabled == true) enableBatchEntryFields();
        	//alert(" Edit Batch Entry Id=" +entryId + " subSection=" + subSection); 
		var beIdStr = entryId.match(/\d+/)[0];
		
		if (beIdStr == 'null' || beIdStr == "")
			return;
			
           //get all rows of data
	    JDSPersonForm.getAllBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
					gBatchEntryUpdateSeq = id;
			        	var typeDescNode = getElementByIdOrByName('typeID');
			        	var typeDescTxtNode = getElementByIdOrByName('typeID_textbox');
			        	if (typeDescNode != null) {
			        		typeDescNode.value = ans.answerMap['typeID'];
			        		typeDescTxtNode.value = ans.answerMap['typeIDTxt'];
			        		//alert("typeID=" + ans.answerMap['typeID']);
			        	}
			        	
			        	
			        	
			        	var assigningAuthorityNode = getElementByIdOrByName('assigningAuthority');
			        	var assigningAuthorityTxtNode = getElementByIdOrByName('assigningAuthority_textbox');
			        	if (assigningAuthorityNode != null) {
			        		assigningAuthorityNode.value = ans.answerMap['assigningAuthority'];
			        		assigningAuthorityTxtNode.value = ans.answerMap['assigningAuthorityTxt'];
			        	}
			        	
			        	
			        	var idValueNode = getElementByIdOrByName('idValue');
			        	if (idValueNode != null) {
			        		idValueNode.value = ans.answerMap['idValue'];
			        	}
			        $j("#AddButtonToggleIdSubSection").hide();
			        $j("#UpdateButtonToggleIdSubSection").show();
			        	
				break;
			}
		}		
			
	    }); //all rows of data


        } 
       function updateBatchIdEntry( subSectNm,pattern,questionbody)
       {
    	  	var t = getElementByIdOrByName("IdSubSection");
      	 	//t.style.display = "block";
       		for (var i = 0; i < 4; i++){
       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
        	}
	      	var ansMap = getBatchAnswerMapFromScreen();
	      	
			dwr.engine.beginBatch();
			
			var batchentry = { subSectNm:subSectNm, id:gBatchEntryUpdateSeq, answerMap:ansMap }; 
			
			JDSPersonForm.updateBatchAnswer(batchentry);
			rewriteBatchIdHeader(subSectNm, pattern, questionbody);
			clearBatchEntryFields(subSectNm);
	      	dwr.engine.endBatch();
	      	$j("#AddButtonToggleIdSubSection").show();
			$j("#UpdateButtonToggleIdSubSection").hide();
        	
       } 
        
       function clearBatchIdEntryFields(subSection) 
       {
       		enableBatchEntryFields();
       		clearBatchEntryFields(subSection);
       		$j("#AddButtonToggleIdSubSection").show();
       		$j("#AddNewButtonToggleIdSubSection").hide();
		$j("#UpdateButtonToggleIdSubSection").hide();
	}	
       
       function viewClicked( entryId,subSection,pattern,questionbody)
       {
    	   var t = getElementByIdOrByName("IdSubSection");
       	 	//t.style.display = "block";
       	 for (var i = 0; i < 4; i++){
       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
        	}	
        	//alert(" View Batch Entry Id=" +entryId + " subSection=" + subSection); 
		var beIdStr = entryId.match(/\d+/)[0];
		if (beIdStr == 'null' || beIdStr == "")
			return;
			
	    if (gBatchEntryFieldsDisabled == true) enableBatchEntryFields();
	    
           //get all rows of data
	    JDSPersonForm.getAllBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
					gBatchEntryUpdateSeq = id;
			        	var typeDescNode = getElementByIdOrByName('typeID');
			        	var typeDescTxtNode = getElementByIdOrByName('typeID_textbox');
			        	if (typeDescNode != null) {
			        		typeDescNode.value = ans.answerMap['typeID'];
			        		typeDescTxtNode.value = ans.answerMap['typeIDTxt'];
			        		//alert("typeID=" + ans.answerMap['typeID']);
			        	}
			        	
			        	
			        	
			        	var assigningAuthorityNode = getElementByIdOrByName('assigningAuthority');
			        	var assigningAuthorityTxtNode = getElementByIdOrByName('assigningAuthority_textbox');
			        	if (assigningAuthorityNode != null) {
			        		assigningAuthorityNode.value = ans.answerMap['assigningAuthority'];
			        		assigningAuthorityTxtNode.value = ans.answerMap['assigningAuthorityTxt'];
			        	}
			        	
			        	
			        	var idValueNode = getElementByIdOrByName('idValue');
			        	if (idValueNode != null) {
			        		idValueNode.value = ans.answerMap['idValue'];
			        	}

				disableBatchEntryFields();
				break;
			}
		}		
	   }); //all rows of data
        	
     }  //viewClicked	
    
    
    //when the State is changed, update the counties
    function getDWRPatientCounties(state, elementId)
    {
    	var state1 = state.value;
    	if(state1 == null)  state1= state;
    
     	JDSPersonForm.getDwrCountiesForState(state1, function(data) {
	        DWRUtil.removeAllOptions(elementId);
	        getElementByIdOrByName(elementId + "_textbox").value="";
	        DWRUtil.addOptions(elementId, data, "key", "value" );
      	});  

     }

     function unhideBatchImg()
     {
    	 
    	 var t = getElementByIdOrByName("IdSubSection");
    	 //t.style.display = "block";
    	 //alert($j(t).html());
    	// alert($j($j(t).find("TBODY").get(0)).html()); 
    	 for (var i = 0; i < 4; i++){
    	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
    	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
     	}
     }

 	function hideBatchImg(typeVal, assignVal, idValVal)
 	{
 		//alert("typeId:"+typeVal.value+",assignAuth:"+assignVal.value+",idVal:"+idValVal.value);
 		var assigningAuthorityTxtNode = getElementByIdOrByName('assigningAuthority_textbox');
		var idValueNode = getElementByIdOrByName('idValue');
		var typeDescTxtNode = getElementByIdOrByName('typeID_textbox');

		var typeId = jQuery.trim(typeDescTxtNode.value);
       	var assignAuth = jQuery.trim(assigningAuthorityTxtNode.value);
       	var idVal = jQuery.trim(idValueNode.value);
       	
		if(typeVal.value != null && typeVal.value != "" && typeVal.value != 'undefined'){
			typeId= typeVal.value;
		}else if(typeVal.value != null && typeVal.value == "" && typeVal.value != 'undefined')
			typeId= typeVal.value;
		if(assignVal.value != null && assignVal.value != "" && assignVal.value != 'undefined')
			assignAuth= assignVal.value;
		else if(assignVal.value != null && assignVal.value == "" && assignVal.value != 'undefined')
			assignAuth= assignVal.value;
		
		if(idValVal.value != null && idValVal.value != "")
			idVal= idValVal.value;
       	
       //	alert("typeId:"+typeId+",assignAuth:"+assignAuth+",idVal:"+idVal);
       	if ( (typeId == null || typeId == "") && (assignAuth == null || assignAuth == "") &&
       		(idVal == null || idVal == "") ) {
        	var t = getElementByIdOrByName("IdSubSection");
       	 	t.style.display = "block";
        	for (var i = 0; i < 4; i++){
        	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
        	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
         	}
       	}
 	}
 	

     </script>  
            
         <style type="text/css">
                table.FORM {width:100%; margin-top:15em;}
                div.printexport { 
					width:100%; 
					border:1px solid #DDD; 
					margin:0.5em 0em 0.5em 0em;
					background: #efefef; 
					border-color:none; 
					border-style:solid; 
					font: bold 78% verdana;
					font-size:12px; 
					font-weight:bold;
					padding: 2px;
					border-left-style: outset;
					border-bottom-style: outset
				}
                
         </style>
    </head>
    <body class onload="startCountdown();initializePatientBasic();populateFromSearchCriteria();
    hideOrShowTarget('DEM127','deceasedDate');autocompTxtValuesForJSP();pullInIdFromSearch();
    $j('#DEM102').parent().find(':input').focus();removePSIDOptionIfNoPermission();">
            <div id="pageview"></div>
            <!-- Container Div: To hold top nav bar, button bar, body and footer -->
            <div id="doc3">
                <html:form>
                    <!-- Body div -->
                    <div id="bd">
                        <!-- Top Nav Bar and top button bar -->
                        <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>                 

                            <!-- top button bar -->
                             <div class="printexport" id="printExport" align="right">
								<input type="button" id="Submit" name="Submit" value="Submit" style="font-weight:normal" onclick="return saveForm();"/>
				                <input type="button" name="Cancel" value="Cancel" style="font-weight:normal" onclick="return cancelForm();" />
				                <input type="button" name="Submit" value="Add Extended Data" style="width: 130px; font-weight:normal"  onclick="return addExtendedForm();" />
							</div>	
                
                           <!-- Page Errors -->
                           <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
  <table role="presentation" width="100%"  border="0" align="center">
 <tr>
 <td   width="100%">
 <div class="infoBox errors" style="display: none;visibility: none;" id="PatientSubmitErrorMessages">
 <b> <a name="PatientSubmitErrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
 </div> 
 </tr></table>
 
   <table role="presentation"  display="none"> 					   
	<tr>
		<td><input id="today" type="hidden" value=""/></td>
		<td><input id="calcDOB" type="hidden" name="person.thePersonDT.birthTimeCalc_s" value=""/></td>
	</tr>
   </table>
    <nedss:container id="demographicSection" name="Basic Demographic Data" classType="sect" includeBackToTopLink="no">      
    <nedss:container id="top_subsection" name="General Information" isHidden="F" classType="subSect" >	

	<!--General As Of Date-->
	<tr>
	<td class="fieldName" align="left" width="150">
	<font class="boldTenRed" > * </font><span class="InputFieldLabel" id="DEM209L" title="As of Date is the last known date for which the information is valid.">
	Information As of Date:</span>
	</td>
	<td>
	<html:text title="Information As of Date" name="DSPersonForm" property="patientAsOfDateGeneral" maxlength="10" size="10" styleId="DEM209" onkeyup="DateMask(this,null,event)"/>
	<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM209','DEM209Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM209','DEM209Icon',event);" styleId="DEM209Icon"></html:img>
	
	</td>
	</tr>

		
	<!-- General Comments -->
	<tr align="left">
	<td class="fieldName">
	 <span class="InputFieldLabel" id="DEM196L" title="General comments pertaining to the patient.">
	Comments:</span>
	</td>
	<td>
	<html:textarea title="General Information Comments" style="WIDTH: 500px; HEIGHT: 100px;" name="DSPersonForm" property="person.thePersonDT.description" styleId ="DEM196" onkeyup="checkTextAreaLength(this, 2000)"/>
	</td>
	</tr>
		
    </nedss:container>
    <nedss:container id="name_subsection" name="Name Information" isHidden="F" classType="subSect" >
	
	<!-- Last Name -->
	<tr> 
	<td class="fieldName">
	<span class="InputFieldLabel" id="DEM102L" title="The patient's last name.">
	Last Name:</span>
	</td>
	<td>
	<html:text name="DSPersonForm" property="person.thePersonDT.lastNm" size="50" maxlength="50" title="The patient's last name." styleId="DEM102"/>
	</td></tr>
	
	
	<!-- First Name -->
	<tr>
	<td class="fieldName" align="left" width="200">
	<span class="InputFieldLabel" id="DEM104L" title="The patient's First name.">
	First Name:</span>
	</td>
	<td>
	<html:text name="DSPersonForm" property="person.thePersonDT.firstNm" size="50" maxlength="50" title="The patient's first name." styleId="DEM104"/>
	</td></tr>
	
	<!-- Middle Name -->
	<tr> 
	<td class="fieldName">
	<span class="InputFieldLabel" id="DEM105L" title="The patient's middle name.">
	Middle Name:</span>
	</td>
	<td>
	<html:text name="DSPersonForm" property="person.thePersonDT.middleNm" size="50" maxlength="50" title="The patient's middle name." styleId="DEM105"/>
	</td></tr>
	
	<!-- Suffix -->
	<tr>
	<td class="fieldName">
	<span class=" InputFieldLabel" id="DEM107L" title="The patient's name suffix">
	Suffix:</span>
	</td>
	<td>
	<html:select title="The patient's suffix" name="DSPersonForm" property="person.thePersonDT.nmSuffix" styleId="DEM107">
	<html:optionsCollection name="DSPersonForm" property="codedValue(P_NM_SFX)" value="key" label="value" /></html:select>
	</td></tr>
	</nedss:container>
   <nedss:container id="otherDetails_subsection" name="Other Personal Details" isHidden="F" classType="subSect" >
	
 	<!-- DOB -->
 	<tr><td class="fieldName">
	<span class="InputFieldLabel" id="DEM115L" title="Reported date of birth of patient">
	DOB:</span>
	</td>
	<td>
	<html:text  name="DSPersonForm" title="Reported date of birth of patient" property="patientBirthTime" maxlength="10" size="10" styleId="patientDOB" onkeyup="DateMask(this,null,event)" onblur="updatePatientDOBInfo(this);" onchange="updatePatientDOBInfo(this);"/>
	<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('patientDOB','patientDOBIcon'); return false;" onkeypress ="showCalendarEnterKey('patientDOB','patientDOBIcon',event);" styleId="patientDOBIcon"></html:img>
	</td>
	</tr>
	<!-- Current Age -->
 	<tr><td class="fieldName">
	<span class="InputFieldLabel" id="currentAgeLabel" title="Current age of the patient">
	Current Age:</span></td>
	<td><span id="currentAge" type="text"></span>
	<span id="currentAgeUnits" type="select"></span><input type="hidden" id="listcurrentAgeUnits" value="asd$asdas|D$Days|H$Hours|N$Minutes|M$Months|UNK$unknown|W$Weeks|Y$Years|"></td>
	<!-- AGE_UNIT -->
	</td>
	</tr>

	<!-- Sex -->
	<tr><td class="fieldName">
	<span class=" InputFieldLabel" id="DEM113L" title="Patient's current sex.">
	Current Sex:</span>
	</td>
		<td>
		<html:select title="The patient's current sex" name="DSPersonForm" property="person.thePersonDT.currSexCd" styleId="DEM113" onchange="pgSelectNextFocus(this);">
		<html:optionsCollection name="DSPersonForm" property="codedValue(SEX)" value="key" label="value" /></html:select>
		</td></tr>
	<!--Birth Sex -->
		<tr>
			<td class="fieldName"><span title="Patient Birth Sex"
				id="BirSexL">Birth Sex:</span></td>
			<td><html:select title="Patient Birth Sex"
					name="DSPersonForm" property="person.thePersonDT.birthGenderCd"
					styleId="DEM114" onchange="pgSelectNextFocus(this);">
					<html:optionsCollection name="DSPersonForm"
						property="codedValue(SEX)" value="key" label="value" />
				</html:select></td>
		</tr>
		<!-- Deceased? -->
	<tr><td class="fieldName">
	<span class=" InputFieldLabel" id="DEM127L" title="Indicator of whether or not a patient is alive or dead.">
	Is the patient deceased?</span>
	</td>
	<td>
	<html:select title="Is the patient deceased?" name="DSPersonForm" property="person.thePersonDT.deceasedIndCd" styleId="DEM127" onchange="hideOrShowTarget('DEM127','deceasedDate');pgSelectNextFocus(this);">
	<html:optionsCollection name="DSPersonForm" property="codedValue(YNU)" value="key" label="value" /></html:select>
	</td></tr>
 	<tr><td class="fieldName">
	<span class="InputFieldLabel" id="deceasedDateL" title="The date the patient died.">
	Date of Death:</span></td>
	<td><html:text  title="Date of Death" name="DSPersonForm" styleId="deceasedDate" property="patientDeceasedDate" size="10" maxlength="10" onkeyup="DateMask(this,null,event)"/>
	     <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('deceasedDate','deceasedDateIcon'); return false;"  onkeypress ="showCalendarEnterKey('deceasedDate','deceasedDateIcon',event);" styleId="deceasedDateIcon"></html:img>
	</td>
	</tr>
	<!-- Marital -->
	<tr><td class="fieldName">
	<span class=" InputFieldLabel" id="DEM140L" title="A code indicating the married or similar partnership status of a patient.">
	Marital Status:</span>
	</td>
	<td>
	<html:select title="The patient's marital status" name="DSPersonForm" property="person.thePersonDT.maritalStatusCd" styleId="DEM140">
	<html:optionsCollection name="DSPersonForm" property="codedValue(P_MARITAL)" value="key" label="value" /></html:select>
	</td></tr>
	<logic:equal name="DSPersonForm" property="securityMap.hasHIVPermissions" value="T">
		<!-- State HIV Case ID -->
		<tr><td class="fieldName">
		<span class=" InputFieldLabel" id="eHARSIDL" title="State HIV Case ID">
		State HIV Case ID:</span>
		</td>
		<td>
		<html:text title = "State HIV Case ID" name="DSPersonForm" styleId="eHARSID" property="person.thePersonDT.eharsId" size="16" maxlength="16"/>
		</td></tr>				
	</logic:equal>
	
    </nedss:container>
 
    <nedss:container id="addressSubsection" name="Address" isHidden="F" classType="subSect">	
     <!-- Street Address 1 -->
         <tr>
          <td class="fieldName"> 
          <span class="InputFieldLabel" id="DEM159L" title="Line one of the address label."> 
             Street Address 1: 
          </span></td> 
           <td> 
           <html:text name="DSPersonForm" property="address[0].thePostalLocatorDT_s.streetAddr1" styleId="DEM159"
            title="Line one of the address label." size="30" maxlength="100"/>
           </td>
          </tr>
            <!-- Street Address 2 streetAddr2 DEM160 -->
         <tr>
             <td class="fieldName"> 
             <span class="InputFieldLabel" id="DEM160L" title="Line two of the address label."> 
                Street Address 2: 
             </span></td> 
           <td> 
             <html:text name="DSPersonForm" property="address[0].thePostalLocatorDT_s.streetAddr2" styleId="DEM160"
               title="Line two of the address label." size="30" maxlength="100"/>
            </td>
          </tr>
    
          <!-- City DEM161 cityDescTxt -->
         <tr>
         <td class="fieldName"> 
         <span class="InputFieldLabel" id="DEM161L" title="The city for a postal location."> 
          City: 
          </span></td>
         <td> 
          <html:text name="DSPersonForm" property="address[0].thePostalLocatorDT_s.cityDescTxt" styleId="DEM161" 
           title="The city for a postal location." size="30" maxlength="100"/>
          </td>
          </tr>
          <tr>
        <!-- State DEM162 -->
	<td class="fieldName">
	<span class=" InputFieldLabel" id="DEM162L" title="The state code for a postal location.">
	State:</span></td>
	<td>
	  <html:select title="The state for a postal location" name="DSPersonForm" property="address[0].thePostalLocatorDT_s.stateCd" styleId="DEM162" onchange="getDWRPatientCounties(this, 'DEM165');">
	  <html:optionsCollection name="DSPersonForm" property="stateList" value="key" label="value"/></html:select>
	</td>
	</tr>
	
           <!-- Zip DEM163 -->
         <tr>
         <td class="fieldName"> 
         <span class="InputFieldLabel" id="DEM163L" title="The zip code of a residence of the case patient or entity."> 
          Zip: 
          </span></td>
         <td> 
          <html:text name="DSPersonForm" property="address[0].thePostalLocatorDT_s.zipCd" styleId="DEM163" 
               size="10" maxlength="10" title="The zip code of a residence of the case patient or entity." onkeyup="ZipMask(this,event)"/>
          </td>
          </tr>
          <tr>
        <!-- County DEM165 -->
	<td class="fieldName">
	<span class=" InputFieldLabel" id="DEM165L" title="The county of residence of the case patient or entity.">
	County:</span></td>
	<td>
	  <html:select title="The county of a postal location" name="DSPersonForm" property="address[0].thePostalLocatorDT_s.cntyCd" styleId="DEM165">
	  <html:optionsCollection name="DSPersonForm" property="dwrDefaultStateCounties" value="key" label="value"/></html:select>
	</td>
	</tr>
	
	<!-- Census Tract DEM168 -->
         <tr>
         <td class="fieldName"> 
         <span class="InputFieldLabel" id="DEM168L" title="The Census Tract for a postal location."> 
          Census Tract: 
          </span></td>
         <td> 
          <html:text name="DSPersonForm" property="address[0].thePostalLocatorDT_s.censusTract" styleId="DEM168" 
           title="The Census Tract for a postal location." size="13" maxlength="7"/>
          </td>
          </tr>
        <!-- Country DEM167 -->
	<td class="fieldName">
	<span class=" InputFieldLabel" id="DEM167L" title="The country code for a postal location.">
	Country:</span></td>
	<td>
	  <html:select title="The country of a postal location" name="DSPersonForm" property="address[0].thePostalLocatorDT_s.cntryCd" styleId="DEM167">
	  <html:optionsCollection name="DSPersonForm" property="countryList" value="key" label="value"/></html:select>
	</td>
	</tr>
           
   </nedss:container>
   
   <nedss:container id="telephoneSubsection" name="Telephone" isHidden="F" classType="subSect">
        <!-- Home Phone telephone[0].theTeleLocatorDT_s.phoneNbrTxt -->
          <tr>
          <td class="fieldName"> 
          <span class="InputFieldLabel" id="DEM177L" title="Telephone number (including area code)."> 
           Home Phone: 
          </span></td> 
           <td> 
           <html:text name="DSPersonForm" property="patientHomePhone" styleId="DEM177" onkeyup="TeleMask(this,event)"
            title="The patient's home phone number." size="13" maxlength="13"/>
           </td>
          </tr>
         <!-- Work Phone -->
          <tr>
          <td class="fieldName"> 
          <span class="InputFieldLabel" id="NBS002L" title="The patient's work phone number."> 
           Work Phone: 
          </span></td> 
           <td> 
           <html:text name="DSPersonForm" property="patientWorkPhone" styleId="NBS002" onkeyup="TeleMask(this,event)"
            title="The patient's work phone number." size="13" maxlength="13" />
           </td>
          </tr>
           <!-- Work Phone Ext -->
          <tr>
          <td class="fieldName"> 
          <span class="InputFieldLabel" id="NBS003L" title="The patient's work phone number extension."> 
           Work Phone Ext: 
          </span></td> 
           <td> 
           <html:text name="DSPersonForm" property="patientWorkPhoneExt" styleId="NBS003" onkeyup="isNumericCharacterEntered(this)"
            title="The patient's work phone number extension." size="8" maxlength="8"/>
           </td>
          </tr>     
           <!-- Cell Phone -->
          <tr>
          <td class="fieldName"> 
          <span class="InputFieldLabel" id="NBS006L" title="The patient's cellular phone number.">
           Cell Phone: 
          </span></td> 
           <td> 
           <html:text name="DSPersonForm" property="patientCellPhone" styleId="NBS006" onkeyup="TeleMask(this,event)" 
            title="The patient's cell phone number." size="13" maxlength="13"/>
           </td>
          </tr>         
          <!-- Email -->
          <tr>
          <td class="fieldName"> 
          <span class="InputFieldLabel" id="DEM182L" title="The patient's email address."> 
           Email: 
          </span></td> 
           <td> 
           <html:text name="DSPersonForm" property="patientEmail" styleId="DEM182" onblur="checkEmail(this)" styleClass="emailField"
            title="The patient's email address." size="50" maxlength="50" />
           </td>
          </tr>         
    
     </nedss:container>
     <nedss:container id="raceSubsection" name="Ethnicity and Race Information" isHidden="F" classType="subSect">
     
<!--Ethnicity  -->
	<tr><td class="fieldName">
	<span class=" InputFieldLabel" id="DEM155L" title="Indicates if the patient is hispanic or not.">
	Ethnicity:</span>
	</td>
	<td>
	<html:select title="Ethnicity" name="DSPersonForm" property="person.thePersonDT.ethnicGroupInd" styleId="DEM155" onchange="pgSelectNextFocus(this);">
	<html:optionsCollection name="DSPersonForm" property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
	</td></tr>
<!--processing Checkbox Coded Question-->
	<tr>
	<td class="fieldName">
	<span title="Reported race; supports collection of multiple race categories.  This field could repeat.">
	Race:</span>
	</td>
	<td>
	<html:checkbox  name="DSPersonForm" property="pamClientVO.americanIndianAlskanRace" value="1" styleId="americanIndianRace"
	title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox> <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>
	</td>
	</tr>
	<tr>
	<td class="fieldName">
	&nbsp;
	</td>
	<td>
	<html:checkbox  name="DSPersonForm" property="pamClientVO.asianRace" value="1" styleId="asianRace"
	title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.asian"/>
	</td>
	</tr>
	<tr>
	<td class="fieldName">
	&nbsp;
	</td>
	<td>
	<html:checkbox  name="DSPersonForm" property="pamClientVO.africanAmericanRace" value="1" styleId="africanRace"
	title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>   <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>
	</td>
	</tr>
	<tr>
	<td class="fieldName">
	&nbsp;
	</td>
	<td>
	<html:checkbox  name="DSPersonForm" property="pamClientVO.hawaiianRace" value="1" styleId="hawaiianRace"
	title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox> <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/> 
	</td>
	</tr>
	<tr>
		<td class="fieldName">
		&nbsp;
		</td>
		<td>
		<html:checkbox  name="DSPersonForm" property="pamClientVO.whiteRace" value="1" styleId="whiteRace"
		title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.white"/>
		</td>
	</tr>
	<tr>
		<td class="fieldName">
		&nbsp;
		</td>
		<td>
		<html:checkbox  name="DSPersonForm" property="pamClientVO.otherRace" value="1" styleId="otherRace"
		title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.other"/>
		</td>
	</tr>
	<tr>
		<td class="fieldName">
		&nbsp;
		</td>
		<td>
		<html:checkbox  name="DSPersonForm" property="pamClientVO.refusedToAnswer" value="1" styleId="refusedToAnswer"
		title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>
		</td>
	</tr>
	<tr>
		<td class="fieldName">
		&nbsp;
		</td>
		<td>
		<html:checkbox  name="DSPersonForm" property="pamClientVO.notAsked" value="1" styleId="notAsked"
		title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.notAsked"/>
		</td>
	</tr>
	<tr>
		<td class="fieldName">
		&nbsp;
		</td>
		<td>
		<html:checkbox  name="DSPersonForm" property="pamClientVO.unKnownRace" value="1" styleId="unknownRace"
		title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.unknown"/>
		</td>
	</tr>
     </nedss:container>
     
     <!-- ########### SUB SECTION ###########  -->
 <nedss:container id="IdSubSection" name="Identification" isHidden="F" classType="subSect"  addedClass="batchSubSection">
 <tr> <td colspan="2" width="100%">
 <table role="presentation" width="90%"  border="0" align="center" style="margin: auto">
 <tr>
 <td   width="100%">
 <div class="infoBox errors" style="display: none;visibility: none;" id="IdSubSectionerrorMessages">
 <b> <a name="IdSubSectionerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
 </div>
 <table role="presentation"  class="dtTable" align="center" >
 <thead>
 <td width="10%" colspan="3" style="background-color: #EFEFEF; border:1px solid #666666"> &nbsp;</td>
 <th width="25%"><font color="black">Type</font></th>
 <th width="20%"><font color="black">Assigning Authority</font></th>
 <th width="45%"><font color="black">ID Value</font></th>
 </thead>
 
 <tbody id="questionbodyIdSubSection">
 <tr id="patternIdSubSection" class="odd" style="display:none">
 <td style="width:3%;text-align:center;">
 <input id="viewIdSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'IdSubSection','patternIdSubSection','questionbodyIdSubSection');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
 </td><td style="width:3%;text-align:center;">
 <input id="editIdSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'IdSubSection');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
 </td><td style="width:3%;text-align:center;">
 <input id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'IdSubSection','patternIdSubSection','questionbodyIdSubSection');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>


<td width="20%" align="left">
<span id="tabletypeIDTxt"> </span>
</td>
 
<td width="20%" align="left">
<span id="tableassigningAuthorityTxt"> </span>
</td>
 
<td width="50%" align="left">
<span id="tableidValue"> </span>
</td>
 
</tr>
</tbody>

 <tbody id="questionbodyIdSubSection">
 <tr id="nopatternIdSubSection" class="odd">
 <td colspan="21"> <span>&nbsp; No Data has been entered.
 </span>
 </td>
 </tr>
 </tbody>
 </table>
 </td>
 <td width="5%"> &nbsp; </td>
 </tr>
 </Table>
</td>
 
 	<!-- ID Type -->
 	<tr><td class="fieldName">
 	<span class=" InputFieldLabel" id="typeIDL" title="Type of Identification">
 	Type:</span>
 	</td>
 	<td>
 	<html:select title="Identification type" name="DSPersonForm" property="idTypeDescCd" styleId="typeID" onchange="unhideBatchImg();hideBatchImg(this,'','');">
 	<html:optionsCollection name="DSPersonForm" property="codedValue(EI_TYPE_PAT)" value="key" label="value" /></html:select>
 	</td></tr>
 	<!-- ID Assigning Authority -->
 	<tr><td class="fieldName">
 	<span class=" InputFieldLabel" id="assigningAuthorityL" title="Assigning authority for the Identification.">
 	Assigning Authority:</span>
 	</td>
 	<td>
 	<html:select title="Identification Assigning Authority" name="DSPersonForm" property="idAssigningAuthorityCd" styleId="assigningAuthority" onchange="unhideBatchImg();hideBatchImg('', this,'');">
 	<html:optionsCollection name="DSPersonForm" property="codedValue(EI_AUTH_PAT)" value="key" label="value" /></html:select>
 	</td></tr> 	
	<!-- Type -->
         <tr>
         <td class="fieldName"> 
         <span class="InputFieldLabel" id="idValueL" title="Identification value."> 
                ID Value: 
             </span></td> 
           <td> 
             <html:text name="DSPersonForm" property="idValue" styleId="idValue"
               title="Identification value." size="30" maxlength="100" onchange="unhideBatchImg();hideBatchImg('','', this);"/>
            </td>
          </tr>

	<tr id="AddButtonToggleIdSubSection">
	<td colspan="2" align="right">
	
	<input type="button" value="     Add ID     "  onclick="if (IdSubSectionBatchValidateFunction()) writeBatchIdEntry('IdSubSection','patternIdSubSection','questionbodyIdSubSection')"/>&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	</tr>

	<tr id="UpdateButtonToggleIdSubSection"
	style="display:none">
	<td colspan="2" align="right">
	<input type="button" value="   Update ID   "    onclick="if (IdSubSectionBatchValidateFunction()) updateBatchIdEntry('IdSubSection','patternIdSubSection','questionbodyIdSubSection')"/>&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>

	<tr id="AddNewButtonToggleIdSubSection"
	style="display:none">
	<td colspan="2" align="right">
	<input type="button" value="   Add New   "    onclick="clearBatchIdEntryFields('IdSubSection')"/>&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>

    </nedss:container>  
     
</nedss:container>
		                             
   <!-- SECTION : Local Fields -->
   <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
    <nedss:container id="LocalFieldsSection" name="Patient Custom Fields" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="LocalFieldsSubSection" name="Custom Fields" classType="subSect" >
        	${fn:escapeXml(NEWPAT_LDFS)}
        </nedss:container>
   </nedss:container>
    <%} %>

	</div> <!-- body div bd -->
				<!-- top button bar -->
    <div class="printexport" id="printExport" align="right">
		<input type="button" id="Submit" name="Submit" value="Submit" onclick="return saveForm();" style="font-weight: normal"/>
        <input type="button" name="Cancel" value="Cancel" onclick="return cancelForm();" style="font-weight: normal" />
        <input type="button" name="Cancel" style="width: 130px; font-weight: normal" value="Add Extended Data" onclick="return addExtendedForm();" />
	</div>

	    	
               </html:form>
          </div> <!-- Container Div  doc3 -->
    </body>
     
</html>