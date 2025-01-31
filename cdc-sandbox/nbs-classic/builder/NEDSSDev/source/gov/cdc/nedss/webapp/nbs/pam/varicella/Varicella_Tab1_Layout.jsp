<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants, gov.cdc.nedss.webapp.nbs.form.pam.PamForm, gov.cdc.nedss.webapp.nbs.form.pam.FormField"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>

 
 <tr> <td>
 
 <% 
 String sectionId = "investigationInformation";
 int subSectionIndex = 0; 
%>
 
<div class="view" id="view1">    
<%
String []sectionNames = {"Investigation Information", "Reporting Information", "Clinical Information",
                           "Laboratory Information", "Vaccine Information", "Vaccination Record",
                            "Epidemiologic Information", "Investigation Comments", "Custom Fields"};
int sectionIndex = 0;
%> 
<table role="presentation" class="sectionsToggler" style="width:100%;">
<tr>
    <td>
        <ul class="horizontalList">
            <li style="margin-right:5px;"><b>Go to: </b></li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
            <li class="delimiter"> | </li>
            <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>    
            <% if(request.getAttribute("2264") != null) { %>
                <li class="delimiter"> | </li>
                <li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>    
            <% } %>

        </ul>
    </td>
</tr>
<tr>
    <td style="padding-top:1em;">
        <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('view1')"/>Collapse Sections</a>
    </td>
</tr>
</table>
<%
// reset the sectionIndex to 0 before utilizing the sectionNames array.
sectionIndex = 0;
%>
<!-- SECTION : Investigation Information --> 
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
    <!-- SUB_SECTION : Investigation Details -->
    <nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Investigation Details" classType="subSect" >
        <!-- Jurisdiction -->
        <logic:empty name="PamForm" property="attributeMap.ReadOnlyJursdiction">
            <tr>
                <td class="${PamForm.formFieldMap.INV107.state.visibleString}">
                    <span style="${PamForm.formFieldMap.INV107.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV107.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV107.tooltip}">
                        ${PamForm.formFieldMap.INV107.label}
                    </span> 
                </td>
                <td>
                    <html:select disabled ="${PamForm.formFieldMap.INV107.state.disabled}" name="PamForm" property="pamClientVO.answer(INV107)" styleId = "INV107">
                        <html:optionsCollection property="jurisdictionListNoExport" value="key" label="value"/>
                    </html:select>
                    <html:hidden property="attributeMap.NBSSecurityJurisdictions" 
                            styleId="NBSSecurityJurisdictions"/>
                </td>
            </tr>
        </logic:empty>
        <logic:notEmpty name="PamForm" property="attributeMap.ReadOnlyJursdiction">
            <tr>
                <td class="${PamForm.formFieldMap.INV107.state.visibleString}"> 
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
            <td class="${PamForm.formFieldMap.INV108.state.visibleString}"> 
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
            <td class="${PamForm.formFieldMap.INV174.state.visibleString}">
                <span style="${PamForm.formFieldMap.INV174.errorStyleClass}"  
                        title="${PamForm.formFieldMap.INV174.tooltip}">${PamForm.formFieldMap.INV174.label}</span>
            </td>
            <td>
                <html:checkbox disabled ="${PamForm.formFieldMap.INV174.state.disabled}" name="PamForm" property="pamClientVO.answer(INV174)" value="1" 
                        title="${PamForm.formFieldMap.INV174.label}"></html:checkbox>
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
                <html:select disabled ="${PamForm.formFieldMap.INV109.state.disabled}"
                        name="PamForm" property="pamClientVO.answer(INV109)" styleId="INV109" 
                        title="${PamForm.formFieldMap.INV109.label}">
                    <html:optionsCollection property="codedValueNoBlnk(INV109)" value="key" label="value"/>
                </html:select>
            </td>
        </tr>
        <!-- Investigation Start Date -->
        <tr>
            <td class="${PamForm.formFieldMap.INV147.state.visibleString}">
                <span style="${PamForm.formFieldMap.INV147.state.requiredIndClass}">*</span>
                <span style="${PamForm.formFieldMap.INV147.errorStyleClass}" 
                        title="${PamForm.formFieldMap.INV147.tooltip}">
                    ${PamForm.formFieldMap.INV147.label}
                </span> 
            </td>
            <td>
                <html:text disabled ="${PamForm.formFieldMap.INV147.state.disabled}" 
                        name="PamForm" property="pamClientVO.answer(INV147)" maxlength="10" 
                        styleId="INV147"  title="${PamForm.formFieldMap.INV147.label}" onkeyup="DateMask(this,null,event)" size="10"/>
                <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV147','INV147Icon'); return false;" onkeypress ="showCalendarEnterKey('INV147','INV147Icon',event);" 
                        styleId="INV147Icon"></html:img>
            </td>
        </tr>
        <!-- Investigation Details LDFs -->
        <%= request.getAttribute("2035") == null ? "" :  request.getAttribute("2035") %>
    </nedss:container>
    
<!-- SUB_SECTION : Investigator -->

	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Investigator" classType="subSect" >
	    <!-- Investigator -->
	    <logic:empty name="PamForm" property="attributeMap.INV207Uid">                
	        <tr>
	            <td class="${PamForm.formFieldMap.INV207.state.visibleString}"> 
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
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV207Text','INV207_qec_list')" title="${PamForm.formFieldMap.INV207.label}"/>
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV207CodeLookupButton" onclick="getDWRProvider('INV207')"/>                                
                        <div class="page_name_auto_complete" id="INV207_qec_list" style="background:#DCDCDC"></div>
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
                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV207Text','INV207_qec_list')" title="${PamForm.formFieldMap.INV207.label}" 
                                />
                        <input type="button" class="Button" value="Quick Code Lookup" 
                            id="INV207CodeLookupButton" onclick="getDWRProvider('INV207')" 
                            style="visibility:hidden"/>                                
                        <div class="page_name_auto_complete" id="INV207_qec_list" style="background:#DCDCDC"></div>
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
	                <html:text disabled ="${PamForm.formFieldMap.INV110.state.disabled}" 
	                        name="PamForm"  title="${PamForm.formFieldMap.INV110.label}" property="pamClientVO.answer(INV110)" maxlength="10" 
	                        styleId="INV110" value="" onkeyup="DateMask(this,null,event)" size="10"/>
	                <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon'); return false;" onkeypress ="showCalendarEnterKey('INV110','INV110Icon',event);" 
	                        styleId="INV110Icon"></html:img>
	            </td>
	        </tr>
        </logic:equal>
        
        <logic:notEqual name="PamForm" property="attributeMap.INV207SearchResult" value="">
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV110.state.requiredIndClass}">*</span>
                    <span id="INV110L" title="${PamForm.formFieldMap.INV110.tooltip}">
                        ${PamForm.formFieldMap.INV110.label}
                    </span> 
                </td>
                <td>
                    <html:text disabled ="${PamForm.formFieldMap.INV110.state.disabled}" 
                            name="PamForm" title="${PamForm.formFieldMap.INV110.label}" property="pamClientVO.answer(INV110)" maxlength="10" 
                            styleId="INV110" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon'); return false;" onkeypress ="showCalendarEnterKey('INV110','INV110Icon',event);" 
                            styleId="INV110Icon"></html:img>
                </td>
            </tr>
        </logic:notEqual>
        
            
	     <!-- Investigator LDFs -->
	     <%= request.getAttribute("2041") == null ? "" :  request.getAttribute("2041") %>            
	</nedss:container>  
