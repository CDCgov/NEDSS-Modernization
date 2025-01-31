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
     String []sectionNames = {"Investigation Information", "Reporting Information", "Patient History",
                                   "Clinical Information", "Laboratory Information", "Risk Factors",
                                    "Treatment", "Epidemiologic Information", "Investigation Comments", "Custom Fields"};                                    
    int sectionIndex = 0;
    int subSectionIndex = 0; 
    String tabId = "viewTuberculosis";
%>

<h2 class="printOnlyTabHeader">
    Tuberculosis
</h2>

<div class="view" id="<%= tabId %>" style="text-align:center;">    
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
                    </td>
                </tr>
         
            <!-- Program Area -->
            <tr>
                <td class="fieldName"> 
                    <span title="${PamForm.formFieldMap.INV108.tooltip}">
                        ${PamForm.formFieldMap.INV108.label}</span>
                </td>
                <td>
                   <html:hidden property="pamClientVO.answer(INV108)"/>
                    <nedss:view name="PamForm" property="pamClientVO.answer(INV108)" 
                           codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
                </td>
            </tr>
            <!-- Share Indicator -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap. INV174.errorStyleClass}"  
                            title="${PamForm.formFieldMap.INV174.tooltip}">${PamForm.formFieldMap.INV174.label}</span>
                </td>
                <td>
                   <logic:equal name="PamForm" property="pamClientVO.answer(INV174)" value="1">
                        Yes
                   </logic:equal>
                   <logic:notEqual name="PamForm" property="pamClientVO.answer(INV174)" value="1">
                        No
                   </logic:notEqual>
                </td>
            </tr>
            <!-- Investigation Status -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV109.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV109.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV109.tooltip}">
                        ${PamForm.formFieldMap.INV109.label}
                    </span> 
                </td>
                <td class="InputField">
                    <nedss:view name="PamForm" property="pamClientVO.answer(INV109)" 
                            codeSetNm="${PamForm.formFieldMap.INV109.codeSetNm}"/>
                </td>
            </tr>
            <!-- Investigation Start Date -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV147.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV147.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV147.tooltip}">
                        ${PamForm.formFieldMap.INV147.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV147)"/>
                </td>
            </tr>
            <!-- Investigation Details LDFs -->
            <%= request.getAttribute("1287") == null ? "" :  request.getAttribute("1287") %>
        </nedss:container>

        <!-- SUB_SECTION : Investigator -->
    
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigator" classType="subSect" >                  
            <!-- Investigator -->            
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.INV207.errorStyleClass}"
                            title="${PamForm.formFieldMap.INV207.tooltip}">${PamForm.formFieldMap.INV207.label}</span>  
                </td>

                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV207)"/>   
                       <span id="INV207">${PamForm.attributeMap.INV207SearchResult}</span>
                </td>
            </tr>    
            <!-- Date Assigned to Investigation -->            
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.INV110.errorStyleClass}"
                            title="${PamForm.formFieldMap.INV110.tooltip}">${PamForm.formFieldMap.INV110.label}</span>  
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV110)"/>
                </td>
            </tr>  
          <!-- Investigator LDFs -->
          <%= request.getAttribute("1321") == null ? "" :  request.getAttribute("1321") %>  
        </nedss:container>  
    </nedss:container>
                
    <!-- SECTION : Reporting Information -->
     <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">  
        <!-- SUB_SECTION : Key Report Dates -->
         <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Key Report Dates" classType="subSect" >      
            <!-- Date Reported -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV111.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV111.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV111.tooltip}">
                        ${PamForm.formFieldMap.INV111.label}
                    </span> 
                </td>
                <td class="topBorder">
                    <nedss:view name="PamForm" property="pamClientVO.answer(INV111)"/>            
                </td>
            </tr>
            <!-- Date Submitted -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV121.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV121.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV121.tooltip}">
                        ${PamForm.formFieldMap.INV121.label}
                    </span> 
                </td>
                <td class="topBorder">
                       <nedss:view name="PamForm" property="pamClientVO.answer(INV121)"/>  
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
		     	<nedss:view name="PamForm" property="pamClientVO.answer(INV120)"/>
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
                    <span style="${PamForm.formFieldMap.INV173.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV173.tooltip}">
                        ${PamForm.formFieldMap.INV173.label}
                    </span> 
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(INV173)"/>  
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(INV198)"/>  
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB100)"/>  
                </td>
            </tr>
            <!-- Link Reason 1 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB101.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB101.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB101.tooltip}">
                        ${PamForm.formFieldMap.TUB101.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB101)" codeSetNm="${PamForm.formFieldMap.TUB101.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB102)"/>  
                </td>
            </tr>
            <!-- Link Reason 2 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB103.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB103.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB103.tooltip}">
                        ${PamForm.formFieldMap.TUB103.label}
                    </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB103)" codeSetNm="${PamForm.formFieldMap.TUB103.codeSetNm}"/>
                </td>
            </tr>
              <!-- Case Numbers LDFs -->
             <%= request.getAttribute("1005") == null ? "" :  request.getAttribute("1005") %>            
        </nedss:container>
		<!-- SUBSECTION : Reporting Organization: --> 
		<div class='${PamForm.formFieldMap["1340"].state.visibleString}'>
			<%@ include file="/pam/ext/view/ReportingOrganization.jsp" %>        
		</div>	
		<!-- SUBSECTION : Reporting Provider: --> 
		<div class='${PamForm.formFieldMap["1343"].state.visibleString}'>
			<%@ include file="/pam/ext/view/ReportingProvider.jsp" %>        
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
                    <span style="${PamForm.formFieldMap.INV111.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB111.tooltip}">${PamForm.formFieldMap.TUB111.label}</span>
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB111)" codeSetNm="${PamForm.formFieldMap.TUB111.codeSetNm}"/>
                </td>
            </tr>
            <!-- Year of Previous Diagnosis -->  
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB112.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB112.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB112.tooltip}">${PamForm.formFieldMap.TUB112.label}</span>
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB112)"/>                       
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB277)" codeSetNm="${PamForm.formFieldMap.TUB277.codeSetNm}"/>
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB276)" codeSetNm="${PamForm.formFieldMap.TUB276.codeSetNm}"/>
                </td>
            </tr>
            <!-- Month-Year Arrived in the US -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB273.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB273.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB273.tooltip}">${PamForm.formFieldMap.TUB273.label}</span>                        
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB273)"/>       
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB115)" codeSetNm="${PamForm.formFieldMap.TUB115.codeSetNm}"/>
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
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB116)" codeSetNm="${PamForm.formFieldMap.TUB116.codeSetNm}"/>
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB113)" codeSetNm="${PamForm.formFieldMap.TUB113.codeSetNm}"/>
                </td>
            </tr>
            <!-- Countries -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB114.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB114.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB114.tooltip}">${PamForm.formFieldMap.TUB114.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB114)" 
                            codeSetNm="${PamForm.formFieldMap.TUB114.codeSetNm}"/>
                </td>
            </tr>
            <!-- Pediatric TB Patients LDFs -->
            <%= request.getAttribute("1036") == null ? "" :  request.getAttribute("1036") %>
        </nedss:container>
    </nedss:container>
    
    <!-- Clinical Information -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
		<!-- SUBSECTION: Physician -->	
		<div class='${PamForm.formFieldMap["1345"].state.visibleString}'>
			<%@ include file="/pam/ext/view/Physician.jsp" %>
		</div>	
		<!-- SUBSECTION: Hospital -->	
		  <div class='${PamForm.formFieldMap["1347"].state.visibleString}'>
			<%@ include file="/pam/ext/view/Hospital.jsp" %>
		</div>
		<!-- SUBSECTION: Condition -->	
		 <div class='${PamForm.formFieldMap["1353"].state.visibleString}'>
			<%@ include file="/pam/ext/view/Condition.jsp" %>    
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB117)" codeSetNm="${PamForm.formFieldMap.TUB117.codeSetNm}"/>
                </td>
            </tr>
            <!-- Date of Death -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV146.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV146.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV146.tooltip}">${PamForm.formFieldMap.INV146.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(INV146)"/>       
                </td>
            </tr>
            <!-- Was TB a cause of death -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV145.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV145.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV145.tooltip}">${PamForm.formFieldMap.INV145.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(INV145)" codeSetNm="${PamForm.formFieldMap.INV145.codeSetNm}"/>
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
                   <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB119)" 
                        codeSetNm="${PamForm.formFieldMap.TUB119.codeSetNm}"/>
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
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB120)" 
                        codeSetNm="${PamForm.formFieldMap.TUB120.codeSetNm}"/>
                </td>
            </tr>
            <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB121.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB121.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB121.tooltip}">${PamForm.formFieldMap.TUB121.label}                    
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB121)"/>  
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB122)" 
                            codeSetNm="${PamForm.formFieldMap.TUB122.codeSetNm}"/>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB123.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB123.errorStyleClass}"
                            title="${PamForm.formFieldMap.TUB123.tooltip}">${PamForm.formFieldMap.TUB123.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB123)"/>  
                </td>
            </tr>
            <!-- Date Result Reported -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB124.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB124.errorStyleClass}"
                            title="${PamForm.formFieldMap.TUB124.tooltip}">${PamForm.formFieldMap.TUB124.label}                    
                </td>
                <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB124)"/>  
                </td>
            </tr>
            <!-- Reporting Laboratory Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB125.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB125.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB125.tooltip}">${PamForm.formFieldMap.TUB125.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB125)" 
                            codeSetNm="${PamForm.formFieldMap.TUB125.codeSetNm}"/>
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
                <nedss:view name="PamForm" property="pamClientVO.answer(TUB126)" 
                        codeSetNm="${PamForm.formFieldMap.TUB126.codeSetNm}"/>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB127.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB127.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB127.tooltip}">${PamForm.formFieldMap.TUB127.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB127)"/>  
                </td>
            </tr>
            <!-- Anatomic Site -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB128.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB128.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB128.tooltip}">${PamForm.formFieldMap.TUB128.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB128)" 
                            codeSetNm="${PamForm.formFieldMap.TUB128.codeSetNm}"/>
                </td>
            </tr>
            <!-- Type of Exam -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB129.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB129.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB129.tooltip}">${PamForm.formFieldMap.TUB129.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB129)" 
                        codeSetNm="${PamForm.formFieldMap.TUB129.codeSetNm}"/>
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
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB130)" 
                        codeSetNm="${PamForm.formFieldMap.TUB130.codeSetNm}"/>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB131.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB131.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB131.tooltip}">${PamForm.formFieldMap.TUB131.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB131)"/>  
                </td>
            </tr>
            <!-- Anatomic Site -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB132.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB132.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB132.tooltip}">${PamForm.formFieldMap.TUB132.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB132)" 
                        codeSetNm="${PamForm.formFieldMap.TUB132.codeSetNm}"/>
                </td>
            </tr>
            <!-- Date Result Reported -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB133.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB133.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB133.tooltip}">${PamForm.formFieldMap.TUB133.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB133)"/>  
                </td>
            </tr>
            <!-- Reporting Laboratory Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB134.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB134.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB134.tooltip}">${PamForm.formFieldMap.TUB134.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB134)" 
                            codeSetNm="${PamForm.formFieldMap.TUB134.codeSetNm}"/>
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
                <nedss:view name="PamForm" property="pamClientVO.answer(TUB135)" 
                        codeSetNm="${PamForm.formFieldMap.TUB135.codeSetNm}"/>
                </td>
           </tr>
           <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB136.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB136.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB136.tooltip}">${PamForm.formFieldMap.TUB136.label}                    
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB136)"/> 
                </td>
            </tr>
            <!-- Speciment Type is Sputum -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB137.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB137.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB137.tooltip}">${PamForm.formFieldMap.TUB137.label}</span>
               </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB137)" 
                           codeSetNm="${PamForm.formFieldMap.TUB137.codeSetNm}"/>
                </td>
           </tr>
           <!-- Speciment Type Not Sputum -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB138.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB138.errorStyleClass}" 
                          title="${PamForm.formFieldMap.TUB138.tooltip}">${PamForm.formFieldMap.TUB138.label}</span>
               </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB138)" 
                        codeSetNm="${PamForm.formFieldMap.TUB138.codeSetNm}"/>
                </td>
           </tr>
           <!-- Date Result Reported -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB139.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB139.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB139.tooltip}">${PamForm.formFieldMap.TUB139.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB139)"/> 
                </td>
            </tr>
            <!-- Reporting Laboratory Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB140.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB140.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB140.tooltip}">${PamForm.formFieldMap.TUB140.label}                    
                </td>
                <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB140)" 
                                codeSetNm="${PamForm.formFieldMap.TUB140.codeSetNm}"/>
                </td>
            </tr>
            <!-- Nucleic Acid... LDFs -->
            <%= request.getAttribute("1067") == null ? "" :  request.getAttribute("1067") %>
        </nedss:container>
        
        <!-- SUB_SECTION : Initial Chest Radiograph and Other Chest Imaging Study -->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="22. Initial Chest Radiograph and Other Chest Imaging Study" classType="subSect" > 
            <!-- Initial Chest Radiograph -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB141.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB141.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB141.tooltip}">${PamForm.formFieldMap.TUB141.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB141)" 
                            codeSetNm="${PamForm.formFieldMap.TUB141.codeSetNm}"/>
                </td>
            </tr>
            <!-- Evidence of a cavity -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB142.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB142.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB142.tooltip}">${PamForm.formFieldMap.TUB142.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB142)" 
                            codeSetNm="${PamForm.formFieldMap.TUB142.codeSetNm}"/>
                </td>
            </tr>
            <!-- Evidence of military TB -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB143.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB143.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB143.tooltip}">${PamForm.formFieldMap.TUB143.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB143)" 
                        codeSetNm="${PamForm.formFieldMap.TUB143.codeSetNm}"/>
                </td>
            </tr>
            <!-- Initial Chest CT scan -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB144.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB144.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB144.tooltip}">${PamForm.formFieldMap.TUB144.label}                    
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB144)" 
                            codeSetNm="${PamForm.formFieldMap.TUB144.codeSetNm}"/>
                </td>
            </tr>
            <!-- Evidence of a cavity -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB145.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB145.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB145.tooltip}">${PamForm.formFieldMap.TUB145.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB145)" 
                        codeSetNm="${PamForm.formFieldMap.TUB145.codeSetNm}"/>
                </td>
            </tr>
            <!-- Evidence of military TB -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB146.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB146.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB146.tooltip}">${PamForm.formFieldMap.TUB146.label}                    
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB146)" 
                        codeSetNm="${PamForm.formFieldMap.TUB146.codeSetNm}"/>
                </td>
            </tr>
            <!-- Initial Chest... LDFs -->
            <%= request.getAttribute("1074") == null ? "" :  request.getAttribute("1074") %>
        </nedss:container>
        
        <!-- SUB_SECTION : 23. Tuberculin (Mantoux) Skin Test Diagnosis -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="23. Tuberculin (Mantoux) Skin Test Diagnosis" classType="subSect" >  
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB147.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB147.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB147.tooltip}">${PamForm.formFieldMap.TUB147.label}                    
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB147)" 
                            codeSetNm="${PamForm.formFieldMap.TUB147.codeSetNm}"/>
                </td>
            </tr>
            <!-- Date Tuberculin Skin Test (TST) Placed -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB148.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB148.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB148.tooltip}">${PamForm.formFieldMap.TUB148.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB148)"/> 
                </td>
            </tr>
            <!-- Millimeters of Induration -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB149.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB149.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB149.tooltip}">${PamForm.formFieldMap.TUB149.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB149)"/> 
                </td>
            </tr>
            <!-- Tuberculin(Mantoux)  LDFs -->
            <%= request.getAttribute("1082") == null ? "" :  request.getAttribute("1082") %>
        </nedss:container>
        
        <!-- SUB_SECTION : 24. Interferon Gamma Release Assay for Mycrobacterium tuberculosis at Diagnosis -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="24. Interferon Gamma Release Assay for Mycobacterium tuberculosis at Diagnosis" classType="subSect" > 
       
            <!-- Result -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB150.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB150.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB150.tooltip}">${PamForm.formFieldMap.TUB150.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB150)" 
                            codeSetNm="${PamForm.formFieldMap.TUB150.codeSetNm}"/>
                </td>
            </tr>
            <!-- Date Collected -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB151.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB151.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB151.tooltip}">${PamForm.formFieldMap.TUB151.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB151)"/> 
                </td>
            </tr>
            <!-- Test Type -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB152.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB152.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB152.tooltip}">${PamForm.formFieldMap.TUB152.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB152)"/> 
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
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB153)" 
                        codeSetNm="${PamForm.formFieldMap.TUB153.codeSetNm}"/>
                </td>
            </tr>
            <!-- Primary Reason LDFs -->
            <%= request.getAttribute("1090") == null ? "" :  request.getAttribute("1090") %>
        </nedss:container>
        
        <!-- SUB_SECTION : 26. HIV Status at Time of Diagnosis -->
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
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB154)" codeSetNm="${PamForm.formFieldMap.TUB154.codeSetNm}"/>
                </td>
            </tr>
            <!-- State HIV/AIDS Patient Number -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB155.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB155.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB155.tooltip}">${PamForm.formFieldMap.TUB155.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB155)"/> 
                </td>
            </tr>
            <!-- City/County HIV/AIDS Patient Number -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB156.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB156.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB156.tooltip}">${PamForm.formFieldMap.TUB156.label}                    
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB156)"/> 
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
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB157)" codeSetNm="${PamForm.formFieldMap.TUB157.codeSetNm}"/>
                </td>
            </tr>
            <!-- Resident of Correctional Facility at Time of Diagnosis -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB158.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB158.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB158.tooltip}">${PamForm.formFieldMap.TUB158.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB158)" codeSetNm="${PamForm.formFieldMap.TUB158.codeSetNm}"/>
                </td>
            </tr>
            <!-- Type of Correctional Facility -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB159.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB159.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB159.tooltip}">${PamForm.formFieldMap.TUB159.label}                    
                </td>
                <td>
                 <nedss:view name="PamForm" property="pamClientVO.answer(TUB159)" codeSetNm="${PamForm.formFieldMap.TUB159.codeSetNm}"/>
                </td>
            </tr>
            <!-- Under custody of Immigration and Customs Enforcrement -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB160.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB160.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB160.tooltip}">${PamForm.formFieldMap.TUB160.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB160)" codeSetNm="${PamForm.formFieldMap.TUB160.codeSetNm}"/>
                </td>
            </tr>
            <!-- Resident of Long Term Care Facility at Time of Diagnosis -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB161.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB161.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB161.tooltip}">${PamForm.formFieldMap.TUB161.label}                    
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB161)" codeSetNm="${PamForm.formFieldMap.TUB161.codeSetNm}"/>
                </td>
            </tr>
            <!-- Type of Long Term Care Facility -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB162.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB162.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB162.tooltip}">${PamForm.formFieldMap.TUB162.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB162)" codeSetNm="${PamForm.formFieldMap.TUB162.codeSetNm}"/>
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
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB163)" codeSetNm="${PamForm.formFieldMap.TUB163.codeSetNm}"/>
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
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB164)" codeSetNm="${PamForm.formFieldMap.TUB164.codeSetNm}"/>
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
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB165)" codeSetNm="${PamForm.formFieldMap.TUB165.codeSetNm}"/>
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
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB166)" codeSetNm="${PamForm.formFieldMap.TUB166.codeSetNm}"/>
                </td>
            </tr>
            <!-- Drug and Alcohol Use LDFs -->
            <%= request.getAttribute("1294") == null ? "" :  request.getAttribute("1294") %>
        </nedss:container>
        <!-- SUB_SECTION : Additional Risk Factors -->
         <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Additional Risk Factors" classType="subSect" >       
            <!-- Additional TB Risk Factors -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB167.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB167.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB167.tooltip}">${PamForm.formFieldMap.TUB167.label}                    
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB167)" 
                        codeSetNm="${PamForm.formFieldMap.TUB167.codeSetNm}"/>
                </td>
            </tr>
            <!-- Other Risk Factor(s) -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB168.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB168.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB168.tooltip}">${PamForm.formFieldMap.TUB168.label}                    
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB168)"/> 
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB169)" codeSetNm="${PamForm.formFieldMap.TUB169.codeSetNm}"/>
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
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB170)"/> 
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
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB171)" codeSetNm="${PamForm.formFieldMap.TUB171.codeSetNm}"/>
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
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB172)" codeSetNm="${PamForm.formFieldMap.TUB172.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB173)" codeSetNm="${PamForm.formFieldMap.TUB173.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB174)" codeSetNm="${PamForm.formFieldMap.TUB174.codeSetNm}"/>
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
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB175)" codeSetNm="${PamForm.formFieldMap.TUB175.codeSetNm}"/>
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
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB182)" codeSetNm="${PamForm.formFieldMap.TUB182.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB185)" codeSetNm="${PamForm.formFieldMap.TUB185.codeSetNm}"/>
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
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB176)" codeSetNm="${PamForm.formFieldMap.TUB176.codeSetNm}"/>
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
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB181)" codeSetNm="${PamForm.formFieldMap.TUB181.codeSetNm}"/>
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
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB177)" codeSetNm="${PamForm.formFieldMap.TUB177.codeSetNm}"/>
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
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB179)" codeSetNm="${PamForm.formFieldMap.TUB179.codeSetNm}"/>
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
                         <nedss:view name="PamForm" property="pamClientVO.answer(TUB183)" codeSetNm="${PamForm.formFieldMap.TUB183.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB186)" codeSetNm="${PamForm.formFieldMap.TUB186.codeSetNm}"/>
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
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB184)" codeSetNm="${PamForm.formFieldMap.TUB184.codeSetNm}"/>
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
                         <nedss:view name="PamForm" property="pamClientVO.answer(TUB187)" codeSetNm="${PamForm.formFieldMap.TUB187.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB178)" codeSetNm="${PamForm.formFieldMap.TUB178.codeSetNm}"/>
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
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB180)" codeSetNm="${PamForm.formFieldMap.TUB180.codeSetNm}"/>
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
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB188)" codeSetNm="${PamForm.formFieldMap.TUB188.codeSetNm}"/>
                </td>
            </tr>
            <!-- Specify Other Drug -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB189.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB189.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB189.tooltip}">${PamForm.formFieldMap.TUB189.label}                    
                </td>
                <td>
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB189)"/>
                </td>
            </tr>
            <!-- Other Drug 2 -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB190.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB190.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB190.tooltip}">${PamForm.formFieldMap.TUB190.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB190)" codeSetNm="${PamForm.formFieldMap.TUB190.codeSetNm}"/>
                </td>
            </tr>
            <!-- Specify Other Drug 2 -->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB191.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB191.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB191.tooltip}">${PamForm.formFieldMap.TUB191.label}                    
                </td>
                <td>
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB191)"/>
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
					<span style="${PamForm.formFieldMap.INV148.errorStyleClass}" title="${PamForm.formFieldMap.INV148.tooltip}">${PamForm.formFieldMap.INV148.label}</span>
				</td>
				<td>
					<nedss:view name="PamForm" property="pamClientVO.answer(INV148)" codeSetNm="${PamForm.formFieldMap.INV148.codeSetNm}"/>
				</td>
			</tr>
			 <!-- Is this person a food handler:-->
			<tr class="${PamForm.formFieldMap.INV149.state.visibleString}">
				<td class="fieldName">
					<span style="${PamForm.formFieldMap.INV149.state.requiredIndClass}">*</span>
					<span style="${PamForm.formFieldMap.INV149.errorStyleClass}" title="${PamForm.formFieldMap.INV149.tooltip}">${PamForm.formFieldMap.INV149.label}</span>
				</td>
				<td>
					<nedss:view name="PamForm" property="pamClientVO.answer(INV149)" codeSetNm="${PamForm.formFieldMap.INV149.codeSetNm}"/>
				</td>
			</tr>
				<!--Is this case part of an outbreak: -->
			<tr class="${PamForm.formFieldMap.INV150.state.visibleString}">
			     <td class="fieldName"> 
			         <span style="${PamForm.formFieldMap.INV150.state.requiredIndClass}">*</span>
			         <span style="${PamForm.formFieldMap.INV150.errorStyleClass}"
			                 title="${PamForm.formFieldMap.INV150.tooltip}">${PamForm.formFieldMap.INV150.label}</span>  
			     </td>
			     <td> 
			     <nedss:view name="PamForm" property="pamClientVO.answer(INV150)" codeSetNm="${PamForm.formFieldMap.INV150.codeSetNm}"/>	
			 	</td>
			 </tr>
				<!--Outbreak Name: -->
				<tr class="${PamForm.formFieldMap.INV151.state.visibleString}">
			     <td class="fieldName"> 
			         <span style="${PamForm.formFieldMap.INV151.state.requiredIndClass}">*</span>
			         <span style="${PamForm.formFieldMap.INV151.errorStyleClass}"
			                 title="${PamForm.formFieldMap.INV151.tooltip}">${PamForm.formFieldMap.INV151.label}</span>  
			     </td>
			     <td> 	  
				 <nedss:view name="PamForm" property="pamClientVO.answer(INV151)" codeSetNm="${PamForm.formFieldMap.INV151.codeSetNm}"/>
			 	</td>
			 </tr>
	         <!-- Comments LDFs -->
	         <%= request.getAttribute("1363") == null ? "" :  request.getAttribute("1363") %>                              
        </nedss:container>
        </div>
        
		<!-- SUB_SECTION Disease Aquisition-->
		 <div class='${PamForm.formFieldMap["1368"].state.visibleString}'>
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Disease Acquisition" classType="subSect">
			<jsp:include page="/pam/ext/view/DiseaseAcquisition.jsp"/>
		      	<!-- Comments LDFs -->
	         <%= request.getAttribute("1368") == null ? "" :  request.getAttribute("1368") %>
		</nedss:container>
		</div>
		<!-- SUB_SECTION CaseStatus-->
		 <div class='${PamForm.formFieldMap["1368"].state.visibleString}'>
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Case Status" classType="subSect">
			<jsp:include page="/pam/ext/view/CaseStatus.jsp"/>
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
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.INV167.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV167.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV167.tooltip}">${PamForm.formFieldMap.INV167.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(INV167)"/> 
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
    </div>    
</div> <!-- view -->

</td> </tr>