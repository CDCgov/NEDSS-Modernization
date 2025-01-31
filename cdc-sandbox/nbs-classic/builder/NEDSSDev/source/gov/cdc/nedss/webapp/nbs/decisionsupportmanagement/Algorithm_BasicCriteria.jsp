<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants" %>
<%@ page isELIgnored ="false" %>
								
<tr><td>
	<fieldset style="border-width:0px;" id="condition">
	<div id="hideSection" style="display:none">
	<nedss:container id="section0" name="Hide Section" classType="sect" includeBackToTopLink="no">
	</nedss:container>
	</div>
      <nedss:container id="section2" name="Basic Algorithm Criteria" classType="sect" includeBackToTopLink="no">
    <!-- Form Entry Errors -->
            <nedss:container id="subsec1" classType="subSect" name="Basic Criteria" >
                <tr>
                    <td class="fieldName"  id="algo">
                        <font class="boldTenRed" > * </font><span>Algorithm Name:</span>
                    </td>
                    <td> 
                    	   	<html:text title = "Enter the Algorithm Name" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ALGORITHM_NM+")"%>' styleId="AlgoNm"/> 
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" id="eventT">
                        <font class="boldTenRed" > * </font><span>Event Type:</span>
                    </td>
                    <td>
                    	<logic:equal name="decisionSupportForm" property="actionMode" value="Create">
                    		<html:select title = "Enter the Event Type" name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>' onchange="getActionDropdown();"  styleId = "EVENT_TY">
                            	<html:optionsCollection property="codedValue(PUBLIC_HEALTH_EVENT)" value="key" label="value"/>
                        	</html:select> 
                    	</logic:equal>
                       <logic:notEqual name="decisionSupportForm" property="actionMode" value="Create">
                       		<%-- <html:select name="decisionSupportForm" property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>" onchange="getActionDropdown();"  styleId = "EVENT_TY">
                            	<html:optionsCollection property="codedValue(PUBLIC_HEALTH_EVENT)" value="key" label="value"/>
                        	</html:select> --%>
                    		 <nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>' codeSetNm="PUBLIC_HEALTH_EVENT" /> 
                    		 <html:hidden styleId="EVENT_TY"  property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>'/>
                    	</logic:notEqual>             
                    </td>
                </tr>
            </nedss:container> 
            <nedss:container id="subsec2" classType="subSect" name="Frequency & Scope" >
                <tr>
                    <td class="fieldName" id="frq"><font class="boldTenRed" > * </font><span>Frequency:</span>
                    </td>
                    <td>
                   		<html:select title = "Select the Frequency" name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.FREQUENCY+")"%>' styleId = "Freq">
                           	<html:optionsCollection property="codedValue(NBS_FREQUENCY)" value="key" label="value"/>
                       	</html:select> 
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" id="App"><font class="boldTenRed" > * </font><span>Apply To:</span>
                    </td>
                    <td>
                    	<logic:iterate id="dropDownCodeDT" name="decisionSupportForm" property="codedValueForApplyTo(NBS_ENTRY_METHOD)" type="gov.cdc.nedss.systemservice.util.DropDownCodeDT">
                    		<bean:define id="key" name="dropDownCodeDT"  property="key"/>
						    <html:multibox title = "Electronic Document/Documents Entered by External Users checkboxes" property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.APPLY_TO+")"%>' styleId="ApplyTo" value="<%=key.toString()%>"> 
						    	<bean:define id="value" name="dropDownCodeDT"  property="value"/>
						        <bean:write name="key"/>
						    </html:multibox>
						      <%--  <bean:write name="value"/> --%>
						       <FONT COLOR="#555555">${fn:escapeXml(value)}</FONT>
						</logic:iterate>
                    </td>
                </tr>
                <tr id ="sendingSystem">
                    <td class="fieldName" ><span>Sending System(s):</span>
                    </td>
                    <td>
                    	<div class="multiSelectBlock">
	                        <i> (Use Ctrl to select more than one value) </i> <br/>
	                        If Sending System(s) are not selected, the algorithm will be <br/>
	                        applicable to all systems listed. <br/>
	                        <html:select title = "Select Sending System(s)" property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.SENDING_SYS+")"%>'
	                                styleId="sendSys" 
	                                onchange="displaySelectedOptions(this, 'sendSys-selectedValues');"
	                                multiple="true" size="4" >
	                            <html:optionsCollection property="sendingSystemList" value="key" label="value"/>
	                        </html:select> 
	                        <br/>
	                        <div id="sendSys-selectedValues" style="margin:0.25em;">
	                           <b> Selected Values: </b>
	                        </div>
                    	</div>
                    </td>
                </tr>
                 <tr id ="laboratory">
                    <td class="fieldName"><span>Sending System(s):</span>
                    </td>
                    <td>
                    	<div class="multiSelectBlock">
	                         <i> (Use Ctrl to select more than one value) </i> <br/>
	                        If Sending System(s) are not selected, the algorithm will be <br/>
	                        applicable to all systems listed. <br/>
	                        <html:select title = "Select Sending System(s)" property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.SENDING_SYS+")"%>'
	                                styleId="selectedLaboratory" 
	                                onchange="displaySelectedOptions(this, 'laboratory-selectedValues');"
	                                multiple="true" size="4" >
	                            <html:optionsCollection property="laboratoryList" value="key" label="value"/>
	                        </html:select> 
	                        <br/>
	                        <div id="laboratory-selectedValues" style="margin:0.25em;">
	                           <b> Selected Values: </b>
	                        </div>
                    	</div>
                    </td>
                </tr>
            </nedss:container> 
            <nedss:container id="subsec3" classType="subSect" name="Administrative Information" >
                <tr>
                    <td class="fieldName"  id="abc"><span>Administrative Comments:</span>
                    </td>
                    <td>
                       <html:textarea title = "Enter the Administrative Comments" style="WIDTH: 500px; HEIGHT: 100px;" onkeyup="chkMaxLength(this,2000)" 
                       onkeydown="chkMaxLength(this,2000)"   name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADMINISTRATIVE_COMMENTS+")"%>'/>
                    </td>
                </tr>
            </nedss:container> 
    	</nedss:container>
  	 </fieldset>
  	 <div class="tabNavLinks">
        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
        <a href="javascript:navigateTab('next')"> Next </a>
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
</td></tr>