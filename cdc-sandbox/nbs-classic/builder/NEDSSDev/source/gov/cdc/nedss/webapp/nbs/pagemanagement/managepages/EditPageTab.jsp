<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%
  String strPageTitle = (String)request.getAttribute("pageTitle");
  if(strPageTitle!=null && strPageTitle.contains("Add")){
    strPageTitle ="Add Tab"; 
  }else{
    strPageTitle ="Edit Tab"; 
  } 
%>

<html lang="en">
    <head>
    	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <base target="_self">
        <title>
            <logic:present name="windowTitle">
                <bean:write name="windowTitle" />
            </logic:present>
        </title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        
        <script type="text/javascript">
        
        	blockEnterKey();
        
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                 // get reference to opener/parent           
                    var opener = getDialogArgument();
                    
                    var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                    if (grayOverlay == null) {
                        grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                    }
                    grayOverlay.style.display = "none";
                    
                    if (e.clientY < 0 || closePopup == true) {
                         
                        self.close();
                        return true;
                    }
                }
            }
            
            function submitForm()
            {
                var allEnabledSearchIpElts = $j("#editPageTabBlock").find(':input:enabled');
                var errorMsgArray = new Array();
                
                for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
                    if ($j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'button'
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'select-one'  
                        && jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) == "") 
                    {
                        var fieldLabel = jQuery.trim($j($j($j($j(allEnabledSearchIpElts[i]).parents("tr")).get(0)).find("td.fieldName").get(0)).attr("title"));
                        var msg = fieldLabel + " is a required field." + "\n";
                        errorMsgArray.push(msg);
                    }
                }
                
                // verify the uniqueness of this name in case of 'add' mode (i.e., pageElementId = 0)
                var opener = getDialogArgument();
                if (opener.isUniqueElementName('tab', $j("#tabNameTd").val(), $j("#pageElementUid").val()) == false) {
                    errorMsgArray.push("Tab name you entered already exists in the page. Please choose a different one.");
                }
	                
                if(errorMsgArray != null && errorMsgArray.length > 0){
                    displayGlobalErrorMessage(errorMsgArray);
                }
                else {
                	unblock=false;
                    document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=section&waQuestionUId=" + $j("#pageElementUid").val();
                    document.forms[0].submit();
                }
           }
           
            function handlePageOnload()
            {
                // focus on the first valid element
                $j("div#editPageTabBlock").find(':input[type!=button]:visible:enabled:first').focus();
            }
        </script>
    </head>
    <body class="popup" onload="handlePageOnload();startCountdown();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;" style="text-align:center;">
        <html:form action="/ManagePageElement.do?action=editSubmit">
            <html:hidden styleId="pageElementUid" property="pageEltVo.pageElementUid" />
            <logic:present name="pageTitle">
                <div class="popupTitle">
                    <bean:write name="pageTitle" />
                </div>
            </logic:present>
	        
	        <!-- Top button bar -->
	        <div class="popupButtonBar">
	            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
	            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
	        </div>
	        
            <!-- Required Field Indicator -->
            <div style="text-align:right; width:100%; margin-top:0.5em;"> 
                <span style="color:#CC0000;"> * </span>
                <span style="color:black; font-style:italic;"> Indicates a Required Field </span>  
            </div>
	        
	        <!-- Error Messages using Action Messages-->
	        <div id="globalFeedbackMessagesBar" class="screenOnly"> </div>
	        
	        <!-- Collection of form fields to edit a question -->
	        <div id="editPageTabBlock">
                <nedss:container id="sect_section" name="<%= strPageTitle %>" classType="sect" displayLink="false" displayImg="false" includeBackToTopLink="no">
                    <nedss:container id="subsect_basicInfo" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">
                     <tr>
                            <td title="Tab Name" class="fieldName">
                                <span class="boldRed">*</span> Tab Name: 
                            </td>
                            <td>
                                <html:text title="Tab Name" property="pageEltVo.waUiMetadataDT.questionLabel" maxlength="300" size="40" styleId="tabNameTd" onkeyup="isSpecialCharEnteredForTabName(this)"/>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <td title="Secure Tab" class="fieldName"> Secure Tab <font class="boldTenRed" >*</font>: </td>
                            <td>
                                <html:radio property="pageEltVo.waUiMetadataDT.isSecured" value="T" /> Yes &nbsp;&nbsp;
                                <html:radio property="pageEltVo.waUiMetadataDT.isSecured" value="F"/> No
                            </td>
                        </tr>
                        -->
                        <tr>
                            <td title="Visible" class="fieldName"> <span class="boldRed">*</span> Visible: </td>
                            <td>
                                <html:radio title="Visible Yes" property="pageEltVo.waUiMetadataDT.displayInd" value="T" /> Yes &nbsp;&nbsp;
                                <html:radio title="Visible No" property="pageEltVo.waUiMetadataDT.displayInd" value="F"/> No
                            </td>
                        </tr>
                    </nedss:container>
                </nedss:container>              
	        </div>
	        
	        <!-- Bottom button bar -->
	        <div class="popupButtonBar">
	            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
	            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
	        </div>
        </html:form>
    </body>
</html>
