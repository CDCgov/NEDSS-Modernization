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
      NBS: Select Processing Decision
    </title>
    <%@ include file="/jsp/resources.jsp" %>
  </head>
  <SCRIPT Language="JavaScript" Type="text/javascript" SRC="ProcessingDecision.js"></SCRIPT>
  <body onunload="closeDialog()" onload="windowResize()">
  
   <script language="JavaScript">
	blockEnterKey();
   </script>
   
  <div id ="resizeDiv">
    <div 
        id="headNotComments"
        style="width:100%; margin:0 auto; margin-top:3px; margin-bottom:3px; text-align:left; font-size:1.1em; font-weight:bold;color:white;background:#003470; padding:3px 0px">
      Create Investigation - Processing Decision
    </div>
    <div id="topProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
      <logic:equal name="event" value="LabReport">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
      <logic:equal name="event" value="MorbReport">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="CaseReport">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSubmitandCreateInv('CaseCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="InvCreate">
        <logic:equal name="typeDocument" value="CaseReport">
       	  	<input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionCreateInv('New', 'CaseReport',<%=request.getAttribute("cond") %>)"/>
       	</logic:equal>
       	<logic:notEqual name="typeDocument" value="CaseReport">
        	<input type="button" align="right"  value="Submit" onclick="markProcessingDecisionCreateInv('New')"/>
        </logic:notEqual>
      </logic:equal>
      <logic:equal name="event" value="selectCondition">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
      <input type="button" align="right"  value= "Cancel" onclick="closeDialog()"/>
    </div>
    <div style="text-align:right; width:100%;"> 
        <span class="boldTenRed"> * </span>
        <span class="boldTenBlack"> <I>Indicates a Required Field </I></span>  
    </div>
    <div class="infoBox messages" align="left">
	    <p style="text-align:left">Please select a reason for deciding to create investigation from this report. Select OK to continue, or select Cancel to cancel this action.</p>
	    <logic:equal name="observationForm" property="processingDecisionLogic" value="STD_CREATE_INV_LAB_UNKCOND_PROC_DECISION">
	    	     <p style="text-align:left"><b>BFP</b> is only applicable if the report is for Syphilis; <b>Not Program Priority</b> is only applicable if the report is for a non-syphilis condition.
	     </logic:equal>
	</div>
    <html:form>
      <nedss:container 
          id="sect_processingDecision"
          name="Create Investigation"
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
                <html:select title="Processing Decision" property="proxy.observationVO_s[0].theObservationDT.processingDecisionCd"  styleId ="reviewReason">
                  <html:optionsCollection property="processingDecisionList" value="key" label="value"/>
                </html:select>
              </td>
            </tr>
          </nedss:container>
        </nedss:container>
      </div>
      <div style="padding:1.0em 0em;"/>
      <div id="botProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
      <logic:equal name="event" value="LabReport">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
      <logic:equal name="event" value="MorbReport">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="CaseReport">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSubmitandCreateInv('CaseCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="InvCreate">
        <logic:equal name="typeDocument" value="CaseReport">
       	  	<input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionCreateInv('New', 'CaseReport',<%=request.getAttribute("cond") %>)"/>
       	</logic:equal>
       	<logic:notEqual name="typeDocument" value="CaseReport">
        	<input type="button" align="right"  value="Submit" onclick="markProcessingDecisionCreateInv('New')"/>
        </logic:notEqual>
      </logic:equal>
      <logic:equal name="event" value="selectCondition">
        <input type="button" align="right"  value="Submit" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
        <input type="button" align="right"  value= "Cancel" onclick="closeDialog()"/>
      </div>
    </html:form>
    </div>
  </body>
</html>
