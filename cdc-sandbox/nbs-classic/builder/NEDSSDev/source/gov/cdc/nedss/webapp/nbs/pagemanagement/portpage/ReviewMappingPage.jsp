<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil"%>
<html lang="en">
    <head>
     <title>NBS: Manage Pages</title>
     <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
     <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
     <script src="/nbs/dwr/interface/JPortPage.js" type="text/javascript"></script>
     <%@ include file="../../jsp/resources.jsp" %>     
     <script type="text/javascript" src="pagemanagementSpecific.js"></script>
     <script type="text/javascript" src="PortPageSpecific.js"></script>
     <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
	 <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
     <script language="JavaScript">
  
/* 		function cancelForm()
		{
	
		        	document.forms[0].target="";     
		            var confirmMsg="You have indicated that you would like to cancel from this page. All changes that have been made will be lost and cannot be recovered. Select OK to continue or Cancel to return to the same page.";
		            if (confirm(confirmMsg)) {
		            	document.forms[0].action ="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true";
		                document.forms[0].submit();
		            } else {
		                return false;
		            }    
	
		            return true; 
		} */
	       
	    function returnToQuestions()
	     {
	  	   
	  	   document.forms[0].action="/nbs/PortPage.do?method=portPageSubmit&context=ReturnToQuestion";
	  	   document.forms[0].submit();
	     }
		
		function returnToAnswers()
	     {
	  	   
	  	   document.forms[0].action="/nbs/PortPage.do?method=submitMappingQuestions&context=ReturnToAnswer";
	  	   document.forms[0].submit();
	     }
		
		function printQueue() {           
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
	    }
	    
	    function exportQueue() {
	    	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
	    }

	    function exportMap(){
	    	document.forms[0].action="/nbs/PortPage.do?method=exportMapping";
		  	document.forms[0].submit();
		}
	    
	    function attachIcons() { 
	           $j("#parent thead tr th a").each(function(i) {  
				   if($j(this).html() == 'From ID'){         
	                  $j(this).parent().append($j("#fromID"));
	               }
	               if($j(this).html() == 'From Label'){         
	                  $j(this).parent().append($j("#fromLab"));
	               }
	               if($j(this).html() == 'From Data Type'){                       
	            	  $j(this).parent().append($j("#fromDtTyp"));
	               }      
	               if($j(this).html() == 'Map Question?'){
	                  $j(this).parent().append($j("#mapQue"));  
	               }
	               if($j(this).html() == 'To ID'){
	                  $j(this).parent().append($j("#toID"));  
	               }
	               if($j(this).html() == 'To Label'){
	                   $j(this).parent().append($j("#toLab"));  
	               }
	               if($j(this).html() == 'To Data Type'){
	                   $j(this).parent().append($j("#toDtTyp"));  
	               }
	               if($j(this).html() == 'From Answer'){
	                   $j(this).parent().append($j("#fromAnswer"));  
	               } 
	               if($j(this).html() == 'Map Answer?'){
	                   $j(this).parent().append($j("#mapAns"));  
	               }
	               if($j(this).html() == 'To Answer'){
	                   $j(this).parent().append($j("#toAnswer"));  
	               } 
	               }); 
	            $j("#parent").before($j("#whitebar"));
	            $j("#parent").before($j("#removeFilters"));
	        }
	    
	    function makeMSelects() {
           $j("#fromID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#fromLab").text({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#fromDtTyp").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#mapQue").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#toID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#toLab").text({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#toDtTyp").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#fromAnswer").text({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#mapAns").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
           $j("#toAnswer").text({actionMode: '${fn:escapeXml(ActionMode)}'});
       }
	    
	 // TO display sorting/filtering icons
        function displayFilterAndSortIcons() {
			$j(".sortable a").each(function(i) {
				var headerNm = $j(this).html();
			      if(headerNm == 'From ID') {
					_setAttributes(headerNm, $j(this), $j("#INV001"));
			      } 
			      else if(headerNm == 'From Label') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV002"));
			      } else if(headerNm == 'From Data Type') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV111"));
			      } else if(headerNm == 'Map Question?') {                     
					_setAttributes(headerNm, $j(this), $j("#INV222"));
			      } else if(headerNm == 'To ID') {
					_setAttributes(headerNm, $j(this), $j("#INV003"));
			      } else if(headerNm == 'To Label') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV004"));			
			      } else if(headerNm == 'To Data Type') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV333"));
			      } else if(headerNm == 'From Answer') {
					_setAttributes(headerNm, $j(this), $j("#INV005"));
			      } else if(headerNm == 'Map Answer?') {                   
			    	  _setAttributes(headerNm, $j(this), $j("#INV444"));			
			      } else if(headerNm == 'To Answer') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV006"));
			      }
			      });				
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
			document.forms[0].action ='/nbs/PortPage.do?method=filterReviewPageLibSubmit';
			document.forms[0].submit();
		}
	    
	    function cancelFilter(key) {				  	
			key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));	
			JPortPage.getAnswerArray(key1, function(data) {			  			
				revertOldSelections(key, data);
			});		  	
		}
	    function revertOldSelections(name, value) 
		{  
			if(value == null) {
				$j("input[@name="+name+"][type='checkbox']").attr('checked', true);
				$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
				return;
			}
			//step1: clear all selections
			$j("input[@name="+name+"][type='checkbox']").attr('checked', false);
			$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', false);

			//step2: check previous selections from the form
		   	for(var i=0; i<value.length; i++) {
				   $j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr('checked', true);
			   }
			//step3: if all are checked, automatically check the 'select all' checkbox
			if(value.length == $j("input[@name="+name+"][type='checkbox']").parent().length)
				$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
		}
	  
	   $j(document).ready(function() {
		var lockMapping= $j("#lockMapping").attr("value");
		if(lockMapping =='true'){
			getElementByIdOrByName("mapQuestion").disabled=true;
			getElementByIdOrByName("mapAnswer").disabled=true;
			getElementByIdOrByName("mapQuestion1").disabled=true;
			getElementByIdOrByName("mapAnswer1").disabled=true;
		}else{
			getElementByIdOrByName("mapQuestion").disabled=false;
			getElementByIdOrByName("mapAnswer").disabled=false;
			getElementByIdOrByName("mapQuestion1").disabled=false;
			getElementByIdOrByName("mapAnswer1").disabled=false;
		}
	    });
			
	    function clearFilter()
		{
			document.forms[0].action ='/nbs/PortPage.do?method=clearReviewPageFilters&initLoad=true';
			document.forms[0].submit();                                    
		}
	    
	    function onKeyUpValidate(){      	  
        	if(getElementByIdOrByName("SearchText1").value != ""){
         		getElementByIdOrByName("b1SearchText1").disabled=false;
         		getElementByIdOrByName("b2SearchText1").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText1").value == ""){
         		getElementByIdOrByName("b1SearchText1").disabled=true;
         		getElementByIdOrByName("b2SearchText1").disabled=true;
         	   }
        	if(getElementByIdOrByName("SearchText2").value != ""){
         		getElementByIdOrByName("b1SearchText2").disabled=false;
         		getElementByIdOrByName("b2SearchText2").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText2").value == ""){
         		getElementByIdOrByName("b1SearchText2").disabled=true;
         		getElementByIdOrByName("b2SearchText2").disabled=true;
         	   } 
        	if(getElementByIdOrByName("SearchText3").value != ""){
         		getElementByIdOrByName("b1SearchText3").disabled=false;
         		getElementByIdOrByName("b2SearchText3").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText3").value == ""){
         		getElementByIdOrByName("b1SearchText3").disabled=true;
         		getElementByIdOrByName("b2SearchText3").disabled=true;
         	   }
        	if(getElementByIdOrByName("SearchText4").value != ""){
         		getElementByIdOrByName("b1SearchText4").disabled=false;
         		getElementByIdOrByName("b2SearchText4").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText4").value == ""){
         		getElementByIdOrByName("b1SearchText4").disabled=true;
         		getElementByIdOrByName("b2SearchText4").disabled=true;
         	   }
        	if(getElementByIdOrByName("SearchText5").value != ""){
         		getElementByIdOrByName("b1SearchText5").disabled=false;
         		getElementByIdOrByName("b2SearchText5").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText5").value == ""){
         		getElementByIdOrByName("b1SearchText5").disabled=true;
         		getElementByIdOrByName("b2SearchText5").disabled=true;
         	   } 
        	if(getElementByIdOrByName("SearchText6").value != ""){
         		getElementByIdOrByName("b1SearchText6").disabled=false;
         		getElementByIdOrByName("b2SearchText6").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText6").value == ""){
         		getElementByIdOrByName("b1SearchText6").disabled=true;
         		getElementByIdOrByName("b2SearchText6").disabled=true;
         	   }
 		}
	    
        function _setAttributes(headerNm, link, colId) {
			
			var imgObj = link.parent().find("img");
			var toolTip = "";	
			var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
			var orderCls = "SortAsc.gif";
			var altOrderCls = "Sort Ascending";
			var sortOrderCls = "FilterAndSortAsc.gif";
			var altSortOrderCls = "Filter Applied with Sort Ascending";
			
			if(sortSt != null && sortSt.indexOf("descending") != -1) {
				orderCls = "SortDesc.gif";
				altOrderCls = "Sort Descending";
				
				sortOrderCls = "FilterAndSortDesc.gif";
				altSortOrderCls = "Filter Applied with Sort Descending";
				
			}  	
			var filterCls = "Filter.gif";
			var altFilterCls = "Filter Applied";
		  	
		  	toolTip = colId.html() == null ? "" : colId.html();
		  	if(toolTip.length > 0) {
				link.attr("title", toolTip);
				imgObj.attr("src", filterCls);
				imgObj.attr("alt", altFilterCls);

				if(sortSt != null && sortSt.indexOf(headerNm) != -1 ){
					imgObj.attr("src", sortOrderCls);		
					imgObj.attr("alt", altSortOrderCls);		
		  			}
		  		} else {
				if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
					imgObj.attr("src", orderCls);	
					imgObj.attr("alt", altOrderCls);
					
				}			
		  	}
		}
		
		function _handlePatient(headerNm, link, colId) {
			var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
			var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
			var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
			if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
				if(sortSt != null && sortSt.indexOf("descending") != -1) {
				link.after(htmlDesc);
				} else {
				link.after(htmlAsc);
				}
			}
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

 <body onload="attachIcons();makeMSelects();showCount();displayFilterAndSortIcons();">
    <div id="blockparent"></div>
      <html:form action="/PortPage.do" styleId="reviewPageForm">
        <div id="doc3">
                  <!-- Body div -->
	                <div id="bd">
                    	     <!-- Top Nav Bar and top button bar -->
                    		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
		            <!-- Return to Manage Page Porting -->
				<div  style="text-align:right; margin-bottom:8px;">
                    <a href="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true">Return to Manage Page Porting</a>
                </div>
		             <!-- Top button bar -->
		            <div class="grayButtonBar">
						<table role="presentation" width="100%">
							<tr>
								<td align="left">
									<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
										<input type="button"  value="Export Map" id=" " onclick="exportMap();"/> 
									</logic:equal>
								</td>
								<td align="right">
								    <input type="button" name="Map Question" id="mapQuestion" value="Map Questions" onclick="returnToQuestions();"/>
								    <input type="button" name="Map Answer" id="mapAnswer" value="Map Answers" onclick="returnToAnswers();"/>
									<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
									<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
								</td>
							</tr>
						</table>
					</div>   
				          	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Port Page: Review Mapping" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
        				<div class="infoBox messages" style="text-align: left; width:98.75%; ">
        					<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
        						The following table shows the final mapping for ${FromPageName} to ${ToPageName}. There are currently ${FromPageTotalAssociatedConditions} condition(s) using the ${FromPageName} page. <a href='/nbs/PortPage.do?method=loadPortCondition&initLoad=true'> <u>Click here</u></a> when you are finished reviewing the mapping and ready to start condition conversion. You should NOT perform condition conversion in your production environment while your user community is accessing the system.
        					</logic:equal>
        					<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_LEGACY%>">
								The following table shows the final mapping for Legacy Vaccination page to Vaccination page mapping. <a href='/nbs/PortPage.do?method=loadPortCondition&initLoad=true'> <u>Click here</u></a> when you are finished reviewing the mapping and ready to start Legacy data conversion. You should NOT perform Legacy data conversion in your production environment while your user community is accessing the system.
							</logic:equal>
        				</div>
        				<!-- To Display Map Name -->
        			   <div class="grayButtonBar" style="text-align:left;font-weight:bold;font-size:14px">
        				   <nedss:view name="portPageForm" property="mapName"/>
	           	 	   </div>
	           	 	   
	           	 	   <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
				                    <div class="removefilter" id="removeFilters">
					                  <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
				                    </div>
               			<table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">
                                    <display:table name="questionAnswerListToReview" class="dtTable" pagesize="${portPageForm.attributeMap.queueSize}" id="parent" requestURI="/nbs/PortPage.do?method=submitAnswerMapping&existing=true&initLoad=true" export="true"
                                    sort="external" excludedParams="answerArray(FRMDATATYPE) answerArray(MAPQUESTION) answerArray(TODATATYPE) answerArray(MAPANSWER) answerArrayText(SearchText1) answerArrayText(SearchText2) answerArrayText(SearchText3) answerArrayText(SearchText4) answerArrayText(SearchText5) answerArrayText(SearchText6) answerArray(PORTCOND) answerArray(PORTDATE) answerArray(EVENTTYPE) answerArray(MAPPINGSTATUS) method" >
                                        <display:setProperty name="export.csv.filename" value="PortPageReview.csv"/>
                       					<display:setProperty name="export.pdf.filename" value="PortPageReview.pdf"/>
                       					
                       					<display:column property="statusDesc" title="Status" sortable="true" media="csv pdf" style="width:10%;"/>                                      
                                        <display:column property="fromQuestionId" title="From ID" sortable="true" sortName="getFromQuestionId" defaultorder="ascending"  style="width:7%;"/>
                                        <display:column property="fromLabel" title="From Label" sortable="true" sortName="getFromLabel" defaultorder="ascending"  style="width:15%;"/>
                                        <display:column property="fromDbLocation" title="From DB Location" sortable="true" media="csv pdf"  style="width:10%;"/>
                                        <display:column property="fromDataType" title="From Data Type" sortable="true" media="html" sortName="getFromDataType" defaultorder="ascending"  style="width:9.5%;"/>
                                        <display:column property="fromDataType" title="From Data Type" sortable="true" media="csv pdf" style="width:7%;"/>
                                        <display:column property="fromCodeSetNm" title="From  Value Set" sortable="true" media="csv pdf" style="width:10%;" />
                                        <display:column property="questionMappedDesc" title="Map Question?" sortable="true" media="html" sortName="getQuestionMappedDesc" defaultorder="ascending" style="width:9%;"/>
                                        <display:column property="questionMappedDesc" title="Question Translation Required" sortable="true" media="csv pdf"  style="width:10%;"/>
                                        <display:column property="toQuestionId" title="To ID" sortable="true" sortName="getToQuestionId" defaultorder="ascending" style="width:7%;"/>
										<display:column property="toLabel" title="To Label" sortable="true" sortName="getToLabel" defaultorder="ascending" style="width:15%;"/>
										<display:column property="toDbLocation" title="To DB Location" sortable="true" media="csv pdf" style="width:10%;" />
                                        <display:column property="toDataType" title="To Data Type" sortable="true" media="html" sortName="getToDataType" defaultorder="ascending" style="width:8%;"/>
                                        <display:column property="toDataType" title="To Data Type" sortable="true" media="csv pdf" defaultorder="ascending" style="width:7%;"/>
                                        <display:column property="toCodeSetNm" title="To  Value Set" sortable="true" media="csv pdf"  style="width:10%;"/>
                                        
                                        <bean:define id="fromCodeDesc" value="${parent.fromCodeDesc }" />
                                        <display:column title="From Answer" sortable="true" sortName="getFromCode" defaultorder="ascending"  style="width:8%;">
	                                        ${parent.fromCode}<logic:notEmpty name="fromCodeDesc"> : ${parent.fromCodeDesc}</logic:notEmpty>
										</display:column>
                                        
                                        <display:column property="answerMappedDesc" title="Map Answer?" sortable="true" media="html" sortName="getAnswerMappedDesc" defaultorder="ascending"  style="width:8%;"/>
                                        <display:column property="answerMappedDesc" title="Answer Translation Required" sortable="true" media="csv pdf"  style="width:10%;"/>
                                        
                                        <bean:define id="toCodeDesc" value="${parent.toCodeDesc }"/>
                                        <display:column title="To Answer" sortable="true" sortName="getToCode" defaultorder="ascending" style="width:7%;">
	                                        ${parent.toCode} <logic:notEmpty name="toCodeDesc"> : ${parent.toCodeDesc}
											</logic:notEmpty>
										</display:column>
                                         <display:column property="answerGroupSeqNbr" title="Answer Group Sequence Number" sortable="true" media="csv pdf"  style="width:10%;"/>
                                        <display:setProperty name="basic.empty.showtable" value="true" /> 

                                    </display:table>
   								</td>
                            </tr>                           
                     	</table>
                     	<br/>
                     	<br/>		
                     	                    	
				</nedss:container>
				<div style="display: none;visibility: none;" id="errorMessages">
						<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
						<ul>
							<logic:iterate id="errors" name="portPageForm" property="attributeMap.searchCriteria">
								<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
							</logic:iterate>
						</ul>
					</div>
					
				<div class="grayButtonBar">
					<table role="presentation" width="100%">
						<tr>
							<td align="left">
								<logic:equal name="portPageForm" property="mappingType" value="<%=PortPageUtil.MAPPING_PAGEBUILDER%>">
									<input type="button"  value="Export Map" id=" " onclick="exportMap();"/> 
								</logic:equal>
							</td>
							<td align="right">
							    <input type="button" name="Map Question" id="mapQuestion1" value="Map Questions" onclick="returnToQuestions();"/>
							    <input type="button" name="Map Answer" id="mapAnswer1" value="Map Answers" onclick="returnToAnswers();"/>
								<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
								<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
							</td>
						</tr>
					</table>
				</div>   
			</div> <!-- id = "bd" -->
			<jsp:include page="ReviewPageDropDown.jsp" />
			<html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>
			<html:hidden styleId="SearchText1" property="searchCriteriaArrayMap.SearchText1_FILTER_TEXT"/>
            <html:hidden styleId="SearchText2" property="searchCriteriaArrayMap.SearchText2_FILTER_TEXT"/>
            <html:hidden styleId="SearchText3" property="searchCriteriaArrayMap.SearchText3_FILTER_TEXT"/>
            <html:hidden styleId="SearchText4" property="searchCriteriaArrayMap.SearchText4_FILTER_TEXT"/>
            <html:hidden styleId="SearchText5" property="searchCriteriaArrayMap.SearchText5_FILTER_TEXT"/>
            <html:hidden styleId="SearchText6" property="searchCriteriaArrayMap.SearchText6_FILTER_TEXT"/>
	</div> <!-- id = "doc3" -->
    </html:form>
    <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
    <html:hidden name="portPageForm" property="lockMapping" styleId="lockMapping"/>
  </body>
</html>