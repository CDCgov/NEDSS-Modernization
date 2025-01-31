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

 <!--
    Page Summary:
    -------------
    This file is a mockup for preview only in the page builder.
-->

<tr> <td>
	<% 
		String []sectionNames = {"Associations", "Notes and Attachments", "History","Custom Fields"};
		int sectionIndex = 0;
		int subSectionIndex = 0; 
		String tabId = "viewSupplementalInformation";
	%>
	
	<h2 class="printOnlyTabHeader">
	    Supplemental Information
	</h2>
	    
	<div class="view" id="<%= tabId %>" style="text-align:center;">   
	 
	    <table role="presentation" class="sectionsToggler" style="width:100%;">
	        <tr>
	            <td>
	                <ul class="horizontalList">
	                    <li style="margin-right:5px;"><b>Go to: </b></li>
	                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
	                    <li class="delimiter"> | </li>
	                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
	                    <% if(request.getAttribute("1329") != null) { %>
	                        <li class="delimiter"> | </li>
	                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>    
	                    <% } %>
	                </ul>
	            </td>
	        </tr>
	        <tr>
	            <td style="padding-top:1em;">
	                <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
	            </td>
	        </tr>
	    </table>
	    
	    <%
	        // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>

	    <!-- SECTION : Associations (Associated Lab Reports, Associated Morbidity Reports, 
	           Associated Treatments, and Associated Vaccination) --> 
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' 
	            name="<%= sectionNames[sectionIndex++] %>" classType="sect">
	            
	        <!-- SUB_SECTION : Associated Lab Reports -->    

		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Lab Reports"  classType="subSect" >
	                <tr style="background:#FFF;">
	                    <td colspan="2">
		                    <display:table name="observationSummaryLabList" class="dtTable" >
		                        <display:column property="actionLink" title="Ordered Test"/>
					            <display:column property="resultedTestString" title="Resulted Test(s)"/>
					            <display:setProperty name="basic.empty.showtable" value="true"/>
		                    </display:table>
		               </td>
		           </tr>
		        </nedss:container>

	        
	        <!-- SUB_SECTION : Associated Morbidity Reports -->

		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Morbidity Reports"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="observationSummaryMorbList" class="dtTable" >
					            <display:column property="actionLink" title="Date Received" />
					            <display:column property="conditionDescTxt" title="Condition" />
					            <display:column property="reportDate" title="Report Date" format="{0,date,MM/dd/yyyy}" />
					            <display:column property="reportTypeDescTxt" title="Type"/>
					            <display:column property="localId" title="Observation ID" />
					            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>

	        
	        <!-- SUB_SECTION : Treatments -->

		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Treatments"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="treatmentList" class="dtTable" >
					            <display:column property="actionLink" title="Date" />
					            <display:column property="customTreatmentNameCode" title="Treatment" />
					            <display:column property="localId" title="Treatment ID"/>
					            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>

	        
	        <!-- SUB_SECTION : Vaccination -->

		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Vaccinations"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="vaccinationList" class="dtTable" >
					            <display:column property="actionLink" title="Date Administered" />
					            <display:column property="vaccineAdministered" title="Vaccine Administered" />
					            <display:column property="localId" title="Vaccination ID"/>
					            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>

	        
	        <!-- SUB_SECTION : Document -->
	
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Documents"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="documentSummaryList" class="dtTable" >
					            <display:column property="actionLink" title="Date Received" />
					            <display:column property="docType" title="Type" />
					            <display:column property="docPurposeCd" title="Purpose"/>
					            <display:column property="cdDescTxt" title="Description"/>
					             <display:column property="localId" title="Document ID"/>
	         		            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>

	        
	    </nedss:container>
	    
	    
	    
	<!-- SECTION : Notes and Attachments --> 
	<nedss:container id='<%= "sect_" + tabId + sectionIndex %>' 
	            name="<%= sectionNames[sectionIndex++] %>" classType="sect">
	            
	   <!-- SUB_SECTION : Notes -->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notes" classType="subSect" >
                <tr>
                    <td style="padding:0.5em;">
                        <display:table id="notesDTTable" name="nbsNotes" class="dtTable" >
                             <display:column class="dateField" property="lastChgTime" title="Date Added" style="width:14%;"/>
                             <display:column class="nameField" property="lastChgUserNm" title="Added By" style="width:14%;"/>
                             <display:column property="note" title="Note"  style="width:60%;"/>
                             <display:column class="iconField" property="privateIndCd" title="Private" style="text-align:center;"/>
                             <display:setProperty name="basic.empty.showtable" value="true"/>
                         </display:table>
                    </td>
                </tr>         
	 	</nedss:container>
	 	<!-- SUB_SECTION : Attachments -->
	 	<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Attachments" classType="subSect" >
	 	       <tr>
	 	        <td style="padding:0.5em;">
	 	               <display:table id="attachmentsDTTable" name="nbsAttachments" class="dtTable" >
	 	               <display:column class="dateField" property="lastChgTime" title="Date Added" />
	 	               <display:column class="nameField" property="lastChgUserNm" title="Added By" />
	 	               <display:column property="fileNmTxt" title="File Name" />
	 	               <display:column property="descTxt" title="Description" />
	 	               <display:setProperty name="basic.empty.showtable" value="true"/>
	                </display:table>
	         </nedss:container>  
    	</nedss:container>	 
	        
        <!-- SECTION : History (Investigation History, Notifications History) --> 
        <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' 
                name="<%= sectionNames[sectionIndex++] %>" classType="sect">	        
	        <!-- SUB_SECTION : Investigation History -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation History"  classType="subSect" >
	           <tr style="background:#FFF;">
                    <td colspan="2">
                        <span id="investigationHistorySection">
                         No history found to display.
                        </span>
                   </td>
               </tr>
	        </nedss:container>
	        
            <!-- SUB_SECTION : Notification History-->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notification History"  
                    classType="subSect">
               <tr style="background:#FFF;">
                    <td colspan="2">
                        <span id="notificationSection">
                            No Notification History found.
                        </span>
                   </td>
               </tr>
            </nedss:container>
        </nedss:container>
	    
	    <div class="tabNavLinks">
	        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
	        <a href="javascript:navigateTab('next')"> Next </a>
	    </div>
	</div>
</td> </tr>