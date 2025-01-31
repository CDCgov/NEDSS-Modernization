<%@page import="gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT"%>
<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.*"%>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
<title>${fn:escapeXml(PageTitle)}</title>
<%@ include file="/jsp/resources.jsp"%>
<SCRIPT Language="JavaScript"
	Src="/nbs/dwr/interface/JTriggerCodeForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="TriggerCodes.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="genericQueue.js"></SCRIPT>
<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="srtadmin.js"></script>
<script language="JavaScript">
		
		function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
		/**
		* addNewCode: method called from Add New Code button
		*/
        function addNewCode(){
        	document.forms[0].action ="${triggerCodeForm.attributeMap.AddNewCode}";
        	document.forms[0].submit();
        }

        
		
		/*
		searchTriggerCodes: method to submit the search. First check if the validation method returns true
		*/
        function searchTriggerCodes()
        {
           if(triggerCodesReqFlds()) {
                return false;
            } else {
                document.forms[0].action ="/nbs/TriggerCodes.do?method=searchLabSubmit&initLoad=true";
            }
        }
		
		function search(){
			document.forms[0].action="/nbs/SearchTriggerCodes.do?method=searchLabLoad";
			document.forms[0].submit();
		}
           function addNewCode()
            {
                //document.forms[0].action ="/nbs/ReceivingSystem.do?method=createLoadRecFacility&exportReceivingFacilityUid"+"#"+"expAlg";
                window.location.href="/nbs/TriggerCodes.do?method=createLoadTriggerCode&exportReceivingFacilityUid"+"#"+"expAlg";
            }
           
           function displayAddNewCode()
           {
        	   
               
           }/*
           function viewRecFacSys(exportReceivingFacilityUid)
           {
				document.forms[0].action ="/nbs/TriggerCodes.do?method=viewRecFacility&"+exportReceivingFacilityUid+"#"+"expAlg";
				document.forms[0].submit();
           }*/
          /*
			function editRecFacSys(exportReceivingFacilityUid){
				document.forms[0].action ="/nbs/TriggerCodes.do?method=editRecFacility&"+exportReceivingFacilityUid+"#"+"expAlg";
				document.forms[0].submit();
           }*/
			
			/**
			*	viewTriggerCode: method called from the view icon
			*/
			
			function viewTriggerCode(nbsUid){
				
				
				
              	document.forms[0].action = "/nbs/TriggerCodes.do?method=viewTriggerCode&nbsUid="+nbsUid;
               	document.forms[0].submit();
           }
           
           /**
			*	editTriggerCode: method called from the edit icon
			*/
			
			function editTriggerCode(nbsUid){
				
				
				document.forms[0].action = "/nbs/TriggerCodes.do?method=editTriggerCode&nbsUid="+nbsUid;
              	document.forms[0].submit();		
       	   
          }
          
           
           function selectfilterCriteria() {
           	document.forms[0].action = "/nbs/TriggerCodes.do?method=filterTriggerCodesSubmit";
           	document.forms[0].submit();
           }
           
           /**
           * dropdownsFunctionality: method to attach filter icons to the column headers. This was created in a separated method to be able to call it after we click
           view/edit icons.
           */
           
           function filterFunctionality(){
        	   
        	   attachIcons();
				makeMSelects();
				displayTooltips();
        	   
           }
			window.onload = function() {
				
				//getElementByIdOrByName("srchCriteria_textbox").setAttribute("size","9");
				//getElementByIdOrByName("srchCriteria_textbox").parentNode.setAttribute("style", "width:7%");
				if(document.getElementById("tableResults")!=null && document.getElementById("tableResults").getAttribute("style").indexOf("display: none")==-1){
					filterFunctionality();
				}
				showCount();
				startCountdown();
				setDropdowns();
				//recFecOnLoad();
				//getActionDropdown();
					<logic:present name="triggerCodeForm" property="attributeMap.NoResultTable">
						disableEnablePrintDownload();
					</logic:present>
				// called during the second phase of the page laod
				<logic:present name="accessMode">
					var accessMode = "<bean:write name="accessMode" />";
					if (accessMode == "viewTriggerCode" || accessMode == "editTriggerCode" || accessMode == "createTriggerCode") {
						disableElementsTriggerCodes();
					
						
					}
					
				</logic:present>

				
				// called during the second phase of the page laod
				<logic:present name="accessMode">
					var accessMode = "<bean:write name="accessMode" />";
					if (accessMode == "createSystem") {
						disableTransfer();
						disableRecSys();
						disablejurDerive();
					//	getActionDropdown();
						
					}
					
				</logic:present>
				
				
			}

			
        </script>
