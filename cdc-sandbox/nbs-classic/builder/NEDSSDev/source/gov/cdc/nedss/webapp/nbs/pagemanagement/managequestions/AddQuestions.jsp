<%@ include file="../../jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<html lang="en">
    <head>
        <title>NBS: Manage Questions</title>
        <%@ include file="../../jsp/resources.jsp" %>        
        <script type="text/javascript" src="/nbs/dwr/interface/JManageQuestionsForm.js"></script>
        <script type="text/javascript" src="pagemanagementSpecific.js"></script>
        <script type="text/javascript" src="Globals.js"></script>
        <script language="JavaScript">
            function submitForm()
            {  
            	
                if ("${manageQuestionsForm.actionMode}" == "Edit")
                {
                    if (manageQuestionReqFlds() == true) {
                        return false;
                    }
                    else { 
                        document.forms[0].action ="/nbs/LoadManageQuestions.do?method=editQuestionSubmit&context=question";
                        document.forms[0].submit();
                    }
                }
                else if("${manageQuestionsForm.actionMode}" == "Create")
                {       
                    if (manageQuestionReqFlds() == true) {
                        return false;
                    }
                    else {
                        document.forms[0].action ="/nbs/LoadManageQuestions.do?method=addQuestionSubmit&context=question";	            	
                        document.forms[0].submit();       
                    }
                }
                blockUIDuringFormSubmissionNoGraphic(); 
                
            } 
       
	        function cancelForm()
            {
                var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
	            if (confirm(confirmMsg))
	            {
	                document.forms[0].action ="/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&actionMode=Manage&context=ReturnToManage";
	                document.forms[0].submit();
	                return true;
	            }
	            else {
	                return false;
	            }
            }
	        
	    	function checkdeActivate(act){
	            var status =  getElementByIdOrByName("status");
	            if(status.value == 'INACTIVE'){
	                var checkErrorConditions = getElementByIdOrByName("deleteError");
	                if(checkErrorConditions!=null) {
	                    if(checkErrorConditions.value!="") {
	                        alert(checkErrorConditions.value);
	                    }
	                }
	                else {
	                    var confirmMsg="You have indicate that you would like to deactivate this question. Once inactive, this question will no longer be available in the Page Builder.  Select OK to continue or Cancel to return to page Details";
	                    if (confirm(confirmMsg)) {
	                        document.forms[0].action ="/nbs/LoadManageQuestions.do?method=editQuestionSubmit";	            					
	                        document.forms[0].submit();
	                    } 
	                }
	            }
	        }
	    </script>       
	    <style type="text/css">
	        table.FORM {width:100%; margin-top:15em;}
	    </style>
    </head>

    <body onload="handleEditCreateLoad();autocompTxtValuesForJSP();updateRDBTableNames();">
        <div id="doc3">
            <div id="bd">
                <!-- Navigation bar -->    
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                     
                <!-- Required Field Indicator -->
                <div style="text-align:right; width:100%;"> 
                    <span class="boldTenRed"> * </span>
                    <span class="boldTenBlack"> Indicates a Required Field </span>  
                </div>

                <!-- Top button bar -->     
                <div class="grayButtonBar">
                    <input type="button" name="Submit" value="Submit" onclick="submitForm();"/>
                    <input type="button"  name="Cancel" value="Cancel" onclick="cancelForm()" />                    
	            </div>

                <!-- Feedback bar -->	        	               
                <%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>

                <!-- Sections container -->
                <div class="view"  style="text-align:center;">
                   <html:form action="/LoadManageQuestions.do">
                        <html:hidden  name="manageQuestionsForm" property="actionMode"  styleId="actionId"/>
                        <html:hidden  name="manageQuestionsForm" property="selection.standardNndIndCd"/>
                        <html:hidden  name="manageQuestionsForm" property="selection.standardQuestionIndCd"/>
                        <html:hidden  name="manageQuestionsForm" property="selection.entryMethod"/>
                        <html:hidden  styleId = "dataLocation" name="manageQuestionsForm" property="selection.dataLocation"/>
                        
                        <input type="hidden" name="deleteError" 
                                value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
                          
	                    <!-- SECTION : Question -->
                        <nedss:container id="sect_question" name="Add Question" classType="sect" displayImg="false" includeBackToTopLink="no">
	                        <!-- SUBSECTION: Basic Info -->
	                        <nedss:container id="subsect_basicInfo" name="Basic Information" classType="subSect" >
	                            <!-- for create mode -->
	                            <logic:equal name="manageQuestionsForm" property="actionMode" value="Create">
	                                <jsp:include page="BasicInfo.jsp"/>
	                            </logic:equal>
	                            
	                            <!-- for edit mode -->
	                            <logic:equal name="manageQuestionsForm" property="actionMode" value="Edit">
	                                <jsp:include page="BasicInforEdit.jsp"/>
	                            </logic:equal>
	                        </nedss:container>
	                        
	                        <!-- SUBSECTION: User Interface -->
	                        <nedss:container id="subsect_userInterface" name="User Interface" classType="subSect">
	                            <jsp:include page="UserInterface.jsp"/>
	                        </nedss:container>
	                        
	                        <!-- SUBSECTION: Data Mart -->
	                        <nedss:container id="subsect_dataMart" name="Data Mart" classType="subSect">
	                            <jsp:include page="DataMart.jsp"/>
	                        </nedss:container>
	                        
	                        <!-- SUBSECTION: Messaging -->
	                        <nedss:container id="subsect_messaging" name="Messaging" classType="subSect">
	                            <jsp:include page="Messaging.jsp"/>
	                        </nedss:container>
	                        
	                        <!-- SUBSECTION: Administrative Comments -->
                            <nedss:container id="subsect_administrative" name="Administrative" classType="subSect">
                                <tr>
                                    <td class="fieldName"> 
                                        <span id="adminCommentL" title="Administrative Comments">Administrative Comments:</span>
                                    </td>
                                    <td>                
                                        <html:textarea title="Administrative Comments" style="width:500px; height:100px;" property="selection.adminComment" styleId="adminComment"> </html:textarea>      
                                    </td>
                                </tr>
                            </nedss:container>
	                    </nedss:container>
                    </html:form>
                </div>    
                
                <!-- Bottom button bar -->
                <div class="grayButtonBar">
                    <input type="button" name="Submit" value="Submit" onclick="submitForm();"/>
                    <input type="button"  name="Cancel" value="Cancel" onclick="cancelForm()" />                    
                </div>
            </div>
        </div>
    </body>
</html>