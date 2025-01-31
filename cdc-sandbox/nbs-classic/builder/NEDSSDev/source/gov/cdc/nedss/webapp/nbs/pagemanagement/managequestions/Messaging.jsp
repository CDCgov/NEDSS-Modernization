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

<tr>
    <td class="fieldName">
        <span class="boldRed">*</span> 
        <span title="Included in Message">Included in Message:</span>
    </td>
    <td>
        <html:radio  title="Included in Message Yes" styleId="nndIndY" onclick="handleMessagingFields(true)" name="manageQuestionsForm"  property="selection.nndMsgInd"  value="<%= NEDSSConstants.TRUE %>"/> Yes
        &nbsp;<html:radio  title="Included in Message No" styleId="nndIndN" onclick="handleMessagingFields(false)" name="manageQuestionsForm"  property="selection.nndMsgInd"  value="<%= NEDSSConstants.FALSE %>"/> No
    </td>               
</tr>
         
<tr class="messagingFieldTr">
    <td class="fieldName">
        <span class="boldRed">*</span>
        <span title="Message Variable ID" id="msgVariableIdL">Message Variable ID:</span>
    </td>
    <td>
        <html:text title="Message Variable ID" property="selection.questionIdentifierNnd" size="25" maxlength="50" styleId="msgVariableId"/>      
    </td>
</tr>

<tr class="messagingFieldTr">
    <td class="fieldName">
        <span class="boldRed">*</span>  
        <span title="Label in Message" id="msgLabelL">Label in Message:</span>
    </td>
    <td>
        <html:text title="Label in Message" property="selection.questionLabelNnd" size="50" maxlength="100" style="width:500px;" styleId="msgLabel"/>      
    </td>
</tr>


<tr class="messagingFieldTr">
    <td class="fieldName">
        <span class="boldRed">*</span>  
        <span title="Label in Message" id="msgCodeSysLable">Code System Name:</span>
    </td>
    <td>
        <html:select  title="Label in Message" name="manageQuestionsForm" property="selection.questionOidCode" styleId="msgcodeSysName" acomplete="false" > 
			<nedss:optionsCollection property="codedValue(CODE_SYSTEM)" value="key" label="value"/>
		</html:select>
		                                        
    </td>
</tr>

<tr class="messagingFieldTr">
    <td class="fieldName">
        <span class="boldRed">*</span>  
        <span title="Required in Message" id="reqmsg">Required in Message:</span>
    </td>
    <td>
        <html:radio title="Required in Message Yes" styleId="reqInMsgY" name="manageQuestionsForm" property="selection.questionReqNnd" value="<%= NEDSSConstants.REQUIRED %>"/> Yes	
        &nbsp;
        <html:radio title="Required in Message No" styleId="reqInMsgN" name="manageQuestionsForm" property="selection.questionReqNnd" value="<%= NEDSSConstants.OPTIONAL %>"/> No		
    </td>               
</tr>
         
<tr class="messagingFieldTr">
    <td class="fieldName">
        <span class="boldRed">*</span>  
        <span title="HL7 Data Type" id="hl7DatatypeL">HL7 Data Type:</span>
    </td>
    <td>
        <html:select title="HL7 Data Type" property="selection.questionDataTypeNnd" styleId = "hl7Datatype" >
            <html:optionsCollection property="codedValue(NBS_HL7_DATA_TYPE)" value="key" label="value"/>
        </html:select>
    </td>
</tr>
      
<tr class="messagingFieldTr">
    <td class="fieldName">
        <!--
        // NOTE: enable for post 4.0 
        <span class="boldRed">*</span>
         -->  
        <span title="HL7 Segment" id="hl7SegmentL">HL7 Segment:</span>
    </td>
    <td>
        <%-- //TODO: remove the hard coded value of 2 for post 4.0 release --%>
        <html:hidden styleId="hl7SegmentValue" property="selection.hl7Segment"/>
        <span id="hl7SegmentSpan"></span> 
        
        <%--
            // TODO: enable the select box for post 4.0 release
	        <html:select property="selection.hl7Segment" styleId = "hl7Segment" >
	            <html:optionsCollection property="codedValue(NBS_HL7_SEGMENT)" value="key" label="value"/>
	        </html:select>
        --%>
    </td>
</tr>

<tr class="messagingFieldTr">
    <td class="fieldName">
        <!--
        // NOTE: enable for post 4.0 
        <span class="boldRed">*</span>
         -->  
        <span title="Group Number(Order Group ID)" id="groupNbrL">Group Number(Order Group ID):</span>
    </td>
    <td>
        <%-- //TODO: remove the hard coded value of 2 for post 4.0 release --%>
        <html:hidden styleId="orderGroupIdValue" property="selection.orderGrpId"/>
        <span id="orderGroupIdSpan"></span>
        
        <%--
            // TODO: enable the text box for post 4.0 release
            <html:text property="selection.orderGrpId" size="5" maxlength="5" styleId="groupNbr"/> 
        --%>
              
    </td>
</tr>