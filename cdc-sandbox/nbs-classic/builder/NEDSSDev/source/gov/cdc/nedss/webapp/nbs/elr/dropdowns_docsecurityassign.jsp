<%@ include file="/jsp/tags.jsp" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored ="false" %>
<!-- Page Code Starts here -->  
           
<d:forEach items="${observationSecurityReviewForm.queueCollection}" var="item">

                          

<html:select property="answerArray(${item.dropdownProperty})" styleId = "${item.dropdownStyleId}" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">

       <html:optionsCollection property="${item.dropdownsValues}" value="key" label="value" style="width:180"/>

</html:select>

      

</d:forEach>