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
       function submitForm()
       {

             if(clonePageReqFlds()==true)
           		return false;
           	else {
           			setSelectedPageOptionValue();
	            		document.forms[0].action ="/nbs/ManagePage.do?method=clonePageSubmit";
           			document.forms[0].submit();
           	      }	
        } 
        
        //performed onblur of pageName field
    	function checkUniquePageName()
			    {
				var pageNm = getElementByIdOrByName("uniquePageName");
                if (pageNm.value == "") { return; }
                var templateType = "Page";
                 
				// dwr call to find if page name is not unique 
				JPageBuilder.getUniquePageNm(pageNm.value,templateType, function(data) {
				if (data == null || data.length > 0)  {
					alert(data[0]);
					pageNm.value = "";
					pageNm.focus();
				    }
 				 })
		}
			           
	function cancelForm()
	{

	        	document.forms[0].target="";     
	            var confirmMsg="You have indicated that you would like to cancel from this page. All changes that have been made will be lost and cannot be recovered. Select OK to continue or Cancel to return to the active page.";
	            if (confirm(confirmMsg)) {
	            	document.forms[0].action ="/nbs/ManagePage.do?method=viewPageDetailsLoad&initLoad=true";
	                document.forms[0].submit();
	            } else {
	                return false;
	            }    

	            return true; 
	}
	        
	
	
	function addSelection(){
	   availableList = getElementByIdOrByName("availableConditions");
	   selectedList = getElementByIdOrByName("selectedConditions");

	   var addIndex = availableList.selectedIndex;
	   while(addIndex > 0) {  //zero item in from list is blank
	    
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
	   availableList.appendChild(
	      selectedList.options.item(selIndex))
	   selectNone(selectedList,availableList);
	   // sortSelectList("availableConditions");
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

	        
	function setSelectedPageOptionValue()
	{
	    var x=document.forms[0].selectedConditionCodes.length;
	    for(var i=0;i<x;i++)
	    {
	        document.forms[0].selectedConditionCodes.options[i].selected=true;
	    }
	}
       </script>       
        <style type="text/css">
            
        </style>
    </head>
<!--
    Page Summary:
    -------------
    This file includes the Clone Page details for adding a new draft page.
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
	        	   	 	<input type="button" name="Submit" value="Submit" onclick="submitForm();"/>
	           	 		<input type="button"  name="Cancel" value="Cancel" onclick="cancelForm();"/>
	           	 	</div>
        	   		<!-- Page Errors -->
				<% if(request.getAttribute("CloneError") != null) { %>
					<div class="infoBox errors">
						<b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
						${fn:escapeXml(CloneError)}
					</div>
				<% }%>          	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Clone Page" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="no">
        				<!-- SUB_SECTION :Page Details -->
        				<nedss:container id="subSection1" name="Page Details" classType="subSect" displayImg ="false">
        				<tr> <td colspan=5>
					    &nbsp;Select a reporting mechanism and enter a page name to create a new page.
					</td></tr>
           					<!-- Event Type - currently always Investigation -->
          						<tr>
             						    <td class="fieldName">
                 						<span disabled='true' title="Event Type" class="InputDisabledLabel" id="busObjTypeL" />
                 						<font class="boldTenRed" >*</font>
                 						Event Type:
               						    </td>
          						    <td>
            							<html:select title="Event Type" property="selection.waTemplateDT.busObjType" styleId="busObjType" disabled='true'>
                 							<html:optionsCollection property="codedValue(BUS_OBJ_TYPE)" value="key" label="value"/>
                					    	</html:select>
         						    </td>
       							</tr>
       						<!-- Seed Page -->
 							<tr>
                					    <td class="fieldName">
                    						<span disabled='true' title="Template" class="InputDisabledLabel" id="existingTemplateL" />
                    						<font class="boldTenRed" >*</font>
                    						Seed Page:
               						    </td>
 	 						    <td>
                						<html:select title="Seed Page" disabled="true" property="selection.waTemplateDT.waTemplateRefUid" styleId="existingTemplate">
                 							<html:optionsCollection property="templateListByBO(${pageBuilderForm.selection.waTemplateDT.busObjType})" value="key" label="value"/>
                						</html:select>
         						    </td>
    							</tr>
       						<!-- Select Mapping Guide -->
       						<logic:equal name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="INV">
    							<tr>
							    <td class="fieldName" id="mappingGuideL">
								<font class="boldTenRed" >*</font>
								<b>Message Mapping Guide:</b>
							    </td>
							    <td style="white-space:nowrap">
	    							<html:select title="Message Mapping Guide" property="selection.waTemplateDT.messageId" styleId = "mappingGuide">
									<html:optionsCollection property="messageIdList" value="key" label="value"/>
	    							</html:select>
							    </td>
   							</tr>
   							</logic:equal>
   						<!-- Page Name -->
   							<tr>
   							    <td class="fieldName">
   								<font class="boldTenRed" >*</font>
   							   	<span class="InputFieldLabel" id="uniquePageNameL" style="" title="The unique name for the page">
   							    	<b>Page Name:</b></span>
   							    </td>
   							    <td>
   								<html:text title="Page Name" property="selection.waTemplateDT.templateNm" size="50" maxlength="50" styleId="uniquePageName"/>
							    </td>
							</tr>
							

							<!-- Data Mart Name -->
							<logic:equal name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="INV">
   							<tr>   							    
   							    <td class="fieldName"  title="A name that uniquely identifies the Data Mart to users of the system.">
								<span  id="uniqueDataMartNameL" />
								<b>Data Mart Name:</b>
   							    </td>
   							    <td >   								
   								<html:text title="Data Mart Name" property="selection.waTemplateDT.dataMartNm" size="50" maxlength="21" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,21);" styleId="dataMartNm"/>   								
							    </td>							    
							</tr>
							</logic:equal>
							
            						<!-- Page Description -->
   							<tr>
   							    <td class="fieldName">
   								<b>Page Description:</b>
   							    </td>
							    <td>
								<html:textarea title="Page Description" style="WIDTH: 500px; HEIGHT: 100px;" property="selection.waTemplateDT.descTxt" styleId="descTxt" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"/>
							    </td>
  							</tr>
  							<tr>  <!-- Conditions -->  
  							<nedss:container id="subSection2" name="Page Conditions" classType="subSect" displayImg ="false">
  								     <tr> <td colspan=5>
							&nbsp;You must select at least one condition from the following list of conditions currently related to the Seed Page to continue with the creation of a cloned page.
							</td></tr>
  								    <tr>
        								<td class="fieldName" id="relatedConditionsL">
        								<font class="boldTenRed" >*</font>
        								   <b>Related Condition(s):</b> 
        								</td>
        								<td style="width:11;">
        									<html:select title="Related Condition(s) left" styleId="availableConditions" name="pageBuilderForm" property="conditionCodes" multiple="true" size="10" style="width:310">
        									   <logic:iterate id="DropDownCodeDT"  name="pageBuilderForm" property="conditionList" type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
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
                    							<td>&nbsp;</td>
         							    </tr>
         							    </nedss:container>
         						
  							 </tr>  <!-- Conditions -->
  							 
   					</nedss:container>
				</nedss:container>
    				<div class="grayButtonBar" style="text-align: right;">
            				<input type="button" align="right" name="Submit" value="Submit" onclick="submitForm();"/>
            				<input type="button" align="right" name="Cancel" value="Cancel" onclick="cancelForm();"/>
   		 		</div>
			</div> <!-- id = "bd" -->
    		</td>
   	    </tr>
	</div> <!-- id = "doc3" -->
    </html:form>
  </body>
</html>