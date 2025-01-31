<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="d"%> 
<%@ page isELIgnored ="false" %>
<!-- Page Code Starts here -->
<d:forEach items="${queueCollection}" var="item">
	<d:if test="${not empty item.dropdownStyleId}">
		<html:select property="answerArray(${item.dropdownProperty})" styleId = "${item.dropdownStyleId}" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
			<html:optionsCollection property="${item.dropdownsValues}" value="key" label="value" style="width:180"/>
		</html:select>
	</d:if>
</d:forEach>
				
<input type="hidden" value="${fn:escapeXml(stringQueueCollection)}" id="stringQueueCollection"/> 
