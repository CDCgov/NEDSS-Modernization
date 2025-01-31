<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Page Code Starts here -->
<html:select property="answerArray(SearchText1)" styleId = "condition" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText2)" styleId = "code" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(PROGRAMAREA)" styleId = "pArea" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="programArea" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(CONDITIONFAMILY)" styleId = "cFamily" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="conditionFamily" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(COINFECTIONGROUP)" styleId = "cInfGroup" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="coinfGroup" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(ASSOCIATEDPAGE)" styleId = "associatedPage" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="associatedPage" value="key" label="value"/>
</html:select>
<html:select property="answerArray(NNDCONDITION)" styleId = "nndCondition" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="nndCondition" value="key" label="value"/>
</html:select>
<html:select property="answerArray(STATUS)" styleId = "status" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="status" value="key" label="value"/>
</html:select>