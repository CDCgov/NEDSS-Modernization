<%@page import="gov.cdc.nedss.page.ejb.portproxyejb.dt.AnswerMappingDT"%>
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
     <script type="text/javascript" src="script.js"></script>
     <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
	 <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
	 <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
     <script language="JavaScript">
         
     function returnToQuestions()
     {
  	   
  	   document.forms[0].action="/nbs/PortPage.do?method=portPageSubmit&context=ReturnToQuestion";
  	   document.forms[0].submit();
     }
     
     function submitForm()
       {
           		/* var confirmMsg="Are you sure you want to do port data for current page to seed page.";
           		if (confirm(confirmMsg))
		        { */
		           	//setSelectedPageOptionValue();
					document.forms[0].action ="/nbs/PortPage.do?method=submitAnswerMapping&initLoad=true";
       				document.forms[0].submit();
		        /* }
		        else {
		        	return false;
           	    } */	
        } 
     function saveContinue()
     {
			document.forms[0].action ="/nbs/PortPage.do?method=submitSaveAndContinue&isQuestionPage=false";
			document.forms[0].submit();
     } 
		/* function cancelForm()
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
		        document.forms[0].selectedConditionCodes.options[i].selected=true;xx
		    }
		}

		function populateToFields(selQuestion){
			//alert("Val--"+selQuestion.value);
		}
  
  		function mapAnswer(){
  			if(checkBeforeSubmitNextAnswerMapping()==true)
	        	return false;
	        else{
		  		document.forms[0].action ="/nbs/PortPage.do?method=mapAnAnswer";
				document.forms[0].submit();
	        }
  		}
  		//Filter Code Starts here//
  			    
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
			$j("#ansStatus").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#fromID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#fromLabel").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#fromCd").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#ansMap").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#toCd").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	    }
	   
	    function attachIcons(){
	    	$j("#parent thead tr th a").each(function(i){
	    		if($j(this).html()=='Status')
	    			$j(this).parent().append($j("#ansStatus"));	
	    		if($j(this).html() == 'From ID')
					$j(this).parent().append($j("#fromID"));
	    		if($j(this).html() == 'From Label')
					$j(this).parent().append($j("#fromLabel"));
	    		if($j(this).html() == 'From Answer')
					$j(this).parent().append($j("#fromCd"));
	    	    if($j(this).html()=='Map')
	    			$j(this).parent().append($j("#ansMap"));
	    	    if($j(this).html() == 'To Answer')
					$j(this).parent().append($j("#toCd"));
	    		
	    	}); 
	    	 $j("#parent").before($j("#whitebar"));
			 $j("#parent").before($j("#removeFilters"));
	    }

	    function displayFilterAndSortIcons() {		// TO display sorting/filtering icons
			$j(".sortable a").each(function(i) {
			
				var headerNm = $j(this).html();
			      if(headerNm == 'Status') {
					_setAttributes(headerNm, $j(this), $j("#INV111"));
			      }else if(headerNm == 'From ID') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV001"));
			      } else if(headerNm == 'From Label') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV002"));
			      } else if(headerNm == 'From Answer') {
					_setAttributes(headerNm, $j(this), $j("#INV003"));
			      } else if(headerNm == 'Map') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV222"));			
			      } else if(headerNm == 'To Answer') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV005"));
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

			document.forms[0].action ='/nbs/PortPage.do?&method=clearAnsFilters&initLoad=true';
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
			document.forms[0].action ='/nbs/PortPage.do?method=filterAnswerLibSubmit';
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
        	if(getElementByIdOrByName("SearchText5").value != ""){
         		getElementByIdOrByName("b1SearchText5").disabled=false;
         		getElementByIdOrByName("b2SearchText5").disabled=false;
         	   }else if(getElementByIdOrByName("SearchText5").value == ""){
         		getElementByIdOrByName("b1SearchText5").disabled=true;
         		getElementByIdOrByName("b2SearchText5").disabled=true;
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
	getElementByIdOrByName("answerMappingReq_textbox").tabIndex = ++i;
	if(!getElementByIdOrByName("toCodeList_textbox").disabled)
		getElementByIdOrByName("toCodeList_textbox").tabIndex = ++i;
	if(!getElementByIdOrByName("toAnswerId").disabled)
		getElementByIdOrByName("toAnswerId").tabIndex = ++i;
	
	getElementByIdOrByName("addButton").tabIndex = ++i;
	getElementByIdOrByName("SaveandContinue2").tabIndex = ++i;
	
	getElementByIdOrByName("Map Question2").tabIndex = ++i;
	getElementByIdOrByName("Submit2").tabIndex = ++i;
	getElementByIdOrByName("print2").tabIndex = ++i;
	getElementByIdOrByName("download2").tabIndex = ++i;
}

		function printQueue() {           
			window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
		}
		
		function exportQueue() {
		    window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
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

 <body onload="attachIcons();makeMSelects();disableSubmit();displayFilterAndSortIcons();tabBrowsing();showCount();autocompTxtValuesForJSP();">
    <div id="blockparent"></div>
      <html:form action="/PortPage.do" styleId="portPageForm">
        <div id="doc3">
                  <!-- Body div -->
	                <div id="bd">
                    	     <!-- Top Nav Bar and top button bar -->
                    		<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                    		
                <!-- Return to Mange Page Porting -->
				<div  style="text-align:right; margin-bottom:8px;">
                    <a href="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true" onclick="saveContinue();" >Return to Manage Page Porting</a>
                </div>
		             <!-- Top button bar -->
    				<div style="text-align: right;"><i> <span class="boldRed">*</span> Indicates a Required Field</i></div>
	      			<div class="grayButtonBar" style="text-align: right;">
	      			    <input type="button" name="SaveandContinue" value="Save and Continue" onclick="saveContinue();"/>
	      			    <input type="button" name="Map Question" value="Map Questions" onclick="saveContinue();returnToQuestions();"/>
	      			     
	        	   	 	<input type="button" name="Submit" value="Review Mapping" onclick="submitForm();"/>
	        	   	 	<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
                  		<input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
	           	 	</div>      	   		
    				<%@ include file="../../../jsp/feedbackMessagesBarGen.jsp" %>
        			<logic:notEmpty name="portPageForm" property="errorList">
				        <div class="infoBox errors" id="errorMessages">
				            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				            <ul>
				                <logic:iterate id="errors" name="portPageForm" property="errorList">
				                         <li>${fn:escapeXml(errors)}</li>                    
				                </logic:iterate>
				            </ul>
				        </div>    
			 	   </logic:notEmpty>
    				<!-- SECTION : Add Page --> 
    				<nedss:container id="section1" name="Port Page: Map Answers" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
    				<!-- To Display Map Name -->
        			   <div class="grayButtonBar" style="text-align:left;font-weight:bold;font-size:14px">
        				   <nedss:view name="portPageForm" property="mapName"/>
	           	 	   </div>
        						<div style="text-align:right">
        				<span ><font color="#CC0000"><b>Answers Remaining To Be Mapped:</b>
        				<span id="totalCnt"></span>
        				</font></span></div>
        				               <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
				                    <div class="removefilter" id="removeFilters">
					                  <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
				                    </div>
               			<table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">
                                    <display:table name="fromAnswerList" class="dtTable" pagesize="${portPageForm.attributeMap.queueSize}" sort="external" id="parent" requestURI="/nbs/PortPage.do?method=submitMappingQuestions&existing=true" export="true" 
                                    excludedParams="answerArrayText(SearchText1) answerArrayText(SearchText2)  answerArrayText(SearchText3) answerArrayText(SearchText5) answerArray(STATUS) answerArray(MAP)  method">
                                    	<bean:define id="rowNum" value="${parent_rowNum}" />
                                    	<display:column title="<p style='display:none'>View</p>" style="width:2%;text-align:center;" media="html">
											&nbsp;&nbsp;&nbsp;<img src="page_white_text.gif" tabIndex="0" class="cursorHand" style= "cursor:pointer"  title="View" alt="View"
														onclick="viewAnswer('<%=((AnswerMappingDT)parent).getQuestionIdentifier()%>',
														&quot;<%=((AnswerMappingDT)parent).getQuestionLabel()%>&quot;,
														'<%=((AnswerMappingDT)parent).getCodeSetNm()%>',
														&quot;<%=((AnswerMappingDT)parent).getCode()%>&quot;,
														&quot;<%=((AnswerMappingDT)parent).getCodeDescTxt()%>&quot;,
														'<%=((AnswerMappingDT)parent).getMapped()%>');"
														onkeypress="if(isEnterKey(event)) viewAnswer('<%=((AnswerMappingDT)parent).getQuestionIdentifier()%>',
														&quot;<%=((AnswerMappingDT)parent).getQuestionLabel()%>&quot;,
														'<%=((AnswerMappingDT)parent).getCodeSetNm()%>',
														&quot;<%=((AnswerMappingDT)parent).getCode()%>&quot;,
														&quot;<%=((AnswerMappingDT)parent).getCodeDescTxt()%>&quot;,
														'<%=((AnswerMappingDT)parent).getMapped()%>');"
														>
										</display:column>
                                    	<display:column title="<p style='display:none'>Edit</p>" style="width:2%;text-align:center;" media="html">
                            				 &nbsp;&nbsp;&nbsp;<img src="page_white_edit.gif" tabIndex="0" class="cursorHand" style= "cursor:pointer"  title="Map" alt="Map"
														onclick="mapAnswers('<%=((AnswerMappingDT)parent).getQuestionIdentifier()%>',
														&quot;<%=((AnswerMappingDT)parent).getQuestionLabel()%>&quot;,
														'<%=((AnswerMappingDT)parent).getCodeSetNm()%>',
														&quot;<%=((AnswerMappingDT)parent).getCode()%>&quot;,
														&quot;<%=((AnswerMappingDT)parent).getCodeDescTxt()%>&quot;,
														'<%=((AnswerMappingDT)parent).getMapped()%>');"
														onkeypress="if(isEnterKey(event)) mapAnswers('<%=((AnswerMappingDT)parent).getQuestionIdentifier()%>',
														&quot;<%=((AnswerMappingDT)parent).getQuestionLabel()%>&quot;,
														'<%=((AnswerMappingDT)parent).getCodeSetNm()%>',
														&quot;<%=((AnswerMappingDT)parent).getCode()%>&quot;,
														&quot;<%=((AnswerMappingDT)parent).getCodeDescTxt()%>&quot;,
														'<%=((AnswerMappingDT)parent).getMapped()%>');">
														
														<logic:equal name="rowNum" value="1">
															<bean:define id="QuestionIdentifier" value="${parent.questionIdentifier}" />
															<bean:define id="QuestionLabel" value="${parent.questionLabel}" />
															<bean:define id="CodeSetNm" value="${parent.codeSetNm}" />
															<bean:define id="Code" value="${parent.code}" />
															<bean:define id="CodeDescTxt" value="${parent.codeDescTxt}" />
															<bean:define id="Mapped" value="${parent.mapped}" />
    													</logic:equal>
										</display:column>
                                        <display:setProperty name="export.csv.filename" value="PortPageMapAnswers.csv"/>
                       					<display:setProperty name="export.pdf.filename" value="PortPageMapAnswers.pdf"/>
                       					<display:column property="status" title="Status" sortable="true" sortName="getStatus" style="width:10%;"/>
                       					<display:column property="questionIdentifier" title="From ID" sortable="true" sortName="getQuestionIdentifier" style="width:7%;" />                                        
                                        <display:column property="questionLabel" title="From Label" sortable="true" sortName="getQuestionLabel" style="width:20%;"/>
                                        <bean:define id="fromCodeDesc" value="${parent.codeDescTxt}" />
                                        <display:column title="From Answer" sortable="true"  sortName="getCode" style="width:20%;">  
											${parent.code}
											<logic:notEmpty name="fromCodeDesc">
												 : ${parent.codeDescTxt}
											</logic:notEmpty>
										</display:column>
                                        <display:column property="mappedDesc" title="Map" sortable="true" sortName="getMapped" style="width:5%;"/>
                                        <bean:define id="toCodeDesc" value="${parent.toCodeDescTxt}" />
										<display:column title="To Answer" sortable="true"  sortName="getToCode" style="width:20%;">  
											${parent.toCode}
											<logic:notEmpty name="toCodeDesc">
												: ${parent.toCodeDescTxt}
											</logic:notEmpty>
										</display:column>
                                        
                                        <display:setProperty name="basic.empty.showtable" value="true" />
                                    </display:table>
   								</td>
                            </tr>                           
                     	</table>
                     	<br/>
                     	<br/>		
                     	<div id="pageMappingAttributes" style="background:#d1e5f6">
                     	    <br/>
							<table role="presentation" width="90%" style="table-layout:fixed"align="center">
								<tr>
									<td class="InputFieldLabel" width="14%">From ID:</td>
									<td id="fromQuestionID"></td>
									<td class="InputFieldLabel" width="14%">To ID:</td>
									<td id="toQuestionID"></td>
								</tr>
								<tr>
									<td class="InputFieldLabel">From Label:</td>
									<td id="fromQuestionLabel"></td>
									<td class="InputFieldLabel">To Label:</td>
									<td id="toQuestionLabel"></td>
								</tr>
								<tr>
									<td class="InputFieldLabel">From DataType:</td>
									<td id="fromDataTypeValue"></td>
									<td class="InputFieldLabel">To DataType:</td>
									<td id="toDataTypeValue"></td>
								</tr>
								<tr>
									<td id="fromCodeSetLabel" class="InputFieldLabel">From Value Set:</td>
									<td id="fromCodeSet"></td>
									<td id="toCodeSetLabel" class="InputFieldLabel">To Value Set:</td>
									<td id="toCodeSet">
								</tr>
								<tr>
									<td class="InputFieldLabel">From Answer:</td>
									<td id="fromCode"></td>
									<td class="InputFieldLabel" id="toCodeListL">To Answer:</td>
									<td>
									<div id="toCode">
											<html:select title="To Answer" name="portPageForm" property='currentQuestion.toCode' styleId="toCodeList" onchange="populateToCodeDesc();">
												<html:optionsCollection property="codedValue(YN)" value="key" label="value" /> <!-- codedValue(YN) is place holder, it will change dynamically -->
											</html:select>
									</div>
									<div id="toAnswer">
											<input title="To Answer" type="text" name="toAnswerId" id="toAnswerId" size="10">
									</div>
									</td>
								</tr>
								<tr>
									<td id="fromCodeDescLabel" class="InputFieldLabel">From Code Desc:</td>
									<td id="fromCodeDesc"></td>
									<td class="InputFieldLabel" id="toCodeDescLabel">To Code Desc:</td>
									<td  id="toCodeDesc"></td>
								</tr>
								<tr>
									<td class="InputFieldLabel" id="answerMappingReqLabel">Map Answer?:</td>
									<td>
										<html:select title="Map Answer?" name="portPageForm" property="currentQuestion.codeMappingRequired" styleId="answerMappingReq" onchange="disableEnableAnswerTOFields(this.value);">
											<html:optionsCollection property="codedValue(YN)" value="key" label="value" />
										</html:select>
									</td>
									<td></td>
									<td>							
									</td>
								</tr>
								<tr>
									<td class="InputFieldLabel"></td>
									<td></td>
									<td class="InputFieldLabel"></td>
									<td style="text-align:right">
									  <div id="addBtn">
										<input type="button" valign="right" name="Submit" id="addButton"  value=" Add " onclick="mapAnswer();"/>
									  </div>
									  <div id="editBtn">
									    <input type="button" valign="right" name="Submit" id="editButton" value=" Edit " onclick="editAnswer();"/>
									  </div>
									</td>
								</tr>
								</table>
								<br/>
						</div>
				</nedss:container>
					<html:hidden name="portPageForm" property="currentQuestion.fromQuestionId" styleId="mappedFromQuestion"/>
					<html:hidden name="portPageForm" property="currentQuestion.toQuestionId" styleId="mappedToQuestion"/>
					<html:hidden name="portPageForm" property="currentQuestion.fromCode" styleId="mappedFromCode"/>
					
					<div style="display: none;visibility: none;" id="errorMessages">
						<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
						<ul>
							<logic:iterate id="errors" name="portPageForm" property="attributeMap.searchCriteria">
								<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
							</logic:iterate>
						</ul>
					</div> 
				
    				<div class="grayButtonBar" style="text-align: right;">
    				<input type="button" id="SaveandContinue2"  name="SaveandContinue2" value="Save and Continue" onclick="saveContinue();"/> 
    				<input type="button" id="Map Question2"  name="Map Question2" value="Map Questions" onclick="saveContinue();returnToQuestions();"/>
    				
            				<input type="button" align="right" id = "Submit2" name="Submit2" value="Review Mapping" onclick="submitForm();"/>
            				<input type="button"  value="Print" id="print2" onclick="printQueue();"/> 
                  		<input type="button"  value="Download" id="download2" onclick="exportQueue();"/> 
   		 		</div>
			</div> <!-- id = "bd" -->
           <html:hidden styleId="SearchText1" property="searchCriteriaArrayMap.SearchText1_FILTER_TEXT"/>
           <html:hidden styleId="SearchText2" property="searchCriteriaArrayMap.SearchText2_FILTER_TEXT"/>
           <html:hidden styleId="SearchText3" property="searchCriteriaArrayMap.SearchText3_FILTER_TEXT"/>
           <html:hidden styleId="SearchText5" property="searchCriteriaArrayMap.SearchText5_FILTER_TEXT"/>
           
			<%@include file="AnswerPageDropDown.jsp" %>
			
	</div> <!-- id = "doc3" -->
    </html:form>
    <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
    <input type="hidden" id="totCnt" value="${fn:escapeXml(totalCount)}"/>
    <input type="hidden" id="mapReqCnt" value="${fn:escapeXml(mapReqCount)}"/>
    
    <logic:equal name="showNextQueToMapp" value="true">
		<script type='text/javascript'>
	    	mapAnswers('${QuestionIdentifier}',
	    		'${QuestionLabel}',
				'${CodeSetNm}',
				'${Code}',
				'${CodeDescTxt}',
				'${Mapped}');
	    </script>
	</logic:equal>
    <logic:notEqual name="showNextQueToMapp" value="true">
	<script type='text/javascript'>
		getElementByIdOrByName("pageMappingAttributes").style.display = "none";
	</script>
    </logic:notEqual>
    
  </body>
</html>