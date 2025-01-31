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
    String []sectionNames = {"Risk Assessment","Contact Records","Custom Fields"};
    int sectionIndex = 0;
%>

<div class="view"  id="<%= tabId %>" style="text-align:center;">    
    <table role="presentation" class="sectionsToggler" style="width:100%;">
        <tr>
            <td>
                <ul class="horizontalList">
                    <li style="margin-right:5px;"><b>Go to: </b></li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <% if(request.getAttribute("1423") != null) { %>
                        <li class="delimiter"> | </li>
                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>    
                    <% } else if(request.getAttribute("2423") != null ){%>
                    <li class="delimiter"> | </li>
                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>    
                    <%} %>                 
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
            
    <!-- SECTION : Risk Assessment --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Risk Assessment" classType="subSect" >
          <!-- Contact Investigation Priority -->
              <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.INV257.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV257.errorStyleClass}" 
                                title="${PamForm.formFieldMap.INV257.tooltip}">
                            ${PamForm.formFieldMap.INV257.label}
                        </span> 
                    </td>
                    <td>
                        <html:select title="${PamForm.formFieldMap.INV257.label}" disabled ="${PamForm.formFieldMap.INV257.state.disabled}" name="PamForm" property="pamClientVO.answer(INV257)" styleId = "INV257">
                           <html:optionsCollection property="codedValue(INV257)" value="key" label="value"/>
                        </html:select>
                     </td>
                </tr>
           <!-- Infectious Period From -->
            <tr>
                <td  class="fieldName" >
                    <span style="${PamForm.formFieldMap.INV258.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV258.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV258.tooltip}">
                        ${PamForm.formFieldMap.INV258.label}
                    </span> 
                </td>
                <td class="topBorder">
                    <html:text title="${PamForm.formFieldMap.INV258.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV258.state.disabled}" 
                            property="pamClientVO.answer(INV258)" maxlength="10" 
                            styleId="INV258" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV258','INV258Icon'); return false;" onkeypress ="showCalendarEnterKey('INV258','INV258Icon',event);" 
                            styleId="INV258Icon"></html:img>
                </td>
            </tr>
            <!-- Infectious Period To -->
            <tr>
                <td  class="fieldName">
                    <span style="${PamForm.formFieldMap.INV259.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV259.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV259.tooltip}">
                        ${PamForm.formFieldMap.INV259.label}
                    </span> 
                </td>
                <td class="topBorder">
                    <html:text title="${PamForm.formFieldMap.INV259.label}" disabled ="${PamForm.formFieldMap.INV259.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(INV259)" maxlength="10" 
                            styleId="INV259" onkeyup="DateMaskFuture(this)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV259','INV259Icon'); return false;" onkeypress ="showCalendarEnterKey('INV259','INV259Icon',event);" 
                            styleId="INV259Icon"></html:img>
                </td>
            </tr>   
             <!-- Risk Assessment LDFs -->
             <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {%>
	         <%= request.getAttribute("1405") == null ? "" :  request.getAttribute("1405") %>
	        <% }else if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)){ %>
	          <%= request.getAttribute("2405") == null ? "" :  request.getAttribute("2405") %>
	        <% }%>
          </nedss:container>
           <!-- Administrative Information -->
           <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Administrative Information" classType="subSect" >
          <!-- Contact Investigation Priority -->
               <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.INV260.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV260.errorStyleClass}" 
                                title="${PamForm.formFieldMap.INV260.tooltip}">
                            ${PamForm.formFieldMap.INV260.label}
                        </span> 
                    </td>
                    <td>
                        <html:select title="${PamForm.formFieldMap.INV260.label}" disabled ="${PamForm.formFieldMap.INV260.state.disabled}" name="PamForm" property="pamClientVO.answer(INV260)" styleId = "INV260">
                           <html:optionsCollection property="codedValue(INV260)" value="key" label="value"/>
                        </html:select>
                     </td>
                </tr>    
             <!-- Contact Investigation Comments -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.INV261.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV261.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV261.tooltip}">${PamForm.formFieldMap.INV261.label}</span> 
                </td>
                <td> <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" property="pamClientVO.answer(INV261)" 
                        title="${PamForm.formFieldMap.INV261.label}" onkeydown="checkMaxLength(this)" 
                        onkeyup="checkMaxLength(this)"></html:textarea> </td>
            </tr>
            <!-- Administrative Information LDFs -->
	         <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {%>
	         <%= request.getAttribute("1409") == null ? "" :  request.getAttribute("1409") %>
	        <% }else if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)){ %>
	          <%= request.getAttribute("2409") == null ? "" :  request.getAttribute("2409") %>
	        <% }%>
          </nedss:container>
        </nedss:container>  
        
    <!-- SECTION : Contact Records -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
    <!-- SECTION : Contacts Named By Patient -->   
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contacts Named By Patient" classType="subSect" >
               <tr style="background:#FFF;"> 
                  <td colspan="2">The following contacts were named within ${PamForm.attributeMap.patientLocalName}'s investigation: </td>
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
                  <td colspan="2">The following contacts  named  ${PamForm.attributeMap.patientLocalName} within their investigation and have been associated
                                  to ${PamForm.attributeMap.patientLocalName}'s investigation: </td>
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
    
    <!-- SECTION : Local Fields -->
   <% if(request.getAttribute("1423") != null ) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		<%= request.getAttribute("1423") == null ? "" :  request.getAttribute("1423") %>
        </nedss:container>
   </nedss:container>
    <%} else if(request.getAttribute("2423") != null){%>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		<%= request.getAttribute("2423") == null ? "" :  request.getAttribute("2423") %>
        </nedss:container>
   </nedss:container>
   <%} %>
    
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