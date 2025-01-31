<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%@ page
	import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<html lang="en">
<head>
<title>NBS: Manage Pages</title>
<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
<script type='text/javascript' src='/nbs/dwr/util.js'></script>
<script src="/nbs/dwr/interface/JPortPage.js" type="text/javascript"></script>

<%@ include file="../../jsp/resources.jsp"%>
<script type="text/javascript" src="PortPageSpecific.js"></script>
<script language="JavaScript">    
     function submitPortPageForm()
     {
    	 setSelectedPageFieldValues();
         if(portPageReqFlds()==true)
        	 return false;
         else{       
               // setSelectedPageOptionValue();
              var divElt = getElementByIdOrByName("blockparent");
    	    	divElt.style.display = "block";		
                 
                getElementByIdOrByName("importFile").disabled=true;
				document.forms[0].action ="/nbs/PortPage.do?method=portPageSubmit&initLoad=true";
     			document.forms[0].submit();
		        }
		        	
      }
			           
	function cancelForm()
	{

	        	document.forms[0].target="";     
	            var confirmMsg="You have indicated that you would like to cancel from this page. All changes that have been made will be lost and cannot be recovered. Select OK to continue or Cancel to return to the active page.";
	            if (confirm(confirmMsg)) {
	            	document.forms[0].action ="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true";
	                document.forms[0].submit();
	            } else {
	                return false;
	            }    

	            return true; 
	}
	
	function clickImport(){
		$j("body").find(":input").attr("disabled","disabled");
		$j("div#addAttachmentBlock").show();
        $j("div#addAttachmentBlock").find(":input").attr("disabled",""); 
        getElementByIdOrByName("importFile").focus();
       /*  var filePath = $j("#filePath").attr("value");
        if(filePath != null && filePath != "null"){
        	getElementByIdOrByName("importFile").value=filePath;
        } */ 
	}
	
	function cancelImport(){
		/* $j("body").find(":input").attr("disabled","");
		 $j("div#addAttachmentBlock").hide();
		 document.portPageForm.importFile.value = ""; */
		document.forms[0].action ="/nbs/PortPage.do?method=cancelImport&initLoad=true";
		document.forms[0].submit();
		 
	}
	
	function submitImport(){
		//alert("File Path: "+getElementByIdOrByName("importFile").value);
		var errors = new Array();
		var hasErrors = false;
		var index=0;
	
	   	if(jQuery.trim(document.portPageForm.importFile.value) == "") {
	   		if(getElementByIdOrByName("PortPageError")!=undefined)	
	   			getElementByIdOrByName("PortPageError").style.display = 'none';
           	errors[index++] = "Select map to import.";
            hasErrors = true;
            $j("td#chooseFileLabel").css("color", "#CC0000");
	   	}else {
         	$j("td#chooseFileLabel").css("color", "black");
	   	}
		 
	    if(hasErrors){
	       	displayGlobalErrorMessage(errors);
        }else{ 
    		document.forms[0].action ="/nbs/PortPage.do?method=submitImport&type=Import&filePath="+getElementByIdOrByName("importFile").value;
	        document.forms[0].submit();
	    }

    }

/* 	function addSelection(){
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
	} */
	
	
/* 	function removeSelection(){
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
	} */
	
	
