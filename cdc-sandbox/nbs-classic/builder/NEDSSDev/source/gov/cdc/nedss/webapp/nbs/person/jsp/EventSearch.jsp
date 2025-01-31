<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<!-- ################### PAGE TAB ###################### - - -->


<%
String tabId = "editCaseInfo2";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments"};
;
%>



<tr>
	<td>
		<div class="view" id="patientSearchByEvent" style="text-align: center;">


			<%  sectionIndex = 0; %>

   
   <nedss:container id="tab03" name="Event Search" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="sect">
  <!-- ########### SUB SECTION ###########  -->
			      <nedss:container id="patSearch6" name="" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="subSect" >
              
              
              
                   
					<tr>
                        <td class="fieldName" id="TYPE"><span style="color:#CC0000; font-weight:bold;">*</span>  Event Type: </td> 
                        
                        <td>
		                    
		                   
		                     <html:select name="personSearchForm" property="personSearch.reportType" onchange="showHideSections();" styleId="ETYPE" title="Please select the type of Event to search">
								<option value="" onclick="setDefaultValuesOnEventTypeChange();" > &nbsp;</option>
								<!--<option value="CR" onclick="setDefaultValuesOnEventTypeChange();" >Case Report</option>-->
								<option value="I" onclick="setDefaultValuesOnEventTypeChange();" >Investigation</option>
								<option value="LR" onclick="setDefaultValuesOnEventTypeChange();" >Laboratory Report</option>
								<!--<option value="MR" onclick="setDefaultValuesOnEventTypeChange();" >Morbidity Report</option>-->
		                    </html:select>	 <!-- onclick="showHideSections();" -->                                                   
                        </td>
                    </tr>
                                                                  
                                                                   
                    
                </nedss:container>
              </nedss:container>
            
              
			<nedss:container id="tab04" name="General Search" defaultDisplay="F" includeBackToTopLink="F" isHidden="F" classType="sect">

			     
				<!-- ########### SUB SECTION ###########  -->
				<nedss:container id="patSearch6" name="" displayImg="F" displayLink="F" defaultDisplay="F" includeBackToTopLink="F"	isHidden="F" classType="subSect">
 
                    <tr>
								<td width="20%"> &nbsp;</td>
								<td style="width: 7%; white-space: nowrap;"><span id=""><span><b>Operators</b></span></span></td>
								<td width="1%"></td>
								<td align=""><span id=""><span><b>Search Criteria</b></span></span></td>	
								
					</tr>	
					
					<!-- Condition -->
					<tr id = "conditionTR">
						<td class="fieldName" id="CON">Condition:</td>
						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;"></td>
						<td width="1%"></td>
						<td style="white-space: nowrap">
							<div>
								<html:select name="personSearchForm"
									property="personSearch.conditionSelected" styleId="COND"
									title="Please select the condition to search"
									onchange="displaySelectedOptions(this, 'COND-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="conditionL" value="key"
										label="value" />
								</html:select>
								<br/>
								<div id="COND-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
					<!-- Program Area -->
					<tr>
						<td class="fieldName" id="PA">Program Area:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                 <td width="1%"></td>
						<td hidden="hidden" id="PALL">
							<div>
								<html:select name="personSearchForm"
									property="personSearch.programAreaSelected"
									styleId="PROGRAMAREA"
									title="Please select the program area to search"
									onchange="displaySelectedOptions(this, 'PROGRAMAREA-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="programAreaList" value="key"
										label="value" />
								</html:select>
								<br/>
								<div id="PROGRAMAREA-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
						<!-- Program Area For Lab-->
							<td hidden="hidden" id="PALAB">
							<div>
								<html:select name="personSearchForm"
									property="personSearch.programAreaLabSelected"
									styleId="PROGRAMAREALAB"
									title="Please select the program area to search"
									onchange="displaySelectedOptions(this, 'PROGRAMAREALAB-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="programListLabs" value="key"
										label="value" />
								</html:select>
								<br/>
								<div id="PROGRAMAREALAB-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
							<!-- Program Area For Morb-->
						<td hidden="hidden" id="PAMORB">
							<div>
								<html:select name="personSearchForm"
									property="personSearch.programAreaMorbSelected"
									styleId="PROGRAMAREAMORB"
									title="Please select the program area to search"
									onchange="displaySelectedOptions(this, 'PROGRAMAREAMORB-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="programListMorb" value="key"
										label="value" />
								</html:select>
								<br/>
								<div id="PROGRAMAREAMORB-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
						<!-- Program Area Case-->
						<td hidden="hidden" id="PACASE">
							<div>
								<html:select name="personSearchForm"
									property="personSearch.programAreaCaseSelected"
									styleId="PROGRAMAREACASE"
									title="Please select the program area to search"
									onchange="displaySelectedOptions(this, 'PROGRAMAREACASE-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="programListCase" value="key"
										label="value" />
								</html:select>
								<br/>
								<div id="PROGRAMAREACASE-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
						<!-- Program Area Investigation-->
						<td hidden="hidden" id="PAINVE">
							<div>
								<html:select name="personSearchForm"
									property="personSearch.programAreaInvestigationSelected"
									styleId="PROGRAMAREAINVEST"
									title="Please select the program area to search"
									onchange="displaySelectedOptions(this, 'PROGRAMAREAINVEST-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="programListInvestigation"
										value="key" label="value" />
								</html:select>
								<br/>
								<div id="PROGRAMAREAINVEST-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
					
					
					
					
				
				
					
					<!-- Jurisdiction -->
					<tr>
						<td class="fieldName" id="JURIS">Jurisdiction:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                 <td width="1%"></td>
						<td>
							<div>
								<html:select name="personSearchForm"
									property="personSearch.jurisdictionSelected" styleId="JURISD"
									title="Please select the jurisdiction to search"
									onchange="displaySelectedOptions(this, 'JURISD-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="jurisdictions" value="key"
										label="value" />
								</html:select>
								<br/>
								<div id="JURISD-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
					
					<!-- Pregnancy Status -->
					<tr id="ESR101TR">
						<td class="fieldName" id="ESR298L">Pregnancy Status:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                 <td width="1%"></td>
						<td><html:select name="personSearchForm"
								property='personSearch.pregnantSelected'
								styleId="pregnantList" title="Please select the pregnancy status to search"  >
								<html:optionsCollection property="pregnantList" value="key"
									label="value" />
							</html:select></td>
					</tr>
					
					<!-- Event ID Type -->   
					<tr>
						<td class="fieldName" id="ESR100L">Event ID Type:</td>
					
                    <td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                        	<td width="1%"></td>
						<td><html:select name="personSearchForm"
								property="personSearch.actType" styleId="ESR100"
								onchange="showHideEventId();clearEventIdDropdown();"
								title="Please select the event ID type to search">
								<html:optionsCollection
									property="eventIDList" value="key"
									label="value" />
							</html:select></td>
							
					</tr>
					
					<!-- Event ID -->   
					<tr id="ESR101TR">
						<td class="fieldName" disabled="disabled" id="ESR101L">Event ID:</td>

						<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em; width: 7%; white-space: nowrap;">
							<table role="presentation" style="background: #d1e5f6">
								<tr>
									<!-- <td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td> -->
									<td style="background: #d1e5f6;white-space: nowrap;">

										<html:select title = "Event ID operator" name="personSearchForm"
											property="personSearch.docOperator" styleId="EIDOP">
												<%-- <html:optionsCollection property="codedValue(SEARCH_ALPHA)" value="key" label="value" /> --%>
											
											<option value="CT">Contains</option>
											<option value="=" selected>Equal</option>
											<option value="!=">Not Equal</option>
											

										</html:select> 
										 <SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("EIDOP"),"<%=request.getAttribute("EIDOp")%>");</SCRIPT> 
									</td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>

							</table>
						</td>
						<td width="1%"></td>

						<td><html:text property="personSearch.actId" disabled="true"
								styleId="ESR101"
								title="Please enter 'Event ID' with an exact match" /></td>
					</tr>
					
					<!-- Event DATE Type -->
					 <tr>
					
						<td class="fieldName" id="resCode">Event Date Type:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			             <td width="1%"></td>
							
			             <td>
							<html:select name="personSearchForm" property="personSearch.dateType" styleId="ESR200"  onchange="showHideEventFields();clearEventDateDropdown();" title="Please select the event date type to search">
								<html:optionsCollection property="codedValue(NBS_EVENT_SEARCH_DATES)" value="key" label="value" />
							</html:select>
						
						</td>
				<!-- dateList -->
					</tr>
					
					<!-- Event DATE  -->
					<tr>
					<td class="fieldName" id="dateLabel">Event Date:</td>
						
					<td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;width: 7%; white-space: nowrap;">
							<table role="presentation" style="background: #d1e5f6">
								<tr>
								<!-- 	<td style="background: #d1e5f6" width="1%">&nbsp;&nbsp;</td> -->
									<td style="background: #d1e5f6;white-space: nowrap;">

										<html:select title = "Event Date operator" name="personSearchForm"
											property="personSearch.dateOperator" onchange="showHideEventDateFields();" styleId="DateOp">
										
											<%-- <html:optionsCollection property="codedValue(SEARCH_DOB)" value="key" label="value" /> --%>
											<option value="6M">Last 6 Months</option>
											<option value="30D">Last 30 Days</option>
											<option value="7D">Last 7 Days</option>
											<option value="BET">Between&nbsp;&nbsp;</option>
											<option value="=" selected>Equal</option>
											

										</html:select>  
											<SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("DateOp"),"<%=request.getAttribute("DateOp")%>");</SCRIPT>
									</td>
									<td style="background: #d1e5f6" width="1%"></td>
								</tr>

							</table>
						</td>
						<td width="1%"></td>
						<td colspan="2" style="width: 400px"><html:text
								name="personSearchForm" title="Event Date From" property="personSearch.dateFrom"
								styleId="from_date" size="20" maxlength="10"
								onkeyup="DateMask(this,null,event)" /> <html:img
								src="calendar.gif" alt="Select a Date"
								onclick="getCalDate('from_date','from_dateIcon'); return false;"
								onkeypress ="showCalendarEnterKey('from_date','from_dateIcon',event);"
								
								styleId="from_dateIcon"></html:img> <span
							style="margin-right: 10px;margin-left: 10px;"><font style="font-weight: bold;">to</font></span>
						<html:text name="personSearchForm" title="Event Date to" property="personSearch.dateTo"
								
								styleId="to_date" size="20" disabled="true" maxlength="10"
								onkeyup="DateMask(this,null,event)"/> <html:img
								src="calendar.gif" alt="Select a Date"
								onclick="getCalDate('to_date','to_dateIcon'); return false;"
								onkeypress ="showCalendarEnterKey('to_date','to_dateIcon',event); return false;"
								styleId="to_dateIcon"></html:img></td>
					</tr>
						<!-- Entry Method -->
					<tr id="enterymethod">

					
						<td class="fieldName" id="DEMETHOD">Entry Method:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                 <td width="1%"></td>
						
						<td>
						<html:checkbox title="Electronic" name="personSearchForm" property="personSearch.electronicSelected" styleId="ELECTRONIC"></html:checkbox> 
						<span id="ele"><nobr>Electronic</nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<html:checkbox title="Manual" name="personSearchForm" property="personSearch.manualSelected" styleId="MANUAL" style="margin-left:2.75px;"></html:checkbox> 
						<span id="manu"><nobr>Manual</nobr></span>
						<!-- 		
						<input type="checkbox" name="electronic" id="ELECTRONIC" />	Electronic&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 <input type="checkbox" name="manual" id="MANUAL" style="margin-left:2.75px;"/> Manual  -->
						 </td> 
					</tr>
					<!-- Entered By -->
					<tr id="enteredBy">
				

						<td class="fieldName" id="EBY">Entered By:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                 <td width="1%"></td>

						<td>
						
						<html:checkbox title="Internal User" name="personSearchForm" property="personSearch.internalUserSelected" styleId="INTERNALUSER" ></html:checkbox> 
						<nobr>Internal User</nobr>&nbsp;
						<html:checkbox title="External User" name="personSearchForm" property="personSearch.externalUserSelected" styleId="EXTERNALUSER"></html:checkbox> 
						<nobr>External User</nobr>
						<!-- 
						<input type="checkbox" name="internalUser" id="INTERNALUSER" /> Internal User&nbsp;
						<input type="checkbox" name="externalUser" id="EXTERNALUSER" /> External User
						 -->
						 </td> 
					</tr>
					<!-- Event Status -->
					<tr id="eventState">

						<td class="fieldName" id="DSTATE">Event Status:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			                 <td width="1%"></td>
					
						<td>
						<html:checkbox  title = "New/Initial checkbox" name="personSearchForm" property="personSearch.newInitialSelected" styleId="newInitial"></html:checkbox> 
						<nobr>New/Initial</nobr> &nbsp;&nbsp;&nbsp;&nbsp;
						<html:checkbox title = "Update checkbox" name="personSearchForm" property="personSearch.updateSelected" styleId="update"></html:checkbox> 
						<nobr>Update</nobr>
						<!-- <input type="checkbox" name="newInitial" /> New/Initial &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="update" /> Update</td> -->
					</tr>
					

					<tr id="processCondition">
						<td class="fieldName" id="DSTATE">Processing Status:</td>
						<td style="background:#d1e5f6; margin:0.05em; padding:0.05em;"></td>
			            <td width="1%"></td>
						<td>
						<html:checkbox  title = "Processed" name="personSearchForm" property="personSearch.processedState" styleId="processState"></html:checkbox> 
						<nobr>Processed</nobr> &nbsp;&nbsp;&nbsp;&#8239;
						<html:checkbox title = "Unprocessed" name="personSearchForm" property="personSearch.unProcessedState" styleId="unProcessState" style="margin-left:1px;"></html:checkbox> 
						<nobr>Unprocessed</nobr>
					</tr>
					
					
					<!-- Event Created By User-->
			  
					<tr id="docCreatedBy">
					<td class="fieldName">
			                          

			        <span class="InputFieldLabel" id="" style="" title=""> Event Created By User:</span>&nbsp;
			       <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
			        <td>
			       			 
						
						<html:select name="personSearchForm"
								property="personSearch.documentCreateSelected"
								styleId="CreatedBy"
								title="Please select the event created by user to search" size="19">
						<html:optionsCollection property="userList" value="key" label="value" />
						</html:select>
						</td> 
						<html:hidden name="personSearchForm" property="personSearch.documentCreateFullNameSelected" styleId="documentCreateFullNameValueSelected"/>
			       </tr>
			       <!-- Event Last Updated By User-->
			  
					<tr id="docUpdatedBy">
					<td class="fieldName">
			                          
			        <span class="InputFieldLabel" id="" style="" title=""> Event Last Updated By User:</span>&nbsp;
			       <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							
						<td width="1%"></td>
			        <td>
			       			 
						<html:select name="personSearchForm"
								property="personSearch.documentUpdateSelected"
								styleId="UpdatedBy"
								title="Please select the event last updated by user to search" size="19">
						<html:optionsCollection property="userList" value="key" label="value" />
						</html:select>
						</td> 
					<td>
				    </td><html:hidden name="personSearchForm" property="personSearch.documentUpdateFullNameSelected" styleId="documentUpdateFullNameValueSelected"/>
			       </tr>

			        <!-- Event Provider/Facility Type-->
			  
			 
					<tr id="ProviderFacility">
					<td class="fieldName">
			                          
			        <span class="InputFieldLabel" id="" style="" title="">Event Provider/Facility Type:</span>&nbsp;
			       <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
							
						<td width="1%"></td>
			        <td>
			       			 
						<html:select name="personSearchForm"
								property="personSearch.providerFacilitySelected"
								styleId="EventProviderFacility"
								onchange="showHideProviderFacility();"
								title="Please select the Provider/Facility to search" size="19">
											<html:optionsCollection property="codedValue(NBS_REP_ORD_TYPE)" value="key" label="value" />
											<!-- <option value=""> &nbsp;</option>
											<option value="RF">Reporting Facility</option>
											<option value="OF">Ordering Facility</option>
											<option value="OP">Ordering Provider</option>
											<option value="RP">Reporting Provider</option> -->
						</html:select>
						</td> 
					<td>
				    </td>
			       </tr>
			      
			       <!-- REPORTING FACILITY -->
			


	       <tr id="reportingFacility">
	            <td class="fieldName"> 
	                <span>Reporting Facility:</span>   
	            </td>
	            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <span id="clearINV185" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV185CodeClearButton" onclick="clearOrganization('INV185');clearValueSelected('reportingFacilitySelected')"/>
	                </span>
	                
	                <span id="INV185SearchControls" class="">
	                   <input type="button" class="Button" value="Search" 
                                id="INV185Icon" onclick="getReportingOrg('INV185');" />
                        &nbsp; - OR - &nbsp;
                        <html:text title="Reporting Facility" property="personSearch.rfSelected" styleId="INV185Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV185Text','INV185_qec_list')"
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV185CodeLookupButton" onclick="getDWROrganization('INV185')" 
                            />                                
                        <div class="page_name_auto_complete" id="INV185_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
				<td></td>
	        </tr>
	        
	        
	        

	        <tr id="reportingFacilitySelected">
           
  <td class="fieldName" style="font-weight:normal"> Reporting Facility Selected:</td>            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV185Uid"/>
	                    <span id="INV185">${PamForm.attributeMap.INV185SearchResult}</span>
	                </span>
	            </td>
	        </tr>
			    <tr>
	        <td colspan="4" style="text-align:center;">
	            <span id="INV185Error"/></td>
	        </td>
	    </tr>
		


		<!-- ORDERING FACILITY -->
			

	       <tr id="orderingFacility">
	            <td class="fieldName"> 
	                <span>Ordering Facility:</span>   
	            </td>
	            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <span id="clearINV186" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV186CodeClearButton" onclick="clearOrganization('INV186');clearValueSelected('orderingFacilitySelected');"/>
	                </span>
	                
	                <span id="INV186SearchControls" class="">
	                   <input type="button" class="Button" value="Search" 
                                id="INV186Icon" onclick="getReportingOrg('INV186');" />
                        &nbsp; - OR - &nbsp;
                        <html:text title="Ordering Facility" property="personSearch.ofSelected" styleId="INV186Text"
                                size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV186Text','INV186_qec_list')"
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV186CodeLookupButton" onclick="getDWROrganization('INV186')" 
                            />                                
                        <div class="page_name_auto_complete" id="INV186_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
				<td></td>
	        </tr>
	        
	        
	        

	        <tr id="orderingFacilitySelected">
            <td class="fieldName" style="font-weight:normal"> Ordering Facility Selected:  </td>
            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV186Uid" />
	                    <span id="INV186">${PamForm.attributeMap.INV186SearchResult}</span>
	                </span>
	            </td>
	        </tr>
 <tr>
	        <td colspan="4" style="text-align:center;">
	            <span id="INV186Error"/></td>
	        </td>
	    </tr>
			<!-- ORDERING PROVIDER -->
			
	       

	       <tr id="orderingProvider">
	            <td class="fieldName"> 
	                <span>Ordering Provider:</span>   
	            </td>
	            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <span id="clearINV209" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV209CodeClearButton" onclick="clearProvider('INV209');clearValueSelected('orderingProviderSelected')"/>
	                </span>
	                
	                <span id="INV209SearchControls" class="">
	                   <input type="button" class="Button" value="Search" 
                                id="INV209Icon" onclick="getProvider('INV209');" />
                        &nbsp; - OR - &nbsp;
                        <html:text title="Ordering Provider" property="personSearch.opSelected" styleId="INV209Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV209Text','INV209_qec_list')"
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV209CodeLookupButton" onclick="getDWRProvider('INV209')" 
                            />                                
                        <div class="page_name_auto_complete" id="INV209_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
				<td></td>
	        </tr>
	        
			<tr id="orderingProviderSelected">
            <td class="fieldName" style="font-weight:normal"> Ordering Provider Selected:  </td>
            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV209Uid"/>
	                    <span id="INV209">${PamForm.attributeMap.INV209SearchResult}</span>
	                </span>
	            </td>
	        </tr>
			 <tr>
	        <td colspan="4" style="text-align:center;">
	            <span id="INV209Error"/></td>
	        </td>
	    </tr>