</nedss:container>   
<!-- SECTION : Reporting Information --> 
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">

	<!-- SUBSECTION : Key Report Dates: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Key Report Dates" classType="subSect" >
	 <!--7. Date of Report:-->
		 <tr>
		     <td class="${PamForm.formFieldMap.INV111.state.visibleString}"> 
		         <span style="${PamForm.formFieldMap.INV111.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV111.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV111.tooltip}">${PamForm.formFieldMap.INV111.label}</span>  
		     </td>
		     <td class="topBorder">
	             <html:text title="${PamForm.formFieldMap.INV111.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV111.state.disabled}" 
	                     property="pamClientVO.answer(INV111)" maxlength="10" 
	                     styleId="INV111" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV111','INV111Icon'); return false;" onkeypress ="showCalendarEnterKey('INV111','INV111Icon',event);" 
	                     styleId="INV111Icon"></html:img>
	         </td>
		 </tr>
		 <!--8. Earliest Date Reported to County-->
		 <tr>
		     <td class="${PamForm.formFieldMap.INV120.state.visibleString}"> 
		         <span style="${PamForm.formFieldMap.INV120.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV120.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV120.tooltip}">${PamForm.formFieldMap.INV120.label}</span>  
		     </td>
	         <td class="topBorder">
	             <html:text name="PamForm" title="${PamForm.formFieldMap.INV120.label}"  disabled ="${PamForm.formFieldMap.INV120.state.disabled}" 
	                     property="pamClientVO.answer(INV120)" maxlength="10" 
	                     styleId="INV120" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV120','INV120Icon'); return false;" onkeypress ="showCalendarEnterKey('INV120','INV120Icon',event);"
	                     styleId="INV120Icon"></html:img>
	         </td>
		 </tr>
		 <!--9. Earliest Date Reported to State:-->
		 <tr>
		     <td class="${PamForm.formFieldMap.INV121.state.visibleString}"> 
		         <span style="${PamForm.formFieldMap.INV121.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.INV121.errorStyleClass}"
		                 title="${PamForm.formFieldMap.INV121.tooltip}">${PamForm.formFieldMap.INV121.label}</span>  
		     </td>
	         <td class="topBorder">
	             <html:text name="PamForm" title="${PamForm.formFieldMap.INV121.label}"  disabled ="${PamForm.formFieldMap.INV121.state.disabled}" 
	                     property="pamClientVO.answer(INV121)" maxlength="10" 
	                     styleId="INV121" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV121','INV121Icon'); return false;" onkeypress ="showCalendarEnterKey('INV121','INV121Icon',event);" 
	                     styleId="INV121Icon"></html:img>
	         </td>
		 </tr>
		 <!-- Key Report Dates -->
	     <%= request.getAttribute("2058") == null ? "" :  request.getAttribute("2058") %>    
	</nedss:container>

<!-- SUBSECTION : Case Numbers: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Case Numbers" classType="subSect" >
	 <!--State Case I.D.-->
	     <tr>
	        <td class="fieldName"> 
	            <span style="${PamForm.formFieldMap.INV173.state.requiredIndClass}">*</span>
	            <span style="${PamForm.formFieldMap.INV173.errorStyleClass}" 
	                    title="${PamForm.formFieldMap.INV173.tooltip}"> 
	                ${PamForm.formFieldMap.INV173.label} 
	            </span> 
	        </td>
	        <td> 
                <html:text name="PamForm" disabled ="${PamForm.formFieldMap.INV173.state.disabled}" 
                        onkeyup="UpperCaseMask(this)" maxlength="15"
                        property="pamClientVO.answer(INV173)" size="17" styleId="INV173"
                        title="${PamForm.formFieldMap.INV173.label}" />	                    
	        </td>
	    </tr>
	    <!-- Case Numbers -->
	     <%= request.getAttribute("2062") == null ? "" :  request.getAttribute("2062") %>       
	</nedss:container>
	<!-- SUBSECTION : Reporting Organization: --> 
		<%@ include file="/pam/ext/ReportingOrganization.jsp" %>
	<!-- SUBSECTION : Reporting Provider: --> 
		<%@ include file="/pam/ext/ReportingProvider.jsp" %>	
</nedss:container>
<!-- SECTION : Clinical Information: --> 
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">

<!-- SUBSECTION: Physician -->	
 <div class='${PamForm.formFieldMap["2308"].state.visibleString}'>
	<%@ include file="/pam/ext/Physician.jsp" %>
