<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(SearchText1)" styleId = "code" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText2)" styleId = "name" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText3)" styleId = "description" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>
<html:select property="answerArray(TYPE)" styleId = "type" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="type" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(STATUS)" styleId = "stats" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="status" value="key" label="value"/>
</html:select>