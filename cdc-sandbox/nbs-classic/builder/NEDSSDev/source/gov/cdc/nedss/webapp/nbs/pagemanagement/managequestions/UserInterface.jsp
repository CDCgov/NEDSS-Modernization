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

<!-- User interface subsection for questions -->
<tr>
    <td class="fieldName"> 
        <span class="boldRed">*</span>
        <span title="Default Label" id="questionLabelL">Label on Screen:</span>
    </td>
    <td>
        <html:text title="Default Label" property="selection.questionLabel" size="50" maxlength="100" style="width:500px;" styleId="questionLabel" />      
    </td>
</tr>

<tr>
    <td class="fieldName">
        <span class="boldRed">*</span> 
        <span title="Default Tooltip" id="questionToolTipL">Tool Tip:</span>
    </td>
    <td>
        <html:textarea title="Default Tooltip" style="width:500px; height:100px;" property="selection.questionToolTip" styleId="questionToolTip" />      
    </td>
</tr>

<logic:notEqual name="manageQuestionsForm" property="selection.standardQuestionIndCd" value="<%= NEDSSConstants.TRUE %>">                     
   <tr>
      <td class="fieldName">
        <span class="boldRed">*</span> 
        <span title="Default Display Control" id="defaultDisplayControlL">Display Control:</span>
      </td>
      <td>
        <html:select title="Default Display Control" property="selection.nbsUiComponentUid" styleId = "defaultDisplayControl" onchange="hidesec(this);">
            <html:optionsCollection property="displayControlList" value="key"  label="value"/>
            
        </html:select>
      </td>
    </tr>  
</logic:notEqual>
<logic:equal name="manageQuestionsForm" property="selection.standardQuestionIndCd" value="<%= NEDSSConstants.TRUE %>">                     

	<tr>
		<td class="fieldName">
                <span title="Default Display Control" id="defaultDisplayControlL">Display Control:</span> 
                </td>
                <td>
                <html:hidden styleId="defaultDisplayControl" property="selection.nbsUiComponentUid"/>
                <logic:present name="defaultDisplayControlDesc">
                  <bean:write name="defaultDisplayControlDesc" />   
                 </logic:present>
                 </td>
        </tr>
 </logic:equal>
