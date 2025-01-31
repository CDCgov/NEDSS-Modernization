<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil"%>
<html lang="en">
    <head>
     <title>NBS: Manage Pages</title>
     <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
     <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
     <script src="/nbs/dwr/interface/JPortPage.js" type="text/javascript"></script>
     <%@ include file="../../jsp/resources.jsp" %>     
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
     <script type="text/javascript" src="PortPageSpecific.js"></script>
     <script type="text/javascript" src="Globals.js"></script>
     <script language="JavaScript">
  
		function reviewMapping() {      
			document.forms[0].action="/nbs/PortPage.do?method=submitAnswerMapping";
		    document.forms[0].submit();
	    }
	    
	    
	    function preRun(){
	    	document.forms[0].action="/nbs/PortPage.do?method=submitPreRun&initLoad=true";
		    document.forms[0].submit();
		
		   
	    }
	    
	    function runConversion(){
	    	var numberOfCasesToMigrate = getElementByIdOrByName("numberOfCasesToMigrate");
	    	if(numberOfCasesToMigrate != null && numberOfCasesToMigrate.value.length == 0) {
	    		var errors = new Array();
		        errors[0] = "Enter number of records to migrate";
		        getElementByIdOrByName("numberOfCasesToMigrate").style.color="990000";
		        getElementByIdOrByName("NumOfCases").style.color="990000";
		        displayGlobalErrorMessage(errors);
		        getElementByIdOrByName("numberOfCasesToMigrate").focus();
		        return false;
    		}
	    	else if(numberOfCasesToMigrate.disabled!= true && !isInteger(numberOfCasesToMigrate.value)) {
	    		var errors = new Array();
		        errors[0] = "Numeric value is required for number of cases to migrate";
		        getElementByIdOrByName("NumOfCases").style.color="990000";
		        displayGlobalErrorMessage(errors);
		        getElementByIdOrByName("numberOfCasesToMigrate").focus();
		        return false;
    		}
	    	
	    	var sCond=getElementByIdOrByName("conditionListId");

	    	var selectedCondition="ALL"; // For vaccination condition doen't exist so set default to ALL
	    	if(sCond!=null && sCond!=='undefined'){
	    		selectedCondition=sCond.options[sCond.selectedIndex].text;
	    	}
	    	
	    	var divElt = getElementByIdOrByName("blockparent");
	    	divElt.style.display = "block";		
            var o = new Object();
            o.opener = self;
            
            var URL =  "/nbs/PortPage.do?method=loadProdRunPopUp&selectedCondition="+selectedCondition+"&numberOfCasesToMigrate="+getElementByIdOrByName("numberOfCasesToMigrate").value;
            var modWin = openWindow(URL,o,GetDialogFeatures(600,450,false,false),divElt,"");
            return false;
	    	//document.forms[0].action="/nbs/PortPage.do?method=submitProductionRun&initLoad=true";
		    //document.forms[0].submit();
	    }
	    
		 function showCount() {
			
				$j(".pagebanner b").each(function(i){ 
					$j(this).append(" of ").append($j("#queueCnt").attr("value"));
				});
				$j(".singlepagebanner b").each(function(i){ 
					var cnt = $j("#queueCnt").attr("value");
					if(cnt > 0)
						$j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
				});		
				
			}
	
 function tabBrowsing(){
	 var i = 0;
	 	 if(getElementByIdOrByName("conditionListId_textbox")!=null)
	 		 getElementByIdOrByName("conditionListId_textbox").tabIndex = ++i;
	 	 if(getElementByIdOrByName("getCaseCnt")!=null)
	 	 	getElementByIdOrByName("getCaseCnt").tabIndex = ++i;
	 	 getElementByIdOrByName("btnPreRun").tabIndex = ++i;
	 	 getElementByIdOrByName("numberOfCasesToMigrate").tabIndex = ++i;
	 	 getElementByIdOrByName("btnRunConversion").tabIndex = ++i;
	 	 getElementByIdOrByName("reviewMappingButton2").tabIndex = ++i;
	 	 if(getElementByIdOrByName("conditionListId_textbox")!=null)
	 		 getElementByIdOrByName("conditionListId_textbox").focus();

	 	var preSuccess=$j("#preRunSuccess").attr("value");
	 	var prodSuccess=$j("#prodRunSuccess").attr("value");
	 	var queueCnt =$j("#queueCnt").attr("value");
	 	
	 	if(preSuccess == "Complete")
	 		getElementByIdOrByName("numberOfCasesToMigrate").focus();
	 	else if((prodSuccess == "Complete" || prodSuccess == "Partial" ) && queueCnt != 0)
	 		getElementByIdOrByName("reviewMappingButton2").focus();
	 }
       </script>       
    </head>

 <body onload="showCount();disableConvBtn();tabBrowsing();">
    <div id="blockparent"></div>
      <html:form action="/PortPage.do" styleId="reviewPageForm">
        <div id="doc3">
                  <!-- Body div -->
                <div id="bd">
                   	     <!-- Top Nav Bar and top button bar -->
                   		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                   		<!-- Top button bar -->
						<div style="text-align: right;">
							<i> <span class="boldRed">*</span> Indicates a Required Field
							</i>
						</div>
	            <!-- Return to Manage Page Porting -->
				<div  style="text-align:right; margin-bottom:8px;">
                    <a href="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true">Return to Manage Page Porting</a>
                </div>
		             <!-- Top button bar -->
	      			<div class="grayButtonBar" style="text-align: right;">
	        	   	 	<input type="button"  value="Review Mapping" onclick="reviewMapping()"/> 
	           	 	</div>
				          	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        			<logic:notEmpty name="portPageForm" property="errorList">
					<div class="infoBox errors" id="errorMessages">
					    <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
					    <ul>
						<logic:iterate id="errors" name="portPageForm" property="errorList">
							 <li>${errors}</li>                    
						</logic:iterate>
					    </ul>
					</div>    
			   	</logic:notEmpty>
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="${section1Header}" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
        			   <div class="infoBox messages" style="text-align: left;">
	           	 	   		<ul>
	           	 	   			<li>
	           	 	   				It is ALWAYS recommended to <span class="boldRed">take a back-up</span> of your database prior to starting any data conversion process.
	           	 	   			</li>
	           	 	   			<li>
	           	 	   				You should NOT perform data conversion in your production environment while your user community is accessing the system.
	           	 	   			</li>
	           	 	   			<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
		           	 	   			<li>
		           	 	   				There are currently ${fromPageTotalAssociatedConditionsCount} active condition(s) using the ${fromPageName}. 
		           	 	   			</li>
	           	 	   			</logic:equal>
	           	 	   		</ul>
	           	 	   </div>
        				<!-- To Display Map Name -->
        			   <div class="grayButtonBar" style="text-align:left;font-weight:bold;font-size:14px">
        				   <nedss:view name="portPageForm" property="mapName"/>
	           	 	   </div>
	           	 	   
	           	 	     <!-- Success Message -->
	           	 	   <div style="text-align:right">
        				     <font color="green" font-weight="bold"><strong><span id="success"></span>
        				           </strong> </font>
        				</div>
	           	 	   
	           	 	   <nedss:container id="section2" name="${section2Header}" classType="sect" displayImg="false" displayLink="false" includeBackToTopLink="false">
	           	 	   		<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
								<!-- Select Condition -->
								<nedss:container id="subsec2" classType="subSect" name="Select Condition" displayImg="false">
									<tr> 
										<td colspan="5">&nbsp;&nbsp;Select a condition to port from the ${fromPageName} to the ${toPageName}, then click on Get Case Count to continue.</td>
	               					</tr>
	               					<tr>
	               						<td class="fieldName" id="ConditionListL" height="35px">
								     		<font class="boldTenRed"> * </font><span>Select Condition:</span>
	               						</td>
	               						<td>
	               							<html:select title="Select Condition" name="portPageForm" property="selectedConditionCode" styleId="conditionListId" onchange="disableBtns()">
	               								<logic:iterate id="DropDownCodeDT"  name="fromPageConditionsList"  type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
	        										<bean:define id="value" name="DropDownCodeDT" property="value"/>
	        								 		<bean:define id="key" name="DropDownCodeDT" property="key"/>
	       									    	<html:option value="<%=key.toString()%>">
	                 										<bean:write name="value"/>
	             									</html:option>
	             								</logic:iterate>
											</html:select>
	               						</td>
	               						<td>
	               							<input type="button" id="getCaseCnt" value="Get Case Count" onclick="getCaseCount();"/>
	               						</td>
	               					</tr>
	               					<tr>
	               						<td class="fieldName" id="conditionPageLabel"><span>Case Count:</span></td>
	               						<td colspan="2" id="caseCount"></td>
	               					</tr>
								</nedss:container>
								<!-- Process Messages -->
								<nedss:container id="subsec3" classType="subSect" name="Process Messages" displayImg="false">
								    <tr>
										<td colspan=5>&nbsp;&nbsp;If there are any notifications requiring approval,please process them,and then run message out processor before continuing 
										with the conversion.</td>
									</tr>
									
									<tr>
									     <td class="fieldName" height= "35px">
								     		<span>Notifications Requiring Approval Count:</span>
	               					     </td>
	               					     <td id="notificationReqApproval">
	               					     
	               					     </td>
									</tr>
									<tr>
									     <td class="fieldName">
									     <span>Notifications To Process Count:</span>  
									     </td>
									     <td id="notificationToProcessCount">
									     
									     </td>
									</tr>
								</nedss:container>
							</logic:equal>
							<!-- Validate Mapping & Convert Condition -->
							<nedss:container id="subsec4" classType="subSect"  name="${subSec4Header}" displayImg="false" >
							<!-- Pre Run -->
							    <tr>
									<td colspan=5>&nbsp;&nbsp;Run the 'pre-run' process to validate the mapping for this condition. </td>
								</tr>
								
								<tr>
								 <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td> <td>&nbsp;</td>
								 <td align="right">
								 	<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
								    	<input id="btnPreRun" type="button"  value="Validate Map (Pre-Run)" onclick="preRun()" disabled="disabled"/>
								    </logic:equal>
								    <logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_LEGACY%>">
								    	<input id="btnPreRun" type="button"  value="Validate Map (Pre-Run)" onclick="preRun()"/>
								    </logic:equal>
								 </td>
								</tr>
								<!-- Run Conversion -->
								<tr>
									<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
								   		<td colspan=5><p>&nbsp;</p>&nbsp;&nbsp;Once the pre-run process runs successfully,indicate the number of cases to convert for the condition selected and run the conversion process.</td>
								    </logic:equal>
								    <logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_LEGACY%>">
								   		<td colspan=5><p>&nbsp;</p>&nbsp;&nbsp;Once the pre-run process runs successfully, indicate the number of records to convert of Legacy Page and run the conversion process.</td>
								    </logic:equal>
								</tr>
								
								<tr>
								   <td class="fieldName" id="NumOfCases">
							     		<font class="boldTenRed"> * </font><span>Enter Number of Records to Migrate:</span>
               					   </td>
               					    <td>
               					    	<input title="Enter Number of Records to Migrate" type="text" name="numberOfCasesToMigrate" id="numberOfCasesToMigrate" size="5" disabled="disabled"> 
               					    	<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_LEGACY%>">
		           	 	   					&nbsp;${legacyRecordCountToConvert}&nbsp;
	           	 	   					</logic:equal>
               					    	of <span id="totalCasesRemaining"></span> remaining records.
               					    </td>
               					    <td>&nbsp;</td><td>&nbsp;</td>
               					   <td align="right">
								   		<input id="btnRunConversion" type="button"  value="Run Conversion" onclick="runConversion()"/>
								   </td> 
								</tr>
								<tr><td colspan=5></td></tr>
					   </nedss:container>
					  </nedss:container>	
	           	 	   
               			<table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">
                                    <display:table name="nbsConversionMasterLogs" class="dtTable" pagesize="10" id="parent" requestURI="/nbs/PortPage.do?method=submitPreRun&existing=true&initLoad=true" sort="external">
                                       	<display:column property="processTypeInd" title="Process" sortable="true" sortName="getProcessTypeInd" defaultorder="ascending"/>                                     
                                        <display:column property="processMessageTxt" title="Message" sortable="true" sortName="getProcessMessageTxt" defaultorder="ascending"/>
                                        <display:column property="statusCd" title="Status" sortable="true" sortName="getStatusCd" defaultorder="ascending"/>
                                        <display:column property="startTime" title="Start Time" sortable="true" sortName="getStartTime" defaultorder="ascending"/>
                                        <display:column property="endTime" title="End Time" sortable="true" sortName="getEndTime" defaultorder="ascending"/>
                                        <display:setProperty name="basic.empty.showtable" value="true" />
                                    </display:table>
   								</td>
                            </tr>                           
                     	</table>
                     	<br/>
                     	<br/>		
                     	                    	
				</nedss:container>
   				<div class="grayButtonBar" style="text-align: right;">
           			<input type="button" id="reviewMappingButton2" value="Review Mapping" onclick="reviewMapping()"/> 
   		 		</div>
			</div> <!-- id = "bd" -->
	</div> <!-- id = "doc3" -->
    </html:form>
        <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
        <input type="hidden" id="preRunSuccess" value="${fn:escapeXml(preRunSuccess)}"/>
        <input type="hidden" id="prodRunSuccess" value="${fn:escapeXml(prodRunSuccess)}"/>
    
    <logic:equal name="populateSelectedCondition" value="true">
	    <script type='text/javascript'>
	    	getCaseCount();
	    	//Populate selected value in condition dropdown
			//var e = getElementByIdOrByName("conditionListId");
			//var selectedCondition = e.options[e.selectedIndex].text;
			//getElementByIdOrByName("conditionListId_textbox").value = selectedCondition;
	    </script>
    </logic:equal>
    
  </body>
</html>