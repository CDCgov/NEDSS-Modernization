<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
    
<!-- Page Code Starts here -->

<html:select property="answerArray(EVENTTYPE)" styleId = "fEventType" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="eventType" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText1)" styleId = "fAlgorithmName" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(SearchText2)" styleId = "fRelatedConditions" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>
<html:select property="answerArray(ACTION)" styleId = "fAction" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="action" value="key" label="value"/>
</html:select>
<html:select property="answerArray(LASTUPDATED)" styleId = "fLastUpdated" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="dateFilterList" value="key" label="value"/>
</html:select>
<html:select property="answerArray(STATUS)" styleId = "fStatus" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="status" value="key" label="value"/>
</html:select>
