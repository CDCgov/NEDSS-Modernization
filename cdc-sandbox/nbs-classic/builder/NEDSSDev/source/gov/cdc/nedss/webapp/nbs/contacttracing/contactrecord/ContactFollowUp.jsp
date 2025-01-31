<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>

<tr> <td>
<% 
    int subSectionIndex = 0;
    String tabId = "editContactFollowUp ";
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
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON128)" styleId="CON128" onchange="fireRule('CON128', this)">
                        <html:optionsCollection property="codedValue(CON128)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Symptoms Onset Date -->
            <tr>
                <td class="fieldName"> 
                	<span   class="${contactTracingForm.formFieldMap.CON129.state.disabledString}"  id="CON129L" style="${contactTracingForm.formFieldMap.CON129.errorStyleClass}" 
 				title="${contactTracingForm.formFieldMap.CON129.tooltip}">
                        ${contactTracingForm.formFieldMap.CON129.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON129.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON129)" maxlength="10" 
                            styleId="CON129" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON129','CON129Icon'); return false;" onkeypress ="showCalendarEnterKey('CON129','CON129Icon',event);" 
                            styleId="CON129Icon"></html:img>
	          	</td>
            </tr>
            <!-- Signs & Symptoms Notes -->
            <tr>
                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON130.tooltip}">
                        ${contactTracingForm.formFieldMap.CON130.label}</span>
                </td> 
	          	<td>
	               <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${contactTracingForm.formFieldMap.CON130.state.disabled}"  styleId ="CON130"
                           onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"   name="contactTracingForm" property="cTContactClientVO.answer(CON130)"/>
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
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON131)" styleId="CON131" onchange="">
                        <html:optionsCollection property="codedValue(CON131)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--Risk Factor Notes -->
            <tr>
                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON132.tooltip}">
                        ${contactTracingForm.formFieldMap.CON132.label}</span>
                </td> 
	          	<td>
	               <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${contactTracingForm.formFieldMap.CON132.state.disabled}"  styleId ="CON132"
                           onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"   name="contactTracingForm" property="cTContactClientVO.answer(CON132)"/>
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
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON117)" styleId="CON117" onchange="fireRule('CON117', this)">
                        <html:optionsCollection property="codedValue(CON117)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
             <!-- Date Of Evaluation -->
            <tr>
                <td class="fieldName"> 
                	<span   class="${contactTracingForm.formFieldMap.CON118.state.disabledString}"  id="CON118L" style="${contactTracingForm.formFieldMap.CON118.errorStyleClass}" 
					 title="${contactTracingForm.formFieldMap.CON118.tooltip}">
                        ${contactTracingForm.formFieldMap.CON118.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON118.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON118)" maxlength="10" 
                            styleId="CON118" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON118','CON118Icon'); return false;" onkeypress ="showCalendarEnterKey('CON118','CON118Icon',event);" 
                            styleId="CON118Icon"></html:img>
            </tr>
            <!--Evaluation Findings -->
            <tr>
                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON119.tooltip}">
                        ${contactTracingForm.formFieldMap.CON119.label}</span>
                </td> 
	          	<td>
	               <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${contactTracingForm.formFieldMap.CON119.state.disabled}"  styleId ="CON119"
                           onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"   name="contactTracingForm" property="cTContactClientVO.answer(CON119)"/>
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
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON120)" styleId="CON120" onchange="fireRule('CON120', this)">
                        <html:optionsCollection property="codedValue(CON120)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
             <!-- Treatment Start Date-->
            <tr>
                 <td class="fieldName"> 
                	<span   class="${contactTracingForm.formFieldMap.CON121.state.disabledString}"  id="CON121L" style="${contactTracingForm.formFieldMap.CON121.errorStyleClass}" 
  title="${contactTracingForm.formFieldMap.CON121.tooltip}">
                        ${contactTracingForm.formFieldMap.CON121.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON121.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON121)" maxlength="10" 
                            styleId="CON121" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON121','CON121Icon'); return false;" onkeypress ="showCalendarEnterKey('CON121','CON121Icon',event);" 
                            styleId="CON121Icon"></html:img>
	          	</td>
            </tr>
            <!-- Reason Treatment Not Started -->
            <tr>
                 <td class="fieldName">
                 	<span   class="${contactTracingForm.formFieldMap.CON122.state.disabledString}"  id="CON122L" style="${contactTracingForm.formFieldMap.CON122.errorStyleClass}" 
                               title="${contactTracingForm.formFieldMap.CON122.tooltip}">
                        ${contactTracingForm.formFieldMap.CON122.label}</span>
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm"  disabled ="${contactTracingForm.formFieldMap.CON122.state.disabled}" property="cTContactClientVO.answer(CON122)" styleId="CON122" onchange="">
                        <html:optionsCollection property="codedValue(CON122)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Was Treatment Completed-->
            <tr>
                 <td class="fieldName">
                 	<span class="${contactTracingForm.formFieldMap.CON123.state.disabledString}"  id="CON123L" title="${contactTracingForm.formFieldMap.CON123.tooltip}">
                        ${contactTracingForm.formFieldMap.CON123.label}</span>
                 </td>
                <td class="conditionControl"> 
                    <html:select disabled ="${contactTracingForm.formFieldMap.CON123.state.disabled}" name="contactTracingForm" property="cTContactClientVO.answer(CON123)" styleId="CON123" onchange="fireRule('CON123', this)">
                        <html:optionsCollection property="codedValue(CON123)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
             <!-- Treatment End Date-->
            <tr>
                 <td class="fieldName"> 
                	<span class="${contactTracingForm.formFieldMap.CON124.state.disabledString}"  id="CON124L" title="${contactTracingForm.formFieldMap.CON124.tooltip}">
                        ${contactTracingForm.formFieldMap.CON124.label}</span> 
                </td> 
	          	<td>
	                <html:text disabled ="${contactTracingForm.formFieldMap.CON124.state.disabled}" 
                            name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(CON124)" maxlength="10" 
                            styleId="CON124" onkeyup="DateMask(this,null,event)" size="10"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('CON124','CON124Icon'); return false;" onkeypress ="showCalendarEnterKey('CON124','CON124Icon',event);" 
                            styleId="CON124Icon"></html:img>
	          	</td>
            </tr>
             <!-- Reason Treatment Not Completed? -->
            <tr>
                 <td class="fieldName">
                 	<span    class="${contactTracingForm.formFieldMap.CON125.state.disabledString}"  id="CON125L" style="${contactTracingForm.formFieldMap.CON125.errorStyleClass}" 
 				title="${contactTracingForm.formFieldMap.CON125.tooltip}">
                        ${contactTracingForm.formFieldMap.CON125.label}</span>
                 </td>
                <td class="conditionControl"> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(CON125)" styleId="CON125" disabled ="${contactTracingForm.formFieldMap.CON125.state.disabled}" onchange="">
                        <html:optionsCollection property="codedValue(CON125)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!--Treatment Notes -->
            <tr>
                <td class="fieldName"><span title="${contactTracingForm.formFieldMap.CON126.tooltip}">
                        ${contactTracingForm.formFieldMap.CON126.label}</span>
                </td> 
	          	<td>
	               <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" disabled ="${contactTracingForm.formFieldMap.CON126.state.disabled}"  styleId ="CON126"
                           onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"   name="contactTracingForm" property="cTContactClientVO.answer(CON126)"/>
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