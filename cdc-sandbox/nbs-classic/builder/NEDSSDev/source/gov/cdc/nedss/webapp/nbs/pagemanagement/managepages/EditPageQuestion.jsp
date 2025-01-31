<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<html lang="en">
    <head>
    	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <base target="_self">
        <title>NBS: Manage Pages</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="/nbs/dwr/interface/JPageElementForm.js"></script>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        
        <style type="text/css">
            table.nedssBlueBG {background:#DCE7F7;}
        </style>
        
        <script type="text/javascript">
        
        	blockEnterKey();
        	
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
			var unblock=true;
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                 // get reference to opener/parent 
				if(unblock){			 
                    var opener = getDialogArgument();
                    
                    var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                    if (grayOverlay == null) {
                        grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                    }
                    grayOverlay.style.display = "none";
				}
                    if (e.clientY < 0 || closePopup == true) {

                        
                                                 
                        self.close();
                        return true;
                    }
                }
            }
            
            function submitForm()
            {
				
                var allEnabledSearchIpElts = $j("#editQuestionBlock").find(':input:enabled');
                var errorMsgArray = new Array();
                
                for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
                    if ($j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'button'
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'select-one'  
                        && jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) == "") 
                    {
                        var checkRequired = true;
                        
                        // handle exceptions where an input field is not required //
                        // adminComment.
                        if ($j(allEnabledSearchIpElts[i]).attr("id") == "adminComment") {
                            checkRequired = false;
                        }
                        //Data mart column name
						 if ($j(allEnabledSearchIpElts[i]).attr("id") == "dataMartColumnNm") {
                           checkRequired = false;
                        }
                        // default value for coded data type
                        else if ($j(allEnabledSearchIpElts[i]).attr("name") == "defaultValueCoded_textbox") {
                            checkRequired = false;
                        }
                        // default value, min value, max value for numeric
                        else if ($j(allEnabledSearchIpElts[i]).attr("id") == "defaultValueNumeric" ||
                            $j(allEnabledSearchIpElts[i]).attr("id") == "minValueNumeric" ||
                            $j(allEnabledSearchIpElts[i]).attr("id") == "maxValueNumeric") {
                            checkRequired = false;
                        }
                        // default value for text
                        else if ($j(allEnabledSearchIpElts[i]).attr("id") == "defaultValueText") {
                            checkRequired = false;
                        }
                        
                        // proceed only if check is required for this specific input element
                        if (checkRequired) {
                            var fieldLabel = $j($j(allEnabledSearchIpElts[i]).parent("td").parent("tr").find("span[title]").get(0)).attr("title");
							
                            var msg = fieldLabel + " is a required field." + "\n";
                            errorMsgArray.push(msg);
                            $j($j(allEnabledSearchIpElts[i]).parent("td").parent("tr").find("span[title]").get(0)).css("color", "#CC0000");
                        }
                        else {
                            continue;
                        }
                    }
                    else if (
                            ($j(allEnabledSearchIpElts[i]).attr("id") == "adminComment" ||
                                $j(allEnabledSearchIpElts[i]).attr("id") == "questionToolTip") &&
                            jQuery.trim($j(allEnabledSearchIpElts[i]).val()).length > 2000) {
                        var fieldLabel = $j($j(allEnabledSearchIpElts[i]).parent("td").parent("tr").find("span[title]").get(0)).attr("title");
                        var msg = fieldLabel + " cannot be more than 2000 characters." + "\n";
                        errorMsgArray.push(msg);
                        $j($j(allEnabledSearchIpElts[i]).parent("td").parent("tr").find("span[title]").get(0)).css("color", "#CC0000");
                    }
                    else {
                        $j($j(allEnabledSearchIpElts[i]).parent("td").parent("tr").find("span[title]").get(0)).css("color", "black");
                    }
                }
                
                
                
                //Default label in Report cannot cotain ,
                var defaultLabelReport = document.getElementsByName("pageEltVo.waUiMetadataDT.reportLabel");
                
                if(defaultLabelReport!=null && defaultLabelReport.length>0 && defaultLabelReport[0]!=null
                		&&defaultLabelReport[0].value!=null)
                	if(document.getElementsByName("pageEltVo.waUiMetadataDT.reportLabel")[0].value.indexOf(",")!=-1)
                		errorMsgArray.push("Default Label in Report cannot contain the special character ,"); 
					
                // verify the uniqueness of this name
                var opener = getDialogArgument();
                if($j("#dataMartColumnNm").val()!=undefined && $j("#dataMartColumnNm").val() !=null){
                	var value = $j("#dataMartColumnNm").val();
                	if (value.match(/^\d/))
                		errorMsgArray.push("Data Mart Column Name cannot start with a number"); 
                	
	                if ($j("#dataMartColumnNm").val()!="" && opener.isUniqueElementName('dataMartColumnName', $j("#dataMartColumnNm").val(), $j("#pageElementUid").val()) == false) {
	                    errorMsgArray.push("Data Mart Column Name you entered already exists in the page. Please choose a different one.");
	                }
	                
	                
	                if(value.endsWith('_1') || value.endsWith('_2') || value.endsWith('_3')
	    					|| value.endsWith('_4') || value.endsWith('_5') || value.endsWith('_ALL')
	    					|| value.endsWith('_GT1_IND') || value.endsWith('_GT2_IND') || value.endsWith('_GT3_IND') || value.endsWith('_GT4_IND') || value.endsWith('_GT5_IND') || value.endsWith('_DETAIL') || value.endsWith('_QEC')){
	                	errorMsgArray.push("Data mart column names ending in _1, _2, _3, _4, _5, _ALL, _GT1_IND, _GT2_IND, _GT3_IND, _GT4_IND, _GT5_IND, _DETAIL, _QEC are not allowed due to potential conflicts in dynamically created data marts");
	    				isError=true;
	    			}
	    			
	    			if(value=='PROGRAM_JURISDICTION_OID' || value=='INVESTIGATION_CREATE_DATE' || value=='INVESTIGATION_CREATED_BY' || value=='INVESTIGATION_LAST_UPDTD_DATE' || value=='INVESTIGATION_LAST_UPDTD_BY' || value=='EVENT_DATE' || value=='EVENT_DATE_TYPE' || value=='LABORATORY_INFORMATION' || value=='EARLIEST_SPECIMEN_COLLECT_DATE' || value=='NOTIFICATION_STATUS' || value=='DISEASE_CD' || value=='DISEASE' || value=='JURISDICTION_NM' || value=='PATIENT_COUNTY_CODE' || value=='PROGRAM_AREA' || value=='NOTIFICATION_LAST_UPDATED_DATE' ){
	    				errorMsgArray.push("The following Data mart column names are not allowed due to conflicts in dynamically created data marts: PROGRAM_JURISDICTION_OID, INVESTIGATION_CREATE_DATE, INVESTIGATION_CREATED_BY, INVESTIGATION_LAST_UPDTD_DATE, INVESTIGATION_LAST_UPDTD_BY, EVENT_DATE,EVENT_DATE_TYPE, LABORATORY_INFORMATION, EARLIEST_SPECIMEN_COLLECT_DATE, NOTIFICATION_STATUS, DISEASE_CD, DISEASE,JURISDICTION_NM, PATIENT_COUNTY_CODE, PROGRAM_AREA, NOTIFICATION_LAST_UPDATED_DATE.");
	    				isError=true;
	    			}
	    			
                }
	                
                
                if(errorMsgArray != null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
                }
                else {
                	unblock=false;
                    document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit";
                    document.forms[0].submit();
                }
           }

           function  handleBatchQuestion()
           {
               var batchInd = '${fn:escapeXml(batchInd)}';
               var displayInd = '${fn:escapeXml(displayInd)}';
               if(batchInd != "null" && batchInd == 'Y'){
                   if(displayInd == 'T')
	                $j("input#isVisible_Yes").attr("checked", true);
                   else
                	$j("input#isVisible_No").attr("checked", true);
	               // disable visible dropdown fields
	               $j("tr.batchFieldTr").find(":input").attr("disabled", true);
	               
	               // update label/text colors
	               $j("tr.batchFieldTr").find("span[title]").css("color", "#CCC");
	               $j("tr.batchFieldTr").find("td").css("color", "#CCC");
               }
           }
           
           function handleMessagingFields(doEnable) {
                if(doEnable == true){
                    // enable all fields 
                    $j("tr.messagingFieldTr").find(":input").attr("disabled", false);
                    
                    // update all fields
                    $j("input#orderGroupIdValue").val("2"); $j("span#orderGroupIdSpan").html("2");

                    var hl7SegmentValue = getElementByIdOrByName("hl7SegmentValue").value;
    		    	if(hl7SegmentValue!=null && hl7SegmentValue.length ==0)
    		        	hl7SegmentValue = "OBX-3.0";
    		    
    		        $j("input#hl7SegmentValue").val(hl7SegmentValue);
            		$j("span#hl7SegmentSpan").html(hl7SegmentValue);
                    
                    // update label/text colors 
                    $j("tr.messagingFieldTr").find("span[title]").css("color", "#000");
                    $j("tr.messagingFieldTr").find("td").css("color", "#000");
                }
                else {
                    // reset all values
                    $j("tr.messagingFieldTr").find(":input[type!=radio]").val("");
                    $j("input#orderGroupIdValue").val(""); $j("span#orderGroupIdSpan").html("");
                    $j("input#hl7SegmentValue").val(""); $j("span#hl7SegmentSpan").html("");
                    $j("input#reqInMsgN").attr("checked", true);
                    
                    // disable all fields
                    $j("tr.messagingFieldTr").find(":input").attr("disabled", true);
                    
                    // update label/text colors
                    $j("tr.messagingFieldTr").find("span[title]").css("color", "#666666");
                    $j("tr.messagingFieldTr").find("td").css("color", "#666666");
                }
            }
           
            function onLoadFunction() {
				
                // focus on the first valid element
                $j("div#editQuestionBlock").find(':input[type!=button]:visible:enabled:first').focus();
            
                // handle autocompletes for select boxes
                autocompTxtValuesForJSP();
                
                // question data type
                var dataType = $j("span#questionDataTypeSpan").html();
                
                dataType = jQuery.trim(dataType.toLowerCase());
                
                // participation can not be a required field at this time
                if (dataType == "participation") {
                	  $j("#isRequired_Yes").parent().find(":input").attr("disabled", true);
                }
                // numeric question
                if (dataType != null && dataType != undefined && dataType == 'numeric') {
                    // fieldLength
                    var maxLenElt = getElementByIdOrByName("maskN");
                    if (maxLenElt != null && maxLenElt != undefined) {
                        handleNumericMaskChange(maxLenElt);    
                    }    
                    
                    // related units
                    var relUnitY = getElementByIdOrByName("relUnitY");
                    var relUnitN = getElementByIdOrByName("relUnitN");
                    if (relUnitY != null && relUnitN != null) {
                        if(relUnitY.checked == true && relUnitN.checked == false) {
                            displayRelatedUnit(true);
                        }
                        else if (relUnitN.checked == true && relUnitY.checked == false) {
                          displayRelatedUnit(false);
                        }
                    } 
                }
                var defaultTypeSpecificCssClass = "defaultType";
                var dataLocation = getElementByIdOrByName("dataLocation").value;
             // coded question
             	$j("tr." + defaultTypeSpecificCssClass).hide();
    			$j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", true);
                if (dataType != null && dataType != undefined && dataType == 'coded') {
               
                   	if(getElementByIdOrByName("valSet") != null && getElementByIdOrByName("valSet").value !=''){
                   	 
                   	    var options = $j("#defaultValueCoded").find("option");
                	    for (var i = 0; i < options.length; i++) {
                		if (options[i].value == "OTH" && !(dataLocation.indexOf('ANSWER_TXT')==-1)) {
                    		
                		    $j("tr." + defaultTypeSpecificCssClass).show();
                		    $j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", false);
                		    break;
                		}
                	    }
                       }
                       else{
                       
                       	var options = $j("#defaultValueCoded").find("option");
                		for (var i = 0; i < options.length; i++) {
                			getElementByIdOrByName("defaultValueCoded").remove(options[i]);
                    	 }
                         $j("tr." + defaultTypeSpecificCssClass).hide();
                	 	 $j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", true);
                       }
                	     
                    
                }
                
                // handle NND part
                var includeMsgFields = false;
                var r1 = $j("input#nndIndY").attr("checked");
                var r2 = $j("input#nndIndN").attr("checked");
                if (r1 == false && r2 == false) {
                    $j("input#nndIndY").attr("checked", false);
                    $j("input#nndIndN").attr("checked", true);    
                    includeMsgFields = false;
                }
                else if (r1 == false && r2 == true) {
                    includeMsgFields = false;
                }
                else if (r1 == true && r2 == false) {
                    includeMsgFields = true;
                }
                handleMessagingFields(includeMsgFields);
            }
            
            /**
             * Update the default values input element identifier by 'defaultValueEltId'
             *  with values corresponding to the valSetGroupId received.
             */
            function updateDefaultValuesForValueSet(valSetGroupId, defaultValueEltId)
            {
                var defaultValuesElt = getElementByIdOrByName(defaultValueEltId);
                var defaultTypeSpecificCssClass = "defaultType"; 
                var dataLocation = getElementByIdOrByName("dataLocation").value;
                if (defaultValuesElt != null) {
                    // remove existing values
                    dwr.util.removeAllOptions(defaultValuesElt);
                    
                    // empty the corresponding text box
                    $j(defaultValuesElt).parent().parent().find(":input[type='text']").val("");
                    
                    // insert new values
                    if (valSetGroupId != null && valSetGroupId != undefined && jQuery.trim(valSetGroupId) != "") {
                        JPageElementForm.getDefaultValuesForValueSet(valSetGroupId, function(data) {
                            dwr.util.addOptions(defaultValuesElt, data, "key", "value");
                            flag = false;
                            for(x in data){
                                var newArray = data[x];
                                for(y in newArray){
                                     if(newArray[y]=="OTH"){
                                    flag = true;                        
                                    
                                     }
                                }   
                            }
                        if(flag && !(dataLocation.indexOf('ANSWER_TXT')==-1)){
                        	$j("tr." + defaultTypeSpecificCssClass).show();
            				$j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", false);
                            getElementByIdOrByName('yesAllowEntry').checked="checked";
                        }
                        else{
                        	$j("tr." + defaultTypeSpecificCssClass).hide();
            				$j("tr." + defaultTypeSpecificCssClass).find(":input").attr("disabled", true);
                            getElementByIdOrByName('noAllowEntry').checked="checked";
                        }
                        });
                    }
                }

                
            }
            
            function handleNumericMaskChange(numericMaskElt)
            {
                // enable/disable the field length element for this numeric field
                if (numericMaskElt != null) {
                    var val = numericMaskElt.value;
                    if (val == "NUM") {
                        $j("input#fieldLenNum").attr("disabled", false);
                        $j("span#fieldLenNumL").css("color", "#000");
                        $j($j("span#fieldNumTxtL").siblings().get(0)).css("color", "#F00");
                    }
                    else {
                        $j("input#fieldLenNum").val("");
                        $j("input#fieldLenNum").attr("disabled", true);
                        $j("span#fieldLenNumL").css("color", "#CCC");
                        $j($j("span#fieldNumTxtL").siblings().get(0)).css("color", "#CCC");
                    }
                }
            }
            
            /**
             * Set the default values for related units block for a question with 
             * numeric date type.
             */
            function displayRelatedUnit(isTrue)
            {
                var cssClass = "questionTypeRelatedField";
                var relatedUnitsSpecificCssClass = "relatedUnitsQuestionType";
                if(isTrue == true) {
                    // now, only show fields that are specific to the related unit selected.
                    $j("tr." + relatedUnitsSpecificCssClass).find(":input").attr("disabled", false);
                    $j("tr." + relatedUnitsSpecificCssClass).show();
                    unitType = getElementByIdOrByName("unitsTypeN")
                    var val = unitType.value;
                    if(val!= null && val != undefined){
                        if (val == "CODED") {
                            // now, only show fields that are specific to the data type selected.
                            $j("tr." + "codedUnitType").find(":input").attr("disabled", false);
                            $j("tr." + "codedUnitType").show();
                            $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
                            $j("tr." + "literalUnitType").hide();
                            
                            // clear out literal units field
                            $j("#literalUnits").val("");
                        }
                        else if (val == "LITERAL"){
                            $j("tr." + "codedUnitType").find(":input").attr("disabled", true);
                            $j("tr." + "codedUnitType").hide();
                            $j("tr." + "literalUnitType").find(":input").attr("disabled", false);
                            $j("tr." + "literalUnitType").show();
                            
                            // clear out value set units field
                            $j("#relatedUnitsV").val("");
                        }
                    }
                    
                }else if (isTrue == false){
                    // initially hide fields that have 'questionTypeRelatedField' assigned to it
                    $j("tr." + relatedUnitsSpecificCssClass).find(":input").attr("disabled", true);
                    $j("tr." + relatedUnitsSpecificCssClass).hide();
                    $j("tr." + "codedUnitType").find(":input").attr("disabled", true);
                    $j("tr." + "codedUnitType").hide();
                    $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
                    $j("tr." + "literalUnitType").hide();
                }
            }
            
            function showUnitTypeSpecificFields(unitType){
                 var cssClass = "questionTypeRelatedField";
                 $j("#literalUnits").val("");
                 $j("#relatedUnitsV").val("");
                 if (unitType != null && unitType != undefined) {
                        // css class assigned to fields that are specific to a particular data type
                        var unitTypeSpecificCssClass = unitType.value.toLowerCase() + "QuestionType";
                        var val = unitType.value;
                        if (val == "CODED") {
                            // now, only show fields that are specific to the data type selected.
                            $j("#literalUnits").val("");
                            $j("tr." + "codedUnitType").find(":input").attr("disabled", false);
                            $j("tr." + "codedUnitType").show();
                            $j("tr." + "literalUnitType").find(":input").attr("disabled", true);
                            $j("tr." + "literalUnitType").hide();
                       }
                       else {
                           $j("#relatedUnitsV").val("");
                            $j("tr." + "codedUnitType").find(":input").attr("disabled", true);
                            $j("tr." + "codedUnitType").hide();
                            $j("tr." + "literalUnitType").find(":input").attr("disabled", false);
                            $j("tr." + "literalUnitType").show();
                       }
                    }
                    else {
                        // initially hide fields that have 'questionTypeRelatedField' assigned to it
                        $j("tr." + cssClass).hide();
                    }
            }

   
        </script>
    </head>
    <body class="popup" onload="onLoadFunction();handleBatchQuestion();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;" style="text-align:center;">
        <div class="popupTitle">ManagePage: Edit Question</div>
        
        <!-- Top button bar -->
        <div class="popupButtonBar">
            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
        </div>
        
        <!-- Required Field Indicator -->
        <div style="text-align:right; width:100%; margin-top:0.5em;"> 
            <span style="color:#CC0000;"> * </span>
            <span style="color:black; font-style:italic;"> Indicates a Required Field </span>  
        </div>
        
        <!-- Error Messages using Action Messages-->
        <div id="globalFeedbackMessagesBar" class="screenOnly"> </div>
        <html:hidden  styleId = "dataLocation" name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataLocation"/>
        <!-- Collection of form fields to edit a question -->
        <div id="editQuestionBlock">
            <html:form action="/ManagePageElement.do?method=editSubmit">
               <html:hidden styleId="pageElementUid" property="pageEltVo.pageElementUid" />
               <div class="view"  style="text-align:center;">
                   <!-- SECTION : Question --> 
                   <nedss:container id="sect_question" name="Customize Question for this Page" classType="sect" displayImg="false" includeBackToTopLink="no">
                       <!-- SUBSECTION: Basic Info -->
                       <nedss:container id="subsect_basicInfo" name="Basic Info" classType="subSect" >
                            <tr>
                                <td class="fieldName">
                                    <span title="Question Type">Question Type:</span>
                                </td>
                                <td>
                                    <nedss:view name ="pageElementForm" property="pageEltVo.waUiMetadataDT.questionType" codeSetNm="<%= NEDSSConstants.NBS_QUESTION_TYPE %>" />
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span title="Unique Identifier">Unique Identifier:</span> 
                                </td>
                                <td>
                                    ${fn:escapeXml(pageElementForm.pageEltVo.waUiMetadataDT.questionIdentifier)}
                                    <%-- <bean:write name="pageElementForm" property="pageEltVo.waUiMetadataDT.questionIdentifier"/> --%>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> 
                                    <span title="Value Set">Unique Name:</span> 
                                </td>
                                <td>
                                    ${fn:escapeXml(pageElementForm.pageEltVo.waUiMetadataDT.questionNm)}
                                   <%--  <bean:write name="pageElementForm" property="pageEltVo.waUiMetadataDT.questionNm" /> --%>
                                </td>
                            </tr>
                           <%--  <tr>
                                <td class="fieldName"> 
                                    <span title="Group">Group:</span> 
                                </td>
                                <td>
                                    <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.groupNm" codeSetNm="<%= NEDSSConstants.PAGE_GROUP %>"/>
                                </td>
                            </tr> --%>
                            <tr>
                                <td class="fieldName"> 
                                    <span title="Subgroup">Subgroup:</span> 
                                </td>
                                <td>
                                    <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.subGroupNm" codeSetNm="<%= NEDSSConstants.PAGE_SUBGROUP %>"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> 
                                    <span title="Description">Description:</span> 
                                </td>
                                <td>
                                    <logic:present name="pageElementForm" property="pageEltVo.waUiMetadataDT.descTxt">
                                        <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.descTxt"/>
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
						    <td class="fieldName">
						        <span title="Description" id="descL">Co-Infection Question:</span>
						    </td>
						    <td>
						    <nedss:view name ="pageElementForm" property="pageEltVo.waUiMetadataDT.coinfectionIndCd" />
							
					        </td>
					        </tr>
  
                            <tr>
                                <td class="fieldName"> 
                                    <span title="Datatype">Datatype:</span> 
                                </td>
                                <td>
                                    <span id="questionDataTypeSpan">
                                       <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataType" 
                                               codeSetNm="<%= NEDSSConstants.PAGE_DATATYPE %>"/>   
                                    </span>
                                </td>
                            </tr>
                            
                            <!-- fields specific to 'coded' type question -->   
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataType" value="CODED">
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span class="boldRed">*</span> 
                                            <span title="Value Set">Value Set:</span> 
                                        </td>
                                        <td>
                                            <html:select property="pageEltVo.waUiMetadataDT.codeSetGroupId" styleId="valSet"
                                                    onchange="javascript:updateDefaultValuesForValueSet(this.options[this.selectedIndex].value, 'defaultValueCoded');">
                                                <html:optionsCollection property="valueSets" value="key" label="value"/>
                                            </html:select>
                                        </td>
                                    </tr>
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Value Set">Value Set:</span> 
                                        </td>
                                        <td>
                                            <html:hidden styleId="valSet" property="pageEltVo.waUiMetadataDT.codeSetGroupId"/>
                                            <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.codeSetGroupId" 
                                               codeSetNm="<%= NEDSSConstants.CODE_SET_NMS %>"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                                <tr>
                                    <td class="fieldName">
                                        <span title="Default Value">Default Value:</span> 
                                    </td>
                                    <td>
                                        <html:select property="pageEltVo.waUiMetadataDT.defaultValue" styleId="defaultValueCoded">
                                            <html:optionsCollection property="defaultValueUnitColl" value="key" label="value"/>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr class="questionTypeRelatedField defaultType">
                                    <td class="fieldName">
                                        <span class="boldRed">*</span>
                                        <span title="Allow for Entry of Other Value" id="AllowEntryL">Allow for Entry of Other Value:</span>
                                    </td>
                                    <td>
                                        <html:radio styleId="yesAllowEntry" name="pageElementForm" property="pageEltVo.waUiMetadataDT.otherValIndCd"  value="<%= NEDSSConstants.TRUE %>"/> Yes
                                        &nbsp;<html:radio styleId="noAllowEntry" name="pageElementForm" property="pageEltVo.waUiMetadataDT.otherValIndCd"  value="<%= NEDSSConstants.FALSE %>"/> No
                                    </td>
                                </tr>


                            </logic:equal>

                            <!-- fields specific to 'text' type question -->
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataType" value="TEXT">
                                <!-- Mask -->
                                <tr>
                                    <td class="fieldName"> 
                                        <span title="Mask">Mask:</span> 
                                    </td>
                                    <td>
                                        <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.mask" codeSetNm="<%= NEDSSConstants.PAGE_MASK %>"/>
                                    </td>
                                </tr>    
                                
                                <!-- Field Length -->
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName"> 
                                            <span title="Field Length">Field Length:</span> 
                                        </td>
                                        <td>
                                           <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.fieldLength"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName"> 
                                            <span title="Field Length">Field Length:</span> 
                                        </td>
                                        <td>
                                            <html:text property="pageEltVo.waUiMetadataDT.fieldLength" size="10" maxlength="10" onkeyup="isNumericCharacterEntered(this);"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                                
                                <tr>
                                    <td class="fieldName">
                                        <span title="Default Value">Default Value:</span> 
                                    </td>
                                    <td>
                                        <html:text property="pageEltVo.waUiMetadataDT.defaultValue" styleId="defaultValueText" />
                                    </td>
                                </tr>
                            </logic:equal>

                            <!-- fields specific to 'date' type question -->
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataType" value="DATE">
                            
                                <!-- Mask -->
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span class="boldRed">*</span> 
                                            <span title="Mask">Mask:</span> 
                                        </td>
                                        <td>
                                            <html:select property="pageEltVo.waUiMetadataDT.mask" styleId = "maskD">
                                                <html:optionsCollection property="maskforDate(NBS_MASK_TYPE)" value="key" label="value"/>
                                            </html:select>
                                        </td>
                                    </tr>           
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Mask">Mask:</span> 
                                        </td>
                                        <td>
                                            <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.mask" codeSetNm="<%= NEDSSConstants.PAGE_MASK %>"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                                
                                <!--  Allow future dates -->
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span class="boldRed">*</span> 
                                            <span title="Mask" id="allowFutureDatesL"> Allow for Future Dates:</span> 
                                        </td>
                                        <td>
                                            <html:radio name="pageElementForm" property="pageEltVo.waUiMetadataDT.futureDateIndCd"  value="<%= NEDSSConstants.TRUE %>"/> Yes
                                            &nbsp;<html:radio name="pageElementForm" property="pageEltVo.waUiMetadataDT.futureDateIndCd"  value="<%= NEDSSConstants.FALSE %>"/> No
                                        </td>  
                                    </tr>                               
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Mask" id="allowFutureDatesL"> Allow for Future Dates:</span> 
                                        </td>
                                        <td>
                                            <logic:equal name ="pageElementForm"  property="pageEltVo.waUiMetadataDT.futureDateIndCd" value="<%= NEDSSConstants.TRUE %>">
                                                Yes
                                            </logic:equal>
                                            <logic:equal name ="pageElementForm"  property="pageEltVo.waUiMetadataDT.futureDateIndCd" value="<%= NEDSSConstants.FALSE %>">
                                                No                
                                            </logic:equal>
                                        </td>                                               
                                    </tr>                               
                                </logic:equal>
                            </logic:equal>
                            
                            <!-- fields specific to 'date/time' type question -->
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataType" value="DATETIME">
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Mask">Mask:</span> 
                                        </td>
                                        <td>
                                            <html:select property="pageEltVo.waUiMetadataDT.mask" styleId = "maskDT">
                                                <html:optionsCollection property="maskforDateTime(NBS_MASK_TYPE)" value="key" label="value"/>
                                            </html:select>                                        
                                        </td>
                                    </tr>
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Mask">Mask:</span> 
                                        </td>
                                        <td>
                                           <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.mask" codeSetNm="<%= NEDSSConstants.PAGE_MASK %>"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                            </logic:equal>
                            
                            <!-- fields specific to 'numeric' type question -->
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.dataType" value="NUMERIC">
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Mask">Mask:</span> 
                                        </td>
                                        <td>
                                            <html:select property="pageEltVo.waUiMetadataDT.mask" styleId="maskN" onchange="handleNumericMaskChange(this)">
                                                <html:optionsCollection property="maskforNumeric(NBS_MASK_TYPE)" value="key" label="value"/>
                                            </html:select>
                                        </td>
                                    </tr>                        
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Mask">Mask:</span> 
                                        </td>
                                        <td>
                                            <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.mask" codeSetNm="<%= NEDSSConstants.PAGE_MASK %>"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                                
                                <!-- Field Length -->
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span class="boldRed">*</span>
                                            <span title="Field Length" id="fieldLenNumL">Field Length:</span> 
                                        </td>
                                        <td>
                                            <html:text property="pageEltVo.waUiMetadataDT.fieldLength" size="10" maxlength="10"
                                                    styleId="fieldLenNum" 
                                                    onkeyup="isNumericCharacterEntered(this);"/>
                                        </td>
                                    </tr>
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Field Length" id="fieldLenNumL">Field Length:</span> 
                                        </td>
                                        <td>
                                            <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.fieldLength"/>
                                        </td>
                                    </tr>
                                </logic:equal>

                                <!-- Default Value -->                              
                                <tr>
                                    <td class="fieldName">
                                        <span title="Default Value">Default Value:</span> 
                                    </td>
                                    <td>
                                        <html:text property="pageEltVo.waUiMetadataDT.defaultValue" styleId="defaultValueNumeric" />
                                    </td>
                                </tr>
                                
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    <!-- Min value -->
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Minimum Value">Minimum Value:</span> 
                                        </td>
                                        <td>
                                            <html:text property="pageEltVo.waUiMetadataDT.minValue" styleId="minValueNumeric"/>
                                        </td>
                                    </tr>
                                    
                                    <!-- Max Value -->
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Maximum Value">Maximum Value:</span> 
                                        </td>
                                        <td>
                                            <html:text property="pageEltVo.waUiMetadataDT.maxValue" styleId="maxValueNumeric"/>
                                        </td>
                                    </tr>
                                    
                                    <!-- Related Units -->
                                    <tr class="questionTypeRelatedField numericQuestionType">
                                        <td class="fieldName"> 
                                            <span class="boldRed">*</span>
                                            <span title="Related Units">Related Units:</span>
                                        </td>
                                        <td>
                                            <html:radio styleId="relUnitY" onclick="displayRelatedUnit(true)"  property="pageEltVo.waUiMetadataDT.relatedUnitInd"  value="<%= NEDSSConstants.TRUE %>"/> Yes
                                            &nbsp;<html:radio styleId="relUnitN" onclick="displayRelatedUnit(false)"  property="pageEltVo.waUiMetadataDT.relatedUnitInd"  value="<%= NEDSSConstants.FALSE %>"/> No
                                        </td>                          
                                    </tr>
                                    
                                    <!-- Units Type -->
                                    <tr class="questionTypeRelatedField relatedUnitsQuestionType">
                                        <td class="fieldName"> 
                                            <span class="boldRed">*</span>
                                            <span title="Units Type" id="unitsTypeL">Units Type:</span>
                                        </td>
                                        <td>
                                            <html:select property="pageEltVo.waUiMetadataDT.unitTypeCd" styleId="unitsTypeN" onchange="showUnitTypeSpecificFields(this);">
                                                <html:optionsCollection property="unitsType" value="key" label="value"/>
                                            </html:select>
                                        </td>
                                    </tr>
                                    
                                    <!-- Units Value -->
                                    <tr class="questionTypeRelatedField codedUnitType">
                                        <td class="fieldName"> 
                                            <span class="boldRed">*</span>
                                            <span title="Related Units Value Set" id="relatedUnitsVL">Related Units Value Set:</span>
                                        </td>
                                        <td>
                                            <html:select property="pageEltVo.waUiMetadataDT.unitValue" styleId="relatedUnitsV" onchange="handleNumericMaskChange(this)">
                                                 <html:optionsCollection property="valueSets" value="key" label="value"/>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr class="questionTypeRelatedField literalUnitType">
                                        <td class="fieldName"> 
                                            <span class="boldRed">*</span>
                                            <span title="Literal Units Value" id="literalUnitsL">Literal Units Value:</span>
                                        </td>
                                        <td>
                                           <html:text property="pageEltVo.waUiMetadataDT.unitValue" size="15" maxlength="50" styleId="literalUnits" />  
                                        </td>
                                    </tr>
                                </logic:equal>
                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Minimum Value">Minimum Value:</span> 
                                        </td>
                                        <td>
                                            <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.minValue"/>  
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fieldName">
                                            <span title="Maximum Value">Maximum Value:</span> 
                                        </td>
                                        <td>
                                            <nedss:view name="pageElementForm" property="pageEltVo.waUiMetadataDT.maxValue"/>
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td class="fieldName">Related Units:</td>
                                        <td>
                                            <logic:equal name ="pageElementForm"  property="pageEltVo.waUiMetadataDT.relatedUnitInd" value="<%= NEDSSConstants.TRUE %>">
                                                Yes
                                            </logic:equal>
                                            <logic:equal name ="pageElementForm"  property="pageEltVo.waUiMetadataDT.relatedUnitInd" value="<%= NEDSSConstants.FALSE %>">
                                                No                
                                            </logic:equal> 
                                        </td>
                                    </tr> 
                                    <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.relatedUnitInd" value="<%= NEDSSConstants.TRUE %>">  
                                        <tr>
                                            <td class="fieldName">Units Type:</td>
                                            <td>
                                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.unitTypeCd" value="CODED">
                                                    Coded
                                                </logic:equal>
                                                <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.unitTypeCd" value="LITERAL">
                                                    Literal
                                                </logic:equal>
                                            </td>
                                        </tr>
                                        <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.unitTypeCd" value="CODED">
                                            <tr>
                                                <td class="fieldName"> Related Units Value Set:</td>
                                                <td>
                                                    <logic:present name="unitValueDesc">
                                                        <bean:write name ="unitValueDesc"/>    
                                                    </logic:present>
                                                </td>
                                            </tr>
                                        </logic:equal>
                                        <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.unitTypeCd" value="LITERAL">
                                            <tr>
                                                <td class="fieldName">Literal Units Value:</td>
                                                <td>
                                                   <nedss:view name ="pageElementForm"  property="pageEltVo.waUiMetadataDT.unitValue"/>  
                                                </td>
                                            </tr>
                                        </logic:equal>
                                    </logic:equal>
                                </logic:equal>
                                
                            </logic:equal>
                       </nedss:container>
                       
                       <!-- SUBSECTION: User Interface -->
                       <nedss:container id="subsect_userInterface" name="User Interface" classType="subSect">
                            <tr>
                                <td class="fieldName">
                                    <span title="Label on Screen">Label on Screen:</span> 
                                </td>
                                <td> 
                                   <html:text title="Label on Screen" property="pageEltVo.waUiMetadataDT.questionLabel" size="50" maxlength="100" style="width:350px;"/>
                                </td>                          
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span class="boldRed">*</span> 
                                    <span title="Tool Tip">Tool Tip:</span> 
                                </td>
                                <td> <html:textarea title="Tool Tip" style="width:350px;height:100px;" property="pageEltVo.waUiMetadataDT.questionToolTip" styleId="questionToolTip" /></td>
                            </tr>
                            <!-- Display Control -->
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.FALSE %>">
                            	<logic:notEqual name="pageElementForm" property="pageEltVo.waUiMetadataDT.standardQuestionIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                    	<td class="fieldName">
                                        	<span class="boldRed">*</span> 
                                        	<span title="Display Control">Display Control:</span> 
                                    	</td>
                                    	<td>
                                        	<html:select title="Display Control" property="pageEltVo.waUiMetadataDT.nbsUiComponentUid" styleId="defaultDisplayControl">
                                            	<html:optionsCollection property="displayControlList" value="key" label="value"/>
                                        	</html:select>
                                    	</td>
                                   </tr>   
                                </logic:notEqual>
                            </logic:equal>
                            <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                            	<logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.standardQuestionIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                    	<td class="fieldName">
                                        <span title="Display Control">Display Control:</span> 
                                    	</td>
                                    	<td>
                                        	<logic:present name="defaultDisplayControlDesc">
                                           	<bean:write name="defaultDisplayControlDesc" />   
                                        	</logic:present>
                                    	</td>
                                    </tr>
                                </logic:equal>
                            </logic:equal>
                            <logic:notEqual name="pageElementForm" property="pageEltVo.waUiMetadataDT.publishIndCd" value="<%= NEDSSConstants.TRUE %>">
                            	<logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.standardQuestionIndCd" value="<%= NEDSSConstants.TRUE %>">
                                    <tr>
                                    	<td class="fieldName">
                                        <span title="Display Control">Display Control:</span> 
                                    	</td>
                                    	<td>
                                        	<logic:present name="defaultDisplayControlDesc">
                                           	<bean:write name="defaultDisplayControlDesc" />   
                                        	</logic:present>
                                    	</td>
                                    </tr>
                                </logic:equal>
                            </logic:notEqual>                            
                            <tr class="batchFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span> 
                                    <span title="Visible">Visible:</span> 
                                </td>
                                <td>
                                    <html:radio title="Visibl Yes" styleId="isVisible_Yes" property="pageEltVo.waUiMetadataDT.displayInd" value="<%= NEDSSConstants.TRUE %>"/> Yes
                                    <html:radio title="Visibl No" styleId="isVisible_No" property="pageEltVo.waUiMetadataDT.displayInd" value="<%= NEDSSConstants.FALSE %>"/> No
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span class="boldRed">*</span> 
                                    <span title="Enabled">Enabled:</span> 
                                </td>
                                <td>
                                    <html:radio title="Enabled Yes" styleId="isEnabled_Yes" property="pageEltVo.waUiMetadataDT.enableInd" value="<%= NEDSSConstants.TRUE %>"/> Yes
                                    <html:radio title="Enabled No" styleId="isEnabled_No" property="pageEltVo.waUiMetadataDT.enableInd" value="<%= NEDSSConstants.FALSE %>"/> No
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span class="boldRed">*</span> 
                                    <span title="Required">Required:</span> 
                                </td>
                                <td>
                                    <html:radio title="Required Yes" styleId="isRequired_Yes" property="pageEltVo.waUiMetadataDT.requiredInd" value="<%= NEDSSConstants.TRUE %>"/> Yes
                                    <html:radio title="Required No" styleId="isRequired_No" property="pageEltVo.waUiMetadataDT.requiredInd" value="<%= NEDSSConstants.FALSE %>"/> No
                                </td>
                            </tr>
                       </nedss:container>
                       
                       <!-- SUBSECTION: Data Mart -->
                       <nedss:container id="subsect_dataMart" name="Data Mart" classType="subSect">
                            <tr>
                                <td class="fieldName">
                                    <span class="boldRed">*</span> 
                                    <span title="Default Label in Report"> Default Label in Report:</span> 
                                </td>
                                <td> <html:text title="Default Label in Report" property="pageEltVo.waUiMetadataDT.reportLabel" size="50" maxlength="50" style="width:350px;"/> </td>                          
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span title="RDB Table Name" id="datamartTableNmL">Default RDB Table Name:</span>
                                </td>
                                <td>
                                    ${fn:escapeXml(pageElementForm.pageEltVo.waUiMetadataDT.rdbTableNm)}
                                    <%-- <bean:write name="pageElementForm" property="pageEltVo.waUiMetadataDT.rdbTableNm" /> --%>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span title="RDB Column Name" id="datamartColumnNmL">RDB Column Name:</span>
                                </td>
                                <td>
                                     ${fn:escapeXml(pageElementForm.pageEltVo.waUiMetadataDT.rdbcolumnNm)}
                                    <%-- <bean:write name="pageElementForm" property="pageEltVo.waUiMetadataDT.rdbcolumnNm"/> --%>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">
                                    <span title="Data Mart Column Name" id="datamartColumnNmL">Data Mart Column Name:</span>
                                </td>
                                <td>
                                <logic:notEmpty name="pageElementForm" property="pageEltVo.waUiMetadataDT.rdbcolumnNm">
									<html:text styleId="dataMartColumnNm" title="Data mart column name is used as a column header in dynamically created data marts. If a data mart column name is not indicated, then this question will not appear in the dynamic data mart. Note that data mart column names must be unique for each question on a given page." property="pageEltVo.waUiMetadataDT.userDefinedColumnNm" size="50" maxlength="21" style="width:350px;" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,21)"/>
                                </logic:notEmpty>
                                <logic:empty name="pageElementForm" property="pageEltVo.waUiMetadataDT.rdbcolumnNm">
                               		<nedss:view name ="pageElementForm" property="pageEltVo.waUiMetadataDT.userDefinedColumnNm"/>     
                                </logic:empty>
                                
                                </td>
                            </tr>
                       </nedss:container>

                       <!-- SUBSECTION: Messaging -->
                       <nedss:container id="subsect_messaging" name="Messaging" classType="subSect">
                            <tr>
                                <td class="fieldName">
                                    <span class="boldRed">*</span> 
                                    <span title="Included in Message">Included in Message:</span>
                                </td>
                                <td>
                                    <html:radio title="Included in Message Yes" styleId="nndIndY" onclick="handleMessagingFields(true)" property="pageEltVo.waUiMetadataDT.nndMsgInd"  value="<%= NEDSSConstants.TRUE %>"/> Yes
                                    &nbsp;<html:radio  title="Included in Message No"  styleId="nndIndN" onclick="handleMessagingFields(false)" property="pageEltVo.waUiMetadataDT.nndMsgInd"  value="<%= NEDSSConstants.FALSE %>"/> No
                                </td>               
                            </tr>
                                     
                            <tr class="messagingFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span>  
                                    <span title="Message Variable ID" class="messagingPropertyTitle" id="msgVariableIdL">Message Variable ID:</span>
                                </td>
                                <td>
                                    <html:text title="Message Variable ID" property="pageEltVo.waUiMetadataDT.questionIdentifierNnd" size="25" maxlength="50" styleId="msgVariableId"/>
                                </td>
                            </tr>
                            <tr class="messagingFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span>  
                                    <span title="Label in Message" class="messagingPropertyTitle" id="msgLabelL">Label in Message:</span>
                                </td>
                                <td>
                                    <html:text title="Label in Message" property="pageEltVo.waUiMetadataDT.questionLabelNnd" size="50" maxlength="100" style="width:350px;" styleId="msgLabel"/> 
                                </td>
                            </tr>
                            
                            
                            <tr class="messagingFieldTr">
							    <td class="fieldName">
							        <span class="boldRed">*</span>  
							        <span title="Code System Name" class="messagingPropertyTitle"  id="msgCodeSysLable">Code System Name:</span>
							    </td>
							    <td>
							        <html:select title="Code System Name" property="pageEltVo.waUiMetadataDT.questionOidCode" styleId="msgcodeSysName" > 
										<nedss:optionsCollection property="codedValue(CODE_SYSTEM)" value="key" label="value"/>
									</html:select>
									                                        
							    </td>
							</tr>

                            <tr class="messagingFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span>  
                                    <span title="Required in Message" class="messagingPropertyTitle" id="reqmsg">Required in Message:</span>
                                </td>
                                <td>
                                    <html:radio title="Required in Message Yes" styleId="reqInMsgY" property="pageEltVo.waUiMetadataDT.questionReqNnd"  value="<%= NEDSSConstants.REQUIRED %>" /> Yes  
                                    &nbsp;<html:radio title="Required in Message No" styleId="reqInMsgN" property="pageEltVo.waUiMetadataDT.questionReqNnd"  value="<%= NEDSSConstants.OPTIONAL %>" /> No   
                                </td>               
                            </tr>
                                     
                            <tr class="messagingFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span>  
                                    <span title="HL7 Data Type" class="messagingPropertyTitle" id="hl7DatatypeL">HL7 Data Type:</span>
                                </td>
                                <td>
                                    <html:select title="HL7 Data Type" property="pageEltVo.waUiMetadataDT.questionDataTypeNnd" styleId = "hl7Datatype" >
                                        <html:optionsCollection property="codedValue(NBS_HL7_DATA_TYPE)" value="key" label="value"/>
                                    </html:select>
                                </td>
                            </tr>
                                  
                            <tr class="messagingFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span>  
                                    <span title="HL7 Segment" class="messagingPropertyTitle" id="hl7SegmentL">HL7 Segment:</span>
                                </td>
                                <td>
                                    <%-- //TODO: remove the hard coded value of 2 for post 4.0 release --%>
                                    <html:hidden styleId="hl7SegmentValue" property="pageEltVo.waUiMetadataDT.hl7Segment"/>
                                    <span id="hl7SegmentSpan"></span> 
                                    
                                    <%--
                                        // TODO: enable the select box for post 4.0 release
                                        <html:select property="pageEltVo.waUiMetadataDT.hl7Segment" styleId = "hl7Segment" >
                                            <html:optionsCollection property="codedValue(NBS_HL7_SEGMENT)" value="key" label="value"/>
                                        </html:select>
                                    --%>
                                </td>
                            </tr>
                            
                            <tr class="messagingFieldTr">
                                <td class="fieldName">
                                    <span class="boldRed">*</span>  
                                    <span title="Group Number(Order Group ID)" class="messagingPropertyTitle" id="groupNbrL">Group Number(Order Group ID):</span>
                                </td>
                                <td>
                                    <%-- //TODO: remove the hard coded value of 2 for post 4.0 release --%>
                                    <html:hidden styleId="orderGroupIdValue" property="pageEltVo.waUiMetadataDT.orderGrpId"/>
                                    <span id="orderGroupIdSpan"></span>
                                    
                                    <%--
                                        // TODO: enable the text box for post 4.0 release
                                        <html:text property="pageEltVo.waUiMetadataDT.orderGrpId" size="20" maxlength="25" styleId="groupNbr"/> 
                                    --%>
                                          
                                </td>
                            </tr>
                       </nedss:container>

                       <!-- SUBSECTION: Administrative -->
                       <nedss:container id="subsect_administrative" name="Administrative" classType="subSect">
                            <tr>
                                <td class="fieldName">
                                    <span title="Administrative Comments">Administrative Comments:</span>
                                </td>
                                <td> <html:textarea title="Administrative Comments" style="width:350px;height:100px;" property="pageEltVo.waUiMetadataDT.adminComment" styleId="adminComment" /></td>
                            </tr>
                       </nedss:container>
                   </nedss:container>
                </div>                        
            </html:form>
        </div>
        
        <!-- Bottom button bar -->
        <div class="popupButtonBar">
            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
        </div>
    </body>
</html>