<%@ include file="../../../jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants, 
                 gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT,   
                 gov.cdc.nedss.webapp.nbs.form.pagemanagement.managequestion.ManageQuestionsForm" %>

<html lang="en">
    <head>
        <title>NBS: Manage Questions</title>
        <%@ include file="../../../jsp/resources.jsp" %>        
        <script language="JavaScript">
            function editForm()
            {
            	blockUIDuringFormSubmissionNoGraphic();
                document.forms[0].action ="/nbs/LoadManageQuestions.do?method=editQuestionLoad";
                document.forms[0].submit();
            }
                   
            function cancelForm()
            {
                document.forms[0].action ="/nbs/LoadManageQuestions.do?method=addQuestionLoad";
                document.forms[0].submit();
            return true;
            }
       
	        function ChangeQuestionStatus(action)
	        {    						 
	            if (action == 'activate') {
                    document.forms[0].action ="/nbs/LoadManageQuestions.do?method=activeInactiveQuestionSubmit";                                    
                    document.forms[0].submit();
                    return true;
	            }
	            else if (action == 'deactivate') {
	                    var confirmMsg="You have indicate that you would like to deactivate this question. Once inactive, this question will no longer be available in the Page Builder.  Select OK to continue or Cancel to return to page Details";
	                    if (confirm(confirmMsg)) {
	                        document.forms[0].action ="/nbs/LoadManageQuestions.do?method=activeInactiveQuestionSubmit";                                    
	                        document.forms[0].submit();
	                        return true;
	                    } else {
	                        return false;
	                    }
	            }
			}	 
	    </script>       
        <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
        </style>
    </head>

    <body onload="startCountdown();">        
        <div id="doc3">
            <html:form action="/LoadManageQuestions.do">
                <div id="bd">
                    <!-- Navigation bar -->
                    <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>

                    <!-- Return link -->    
                    <div  style="text-align:right; margin-bottom:8px;">
                        <a id="manageLink" href="/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&context=ReturnToManage&existing=true">
                            Return to Question Library
                        </a>
                    </div>
                    <!-- Top button bar -->     
                    <div class="grayButtonBar">
                        <logic:present name ="manageQuestionsForm" property="selection.recordStatusCd">
                            <bean:define id="recordStatusCode" value="<%= ((WaQuestionDT)(((ManageQuestionsForm) request.getSession().getAttribute(\"manageQuestionsForm\")).getSelection())).getRecordStatusCd().toLowerCase() %>" />
                            <bean:define id="isCoInfQuestion" value="<%= ((WaQuestionDT)(((ManageQuestionsForm) request.getSession().getAttribute(\"manageQuestionsForm\")).getSelection())).getCoInfQuestion().toLowerCase() %>" />
                            
                            <logic:equal name="recordStatusCode" value="active">
                            	<logic:notEqual  name="isCoInfQuestion" value="t">
                                <input type="button" name="Edit" value="Edit" onclick="editForm();"/>
                               </logic:notEqual>
                            </logic:equal>
                        </logic:present>
                        
                        
                        <!-- Activate/Inactivate button -->
                        <logic:present name ="manageQuestionsForm" property="selection.recordStatusCd">
                            <bean:define id="recordStatusCode" value="<%= ((WaQuestionDT)(((ManageQuestionsForm) request.getSession().getAttribute(\"manageQuestionsForm\")).getSelection())).getRecordStatusCd().toLowerCase() %>" />
                            <logic:equal name="recordStatusCode" value="active">
                                <input type="button" name="Make Inactive" value="Make Inactive" onclick="ChangeQuestionStatus('deactivate');" />
                            </logic:equal>
                            <logic:equal name="recordStatusCode" value="inactive">
                                <input type="button" name="Make Active" value="Make Active" onclick="ChangeQuestionStatus('activate');" />
                            </logic:equal>
                        </logic:present>
                    </div>
                       
                    <!-- Page errors -->
                    <%@ include file="../../../../jsp/feedbackMessagesBar.jsp" %>
                    
                    <!-- Sections container -->
                    <div class="view"  style="text-align:center;">
                        <!-- SECTION : Question --> 
                        <nedss:container id="sect_question" name="View Question" classType="sect" displayImg="false" 
                                includeBackToTopLink="no">
                            <!-- SUBSECTION: Basic Info -->
                            <nedss:container id="subsect_basicInfo" name="Basic Information" classType="subSect" >
                                <jsp:include page="ViewBasicInfo.jsp"/>
                            </nedss:container>
                            
                            <!-- SUBSECTION: User Interface -->
                            <nedss:container id="subsect_userInterface" name="User Interface" classType="subSect" >
                                <jsp:include page="ViewUserInterface.jsp"/>
                            </nedss:container>
                            
                            <!-- SUBSECTION: Data Mart -->
                            <nedss:container id="subsect_dataMart" name="Data Mart" classType="subSect" >
                                <jsp:include page="ViewDataMart.jsp"/>
                            </nedss:container>
                            
                            <!-- SUBSECTION: Messaging -->
                            <nedss:container id="subsect_messaging" name="Messaging" classType="subSect" >
                                <jsp:include page="ViewMessaging.jsp"/>
                            </nedss:container>
                            
                            <!-- SUBSECTION: Administrative Comments -->
                            <nedss:container id="subsect_administrative" name="Administrative" classType="subSect">
                                <tr>
                                    <td class="fieldName"> 
                                        <span id="adminCommentL" title="Administrative Comments">Administrative Comments:</span>
                                    </td>
                                    <td>                
                                        <nedss:view name ="manageQuestionsForm" property="selection.adminComment" />    
                                    </td>
                                </tr>
                            </nedss:container>
                        </nedss:container>
                    </div>
                    
                    <!-- Bottom button bar -->
                    <div class="grayButtonBar">
                        <logic:present name ="manageQuestionsForm" property="selection.recordStatusCd">
                            <bean:define id="recordStatusCode" value="<%= ((WaQuestionDT)(((ManageQuestionsForm) request.getSession().getAttribute(\"manageQuestionsForm\")).getSelection())).getRecordStatusCd().toLowerCase() %>" />
                            <bean:define id="isCoInfQuestion" value="<%= ((WaQuestionDT)(((ManageQuestionsForm) request.getSession().getAttribute(\"manageQuestionsForm\")).getSelection())).getCoInfQuestion().toLowerCase() %>" />
                            
                            <logic:equal name="recordStatusCode" value="active">
                            	<logic:notEqual  name="isCoInfQuestion" value="t">
                                <input type="button" name="Edit" value="Edit" onclick="editForm();"/>
                               </logic:notEqual>
                            </logic:equal>
                        </logic:present>
                        
                        
                        
                        <!-- Activate/Inactivate button -->
                        <logic:present name ="manageQuestionsForm" property="selection.recordStatusCd">
                            <bean:define id="recordStatusCode" value="<%= ((WaQuestionDT)(((ManageQuestionsForm) request.getSession().getAttribute(\"manageQuestionsForm\")).getSelection())).getRecordStatusCd().toLowerCase() %>" />
                            <logic:equal name="recordStatusCode" value="active">
                                <input type="button" name="Make Inactive" value="Make Inactive" onclick="ChangeQuestionStatus('deactivate');" />
                            </logic:equal>
                            <logic:equal name="recordStatusCode" value="inactive">
                                <input type="button" name="Make Active" value="Make Active" onclick="ChangeQuestionStatus('activate');" />
                            </logic:equal>
                        </logic:present>
                    </div>
                </div> <!-- id = "bd" -->
            </html:form>
        </div> <!-- Container Div -->
    </body>
</html>