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
    String tabId = "editTuberculosis";
    String []sectionNames = {"Investigation Information", "Reporting Information", "Patient History",
                                   "Clinical Information", "Laboratory Information", "Risk Factors",
                                    "Treatment", "Epidemiologic Information", "Investigation Comments", "Custom Fields"};
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
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <span class='${PamForm.formFieldMap["1362"].state.visibleString}'>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    </span>
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
            
    <!-- SECTION : Investigation Information --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Investigation Details -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation Details" classType="subSect" >
            <!-- Jurisdiction -->
            <logic:empty name="PamForm" property="attributeMap.ReadOnlyJursdiction">
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.INV107.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV107.errorStyleClass}" 
                                title="${PamForm.formFieldMap.INV167.label}">
                            ${PamForm.formFieldMap.INV107.label}
                        </span> 
                    </td>
                    <td>
                        <html:select title="${PamForm.formFieldMap.INV167.label}" disabled ="${PamForm.formFieldMap.INV107.state.disabled}" name="PamForm" property="pamClientVO.answer(INV107)" styleId = "INV107">
                            <html:optionsCollection property="jurisdictionListNoExport" value="key" label="value"/>
                        </html:select>
                        <html:hidden property="attributeMap.NBSSecurityJurisdictions" 
                                styleId="NBSSecurityJurisdictions"/>
                    </td>
                </tr>
            </logic:empty>
            <logic:notEmpty name="PamForm" property="attributeMap.ReadOnlyJursdiction">
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.INV107.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV107.errorStyleClass}"  
                            title="${PamForm.formFieldMap.INV107.tooltip}">
                            ${PamForm.formFieldMap.INV107.label}
                        </span> 
                    </td>
                    <td>
                         <nedss:view name="PamForm" property="pamClientVO.answer(INV107)" 
                                codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
                         <html:hidden name="PamForm" property="pamClientVO.answer(INV107)"/>
                    </td>
                </tr>
            </logic:notEmpty>
            <!-- Program Area -->
            <tr>
                <td class="fieldName"> 
                    <span title="${PamForm.formFieldMap.INV108.tooltip}">
                        ${PamForm.formFieldMap.INV108.label}</span>
                </td>
                <td>
                    <html:hidden property="pamClientVO.answer(INV108)"/>
                    <%--Needs to Fix this with new form --%>
                    <nedss:view name="PamForm" property="pamClientVO.answer(INV108)" 
                            codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/> 
                </td>
            </tr>
            <!-- Share Indicator -->
            <tr>
                <td  class="${PamForm.formFieldMap.INV174.state.visibleString}">
                    <span style="${PamForm.formFieldMap.INV174.errorStyleClass}"  
                            title="${PamForm.formFieldMap.INV174.tooltip}">${PamForm.formFieldMap.INV174.label}</span>
                </td>
                <td>
                    <html:checkbox disabled ="${PamForm.formFieldMap.INV174.state.disabled}" name="PamForm" property="pamClientVO.answer(INV174)" value="1" 
                            title="${PamForm.formFieldMap.INV174.tooltip}"></html:checkbox>
                </td>
            </tr>
            <!-- Investigation Status -->
            <tr>
                <td class="${PamForm.formFieldMap.INV109.state.visibleString}">
                    <span style="${PamForm.formFieldMap.INV109.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV109.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV109.tooltip}">
                        ${PamForm.formFieldMap.INV109.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.INV109.label}" disabled ="${PamForm.formFieldMap.INV109.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(INV109)" styleId="INV109">
                        <html:optionsCollection property="codedValueNoBlnk(INV109)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Investigation Start Date -->
            <tr>
                <td class="fieldName" >
                    <span style="${PamForm.formFieldMap.INV147.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV147.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV147.tooltip}">
                        ${PamForm.formFieldMap.INV147.label}
                    </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.INV174.state.disabled}" 
                            name="PamForm" property="pamClientVO.answer(INV147)" maxlength="10" 
                            title="${PamForm.formFieldMap.INV147.label}" styleId="INV147" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV147','INV147Icon'); return false;" onkeypress ="showCalendarEnterKey('INV147','INV147Icon',event);" 
                            styleId="INV147Icon"></html:img>
                </td>
            </tr>
	         <!-- Investigation Details LDFs -->
	         <%= request.getAttribute("1287") == null ? "" :  request.getAttribute("1287") %>
        </nedss:container>

        <!-- SUB_SECTION : Investigator -->
        <div>
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigator" classType="subSect" >                
            <!-- Investigator -->
	            <logic:empty name="PamForm" property="attributeMap.INV207Uid">                
		            <tr>
		                <td class="fieldName"> 
			                <span style="${PamForm.formFieldMap.INV207.state.requiredIndClass}">*</span>
			                <span style="${PamForm.formFieldMap.INV207.errorStyleClass}"
			                        title="${PamForm.formFieldMap.INV207.tooltip}">${PamForm.formFieldMap.INV207.label}</span>  
			            </td>	
		                <td>
		                    <span id="clearINV207" class="none">        
		                        <input type="button" class="Button" value="Clear/Reassign" 
		                                id="INV207CodeClearButton" onclick="clearProvider('INV207')"/>
		                    </span>   
		                    
		                    <span id="INV207SearchControls">
		                       <input type="button" class="Button" value="Search" 
		                                id="INV207Icon" onclick="getProvider('INV207');" />
		                        &nbsp; - OR - &nbsp;
		                        <html:text property="pamClientVO.answer(INV207)" styleId="INV207Text"
		                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV207Text','qec_list')" title="${PamForm.formFieldMap.INV207.label}"/>
		                        <input type="button" class="Button" value="Quick Code Lookup" 
		                            id="INV207CodeLookupButton" onclick="getDWRProvider('INV207')"/>                                
		                        <div class="page_name_auto_complete" id="qec_list" style="background:#DCDCDC"></div>
		                    </span>
		                </td>
		            </tr>
		            <tr>
		                <td class="fieldName"> Investigator Selected:  </td>
		                <td>
		                    <span id="test2">
		                        <html:hidden property="attributeMap.INV207Uid"/>
		                        <span id="INV207">${PamForm.attributeMap.INV207SearchResult}</span>
		                    </span>
		                </td>
		            </tr>
		       </logic:empty>
		       <logic:notEmpty name="PamForm" property="attributeMap.INV207Uid">                
		           <tr>
		               <td class="fieldName"> 
			               <span style="${PamForm.formFieldMap.INV207.state.requiredIndClass}">*</span>
			               <span style="${PamForm.formFieldMap.INV207.errorStyleClass}"
			                       title="${PamForm.formFieldMap.INV207.tooltip}">${PamForm.formFieldMap.INV207.label}</span>  
			           </td>	
		                <td>
		                    <span id="clearINV207">        
		                        <input type="button" class="Button" value="Clear/Reassign" 
		                                id="INV207CodeClearButton" onclick="clearProvider('INV207')"/>
		                    </span>
		                    
		                    <span id="INV207SearchControls" class="none">
		                       <input type="button" class="Button" value="Search" 
		                                id="INV207Icon" onclick="getProvider('INV207');" />
		                        &nbsp; - OR - &nbsp;
		                        <html:text property="pamClientVO.answer(INV207)" styleId="INV207Text"
		                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV207Text','qec_list')" title="${PamForm.formFieldMap.INV207.label}" 
		                                />
		                        <input type="button" class="Button" value="Quick Code Lookup" 
		                            id="INV207CodeLookupButton" onclick="getDWRProvider('INV207')" 
		                            style="visibility:hidden"/>                                
		                        <div class="page_name_auto_complete" id="qec_list" style="background:#DCDCDC"></div>
		                    </span>
		                </td>
		            </tr>
		            <tr>
		                <td class="fieldName"> Investigator Selected:  </td>
		                <td>
		                    <!--  <span class="none test2"> -->
		                    <span class="test2">
		                        <html:hidden property="attributeMap.INV207Uid"/>
		                        <span id="INV207">${PamForm.attributeMap.INV207SearchResult}</span>
		                    </span>
		                </td>
		            </tr>
		        </logic:notEmpty>
		        <!-- Invstigator search error. Not defined in the metadata -->
                <tr>
                    <td colspan="2" style="text-align:center;">
                        <span id="INV207Error"/></td>
                    </td>
                </tr>
                
                <!-- Date Assigned to Investigation -->
		        <logic:equal name="PamForm" property="attributeMap.INV207SearchResult" value="">   
		            <tr>
		               <td class="fieldName">
		                    <span style="${PamForm.formFieldMap.INV110.state.requiredIndClass}">*</span>
		                    <span id="INV110L" class="InputDisabledLabel" 
		                            title="${PamForm.formFieldMap.INV110.tooltip}">
		                        ${PamForm.formFieldMap.INV110.label}
		                    </span> 
		                </td>
		                <td>
		                    <html:text disabled="true" 
		                            name="PamForm" property="pamClientVO.answer(INV110)" maxlength="10" 
		                            title="${PamForm.formFieldMap.INV110.label}" styleId="INV110" value="" onkeyup="DateMask(this,null,event)" size="10"/>
		                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon'); return false;" onkeypress ="showCalendarEnterKey('INV110','INV110Icon',event);" 
		                            styleId="INV110Icon"></html:img>
		                </td>
		            </tr>
		        </logic:equal>
		        
		        <logic:notEqual name="PamForm" property="attributeMap.INV207SearchResult" value="">
		            <tr>
		               <td  class="${PamForm.formFieldMap.INV110.state.visibleString}">
		                    <span style="${PamForm.formFieldMap.INV110.state.requiredIndClass}">*</span>
		                    <span id="INV110L" title="${PamForm.formFieldMap.INV110.tooltip}">
		                        ${PamForm.formFieldMap.INV110.label}
		                    </span> 
		                </td>
		                <td>
		                    <html:text disabled ="${PamForm.formFieldMap.INV110.state.disabled}" 
		                            name="PamForm" property="pamClientVO.answer(INV110)" maxlength="10" 
		                            title="${PamForm.formFieldMap.INV110.label}" styleId="INV110" onkeyup="DateMask(this,null,event)" size="10"/>
		                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon'); return false;" onkeypress="showCalendarEnterKey('INV110','INV110Icon',event);" 
		                            styleId="INV110Icon"></html:img>
		                </td>
		            </tr>
		        </logic:notEqual>
 
	         <!-- Investigator LDFs -->
	         <%= request.getAttribute("1321") == null ? "" :  request.getAttribute("1321") %>            
        </nedss:container>  
        </nedss:container>    
       </div>  
        
    <!-- SECTION : Reporting Information -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Investigation Details -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Key Report Dates" classType="subSect" >
            <!-- Date Reported -->
            <tr>
                <td  class="fieldName" >
                    <span style="${PamForm.formFieldMap.INV111.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV111.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV111.tooltip}">
                        ${PamForm.formFieldMap.INV111.label}
                    </span> 
                </td>
                <td class="topBorder">
                    <html:text name="PamForm" disabled ="${PamForm.formFieldMap.INV111.state.disabled}" 
                            property="pamClientVO.answer(INV111)" maxlength="10" 
                            title="${PamForm.formFieldMap.INV111.label}" styleId="INV111" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV111','INV111Icon'); return false;" onkeypress ="showCalendarEnterKey('INV111','INV111Icon',event);" 
                            styleId="INV111Icon"></html:img>
                </td>
            </tr>
            <!-- Date Submitted -->
            <tr>
                <td  class="fieldName">
                    <span style="${PamForm.formFieldMap.INV121.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV121.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV121.tooltip}">
                        ${PamForm.formFieldMap.INV121.label}
                    </span> 
                </td>
                <td class="topBorder">
                    <html:text disabled ="${PamForm.formFieldMap.INV121.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(INV121)" title="${PamForm.formFieldMap.INV121.label}" maxlength="10" 
                            styleId="INV121" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV121','INV121Icon'); return false;" onkeypress ="showCalendarEnterKey('INV121','INV121Icon',event);" 
                            styleId="INV121Icon"></html:img>
                </td>
            </tr>
		 <!-- Earliest Date Reported to County-->
		 <tr class="${PamForm.formFieldMap.INV120.state.visibleString}">
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.INV120.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV120.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV120.tooltip}">${PamForm.formFieldMap.INV120.label}</span>  
		     </td>
	         <td class="topBorder">
	             <html:text name="PamForm" disabled ="${PamForm.formFieldMap.INV120.state.disabled}" 
	                     property="pamClientVO.answer(INV120)" title="${PamForm.formFieldMap.INV120.label}" maxlength="10" 
	                     styleId="INV120" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV120','INV120Icon'); return false;" onkeypress ="showCalendarEnterKey('INV120','INV120Icon',event);" 
	                     styleId="INV120Icon"></html:img>
	         </td>
		 </tr>
            
	         <!--Key Report Dates LDFs -->
	         <%= request.getAttribute("1004") == null ? "" :  request.getAttribute("1004") %>            
        </nedss:container>
        <!-- SUB_SECTION : 3. Case Numbers -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="3. Case Numbers" classType="subSect" >
            <!-- State Case Number -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV173.state.requiredIndClass}">*</span>
                    <span  id="INV173L" style="${PamForm.formFieldMap.INV173.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV173.tooltip}">
                        ${PamForm.formFieldMap.INV173.label}
                    </span> 
                </td>
                <td>
                     <html:text disabled ="${PamForm.formFieldMap.INV173.state.disabled}"
                            property="pamClientVO.answer(INV173)" styleId="INV173"
                            title="${PamForm.formFieldMap.INV173.label}" maxlength="17" size="17"
                            onkeyup="CaseNumberMask(this)"   
                            onblur="FormatCaseNumberMask(this)" />
                     (example : 2008-OK-ABCD56789)
                </td>
            </tr>
            <!-- City/County Case Number -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV198.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV198.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV198.tooltip}">
                        ${PamForm.formFieldMap.INV198.label}
                    </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.INV198.state.disabled}"
                            property="pamClientVO.answer(INV198)" styleId="INV198"
                            title="${PamForm.formFieldMap.INV198.label}" maxlength="17" size="17"
                            onkeyup="CaseNumberMask(this)" 
                            onblur="FormatCaseNumberMask(this)" />
                    (example : 2008-OK-ABCD56789)                            
                </td>
            </tr>
            <!-- Linking State Case Number 1 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB100.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB100.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB100.tooltip}">
                        ${PamForm.formFieldMap.TUB100.label}
                    </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB100.state.disabled}"
                            size="17"
                            property="pamClientVO.answer(TUB100)" styleId="TUB100" 
                            title="${PamForm.formFieldMap.TUB100.label}" maxlength="17"
                            onkeyup="CaseNumberMask(this)"
                            onkeydown="fireRuleAndChangeFocusOnTabKey('TUB100', this);"
                            onblur="FormatCaseNumberMask(this); fireRule('TUB100', this, false);" />
                    (example: 2008-OK-ABCD56789)                            
                </td>
            </tr>
            <!-- Link Reason 1 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB101.state.requiredIndClass}">*</span>
                    <span   class="${PamForm.formFieldMap.TUB101.state.disabledString}" 
                        id="TUB101L" style="${PamForm.formFieldMap.TUB101.errorStyleClass}" 
                        title="${PamForm.formFieldMap.TUB101.tooltip}">
                        ${PamForm.formFieldMap.TUB101.label}
                    </span> 
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB101.label}" disabled ="${PamForm.formFieldMap.TUB101.state.disabled}" 
                            name="PamForm" property="pamClientVO.answer(TUB101)" styleId="TUB101" onchange = "fireRule('TUB101', this);">
                        <html:optionsCollection property="codedValue(TUB101)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Linking State Case Number 2 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB102.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB102.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB102.tooltip}">
                        ${PamForm.formFieldMap.TUB102.label}
                    </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB102.state.disabled}"
                            property="pamClientVO.answer(TUB102)" styleId="TUB102" 
                            title="${PamForm.formFieldMap.TUB102.label}" maxlength="17" size="17"
                            onkeyup="CaseNumberMask(this);"
                            onblur="FormatCaseNumberMask(this); fireRule('TUB102', this, false);" />
                           (example : 2008-OK-ABCD56789)                            
                </td>
            </tr>
            <!-- Link Reason 2 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB103.state.requiredIndClass}">*</span>
                    <span   class="${PamForm.formFieldMap.TUB103.state.disabledString}" 
			  id="TUB103L" style="${PamForm.formFieldMap.TUB103.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB103.tooltip}">
                        ${PamForm.formFieldMap.TUB103.label}
                    </span> 
                </td>
                <td>
                    <html:select disabled ="${PamForm.formFieldMap.TUB103.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB103)" styleId="TUB103" onchange = "fireRule('TUB103', this);"  
                            title="${PamForm.formFieldMap.TUB103.label}">
                        <html:optionsCollection property="codedValue(TUB103)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Case Numbers LDFs -->
	         <%= request.getAttribute("1005") == null ? "" :  request.getAttribute("1005") %>            
        </nedss:container>
	<!-- SUBSECTION : Reporting Organization: --> 
	  <div class='${PamForm.formFieldMap["1340"].state.visibleString}'>
		<%@ include file="/pam/ext/ReportingOrganization.jsp" %>        
	</div>	
	<!-- SUBSECTION : Reporting Provider: --> 
	  <div class='${PamForm.formFieldMap["1343"].state.visibleString}'>
		<%@ include file="/pam/ext/ReportingProvider.jsp" %>
	</div>
    </nedss:container>
    
    <!-- SECTION : Patient History -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Previous Diagnosis -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Previous Diagnosis" classType="subSect" >
            <!-- Previous Diagnosis of TB Disease -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB111.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB111.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB111.tooltip}">${PamForm.formFieldMap.TUB111.label}</span>
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.INV111.label}" disabled ="${PamForm.formFieldMap.TUB111.state.disabled}"
                            property="pamClientVO.answer(TUB111)" styleId="TUB111" onchange="fireRule('TUB111', this)">
                        <html:optionsCollection property="codedValue(TUB111)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Year of Previous Diagnosis -->  
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB112.state.requiredIndClass}">*</span>
                    <span id="TUB112L" style="${PamForm.formFieldMap.TUB112.errorStyleClass}"
                            class="${PamForm.formFieldMap.TUB112.state.disabledString}" 
                            title="${PamForm.formFieldMap.TUB112.tooltip}">${PamForm.formFieldMap.TUB112.label}</span>
                </td>
                <td>
                    <html:text maxlength="4" property="pamClientVO.answer(TUB112)" styleId="TUB112" onblur="fireRule('TUB112', this)"
                            disabled ="${PamForm.formFieldMap.TUB112.state.disabled}" 
                            onkeyup="isNumericCharacterEntered(this); YearMask(this);" 
                            size="4" title="${PamForm.formFieldMap.TUB112.label}"/>                        
                </td>
            </tr>
	         <!--Previous Diagnosis LDFs -->
	         <%= request.getAttribute("1032") == null ? "" :  request.getAttribute("1032") %>            
        </nedss:container>
        <!-- SUB_SECTION : Origin -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Origin" classType="subSect" >
            <!-- "US-born" (or born abroad to a parent who was a U.S Citizen) --> 
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB277.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB277.errorStyleClass}"
                            title="${PamForm.formFieldMap.TUB277.tooltip}">${PamForm.formFieldMap.TUB277.label}                    
                </td>
                <td>
                    <html:select disabled ="${PamForm.formFieldMap.TUB277.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB277)" styleId="TUB277" 
                            title="${PamForm.formFieldMap.TUB277.label}">
                        <html:optionsCollection property="codedValue(TUB277)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
           <!-- Country of Birth --> 
           <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB276.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB276.errorStyleClass}"
                            title="${PamForm.formFieldMap.TUB276.tooltip}">${PamForm.formFieldMap.TUB276.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB276.label}" disabled ="${PamForm.formFieldMap.TUB276.state.disabled}"
                            property="pamClientVO.answer(TUB276)" styleId="TUB276" onchange="fireRule('TUB276', this)">
                        <html:optionsCollection property="codedValue(TUB276)" value="key" label="value" />
                    </html:select>
                </td>
            </tr>
            <!-- Month-Year Arrived in the US -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB273.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB273.state.disabledString}" 
				 id="TUB273L" style="${PamForm.formFieldMap.TUB273.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB273.tooltip}">${PamForm.formFieldMap.TUB273.label}</span>                        
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB273.state.disabled}"
                            name="PamForm" title="${PamForm.formFieldMap.TUB273.label}" property="pamClientVO.answer(TUB273)" maxlength="10" 
                            styleId="TUB273" onblur="fireRule('TUB273', this)" onchange="fireRule('TUB273', this)"  onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB273','TUB273Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB273','TUB273Icon',event);" 
                            styleId="TUB273Icon"></html:img>                            
                </td>
            </tr>
	         <!-- Origin LDFs -->
	         <%= request.getAttribute("1034") == null ? "" :  request.getAttribute("1034") %>            
        </nedss:container>
        <!-- SUB_SECTION : 14. Pediatric TB Patients (<15 years old) -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="14. Pediatric TB Patients (<15 years old)" classType="subSect" >
            <!-- Primary Guardian 1 Birth Country -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB115.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB115.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB115.tooltip}">${PamForm.formFieldMap.TUB115.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB115.label}" disabled ="${PamForm.formFieldMap.TUB115.state.disabled}"
                            property="pamClientVO.answer(TUB115)" styleId="TUB115">
                        <html:optionsCollection property="codedValue(TUB115)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Primary Guardian 2 Birth Country -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB116.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB116.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB116.tooltip}">${PamForm.formFieldMap.TUB116.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB116.label}" disabled ="${PamForm.formFieldMap.TUB116.state.disabled}"
                            property="pamClientVO.answer(TUB116)" styleId="TUB116">
                        <html:optionsCollection property="codedValue(TUB116)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Patient lived outside of US for more than 2 months : --> 
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB113.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB113.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB113.tooltip}">${PamForm.formFieldMap.TUB113.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB113.label}" disabled ="${PamForm.formFieldMap.TUB113.state.disabled}"
                            property="pamClientVO.answer(TUB113)" styleId="TUB113"  onchange="fireRule('TUB113', this,false)">
                         <html:optionsCollection property="codedValue(TUB113)" value="key" label="value" />
                    </html:select>
                </td>
            </tr>
            <!-- Countries -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB114.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB114.state.disabledString}" 
                            id="TUB114L" style="${PamForm.formFieldMap.TUB114.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB114.tooltip}">${PamForm.formFieldMap.TUB114.label}                    
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB114.label}" property="pamClientVO.answerArray(TUB114)" 
                                styleId="TUB114" 
                                disabled ="${PamForm.formFieldMap.TUB114.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB114-selectedValues');fireRule('TUB114', this,false)" >
                            <html:optionsCollection property="codedValue(TUB114)" value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB114-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>
                </td>
             </tr>
	         <!-- Pediatric TB Patients LDFs -->
	         <%= request.getAttribute("1036") == null ? "" :  request.getAttribute("1036") %>            
        </nedss:container>
    </nedss:container>
    
    <!-- SECTION : Clinical Information -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
