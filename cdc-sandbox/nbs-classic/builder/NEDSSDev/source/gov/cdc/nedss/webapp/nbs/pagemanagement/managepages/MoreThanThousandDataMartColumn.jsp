<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>


<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<html lang="en">
    <head>
        <title>NBS: Manage Pages: More Than Thousand Data Mart Columns </title>
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

 function resizeWindow(){
	// window.resizeTo("650", "400");
//	 window.moveTo("600","100");
	 
	// resizeTo(100,400);
 //  window.focus();
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

	    <div id="globalFeedbackMessagesBar" class="screenOnly"> </div>

        <div style="padding:0.5em 1em;text-align:left">

							<table role="presentation">
				                    <tr>
					              		<td colspan="2"> 
					                         More than 1,000 data elements (<%=request.getAttribute("datamartNumber")!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("datamartNumber"))):""%>) have been indicated for inclusion in the data mart for this page. Please update the page to decrease the number of elements included. This can be achieved by:
											 <ul>
											 <li>Reducing the number of repeating blocks to include in the data mart by updating the Data Mart Repeat Number for a repeating block(s).</li>
											 <li>Indicating participants (providers and organizations) that are not necessary for analysis as 'do not include'.</li>
											 <li>Indicating any other 'not necessary for analysis' data elements as 'do not include'.</li></ul>
			
											 Click on Page Metadata button from previous page to see the RDB metadata currently defined for this page.
												
											 </td>
					                      							  
				                   </tr>
								   <tr><td></td></tr>
										   
						                  
						            </table>    

	        
				</br>
<table role="presentation">
<tr>
					   <td>Select OK to return to the Page to make the necessary changes to reduce the number of elements included in the data mart for this page.</td>
					   </tr>
</table>				
						
					
        </div>
	    
	    <div style="text-align:right; margin-top:5px;">
	        <input style="width: 60px" type="button" align="right" title="Click Ok to close this window" name="Cancel" value="OK" onclick="closePopup();" />
	    </div>
    </body>
</html>