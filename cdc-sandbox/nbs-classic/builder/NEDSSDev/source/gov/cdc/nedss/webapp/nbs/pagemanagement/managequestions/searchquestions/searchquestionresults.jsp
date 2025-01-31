<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Questions</title>
        <%@ include file="/jsp/resources.jsp" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
      	<script type="text/javascript" src="/nbs/dwr/interface/JManageQuestionsForm.js"></script>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="Globals.js"></script>
        <script type="text/javaScript">
        
        blockEnterKey();
		
        function addQuestion()
        {
            document.forms[0].action ="/nbs/LoadManageQuestions.do?method=addQuestionLoad";
        document.forms[0].submit();
        }
        function searchQuestion()
        {
       		document.forms[0].action ="/nbs/SearchManageQuestions.do?method=searchQuestionsLoad";
        }

        function viewQuestion(questionUid)
        {
        		blockUIDuringFormSubmissionNoGraphic();
				document.forms[0].action ="/nbs/LoadManageQuestions.do?method=viewQuestionLoad&waQuestionUid="+questionUid;
				document.forms[0].submit();
        }
        function editQuestion(questionUid)
        {
        	blockUIDuringFormSubmissionNoGraphic();
            document.forms[0].action ="/nbs/LoadManageQuestions.do?method=editQuestionLoad&waQuestionUid="+questionUid;
			document.forms[0].submit();
        }
	  function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
       function selectfilterCriteria()
		{
			document.forms[0].action ='/nbs/SearchManageQuestions.do?method=filterQuestionLibSubmit';
			document.forms[0].submit();
		}
		function clearFilter()
		{

			document.forms[0].action ='/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&initLoad=true';
			document.forms[0].submit();                                    
		}
		function cancelFilter(key) {				  	
			key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));	
			JManageQuestionsForm.getAnswerArray(key1, function(data) {			  			
				revertOldSelections(key, data);
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
        function makeMSelects() {
			$j("#uniqueId").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#uniqueName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#label").text({actionMode: '${fn:escapeXml(ActionMode)}'});	
		     $j("#type").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
		     $j("#grup").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
		     $j("#subgrup").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
		     $j("#status").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
		}
		
		function attachIcons() {	
		    $j("#parent thead tr th a").each(function(i) {		
		    	if($j(this).html() == 'Unique ID')
					$j(this).parent().append($j("#uniqueId"));
		    	if($j(this).html() == 'Unique Name')
					$j(this).parent().append($j("#uniqueName"));
		    	if($j(this).html() == 'Label')
					$j(this).parent().append($j("#label"));			      
		      	if($j(this).html() == 'Type')
					$j(this).parent().append($j("#type"));
		      	if($j(this).html() == 'Group')
					$j(this).parent().append($j("#grup"));
		      	if($j(this).html() == 'Subgroup')
					$j(this).parent().append($j("#subgrup"));
		      	if($j(this).html() == 'Status')
					$j(this).parent().append($j("#status"));
					      														
			}); 
		    $j("#parent").before($j("#whitebar"));
		    $j("#parent").before($j("#removeFilters"));
		}

		function displayTooltips() {		
			$j(".sortable a").each(function(i) {
			
				var headerNm = $j(this).html();
			      if(headerNm == 'Type') {
					_setAttributes(headerNm, $j(this), $j("#INV111"));
			      } 
			      else if(headerNm == 'Unique ID') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV001"));
			      } else if(headerNm == 'Unique Name') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV002"));
			      } else if(headerNm == 'Group') {
					_setAttributes(headerNm, $j(this), $j("#INV222"));
			      } else if(headerNm == 'Subgroup') {
					_setAttributes(headerNm, $j(this), $j("#INV333"));
			      } else if(headerNm == 'Label') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV003"));			
			      } else if(headerNm == 'Status') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV444"));
			      }	      
			});				
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
    <body onload="attachIcons();makeMSelects();showCount();displayTooltips();startCountdown();">
    	<div id="blockparent"></div>
         <div id="doc3">
         <html:form action="/SearchManageQuestions.do" >	
            <div id="bd">
            <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
            
                <!-- Return to System Admin Screen -->
				<div  style="text-align:right; margin-bottom:8px;">
                    <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin4">
                        Return to System Management Main Menu
                    </a>
                </div>
				
            	<!-- top bar -->
            	<div class="popupButtonBar">
	                <input type="button" name="Submit" value="Add New" onclick="addQuestion();"/>
	              	<input type="submit" id="submitCr" value="Search" onclick="searchQuestion();"/>
	                <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
				    <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
	            </div>

	           <!-- Refine search link --> 
				<% if (request.getAttribute("RefineSearchLink") != null) { %>
				   <div style="width:100%; text-align:right;">
				  		<a href="${fn:escapeXml(RefineSearchLink)}">Refine Search</a>
				  </div>    
		        <% } %>
			        
				<nedss:container id="section1" name="Question Library" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
				<!-- Confirm Message -->
					<tr><td>&nbsp;</td></tr>
		           <% if (request.getAttribute("searchConfirmMsg") != null) { %>
					  <div class="infoBox messages">
					  		${fn:escapeXml(searchConfirmMsg)}
							<b>
							${fn:escapeXml(searchConfirmMsg1)}
							</b>
							${fn:escapeXml(searchConfirmMsg2)}
					  </div>    
		           <% } %>
		             <fieldset style="border-width:0px;" id="result">
						 <table role="presentation" width="100%">
				             <tr>
					             <td align="center">
								  	<display:table name="manageList" class="dtTable" style="margin-top:0em;" pagesize="${manageQuestionsForm.attributeMap.queueSize}"  id="parent" 
								  	requestURI="/SearchManageQuestions.do?method=loadQuestionLibrary&existing=true" sort="external" export="true" 
								  	excludedParams="answerArrayText(SearchText1) answerArrayText(SearchText2)  answerArrayText(SearchText3) answerArray(TYPE) answerArray(GROUP) answerArray(SUBGROUP) answerArray(STATUS) method">
										  	<display:setProperty name="export.csv.filename" value="QuestionLibrary.csv"/>
										  	<display:setProperty name="export.pdf.filename" value="QuestionLibrary.pdf"/>
											<display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:2%;text-align:center;"/>
	                                        <display:column property="editLink" title="<p style='display:none'>Edit</p>" media="html" style="width:2%;text-align:center;"/>
											<display:column property="questionType" title="Type"  sortable="true" sortName="getQuestionType" defaultorder="ascending" style="width:3%;"/>
											<display:column property="questionIdentifier" defaultorder="ascending" sortable="true" sortName= "getQuestionIdentifier" title="Unique ID" style="width:4%;"/>
											<display:column property="questionNm" defaultorder="ascending" sortable="true" sortName= "getQuestionNm" title="Unique Name" style="width:8%;"/>
	                                        <display:column property="subGroupDesc" defaultorder="ascending" sortable="true" sortName= "getSubGroupDesc" title="Subgroup" style="width:8%;"/>
	                                        <display:column property="questionLabel" defaultorder="ascending" sortable="true" sortName= "getQuestionLabel" title="Label" style="width:12%;"/>
											<display:column property="recordStatusCd" title="Status" sortable="true" sortName="getRecordStatusCd" defaultorder="ascending" style="width:3%;"/>
											<display:setProperty name="basic.empty.showtable" value="true"/>
								     </display:table>
							      </td>
						     </tr>
					   </table>
				    </fieldset>
			    </nedss:container>
	            <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
				<div class="removefilter" id="removeFilters">
					<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
				</div>
			   <div style="display: none;visibility: none;" id="errorMessages">
					<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
					<ul>
						<logic:iterate id="errors" name="manageQuestionsForm" property="attributeMap.searchCriteria">
							<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
						</logic:iterate>
					</ul>
				</div> 
		            
	            <div class="popupButtonBar">
	                <input type="button" name="Submit" value="Add New" onclick="addQuestion();"/>
	              	<input type="submit" id="submitCr" value="Search" onclick="searchQuestion();"/>
	                <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
				    <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
	            </div>
           	</div>
           	<html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.INV001"/>
			<html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.INV002"/>
			<html:hidden styleId="SearchText3" property="attributeMap.searchCriteria.INV003"/>
            <%@ include file="ManageQuestionDropDown.jsp" %>
         </html:form>
        </div>
    	<input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
    </body>
</html>