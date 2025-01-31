<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>


<tr> <td>
	<% 
	    int subSectionIndex = 0;
	    String tabId = "viewContactFollowUp";
	    String []sectionNames = {"Contact Follow Up"};
	    int sectionIndex = 0;
	%>
	<div class="view"  id="<%= tabId %>" style="text-align:center;">    
	    <table role="presentation" class="sectionsToggler" style="width:100%;">
	        <tr>
	            <td>
	                <ul class="horizontalList">
	                    <li style="margin-right:5px;"><b>Go to: </b></li>
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
	
	    <%
	        // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>
	     <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect" includeBackToTopLink="no">
	        <!-- SUB_SECTION : Signs And Symptoms -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Signs And Symptoms" classType="subSect" >
	        <!-- Were there any signs/symptoms for the illness? -->
	             <tr>
	                <td class="fieldName"> 
	                	<span style="${contactTracingForm.formFieldMap.CON128.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.CON128.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.CON128.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.CON128.label} 
	                    </span> 
	                </td>
	                <td class="conditionControl"> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON128)" codeSetNm="${contactTracingForm.formFieldMap.CON128.codeSetNm}"/>
	                </td>
	            </tr>
	            <!-- Symptoms Onset Date -->
	            <tr>
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON129.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON129.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON129)" />
		          	</td>
	            </tr>
	            <!-- Signs & Symptoms Notes -->
	            <tr>
	                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON130.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON130.label}</span>
	                </td> 
		          	<td>
		               <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON130)" />
		          	</td>
	            </tr>
	       </nedss:container>
	       <!-- Risk Factors -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Risk Factors" classType="subSect" >
	        <!-- Were there any Risk Factor for the illness? -->
	            <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON131.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON131.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON131)" codeSetNm="${contactTracingForm.formFieldMap.CON131.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--Risk Factor Notes -->
	            <tr>
	                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON132.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON132.label}</span>
	                </td> 
		          	<td>
		               <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON132)" />
		          	</td>
	            </tr>
	       </nedss:container>
	        <!-- Testing & Evaluation -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Testing & Evaluation" classType="subSect" >
	        <!-- Were testing/evaluation completed for this illness? -->
	            <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON117.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON117.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON117)" codeSetNm="${contactTracingForm.formFieldMap.CON117.codeSetNm}"/>
	                </td>
	            </tr>
	             <!-- Date Of Evaluation -->
	            <tr>
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON118.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON118.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON118)"/>
	            </tr>
	            <!--Evaluation Findings -->
	            <tr>
	                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON119.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON119.label}</span>
	                </td> 
		          	<td>
		               <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON119)"/>
		          	</td>
	            </tr>
	       </nedss:container>
	       <!-- Treatment -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Treatment" classType="subSect" >
	        <!-- Were treatment initiated for this illness? -->
	            <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON120.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON120.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON120)" codeSetNm="${contactTracingForm.formFieldMap.CON120.codeSetNm}"/>
	                </td>
	            </tr>
	             <!-- Treatment Start Date-->
	            <tr>
	                 <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON121.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON121.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON121)" />
		          	</td>
	            </tr>
	            <!-- Reason Treatment Not Started -->
	            <tr>
	                 <td class="fieldName">
	                 	<span title="${contactTracingForm.formFieldMap.CON122.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON122.label}</span>
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON122)" codeSetNm="${contactTracingForm.formFieldMap.CON122.codeSetNm}"/>
	                </td>
	            </tr>
	            <!-- Was Treatment Completed-->
	            <tr>
	                 <td class="fieldName">
	                 	<span title="${contactTracingForm.formFieldMap.CON123.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON123.label}</span>
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON123)" codeSetNm="${contactTracingForm.formFieldMap.CON123.codeSetNm}"/>
	                </td>
	            </tr>
	             <!-- Treatment End Date-->
	            <tr>
	                 <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON124.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON124.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON124)" />
		          	</td>
	            </tr>
	             <!-- Reason Treatment Not Completed? -->
	            <tr>
	                 <td class="fieldName">
	                 	<span title="${contactTracingForm.formFieldMap.CON125.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON125.label}</span>
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON125)" codeSetNm="${contactTracingForm.formFieldMap.CON125.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--Treatment Notes -->
	            <tr>
	                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON126.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON126.label}</span>
	                </td> 
		          	<td>
		               <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON126)" />
		          	</td>
	            </tr>
	       </nedss:container>
	       
	    </nedss:container>
	    <div class="tabNavLinks">
            <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
            <a href="javascript:navigateTab('next')"> Next </a>
            <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
            <input type="hidden" name="endOfTab" />
    	</div>
	    
	</div>
</td></tr>