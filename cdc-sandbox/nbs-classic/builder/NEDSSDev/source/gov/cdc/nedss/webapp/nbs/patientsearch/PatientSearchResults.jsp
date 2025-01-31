<%@ include file="/jsp/tags.jsp" %>
<%@ page language="java" %>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>      
<%@ page import="gov.cdc.nedss.entity.person.dt.*" %>      

<html lang="en">
    <title>Contact Search</title>
	<head>
	   <base target="_self">
		<%@ include file="/jsp/resources.jsp" %>
		<script type='text/javascript' src='/nbs/dwr/interface/JPamForm.js'></script>		
 	    <script language="javascript">
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
            
            function handlePageUnload(closePopup,e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;

                    if (e.clientY < 0 || closePopup == true) {
                    	      var otherInfected ="${patientSearchForm.attributeMap.contactPatientEle}";
			       if (otherInfected != null && otherInfected != "") {
			                //pass control to parent's call back handler
					window.returnValue = "windowClosed";
			                window.close();
			                return;
               			}
                    	   
	                    // get reference to opener/parent           
	                    var opener = getDialogArgument();
	                    opener.document.forms[0].action ="/nbs/LoadPatientSearch.do?method=Cancel";
                        opener.document.forms[0].submit();
                         
	                    // pass control to parent's call back handler
	                    window.returnValue = "windowClosed";
                        window.close();
                    }
                }
            }
	function showPatientInvestigations(patientUid, identifier) 
	{
		document.forms[0].action = "/nbs/PatientSearch.do?method=patientInvestigationListLoad&selectedPatientUid="+patientUid+"&identifier="+identifier;
		document.forms[0].submit();		
			
			
	}
			function populatePatient(uid, identifier,patientUid, caseLocalId)
			{
				document.forms[0].action = "/nbs/ContactTracing.do?method=AddContactLoad&MprUid=" + uid+"&patientUid="+patientUid+"&caseLocalId="+caseLocalId;
				document.forms[0].submit();
			}

			function viewPatientEvents(url)
			{    
		        var o = new Object();
		        o.opener = self;    
		        var dialogFeatures = "dialogWidth: " + 780 + "px;dialogHeight: " +
                700 + "px;status: no;unadorned: yes;scroll: yes; scrollbars:yes; help: no; resizable: yes;";
		        var modWin = openWindow(url, o,dialogFeatures, null, "");
			}

			
					
	  		function createProvider(id){
	  			document.forms[0].action ="/nbs/AddProvider.do?method=addProvider&identifier="+id;
	  			document.forms[0].submit();
	  		}
	  		
	  		function AddNewPatient(caseLocalId)
            {
                var uid="-1";
                patientUid="-1";
                document.forms[0].action = "/nbs/ContactTracing.do?method=AddContactLoad&MprUid=" + uid+"&patientUid="+patientUid+"&caseLocalId="+caseLocalId+"&addNew=true";
                document.forms[0].submit();
            }
			
		</script>		
	</head>
	<body class="popup" onload ="addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;" style="padding-right:15px;">
	<div id="PatientSearchResultsLoad"></div>
        <!-- Page title -->
        <div class="popupTitle">
        <logic:empty name="patientSearchForm" property="attributeMap.contactPatientEle">
            Contact Search Results
        </logic:empty>
        <logic:notEmpty name="patientSearchForm" property="attributeMap.contactPatientEle">
            Contact Search Results - Other Infected Patient
        </logic:notEmpty>
                
        
        </div>
        
        <!-- Top button bar -->
        <%
        String queId = (String) request.getAttribute("identifier");
        String caseLocalId = (String) request.getAttribute("caseLocalId");
       %>
        <div class="popupButtonBar">
           <logic:empty name="patientSearchForm" property="attributeMap.contactPatientEle">
	           <logic:equal value="true" name="addButton" scope="request">
	            	<input type="button"  name="Add" value="Add New" id="Add" onclick="AddNewPatient('${fn:escapeXml(caseLocalId)}')" />
	            </logic:equal>
           </logic:empty> 
            <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="handlePageUnload(true,event)" />
        </div>
        
        <!-- Results block -->
        <div style="width:100%; text-align:center;">
            <div style="width:98%;">
	            <form method="post" id="nedssForm" action="">
	                <nedss:container id="section1" name="Search Results" classType="sect" 
	                        displayImg ="false" displayLink="false" includeBackToTopLink="no">
	                        
	                    <div style="width:100%; text-align:right;margin:4px 0px 4px 0px;">
	                        <a href="${fn:escapeXml(NewSearchLink)}">New Search</a>&nbsp;|&nbsp;
	                        <a href="${fn:escapeXml(RefineSearchLink)}">Refine Search</a>
	                    </div>
	                    
	                    <div class="infoBox messages" align="left">
	                        Your Search Criteria: <i><c:out value="${SearchCriteria}"/></i>
	                        resulted in <b>${fn:escapeXml(ResultsCount)}</b> possible matches.
	                     <logic:empty name="patientSearchForm" property="attributeMap.contactPatientEle">
	                        <br/>  Select an existing person below to add as a contact,
	                        <logic:equal value="true" name="addButton" scope="request">   
	                        	or <a href class="cursorHand" onclick="AddNewPatient('${fn:escapeXml(caseLocalId)}'); return false;">Add New</a> </n>
	                        </logic:equal>
			      </logic:empty>
			      <logic:notEmpty name="patientSearchForm" property="attributeMap.contactPatientEle">
			      	<br/> Select an existing person below to view a summary of the patient's investigations.
			      </logic:notEmpty>
	                    </div>
	                    
	                    <table role="presentation" width="100%" border="0" cellspacing="0">
	                        <tr>
	                           <td align="center">              
	                               <display:table name="patientsList" class="dtTable" pagesize="20"  id="parent" requestURI="">
	                                  <display:column property="actionLink" title="<p style='display:none'>Action</p>" class="dstag"/> 
	                                  <display:column property="fullName" title="Name" class="dstag"/>
	                                  <display:column property="ageSexDOB" title="Age/DOB/Sex" class="dstag"/>
	                                  <display:column property="address" title="Address" class="dstag"/>
	                                  <display:column property="telephone" title="Telephone" class="dstag"/>
	                                  <display:column property="conditions" title="Conditions" class="dstag"/>
	                                  <display:setProperty name="basic.empty.showtable" value="true"/>
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
	      <logic:empty name="patientSearchForm" property="attributeMap.contactPatientEle">
	      <logic:equal value="true" name="addButton" scope="request">
	        <input type="button"  name="Add" value="Add New" id="Add" onclick="AddNewPatient('<%=caseLocalId%>')" />
	        </logic:equal>
	      </logic:empty>
	        <input type="button"  name="Cancel" value="Cancel" id="Cancel" onclick="handlePageUnload(true,event)" />
	    </div>
    </body>
</html>