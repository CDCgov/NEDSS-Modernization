<%@page import="gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
    <head>
     <title>NBS: Manage Pages</title>
     <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
     <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
     <script src="/nbs/dwr/interface/JPageBuilder.js" type="text/javascript"></script>
     
     <%@ include file="../../jsp/resources.jsp" %>     
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
     <script type="text/javascript" src="PortPageSpecific.js"></script>
     <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="genericQueue.js"></SCRIPT>
	 <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
     <script language="JavaScript">
  
		function cancelForm()
		{
	
		      document.forms[0].target="";     
		           var confirmMsg="You have indicated that you would like to cancel from this page. All changes that have been made will be lost and cannot be recovered. Select OK to continue or Cancel to return to the same page.";
		            if (confirm(confirmMsg)) {
		            	document.forms[0].action ="/nbs/ManagePage.do?method=loadPortPage&initLoad=true";
		                document.forms[0].submit();
		            } else {
		                return false;
		            }    
	
		            return true; 
		}
	        
		function printQueue() {           
            	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
	    }
	    
	    function exportQueue() {
	            window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
	    }

	    function portPage(){
	    	document.forms[0].action ="/nbs/PortPage.do?method=loadPortPage";
            document.forms[0].submit();
		}

	    function viewMappings(nbsConversionPageMgmtUid,fromPageWaTemplateUid,toPageWaTemplateUid,mapName,isMappingLocked,fromPageFormCd){
		    if(nbsConversionPageMgmtUid!=null && nbsConversionPageMgmtUid!="null"){
			    if(isMappingLocked=='true'){
			    	document.forms[0].action ="/nbs/PortPage.do?method=viewPageMapping&nbsConversionPageMgmtUid="+nbsConversionPageMgmtUid+"&fromPageWaTemplateUid="+fromPageWaTemplateUid+"&toPageWaTemplateUid="+toPageWaTemplateUid+"&mapName="+mapName+"&fromPageFormCd="+fromPageFormCd+"&context=ReviewMapping";
				}else{
		    		document.forms[0].action ="/nbs/PortPage.do?method=viewPageMapping&nbsConversionPageMgmtUid="+nbsConversionPageMgmtUid+"&fromPageWaTemplateUid="+fromPageWaTemplateUid+"&toPageWaTemplateUid="+toPageWaTemplateUid+"&mapName="+mapName+"&fromPageFormCd="+fromPageFormCd;
		    	}
	            document.forms[0].submit();
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
			document.forms[0].action ='/nbs/ManagePage.do?method=filterPortPageLibSubmit';
			document.forms[0].submit();
		}

        function clearFilter()
        {

        	document.forms[0].action ='/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true';
        	document.forms[0].submit();										
        }
        
	    function cancelFilter(key) {				  	
			key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));	
			JPageBuilder.getAnswerArray(key1, function(data) {			  			
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

 <body onload="attachIcons();makeMSelects();showCount();displayTooltips();">
    <div id="blockparent"></div>
      <html:form action="/ManagePage.do" styleId="pageBuilderForm">
        <div id="doc3">
                  <!-- Body div -->
	                <div id=bd">
                    	     <!-- Top Nav Bar and top button bar -->
                    		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
             <div align="right">
                 <a href="ManagePage.do?method=list&initLoad=true">Return to Manage Pages:Page Library</a> &nbsp;&nbsp;&nbsp;
            </div>
		             <!-- Top button bar -->
	      			<div class="grayButtonBar" style="text-align: right;">
	      				<input type="button" name="Submit" value="Port Page" onclick="portPage();"/>
	        	   	 	<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
	           	 		<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
	           	 	</div>
				          	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Page Porting Management" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
        				<tr><td>&nbsp;</td></tr>
               			
               			<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
				                    <div class="removefilter" id="removeFilters">
					                  <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
				                    </div>
               			
               			<table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">
                                    <display:table name="portPageList" class="dtTable" pagesize="${pageBuilderForm.attributeMap.queueSize}" id="parent" requestURI="/nbs/ManagePage.do?method=loadManagePagePort&existing=true&initLoad=true" export="true" 
                                    sort="external" excludedParams="stringQueueCollection answerArray(PORTEVENTTYPE) answerArrayText(MAPNAME) answerArrayText(FROMPAGENAME) answerArrayText(TOPAGENAME) answerArray(MAPPINGSTATUS) answerArray(CONDITIONDESCTEXT) answerArray(LASTCHANGEDATE) answerArrayText(SearchText1) answerArrayText(SearchText2) answerArray(STATUS) answerArray(BUSOBJTYPE) answerArray(LASTUPDATED) answerArray(LASTUPDATEDBY) method">
                                        <display:setProperty name="export.csv.filename" value="PortPagePortLibrary.csv"/> 
                       					<display:setProperty name="export.pdf.filename" value="PortPagePortLibrary.pdf"/>
                       					<display:column title="<p style='display:none'>View</p>" style="width:2%;text-align:center;" media="html">
												 &nbsp;&nbsp;&nbsp;<img src="page_white_text.gif" tabIndex="0" class="cursorHand" style= "cursor:pointer"  title="View Map" alt="View Map"
														onclick="viewMappings('<%=((ManagePageDT)parent).getNbsConversionPageMgmtUid()%>','<%=((ManagePageDT)parent).getFromPageWaTemplateUid()%>','<%=((ManagePageDT)parent).getToPageWaTemplateUid()%>','<%=((ManagePageDT)parent).getMapName()%>','<%=((ManagePageDT)parent).isMappingLocked()%>','<%=((ManagePageDT)parent).getFromPageFormCd()%>');"
														 onkeypress="if(isEnterKey(event)) viewMappings('<%=((ManagePageDT)parent).getNbsConversionPageMgmtUid()%>','<%=((ManagePageDT)parent).getFromPageWaTemplateUid()%>','<%=((ManagePageDT)parent).getToPageWaTemplateUid()%>','<%=((ManagePageDT)parent).getMapName()%>','<%=((ManagePageDT)parent).isMappingLocked()%>','<%=((ManagePageDT)parent).getFromPageFormCd()%>');"
														>
										</display:column>
                       					<d:forEach items="${pageBuilderForm.queueCollection}" var="item">
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
										</d:forEach>
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
							<logic:iterate id="filterAndSort" name="pageBuilderForm" property="attributeMap.searchCriteria">
								<li id="${fn:escapeXml(filterAndSort.key)}">${fn:escapeXml(filterAndSort.value)}</li>
							</logic:iterate>
						</ul>
					</div>
    				
    				<div class="grayButtonBar" style="text-align: right;">
            			<input type="button" name="Submit" value="Port Page" onclick="portPage()"/>
	      				<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
	      				<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
	        	   	 	
   		 			</div>
			</div> <!-- id = "bd" -->
			<%@ include file="/jsp/dropDowns_Generic_Queue.jsp" %>
            
            <d:forEach items="${pageBuilderForm.queueCollection}" var="item">
	            <bean:define id="filterType" value="${item.filterType}" />
	            <logic:match name="filterType" value="1">
	                           <input type='hidden' id="${item.dropdownProperty}"
	                           value="<%=request.getAttribute("${item.backendId}") != null ? request.getAttribute("${item.backendId}") : ""%>" />
	            </logic:match>
            </d:forEach>

			<input type='hidden' id='actionMode' value="${fn:escapeXml(ActionMode)} != null ? ${fn:escapeXml(ActionMode)}: " />
		
	</div> <!-- id = "doc3" -->
    </html:form>
    <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
  </body>
</html> 