<!-- REPORTING PROVIDER -->
			
	       <tr id="reportingProvider">
	            <td class="fieldName"> 
	                <span>Reporting Provider:</span>   
	            </td>
	            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <span id="clearINV208" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV208CodeClearButton" onclick="clearProvider('INV208'); clearValueSelected('reportingProviderSelected');"/>
	                </span>
	                
	                <span id="INV208SearchControls" class="">
	                   <input type="button" class="Button" value="Search" 
                                id="INV208Icon" onclick="getProvider('INV208');" />
                        &nbsp; - OR - &nbsp;
                        <html:text title="Reporting Provider" property="personSearch.rpSelected" styleId="INV208Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV208Text','INV208_qec_list')"
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV208CodeLookupButton" onclick="getDWRProvider('INV208')" 
                            />                                
                        <div class="page_name_auto_complete" id="INV208_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
				<td></td>
	        </tr>
			
			
	        
	    
	        <tr id="reportingProviderSelected">
            <td class="fieldName" style="font-weight:normal"> Reporting Provider Selected:  </td>
            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV208Uid"/>
	                    <span id="INV208">${PamForm.attributeMap.INV208SearchResult}</span>
	                </span>
	            </td>
	        </tr>
				  

			    <tr>
	        <td colspan="4" style="text-align:center;">
	            <span id="INV208Error"/></td>
	        </td>
	    </tr>
		
