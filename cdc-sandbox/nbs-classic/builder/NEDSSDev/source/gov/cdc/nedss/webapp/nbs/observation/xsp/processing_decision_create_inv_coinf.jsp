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
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSelectCondition('Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
      <logic:equal name="event" value="MorbReport">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="CaseReport">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSubmitandCreateInv('CaseCreate','Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSubmitandCreateInv('CaseCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="InvCreate">
      	<logic:equal name="permission" value="true">
	       	<logic:equal name="typeDocument" value="CaseReport">
	       	  <input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionCreateInv('Co-infection','CaseReport',<%=request.getAttribute("cond") %>)"/>
       		</logic:equal>
	       	<logic:notEqual name="typeDocument" value="CaseReport">
	       		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionCreateInv('Co-infection')"/>
	       	</logic:notEqual>
       	</logic:equal>
        <logic:equal name="typeDocument" value="CaseReport">
       	  	<input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionCreateInv('New', 'CaseReport',<%=request.getAttribute("cond") %>)"/>
       	</logic:equal>
       	<logic:notEqual name="typeDocument" value="CaseReport">
     	   	<input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionCreateInv('New')"/>
        </logic:notEqual>
      </logic:equal>
      <logic:equal name="event" value="selectCondition">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSelectCondition('Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSelectCondition('New')"/>
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
        <logic:notEmpty name="coInfectionInvestigationList">
	        <nedss:container 
	          id="sect_coinfectionInv"
	          name="Create as Co-infection Investigation"
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
				<td colspan=2>
	                <span>There are open investigation(s) for other condition(s) in the same Co-infection Group as <%=request.getAttribute("condition") %>.</span>
	             </td>
	            </tr>
	            <tr>
				<td colspan=2>
			<span>&nbsp;</span>
		     </td>
	            </tr>
	            <logic:notEqual name="permission" value="true">
		            <tr>
						<td colspan=2>
		                	<span>The user does not have permissions to edit all the Co-infection investigations. Select Create New Inv to create a new separate investigation.<br></span>
		             	</td>
		            </tr>
	            </logic:notEqual>
	            <logic:equal name="permission" value="true">
		            <tr>
						<td colspan=2>
		                	<span>To add this new investigation to existing Co-infection investigation(s), select Create as Co-infection. Select Create New Inv to create a new separate investigation.<br></span>
		             	</td>
		            </tr>
	            </logic:equal>
	            <tr>
				<td colspan=2>
			<span>&nbsp;</span>
		     </td>
	            </tr>
	            <tr>
				<td colspan=2>
	                <span>This action cannot be reversed or performed later.<br></span>
	             </td>
	            </tr>
	            <tr>
				<td colspan=2>
					<span>&nbsp;</span>
			     </td>
	            </tr>
	            <logic:equal name="permission" value="true">
		            <tr>
						<td colspan=2>
							<span>If adding as a co-infection, you will be associating the new investigation to the following:<br></span>
				     	</td>
		            </tr>
		            <tr>
		              <nedss:bluebar id="inv1" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
							<tr><td>		
								<display:table name="coInfectionInvestigationList" class="bluebardtTable"  id="coInfectionInvSumary">
								   <display:column property="investigationStartDate" format="{0,date,MM/dd/yyyy}" style="width:8%;text-align:left;" title="Start Date"/>
								   <display:column property="status" style="width:6%;text-align:left;" title="Status"/>
								   <display:column property="condition" style="width:24%;text-align:left;" title="Condition"/>                                                  
								   <display:column property="caseStatus" style="width:10%;text-align:left;" title="Case Status"/>
								   <display:column property="jurisdiction" style="width:14%;text-align:left;" title="Jurisdiction"/>
								   <display:column property="investigator" style="width:14%;text-align:left;" title="Investigator"/>
								   <display:column property="localId" style="width:12%;text-align:left;" title="Investigation ID "/>
								   <display:column property="coinfectionId" style="width:12%;text-align:left;" title="Coinfection ID "/> 
								</display:table> 
							</td></tr>
					     </nedss:bluebar> 
		            </tr>
	            </logic:equal>
	          </nedss:container>
	        </nedss:container>
        </logic:notEmpty>
      </div>
      <div id="topProcessingDecisionId" class="grayButtonBar" style="text-align: right;">
      <logic:equal name="event" value="LabReport">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSelectCondition('Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
      <logic:equal name="event" value="MorbReport">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSubmitandCreateInv('MorbCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="CaseReport">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSubmitandCreateInv('CaseCreate','Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSubmitandCreateInv('CaseCreate','New')"/>
      </logic:equal>
      <logic:equal name="event" value="InvCreate">
      	<logic:equal name="permission" value="true">
	      	<logic:equal name="typeDocument" value="CaseReport">
	       	  	<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionCreateInv('Co-infection','CaseReport',<%=request.getAttribute("cond") %>)"/>
       		</logic:equal>
	       	<logic:notEqual name="typeDocument" value="CaseReport">
	       		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionCreateInv('Co-infection')"/>
	       </logic:notEqual>
       	</logic:equal>
        <logic:equal name="typeDocument" value="CaseReport">
       	  	<input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionCreateInv('New', 'CaseReport',<%=request.getAttribute("cond") %>)"/>
       	</logic:equal>
       	<logic:notEqual name="typeDocument" value="CaseReport">
     	   	<input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionCreateInv('New')"/>
        </logic:notEqual>
      </logic:equal>
      <logic:equal name="event" value="selectCondition">
      	<logic:equal name="permission" value="true">
      		<input type="button" align="right"  value="Create as Co-infection" onclick="markProcessingDecisionSelectCondition('Co-infection')"/>
      	</logic:equal>
        <input type="button" align="right"  value="Create New Inv" onclick="markProcessingDecisionSelectCondition('New')"/>
      </logic:equal>
      <input type="button" align="right"  value= "Cancel" onclick="closeDialog()"/>
    </div>
    </html:form>
    </div>
  </body>
</html>
