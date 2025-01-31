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
        <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
        <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
        <script src="/nbs/dwr/interface/JPageBuilder.js" type="text/javascript"></script>
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

 
   	 function submitTemplate()
	    {
      			var errors = new Array();
      			var isError = false;
				var templateNm = getElementByIdOrByName("templateNm");
				var descTxt= getElementByIdOrByName("descTxt1");
				var templateType = "TEMPLATE";
                errors =  checkReqForSaveAsTemplate();
                if(errors.length == 0){
					// dwr call to find the error list 
				JPageBuilder.getUniqueTemplateNm(templateNm.value,templateType, function(data) {
       			    if (data != null && data.length == 0) {
           			    
       			    	var opener = getDialogArgument();
						opener.reloadLibrary(templateNm, descTxt);
						window.returnValue ="true";
						closePopup();
       			    }
       			    else if (data == null || data.length > 0)  {
       			    	var err = null;
       			    	if(data != null){
							err = data[0];
							errors.push(err);
       			    	}
       			    	
						displayErrors("errorMessages", errors)
			        }
  				 }) 
                }

		 }
			    
			
		 function checkReqForSaveAsTemplate(){
		 	var isError = false;
	 		var errors = new Array();
		   // check for the required fields 
            var templNm  =  getElementByIdOrByName("templateNm");
            var templDes = getElementByIdOrByName("descTxt1");
			                
            if(templNm  != null && templNm.value.length == 0){
                      //alert(" Template name is null ");
				errors.push("Template Name is a required field");
	 	        getElementByIdOrByName("templateNmL").style.color="#CC0000";
		        isError = true;
            } else {
              getElementByIdOrByName("templateNmL").style.color="black";
			}
		    if(templDes != null && templDes.value.length == 0){
			  	errors.push("Template Description is a required field");
	 	      	getElementByIdOrByName("descTxtL").style.color="#CC0000";
		      	isError = true;
	    	} else {
	           getElementByIdOrByName("descTxtL").style.color="black";
		    }
			               
           if(isError){			           
 			displayErrors("errorMessages", errors);
           }
           return errors ;
		 
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
<!--
    Page Summary:
    -------------
    This file includes the contents of a single tab (the Tuberculosis tab). This tab 
    is part of the tab container who structure is defined in a separate JSP.
-->



<% 
    int subSectionIndex = 0;
    String tabId = "userInterface";
    
    int sectionIndex = 0;
%>

 <body  class="popup" onload="autocompTxtValuesForJSP();startCountdown();"  onunload="closePopup()">        
 
 <html:form action="/ManagePage.do">      
  
                <div class="popupTitle">
	                   Manage Pages: Save As Template
	        	</div>          
                  
	      		<div class="popupButtonBar" style="text-align:right;">
	        	   	 <input type="button" name="Submit" value="Submit" onclick="submitTemplate();"/>
	           	 	<input type="button"  name="Cancel" value="Cancel" onclick="closePopup();" />	           	 	
	           	 </div>       
	        	   <!-- Page Errors -->
		     <div class="infoBox errors" id="errorMessages" style="display:none;"">  </div>
		         		<div style="text-align:right; width:100%;"> 
                           <i>
			                <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
			            	</i>
             			</div>  
    <!-- SECTION : Add New Page --> 

    <nedss:container id="section1" name="Save As Template" classType="sect" displayImg ="false" displayLink="false"  includeBackToTopLink="no">
        <!-- SUB_SECTION :Default User Interface Metadat -->
        <nedss:container id="subSection1" name="" classType="subSect" displayImg ="false" displayLink="false">
          <tr>
             <td class="fieldName"> 
              	 <font class="boldTenRed" > * </font>
                 <span title="Template Name" id="templateNmL">
                      Template Name:</span> 
               </td>
          <td>
           <html:text title="Template Name" property="selection.templateNm" styleId = "templateNm" size='50' maxlength='50' onkeyup="isSpecialCharEnteredForName(this,null,event)"/>
           </td>       
       </tr>  
       <!-- Template Description-->
       <tr>
          <td class="fieldName"> 
          		 <font class="boldTenRed" > * </font>
                 <span title="Template Description"id="descTxtL">
                    Template Description:</span>
               </td>
          <td>
          <html:textarea title="Template Description" style="WIDTH: 500px; HEIGHT: 100px;" property="selection.descTxt" styleId="descTxt1" onkeydown="checkMaxLength(this)" 
                        onkeyup="checkMaxLength(this)"/>      
         </td>
       </tr>    
     
   </nedss:container>
</nedss:container>    
    <div class="popupButtonBar" style="text-align:right;">
            <input type="button" align="right"  name="Submit" value="Submit" onclick="submitTemplate();">
            <input type="button" align="right"  name="Cancel" value="Cancel" onclick="closePopup();" />
    </div>
 </div> <!-- id = "bd" -->
</html:form>
  </body>
</html>