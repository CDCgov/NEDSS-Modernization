<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<html lang="en">
    <head>
        <title>NBS: Manage Questions</title>
        <%@ include file="../../../jsp/resources.jsp" %>   
        <script language="JavaScript">
        
        blockEnterKey();
        
	        function submitForm()
	        {
	        	var atLeastOneSearchCriteria = false;
                var errorTD = getElementByIdOrByName("error1");
                errorTD.setAttribute("className", "none");
                var errorText=""; 
                var isError = false;
                var errorMsgArray = new Array(); 
               
					
                if(getElementByIdOrByName("searchCriteria(questionType)") != null && isEmpty(getElementByIdOrByName("searchCriteria(questionType)").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("searchCriteria(questionIdentifier)")!=null && isEmpty(getElementByIdOrByName("searchCriteria(questionIdentifier)").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("searchCriteria(questionNm)")!=null && isEmpty(getElementByIdOrByName("searchCriteria(questionNm)").value) == false) atLeastOneSearchCriteria = true;
                
                if(getElementByIdOrByName("searchCriteria(subGroup)")!=null && isEmpty(getElementByIdOrByName("searchCriteria(subGroup)").value) == false) atLeastOneSearchCriteria = true;
                if(getElementByIdOrByName("searchCriteria(label)")!=null && isEmpty(getElementByIdOrByName("searchCriteria(label)").value) == false) atLeastOneSearchCriteria = true;
            	
				if( atLeastOneSearchCriteria == false ) 
				{
					errorText = "Please enter at least one item to search.\n";
					isError = true;
					errorMsgArray.push(errorText);
				 }
				if( atLeastOneSearchCriteria == true && isError == false) {
		            document.forms[0].action ="/nbs/SearchManageQuestions.do?method=searchQuestionsSubmit&initLoad=true";
		            document.forms[0].submit();
		            return true;
				}

				// display the errors in the error messages bar.
			  	if(errorMsgArray!=null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
                }
			  	
                return false;
	        }
	

	        function cancelForm()
		    {
		        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
		        if (confirm(confirmMsg))
		        {
		            document.forms[0].action ="${manageQuestionsForm.attributeMap.cancel}";
		        }
		        else {
		            return false;
		        }
		    }
	    </script>   
        <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
        </style>
    </head>
        
    <body onload="autocompTxtValuesForJSP();startCountdown();">
        <html:form action="/SearchManageQuestions.do">  
	        <% 
	            int subSectionIndex = 0;
	            String tabId = "Search Questions";
	            int sectionIndex = 0;
	        %>
        
            <!--  Navigation bar -->
            <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
               
           <table role="presentation" style="width:100%;">
				<TR>
					<TD align='right'>
				 	 <i>
			             <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
			     	 </i>
			      </TD>
		       </TR>
		   </table>
			
            <!-- Button bar -->			
            <div class="grayButtonBar">
                <input type="button" name="Submit" value="Search" onclick="submitForm();"/>
                <input type="submit" id="submitB" value="Cancel" onclick="return cancelForm();" />
            </div>
                <!-- error1 (error message block) -->
        	<div class="none" id="error1" style="width:100%; text-align:center;"> </div>
            <!-- Page Errors -->
        	<%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>     
			<!-- Confirm Message -->
			<tr><td>&nbsp;</td></tr>
	           <% if (request.getAttribute("searchConfirmMsgNoResult") != null) { %>
					 <div class="infoBox messages">${fn:escapeXml(searchConfirmMsgNoResult)}&nbsp;<a href="${fn:escapeXml(clickHereLk)}">Click Here</a> to add a new question.<br/>
				  </div>    
	           <% } %>
            <div class="view"  id="searchQuestionsBlock" style="text-align:center;">    
	            <nedss:container id="sect_search" name="Search Criteria" classType="sect" 
	                   displayImg="false" includeBackToTopLink="no" displayLink="no">
	                <nedss:container id="subsect_basicInfo" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">
	                    <tr>
	                        <td class="fieldName"> <span title="Question Type">Question Type:</span></td>         
                              <td>
	                            <html:select title="Question Type" property="searchCriteria(questionType)" styleId = "questionType">
	                                <html:optionsCollection property="codedValue(NBS_QUESTION_TYPE)" value="key" label="value"/>
	                            </html:select>
	                        </td>              
	                    </tr>
	                    <tr>
	                        <td class="fieldName"><span title="Unique ID">Unique Identifier:</span></td>
	                        <td>
	                            <html:text title="Unique Identifier" property="searchCriteria(questionIdentifier)"  size="40"  styleId="questionIdentifier" />      
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="fieldName"><span title="Unique Name">Unique Name:</span></td>
	                        <td>
	                            <html:text title="Unique Name" property="searchCriteria(questionNm)" size="40"  styleId="questionNm" />      
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="fieldName"> 
	                            <span title="Subgroup">Subgroup:</span>  
	                        </td>
	                        <td>
	                            <html:select title="Subgroup" property="searchCriteria(subGroup)" styleId = "subGroup">
	                                <html:optionsCollection property="codedValue(NBS_QUES_SUBGROUP)" value="key" label="value"/>
	                            </html:select>
	                        </td>
	                    </tr>  
	                    <tr>
	                        <td class="fieldName"> 
	                            <span title="Label">Label:</span> 
	                        </td>
	                        <td>
	                            <html:text title="Label" property="searchCriteria(label)" styleId="label" size="40"/>      
	                        </td>
	                    </tr>    
	                    <tr>
	                        <td class="fieldName"> 
	                            <span title="Include Inactive Questions">Include Inactive Questions:</span> 
	                        </td>
	                        <td>
	                            <html:radio title="Include Inactive Questions Yes" styleId="IncludeY" name="manageQuestionsForm"  property="searchCriteria(recordStatusCd)"  value="Y"/> Yes
	                            &nbsp;<html:radio title="Include Inactive Questions No" styleId= "IncludeN"   name="manageQuestionsForm" property="searchCriteria(recordStatusCd)"   value="N"  /> No
	                        </td>                          
	                    </tr>  
	                </nedss:container>
	            </nedss:container>
            </div>
    
            <!-- Bottom button bar -->        
            <div class="grayButtonBar">
                <input type="button" name="Submit" value="Search" onclick="submitForm();"/>
                <input type="submit" id="submitB" value="Cancel" onclick="return cancelForm();" />
            </div> 
        </html:form>
    </body>
 </html>