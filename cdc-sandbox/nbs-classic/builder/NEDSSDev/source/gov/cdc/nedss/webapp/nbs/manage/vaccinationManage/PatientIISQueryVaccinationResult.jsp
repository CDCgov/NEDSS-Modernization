<%@page import="gov.cdc.nedss.vaccination.iis.dt.VaccinationSearchResultDT"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>
<html lang="en">
    <head>
    <base target="_self">
     <title>Immunization Registry Search result</title>
     <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
     <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
          <script src="/nbs/dwr/interface/JIISQueryForm.js" type="text/javascript"></script>
     <%@ include file="../../jsp/resources.jsp" %>
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
     <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="observationSecurityAssgnReview.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="genericQueue.js"></SCRIPT>
	 <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
     <script language="JavaScript">

		function checkToClosePopup(){
		    	var closePopup = '${closePopup}';
		    	if(closePopup == 'true'){
		    		var opener = getDialogArgument(); 
					if(opener!=null){                
					   opener.document.forms[0].action ="/nbs/PageAction.do?method=closeWindow";
					   opener.document.forms[0].submit();          	                    
					   window.returnValue = "windowClosed"; 
					   window.close();
					   }
			    }
		}
		
     	function importSelectedRecordsForm(){
     		var errorText="";
     		var errorMsgArray = new Array(); 
			if(isAnyCheckBoxSelected(getElementByIdOrByName("selectCheckBox"))){
				var confirmMsg="If you continue with the Import Selected Records action, the selected vaccinations will have individual Vaccination Events created and become associated to the current Investigation. Select OK to continue, or Cancel to not continue.";
				if (confirm(confirmMsg)) {
					var opener = getDialogArgument();			
					var pview =getElementByIdOrByNameNode("pageview", opener.document);
					pview.style.display = "none";
					document.forms[0].action ='/nbs/IISQuery.do?method=importSelectedVaccinations';
		        	document.forms[0].submit();	
		        	
				} else {
					return false;
				}
			}else{
				errorText = "Please select at least one vaccination record for import into the NBS.";
				errorMsgArray.push(errorText);
				displayGlobalErrorMessage(errorMsgArray);
				    var tabElts = new Array(); 
				    setErrorTabProperties(tabElts);  
				    return false;
			}
        }
        
     	function cancelForm()
		{
			var confirmMsg="If you continue with the Cancel action, vaccination records will not be imported and you will be returned to the Manage Associations screen. Select OK to continue, or Cancel to not continue.";
		    if (confirm(confirmMsg)) {
				var opener = getDialogArgument();			
				var pview =getElementByIdOrByNameNode("pageview", opener.document);
				pview.style.display = "none";
				self.close();
			return false;
		    } else {
				return false;
			}
		}
		
        function showCount() {
			$j(".pagebanner b").each(function(i){ 
				$j(this).append(" of ").append($j("#queueCnt").attr("value"));
			});
			$j(".singlepagebanner b").each(function(i){ 
				var cnt = $j("#queueCnt").attr("value");
				if(cnt > 0)
					$j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
			});	
		}
	   
        function selectfilterCriteria()
		{
			document.forms[0].action ='/nbs/IISQuery.do?method=filterVaccinationSearchListSubmit';
			document.forms[0].submit();
		}

        function clearFilter()
        {

        	document.forms[0].action ='/nbs/IISQuery.do?method=searchVaccinationsForPatientFromIIS&initLoad=true';
        	document.forms[0].submit();										
        }
        
	    function cancelFilter(key) {
			key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));	
			JIISQueryForm.getAnswerArray(key1, function(data) {			  			
				revertOldSelections(key, data);
			});		  	
		}
	    
       </script> 
       <style type="text/css">
        div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
        .removefilter{
					background-color:#003470; width:100%; height:25px;
					line-height:25px;float:right;text-align:right;
					}
					removefilerLink {vertical-align:bottom;  }
					.hyperLink
					{
					    font-size : 10pt;
					    font-family : Geneva, Arial, Helvetica, sans-serif;
					    color : #FFFFFF;
						text-decoration: none;
					}
        </style>      
    </head>

 <body class="popup" onload="attachIcons();makeMSelects();showCount();displayTooltips();uncheckHeader();checkToClosePopup();">
 <div id="blockparent"></div>
      <html:form action="/IISQuery.do" styleId="iisQueryForm">
      <div class="popupTitle">
              	Immunization Registry Vaccination
        </div>
        <div id="doc3">
                  <!-- Body div -->
	                <div id=bd">

		             <!-- Top button bar -->
	      			<div class="grayButtonBar" style="text-align: right;">
	      				<input type="button" name="Submit" value="Import Selected Records" onclick="importSelectedRecordsForm();"/>
	      				<input type="button" name="Submit" value="Cancel" onclick="cancelForm();"/>
	           	 	</div>
	           	 	
					<div id="globalFeedbackMessagesBar" class="screenOnly">
						<logic:messagesPresent name="error_messages">
							<div class="infoBox errors" id="errorMessages">
							    <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
							    <ul>
								<html:messages id="msg" name="error_messages">
								    <li> <bean:write name="msg" /> </li>
								</html:messages>
							    <ul>
							</div>
						</logic:messagesPresent>
					</div>
				
					<logic:equal name="isError" value="true">
						<div class="infoBox ${infoBox}" id="warningMessages" align="left">
							<p style="text-align:left">${feedbackErroMessage}</p>
						</div>
					</logic:equal>
					
					<logic:equal name="isErrorBeforeImport" value="true">
						<div class="infoBox errors" id="errorMessages" align="left">
							<p style="text-align:left">${erroMessage}</p>
						</div>
					</logic:equal>
					
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Search Results" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">

        				<tr><td>&nbsp;</td></tr>
               			<div style="width:100%; text-align:right;margin:4px 0px 4px 0px;">
				 	<a href="IISQuery.do?method=searchPatientFromIIS&initLoad=false">Return to Search Results</a> &nbsp;&nbsp;&nbsp;
            			</div>
               			<table role="presentation" class="style">
				        <tr class="cellColor">
				            <!-- Name, Sex, DOB, Patient Id -->
						<td class="border" colspan="3">
						<%
					            String name = (String) request.getAttribute("patientName");
								if( name == null || name.trim().length() ==0)
					            	name = "---";
					            name = name.trim(); 
					            request.setAttribute("name", name);
					           
					            String currentSex = (String) request.getAttribute("patientSex");
					            if( currentSex == null || currentSex.trim().length() ==0)
					            	currentSex= "---";
					            currentSex = currentSex.trim();
					            request.setAttribute("currentSex", currentSex);
					            
					            String DOB = (String) request.getAttribute("patientDOB");
					            if( DOB == null || DOB.trim().length() ==0)
					            	DOB= "---";
					            request.setAttribute("DOB", DOB);
					           
					         %>
					            <span class="valueTopLine"> <c:out value="${name}" /></span>
					            <span class="valueTopLine">|</span>
					            <span class="valueTopLine"> <c:out value="${currentSex}"/> </span>
					            <span class="valueTopLine">|</span>
					            <%if(request.getAttribute("currentAgeUnitCd") != null){%>
					            <span class="valueTopLine"> <c:out value="${DOB}"/> (<%= HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge")))!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge"))):""%><%=HTMLEncoder.encodeHtml(CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT"))%>) </span>
					            <%}else{ %>
					            <span class="valueTopLine"> <c:out value="${DOB}"/>  </span>
					            <%} %>
						</td>
						<td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">

						    <span class="valueTopLine"> Registry Patient ID: </span>
						    <span style="font:16px Arial; margin-left:0.2em;"> <c:out value="${registryPatientIDStr}" /> </span>
						    <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
						</td>	
				        </tr>
				    
   				</table>
   				<br/>
               			<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
	                    <div class="removefilter" id="removeFilters">
		                	<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
	                    </div>
               			
               			<table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">
                                	
                                    <display:table name="vaccinationSearchList" class="dtTable" pagesize="${iisQueryForm.attributeMap.queueSize}" id="parent" requestURI="/nbs/IISQuery.do?method=searchPatientFromIIS&existing=true&initLoad=true" export="true" 
                                    sort="external" excludedParams="stringQueueCollection">
                                    <display:column style="width:3%;text-align:center" media="html" title="<p style='display:none'>Select/Deselect All</p><input title='Select/Deselect All' type='checkbox' style='margin-left:27%' name='selectAllValue' onchange='checkAll(this)' />" ><input title="Select/Deselect checkbox" type="checkbox" name="selectCheckBox" class="selectCheckBox" value="${parent.vaccSeqNbr}"/> </display:column>
                                        <display:setProperty name="export.csv.filename" value="PortPagePortLibrary.csv"/> 
                       					<display:setProperty name="export.pdf.filename" value="PortPagePortLibrary.pdf"/>
                       						<c:forEach items="${iisQueryForm.queueCollection}" var="item">
											<bean:define id="media" value="${item.media}" />
											<logic:match name="media" value="html">
												<display:column property="${item.mediaHtmlProperty}" defaultorder="${item.defaultOrder}" sortable="${item.sortable}" sortName= "${item.sortNameMethod}" media="html" title="${item.columnName}" style="${item.columnStyle}" class ="${item.className}" headerClass="${item.headerClass}" />
											</logic:match>
											<logic:match name="media" value="pdf">
												<display:column property="${item.mediaPdfProperty}" defaultorder="${item.defaultOrder}" sortable="${item.sortable}" sortName= "${item.sortNameMethod}" media="pdf" title="${item.columnName}" style="${item.columnStyle}" class ="${item.className}" headerClass="${item.headerClass}" />
											</logic:match>
											<logic:match name="media" value="csv">
												<display:column property="${item.mediaCsvProperty}" defaultorder="${item.defaultOrder}" sortable="${item.sortable}" sortName= "${item.sortNameMethod}" media="csv" title="${item.columnName}" style="${item.columnStyle}" class ="${item.className}" headerClass="${item.headerClass}" />
											</logic:match>
											<br>
										</c:forEach>
										<display:setProperty name="basic.empty.showtable" value="true"/>
                                    </display:table>
   								</td>
                            </tr>                           
                     	</table>
                     	<br/>
                     	<br/>		
                     	                    	
					</nedss:container>
    				<div style="display: none;visibility: none;" id="sortingAndFilterMessages">
						<b> <a name="sortingAndFilterMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
						<ul>
							<logic:iterate id="filterAndSort" name="iisQueryForm" property="attributeMap.searchCriteria">
								<li id="${fn:escapeXml(filterAndSort.key)}">${fn:escapeXml(filterAndSort.value)}</li>
							</logic:iterate>
						</ul>
					</div>
    				
    				<div class="grayButtonBar" style="text-align: right;">
    					<input type="button" name="Submit" value="Import Selected Records" onclick="importSelectedRecordsForm();"/>
	      				<input type="button" name="Submit" value="Cancel" onclick="cancelForm();"/>
	           	 	</div>
			</div> <!-- id = "bd" -->
			<%@ include file="/jsp/dropDowns_Generic_Queue.jsp" %>
            
            <c:forEach items="${iisQueryForm.queueCollection}" var="item">
	            <bean:define id="filterType" value="${item.filterType}" />
	            <logic:match name="filterType" value="1">
	            	<input type='hidden' id="${item.dropdownProperty}" value="<%=request.getAttribute("${item.backendId}") != null ? request.getAttribute("${item.backendId}") : ""%>" />
	            </logic:match>
            </c:forEach>

			<input type='hidden' id='actionMode' value="${fn:escapeXml(ActionMode)}" />
			
			<input type="hidden" id="vaccinationImportSucessMessage" value="<%= request.getAttribute("vaccinationImportFailureMessage") %>"/> 
			<input type="hidden" id="vaccinationImportFailureMessage" value="<%= request.getAttribute("vaccinationImportFailureMessage") %>"/> 
	</div> <!-- id = "doc3" -->
    </html:form>
    <input type="hidden" id="queueCnt" value="<c:out value="${queueCount}" />"/>
  </body>
</html> 