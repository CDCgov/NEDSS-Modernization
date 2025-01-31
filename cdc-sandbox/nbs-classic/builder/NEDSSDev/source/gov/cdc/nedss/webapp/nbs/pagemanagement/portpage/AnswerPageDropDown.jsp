<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(STATUS)" styleId = "ansStatus" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="answerStatus" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText1)" styleId = "fromID" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select><html:select property="answerArray(SearchText2)" styleId = "fromLabel" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText3)" styleId = "fromCd" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(MAP)" styleId = "ansMap" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="answerMap" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText5)" styleId = "toCd" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>