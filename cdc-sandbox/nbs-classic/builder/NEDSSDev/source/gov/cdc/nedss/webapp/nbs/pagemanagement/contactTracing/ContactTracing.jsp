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
    This file includes the contents of a single tab (the Tuberculosis tab). This tab 
    is part of the tab container who structure is defined in a separate JSP.
-->


<tr> <td>

<% 
    int subSectionIndex = 0;
    String tabId = "editContactTracing";
    String []sectionNames = {"Interviews","Contact Records"};
    int sectionIndex = 0;
%>

<div class="view"  id="<%= tabId %>" style="text-align:center;">    
            
    <!-- SECTION : Interviews -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
     
    <!-- SECTION : Interview -->   
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Interview" classType="subSect" >
               <tr style="background:#FFF;"> 
                   <td colspan="2">The following interviews are associated with ${PageForm.attributeMap.patientLocalName}'s investigation: </td>
               </tr>
               <tr style="background:#FFF;">
                    <td colspan="2">
                         <display:table name="interviewList" class="dtTable" id="interviewListID">
					     <display:column property="dateActionLink" title="Date of Interview" format="{0,date,MM/dd/yyyy}" />
					     <display:column property="interviewerFullName" title="Interviewer" />
					     <display:column property="intervieweeFullName" title="Interviewee" />
					     <display:column property="intervieweeRoleDesc" title="Role"/>
					     <display:column property="interviewTypeDesc" title="Type"/>
					     <display:column property="interviewLocDesc" title="Location"/>
					     <display:column property="interviewStatusDesc" title="Interview Status"/>
	         		             <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
                   </td>
               </tr>         
        </nedss:container>
    </nedss:container>   
    <!-- SECTION : Contact Records -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
    <!-- SECTION : Contacts Named By Patient -->   
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contacts Named By Patient" classType="subSect" >
               <tr style="background:#FFF;"> 
                  <td colspan="2">The following contacts were named within ${PageForm.attributeMap.patientLocalName}'s investigation: </td>
               </tr>
               <tr style="background:#FFF;">
                    <td colspan="2">
                        <display:table name="contactNamedByPatList" class="dtTable" id="contactNamedByPatListID">
					            <display:column property="namedOnDate" title="Date Named"  format="{0,date,MM/dd/yyyy}"/>
					            <display:column property="localId" title="Contact Record ID" />
					            <display:column property="name" title="Name"/>
					            <display:column property="priority" title="Priority"/>
					             <display:column property="disposition" title="Disposition"/>
					             <display:column property="contactPhcLocalId" title="Investigation ID"/>
	         		            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
                   </td>
               </tr>   
                <!--Temporary for contact tracing -->
	           <!--  <Tr>
		            <td>
		             Contact Tracing
		            </td>
		            <td>
		                 <input type="button" name="Cancel" value="Contact Tracing" onclick="javascript:AddContactPopUp();" />
		                      
	                </td>
                </Tr>   -->    
        </nedss:container>
        <!-- Patient Named By Contacts -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Patient Named By Contacts" classType="subSect" >
            <tr style="background:#FFF;"> 
                  <td colspan="2">The following contacts  named  ${PageForm.attributeMap.patientLocalName} within their investigation and have been associated
                                  to ${PageForm.attributeMap.patientLocalName}'s investigation: </td>
            </tr>
            <tr style="background:#FFF;">
                    <td colspan="2">
                         <display:table name="patNamedByContactsList" class="dtTable" id="patNamedByContactsListID">
					             <display:column property="namedOnDate" title="Date Named"  format="{0,date,MM/dd/yyyy}"/>
					            <display:column property="localId" title="Contact Record ID" />
					            <display:column property="namedBy" title="Named By"/>
					            <display:column property="priority" title="Priority"/>
					             <display:column property="disposition" title="Disposition"/>
					             <display:column property="subjectPhcLocalId" title="Investigation ID"/>
	         		            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
                   </td>
               </tr>           
        </nedss:container>      
	       
    </nedss:container>   
    
    
    <div class="tabNavLinks">       
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
    
    <div class="tabNavLinks">
        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
        <a href="javascript:navigateTab('next')"> Next </a>
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
</div> <!-- view -->

</td> </tr>