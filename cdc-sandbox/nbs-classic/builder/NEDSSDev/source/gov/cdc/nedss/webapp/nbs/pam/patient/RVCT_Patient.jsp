<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO"%>
<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page isELIgnored ="false" %>


<tr> <td>
 <!--
    Page Summary:
    -------------
    This file includes the contents of a single tab (Patient). This tab is part of the tab 
    container whose structure is defined in a separate JSP. The file is made up of a sequence of 
    tables (one table for each logical section).
-->

<% 
    int subSectionIndex = 0;
    String tabId = "editPatient";
    String []sectionNames = {"Patient Information", "Custom Fields"};
    int sectionIndex = 0;
%>

<div class="view" id="<%= tabId %>" style="text-align:center;">    
    
    <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
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

    <!-- SECTION : Patient Information --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : General Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="General Information" classType="subSect" >
            <!-- As of date - Create mode -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.DEM215.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM215.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM215.tooltip}">
                        ${PamForm.formFieldMap.DEM215.label}
                    </span> 
                </td>
                <td>
                    <html:text name="PamForm" property="pamClientVO.answer(DEM215)" maxlength="10" title="Enter a Date" 
                            styleId="DEM215" onkeyup="DateMask(this,null,event)" size="10"
                            onchange="calculateReportedAge()"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM215','DEM215Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM215','DEM215Icon',event);" 
                            styleId="DEM215Icon"></html:img>
                </td>
            </tr>
            <!-- Comments -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM196.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM196.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM196.tooltip}">${PamForm.formFieldMap.DEM196.label}</span> 
                </td>
                <td> <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" property="pamClientVO.answer(DEM196)" 
                        title="${PamForm.formFieldMap.DEM196.tooltip}" onkeydown="checkMaxLength(this)" 
                        onkeyup="checkMaxLength(this)"></html:textarea> </td>
            </tr>
        </nedss:container>
        
        <!-- SUB_SECTION : Name Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Name Information" classType="subSect">
            <!-- Name Info as of date - Edit mode only -->
            <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.DEM206.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.DEM206.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM206.tooltip}">${PamForm.formFieldMap.DEM206.label}</span> 
                    </td>
                    <td> 
                        <html:text name="PamForm" property="pamClientVO.answer(DEM206)" maxlength="10" title="Enter a Date" 
                                styleId="DEM206" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM206','DEM206Icon'); return false;"  onkeypress ="showCalendarEnterKey('DEM206','DEM206Icon',event);" 
                                styleId="DEM206Icon"></html:img> 
                    </td>
                </tr>
              </logic:notEqual> 
           </logic:notEqual>
            <!-- First Name -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.DEM104.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM104.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM104.tooltip}"> 
                        ${PamForm.formFieldMap.DEM104.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM104)" maxlength="48"
                            title="${PamForm.formFieldMap.DEM104.tooltip}"/>
                </td>
            </tr>
            <!-- Middle Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM105.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM105.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM105.tooltip}"> 
                        ${PamForm.formFieldMap.DEM105.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM105)" maxlength="48" 
                            title="${PamForm.formFieldMap.DEM105.tooltip}"/>
                </td>
            </tr>
            <!-- Last Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM102.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM102.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM102.tooltip}"> 
                        ${PamForm.formFieldMap.DEM102.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM102)" maxlength="48" 
                            title="${PamForm.formFieldMap.DEM102.tooltip}"/>
                </td>
            </tr>
            <!-- Suffix -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM107.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM107.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM107.tooltip}"> 
                        ${PamForm.formFieldMap.DEM107.label} 
                    </span> 
                </td>
                <td> 
                     <html:select name="PamForm" property="pamClientVO.answer(DEM107)" styleId="DEM107" 
                            title="${PamForm.formFieldMap.DEM107.label}">
                        <html:optionsCollection property="codedValue(DEM107)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
        </nedss:container>
        
	    <!-- SUB_SECTION : Other Personal Details -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Other Personal Details" classType="subSect" >
	       <!-- Other details As of Date - Edit mode only -->
            <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.DEM207.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.DEM207.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM207.tooltip}">
                            ${PamForm.formFieldMap.DEM207.label}
                        </span>
                    </td>
                    <td>
                        <html:text name="PamForm" property="pamClientVO.answer(DEM207)" maxlength="10" title="Enter a Date" 
                                styleId="DEM207" onkeyup="DateMask(this,null,event)" size="10"  onchange="calculateReportedAge()"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM207','DEM207Icon'); return false;"  onkeypress ="showCalendarEnterKey('DEM207','DEM207Icon',event);" 
                                styleId="DEM207Icon"></html:img>
                    </td>
                </tr>
             </logic:notEqual> 
           </logic:notEqual>
            <!-- Date of Birth -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM115.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM115.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM115.tooltip}"> 
                        ${PamForm.formFieldMap.DEM115.label} 
                    </span> 
                </td>
                <td> 
                    <html:text name="PamForm" property="pamClientVO.answer(DEM115)" maxlength="10" 
                            styleId="DEM115" title="${PamForm.formFieldMap.DEM115.tooltip}" onkeyup="DateMask(this,null,event)" 
                            size="10" onchange="calculateReportedAge()" onblur="calculateReportedAge()"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM115','DEM115Icon'); return false;"  onkeypress ="showCalendarEnterKey('DEM115','DEM115Icon',event);" 
                            styleId="DEM115Icon"></html:img>
                </td>
            </tr>
            <!-- Reported Age -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM216.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM216.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM216.tooltip}"> 
                        ${PamForm.formFieldMap.DEM216.label} 
                    </span>  
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM216)" size="3" maxlength="3" 
                            title="${PamForm.formFieldMap.DEM216.tooltip}" styleId="DEM216" onkeyup="isNumericCharacterEntered(this)"/>
                     <html:select title="${PamForm.formFieldMap.DEM216.label}" name="PamForm" property="pamClientVO.answer(DEM218)" styleId="DEM218">
                        <html:optionsCollection property="codedValue(DEM218)" value="key" label="value"/>
                    </html:select>
                    <!-- FIXME : see if this is 115 or not -->
                    <html:hidden property="pamClientVO.answer(DEM115)" styleId="DEM216"/>
                </td>
            </tr>
            <!-- Sex at Birth -->
            <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
        		%>

            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM114.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM114.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM114.tooltip}"> 
                        ${PamForm.formFieldMap.DEM114.label} 
                    </span> 
                </td>
                <td> 
                     <html:select title="${PamForm.formFieldMap.DEM114.label}" name="PamForm" property="pamClientVO.answer(DEM114)" styleId="DEM114"  onchange="fireRule('DEM114', this)">
                        <html:optionsCollection property="codedValue(DEM114)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <% } %>
            <!-- Current Sex -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM113.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM113.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM113.tooltip}"> 
                        ${PamForm.formFieldMap.DEM113.label} 
                    </span> 
                </td>
                <td> 
                     <html:select title="${PamForm.formFieldMap.DEM113.label}" name="PamForm" property="pamClientVO.answer(DEM113)" styleId="DEM113" onchange="fireRule('DEM113', this)">
                        <html:optionsCollection property="codedValue(DEM113)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Mortality Information As of Date - Edit mode only -->
             <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${PamForm.formFieldMap.DEM208.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.DEM208.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM208.tooltip}">${PamForm.formFieldMap.DEM208.label}</span>
                    </td>
                    <td class="topBorder">
                         <html:text name="PamForm" property="pamClientVO.answer(DEM208)" maxlength="10" title="Enter a Date" 
                                styleId="DEM208" onkeyup="DateMask(this,null,event)" size="10"/>
                         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM208','DEM208Icon'); return false;"  onkeypress ="showCalendarEnterKey('DEM208','DEM208Icon',event);" 
                                styleId="DEM208Icon"></html:img>
                    </td>
                </tr>
            </logic:notEqual> 
           </logic:notEqual>
            <!-- Is the Patient Deceased -->
            <tr>
                <td class="fieldName" id="DEM127L"> 
                    <span style="${PamForm.formFieldMap.DEM127.state.requiredIndClass}">*</span>
                    <span   style="${PamForm.formFieldMap.DEM127.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM127.tooltip}"> 
                        ${PamForm.formFieldMap.DEM127.label} 
                    </span> 
                </td>
                <td> 
                    <html:select title="${PamForm.formFieldMap.DEM127.label}" name="PamForm" property="pamClientVO.answer(DEM127)" styleId="DEM127" 
                            onchange="fireRule('DEM127', this)">
                         <html:optionsCollection property="codedValue(DEM127)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Deceased Date -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM128.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.DEM128.state.disabledString}"  id="DEM128L" style="${PamForm.formFieldMap.DEM128.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM128.tooltip}"> 
                        ${PamForm.formFieldMap.DEM128.label} 
                    </span> 
                </td>
                <td> 
                    <html:text name="PamForm" property="pamClientVO.answer(DEM128)" maxlength="10" 
                            styleId="DEM128" onchange="fireRule('DEM128', this)" onblur="fireRule('DEM128', this)" title="${PamForm.formFieldMap.DEM128.tooltip}"
                            onkeyup="DateMask(this,null,event)" size="10" disabled ="${PamForm.formFieldMap.DEM128.state.disabled}" />
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM128','DEM128Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM128','DEM128Icon',event);"
                            styleId="DEM128Icon"></html:img>
                </td>
            </tr>
            <!-- Marital Status as of date - Edit mode only -->
             <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                 <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${PamForm.formFieldMap.DEM209.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.DEM209.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM209.tooltip}">${PamForm.formFieldMap.DEM209.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="PamForm" property="pamClientVO.answer(DEM209)" maxlength="10" title="Enter a Date" 
                                styleId="DEM209" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM209','DEM209Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM209','DEM209Icon',event);" 
                                styleId="DEM209Icon"></html:img>
                    </td>
                </tr>
           </logic:notEqual> 
           </logic:notEqual>
            <!-- Marital Status -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM140.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM140.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM140.tooltip}"> 
                        ${PamForm.formFieldMap.DEM140.label} 
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.DEM140.label}" name="PamForm" property="pamClientVO.answer(DEM140)" styleId="DEM140" 
                            onchange="fireRule('DEM140', this)">
                         <html:optionsCollection property="codedValue(DEM140)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- SSN As of date - Edit mode only -->
          <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${PamForm.formFieldMap.DEM210.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.DEM210.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM210.tooltip}">${PamForm.formFieldMap.DEM210.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="PamForm" property="pamClientVO.answer(DEM210)" maxlength="10" title="Enter a Date" 
                                styleId="DEM210" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM210','DEM210Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM210','DEM210Icon',event);" 
                                styleId="DEM210Icon"></html:img>
                    </td>
                </tr>
         </logic:notEqual> 
           </logic:notEqual>
            <!-- SSN -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM133.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM133.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM133.tooltip}"> 
                        ${PamForm.formFieldMap.DEM133.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM133)" 
                        size="11" maxlength="11" onkeyup="SSNMask(this,event)" 
                        title="${PamForm.formFieldMap.DEM133.tooltip}" />
                </td>
            </tr>   
	    </nedss:container>
	    
	    <!-- SUB_SECTION : 4. Reporting Address for Case Counting-->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="4. Reporting Address for Case Counting" classType="subSect" >
	       <!-- Contact Information As of date - Edit mode only -->
            <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM213.state.requiredIndClass}">*</span>
                         <span style="${PamForm.formFieldMap.DEM213.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM213.tooltip}">${PamForm.formFieldMap.DEM213.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="PamForm" property="pamClientVO.answer(DEM213)" maxlength="10" title="Enter a Date" 
                                styleId="DEM213" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM213','DEM213Icon'); return false;"  onkeypress ="showCalendarEnterKey('DEM213','DEM213Icon',event);" 
                                styleId="DEM213Icon"></html:img>
                    </td>
                </tr>
         </logic:notEqual> 
           </logic:notEqual>
            <!-- Street Address 1 -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM159.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM159.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM159.tooltip}"> 
                        ${PamForm.formFieldMap.DEM159.label} 
                    </span> 
                <td> 
                    <html:text name="PamForm" property="pamClientVO.answer(DEM159)" 
                        title="${PamForm.formFieldMap.DEM159.tooltip}" size="30" maxlength="100"/>
                </td>
            </tr>
            <!-- Street Address 2 -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM160.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM160.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM160.tooltip}"> 
                        ${PamForm.formFieldMap.DEM160.label} 
                    </span>  
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM160)" 
                        title="${PamForm.formFieldMap.DEM160.tooltip}" size="30" maxlength="100"/>
                </td>
            </tr>
            <!-- City -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM161.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM161.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM161.tooltip}"> 
                        ${PamForm.formFieldMap.DEM161.label} 
                    </span> 
                </td>
                <td> 
                 <span id="DEM161SearchControls">
                	<html:text name="PamForm" property="pamClientVO.answer(DEM161)" 
                    title="${PamForm.formFieldMap.DEM161.tooltip}" size="30"  maxlength="100" styleId="DEM161"  onkeydown="genAutocomplete('DEM161','city_list')"/>
					<div class="page_name_auto_complete" id="city_list" style="background:#DCDCDC"></div>
					</span>
                </td>
            </tr>
            <!-- State -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM162.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM162.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM162.tooltip}"> 
                        ${PamForm.formFieldMap.DEM162.label} 
                    </span> 
                </td>
                <td> 
                    <html:select title="${PamForm.formFieldMap.DEM162.label}" name="PamForm" property="pamClientVO.answer(DEM162)" 
                            onchange="getDWRCounties(this, 'countyList');getDWRCitites(this);" styleId="DEM162">
                        <html:optionsCollection property="stateList" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Zip -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM163.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM163.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM163.tooltip}"> 
                        ${PamForm.formFieldMap.DEM163.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM163)" size="10" 
                        maxlength="10" title="${PamForm.formFieldMap.DEM163.tooltip}" onkeyup="ZipMask(this,event)"/>
                </td>
            </tr>
            <!-- County -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM165.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM165.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM165.tooltip}"> 
                        ${PamForm.formFieldMap.DEM165.label} 
                    </span> 
                </td>
                <td> 
                    <html:select title="${PamForm.formFieldMap.DEM165.label}" name="PamForm" property="pamClientVO.answer(DEM165)" 
                            styleId="countyList" >
                        <!-- <html:option value="" /> -->
                        <html:optionsCollection property="dwrCounties" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Country -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM167.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM167.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM167.tooltip}"> 
                        ${PamForm.formFieldMap.DEM167.label} 
                    </span> 
                </td>
                <td> 
                     <html:select title="${PamForm.formFieldMap.DEM167.label}" name="PamForm" property="pamClientVO.answer(DEM167)" styleId="DEM167">
                        <html:option value="" />
                        <html:optionsCollection property="countryList" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
        	%>
            <!-- Within City Limits -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM237.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM237.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM237.tooltip}"> 
                        ${PamForm.formFieldMap.DEM237.label} 
                    </span> 
                </td>
                <td> 
                     <html:select title="${PamForm.formFieldMap.DEM237.label}" name="PamForm" property="pamClientVO.answer(DEM237)" styleId="DEM237">
                        <html:option value="" />
                        <html:optionsCollection property="codedValue(DEM237)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <%}%>
	    </nedss:container>
	    
	    <!-- SUB_SECTION : Telephone Information -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Telephone Information" classType="subSect" >
            <!-- Telephone Information As of date - Edit mode only -->
             <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM214.state.requiredIndClass}">*</span>
                         <span style="${PamForm.formFieldMap.DEM214.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM214.tooltip}">${PamForm.formFieldMap.DEM214.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="PamForm" property="pamClientVO.answer(DEM214)" maxlength="10" title="Enter a Date" 
                                styleId="DEM214" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM214','DEM214Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM214','DEM214Icon',event);" 
                                styleId="DEM214Icon"></html:img>
                    </td>
                </tr>
           </logic:notEqual> 
           </logic:notEqual>
            <!-- Home Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM238.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM238.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM238.tooltip}"> 
                        ${PamForm.formFieldMap.DEM238.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM238)" styleId="DEM238"
                            onkeyup="TeleMask(this,event)"
                            title="${PamForm.formFieldMap.DEM238.tooltip}" maxlength="12" size="15"/>
                </td>
            </tr>
            <!-- Home Phone ext -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM239.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM239.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM239.tooltip}"> 
                        ${PamForm.formFieldMap.DEM239.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM239)" size="10" maxlength="8" 
                        title="${PamForm.formFieldMap.DEM239.tooltip}" onkeyup="isNumericCharacterEntered(this)"/>
                </td>
            </tr>
            <!-- Work Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM240.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM240.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM240.tooltip}"> 
                        ${PamForm.formFieldMap.DEM240.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM240)"  styleId="DEM240"
                            onkeyup="TeleMask(this,event)" maxlength="12" size="15"
                            title="${PamForm.formFieldMap.DEM240.tooltip}"/>
                </td>
            </tr>
            <!-- Work Phone ext -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM241.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM241.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM241.tooltip}"> 
                        ${PamForm.formFieldMap.DEM241.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="PamForm" property="pamClientVO.answer(DEM241)" size="10" maxlength="8"  
                        title="${PamForm.formFieldMap.DEM241.tooltip}" onkeyup="isNumericCharacterEntered(this)"/>
                </td>
            </tr>	       
	    </nedss:container>
	    
	    <!-- SUB_SECTION : Ethnicity & Race Information -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Ethnicity & Race Information" classType="subSect" >
            <!-- Ethnicity and Race Information As of date - Edit mode only -->
           <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM211.state.requiredIndClass}">*</span>
                         <span style="${PamForm.formFieldMap.DEM211.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM211.tooltip}">${PamForm.formFieldMap.DEM211.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="PamForm" property="pamClientVO.answer(DEM211)" maxlength="10" title="Enter a Date" 
                                styleId="DEM211" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM211','DEM211Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM211','DEM211Icon',event);" 
                                styleId="DEM211Icon"></html:img>
                    </td>
                </tr>
           </logic:notEqual> 
           </logic:notEqual>
            
            <!-- Ethnicity -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM155.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM155.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM155.tooltip}"> 
                        ${PamForm.formFieldMap.DEM155.label} 
                    </span> 
                </td>
                <td> 
                    <html:select title="${PamForm.formFieldMap.DEM155.label}" name="PamForm" property="pamClientVO.answer(DEM155)" styleId="DEM155">
                        <html:optionsCollection property="codedValue(DEM155)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Race -->
        <logic:notEqual name="PamForm" property="actionMode" value="Create">	
			    <logic:notEqual name="PamForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM212.state.requiredIndClass}">*</span>
                         <span style="${PamForm.formFieldMap.DEM212.errorStyleClass}" 
                                title="${PamForm.formFieldMap.DEM212.tooltip}">${PamForm.formFieldMap.DEM212.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="PamForm" property="pamClientVO.answer(DEM212)" maxlength="10" title="Enter a Date" 
                                styleId="DEM212" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM212','DEM212Icon'); return false;"  onkeypress ="showCalendarEnterKey('DEM212','DEM212Icon',event);" 
                                styleId="DEM212Icon"></html:img>
                    </td>
                </tr>
               </logic:notEqual> 
           </logic:notEqual>
            <!-- Alaska & American Indian race -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM152.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM152.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM152.tooltip}"> 
                        ${PamForm.formFieldMap.DEM152.label} 
                    </span> 
                </td>
                <td>
                   <html:checkbox name="PamForm" property="pamClientVO.americanIndianAlskanRace" value="1" 
                            title="${PamForm.formFieldMap.DEM152.tooltip}">
                    </html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>
                </td>
            </tr>
            <!-- Asian race -->
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <!-- Asian race Only - For Varicella -->
                    <% 
                        if(!request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
                    %>
	                    <html:checkbox name="PamForm" property="pamClientVO.asianRace" value="1" 
	                            title="${PamForm.formFieldMap.DEM152.tooltip}">
	                    </html:checkbox>
	                    <bean:message bundle="RVCT" key="rvct.asian"/>
                    <%}%>
                    
                    <!-- Asian race with sub race options - For TB -->
                     <% 
                        if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
                     %> 
                        <html:checkbox name="PamForm" property="pamClientVO.asianRace" value="1" 
	                            title="${PamForm.formFieldMap.DEM152.tooltip}" onclick="checkAsianRaces();">
	                    </html:checkbox>
	                    <bean:message bundle="RVCT" key="rvct.asian"/>
	                    <!-- Asian races options -->
			            <div id="asianlist">
			                <div class="CheckboxField">
			                    <div id="asian-multi" style="background:#DFEFFF; margin:0.1em 1em 0.1em 0.25em; padding:0.5em;"> 
			                        <i> (Use Ctrl to select more than one) </i> <br/>
			                        <html:select title="${PamForm.formFieldMap.DEM152.label}" property="pamClientVO.answerArray(DEM243)" 
			                                styleId="DEM243" 
			                                multiple="true" size="4"
			                                onchange="displaySelectedOptions(this, 'DEM243-selectedValues')" >
			                             <!-- FIXME : verify value passed in raceList -->   
			                            <html:optionsCollection property="raceList(2028-9)" value="key" label="value"/>
			                        </html:select> 
			                        <br/>
			                        <!-- Asian races selections made -->
			                        <div id="DEM243-selectedValues" style="margin:0.25em;">
			                           <b> Selected Values:</b>
			                        </div>       
			                    </div>
			                </div>
			            </div>
		            <% } %>        
                </td>
            </tr>
            
            <!-- Black/African American race -->
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <html:checkbox name="PamForm" property="pamClientVO.africanAmericanRace" 
                            value="1" title="${PamForm.formFieldMap.DEM152.tooltip}">
                    </html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>
                </td>
            </tr>
            <!-- Native Hawaiian or Other Pacific Islander race -->
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <!-- Hawaiian race Only - For Varicella -->
                    <% 
                        if(!request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
                    %>
                    <html:checkbox name="PamForm" property="pamClientVO.hawaiianRace" value="1" 
                            title="${PamForm.formFieldMap.DEM152.tooltip}">
	                </html:checkbox>
	                <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>
	                <% } %>
	            
	                <!-- Hawaiian race with sub race options - For TB -->
                    <% 
                       if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
                    %>
                        <html:checkbox name="PamForm" property="pamClientVO.hawaiianRace" value="1" 
                                title="${PamForm.formFieldMap.DEM152.tooltip}" onclick="checkHawaiianRaces();">
	                    </html:checkbox>
	                    <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>
	                    
			            <div id="hawaiianlist">
			                <div class="CheckboxField">
			                    <div id="hawaiian-multi" style="background:#DFEFFF;  margin:0.25em 1em 0.25em 0.25em; padding:0.5em; "> 
			                        <i> (Use Ctrl to select more than one) </i> <br/>
			
			                        <html:select title="${PamForm.formFieldMap.DEM152.label}" property="pamClientVO.answerArray(DEM245)" 
			                                styleId="DEM245" 
			                                multiple="true" size="4"
			                                onchange="displaySelectedOptions(this, 'DEM245-selectedValues')" >
			                            <!-- FIXME : verify value passed in raceList -->
			                            <html:optionsCollection property="raceList(2076-8)" value="key" label="value"/>
			                        </html:select>
			                        <br/>
			                        <!-- Hawaiian races selections made -->
			                        <div id="DEM245-selectedValues" style="margin:0.25em; padding:0.25em; min-height:1px;">
			                           <b> Selected Values:</b>
			                        </div>       
			                    </div>
			                </div>
			            </div>
		            <% } %>
                </td>
            </tr>
            
            <!-- White race -->    
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <html:checkbox name="PamForm" property="pamClientVO.whiteRace" value="1" 
                            title="${PamForm.formFieldMap.DEM152.tooltip}"></html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.white"/>
                </td>
            </tr>
            <!-- Unknown race -->    
            <tr>
                <td>
                    &nbsp;
                </td>
                <td>
                    <html:checkbox name="PamForm" property="pamClientVO.unKnownRace" value="1" 
                            title="${PamForm.formFieldMap.DEM152.tooltip}"></html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.unknown"/>
                </td>
            </tr>	       
	    </nedss:container>
	</nedss:container>
   <!-- SECTION : Local Fields -->
   <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		${fn:escapeXml(NEWPAT_LDFS)}
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
</div> <!-- div class="view" -->
</td> </tr>