</div>
<!-- SUBSECTION: Condition -->	
	<%@ include file="/pam/ext/Condition.jsp" %>

	<!--SUBSECTION : Signs and Symptoms: Rash:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Signs and Symptoms: Rash" classType="subSect" >
	 <!--12. Rash Onset Date:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR102.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR102.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR102.tooltip}">${PamForm.formFieldMap.VAR102.label}</span>  
		     </td>
		     <td>
	             <html:text name="PamForm" title="${PamForm.formFieldMap.VAR102.label}"  disabled ="${PamForm.formFieldMap.VAR102.state.disabled}" property="pamClientVO.answer(VAR102)" maxlength="10" 
	                     styleId="VAR102" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange=""/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR102','VAR102con'); return false;" onkeypress ="showCalendarEnterKey('VAR102','VAR102con',event);" 
	                     styleId="VAR102con"></html:img>
	         </td>
		 </tr>
		 <!--13. Rash Location:-->
		 <tr>
			 <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR103.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR103.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR103.tooltip}">${PamForm.formFieldMap.VAR103.label}</span>  
		     </td>
		     <td> 
	             <html:select title="${PamForm.formFieldMap.VAR103.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR103.state.disabled}" property="pamClientVO.answer(VAR103)" 
	                   styleId="VAR103" onchange="fireRule('VAR103', this)" >
	              <html:optionsCollection property="codedValue(VAR103)" value="key" label="value"/>
	         </html:select>
	        </td>
		 </tr>
		 <!--Specify Dermatome:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR104.state.requiredIndClass}">*</span>
		         <span  class="${PamForm.formFieldMap.VAR104.state.disabledString}"  id="VAR104L"  style="${PamForm.formFieldMap.VAR104.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR104.tooltip}">${PamForm.formFieldMap.VAR104.label}</span>  
		     </td>
		     <td> 
	             <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR104.state.disabled}" property="pamClientVO.answer(VAR104)" maxlength="100" size="25" styleId="VAR104"  onblur="fireRule('VAR104', this)" 
	                      title="${PamForm.formFieldMap.VAR104.label}"/>
	        </td>
		 </tr>
		 <!--Location First Noted:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR105.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR105.state.disabledString}"  id="VAR105L"  style="${PamForm.formFieldMap.VAR105.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR105.tooltip}">${PamForm.formFieldMap.VAR105.label}</span>  
		     </td>
		     <td>
	             <div class="multiSelectBlock">
	                  <i> (Use Ctrl to select more than one) </i> <br/>
	                  <html:select title="${PamForm.formFieldMap.VAR105.label}" property="pamClientVO.answerArray(VAR105)" 
	                          styleId="VAR105" 
	                           disabled ="${PamForm.formFieldMap.VAR105.state.disabled}" 	                          
	                          multiple="true" size="4"  onblur="fireRule('VAR105', this)" 
                              onchange="displaySelectedOptions(this, 'VAR105-selectedValues'); fireRule('VAR105', this);" >                             
	                      <html:optionsCollection property="codedValue(VAR105)" value="key" label="value"/>
	                  </html:select> 
	                  <br/>
	                  <div id="VAR105-selectedValues" style="margin:0.25em;">
	                     <b> Selected Values: </b>
	                  </div>
	              </div>       
	         </td>
		 </tr>
		 <!--Other Generalized Rash Location:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR106.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR106.state.disabledString}"  id="VAR106L"  style="${PamForm.formFieldMap.VAR106.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR106.tooltip}">${PamForm.formFieldMap.VAR106.label}</span>  
		     </td>
		     <td> 
	             <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR106.state.disabled}" property="pamClientVO.answer(VAR106)" maxlength="100" size="25"  styleId="VAR106"  onblur="fireRule('VAR106', this)" 
	                    title="${PamForm.formFieldMap.VAR106.label}"/>
	        </td>
		 </tr>
		 <!--14. Number of lesions in total::-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR100.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR100.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR100.tooltip}">${PamForm.formFieldMap.VAR100.label}</span>  
		     </td>
		     <td> 
			     <html:select title="${PamForm.formFieldMap.VAR100.label}"  name="PamForm" disabled ="${PamForm.formFieldMap.VAR100.state.disabled}" property="pamClientVO.answer(VAR100)" styleId="VAR100" onchange="fireRule('VAR100', this)" >
		             <html:optionsCollection property="codedValue(VAR100)" value="key" label="value"/>
		        </html:select>
	        </td>
		 </tr>
		 <!--15. Number of lesions (with< 50):-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR163.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR163.state.disabledString}"  id="VAR163L"  style="${PamForm.formFieldMap.VAR163.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR163.tooltip}">${PamForm.formFieldMap.VAR163.label}</span>  
		     </td>
		     <td> 
	             <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR163.state.disabled}" property="pamClientVO.answer(VAR163)" maxlength="2" size="5" styleId="VAR163"   title="${PamForm.formFieldMap.VAR163.label}" onkeyup="isNumericCharacterEntered(this)" onblur="fireRule('VAR163', this)"/>
	        </td>
		 </tr>
		<!--Macules (flat) present:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR107.state.requiredIndClass}">*</span>
		         <span  class="${PamForm.formFieldMap.VAR107.state.disabledString}"  id="VAR107L" style="${PamForm.formFieldMap.VAR107.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR107.tooltip}">${PamForm.formFieldMap.VAR107.label}</span>  
		     </td>
		     <td> 
			     <html:select title="${PamForm.formFieldMap.VAR107.label}"  name="PamForm" disabled ="${PamForm.formFieldMap.VAR107.state.disabled}" property="pamClientVO.answer(VAR107)" styleId="VAR107"   onchange="fireRule('VAR107', this)">
		              <html:optionsCollection property="codedValue(VAR107)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Number of Macules:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR108.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR108.state.disabledString}"  id="VAR108L" style="${PamForm.formFieldMap.VAR108.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR108.tooltip}">${PamForm.formFieldMap.VAR108.label}</span>  
		     </td>
		     <td> 
	             <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR108.state.disabled}" property="pamClientVO.answer(VAR108)" maxlength="2" size="5" styleId="VAR108" onblur="fireRule('VAR108', this)" 
	                    title="${PamForm.formFieldMap.VAR108.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        </td>
		 </tr>
		 <!--Papules (raised) present:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR109.state.requiredIndClass}">*</span>
		         <span  class="${PamForm.formFieldMap.VAR109.state.disabledString}"  id="VAR109L"  style="${PamForm.formFieldMap.VAR109.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR109.tooltip}">${PamForm.formFieldMap.VAR109.label}</span>  
		     </td>
		     <td> 
		     <!--TODO add the drop down forVAR109-->
			     <html:select title="${PamForm.formFieldMap.VAR109.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR109.state.disabled}" property="pamClientVO.answer(VAR109)" styleId="VAR109"  onchange="fireRule('VAR109', this)" >
		              <html:optionsCollection property="codedValue(VAR109)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Number of Papules:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR110.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR110.state.disabledString}"  id="VAR110L" style="${PamForm.formFieldMap.VAR110.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR110.tooltip}">${PamForm.formFieldMap.VAR110.label}</span>  
		     </td>
		     <td> 
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR110.state.disabled}"  property="pamClientVO.answer(VAR110)" maxlength="2" size="5" styleId="VAR110" onblur="fireRule('VAR110', this)"
		     		title="${PamForm.formFieldMap.VAR110.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        </td>
		 </tr>
		 <!--Vesicles (fluid) present-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR111.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR111.state.disabledString}"  id="VAR111L" style="${PamForm.formFieldMap.VAR111.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR111.tooltip}">${PamForm.formFieldMap.VAR111.label}</span>  
		     </td>
		     <td> 
			     <html:select title="${PamForm.formFieldMap.VAR111.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR111.state.disabled}" property="pamClientVO.answer(VAR111)" styleId="VAR111"  onchange="fireRule('VAR111', this)">
		              <html:optionsCollection property="codedValue(VAR111)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Number of Vesicles-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR112.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR112.state.disabledString}"  id="VAR112L" style="${PamForm.formFieldMap.VAR112.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR112.tooltip}">${PamForm.formFieldMap.VAR112.label}</span>  
		     </td>
		     <td> 
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR112.state.disabled}" property="pamClientVO.answer(VAR112)" maxlength="2" size="5" styleId="VAR112" onblur="fireRule('VAR112', this)"
		     		title="${PamForm.formFieldMap.VAR112.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        </td>
		 </tr>
		 <!--16. Character of Lesions (all categories-1 to >500):-->
		 <tr>
		 	<td class="fieldName">
		 		16. Character of Lesions(all categories-1 to >500):
		 	</td>
		 	<td>
		 	
		 	</td>
		 </tr>
		 <!--Mostly macular/papular-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR113.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR113.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR113.tooltip}">${PamForm.formFieldMap.VAR113.label}</span>  
		     </td>
		     <td> 
			     <html:select title="${PamForm.formFieldMap.VAR113.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR113.state.disabled}" property="pamClientVO.answer(VAR113)" styleId="VAR113" >
		              <html:optionsCollection property="codedValue(VAR113)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Mostly vesicular-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR114.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR114.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR114.tooltip}">${PamForm.formFieldMap.VAR114.label}</span>  
		     </td>
		     <td> 
		     <!--TODO add the drop down for VAR114-->
			     <html:select title="${PamForm.formFieldMap.VAR114.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR114.state.disabled}" property="pamClientVO.answer(VAR114)" styleId="VAR114">
		              <html:optionsCollection property="codedValue(VAR114)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Hemorrhagic:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR115.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR115.state.disabledString}"  id="VAR115L" style="${PamForm.formFieldMap.VAR115.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR115.tooltip}">${PamForm.formFieldMap.VAR115.label}</span>  
		     </td>
		     <td> 
		     <!--TODO add the drop down for VAR115-->
			     <html:select title="${PamForm.formFieldMap.VAR115.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR115.state.disabled}" property="pamClientVO.answer(VAR115)" styleId="VAR115" >
		              <html:optionsCollection property="codedValue(VAR115)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Itchy:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR116.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR116.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR116.tooltip}">${PamForm.formFieldMap.VAR116.label}</span>  
		     </td>
		     <td> 
		     <!--TODO add the drop down for VAR116-->
			     <html:select title="${PamForm.formFieldMap.VAR116.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR116.state.disabled}"  property="pamClientVO.answer(VAR116)" styleId="VAR116" >
		              <html:optionsCollection property="codedValue(VAR116)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Scabs:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR117.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR117.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR117.tooltip}">${PamForm.formFieldMap.VAR117.label}</span>  
		     </td>
		     <td> 
		     <!--TODO add the drop down for VAR117-->
			     <html:select title="${PamForm.formFieldMap.VAR117.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR117.state.disabled}" property="pamClientVO.answer(VAR117)" styleId="VAR117"> 
		              <html:optionsCollection property="codedValue(VAR117)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Crops/Waves:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR118.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR118.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR118.tooltip}">${PamForm.formFieldMap.VAR118.label}</span>  
		     </td>
		     <td> 
			     <html:select title="${PamForm.formFieldMap.VAR118.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR118.state.disabled}" property="pamClientVO.answer(VAR118)" styleId="VAR118" >
		              <html:optionsCollection property="codedValue(VAR118)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--17. Did the rash crust:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR119.state.requiredIndClass}">*</span>
		         <span style="${PamForm.formFieldMap.VAR119.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR119.tooltip}">${PamForm.formFieldMap.VAR119.label}</span>  
		     </td>
		     <td> 
			     <html:select title="${PamForm.formFieldMap.VAR119.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR119.state.disabled}" property="pamClientVO.answer(VAR119)" styleId="VAR119"  onchange="fireRule('VAR119', this)">
		              <html:optionsCollection property="codedValue(VAR119)" value="key" label="value"/>
		         </html:select>
	        </td>
		 </tr>
		 <!--Number of days until all lesions crusted over:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR120.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR120.state.disabledString}"  id="VAR120L" style="${PamForm.formFieldMap.VAR120.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR120.tooltip}">${PamForm.formFieldMap.VAR120.label}</span>  
		     </td>
		     <td> 
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR120.state.disabled}" property="pamClientVO.answer(VAR120)" maxlength="3" size="5" styleId="VAR120"  onblur="fireRule('VAR120', this)"
		     		title="${PamForm.formFieldMap.VAR120.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        </td>
		 </tr>
		 <!--Number of days rash lasted:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR121.state.requiredIndClass}">*</span>
		         <span class="${PamForm.formFieldMap.VAR121.state.disabledString}"  id="VAR121L" style="${PamForm.formFieldMap.VAR121.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR121.tooltip}">${PamForm.formFieldMap.VAR121.label}</span>  
		     </td>
		     <td> 
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR121.state.disabled}" property="pamClientVO.answer(VAR121)" maxlength="3" size="5" styleId="VAR121"  onblur="fireRule('VAR121', this)"
		     		title="${PamForm.formFieldMap.VAR121.label}" onkeyup="isNumericCharacterEntered(this)"/>
	        </td>
		 </tr>
		 <!-- Signs And Symptoms: Rash: -->
	     <%= request.getAttribute("2068") == null ? "" :  request.getAttribute("2068") %>    
	</nedss:container>
	<!--SUBSECTION : Signs and Symptoms: Fever:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Signs and Symptoms: Fever" classType="subSect" >
	<!-- 18. Did the patient have a fever -->
    <tr>
        <td class="fieldName"> 
            <span style="${PamForm.formFieldMap.VAR122.state.requiredIndClass}">*</span>
            <span style="${PamForm.formFieldMap.VAR122.errorStyleClass}" 
                    title="${PamForm.formFieldMap.VAR122.tooltip}"> 
                ${PamForm.formFieldMap.VAR122.label} 
            </span> 
        </td>
        <td> 
            <html:select title="${PamForm.formFieldMap.VAR122.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR122.state.disabled}" property="pamClientVO.answer(VAR122)" styleId="VAR122" onchange="fireRule('VAR122', this)"> 
                 <html:optionsCollection property="codedValue(VAR122)" value="key" label="value"/>
            </html:select>
        </td>
    </tr>
    <!--19. Date of Fever Onset -->
    <tr>
        <td class="fieldName"> 
            <span style="${PamForm.formFieldMap.VAR123.state.requiredIndClass}">*</span>
            <span  class="${PamForm.formFieldMap.VAR123.state.disabledString}"  id="VAR123L" style="${PamForm.formFieldMap.VAR123.errorStyleClass}" 
                    title="${PamForm.formFieldMap.VAR123.tooltip}"> 
                ${PamForm.formFieldMap.VAR123.label} 
            </span> 
        </td>
        <td> 
           <html:text title="${PamForm.formFieldMap.VAR123.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR123.state.disabled}"  property="pamClientVO.answer(VAR123)" maxlength="10" 
	                     styleId="VAR123"  onblur="fireRule('VAR123', this)" onchange="fireRule('VAR123', this)" onkeyup="DateMask(this,null,event)" size="10"  
	                    />
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR123','VAR123con'); return false;" onkeypress ="showCalendarEnterKey('VAR123','VAR123con',event);" 
	                     styleId="VAR123con"></html:img>
        </td>
    </tr>
 <!--20. Highest measured temperature:-->
		 <tr>
		     <td class="fieldName"> 
		         <span style="${PamForm.formFieldMap.VAR124.state.requiredIndClass}">*</span>
		         <span  class="${PamForm.formFieldMap.VAR124.state.disabledString}"  id="VAR124L"  style="${PamForm.formFieldMap.VAR124.errorStyleClass}"
		                 title="${PamForm.formFieldMap.VAR124.tooltip}">${PamForm.formFieldMap.VAR124.label}</span>  
		     </td>
		     <td> 
		     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR124.state.disabled}"  property="pamClientVO.answer(VAR124)" maxlength="6" size="7" styleId="VAR124"  onblur="fireRule('VAR124', this)"
		     		title="${PamForm.formFieldMap.VAR124.label}" onkeyup="isDecimalCharacterEntered(this)"/>
		     		
			     	 <html:select title="${PamForm.formFieldMap.VAR124.label}"  name="PamForm" disabled ="${PamForm.formFieldMap.VAR211.state.disabled}" property="pamClientVO.answer(VAR211)" styleId="VAR211"  onchange="fireRule('VAR211', this)" >
		                 <html:optionsCollection property="codedValue(VAR211)" value="key" label="value"/>
		              </html:select>
			 	</td>
	 </tr>
		<!--21. Total number of days with fever:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR125.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR125.state.disabledString}"  id="VAR125L" style="${PamForm.formFieldMap.VAR125.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR125.tooltip}">${PamForm.formFieldMap.VAR125.label}</span>  
	     </td>
	     <td> 
	     	<html:text name="PamForm"  disabled ="${PamForm.formFieldMap.VAR125.state.disabled}" property="pamClientVO.answer(VAR125)" maxlength="3" size="5" styleId="VAR125" onblur="fireRule('VAR125', this)"
	     		title="${PamForm.formFieldMap.VAR125.label}" onkeyup="isNumericCharacterEntered(this)"/></td>
	 	</td>
	 </tr>
	 <!-- Signs And Symptoms: Fever: -->
     <%= request.getAttribute("2213") == null ? "" :  request.getAttribute("2213") %>    
	</nedss:container>
	<!--SUBSECTION : Signs and Symptoms:Immunocompromised:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Signs and Symptoms: Immunocompromised" classType="subSect" >
	<!--22. Is patient immunocompromised due to medical condition or treatment-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR126.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR126.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR126.tooltip}">${PamForm.formFieldMap.VAR126.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR126.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR126.state.disabled}" property="pamClientVO.answer(VAR126)" styleId="VAR126"  onchange="fireRule('VAR126', this)" >
                 <html:optionsCollection property="codedValue(VAR126)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Medical Condition or Treatment:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR127.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR127.state.disabledString}"  id="VAR127L" style="${PamForm.formFieldMap.VAR127.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR127.tooltip}">${PamForm.formFieldMap.VAR127.label}</span>  
	     </td>
	     <td> 
	     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR127.state.disabled}" property="pamClientVO.answer(VAR127)" maxlength="100" size="25" styleId="VAR127" onblur="fireRule('VAR127', this)"
	     		title="${PamForm.formFieldMap.VAR127.label}"/></td>
	 	</td>
	 </tr>
	 <!-- Signs And Symptoms: Immunocompromised: -->
     <%= request.getAttribute("2214") == null ? "" :  request.getAttribute("2214") %>    
	</nedss:container>
	
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Complications" classType="subSect" >
	<!--23. Did the patient visit a healthcare provider during this illness:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR128.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR128.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR128.tooltip}">${PamForm.formFieldMap.VAR128.label}</span>  
	     </td>
	     <td> 
	     	 <html:select name="PamForm" title="${PamForm.formFieldMap.VAR128.label}" disabled ="${PamForm.formFieldMap.VAR128.state.disabled}" property="pamClientVO.answer(VAR128)" styleId="VAR128" >
                 <html:optionsCollection property="codedValue(VAR128)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--24. Did the patient develop any complications that were diagnosed by a healthcare provider::-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR129.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR129.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR129.tooltip}">${PamForm.formFieldMap.VAR129.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR129.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR129.state.disabled}" property="pamClientVO.answer(VAR129)" styleId="VAR129" onchange="fireRule('VAR129', this)" >
                 <html:optionsCollection property="codedValue(VAR129)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Skin/Soft Tissue Infection:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR130.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR130.state.disabledString}"  id="VAR130L" style="${PamForm.formFieldMap.VAR130.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR130.tooltip}">${PamForm.formFieldMap.VAR130.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR130.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR130.state.disabled}" property="pamClientVO.answer(VAR130)" styleId="VAR130"  onchange="fireRule('VAR130', this)" >
                 <html:optionsCollection property="codedValue(VAR130)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Cerebellitis/Ataxia:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR131.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR131.state.disabledString}"  id="VAR131L" style="${PamForm.formFieldMap.VAR131.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR131.tooltip}">${PamForm.formFieldMap.VAR131.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR131.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR131.state.disabled}" property="pamClientVO.answer(VAR131)" styleId="VAR131" onchange="fireRule('VAR131', this)" >
                 <html:optionsCollection property="codedValue(VAR131)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Encephalitis:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR132.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR132.state.disabledString}"  id="VAR132L" style="${PamForm.formFieldMap.VAR132.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR132.tooltip}">${PamForm.formFieldMap.VAR132.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR132.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR132.state.disabled}" property="pamClientVO.answer(VAR132)" styleId="VAR132"  onchange="fireRule('VAR132', this)" >
                 <html:optionsCollection property="codedValue(VAR132)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Dehydration:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR133.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR133.state.disabledString}"  id="VAR133L" style="${PamForm.formFieldMap.VAR133.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR133.tooltip}">${PamForm.formFieldMap.VAR133.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR133.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR133.state.disabled}" property="pamClientVO.answer(VAR133)" styleId="VAR133" onchange="fireRule('VAR133', this)" >
                 <html:optionsCollection property="codedValue(VAR133)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Hemorrhagic Condition:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR134.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR134.state.disabledString}"  id="VAR134L" style="${PamForm.formFieldMap.VAR134.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR134.tooltip}">${PamForm.formFieldMap.VAR134.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR134.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR134.state.disabled}"  property="pamClientVO.answer(VAR134)" styleId="VAR134" onchange="fireRule('VAR134', this)">
                 <html:optionsCollection property="codedValue(VAR134)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Pneumonia:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR135.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR135.state.disabledString}"  id="VAR135L"  style="${PamForm.formFieldMap.VAR135.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR135.tooltip}">${PamForm.formFieldMap.VAR135.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR135.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR135.state.disabled}" property="pamClientVO.answer(VAR135)" styleId="VAR135"  onchange="fireRule('VAR135', this)" >
                 <html:optionsCollection property="codedValue(VAR135)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--How was pneumonia diagnosed:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR136.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR136.state.disabledString}"  id="VAR136L" style="${PamForm.formFieldMap.VAR136.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR136.tooltip}">${PamForm.formFieldMap.VAR136.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR136.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR136.state.disabled}" property="pamClientVO.answer(VAR136)" styleId="VAR136"  onchange="fireRule('VAR136', this)">
                 <html:optionsCollection property="codedValue(VAR136)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Other Complications::-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR137.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR137.state.disabledString}"  id="VAR137L" style="${PamForm.formFieldMap.VAR137.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR137.tooltip}">${PamForm.formFieldMap.VAR137.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR137.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR137.state.disabled}" property="pamClientVO.answer(VAR137)" styleId="VAR137"  onchange="fireRule('VAR137', this)">
                 <html:optionsCollection property="codedValue(VAR137)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other Complications:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR138.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR138.state.disabledString}"  id="VAR138L"style="${PamForm.formFieldMap.VAR138.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR138.tooltip}">${PamForm.formFieldMap.VAR138.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR138.state.disabled}"  property="pamClientVO.answer(VAR138)" maxlength="100" size="25" styleId="VAR138" onblur="fireRule('VAR138', this)"
	     		title="${PamForm.formFieldMap.VAR138.label}"/></td>
	 	</td>
	 </tr>
	 <!-- Complications: -->
     <%= request.getAttribute("2099") == null ? "" :  request.getAttribute("2099") %>    
	</nedss:container>
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Treatment" classType="subSect" >
	<!--25. Was the patient treated with acyvlovir, famvir, or any licensed antiviral for this illness:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR139.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR139.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR139.tooltip}">${PamForm.formFieldMap.VAR139.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR139.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR139.state.disabled}" property="pamClientVO.answer(VAR139)" styleId="VAR139" onchange="fireRule('VAR139',this)" >
                 <html:optionsCollection property="codedValue(VAR139)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Name of Medication:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR140.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR140.state.disabledString}"  id="VAR140L"  style="${PamForm.formFieldMap.VAR140.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR140.tooltip}">${PamForm.formFieldMap.VAR140.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR140.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR140.state.disabled}" property="pamClientVO.answer(VAR140)" styleId="VAR140"  onchange="fireRule('VAR140', this)" >
                 <html:optionsCollection property="codedValue(VAR140)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Other Medication:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR210.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR210.state.disabledString}"  id="VAR210L" style="${PamForm.formFieldMap.VAR210.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR210.tooltip}">${PamForm.formFieldMap.VAR210.label}</span>  
	     </td>
	     <td> 
	     	<html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR210.state.disabled}" property="pamClientVO.answer(VAR210)" maxlength="100" size="25" styleId="VAR210"  onblur="fireRule('VAR210', this)" 
	     		title="${PamForm.formFieldMap.VAR210.label}"/></td>
	 	</td>
	 </tr>
