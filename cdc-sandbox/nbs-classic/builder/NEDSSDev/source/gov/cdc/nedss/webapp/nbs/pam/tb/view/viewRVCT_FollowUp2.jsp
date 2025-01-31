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
    String tabId = "viewFollowUp2";
    String []sectionNames = {"Case Completion Report Report",  "Custom Fields"};
    int sectionIndex = 0; 
%>

<h2 class="printOnlyTabHeader">
    Follow Up 2
</h2>

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
            
    <!-- SECTION : Investigation Information --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION : Investigation Details -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="41. Sputum Culture Conversion Documented" classType="subSect" >
            <!-- Sputum Culture Conversion Documented -->           
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.TUB220.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB220.errorStyleClass}" 
                                title="${PamForm.formFieldMap.TUB220.tooltip}">
                            ${PamForm.formFieldMap.TUB220.label}
                        </span> 
                    </td>
                    <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB220)" codeSetNm="${PamForm.formFieldMap.TUB220.codeSetNm}"/>
                       
                    </td>
                </tr>
                <!-- Date of First Consistently Negative Culture -->           
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.TUB221.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB221.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB221.tooltip}">
                            ${PamForm.formFieldMap.TUB221.label}
                        </span> 
                    </td>
                    <td>
                           <nedss:view name="PamForm" property="pamClientVO.answer(TUB221)"/>
                    </td>
                </tr>
				 <!-- Reason for not documenting  sputum culture conversion -->           
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.TUB222.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB222.errorStyleClass}" 
                                title="${PamForm.formFieldMap.TUB222.tooltip}">
                            ${PamForm.formFieldMap.TUB222.label}
                        </span> 
                    </td>
                    <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB222)" codeSetNm="${PamForm.formFieldMap.TUB222.codeSetNm}"/>
                       
                    </td>
                </tr>
				 <!--Other  reason for not documenting  sputum culture conversion-->           
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.TUB223.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB223.errorStyleClass}" 
                                title="${PamForm.formFieldMap.TUB223.tooltip}">
                            ${PamForm.formFieldMap.TUB223.label}
                        </span> 
                    </td>
                    <td>
                          <nedss:view name="PamForm" property="pamClientVO.answer(TUB223)"/>
                       
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
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB224)" codeSetNm="${PamForm.formFieldMap.TUB224.codeSetNm}"/>
                </td>
            </tr>
            <!-- Moved to where -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB225.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB225.errorStyleClass}" title="${PamForm.formFieldMap.TUB225.tooltip}">${PamForm.formFieldMap.TUB225.label}</span>
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB225)" codeSetNm="${PamForm.formFieldMap.TUB225.codeSetNm}"/>
                </td>
            </tr>
            <!--In State Move - City -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB227.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB227.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB227.tooltip}">
                        ${PamForm.formFieldMap.TUB227.label}
                    </span> 
                </td>
                <td>
                           <nedss:view name="PamForm" property="pamClientVO.answer(TUB227)"/>
                </td>
            </tr>
          <!--In State Move - City 2 -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB272.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB272.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB272.tooltip}">
                        ${PamForm.formFieldMap.TUB272.label}
                    </span> 
                </td>
                <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB272)"/>
                </td>
            </tr>
			 <!-- In State Move - County -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB228.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB228.errorStyleClass}" title="${PamForm.formFieldMap.TUB228.tooltip}">${PamForm.formFieldMap.TUB228.label}</span>
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB228)" methodNm="CountyCodes" methodParam="${PamForm.attributeMap.TUB225_STATE}"/>
                </td>
            </tr>
			 <!-- out of state move - state -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB229.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB229.errorStyleClass}" title="${PamForm.formFieldMap.TUB229.tooltip}">${PamForm.formFieldMap.TUB229.label}</span>
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB229)" 
                          codeSetNm="<%=NEDSSConstants.STATE_LIST%>"  />
                </td>
            </tr>
			 <!-- out of country move - country-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB230.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB230.errorStyleClass}" title="${PamForm.formFieldMap.TUB230.tooltip}">${PamForm.formFieldMap.TUB230.label}</span>
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB230)" 
                      codeSetNm="${PamForm.formFieldMap.TUB230.codeSetNm}"/>
                </td>
            </tr>
			<!-- Transnational Referral-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB226.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB226.errorStyleClass}" title="${PamForm.formFieldMap.TUB226.tooltip}">${PamForm.formFieldMap.TUB226.label}</span>
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB226)" codeSetNm="${PamForm.formFieldMap.TUB226.codeSetNm}"/>
                </td>
            </tr>
            <!-- Moved LDFs -->
            <%= request.getAttribute("1183") == null ? "" :  request.getAttribute("1183") %>
        </nedss:container>
        
        <!-- SUBSECT : Therapy -->
		<nedss:container id='<%= (sectionNames[sectionIndex].replaceAll(" ", "")) + (++subSectionIndex) %>' name="Therapy" classType="subSect" >
			<!-- 43. Date Therapy Stopped -->
			 <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB232.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB232.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB232.tooltip}">${PamForm.formFieldMap.TUB232.label}</span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB232)"/>
                    
                </td>
            </tr>
			<!-- Reason Therapy Stopped or Never Started-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB233.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB233.errorStyleClass}" title="${PamForm.formFieldMap.TUB233.tooltip}">${PamForm.formFieldMap.TUB233.label}</span>
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB233)" codeSetNm="${PamForm.formFieldMap.TUB233.codeSetNm}"/>
                </td>
            </tr>
			<!-- Indicate cause of  death-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB234.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB234.errorStyleClass}" title="${PamForm.formFieldMap.TUB234.tooltip}">${PamForm.formFieldMap.TUB234.label}</span>
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB234)" codeSetNm="${PamForm.formFieldMap.TUB234.codeSetNm}"/>
                </td>
            </tr>
				<!-- 45. Reason Therapy Extended >12 months:-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB235.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB235.errorStyleClass}" title="${PamForm.formFieldMap.TUB235.tooltip}">${PamForm.formFieldMap.TUB235.label}</span>
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB235)" 
                        codeSetNm="${PamForm.formFieldMap.TUB235.codeSetNm}"/>
                </td>
            </tr>
			<!-- Other Reason Therapy Extended -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB236.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB236.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB236.tooltip}">
                        ${PamForm.formFieldMap.TUB236.label}
                    </span> 
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB236)"/>
                </td>
            </tr>
			<!-- 46. Type of Outpatient Health Care Provider:-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB237.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB237.errorStyleClass}" title="${PamForm.formFieldMap.TUB237.tooltip}">${PamForm.formFieldMap.TUB237.label}</span>
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answerArray(TUB237)" 
                            codeSetNm="${PamForm.formFieldMap.TUB237.codeSetNm}"/>
                </td>
            </tr>
			<!-- 47. Directly Observed Therapy(DOT):-->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB238.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB238.errorStyleClass}" title="${PamForm.formFieldMap.TUB238.tooltip}">${PamForm.formFieldMap.TUB238.label}</span>
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB238)" codeSetNm="${PamForm.formFieldMap.TUB238.codeSetNm}"/>
                </td>
            </tr>
				<!-- Number of weeks of directly observed therapy(DOT):-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB239.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB239.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB239.tooltip}">
                            ${PamForm.formFieldMap.TUB239.label}
                        </span> 
                </td>
                <td>
                           <nedss:view name="PamForm" property="pamClientVO.answer(TUB239)"/>
                </td>
            </tr>
            <!-- Therapy LDFs -->
            <%= request.getAttribute("1192") == null ? "" :  request.getAttribute("1192") %>          				
        </nedss:container>
        
        <!-- SUBSECT : 48. Final Drug Susceptibility Testing -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="48. Final Drug Susceptibility Testing" classType="subSect" >
            <!-- Was follow up  drug susceptibilty testing done-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB240.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB240.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB240.tooltip}">
                            ${PamForm.formFieldMap.TUB240.label}
                        </span> 
                </td>
              <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB240)" codeSetNm="${PamForm.formFieldMap.TUB240.codeSetNm}"/>
                </td>  
            </tr>	
            
			<!-- Date Final  Isolate Collected -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB241.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB241.errorStyleClass}" title="${PamForm.formFieldMap.TUB241.tooltip}">${PamForm.formFieldMap.TUB241.label}</span>
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB241)"/>
                </td>
            </tr>
            <!-- Specimen Type is Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB242.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB242.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB242.tooltip}">
                        ${PamForm.formFieldMap.TUB242.label}
                    </span> 
                </td>
                <td>
                        <nedss:view name="PamForm" property="pamClientVO.answer(TUB242)" codeSetNm="${PamForm.formFieldMap.TUB242.codeSetNm}"/>
                </td>
            </tr>
            <!-- Specimen Type  not  Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB243.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB243.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB243.tooltip}">
                        ${PamForm.formFieldMap.TUB243.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB243)" codeSetNm="${PamForm.formFieldMap.TUB243.codeSetNm}"/>
                </td>
            </tr>
            <!-- 48. Final Drug Susceptibility Testing LDFs -->
            <%= request.getAttribute("1201") == null ? "" :  request.getAttribute("1201") %>
        </nedss:container>
        
        <!-- SUBSECT : 48. Final Drug Susceptibility Testing: -->
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="49. Final Drug Susceptibility Results" classType="subSect" >
	        <!-- Isoniazid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB244.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB244.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB244.tooltip}">
                            ${PamForm.formFieldMap.TUB244.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB244)" codeSetNm="${PamForm.formFieldMap.TUB244.codeSetNm}"/>
                </td>
            </tr>
			 <!-- Rifampin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB245.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB245.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB245.tooltip}">
                            ${PamForm.formFieldMap.TUB245.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB245)" codeSetNm="${PamForm.formFieldMap.TUB245.codeSetNm}"/>
                </td>
            </tr>
			 <!--Pyrazinamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB246.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB246.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB246.tooltip}">
                            ${PamForm.formFieldMap.TUB246.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB246)" codeSetNm="${PamForm.formFieldMap.TUB246.codeSetNm}"/>
                </td>
            </tr>
			 <!--Ehtambutol-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB247.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB247.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB247.tooltip}">
                            ${PamForm.formFieldMap.TUB247.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB247)" codeSetNm="${PamForm.formFieldMap.TUB247.codeSetNm}"/>
                </td>
            </tr>
			 <!--Streptomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB248.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB248.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB248.tooltip}">
                            ${PamForm.formFieldMap.TUB248.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB248)" codeSetNm="${PamForm.formFieldMap.TUB248.codeSetNm}"/>
                </td>
            </tr>
            <!--Rifabutin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB255.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB255.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB255.tooltip}">
                            ${PamForm.formFieldMap.TUB255.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB255)" codeSetNm="${PamForm.formFieldMap.TUB255.codeSetNm}"/>
                </td>
            </tr>
            <!-- Rifapentine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB258.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB258.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB258.tooltip}">
                            ${PamForm.formFieldMap.TUB258.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB258)" codeSetNm="${PamForm.formFieldMap.TUB258.codeSetNm}"/>
                </td>
            </tr>
            <!--Ethionamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB249.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB249.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB249.tooltip}">
                            ${PamForm.formFieldMap.TUB249.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB249)" codeSetNm="${PamForm.formFieldMap.TUB249.codeSetNm}"/>
                </td>
            </tr>
            <!-- Amikacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB254.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB254.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB254.tooltip}">
                            ${PamForm.formFieldMap.TUB254.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB254)" codeSetNm="${PamForm.formFieldMap.TUB254.codeSetNm}"/>
                </td>
            </tr>
            <!--Kanamycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB250.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB250.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB250.tooltip}">
                            ${PamForm.formFieldMap.TUB250.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB250)" codeSetNm="${PamForm.formFieldMap.TUB250.codeSetNm}"/>
                </td>
            </tr>
            <!-- Capreomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB252.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB252.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB252.tooltip}">
                            ${PamForm.formFieldMap.TUB252.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB252)" codeSetNm="${PamForm.formFieldMap.TUB252.codeSetNm}"/>
                </td>
            </tr>
            <!-- Ciprofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB256.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB256.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB256.tooltip}">
                            ${PamForm.formFieldMap.TUB256.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB256)" codeSetNm="${PamForm.formFieldMap.TUB256.codeSetNm}"/>
                </td>
            </tr>
            <!-- Levofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB259.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB259.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB259.tooltip}">
                            ${PamForm.formFieldMap.TUB259.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB259)" codeSetNm="${PamForm.formFieldMap.TUB259.codeSetNm}"/>
                </td>
            </tr>
            <!-- Ofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB257.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB257.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB257.tooltip}">
                            ${PamForm.formFieldMap.TUB257.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB257)" codeSetNm="${PamForm.formFieldMap.TUB257.codeSetNm}"/>
                </td>
            </tr>
            <!-- Moxifloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB260.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB260.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB260.tooltip}">
                            ${PamForm.formFieldMap.TUB260.label}
                        </span> 
                </td>
                <td>
                 <nedss:view name="PamForm" property="pamClientVO.answer(TUB260)" codeSetNm="${PamForm.formFieldMap.TUB260.codeSetNm}"/>
                </td>
            </tr>
            <!-- Other Quinolones-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB261.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB261.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB261.tooltip}">
                            ${PamForm.formFieldMap.TUB261.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB261)" codeSetNm="${PamForm.formFieldMap.TUB261.codeSetNm}"/>
                </td>
            </tr>
            <!-- Cycloserine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB251.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB251.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB251.tooltip}">
                            ${PamForm.formFieldMap.TUB251.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB251)" codeSetNm="${PamForm.formFieldMap.TUB251.codeSetNm}"/>
                </td>
            </tr>
  		    <!-- Para-Amino Salicylic Acid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB253.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB253.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB253.tooltip}">
                        ${PamForm.formFieldMap.TUB253.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB253)" codeSetNm="${PamForm.formFieldMap.TUB253.codeSetNm}"/>
                </td>
            </tr>
            <!-- Other Drug -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB262.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB262.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB262.tooltip}">
                        ${PamForm.formFieldMap.TUB262.label}
                    </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB262)" codeSetNm="${PamForm.formFieldMap.TUB262.codeSetNm}"/>
                </td>
            </tr>
			 <!-- Specify Other Drug-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB263.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB263.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB263.tooltip}">
                        ${PamForm.formFieldMap.TUB263.label}
                    </span> 
                </td>
                <td>
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB263)"/>
                </td>
            </tr>
			 <!-- Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB264.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB264.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB264.tooltip}">
                        ${PamForm.formFieldMap.TUB264.label}
                    </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB264)" codeSetNm="${PamForm.formFieldMap.TUB264.codeSetNm}"/>
                </td>
            </tr>
			<!-- Specify Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB265.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB265.errorStyleClass}"  
                        title="${PamForm.formFieldMap.TUB265.tooltip}">
                        ${PamForm.formFieldMap.TUB265.label}
                    </span> 
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB265)"/>
                </td>
            </tr>
            <!-- 49. Final Drug Susceptibility Results: LDFs -->
            <%= request.getAttribute("1206") == null ? "" :  request.getAttribute("1206") %>
		</nedss:container>

        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Follow Up 2 Comments" classType="subSect" >			
			<!-- Follow Up Comments-->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB271.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB271.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB271.tooltip}">${PamForm.formFieldMap.TUB271.label}                    
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB271)"/>
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
    </div>    
</div> <!-- view -->

</td> </tr>