<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>


<html:hidden name="manageEventsForm" property="selectedcheckboxIds" styleId="chkboxIds"/>
<% 
int  subSectionIndex= (new Integer(request.getParameter("param2").toString())).intValue();
String tabId = request.getParameter("param3").toString();
request.setAttribute("finalTabID", tabId + (++subSectionIndex));
%>
<!-- SECTION -->

<%if(((String)request.getAttribute("AssoLabPermission")).equals("true")){%>
    <nedss:container id="${fn:escapeXml(finalTabID)}" name="Lab Reports" classType="subSect">                           
        <tr>
            <td colspan="2">
               <display:table name="observationSummaryLabList" class="dtTable" id="lablist" style="margin-top:0.15em;">
                    <display:column title="<p style='display:none'>Select/Deselect All</p>" style="width:5%;">
                        <div align="center" valign="middle">
                            <input type="checkbox" title="Select/Deselect checkbox" value="${lablist.isAssociated}" name="${lablist.observationUid}" ${lablist.checkBoxId}  ${lablist.disabled}/>
                        </div>
                    </display:column>
			   <display:column property="dateReceived" style="width:11%;text-align:left;" title="Date Received"/>		                                                     
			   <display:column property="providerFacility" style="width:22%;text-align:left;" title="Reporting Facility/Provider"/>
			   <display:column property="dateCollected" style="width:11%;text-align:left;" title="Date Collected"/>
			   <display:column property="description" style="width:34%;text-align:left;" title="Test Results"/>
			   <display:column property="progArea" style="width:15%;text-align:left;" title="Program Area"/>
			   <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
			   <display:setProperty name="basic.empty.showtable" value="true"/>
		      </display:table>
                
                <!-- Button to add new lab reports -->
                <%          
                if(((String)request.getAttribute("AddLabPermission")).equals("true")) 
                { %>
                <div style="text-align:right; margin-top:0.5em;">
                    <input type="button" value="Add Lab Report" onclick="labAddButtn();">
                </div>        
                <% 
                } %>
            </td>
        </tr>
    </nedss:container>
    <img style="background-color: #5F8DBF;width: 100%;" alt="" border="0" height="1" width="50" src="transparent.gif" alt="">
<%}%>

<%if(((String)request.getAttribute("AssoMorbPermission")).equals("true")){%>    
    <nedss:container id="${fn:escapeXml(finalTabID)}" name="Morbidity Reports" classType="subSect">
        <tr>
            <td colspan="2">
                <!-- Display tag table for listing all the morbidity reports -->
                <display:table name="observationSummaryMorbList" class="dtTable" id="morbList" style="margin-top:0.15em;">
                    <display:column title="<p style='display:none'>Select/Deselect All</p>" style="width:5%;">
                        <div align="center" >
                            <input type="checkbox" title="Select/Deselect checkbox" value="${morbList.isAssociated}" name="${morbList.localId}" ${morbList.checkBoxId}  ${morbList.disabled}/>
                        </div>
                    </display:column>
                    <display:column property="actionLink" title="Date Received" style="width:10%;"/>
                    <display:column property="conditionDescTxt" title="Condition"/>
                    <display:column property="reportDate" title="Report Date" format="{0,date,MM/dd/yyyy}" style="width:10%;"/>
                    <display:column property="reportTypeDescTxt" title="Type"/>
                    <display:column property="localId" title="Observation ID " style="width:15%;"/>
                    <display:setProperty name="basic.empty.showtable" value="true"/>
                </display:table>
                <!-- Button to add new morbidity reports -->
                <%          
                if(((String)request.getAttribute("AddMorbPermission")).equals("true")) 
                { %>  
                <div style="text-align:right; margin-top:0.5em;">
                    <input type="button" value="Add Morbidity Report" onclick="morbAddButtn();"> 
                </div>
                <% 
                } %>
             </td>
         </tr>
    </nedss:container>
    <img style="background-color: #5F8DBF;width: 100%;" alt="" border="0" height="1" width="50" src="transparent.gif" alt="">
<%}%>


  
   