<!--Start Date of Medication -->
    <tr>
        <td class="fieldName"> 
            <span style="${PamForm.formFieldMap.VAR141.state.requiredIndClass}">*</span>
            <span class="${PamForm.formFieldMap.VAR141.state.disabledString}"  id="VAR141L" style="${PamForm.formFieldMap.VAR141.errorStyleClass}" 
                    title="${PamForm.formFieldMap.VAR141.tooltip}"> 
                ${PamForm.formFieldMap.VAR141.label} 
            </span> 
        </td>
        <td> 
           <html:text name="PamForm" title="${PamForm.formFieldMap.VAR141.label}" disabled ="${PamForm.formFieldMap.VAR141.state.disabled}" property="pamClientVO.answer(VAR141)" maxlength="10" 
	                     styleId="VAR141"  onblur="fireRule('VAR141', this)"  onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR141', this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR141','VAR141con'); return false;"  onkeypress ="showCalendarEnterKey('VAR141','VAR141con',event);" 
	                     styleId="VAR141con"></html:img>
        </td>
    </tr>
<!--Stop Date of Medication:-->
    <tr>
        <td class="fieldName"> 
            <span style="${PamForm.formFieldMap.VAR142.state.requiredIndClass}">*</span>
            <span class="${PamForm.formFieldMap.VAR142.state.disabledString}"  id="VAR142L" style="${PamForm.formFieldMap.VAR142.errorStyleClass}" 
                    title="${PamForm.formFieldMap.VAR142.tooltip}"> 
                ${PamForm.formFieldMap.VAR142.label} 
            </span> 
        </td>
        <td> 
           <html:text name="PamForm" title="${PamForm.formFieldMap.VAR142.label}" disabled ="${PamForm.formFieldMap.VAR142.state.disabled}" property="pamClientVO.answer(VAR142)" maxlength="10" 
	                     styleId="VAR142" onblur="fireRule('VAR142', this)" 
 onkeyup="DateMask(this,null,event)" size="10"  onchange="fireRule('VAR142', this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR142','VAR142con'); return false;" onkeypress ="showCalendarEnterKey('VAR142','VAR142con',event);" 
	                     styleId="VAR142con"></html:img>
        </td>
    </tr>
    <!-- Treatment: -->
    <%= request.getAttribute("2215") == null ? "" :  request.getAttribute("2215") %>    
	</nedss:container>
	 <!-- Hospitalization: -->
	 	<%@ include file="/pam/ext/Hospital.jsp" %>
	<!---SUBSECT :Death:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Death" classType="subSect" >
	<!--27. Did the patient die from varicella or complications (including secondary infection) associated with varicella:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV145.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.INV145.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV145.tooltip}">${PamForm.formFieldMap.INV145.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR145.label}" name="PamForm" disabled ="${PamForm.formFieldMap.INV145.state.disabled}"  property="pamClientVO.answer(INV145)" styleId="INV145" onchange="displayDeathWS('INV145', 'resultSpanRowId');fireRule('INV145', this);">
                 <html:optionsCollection property="codedValue(INV145)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Date of Death:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.INV146.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.INV146.state.disabledString}"  id="INV146L"  style="${PamForm.formFieldMap.INV146.errorStyleClass}"
	                 title="${PamForm.formFieldMap.INV146.tooltip}">${PamForm.formFieldMap.INV146.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR146.label}" disabled ="${PamForm.formFieldMap.INV146.state.disabled}" property="pamClientVO.answer(INV146)" maxlength="10" 
	                     styleId="INV146" onblur="fireRule('INV146', this);" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('INV146', this);"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV146','INV146con'); return false;" onkeypress ="showCalendarEnterKey('INV146','INV146con',event);" 
	                     styleId="INV146con"></html:img>
	 	</td>
	 </tr>
