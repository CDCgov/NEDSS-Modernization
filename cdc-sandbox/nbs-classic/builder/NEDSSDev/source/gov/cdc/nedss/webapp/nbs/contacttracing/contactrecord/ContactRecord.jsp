<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	

<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<% 
    int subSectionIndex = 0;
    String tabId = "editContactRecord ";
    String []sectionNames = {"Contact Record"};
    int sectionIndex = 0;
%>

<tr> <td>
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
                    <html:select  title="${contactTracingForm.formFieldMap.CON134.label}" disabled ="${contactTracingForm.formFieldMap.CON134.state.disabled}" name="contactTracingForm" property="cTContactClientVO.answerMap(CON134)" styleId = "CON134">
                            <html:optionsCollection property="jurisdictionListNoExport" value="key" label="value"/>
                    </html:select>
                    <html:hidden property="attributeMap.NBSSecurityJurisdictions" 
                            styleId="NBSSecurityJurisdictions"/>
                </td>
            </tr>
            <!-- Program Area -->
            <tr>
                <td class="fieldName">
                	<span style="${contactTracingForm.formFieldMap.CON135.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON135.tooltip}">
                        ${contactTracingForm.formFieldMap.CON135.label}</span></td>
                <td class="conditionControl"> 
                     <html:hidden property="cTContactClientVO.answerMap(CON135)"/>
                    <%--Needs to Fix this with new form  for code setnm--%>
                    <nedss:view name="contactTracingForm" property="cTContactClientVO.answerMap(CON135)" 
                           codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/> 
                </td>
            </tr>
            <!-- Shared Indicator -->
            <tr>
                <td class="fieldName"> 
               		<span style="${contactTracingForm.formFieldMap.CON136.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON136.tooltip}">
                        ${contactTracingForm.formFieldMap.CON136.label}</span>
                </td>
                <td class="conditionControl"> 
                    <html:checkbox disabled ="${contactTracingForm.formFieldMap.CON136.state.disabled}" name="contactTracingForm" property="cTContactClientVO.answerMap(CON136)" value="1" 
                            title="${contactTracingForm.formFieldMap.CON136.tooltip}"></html:checkbox>
                </td>
            </tr>
       </nedss:container>
        <!-- SUB_SECTION : Administrative Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Administrative Information" classType="subSect" >
        	<!-- Status -->
            <tr>
                <td class="fieldName"> 
                	<span style="${contactTracingForm.formFieldMap.CON139.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON139.tooltip}">
                        ${contactTracingForm.formFieldMap.CON139.label}</span>
                </td>
                <td class="conditionControl"> 
                    <html:select disabled ="${contactTracingForm.formFieldMap.CON139.state.disabled}"
                            name="contactTracingForm" property="cTContactClientVO.answerMap(CON139)" styleId="CON139" 
                            title="${contactTracingForm.formFieldMap.CON139.tooltip}">
                        <html:optionsCollection property="codedValueNoBlnk(CON139)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--  Priority  -->
             <tr>
                <td class="fieldName"> 
                	<span style="${contactTracingForm.formFieldMap.CON112.errorStyleClass}"  title="${contactTracingForm.formFieldMap.CON112.tooltip}">
                        ${contactTracingForm.formFieldMap.CON112.label}</span>
                </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON112)" styleId="CON112" onchange="">
                        <html:optionsCollection property="codedValue(CON112)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--  Group/Lot ID  -->
             <tr>
                <td class="fieldName">
                 	<span style="${contactTracingForm.formFieldMap.CON127.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON127.tooltip}">
                        ${contactTracingForm.formFieldMap.CON127.label}</span>
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON127)" styleId="CON127" onchange="">
                        <html:optionsCollection property="codedValue(CON127)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--  Investigator  -->

            <logic:empty name="contactTracingForm" property="attributeMap.CON137Uid">
		            <tr>
		                <td class="fieldName">
			                <span style="${contactTracingForm.formFieldMap.CON137.state.requiredIndClass}">*</span>
			                <span style="${contactTracingForm.formFieldMap.CON137.errorStyleClass}"
			                        title="${contactTracingForm.formFieldMap.CON137.tooltip}">${contactTracingForm.formFieldMap.CON137.label}</span>
			            </td>
		                <td>
		                    <span id="clearCON137" class="none">
		                        <input type="button" class="Button" value="Clear/Reassign"
		                                id="" onclick="clearProvider('CON137')"/>
		                    </span>

		                    <span id="CON137SearchControls">
		                       <input type="button" class="Button" value="Search"
		                                id="CON137Icon" onclick="getProvider('CON137');" />
		                        &nbsp; - OR - &nbsp;
		                        <html:text property="cTContactClientVO.answer(CON137)" styleId="CON137Text"
		                                size="10" maxlength="10" onkeydown="" title="${contactTracingForm.formFieldMap.CON137.tooltip}"/>
		                        <input type="button" class="Button" value="Quick Code Lookup"
		                            id="CON137CodeLookupButton" onclick="getDWRProvider('CON137')"/>
		                        <div class="page_name_auto_complete" id="qec_list" style="background:#DCDCDC"></div>
		                    </span>
		                </td>
		            </tr>
		            <tr>
		                <td class="fieldName"> Investigator Selected:  </td>
		                <td>
		                    <span id="test2">
		                        <html:hidden property="attributeMap.CON137Uid"/>
		                        <span id="CON137">${contactTracingForm.attributeMap.CON137SearchResult}</span>
		                    </span>
		                </td>
		            </tr>
		       </logic:empty>
		       <logic:notEmpty name="contactTracingForm" property="attributeMap.CON137Uid">
		           <tr>
		               <td class="fieldName">
			               <span style="${contactTracingForm.formFieldMap.CON137.state.requiredIndClass}">*</span>
			               <span style="${contactTracingForm.formFieldMap.CON137.errorStyleClass}"
			                       title="${contactTracingForm.formFieldMap.CON137.tooltip}">${contactTracingForm.formFieldMap.CON137.label}</span>
			           </td>
		                <td>
		                    <span id="clearCON137">
		                        <input type="button" class="Button" value="Clear/Reassign"
		                                id="CON137CodeClearButton" onclick="clearProvider('CON137')"/>
		                    </span>

		                    <span id="CON137SearchControls" class="none">
		                       <input type="button" class="Button" value="Search"
		                                id="CON137Icon" onclick="getProvider('CON137');" />
		                        &nbsp; - OR - &nbsp;
		                        <html:text property="cTContactClientVO.answer(CON137)" styleId="CON137Text"
		                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('CON137Text','qec_list')" title="${contactTracingForm.formFieldMap.CON137.tooltip}"
		                                />
		                        <input type="button" class="Button" value="Quick Code Lookup"
		                            id="CON137CodeLookupButton" onclick="getDWRProvider('CON137')"
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
		                        <html:hidden property="attributeMap.CON137Uid"/>
		                        <span id="CON137">${contactTracingForm.attributeMap.CON137SearchResult}</span>
		                    </span>
		                </td>
		            </tr>
		        </logic:notEmpty>
		        
		  <!-- Invstigator search error. Not defined in the metadata -->
             <tr>
                 <td colspan="2" style="text-align:center;">
                     <span id="CON137Error"/></td>
                 </td>
             </tr>
             <!-- Date Assigned to Investigation -->
             <logic:equal name="contactTracingForm" property="attributeMap.CON137SearchResult" value="">
	            <tr>
	               <td class="fieldName">
	                    <span style="${contactTracingForm.formFieldMap.CON138.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.CON138.errorStyleClass}" id="CON138L" class="InputDisabledLabel"
	                            title="${contactTracingForm.formFieldMap.CON138.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON138.label}
	                    </span>
	                </td>
	                <td>
	                    <html:text disabled="true"
	                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON138)" maxlength="10"
	                            styleId="CON138" value="" onkeyup="DateMask(this,null,event)" size="10"/>
	                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON138','CON138Icon'); return false;" onkeypress ="showCalendarEnterKey('CON138','CON138Icon',event);"
	                            styleId="CON138Icon"></html:img>
	                </td>
	            </tr>
	        </logic:equal>

	        <logic:notEqual name="contactTracingForm" property="attributeMap.CON137SearchResult" value="">
	            <tr>
	               <td  class="${contactTracingForm.formFieldMap.CON138.state.visibleString}">
	                    <span style="${contactTracingForm.formFieldMap.CON138.state.requiredIndClass}">*</span>
	                    <span style="${contactTracingForm.formFieldMap.CON138.errorStyleClass}" id="CON138L" title="${contactTracingForm.formFieldMap.CON138.tooltip}">
	                        ${contactTracingForm.formFieldMap.CON138.label}
	                    </span>
	                </td>
	                <td>
	                    <html:text 
	                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON138)" maxlength="10"
	                            styleId="CON138" onkeyup="DateMask(this,null,event)" size="10"/>
	                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON138','CON138Icon'); return false;" onkeypress ="showCalendarEnterKey('CON138','CON138Icon',event);"
	                            styleId="CON138Icon"></html:img>
	                </td>
	            </tr>
	        </logic:notEqual>
	        <!-- End of investigatior -->
      
            <!-- Disposition -->
            <tr>
                <td class="fieldName">
                	<span style="${contactTracingForm.formFieldMap.CON114.errorStyleClass}"  title="${contactTracingForm.formFieldMap.CON114.tooltip}">
                        ${contactTracingForm.formFieldMap.CON114.label}</span> 
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON114)" styleId="CON114" onchange="fireRule('CON114', this)">
                        <html:optionsCollection property="codedValue(CON114)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--Disposition Date -->
        	 <tr>    
               <td class="fieldName"> 
                	<span class="${contactTracingForm.formFieldMap.CON140.state.disabledString}"  id="CON140L" style="${contactTracingForm.formFieldMap.CON140.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON140.tooltip}">
                        ${contactTracingForm.formFieldMap.CON140.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON140.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON140)" maxlength="10" 
                            styleId="CON140" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON140','CON140Icon'); return false;" onkeypress ="showCalendarEnterKey('CON140','CON140Icon',event);" 
                            styleId="CON140Icon"></html:img>
	          	</td>
            </tr>
       </nedss:container>
        <!-- SUB_SECTION : Contact Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contact Information" classType="subSect" >
        	<!--Date Named-->
        	 <tr>    
                <td class="fieldName"> 
                	<span style="${contactTracingForm.formFieldMap.CON101.state.requiredIndClass}">*</span>
                	<span style="${contactTracingForm.formFieldMap.CON101.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON101.tooltip}">
                        ${contactTracingForm.formFieldMap.CON101.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON101.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON101)" maxlength="10" 
                            styleId="CON101" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON101','CON101Icon'); return false;" onkeypress ="showCalendarEnterKey('CON101','CON101Icon',event);" 
                            styleId="CON101Icon"></html:img>
	          	</td>
            </tr>
            <!--  Relationship  -->
             <tr>
                <td class="fieldName">
                	<span style="${contactTracingForm.formFieldMap.CON103.state.requiredIndClass}">*</span>
                	<span style="${contactTracingForm.formFieldMap.CON103.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON103.tooltip}">
                        ${contactTracingForm.formFieldMap.CON103.label}</span> 
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON103)" styleId="CON103" onchange="">
                        <html:optionsCollection property="codedValue(CON103)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--  Health Status  -->
             <tr>
                <td class="fieldName">
                	<span style="${contactTracingForm.formFieldMap.CON115.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON115.tooltip}">
                        ${contactTracingForm.formFieldMap.CON115.label}</span> 
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON115)" styleId="CON115" onchange="">
                        <html:optionsCollection property="codedValue(CON115)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
       </nedss:container>
        <!-- SUB_SECTION : Contact Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Exposure Information" classType="subSect" >
        	<!--Exposur Type -->
        	 <tr>    
                <td class="fieldName"> 
                	<span style="${contactTracingForm.formFieldMap.CON104.state.requiredIndClass}">*</span>
                	<span style="${contactTracingForm.formFieldMap.CON104.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON104.tooltip}">
                        ${contactTracingForm.formFieldMap.CON104.label}</span> 
                </td> 
	          	<td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON104)" styleId="CON104" onchange="">
                        <html:optionsCollection property="codedValue(CON104)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--  Exposur Site Type  -->
             <tr>
                <td class="fieldName">
                	<span style="${contactTracingForm.formFieldMap.CON105.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON105.tooltip}">
                        ${contactTracingForm.formFieldMap.CON105.label}</span> 
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON105)" styleId="CON105" onchange="">
                        <html:optionsCollection property="codedValue(CON105)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--Exposur Site  -->
            
   		 <logic:empty name="contactTracingForm" property="attributeMap.CON106Uid">
   	  <tr>
   		     <td class="fieldName"> 
   		         <span style="${contactTracingForm.formFieldMap.CON106.state.requiredIndClass}">*</span>
   		         <span style="${contactTracingForm.formFieldMap.CON106.errorStyleClass}"
   		                 title="${contactTracingForm.formFieldMap.CON106.tooltip}">${contactTracingForm.formFieldMap.CON106.label}</span>  
   		     </td>
   		     <td>
   		         <span id="clearCON106" class="none">        
   		             <input type="button" class="Button" value="Clear/Reassign" 
   		                     id="CON106CodeClearButton" onclick="clearOrganization('CON106');" />
   	             </span>   
   	             
   	             <span id="CON106SearchControls">
                      <input type="button" class="Button" value="Search" 
                               id="CON106Icon" onclick="getReportingOrg('CON106');" />
                       &nbsp; - OR - &nbsp;
                       <html:text property="cTContactClientVO.answer(CON106)" styleId="CON106Text"
                               size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('CON106Text','CON106_qec_list')" title="${contactTracingForm.formFieldMap.CON106.tooltip}"/>
                       <input type="button" class="Button" value="Quick Code Lookup" 
                           id="CON106CodeLookupButton" onclick="getDWROrganization('CON106');"/>                                
                       <div class="page_name_auto_complete" id="CON106_qec_list" style="background:#DCDCDC"></div>
                    </span>
   		     </td>
   		 </tr>
   		 <tr>
   	         <td class="fieldName">Organization Selected:  </td>
   	         <td>
   	             <!--  <span class="none test2"> -->
   	             <span class="test2">
   	                 <html:hidden property="attributeMap.CON106Uid"/>
   	                 <span id="CON106">${contactTracingForm.attributeMap.CON106SearchResult}</span>
   	             </span>
   	         </td>
   	     </tr>
   	</logic:empty>
   	
   	 <logic:notEmpty name="contactTracingForm" property="attributeMap.CON106Uid">
   	 		<tr>
   			     <td class="fieldName"> 
   			         <span style="${contactTracingForm.formFieldMap.CON106.state.requiredIndClass}">*</span>
   			         <span style="${contactTracingForm.formFieldMap.CON106.errorStyleClass}"
   			                 title="${contactTracingForm.formFieldMap.CON106.tooltip}">${contactTracingForm.formFieldMap.CON106.label}</span>  
   			     </td>
   			     <td>
   			         <span id="clearCON106" >        
   			             <input type="button" class="Button" value="Clear/Reassign" 
   			                     id="CON106CodeClearButton" onclick="clearOrganization('CON106');"/>
   			         </span>
   			         
   			         <span id="CON106SearchControls" class="none">
                          <input type="button" class="Button" value="Search" 
                                   id="CON106Icon" onclick="getReportingOrg('CON106');" />
                           &nbsp; - OR - &nbsp;
                           <html:text property="cTContactClientVO.answer(CON106)" styleId="CON106Text"
                                   size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('CON106Text','CON106_qec_list')" title="${contactTracingForm.formFieldMap.CON106.tooltip}" 
                                   />
                           <input type="button" class="Button" value="Quick Code Lookup" 
                               id="CON106CodeLookupButton" onclick="getDWROrganization('CON106');" 
                               style="visibility:hidden"/>                                
                           <div class="page_name_auto_complete" id="CON106_qec_list" style="background:#DCDCDC"></div>
                       </span>
   			     </td>
   		</tr>
   	    <tr>
                <td class="fieldName">Organization Selected:  </td>
                <td>
                    <!--  <span class="none test2"> -->
                    <span class="test2">
                        <html:hidden property="attributeMap.CON106Uid"/>
                        <span id="CON106">${contactTracingForm.attributeMap.CON106SearchResult}</span>
                    </span>
                </td>
   	   </tr>
   	</logic:notEmpty>
   	 <!-- Reporting search error. Not defined in the metadata -->
   	    <tr>
   	        <td colspan="2" style="text-align:center;">
   	            <span id="CON106Error"/></td>
   	        </td>
   	    </tr>
   		 
            <!--First Exposur Date -->
        	 <tr>    
                <td class="fieldName"> 
                	<span title="${contactTracingForm.formFieldMap.CON107.tooltip}">
                        ${contactTracingForm.formFieldMap.CON107.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON107.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON107)" maxlength="10" 
                            styleId="CON107" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON107','CON107Icon'); return false;" onkeypress ="showCalendarEnterKey('CON107','CON107Icon',event);" 
                            styleId="CON107Icon"></html:img>
	          	</td>
            </tr>
             <!--Last Exposer Date -->
        	 <tr>    
                <td class="fieldName"> 
                	<span style="${contactTracingForm.formFieldMap.CON108.errorStyleClass}" title="${contactTracingForm.formFieldMap.CON108.tooltip}">
                        ${contactTracingForm.formFieldMap.CON108.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON108.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON108)" maxlength="10" 
                            styleId="CON108" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON108','CON108Icon'); return false;"  onkeypress ="showCalendarEnterKey('CON108','CON108Icon',event);" 
                            styleId="CON108Icon"></html:img>
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
	               <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${contactTracingForm.formFieldMap.CON133.state.disabled}"  styleId ="CON133"
                           onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"   name="contactTracingForm" property="cTContactClientVO.answer(CON133)"/>
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
    
</div> </td> </tr>