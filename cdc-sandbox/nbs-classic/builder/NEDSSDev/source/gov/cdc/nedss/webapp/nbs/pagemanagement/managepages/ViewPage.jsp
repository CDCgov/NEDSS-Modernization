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
        <title>NBS: Manage Page </title>
        <%@ include file="../../jsp/resources.jsp" %>        
    </head>
     <script type="text/javascript">
   
            function editLoad()
	        {
	            document.forms[0].action ="/nbs/ManagePage.do?method=editPageContentsLoad";
	            document.forms[0].submit();
	            return true;
	        }
	        function publish()
	        {
	            document.forms[0].action ="/nbs/ManagePage.do?method=publishPage";
	            document.forms[0].submit();
	            return true;
	        }

        function deleteDraft()
	        {
                var conditionNm = "${fn:escapeXml(codeDesc)}";
                  var confirmMsg="You have indicated that you would like to delete the draft version of " +conditionNm +".   Once the page draft is deleted, it will no longer be available in the System; any changes you have made will be lost. Select OK to continue or Cancel to return to page Details";
	            if (confirm(confirmMsg)) {
		            document.forms[0].action ="/nbs/ManagePage.do?method=deleteDraft";
		            document.forms[0].submit();
	       	     return true;
	       	}else{
	       	 	return false;		
	       	}
	        }
	      function saveAsTemplate()
	        {
	            document.forms[0].action ="/nbs/ManagePage.do?method=saveAsTemplateLoad";
	            document.forms[0].submit();
	            return true;
	        }
	        
    function saveAsTemplatePopUp(){
      
                       
     		var urlToOpen =  "/nbs/ManagePage.do?method=saveAsTemplateLoad";
     		var divElt = getElementByIdOrByName("parentWindowDiv");
     	    	if (divElt == null) {
     	       	 divElt = getElementByIdOrByName("blockparent");
     	    	}
     	    	divElt.style.display = "block";
     	    	var o = new Object();
     	    	o.opener = self;
     	    	
     	    	var modWin = openWindow(urlToOpen, o, GetDialogFeatures(760, 700, false, true), divElt, "");
     	    	
     	    	
     	    //	window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
     	    	//window.open(urlToOpen);
     	}


      </script>       
  
    <body onload="autocompTxtValuesForJSP();startCountdown();">
        <div id="parentWindowDiv"></div>
        <div id="doc3">
           <html:form action="/ManagePage.do">
            <div id="bd" style="text-align:center;">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
            	<div align="right">
                 <a href="ManagePage.do?method=list">Return to Page Library</a> &nbsp;&nbsp;&nbsp;
            </div> 
		        
  
	   	</div><br> 
   
	   </html:form>
	   <!-- Top button bar -->     
	   		<div class="grayButtonBar" style="text-align:right;">
                    <input type="button" name="Edit Page" value="Edit Page" onclick="editLoad();"/>
                    <input type="button"  name="Publish" value="Publish" onclick="publish()" />	
                    <input type="button"  name="Delete Draft" value="Delete Draft" onclick="deleteDraft()" />
                     <input type="button"  name="Save As Template" value="Save As Template" onclick="saveAsTemplatePopUp()" />	            	 	
	     
            </div>
        </div>
    </body>
</html>