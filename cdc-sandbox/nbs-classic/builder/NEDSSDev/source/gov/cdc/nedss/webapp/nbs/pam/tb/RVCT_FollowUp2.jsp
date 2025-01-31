<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import = "gov.cdc.nedss.util.PropertyUtil" %>
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
    String tabId = "editFollowUp2";
    String []sectionNames = {"Case Completion Report", "Custom Fields"};
    int sectionIndex = 0; 
%>

<div class="view" id="<%= tabId %>" style="text-align:center;">    
    <% if(request.getAttribute("1333") != null) { %>
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
            
    <!-- SECTION : Case Completion Report --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : 41. Sputum Culture Conversion Documented: -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="41. Sputum Culture Conversion Documented" classType="subSect" >
            <!-- Sputum Culture Conversion Documented -->           
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB220.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB220.state.disabledString}"  id="TUB220L" style="${PamForm.formFieldMap.TUB220.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB220.tooltip}">
                        ${PamForm.formFieldMap.TUB220.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB220.label}" disabled ="${PamForm.formFieldMap.TUB220.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB220)" styleId = "TUB220" onchange="fireRule('TUB220', this)">
                          <html:optionsCollection property="codedValue(TUB220)" value="key" label="value"/>
                    </html:select>
                   
                </td>
            </tr>
           <!-- Date of First Consistently Negative Culture -->           
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB221.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB221.state.disabledString}"  id="TUB221L" style="${PamForm.formFieldMap.TUB221.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB221.tooltip}">
                        ${PamForm.formFieldMap.TUB221.label}
                    </span> 
                </td>
                <td>
                       <html:text maxlength="10" property="pamClientVO.answer(TUB221)" styleId="TUB221"  onblur="fireRule('TUB221', this)"  onchange="fireRule('TUB221', this)" disabled ="${PamForm.formFieldMap.TUB221.state.disabled}" onkeyup="DateMask(this,null,event)" size="10" title="${PamForm.formFieldMap.TUB221.label}"/>
            <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB221','fieldDateofFirstNegativeCultureIcon'); return false;" onkeypress ="showCalendarEnterKey('TUB221','fieldDateofFirstNegativeCultureIcon',event);" styleId="fieldDateofFirstNegativeCultureIcon"/>
                </td>
            </tr>
			 <!-- Reason for not documenting  sputum culture conversion -->           
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB222.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB222.state.disabledString}" id="TUB222L" style="${PamForm.formFieldMap.TUB222.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB222.tooltip}">
                        ${PamForm.formFieldMap.TUB222.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB222.label}" disabled ="${PamForm.formFieldMap.TUB222.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB222)" styleId = "TUB222" onchange="fireRule('TUB222', this)">
                          <html:optionsCollection property="codedValue(TUB222)" value="key" label="value"/>
                    </html:select>
                   
                </td>
            </tr>
			 <!--Other  reason for not documenting  sputum culture conversion-->           
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB223.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB223.state.disabledString}" id="TUB223L" style="${PamForm.formFieldMap.TUB223.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB223.tooltip}">
                        ${PamForm.formFieldMap.TUB223.label}
                    </span> 
                </td>
                <td>
                     <html:text name="PamForm" property="pamClientVO.answer(TUB223)" maxlength="100" 
                            title="${PamForm.formFieldMap.TUB223.label}" styleId="TUB223" onblur="fireRule('TUB223', this)" disabled ="${PamForm.formFieldMap.TUB223.state.disabled}"  />
                   
                </td>
            </tr>		
            <!-- 41. Sputum Culture Conversion Documented: LDFs-->
            <%= request.getAttribute("1178") == null ? "" :  request.getAttribute("1178") %>		
        </nedss:container>
        
        <!-- SUBSECT : 42. Moved: -->
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="42. Moved" classType="subSect" >
            <!-- Did the patient moved during TB therapy-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB224.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB224.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB224.tooltip}">
                            ${PamForm.formFieldMap.TUB224.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB224.label}" disabled ="${PamForm.formFieldMap.TUB224.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB224)" styleId = "TUB224" onchange="fireRule('TUB224', this)">
                            <html:optionsCollection property="codedValue(TUB224)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Moved to where -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB225.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB225.state.disabledString}" id="TUB225L" style="${PamForm.formFieldMap.TUB225.errorStyleClass}" title="${PamForm.formFieldMap.TUB225.tooltip}">${PamForm.formFieldMap.TUB225.label}</span>
                </td>
               <!-- <td>
                   <html:select title="${PamForm.formFieldMap.TUB225.label}" disabled ="${PamForm.formFieldMap.TUB225.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB225)" styleId = "TUB225"  onchange="fireRule('TUB225', this);">
                            <html:optionsCollection property="codedValue(TUB225)" value="key" label="value"/>
                        </html:select>
                </td> -->
                <td>
		                    <div class="multiSelectBlock">
		                            <i> (Use Ctrl to select more than one) </i> <br/>
									<html:select title="${PamForm.formFieldMap.TUB225.label}" property="pamClientVO.answerArray(TUB225)" 
											styleId="TUB225" multiple="true" size="4"
											disabled ="${PamForm.formFieldMap.TUB225.state.disabled}"
											onchange="displaySelectedOptions(this, 'TUB225-selectedValues'); fireRule('TUB225', this);">
										<html:optionsCollection  property="codedValue(TUB225)" value="key" label="value"/>
									</html:select> 
		                        <br/>
		                        <div id="TUB225-selectedValues" style="margin:0.25em;">
		                           <b> Selected Values: </b>
		                        </div>
		                    </div>                        
                </td>
            </tr>
            <!--In State Move - City -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB227.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB227.state.disabledString}" id="TUB227L" style="${PamForm.formFieldMap.TUB227.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB227.tooltip}">
                        ${PamForm.formFieldMap.TUB227.label}
                    </span> 
                </td>
                <td>
                    <html:text name="PamForm" property="pamClientVO.answer(TUB227)" maxlength="100" 
                        title="${PamForm.formFieldMap.TUB227.label}" styleId="TUB227" 
                        disabled ="${PamForm.formFieldMap.TUB227.state.disabled}"
                        onkeydown="fireRuleAndChangeFocusOnTabKey('TUB227', this)" 
                        onblur="fireRule('TUB227', this, false)"/>
                </td>
            </tr>
            <!--In State Move - City 2 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB272.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB272.state.disabledString}" id="TUB272L" style="${PamForm.formFieldMap.TUB272.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB272.tooltip}">
                        ${PamForm.formFieldMap.TUB272.label}
                    </span> 
                </td>
                <td>
                     <html:text name="PamForm" property="pamClientVO.answer(TUB272)" maxlength="50" disabled ="${PamForm.formFieldMap.TUB272.state.disabled}" styleId="TUB272"  onblur="fireRule('TUB272', this)"
                            title="${PamForm.formFieldMap.TUB272.label}"/>
                </td>
            </tr>
			 <!-- In State Move - County -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB228.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB228.state.disabledString}"  
                            id="TUB228L" style="${PamForm.formFieldMap.TUB228.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB228.tooltip}">${PamForm.formFieldMap.TUB228.label}</span>
                </td>
                <td>
                    <div class="multiSelectBlock">
                            <i> (Use Ctrl to select more than one) </i> <br/>
							<html:select title="${PamForm.formFieldMap.TUB228.label}" property="pamClientVO.answerArray(TUB228)" 
									styleId="TUB228"
									disabled ="${PamForm.formFieldMap.TUB228.state.disabled}"
									multiple="true" size="4"
									onchange="displaySelectedOptions(this, 'TUB228-selectedValues');" >
								<html:optionsCollection  property="dwrDefaultStateCounties" value="key" label="value"/>
							</html:select> 
                        <br/>
                        <div id="TUB228-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>                        
                </td>
            </tr>
			 <!-- out of state move - state -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB229.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB229.state.disabledString}" id="TUB229L" style="${PamForm.formFieldMap.TUB229.errorStyleClass}" title="${PamForm.formFieldMap.TUB229.tooltip}">${PamForm.formFieldMap.TUB229.label}</span>
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB229.label}" property="pamClientVO.answerArray(TUB229)" 
                                styleId="TUB229" 
                                disabled ="${PamForm.formFieldMap.TUB229.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB229-selectedValues')" >
                            <!-- FIXME : verify property value passed -->        
                            <html:optionsCollection property="stateList"  value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB229-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>        
                </td>
            </tr>
			 <!-- out of country move - country-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB230.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB230.state.disabledString}" id="TUB230L" style="${PamForm.formFieldMap.TUB230.errorStyleClass}" title="${PamForm.formFieldMap.TUB230.tooltip}">${PamForm.formFieldMap.TUB230.label}</span>
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB230.label}" property="pamClientVO.answerArray(TUB230)" 
                                styleId="TUB230" 
                                disabled ="${PamForm.formFieldMap.TUB230.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB230-selectedValues')" >
                            <!-- FIXME : verify property value passed -->        
                            <html:optionsCollection property="codedValue(TUB230)"  value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB230-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>      
                </td>
            </tr>
			<!-- Transnational Referral-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB226.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB226.state.disabledString}" id="TUB226L" style="${PamForm.formFieldMap.TUB226.errorStyleClass}" title="${PamForm.formFieldMap.TUB226.tooltip}">${PamForm.formFieldMap.TUB226.label}</span>
                </td>
                <td>
                   <html:select title="${PamForm.formFieldMap.TUB226.label}" disabled ="${PamForm.formFieldMap.TUB226.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB226)" styleId = "TUB226">
                            <html:optionsCollection property="codedValue(TUB226)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Moved LDFs -->
            <%= request.getAttribute("1183") == null ? "" :  request.getAttribute("1183") %>
        </nedss:container>

        <!-- SUBSECT : Therapy -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Therapy" classType="subSect" >
			<!-- 43. Date Therapy Stopped -->
			 <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB232.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB232.state.disabledString}"  id="TUB232L" style="${PamForm.formFieldMap.TUB232.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB232.tooltip}">${PamForm.formFieldMap.TUB232.label}</span> 
                </td>
                <td>
                    <html:text name="PamForm" title="${PamForm.formFieldMap.TUB232.label}" property="pamClientVO.answer(TUB232)" maxlength="10" styleId="TUB232" 
                            onkeyup="DateMask(this,null,event)" size="10" disabled ="${PamForm.formFieldMap.TUB232.state.disabled}"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB232','TUB232Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB232','TUB232Icon',event);" 
                            styleId="TUB232Icon"></html:img>
                    
                </td>
            </tr>
            
		    <!-- Reason Therapy Stopped or Never Started-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB233.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB233.state.disabledString}" id= "TUB233L" style="${PamForm.formFieldMap.TUB233.errorStyleClass}" title="${PamForm.formFieldMap.TUB233.tooltip}">${PamForm.formFieldMap.TUB233.label}</span>
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB233.label}" disabled ="${PamForm.formFieldMap.TUB233.state.disabled}" 
                            name="PamForm" property="pamClientVO.answer(TUB233)" styleId = "TUB233" onchange="fireRule('TUB233', this)">
                            <html:optionsCollection property="codedValue(TUB233)" value="key" label="value"/>
                        </html:select>                    
                </td>
            </tr>

			<!-- Indicate cause of  death-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB234.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB234.state.disabledString}" id="TUB234L" style="${PamForm.formFieldMap.TUB234.errorStyleClass}" title="${PamForm.formFieldMap.TUB234.tooltip}">${PamForm.formFieldMap.TUB234.label}</span>
                </td>
                <td>
                   <html:select title="${PamForm.formFieldMap.TUB234.label}" disabled ="${PamForm.formFieldMap.TUB234.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB234)" styleId = "TUB234" onchange="fireRule('TUB234', this)">
                            <html:optionsCollection property="codedValue(TUB234)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            
            <!-- 45. Reason Therapy Extended >12 months:-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB235.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB235.errorStyleClass}" title="${PamForm.formFieldMap.TUB235.tooltip}">${PamForm.formFieldMap.TUB235.label}</span>
                </td>
                <td>
                   
                   <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB235.label}" property="pamClientVO.answerArray(TUB235)" 
                                styleId="TUB235" 
                                disabled ="${PamForm.formFieldMap.TUB235.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB235-selectedValues'); fireRule('TUB235', this);">
                            <!-- FIXME : verify property value passed -->        
                            <html:optionsCollection property="codedValue(TUB235)" value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB235-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>       
                </td>
            </tr>
			<!-- Other Reason Therapy Extended -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB236.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB236.state.disabledString}" id="TUB236L" style="${PamForm.formFieldMap.TUB236.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB236.tooltip}">
                        ${PamForm.formFieldMap.TUB236.label}
                    </span> 
                </td>
                <td>
                     <html:text name="PamForm" property="pamClientVO.answer(TUB236)" styleId="TUB236" onblur="fireRule('TUB236', this)" maxlength="100" 
                            title="${PamForm.formFieldMap.TUB236.label}"/>
                </td>
            </tr>
			<!-- 46. Type of Outpatient Health Care Provider:-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB237.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB237.errorStyleClass}" title="${PamForm.formFieldMap.TUB237.tooltip}">${PamForm.formFieldMap.TUB237.label}</span>
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB237.label}" property="pamClientVO.answerArray(TUB237)" 
                                styleId="TUB237" 
                                disabled ="${PamForm.formFieldMap.TUB237.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB237-selectedValues')" >
                            <!-- FIXME : verify property value passed -->        
                            <html:optionsCollection property="codedValue(TUB237)" value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB237-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>                               
                </td>
            </tr>
			<!-- 47. Directly Observed Therapy(DOT):-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB238.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB238.errorStyleClass}" title="${PamForm.formFieldMap.TUB238.tooltip}">${PamForm.formFieldMap.TUB238.label}</span>
                </td>
                <td>
                   <html:select title="${PamForm.formFieldMap.TUB238.label}" disabled ="${PamForm.formFieldMap.TUB238.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB238)" styleId = "TUB238" onchange="fireRule('TUB238', this)">
                            <html:optionsCollection property="codedValue(TUB238)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!-- Number of weeks of directly observed therapy(DOT):-->
			  <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB239.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB239.state.disabledString}"   id="TUB239L" style="${PamForm.formFieldMap.TUB239.errorStyleClass}"
                     title="${PamForm.formFieldMap.TUB239.tooltip}">${PamForm.formFieldMap.TUB239.label}</span>
                </td>
                <td>              
				 <html:text name="PamForm" property="pamClientVO.answer(TUB239)" maxlength="3" styleId="TUB239" onblur="fireRule('TUB239', this)" disabled ="${PamForm.formFieldMap.TUB239.state.disabled}"  title="${PamForm.formFieldMap.TUB239.label}" onkeyup="isNumericCharacterEntered(this)"/>
                </td>
            </tr>
            <!-- Therapy LDFs -->
            <%= request.getAttribute("1192") == null ? "" :  request.getAttribute("1192") %>				
        </nedss:container>
        
        <!-- SUBSECT : 48. Final Drug Susceptibility Testing: -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="48. Final Drug Susceptibility Testing" classType="subSect" >
            <!-- Was follow up  drug susceptibilty testing done-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB240.state.requiredIndClass}">*</span>
                        <span id="TUB240L" style="${PamForm.formFieldMap.TUB240.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB240.tooltip}">
                            ${PamForm.formFieldMap.TUB240.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB240.label}" disabled ="${PamForm.formFieldMap.TUB240.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB240)" styleId = "TUB240" onchange="fireRule('TUB240', this); handleFollowUp2_DrugTestBtns('TUB240')">
                        <html:optionsCollection property="formFieldMap.TUB240.state.values" value="key" label="value"/>
                    </html:select>
                </td>  
            </tr>	
            
			<!-- Date Final  Isolate Collected -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB241.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB241.state.disabledString}"   id="TUB241L" style="${PamForm.formFieldMap.TUB241.errorStyleClass}"
                     title="${PamForm.formFieldMap.TUB241.tooltip}">${PamForm.formFieldMap.TUB241.label}</span>
                </td>
                <td>
                   <html:text maxlength="10" property="pamClientVO.answer(TUB241)" styleId="TUB241" onblur="fireRule('TUB241', this)" onchange="fireRule('TUB241', this)" disabled ="${PamForm.formFieldMap.TUB241.state.disabled}" onkeyup="DateMask(this,null,event)" size="10" title="${PamForm.formFieldMap.TUB241.label}"/>
                <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB241','fieldDateFinaIsolateCollectedIcon'); return false;" onkeypress ="showCalendarEnterKey('TUB241','fieldDateFinaIsolateCollectedIcon',event);" styleId="fieldDateFinaIsolateCollectedIcon"/>
                </td>
            </tr>
            <!-- Specimen Type is Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB242.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB242.state.disabledString}"  id="TUB242L" style="${PamForm.formFieldMap.TUB242.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB242.tooltip}">
                        ${PamForm.formFieldMap.TUB242.label}
                    </span> 
                </td>
                <td>
                     <html:select title="${PamForm.formFieldMap.TUB242.label}" disabled ="${PamForm.formFieldMap.TUB242.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB242)" styleId="TUB242" 	
                           onchange="fireRule('TUB242', this)">
                        <html:optionsCollection property="codedValue(TUB242)" value="key" label="value"/>
                    </html:select> 
                </td>
            </tr>
            <!-- Specimen Type  not  Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB243.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB243.state.disabledString}"   id="TUB243L" style="${PamForm.formFieldMap.TUB243.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB243.tooltip}">
                        ${PamForm.formFieldMap.TUB243.label}
                    </span> 
                </td>
                <td>
                     <html:select title="${PamForm.formFieldMap.TUB243.label}" disabled ="${PamForm.formFieldMap.TUB243.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB243)" styleId="TUB243" onchange="fireRule('TUB243', this)" 
                            >
                        <html:optionsCollection property="codedValue(TUB243)" value="key" label="value"/>
                    </html:select> 
                </td>
            </tr>
            <!-- 48. Final Drug Susceptibility Testing LDFs -->
            <%= request.getAttribute("1201") == null ? "" :  request.getAttribute("1201") %>
        </nedss:container>
        
        <!-- SUBSECT : 49. Final Drug Susceptibility Results: -->    
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="49. Final Drug Susceptibility Results" classType="subSect" >
			<!-- Standard 4 Susceptiblities -->
            <tr>
               <td class="fieldName">
                   &nbsp;           
                </td>
                <td>
                    <input id="followUp2_StandardSusceptibilities" type="button" class="Button"  
                            value="Standard Susceptiblities (4)" style="width:15em;"  
                            onclick="populateFinalSusceptibility('followUp2_MarkRestNotDone');"/>
                </td>
            </tr>
			 <!-- Mark Rest Not Done -->
            <tr>
               <td class="fieldName">
                    &nbsp; 
                </td>
                <td>
                    <input id="followUp2_MarkRestNotDone" type="button" class="Button" 
                            value="Mark Rest 'Not Done'" style="width:15em;"  
                            onclick="populateFinalMarkRestNo('<%= tabId + (subSectionIndex) %>');"/>
                </td>
            </tr>
	        <!-- Isoniazid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB244.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB244.state.disabledString}"  id="TUB244L"  style="${PamForm.formFieldMap.TUB244.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB244.tooltip}">
                            ${PamForm.formFieldMap.TUB244.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB244.label}" disabled ="${PamForm.formFieldMap.TUB244.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB244)" styleId = "TUB244" onchange="fireRule('TUB244', this)">
                            <html:optionsCollection property="codedValue(TUB244)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!-- Rifampin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB245.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB245.state.disabledString}" id="TUB245L" style="${PamForm.formFieldMap.TUB245.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB245.tooltip}">
                            ${PamForm.formFieldMap.TUB245.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB245.label}" disabled ="${PamForm.formFieldMap.TUB245.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB245)" styleId = "TUB245" onchange="fireRule('TUB245', this)">
                            <html:optionsCollection property="codedValue(TUB245)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!--Pyrazinamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB246.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB246.state.disabledString}" id="TUB246L" style="${PamForm.formFieldMap.TUB246.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB246.tooltip}">
                            ${PamForm.formFieldMap.TUB246.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB246.label}" disabled ="${PamForm.formFieldMap.TUB246.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB246)" styleId = "TUB246" onchange="fireRule('TUB246', this)">
                            <html:optionsCollection property="codedValue(TUB246)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!--Ehtambutol-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB247.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB247.state.disabledString}" id="TUB247L" style="${PamForm.formFieldMap.TUB247.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB247.tooltip}">
                            ${PamForm.formFieldMap.TUB247.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB247.label}" disabled ="${PamForm.formFieldMap.TUB247.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB247)" styleId = "TUB247" onchange="fireRule('TUB247', this)">
                            <html:optionsCollection property="codedValue(TUB247)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!--Streptomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB248.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB248.state.disabledString}" id="TUB248L"  style="${PamForm.formFieldMap.TUB248.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB248.tooltip}">
                            ${PamForm.formFieldMap.TUB248.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB248.label}" disabled ="${PamForm.formFieldMap.TUB248.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB248)" styleId = "TUB248" onchange="fireRule('TUB248', this)">
                            <html:optionsCollection property="codedValue(TUB248)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!--Rifabutin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB255.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB255.state.disabledString}"  id="TUB255L"  style="${PamForm.formFieldMap.TUB255.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB255.tooltip}">
                            ${PamForm.formFieldMap.TUB255.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB255.label}" disabled ="${PamForm.formFieldMap.TUB255.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB255)" styleId = "TUB255" onchange="fireRule('TUB255', this)">
                            <html:optionsCollection property="codedValue(TUB255)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Rifapentine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB258.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB258.state.disabledString}" id="TUB258L" style="${PamForm.formFieldMap.TUB258.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB258.tooltip}">
                            ${PamForm.formFieldMap.TUB258.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB258.label}" disabled ="${PamForm.formFieldMap.TUB258.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB258)" styleId = "TUB258" onchange="fireRule('TUB258', this)">
                            <html:optionsCollection property="codedValue(TUB258)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!--Ethionamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB249.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB249.state.disabledString}"  id="TUB249L" style="${PamForm.formFieldMap.TUB249.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB249.tooltip}">
                            ${PamForm.formFieldMap.TUB249.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB249.label}" disabled ="${PamForm.formFieldMap.TUB249.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB249)" styleId = "TUB249" onchange="fireRule('TUB249', this)">
                            <html:optionsCollection property="codedValue(TUB249)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Amikacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB254.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB254.state.disabledString}" id="TUB254L" style="${PamForm.formFieldMap.TUB254.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB254.tooltip}">
                            ${PamForm.formFieldMap.TUB254.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB254.label}" disabled ="${PamForm.formFieldMap.TUB254.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB254)" styleId = "TUB254" onchange="fireRule('TUB254', this)">
                            <html:optionsCollection property="codedValue(TUB254)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!--Kanamycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB250.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB250.state.disabledString}"  id="TUB250L" style="${PamForm.formFieldMap.TUB250.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB250.tooltip}">
                            ${PamForm.formFieldMap.TUB250.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB250.label}" disabled ="${PamForm.formFieldMap.TUB250.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB250)" styleId = "TUB250" onchange="fireRule('TUB250', this)">
                            <html:optionsCollection property="codedValue(TUB250)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Capreomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB252.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB252.state.disabledString}"  id="TUB252L" style="${PamForm.formFieldMap.TUB252.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB252.tooltip}">
                            ${PamForm.formFieldMap.TUB252.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB252.label}" disabled ="${PamForm.formFieldMap.TUB252.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB252)" styleId = "TUB252" onchange="fireRule('TUB252', this)">
                            <html:optionsCollection property="codedValue(TUB252)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Ciprofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB256.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB256.state.disabledString}" id="TUB256L" style="${PamForm.formFieldMap.TUB256.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB256.tooltip}">
                            ${PamForm.formFieldMap.TUB256.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB256.label}" disabled ="${PamForm.formFieldMap.TUB256.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB256)" styleId = "TUB256" onchange="fireRule('TUB256', this)">
                            <html:optionsCollection property="codedValue(TUB256)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Levofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB259.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB259.state.disabledString}" id="TUB259L"  style="${PamForm.formFieldMap.TUB259.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB259.tooltip}">
                            ${PamForm.formFieldMap.TUB259.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB259.label}" disabled ="${PamForm.formFieldMap.TUB259.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB259)" styleId = "TUB259" onchange="fireRule('TUB259', this)">
                            <html:optionsCollection property="codedValue(TUB259)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Ofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB257.state.requiredIndClass}">*</span>
                        <span   class="${PamForm.formFieldMap.TUB257.state.disabledString}" id="TUB257L"  style="${PamForm.formFieldMap.TUB257.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB257.tooltip}">
                            ${PamForm.formFieldMap.TUB257.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB257.label}" disabled ="${PamForm.formFieldMap.TUB257.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB257)" styleId = "TUB257" onchange="fireRule('TUB257', this)">
                            <html:optionsCollection property="codedValue(TUB257)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Moxifloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB260.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB260.state.disabledString}"  id="TUB260L" style="${PamForm.formFieldMap.TUB260.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB260.tooltip}">
                            ${PamForm.formFieldMap.TUB260.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB260.label}" disabled ="${PamForm.formFieldMap.TUB260.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB260)" styleId = "TUB260" onchange="fireRule('TUB260', this)">
                            <html:optionsCollection property="codedValue(TUB260)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Other Quinolones-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB261.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB261.state.disabledString}"  id="TUB261L" style="${PamForm.formFieldMap.TUB261.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB261.tooltip}">
                            ${PamForm.formFieldMap.TUB261.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB261.label}" disabled ="${PamForm.formFieldMap.TUB261.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB261)" styleId = "TUB261" onchange="fireRule('TUB261', this)">
                            <html:optionsCollection property="codedValue(TUB261)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Cycloserine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB251.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB251.state.disabledString}" id="TUB251L" style="${PamForm.formFieldMap.TUB251.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB251.tooltip}">
                            ${PamForm.formFieldMap.TUB251.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB251.label}" disabled ="${PamForm.formFieldMap.TUB251.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB251)" styleId = "TUB251" onchange="fireRule('TUB251', this)">
                            <html:optionsCollection property="codedValue(TUB251)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!-- Para-Amino Salicylic Acid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB253.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB253.state.disabledString}" id="TUB253L" style="${PamForm.formFieldMap.TUB253.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB253.tooltip}">
                            ${PamForm.formFieldMap.TUB253.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB253.label}" disabled ="${PamForm.formFieldMap.TUB253.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB253)" styleId = "TUB253" onchange="fireRule('TUB253', this)">
                            <html:optionsCollection property="codedValue(TUB253)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
            <!-- Other Drug -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB262.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB262.state.disabledString}" id="TUB262L" style="${PamForm.formFieldMap.TUB262.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB262.tooltip}">
                            ${PamForm.formFieldMap.TUB262.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB262.label}" disabled ="${PamForm.formFieldMap.TUB262.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB262)" styleId = "TUB262"  onchange="fireRule('TUB262', this)">
                            <html:optionsCollection property="codedValue(TUB262)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			 <!-- Specify Other Drug -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB263.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB263.state.disabledString}" id="TUB263L" style="${PamForm.formFieldMap.TUB263.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB263.tooltip}">
                            ${PamForm.formFieldMap.TUB263.label}
                        </span> 
                </td>
                <td>
                    <html:text name="PamForm" disabled ="${PamForm.formFieldMap.TUB263.state.disabled}" 
                            property="pamClientVO.answer(TUB263)" maxlength="100" styleId = "TUB263" onblur="fireRule('TUB263', this)"
                            title="${PamForm.formFieldMap.TUB263.label}"/>
                </td>
            </tr>
			<!-- Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB264.state.requiredIndClass}">*</span>
                        <span class="${PamForm.formFieldMap.TUB264.state.disabledString}" id="TUB264L" style="${PamForm.formFieldMap.TUB264.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB264.tooltip}">
                            ${PamForm.formFieldMap.TUB264.label}
                        </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB264.label}" disabled ="${PamForm.formFieldMap.TUB264.state.disabled}" name="PamForm" property="pamClientVO.answer(TUB264)" styleId = "TUB264" onchange="fireRule('TUB264', this)">
                            <html:optionsCollection property="codedValue(TUB264)" value="key" label="value"/>
                        </html:select>
                </td>
            </tr>
			<!-- Specify Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB265.state.requiredIndClass}">*</span>
                        <span  class="${PamForm.formFieldMap.TUB265.state.disabledString}"  id= "TUB265L" style="${PamForm.formFieldMap.TUB265.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB265.tooltip}">
                            ${PamForm.formFieldMap.TUB265.label}
                        </span> 
                </td>
                <td>
                 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.TUB265.state.disabled}" 
                        property="pamClientVO.answer(TUB265)" 
                        maxlength="100" styleId = "TUB265" onblur="fireRule('TUB265', this)" title="${PamForm.formFieldMap.TUB265.label}"/>
                </td>
            </tr>
			<!--Clear -->
            <tr>
               <td class="fieldName">
                    &nbsp;                
                </td>
                <td>
                    <input id="followUp2_Clear" type="button" class="Button" value="Clear" onclick="clearAll('<%= tabId + (subSectionIndex) %>');"/>
                </td>
            </tr>
            <!-- 49. Final Drug Susceptibility Results: LDFs -->
            <%= request.getAttribute("1206") == null ? "" :  request.getAttribute("1206") %>
        </nedss:container>

        <!-- SUBSECT : Follow Up 2 Comments: -->    
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Follow Up 2 Comments" classType="subSect" >			
			<!-- Comments-->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB271.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB271.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB271.tooltip}">${PamForm.formFieldMap.TUB271.label}                    
                </td>
                <td>
                    <html:textarea title="${PamForm.formFieldMap.TUB271.label}" style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${PamForm.formFieldMap.TUB271.state.disabled}" 
                             onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"  name="PamForm" property="pamClientVO.answer(TUB271)"/>
                </td>
            </tr>
            <!-- Follow Up 2 Comments: LDFs -->
            <%= request.getAttribute("1229") == null ? "" :  request.getAttribute("1229") %>
        </nedss:container>   
    </nedss:container>
    
    <!-- SECTION : Local Fields -->
    <% if(request.getAttribute("1333") != null) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
                <%= request.getAttribute("1333") == null ? "" :  request.getAttribute("1333") %>
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