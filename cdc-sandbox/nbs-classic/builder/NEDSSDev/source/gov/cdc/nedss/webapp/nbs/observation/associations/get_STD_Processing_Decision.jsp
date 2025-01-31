<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.*" %>

<html lang="en">
  <head>
    <title>
      Associate to Investigations(s) - Processing Decision
    </title>
    <%@ include file="/jsp/resources.jsp" %>
  </head>
  <script language="JavaScript">
  
    function setProcessingDecision() {
    	var opener = getDialogArgument();
      var reason = getElementByIdOrByName("theProcessingDecision").value;
      getElementByIdOrByNameNode("processingDecisionReason", opener.document).value=reason;
 
      if(checkRequired()){
        return false;
      }

      var invest = getElementByIdOrByNameNode("blockparent", opener.document);
      invest.style.display = "none";
      window.returnValue ="true";
      window.close();
    }
    function checkMaxLength(sTxtBox) {
      maxlimit = 1000;
      if (sTxtBox.value.length > maxlimit){
        sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
      }
    }
    function trim(str)
    {
      while (str.charAt(0) == " ") {
        // remove leading spaces
        str = str.substring(1);
      }

      while (str.charAt(str.length - 1) == " "){
        // remove trailing spaces
        str = str.substring(0,str.length - 1);
      }
      return str;
    }
    function closeProcDecision() {
      self.close();
      var opener = getDialogArgument();
      getElementByIdOrByNameNode("processingDecisionReason", opener.document).value="";
      var invest = getElementByIdOrByNameNode("blockparent", opener.document);
      invest.style.display = "none";
    }
    
    function checkRequired()
    {
      getElementByIdOrByName("errorBlock").style.display="none";

      var errors = new Array();
      var index = 0;
      var isError = false;
      var reason = trim(getElementByIdOrByName("theProcessingDecision").value);

      if( reason==null || (reason != null && reason.length == 0)) {
        errors[index++] =  "Processing Decision is required";
        getElementByIdOrByName("disReason").style.color="#CC0000";
        isError = true;
      }
      else {
        getElementByIdOrByName("disReason").style.color="black";
      }
      if(isError) {
        displayErrors("errorBlock", errors);
        return isError;
      }
    }
  </script>
  
  
  
  <body>
    <div 
        id="headNotComments"
        style="width:100%; margin:0 auto; margin-top:3px; margin-bottom:3px; text-align:left; font-size:1.1em; font-weight:bold;color:white;background:#003470; padding:3px 0px">
      Associate to Investigation(s): Processing Decision
    </div>
    <div id="topProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
      <input type="button" align="right"  value="Submit" onclick="setProcessingDecision()"/>
      <input type="button" align="right"  value= "Cancel" onclick="closeProcDecision();"/>
    </div>
    <html:form>
      <nedss:container 
          id="sect_processingDecision"
          name="Reason for No Further Action"
          classType="sect"
          displayImg="false"
          includeBackToTopLink="no"
          displayLink="no">
        <nedss:container 
            id="subsect_processingDecision"
            name=""
            classType="subSect"
            displayImg="false"
            includeBackToTopLink="no">
          <tr>
            <td colspan="2">
              <div class="infoBox errors" style="display:none;" id="errorBlock">
              </div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
	             <div class="infoBox messages" align="left">
	    	     <p style="text-align:left">Please select a reason for deciding to associate this report to a closed investigation to indicate why no further action is required. Select OK to continue, or select Cancel to cancel this action.</p>
	    	     <logic:equal name="associateToInvestigationsForm" property="processingDecisionLogic" value="STD_SYPHILIS_NONSYPHILIS_PROCESSING_DECISION">
	    	     <p style="text-align:left"><b>BFP</b> is only applicable if the report is for Syphilis; <b>Not Program Priority</b> is only applicable if the report is for a non-syphilis condition.
	    	     </logic:equal>
	    	</div>
	 </td>
            </tr>
            <tr>
              <td class="InputFieldLabel" id="disReason">
                <span style="color:#CC0000">
                  *
                </span>
                Processing Decision:
              </td>
              <td class="InputField" id="transfer">
                <html:select title="Processing Decision" property="processingDecision"  styleId ="theProcessingDecision">
         	<logic:equal name="associateToInvestigationsForm" property="processingDecisionLogic" value="STD_LAB_SYPHILIS_PROC_DECISION">       
                	<html:optionsCollection property="codedValue(STD_LAB_SYPHILIS_PROC_DECISION)" value="key" label="value"/>
         	</logic:equal>
         	<logic:equal name="associateToInvestigationsForm" property="processingDecisionLogic" value="STD_MORB_SYPHILIS_PROC_DECISION">       
		        <html:optionsCollection property="codedValue(STD_MORB_SYPHILIS_PROC_DECISION)" value="key" label="value"/>
         	</logic:equal>
         	<logic:equal name="associateToInvestigationsForm" property="processingDecisionLogic" value="STD_NONSYPHILIS_PROC_DECISION">       
                	<html:optionsCollection property="codedValue(STD_NONSYPHILIS_PROC_DECISION)" value="key" label="value"/>
         	</logic:equal>       
         	<logic:equal name="associateToInvestigationsForm" property="processingDecisionLogic" value="STD_UNKCOND_PROC_DECISION">       
	                 <html:optionsCollection property="codedValue(STD_UNKCOND_PROC_DECISION)" value="key" label="value"/>
	 	</logic:equal>       
      		<logic:equal name="associateToInvestigationsForm" property="processingDecisionLogic" value="NA">       
	                 <html:optionsCollection property="codedValue(NBS_LAB_REW_PROC_DEC)" value="key" label="value"/>
	 	</logic:equal>       

                </html:select>
              </td>
            </tr>
          </nedss:container>
        </nedss:container>
      </div>
      <div style="padding:1.0em 0em;"/>
      <div id="botProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
        <input type="button" align="right"  value="Submit" onclick="setProcessingDecision()"/>
        <input type="button" align="right"  value= "Cancel" onclick="closeProcDecision();"/>
      </div>
    </html:form>
  </body>
</html>