<!-- Sending Facility -->
			
	       <tr id="sendingFacility">
	            <td class="fieldName"> 
	                <span>Sending Facility:</span>   
	            </td>
	            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <span id="clearINV187" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV187CodeClearButton" onclick="clearOrganization('INV187');clearValueSelected('sendingFacilitySelected')"/>
	                </span>
	                
	                <span id="INV187SearchControls" class="">
	                   <input type="button" class="Button" value="Search" 
                                id="INV187Icon" onclick="getReportingOrg('INV187');" />
                        &nbsp; - OR - &nbsp;
                        <html:text title="Sending Facility" property="personSearch.rpSelected" styleId="INV187Text"
                                size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV187Text','INV187_qec_list')"
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV187CodeLookupButton" onclick="getDWROrganization('INV187')" 
                            />                                
                        <div class="page_name_auto_complete" id="INV187_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
				<td></td>
	        </tr>
			
			
	        
	    
	        <tr id="sendingFacilitySelected">
            <td class="fieldName" style="font-weight:normal"> Sending Facility Selected:  </td>
            <td style="background: #d1e5f6; margin: 0.05em; padding: 0.05em;">
					<td width="1%"></td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV187Uid"/>
	                    <span id="INV187">${PamForm.attributeMap.INV187SearchResult}</span>
	                </span>
	            </td>
	        </tr>
			<tr>
				<td colspan="4" style="text-align: center;"><span id="INV187Error" /></td>
				</td>
			</tr>
