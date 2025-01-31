<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>
<%@ page import="gov.cdc.nedss.pagemanagement.wa.dt.*"%>

<html lang="en">
    <head>
        <title>NBS: Manage Templates </title>
        <%@ include file="/jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageTemplateForm.js"></SCRIPT>
        <script type="text/javaScript" src="pagemanagementSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="Globals.js"></script>
        <SCRIPT Language="JavaScript" Src="genericQueue.js"></SCRIPT>
        <script type="text/javaScript">

        function addNewTemplate()
        {
       		
        }
              
        function createLink(url)
		{
			// call the JS function to block the UI while saving is on progress.
			blockUIDuringFormSubmissionNoGraphic();
			window.location=url;
            //document.forms[0].action= url;
            //document.forms[0].submit();  
		} 
        

      <%--   function makeMSelects() {
        	$j("#templateName").text({actionMode: '<%= request.getAttribute("ActionMode") %>'});
        	$j("#templateDescr").text({actionMode: '<%= request.getAttribute("ActionMode") %>'});
        	$j("#lUpdated").multiSelect({actionMode: '<%= request.getAttribute("ActionMode") %>'});
        	$j("#lUpdatedBy").multiSelect({actionMode: '<%= request.getAttribute("ActionMode") %>'});
        	$j("#source").multiSelect({actionMode: '<%= request.getAttribute("ActionMode") %>'});
        	$j("#status").multiSelect({actionMode: '<%= request.getAttribute("ActionMode") %>'});

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
        --%>
       function cancelFilter(key)
       {				  	
       	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
       	JManageTemplateForm.getAnswerArray(key1, function(data) {			  			
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
       	document.forms[0].action ='/nbs/ManageTemplates.do?method=filterTemplateSubmit';
       	document.forms[0].submit();
       }

       //the following code for Queue
      /*  function attachIcons() {
           $j("#parent thead tr th a").each(function(i) 
           {
        	   if($j(this).html() == 'Template Name') {
                   $j(this).parent().append($j("#templateName"));
               }
               if($j(this).html() == 'Template Description') {
                   $j(this).parent().append($j("#templateDescr"));
               }
               if($j(this).html() == 'Last Updated') {
                   $j(this).parent().append($j("#lUpdated"));
               }
               if($j(this).html() == 'Last Updated By') {
                   $j(this).parent().append($j("#lUpdatedBy"));
               }
               if($j(this).html() == 'Source') {
                   $j(this).parent().append($j("#source"));
               }
               if($j(this).html() == 'Status') {
                   $j(this).parent().append($j("#status"));
               }
           });
           
           $j("#parent").before($j("#whitebar"));
           $j("#parent").before($j("#removeFilters"));
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
		   
		}
       function displayTooltips() 
       {
       	var INV147 = getElementByIdOrByName("INV147") == null ? "" : getElementByIdOrByName("INV147").value;
       	var INV163 = getElementByIdOrByName("INV163") == null ? "" : getElementByIdOrByName("INV163").value;
       	var INV100 = getElementByIdOrByName("INV100") == null ? "" : getElementByIdOrByName("INV100").value;
       	var NOT118 = getElementByIdOrByName("NOT118") == null ? "" : getElementByIdOrByName("NOT118").value;
    	var TEMPLATENM = getElementByIdOrByName("TEMPLATENM") == null ? "" : getElementByIdOrByName("TEMPLATENM").value;
       	var TEMPLATEDESCR = getElementByIdOrByName("TEMPLATEDESCR") == null ? "" : getElementByIdOrByName("TEMPLATEDESCR").value;
       	// Since 'Last Updated' and 'Last Updated By' both have 'Last Updated' text common I wrote following steps 
       	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
       	var checkLastUpdatedBy = false;
       	if(sortSt != null && sortSt.indexOf('Last Updated By') != -1 )
       		checkLastUpdatedBy = true;
           	
        	$j(".sortable a").each(function(i) {
       	    var headerNm = $j(this).html();
       	    if(headerNm == 'Template Name' && !checkLastUpdatedBy) {
       	    	_setAttributes(headerNm, $j(this), TEMPLATENM);
       	    }else if(headerNm == 'Template Description' && !checkLastUpdatedBy) {
       	    	_setAttributes(headerNm, $j(this), TEMPLATEDESCR);
       	    }else if(headerNm == 'Last Updated'&& !checkLastUpdatedBy) {
       	    	_setAttributes(headerNm, $j(this),INV147);
       	    }else if(headerNm == 'Last Updated By' && checkLastUpdatedBy) {
       	    	_setAttributes(headerNm, $j(this), INV100);
       	    }else if(headerNm == 'Source' && !checkLastUpdatedBy) {
       	    	_setAttributes(headerNm, $j(this), INV163);
       	    }  else if(headerNm == 'Status' && !checkLastUpdatedBy) {
       	    	_setAttributes(headerNm, $j(this),NOT118);
       	    }
           });
        } */
        function displayTooltips() 
        {
        	if(document.getElementById("stringQueueCollection")!=null &&  document.getElementById("stringQueueCollection")!=undefined){
				var stringQueue = document.getElementById("stringQueueCollection").value;
				var arrayQueue = stringQueue.split("#");
				var values = new Array();
				
				for(var i=0; i<arrayQueue.length; i++){
					var elements = arrayQueue[i].split(",");
					var dropdownStyleId="";
					var type="";
					for(var j=0; j<elements.length; j++){
						var key =elements[j].split(":")[0];
						var value =elements[j].split(":")[1];
						if(key.indexOf("backendId")!=-1){
							dropdownStyleId=value;
							if(values.indexOf(dropdownStyleId)==-1)
								values.push(dropdownStyleId);
						}
						
					}
				}// Since 'Last Updated' and 'Last Updated By' both have 'Last Updated' text common I wrote following steps 
        	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").innerText;
        	var checkLastUpdatedBy = false;
        	if(sortSt != null && sortSt.indexOf('Last Updated By') != -1 ){
        		//checkLastUpdatedBy = true;
        		sortSt = sortSt.replace("Last Updated By", "By");
        		getElementByIdOrByName("sortSt").innerText = sortSt;
        	}
        	var i=0;
         	$j(".sortable a").each(function(i) {
        	    var headerNm = $j(this).html();
        	    if(headerNm == 'Template Name'/*  && !checkLastUpdatedBy */) {
        	    	_setAttributes(headerNm, $j(this),  $j("#"+values[i]+""));
        	    }else if(headerNm == 'Template Description'/*  && !checkLastUpdatedBy */) {
        	    	_setAttributes(headerNm, $j(this),  $j("#"+values[i]+""));
        	   	}else if(headerNm == 'Last Updated By'/*  && checkLastUpdatedBy */) {
        	    	_setAttributes('By', $j(this),  $j("#"+values[i]+""));
        	   	}else if(headerNm == 'Last Updated'/*&&  !checkLastUpdatedBy */) {
       	    		_setAttributes(headerNm, $j(this), $j("#"+values[i]+""));
       	    	}else if(headerNm == 'Source'/*  && !checkLastUpdatedBy */) {
        	    	_setAttributes(headerNm, $j(this),  $j("#"+values[i]+""));
        	    }else if(headerNm == 'Status'/*  && !checkLastUpdatedBy */) {
        	    	_setAttributes(headerNm, $j(this), $j("#"+values[i]+""));
        	    }
        		i++;
            });
         }
        }
      /*  function _setAttributes(headerNm, link, colId) 
       {
        	var imgObj = link.parent().find("img");
        	var toolTip = "";
        	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
        	var orderCls = "SortAsc.gif";
        	var sortOrderCls = "FilterAndSortAsc.gif";
        	if(sortSt != null && sortSt.indexOf("descending") != -1) {
        		orderCls = "SortDesc.gif";
        		sortOrderCls = "FilterAndSortDesc.gif";
        	}
        	var filterCls = "Filter.gif";
          	toolTip = colId == null ? "" : colId;
          	if(colId != null && colId.length > 0) {
        		link.attr("title", toolTip);
        		imgObj.attr("src", filterCls);
        		if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
        		 imgObj.attr("src", sortOrderCls);
        		}
          	} else {
        		if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
        			imgObj.attr("src", orderCls);
        		}
          	}
       }

       function _handleTemplate(headerNm, link, colId)
       {
        	var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
        	var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" id="queueIcon" align="top" border="0"/>';
        	
        	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
        	if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
        		if(sortSt != null && sortSt.indexOf("descending") != -1) {
        			link.after(htmlDesc);
        		} else {
        			link.after(htmlAsc);
        		}
        	}
       }
         */
       function clearFilter()
       {
       	document.forms[0].action ='/nbs/ManageTemplates.do?method=ManageTemplatesLib&initLoad=true&context=RemoveAllFilters';
       	document.forms[0].submit();
       }
       
       function importTemplate()
       {
        var urlToOpen =  '/nbs/ManageTemplates.do?method=importTemplateLoad';
        
        // get the gray background element & activate it.
        var divElt = getElementByIdOrByName("pamview");
        if (divElt == null) {
            divElt = getElementByIdOrByName("blockparent");
        }
        divElt.style.display = "block";
        
        // open a modal window        
        var o = new Object();
        o.opener = self;
        
        //var modWin = window.showModalDialog(urlToOpen,o, GetDialogFeatures(600, 150, false));
        
        var modWin = openWindow(urlToOpen, o, GetDialogFeatures(600, 150, false, true), divElt, "");
        
        // handle any return values from modal window. If return value is
        // "windowClosed", deactivate the gray background by hiding it. 
        //if (modWin == "windowClosed") {
         //   divElt.style.display = "none";
       // }
      }
      
      function importTemplateSubmit(filePath)
       {

       	document.forms[0].action ='/nbs/ManageTemplates.do?method=importTemplate&FilePath='+filePath;
       	document.forms[0].submit();
       }
       
          function viewImpExpActivityLog()
        {
        	  blockUIDuringFormSubmissionNoGraphic();
              document.forms[0].action ='/nbs/ManageTemplates.do?method=manageImportExportLogLib';
              document.forms[0].submit();
        }

         function makeCheckedUnchecked(){
        		/* var pText = document.getElementsByClassName('multiSelectOptions')[3];
        		var inputs = pText.getElementsByTagName("input");
        		var sortSt = getElementByIdOrByName("errorMessages") == null ? "" : getElementByIdOrByName("errorMessages").innerText;
            	
        		for (var i = 1; i < inputs.length; i++) {
        			var checkbox = inputs[i].type;
        			
        			if(checkbox='checkbox' && sortSt != null && sortSt.indexOf('Status') != -1 ){
        			   var defaultChecked = inputs[i].defaultChecked;
        			   if(!defaultChecked)		
        				   inputs[i].checked=defaultChecked;
        		    }
        		} */
        		
        		cancelFilter("answerArray(STATUS)");
        		cancelFilter("answerArray(SOURCE)");
        		cancelFilter("answerArray(LASTUPDATED)");
        		cancelFilter("answerArray(LASTUPDATEDBY)");
        		
        	 
         }
        </script>  
        <style type="text/css">
	   div#addAttachmentBlock {margin-top:10px; display:none; width:100%; text-align:center;}
	    </style>
        <style type="text/css">
         div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
         div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
         .boxed {
          border: 1px solid #5F8DBF ;background:#E4F2FF
             }
        
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
    <body onload="attachIcons();makeMSelects();showCount();displayTooltips();autocompTxtValuesForJSP();startCountdown();makeCheckedUnchecked();">
        <div id="blockparent"></div>
         <html:form action="/ManageTemplates.do" enctype="multipart/form-data" method="post">
            <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
	                		 <!-- Top Nav Bar and top button bar -->
								<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
								 
							    <table role="presentation" style="width:100%;">
									<tr>
									    <td style="text-align:right"  id="srtLink"> 
									        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin4">
									            Return to System Management Main Menu
									        </a>  
									        <input type="hidden" id="ActionModeT" value="${manageTemplateForm.actionMode}"/>
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
								   		 		  <input type="button" name="View Import/Export Activity Log" style="width: 200px" value="View Import/Export Activity Log" onclick="viewImpExpActivityLog()"/>
										   	  &nbsp;</td>
										 	<td align="right"> 
							                  
								             	<input type="button"  value="Import" onclick="showAddAttachmentBlock()"/>
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
								        <ul><li>${fn:escapeXml(error)}</li>
<%-- 								            <li> <%= request.getAttribute("error")%> </li> --%>
								        </ul>
								    </div>    
								<% }%>
								 <%@ include file="../../jsp/feedbackMessagesBar.jsp" %>
								 
						           <% if (request.getAttribute("ConfirmMesg") != null) { %>
									  <div class="infoBox success" id="success1">${fn:escapeXml(ConfirmMesg)}
									  </div>    
						           <% } %>
						           
						            <% if (request.getAttribute("confirmation") != null && request.getAttribute("confirmation").toString().equals("uploaderror")) {
						            	 if(request.getAttribute("status") != null && request.getAttribute("status").toString().equals("templateAlreadyExists")) {%>
					                        <div class="infoBox errors" id="error1">
						                        Import of <b>${fn:escapeXml(srcTemplateNm)}</b> failed. Template  with the given  template name  <b>${fn:escapeXml(srcTemplateNm)}</b> being imported already exists in the system.   
						                         
						                    </div>  
						                 <%}else if(request.getAttribute("status") != null && request.getAttribute("status").toString().equals("corrupt file template")) {%>
					                        <div class="infoBox errors" id="error2">
					                        Import of template failed. The source file for the template being imported is either corrupt or does not conform to the required format. 
					                         
					                         </div>  					                    
					                    <%}else if(request.getAttribute("status") != null && request.getAttribute("status").toString().equals("filenotfound")) {%>
					                        <div class="infoBox errors" id="error2">
					                        Import of template failed. The source file is required to be on the application server in-order to be imported successfully. Please contact your system administrator for assistance.
					                         
					                         </div>  
					                    
					                     <%}else{ %>     
					                        <div class="infoBox errors" id="error3">
						                        Import of <b>${fn:escapeXml(srcTemplateNm)}</b> failed. Please see import log for more details.    
						                         
						                    </div>    
						                  <% } %>   
				                    <% } %>
						           
								 <!-- Container Div -->
	          				      <tr>
					                <td style="padding:0.5em;">					                   
					                    <div id="addAttachmentBlock" class="boxed">
				                           <table role="presentation" class="formTable" style=" width:100%; margin:0px;">
				                                                 <h2> &nbsp;&nbsp; Import Template </h2> 
									       
									        <tr>
									            <td colspan="2">
									                <div id="errorBlock" class="screenOnly infoBox errors" style="display:none;"> </div>
									                <div id="msgBlock" class="screenOnly infoBox messages" style="display:none;"> </div>
									            </td>
									        </tr>
									        
									    
									        <tr>
									               <td colspan="2" style="text-align:left;"><span style="color:black; font-weight:normal;"> &nbsp;&nbsp;Please enter the location of or browse to the Template you would like to import.	</a></td>
									        </tr>
									        <!-- File Attachment -->
									        <tr>
									            <td class="fieldName" id="chooseFileLabel"> <span style="color:#CC0000; font-weight:bold;">*</span>Template Name:</td>
									            <td><html:file onkeypress="this.click();" title="Template Name" property="importFile" style="height: 1.8em;" maxlength="70" size= "70"> </html:file></td>
									           
									        </tr> 
									         <tr>
									            <td colspan="2" style="text-align:right;">
									                <input type="button" value="Submit" name="submitButton" onclick="return submitAttachment();"/>
									                <input type="reset" value="Cancel" name="cancelButton" onclick="javascript:cancelAttachment()"/>
									            </td>
									        </tr>      
									    </table>
									    
									    <p><span align='left' class='status-text' id='updateStatusMsg'></span>
									    <div id="progressBar" style="display: none;">
									        <div id="theMeter">
									            <div id="progressBarText"></div>
									            <div id="progressBarBox">
									                <div id="progressBarBoxContent"></div>
									            </div>
									        </div>
									    </div>
									    </p>
					                    </div>
					                </td>
					            </tr>
					            
	
	                			 <nedss:container id="section1" name="Template Library" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="false">
		                             <fieldset style="border-width:0px;" id="result">
		                             <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
		                                <table role="presentation" width="98%">
		                                    <tr>
			                                    <td align="center">
			                                      <display:table name="manageList" class="dtTable" style="margin-top:0em;"  id="parent"  pagesize="${manageTemplateForm.attributeMap.queueSize}"   requestURI="/ManageTemplates.do?method=ManageTemplatesLib&existing=true" sort="external" export="true" excludedParams="stringQueueCollection answerArrayText(SearchText1) answerArrayText(SearchText2) answerArray(LASTUPDATED) answerArray(LASTUPDATEDBY) answerArray(SOURCE) answerArray(STATUS)  method">
			                            				<display:setProperty name="export.csv.filename" value="TemplateLibrary.csv"/>
			                    					  	<display:setProperty name="export.pdf.filename" value="TemplateLibrary.pdf"/>
														<display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:4%;text-align:center;"/>
                                        				
		                                               <%--  <display:column property="templateNm" title="Template Name"  sortable="true"  sortName="getTemplateNm" defaultorder="ascending" style="width:15%;" />
		                                                <display:column property="descTxt" title="Template Description"   sortable="true"  sortName="getDescTxt" defaultorder="ascending" style="width:22%;" />
		                                                <display:column property="lastChgTime" title="Last Updated" format="{0,date,MM/dd/yyyy}" sortable="true" sortName="getLastChgTime" defaultorder="ascending" style="width:10%;"/>
		                                                <display:column property="lastChgUserNm" title="Last Updated By"  sortable="true"  sortName="getLastChgUserNm" defaultorder="ascending" style="width:12%;" />
		                                                <display:column property="sourceNm" title="Source"  sortable="true"  sortName="getSourceNm" defaultorder="ascending" style="width:8%;" />
		                                                <display:column property="recStatusCd" title="Status"  sortable="true"  sortName="getRecStatusCd" defaultorder="ascending" style="width:6%;" /> --%>

												<d:forEach items="${manageTemplateForm.queueCollection}"
													var="item">
													<bean:define id="media" value="${item.media}" />
													<logic:match name="media" value="html">
														<display:column property="${item.mediaHtmlProperty}"
															defaultorder="${item.defaultOrder}"
															sortable="${item.sortable}"
															sortName="${item.sortNameMethod}" media="html"
															title="${item.columnName}" style="${item.columnStyle}"
															class="${item.className}"
															headerClass="${item.headerClass}" />
													</logic:match>
													<logic:match name="media" value="pdf">
														<display:column property="${item.mediaPdfProperty}"
															defaultorder="${item.defaultOrder}"
															sortable="${item.sortable}"
															sortName="${item.sortNameMethod}" media="pdf"
															title="${item.columnName}" style="${item.columnStyle}"
															class="${item.className}"
															headerClass="${item.headerClass}" />
													</logic:match>
													<logic:match name="media" value="csv">
														<display:column property="${item.mediaCsvProperty}"
															defaultorder="${item.defaultOrder}"
															sortable="${item.sortable}"
															sortName="${item.sortNameMethod}" media="csv"
															title="${item.columnName}" style="${item.columnStyle}"
															class="${item.className}"
															headerClass="${item.headerClass}" />
													</logic:match>
													<br>
												</d:forEach>
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
						<td align="left"><input type="button"
							name="View Import/Export Activity Log" style="width: 200px"
							value="View Import/Export Activity Log"
							onclick="viewImpExpActivityLog()" /></td>
						<td align="right"><input type="button" value="Import"
							onclick="showAddAttachmentBlock()" /> <input type="button"
							value="Print" id=" " onclick="printQueue();" /> <input
							type="button" value="Download" id=" " onclick="exportQueue();" />
						</td>
					</tr>
				</table>
			
			</div>
			</td>
			</tr>
			<div style="display: none; visibility: none;" id="errorMessages">
				<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by:
				</b> <br />
				<ul>
					<logic:iterate id="errors" name="manageTemplateForm"
						property="attributeMap.searchCriteria">
						<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
					</logic:iterate>
				</ul>
			</div>
		</div>
               		
               		
               		<%-- <html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.TEMPLATENM"/>
			    	<html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.TEMPLATEDESCR"/>
               		<html:hidden styleId="TEMPLATENM" property="attributeMap.searchCriteria.TEMPLATENM"/>
			    	<html:hidden styleId="TEMPLATEDESCR" property="attributeMap.searchCriteria.TEMPLATEDESCR"/>
               		
               		<html:hidden styleId="INV147" property="attributeMap.searchCriteria.INV147"/>	
               		<html:hidden styleId="INV163" property="attributeMap.searchCriteria.INV163"/>	
               		<html:hidden styleId="INV100" property="attributeMap.searchCriteria.INV100"/>	
               		<html:hidden styleId="NOT118" property="attributeMap.searchCriteria.NOT118"/>	
			   		<html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>	 --%>
		<!-- Hidden Fields -->
		<d:forEach items="${manageTemplateForm.queueCollection}" var="item">
			<bean:define id="filterType" value="${item.filterType}" />
			<logic:match name="filterType" value="1">
				<input type='hidden' id="${item.dropdownProperty}"
					value="<%=request.getAttribute("${item.backendId}") != null ? request.getAttribute("${item.backendId}") : ""%>" />
			</logic:match>
		</d:forEach>
		<input type='hidden' id='actionMode' value="${fn:escapeXml(ActionMode)}" />
		<div id="dropdownsFiltering" style="Display: none">
			<!-- Added to resolve an issue with IE11 -->
			<%@ include file="/jsp/dropDowns_Generic_Queue.jsp"%>
		</div>
		
	</html:form>
    </body>

 <script type="text/javaScript">
 function showAddAttachmentBlock() {
            $j("body").find(":input").attr("disabled","disabled");
            $j("div#addAttachmentBlock").show();
            $j("div#addAttachmentBlock").find(":input").attr("disabled","");            
            
            // disable links  
            var hrefs = $j("table.dtTable").find("a");
            for (var li = 0; li < hrefs.length; li++) {
                var href = $j(hrefs[li]).attr("href");
                var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
                $j(hrefs[li]).append(hiddenSpan);
                $j(hrefs[li]).removeAttr("href");
                $j(hrefs[li]).attr("disabled", "disabled");
                $j(hrefs[li]).css("cursor", "none");        
            }
            
			// disable filter icons  
            hrefs = $j("table.dtTable").find(".multiSelect");
            for (var li = 0; li < hrefs.length; li++) {
                var href = $j(hrefs[li]).attr("href");
                var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
                $j(hrefs[li]).append(hiddenSpan);
                $j(hrefs[li]).removeAttr("href");
                $j(hrefs[li]).attr("disabled", "disabled");
                $j(hrefs[li]).css("cursor", "none");        
            }

				
			
             hrefs = $j("span.pagelinks").find("a");
            
	                for (var li = 0; li < hrefs.length; li++) {
	                    var href = $j(hrefs[li]).attr("href");
	                    var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
	                    $j(hrefs[li]).append(hiddenSpan);
	                    $j(hrefs[li]).removeAttr("href");
	                    $j(hrefs[li]).attr("disabled", "disabled");
	                    $j(hrefs[li]).css("cursor", "none");        
            }
               $j("div#error1").hide();
	       $j("div#error2").hide();
	       $j("div#error3").hide();
               $j("div#success1").hide();
               window.scrollTo(0,0);
	    }	    
	    
	    function cancelAttachmentBlock() {
	        // enable all input elts
	        $j("body").find(":input").attr("disabled","");

	        // hide attachments block
	        $j("div#addAttachmentBlock").hide();
	        
            // enable links
            var hrefs = $j("table.dtTable").find("a");
            for (var li = 0; li < hrefs.length; li++) {
                var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
                if (href != "") {
                    href = href.replace(/&amp;/g, "&");
                    $j(hrefs[li]).attr("href", href);    
                }
                $j(hrefs[li]).find("span").remove();
                $j(hrefs[li]).attr("disabled", "");
                $j(hrefs[li]).css("cursor", "hand");
            }
			
			//enable filter icons
			hrefs = $j("table.dtTable").find(".multiSelect");
            for (var li = 0; li < hrefs.length; li++) {
                var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
                if (href != "") {
                    href = href.replace(/&amp;/g, "&");
                    $j(hrefs[li]).attr("href", href);    
                }
                $j(hrefs[li]).find("span").remove();
                $j(hrefs[li]).attr("disabled", "");
                $j(hrefs[li]).css("cursor", "hand");
            }
			
			
             hrefs = $j("span.pagelinks").find("a");
	                for (var li = 0; li < hrefs.length; li++) {
	                    var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
	                    if (href != "") {
	                        href = href.replace(/&amp;/g, "&");
	                        $j(hrefs[li]).attr("href", href);    
	                    }
	                    $j(hrefs[li]).find("span").remove();
	                    $j(hrefs[li]).attr("disabled", "");
	                    $j(hrefs[li]).css("cursor", "hand");
            }
	    } 
	    
	    function submitAttachment() {
	      var errors = new Array();
        var index = 0;
        var hasErrors = false;
      
        // file attached
        if (jQuery.trim(document.manageTemplateForm.importFile.value) == "") {
            errors[index++] = "Template name is a required field.";
            hasErrors = true;
			 
            $j("td#chooseFileLabel").css("color", "#CC0000");
        }
        else {
            $j("td#chooseFileLabel").css("color", "black");
        }        
       
        
        if (hasErrors) {
            displayErrors("errorBlock", errors);
            // call resize fram to take into account the added error text, that caused
            // the iframe's height to grow. 
			
		if(parent.resizeIFrame!=undefined)
            parent.resizeIFrame();
		else{
			if(document.getElementsByClassName("formTable")!=null && document.getElementsByClassName("formTable")!=undefined &&
			document.getElementsByClassName("formTable")[0]!=null && document.getElementsByClassName("formTable")[0]!=undefined)
			document.getElementsByClassName("formTable")[0].setAttribute("style","margin: 0px; width: 99%");
		}
			
            return false;
        }
	    
        else {
           blockUIDuringFormSubmissionNoGraphic();  
           document.manageTemplateForm.action="/nbs/ManageTemplates.do?method=importTemplate&type=Import";           
           document.manageTemplateForm.submit();
            return true;
           }
        
	}	
	
	function cancelAttachment() {
        resetFileUploadForm();
        
        // display elts
        $j("td#fileNameLabel").css("color", "black");
        $j("td#chooseFileLabel").css("color", "black");
        $j("div#errorBlock").hide();
        $j("div#msgBlock").hide();
        $j("div#error1").hide();
          $j("div#error2").hide();
            $j("div#error3").hide();
              $j("div#success1").hide();
       
        
        cancelAttachmentBlock();
	}
	
	function resetFileUploadForm() {
        // form elts
        
        document.manageTemplateForm.importFile.value = "";
        $j("form#fileAttachmentForm span#attachmentFileChosen").html("");
        document.manageTemplateForm.submitButton.disabled = false;
        document.manageTemplateForm.cancelButton.disabled = false;
	}
	
	
	    </script>    
	    </html>
        