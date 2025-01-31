<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<tr>
    <td class="fieldName">Label on Screen:</td>
    <td>
        <nedss:view name="manageQuestionsForm" property="selection.questionLabel" />      
    </td>
</tr>

<tr>
    <td class="fieldName">Tool Tip:</td>
    <td>
        <nedss:view name="manageQuestionsForm" property="selection.questionToolTip" />    
    </td>
</tr>

<tr>
    <td class="fieldName">Display Control:</td>
    <td>
        <nedss:view name="manageQuestionsForm" property="selection.defaultDisplayControlDesc" /> 
    </td>
</tr>