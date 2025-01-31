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
    String tabId = "viewFollowUp1";
    String []sectionNames = {"Initial Drug Susceptibility Report", "Custom Fields"};
    int sectionIndex = 0; 
%>

<h2 class="printOnlyTabHeader">
    Follow Up 1
</h2>

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
        <!-- SUB_SECTION : 38.Genotyping Accession Number  -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="38.Genotyping Accession Number" classType="subSect" >
            <!-- Isolate submitted for genotyping -->           
                <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.TUB192.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB192.errorStyleClass}" 
                                title="${PamForm.formFieldMap.TUB192.tooltip}">
                            ${PamForm.formFieldMap.TUB192.label}
                        </span> 
                    </td>
                    <td>
                       <nedss:view name="PamForm" property="pamClientVO.answer(TUB192)" codeSetNm="${PamForm.formFieldMap.TUB192.codeSetNm}"/>
                       
                    </td>
                </tr>
                <!-- Genotyping Accession number -->           
                <tr>
                    <td class="fieldName"> 
                        <span style="${PamForm.formFieldMap.TUB193.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB193.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB193.tooltip}">
                            ${PamForm.formFieldMap.TUB193.label}
                        </span> 
                    </td>
                    <td>
                          <nedss:view name="PamForm" property="pamClientVO.answer(TUB193)"/>
                    </td>
                </tr>
                <!-- 38.Genotyping Accession Number LDFs -->
                <%= request.getAttribute("1142") == null ? "" :  request.getAttribute("1142") %>
            </nedss:container>
             
            <!-- SUBSECT : 39. Initial Drug Susceptibility Testing --> 
		    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="39. Initial Drug Susceptibility Testing" classType="subSect" >
            <!-- Was drug susceptibilty testing done-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB194.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB194.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB194.tooltip}">
                            ${PamForm.formFieldMap.TUB194.label}
                        </span> 
                </td>
              <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB194)" codeSetNm="${PamForm.formFieldMap.TUB194.codeSetNm}"/>
                </td>  
            </tr>	
            
			<!-- Date First Isolate Collected -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB195.state.requiredIndClass}">*</span><span style="${PamForm.formFieldMap.TUB195.errorStyleClass}" title="${PamForm.formFieldMap.TUB195.tooltip}">${PamForm.formFieldMap.TUB195.label}</span>
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB195)"/>
                </td>
            </tr>
            <!-- Specimen Type is Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB196.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB196.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB196.tooltip}">
                        ${PamForm.formFieldMap.TUB196.label}
                    </span> 
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB196)" codeSetNm="${PamForm.formFieldMap.TUB196.codeSetNm}"/>
                </td>
            </tr>
            <!-- Specimen Type  not  Sputum -->
            <tr>
                <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB197.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB197.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB197.tooltip}">
                        ${PamForm.formFieldMap.TUB197.label}
                    </span> 
                </td>
                <td>
                     <nedss:view name="PamForm" property="pamClientVO.answer(TUB197)" codeSetNm="${PamForm.formFieldMap.TUB197.codeSetNm}"/>
                </td>
            </tr>
             <!-- 39. Initial Drug Susceptibility Testing LDFs-->
             <%= request.getAttribute("1145") == null ? "" :  request.getAttribute("1145") %>
        </nedss:container>
        
        <!-- SUBSECT : 40. Initial Drug Susceptibility Results -->
		<nedss:container id="<%= tabId + (++subSectionIndex) %>" name="40. Initial Drug Susceptibility Results" classType="subSect" >
		<!-- Standard Susceptibilities -->
	        <!-- Isoniazid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB198.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB198.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB198.tooltip}">
                            ${PamForm.formFieldMap.TUB198.label}
                        </span> 
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB198)" codeSetNm="${PamForm.formFieldMap.TUB198.codeSetNm}"/>
                </td>
            </tr>
			 <!-- Rifampin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB199.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB199.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB199.tooltip}">
                            ${PamForm.formFieldMap.TUB199.label}
                        </span> 
                </td>
                <td>
                      <nedss:view name="PamForm" property="pamClientVO.answer(TUB199)" codeSetNm="${PamForm.formFieldMap.TUB199.codeSetNm}"/>
                </td>
            </tr>
			 <!--Pyrazinamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB200.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB200.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB200.tooltip}">
                            ${PamForm.formFieldMap.TUB200.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB200)" codeSetNm="${PamForm.formFieldMap.TUB200.codeSetNm}"/>
                </td>
            </tr>
			 <!--Ehtambutol-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB201.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB201.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB201.tooltip}">
                            ${PamForm.formFieldMap.TUB201.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB201)" codeSetNm="${PamForm.formFieldMap.TUB201.codeSetNm}"/>
                </td>
            </tr>
			 <!--Streptomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB202.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB202.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB202.tooltip}">
                            ${PamForm.formFieldMap.TUB202.label}
                        </span> 
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB202)" codeSetNm="${PamForm.formFieldMap.TUB202.codeSetNm}"/>
                </td>
            </tr>
            <!--Rifabutin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB209.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB209.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB209.tooltip}">
                            ${PamForm.formFieldMap.TUB209.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB209)" codeSetNm="${PamForm.formFieldMap.TUB209.codeSetNm}"/>
                </td>
            </tr>
            <!-- Rifapentine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB212.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB212.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB212.tooltip}">
                            ${PamForm.formFieldMap.TUB212.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB212)" codeSetNm="${PamForm.formFieldMap.TUB212.codeSetNm}"/>
                </td>
            </tr>
            <!--Ethionamide-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB203.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB203.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB203.tooltip}">
                            ${PamForm.formFieldMap.TUB203.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB203)" codeSetNm="${PamForm.formFieldMap.TUB203.codeSetNm}"/>
                </td>
            </tr>
            <!-- Amikacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB208.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB208.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB208.tooltip}">
                            ${PamForm.formFieldMap.TUB208.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB208)" codeSetNm="${PamForm.formFieldMap.TUB208.codeSetNm}"/>
                </td>
            </tr>
            <!--Kanamycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB204.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB204.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB204.tooltip}">
                            ${PamForm.formFieldMap.TUB204.label}
                        </span> 
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB204)" codeSetNm="${PamForm.formFieldMap.TUB204.codeSetNm}"/>
                </td>
            </tr>
            <!-- Capreomycin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB206.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB206.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB206.tooltip}">
                            ${PamForm.formFieldMap.TUB206.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB206)" codeSetNm="${PamForm.formFieldMap.TUB206.codeSetNm}"/>
                </td>
            </tr>
            <!-- Ciprofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB210.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB210.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB210.tooltip}">
                            ${PamForm.formFieldMap.TUB210.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB210)" codeSetNm="${PamForm.formFieldMap.TUB210.codeSetNm}"/>
                </td>
            </tr>
            <!-- Levofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB213.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB213.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB213.tooltip}">
                            ${PamForm.formFieldMap.TUB213.label}
                        </span> 
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB213)" codeSetNm="${PamForm.formFieldMap.TUB213.codeSetNm}"/>
                </td>
            </tr>
            <!-- Ofloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB211.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB211.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB211.tooltip}">
                            ${PamForm.formFieldMap.TUB211.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB211)" codeSetNm="${PamForm.formFieldMap.TUB211.codeSetNm}"/>
                </td>
            </tr>
            <!-- Moxifloxacin-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB214.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB214.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB214.tooltip}">
                            ${PamForm.formFieldMap.TUB214.label}
                        </span> 
                </td>
                <td>
                    <nedss:view name="PamForm" property="pamClientVO.answer(TUB214)" codeSetNm="${PamForm.formFieldMap.TUB214.codeSetNm}"/>
                </td>
            </tr>
            <!-- Other Quinolones-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB215.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB215.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB215.tooltip}">
                            ${PamForm.formFieldMap.TUB215.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB215)" codeSetNm="${PamForm.formFieldMap.TUB215.codeSetNm}"/>
                </td>
            </tr>
            <!-- Cycloserine-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB205.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB205.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB205.tooltip}">
                            ${PamForm.formFieldMap.TUB205.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB205)" codeSetNm="${PamForm.formFieldMap.TUB205.codeSetNm}"/>
                </td>
            </tr>
            <!-- Para-Amino Salicylic Acid-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB207.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB207.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB207.tooltip}">
                            ${PamForm.formFieldMap.TUB207.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB207)" codeSetNm="${PamForm.formFieldMap.TUB207.codeSetNm}"/>
                </td>
            </tr>
            <!-- Other Drug -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB216.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB216.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB216.tooltip}">
                            ${PamForm.formFieldMap.TUB216.label}
                        </span> 
                </td>
                <td>
                   <nedss:view name="PamForm" property="pamClientVO.answer(TUB216)" codeSetNm="${PamForm.formFieldMap.TUB216.codeSetNm}"/>
                </td>
            </tr>
			<!-- Specify Other Drug-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB217.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB217.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB217.tooltip}">
                            ${PamForm.formFieldMap.TUB217.label}
                        </span> 
                </td>
                <td>                
				      <nedss:view name="PamForm" property="pamClientVO.answer(TUB217)"/>
                </td>
            </tr>
			 <!-- Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB218.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB218.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB218.tooltip}">
                            ${PamForm.formFieldMap.TUB218.label}
                        </span> 
                </td>
                <td>
				 
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB218)" codeSetNm="${PamForm.formFieldMap.TUB218.codeSetNm}"/>
                </td>
            </tr>
			<!-- Specify Other Drug 2-->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.TUB219.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.TUB219.errorStyleClass}"  
                            title="${PamForm.formFieldMap.TUB219.tooltip}">
                            ${PamForm.formFieldMap.TUB219.label}
                        </span> 
                </td>
                <td>
				  <nedss:view name="PamForm" property="pamClientVO.answer(TUB219)"/>
                </td>
            </tr>
            <!-- 40. Initial Drug Susceptibility Results LDFs-->
            <%= request.getAttribute("1150") == null ? "" :  request.getAttribute("1150") %>
        </nedss:container>

        <!-- SUBSECT : Follow Up 1 Comments -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Follow Up 1 Comments" classType="subSect" >			
			<!-- Follow Up Comments-->
            <tr>
               <td class="fieldName">
                    <span style="${PamForm.formFieldMap.TUB270.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.TUB270.errorStyleClass}" 
                            title="${PamForm.formFieldMap.TUB270.tooltip}">${PamForm.formFieldMap.TUB270.label}                    
                </td>
                <td>
                  <nedss:view name="PamForm" property="pamClientVO.answer(TUB270)"/>
                </td>
            </tr>
            <!-- Follow Up Comments LDFs -->
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
    </div>
</div> <!-- view -->

</td> </tr>