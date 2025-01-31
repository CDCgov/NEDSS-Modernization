<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(SearchText1)" styleId = "templateName" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText2)" styleId = "templateDescr" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(LASTUPDATED)" styleId = "lUpdated" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="lastUpdated" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(LASTUPDATEDBY)" styleId = "lUpdatedBy" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="lastUpdatedBy" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SOURCE)" styleId = "source" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="source" value="key" label="value"/>
</html:select>
<html:select property="answerArray(STATUS)" styleId = "status" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="status" value="key" label="value"/>
</html:select>