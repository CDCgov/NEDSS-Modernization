<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tr>
    <td class="fieldName"> 
        <span class="boldRed">*</span>
        <span title="Default Label in Report" id="defaultLabelReportL">Default Label in Report:</span>
    </td>
    <td>
        <html:text title="Default Label in Report" property="selection.reportAdminColumnNm" size="50" maxlength="50" style="width:500px;" styleId="defaultLabelReport" />      
    </td>
</tr>

<tr>
    <td class="fieldName">
        <span class="boldRed">*</span> 
        <span title="RDBTable Name" id="rdbTableNmL">Default RDB Table Name:</span>
    </td>
    <td >
        <!-- for referenced question -->
        <logic:equal name="manageQuestionsForm" property="selection.referencedInd" value="<%= NEDSSConstants.TRUE %>">
            <html:hidden property="selection.rdbTableNm" />		 
			<nedss:view name ="manageQuestionsForm" property="selection.rdbTableNm"/>
        </logic:equal>
        <!-- for non-referenced question -->
        <logic:notEqual name="manageQuestionsForm" property="selection.referencedInd" value="<%= NEDSSConstants.TRUE %>">
        	
		  	<logic:equal name="manageQuestionsForm" property="actionMode" value="Edit">
		  		<nedss:view name ="manageQuestionsForm" property="selection.rdbTableNm"/>
		  	</logic:equal>
		  	<logic:equal name="manageQuestionsForm" property="actionMode" value="Create">
		       		<html:text title="RDBTable Name" property="selection.rdbTableNm" styleId="rdbTableNm" size="25" style="width:200px;"/>
			</logic:equal>
		    <input type="hidden" name="rdbTableNmHid" id = "rdbTableNmHid"/>

        </logic:notEqual>
    </td>
</tr>
<tr>
    <td class="fieldName">
        <span class="boldRed">*</span> 
        <span title="RDB Column Name" id="rdbcolumnNmL">RDB Column Name:</span>
    </td>
    <td>
        <!-- for referenced question -->
        <logic:equal name="manageQuestionsForm" property="selection.referencedInd" value="<%= NEDSSConstants.TRUE %>">
            <html:hidden property="selection.rdbcolumnNm" />
            <bean:write name="manageQuestionsForm" property="selection.rdbcolumnNm"/>
        </logic:equal>
        <!-- for non-referenced question -->
        <logic:notEqual name="manageQuestionsForm" property="selection.referencedInd" value="<%= NEDSSConstants.TRUE %>">
            <html:text title="RDB Column Name" property="selection.rdbcolumnNm" size="25" maxlength="21" style="width:500px;" styleId="rdbcolumnNm" onkeyup="nospaces(this);copyValueToDataMartColumnName()"/>
        </logic:notEqual>      
    </td>
</tr>
<tr>
    <td class="fieldName">
        <span title="Data Mart Column Name" id="rdbcolumnNmL">Data Mart Column Name:</span>
    </td>
    <td>
	<logic:notEmpty name="manageQuestionsForm" property="selection.rdbcolumnNm">
	      <html:text title="Data mart column name is used as a column header in dynamically created data marts. If a data mart column name is not indicated, then this question will not appear in the dynamic data mart. Note that data mart column names must be unique for each question on a given page." property="selection.userDefinedColumnNm" size="25" maxlength="21" style="width:500px;" styleId="dataMartNm" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,21)"/>
	</logic:notEmpty>
	
	<logic:empty name="manageQuestionsForm" property="selection.rdbcolumnNm">
		<logic:equal name="manageQuestionsForm" property="selection.referencedInd" value="<%= NEDSSConstants.TRUE %>">
			<nedss:view name ="manageQuestionsForm" property="selection.userDefinedColumnNm"  />  
		</logic:equal>	
		<logic:notEqual name="manageQuestionsForm" property="selection.referencedInd" value="<%= NEDSSConstants.TRUE %>">	
		  <html:text title="Data mart column name is used as a column header in dynamically created data marts. If a data mart column name is not indicated, then this question will not appear in the dynamic data mart. Note that data mart column names must be unique for each question on a given page." property="selection.userDefinedColumnNm" size="25" maxlength="21" style="width:500px;" styleId="dataMartNm" onkeyup="isSpecialCharEnteredForDataMarts(this,null,event,21)"/>
		</logic:notEqual>   
	</logic:empty>
    </td>
</tr>