<!--Autopsy performed:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR143.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR143.state.disabledString}"  id="VAR143L"  style="${PamForm.formFieldMap.VAR143.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR143.tooltip}">${PamForm.formFieldMap.VAR143.label}</span>  
	     </td>
	     <td> 
	     	   <html:select title="${PamForm.formFieldMap.VAR143.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR143.state.disabled}" property="pamClientVO.answer(VAR143)" styleId="VAR143" onchange="fireRule('VAR143', this);">
                 <html:optionsCollection property="codedValue(VAR143)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Cause of death:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR144.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR144.state.disabledString}"  id="VAR144L" style="${PamForm.formFieldMap.VAR144.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR144.tooltip}">${PamForm.formFieldMap.VAR144.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR144.state.disabled}" property="pamClientVO.answer(VAR144)" maxlength="100" size="25" styleId="VAR144" onblur="fireRule('VAR144', this);"
	     		title="${PamForm.formFieldMap.VAR144.label}"/></td>
	 	</td>
	 </tr>
	 <!--Click here to access varicella death worksheet -->	 
		<tr id="resultSpanRowId">
			<td class="fieldName" > 
			    <span title="${PamForm.formFieldMap["2124"].tooltip}">${PamForm.formFieldMap["2124"].label}</span>  
			</td>
			<td valign=bottom>
				<span>
					<%	
					String hyperLnk = "";
					String lnkUrl = "";
					try
					{
						PamForm frm = (PamForm) request.getSession().getAttribute("PamForm");
						Object obj = frm.getFormFieldMap().get("2124");
						FormField ff = (FormField) obj;
						String tooltip= ff.getTooltip();
						hyperLnk = tooltip.substring(0,tooltip.indexOf("("));
						lnkUrl = tooltip.substring(tooltip.indexOf("(")+1, tooltip.indexOf(")"));
						request.setAttribute("hyperLnk",hyperLnk);
						request.setAttribute("lnkUrl",lnkUrl);
					}
					catch (Exception e)
					{
					    e.printStackTrace();
					}
					%>
				
					<a href="${fn:escapeXml(lnkUrl)}" target="_blank">${fn:escapeXml(hyperLnk)} </a>
				</span>
			</td>
		</tr>

	 <!-- Death: -->
     <%= request.getAttribute("2217") == null ? "" :  HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("2217"))) %>    
	</nedss:container>
	</nedss:container>

	<!--SECTION:Laboratory Information-->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">

