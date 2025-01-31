<%@ include file="/jsp/tags.jsp" %>
<%@ include file="/jsp/tags.jsp" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page import="java.util.*,
                gov.cdc.nedss.util.*, 
                gov.cdc.nedss.systemservice.nbssecurity.*,
                gov.cdc.nedss.webapp.nbs.action.person.util.*,
                gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*"%>
                
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<html lang="en">
    <head>
        <%@ include file="../../jsp/resources.jsp" %>
        <title>${fn:escapeXml(PageTitle)}</title>
        <script language="JavaScript" Src="NotificationSpecific.js"></script>
        <script language="javascript">          
	        function startCountdown()
	        {
	            autocompTxtValuesForJSP();
	            var sessionTimeout = <%= request.getSession().getMaxInactiveInterval()%>
	            alert(sessionTimeout.value);
	            min = sessionTimeout / 60;
	            sec = 0;                
	            getTimerCountDown();
	        }
	    </script>
    </head>  
    
    <body class onload="startCountdown();">
        <div id="doc3">
	        <form method="post" id="nedssForm" action="">
	            <div id="bd">
	                <!-- Top nav bar -->
	                <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
	                
	                <!-- Top button bar -->
	                <table role="presentation" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;" 
	                        class="bottomButtonBar">
	                    <tr>
	                        <td style="vertical-align:top; padding:0px;">
	                            <table role="presentation" align="right">
	                                <tr>
	                                    <td style="vertical-align:top; padding-top:0px;">
	                                        <input type="image" src="task_button/fa_submit.jpg" 
	                                                width="30" height="40" border="0" name="Submit" id="Submit" 
	                                                alt="Submit button" title="Submit button" class="cursorHand" onclick="return removeUpdatedNotificationsInQueue();"><br/>Submit
	                                    </td>
	                                </tr>            
	                            </table>
	                        </td>
	                    </tr>
	                </table>
	                
	                <!-- Feedback messages bar -->
	                <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
	                
		          	<!-- Check all/Clear all links -->
	                <table role="presentation">
	                    <tr valign="center" rowID="1">
	                        <td valign="top" align="" colspan="5" width="100%">
	                            <span>
	                                <a href="javascript:NoOp();" onclick="checkAll_ck();">Check All</a> | 
	                                <a href="javascript:NoOp();" onclick="clearAll_ck();">Clear All</a>
	                            </span>
	                        </td>
		          		</tr>
		          	<table role="presentation">
		          	
                    <% 
                        int maxRowCount = ((Integer)(request.getAttribute("maxRowCount"))).intValue();
                        ArrayList updatedNotificationList = new ArrayList();
                        updatedNotificationList = (ArrayList) request.getAttribute("updatedNotificationList");
                        String viewInvestigationHref= (String)request.getAttribute("ViewInvestigationHref");
                    %>
					
	                <table role="presentation" width="100%">
	                    <tr>
	                        <td align="center">
	                            <display:table name="updatedNotificationList" class="dtTable" id="updatedNotificationsQueue" 
	                                    style="margin-top:0em;" pagesize="${maxRowCount}"  sort="external">
	                                <display:column title="Remove" style="width:10%;">
	                                    <div align="center" >
	                                        <input type="checkbox" class="isRemoved" value="${updatedNotificationsQueue.removeFlag}" 
	                                            name="isRemoved-${updatedNotificationsQueue.notificationUid}_${updatedNotificationsQueue.versionCtrlNbr}"/>
	                                    </div>
	                                </display:column>
	                                <display:column property="lastNm" defaultorder="ascending" sortable="true" sortName= "getLastNm"  title="Patient Name" style="width:10%;"/>
	                                <display:column property="conditionLink" defaultorder="ascending" sortable="true" sortName= "getCdTxt"  title="Condition" style="width:15%;"/>
	                                <display:column property="jurisdictionCdTxt" defaultorder="ascending" sortable="true"  sortName= "getJurisdictionCdTxt" media="html" title="Jurisdiction" style="width:10%;"/>
	                                <display:column property="caseClassCdTxt" defaultorder="ascending" sortable="true" sortName= "getCaseClassCdTxt" media="html" title="Case Status" style="width:10%;"/>
	                                <display:column property="addUserName" defaultorder="ascending" sortable="true" sortName= "getAddUserName" title="Submitted By" style="width:12%;"/>
	                                <display:column property="addTime" defaultorder="ascending" sortable="true" sortName= "getAddTime" format="{0,date,MM/dd/yyyy}" title="Update Date" style="width:8%;"/>
	                                <display:setProperty name="basic.empty.showtable" value="true"/>
	                            </display:table>
	                        </td>
	                    </tr>
	                </table>
	            </div>	 
	        </form>
	    </div>
    </body>
</html>