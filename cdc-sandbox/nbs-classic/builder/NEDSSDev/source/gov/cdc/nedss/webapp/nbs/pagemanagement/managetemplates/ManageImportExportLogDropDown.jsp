<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(PROCESSEDTIME)" styleId = "lprocessedTime" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="recordStatusTime" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(TYPE)" styleId = "ltype" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="impExpIndCdDesc" value="key" label="value"/>
</html:select>
<html:select property="answerArray(TEMPLATENAME)" styleId = "ltemplateName" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="viewLink" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SOURCE)" styleId = "lsource" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="srcName" value="key" label="value"/>
</html:select>
<html:select property="answerArray(STATUS)" styleId = "lstatus" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="recordStatusCd" value="key" label="value"/>
</html:select>