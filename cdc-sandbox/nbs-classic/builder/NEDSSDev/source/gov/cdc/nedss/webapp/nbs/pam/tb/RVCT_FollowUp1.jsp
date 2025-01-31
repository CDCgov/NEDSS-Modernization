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
    String tabId = "editFollowUp1";
    String []sectionNames = {"Initial Drug Susceptibility Report", "Custom Fields"};
    int sectionIndex = 0; 
%>

<div class="view" id="<%= tabId %>" style="text-align:center;">    

    <% if(request.getAttribute("1331") != null) { %>
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
            
    <!-- SECTION : Initial Drug Susceptibility Report --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : 38.Genotyping Accession Number -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="38.Genotyping Accession Number" classType="subSect" >
            <!-- Isolate submitted for genotyping -->           
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB192.state.requiredIndClass}">*</span>
                    <span id="TUB192L" style="${PamForm.formFieldMap.TUB192.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB192.tooltip}">
                        ${PamForm.formFieldMap.TUB192.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB192.label}" disabled ="${PamForm.formFieldMap.TUB192.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB192)" styleId = "TUB192" onchange="fireRule('TUB192', this)">
                          <html:optionsCollection property="codedValue(TUB192)" value="key" label="value"/>
                    </html:select>
                   
                </td>
            </tr>
           <!-- Genotyping Accession number -->           
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB193.state.requiredIndClass}">*</span>
                    <span   class="${PamForm.formFieldMap.TUB114.state.disabledString}" 
			     id="TUB193L" style="${PamForm.formFieldMap.TUB193.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB193.tooltip}">
                        ${PamForm.formFieldMap.TUB193.label}
                    </span> 
                </td>
                <td> 
                      <html:text maxlength="12" size="12" disabled ="${PamForm.formFieldMap.TUB193.state.disabled}"
                        property="pamClientVO.answer(TUB193)" styleId="TUB193" 
                        title="${PamForm.formFieldMap.TUB193.tooltip}"
                        onkeydown="fireRuleAndChangeFocusOnTabKey('TUB193', this)" 
                        onblur="fireRule('TUB193', this, false)"/>
                </td>
            </tr>
            <!-- 38.Genotyping Accession Number LDFs -->
            <%= request.getAttribute("1142") == null ? "" :  request.getAttribute("1142") %>
         </nedss:container>
         
         <!-- SUBSECT : 39. Initial Drug Susceptibility Testing -->
		 <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="39. Initial Drug Susceptibility Testing" classType="subSect" >
            <!-- Was drug susceptibilty testing done -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB194.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB194.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB194.tooltip}">
                            ${PamForm.formFieldMap.TUB194.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB194.label}" disabled ="${PamForm.formFieldMap.TUB194.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB194)" styleId = "TUB194" onchange="fireRule('TUB194', this); handleFollowUp1_DrugTestBtns('TUB194');">
                            <html:optionsCollection property="formFieldMap.TUB194.state.values" value="key" label="value"/>
                    </html:select>
                </td>  
            </tr>	
            
			<!-- Date First Isolate Collected -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB195.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB195.state.disabledString}" 
				  id="TUB195L"  style="${PamForm.formFieldMap.TUB195.errorStyleClass}" title="${PamForm.formFieldMap.TUB195.tooltip}">${PamForm.formFieldMap.TUB195.label}</span>
                </td>
                <td>
                   <html:text maxlength="10" property="pamClientVO.answer(TUB195)" styleId="TUB195" onblur="fireRule('TUB195', this)" onchange="fireRule('TUB195', this)" disabled ="${PamForm.formFieldMap.TUB195.state.disabled}" onkeyup="DateMask(this,null,event)" size="10" title="${PamForm.formFieldMap.TUB195.tooltip}"/>
                <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB195','fieldDateFirstIsolateCollectedIcon'); return false;" onkeypress ="showCalendarEnterKey('TUB195','fieldDateFirstIsolateCollectedIcon',event);" styleId="fieldDateFirstIsolateCollectedIcon"/>
                </td>
            </tr>
            <!-- Specimen Type is Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB196.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB196.state.disabledString}" 
				 id="TUB196L" style="${PamForm.formFieldMap.TUB196.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB196.tooltip}">
                        ${PamForm.formFieldMap.TUB196.label}
                    </span> 
                </td>
                <td>
                     <html:select title="${PamForm.formFieldMap.TUB196.label}" disabled ="${PamForm.formFieldMap.TUB196.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB196)" styleId="TUB196" 
                            onchange="fireRule('TUB196', this)" >
                        <html:optionsCollection property="codedValue(TUB196)" value="key" label="value"/>
                    </html:select> 
                </td>
            </tr>
            <!-- Specimen Type  not  Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB197.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB197.state.disabledString}" 
					 id="TUB197L" style="${PamForm.formFieldMap.TUB197.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB197.tooltip}">
                        ${PamForm.formFieldMap.TUB197.label}
                    </span> 
                </td>
                <td>
                     <html:select title="${PamForm.formFieldMap.TUB197.label}" disabled ="${PamForm.formFieldMap.TUB197.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB197)" styleId="TUB197" onchange="fireRule('TUB197', this)"
                           >
                        <html:optionsCollection property="codedValue(TUB197)" value="key" label="value"/>
                    </html:select> 
                </td>
            </tr>
            <!-- 39. Initial Drug Susceptibility Testing LDFs-->
            <%= request.getAttribute("1145") == null ? "" :  request.getAttribute("1145") %>
        </nedss:container>
        
        <!-- SUBSECT : 40. Initial Drug Susceptibility Results -->
		<nedss:container id="<%= tabId  + (++subSectionIndex) %>" name="40. Initial Drug Susceptibility Results" classType="subSect" >
		    <!-- Standard 4 Susceptiblities -->
            <tr>
               <td class="fieldName">
                   &nbsp;           
                </td>
                <td>
                    <input id="followUp1_StandardSusceptibilities" type="button" class="Button" 
                            value="Standard Susceptiblities (4)" style="width:15em;"
                            onclick="populateInitialSusceptibility('followUp1_MarkRestNotDone');"/>
                </td>
            </tr>
			 <!-- Mark Rest Not Done -->
            <tr>
               <td class="fieldName">
                    &nbsp; 
                </td>
                <td>
                    <input id="followUp1_MarkRestNotDone" type="button" class="Button" 
                            value="Mark Rest 'Not Done'" style="width:15em;" 
                            onclick="populateInitialMarkRestNo('<%= tabId + (subSectionIndex) %>');"/>
                </td>
            </tr>
	        <!-- Isoniazid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB198.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB198.state.disabledString}" 
                          id="TUB198L" style="${PamForm.formFieldMap.TUB198.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB198.tooltip}">
                            ${PamForm.formFieldMap.TUB198.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB198.label}" disabled ="${PamForm.formFieldMap.TUB198.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB198)" styleId = "TUB198" onchange="fireRule('TUB198', this)">
                            <html:optionsCollection property="codedValue(TUB198)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!-- Rifampin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB199.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB199.state.disabledString}" 
				  id="TUB199L"  style="${PamForm.formFieldMap.TUB199.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB199.tooltip}">
                            ${PamForm.formFieldMap.TUB199.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB199.label}" disabled ="${PamForm.formFieldMap.TUB199.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB199)" styleId = "TUB199" onchange="fireRule('TUB199', this)">
                            <html:optionsCollection property="codedValue(TUB199)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!--Pyrazinamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB200.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB200.state.disabledString}" 
				  id="TUB200L" style="${PamForm.formFieldMap.TUB200.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB200.tooltip}">
                            ${PamForm.formFieldMap.TUB200.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB200.label}" disabled ="${PamForm.formFieldMap.TUB200.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB200)" styleId = "TUB200" onchange="fireRule('TUB200', this)">
                            <html:optionsCollection property="codedValue(TUB200)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!--Ehtambutol-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB201.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB201.state.disabledString}" 
				 id="TUB201L" style="${PamForm.formFieldMap.TUB201.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB201.tooltip}">
                            ${PamForm.formFieldMap.TUB201.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB201.label}" disabled ="${PamForm.formFieldMap.TUB201.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB201)" styleId = "TUB201" onchange="fireRule('TUB201', this)">
                            <html:optionsCollection property="codedValue(TUB201)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!--Streptomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB202.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB202.state.disabledString}" 
				  id="TUB202L" style="${PamForm.formFieldMap.TUB202.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB202.tooltip}">
                            ${PamForm.formFieldMap.TUB202.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB202.label}" disabled ="${PamForm.formFieldMap.TUB202.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB202)" styleId = "TUB202" onchange="fireRule('TUB202', this)">
                            <html:optionsCollection property="codedValue(TUB202)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!--Rifabutin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB209.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB209.state.disabledString}" 
                  id="TUB209L" style="${PamForm.formFieldMap.TUB209.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB209.tooltip}">
                            ${PamForm.formFieldMap.TUB209.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB209.label}" disabled ="${PamForm.formFieldMap.TUB209.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB209)" styleId = "TUB209" onchange="fireRule('TUB209', this)">
                            <html:optionsCollection property="codedValue(TUB209)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Rifapentine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB212.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB212.state.disabledString}" 
                  id="TUB212L" style="${PamForm.formFieldMap.TUB212.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB212.tooltip}">
                            ${PamForm.formFieldMap.TUB212.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB212.label}" disabled ="${PamForm.formFieldMap.TUB212.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB212)" styleId = "TUB212" onchange="fireRule('TUB212', this)">
                            <html:optionsCollection property="codedValue(TUB212)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!--Ethionamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB203.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB203.state.disabledString}" 
				 id="TUB203L" style="${PamForm.formFieldMap.TUB203.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB203.tooltip}">
                            ${PamForm.formFieldMap.TUB203.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB203.label}" disabled ="${PamForm.formFieldMap.TUB203.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB203)" styleId = "TUB203" onchange="fireRule('TUB203', this)">
                            <html:optionsCollection property="codedValue(TUB203)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Amikacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB208.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB208.state.disabledString}" 
                  id="TUB208L" style="${PamForm.formFieldMap.TUB208.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB208.tooltip}">
                            ${PamForm.formFieldMap.TUB208.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB208.label}" disabled ="${PamForm.formFieldMap.TUB208.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB208)" styleId = "TUB208" onchange="fireRule('TUB208', this)">
                            <html:optionsCollection property="codedValue(TUB208)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!--Kanamycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB204.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB204.state.disabledString}" 
				  id="TUB204L" style="${PamForm.formFieldMap.TUB204.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB204.tooltip}">
                            ${PamForm.formFieldMap.TUB204.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB204.label}" disabled ="${PamForm.formFieldMap.TUB204.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB204)" styleId = "TUB204" onchange="fireRule('TUB204', this)">
                            <html:optionsCollection property="codedValue(TUB204)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Capreomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB206.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB206.state.disabledString}" 
                  id="TUB206L" style="${PamForm.formFieldMap.TUB206.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB206.tooltip}">
                            ${PamForm.formFieldMap.TUB206.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB206.label}" disabled ="${PamForm.formFieldMap.TUB206.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB206)" styleId = "TUB206" onchange="fireRule('TUB206', this)">
                            <html:optionsCollection property="codedValue(TUB206)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Ciprofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB210.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB210.state.disabledString}" 
                  id="TUB210L" style="${PamForm.formFieldMap.TUB210.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB210.tooltip}">
                            ${PamForm.formFieldMap.TUB210.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB210.label}" disabled ="${PamForm.formFieldMap.TUB210.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB210)" styleId = "TUB210" onchange="fireRule('TUB210', this)">
                            <html:optionsCollection property="codedValue(TUB210)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Levofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB213.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB213.state.disabledString}"
                          id="TUB213L" style="${PamForm.formFieldMap.TUB213.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB213.tooltip}">
                            ${PamForm.formFieldMap.TUB213.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB213.label}" disabled ="${PamForm.formFieldMap.TUB213.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB213)" styleId = "TUB213" onchange="fireRule('TUB213', this)">
                            <html:optionsCollection property="codedValue(TUB213)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Ofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB211.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB211.state.disabledString}" 
                 id="TUB211L" style="${PamForm.formFieldMap.TUB211.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB211.tooltip}">
                            ${PamForm.formFieldMap.TUB211.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB211.label}" disabled ="${PamForm.formFieldMap.TUB211.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB211)" styleId = "TUB211" onchange="fireRule('TUB211', this)">
                            <html:optionsCollection property="codedValue(TUB211)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Moxifloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB214.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB214.state.disabledString}" 
                            id="TUB214L" style="${PamForm.formFieldMap.TUB214.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB214.tooltip}">
                            ${PamForm.formFieldMap.TUB214.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB214.label}" disabled ="${PamForm.formFieldMap.TUB214.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB214)" styleId = "TUB214" onchange="fireRule('TUB214', this)">
                            <html:optionsCollection property="codedValue(TUB214)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Other Quinolones-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB215.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB215.state.disabledString}"  id="TUB215L"  style="${PamForm.formFieldMap.TUB215.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB215.tooltip}">
                            ${PamForm.formFieldMap.TUB215.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB215.label}" disabled ="${PamForm.formFieldMap.TUB215.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB215)" styleId = "TUB215" onchange="fireRule('TUB215', this)">
                            <html:optionsCollection property="codedValue(TUB215)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Cycloserine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB205.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB205.state.disabledString}" 
				  id="TUB205L" style="${PamForm.formFieldMap.TUB205.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB205.tooltip}">
                            ${PamForm.formFieldMap.TUB205.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB205.label}" disabled ="${PamForm.formFieldMap.TUB205.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB205)" styleId = "TUB205" onchange="fireRule('TUB205', this)">
                            <html:optionsCollection property="codedValue(TUB205)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!-- Para-Amino Salicylic Acid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB207.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB207.state.disabledString}" 
				 id="TUB207L" style="${PamForm.formFieldMap.TUB207.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB207.tooltip}">
                            ${PamForm.formFieldMap.TUB207.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB207.label}" disabled ="${PamForm.formFieldMap.TUB207.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB207)" styleId = "TUB207" onchange="fireRule('TUB207', this)">
                            <html:optionsCollection property="codedValue(TUB207)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!-- Other Drug-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB216.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB216.state.disabledString}"  id="TUB216L" style="${PamForm.formFieldMap.TUB216.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB216.tooltip}">
                            ${PamForm.formFieldMap.TUB216.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB216.label}" disabled ="${PamForm.formFieldMap.TUB216.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB216)" styleId = "TUB216" onchange="fireRule('TUB216', this)">
                            <html:optionsCollection property="codedValue(TUB216)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!-- Specify Other Drug-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB217.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB217.state.disabledString}"  id="TUB217L" style="${PamForm.formFieldMap.TUB217.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB217.tooltip}">
                            ${PamForm.formFieldMap.TUB217.label}
                        </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB217.state.disabled}"  styleId = "TUB217" onblur="fireRule('TUB217', this)" maxlength="100"
                            property="pamClientVO.answer(TUB217)" title="${PamForm.formFieldMap.TUB217.tooltip}" />    
                </td>
            </tr>
			 <!-- Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB218.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB218.state.disabledString}"  id="TUB218L" style="${PamForm.formFieldMap.TUB218.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB218.tooltip}">
                            ${PamForm.formFieldMap.TUB218.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB218.label}" disabled ="${PamForm.formFieldMap.TUB218.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB218)" styleId = "TUB218" onchange="fireRule('TUB218', this)">
                            <html:optionsCollection property="codedValue(TUB218)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!-- Specify Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB219.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB219.state.disabledString}"  id="TUB219L"style="${PamForm.formFieldMap.TUB219.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB219.tooltip}">
                            ${PamForm.formFieldMap.TUB219.label}
                        </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB219.state.disabled}"  styleId = "TUB219"  onblur="fireRule('TUB219', this)" maxlength="100"
                            property="pamClientVO.answer(TUB219)" title="${PamForm.formFieldMap.TUB219.label}" />                        
                </td>
            </tr>
			<!--Clear -->
            <tr>
               <td class="fieldName">
                    &nbsp;                
                </td>
                <td>
                    <input id="followUp1_Clear" type="button" class="Button" value="Clear" onclick="clearAll('<%= tabId + (subSectionIndex) %>');"//>
                </td>
            </tr>
            <!-- 40. Initial Drug Susceptibility Results LDFs-->
            <%= request.getAttribute("1150") == null ? "" :  request.getAttribute("1150") %>
        </nedss:container>
        
        <!-- SUBSECT : Follow Up Comments-->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Follow Up 1 Comments" classType="subSect" >			
			<!-- Comments-->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB270.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB270.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB270.tooltip}">${PamForm.formFieldMap.TUB270.label}                    
                </td>
                <td>
                    <html:textarea title="${PamForm.formFieldMap.TUB270.label}" style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${PamForm.formFieldMap.TUB270.state.disabled}" 
                             onkeydown="checkMaxLength(this)"  onkeyup="checkMaxLength(this)"  name="PamForm" property="pamClientVO.answer(TUB270)"/>
                </td>
            </tr>
            <!-- Follow Up Comments LDFs-->
            <%= request.getAttribute("1173") == null ? "" :  request.getAttribute("1173") %>
	   </nedss:container>   
   </nedss:container>
   
    <!-- SECTION : Local Fields -->
    <% if(request.getAttribute("1331") != null) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
                <%= request.getAttribute("1331") == null ? "" :  request.getAttribute("1331") %>
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