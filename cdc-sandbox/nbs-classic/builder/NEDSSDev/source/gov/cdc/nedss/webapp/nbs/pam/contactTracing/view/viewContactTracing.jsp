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
        var urlToOpen =  "<%=request.getAttribute("contactUrl")%>&patientUid="+patientRevision+"&caseLocalId="+caseLocalId+"&personMprUid="+perMprUid;
        
        // get the gray background element & activate it.
        var divElt = getElementByIdOrByName("pamview");
        if (divElt == null) {
            divElt = getElementByIdOrByName("blockparent");
        }
        divElt.style.display = "block";
        
        // open a modal window        
        var o = new Object();
        o.opener = self;
        //var modWin = window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
        
    	//var modWin = window.showModalDialog(urlToOpen , o, "dialogWidth: " + 760 + "px;dialogHeight: " +
         //       700 + "px;status: no;unadorned: yes;scroll: yes;help: no; resizable: yes;");
        
    	
    	
    	var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " +
        700 + "px;status: no;unadorned: yes;scroll: yes; scrollbars=yes;help: no; resizable: yes;";
        
    	var modWin = openWindow(urlToOpen, o,dialogFeatures, divElt, "");
    	
    	
    	
    	
        // handle any return values from modal window. If return value is
        // "windowClosed", deactivate the gray background by hiding it. 
        if (modWin == "windowClosed") {
            divElt.style.display = "none";
        }
    }
    
    function AddContactPopUp()
    {
        var urlToOpen = "";
        var divElt = getElementByIdOrByName("pamview");
        divElt.style.display = "block";		
        var o = new Object();
        o.opener = self;
        //window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));

        window.open( "/nbs/PatientSearch.do?method=searchLoad", "myWindow", 
            "status = 1, height = 600, width = 700, resizable = 0, scrollbars= yes" )
    }
		
    function populateContactRecordPopUp(contactRecordUID)
    {
        var urlToOpen =  "<%=request.getAttribute("populateContactRecord")%>&contactRecordUid="+contactRecordUID;
        var divElt = getElementByIdOrByName("pamview");
        if (divElt == null) {
            divElt = getElementByIdOrByName("blockparent");
        }
        divElt.style.display = "block";
        var o = new Object();
        o.opener = self;
        //var modWin = window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
    
        var modWin = openWindow(urlToOpen, o,GetDialogFeatures(760, 700, false, true), divElt, "");
        
        if (modWin == "windowClosed") {
            divElt.style.display = "none";
        }
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
        var urlToOpen =  "<%=request.getAttribute("managectAssoUrl")%>&patientUid="+patientRevision+"&caseLocalId="+caseLocalId+"&personMprUid="+perMprUid;
        
		var divElt = getElementByIdOrByName("pamview");
        if (divElt == null) {
            divElt = getElementByIdOrByName("blockparent");
        }
        divElt.style.display = "block";
	    
        var o = new Object();
        o.opener = self;
        //window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 700, false));
        
        var modWin = openWindow(urlToOpen, o,GetDialogFeatures(760, 700, false, true), divElt, "");
        
        
    }
</script>

<tr> <td>
<% 
    int subSectionIndex = 0;
    String tabId = "editContactTracing";
    String []sectionNames = {"Risk Assessment","Contact Records","Custom Fields"};
    int sectionIndex = 0;
%>

