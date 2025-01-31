<%@ include file="/jsp/tags.jsp" %>
<%@ page import="java.util.*" %>      
<%@ page import="gov.cdc.nedss.entity.person.dt.*" %>      

<html lang="en">
    <title>Contact Search Results</title>
	<head>
	<base target="_self">
		<%@ include file="/jsp/resources.jsp" %>
		<script type='text/javascript' src='/nbs/dwr/interface/JPamForm.js'></script>		
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JCTContactForm.js"></SCRIPT>	
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>	
 	    <SCRIPT LANGUAGE="JavaScript">
 	    
	function populateOtherPatient(patientUid, differentLot, investigationUid, identifier, investigatorId)
	{
			
			
		if (differentLot == "true") {
			alert("The Other Infected Patient's Investigation is in a different epi-linked group (Lot). Please contact your Supervisor or System Administrator if assistance is needed to merge epi-linked groups.");
		}
		var opener = getDialogArgument();		       
		pview =getElementByIdOrByNameNode("pageview", opener.document); 
		var parent = opener;
		var parentDoc = parent.document;				
		if(identifier == "CON142"){
			JCTContactForm.getDwrContactPatientDetailsByUid(patientUid, investigationUid, identifier,investigatorId,function(data) {
				dwr.util.setEscapeHtml(false);
				dwr.util.setValue(parentDoc.getElementById("cTContactClientVO.answer(CON142)"), "");
				dwr.util.setValue(parentDoc.getElementById(identifier), data);
				//dwr.util.setValue(parentDoc.getElementById("investigator.personUid"), uid);
				//parentDoc.getElementById(identifier+"Text").style.visibility="hidden";
				parentDoc.getElementById(identifier+"Icon").style.visibility="hidden";
				//parentDoc.getElementById(identifier+"CodeLookupButton").style.visibility="hidden";
				parentDoc.getElementById("clear"+identifier).className="";
				parentDoc.getElementById(identifier+"SearchControls").className="none";
			   	self.close();
			});
		}
		if (pview != null) {
	                pview.style.display = "none";                  
	        }
	}
					

	function closePopup()
        {              
          self.close();
          var opener = getDialogArgument();       
          var invest =getElementByIdOrByNameNode("pageview", opener.document);
          if (invest != null) {
               invest.style.display = "none";  
          }                    
        }   
			
    </SCRIPT>		
	</head>
	<body class="popup">
        <!-- Page title -->
        <div class="popupTitle">
            Contact Search Results - Other Infected Patient
        </div>
        
        <!-- Top button bar -->
        <%String queId = (String) request.getAttribute("identifier");%>
        <div class="popupButtonBar">
            <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="closePopup();"/>
        </div>
        
        <!-- Results block -->
        <div style="width:100%; text-align:center;">
            <div style="width:98%;">
	            <form method="post" id="nedssForm" action="">
	                <nedss:container id="section1" name="Search Results - Select Investigation" classType="sect" 
	                        displayImg ="false" displayLink="false" includeBackToTopLink="no">
	                        

	                    
	                    <div class="infoBox messages">
	                       <logic:equal name="InvestigationsToSelect" value="true">
	                          Select is available for open investigations for the same condition in which <i><%=request.getAttribute("ContactPatientName")%> </i>
	                          is <b>infected</b> and <b>interviewed</b>.<br/>
	                          Select the investigation to which the contact record should be associated.
	                        </logic:equal>
	                       <logic:equal name="InvestigationsToSelect" value="false">
	                          There are 0 open investigations for the same condition in which <i><%=request.getAttribute("ContactPatientName")%> </i>
	                          is <b>infected</b> and <b>interviewed</b>.<br/>
	                        </logic:equal>	                        
	                    </div>
	                    
	                    <table role="presentation" width="100%" border="0" cellspacing="0">
	                        <tr>
	                           <td align="center">              
	                               <display:table name="patientInvestigationList" class="dtTable" pagesize="20"  id="parent" requestURI="">
	                                  <display:column property="actionLink" title="<p style='display:none'>Action</p" style="width:8%;text-align:left;" class="dstag"/> 
	                                  <display:column property="startDate" style="width:8%;text-align:left;" title="Start Date"/>
				   	  <display:column property="status" style="width:6%;text-align:left;" title="Status"/>
				   	  <display:column property="conditions" style="width:16%;text-align:left;" title="Condition"/>                                                  
				   	  <display:column property="caseStatus" style="width:10%;text-align:left;" title="Case Status"/>
				   	  <display:column property="disposition" style="width:12%;text-align:left;" title="Disposition"/>
				   	  <display:column property="jurisdiction" style="width:14%;text-align:left;" title="Jurisdiction"/>
				   	  <display:column property="investigator" style="width:14%;text-align:left;" title="Investigator"/>
				   	  <display:column property="investigationId" style="width:12%;text-align:left;" title="Investigation ID "/>
	                                </display:table> 
	                            </td>
	                        </tr>
	                    </table>
	                </nedss:container>
	            </form>
	        </div>    
        </div>
        
        <!-- Bottom button bar -->
	    <div class="popupButtonBar">
	        <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="closePopup();"/>
	    </div>
    </body>
</html>


