<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<!-- Failure/Error Messages -->
<div id="globalFeedbackMessagesBar" class="screenOnly">
    <logic:present name="BaseForm">
	    <logic:notEmpty name="BaseForm" property="errorList">
	        <div class="infoBox errors" id="errorMessages">
	            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
	            <ul>
	                <logic:iterate id="errors" name="BaseForm" property="errorList">
	                        <li>${fn:escapeXml(errors)}</li>                    
	                </logic:iterate>
	            </ul>
	        </div>    
	    </logic:notEmpty>
    </logic:present>
    
    <!-- Error Messages using Action Messages-->
    <logic:messagesPresent name="error_messages">
        <div class="infoBox errors" id="errorMessages">
            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
            <ul>
                <html:messages id="msg" name="error_messages">
                    <li> <bean:write name="msg" /> </li>
                </html:messages>
            <ul>
        </div>
    </logic:messagesPresent>
    
    <!-- Success Messages -->
    <logic:messagesPresent name="success_messages">
        <div class="infoBox success" id="successMessages">
            <!-- <img src="success_24x24.gif" alt=""/> -->
            <html:messages id="msg" name="success_messages">
                <bean:write name="msg" />
            </html:messages>
        </div>
    </logic:messagesPresent>
    
     <!-- Feedback Messages -->
     <!--   <logic:messagesPresent name="feedback_messages">  -->
     <!--     <div class="infoBox messages" id="feedbackMessages">        
            <html:messages id="msg" name="feedback_messages">
                <bean:write name="msg" />
            </html:messages>
        </div>
    </logic:messagesPresent>  -->
    
     <% if ((String)request.getAttribute("pamShareConfMsg") != null) { %>
              <div class="infoBox messages">
               <%= request.getAttribute("pamShareConfMsg") %>
              </div>    
            <% } %>  
</div>