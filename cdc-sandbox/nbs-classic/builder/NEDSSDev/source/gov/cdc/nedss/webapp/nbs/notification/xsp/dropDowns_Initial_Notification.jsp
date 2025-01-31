<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<!-- Page Code Starts here -->
<html:select property="answerArray(SUBMITDATE)" styleId = "sdate" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="dateFilterList" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SUBMITTEDBY)" styleId = "inv" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="submittedBy" value="key" label="value"/>
</html:select>

<html:select property="answerArray(CONDITION)" styleId = "cond" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="conditions" value="key" label="value"/>
</html:select>
<html:select property="answerArray(STATUS)" styleId = "stat" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="caseStatus" value="key" label="value"/>
</html:select>
<html:select property="answerArray(TYPE)" styleId = "notif" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="type" value="key" label="value"/>
</html:select>
<html:select property="answerArray(RECIPIENT)" styleId = "recipient" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="recipient" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText1)" styleId = "patient" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText2)" styleId = "comment" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>