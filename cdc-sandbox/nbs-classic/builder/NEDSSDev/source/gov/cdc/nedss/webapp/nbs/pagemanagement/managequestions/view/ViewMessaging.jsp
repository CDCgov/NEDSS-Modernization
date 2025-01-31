<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<tr>
    <td class="fieldName">Included in Message:</td>
    <td>
        <nedss:view name="manageQuestionsForm" property="selection.nndMsgIndDesc" />
    </td>               
</tr>
         
<tr>
    <td class="fieldName">Message Variable ID:</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm" property="selection.questionIdentifierNnd" />    
        </logic:equal>
    </td>
</tr>

<tr>
    <td class="fieldName">Label in Message:</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm"  property="selection.questionLabelNnd"  />
        </logic:equal>      
    </td>
</tr>

<tr>
    <td class="fieldName">Code System Name:</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm"  property="selection.questionOidSystemTxt"  />
        </logic:equal>      
    </td>
</tr>

<tr>
    <td class="fieldName">Required in Message:</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm"   property="selection.questionReqNndDesc"  />
        </logic:equal>
    </td>               
</tr>
         
<tr>
    <td class="fieldName">HL7 Data Type:</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm" property="selection.questionDataTypeNndDesc"  />
        </logic:equal>
    </td>
</tr>      

<tr>
    <td class="fieldName">HL7 Segment:</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm" property="selection.hl7SegmentDesc"  />
        </logic:equal>
    </td>
</tr>

<tr>
    <td class="fieldName">Group Number(Order Group ID):</td>
    <td>
        <logic:equal name="manageQuestionsForm" property="selection.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
            <nedss:view name="manageQuestionsForm" property="selection.orderGrpId"  />
        </logic:equal>
    </td>
</tr>