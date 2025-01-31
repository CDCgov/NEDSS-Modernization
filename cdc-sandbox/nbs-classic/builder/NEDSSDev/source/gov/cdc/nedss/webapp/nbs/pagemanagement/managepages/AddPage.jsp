<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<html lang="en">
    <head>
        <title>NBS: Manage Pages</title>
        <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
        <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
        <script src="/nbs/dwr/interface/JPageBuilder.js" type="text/javascript"></script>
        <%@ include file="../../jsp/resources.jsp" %>     
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
     <script language="JavaScript">    
     var formSubmitted = false;    
	
     $j(document).ready(function() {
    		var preventDuplicatePage = getElementByIdOrByName("preventDuplicatePage");
    		if(preventDuplicatePage!=null && preventDuplicatePage == 'true'){
    			//getBusinessObjectTypeDD('VAC');
    			//getBusinessObjectTypeDD('LAB');
            }		
    		
    		showHideDataMartName();
	  });
		
    function submitForm()
    {
     	if(addPageReqFlds()==true)
           	return false;
     	else {
        	   setSelectedPageOptionValue();
        
           		var divElt = getElementByIdOrByName("blockparent");
				divElt.style.display = "block";		                 
          	  	document.forms[0].action ="/nbs/ManagePage.do?method=addPageSubmit";
       			document.forms[0].submit();
        
        }	
    } 
        
			           
	function cancelForm()
	{

	        	document.forms[0].target="";     
	            var confirmMsg="You have indicated that you would like to cancel from this page. All changes that have been made will be lost and cannot be recovered. Select OK to continue or Cancel to return to the active page.";
	            if (confirm(confirmMsg)) {
	            	document.forms[0].action ="/nbs/ManagePage.do?method=list";
	                document.forms[0].submit();
	            } else {
	                return false;
	            }    

	            return true; 
	}
    /**
    *showHideDataMartName(): show Data Mart Name if the Event Type = Investigation, otherwise, hide the Data Mart Name
    */
	function showHideDataMartName(){
		if($j("#busObjType")!=null && $j("#busObjType").val()=="INV"){
			
			$j("#dataMartRow").show();
		}else{
			$j("#dataMartRow").hide();			
		}
	}
	
	function addSelection(){
	   availableList = getElementByIdOrByName("availableConditions");
	   selectedList = getElementByIdOrByName("selectedConditions");

	   var addIndex = availableList.selectedIndex;
	   while(addIndex >= 0) {  //zero item in from list is blank
	    
	   	if (availableList.item(addIndex).value == "") {
	      		return;
           	}
	   	selectedList.appendChild(availableList.options.item(addIndex));
	   	addIndex = availableList.selectedIndex;
	   }
	   selectNone(selectedList,availableList);
	   // sortSelectList("selectedConditions");
	}
	
	
	function removeSelection(){
	    availableList = getElementByIdOrByName("availableConditions");
	    selectedList = getElementByIdOrByName("selectedConditions");
	
	   var selIndex = selectedList.selectedIndex;
	   if(selIndex < 0)
	      return;
	   while(selIndex >= 0) {  
	   	availableList.appendChild(selectedList.options.item(selIndex));
	   	selIndex = selectedList.selectedIndex;
	   }
	   selectNone(selectedList,availableList);
	   // sortSelectList("availableConditions");
	}
	


	function selectNone(list1,list2){
	    list1.selectedIndex = -1;
	    list2.selectedIndex = -1;
	    addIndex = -1;
	    selIndex = -1;
	}

	function sortSelectList(listId) {
		var lb = getElementByIdOrByName(listId);
		arrTexts = new Array();
	
		for(i=0; i<lb.length; i++)  {
	  	arrTexts[i] = lb.options[i].text;
		}
	
		arrTexts.sort();
	
		for(i=0; i<lb.length; i++)  {
	  		lb.options[i].text = arrTexts[i];
	  		lb.options[i].value = arrTexts[i];
		}
	}

	function reconcileSelects(fromList,toList)
	{
		
		var fromL = getElementByIdOrByName(fromList);
		var toL = getElementByIdOrByName(toList);
		if (toL.length == 0) { 
			return; 
		}
		//start from end of from list
		for(i=0; i<toL.length; i++)  {
			for(j=fromL.options.length-1;j>=0;j--) {
			    if (fromL.options[j].value == toL.options[i].value) {
			    	fromL.remove(j);
			    	break;
			    }
			}
		}
		
	}

	function setSelectedPageOptionValue()
	{
	    var x=document.forms[0].selectedConditionCodes.length;	
	    for(var i=0;i<x;i++)
	    {
	        document.forms[0].selectedConditionCodes.options[i].selected=true;
	    }
	}
	
	function onClickViewLink(url)
	{
		// call the JS function to block the UI while saving is on progress.
		blockUIDuringFormSubmissionNoGraphic();
		window.location=url;
	}
		
	function getBusinessObjectTypeDD(busObjType)
	{
	    getElementByIdOrByName("uniquePageName").value="";
	    getElementByIdOrByName("uniquePageName").disabled = false;
				
	    var busObjType1 = busObjType.value;
	    if(busObjType1 == null) {
	        busObjType1= busObjType;
	        
	    }
	    
	    JPageBuilder.getTemplateListByBO(busObjType1, function(data) {
	        DWRUtil.removeAllOptions("existingTemplate");
	        DWRUtil.addOptions("existingTemplate", data, "key", "value" );
	    });
		
	    JPageBuilder.getConditionAllListByBO(busObjType1, function(data) {
	        DWRUtil.removeAllOptions("availableConditions");
	        DWRUtil.addOptions("availableConditions", data, "key", "value" );
	     }); 
	       
	    
	    if(busObjType1!='INV'){
        	getElementByIdOrByName("mappingGuideTR").style.visibility = "hidden";
        	getElementByIdOrByName("mappingGuideTR").className="none";
	    }else{
 	        getElementByIdOrByName("mappingGuideTR").style.visibility = "visible";
  	        getElementByIdOrByName("mappingGuideTR").className="";
	    }
        if( busObjType1 == 'VAC'){
           
			getElementByIdOrByName("subSection2").style.display = "none";

			JPageBuilder.findPageExistenceByBusinessObjType(busObjType1, function(data) {
		        var waTemplateUid = data;
		        if(waTemplateUid != null){
		        	var linkToPage = "<a href='javascript:onClickViewLink(\"/nbs/PreviewPage.do?from=L&amp;waTemplateUid="+waTemplateUid+"&amp;method=viewPageLoad\")'>clicking here.</a>";
		        	getElementByIdOrByName("unableToCreatePageInfo").innerHTML = "Vaccination events do not have a related program area or condition code, and therefore sites can only have a single vaccination page per installation. Vaccination page already exists in the system. Navigate to existing vaccination page by "+linkToPage;
		        	getElementByIdOrByName("unableToCreatePageInfo").style.display = "block";
		        	getElementByIdOrByName("submitButton").disabled=true;
		        	getElementByIdOrByName("submitButton1").disabled=true;
				}
		    }); 
		}else if( busObjType1 == 'LAB' ||  busObjType1 == 'ISO' ||  busObjType1 == 'SUS'){
			getElementByIdOrByName("subSection2").style.display = "none";
			var pageName="";
			if(busObjType1 == 'LAB')
				pageName="Laboratory Report";
			else if(busObjType1 == 'ISO')
				pageName = "LAB TRACK ISOLATES";
			else if(busObjType1 == 'SUS')
				pageName = "LAB SUSCEPTIBILITIES";
			else if(busObjType1 == 'TRMT')
				pageName = "TREATMENT";
					
			if( busObjType1 == 'ISO' ||  busObjType1 == 'SUS' ||  busObjType1 == 'TRMT'){
				getElementByIdOrByName("uniquePageName").value=pageName;
				getElementByIdOrByName("uniquePageName").disabled = true;
				getElementByIdOrByName("pageNameHidden").value=pageName;
			}
			
			JPageBuilder.findPageExistenceByBusinessObjType(busObjType1, function(data) {
		        var waTemplateUid = data;
		        if(waTemplateUid != null){
		        	var linkToPage = "<a href='javascript:onClickViewLink(\"/nbs/PreviewPage.do?from=L&amp;waTemplateUid="+waTemplateUid+"&amp;method=viewPageLoad\")'>clicking here.</a>";
		        	getElementByIdOrByName("unableToCreatePageInfo").innerHTML = pageName+" page already exists in the system. Navigate to existing "+pageName+" page by "+linkToPage;
		        	getElementByIdOrByName("unableToCreatePageInfo").style.display = "block";
		        	getElementByIdOrByName("submitButton").disabled=true;
		        	getElementByIdOrByName("submitButton1").disabled=true;
				}else{
					getElementByIdOrByName("submitButton").disabled=false;
		        	getElementByIdOrByName("submitButton1").disabled=false;
		        	getElementByIdOrByName("unableToCreatePageInfo").innerHTML = "";
		        	getElementByIdOrByName("unableToCreatePageInfo").style.display = "none";
				}
		    }); 
		}else{
      			
			getElementByIdOrByName("subSection2").style.display = "block";
			getElementByIdOrByName("unableToCreatePageInfo").style.display = "none";
			getElementByIdOrByName("submitButton").disabled=false;
        	getElementByIdOrByName("submitButton1").disabled=false;
		}
		
        fnClearFormFields(); 
	}
	function fnClearFormFields(){
		
		 getElementByIdOrByName("existingTemplate_textbox").value="";	 
		 getElementByIdOrByName("mappingGuide_textbox").value="";		 
		 removeOptions(document.getElementById("selectedConditions"));
	}
	 
	 
	function removeOptions(selectbox)
	{
		var i;
		for(i = selectbox.options.length - 1 ; i >= 0 ; i--)
		{
			selectbox.remove(i);
		}
	}
	
	
	function setSelected(select_id,value){

    var mySelect = document.getElementById(select_id);
    var options = mySelect.options;
    var key;
    for(key in options){

        if(options[key].value === value){
            options[key].setAttribute("selected","");
			break;
        }
        
    }
}
	
	function fnOnloadBusinessObjectTypeDD()
	{
	    var busObjType = document.getElementById("busObjTypeHidden");
		
		var busObjType1 = busObjType.value;
	    
		if(busObjType1 == null) {
	        busObjType1= busObjType;
	        
	    }
	    
	    JPageBuilder.getTemplateListByBO(busObjType1, function(data) {
	        DWRUtil.removeAllOptions("existingTemplate");
	        DWRUtil.addOptions("existingTemplate", data, "key", "value" );
			
		    try {
				    var templatehiddenValue   = getElementByIdOrByName("templateValueHidden").value;
				
					setSelected("existingTemplate",templatehiddenValue);
					AutocompleteSynch('existingTemplate_textbox','existingTemplate');
				
			} catch(err){
			
			}
	    });
		
	    JPageBuilder.getConditionAllListByBO(busObjType1, function(data) {
	        DWRUtil.removeAllOptions("availableConditions");
	        DWRUtil.addOptions("availableConditions", data, "key", "value" );
	        AutocompleteSynch('availableConditions','selectedConditionCodes');
			reconcileSelects('availableConditions','selectedConditions');
	     }); 
	       
	    
	    if(busObjType1!='INV'){
        	getElementByIdOrByName("mappingGuideTR").style.visibility = "hidden";
        	getElementByIdOrByName("mappingGuideTR").className="none";
	    }else{
 	        getElementByIdOrByName("mappingGuideTR").style.visibility = "visible";
  	        getElementByIdOrByName("mappingGuideTR").className="";
	    }
        if( busObjType1 == 'VAC'){
           
			getElementByIdOrByName("subSection2").style.display = "none";
			
		}else{
      			
			getElementByIdOrByName("subSection2").style.display = "block";
			getElementByIdOrByName("unableToCreatePageInfo").style.display = "none";
			getElementByIdOrByName("submitButton").disabled=false;
        	getElementByIdOrByName("submitButton1").disabled=false;
		}
		
        
	}
	
       </script>       
        <style type="text/css">
            
        </style>
    </head>