</nedss:container>
              </nedss:container>
			  

			 

			  <nedss:container id="tab06" name="Laboratory Report Criteria" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="sect">

			      <!-- ########### SUB SECTION ###########  -->
			      <nedss:container id="patSearch6" name="" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="subSect" >
                    <tr>

					
			  <!--RESULTED TEST-->
			  
			    <tr>
						<td class="fieldName"><span
							class=" InputFieldLabel" id="resultedTestL" title="Resulted Test">
								Resulted Test:</span>
						</td>

						<td style="width:7%; white-space: nowrap">
						
									<html:select title = "Resulted Test operator" name="personSearchForm" property='personSearch.resultedTestCodeDropdown' styleId="resultOperatorList">
										<!--onkeyup="changeElrResultSelection('resultOperatorList');"
										onchange="changeElrResultSelection('resultOperatorList');changeElrSectionDarkBGColor()"
										-->
										
										<html:optionsCollection property="resultedTestList" value="key" label="value" />
									</html:select>
										<SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("resultOperatorList"),"<%=request.getAttribute("TestOP")%>");</SCRIPT>
						</td>
						<td width="10%">
									<span class="test2"> <html:hidden
									styleId="testCodeId" property="personSearch.resultedTestCode" /> <html:hidden
									styleId="testDescriptionId"	property="personSearch.resultedTestDescriptionWithCode" />
									<div id="divResultedText"><html:text property="personSearch.resultedTestDescriptionWithCode" styleId="INV211Text"
									size="40" maxlength="50" title="Enter the Resulted Test" 
									/></div>
									<span
									id="testDescription" title="Test Description">${personSearch.resultedTestDescriptionWithCode}</span>
									</span>
						</td>

						<td colspan="" align="left"><input id="testSearchButton"
							type="button" value="     Search    "
							onclick="searchLabResultedTest()"; style="margin-left:5px"/> <input id="testClearButton"
							type="button" value="     Clear    "
							onclick="clearTestedResult()" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
				</tr>
				
				
				
				<!--CODED RESULT ORGANISM-->
			  
			    <tr>
						<td class="fieldName"><span
							class=" InputFieldLabel" id="resultedTestL" title="Resulted Test">
								Coded Result/Organism:</span>
						</td>

						<td>	
						
									<html:select title = "Coded Result/Organism operator" property='personSearch.codeResultOrganismDropdown' styleId="codedResultOrganismOperatorList" >
										<!--onkeyup="changeElrResultSelection('resultOperatorList');"
										onchange="changeElrResultSelection('resultOperatorList');changeElrSectionDarkBGColor()"
										-->
										
										<html:optionsCollection property="codedValue(SEARCH_TEXT)" value="key" label="value" />
									</html:select>
																		<SCRIPT Language="JavaScript" Type="text/javascript">setSelectedIndex(getElementByIdOrByName("codedResultOrganismOperatorList"),"<%=request.getAttribute("CodedOp")%>");</SCRIPT>
						</td>
						<td>
									<span class="test2"> <html:hidden
									styleId="codeResultId" property="personSearch.codeResultOrganismCode" /> <html:hidden
									styleId="resultDescriptionId"	property="personSearch.codeResultOrganismDescription" />
									<div id="divOrganismText"><html:text property="personSearch.codeResultOrganismDescription" styleId="INV212Text"
									size="40" maxlength="50" title="Enter the Coded Result/Organism" 
									/></div>
									<span
									id="resultDescription" title="Test Description">${personSearch.ResultDescription}</span>
									</span>
						</td>

						<td colspan="" align="left"><input id="codeSearchButton"
							type="button" value="     Search    "
							onclick="searchCodeResult()"; style="margin-left:5px"/> <input id="codeClearButton"
							type="button" value="     Clear    "
							onclick="clearCodeResult()" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
				</tr>
				
                </nedss:container>
              </nedss:container>