/* 
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
	} */
	
	/*The below function gets related conditions,MMG and Page Desc of FROM PAGE*/
	  function getFieldsByFromPage(existingTemplate){
		var existingTemplate1=existingTemplate.value;
		var tempArr=existingTemplate1.split("|");
		if(existingTemplate==null){
			tempArr[0]=existingTemplate;	
		}
	JPortPage.getPageFieldsByUid(tempArr[0],function(data){
		getElementByIdOrByName("dispFromMMG").innerHTML=data[0];
		getElementByIdOrByName("dispFromDESC").innerHTML=data[1];
		getElementByIdOrByName("dispFromCond").innerHTML=data[2];
	});
	JPortPage.getToPageList(existingTemplate1,function(data){
		var toTemplate = getElementByIdOrByName("toTemplate").value;
		DWRUtil.removeAllOptions("toTemplate");
		DWRUtil.addOptions("toTemplate",data,"key","value");
		
		if(toTemplate != null && toTemplate.length != 0 ){
			
			getElementByIdOrByName("toTemplate").value = toTemplate;
		}
	});
/* 	if(getElementByIdOrByName("toTemplate").value==""){
		getElementByIdOrByName("Submit").disabled=true;
		getElementByIdOrByName("Submit2").disabled=true;
	}else{
		getElementByIdOrByName("Submit").disabled=false;
		getElementByIdOrByName("Submit2").disabled=false;
	} */
 }
	
	/*The below function gets related conditions,MMG and Page Desc of TO PAGE*/
	function getFieldsByToPage(existingTemplate){
		var existingTemplate1=existingTemplate.value;
		var tempArr=existingTemplate1.split("|");
		if(existingTemplate==null){
			tempArr[0]=existingTemplate;	
		}
		JPortPage.getPageFieldsByUid(tempArr[0],function(data){
			getElementByIdOrByName("dispToMMG").innerHTML=data[0];
			getElementByIdOrByName("dispToDESC").innerHTML=data[1];
			getElementByIdOrByName("dispToCond").innerHTML=data[2];
		});
		/* if(getElementByIdOrByName("fromTemplate").value==""){
			getElementByIdOrByName("Submit").disabled=true;
			getElementByIdOrByName("Submit2").disabled=true;
		}else{
			getElementByIdOrByName("Submit").disabled=false;
			getElementByIdOrByName("Submit2").disabled=false;
		} */
	}
	function getFieldsAfterPageLoad(){
		
		var fromPage = getElementByIdOrByName("fromPageFormCdValue").value;
		getElementByIdOrByName("fromTemplate").value = fromPage; 
		var toPage = getElementByIdOrByName("toPageFormCdValue").value;
		getElementByIdOrByName("toTemplate").value = toPage;
		
		if(fromPage.length != 0 && fromPage != null)
			getFieldsByFromPage(getElementByIdOrByName("fromTemplate"));
		
		
		if(toPage.length != 0 && toPage != null)
			getFieldsByToPage(getElementByIdOrByName("toTemplate"));
		getElementByIdOrByName("mapName").focus();
	}
	
	function setSelectedPageFieldValues(){
		var fromPage =getElementByIdOrByName("fromTemplate").value; 
		getElementByIdOrByName("fromPageFormCdValue").value = fromPage;
		
		var toPage = getElementByIdOrByName("toTemplate").value;
		getElementByIdOrByName("toPageFormCdValue").value = toPage;
		
	}
	</script>
<style type="text/css">
    
    .boxed {
          border: 1px solid #5F8DBF ;
             }
        
</style>
</head>
<!--
    Page Summary:
    -------------
    This file includes the Port Page details  to create a port page.
-->


<% 
    int sectionIndex = 0;
    int subSectionIndex = 0;
    String tabId = "userInterface";
    
