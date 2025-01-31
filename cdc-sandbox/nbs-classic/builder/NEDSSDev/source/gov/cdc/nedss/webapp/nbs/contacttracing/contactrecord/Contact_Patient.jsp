<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<% 
    int subSectionIndex = 0;
    String tabId = "editPatient";
    String []sectionNames = {"Contact Information"};
    int sectionIndex = 0;
%>

<tr> <td>
<div class="view" id="<%= tabId %>" style="text-align:center;">    
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

    <!-- SECTION : Contact Information --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect" includeBackToTopLink="no">
        <!-- SUB_SECTION : General Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="General Information" classType="subSect" >
            <!-- As of date - Create mode -->
            <tr>
                <td class="fieldName">
                    <span style="${contactTracingForm.formFieldMap.DEM215.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM215.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM215.tooltip}">
                        ${contactTracingForm.formFieldMap.DEM215.label}
                    </span> 
                </td>
                <td>
                    <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM215)" maxlength="10"
                            styleId="DEM215" onkeyup="DateMask(this,null,event)" size="10"
                            />
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM215','DEM215Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM215','DEM215Icon',event);" 
                            styleId="DEM215Icon"></html:img>
                </td>
            </tr>
            <!-- Comments -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM196.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM196.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM196.tooltip}">${contactTracingForm.formFieldMap.DEM196.label}</span> 
                </td>
                <td> <html:textarea style="WIDTH: 500px; HEIGHT: 100px;" property="cTContactClientVO.answer(DEM196)" 
                        title="${contactTracingForm.formFieldMap.DEM196.tooltip}" onkeydown="checkMaxLength(this)" 
                        onkeyup="checkMaxLength(this)"></html:textarea> </td>
            </tr>
        </nedss:container>
        
        <!-- SUB_SECTION : Name Information -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Name Information" classType="subSect">
            <!-- Name Info as of date - Edit mode only -->
            <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName"> 
                        <span style="${contactTracingForm.formFieldMap.DEM206.state.requiredIndClass}">*</span>
                        <span style="${contactTracingForm.formFieldMap.DEM206.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM206.tooltip}">${contactTracingForm.formFieldMap.DEM206.label}</span> 
                    </td>
                    <td> 
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM206)" maxlength="10" 
                                styleId="DEM206" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM206','DEM206Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM206','DEM206Icon',event);" 
                                styleId="DEM206Icon"></html:img> 
                    </td>
                </tr>
              </logic:notEqual> 
           </logic:notEqual>
            <!-- First Name -->
            <tr>
                <td class="fieldName">
                    <span style="${contactTracingForm.formFieldMap.DEM104.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM104.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM104.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM104.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM104)" maxlength="48"
                            title="${contactTracingForm.formFieldMap.DEM104.tooltip}"/>
                </td>
            </tr>
            <!-- Middle Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM105.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM105.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM105.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM105.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM105)" maxlength="48" 
                            title="${contactTracingForm.formFieldMap.DEM105.tooltip}"/>
                </td>
            </tr>
            <!-- Last Name -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM102.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM102.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM102.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM102.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM102)" maxlength="48" 
                            title="${contactTracingForm.formFieldMap.DEM102.tooltip}"/>
                </td>
            </tr>
            <!-- Suffix -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM107.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM107.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM107.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM107.label} 
                    </span> 
                </td>
                <td> 
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM107)" styleId="DEM107" 
                            title="${contactTracingForm.formFieldMap.DEM107.tooltip}">
                        <html:optionsCollection property="codedValue(DEM107)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
             <!-- Alias/Nickname -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM250.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM250.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM250.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM250.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM250)" maxlength="48" 
                            title="${contactTracingForm.formFieldMap.DEM250.tooltip}"/>
                </td>
            </tr>
        </nedss:container>
        
	    <!-- SUB_SECTION : Other Personal Details -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Other Personal Details" classType="subSect" >
	       <!-- Other details As of Date - Edit mode only -->
            <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName"> 
                        <span style="${contactTracingForm.formFieldMap.DEM207.state.requiredIndClass}">*</span>
                        <span style="${contactTracingForm.formFieldMap.DEM207.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM207.tooltip}">
                            ${contactTracingForm.formFieldMap.DEM207.label}
                        </span>
                    </td>
                    <td>
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM207)" maxlength="10" 
                                styleId="DEM207" onkeyup="DateMask(this,null,event)" size="10"  />
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM207','DEM207Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM207','DEM207Icon',event);" 
                                styleId="DEM207Icon"></html:img>
                    </td>
                </tr>
             </logic:notEqual> 
           </logic:notEqual>
            <!-- Date of Birth -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM115.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM115.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM115.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM115.label} 
                    </span> 
                </td>
                <td> 
                    <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM115)" maxlength="10" 
                            styleId="DEM115" title="${contactTracingForm.formFieldMap.DEM115.label}" onkeyup="DateMask(this,null,event)" 
                            size="10" onchange="calculateReportedAge()" onblur="calculateReportedAge()"/>
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM115','DEM115Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM115','DEM115Icon',event);" 
                            styleId="DEM115Icon"></html:img>
                </td>
            </tr>
            <!-- Reported Age -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM216.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM216.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM216.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM216.label} 
                    </span>  
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM216)" size="3" maxlength="3" 
                            title="${contactTracingForm.formFieldMap.DEM216.tooltip}" styleId="DEM216" onkeyup="isNumericCharacterEntered(this)"/>
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM218)" styleId="DEM218">
                        <html:optionsCollection property="codedValue(DEM218)" value="key" label="value"/>
                    </html:select>
                    <!-- FIXME : see if this is 115 or not -->
                    <html:hidden property="cTContactClientVO.answer(DEM115)" styleId="DEM216"/>
                </td>
            </tr>
           
           
            <!-- Current Sex -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM113.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM113.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM113.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM113.label} 
                    </span> 
                </td>
                <td> 
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM113)" styleId="DEM113" onchange="fireRule('DEM113', this)">
                        <html:optionsCollection property="codedValue(DEM113)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Mortality Information As of Date - Edit mode only -->
             <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${contactTracingForm.formFieldMap.DEM208.state.requiredIndClass}">*</span>
                        <span style="${contactTracingForm.formFieldMap.DEM208.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM208.tooltip}">${contactTracingForm.formFieldMap.DEM208.label}</span>
                    </td>
                    <td class="topBorder">
                         <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM208)" maxlength="10" 
                                styleId="DEM208" onkeyup="DateMask(this,null,event)" size="10"/>
                         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM208','DEM208Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM208','DEM208Icon',event);" 
                                styleId="DEM208Icon"></html:img>
                    </td>
                </tr>
            </logic:notEqual> 
           </logic:notEqual>
            <!-- Is the Patient Deceased -->
            <tr>
                <td class="fieldName" id="DEM127L"> 
                    <span style="${contactTracingForm.formFieldMap.DEM127.state.requiredIndClass}">*</span>
                    <span   style="${contactTracingForm.formFieldMap.DEM127.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM127.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM127.label} 
                    </span> 
                </td>
                <td> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM127)" styleId="DEM127"  onchange="fireRule('DEM127', this)" >
                         <html:optionsCollection property="codedValue(DEM127)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Deceased Date -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM128.state.requiredIndClass}">*</span>
                    <span  class="${contactTracingForm.formFieldMap.DEM128.state.disabledString}"  id="DEM128L" style="${contactTracingForm.formFieldMap.DEM128.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM127.label}"> 
                        ${contactTracingForm.formFieldMap.DEM128.label} 
                    </span> 
                </td>
                <td> 
                    <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM128)" maxlength="10" 
                            styleId="DEM128"  title="${contactTracingForm.formFieldMap.DEM128.tooltip}" 
                            onkeyup="DateMask(this,null,event)" size="10" disabled ="${contactTracingForm.formFieldMap.DEM128.state.disabled}" />
                    <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM128','DEM128Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM128','DEM128Icon',event);" 
                            styleId="DEM128Icon"></html:img>
                </td>
            </tr>
            <!-- Marital Status as of date - Edit mode only -->
             <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                 <tr>
                    <td class="fieldName topBorder"> 
                        <span style="${contactTracingForm.formFieldMap.DEM209.state.requiredIndClass}">*</span>
                        <span style="${contactTracingForm.formFieldMap.DEM209.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM209.tooltip}">${contactTracingForm.formFieldMap.DEM209.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM209)" maxlength="10" 
                                styleId="DEM209" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM209','DEM209Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM209','DEM209Icon',event);" 
                                styleId="DEM209Icon"></html:img>
                    </td>
                </tr>
           </logic:notEqual> 
           </logic:notEqual>
            <!-- Marital Status -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM140.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM140.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM140.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM140.label} 
                    </span> 
                </td>
                <td>
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM140)" styleId="DEM140" 
                            >
                         <html:optionsCollection property="codedValue(DEM140)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            
             <!-- Primary Occupation -->
             <tr>
                <td class="fieldName topBorder"> 
                    <span style="${contactTracingForm.formFieldMap.DEM139.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM139.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM139.tooltip}">${contactTracingForm.formFieldMap.DEM139.label}</span>
                </td>
                <td class="topBorder">
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM139)" styleId="DEM139" 
                            >
                         <html:optionsCollection property="primaryOccupationCodes" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
             <!-- Birth COuntry -->
             <tr>
                <td class="fieldName topBorder"> 
                    <span style="${contactTracingForm.formFieldMap.DEM126.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM126.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM126.tooltip}">${contactTracingForm.formFieldMap.DEM126.label}</span>
                </td>
                <td class="topBorder">
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM126)" styleId="DEM126">
                         <html:optionsCollection property="birthCountry" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Primary Language -->
             <tr>
                <td class="fieldName topBorder"> 
                    <span style="${contactTracingForm.formFieldMap.DEM142.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM142.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM142.tooltip}">${contactTracingForm.formFieldMap.DEM142.label}</span>
                </td>
                <td class="topBorder">
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM142)" styleId="DEM142" 
                            >
                         <html:optionsCollection property="languageCodes" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	    </nedss:container>
	    
	    <!-- SUB_SECTION : 4. Reporting Address for Case Counting-->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Address" classType="subSect" >
	       <!-- Contact Information As of date - Edit mode only -->
            <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${contactTracingForm.formFieldMap.DEM213.state.requiredIndClass}">*</span>
                         <span style="${contactTracingForm.formFieldMap.DEM213.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM213.tooltip}">${contactTracingForm.formFieldMap.DEM213.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM213)" maxlength="10" 
                                styleId="DEM213" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM213','DEM213Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM213','DEM213Icon',event);" 
                                styleId="DEM213Icon"></html:img>
                    </td>
                </tr>
         </logic:notEqual> 
           </logic:notEqual>
            <!-- Street Address 1 -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM159.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM159.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM159.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM159.label} 
                    </span> 
                <td> 
                    <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM159)" 
                        title="${contactTracingForm.formFieldMap.DEM159.tooltip}" size="30" maxlength="100"/>
                </td>
            </tr>
            <!-- Street Address 2 -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM160.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM160.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM160.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM160.label} 
                    </span>  
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM160)" 
                        title="${contactTracingForm.formFieldMap.DEM160.tooltip}" size="30" maxlength="100"/>
                </td>
            </tr>
            <!-- City -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM161.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM161.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM161.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM161.label} 
                    </span> 
                </td>
                <td> 
                 <span id="DEM161SearchControls">
                	<html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM161)" 
                    title="${contactTracingForm.formFieldMap.DEM161.tooltip}" size="30"  maxlength="100" styleId="DEM161"  />
					<div class="page_name_auto_complete" id="city_list" style="background:#DCDCDC"></div>
					</span>
                </td>
            </tr>
            <!-- State -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM162.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM162.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM162.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM162.label} 
                    </span> 
                </td>
                <td> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM162)" 
                            onchange="getDWRCounties(this, 'countyList');getDWRCitites(this);" styleId="DEM162">
                        <html:optionsCollection property="stateList" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Zip -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM163.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM163.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM163.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM163.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM163)" size="10" 
                        maxlength="10" title="${contactTracingForm.formFieldMap.DEM163.tooltip}" onkeyup="ZipMask(this,event)"/>
                </td>
            </tr>
            <!-- County -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM165.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM165.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM165.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM165.label} 
                    </span> 
                </td>
                <td> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM165)" 
                            styleId="countyList" >
                        <!-- <html:option value="" /> -->
                        <html:optionsCollection property="dwrCounties" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Country -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM167.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM167.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM167.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM167.label} 
                    </span> 
                </td>
                <td> 
                     <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM167)" styleId="DEM167">
                        <html:option value="" />
                        <html:optionsCollection property="countryList" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
	    </nedss:container>
	    
	    <!-- SUB_SECTION : Telephone Information -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Telephone Information" classType="subSect" >
            <!-- Telephone Information As of date - Edit mode only -->
             <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${contactTracingForm.formFieldMap.DEM214.state.requiredIndClass}">*</span>
                         <span style="${contactTracingForm.formFieldMap.DEM214.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM214.tooltip}">${contactTracingForm.formFieldMap.DEM214.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM214)" maxlength="10" 
                                styleId="DEM214" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM214','DEM214Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM214','DEM214Icon',event);" 
                                styleId="DEM214Icon"></html:img>
                    </td>
                </tr>
           </logic:notEqual> 
           </logic:notEqual>
            <!-- Home Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM238.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM238.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM238.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM238.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM238)" styleId="DEM238"
                            maxlength="50" onkeyup="TeleMask(this,event)"
                            title="${contactTracingForm.formFieldMap.DEM238.tooltip}" size="15"/>
                </td>
            </tr>
           
            <!-- Work Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM240.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM240.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM240.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM240.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM240)"  styleId="DEM240"
                            maxlength="50" onkeyup="TeleMask(this,event)"  size="15"
                            title="${contactTracingForm.formFieldMap.DEM240.tooltip}"/>
                </td>
            </tr>
            <!-- Work Phone ext -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM241.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM241.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM241.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM241.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM241)" size="10" maxlength="8"  
                        title="${contactTracingForm.formFieldMap.DEM241.tooltip}" onkeyup="isNumericCharacterEntered(this)"/>
                </td>
            </tr>	
            <!-- Cell Phone -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM251.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM251.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM251.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM251.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM251)"  styleId="DEM251"
                            maxlength="50" onkeyup="TeleMask(this,event)" size="15"
                            title="${contactTracingForm.formFieldMap.DEM251.tooltip}"/>
                </td>
            </tr>  
             <!-- Email -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM252.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM252.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM252.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM252.label} 
                    </span> 
                </td>
                <td> 
                     <html:text name="contactTracingForm" property="cTContactClientVO.answer(DEM252)"  styleId="DEM252"
                             maxlength="100" size="15"
                            title="${contactTracingForm.formFieldMap.DEM252.tooltip}"/>
                </td>
            </tr>       
	    </nedss:container>
	    
	    <!-- SUB_SECTION : Ethnicity & Race Information -->
	    <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Ethnicity & Race Information" classType="subSect" >
            <!-- Ethnicity and Race Information As of date - Edit mode only -->
           <logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${contactTracingForm.formFieldMap.DEM211.state.requiredIndClass}">*</span>
                         <span style="${contactTracingForm.formFieldMap.DEM211.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM211.tooltip}">${contactTracingForm.formFieldMap.DEM211.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM211)" maxlength="10" 
                                styleId="DEM211" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM211','DEM211Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM211','DEM211Icon',event);" 
                                styleId="DEM211Icon"></html:img>
                    </td>
                </tr>
           </logic:notEqual> 
           </logic:notEqual>
            
            <!-- Ethnicity -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM155.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM155.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM155.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM155.label} 
                    </span> 
                </td>
                <td> 
                    <html:select name="contactTracingForm" property="cTContactClientVO.answer(DEM155)" styleId="DEM155">
                        <html:optionsCollection property="codedValue(DEM155)" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <!-- Race -->
        	<logic:notEqual name="contactTracingForm" property="actionMode" value="Create">	
			    <logic:notEqual name="contactTracingForm" property="actionMode" value="CREATE_SUBMIT">	
                <tr>
                    <td class="fieldName topBorder">
                         <span style="${contactTracingForm.formFieldMap.DEM212.state.requiredIndClass}">*</span>
                         <span style="${contactTracingForm.formFieldMap.DEM212.errorStyleClass}" 
                                title="${contactTracingForm.formFieldMap.DEM212.tooltip}">${contactTracingForm.formFieldMap.DEM212.label}</span>
                    </td>
                    <td class="topBorder">
                        <html:text name="contactTracingForm" title="Enter a Date" property="cTContactClientVO.answer(DEM212)" maxlength="10" 
                                styleId="DEM212" onkeyup="DateMask(this,null,event)" size="10"/>
                        <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM212','DEM212Icon'); return false;" onkeypress ="showCalendarEnterKey('DEM212','DEM212Icon',event);" 
                                styleId="DEM212Icon"></html:img>
                    </td>
                </tr>
               </logic:notEqual> 
           </logic:notEqual>
            <!-- Alaska & American Indian race -->
            <tr>
                <td class="fieldName"> 
                    <span style="${contactTracingForm.formFieldMap.DEM152.state.requiredIndClass}">*</span>
                    <span style="${contactTracingForm.formFieldMap.DEM152.errorStyleClass}" 
                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}"> 
                        ${contactTracingForm.formFieldMap.DEM152.label} 
                    </span> 
                </td>
                <td>
                   <html:checkbox name="contactTracingForm" property="cTContactClientVO.americanIndianAlskanRace" value="1" 
                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}">
                    </html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>
                </td>
            </tr>
            <!-- Asian race -->
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <!-- Asian race Only - For Varicella -->
                    <html:checkbox name="contactTracingForm" property="cTContactClientVO.asianRace" value="1" 
                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}">
                    </html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.asian"/>
                </td>
            </tr>
            
            <!-- Black/African American race -->
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <html:checkbox name="contactTracingForm" property="cTContactClientVO.africanAmericanRace" 
                            value="1" title="${contactTracingForm.formFieldMap.DEM152.tooltip}">
                    </html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>
                </td>
            </tr>
            <!-- Native Hawaiian or Other Pacific Islander race -->
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <!-- Hawaiian race Only - For Varicella -->
                    <html:checkbox name="contactTracingForm" property="cTContactClientVO.hawaiianRace" value="1" 
                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}">
	                </html:checkbox>
	                <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>
                </td>
            </tr>
            
            <!-- White race -->    
            <tr>
                <td class="fieldName">
                    &nbsp;
                </td>
                <td>
                    <html:checkbox name="contactTracingForm" property="cTContactClientVO.whiteRace" value="1" 
                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}"></html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.white"/>
                </td>
            </tr>
            <!-- Unknown race -->    
            <tr>
                <td>
                    &nbsp;
                </td>
                <td>
                    <html:checkbox name="contactTracingForm" property="cTContactClientVO.unKnownRace" value="1" 
                            title="${contactTracingForm.formFieldMap.DEM152.tooltip}"></html:checkbox>
                    <bean:message bundle="RVCT" key="rvct.unknown"/>
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