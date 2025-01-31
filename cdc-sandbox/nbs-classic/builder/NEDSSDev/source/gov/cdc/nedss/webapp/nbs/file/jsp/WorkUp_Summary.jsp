<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ include file="/jsp/resources.jsp" %> 
<%@ page import="gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm" %>
<%@ page import="gov.cdc.nedss.entity.person.vo.PatientSrchResultVO" %>	
<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO" %>
<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page import="gov.cdc.nedss.util.StringUtils" %>
 <style type="text/css">

	.header{
		background-color:white; width:100%; height:35px;
			line-height:35px;float:right;text-align:right;
		}
		
	.showup{}
  </style>

  <%
  	 	String tabId = "editCaseInfo1";
  	 	tabId = tabId.replace("]","");
  	 	tabId = tabId.replace("[","");
  	 	tabId = tabId.replaceAll(" ", "");
  	 	int subSectionIndex = 0;
  	 	int sectionIndex = 0;
  	 	String sectionId = "";
  	 	String [] sectionNames  = {"Patient Summary","Reporting Information","Clinical","Epidemiologic","General Comments"};
  	 	;
  	 sectionIndex = 0; %>
    	 
	  <% CompleteDemographicForm fform = (CompleteDemographicForm)request.getSession().getAttribute("DSPersonForm");
	   PatientSrchResultVO pVo = fform.getPatSrcResVO();
	   PersonVO vo = fform.getPerson();
	   request.setAttribute("AddressProfile",pVo.getPersonAddressProfile());
	   request.setAttribute("PhoneProfile",pVo.getPersonPhoneprofile());
	   request.setAttribute("PersonIds",pVo.getPersonIds());
	   request.setAttribute("Profile",pVo.getProfile());
	   %>
  
  
  <%
    
    
    String strInvestigationSummarySize =request.getAttribute("investigationSummarySize")!=null?
    			(String)request.getAttribute("investigationSummarySize"):"0";
    int investigationSummarySize = 0;
    
    if(request.getAttribute("reportListSize") !=null){
    	investigationSummarySize=new Integer(strInvestigationSummarySize).intValue();
    }
    String statusInvestigation ="expand";
    if(investigationSummarySize > 0)
	  	statusInvestigation ="collapse";
   
    
    String reportListSize =request.getAttribute("reportListSize")!=null?(String)request.getAttribute("reportListSize"):"0"; 
    String summaryReport ="expand";
    int reportsSize = 0;
    if(request.getAttribute("reportListSize") !=null){
    	reportsSize=new Integer(reportListSize).intValue();
    }	
    if(reportsSize > 0)
    	summaryReport ="collapse";    	
    	
  %>
    <script language="JavaScript"> 
        
        
      	function tableCollapseSummary(){      
		var invSum = '<%= investigationSummarySize %>';
		if(invSum == 0 ){	
		   toggleSectionDisplay1('subsect_Summary');
		}
		var report = '<%= reportsSize %>';
		if(report == 0 ){
			toggleSectionDisplay1('subsect_Reports');
		}
      	}
  </script>
            
          <style type="text/css">
            table.nedssBlueBG {background:#DCE7F7;}
        </style>      
         
      
      <tr><td > 
      	 	 
    <div class="view" id="viewWorkupSummary" >
			<div class="showup" style="text-align:right;"> 
				<a   href="javascript:toggleAllSectionsDisplayWorkup('viewWorkupSummary',2)"/><font class="hyperLink"> Expand All</font></a>
				 | <a  href="javascript:toggleAllSectionsDisplayWorkup('viewWorkupSummary',1)"/><font class="hyperLink"> Collapse All</font></a>
			</div>	
			<div> 
		         <table role="presentation"><tr><td height="05 px"></td></tr></table>
		        </div>	
		<table role="presentation" width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr bgcolor="#003470">
				<td height="25 px" valign="center" align="left"><font class="boldTwelveYellow">
				&nbsp;Patient Summary</font>
				</td>
				<td align="right">
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2"></td>
			</tr>
		</table>
	
	
    <table role="presentation" class="sectionsToggler" style="width:100%;">
        <tr>
            <td>
                <ul class="horizontalList">
                    <li style="margin-right:5px;"><b>Go to: </b></li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_basicInfo')">Patient Summary</a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Summary')">Open Investigations</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Reports')">Documents Requiring Review</a> </li>
                 </ul>
            </td>
        </tr>
        <tr>
            <td>
                <a class="toggleHref"></a>
            </td>
        </tr>
    </table> 
	
         
    
    
    <nedss:bluebar id="subsect_basicInfo" name="Patient Summary" defaultDisplay="F" classType="bluebarsect" >
	  <nedss:bluebar id="Summary_summary" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	  
								<tr>  
					                        <td valign="top" width="25%">             
					                         <%=HTMLEncoder.sanitizeHtml(pVo.getPersonAddressProfile())%>
					                       </td> 
					                        <td valign="top" width="25%">                    
					                          <%=HTMLEncoder.sanitizeHtml(pVo.getPersonPhoneprofile())%>
					                       </td>       
					                        <td valign="top" width="25%">                    
					                        <%=HTMLEncoder.sanitizeHtml(pVo.getPersonIds())%>
					                       </td>       
					                        <td valign="top" width="25%">                    
					                        <%=HTMLEncoder.sanitizeHtml(pVo.getProfile())%>
					                       </td>                     
					                               
			                    </tr>
          </nedss:bluebar>                 
	</nedss:bluebar>  
	
	
	<nedss:bluebar id="subsect_Summary" name="Open Investigations" classType="bluebarsect" displayLink="F" count="<%= strInvestigationSummarySize %>" 
				defaultDisplay="<%= statusInvestigation %>">
		<nedss:bluebar id="Summary1" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
		  <logic:equal name="viewInves" value="true">
			<tr><td>
		      <display:table name="investigationSummaryDtList" class="bluebardtTable"  id="PatSumaryInv1">
				   <display:column property="startDate" style="width:8%;text-align:left;" title="Start Date"/>
				   <display:column property="conditions" style="width:19%;text-align:left;" title="Conditions"/>                                                  
				   <display:column property="caseStatus" style="width:10%;text-align:left;" title="Case Status"/>
				   <display:column property="notification" style="width:13%;text-align:left;" title="Notification"/>
				   <display:column property="jurisdiction" style="width:14%;text-align:left;" title="Jurisdiction"/>
				   <display:column property="investigator" style="width:12%;text-align:left;" title="Investigator"/>
				   <display:column property="investigationId" style="width:16%;text-align:left;" title="Investigation ID "/>
				     <display:column property="coinfectionId" style="width:14%;text-align:left;" title="Co-Infection ID "/> 
		      </display:table>               
	      	</td></tr>
	      	</logic:equal>
			<logic:equal name="viewInves" value="false">
				<tr><td>&nbsp;</td></tr>
	     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
			    <tr><td>&nbsp;</td></tr>
			</logic:equal>
         </nedss:bluebar>  
	</nedss:bluebar>
	
	<nedss:bluebar id="subsect_Reports" name="Documents Requiring Review" classType="bluebarsect" displayLink="F"  count="<%= reportListSize %>" 
				defaultDisplay="<%= summaryReport %>">
           <nedss:bluebar id="Summary2" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
           <logic:equal name="viewObs" value="true">
			<tr><td>                      
		      <display:table name="unprocessedReports" class="bluebardtTable"  id="PatSumaryDRRQ">
					   <display:column property="eventType" style="width:10%;text-align:left;" title="Document Type"/>
					   <display:column property="dateReceived" style="width:9%;text-align:left;" title="Date Received"/>                                                  
					   <display:column property="providerFacility" style="width:20%;text-align:left;" title="Reporting Facility/Provider"/>
					   <display:column property="dateCollected" style="width:10%;text-align:left;" title="Event Date"/>
					   <display:column property="description" style="width:30%;text-align:left;" title="Description"/>
					   <display:column property="eventId" style="width:12%;text-align:left;" title="Event ID "/>
	
		      </display:table>  
		     </td></tr>	
		    </logic:equal>
			<logic:equal name="viewObs" value="false">
				<tr><td>&nbsp;</td></tr>
	     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
			    <tr><td>&nbsp;</td></tr>
			</logic:equal>	                   
           </nedss:bluebar>         
	</nedss:bluebar>
	</div>
	<div class="tabNavLinks">
							<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
							<a href="javascript:navigateTab('next')"> Next </a>
							<input name="endOfTab" type="hidden"/>
						</div>
</td> 
</tr> 
	
	