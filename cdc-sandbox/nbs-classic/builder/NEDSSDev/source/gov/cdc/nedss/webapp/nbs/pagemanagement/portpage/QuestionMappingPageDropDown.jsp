<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(STATUS)" styleId = "pStatus" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="portStatus" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText1)" styleId = "fromID" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select><html:select property="answerArray(SearchText2)" styleId = "fromLabel" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
 <html:select property="answerArray(FRMDATATYPE)" styleId = "fromDtTyp" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="frmDataType" value="key" label="value"/>
</html:select>
<html:select property="answerArray(MAP)" styleId = "pMap" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="map" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText3)" styleId = "toID" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText4)" styleId = "toLab" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
 <html:select property="answerArray(TODATATYPE)" styleId = "toDtTyp" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection name="portPageForm" property="toDataType" value="key" label="value"/>
</html:select>
