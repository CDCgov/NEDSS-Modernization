<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import = "gov.cdc.nedss.webapp.nbs.form.pam.PamForm, gov.cdc.nedss.webapp.nbs.form.pam.FormField"%>
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
    String tabId = "editCaseVerification";
    String []sectionNames = {"Case Verification", "Custom Fields"};
    int sectionIndex = 0;  
%>

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
            
    <!-- SECTION : Case Verification --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Case Counting: -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Case Verification"  classType="subSect" >
            <!-- Case Verification -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB266.state.requiredIndClass}">*</span>
                    <span id="TUB266L" style="${PamForm.formFieldMap.TUB266.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB266.tooltip}">
                        ${PamForm.formFieldMap.TUB266.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB266.label}" property="pamClientVO.answer(TUB266)" styleId="TUB266" disabled ="${PamForm.formFieldMap.TUB266.state.disabled}" onchange="populateAsUserEntered();populateProviderDiagnosisDetails('prvdetails_279');fireRule('TUB266', this);">
			                  <html:optionsCollection property="formFieldMap.TUB266.state.values" value="key" label="value"/>
			                </html:select>
                   
                </td>
            </tr>
             <!-- Provider Diagnosis Details -->   
			
			 <tr id="prvdetails_279">
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB279.state.requiredIndClass}">*</span>
                    <span  id="TUB279L" style="${PamForm.formFieldMap.TUB279.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB279.tooltip}">${PamForm.formFieldMap.TUB279.label}                    
                </td>
                <td>
                    <html:textarea title="${PamForm.formFieldMap.TUB279.label}" style="WIDTH: 500px; HEIGHT: 100px;" styleId="TUB279" disabled ="${PamForm.formFieldMap.TUB279.state.disabled}"  
                            name="PamForm" property="pamClientVO.answer(TUB279)"/>
                </td>
            </tr>
		
            <!-- Case Status -->    
            <tr>
                <td  class="fieldName"> 
                    <span style="${PamForm.formFieldMap.INV163.state.requiredIndClass}">*</span>
                    <span  id="INV163L" style="${PamForm.formFieldMap.INV163.errorStyleClass}"  
                        title="${PamForm.formFieldMap.INV163.tooltip}">
                        ${PamForm.formFieldMap.INV163.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.INV163.label}" property="pamClientVO.answer(INV163)" styleId="INV163"  disabled ="${PamForm.formFieldMap.INV163.state.disabled}">
			                  <html:optionsCollection property="codedValue(INV163)" value="key" label="value"/>
			                </html:select>
                </td>
            </tr>
            <!--Count Status-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB108.state.requiredIndClass}">*</span>
                    <span  id="TUB108L" style="${PamForm.formFieldMap.TUB108.errorStyleClass}" title="${PamForm.formFieldMap.TUB108.tooltip}">${PamForm.formFieldMap.TUB108.label}</span>
                </td>
                <td>
                   <html:select title="${PamForm.formFieldMap.TUB108.label}" property="pamClientVO.answer(TUB108)" onchange="enableFieldLabels();;fireRule('TUB108', this)" styleId="TUB108">
				                  <html:optionsCollection property="formFieldMap.TUB108.state.values" value="key" label="value"/>
				                </html:select>
                </td>
            </tr>
            <!-- Country of Case Verified -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB109.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB109.state.disabledString}"  id="TUB109L"  style="${PamForm.formFieldMap.TUB109.errorStyleClass}" title="${PamForm.formFieldMap.TUB109.tooltip}">${PamForm.formFieldMap.TUB109.label}</span>
                </td>
                <td>
                   <html:select title="${PamForm.formFieldMap.TUB109.label}" property="pamClientVO.answer(TUB109)" styleId="TUB109" onchange="fireRule('TUB109', this)" disabled ="${PamForm.formFieldMap.TUB109.state.disabled}" >
				                  <html:optionsCollection property="codedValue(TUB109)" value="key" label="value"/>
				                </html:select>
                </td>
            </tr>
            <!--Date Counted -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB110.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB110.state.disabledString}"  id="TUB110L"  style="${PamForm.formFieldMap.TUB110.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB110.tooltip}">
                        ${PamForm.formFieldMap.TUB110.label}
                    </span> 
                </td>
                <td>
                    <html:text title="${PamForm.formFieldMap.TUB110.label}" maxlength="10" property="pamClientVO.answer(TUB110)" styleId="TUB110" 
                            size="10"
                            disabled ="${PamForm.formFieldMap.TUB110.state.disabled}"
                            onkeydown="fireRuleAndChangeFocusOnTabKey('TUB110', this)"
                            onkeyup="DateMask(this,null,event)"
                            onblur="fireRule('TUB110', this)"
                            onchange="fireRule('TUB110', this)"
                            />
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB110','fieldMMWRDateIcon'); return false;" onkeypress ="showCalendarEnterKey('TUB110','fieldMMWRDateIcon',event);" 
                            styleId="fieldMMWRDateIcon"/>   
                </td>
            </tr>
            
            <!--MMWR Week -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV165.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.INV165.state.disabledString}"  id="INV165L"  style="${PamForm.formFieldMap.INV165.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV165.tooltip}">
                        ${PamForm.formFieldMap.INV165.label}
                    </span> 
                </td>
                <td>
                   <html:text  maxlength="2"  size="3" property="pamClientVO.answer(INV165)" styleId="INV165"  disabled ="${PamForm.formFieldMap.INV165.state.disabled}" title="${PamForm.formFieldMap.INV165.label}" onkeyup="isNumericCharacterEntered(this)"> </html:text>
                </td>
            </tr>
			 <!--MMWR Year -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV166.state.requiredIndClass}">*</span>
                    <span   class="${PamForm.formFieldMap.INV166.state.disabledString}" id="INV166L" style="${PamForm.formFieldMap.INV166.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV166.tooltip}">
                        ${PamForm.formFieldMap.INV166.label}
                    </span> 
                </td>
                <td>
                   <html:text  maxlength="4"  size="4" property="pamClientVO.answer(INV166)" styleId="INV166" disabled ="${PamForm.formFieldMap.INV166.state.disabled}"  title="${PamForm.formFieldMap.INV166.label}" onkeyup="isNumericCharacterEntered(this);YearMask(this)"></html:text>
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
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
</div> <!-- view -->

</td> </tr>