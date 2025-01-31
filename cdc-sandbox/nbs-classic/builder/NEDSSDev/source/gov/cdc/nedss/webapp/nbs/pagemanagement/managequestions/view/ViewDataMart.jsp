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
    <td class="fieldName">Default Label in Report:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.reportAdminColumnNm" />      
    </td>
</tr>
<%-- <logic:equal name ="manageQuestionsForm" property="selection.groupNm" value="GROUP_INV">
<tr>
    <td class="fieldName">RDB Table Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.rdbTableNm" codeSetNm="NBS_PH_DOMAINS"/>      
    </td>
</tr>
</logic:equal>
<logic:equal name ="manageQuestionsForm" property="selection.groupNm" value="GROUP_IXS">
<tr>
    <td class="fieldName">RDB Table Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.rdbTableNm" codeSetNm="NBS_PH_DOMAINS_IXS"/>      
    </td>
</tr>
</logic:equal>
<logic:equal name ="manageQuestionsForm" property="selection.groupNm" value="GROUP_CON">
<tr>
    <td class="fieldName">RDB Table Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.rdbTableNm" codeSetNm="NBS_PH_DOMAINS_CR"/>      
    </td>
</tr>
</logic:equal>
 --%>
<tr>
    <td class="fieldName">Default RDB Table Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.rdbTableNm"/>      
    </td>
</tr>
<tr>
    <td class="fieldName">RDB Column Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.rdbcolumnNm"  />      
    </td>
</tr>
<tr>
    <td class="fieldName">Data Mart Column Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.userDefinedColumnNm"  />      
    </td>
</tr>
