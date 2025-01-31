<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>

<!-- Multi-select box to serve as filters for specific columns -->
<html:select name="updatedNotificationsQueueForm" property="answerArray(UPDATEDATE)" styleId = "updateDateMS" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection name="updatedNotificationsQueueForm" property="updateDateOptions" value="key" label="value"/>
</html:select>
<html:select name="updatedNotificationsQueueForm" property="answerArray(SUBMITTEDBY)" styleId = "submittedByMS" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection name="updatedNotificationsQueueForm" property="submittedByOptions" value="key" label="value"/>
</html:select>
<html:select name="updatedNotificationsQueueForm" property="answerArray(RECIPIENT)" styleId = "recipientMS" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection name="updatedNotificationsQueueForm" property="recipientOptions" value="key" label="value"/>
</html:select>
<html:select name="updatedNotificationsQueueForm" property="answerArray(NOTIFICATIONCODE)" styleId = "notificationCodeMS" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection name="updatedNotificationsQueueForm" property="notificationCodeOptions" value="key" label="value"/>
</html:select>
<html:select name="updatedNotificationsQueueForm" property="answerArray(CONDITION)" styleId = "testConditionMS" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection name="updatedNotificationsQueueForm" property="conditionOptions" value="key" label="value"/>
</html:select>
<html:select name="updatedNotificationsQueueForm" property="answerArray(CASESTATUS)" styleId = "caseStatusMS" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
    <html:optionsCollection name="updatedNotificationsQueueForm" property="caseStatusOptions" value="key" label="value"/>
</html:select>
<html:select property="answerArray(SearchText1)" styleId = "patient" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
	<html:optionsCollection property="noDataArray" value="key" label="value"/>
</html:select>