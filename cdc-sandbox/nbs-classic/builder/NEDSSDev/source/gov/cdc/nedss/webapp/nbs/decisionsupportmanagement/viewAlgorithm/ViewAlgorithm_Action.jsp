<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page
	import="gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DecisionSupportForm"%>
<%@ page
	import="gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.DecisionSupportClientVO"%>
<%@ page import="gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry"%>
<%@ page
	import="gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportUtil"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants" %>
<%@ page isELIgnored="false"%>
<% DecisionSupportForm fform = (DecisionSupportForm)request.getSession().getAttribute("DSForm");
 	DecisionSupportClientVO dsVo = fform.getDecisionSupportClientVO();
 	DecisionSupportUtil dsutil = new DecisionSupportUtil();
  %>
<tr>
	<td>
	<fieldset style="border-width: 0px;" id="condition"><nedss:container
		id="section3" name="Action" classType="sect" includeBackToTopLink="no">
		<!-- Form Entry Errors -->
		<nedss:container id="subsec4" classType="subSect" addedClass="actionSect" name="Action">
			<tr>
				<td class="fieldName" id="ActionListL">
				<span>Action:</span></td>
				<td><nedss:view name="decisionSupportForm"
					property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ACTION+")"%>'
					codeSetNm="NBS_EVENT_ACTION" />
					 <html:hidden styleId="actionVal"  property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ACTION+")"%>'/>
				</td>
			</tr>
			 <tr>
                  <td class="fieldName" id="PublishedConditionLabel">
                      <span>Condition:</span>
                  </td>
                  <td id="PublishedConditionField"> 
                      <nedss:view name="decisionSupportForm"
						property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.PUBLISHED_CONDITION+")"%>'
						methodNm='AllConditions'/> 				                            
                  </td>
              </tr>
			<!-- Investigation Type (Related Page) -->
			<tr>
				<td class="fieldName" id="RelatedLabel">
				<span>Investigation
				Type(Related Page):</span></td>
				<td id="RelatedField"><nedss:view name="decisionSupportForm"
					property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE+")"%>'
					methodNm='InvestigationTypeRelatedPage' /></td>
			</tr>
			<!-- Condition(s) -->
			<tr>
				<td class="fieldName" id="ConditionLabel">
				<span>Condition(s):</span>
				</td>
				<td id="ConditionField"><nedss:view name="decisionSupportForm"
					property='<%="decisionSupportClientVO.answerArray("+DecisionSupportConstants.CONDITIONS+")"%>'
					methodNm="ConditionDropDown"
					methodParam="${decisionSupportForm.attributeMap.FormCd}" /></td>
			</tr>
			<!-- Specific for Action= Create Investigation(TO DO) -->
			<tr>
				<td class="fieldName" id="onFailLabel">
				<span>On Failure to Create Investigation:</span></td>
				<td id="onFailField"><nedss:view name="decisionSupportForm"
					property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ON_FAILURE_TO_CREATE_INV+")"%>' codeSetNm="NBS_FAILURE_RESPONSE"/></td>
			</tr>
			<tr>
				<td class="fieldName" id="onFailReviewLabel">
				<span>On Failure to Mark as Reviewed:</span></td>
				<td id="onFailReviewField"><nedss:view name="decisionSupportForm"
					property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ON_FAILURE_TO_MARK_REVIEWED+")"%>' codeSetNm="NBS_FAILURE_RESPONSE"/>
				</td>
			</tr>
			<%-- <tr>
				<td class="fieldName" id="updateLabel">
				<span>Update Action:</span></td>
				<td id="updateField"><nedss:view name="decisionSupportForm"
					property="<%="decisionSupportClientVO.answer("+DecisionSupportConstants.UPDATE_ACTION+")"%>"
					codeSetNm="NBS_UPDATE_ACTION" /></td>
			</tr> --%>
		</nedss:container>
		
		<jsp:include page="ViewAlgorithm_AdvancedCriteria.jsp" />
	
		<!-- Subsection: Investigation Default Values -->
		<nedss:container id="IdSubSection" classType="subSect"
			name="Investigation Default Values" addedClass="batchSubSection">
			<tr>
				<td colspan="2" width="100%">
				<div id="IdSubSectionInfoMessages"></div>
						<table class="dtTable" align="center">
							<thead>
								<tr>
									<td width="5%" style="background-color: #EFEFEF;border:1px solid #666666"></td>
									<th width="40%"><b> Question </b></th>
									<th width="15%"><b> Value </b></th>
									<th width="40%"><b> Behavior </b></th>
								</tr>
							</thead>
							<%ArrayList  batchList = dsVo.getBatchEntryList();
					  		Iterator  iter = batchList.iterator();
					  		int count=0;%>
							<% while (iter.hasNext()) {
					 
			            	BatchEntry batchEnt = (BatchEntry) iter.next();
			            	if (batchEnt != null) {
			            	if(count ==0 || count%2==0){%>
							<tr class="odd">
								<%}else{%>
								<tr class="even">
									<% } %>
									<td style="width:3%;text-align:center;"><input id="viewIdSubSection" type="image"
										src="page_white_text.gif"
										onclick="viewClickedOnViewPage('<%=count%>','defaultTable');return false"
										name="image" align="middle" cellspacing="2" cellpadding="3"
										border="55" class="cursorHand" title="View" alt="View"></td>
									<td>
									<% String question = (String) batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.QUESTION);%>
									<%	
										String questionlabel = DecisionSupportUtil.getQueLabelForId(question, fform); 
										request.setAttribute("questionlabel", questionlabel);
									%>
									<c:out value="${questionlabel}" />
									</td>
									<td>
									<%
			            	String value = "";
			            	String valSet = DecisionSupportUtil.getQueValueSet(question, fform);
			            	String dataType = null;
			            	if(valSet != null && valSet.indexOf("^CODED") == -1)// Single select
			            	{
			            		if(valSet.indexOf("^^") == -1){// Multi select
			            			String[] valCode =(String[])batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.VALUE);
			            			
			            			for(int i=0; i<valCode.length; i++)
			            			{
			            				value = value+DecisionSupportUtil.getCodedescForCode(valCode[i], valSet)+", ";
			            			}
			            		}else// SelectSelect
			            		{
			            			String valCode = (String)batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.VALUE);
			            			String codeSet = valSet.substring(0,valSet.indexOf("^^"));
			            			value = DecisionSupportUtil.getCodedescForCode(valCode, codeSet);
			            		}
			            		request.setAttribute("value", value);
			            	}else if(valSet != null && valSet.indexOf("^CODED") != -1){//Structure Numeric Coded
			            		String codedVal = (String)batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.VALUE);
			            		String val = codedVal.substring(0, codedVal.indexOf("^") != -1 ? codedVal.indexOf("^"):codedVal.length());
			            		String code = codedVal.substring(codedVal.indexOf("^")+1, codedVal.length());
			            		String codeSet = valSet.substring(0, valSet.indexOf("^CODED"));
			            		String codeDesc = DecisionSupportUtil.getCodedescForCode(code, codeSet);
			            		value = val + codeDesc;
			            		request.setAttribute("value", value);
			            	}
			            	else
			            	{
			            		value= (String)batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.VALUE);// Text, Date, TextArea, Literal
			            		dataType = (String)batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.ENTITY_CLASS);
			            		request.setAttribute("value", value);
			            		request.setAttribute("dataType", dataType);
			            	}
			            	%> 
			            	
			            	<c:if test="${empty dataType}">
							    <c:out value="${value}"/>
							</c:if>
							<c:if test="${not empty dataType}">
							    <c:out value="${value}"/>
							</c:if>
			            		</td>
									<td>
									<% String behavior= (String)batchEnt.getDsmAnswerMap().get(DecisionSupportConstants.BEHAVIOR);%>
									<% String behaviorDesc = DecisionSupportUtil.getCodedescForCode(behavior, "NBS_DEFAULT_BEHAVIOR"); %>
									<%=behaviorDesc%></td>
								</tr>
								<%count=count+1; %>
								<% }
				        }
					  %>
							
						</table>

						<table role="presentation" id="defaultTable" width="100%">
							<tr>
								<td class="fieldName" width="100%">Question:</td>
								<td id="question" width="100%">&nbsp;</td>
							</tr>
							<tr>
								<td class="fieldName">Value:</td>
								<td id="value">&nbsp;</td>
							</tr>
							<tr>
								<td class="fieldName">Behavior:</td>
								<td id="behavior">&nbsp;</td>
							</tr>
							
						</table> 
				</td>
			</tr>
			<tr id="defaultidNote" style="display:none">
				<td class="fieldName">
				 <span class="InputFieldLabel" id="notesL" title="Notes"> 
						Notes: 
					</span>
				</td>
				<td> 
					<nedss:view name="decisionSupportForm" 
					property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NOTES+")"%>' />	</td>  
			</tr>
</nedss:container>
<nedss:container id="subsec7" classType="subSect" name="" displayImg="false">
	
</nedss:container>
<!-- Subsection: NND Notification Details (Specific for Create Inv with NND Notification) TODO-->
<nedss:container id="subsec6" classType="subSect"
	name="NND Notification Details">
	<tr>
		<td class="fieldName" id="onFailNotL">
		<span>On Failure to Create Notification:</span></td>
		<td><nedss:view name="decisionSupportForm"
			property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION+")"%>' codeSetNm="NBS_NOT_FAILURE_RESPONSE"/>
		</td>
	</tr>
	<tr>
		<td class="fieldName" id="queueAppLabel">
		<span>Queue for Approval:</span></td>
		<td><nedss:view name="decisionSupportForm"
			property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.QUEUE_FOR_APPROVAL+")"%>' /></td>
	</tr>
	<tr>
		<td class="fieldName" id="NotCommentL">
		<span>Notification Comments:</span></td>
		<td><nedss:view name="decisionSupportForm"
			property='<%="decisionSupportClientVO.answer("+DecisionSupportConstants.NOTIFICATION_COMMENTS+")"%>' /></td>
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
</td>
</tr>