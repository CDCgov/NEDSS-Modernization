<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>

<tr> <td>
	<% 
	    int subSectionIndex = 0;
	    String tabId = "supplementalInfo";
	    String []sectionNames = {"Supplemental Information"};
	    int sectionIndex = 0;
	%>
	
	<style type="text/css">
	   div#addAttachmentBlock {margin-top:10px; display:none; width:100%; text-align:center;}
	</style>
	<div class="view"  id="<%= tabId %>" style="text-align:center;">
	    <table role="presentation" class="sectionsToggler" style="width:100%;">
	        <tr>
	            <td>
	                <ul class="horizontalList">
	                    <li style="margin-right:5px;"><b>Go to: </b></li>
	                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
	                </ul>
	            </td>
	        </tr>
	        <tr>
	            <td style="padding-top:1em;">
	                <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
	            </td>
	        </tr>
	    </table>
	
	    <%  // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>
	    
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect" includeBackToTopLink="no">
	        <!-- SUB_SECTION : Attachments -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Attachments" classType="subSect" >
	            <tr>
	                <td style="padding:0.5em;">
                        <display:table name="contactAttachments" class="dtTable" defaultsort="3" defaultorder="ascending">
                            <display:column class="dateField" property="lastChgTime" title="Date Added" format="{0,date,MM/dd/yyyy}" />
                            <display:column class="nameField" property="lastChgUserNm" title="Added By" />
                            <display:column property="fileNmTxt" title="File Name" />
                            <display:column property="descTxt" title="Description" />
							<display:setProperty name="basic.empty.showtable" value="true"/>
                        </display:table>
	                </td>
	            </tr>
	        </nedss:container>
	        
	        <!-- SUB_SECTION : Notes -->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notes" classType="subSect" >
                <tr>
                    <td style="padding:0.5em;">
                        <display:table id="notesDTTable" name="contactNotes" class="dtTable" defaultsort="4" defaultorder="ascending">
                             <display:column class="dateField" property="lastChgTime" title="Date Added" format="{0,date,MM/dd/yyyy}" />
                             <display:column class="nameField" property="lastChgUserNm" title="Added By" />
                             <display:column property="note" title="Note" />
                             <display:column class="iconField" property="privateIndCd" title="Private"/>
                             <display:setProperty name="basic.empty.showtable" value="true"/>
                         </display:table>
                    </td>
                </tr>
            </nedss:container>
	        
	        <!-- SUB_SECTION : Revision History -->
	        <!-- 
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Revision History" classType="subSect" >
	        </nedss:container>
	        -->
	        
	        <!-- SUB_SECTION : Investigation Summary -->
            <!-- 
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation Summary" classType="subSect">
	        </nedss:container>
	         -->
	              <!-- SUB_SECTION : Investigation Summary  -->
	        <logic:equal name="viewInves" value="true">
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation Summary" classType="subSect" >
	       
	        <tr><td>		
				<display:table name="strContactInvestigationList" class="bluebardtTable"  id="eventSumaryInv">
				   <display:column property="rscSecRef" style="width:6%;text-align:left;" title="RSC/SecRef"/>
				   <display:column property="status" style="width:6%;text-align:left;" title="Status"/>
				   <display:column property="conditions" style="width:24%;text-align:left;" title="Condition"/>                                                  
				   <display:column property="caseStatus" style="width:10%;text-align:left;" title="Case Status"/>
				   <display:column property="disposition" style="width:12%;text-align:left;" title="Disposition"/>
				   <display:column property="jurisdiction" style="width:14%;text-align:left;" title="Jurisdiction"/>
				   <display:column property="investigator" style="width:14%;text-align:left;" title="Investigator"/>
				   <display:column property="investigationId" style="width:12%;text-align:left;" title="Investigation ID "/>
				</display:table> 
			</td></tr>
			
	        </nedss:container> 
	        </logic:equal>
	         
	    </nedss:container>
	    <div class="tabNavLinks">
            <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
            <a href="javascript:navigateTab('next')"> Next </a>
            <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
            <input type="hidden" name="endOfTab" />
    	</div>
	    
	</div>
</td> </tr>