<!-- INVESTIGATION CRITERIA SECTION -->
			                
                  <nedss:container id="tab07" name="Investigation Criteria" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="sect">

			      <!-- ########### SUB SECTION ###########  -->
			      <nedss:container id="patSearch6" name="" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" classType="subSect" >
               
                   

					
					
					<!-- INVESTIGATOR -->
			
	       
	       <tr id="investigator">
	            <td class="fieldName"> 
	                <span id="INV210L">Investigator:</span>   
	            </td>
				<td width="7%" style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>
	            <td>
	                <span id="clearINV210" class="none">        
	                    <input type="button" class="Button" value="Clear/Reassign" 
	                            id="INV210CodeClearButton" onclick="clearProvider('INV210'); clearValueSelected('investigatorSelected')"/>
	                </span>
	                
	                <span id="INV210SearchControls" class="">
	                   <input type="button" class="Button" value="Search" 
                                id="INV210Icon" onclick="getProvider('INV210');" />
                        &nbsp; - OR - &nbsp;
                        <html:text title="Investigator" property="personSearch.invSelected" styleId="INV210Text"
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV210Text','INV210_qec_list')"
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV210CodeLookupButton" onclick="getDWRProvider('INV210')" 
                            />                                
                        <div class="page_name_auto_complete" id="INV210_qec_list" style="background:#DCDCDC"></div>
	                </span>
	            </td>
				<td></td>
	        </tr>
	        
			<tr id="investigatorSelected">
            <td class="fieldName" id="INV210S" style="font-weight:normal"> Investigator Selected:  </td>
			<td style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>
	            <td>
	                <!--  <span class="none test2"> -->
	                <span class="test2">
	                    <html:hidden property="attributeMap.INV210Uid"/>
	                    <span id="INV210">${PamForm.attributeMap.INV210SearchResult}</span>
	                </span>
	            </td>
	        </tr>
			
			<tr>
				<td colspan="4" style="text-align:center;">
					<span id="INV210Error"/></td>
				</td>
			</tr>
		
			<!-- INVESTIGATION STATUS -->
					<tr>
                        <td class="fieldName" id="TYPE"> Investigation Status: </td> 
						<td style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>
                        <td>
		                    
			
			
			
		                     <html:select name="personSearchForm" property="personSearch.investigationStatusSelected" styleId="InvestigationStatus"  title="Please select the investigation status">
		                        <html:optionsCollection property="investigationStatusList" value="key" label="value"/>
		                    </html:select>	                                                    
                        </td>
                    </tr>
					
					
					<!-- OUTBREAK NAME -->
					<!-- onkeyup="changeElrResultSelection('resultOperatorList');"
												onchange="changeElrResultSelection('resultOperatorList');changeElrSectionDarkBGColor()" -->
		
				<tr id="ESR101TR">
					<td class="fieldName" id="ESR297L">Outbreak Name:</td>
					<td
						style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>
					<td>
						<div>
							<html:select name="personSearchForm"
								property='personSearch.outbreakNameSelected'
								styleId="outbreakNameList"
								title="Please select the outbreak name to search"
								onchange="displaySelectedOptions(this, 'outbreakNameList-selectedValues');"
								multiple="true" size="4">
								<html:optionsCollection property="outbreakNameList" value="key"
									label="value" />
							</html:select>
							<br />
							<div id="outbreakNameList-selectedValues" style="margin: 0.25em;">
								<b> Selected Values: </b>
							</div>
						</div>
		
					</td>
				</tr>
		
		
				<!-- CASE STATUS -->
					
					<tr id="caseStatusListValuesSelected">
						<td class="fieldName" id="ConditionLabel"><span>Case Status:</span></td>
						<td style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>
						<td id="ConditionField">
							<div class="multiSelectBlock">
								<input title="Case Status Include unassigned Status" type="checkbox" name="caseStatusUnassigned" id="caseStatusUnassigned"/> Include Unassigned Status <br />
								<html:select title="Case Status Select unassigned status" name="personSearchForm"
									property='personSearch.caseStatusListSelected'
									styleId="caseStatusList"
									onchange="displaySelectedOptions(this, 'caseStatusList-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="codedValue(PHC_CLASS)" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="caseStatusList-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
			  
					 <!-- NOTIFICATION STATUS -->
										<tr id="notificationValuesSelected">
						<td class="fieldName" id="ConditionLabel"><span>Notification Status:</span></td>
						<td style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>
						<td id="ConditionField">
							<div class="multiSelectBlock">
								<input title="Notification Status Include unassigned status" type="checkbox" name="notificationStatusUnassigned" id="notificationStatusUnassigned"/> Include Unassigned Status <br />
								<html:select  title="Notification Status Select unassigned status" name="personSearchForm"
									property='personSearch.notificationStatusSelected'
									styleId="notificationStatusList"
									onchange="displaySelectedOptions(this, 'notificationStatusList-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="codedValue(REC_STAT_NOT_UI)" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="notificationStatusList-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
					

					<html:hidden name="personSearchForm" property="personSearch.notificationValuesSelected" styleId="notificationValues"/>
					<html:hidden name="personSearchForm" property="personSearch.caseStatusListValuesSelected" styleId="caseStatusValues"/>
					<html:hidden name="personSearchForm" property="personSearch.currentProcessStateValuesSelected" styleId="currentProcessingStatusValues"/>
					<html:hidden name="personSearchForm" property="personSearch.investigatorSelected" styleId="investigatorValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.reportingFacilitySelected" styleId="reportingFacilityValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.reportingProviderSelected" styleId="reportingProviderValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.providerFacilitySelected" styleId="providerFacilityValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.reportingFacilityDescSelected" styleId="reportingFacilityDescValueSelected"/>	
					
					<html:hidden name="personSearchForm" property="personSearch.investigatorDescSelected" styleId="investigatiorDescValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.reportingProviderDescSelected" styleId="reportingProviderDescValueSelected"/>	
					<html:hidden name="personSearchForm" property="personSearch.orderingFacilitySelected" styleId="orderingFacilityValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.orderingFacilityDescSelected" styleId="orderingFacilityDescValueSelected"/>	
					<html:hidden name="personSearchForm" property="personSearch.orderingProviderSelected" styleId="orderingProviderValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.orderingProviderDescSelected" styleId="orderingProviderDescValueSelected"/>
										
 					<html:hidden name="personSearchForm" property="personSearch.eventStatusInitialSelected" styleId="eventStatusInitialValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.eventStatusUpdateSelected" styleId="eventStatusUpdateValueSelected"/>
					<html:hidden name="personSearchForm" property="personSearch.notificationCodedValuesSelected" styleId="notificationCodedValues"/>
					<html:hidden name="personSearchForm" property="personSearch.caseStatusCodedValuesSelected" styleId="caseStatusCodedValues"/>
					<html:hidden name="personSearchForm" property="personSearch.currentProcessCodedValuesSelected" styleId="currentProcessCodedValues"/>
					<html:hidden name="personSearchForm" property="personSearch.currentProcessingStatusValuesSelected" styleId="currentProcessingStatusValues"/>
			  
			        <html:hidden name="personSearchForm" property="personSearch.electronicValueSelected" styleId="electronicValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.manualValueSelected" styleId="manualValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.internalValueSelected" styleId="internalValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.externalValueSelected" styleId="externalValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.testDescription" styleId="testDescriptionValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.resultedTestDescriptionWithCodeValue" styleId="descriptionWithCodeValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.resultDescriptionValue" styleId="resultDescriptionValueSelected"/>
			        <html:hidden name="personSearchForm" property="personSearch.codeResultOrganismDescriptionValue" styleId="codeResultOrganismValueSelected"/>
			        
			        
			        
			        
			        
			        
			  
                        <!-- CURRENT PROCESS STATE -->
				<tr id="currentProcessStateValuesSelected">
						<td class="fieldName" id="ConditionLabel"><span>Current Processing Status:</span></td>
						<td style="background: rgb(206, 227, 246); margin: 0.05em; padding: 0.05em;"></td>	
						<td id="ConditionField">
							<div class="multiSelectBlock">
								<input title="Current Processing Status Include Unassigned Status" type="checkbox" name="currentProcessStateUnassigned" id="currentProcessStateUnassigned"/> Include Unassigned Status <br />
								<html:select title="Current Processing Status Select Unassigned Status" name="personSearchForm"
									property='personSearch.currentProcessStateSelected'
									styleId="currentProcessList"
									onchange="displaySelectedOptions(this, 'currentProcessStateList-selectedValues');"
									multiple="true" size="4">
									<html:optionsCollection property="codedValue(CM_PROCESS_STAGE)" value="key"
										label="value" />
								</html:select>
								<br />
								<div id="currentProcessStateList-selectedValues" style="margin: 0.25em;">
									<b> Selected Values: </b>
								</div>
							</div>
						</td>
					</tr>
					
			  
                                              
                    
                </nedss:container>
              </nedss:container>

<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>