<div class="view"  id="<%= tabId %>" style="text-align:center;">    
    <table role="presentation" class="sectionsToggler" style="width:100%;">
        <tr>
            <td>
                <ul class="horizontalList">
                    <li style="margin-right:5px;"><b>Go to: </b></li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
                   
                    <% if(request.getAttribute("1423") != null && request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) { %>
                        <li class="delimiter"> | </li>
                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>    
                    <% } else if(request.getAttribute("2423") != null && request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)){%>
                    <li class="delimiter"> | </li>
                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>    
                    <%} %>
                    
                </ul>
            </td>
        </tr>
        <tr>
            <td style="padding-top:1em;">
                <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
            </td>
        </tr>
    </table>

    <%
        // reset the sectionIndex to 0 before utilizing the sectionNames array.
        sectionIndex = 0;
    %>
            
    <!-- SECTION : Risk Assessment --> 
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Risk Assessment" classType="subSect" >
         <!-- Contact Investigation Priority -->
              <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.INV257.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV257.errorStyleClass}" 
                                title="${PamForm.formFieldMap.INV257.tooltip}">
                            ${PamForm.formFieldMap.INV257.label}
                        </span> 
                    </td>
                    <td>
                         <nedss:view name="PamForm" property="pamClientVO.answer(INV257)" 
                                codeSetNm="<%=NEDSSConstants.CONTACT_PRIORITY%>"/>
                    </td>
                </tr>
            <!-- Infectious Period From -->
            <tr>
                <td  class="fieldName" >
                    <span style="${PamForm.formFieldMap.INV258.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV258.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV258.tooltip}">
                        ${PamForm.formFieldMap.INV258.label}
                    </span> 
                </td>
                <td> 
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV258)"/>
                </td>
               
            </tr>
            <!-- Infectious Period To -->
            <tr>
                <td  class="fieldName">
                    <span style="${PamForm.formFieldMap.INV259.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV259.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV259.tooltip}">
                        ${PamForm.formFieldMap.INV259.label}
                    </span> 
                </td>                
                 <td> 
                   <nedss:view name="PamForm" property="pamClientVO.answer(INV259)"/>
                </td>
            </tr>   
            <!-- Risk Assessment LDFs -->
             <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {%>
	         <%= request.getAttribute("1405") == null ? "" :  request.getAttribute("1405") %>
	        <% }else if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)){ %>
	          <%= request.getAttribute("2405") == null ? "" :  request.getAttribute("2405") %>
	        <% }%>
	     </nedss:container>
	     <!-- Administrative Information -->
	     <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Administrative Information" classType="subSect" >
         <!-- Contact Investigation Status -->
              <tr>
                    <td class="fieldName">
                        <span style="${PamForm.formFieldMap.INV260.state.requiredIndClass}">*</span>
                        <span style="${PamForm.formFieldMap.INV260.errorStyleClass}" 
                                title="${PamForm.formFieldMap.INV260.tooltip}">
                            ${PamForm.formFieldMap.INV260.label}
                        </span> 
                    </td>
                    <td>
                         <nedss:view name="PamForm" property="pamClientVO.answer(INV260)" 
                                codeSetNm="<%=NEDSSConstants.CONTACT_STATUS%>"/>
                    </td>
                </tr>
                      
           <!-- Contact Investigation Comments -->
            <tr>
                <td class="fieldName"> 
                    <span style="${PamForm.formFieldMap.INV261.state.requiredIndClass}">*</span>
                    <span style="${PamForm.formFieldMap.INV261.errorStyleClass}" 
                            title="${PamForm.formFieldMap.INV261.tooltip}">${PamForm.formFieldMap.INV261.label}</span> 
                </td>
                <td > 
                       <nedss:view name="PamForm" property="pamClientVO.answer(INV261)"/>
                </td>
            </tr>
            <!-- Administrative Information LDFs -->
	         <%
        	if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) {%>
	         <%= request.getAttribute("1409") == null ? "" :  request.getAttribute("1409") %>
	        <% }else if(request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)){ %>
	          <%= request.getAttribute("2409") == null ? "" :  request.getAttribute("2409") %>
	        <% }%>
         </nedss:container>
	     
        </nedss:container>     
        
    <!-- SECTION : Contact Records -->
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
     
    <!-- SECTION : Contacts Named By Patient -->   
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Contacts Named By Patient" classType="subSect" >
               <tr style="background:#FFF;"> 
                   <td colspan="2">The following contacts were named within ${PamForm.attributeMap.patientLocalName}'s investigation: </td>
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
                  <td colspan="2">The following contacts  named  ${PamForm.attributeMap.patientLocalName} within their investigation and have been associated
                                  to ${PamForm.attributeMap.patientLocalName}'s investigation: </td>
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
    <!-- SECTION : Local Fields -->
   <% if(request.getAttribute("1423") != null && request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_RVCT)) { %>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		<%= request.getAttribute("1423") == null ? "" :  request.getAttribute("1423") %>
        </nedss:container>
   </nedss:container>
    <%} else if(request.getAttribute("2423") != null && request.getAttribute("formCode").equals(NEDSSConstants.INV_FORM_VAR)){%>
    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">
        <!-- SUB_SECTION :  Local Fields -->
        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Custom Fields" classType="subSect" >
        		<%= request.getAttribute("2423") == null ? "" :  request.getAttribute("2423") %>
        </nedss:container>
   </nedss:container>
   <%} %>
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