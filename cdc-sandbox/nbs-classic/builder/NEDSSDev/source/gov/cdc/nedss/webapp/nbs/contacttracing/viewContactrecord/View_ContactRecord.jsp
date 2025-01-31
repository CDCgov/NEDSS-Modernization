<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>


<tr> <td>
	<% 
	    int subSectionIndex = 0;
	    String tabId = "viewContactRecord";
	    String []sectionNames = {"Contact Record"};
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
	     <!-- SUB_SECTION : Contact Record Security -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contact Record Security" classType="subSect" >
	        <!-- Jurisdiction -->
	            <tr>
	                <td class="fieldName"> 
	                	<span style="${contactTracingForm.formFieldMap.CON134.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.CON134.errorStyleClass}" 
	                            title="${contactTracingForm.formFieldMap.CON134.tooltip}"> 
	                        ${contactTracingForm.formFieldMap.CON134.label} 
	                    </span> 
	                </td>
	                <td class="conditionControl"> 
	                     <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON134)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
	                </td>
	                 <td class="none"> 
                    <html:select title="${contactTracingForm.formFieldMap.CON134.label}" disabled ="${contactTracingForm.formFieldMap.CON134.state.disabled}" name="contactTracingForm" property="cTContactClientVO.answerMap(CON134)" styleId = "CON134">
                            <html:optionsCollection property="jurisdictionListNoExport" value="key" label="value"/>
                    </html:select>
                    <html:hidden property="attributeMap.NBSSecurityJurisdictions" 
                            styleId="NBSSecurityJurisdictions"/>
                </td>

	            </tr>
	            <!-- Program Area -->
	            <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON135.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON135.label}</span></td>
	                <td class="conditionControl"> 
	                     <html:hidden property="cTContactClientVO.answer(CON135)"/>
	                    <%--Needs to Fix this with new form  for code setnm--%>
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON135)" 
	                           codeSetNm="<%=NEDSSConstants.PROG_AREA%>" /> 
	                </td>
	            </tr>
	            <!-- Shared Indicator -->
	            <tr>
	                <td class="fieldName"> 
	               		<span title="${contactTracingForm.formFieldMap.CON136.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON136.label}</span>
	                </td>
	                <td class="conditionControl"> 
	                    <logic:equal name="contactTracingForm" property="cTContactClientVO.answer(CON136)" value="1">
                        Yes
	                   </logic:equal>
	                   <logic:notEqual name="contactTracingForm" property="cTContactClientVO.answer(CON136)" value="1">
	                        No
	                   </logic:notEqual>
	                    
	                </td>
	            </tr>
	       </nedss:container>
	        <!-- SUB_SECTION : Administrative Information -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Administrative Information" classType="subSect" >
	        	<!-- Status -->
	            <tr>
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON139.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON139.label}</span>
	                </td>
	                <td class="conditionControl"> 
	                   <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON139)" codeSetNm="${contactTracingForm.formFieldMap.CON139.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--  Priority  -->
	             <tr>
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON112.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON112.label}</span>
	                </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON112)" codeSetNm="${contactTracingForm.formFieldMap.CON112.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--  Group/Lot ID  -->
	             <tr>
	                <td class="fieldName">
	                 	<span title="${contactTracingForm.formFieldMap.CON127.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON127.label}</span>
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON127)" codeSetNm="${contactTracingForm.formFieldMap.CON127.codeSetNm}"/>
	                </td>
	            </tr>
	             <!-- Investigator -->            
	            <tr>
	                <td class="fieldName"> 
	                    <span style="${contactTracingForm.formFieldMap.CON137.errorStyleClass}"
	                            title="${contactTracingForm.formFieldMap.CON137.tooltip}">${contactTracingForm.formFieldMap.CON137.label}</span>  
	                </td>
	
	                <td>
	                   <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON137)"/>   
	                       <span id="CON137">${contactTracingForm.attributeMap.CON137SearchResult}</span>
	                </td>
	            </tr>    
	             <!--Date Assigned-->
	        	 <tr>    
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON138.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON138.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON138)"/>
		          	</td>
	            </tr>
	            <!-- Disposition -->
	            <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON114.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON114.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON114)" codeSetNm="${contactTracingForm.formFieldMap.CON114.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--Disposition Date -->
	        	 <tr>    
	               <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON140.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON140.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON140)"/>
		          	</td>
	            </tr>
	       </nedss:container>
	        <!-- SUB_SECTION : Contact Information -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contact Information" classType="subSect" >
	        	<!--Date Named-->
	        	 <tr>    
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON101.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON101.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON101)" />
		          	</td>
	            </tr>
	            <!--  Relationship  -->
	             <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON103.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON103.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON103)" codeSetNm="${contactTracingForm.formFieldMap.CON103.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--  Health Status  -->
	             <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON115.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON115.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON115)" codeSetNm="${contactTracingForm.formFieldMap.CON115.codeSetNm}"/>
	                </td>
	            </tr>
	       </nedss:container>
	        <!-- SUB_SECTION : Contact Information -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Exposure Information" classType="subSect" >
	        	<!--Exposer Type -->
	        	 <tr>    
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON104.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON104.label}</span> 
	                </td> 
		          	<td>
		               <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON104)" codeSetNm="${contactTracingForm.formFieldMap.CON104.codeSetNm}" />
		          	</td>
	            </tr>
	            <!--  Exposer Site Type  -->
	             <tr>
	                <td class="fieldName">
	                	<span title="${contactTracingForm.formFieldMap.CON105.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON105.label}</span> 
	                 </td>
	                <td class="conditionControl"> 
	                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON105)" codeSetNm="${contactTracingForm.formFieldMap.CON105.codeSetNm}"/>
	                </td>
	            </tr>
	            <!--Exposer Site  -->
	            <tr  class='${contactTracingForm.formFieldMap.CON106.state.visibleString}'>
				     <td class="fieldName"> 
				         <span style="${contactTracingForm.formFieldMap.CON106.state.requiredIndClass}">*</span>
				         <span style="${contactTracingForm.formFieldMap.CON106.errorStyleClass}"
				                 title="${contactTracingForm.formFieldMap.CON106.tooltip}">${contactTracingForm.formFieldMap.CON106.label}</span>  
				     </td>
				     <td>
				     	<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON106)"/>
				     	<span id="CON106">${contactTracingForm.attributeMap.CON106SearchResult}</span>
				     </td>
				 </tr>
	            <!--First Exposer Date -->
	        	 <tr>    
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON107.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON107.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON107)"/>
		          	</td>
	            </tr>
	             <!--Last Exposer Date -->
	        	 <tr>    
	                <td class="fieldName"> 
	                	<span title="${contactTracingForm.formFieldMap.CON108.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON108.label}</span> 
	                </td> 
		          	<td>
		                <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON108)"/>
		          	</td>
	            </tr>
	       </nedss:container>
	        <!-- SUB_SECTION : Contact Record Comments -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contact Record Comments" classType="subSect" >
	        	<!--Date Named-->
	        	 <tr>    
	                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON133.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON133.label}</span>
	                </td> 
		          	<td>
		               <nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON133)" />
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
</td> </tr>