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

    	   var busObjTypValue = getElementByIdOrByName("busObjType").value;
   		
           if(checkEditPageDetailsReqFlds()==true)
         		return false;
         	else {        
         			    if(busObjTypValue !="VAC" && busObjTypValue !="LAB" && busObjTypValue !="ISO" && busObjTypValue !="SUS"){
							setSelectedPageOptionValue();
	            		}
	            		document.forms[0].action ="/nbs/ManagePage.do?method=editPageDetailsSubmit";
           			document.forms[0].submit();
           	      }	
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
	        
	//Edit Page Required Fields Validation Function
	function checkEditPageDetailsReqFlds(){
	
		$j(".infoBox").hide();
	    
	    	var errors = new Array();
	    	var index = 0;
		var isError = false;
	
		var busObjType= getElementByIdOrByName("busObjType");
		var mappingGuide= getElementByIdOrByName("mappingGuide");
		var uniquePageName= getElementByIdOrByName("uniquePageName");
		if(busObjType != null && busObjType.value.length == 0)
			{
				errors[index++] = "Business Object Name is required";
				getElementByIdOrByName("busObjTypeL").style.color="#CC0000";
				isError = true;
			}
			else {
			   getElementByIdOrByName("busObjTypeL").style.color="black";
		}
				
		
		var dataMartNm = getElementByIdOrByName("dataMartNm");
		if(dataMartNm){
				if(dataMartNm!=null && dataMartNm!='undefined' && dataMartNm.value!=null && dataMartNm.value.match(/^\d/)){
					errors[index++]="Data Mart Name cannot start with a number";
					isError = true;
					}
			}
		if(mappingGuide != null && mappingGuide.value.length == 0)
				{
					errors[index++] = "Mapping Guide is required";
					getElementByIdOrByName("mappingGuideL").style.color="#CC0000";
					isError = true;
				}
		else {
			if (getElementByIdOrByName("mappingGuideL") != null) {
				  getElementByIdOrByName("mappingGuideL").style.color="black";
			}
		}
	
		if(uniquePageName != null) 
			{ 
				var pgName = uniquePageName.value;
				if (jQuery.trim(pgName).length == 0) {
					errors[index++] = "Page Name is required";
					getElementByIdOrByName("uniquePageNameL").style.color="#CC0000";
					isError = true;
				}
			else {
			   getElementByIdOrByName("uniquePageNameL").style.color="black";
			}
		}
	        if(isError){
	     		displayGlobalErrorMessage(errors);
		}
		
		return isError;
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
		if (undefined == toL || toL.length == 0) { 
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

	function editPageOnload(){
		var busObjType1 = getElementByIdOrByName("busObjType").value;
		if( busObjType1 == 'ISO' ||  busObjType1 == 'SUS'){
			getElementByIdOrByName("uniquePageName").disabled = true;
		}
	}
       </script>       
        <style type="text/css">
            
        </style>
    </head>
<!--
    Page Summary:
    -------------
    This file includes the Edit Page Details for editing DMB Page Details. Note that Page Name can not be edited if published.
-->


<% 
    int sectionIndex = 0;
    int subSectionIndex = 0;
    String tabId = "userInterface";
    
%>
 <body onload="autocompTxtValuesForJSP();startCountdown();reconcileSelects('availableConditions','selectedConditions');editPageOnload();">
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
          	   		
				<% if(request.getAttribute("EditDetailError") != null) { %>
					<div class="infoBox errors">
						<b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
						${fn:escapeXml(EditDetailError)}
					</div>
				<% }%>           	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        
    				<!-- SECTION : Edit Page Details --> 
    				<nedss:container id="section1" name="Edit Page Details" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="no">
        				<!-- SUB_SECTION :Page Details -->
        				<nedss:container id="subSection1" name="Page Details" classType="subSect" displayImg ="false">
           					<!-- Event Type - currently always Investigation -->
          						<tr>
             						    <td class="fieldName" title="The type of event associated with the page.">
                 						<span id="busObjTypeL" disabled='true' />
                 						<font class="boldTenRed" >*</font>
                 						<b>Event Type:</b>
               						    </td>
          						    <td>
            							<html:select title="Event Type" property="selection.waTemplateDT.busObjType" styleId="busObjType" disabled='true'>
                 							<html:optionsCollection property="codedValue(BUS_OBJ_TYPE)" value="key" label="value"/>
                					    	</html:select>
         						    </td>
       							</tr>
       						<!-- Select Mapping Guide -->
       						    <logic:equal name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="INV">
	    							<tr>
								    <td class="fieldName" id="mappingGuideL"  title="The reporting mechanism for the page (e.g, the name of the messaging guide that should be used to report to the CDC).">
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
   							    <%if(request.getAttribute("conditionList") != null){%>
   							    <td class="fieldName"  title="A name that uniquely identifies the page to users of the system.">
								<font class="boldTenRed" >*</font>
								<span  id="uniquePageNameL" disabled='true'/>
								<b>Page Name:</b>
   							    </td>
   							    <td>
   								<nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.templateNm" />
							    </td>
							    <%}%>
   							    <%if(request.getAttribute("conditionList") == null){%>
   							    <td class="fieldName"  title="A name that uniquely identifies the page to users of the system.">
								<font class="boldTenRed" >*</font>
								<span  id="uniquePageNameL" />
								<b>Page Name:</b>
   							    </td>
   							    <td >   								
   								<html:text title="Page Name" property="selection.waTemplateDT.templateNm" size="50" maxlength="50" onkeyup="isSpecialCharEnteredForName(this,null,event);" styleId="uniquePageName"/>   								
							    </td>
							    <%}%>
							    
							</tr>
							
							<logic:equal name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="INV">
							<!-- Data Mart Name -->
   							<tr>   							    
   							    <%if(request.getAttribute("conditionList") != null && (request.getAttribute("dataMartName") != null) && !request.getAttribute("dataMartName").equals("null")){%>
   							    <td class="fieldName"  title="A name that uniquely identifies the Data Mart to users of the system.">
								<span  id="uniqueDataMartNameL" disabled='true'/>
								<b>Data Mart Name:</b>
   							    </td>
   							    <td>
   								<nedss:view name = "pageBuilderForm" property="selection.waTemplateDT.dataMartNm" />
							    </td>
							    <%}%><!-- If page has not been published or if the data mart name has not been entered -->
							    
   							    <%if(request.getAttribute("dataMartName") == null || request.getAttribute("dataMartName").equals("null")){%>
   							    <td class="fieldName"  title="A name that uniquely identifies the Data Mart to users of the system.">
								<span  id="uniqueDataMartNameL" />
								<b>Data Mart Name:</b>
   							    </td>
   							    <td >   								
   								<html:text title="Data Mart Name" property="selection.waTemplateDT.dataMartNm" size="50" maxlength="21" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,21);" styleId="dataMartNm"/>   								
							    </td>
							    <%}%>
							    
							</tr>
							</logic:equal>
            						<!-- Page Description -->
   							<tr>
   							    <td class="fieldName"  title="A description of the page.">
   								<b>Page Description:</b>
   							    </td>
							    <td>
								<html:textarea title="Page Description" style="WIDTH: 500px; HEIGHT: 100px;" property="selection.waTemplateDT.descTxt" styleId="descTxt" onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"/>
							    </td>
  							</tr>
  							<!-- Conditions -->  
  					 <logic:notEqual name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="VAC"> 
  					 <logic:notEqual name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="LAB">
  					 <logic:notEqual name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="ISO">
  					 <logic:notEqual name="pageBuilderForm" property="selection.waTemplateDT.busObjType" value="SUS"> 
  							<nedss:container id="subSection2" name="Page Conditions" classType="subSect" displayImg ="false">
  								    <tr> <td colspan=5>
									&nbsp;If the condition you would like to map is not listed in the available condition list below, please search the page library to ensure the condition has not already been &nbsp;mapped to another page. If the condition was previously mapped, it cannot be mapped to this page (it must be cloned).
								    </td></tr>
  								    <tr>
        								<td class="fieldName" id="relatedCondL"  title="Additional conditions related to this published page.">
        								   <b>Relate Additional Condition(s):</b> 
        								</td>
        								<td style="width:11;">
        									<html:select title="Relate Additional Condition(s) left" styleId="availableConditions" name="pageBuilderForm" property="conditionCodes" multiple="true" size="10" style="width:310">
        									   <logic:iterate id="DropDownCodeDT"  name="pageBuilderForm" property="conditionAllListByBO(${pageBuilderForm.selection.waTemplateDT.busObjType})" type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
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
	                							<html:select title="Relate Additional Condition(s) right" styleId="selectedConditions" name="pageBuilderForm" property="selectedConditionCodes" multiple="true" size="10" style="width:310">
	                							  <logic:iterate id="DropDownCodeDT"  name="pageBuilderForm" property="selectedCondList" type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
	                 								<bean:define id="value" name="DropDownCodeDT" property="value"/>
	                  					    			<bean:define id="key" name="DropDownCodeDT" property="key"/>
	                 					     			<html:option value="<%=key.toString()%>">
	                        					 		    <bean:write name="value"/>
	                      								</html:option>
	                							  </logic:iterate>
	                     							</html:select>
                    							</td>
         							    </tr>					 
  							 <!-- Conditions -->
							<%if(request.getAttribute("conditionList") != null){%>
							      
  								<tr>
  									<td colspan=5>
									&nbsp;The following is a list of conditions that have been mapped to and published with this page. If you would like to create a new page for a condition that is currently &nbsp;mapped to this page, please utilize the Clone Page functionality from View Page Details.
									</td>
								</tr>
								<tr>
									<td colspan=1>
									</td>
									<td colspan=4 title="The condition or conditions related to this page.">
										<table role="presentation">
										<nedss:Condition  toolTip="The condition or conditions related to this page." busObjType="${pageBuilderForm.selection.waTemplateDT.busObjType}" name="conditionList" conditionLabel="Related Condition(s)" />
										</table>
									</td>
							 	</tr>
							      
							<%}%>
						  </nedss:container>
						  </logic:notEqual>
						  </logic:notEqual>
						 </logic:notEqual>  						 
   						 </logic:notEqual>  		
   						 				 
   					</nedss:container>
				</nedss:container>
    				<div class="grayButtonBar" style="text-align: right;">
            				<input type="button" align="right" name="Submit" value="Submit" onclick="submitForm();"/>
            				<input type="button" align="right" name="Cancel" value="Cancel" onclick="cancelForm();"/>
   		 		</div>
			</div> <!-- id = "bd" -->
    		</td></tr>
	</div> <!-- id = "doc3" -->
    </html:form>
  </body>
</html>