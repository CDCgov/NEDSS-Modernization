<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>${fn:escapeXml(PageTitle)}</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript" src="/nbs/dwr/interface/JSRTForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="Globals.js"></script>
        <script type="text/javaScript">

        blockEnterKey();
		
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
        
        function manageCodeSet()
        {
	    	if(codeSetSelectReqFlds()) {
                return false;
            } else {
                document.forms[0].action ="/nbs/ManageCodeSet.do?method=searchCodeSetSubmit";
            }
        }	
        
        function returnToManage()
      	{
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg))
            {
                document.forms[0].action ="/nbs/SrtAdministration.do?method=manageAdmin&focus=systemAdmin4";
            } else {
                return false;
            }	      	
        }
        function add(){
        	document.forms[0].action ="/nbs/ManageCodeSet.do?method=createCodeSet";
            }

        function importVadsValue(newVads, isMore){
        	blockUIDuringFormSubmissionNoGraphic();
			document.forms[0].action= "/nbs/ManageCodeSet.do?method=importValueSetLoad&vads="+newVads+"&existing=true&initLoad=true&isMoreAllowed="+isMore;
            document.forms[0].submit();  
		}

        function importVadsPopup()
        {
            var divElt = getElementByIdOrByName("blockparent");
            divElt.style.display = "block";		
            var o = new Object();
            o.opener = self;
           // window.showModalDialog("/nbs/ManageCodeSet.do?method=loadImportPopup", o, GetDialogFeatures(750, 270, false));
            
            var URL = "/nbs/ManageCodeSet.do?method=loadImportPopup";
            var modWin = openWindow(URL, o, GetDialogFeatures(750, 270, false, false), divElt, "");
		
            return false;
        }
        
        
        function confirmConceptMsgDisplay()
        {
        <%--     var confirmMsg = '<%=request.getAttribute("confirm_Concept_Msg") == null?null:HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("confirm_Concept_Msg")))%>';
            var newVads = '<%=request.getAttribute("newVads") == null?null:HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("newVads")))%>';
        --%>   
            var confirmMsg ='${fn:escapeXml(confirm_Concept_Msg)}';
            var newVads ='${fn:escapeXml(newVads)}';
            var isMore = true;
            if(isNullOrWhitespace(confirmMsg))
            {
            	 return false;
            }else
            {
            	if (confirm(confirmMsg)) {
            		importVadsValue(newVads,isMore);
    			} else {
    				return false;
    			}
            }
            
        }
        function confirmMsgDisplay()
        {
<%--             var confirmMsg = '<%=request.getAttribute("confirm_Msg") == null?null:HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("confirm_Msg")))%>'; --%>
            var confirmMsg = '${fn:escapeXml(confirm_Msg)}';
            if(isNullOrWhitespace(confirmMsg))
            {
                return false;
            }
            else 
            {
                alert(confirmMsg);
            }
                
        }
        
          
        function isNullOrWhitespace(input) {
        	  return !input || !input.trim();
        }
        
        function viewValueset(codeSetNm)
        {
        		blockUIDuringFormSubmissionNoGraphic();
				document.forms[0].action ="/nbs/ManageCodeSet.do?method=viewCodeSet&codeSetNm="+codeSetNm+"&fromView=true";
				document.forms[0].submit();
        }
        function editValueset(codeSetNm)
        {
        	blockUIDuringFormSubmissionNoGraphic();
				document.forms[0].action ="/nbs/ManageCodeSet.do?method=editCodeSet&codeSetNm="+codeSetNm;
				document.forms[0].submit();
        }

        function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
        
		// filter JS
		
		function selectfilterCriteria()
		{
			document.forms[0].action ='/nbs/ManageCodeSet.do?method=filterCodesetLibSubmit';
			document.forms[0].submit();
		}
		function clearFilter()
		{

			document.forms[0].action ='/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true';
			document.forms[0].submit();                                    
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
		function cancelFilter(key) {				  	
			key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
			JSRTForm.getAnswerArray(key1, function(data) {			  			
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
		function attachIcons() {	
		    $j("#parent thead tr th a").each(function(i) {		       
		      if($j(this).html() == 'Type')
					$j(this).parent().append($j("#type"));
		      if($j(this).html() == 'Status')
					$j(this).parent().append($j("#stats"));
		      if($j(this).html() == 'Value Set Code')
					$j(this).parent().append($j("#code"));
		      if($j(this).html() == 'Value Set Name')
					$j(this).parent().append($j("#name"));
		      if($j(this).html() == 'Value Set Description')
					$j(this).parent().append($j("#description"));					

		     }); 
		    $j("#parent").before($j("#whitebar"));
		    $j("#parent").before($j("#removeFilters"));
		}
		function displayTooltips() {		
			$j(".sortable a").each(function(i) {
			
				var headerNm = $j(this).html();
			       if(headerNm == 'Type') {
					_setAttributes(headerNm, $j(this), $j("#INV111"));
			      } else if(headerNm == 'Status') {
					_setAttributes(headerNm, $j(this), $j("#INV222"));
			      }else if(headerNm == 'Value Set Code') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV333"));
			      }else if(headerNm == 'Value Set Name') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV444"));
			      }else if(headerNm == 'Value Set Description') {
			    	  _setAttributes(headerNm, $j(this), $j("#INV555"));
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
		function makeMSelects() {
			$j("#type").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#stats").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#code").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#name").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#description").text({actionMode: '${fn:escapeXml(ActionMode)}'});			
		}	
        </script> 
        <style type="text/css">
        div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
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
    <body onLoad="startCountdown();disableSRTFlds();confirmMsgDisplay();confirmConceptMsgDisplay();showCount();attachIcons();makeMSelects();displayTooltips();">
    	 <div id="blockparent"></div>
         <div id="doc3">
         <html:form action="/ManageCodeSet.do" styleId="codeSetForm">	
            <div id="bd">
            <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
            <!-- Return to System Admin Screen -->
            	<table role="presentation" style="width:100%;">
					<tr>
					    <td style="text-align:right"  id="srtLink"> 
					        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin4">
					            Return to System Management Main Menu
					        </a>  
					    </td>
					</tr>
				</table>
            <!-- top bar -->
			  		<tr><td>&nbsp;</td></tr>
	            	<div class="popupButtonBar">
			            <input type="submit" id="submitA" value="Add New" onClick="add();"/>
			            <input type="button" id="submitB" value="Import" onClick="importVadsPopup();"/>
			            <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
			           <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
			         </div>
		             <!-- Page Code Starts here -->
		            
		            <%if(request.getAttribute("error") != null) {%>
						<div class="infoBox errors" id="error2">
							${fn:escapeXml(error)}
						</div>
			     	<%}%>
			      
          			<input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
          			
          			 <nedss:container id="section1" name="Value Set Library" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false"> 
		             <fieldset style="border-width:0px;" id="result">
					 <table role="presentation" width="100%">
			             <tr>
				             <td align="center">
								  	<display:table name="manageList" class="dtTable" style="margin-top:0em;" 
								  	pagesize="${SRTAdminManageForm.attributeMap.queueSize}"  
								  	id="parent" requestURI="/ManageCodeSet.do?method=ViewValueSetLib&existing=true&initLoad=true" sort="external" export="true" 
								  	excludedParams="answerArrayText(SearchText1) answerArrayText(SearchText2)  answerArrayText(SearchText3) answerArray(TYPE) answerArray(STATUS) method">
									  	<display:setProperty name="export.csv.filename" value="ValueSetLibrary.csv"/>
									  	<display:setProperty name="export.pdf.filename" value="ValueSetLibrary.pdf"/>
										<display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:4%;text-align:center;"/>
                                        <display:column property="editLink" title="<p style='display:none'>Edit</p>" media="html" style="width:4%;text-align:center;"/>
										<display:column property="valueSetTypeCd" title="Type"  sortable="true" sortName="getValueSetTypeCd" defaultorder="ascending" style="width:7%;"/>
										<display:column property="valueSetCode" title="Value Set Code"  sortable="true" sortName="getCodeSetNm" defaultorder="ascending" style="width:15%;"/>
										<display:column property="valueSetNm" title="Value Set Name"  sortable="true" sortName="getCodeSetShortDescTxt" defaultorder="ascending" style="width:15%;"/>
										<display:column property="codeSetDescTxt" title="Value Set Description"  sortable="true" sortName="getCodeSetDescTxt" defaultorder="ascending" style="width:25%;"/>
										<display:column property="statusCdDescTxt" title="Status" sortable="true" sortName="getStatusCd" defaultorder="ascending" style="width:8%;"/>
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
							<logic:iterate id="errors" name="SRTAdminManageForm" property="attributeMap.searchCriteria">
								<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
							</logic:iterate>
						</ul>
					</div> 
					
	         	   	<div class="popupButtonBar">
			            <input type="submit" id="submitA" value="Add New" onClick="add();"/>
			            <input type="button" id="submitB" value="Import" onClick="importVadsPopup();"/>
			            <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
			            <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
		           	</div>
            </div>
             <%@ include file="ManageCodesetDropdown.jsp" %>
			
		<html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>	
		<html:hidden styleId="INV111" property="attributeMap.searchCriteria.INV111"/>
		<html:hidden styleId="INV222" property="attributeMap.searchCriteria.INV222"/>
		<html:hidden styleId="HIDDEN333" property="attributeMap.searchCriteria.INV333"/>
		<html:hidden styleId="HIDDEN444" property="attributeMap.searchCriteria.INV444"/>
		<html:hidden styleId="HIDDEN555" property="attributeMap.searchCriteria.INV555"/>
		
		<html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.INV333"/>
		<html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.INV444"/>
		<html:hidden styleId="SearchText3" property="attributeMap.searchCriteria.INV555"/>
		
         </html:form>
        </div>
    </body>
</html>