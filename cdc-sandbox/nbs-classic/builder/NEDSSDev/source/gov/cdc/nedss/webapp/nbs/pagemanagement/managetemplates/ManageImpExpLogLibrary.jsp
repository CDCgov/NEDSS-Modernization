<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Templates </title>
        <%@ include file="/jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageImportExportLogForm.js"></SCRIPT>
        <script type="text/javaScript" src="pagemanagementSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javaScript"> 
        
        

        function makeMSelects() {
        	$j("#lprocessedTime").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#ltype").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#ltemplateName").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#lsource").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
        	$j("#lstatus").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});

        }
        function showCount()
        {
            $j(".pagebanner b").each(function(i){ 
                $j(this).append(" of ").append($j("#queueCnt").attr("value"));
            });
            $j(".singlepagebanner b").each(function(i){ 
                var cnt = $j("#queueCnt").attr("value");
                if(cnt > 0)
                    $j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
            });             
        }
        function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
       
       function cancelFilter(key)
       {				  	
       	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
       	JManageImportExportLogForm.getAnswerArray(key1, function(data) {			  			
       		revertOldSelections(key, data);
       	});		  	
       }

       function revertOldSelections(name, value) 
       {  
       	if (value == null) {
       		$j("input[@name="+name+"][type='checkbox']").attr('checked', true);
       		$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
       		return;
       	}

       	//step1: clear all selections
       	$j("input[@name="+name+"][type='checkbox']").attr('checked', false);
       	$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', false);

       	//step2: check previous selections from the form
          	for (var i=0; i<value.length; i++) {
               $j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr('checked', true);
           }
           
       	//step3: if all are checked, automatically check the 'select all' checkbox
       	if(value.length == $j("input[@name="+name+"][type='checkbox']").parent().length) {
       	   $j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
       	}
       }

       function selectfilterCriteria()
       {
       	document.forms[0].action ='/nbs/ManageImpExpLog.do?method=filterImpExpLogSubmit';
       	document.forms[0].submit();
       }

       //the following code for Queue
       function attachIcons() {
           $j("#parent thead tr th a").each(function(i) 
           {
               if($j(this).html() == 'Processed Time') {
                   $j(this).parent().append($j("#lprocessedTime"));
               }
               if($j(this).html() == 'Type') {
                   $j(this).parent().append($j("#ltype"));
               }
                     if($j(this).html() == 'Template Name' ) {
                   $j(this).parent().append($j("#ltemplateName"));
               }

               if($j(this).html() == 'Source') {
                   $j(this).parent().append($j("#lsource"));
               }
               if($j(this).html() == 'Status') {
                   $j(this).parent().append($j("#lstatus"));
               }
           });
           
           $j("#parent").before($j("#whitebar"));
           $j("#parent").before($j("#removeFilters"));
       }

       function displayTooltips() 
       {
       			
        		$j(".sortable a").each(function(i) {
       	    var headerNm = $j(this).html();
       	  //  alert(headerNm);
       	  var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
       	  var DEM102 = getElementByIdOrByName("DEM102") == null ? "" : getElementByIdOrByName("DEM102").value;
       	  var INV147 = getElementByIdOrByName("INV147") == null ? "" : getElementByIdOrByName("INV147").value;
       	  var INV100 = getElementByIdOrByName("INV100") == null ? "" : getElementByIdOrByName("INV100").value;
       	  var INV163 = getElementByIdOrByName("INV163") == null ? "" : getElementByIdOrByName("INV163").value;
       	  var INV150 = getElementByIdOrByName("INV150") == null ? "" : getElementByIdOrByName("INV150").value;
       	   var NOT118 = getElementByIdOrByName("NOT118") == null ? "" : getElementByIdOrByName("NOT118").value;       	   
       	  
       	  
       	    if(headerNm == 'Exception Text' ) {
       	    	_handleTemplate(headerNm, $j(this), DEM102 );       	
       	    }else if(headerNm == 'Processed Time') {
       	    	_setAttributes(headerNm, $j(this),INV147);
       	    }else if(headerNm == 'Type' ) {
       	    	_setAttributes(headerNm, $j(this), INV100);
            }else if(headerNm == 'Template Name' ) {
       	    	_setAttributes(headerNm, $j(this), INV150);
       	    }else if(headerNm == 'Source' ) {
       	    	_setAttributes(headerNm, $j(this), INV163);
       	    }  else if(headerNm == 'Status' ) {
       	    	_setAttributes(headerNm, $j(this),NOT118);
       	    }
           });
        }

       function _setAttributes(headerNm, link, colId) 
       {
        	var imgObj = link.parent().find("img");
        	var toolTip = "";
        	//var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();  
        	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;      	
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
		  	
          	toolTip = colId == null ? "" : colId;
          	if(colId != null && colId.length > 0) {
        		link.attr("title", toolTip);
        		imgObj.attr("src", filterCls);
        		imgObj.attr("alt", altFilterCls);

        		if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
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

       function _handleTemplate(headerNm, link, colId)
       {
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
        
       function clearFilter()
       {
       	document.forms[0].action ='/nbs/ManageImpExpLog.do?method=manageImportExportLogLib&initLoad=true';
       	document.forms[0].submit();
       }
       
        function viewActitivityLogDetails(urlToOpen)
       {
       
        	document.forms[0].action =urlToOpen;
       	   document.forms[0].submit();
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
    <body onload="attachIcons();makeMSelects();showCount();displayTooltips();autocompTxtValuesForJSP();startCountdown();">
        <div id="blockparent"></div>
         <html:form action="/ManageImpExpLog.do">
            <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
	                		 <!-- Top Nav Bar and top button bar -->
								<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
								 
							    <table role="presentation" style="width:100%;">
									<tr>
									    <td style="text-align:right"  id="srtLink"> 
									        <a id="manageLink" href="/nbs/ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&context=ReturnToManage">
									            Return to Template Library</a>  
									        <input type="hidden" id="actionMode" value="${fn:escapeXml(manageImportExportLogForm.actionMode)}"/>
									    </td>
									</tr>
									
								</table>
								 <!-- top bar -->
								<tr><td>&nbsp;</td></tr>
						          <!-- Top button bar -->     

						   		<div class="grayButtonBar">
							   		<table role="presentation" width="100%">
								   		 <tr>
								   		 	<td align="left" >
								   		 		 <!-- <input type="button" name="View Import/Export Activity Log" style="width: 190px" value="View Import/Export Activity Log" onclick="viewPageHistoryPopUp()"/>
										   	-->  &nbsp;</td>
										 	<td align="right"> 
							                          	<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
								           		<input type="button"  value="Download" id=" " onclick="exportQueue();"/>             
							                </td>
							              </tr>
									</table>
					            </div>
						         <!-- Error message -->
								<% if(request.getAttribute("error") != null) { %>
								    <div class="infoBox errors">
								        <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
								        <ul>
								            <li>${fn:escapeXml(error)}</li>
								        </ul>
								    </div>    
								<% }%>
								 <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
									
						           
								 <!-- Container Div -->
	          				 
	
	                			 <nedss:container id="section1" name="Import/Export Activity Log" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
		                             <fieldset style="border-width:0px;" id="result">
		                             <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
		                                <table role="presentation" width="98%">
		                                    <tr>
			                                    <td align="center">
			                                      <display:table name="manageList" class="dtTable" style="margin-top:0em;"  id="parent"  pagesize="${manageImportExportLogForm.attributeMap.queueSize}"   requestURI="/ManageImpExpLog.do?method=manageImportExportLogLib&existing=true" sort="external" export="true" excludedParams="answerArray(PROCESSEDTIME) answerArray(TYPE)answerArray(TEMPLATENAME) answerArray(SOURCE) answerArray(STATUS)  method">
			                            				<display:setProperty name="export.csv.filename" value="ImportExportLibrary.csv"/>
			                    						<display:setProperty name="export.pdf.filename" value="ImportExportLibrary.pdf"/>									
		                                                <display:column property="viewLink" title="Processed Time" media="html" format="{0,date,MM/dd/yyyy hh:mm:ss}" sortable="true" sortName="getRecordStatusTime" defaultorder="descending" style="width:15%;"/>
		                                                <display:column property="recordStatusTime" title="Processed Time"  media="csv pdf" sortable="true"  sortName="getRecordStatusTime" defaultorder="ascending" style="width:10%;" />
		                                                <display:column property="impExpIndCdDesc" title="Type"   sortable="true"  sortName="getImpExpIndCdDesc" defaultorder="ascending" style="width:9%;" />
		                                                <display:column property="docName" title="Template Name"   media="html" sortable="true"  sortName="getDocName" defaultorder="ascending" style="width:14%;" />
		                                                <display:column property="docName" title="Template Name"  media="csv pdf" sortable="true"  sortName="getDocName" defaultorder="ascending" />
		                                                <display:column property="srcName" title="Source" sortable="true" sortName="getSrcName" defaultorder="ascending" style="width:9%;"/>
		                                                <display:column property="recordStatusCd" title="Status"  sortable="true"  sortName="getRecordStatusCd" defaultorder="ascending" style="width:9%;" />
		                                                <display:column property="exceptionShort" title="Exception Text"  sortable="true"  sortName="getException" defaultorder="ascending"  style="width:38%;" />
		                                                <display:setProperty name="basic.empty.showtable" value="true"  />
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
	        		   		</div>
        		   		<!-- top bar -->
							<tr><td>&nbsp;</td></tr>
			            	<div class="grayButtonBar">
						   		<table role="presentation" width="100%">
							   		 <tr>
							   		  	<td align="right"> 
							                          	<input type="button"  value="Print" id=" " onclick="printQueue();"/> 
								           		<input type="button"  value="Download" id=" " onclick="exportQueue();"/>             
							                    </td>
						                     </tr>
								</table>
					       </div>
						</td>
					</tr>
               </div>
     	 	<html:select property="answerArray(PROCESSEDTIME)" styleId = "lprocessedTime" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
			<html:optionsCollection property="processedTime" value="key" label="value" style="width:180"/>
		</html:select>
		<html:select property="answerArray(TYPE)" styleId = "ltype" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
			<html:optionsCollection property="type" value="key" label="value"/>
		</html:select>
		<html:select property="answerArray(TEMPLATENAME)" styleId = "ltemplateName" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
			<html:optionsCollection property="templateName" value="key" label="value"/>
		</html:select>
		<html:select property="answerArray(SOURCE)" styleId = "lsource" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
			<html:optionsCollection property="source" value="key" label="value"/>
		</html:select>
		<html:select property="answerArray(STATUS)" styleId = "lstatus" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
		<html:optionsCollection property="status" value="key" label="value"/>
		</html:select> 
		<html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>
		<html:hidden styleId="INV147" property="attributeMap.searchCriteria.INV147"/>	
		<html:hidden styleId="INV163" property="attributeMap.searchCriteria.INV163"/>	
		<html:hidden styleId="INV100" property="attributeMap.searchCriteria.INV100"/>	
		<html:hidden styleId="INV150" property="attributeMap.searchCriteria.INV150"/>	
        <html:hidden styleId="NOT118" property="attributeMap.searchCriteria.NOT118"/>
		  					   	  
        </html:form>
    </body>
</html>