<!-- SUBSECTION : Laboratory Testing:: --> 
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Laboratory Testing" classType="subSect" >
<!--28. Was laboratory testing done for varicella::-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR170.state.requiredIndClass}">*</span>
	         <span style="${PamForm.formFieldMap.VAR170.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR170.tooltip}">${PamForm.formFieldMap.VAR170.label}</span>  
	     </td>
	     <td> 
	     	   <html:select title="${PamForm.formFieldMap.VAR170.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR170.state.disabled}"  property="pamClientVO.answer(VAR170)" styleId="VAR170" onchange="fireRule('VAR170', this)" >
                 <html:optionsCollection property="codedValue(VAR170)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!-- Laboratory Testing: -->
     <%= request.getAttribute("2126") == null ? "" :  request.getAttribute("2126") %>    
	</nedss:container>
	<!---SUBSECT :Direct Fluorescent Antibody (DFA) Testing:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Direct Fluorescent Antibody (DFA) Testing" classType="subSect" >
	<!--29. Was direct fluorescent antibody (DFA) testing performed:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR171.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR171.state.disabledString}"  id="VAR171L" style="${PamForm.formFieldMap.VAR171.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR171.tooltip}">${PamForm.formFieldMap.VAR171.label}</span>  
	     </td>
	     <td> 
	     	   <html:select name="PamForm" title="${PamForm.formFieldMap.VAR171.label}" disabled ="${PamForm.formFieldMap.VAR171.state.disabled}" property="pamClientVO.answer(VAR171)" styleId="VAR171" onchange="fireRule('VAR171', this)">
                 <html:optionsCollection property="codedValue(VAR171)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Date of DFA:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR172.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR172.state.disabledString}"  id="VAR172L" style="${PamForm.formFieldMap.VAR172.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR172.tooltip}">${PamForm.formFieldMap.VAR172.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR172.label}" disabled ="${PamForm.formFieldMap.VAR172.state.disabled}" property="pamClientVO.answer(VAR172)" maxlength="10" 
	                     styleId="VAR172"  onblur="fireRule('VAR172', this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR172', this)" />
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR172','VAR172con'); return false;"  onkeypress ="showCalendarEnterKey('VAR172','VAR172con',event);" 
	                     styleId="VAR172con"></html:img>
	 	</td>
	 </tr>
<!--DFA Result:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR173.state.requiredIndClass}">*</span>
	         <span   class="${PamForm.formFieldMap.VAR173.state.disabledString}"  id="VAR173L" style="${PamForm.formFieldMap.VAR173.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR173.tooltip}">${PamForm.formFieldMap.VAR173.label}</span>  
	     </td>
	     <td> 
	     	   <html:select title="${PamForm.formFieldMap.VAR173.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR173.state.disabled}" property="pamClientVO.answer(VAR173)" styleId="VAR173" onchange="fireRule('VAR173', this)"  >
                 <html:optionsCollection property="codedValue(VAR173)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!-- Direct Fluorescent Antibody (DFA) Testing: -->
     <%= request.getAttribute("2128") == null ? "" :  request.getAttribute("2128") %>    
	</nedss:container>
	<!--SUBSECTION: PCR Testing:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="PCR Testing" classType="subSect" >
	
	<!--30. PCR Specimen:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR174.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR174.state.disabledString}"  id="VAR174L" style="${PamForm.formFieldMap.VAR174.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR174.tooltip}">${PamForm.formFieldMap.VAR174.label}</span>  
	     </td>
	     <td> 
	     	   <html:select title="${PamForm.formFieldMap.VAR174.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR174.state.disabled}" property="pamClientVO.answer(VAR174)" styleId="VAR174" onchange="fireRule('VAR174', this)"> 
                 <html:optionsCollection property="codedValue(VAR174)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Date of PCR Specimen:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR175.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR175.state.disabledString}"  id="VAR175L" style="${PamForm.formFieldMap.VAR175.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR175.tooltip}">${PamForm.formFieldMap.VAR175.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR175.label}" disabled ="${PamForm.formFieldMap.VAR175.state.disabled}" property="pamClientVO.answer(VAR175)" maxlength="10" 
	                     styleId="VAR175"   onblur="fireRule('VAR175', this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR175', this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR175','VAR175con'); return false;"  onkeypress ="showCalendarEnterKey('VAR175','VAR175con',event);" 
	                     styleId="VAR175con"></html:img>
	 	</td>
	 </tr>
<!--Source of PCR Specimen:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR176.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR176.state.disabledString}"  id="VAR176L" style="${PamForm.formFieldMap.VAR176.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR176.tooltip}">${PamForm.formFieldMap.VAR176.label}</span>  
	     </td>
	     <td>
	         <div class="multiSelectBlock">
	             <i> (Use Ctrl to select more than one) </i> <br/>  
	             <html:select title="${PamForm.formFieldMap.VAR176.label}" property="pamClientVO.answerArray(VAR176)" disabled ="${PamForm.formFieldMap.VAR176.state.disabled}" 
	                     styleId="VAR176" 
	                     multiple="true" size="4"
	                     onchange="displaySelectedOptions(this, 'VAR176-selectedValues'); fireRule('VAR176', this);">
	                 <!-- FIXME : verify property value passed -->        
	                 <html:optionsCollection property="codedValue(VAR176)" value="key" label="value"/>
	             </html:select> 
	             <br/>
	             <div id="VAR176-selectedValues" style="margin:0.25em;">
	                <b> Selected Values: </b>
	             </div>
	         </div>                               
	     </td>
	 </tr>
<!--Specify Other PCR Source:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR177.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR177.state.disabledString}"  id="VAR177L" style="${PamForm.formFieldMap.VAR177.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR177.tooltip}">${PamForm.formFieldMap.VAR177.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR177.state.disabled}" property="pamClientVO.answer(VAR177)" maxlength="100" size="25" styleId="VAR177"  onblur="fireRule('VAR177', this)"
	     		title="${PamForm.formFieldMap.VAR177.label}"/> 
	 	</td>
	 </tr>

<!--PCR Result:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR178.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR178.state.disabledString}"  id="VAR178L" style="${PamForm.formFieldMap.VAR178.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR178.tooltip}">${PamForm.formFieldMap.VAR178.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR178.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR178.state.disabled}" property="pamClientVO.answer(VAR178)" styleId="VAR178"  onchange="fireRule('VAR178',this)" >
                 <html:optionsCollection property="codedValue(VAR178)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other PCR Result:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR179.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR179.state.disabledString}"  id="VAR179L" style="${PamForm.formFieldMap.VAR179.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR179.tooltip}">${PamForm.formFieldMap.VAR179.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR179.state.disabled}" property="pamClientVO.answer(VAR179)" maxlength="100" size="25" styleId="VAR179" onblur="fireRule('VAR179', this)"
	     		title="${PamForm.formFieldMap.VAR179.label}"/> 
	 	</td>
	 </tr>
	 <!-- PCR Testing: -->
     <%= request.getAttribute("2132") == null ? "" :  request.getAttribute("2132") %>   
	</nedss:container>
	<!--SUBSECTION:Culture Testing::-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Culture Testing" classType="subSect" >
	<!--31. Culture Performed:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR180.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR180.state.disabledString}"  id="VAR180L" style="${PamForm.formFieldMap.VAR180.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR180.tooltip}">${PamForm.formFieldMap.VAR180.label}</span>  
	     </td>
	     <td> 
	     	 <html:select title="${PamForm.formFieldMap.VAR180.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR180.state.disabled}" property="pamClientVO.answer(VAR180)" styleId="VAR180" onchange="fireRule('VAR180',this)" >
                 <html:optionsCollection property="codedValue(VAR180)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Date of Culture Specimen:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR181.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR181.state.disabledString}"  id="VAR181L" style="${PamForm.formFieldMap.VAR181.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR181.tooltip}">${PamForm.formFieldMap.VAR181.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR181.label}" disabled ="${PamForm.formFieldMap.VAR181.state.disabled}" property="pamClientVO.answer(VAR181)" maxlength="10" 
	                     styleId="VAR181" onblur="fireRule('VAR181',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR181',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR181','VAR181con'); return false;"  onkeypress ="showCalendarEnterKey('VAR181','VAR181con',event);" 
	                     styleId="VAR181con"></html:img>
	 	</td>
	 </tr>
