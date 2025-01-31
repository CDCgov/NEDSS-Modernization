<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import = "gov.cdc.nedss.webapp.nbs.form.pam.PamForm,gov.cdc.nedss.webapp.nbs.form.pam.FormField"%>
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
    String tabId = "viewCaseVerification"; 
    int subSectionIndex = 0;
    String []sectionNames = {"Case Verification",  "Custom Fields"};
    int sectionIndex = 0; 
%>

<h2 class="printOnlyTabHeader">
    Case Verification
</h2>

<div class="view" id="<%= tabId %>" style="text-align:center;">    
     <% if(request.getAttribute("1335") != null) { %>
        <table role="presentation" class="sectionsToggler" style="width:100%;">
            <tr>
                <td>
                    <ul class="horizontalList">
                        <li style="margin-right:5px;"><b>Go to: </b></li>
                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                        <li class="delimiter"> | </li>
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
    <% } %>
    
    <%
        // reset the sectionIndex to 0 before utilizing the sectionNames array.
        sectionIndex = 0;
    %>
            
    <!-- SECTION : Investigation Information --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Investigation Details -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Case Verification"  classType="subSect" >
            <!-- Case Verification -->
           
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.TUB266.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB266.errorStyleClass}" 
                                title="${PamForm.formFieldMap.TUB266.tooltip}">
                            ${PamForm.formFieldMap.TUB266.label}
                        </span> 
                    </td>
                    <td>
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB266)" codeSetNm="${PamForm.formFieldMap.TUB266.codeSetNm}"/>
                       
                    </td>
                </tr>
				 <!-- Provider Diagnosis Details -->    
				  <%if(((PamForm) session.getAttribute("PamForm")).getFormFieldMap() != null){ 
				  Map map_266 = ((PamForm) session.getAttribute("PamForm")).getFormFieldMap(); 
				  if(map_266.get("TUB266") != null) {
	           FormField fField = (FormField)map_266.get("TUB266");
				 if(fField.getValue()!= null && ((String)fField.getValue()).equals("PHC165")){
		     	%>

				   <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB279.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB279.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB279.tooltip}">${PamForm.formFieldMap.TUB279.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB279)"/> 
                </td>
            </tr>
			<%}}}%>
        <!-- Case Status -->    
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.INV163.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV163.errorStyleClass}"  
                            title="${PamForm.formFieldMap.INV163.tooltip}">
                            ${PamForm.formFieldMap.INV163.label}
                        </span> 
                    </td>
                    <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(INV163)" codeSetNm="${PamForm.formFieldMap.INV163.codeSetNm}"/>
                    </td>
                </tr>
            
            <!--Count Status-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB108.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB108.errorStyleClass}" title="${PamForm.formFieldMap.TUB108.tooltip}">${PamForm.formFieldMap.TUB108.label}</span>
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB108)" codeSetNm="${PamForm.formFieldMap.TUB108.codeSetNm}"/>
                </td>
            </tr>
            <!-- Country of Case Verified -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB109.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB109.errorStyleClass}" title="${PamForm.formFieldMap.TUB109.tooltip}">${PamForm.formFieldMap.TUB109.label}</span>
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB109)" codeSetNm="${PamForm.formFieldMap.TUB109.codeSetNm}"/>
                </td>
            </tr>
            <!--Date Counted -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB110.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB110.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB110.tooltip}">
                        ${PamForm.formFieldMap.TUB110.label}
                    </span> 
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB110)"/>
                </td>
            </tr>
            <!--MMWR Week -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV165.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV165.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV165.tooltip}">
                        ${PamForm.formFieldMap.INV165.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV165)"/>
                </td>
            </tr>
			 <!--MMWR Year -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV166.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV166.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV166.tooltip}">
                        ${PamForm.formFieldMap.INV166.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV166)"/>
                </td>
            </tr>
            <!-- Case Verification LDFs -->
            <%= request.getAttribute("1021") == null ? "" :  request.getAttribute("1021") %>
	       </nedss:container> 
        </nedss:container>
        
        <!-- SECTION : Local Fields -->
	    <% if(request.getAttribute("1335") != null) { %>
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
	        <!-- SUB_SECTION :  Local Fields -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
	                <%= request.getAttribute("1335") == null ? "" :  request.getAttribute("1335") %>
	        </nedss:container>
	    </nedss:container>
	    <%} %>

    <div class="tabNavLinks">
        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
        <a href="javascript:navigateTab('next')"> Next </a>
    </div>
        
</div> <!-- view -->

</td> </tr>