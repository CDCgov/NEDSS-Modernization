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
        <title>NBS: Manage Pages: Publish Page Confirmation </title>
        <%@ include file="../../jsp/resources.jsp" %>        
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
    <base target="_self">	
		<script type="text/javascript">
		    var isFormSubmission = false;
			window.onload = function() {
				$j("body").find(':input:visible:enabled:first').focus();
			}
		
			if (typeof window.event != 'undefined')
		  		document.onkeydown = function()
		    {
				var t=event.srcElement.type;
				if(t == '' || t == 'undefined' || t == 'button') {
					return;
				}
				var kc=event.keyCode;
				if(t == 'text' && kc == 13) {
				}	
				
				return preventF12(event);
		    }

 
	function submitPublishPopUp()
	{
				
		var errorMsgArray = new Array();
		var versionNotes    =getElementByIdOrByName("versionNote");
        if(versionNotes.value==0){
             var msg = "Version Notes is a required field." + "\n";
             errorMsgArray.push(msg);
         }
                    				    
		if(errorMsgArray != null && errorMsgArray.length > 0){
				displayGlobalErrorMessage(errorMsgArray);
		}
		else {
			var opener = getDialogArgument();    
			opener.getValueFromPopUpWindow(versionNotes);
			window.returnValue ="true";
			closePopup();
			}    				
	  }

	  function cancelForm()
	  {
	         document.forms[0].action ="/nbs/ManagePage.do?method=viewPageLoad";
	         document.forms[0].submit();
	         return true;
	   }
	        
	   function closePopup()
		    {
			    if (isFormSubmission == false) {
	                self.close();
	                var opener = getDialogArgument();
	                var pageId = '${fn:escapeXml(pageId)}'; 
	                var invest = null; 
	                if(pageId == 'pageview') { 
	                	invest = getElementByIdOrByNameNode("pageview", opener.document);}
	                else        
	                	invest = getElementByIdOrByNameNode("parentWindowDiv", opener.document)
	                if (invest == null) {
               			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
         			 }
	                invest.style.display = "none";  
	                opener.unblockUIDuringFormSubmissionNoGraphic();
	                return true;
			    } 
		    }

       </script>       
        <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
        </style>
    </head>

<% 
    int subSectionIndex = 0;
    String tabId = "userInterface";
    
    int sectionIndex = 0;
%>

    <body onload="autocompTxtValuesForJSP();startCountdown();"  onunload="closePopup()" style="overflow-x:hidden; margin-right:20px">
        <!-- Error Messages using Action Messages-->
        <div style="width:100%; margin:0 auto; margin-top:3px; margin-bottom:3px; text-align:left; font-size:1.1em; font-weight:bold;color:white;background:#003470; padding:3px 0px">Manage Pages: Publish Page</div>
                <div class="grayButtonBar" style="text-align:right; margin-top:5px;">
	        <input type="button" align="right"  name="Submit" value="Submit" onclick="submitPublishPopUp();">
	        <input type="button" align="right"  name="Cancel" value="Cancel" onclick="closePopup();" />
	    </div>
	     <div style="text-align: right;"><i> <span class="boldRed">*</span> Indicates a Required Field</i></div>
	    <div id="globalFeedbackMessagesBar" class="screenOnly"> </div>
	<% if (request.getAttribute("messageInd") != null ) { %>
      	<div class="infoBox info" style="text-align: left;">
		   Please note that any conditions that require data porting, (highlighted below), will utilize the legacy page
		   for data entry until the data porting process has been completed.
		</div>
	<%} %>	
        <div style="padding:0.5em 0em;">
	        <nedss:container id="sect_publish" name="Publish Page" classType="sect" displayImg="false" includeBackToTopLink="no" displayLink="no">
	            <nedss:container id="subsect_publish_ss" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">
	                <html:form action="/ManagePage.do">
	                    <tr>
		              		<td colspan="2"> 
		                         You have indicated that you would like to publish the <B>${fn:escapeXml(pgPageName)}</B> page. 
		                         Please enter the Version Notes, 
								 
									 <logic:notEqual name="pageBuilderForm"
								property="selection.busObjType" value="VAC">
								<logic:notEqual name="pageBuilderForm"
									property="selection.busObjType" value="LAB">
									<logic:notEqual name="pageBuilderForm"
										property="selection.busObjType" value="SUS">
										<logic:notEqual name="pageBuilderForm"
											property="selection.busObjType" value="ISO">
									and review the related condition(s)
									</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual>
								
							</logic:notEqual> then select Submit to continue, or select Cancel to return to View Page. 
		                      </td>                 	                  
	                   </tr>
	                   <tr>
	                      <td colspan="2"> &nbsp;
	                    </td>       
	                   </tr>
	                    <tr>
	                        <td class="fieldName"> 
	                            <font class="boldTenRed" > * </font><b>Version Notes:</b>  
	                        </td>
	                        <td>
	                            <html:textarea title="Version Notes" style="WIDTH: 500px; HEIGHT: 100px;" property="selection.versionNote" styleId="versionNote" onkeydown="checkMaxLength(this)" 
                        onkeyup="checkMaxLength(this)"/>   
	                        </td>
	                    </tr>
						
						<logic:notEqual name="pageBuilderForm" property="selection.busObjType" value="VAC">
							<logic:notEqual name="pageBuilderForm" property="selection.busObjType" value="LAB">
							<logic:notEqual name="pageBuilderForm" property="selection.busObjType" value="SUS">
							<logic:notEqual name="pageBuilderForm" property="selection.busObjType" value="ISO">
								<nedss:Condition  name="conditionList" conditionLabel="Related Condition(s)" busObjType="${selection.busObjType}"  />
						    </logic:notEqual>
						    </logic:notEqual>							 
							 </logic:notEqual>
						 </logic:notEqual>
						 
	                </html:form>
	                
	            </nedss:container>
	        </nedss:container>
        </div>
	    
	    <div class="grayButtonBar" style="text-align:right; margin-top:5px;">
	        <input type="button" align="right"  name="Submit" value="Submit" onclick="submitPublishPopUp();">
	        <input type="button" align="right"  name="Cancel" value="Cancel" onclick="closePopup();" />
	    </div>
    </body>
</html>