<!--
    Page Summary:
    -------------
    This file includes the Add Page details for adding a new draft page.
-->


<% 
    int sectionIndex = 0;
    int subSectionIndex = 0;
    String tabId = "userInterface";
    
%>
 <body onload="autocompTxtValuesForJSP();startCountdown();reconcileSelects('availableConditions','selectedConditions');">
    <div id="blockparent"></div>
      <html:form action="/ManagePage.do" styleId="addPageForm">
        <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
                    	     <!-- Top Nav Bar and top button bar -->
                    		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
		             <!-- Top button bar -->
    				<div style="text-align: right;"><i> <span class="boldRed">*</span> Indicates a Required Field</i></div>
	      			<div class="grayButtonBar" style="text-align: right;">
	        	   	 	<input type="button" id="submitButton" name="Submit" value="Submit" onclick="submitForm();"/>
	           	 		<input type="button"  name="Cancel" value="Cancel" onclick="cancelForm();"/>
	           	 	</div>
        	   		<!-- Page Errors -->
        	   		
				<% if(request.getAttribute("CreateError") != null) { %>
					<div class="infoBox errors">
						<b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
						<%= request.getAttribute("CreateError")%>
					</div>
				<% }%>        	   		
        	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
    				<logic:notEmpty name="pageBuilderForm" property="errorList">
			        <div class="infoBox errors" id="errorMessages">
			            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
			            <ul>
			                <logic:iterate id="errors" name="pageBuilderForm" property="errorList">
			                         <li>${errors}</li>                    
			                </logic:iterate>
			            </ul>
			        </div>    
			 	   </logic:notEmpty> 				
        
        		    <div id="unableToCreatePageInfo" class="infoBox info" style="text-align: left;display:none">
						
				    </div>
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Add Page" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="no">
        				<!-- SUB_SECTION :Page Details -->
        				<nedss:container id="subSection1" name="Page Details" classType="subSect" displayImg ="false">
        				<tr> 
        					<td colspan=5>
								&nbsp;Select an event type, template, reporting mechanism and enter a page name to create a new page.
							</td>
						</tr>
           					<!-- Event Type - currently always Investigation -->
          						<tr>
             						    <td class="fieldName" title="The type of event associated with the page.">
                 						<span id="busObjTypeL"/>
                 						<font class="boldTenRed" >*</font>
                 						<b>Event Type:</b>
               						    </td>
          						    <td>
            							<html:select title="Event Type" property="selection.waTemplateDT.busObjType" styleId="busObjType" onchange="getBusinessObjectTypeDD(this);showHideDataMartName();">
                 							<html:optionsCollection property="codedValue(BUS_OBJ_TYPE)" value="key" label="value"/>
                					    	</html:select>
         						    </td>
       							</tr>
       						<!-- Select Template -->
 							<tr>
                					    <td class="fieldName" title="A name that uniquely identifies the template to users of the system." >
                    						<span id="existingTemplateL"/>
                    						<font class="boldTenRed" >*</font>
                    						<b>Template:</b>
               						    </td>
 	 						    <td>
                						<html:select title="Template" property="selection.waTemplateDT.waTemplateRefUid" styleId="existingTemplate">
                 							<html:optionsCollection property="templateListByBO(INV)" value="key" label="value"/>
                						</html:select>
         						    </td>
    							</tr>
    				
       						<!-- Select Mapping Guide -->
    							<tr id="mappingGuideTR">
							    <td class="fieldName" id="mappingGuideL"   title="The reporting mechanism for the page (e.g, the name of the messaging guide that should be used to report to the CDC).">
								<font class="boldTenRed" >*</font>
								<b>Message Mapping Guide:</b>
							    </td>
							    <td style="white-space:nowrap">
	    							<html:select title="Message Mapping Guide" property="selection.waTemplateDT.messageId" styleId = "mappingGuide">
									<html:optionsCollection property="messageIdList" value="key" label="value"/>
	    							</html:select>
							    </td>
   							</tr>
   						
   						<!-- Page Name -->
   							<tr>
   							    <td class="fieldName"  title="A name that uniquely identifies the page to users of the system.">
   								<font class="boldTenRed" >*</font>
   							   	<span class="InputFieldLabel" id="uniquePageNameL" >
   							    	<b>Page Name:</b></span>
   							    </td>
   							    <td>
   								<html:text title="Page Name" property="selection.waTemplateDT.templateNm" size="50" maxlength="50" onkeyup="isSpecialCharEnteredForName(this,null,event);"  styleId="uniquePageName"/>
							    </td>
							</tr>
							
							<!-- Data Mart Name -->
   							<tr id="dataMartRow">
   							    <td class="fieldName"  title="A name that uniquely identifies the Data Mart to users of the system.">
   							   	<span class="InputFieldLabel" id="uniqueDataMartNameL" >
   							    	<b>Data Mart Name:</b></span>
   							    </td>
   							    <td>
   								<html:text title="Data Mart Name" property="selection.waTemplateDT.dataMartNm" size="50" maxlength="21" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,21);"  styleId="dataMartNm"/>
							    </td>
							</tr>
							
            						<!-- Page Description -->
   							<tr>
   							    <td class="fieldName"  title="A description of the page.">
   								<b>Page Description:</b>
   							    </td>
							    <td>
								<html:textarea title="Page Description" style="WIDTH: 500px; HEIGHT: 100px;" property="selection.waTemplateDT.descTxt" styleId="descTxt" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"/>
							    </td>
  							</tr>
  					
  							<tr>  <!-- Conditions -->  
  							<nedss:container id="subSection2" name="Page Conditions" classType="subSect" displayImg ="false">
  								     <tr> <td colspan=5>
							&nbsp;If the condition you would like to map is not listed in the available condition list below, please search the page library to ensure the condition has not already been &nbsp;mapped to another page. If the condition was previously mapped, it cannot be mapped to this page (it must be cloned).
							</td></tr>
  								    <tr>
        								<td class="fieldName" id="relatedCondL"  title="The condition or conditions related to this page.">
        								   <b>Related Condition(s):</b> 
        								</td>
        								<td style="width:11;">
        									<html:select title="Related Condition(s) left" styleId="availableConditions" name="pageBuilderForm" property="conditionCodes" multiple="true" size="10" style="width:310">
        									   <logic:iterate id="DropDownCodeDT"  name="pageBuilderForm" property="conditionAllListByBO(INV)" type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
        										<bean:define id="value" name="DropDownCodeDT" property="value"/>
        								 		<bean:define id="key" name="DropDownCodeDT" property="key"/>
        									    	<html:option value="<%=key.toString()%>">
                  										<bean:write name="value"/>
              									    	</html:option>
              									  </logic:iterate>
           									</html:select>
         								</td>
         								<td style="width:1;text-align: left;">
	                 							<input type="button" align="left" value="Add &gt" id=" " onClick="addSelection(); return false;"  style="width:75;"/>
	                	 						<input type="button" align="left" value="&lt Remove" id=" "  onClick="removeSelection(); return false;" style="width:75;"/>
        		 						</td>
         								<td style="width:11;">
	                							<html:select title="Related Condition(s) right" styleId="selectedConditions" name="pageBuilderForm" property="selectedConditionCodes" multiple="true" size="10" style="width:310">
	                							  <logic:iterate id="DropDownCodeDT"  name="pageBuilderForm" property="selectedCondList" type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
	                 								<bean:define id="value" name="DropDownCodeDT" property="value"/>
	                  					    			<bean:define id="key" name="DropDownCodeDT" property="key"/>
	                 					     			<html:option value="<%=key.toString()%>">
	                        					 		    <bean:write name="value"/>
	                      								</html:option>
	                							  </logic:iterate>
	                     							</html:select>
                    							</td>
                    							<td style="width:520;">&nbsp;</td>
         							    </tr>
         							    </nedss:container>
         						
  							 </tr>  <!-- Conditions -->
  						
   					</nedss:container>
				</nedss:container>
				<html:hidden property="selection.waTemplateDT.busObjType"  styleId="busObjTypeHidden" />
				<html:hidden property="selection.waTemplateDT.waTemplateRefUid"  styleId="templateValueHidden" />
				<html:hidden name="pageBuilderForm" property="actionMode"  styleId="actionModeHidden" />
				<html:hidden property="selection.waTemplateDT.templateNm"  styleId="pageNameHidden" />
    				<div class="grayButtonBar" style="text-align: right;">
            				<input type="button" id="submitButton1" align="right" name="Submit" value="Submit" onclick="submitForm();"/>
            				<input type="button" align="right" name="Cancel" value="Cancel" onclick="cancelForm();"/>
   		 		</div>
			</div> <!-- id = "bd" -->
    		</td>
   	    </tr>
	</div> <!-- id = "doc3" -->
		<input type="hidden" id="preventDuplicatePage" value="<%= request.getAttribute("preventDuplicatePage") %>"/>  
		<input type="hidden" id="pageAction" value="ADD"/>  
	<script type='text/javascript' >
			
		var pageAction = getElementByIdOrByName("actionModeHidden");
		
		if( pageAction != 'undefined' ){
		
         		if(pageAction.value == "CREATE_SUBMIT"){ 
					
					fnOnloadBusinessObjectTypeDD();
				}
		}
		
	</script> 
    </html:form>
  </body>
</html>