<!--Culture Result:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR182.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR182.state.disabledString}"  id="VAR182L" style="${PamForm.formFieldMap.VAR182.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR182.tooltip}">${PamForm.formFieldMap.VAR182.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR182.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR182.state.disabled}" property="pamClientVO.answer(VAR182)" styleId="VAR182" onchange="fireRule('VAR182',this)"  >
                 <html:optionsCollection property="codedValue(VAR182)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!-- Culture Testing: -->
     <%= request.getAttribute("2139") == null ? "" :  request.getAttribute("2139") %>   
	</nedss:container>
<!--SUBSECTION:Other Testing:::-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Other Testing" classType="subSect" >
	<!--32. Was other laboratory testing done:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR183.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR183.state.disabledString}"  id="VAR183L" style="${PamForm.formFieldMap.VAR183.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR183.tooltip}">${PamForm.formFieldMap.VAR183.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR183.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR183.state.disabled}" property="pamClientVO.answer(VAR183)" styleId="VAR183" onchange="fireRule('VAR183',this)" >
                 <html:optionsCollection property="codedValue(VAR183)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>

<!--Specify Other Test:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR184.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR184.state.disabledString}"  id="VAR184L" style="${PamForm.formFieldMap.VAR184.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR184.tooltip}">${PamForm.formFieldMap.VAR184.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR184.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR184.state.disabled}" property="pamClientVO.answer(VAR184)" styleId="VAR184" onchange="fireRule('VAR184',this)" >
                 <html:optionsCollection property="codedValue(VAR184)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Date of Other Test:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR185.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR185.state.disabledString}"  id="VAR185L" style="${PamForm.formFieldMap.VAR185.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR185.tooltip}">${PamForm.formFieldMap.VAR185.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR185.label}" disabled ="${PamForm.formFieldMap.VAR185.state.disabled}" property="pamClientVO.answer(VAR185)" maxlength="10" 
	                     styleId="VAR185" onblur="fireRule('VAR185',this)" onchange="fireRule('VAR185',this)" onkeyup="DateMask(this,null,event)" size="10"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR185','VAR185con'); return false;" onkeypress ="showCalendarEnterKey('VAR185','VAR185con',event);" 
	                     styleId="VAR185con"></html:img>
	 	</td>
	 </tr>
<!--Other Lab Test Result:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR186.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR186.state.disabledString}"  id="VAR186L" style="${PamForm.formFieldMap.VAR186.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR186.tooltip}">${PamForm.formFieldMap.VAR186.label}</span>  
	     </td>
	     <td> 
	     	<html:select title="${PamForm.formFieldMap.VAR186.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR186.state.disabled}" property="pamClientVO.answer(VAR186)" styleId="VAR186" onchange="fireRule('VAR186',this)" >
                 <html:optionsCollection property="codedValue(VAR186)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Other Test Result Value:-->
	 <tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR187.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR187.state.disabledString}"  id="VAR187L" style="${PamForm.formFieldMap.VAR187.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR187.tooltip}">${PamForm.formFieldMap.VAR187.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR187.state.disabled}" property="pamClientVO.answer(VAR187)" maxlength="100" size="25"   styleId="VAR187"  onblur="fireRule('VAR187',this)" 
	     		title="${PamForm.formFieldMap.VAR187.label}"/> 
	 	</td>
	 </tr>
	 <!-- Other Testing: -->
     <%= request.getAttribute("2143") == null ? "" :  request.getAttribute("2143") %>   
	</nedss:container>
	<!--SUBSECTION : Serology Testing:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Serology Testing" classType="subSect" >
	<!--33. Serology performed-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR188.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR188.state.disabledString}"  id="VAR188L"  style="${PamForm.formFieldMap.VAR188.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR188.tooltip}">${PamForm.formFieldMap.VAR188.label}</span>  
	     </td>
	     <td> 
	     	<html:select title="${PamForm.formFieldMap.VAR188.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR188.state.disabled}"  property="pamClientVO.answer(VAR188)" styleId="VAR188"  onchange="fireRule('VAR188',this)"> 
                 <html:optionsCollection property="codedValue(VAR188)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!-- Serology Testing: -->
     <%= request.getAttribute("2149") == null ? "" :  request.getAttribute("2149") %>  
	</nedss:container>
	<!--SUBSECTION : IgM Testing:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="IgM Testing" classType="subSect" >
	<!--34. IgM performed-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR189.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR189.state.disabledString}"  id="VAR189L"  style="${PamForm.formFieldMap.VAR189.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR189.tooltip}">${PamForm.formFieldMap.VAR189.label}</span>  
	     </td>
	     <td> 
	     	<html:select title="${PamForm.formFieldMap.VAR189.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR189.state.disabled}" property="pamClientVO.answer(VAR189)" styleId="VAR189" onchange="fireRule('VAR189',this)">
                 <html:optionsCollection property="codedValue(VAR189)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Type of IgM Test-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR190.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR190.state.disabledString}"  id="VAR190L" style="${PamForm.formFieldMap.VAR190.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR190.tooltip}">${PamForm.formFieldMap.VAR190.label}</span>  
	     </td>
	     <td> 
	     	<html:select title="${PamForm.formFieldMap.VAR190.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR190.state.disabled}" property="pamClientVO.answer(VAR190)" styleId="VAR190" onchange="fireRule('VAR190',this)">
                 <html:optionsCollection property="codedValue(VAR190)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other IgM Test:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR191.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR191.state.disabledString}"  id="VAR191L" style="${PamForm.formFieldMap.VAR191.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR191.tooltip}">${PamForm.formFieldMap.VAR191.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR191.state.disabled}" property="pamClientVO.answer(VAR191)" maxlength="100" size="25" styleId="VAR191" onblur="fireRule('VAR191',this)"
	     		title="${PamForm.formFieldMap.VAR191.label}"/> 
	 	</td>
	 </tr>
<!--Date IgM Specimen Taken:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR192.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR192.state.disabledString}"  id="VAR192L" style="${PamForm.formFieldMap.VAR192.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR192.tooltip}">${PamForm.formFieldMap.VAR192.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR192.label}" disabled ="${PamForm.formFieldMap.VAR192.state.disabled}"  property="pamClientVO.answer(VAR192)" maxlength="10" 
	                     styleId="VAR192" onblur="fireRule('VAR192',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR192',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR192','VAR192con'); return false;" onkeypress ="showCalendarEnterKey('VAR192','VAR192con',event);" 
	                     styleId="VAR192con"></html:img>
	 	</td>
	 </tr>
<!--IgM Test Result:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR193.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR193.state.disabledString}"  id="VAR193L" style="${PamForm.formFieldMap.VAR193.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR193.tooltip}">${PamForm.formFieldMap.VAR193.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR193.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR193.state.disabled}" property="pamClientVO.answer(VAR193)" styleId="VAR193" onchange="fireRule('VAR193',this)" >
                 <html:optionsCollection property="codedValue(VAR193)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--IgM Test Result Value-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR194.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR194.state.disabledString}"  id="VAR194L" style="${PamForm.formFieldMap.VAR194.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR194.tooltip}">${PamForm.formFieldMap.VAR194.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR194.state.disabled}" property="pamClientVO.answer(VAR194)" maxlength="50" size="25"
	     		title="${PamForm.formFieldMap.VAR194.label}" styleId="VAR194" onblur="fireRule('VAR194',this)"  /> 
	 	</td>
	 </tr>
	 <!-- IgM Testing: -->
     <%= request.getAttribute("2151") == null ? "" :  request.getAttribute("2151") %>  
	</nedss:container>
