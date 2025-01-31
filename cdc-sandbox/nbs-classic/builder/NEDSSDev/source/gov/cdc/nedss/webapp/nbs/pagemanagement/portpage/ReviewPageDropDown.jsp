<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(SearchText1)" styleId = "fromID" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select><html:select property="answerArray(SearchText2)" styleId = "fromLab" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
 <html:select property="answerArray(FRMDATATYPE)" styleId = "fromDtTyp" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="revFromDataType" value="key" label="value"/>
</html:select>
<html:select property="answerArray(MAPQUESTION)" styleId = "mapQue" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="revMapQuestion" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText3)" styleId = "toID" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText4)" styleId = "toLab" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
 <html:select property="answerArray(TODATATYPE)" styleId = "toDtTyp" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="revToDataType" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText5)" styleId = "fromAnswer" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(MAPANSWER)" styleId = "mapAns" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="revMapAnswer" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText6)" styleId = "toAnswer" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