<!-- SUBSECTION: Physician -->	
    <div class='${PamForm.formFieldMap["1345"].state.visibleString}'>
	<%@ include file="/pam/ext/Physician.jsp" %>
    </div>
	
<!-- SUBSECTION: Hospital -->	
    <div class='${PamForm.formFieldMap["1347"].state.visibleString}'>
	<%@ include file="/pam/ext/Hospital.jsp" %>
	</div>
<!-- SUBSECTION: Condition -->	
    <div class='${PamForm.formFieldMap["1353"].state.visibleString}'>
	<%@ include file="/pam/ext/Condition.jsp" %>
   </div>	

        <!-- SUB_SECTION : 15. Status at TB Diagnosis -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="15. Status at TB Diagnosis" classType="subSect" >
           <!-- Patient Status at Diagnosis --> 
           <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB117.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB117.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB117.tooltip}">${PamForm.formFieldMap.TUB117.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB117.label}" disabled ="${PamForm.formFieldMap.TUB117.state.disabled}"
                            property="pamClientVO.answer(TUB117)" styleId="TUB117" onchange="fireRule('TUB117', this)">
                         <html:optionsCollection property="codedValue(TUB117)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Date of Death -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV146.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.INV146.state.disabledString}" id="INV146L" style="${PamForm.formFieldMap.INV146.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV146.tooltip}">${PamForm.formFieldMap.INV146.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.INV146.state.disabled}"
                            name="PamForm" title="${PamForm.formFieldMap.INV146.label}" property="pamClientVO.answer(INV146)" maxlength="10" 
                            styleId="INV146" onblur="fireRule('INV146', this)"  onchange="fireRule('INV146', this)" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV146','INV146Icon'); return false;"  onkeypress ="showCalendarEnterKey('INV146','INV146Icon',event);" 
                            styleId="INV146Icon"></html:img>
                </td>
            </tr>
            <!-- Was TB a cause of death -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV145.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.INV145.state.disabledString}" 
   				id="INV145L" style="${PamForm.formFieldMap.INV145.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV145.tooltip}">${PamForm.formFieldMap.INV145.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.INV145.label}" disabled ="${PamForm.formFieldMap.INV145.state.disabled}"
                            property="pamClientVO.answer(INV145)" styleId="INV145" onchange="fireRule('INV145', this)">
                         <html:optionsCollection property="codedValue(INV145)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Status at TB Diagnosis LDFs -->
	         <%= request.getAttribute("1041") == null ? "" :  request.getAttribute("1041") %>              
        </nedss:container>
        <!-- SUB_SECTION : 16. Site of TB Disease -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="16. Site of TB Disease" classType="subSect" >
            <!-- Site(s) of Disease -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB119.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB119.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB119.tooltip}">${PamForm.formFieldMap.TUB119.label}</span>
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB119.label}" property="pamClientVO.answerArray(TUB119)" 
                                styleId="TUB119" 
                                disabled ="${PamForm.formFieldMap.TUB119.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB119-selectedValues'); fireRule('TUB119', this);">
                            <html:optionsCollection property="codedValue(TUB119)" value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB119-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>
                </td>
            </tr>
             <!-- Site of TB Disease LDFs -->
             <%= request.getAttribute("1046") == null ? "" :  request.getAttribute("1046") %>                  
        </nedss:container>
    </nedss:container>
    
    <!-- SECTION : Laboratory Information -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : 17. Sputum Smear -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="17. Sputum Smear" classType="subSect" >
            <!-- Result -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB120.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB120.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB120.tooltip}">${PamForm.formFieldMap.TUB120.label}</span>
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB120.label}" disabled ="${PamForm.formFieldMap.TUB120.state.disabled}"
                            property="pamClientVO.answer(TUB120)" styleId="TUB120" onchange="fireRule('TUB120', this)">
                        <html:optionsCollection property="formFieldMap.TUB120.state.values" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB121.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB121.state.disabledString}"  id="TUB121L" style="${PamForm.formFieldMap.TUB121.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB121.tooltip}">${PamForm.formFieldMap.TUB121.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB121.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB121)" maxlength="10" 
                            styleId="TUB121" title="${PamForm.formFieldMap.TUB121.label}" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB121','TUB121Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB121','TUB121Icon',event);" 
                            styleId="TUB121Icon"></html:img>
                </td>
            </tr>
	         <!-- Sputnum Spear LDFs -->
	         <%= request.getAttribute("1048") == null ? "" :  request.getAttribute("1048") %>                  
        </nedss:container>
        <!-- SUB_SECTION : 18. Sputum Culture -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="18. Sputum Culture" classType="subSect" >
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB122.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB122.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB122.tooltip}">${PamForm.formFieldMap.TUB122.label}</span>
               </td>
                <td>
                  <html:select title="${PamForm.formFieldMap.TUB122.label}" disabled ="${PamForm.formFieldMap.TUB122.state.disabled}"
                        property="pamClientVO.answer(TUB122)" styleId="TUB122" onchange="fireRule('TUB122', this)">
                      <html:optionsCollection property="formFieldMap.TUB122.state.values" value="key" label="value"/>
                  </html:select>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB123.state.requiredIndClass}">*</span>
                    <span id="TUB123L" style="${PamForm.formFieldMap.TUB123.errorStyleClass}"
                            title="${PamForm.formFieldMap.TUB123.tooltip}">${PamForm.formFieldMap.TUB123.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB123.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB123)" maxlength="10" 
                            styleId="TUB123" title="${PamForm.formFieldMap.TUB123.label}" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB123','TUB123Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB123','TUB123Icon',event);" 
                            styleId="TUB123Icon"></html:img>
                </td>
            </tr>
            <!-- Date Result Reported -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB124.state.requiredIndClass}">*</span>
                    <span  id="TUB124L" style="${PamForm.formFieldMap.TUB124.errorStyleClass}"
                            title="${PamForm.formFieldMap.TUB124.tooltip}">${PamForm.formFieldMap.TUB124.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB124.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB124)" maxlength="10" 
                            styleId="TUB124" title="${PamForm.formFieldMap.TUB124.label}" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB124','TUB124Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB124','TUB124Icon',event);" 
                            styleId="TUB124Icon"></html:img>
                </td>
            </tr>
            <!-- Reporting Laboratory Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB125.state.requiredIndClass}">*</span>
                    <span id="TUB125L" style="${PamForm.formFieldMap.TUB125.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB125.tooltip}">${PamForm.formFieldMap.TUB125.label}                    
                </td>
                <td>
                    <!-- FIXME: update the value passed to property -->
                    <html:select title="${PamForm.formFieldMap.TUB125.label}" disabled ="${PamForm.formFieldMap.TUB125.state.disabled}"
                            property="pamClientVO.answer(TUB125)" styleId="TUB125">
                      <html:optionsCollection property="codedValue(TUB125)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Sputnum Culture LDFs -->
	         <%= request.getAttribute("1051") == null ? "" :  request.getAttribute("1051") %>                  
        </nedss:container>
        
        <!-- SUB_SECTION : 19. Smear/Pathology/Cytology of Tissue and Other Bodily Fluids -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="19. Smear/Pathology/Cytology of Tissue and Other Bodily Fluids" classType="subSect" >
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB126.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB126.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB126.tooltip}">${PamForm.formFieldMap.TUB126.label}</span>
               </td>
                <td>
                  <html:select title="${PamForm.formFieldMap.TUB126.label}" disabled ="${PamForm.formFieldMap.TUB126.state.disabled}"
                        property="pamClientVO.answer(TUB126)" styleId="TUB126"  onchange="fireRule('TUB126', this)" >
                      <html:optionsCollection property="codedValue(TUB126)" value="key" label="value"/>
                  </html:select>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB127.state.requiredIndClass}">*</span>
                    <span id="TUB127L" style="${PamForm.formFieldMap.TUB127.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB127.tooltip}">${PamForm.formFieldMap.TUB127.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB127.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB127)" maxlength="10" 
                            styleId="TUB127" title="${PamForm.formFieldMap.TUB127.label}" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB127','TUB127Icon'); return false;"  onkeypress ="showCalendarEnterKey('TUB127','TUB127Icon',event);" 
                            styleId="TUB127Icon"></html:img>
                </td>
            </tr>
            <!-- Anatomic Site -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB128.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB128.state.disabledString}"  id="TUB128L"  style="${PamForm.formFieldMap.TUB128.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB128.tooltip}">${PamForm.formFieldMap.TUB128.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB128.label}" disabled ="${PamForm.formFieldMap.TUB128.state.disabled}"
                            property="pamClientVO.answer(TUB128)" styleId="TUB128" onchange="fireRule('TUB128', this)" >
                      <html:optionsCollection property="formFieldMap.TUB128.state.values" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Type of Exam -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB129.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB129.state.disabledString}"  id="TUB129L"  
                            style="${PamForm.formFieldMap.TUB129.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB129.tooltip}">${PamForm.formFieldMap.TUB129.label}                    
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB129.label}" property="pamClientVO.answerArray(TUB129)" 
                                styleId="TUB129" 
                                disabled ="${PamForm.formFieldMap.TUB129.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB129-selectedValues'); fireRule('TUB129', this);">
                            <html:optionsCollection property="codedValue(TUB129)" value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB129-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>
                </td>
            </tr>
	         <!-- Smear/Pathology/Cytology of.... LDFs -->
	         <%= request.getAttribute("1056") == null ? "" :  request.getAttribute("1056") %>                  
        </nedss:container>
        <!-- SUB_SECTION : 20. Culture of Tissue and Other Body Fluids: -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="20. Culture of Tissue and Other Body Fluids" classType="subSect" >
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB130.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB130.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB130.tooltip}">${PamForm.formFieldMap.TUB130.label}</span>
               </td>
                <td>
                  <html:select title="${PamForm.formFieldMap.TUB130.label}" disabled ="${PamForm.formFieldMap.TUB130.state.disabled}"
                        property="pamClientVO.answer(TUB130)" styleId="TUB130"  onchange="fireRule('TUB130', this)" >
                      <html:optionsCollection property="codedValue(TUB130)" value="key" label="value"/>
                  </html:select>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB131.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB131.state.disabledString}" id="TUB131L" style="${PamForm.formFieldMap.TUB131.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB131.tooltip}">${PamForm.formFieldMap.TUB131.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB131.state.disabled}"
                            name="PamForm" property="pamClientVO.answer(TUB131)" maxlength="10" 
                            styleId="TUB131" title="${PamForm.formFieldMap.TUB131.label}" onchange="fireRule('TUB131', this)"    onblur="fireRule('TUB131', this)"  onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB131','TUB131Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB131','TUB131Icon',event);" 
                            styleId="TUB131Icon"></html:img>
                </td>
            </tr>
            <!-- Anatomic Site -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB132.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB132.state.disabledString}"  id="TUB132L" style="${PamForm.formFieldMap.TUB132.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB132.tooltip}">${PamForm.formFieldMap.TUB132.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB132.label}" disabled ="${PamForm.formFieldMap.TUB132.state.disabled}"
                            property="pamClientVO.answer(TUB132)" styleId="TUB132" onchange="fireRule('TUB132', this)" >
                      <html:optionsCollection property="formFieldMap.TUB132.state.values" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Date Result Reported -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB133.state.requiredIndClass}">*</span>
                    <span id="TUB133L" style="${PamForm.formFieldMap.TUB133.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB133.tooltip}">${PamForm.formFieldMap.TUB133.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB133.state.disabled}"
                            name="PamForm" title="${PamForm.formFieldMap.TUB133.label}" property="pamClientVO.answer(TUB133)" maxlength="10" 
                            styleId="TUB133" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB133','TUB133Icon'); return false;"  onkeypress ="showCalendarEnterKey('TUB133','TUB133Icon',event);" 
                            styleId="TUB133Icon"></html:img>
                </td>
            </tr>
            <!-- Reporting Laboratory Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB134.state.requiredIndClass}">*</span>
                    <span   id="TUB134L" style="${PamForm.formFieldMap.TUB134.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB134.tooltip}">${PamForm.formFieldMap.TUB134.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB134.label}" disabled ="${PamForm.formFieldMap.TUB134.state.disabled}"
                            property="pamClientVO.answer(TUB134)" styleId="TUB134">
                      <html:optionsCollection property="codedValue(TUB134)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Culture of Tissue and Other Body Fluids LDFs -->
	         <%= request.getAttribute("1061") == null ? "" :  request.getAttribute("1061") %>                  
        </nedss:container>
        <!-- SUB_SECTION : 21. Nucleic Acid Amplification Test Result -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="21. Nucleic Acid Amplification Test Result" classType="subSect" >
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB135.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB135.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB135.tooltip}">${PamForm.formFieldMap.TUB135.label}</span>
               </td>
                <td>
                  <html:select title="${PamForm.formFieldMap.TUB135.label}" disabled ="${PamForm.formFieldMap.TUB135.state.disabled}"
                        property="pamClientVO.answer(TUB135)" styleId="TUB135"  onchange="fireRule('TUB135', this)" >
                      <html:optionsCollection property="codedValue(TUB135)" value="key" label="value"/>
                  </html:select>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB136.state.requiredIndClass}">*</span>
                    <span id="TUB136L" style="${PamForm.formFieldMap.TUB136.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB136.tooltip}">${PamForm.formFieldMap.TUB136.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB136.state.disabled}"
                            name="PamForm" title="${PamForm.formFieldMap.TUB136.label}" property="pamClientVO.answer(TUB136)" maxlength="10" 
                            styleId="TUB136" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB136','TUB136Icon'); return false;"  onkeypress ="showCalendarEnterKey('TUB136','TUB136Icon',event);" 
                            styleId="TUB136Icon"></html:img>
                </td>
            </tr>
            <!-- Speciment Type is Sputum -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB137.state.requiredIndClass}">*</span>
                    <span id="TUB137L" style="${PamForm.formFieldMap.TUB137.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB137.tooltip}">${PamForm.formFieldMap.TUB137.label}</span>
               </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB137.label}" disabled ="${PamForm.formFieldMap.TUB137.state.disabled}"
                           property="pamClientVO.answer(TUB137)" styleId="TUB137" onchange="fireRule('TUB137', this)">
                         <html:optionsCollection property="codedValue(TUB137)" value="key" label="value"/>
                    </html:select>
                </td>
           </tr>
           <!-- Speciment Type Not Sputum -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB138.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB138.state.disabledString}"  id="TUB138L" style="${PamForm.formFieldMap.TUB138.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB138.tooltip}">${PamForm.formFieldMap.TUB138.label}</span>
               </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB138.label}" disabled ="${PamForm.formFieldMap.TUB138.state.disabled}"
                            property="pamClientVO.answer(TUB138)" styleId="TUB138" onchange="fireRule('TUB138', this)">
                         <html:optionsCollection property="codedValue(TUB138)" value="key" label="value"/>
                    </html:select>
                </td>
           </tr>
           <!-- Date Result Reported -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB139.state.requiredIndClass}">*</span>
                    <span  id="TUB139L" style="${PamForm.formFieldMap.TUB139.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB139.tooltip}">${PamForm.formFieldMap.TUB139.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB139.state.disabled}"
                            name="PamForm" title="${PamForm.formFieldMap.TUB139.label}" property="pamClientVO.answer(TUB139)" maxlength="10" 
                            styleId="TUB139" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB139','TUB139Icon'); return false;" onkeypress ="showCalendarEnterKey('TUB139','TUB139Icon',event);" 
                            styleId="TUB139Icon"></html:img>
                </td>
            </tr>
            <!-- Reporting Laboratory Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB140.state.requiredIndClass}">*</span>
                    <span  id="TUB140L" style="${PamForm.formFieldMap.TUB140.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB140.tooltip}">${PamForm.formFieldMap.TUB140.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB140.label}" disabled ="${PamForm.formFieldMap.TUB140.state.disabled}"
                            property="pamClientVO.answer(TUB140)" styleId="TUB140">
                      <html:optionsCollection property="codedValue(TUB140)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Nucleic Acid... LDFs -->
	         <%= request.getAttribute("1067") == null ? "" :  request.getAttribute("1067") %>                  
        </nedss:container>
        
        <!-- SUB_SECTION : Initial Chest Radiograph and Other Chest Imaging Study -->
       <%@ include file="ext/ChestRadiographandStudy.jsp" %>        
  
        <!-- SUB_SECTION : 24. Interferon Gamma Release Assay for Mycrobacterium tuberculosis at Diagnosis -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" 
                name="24. Interferon Gamma Release Assay for Mycobacterium tuberculosis at Diagnosis" 
                classType="subSect" >
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB150.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB150.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB150.tooltip}">${PamForm.formFieldMap.TUB150.label}  
                    </span>                  
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB150.label}" disabled ="${PamForm.formFieldMap.TUB150.state.disabled}"
                            property="pamClientVO.answer(TUB150)" styleId="TUB150" onchange="fireRule('TUB150', this)" >
                      <html:optionsCollection property="codedValue(TUB150)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB151.state.requiredIndClass}">*</span>
                    <span  id="TUB151L" style="${PamForm.formFieldMap.TUB151.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB151.tooltip}">${PamForm.formFieldMap.TUB151.label}
                    </span>                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB151.state.disabled}"
                            name="PamForm" title="${PamForm.formFieldMap.TUB151.label}" property="pamClientVO.answer(TUB151)" maxlength="10" 
                            styleId="TUB151" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB151','TUB151Icon'); return false;"  onkeypress ="showCalendarEnterKey('TUB151','TUB151Icon',event);" 
                            styleId="TUB151Icon"></html:img>
                </td>
            </tr>
            <!-- Test Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB152.state.requiredIndClass}">*</span>
                    <span  id="TUB152L" style="${PamForm.formFieldMap.TUB152.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB152.tooltip}">${PamForm.formFieldMap.TUB152.label}   
                    </span>                 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB152.state.disabled}" maxlength="100"
                            property="pamClientVO.answer(TUB152)" title="${PamForm.formFieldMap.TUB152.label}" />
                </td>
            </tr>
	         <!-- Interferon... LDFs -->
	         <%= request.getAttribute("1086") == null ? "" :  request.getAttribute("1086") %>                  
        </nedss:container>
    </nedss:container>
    
    <!-- SECTION : Risk Factors -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : 25. Primary Information Evalutated for TB Disease -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="25. Primary Reason Evaluated" classType="subSect" >
            <!-- Primary Reason Evaluated -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB153.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB153.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB153.tooltip}">${PamForm.formFieldMap.TUB153.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB153.label}" disabled ="${PamForm.formFieldMap.TUB153.state.disabled}"
                            property="pamClientVO.answer(TUB153)" styleId="TUB153">
                      <html:optionsCollection property="codedValue(TUB153)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
             <!-- Primary Reason LDFs -->
             <%= request.getAttribute("1090") == null ? "" :  request.getAttribute("1090") %>                  
        </nedss:container>
        <!-- SUB_SECTION :26.  HIV Status at Time of Diagnosis -->
        <logic:equal name="BaseForm" property="securityMap(TBHIVSecurity)" value="false">
        	<html:hidden property="pamClientVO.answer(TUB154)"/>
        	<html:hidden property="pamClientVO.answer(TUB155)"/>
        	<html:hidden property="pamClientVO.answer(TUB156)"/>        	
        </logic:equal>          
        <logic:equal name="BaseForm" property="securityMap(TBHIVSecurity)" value="true">
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="26. HIV Status at Time of Diagnosis" classType="subSect" >
            <!-- HIV Status -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB154.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB154.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB154.tooltip}">${PamForm.formFieldMap.TUB154.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB154.label}" disabled ="${PamForm.formFieldMap.TUB154.state.disabled}"
                            property="pamClientVO.answer(TUB154)" styleId="TUB154" onchange="fireRule('TUB154', this)" >
                      <html:optionsCollection property="codedValue(TUB154)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- State HIV/AIDS Patient Number -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB155.state.requiredIndClass}">*</span>
                    <span   class="${PamForm.formFieldMap.TUB155.state.disabledString}" id="TUB155L" style="${PamForm.formFieldMap.TUB155.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB155.tooltip}">${PamForm.formFieldMap.TUB155.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB155.state.disabled}"
                            property="pamClientVO.answer(TUB155)" styleId="TUB155" onblur="fireRule('TUB155', this)"
                            maxlength="10"
                            size="10"
                            onkeyup="UpperCaseMask(this)" 
                            title="${PamForm.formFieldMap.INV155.label}" />
                </td>
            </tr>
            <!-- City/County HIV/AIDS Patient Number -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB156.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB156.state.disabledString}"   id="TUB156L" style="${PamForm.formFieldMap.TUB156.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB156.tooltip}">${PamForm.formFieldMap.TUB156.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB156.state.disabled}"
                            styleId="TUB156" onblur="fireRule('TUB156', this)"
                            property="pamClientVO.answer(TUB156)"
                            onkeyup="UpperCaseMask(this)"
                            maxlength="10"
                            size="10" 
                            title="${PamForm.formFieldMap.TUB156.label}" />
                </td>
            </tr>
             <!-- HIV Status LDFs -->
             <%= request.getAttribute("1093") == null ? "" :  request.getAttribute("1093") %>                  
        </nedss:container>
        </logic:equal>
        <!-- SUB_SECTION : Residence -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Residence" classType="subSect" >
            <!-- Homeless Within Past Year -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB157.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB157.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB157.tooltip}">${PamForm.formFieldMap.TUB157.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB157.label}" disabled ="${PamForm.formFieldMap.TUB157.state.disabled}"
                            property="pamClientVO.answer(TUB157)" styleId="TUB157">
                      <html:optionsCollection property="codedValue(TUB157)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Resident of Correctional Facility at Time of Diagnosis -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB158.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB158.state.disabledString}"  id="TUB158L"  style="${PamForm.formFieldMap.TUB158.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB158.tooltip}">${PamForm.formFieldMap.TUB158.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB158.label}" disabled ="${PamForm.formFieldMap.TUB158.state.disabled}"
                            property="pamClientVO.answer(TUB158)" styleId="TUB158"  onchange="fireRule('TUB158', this)">
                      <html:optionsCollection property="codedValue(TUB158)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Type of Correctional Facility -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB159.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB159.state.disabledString}"  id="TUB159L" style="${PamForm.formFieldMap.TUB159.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB159.tooltip}">${PamForm.formFieldMap.TUB159.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB159.label}" disabled ="${PamForm.formFieldMap.TUB159.state.disabled}"
                            property="pamClientVO.answer(TUB159)" styleId="TUB159" onchange="fireRule('TUB159', this)">
                      <html:optionsCollection property="codedValue(TUB159)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Under custody of Immigration and Customs Enforcrement -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB160.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB160.state.disabledString}"  id="TUB160L" style="${PamForm.formFieldMap.TUB160.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB160.tooltip}">${PamForm.formFieldMap.TUB160.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB160.label}" disabled ="${PamForm.formFieldMap.TUB160.state.disabled}"
                            property="pamClientVO.answer(TUB160)" styleId="TUB160" onchange="fireRule('TUB160', this)">
                      <html:optionsCollection property="codedValue(TUB160)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Resident of Long Term Care Facility at Time of Diagnosis -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB161.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB161.state.disabledString}"  id="TUB161L" style="${PamForm.formFieldMap.TUB161.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB161.tooltip}">${PamForm.formFieldMap.TUB161.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB161.label}" disabled ="${PamForm.formFieldMap.TUB161.state.disabled}"
                            property="pamClientVO.answer(TUB161)" styleId="TUB161"  onchange="fireRule('TUB161', this)">
                      <html:optionsCollection property="codedValue(TUB161)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Type of Long Term Care Facility -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB162.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB162.state.disabledString}"  id="TUB162L" style="${PamForm.formFieldMap.TUB162.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB162.tooltip}">${PamForm.formFieldMap.TUB162.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB162.label}" disabled ="${PamForm.formFieldMap.TUB162.state.disabled}"
                            property="pamClientVO.answer(TUB162)" styleId="TUB162" onchange="fireRule('TUB162', this)">
                      <html:optionsCollection property="codedValue(TUB162)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Residence LDFs -->
	         <%= request.getAttribute("1292") == null ? "" :  request.getAttribute("1292") %>                  
        </nedss:container>
        
        <!-- SUB_SECTION : Occupation -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Occupation" classType="subSect" >
            <!-- Primary Occupation Within Past Year -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB163.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB163.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB163.tooltip}">${PamForm.formFieldMap.TUB163.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB163.label}" disabled ="${PamForm.formFieldMap.TUB163.state.disabled}"
                            property="pamClientVO.answer(TUB163)" styleId="TUB163">
                      <html:optionsCollection property="codedValue(TUB163)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Occupation LDFs -->
	         <%= request.getAttribute("1293") == null ? "" :  request.getAttribute("1293") %>                  
        </nedss:container>
        <!-- SUB_SECTION : Drug and Alcohol Use -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Drug and Alcohol Use" classType="subSect" >
            <!-- Injecting Drug Use Within Past Year -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB164.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB164.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB164.tooltip}">${PamForm.formFieldMap.TUB164.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB164.label}" disabled ="${PamForm.formFieldMap.TUB164.state.disabled}"
                            property="pamClientVO.answer(TUB164)" styleId="TUB164">
                      <html:optionsCollection property="codedValue(TUB164)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Non-Injecting Drug Use Within Past Year -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB165.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB165.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB165.tooltip}">${PamForm.formFieldMap.TUB165.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB165.label}" disabled ="${PamForm.formFieldMap.TUB165.state.disabled}"
                            property="pamClientVO.answer(TUB165)" styleId="TUB165">
                      <html:optionsCollection property="codedValue(TUB165)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Excess Alcohol Use Within Past Year -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB166.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB166.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB166.tooltip}">${PamForm.formFieldMap.TUB166.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB166.label}" disabled ="${PamForm.formFieldMap.TUB166.state.disabled}"
                            property="pamClientVO.answer(TUB166)" styleId="TUB166">
                      <html:optionsCollection property="codedValue(TUB166)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Drug and Alcohol Use LDFs -->
	         <%= request.getAttribute("1294") == null ? "" :  request.getAttribute("1294") %>                              
        </nedss:container>
        <!-- SUB_SECTION : Additional Risk Factors -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Additional Risk Factors" classType="subSect" >
            <!-- 34. Additional TB Risk Factors -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB167.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB167.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB167.tooltip}">${PamForm.formFieldMap.TUB167.label}                    
                </td>
                <td>
                    <div class="multiSelectBlock">
                        <i> (Use Ctrl to select more than one) </i> <br/>
                        <html:select title="${PamForm.formFieldMap.TUB167.label}" property="pamClientVO.answerArray(TUB167)" 
                                styleId="TUB167" 
                                disabled ="${PamForm.formFieldMap.TUB167.state.disabled}"
                                multiple="true" size="4"
                                onchange="displaySelectedOptions(this, 'TUB167-selectedValues'); fireRule('TUB167', this);">
                            <html:optionsCollection property="codedValue(TUB167)" value="key" label="value"/>
                        </html:select> 
                        <br/>
                        <div id="TUB167-selectedValues" style="margin:0.25em;">
                           <b> Selected Values: </b>
                        </div>
                    </div>
                </td>
            </tr>
            <!-- Other Risk Factor(s) -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB168.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB168.state.disabledString}"  id="TUB168L" style="${PamForm.formFieldMap.TUB168.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB168.tooltip}">${PamForm.formFieldMap.TUB168.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB168.state.disabled}"
	                    styleId="TUB168" property="pamClientVO.answer(TUB168)" maxlength="100" 
	                    title="${PamForm.formFieldMap.INV168.label}"  onchange="fireRule('TUB168', this);"/>
                </td>
            </tr>
            <!-- Immigration Status at First Entry to the U.S -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB169.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB169.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB169.tooltip}">${PamForm.formFieldMap.TUB169.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB169.label}" disabled ="${PamForm.formFieldMap.TUB169.state.disabled}" 
                            property="pamClientVO.answer(TUB169)" styleId="TUB169">
                      <html:optionsCollection property="codedValue(TUB169)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	         <!-- Additional Risk Factors LDFs -->
	         <%= request.getAttribute("1295") == null ? "" :  request.getAttribute("1295") %>                              
        </nedss:container>
    </nedss:container>
    
    <!-- SECTION : Treatment -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : 37. Initial Drug Regimen -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="37. Initial Drug Regimen" classType="subSect" >
            <!-- Date Therapy Started -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB170.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB170.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB170.tooltip}">${PamForm.formFieldMap.TUB170.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB170.state.disabled}"
                           name="PamForm" title="${PamForm.formFieldMap.TUB170.label}" property="pamClientVO.answer(TUB170)" maxlength="10" 
                            styleId="TUB170" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TUB170','TUB170Icon'); return false;"  onkeypress ="showCalendarEnterKey('TUB170','TUB170Icon',event);" 
                            styleId="TUB170Icon"></html:img>
                </td>
            </tr>
            <!-- Standard 4 Regimen -->
            <tr>
               <td class="fieldName">
                    &nbsp;                 
                </td>
                <td>
                    <input type="button" class="Button" value="Standard Regimen (4)" style="width:15em;" 
                        onclick="populateStandardRegimen('RVCT_Tuberculosis_MarkRestNo_Btn');"/>
                </td>
            </tr>
			 <!--Mark Rest 'No'-->
            <tr>
               <td class="fieldName">
                    &nbsp;                 
                </td>
                <td>
                    <input type="button" class="Button" value="Mark Rest 'No'" id="RVCT_Tuberculosis_MarkRestNo_Btn"
                        style="width:15em;" onclick="populateMarkRestNo('<%= tabId + (subSectionIndex) %>');"/>
                </td>
            </tr>
            <!-- Isoniazid -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB171.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB171.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB171.tooltip}">${PamForm.formFieldMap.TUB171.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB171.label}" property="pamClientVO.answer(TUB171)" styleId="TUB171" onchange="fireRule('TUB171', this)">
                      <html:optionsCollection property="codedValue(TUB171)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Rifampin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB172.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB172.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB172.tooltip}">${PamForm.formFieldMap.TUB172.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB172.label}" disabled ="${PamForm.formFieldMap.TUB172.state.disabled}"
                            property="pamClientVO.answer(TUB172)" styleId="TUB172" onchange="fireRule('TUB172', this)">
                      <html:optionsCollection property="codedValue(TUB172)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Pyrazinamide -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB173.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB173.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB173.tooltip}">${PamForm.formFieldMap.TUB173.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB173.label}" disabled ="${PamForm.formFieldMap.TUB173.state.disabled}"
                            property="pamClientVO.answer(TUB173)" styleId="TUB173" onchange="fireRule('TUB173', this)">
                      <html:optionsCollection property="codedValue(TUB173)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Ethambutol -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB174.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB174.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB174.tooltip}">${PamForm.formFieldMap.TUB174.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB174.label}" disabled ="${PamForm.formFieldMap.TUB174.state.disabled}"
                            property="pamClientVO.answer(TUB174)" styleId="TUB174" onchange="fireRule('TUB174', this)">
                      <html:optionsCollection property="codedValue(TUB174)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Streptomycin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB175.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB175.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB175.tooltip}">${PamForm.formFieldMap.TUB175.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB175.label}" disabled ="${PamForm.formFieldMap.TUB175.state.disabled}"
                            property="pamClientVO.answer(TUB175)" styleId="TUB175" onchange="fireRule('TUB175', this)">
                      <html:optionsCollection property="codedValue(TUB175)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Rifabutin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB182.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB182.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB182.tooltip}">${PamForm.formFieldMap.TUB182.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB182.label}" disabled ="${PamForm.formFieldMap.TUB182.state.disabled}"
                            property="pamClientVO.answer(TUB182)" styleId="TUB182" onchange="fireRule('TUB182', this)">
                      <html:optionsCollection property="codedValue(TUB182)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Rifapentine -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB185.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB185.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB185.tooltip}">${PamForm.formFieldMap.TUB185.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB185.label}" disabled ="${PamForm.formFieldMap.TUB185.state.disabled}"
                            property="pamClientVO.answer(TUB185)" styleId="TUB185" onchange="fireRule('TUB185', this)">
                      <html:optionsCollection property="codedValue(TUB185)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Ethionamide -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB176.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB176.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB176.tooltip}">${PamForm.formFieldMap.TUB176.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB176.label}" disabled ="${PamForm.formFieldMap.TUB176.state.disabled}"
                            property="pamClientVO.answer(TUB176)" styleId="TUB176" onchange="fireRule('TUB176', this)">
                      <html:optionsCollection property="codedValue(TUB176)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Amikacin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB181.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB181.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB181.tooltip}">${PamForm.formFieldMap.TUB181.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB181.label}" disabled ="${PamForm.formFieldMap.TUB181.state.disabled}"
                            property="pamClientVO.answer(TUB181)" styleId="TUB181" onchange="fireRule('TUB181', this)">
                      <html:optionsCollection property="codedValue(TUB181)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Kanamycin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB177.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB177.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB177.tooltip}">${PamForm.formFieldMap.TUB177.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB177.label}" disabled ="${PamForm.formFieldMap.TUB177.state.disabled}"
                            property="pamClientVO.answer(TUB177)" styleId="TUB177" onchange="fireRule('TUB177', this)">
                      <html:optionsCollection property="codedValue(TUB177)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Capreomycin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB179.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB179.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB179.tooltip}">${PamForm.formFieldMap.TUB179.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB179.label}" disabled ="${PamForm.formFieldMap.TUB179.state.disabled}"
                            property="pamClientVO.answer(TUB179)" styleId="TUB179" onchange="fireRule('TUB179', this)">
                      <html:optionsCollection property="codedValue(TUB179)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Ciprofloxacin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB183.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB183.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB183.tooltip}">${PamForm.formFieldMap.TUB183.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB183.label}" disabled ="${PamForm.formFieldMap.TUB183.state.disabled}"
                            property="pamClientVO.answer(TUB183)" styleId="TUB183" onchange="fireRule('TUB183', this)">
                      <html:optionsCollection property="codedValue(TUB183)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Levofloxacin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB186.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB186.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB186.tooltip}">${PamForm.formFieldMap.TUB186.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB186.label}" disabled ="${PamForm.formFieldMap.TUB186.state.disabled}"
                            property="pamClientVO.answer(TUB186)" styleId="TUB186" onchange="fireRule('TUB186', this)">
                      <html:optionsCollection property="codedValue(TUB186)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Ofloxacin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB184.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB184.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB184.tooltip}">${PamForm.formFieldMap.TUB184.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB184.label}" disabled ="${PamForm.formFieldMap.TUB184.state.disabled}"
                            property="pamClientVO.answer(TUB184)" styleId="TUB184" onchange="fireRule('TUB184', this)">
                      <html:optionsCollection property="codedValue(TUB184)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Moxifloxacin -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB187.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB187.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB187.tooltip}">${PamForm.formFieldMap.TUB187.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB187.label}" disabled ="${PamForm.formFieldMap.TUB187.state.disabled}"
                            property="pamClientVO.answer(TUB187)" styleId="TUB187" onchange="fireRule('TUB187', this)">
                      <html:optionsCollection property="codedValue(TUB187)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Cycloserine -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB178.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB178.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB178.tooltip}">${PamForm.formFieldMap.TUB178.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB178.label}" disabled ="${PamForm.formFieldMap.TUB178.state.disabled}"
                            property="pamClientVO.answer(TUB178)" styleId="TUB178" onchange="fireRule('TUB178', this)">
                      <html:optionsCollection property="codedValue(TUB178)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Para-Amino Salicylic Acid -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB180.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB180.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB180.tooltip}">${PamForm.formFieldMap.TUB180.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB180.label}" disabled ="${PamForm.formFieldMap.TUB180.state.disabled}" property="pamClientVO.answer(TUB180)" styleId="TUB180" onchange="fireRule('TUB180', this)">
                      <html:optionsCollection property="codedValue(TUB180)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Other Drug -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB188.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB188.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB188.tooltip}">${PamForm.formFieldMap.TUB188.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB188.label}" disabled ="${PamForm.formFieldMap.TUB188.state.disabled}"
                            property="pamClientVO.answer(TUB188)" styleId="TUB188" onchange="fireRule('TUB188', this)">
                      <html:optionsCollection property="codedValue(TUB188)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Specify Other Drug -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB189.state.requiredIndClass}">*</span>
                    <span  class="${PamForm.formFieldMap.TUB189.state.disabledString}"  id="TUB189L" style="${PamForm.formFieldMap.TUB189.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB189.tooltip}">${PamForm.formFieldMap.TUB189.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB189.state.disabled}"
                            property="pamClientVO.answer(TUB189)" title="${PamForm.formFieldMap.TUB189.label}"  
                            maxlength="100" styleId="TUB189" onblur="fireRule('TUB189', this)"/>
                </td>
            </tr>
            <!-- Other Drug 2 -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB190.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB190.state.disabledString}" id="TUB190L" style="${PamForm.formFieldMap.TUB190.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB190.tooltip}">${PamForm.formFieldMap.TUB190.label}                    
                </td>
                <td>
                    <html:select title="${PamForm.formFieldMap.TUB190.label}" disabled ="${PamForm.formFieldMap.TUB190.state.disabled}"
                            property="pamClientVO.answer(TUB190)" styleId="TUB190" onchange="fireRule('TUB190', this)">
                      <html:optionsCollection property="codedValue(TUB190)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Specify Other Drug 2 -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB191.state.requiredIndClass}">*</span>
                    <span class="${PamForm.formFieldMap.TUB191.state.disabledString}" id="TUB191L" style="${PamForm.formFieldMap.TUB191.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB191.tooltip}">${PamForm.formFieldMap.TUB191.label}                    
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.TUB191.state.disabled}" maxlength="100"
                            property="pamClientVO.answer(TUB191)"  styleId="TUB191" onblur="fireRule('TUB191', this)"  title="${PamForm.formFieldMap.TUB191.label}" />
                </td>
            </tr>
            <!-- Clear -->
            <tr>
               <td class="fieldName">
                    &nbsp;                    
                </td>
                <td>
                    <input type="button" class="Button" value="Clear" onclick="clearAll('<%= tabId + (subSectionIndex) %>');"/>
                </td>
            </tr>
	        <!-- Initial Drug Regimen LDFs -->
	        <%= request.getAttribute("1112") == null ? "" :  request.getAttribute("1112") %>                              
        </nedss:container>
    </nedss:container>    
    <!-- Epidemiologic -->
    <div class='${PamForm.formFieldMap["1362"].state.visibleString}'>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Epidemiologic  -->
         <div class='${PamForm.formFieldMap["1363"].state.visibleString}'>
          <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Epi-Link" classType="subSect" >
			<!-- Is this person associated with a day care facility:-->	 
				<tr class="${PamForm.formFieldMap.INV148.state.visibleString}">
				     <td class="fieldName"> 
				         <span style="${PamForm.formFieldMap.INV148.state.requiredIndClass}">*</span>
				         <span class="${PamForm.formFieldMap.INV148.state.disabledString}"  id="INV148L" style="${PamForm.formFieldMap.INV148.errorStyleClass}"
				                 title="${PamForm.formFieldMap.INV148.tooltip}">${PamForm.formFieldMap.INV148.label}</span>  
				     </td>
				     <td> 
				     	  <html:select title="${PamForm.formFieldMap.INV148.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV148.state.disabled}" property="pamClientVO.answer(INV148)" styleId="INV148"> 
			                 <html:optionsCollection property="codedValue(INV148)" value="key" label="value"/>
			              </html:select>
				 	</td>
				 </tr>
			<!-- Is this person a food handler:-->
				<tr class="${PamForm.formFieldMap.INV149.state.visibleString}">
				     <td class="fieldName"> 
				         <span style="${PamForm.formFieldMap.INV149.state.requiredIndClass}">*</span>
				         <span class="${PamForm.formFieldMap.INV149.state.disabledString}"  id="INV149L" style="${PamForm.formFieldMap.INV149.errorStyleClass}"
				                 title="${PamForm.formFieldMap.INV149.tooltip}">${PamForm.formFieldMap.INV149.label}</span>  
				     </td>
				     <td> 
				     	  <html:select title="${PamForm.formFieldMap.INV149.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV149.state.disabled}" property="pamClientVO.answer(INV149)" styleId="INV149"> 
			                 <html:optionsCollection property="codedValue(INV149)" value="key" label="value"/>
			              </html:select>
				 	</td>
				 </tr>
				<!--Is this case part of an outbreak: -->
				<tr class="${PamForm.formFieldMap.INV150.state.visibleString}">
				     <td class="fieldName"> 
				         <span style="${PamForm.formFieldMap.INV150.state.requiredIndClass}">*</span>
				         <span style="${PamForm.formFieldMap.INV150.errorStyleClass}"  id="INV150L"
				                 title="${PamForm.formFieldMap.INV150.tooltip}">${PamForm.formFieldMap.INV150.label}</span>  
				     </td>
				     <td> 
				     	  <html:select title="${PamForm.formFieldMap.INV150.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV150.state.disabled}" property="pamClientVO.answer(INV150)" styleId="INV150" onchange="fireRule('INV150', this)">
			                 <html:optionsCollection property="codedValue(INV150)" value="key" label="value"/>
			              </html:select>
				 	</td>
				 </tr>				
				<!--Outbreak Name: -->
				<tr class="${PamForm.formFieldMap.INV151.state.visibleString}">
				     <td class="fieldName"> 
				         <span style="${PamForm.formFieldMap.INV151.state.requiredIndClass}">*</span>
				         <span class="${PamForm.formFieldMap.INV151.state.disabledString}"  id="INV151L" style="${PamForm.formFieldMap.INV151.errorStyleClass}"
				                 title="${PamForm.formFieldMap.INV151.tooltip}">${PamForm.formFieldMap.INV151.label}</span>  
				     </td>
				     <td> 
				     	  <html:select title="${PamForm.formFieldMap.INV157.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV151.state.disabled}" property="pamClientVO.answer(INV151)" styleId="INV151"> 
			                 <html:optionsCollection property="codedValue(INV151)" value="key" label="value"/>
			              </html:select>
				 	</td>
				 </tr>
	         <!-- Comments LDFs -->
	         <%= request.getAttribute("1363") == null ? "" :  request.getAttribute("1363") %>                              
        </nedss:container>
        </div>
        	<!-- SUB_SECTION Disease Aquisition-->
        	 <div class='${PamForm.formFieldMap["1368"].state.visibleString}'>
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Disease Acquisition" classType="subSect">
			<jsp:include page="/pam/ext/DiseaseAcquisition.jsp"/>
       	<!-- Comments LDFs -->
	         <%= request.getAttribute("1368") == null ? "" :  request.getAttribute("1368") %>
		</nedss:container>
		</div>	
		<!-- SUB_SECTION CaseStatus-->
		 <div class='${PamForm.formFieldMap["1374"].state.visibleString}'>
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Case Status" classType="subSect">
			<jsp:include page="/pam/ext/CaseStatus.jsp"/>
	       <!-- Comments LDFs -->
	       <%= request.getAttribute("1374") == null ? "" :  request.getAttribute("1374") %>
		</nedss:container>
		</div>
   	</nedss:container>
      </div>
       <!-- SECTION : Investigation Comments -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Investigation Comments -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation Comments" classType="subSect" >
            <tr>
               <td class="${PamForm.formFieldMap.INV167.state.visibleString}">
                    <span style="${PamForm.formFieldMap.INV167.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV167.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV167.tooltip}">${PamForm.formFieldMap.INV167.label}                    
                </td>
                <td>
                    <html:textarea title="${PamForm.formFieldMap.INV167.label}" style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${PamForm.formFieldMap.INV167.state.disabled}"  styleId ="INV167"
                           onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"   name="PamForm" property="pamClientVO.answer(INV167)"/>
                </td>
            </tr>
	         <!-- Comments LDFs -->
	         <%= request.getAttribute("1138") == null ? "" :  request.getAttribute("1138") %>                              
        </nedss:container>
   </nedss:container>
   
   <!-- SECTION : Local Fields -->
   <% if(request.getAttribute("1329") != null) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		<%= request.getAttribute("1329") == null ? "" :  request.getAttribute("1329") %>
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
    
    <!-- Required form hidden fields -->
    <!-- FIXME: include all the relevant hiddend fields -->
    
</div> <!-- view -->

</td> </tr>