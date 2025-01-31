<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<!--
    Page Summary:
    -------------
    This file includes the contents of a single tab (the Tuberculosis tab). This tab 
    is part of the tab container who structure is defined in a separate JSP.
-->

<script language="JavaScript">
function GetDialogFeatures(dialogWidth, dialogHeight, resizable, scroll)
{
var scrollVar="scroll: yes; scrollbars:yes;";


if(scroll==false)
	scrollVar="scroll: no; scrollbars:no;";


    return "dialogWidth: " + dialogWidth + "px;dialogHeight: " + 
            dialogHeight + "px;status: no;unadorned: yes;help: no;" + scrollVar+
            (resizable ? "resizable: yes;" : "");
}
	
    function SearchPatientPopUp(patientRevision,caseLocalId,perMprUid)
    {
    	<logic:notEqual name="PageForm" property="actionMode" value="Preview">
	        var urlToOpen =  "<%=request.getAttribute("contactUrl")%>&patientUid="+patientRevision+"&caseLocalId="+caseLocalId+"&personMprUid="+perMprUid+"&pageId="+"pageview";
	        // get the gray background element & activate it.
	        var divElt = getElementByIdOrByName("pageview");
	        if (divElt == null) {
	            divElt = getElementByIdOrByName("blockparent");
	        }
	        divElt.style.display = "block";
	        
	        // open a modal window        
	        var o = new Object();
	        o.opener = self;
	        var dialogFeatures = "dialogWidth: " + 800 + "px;dialogHeight: " +
            700 + "px;status: no;unadorned: yes;scroll: yes; scrollbars=yes;help: no; resizable: yes;";
            
            var modWin = openWindow(urlToOpen, o, dialogFeatures, divElt, "");
	        
	        
	    	//var modWin = window.showModalDialog(urlToOpen , o, "dialogWidth: " + 760 + "px;dialogHeight: " +
	                //700 + "px;status: no;unadorned: yes;scroll: yes;help: no; resizable: yes;");
	    	
	        // handle any return values from modal window. If return value is
	        // "windowClosed", deactivate the gray background by hiding it. 
	        
	        //if (modWin == "windowClosed") {
	       //     divElt.style.display = "none";
	        //}
	    </logic:notEqual>    
    }
    
    function AddContactPopUp()
    {
        var urlToOpen = "";
        var divElt = getElementByIdOrByName("pageview");
        divElt.style.display = "block";		
        var o = new Object();
        o.opener = self;
        //window.showModalDialog(urlToOpen,o, GetDialogFeatures(800, 760, false));

        window.open( "/nbs/PatientSearch.do?method=searchLoad", "myWindow", 
            "status = 1, height = 600, width = 700, resizable = 0, scrollbars= yes" )
    }
    function AddInterviewPopUp()
    {
    	//check if interview assigned span and/or patient interview status conflicts with Add Interview
	var ixsAssignedSpan = getElementByIdOrByName('NBS186');
	if (ixsAssignedSpan != null) {
	  	var interviewerAssigned = $j('#NBS186').text();
	  	var thePatientInterviewStatus = $j('#NBS192').text();
	  	
	  	//Added because if there's white spaces at the beginning, we are not able to create an interview even if the interview status is interviewed.
	  	thePatientInterviewStatus=thePatientInterviewStatus.replace(/(^\s*)/g, "");
	  	
	  	if (interviewerAssigned == null || interviewerAssigned == "" || thePatientInterviewStatus == null || thePatientInterviewStatus.charAt(0) != 'I') {
	  	     var intvToDo = "";
	  	     if ((interviewerAssigned == null || interviewerAssigned == "") && (thePatientInterviewStatus == null || thePatientInterviewStatus.charAt(0) != 'I')) 
	  	     	intvToDo = "assign an Interviewer, set the Patient Interview Status to Interviewed and try again.";
	  	     else if (interviewerAssigned == null || interviewerAssigned == "" )
	  	        intvToDo = "assign an Interviewer and try again.";
	  	     else if (thePatientInterviewStatus == null || thePatientInterviewStatus.charAt(0) != 'I')
	  	        intvToDo = "set the Patient Interview Status to Interviewed and try again.";

	  	     alert("Note: An Interview can not be added until this investigation is assigned for interview. Please edit the investigation to " +intvToDo);
	  	     return;
	  	}
  	}
        var urlToOpen =  "/nbs/PageAction.do?method=createGenericLoad&mode=Create&Action=DSInvestigationPath&businessObjectType=IXS";
        var divElt = getElementByIdOrByName("pageview");
        if (divElt == null) {
            divElt = getElementByIdOrByName("blockparent");
        }
        divElt.style.display = "block";
        var o = new Object();
        o.opener = self;
        
        
        var modWin = openWindow(urlToOpen, o, GetDialogFeatures(850, 700, false, true), divElt, "");
        
      //  var modWin = window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
    
      //  if (modWin == "windowClosed") {
       //     divElt.style.display = "none";
      //  }
    }
		
    function populateContactRecordPopUp(contactRecordUID)
    {
        var urlToOpen =  "<%=request.getAttribute("populateContactRecord")%>&contactRecordUid="+contactRecordUID;
        var divElt = getElementByIdOrByName("pageview");
        if (divElt == null) {
            divElt = getElementByIdOrByName("blockparent");
        }
        divElt.style.display = "block";
        var o = new Object();
        o.opener = self;
        
        var modWin = openWindow(urlToOpen, o, GetDialogFeatures(800, 760, false, true), divElt, "");
        
    }
    
    function populateContactRecordPopUp2(contactRecordUID, sourceConditionCd, sourceDispositionCd, sourceInterviewStatusCd, sourceCurrentSexCd)
    {
    		
            var urlToOpen =  "<%=request.getAttribute("populateContactRecord")%>&contactRecordUid="+contactRecordUID+"&SourceConditionCd="+sourceConditionCd+"&SourceDispositionCd="+sourceDispositionCd+"&SourceInterviewStatusCd="+sourceInterviewStatusCd+"&SourceCurrentSexCd="+sourceCurrentSexCd;
            var divElt = getElementByIdOrByName("pageview");
            if (divElt == null) {
                divElt = getElementByIdOrByName("blockparent");
            }
            divElt.style.display = "block";
            var o = new Object();
            o.opener = self;
            
            var modWin = openWindow(urlToOpen, o, GetDialogFeatures(800, 760, false, true), divElt, "");
              
    }
    
    function populateInterviewPopUp(interviewUID)
        {
            var urlToOpen =  "<%=request.getAttribute("populateInterviewURL")%>"+interviewUID;
            var divElt = getElementByIdOrByName("pageview");
            if (divElt == null) {
                divElt = getElementByIdOrByName("blockparent");
            }

            divElt.style.display = "block";
            var o = new Object();
            o.opener = self;
            
            var modWin = openWindow(urlToOpen, o, GetDialogFeatures(780, 750, false, true), divElt, "");
            
           // var modWin = window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
        
           // if (modWin == "windowClosed") {
           //     divElt.style.display = "none";
           // }
    }
    
    function editContactRecord(url)
    {
        getElementByIdOrByName("urlStore").value=url;
        
        var divElt = getElementByIdOrByName("blockparent");
        divElt.style.display = "block";

        var o = new Object();
        o.opener = self;
        window.open( "/nbs/PatientSearch.do?method=searchLoad", "myWindow", 
            "status = 1, height = 600, width = 700, resizable = 0, scrollbars= yes" )
    }
		
    function ManageCtAssociationtPopUp(patientRevision,caseLocalId,perMprUid)
    {
    	<logic:notEqual name="PageForm" property="actionMode" value="Preview">
	        var urlToOpen =  "<%=request.getAttribute("managectAssoUrl")%>&patientUid="+patientRevision+"&caseLocalId="+caseLocalId+"&personMprUid="+perMprUid+"&pageId="+"pageview";
	        
			var divElt = getElementByIdOrByName("pageview");
	        if (divElt == null) {
	            divElt = getElementByIdOrByName("blockparent");
	        }
	        divElt.style.display = "block";
		    
	        var o = new Object();
	        o.opener = self;
	        
	        var modWin = openWindow(urlToOpen, o, GetDialogFeatures(760, 700, false, true), divElt, "");
	        
	        
	        //window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
	        
	        
	   </logic:notEqual>	        
    }
