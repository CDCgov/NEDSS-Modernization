<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
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
                        <span>Algorithm Name:</span>
                    </td>
                    <td> 
                    	<nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ALGORITHM_NM+")"%>'/>
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" id="eventT">
                        <span>Event Type:</span>
                    </td>
                    <td>
                    	<nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>' codeSetNm="PUBLIC_HEALTH_EVENT"/>
                    	<html:hidden styleId="eventTypeVal"  property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>'/>
                    </td>
                </tr>
            </nedss:container> 
            <nedss:container id="subsec2" classType="subSect" name="Frequency & Scope" >
                <tr>
                    <td class="fieldName" id="frq"><span>Frequency:</span>
                    </td>
                    <td>
                       	<nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.FREQUENCY+")"%>' codeSetNm="NBS_FREQUENCY"/>
                    </td>
                </tr>
                <tr>
                    <td class="fieldName" id="App"><span>Apply To:</span>
                    </td>
                    <td>
                      	<nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.APPLY_TO+")"%>' methodNm="CodedValueForType" methodParam="NBS_ENTRY_METHOD"/>
                    </td>
                </tr>
                <tr>
                    <td class="fieldName"><span>Sending System(s):</span>
                    </td>
                    <td>
                    	<logic:equal name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>' value="PHC236">
							<nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.SENDING_SYS+")"%>' methodNm="SendingSystemList" methodParam="PHC236"/> 
						</logic:equal>
						<logic:equal name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.EVENT_TYPE+")"%>' value="11648804">
						    <nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.SENDING_SYS+")"%>' methodNm="SendingSystemList" methodParam="11648804"/> 
						</logic:equal>
                    </td>
                </tr>
            </nedss:container> 
            <nedss:container id="subsec3" classType="subSect" name="Administrative Information" >
                <tr>
                    <td class="fieldName"  id="abc"><span>Administrative Comments:</span>
                    </td>
                    <td>
                      <nedss:view name="decisionSupportForm" property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ADMINISTRATIVE_COMMENTS+")"%>'/>
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