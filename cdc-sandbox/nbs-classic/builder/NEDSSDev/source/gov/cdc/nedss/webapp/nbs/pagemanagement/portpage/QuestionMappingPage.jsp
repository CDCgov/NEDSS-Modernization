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
     
         
        function submitForm()
        {
			document.forms[0].action ="/nbs/PortPage.do?method=submitMappingQuestions&initLoad=true";
			document.forms[0].submit();
        } 

       
	/* 	function cancelForm()
		{
		        	document.forms[0].target="";     
		            var confirmMsg="You have indicated that you would like to cancel from this page. All changes that have been made will be lost and cannot be recovered. Select OK to continue or Cancel to return to the same page.";
		            if (confirm(confirmMsg)) {
		            	document.forms[0].action ="/nbs/PortPage.do?method=loadPortPage&initLoad=true";
		                document.forms[0].submit();
		            } else {
		                return false;
		            }    
		            return true; 
		} */
	        
		function reconcileSelects(fromList,toList)
		{
			var fromL = getElementByIdOrByName(fromList);
			var toL = getElementByIdOrByName(toList);
			if (toL==null || toL.length == 0) { 
				return; 
			}
			//start from end of from list
			for(i=0; i<toL.length; i++)  {
				for(j=fromL.options.length-1;j>=0;j--) {
				    if (fromL.options[j].value == toL.options[i].value) {
				    	fromL.remove(j);
				    	break;
				    }
				}
			}
		}
	        
		function setSelectedPageOptionValue()
		{
		    var x=document.forms[0].selectedConditionCodes.length;
		    for(var i=0;i<x;i++)
		    {
		        document.forms[0].selectedConditionCodes.options[i].selected=true;
		    }
		}
  
  		function mapQuestion(){

			if(checkBeforeSubmitNextMappig()==true)
	        	 return false;
	         else{
		         
	  			var fromQuestionID= getElementByIdOrByName("fromQuestionID").innerHTML;
	  			var questionMappingReq= getElementByIdOrByName("questionMappingReq").value;
	  			var answerMappingReq= getElementByIdOrByName("answerMappingReq").value;
	  			var toQuestionList= getElementByIdOrByName("toQuestionList").value;
	  			
	  			//alert("fromQuestionID-"+fromQuestionID+"-questionMappingReq-"+questionMappingReq+"-answerMappingReq-"+answerMappingReq+"-toQuestionList-"+toQuestionList);
	  			
	  			
	  			document.forms[0].action ="/nbs/PortPage.do?method=mapAField";
				document.forms[0].submit();
	         }
  		}


		function printQueue() {           
            	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
	    }
	    
	    function exportQueue() {
	            window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
	    }
	    function saveContinue(context)
        {
			document.forms[0].action ="/nbs/PortPage.do?method=submitSaveAndContinue&isQuestionPage=true&context="+context;
			document.forms[0].submit();
        } 
	    //Filter Code starts here//
	    function showCount() {
			$j(".pagebanner b").each(function(i){ 
				$j(this).append(" of ").append($j("#queueCnt").attr("value"));
			});
			$j(".singlepagebanner b").each(function(i){ 
				var cnt = $j("#queueCnt").attr("value");
				if(cnt > 0)
					$j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
			});	
			var totSize=$j("#totCnt").attr("value");
			var mapNeedSize=$j("#mapReqCnt").attr("value");
		    getElementByIdOrByName("totalCnt").innerHTML=mapNeedSize+"   of  "+totSize;
		}
	
	    function makeMSelects() {
			$j("#pStatus").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#fromID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#fromLabel").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#fromDtTyp").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#pMap").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#toID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#toLab").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#toDtTyp").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	    }
	   
	    function attachIcons(){
	    	$j("#parent thead tr th a").each(function(i){
	    		if($j(this).html()=='Status')
	    			$j(this).parent().append($j("#pStatus"));	
	    		if($j(this).html() == 'From ID')
					$j(this).parent().append($j("#fromID"));
	    		if($j(this).html() == 'From Label')
					$j(this).parent().append($j("#fromLabel"));
	    		if($j(this).html() == 'From Data Type')
					$j(this).parent().append($j("#fromDtTyp"));
	    	    if($j(this).html()=='Map')
	    			$j(this).parent().append($j("#pMap"));
	    	    if($j(this).html() == 'To ID')
					$j(this).parent().append($j("#toID"));
	    		if($j(this).html() == 'To Label')
					$j(this).parent().append($j("#toLab"));
	    		if($j(this).html() == 'To Data Type')
					$j(this).parent().append($j("#toDtTyp"));
	    	}); 
	    	 $j("#parent").before($j("#whitebar"));
			 $j("#parent").before($j("#removeFilters"));
	    }

	    function displayFilterAndSortIcons() {		// TO display sorting/filtering icons
			$j(".sortable a").each(function(i) {
			
				var headerNm = $j(this).html();
			      if(headerNm == 'Status') {
					_setAttributes(headerNm, $j(this), $j("#INV111"));
			      } 
			      else if(headerNm == 'From ID') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV001"));
			      } else if(headerNm == 'From Label') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV002"));
			      } else if(headerNm == 'From Data Type') {
					_setAttributes(headerNm, $j(this), $j("#INV222"));
			      } else if(headerNm == 'Map') {
					_setAttributes(headerNm, $j(this), $j("#INV333"));
			      } else if(headerNm == 'To ID') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV003"));			
			      } else if(headerNm == 'To Label') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV004"));
			      }	else if(headerNm == 'To Data Type') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV444"));
			      }	       
			});				
		}
	    
	    function cancelFilter(key) {				  	
			key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));	
			JPortPage.getAnswerArray(key1, function(data) {			  			
				revertOldSelections(key, data);
			});		  	
		}
	    
	    function clearFilter()
		{

			document.forms[0].action ='/nbs/PortPage.do?method=clearFilters&initLoad=true';
			document.forms[0].submit();                                    
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
	    
	    function selectfilterCriteria()
		{
			document.forms[0].action ='/nbs/PortPage.do?method=filterFromQuestionsSubmit';
			document.forms[0].submit();
		}
	    
	    function onKeyUpValidate()
 		{      	  
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
function tabBrowsing() {      
	var i = 0;
	getElementByIdOrByName("questionMappingReq_textbox").tabIndex = ++i;
	if(!getElementByIdOrByName("toQuestionList_textbox").disabled)
		getElementByIdOrByName("toQuestionList_textbox").tabIndex = ++i;
	if(!getElementByIdOrByName("answerMappingReq_textbox").disabled)
		getElementByIdOrByName("answerMappingReq_textbox").tabIndex = ++i;
	//if(!getElementByIdOrByName("repeatingBlockNumber").disabled)
		getElementByIdOrByName("repeatingBlockNumber").tabIndex = i++;
	getElementByIdOrByName("addButton").tabIndex = ++i;
	getElementByIdOrByName("SaveandContinue2").tabIndex = ++i;
	if(!getElementByIdOrByName("Submit2").disabled)
		getElementByIdOrByName("Submit2").tabIndex = ++i;
	getElementByIdOrByName("print2").tabIndex = ++i;
	getElementByIdOrByName("download2").tabIndex = ++i;
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

 <body onload="attachIcons();makeMSelects();disableSubmit();showCount();displayFilterAndSortIcons();autocompTxtValuesForJSP();tabBrowsing();startCountdown();reconcileSelects('availableConditions','selectedConditions')">
    <div id="blockparent"></div>
      <html:form action="/PortPage.do" styleId="portPageForm">
	
        <div id="doc3">
                  <!-- Body div -->
	                <div id="bd">
                    	     <!-- Top Nav Bar and top button bar -->
                    		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                    		
                    		                    		
                   <!-- Return to Mange Page Porting -->
				<div  style="text-align:right; margin-bottom:8px;">
                    <a href="#" onclick="saveContinue('ManagePagePorting');">Return to Manage Page Porting</a>
                </div>
		             <!-- Top button bar -->
    				<div style="text-align: right;"><i> <span class="boldRed">*</span> Indicates a Required Field</i></div>
	      			<div class="grayButtonBar" style="text-align: right;">
	      				<input type="button"  name="SaveandContinue" value="Save and Continue" id=" " onclick="saveContinue('QuestionPage');"/>
	        	   	 	<input type="button" name="Submit" value="Map Answers" onclick="submitForm();"/>
	        	   	 	<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
                  		<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
	           	 	</div>
        	   		<!-- Page Errors -->
				          	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
    				<logic:notEmpty name="portPageForm" property="errorList">
			        <div class="infoBox errors" id="errorMessages">
			            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
			            <ul>
			                <logic:iterate id="errors" name="portPageForm" property="errorList">
			                         <li>${errors}</li>                    
			                </logic:iterate>
			            </ul>
			        </div>    
			 	   </logic:notEmpty>
			 	   					
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Port Page: Map Questions" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
        			  <!-- To Display Map Name -->
        			   <div class="grayButtonBar" style="text-align:left;font-weight:bold;font-size:14px">
        				   <nedss:view name="portPageForm" property="mapName"/>
	           	 	   </div>
        				<div style="text-align:right">
        				     <span ><font color="#CC0000"><b>Questions Remaining To Be Mapped:</b>
        				                  <span id="totalCnt"></span>
        				            </font>
        				     </span>
        				</div>
        				        
        				        <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
				                    <div class="removefilter" id="removeFilters">
					                  <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
				                    </div>
               			<table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">
             
                                    <display:table name="fromFieldList" class="dtTable" pagesize="${portPageForm.attributeMap.queueSize}" sort="external" id="parent" requestURI="/nbs/PortPage.do?method=portPageSubmit&existing=true" export="true" 
                                    excludedParams="answerArrayText(SearchText1) answerArrayText(SearchText2)  answerArrayText(SearchText3) answerArrayText(SearchText4) answerArray(STATUS) answerArray(MAP) answerArray(FRMDATATYPE) answerArray(TODATATYPE) answerArray(PORTCOND) answerArray(PORTDATE) answerArray(EVENTTYPE) answerArray(MAPPINGSTATUS) currentQuestion.blockIdNbr currentQuestion.answerGroupSeqNbr currentQuestion.fieldMappingRequired currentQuestion.codeMappingRequired currentQuestion.fromQuestionId currentQuestion.legacyBlockInd currentQuestion.toQuestionId method">
                                    	<bean:define id="rowNum" value="${parent_rowNum}" />
										<display:column title="<p style='display:none'>View</p>" style="width:2%;text-align:center;" media="html">
												 &nbsp;&nbsp;&nbsp;<img src="page_white_text.gif" tabIndex="0" class="cursorHand" style= "cursor:pointer" title="View" alt="View"
														onclick="viewFromPageFields('<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getWaUiMetadataUid()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromQuestionId()%>',
														&quot;<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromLabel()%>&quot;,
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromDataType()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromCodeSetNm()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getRecordStatusCd()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionMappedCode()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getAnswerGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getToQuestionGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).isQuestionEditFlag()%>');"
														 onkeypress="if(isEnterKey(event)) viewFromPageFields('<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getWaUiMetadataUid()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromQuestionId()%>',
														&quot;<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromLabel()%>&quot;,
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromDataType()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromCodeSetNm()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getRecordStatusCd()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionMappedCode()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getAnswerGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getToQuestionGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).isQuestionEditFlag()%>');">
										</display:column>
                                    	<display:column title="<p style='display:none'>Edit</p>" style="width:2%;text-align:center;" media="html">
                                    			<bean:define id="questionEditFlag" value="${parent.questionEditFlag}" />
                                    			<logic:equal name="questionEditFlag" value="true">
                            				 &nbsp;&nbsp;&nbsp;<img src="page_white_edit.gif" tabIndex="0" class="cursorHand" style= "cursor:pointer" title="Map" alt="Map"
														onclick="mapFromPageFields('<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getWaUiMetadataUid()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromQuestionId()%>',
														&quot;<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromLabel()%>&quot;,
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromDataType()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromCodeSetNm()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getRecordStatusCd()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionMappedCode()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getAnswerGroupSeqNbr()%>');"
														onkeypress="if(isEnterKey(event)) mapFromPageFields('<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getWaUiMetadataUid()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromQuestionId()%>',
														&quot;<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromLabel()%>&quot;,
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromDataType()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getFromCodeSetNm()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getRecordStatusCd()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionMappedCode()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getQuestionGroupSeqNbr()%>',
														'<%=((gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT)parent).getAnswerGroupSeqNbr()%>');"
														>
							</logic:equal>							
														<logic:equal name="rowNum" value="1">
															<bean:define id="WaUiMetadataUid" value="${parent.waUiMetadataUid}" />
															<bean:define id="FromQuestionId" value="${parent.fromQuestionId}" />
															<bean:define id="FromLabel" value="${parent.fromLabel}" />
															<bean:define id="FromDataType" value="${parent.fromDataType}" />
															<bean:define id="FromCodeSetNm" value="${parent.fromCodeSetNm}" />
															<bean:define id="RecordStatusCd" value="${parent.recordStatusCd}" />
															<bean:define id="Mapped" value="${parent.questionMappedCode}" />
															<bean:define id="QuestionGroupSeqNbr" value="${parent.questionGroupSeqNbr}" />
															<bean:define id="AnswerGroupSeqNbr" value="${parent.answerGroupSeqNbr}" />
    														</logic:equal>
										</display:column>
                                        <display:setProperty name="export.csv.filename" value="PortPageMapQuestion.csv"/>
                       					<display:setProperty name="export.pdf.filename" value="PortPageMapQuestion.pdf"/>
                       					<display:column property="statusDesc" title="Status" sortable="true" sortName="getStatusDesc" defaultorder="ascending" style="width:10%;"/>                                        
                                        <display:column property="fromQuestionId" title="From ID" sortable="true" sortName="getFromQuestionId" defaultorder="ascending" style="width:7%;"/>
                                        <display:column property="fromLabel" title="From Label" sortable="true" sortName="getFromLabel" defaultorder="ascending" style="width:20%;"/>
                                        <display:column property="fromDataType" title="From Data Type" sortable="true" sortName="getFromDataType" defaultorder="ascending" style="width:10%;"/>
                                        <display:column property="questionMappedDesc" title="Map" sortable="true" sortName="getQuestionMappedDesc" defaultorder="ascending" style="width:5%;" />
                                        <display:column property="toQuestionId" title="To ID" sortable="true" sortName="getToQuestionId" defaultorder="ascending" style="width:7%;"/>
                                        <display:column property="toLabel" title="To Label" sortable="true" sortName="getToLabel" defaultorder="ascending" style="width:20%;" />
                                        <display:column property="toDataType" title="To Data Type" sortable="true" sortName="getToDataType" style="width:10%;"/>
                                        <display:setProperty name="basic.empty.showtable" value="true" />
                                         
    					
                                    </display:table>
   								</td>
                            </tr>                           
                     	</table>
                     	<br/>
                     	<br/>		
                     	<div id="pageMappingAttributes" style="background:#d1e5f6">
							<br/>
							<table role="presentation" width="90%" style="table-layout:fixed" align="center">
								<tr>
									<td class="InputFieldLabel" width="14%">From ID:</td>
									<td id="fromQuestionID"></td>
									<td class="InputFieldLabel" id="toQuestionListL" width="14%">To ID:</td>
									<td>
										<html:select title="To ID" name="portPageForm" property="currentQuestion.toQuestionId"  styleId="toQuestionList"  style="width:350" onchange="populateToFields(this)">
										<html:option value=""/>
											<logic:iterate id="portPageToFieldsDT"  name="portPageForm" property="toPageQuestions" type="gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT">
												<bean:define id="value" name="portPageToFieldsDT" property="fromQuestionId"/>
												<bean:define id="label" name="portPageToFieldsDT" property="fromLabel"/>
												<bean:define id="key" name="portPageToFieldsDT" property="fromQuestionId"/>
												
												<html:option value="<%=key.toString()%>">
													<bean:write name="value"/> : <bean:write name="label"/>
												</html:option>
										  	</logic:iterate>
	             						</html:select>
									</td>
								</tr>
								<tr>
									<td class="InputFieldLabel">From Label:</td>
									<td id="fromQuestionLabel"></td>
									<td class="InputFieldLabel">To Label:</td>
									<td id="toQuestionLabel"></td>
								</tr>
								<tr>
									<td class="InputFieldLabel">From Data Type:</td>
									<td id="fromDataType"></td>
									<td class="InputFieldLabel">To Data Type:</td>
									<td id="toDataType"></td>
								</tr>
								<tr>
									<td id="fromValueSetLabel" class="InputFieldLabel">From Value Set:</td>
									<td id="fromValueSet"></td>
									<td id="toValueSetLabel" class="InputFieldLabel">To Value Set:</td>
									<td id="toValueSet"></td>
								</tr>
								<tr>
									<td class="InputFieldLabel" id="questionMappingReqLabel">Map Question?:</td>
									<td>
										<html:select title="Map Question?" name="portPageForm" property="currentQuestion.fieldMappingRequired" styleId="questionMappingReq" onchange="disableEnableTOFields(this.value);">
											<html:optionsCollection property="codedValue(YN)" value="key" label="value" />
										</html:select>
									</td>
									<td class="InputFieldLabel" id="answerMappingReqLabel">Map Answers?:</td>
									<td>
										<html:select title="Map Answers?" name="portPageForm" property="currentQuestion.codeMappingRequired" styleId="answerMappingReq">
											<html:optionsCollection property="codedValue(YN)" value="key" label="value" />
										</html:select>
									</td>
								</tr>
								<tr>
									<td id="legacyBlockIDLable" class="InputFieldLabel"></td>
									<td id="legacyBlockID"></td>
									<td id="RepeatingBlockNumberLabel" class="InputFieldLabel">Repeating Block Number:</td>
									<td>
										<html:text title="Repeating Block Number" name="portPageForm" styleId="repeatingBlockNumber" property="currentQuestion.blockIdNbr" disabled="true" ></html:text>
									</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td id="RepeatingBlockQuestionLabel" class="InputFieldLabel"></td>
									<td id="RepeatingBlockQuestionId"></td>
								</tr>
								<tr>
									<td class="InputFieldLabel"></td>
									<td></td>
									<td class="InputFieldLabel" style="text-align:right"></td>
									<td style="text-align:right">
										<div id="addBtn">
											<input type="button" valign="right" name="Submit" id="addButton" value=" Add " onclick="mapQuestion();"/>
										</div>
										<div id="editBtn">
											<input type="button" valign="right" name="Submit" id="editButton" value=" Edit " onclick="editQuestion();"/>
										</div>
									</td>
								</tr>
								</table>
								<br/>
						</div>
                    	
				</nedss:container>
					<html:hidden name="portPageForm" property="currentQuestion.fromQuestionId" styleId="fromQuestionToMap"/>
					
					<div style="display: none;visibility: none;" id="errorMessages">
						<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
						<ul>
							<logic:iterate id="errors" name="portPageForm" property="attributeMap.searchCriteria">
								<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
							</logic:iterate>
						</ul>
					</div> 

    				<div class="grayButtonBar" style="text-align: right;">
            				
            				<input type="button" align="right" name="SaveandContinue2" value="Save and Continue" onclick="saveContinue('QuestionPage');"/> 
            				<input type="button" align="right" name="Submit2" value="Map Answers" onclick="submitForm();"/>
            				<input type="button"  value="Print" id="print2" onclick="printQueue();"/> 
                  			<input type="button"  value="Download" id="download2" onclick="exportQueue();"/> 
                  			
   		 		</div>
			</div> <!-- id = "bd" -->
			<html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>
           <html:hidden styleId="SearchText1" property="searchCriteriaArrayMap.SearchText1_FILTER_TEXT"/>
           <html:hidden styleId="SearchText2" property="searchCriteriaArrayMap.SearchText2_FILTER_TEXT"/>
           <html:hidden styleId="SearchText3" property="searchCriteriaArrayMap.SearchText3_FILTER_TEXT"/>
           <html:hidden styleId="SearchText4" property="searchCriteriaArrayMap.SearchText4_FILTER_TEXT"/>
      
      		<html:hidden name="portPageForm" property="currentQuestion.codeMappingRequired" styleId="codeMappingRequired"/> 
           <html:hidden name="portPageForm" property="currentQuestion.legacyBlockInd" styleId="MapAnotherInstance"/>
           <html:hidden name="portPageForm" property="currentQuestion.answerGroupSeqNbr" styleId="answerGroupSeqNbr"/>
           <input type="hidden" id="unmappedToQuestion" name="unmappedToQuestion" value=""/>
           <%@ include file="QuestionMappingPageDropDown.jsp" %>
	</div> <!-- id = "doc3" -->
    </html:form>
    <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
    <input type="hidden" id="totCnt" value="${fn:escapeXml(totalCount)}"/>
    <input type="hidden" id="mapReqCnt" value="${fn:escapeXml(mapReqCount)}"/>
    
    <input type="hidden" id="fromQuestionGroupSeqNbr" value=""/>
    <input type="hidden" id="toQuestionGroupSeqNbr" value=""/>
    
    <logic:equal name="showNextQueToMapp" value="true">
	    <script type='text/javascript'>
		mapFromPageFields('${WaUiMetadataUid}',
				'${FromQuestionId}',
				'${FromLabel}',
				'${FromDataType}',
				'${FromCodeSetNm}',
				'${RecordStatusCd}',
				'${Mapped}',
				'${QuestionGroupSeqNbr}',
				'${AnswerGroupSeqNbr}');
	    </script>
    </logic:equal>
    <logic:notEqual name="showNextQueToMapp" value="true">
	<script type='text/javascript'>
		getElementByIdOrByName("pageMappingAttributes").style.display = "none";
	</script>
    </logic:notEqual>
      </body>
</html>