</script>

<tr> <td>
<% 
    int subSectionIndex = 0;
    String tabId = "editContactTracing";
    String []sectionNames = {"Interviews","Contact Records"};
    int sectionIndex = 0;
%>

<div class="view"  id="<%= tabId %>" style="text-align:center;">    
<logic:notEmpty name="PageForm" property="attributeMap.InterviewPageExists">
    <!-- SECTION : Interviews -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="Interviews" classType="sect">

    <!-- SECTION : Interview -->   
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Interview" classType="subSect" >
               <tr style="background:#FFF;"> 
                   <td colspan="2">The following interviews are associated with ${PageForm.attributeMap.patientLocalName}'s investigation: </td>
               </tr>
               <tr style="background:#FFF;">
                    <td colspan="2">
                         <display:table name="interviewList" class="dtTable" id="interviewListID">
					     <display:column property="dateActionLink" title="Date of Interview" />
					     <display:column property="interviewerFullName" title="Interviewer" />
					     <display:column property="intervieweeFullName" title="Interviewee" />
					     <display:column property="intervieweeRoleDesc" title="Role"/>
					     <display:column property="interviewTypeDesc" title="Type"/>
					     <display:column property="interviewLocDesc" title="Location"/>
					     <display:column property="interviewStatusDesc" title="Interview Status"/>
	         		             <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
                   </td>
               </tr>
         <logic:equal name="BaseForm" property="securityMap(addInterviewPermission)" value="true"> 
             <logic:notEqual name="BaseForm" property="attributeMap.InterviewPageExists" value="">
               <tr> 
               		<td colspan="6" style="text-align:right;"> 
               			<input type="button" name="submitIXS" value="Add New Interview"  onclick="AddInterviewPopUp();"> 
               		</td> 
               </tr>
             </logic:notEqual>
	 </logic:equal>
        </nedss:container>
      </nedss:container>
    </logic:notEmpty>  
    <!-- SECTION : Contact Records -->
    <nedss:container id='<%= "sect_" + tabId + (++sectionIndex) %>' name="Contact Records" classType="sect">
     
    <!-- SECTION : Contacts Named By Patient -->   
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contacts Named By Patient" classType="subSect" >
               <tr style="background:#FFF;"> 
                   <td colspan="2">The following contacts were named within ${PageForm.attributeMap.patientLocalName}'s investigation: </td>
               </tr>
               <tr style="background:#FFF;">
                    <td colspan="2">
                         <display:table name="contactNamedByPatList" class="dtTable" id="contactNamedByPatListID">
					            <display:column property="namedOnDate" title="Date Named" format="{0,date,MM/dd/yyyy}" />
					            <display:column property="localId" title="Contact Record ID" />
					            <display:column property="name" title="Name"/>
					            <display:column property="priority" title="Priority"/>
					             <display:column property="disposition" title="Disposition"/>
					             <display:column property="contactPhcLocalId" title="Investigation ID"/>
	         		             <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
                   </td>
               </tr>         
        </nedss:container>
        <!-- Patient Named By Contacts -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Patient Named By Contacts" classType="subSect" >
             <tr style="background:#FFF;"> 
                  <td colspan="2">The following contacts  named  ${PageForm.attributeMap.patientLocalName} within their investigation and have been associated
                                  to ${PageForm.attributeMap.patientLocalName}'s investigation: </td>
               </tr>
            <tr style="background:#FFF;">
                    <td colspan="2">
                       <display:table name="patNamedByContactsList" class="dtTable" id="patNamedByContactsListID">
					             <display:column property="namedOnDate" title="Date Named"  format="{0,date,MM/dd/yyyy}"/>
					            <display:column property="localId" title="Contact Record ID" />
					            <display:column property="namedBy" title="Named By"/>
					            <display:column property="priority" title="Priority"/>
					             <display:column property="disposition" title="Disposition"/>
					             <display:column property="subjectPhcLocalId" title="Investigation ID"/>
	         		            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
                   </td>
               </tr>           
        </nedss:container>      
	       
    </nedss:container>   


    <div class="tabNavLinks">       
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
    <div class="tabNavLinks">
        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
        <a href="javascript:navigateTab('next')"> Next </a>
        <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
        <input type="hidden" name="endOfTab" />
    </div>
</div> <!-- view -->

</td> </tr>