%>
<body onload="autocompTxtValuesForJSP();startCountdown();onLoadAction();getFieldsAfterPageLoad();">
	<div id="blockparent"></div>
	<html:form action="/PortPage.do" styleId="portPageForm" enctype="multipart/form-data">
		<div id="doc3">
			<tr>
				<td>
					<!-- Body div -->
					<div id="bd">
						<!-- Top Nav Bar and top button bar -->
						<%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>
						<!-- Top button bar -->
						<div style="text-align: right;">
							<i> <span class="boldRed">*</span> Indicates a Required Field
							</i>
						</div>
						<div class="grayButtonBar" style="text-align: right;">
							<input type="button" name="Submit" value="Submit" onclick="submitPortPageForm();" />
							<input type="button" name="Cancel" value="Cancel" onclick="cancelForm();" />
						</div>
						<!-- Page Errors -->
						<% if(request.getAttribute("PortPageError") != null) { %>
						<div id="PortPageError" class="infoBox errors">
							<b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br />${fn:escapeXml(PortPageError)}
						</div>
						<% }%>
						<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp"%>

						<!-- SECTION : Add Page -->
						<nedss:container id="section1"
							name="Port Page: Select Port Page Details" classType="sect" displayImg="false" displayLink="false" includeBackToTopLink="no">
							<!-- SUB_SECTION :Page Details -->
							<nedss:container id="subSection1" name="" classType="subSect" displayImg="false">
								<!-- Map Name -->
								<tr>
									<td colspan=5><p>&nbsp;</p>&nbsp;&nbsp;Enter a name for the port mapping. Click on Import to start with an existing map. </td>
									<td class="noTab">&nbsp;</td>
								</tr>
								
								<tr>
									<td class="fieldName" height="35px" id="uniqueMapName"><font class="boldTenRed">*</font><b>Map Name:</b></td>
								<td width="25%">
   								<html:text title="Map Name" property="mapName" size="50" maxlength="100" styleId="mapName"/>
							    </td>
							    <td>
							    <input type="button" align ="left" name="Import" value="Import" onclick="clickImport();" />
							    </td>
							    <td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td>
								</tr>
								<!-- To display Selected File to Import. -->
								<tr>
					            <td class="noTab">&nbsp;</td>
					            <td id="fileSelected" class="noTab" style="font-style:italic;font-size:90%"></td>
					            <td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td>
					            </tr>
					            </nedss:container>
								<!-- Import Map -->
								<tr>
					                <td style="padding:0.5em;">	
					                    <div id="addAttachmentBlock" class="boxed" width="100%" style="background:#d1e5f6">
					                    
				                           <table role="presentation" class="formTable" style="width:100%; margin:0px">
				                              <td class="noTab">&nbsp;</td>                  
                                              <td style="text-align:left"><b>Import Map</b> </td>
				                                                  
									        <tr>
									               <td colspan="2" style="text-align:left;"><span style="color:black; font-weight:normal;"> &nbsp;&nbsp;Please enter the location of or browse to the Map you would like to import.	</a></td>
									        </tr>
									        <!-- File Attachment -->
									        <tr>
									            <td class="fieldName" id="chooseFileLabel"> <span style="color:#CC0000;">*</span><b>Map Name:</b></td>
									            <td width="60%"><html:file onkeypress="this.click();" title="Choose file" name="portPageForm" property="importFile" styleId="importFile" style="height: 1.8em;" maxlength="70" size= "70"> </html:file></td>
														        <html:hidden  name="portPageForm" styleId="toPageFormCdValue" property="toPageFormCd"/>
																<html:hidden  name="portPageForm" styleId="fromPageFormCdValue" property="fromPageFormCd"/>
									        </tr> 
									         <tr>
									            <td colspan="2" style="text-align:right;">
									                <input type="button" value="Submit" name="submitButton" onclick="setSelectedPageFieldValues();submitImport();"/>
									                <input type="reset" value="Cancel" name="cancelButton" onclick="cancelImport()"/>
									            </td>
									        </tr>      
									    </table>
									    
					                    </div>
					                </td>
					                <td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td>
					            </tr>
							
								<!-- From Page Selection -->
								<nedss:container id="subSection2" name="From Page Selection" classType="subSect" displayImg="false">
									<tr>
										<td colspan=5>
											<p>&nbsp;</p>&nbsp;&nbsp;Select the page from which you would like to port data:
										</td>
									</tr>
									<!--From  Page Name -->
									<tr>
										<td class="fieldName" valign="top"><span class="InputFieldLabel" id="uniquePageNameL" style="" title="From Page Name"/>
												<font class="boldTenRed">*</font><b>From Page Name:</b>
										</span></td>
										<td><html:select title="From Page Name" styleId="fromTemplate" name="portPageForm" property="fromPageFormCd" size="10" style="width:210" onchange="getFieldsByFromPage(this)" >
        									   <logic:iterate id="DropDownCodeDT"  name="fromPageDD"  type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
        										<bean:define id="value" name="DropDownCodeDT" property="value"/>
        								 		<bean:define id="key" name="DropDownCodeDT" property="key"/>
        									    	<html:option value="<%=key.toString()%>">
                  										<bean:write name="value"/>
              									    	</html:option>
              									  </logic:iterate>
           									</html:select></td>
										<td class="noTab">&nbsp;</td>
										<td class="noTab">&nbsp;</td>
										<td class="noTab">&nbsp;</td>
									</tr>
									<!-- From Page Description -->
									<tr>
										<td class="fieldName" height="35px"><b>Page Description:</b></td>
										<td id="dispFromDESC"></td>
										<td class="noTab">&nbsp;</td>
										<td class="noTab">&nbsp;</td>
										<td class="noTab">&nbsp;</td>
									</tr>

									<!-- From Page  Mapping Guide -->
									<tr>
										<td class="fieldName" height="35px" id="mappingGuideL"><b>Message Mapping Guide:</b></td>
										<td style="white-space: nowrap" id="dispFromMMG"></td>
										<td class="noTab">&nbsp;</td>
										<td class="noTab">&nbsp;</td>
										<td class="noTab">&nbsp;</td>
									</tr>

									<!--From Page Conditions  -->
										<tr>
											<td class="fieldName" valign="top" height="35px"><b>Related Condition(s):</b></td>
											<td id="dispFromCond"></td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
										</tr>

									<!-- To Page Selection -->
									<nedss:container id="subSection3" name="To Page Selection" classType="subSect" displayImg="false">
										<tr>
											<td colspan=5>
												<p>&nbsp;</p>&nbsp;&nbsp;Select the page to which you would like to port data:
											</td>
										</tr>

										<!-- To Page -->
										<tr>
											<td class="fieldName" valign="top"><span title="Template"
												class="fieldName" id="existingTemplateL" title="From Page Name"/> <font
												class="boldTenRed">*</font> <b> To Page Name:</b></td>
											<td><html:select title = "To Page Name" styleId="toTemplate" name="portPageForm" property="toPageFormCd" size="10" style="width:210" onchange="getFieldsByToPage(this)" >
        									   <logic:iterate id="DropDownCodeDT"  name="toPageDD"  type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
        										<bean:define id="value" name="DropDownCodeDT" property="value"/>
        								 		<bean:define id="key" name="DropDownCodeDT" property="key"/>
        									    	<html:option value="<%=key.toString()%>">
                  										<bean:write name="value"/>
              									    	</html:option>
              									  </logic:iterate>
           									</html:select></td>
											<td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td><td class="noTab">&nbsp;</td>
										</tr>

										<!-- To Page Description -->
										<tr>
											<td class="fieldName" height="35px"><b>Page Description:</b></td>
											<td id="dispToDESC"></td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
										</tr>

										<!-- To Page Select Mapping Guide -->
										<tr>
											<td class="fieldName" id="mappingGuideL" height="35px"><b>Message Mapping Guide:</b></td>
											<td style="white-space: nowrap" id="dispToMMG"></td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
										</tr>

										<!-- To Page Conditions- -->

											<tr>
											<td class="fieldName" valign="top" height="35px"><b>Related Condition(s):</b></td>
											<td id="dispToCond"></td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
											<td class="noTab">&nbsp;</td>
										</tr>
									</nedss:container>
								</nedss:container>
							</nedss:container>
						<div class="grayButtonBar" style="text-align: right;">
							<input type="button" align="right" name="Submit2" value="Submit" onclick="submitPortPageForm();" /> 
							<input type="button" align="right" name="Cancel" value="Cancel" onclick="cancelForm();" />
						</div>
					</div> <!-- id = "bd" -->
				</td>
			</tr>
		</div>
		<!-- id = "doc3" -->
	</html:form>
	
	<input type="hidden" id="fileName" value="${fn:escapeXml(fileName)}"/>
	<input type="hidden" id="filePath" value="${fn:escapeXml(filePath)}"/>
	
	<html:hidden  name="portPageForm" styleId="toPageFormCdValue" property="toPageFormCd"/>
	<html:hidden  name="portPageForm" styleId="fromPageFormCdValue" property="fromPageFormCd"/>
</body>
</html>