<!--SUBSECTION : IgG Testing::-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="IgG Testing" classType="subSect" >
	<!--35. IgG performed:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR195.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR195.state.disabledString}"  id="VAR195L" style="${PamForm.formFieldMap.VAR195.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR195.tooltip}">${PamForm.formFieldMap.VAR195.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR195.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR195.state.disabled}" property="pamClientVO.answer(VAR195)" styleId="VAR195" onchange="fireRule('VAR195',this)"> 
                 <html:optionsCollection property="codedValue(VAR195)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Type of IgG Test::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR196.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR196.state.disabledString}"  id="VAR196L" style="${PamForm.formFieldMap.VAR196.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR196.tooltip}">${PamForm.formFieldMap.VAR196.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR196.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR196.state.disabled}" property="pamClientVO.answer(VAR196)" styleId="VAR196" onchange="fireRule('VAR196',this)"> 
                 <html:optionsCollection property="codedValue(VAR196)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--If "Whole Cell ELISA," specify manufacturer::-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR197.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR197.state.disabledString}"  id="VAR197L" style="${PamForm.formFieldMap.VAR197.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR197.tooltip}">${PamForm.formFieldMap.VAR197.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR197.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR197.state.disabled}" property="pamClientVO.answer(VAR197)" styleId="VAR197" onchange="fireRule('VAR197',this)">
                 <html:optionsCollection property="codedValue(VAR197)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--If "gp ELISA," specify manufacturer:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR198.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR198.state.disabledString}"  id="VAR198L" style="${PamForm.formFieldMap.VAR198.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR198.tooltip}">${PamForm.formFieldMap.VAR198.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR198.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR198.state.disabled}" property="pamClientVO.answer(VAR198)" styleId="VAR198" onchange="fireRule('VAR198',this)"> 
                 <html:optionsCollection property="codedValue(VAR198)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Specify Other IgG Test:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR199.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR199.state.disabledString}"  id="VAR199L" style="${PamForm.formFieldMap.VAR199.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR199.tooltip}">${PamForm.formFieldMap.VAR199.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR199.state.disabled}" property="pamClientVO.answer(VAR199)" maxlength="100" size="25" styleId="VAR199" onblur="fireRule('VAR199',this)"
	     		title="${PamForm.formFieldMap.VAR199.label}"/> 
	 	</td>
	 </tr>
<!--Date of IgG - Acute:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR200.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR200.state.disabledString}"  id="VAR200L" style="${PamForm.formFieldMap.VAR200.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR200.tooltip}">${PamForm.formFieldMap.VAR200.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR200.label}" disabled ="${PamForm.formFieldMap.VAR200.state.disabled}" property="pamClientVO.answer(VAR200)" maxlength="10" 
	                     styleId="VAR200" onblur="fireRule('VAR200',this)"  onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR200',this)" />
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR200','VAR200con'); return false;" onkeypress ="showCalendarEnterKey('VAR200','VAR200con',event);" 
	                     styleId="VAR200con"></html:img>
	 	</td>
	 </tr>
<!--IgG - Acute Result:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR201.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR201.state.disabledString}"  id="VAR201L" style="${PamForm.formFieldMap.VAR201.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR201.tooltip}">${PamForm.formFieldMap.VAR201.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR201.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR201.state.disabled}" property="pamClientVO.answer(VAR201)" styleId="VAR201" onchange="fireRule('VAR201',this)">
                 <html:optionsCollection property="codedValue(VAR201)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--IgG - Acute Test Result Value:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR202.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR202.state.disabledString}"  id="VAR202L" style="${PamForm.formFieldMap.VAR202.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR202.tooltip}">${PamForm.formFieldMap.VAR202.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR202.state.disabled}" property="pamClientVO.answer(VAR202)" maxlength="50" styleId="VAR202" onblur="fireRule('VAR202',this)"  
	     		title="${PamForm.formFieldMap.VAR202.label}"/> 
	 	</td>
	 </tr>
<!--Date of IgG - Convalescent:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR203.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR203.state.disabledString}"  id="VAR203L" style="${PamForm.formFieldMap.VAR203.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR203.tooltip}">${PamForm.formFieldMap.VAR203.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR203.label}" disabled ="${PamForm.formFieldMap.VAR203.state.disabled}" property="pamClientVO.answer(VAR203)" maxlength="10" 
	                     styleId="VAR203" onchange="fireRule('VAR203',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onblur="fireRule('VAR203',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR203','VAR203con'); return false;"  onkeypress ="showCalendarEnterKey('VAR203','VAR203con',event);" 
	                     styleId="VAR203con"></html:img>
	 	</td>
	 </tr>
<!--IgG - Convalescent Result:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR204.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR204.state.disabledString}"  id="VAR204L" style="${PamForm.formFieldMap.VAR204.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR204.tooltip}">${PamForm.formFieldMap.VAR204.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR204.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR204.state.disabled}"  property="pamClientVO.answer(VAR204)" styleId="VAR204" onchange="fireRule('VAR204',this)" >
                 <html:optionsCollection property="codedValue(VAR204)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--IgG - Convalescent Test Result Value:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR205.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR205.state.disabledString}"  id="VAR205L" style="${PamForm.formFieldMap.VAR205.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR205.tooltip}">${PamForm.formFieldMap.VAR205.label}</span>  
	     </td>
	     <td> 
	     	 <html:text name="PamForm" disabled ="${PamForm.formFieldMap.VAR205.state.disabled}" property="pamClientVO.answer(VAR205)" maxlength="50" styleId="VAR205" onchange="fireRule('VAR205',this)"  
	     		title="${PamForm.formFieldMap.VAR205.label}"/> 
	 	</td>
	 </tr>
	 <!-- IgG Testing: -->
     <%= request.getAttribute("2158") == null ? "" :  request.getAttribute("2158") %>  
	</nedss:container>
	<!--SUBSECTION : Specimen Genotyping:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Specimen Genotyping" classType="subSect" >

		<!--36. Were the specimens sent to the CDC for genotyping (molecular typing):-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR206.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR206.state.disabledString}"  id="VAR206L" style="${PamForm.formFieldMap.VAR206.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR206.tooltip}">${PamForm.formFieldMap.VAR206.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR206.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR206.state.disabled}" property="pamClientVO.answer(VAR206)" styleId="VAR206" onchange="fireRule('VAR206',this)"> 
                 <html:optionsCollection property="codedValue(VAR206)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
<!--Date sent for genotyping-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR207.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR207.state.disabledString}"  id="VAR207L" style="${PamForm.formFieldMap.VAR207.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR207.tooltip}">${PamForm.formFieldMap.VAR207.label}</span>  
	     </td>
	     <td> 
	     	  <html:text name="PamForm" title="${PamForm.formFieldMap.VAR207.label}" disabled ="${PamForm.formFieldMap.VAR207.state.disabled}" property="pamClientVO.answer(VAR207)" maxlength="10" 
	                     styleId="VAR207" onblur="fireRule('VAR207',this)" onkeyup="DateMask(this,null,event)" size="10"  
	                     onchange="fireRule('VAR207',this)"/>
	             <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAR207','VAR207con'); return false;" onkeypress ="showCalendarEnterKey('VAR207','VAR207con',event);" 
	                     styleId="VAR207con"></html:img>
	 	</td>
	 </tr>
	 <!-- Specimen Genotyping: -->
     <%= request.getAttribute("2170") == null ? "" :  request.getAttribute("2170") %>  
	</nedss:container>
	<!--SUBSECTION : Specimen Strain Identification:-->
	<nedss:container id='<%= "ss" + (++subSectionIndex) %>' name="Specimen Strain Identification" classType="subSect" >
		<!--37. Was specimen sent for strain (wild- or vaccine-type) identification:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR208.state.requiredIndClass}">*</span>
	         <span  class="${PamForm.formFieldMap.VAR208.state.disabledString}"  id="VAR208L" style="${PamForm.formFieldMap.VAR208.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR208.tooltip}">${PamForm.formFieldMap.VAR208.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR208.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR208.state.disabled}" property="pamClientVO.answer(VAR208)" styleId="VAR208" onchange="fireRule('VAR208',this)">
                 <html:optionsCollection property="codedValue(VAR208)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!--Strain Type:-->
	<tr>
	     <td class="fieldName"> 
	         <span style="${PamForm.formFieldMap.VAR209.state.requiredIndClass}">*</span>
	         <span class="${PamForm.formFieldMap.VAR209.state.disabledString}"  id="VAR209L" style="${PamForm.formFieldMap.VAR209.errorStyleClass}"
	                 title="${PamForm.formFieldMap.VAR209.tooltip}">${PamForm.formFieldMap.VAR209.label}</span>  
	     </td>
	     <td> 
	     	  <html:select title="${PamForm.formFieldMap.VAR209.label}" name="PamForm" disabled ="${PamForm.formFieldMap.VAR209.state.disabled}" property="pamClientVO.answer(VAR209)" styleId="VAR209" onchange="fireRule('VAR209',this)" >
                 <html:optionsCollection property="codedValue(VAR209)" value="key" label="value"/>
              </html:select>
	 	</td>
	 </tr>
	 <!-- Specimen Strain Identification: -->
     <%= request.getAttribute("2173") == null ? "" :  request.getAttribute("2173") %>  
	</nedss:container>

</nedss:container>




<!--SECTION:Epidemiologic Information-->
<jsp:include page="Varicella_Sub_Tab1_Layout.jsp">
   <jsp:param name="param1" value="<%=sectionIndex%>" />
   <jsp:param name="param2" value="<%=subSectionIndex%>" />
   <jsp:param name="param3" value="<%=sectionId%>" />
 </jsp:include>
 
 
</div>
</tr></td>