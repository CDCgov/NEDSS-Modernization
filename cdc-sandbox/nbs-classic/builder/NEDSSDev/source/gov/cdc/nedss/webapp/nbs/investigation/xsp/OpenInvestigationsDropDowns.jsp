<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<html:select property="answerArray(STARTDATE)" styleId = "sdate" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="startDateDropDowns" value="key" label="value" style="width:180"/>
</html:select>
<html:select property="answerArray(INVESTIGATOR)" styleId = "inv" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="investigators" value="key" label="value"/>
</html:select>
<html:select property="answerArray(JURISDICTION)" styleId = "juris" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="jurisdictions" value="key" label="value"/>
</html:select>
<html:select property="answerArray(CONDITION)" styleId = "cond" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="conditions" value="key" label="value"/>
</html:select>
<html:select property="answerArray(CASESTATUS)" styleId = "stat" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="caseStatuses" value="key" label="value"/>
</html:select>
<html:select property="answerArray(NOTIFICATION)" styleId = "notif" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="notifications" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText1)" styleId = "patient" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText2)" styleId = "investigationid" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>