<style type="text/css">
div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%; width: 99%;}
div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
  
div#addNewCodeBlock {margin-top:10px; display:none; width:100%; text-align:center;}      

.removefilter {
	background-color: #003470;
	width: 100%;
	height: 25px;
	line-height: 25px;
	float: right;
	text-align: right;
}

		removefilerLink {
			vertical-align: bottom;
		}

		.hyperLink {
			font-size: 10pt;
			font-family: Geneva, Arial, Helvetica, sans-serif;
			color: #FFFFFF;
			text-decoration: none;
		}
		
		#contains input{
			width: 88%;
		}
		</style>
</head>
<body>
	<div id="blockparent"></div>
	<div id="doc3">
		<%
				  String apprRejMsg = request.getAttribute("confirmationMessage") == null ? "" : (String) request.getAttribute("confirmationMessage");
				  if( apprRejMsg!= "") { %>
		<div class="infoBox success">
			<%= apprRejMsg %>
		</div>

		<%}%>




		
		<html:form action="/TriggerCodes.do">
			<tr>
				<td>
					<!-- Body div -->
					<div id="bd">
						<!-- Top Nav Bar and top button bar -->
						<%@ include file="/jsp/topNavFullScreenWidth.jsp"%>
						<%@ include file="/triggercodesforcasereporting/triggerCodes-topbar.jsp"%>
						 <!-- top bar -->
							

				<%@ include file="../../jsp/feedbackMessagesBar.jsp" %> 

					   		 	
                    <div class="grayButtonBar" style="<%= request.getAttribute("displayTable")%>">
                        <table role="presentation" width="100%">
                            <tr>
                                <td align="left" width="80%"></td>
                                <td align="right" >
									<input type="button" name="button" style="width:70px;display: none;" id="" title="Import" value="Import" onClick="functionalityNotImpleted();"/>	
					   		 	</td>
                                <td align="right" style="width:10px; padding-left: 5px;">
									<input type="button" name="Print" style="width:70px;" id="Print" title="Print" value="Print" onClick="printQueue();"/>
					   		 	</td>
					   		 	<td align="right" style="width:10px; padding-left: 5px;">
									<input type="button" name="Download" style="width:75px; margin-right:10px" title="Download" id="Download" value="Download" onClick="exportQueue();"/>
					   		 	</td>
                            </tr>				   		
					   		
					   	</table>	 		
                         
                    </div>
                            			   		
					   		
					<% if (request.getAttribute("ConfirmMesg") != null) { %>
						  <div class="infoBox success">The code <b><c:out value="${ConfirmMesg}"/></b> has been successfully updated in the library.</div>    
					<% } %>	
               
					
					
                    <nedss:container id="section1" name=" Trigger Code Search" classType="sect" 
                            displayImg ="false" displayLink="false">
							
                        <fieldset style="border-width:0px;" id="search">
                            <nedss:container id="subsec1" classType="subSect" displayImg ="false">
							
							
							
                                <tr>
                                    <td colspan="3" align="left" style="padding-top: 5px;padding-left: 7px"><i>At least one search criteria is required to submit a search.</i></td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="reqCodingSystem"><font class="boldTenRed" ></font><span>Coding System:</span></td>
                                    <td colspan="2">
										<html:select title="Enter the Coding System to search" name = "triggerCodeForm" property="codingSystemSelected" styleId="codingSystem" size="50">
                                            <html:optionsCollection property="codingSystemList" value="key" label="value"/>
                                        </html:select>
                                        
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="reqCode" nowrap><font class="boldTenRed" ></font><span>Code:</span></td>
                                    <td colspan="2">
                                        <html:text title="Enter the Code to search" styleId="code" name = "triggerCodeForm" property="codeSelected" size="56" maxlength="20" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="fieldName" id="reqDisplayName"><font class="boldTenRed" ></font><span>Display Name:</span></td>
									
									
                                   
									 <td id="contains">
										<html:select title = "Select the Display Name to search" value = "Contains" name = "triggerCodeForm" property="displayNameOperatorSelected" styleId="srchCriteria">
				                        <html:optionsCollection property="operatorList" value="key" label="value"/>
				                        </html:select>	
										</td>
										 <td>
										 <html:text title="Enter the Display Name to search" styleId="displayName" name = "triggerCodeForm" property="displayNameSelected" size="70" maxlength="50"/>
 
									</td>
								
                                </tr>
								<tr>
                                    <td class="fieldName" id="reqDefaultCondition"><font class="boldTenRed" ></font><span>Default Condition:</span></td>
                                    <td colspan="2">
                                        <html:select title="Enter the Default Condition to search" name = "triggerCodeForm" property="defaultConditionSelected" styleId="defaultCondition" size="40">
                                       
                                            <html:optionsCollection property="conditionListView" value="key" label="value"/>
                                        </html:select>					 
                                    </td>
                                </tr>
 
								<tr  align ="right" class="InputField">
                           
                                <td colspan="3" align="right" >
									<input type="submit" name="button" style="width:70px" id="" title="Search" value="Search" onClick="return searchTriggerCodes();"/>
					   		 		<input type="button" name="button" style="width:70px; margin-right:10px" id="" title="Clear" value="Clear" onClick="triggerCodesClearData();"/>	
					   		 	</td>
                            </tr>	
							
							
                            </nedss:container>
                        </fieldset>
                    </nedss:container>
			 
			 

				
				
			 <%String name1 = "code"; String name2 = "Add New Code";%>
             <%@ include file="searchResults.jsp" %>   

							
			<logic:empty name="triggerCodeForm" property="attributeMap.NORESULT">	
					
			<div id="tableResults" style="<%= request.getAttribute("displayTable")%>">
              
						
						<nedss:container id="section2"
							name="Trigger Code Search Results" classType="sect"
							displayImg="false" displayLink="false"
							includeBackToTopLink="false">
							<fieldset style="border-width: 0px;" id="result">
								<html:hidden styleId="queueCnt"
									property="attributeMap.queueCount" />
									<div id="whitebar"
							style="background-color: #FFFFFF; width: 100%; height: 1px;"
							align="right"></div>
						<div class="removefilter" id="removeFilters">
							<a class="removefilerLink" href="javascript:clearFilter()"><font
								class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font>
							</a>
						</div>
								<table role="presentation" width="100%">
									<tr>
										<td align="center"><display:table name="manageList"
												class="dtTable" style="margin-top:0em; border:1px solid;" id="parent"
												pagesize="${triggerCodeForm.attributeMap.queueSize}"
												requestURI="/nbs/TriggerCodes.do?method=resultsLoad&existing=true"
												sort="external" export="true"
												excludedParams="answerArray(SearchText1) answerArray(SearchText2) answerArray(CODINGSYSTEM) answerArray(CONDITION) answerArray(ProgramArea) answerArray(STATUS)  method">
											<display:column title="<p style='display:none'>View</p>" style="width:3%;" media="html">
                                        	&nbsp;&nbsp;&nbsp;
                                        	
                                        		<img src="page_white_text.gif" tabIndex="0" onkeypress="viewTriggerCode(<%=((TriggerCodesDT)parent).getNbsUid()%>); return false;" onclick="viewTriggerCode(<%=((TriggerCodesDT)parent).getNbsUid()%>)" class="cursorHand" title="View" alt = "View" align="middle" cellspacing="2" cellpadding="3" border="55">
                                        
												</display:column>
												<display:column title="<p style='display:none'>Edit</p>" style="width:3%;" media="html">
                            				 &nbsp;&nbsp;&nbsp;<img onkeypress = "editTriggerCode(<%=((TriggerCodesDT)parent).getNbsUid()%>); return false;" onclick = "editTriggerCode(<%=((TriggerCodesDT)parent).getNbsUid()%>)"
														src="page_white_edit.gif" tabIndex="0" class="cursorHand" title="Edit" alt="Edit" >
												</display:column> 
												
												
												<display:setProperty name="basic.empty.showtable"
													value="true" />
													
					<d:forEach items="${triggerCodeForm.queueCollection}" var="item">

						<bean:define id="media" value="${item.media}" />
						<logic:match name="media" value="html">
							<display:column property="${item.mediaHtmlProperty}" defaultorder="${item.defaultOrder}" sortable="${item.sortable}" sortName="${item.sortNameMethod}" media="html" title="${item.columnName}" style="${item.columnStyle}" class="${item.className}" headerClass="${item.headerClass}" />
						</logic:match>
						<logic:match name="media" value="pdf">
							<display:column property="${item.mediaPdfProperty}" defaultorder="${item.defaultOrder}" sortable="${item.sortable}" sortName="${item.sortNameMethod}" media="pdf" title="${item.columnName}" style="${item.columnStyle}" class="${item.className}" headerClass="${item.headerClass}" />
						</logic:match>
						<logic:match name="media" value="csv">
							<display:column property="${item.mediaCsvProperty}"	defaultorder="${item.defaultOrder}" sortable="${item.sortable}" sortName="${item.sortNameMethod}" media="csv" title="${item.columnName}" style="${item.columnStyle}" class="${item.className}" headerClass="${item.headerClass}" />
						</logic:match>
						<br>
					</d:forEach> 
											</display:table>
											</td>
									</tr>
									
							
								</table>

							</fieldset>
						</nedss:container>

						<tr><td>&nbsp;</td></tr>

					<table role="presentation" width="100%" style="<%= request.getAttribute("displayTable")%>">
						<tr>
							<td align="right">
								<input type="button" name="button" style="width:105px; margin-right:10px;" id="addNewCode2" title="Add New Code" value="Add New Code" onClick="addNewCode();"/>	
							</td>
						</tr>
					</table>
				
				</div>
				
				
	
		</logic:empty>
		
		
		<div>
					<logic:equal name="triggerCodeForm" property="actionMode"
						value="View">
						<%@ include file="viewTriggerCode.jsp"%>
					</logic:equal>
					<logic:equal name="triggerCodeForm" property="actionMode"
						value="Edit">
						<%@ include file="viewTriggerCode.jsp"%>
					</logic:equal>
					<logic:equal name="triggerCodeForm" property="actionMode"
						value="Create">
						<%@ include file="viewTriggerCode.jsp"%>
					</logic:equal>
				
				
						
					<d:forEach items="${triggerCodeForm.queueCollection}" var="item">
                  
                    <bean:define id="filterType" value="${item.filterType}" />
                    <logic:match name="filterType" value="1">
                        <input type='hidden' id="${item.dropdownProperty}" value="<%=request.getAttribute("${item.backendId}") != null ? request.getAttribute("${item.backendId}") : ""%>" />
                    </logic:match>

           			</d:forEach>
					
					<input type='hidden' id='actionMode' value="${fn:escapeXml(ActionMode)}" />
				  	<input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
				    <div id="dropdownsFiltering" style="Display:none"><!-- Added to resolve an issue with IE11 -->

                    <%@ include file="/jsp/dropDowns_Generic_Queue.jsp" %>
 
                	</div>
	                <div style="display: none;visibility: none;" id="errorMessages">
						<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
						<ul>
						<logic:iterate id="errors" name="triggerCodeForm" property="attributeMap.searchCriteria">
							<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
						</logic:iterate>
						</ul>
					</div> 
				
				</div>
				
				
					<div class="grayButtonBar" style="<%= request.getAttribute("displayTable")%>; float:right">
                        <table role="presentation" width="100%">
                            <tr>
                                <td align="left" width="80%"></td>
                                <td align="right" >
									<input type="button" name="button" style="width:70px;display: none;" id="" title="Import" value="Import" onClick="functionalityNotImpleted();"/>	
					   		 	</td>
                                <td align="right" style="width:10px; padding-left: 5px">
									<input type="button" name="Print2" style="width:70px;" id="Print2" title="Print" value="Print" onClick="printQueue();"/>
					   		 	</td>
					   		 	<td align="right" style="width:10px; padding-left: 5px">
									<input type="button" name="Download2" style="width:75px; margin-right:10px" id="Download2" title="Download" value="Download" onClick="exportQueue();"/>
					   		 	</td>
                            </tr>				   		
					   		
					   	</table>	 		
                         
                    </div>
		</div> 
					
					
	</div>
	</div>
	</html:form>
	</div>
	<%@ include file="/jsp/footer.jsp"%>
</body>
</html>