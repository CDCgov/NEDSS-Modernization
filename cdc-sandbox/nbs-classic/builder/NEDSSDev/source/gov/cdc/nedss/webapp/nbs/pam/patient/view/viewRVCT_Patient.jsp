<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO"%>
<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page isELIgnored ="false" %>

 <!--
    Page Summary:
    -------------
    This file includes the contents of a single tab (RVCT_Patient). This tab is part of the tab 
    container whose structure is defined in a separate JSP. The file is made up of a sequence of 
    tables (one table for each logical section).
-->

<tr> <td>
<!-- handle Display properties of different collapsable components -->
<script type="text/javascript">
    // Load the URL received in this window. Currently, it is used to jump to
    // different sections within a page.
    //function jumpToLocation(location) {
    //    window.location = location;
    //}
</script>
<% 
    String []sectionNames = {"Patient Information", "Custom Fields"};
	 int sectionIndex = 0;
     int subSectionIndex = 0; 
     String tabId = "viewPatient";
%>

<h2 class="printOnlyTabHeader">
    Patient
</h2>

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
	  <!-- SECTION : Investigation Information --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
     <!-- SUB_SECTION : General Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="General Information" classType="subSect" >
            <!-- As of date - View mode -->         
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.DEM215.state.requiredIndClass}">*</span>
                        <span 
                                title="${PamForm.formFieldMap.DEM215.tooltip}">${PamForm.formFieldMap.DEM215.label}</span> 
                    </td>
                    <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(DEM215)"/>
                    </td>
                </tr>
        
            <!-- Comments -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM196.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.DEM196.errorStyleClass}" 
                            title="${PamForm.formFieldMap.DEM196.tooltip}">${PamForm.formFieldMap.DEM196.label}</span> 
                </td>
                <td > 
				       <nedss:view name="PamForm" property="pamClientVO.answer(DEM196)"/>
				</td>
            </tr>
        </nedss:container>
        <!-- SUB_SECTION : Name Information -->
		   <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Name Information" classType="subSect" >
   
		    <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM206.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM206.tooltip}"> 
                        ${PamForm.formFieldMap.DEM206.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM206)"/>
                </td>
            </tr>
             <!-- First Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM104.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM104.tooltip}"> 
                        ${PamForm.formFieldMap.DEM104.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM104)"/>
                </td>
            </tr>
            <!-- Middle Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM105.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM105.tooltip}"> 
                        ${PamForm.formFieldMap.DEM105.label} 
                    </span> 
                </td>
                <td> 
                       <nedss:view name="PamForm" property="pamClientVO.answer(DEM105)"/>
                </td>
            </tr>
            <!-- Last Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM102.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM102.tooltip}"> 
                        ${PamForm.formFieldMap.DEM102.label} 
                    </span> 
                </td>
                <td> 
                      <nedss:view name="PamForm" property="pamClientVO.answer(DEM102)"/>
                </td>
            </tr>
            <!-- Suffix -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM107.state.requiredIndClass}">*</span>
                    <span
                            title="${PamForm.formFieldMap.DEM107.tooltip}"> 
                        ${PamForm.formFieldMap.DEM107.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM107)" codeSetNm="${PamForm.formFieldMap.DEM107.codeSetNm}"/>
                </td>
            </tr>
        </nedss:container>     
     
        
	    <!-- SUB_SECTION : Other Personal Details -->
		   <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Other Personal Details" classType="subSect" >
	   	       
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.DEM207.state.requiredIndClass}">*</span>
                        <span 
                                title="${PamForm.formFieldMap.DEM207.tooltip}">
                            ${PamForm.formFieldMap.DEM207.label}
                        </span>
                    </td>
                    <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(DEM207)"/>
                    </td>
                </tr>
         
            <!-- Date of Birth -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM115.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM115.tooltip}"> 
                        ${PamForm.formFieldMap.DEM115.label} 
                    </span> 
                </td>
                <td> 
                   <nedss:view name="PamForm" property="pamClientVO.answer(DEM115)"/>
                </td>
            </tr>
            <!-- Reported Age -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM216.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM216.tooltip}"> 
                        ${PamForm.formFieldMap.DEM216.label} 
                    </span>  
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM216)"/>
				    <nedss:view name="PamForm" property="pamClientVO.answer(DEM218)" codeSetNm="${PamForm.formFieldMap.DEM218.codeSetNm}"/>					
                </td>
            </tr>
            <!-- Sex at Birth -->
            <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
        	%>
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM114.state.requiredIndClass}">*</span>
                    <span
                            title="${PamForm.formFieldMap.DEM114.tooltip}"> 
                        ${PamForm.formFieldMap.DEM114.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM114)" codeSetNm="${PamForm.formFieldMap.DEM114.codeSetNm}"/>
					
                </td>
            </tr>
            <% } %>
            <!-- Current Sex -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM113.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM113.tooltip}"> 
                        ${PamForm.formFieldMap.DEM113.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM113)" codeSetNm="${PamForm.formFieldMap.DEM113.codeSetNm}"/>
					 
                </td>
            </tr>          
         
                <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${PamForm.formFieldMap.DEM208.state.requiredIndClass}">*</span>
                        <span 
                                title="${PamForm.formFieldMap.DEM208.tooltip}">${PamForm.formFieldMap.DEM208.label}</span>
                    </td>
                    <td class="topBorder">
                         <nedss:view name="PamForm" property="pamClientVO.answer(DEM208)"/>
                    </td>
                </tr>
         
            <!-- Is the Patient Deceased -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM127.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM127.tooltip}"> 
                        ${PamForm.formFieldMap.DEM127.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM127)" codeSetNm="${PamForm.formFieldMap.DEM127.codeSetNm}"/>
                </td>
            </tr>
            <!-- Deceased Date -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM128.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM128.tooltip}"> 
                        ${PamForm.formFieldMap.DEM128.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM128)"/>
                </td>
            </tr>
            <!-- Marital Status as of date - Edit mode only -->
            
                 <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${PamForm.formFieldMap.DEM209.state.requiredIndClass}">*</span>
                        <span 
                                title="${PamForm.formFieldMap.DEM209.tooltip}">${PamForm.formFieldMap.DEM209.label}</span>
                    </td>
                    <td class="topBorder">
                       <nedss:view name="PamForm" property="pamClientVO.answer(DEM209)"/>
                    </td>
                </tr>
         
            <!-- Marital Status -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM140.state.requiredIndClass}">*</span>
                    <span
                            title="${PamForm.formFieldMap.DEM140.tooltip}"> 
                        ${PamForm.formFieldMap.DEM140.label} 
                    </span> 
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(DEM140)" codeSetNm="${PamForm.formFieldMap.DEM140.codeSetNm}"/>
                </td>
            </tr>
            <!-- SSN As of date - View mode only -->
         
                <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${PamForm.formFieldMap.DEM210.state.requiredIndClass}">*</span>
                        <span 
                                title="${PamForm.formFieldMap.DEM210.tooltip}">${PamForm.formFieldMap.DEM210.label}</span>
                    </td>
                    <td class="topBorder">
                      <nedss:view name="PamForm" property="pamClientVO.answer(DEM210)"/>
                    </td>
                </tr>
      
            <!-- SSN -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM133.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM133.tooltip}"> 
                        ${PamForm.formFieldMap.DEM133.label} 
                    </span> 
                </td>
                <td> 
                   <nedss:view name="PamForm" property="pamClientVO.answer(DEM133)"/>
                </td>
            </tr>   
	    </nedss:container>
	    
	    <!-- SUB_SECTION : 4. Reporting Address for Case Counting -->
	   
           <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="4. Reporting Address for Case Counting" classType="subSect" >
	       <!-- Contact Information As of date - Edit mode only -->
           
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM213.state.requiredIndClass}">*</span>
                         <span 
                                title="${PamForm.formFieldMap.DEM213.tooltip}">${PamForm.formFieldMap.DEM213.label}</span>
                    </td>
                    <td class="topBorder">
                        <nedss:view name="PamForm" property="pamClientVO.answer(DEM213)"/>
                    </td>
                </tr>
           
            <!-- Street Address 1 -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM159.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM159.tooltip}"> 
                        ${PamForm.formFieldMap.DEM159.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM159)"/>
                </td>
            </tr>
            <!-- Street Address 2 -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM160.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM160.tooltip}"> 
                        ${PamForm.formFieldMap.DEM160.label} 
                    </span>  
                </td>
                <td> 
                      <nedss:view name="PamForm" property="pamClientVO.answer(DEM160)"/>
                </td>
            </tr>
            <!-- City -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM161.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM161.tooltip}"> 
                        ${PamForm.formFieldMap.DEM161.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM161)"/>
                </td>
            </tr>
            <!-- State -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM162.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM162.tooltip}"> 
                        ${PamForm.formFieldMap.DEM162.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM162)" codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
                </td>
            </tr>
            <!-- Zip -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM163.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM163.tooltip}"> 
                        ${PamForm.formFieldMap.DEM163.label} 
                    </span> 
                </td>
                <td> 
                       <nedss:view name="PamForm" property="pamClientVO.answer(DEM163)"/>
                </td>
            </tr>
            <!-- County -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM165.state.requiredIndClass}">*</span>
                    <span
                            title="${PamForm.formFieldMap.DEM165.tooltip}"> 
                        ${PamForm.formFieldMap.DEM165.label} 
                    </span> 
                </td>
                <td> 
                  <logic:notEmpty name="PamForm" property="pamClientVO.answer(DEM165)">
                  	<logic:notEmpty name="PamForm" property="pamClientVO.answer(DEM162)">
		                  <bean:define id="value" name="PamForm" property="pamClientVO.answer(DEM162)"/>
		                  <nedss:view name="PamForm" property="pamClientVO.answer(DEM165)" codeSetNm='<%=value.toString()+"county"%>'/>
		            </logic:notEmpty>
		          </logic:notEmpty>
                </td>
            </tr>
            <!-- Country -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM167.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM167.tooltip}"> 
                        ${PamForm.formFieldMap.DEM167.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM167)" codeSetNm="<%=NEDSSConstants.COUNTRY_LIST%>"/>
                </td>
            </tr>
            <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {
        	%>
            <!-- Within City Limits -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM237.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM237.tooltip}"> 
                        ${PamForm.formFieldMap.DEM237.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM237)" codeSetNm="${PamForm.formFieldMap.DEM237.codeSetNm}"/>
                </td>
            </tr>
            <%}%>
	    </nedss:container>
	    
	    <!-- SUB_SECTION : Telephone Information -->
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Telephone Information" classType="subSect" >
	 
            <!-- Telephone Information As of date - Edit mode only -->
       
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM214.state.requiredIndClass}">*</span>
                         <span 
                                title="${PamForm.formFieldMap.DEM214.tooltip}">${PamForm.formFieldMap.DEM214.label}</span>
                    </td>
                    <td class="topBorder">
                        <nedss:view name="PamForm" property="pamClientVO.answer(DEM214)"/>
                    </td>
                </tr>
   
            <!-- Home Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM238.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM238.tooltip}"> 
                        ${PamForm.formFieldMap.DEM238.label} 
                    </span> 
                </td>
                <td> 
                     <nedss:view name="PamForm" property="pamClientVO.answer(DEM238)"/>
                </td>
            </tr>
            <!-- Home Phone ext -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM239.state.requiredIndClass}">*</span>
                    <span
                            title="${PamForm.formFieldMap.DEM239.tooltip}"> 
                        ${PamForm.formFieldMap.DEM239.label} 
                    </span> 
                </td>
                <td> 
                      <nedss:view name="PamForm" property="pamClientVO.answer(DEM239)"/>
                </td>
            </tr>
            <!-- Work Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM240.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM240.tooltip}"> 
                        ${PamForm.formFieldMap.DEM240.label} 
                    </span> 
                </td>
                <td> 
                        <nedss:view name="PamForm" property="pamClientVO.answer(DEM240)"/>
                </td>
            </tr>
            <!-- Work Phone ext -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM241.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM241.tooltip}"> 
                        ${PamForm.formFieldMap.DEM241.label} 
                    </span> 
                </td>
                <td> 
                      <nedss:view name="PamForm" property="pamClientVO.answer(DEM241)"/>
                </td>
            </tr>	       
	    </nedss:container>
	    
	    <!-- SUB_SECTION : Ethnicity & Race Information -->
	  
			<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Ethnicity & Race Information" classType="subSect" >
            <!-- Ethnicity and Race Information As of date - View mode  -->
         
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${PamForm.formFieldMap.DEM211.state.requiredIndClass}">*</span>
                         <span 
                                title="${PamForm.formFieldMap.DEM211.tooltip}">${PamForm.formFieldMap.DEM211.label}</span>
                    </td>
                    <td class="topBorder">
                         <nedss:view name="PamForm" property="pamClientVO.answer(DEM211)"/>
                    </td>
                </tr>        
            
            <!-- Ethnicity -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM155.state.requiredIndClass}">*</span>
                    <span 
                            title="${PamForm.formFieldMap.DEM155.tooltip}"> 
                        ${PamForm.formFieldMap.DEM155.label} 
                    </span> 
                </td>
                <td> 
                    <nedss:view name="PamForm" property="pamClientVO.answer(DEM155)" codeSetNm="${PamForm.formFieldMap.DEM155.codeSetNm}"/>
                </td>
            </tr>
            <!-- Race -->
            <tr>
                <td class="fieldName topBorder">
                     <span style="${PamForm.formFieldMap.DEM212.state.requiredIndClass}">*</span>
                     <span 
                            title="${PamForm.formFieldMap.DEM212.tooltip}">${PamForm.formFieldMap.DEM212.label}</span>
                </td>
                <td class="topBorder">
                   <nedss:view name="PamForm" property="pamClientVO.answer(DEM212)"/>
                </td>
            </tr>
           
            <!-- Alaska & American Indian race -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.DEM152.state.requiredIndClass}">*</span>
                    <span title="${PamForm.formFieldMap.DEM152.tooltip}"> 
                        ${PamForm.formFieldMap.DEM152.label}
                    </span> 
                </td>
                <td>
                   <!-- Note: The extra commas and surrounding white spaces (if any) are removed with the help of a 
                        JS function, cleanupPatientRacesViewDisplay(), defined in the PamSpecific.js file -->   
                   <div id="patientRacesViewContainer"> 
	                   <logic:equal name="PamForm" property="pamClientVO.americanIndianAlskanRace" value="1">
	                        <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>
	                   
	                   <logic:equal name="PamForm" property="pamClientVO.asianRace" value="1">
	                        <bean:message bundle="RVCT" key="rvct.asian"/><nedss:view name="PamForm" property="pamClientVO.answerArray(DEM243)" 
	                                codeSetNm="2028-9" mutliSelectResultInParanthesis="yes"/>,</logic:equal> 
	                   
	                   <logic:equal name="PamForm" property="pamClientVO.africanAmericanRace" value="1">
	                        <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>
	                   
	                   <logic:equal name="PamForm" property="pamClientVO.hawaiianRace" value="1">
	                        <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/><nedss:view name="PamForm" property="pamClientVO.answerArray(DEM245)" 
	                            codeSetNm="2076-8" mutliSelectResultInParanthesis="yes"/>,</logic:equal>
	                                      
	                   <logic:equal name="PamForm" property="pamClientVO.whiteRace" value="1">
	                        <bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>
	                   
	                   <logic:equal name="PamForm" property="pamClientVO.unKnownRace" value="1">
	                        <bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>
                    </div>                        
                </td>
            </tr>
	    </nedss:container>
	</nedss:container>
   <!-- SECTION : Local Fields -->
   <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		<%= request.getAttribute("NEWPAT_LDFS") == null ? "" :  request.getAttribute("NEWPAT_LDFS") %>
        </nedss:container>
   </nedss:container>
    <%} %>		
	<div class="tabNavLinks">
        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
        <a href="javascript:navigateTab('next')"> Next </a>
    </div>
</div> <!-- div class="view" -->
</td> </tr>