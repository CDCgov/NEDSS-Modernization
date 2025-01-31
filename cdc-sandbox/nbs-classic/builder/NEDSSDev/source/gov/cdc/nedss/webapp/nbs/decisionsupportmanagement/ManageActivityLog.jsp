<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<html lang="en">
<head>
<title>NBS: Manage Activity Logs</title>
<%@ include file="/jsp/resources.jsp"%>
<SCRIPT Language="JavaScript"
	Src="/nbs/dwr/interface/JDsmActivityLogForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css" />
<script type="text/javaScript">
		function scrollToPosition()
		{
		  if( getElementByIdOrByName("pageYOffset") != null )
		  {
		    var oldPageYOffset = parseInt(document.forms[0].pageYOffset.value);
		    window.scrollTo(0,oldPageYOffset);
		  }
		  return false;  
		}
		function setWindowPositionY()
		{
			if(getElementByIdOrByName("pageYOffset") != null)
			{
				 var scroll;    
				 if (typeof(window.pageYOffset) == 'number')     
					 scroll = window.pageYOffset;     
				 else if (document.body && document.body.scrollTop)     
					scroll = document.body.scrollTop;     
				 else if (document.documentElement && document.documentElement.scrollTop)     
					scroll = document.documentElement.scrollTop;   
				 else scroll = 0; 
				getElementByIdOrByName("pageYOffset").value =  scroll;
				document.forms[0].pageYOffset.value = scroll;
			}
		}
		
		function blockUiDuringFormSubmission()
		{
		    var saveMsg = '<div class="submissionStatusMsg"> <div class="header"> Loading Search Results </div>' +  
		        '<div class="body"> <img src="saving_data.gif" alt="Saving data" title="Saving data"/> Your search results are being loaded. Please wait ... </div> </div>';         
			$j.blockUI({  
			    message: saveMsg,  
			    css: {  
			        top:  ($j(window).height() - 500) /2 + 'px', 
			        left: ($j(window).width() - 500) /2 + 'px', 
			        width: '500px'
			    }  
			});
		}

		 function searchActivityLog()
         {
			blockUiDuringFormSubmission();
 	    	document.forms[0].action ="/nbs/LoadDSMActivityLog.do?method=searchActivityLogSubmit&initLoad=true";
 	    	document.forms[0].submit();
         }	
		 function returnToManage()
         {
             var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
             if (confirm(confirmMsg)) {
                 document.forms[0].action ="/nbs/SystemAdmin.do?focus=systemAdmin2";
             } else {
                 return false;
             }	      	
         }
		 function viewActitivityLogDetails(urlToOpen)
         {
        	document.forms[0].action =urlToOpen;
       	    document.forms[0].submit();
         }
		 function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
		 function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
		
		//the following code for Queue
	       function attachIcons() {
	           $j("#parent thead tr th a").each(function(i) 
	           {
	        	   if($j(this).html() == 'Event ID' ) {
	                   $j(this).parent().append($j("#leventID"));
	               }
	               if($j(this).html() == 'Action' ) {
	                   $j(this).parent().append($j("#laction"));
	               }

	               if($j(this).html() == 'Algorithm Name') {
	                   $j(this).parent().append($j("#lalgorithmName"));
	               }
	               if($j(this).html() == 'Status') {
	                   $j(this).parent().append($j("#lstatus"));
	               }
	               if($j(this).html() == 'Message ID' ) {
	                   $j(this).parent().append($j("#lmessageId"));
	               }
	               if($j(this).html() == 'Reporting Facility' ) {
	                   $j(this).parent().append($j("#lsrcName"));
	               }
	               if($j(this).html() == 'Patient Name' ) {
	                   $j(this).parent().append($j("#lentityNm"));
	               }
	               if($j(this).html() == 'Accession#' ) {
	                   $j(this).parent().append($j("#laccessionNbr"));
	               }
	               if($j(this).html() == 'Observation ID' ) {
	                   $j(this).parent().append($j("#lobservationID"));
	               }
	           });
	           
	           $j("#parent").before($j("#whitebar"));
	           $j("#parent").before($j("#removeFilters"));
	       }
		
	       function makeMSelects() {
	    	    $j("#leventID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	        	$j("#laction").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	        	$j("#lalgorithmName").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	        	$j("#lstatus").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	            $j("#lmessageId").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	            $j("#lsrcName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	            $j("#lentityNm").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	            $j("#laccessionNbr").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	            $j("#lobservationID").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	}

	function showCount() {
		$j(".pagebanner b").each(function(i) {
			$j(this).append(" of ").append($j("#queueCnt").attr("value"));
		});
		$j(".singlepagebanner b").each(
				function(i) {
					var cnt = $j("#queueCnt").attr("value");
					if (cnt > 0)
						$j(this).append(" Results 1 to ").append(cnt).append(
								" of ").append(cnt);
				});
	}
	
	function onKeyUpValidate()
	{      	  
	if(getElementByIdOrByName("SearchText1").value != ""){
		getElementByIdOrByName("b1SearchText1").disabled=false;
		getElementByIdOrByName("b2SearchText1").disabled=false;
	   }else if(getElementByIdOrByName("SearchText1").value == ""){
	   	if(getElementByIdOrByName("b1SearchText1")!=null)
			getElementByIdOrByName("b1SearchText1").disabled=true;
		if(getElementByIdOrByName("b2SearchText1")!=null)
			getElementByIdOrByName("b2SearchText1").disabled=true;
        	   }
    	if(getElementByIdOrByName("SearchText2").value != ""){
    		getElementByIdOrByName("b1SearchText2").disabled=false;
    		getElementByIdOrByName("b2SearchText2").disabled=false;
    	   }else if(getElementByIdOrByName("SearchText2").value == ""){
    	   	if(getElementByIdOrByName("b1SearchText2")!=null)
    			getElementByIdOrByName("b1SearchText2").disabled=true;
    		if(getElementByIdOrByName("b2SearchText2")!=null)
    			getElementByIdOrByName("b2SearchText2").disabled=true;
    	   }
    	if(getElementByIdOrByName("SearchText3").value != ""){
    		getElementByIdOrByName("b1SearchText3").disabled=false;
    		getElementByIdOrByName("b2SearchText3").disabled=false;
    	   }else if(getElementByIdOrByName("SearchText3").value == ""){
    	   	if(getElementByIdOrByName("b1SearchText3")!=null)
    			getElementByIdOrByName("b1SearchText3").disabled=true;
    		if(getElementByIdOrByName("b2SearchText3")!=null)
    			getElementByIdOrByName("b2SearchText3").disabled=true;
    	   }
    	if(getElementByIdOrByName("SearchText4").value != ""){
    		getElementByIdOrByName("b1SearchText4").disabled=false;
    		getElementByIdOrByName("b2SearchText4").disabled=false;
    	   }else if(getElementByIdOrByName("SearchText4").value == ""){
    		if(getElementByIdOrByName("b1SearchText4")!=null)
    			getElementByIdOrByName("b1SearchText4").disabled=true;
    		if(getElementByIdOrByName("b2SearchText4")!=null)
    			getElementByIdOrByName("b2SearchText4").disabled=true;
    	   }
    	if(getElementByIdOrByName("SearchText5").value != ""){
    		getElementByIdOrByName("b1SearchText5").disabled=false;
    		getElementByIdOrByName("b2SearchText5").disabled=false;
    	   }else if(getElementByIdOrByName("SearchText5").value == ""){
    		if(getElementByIdOrByName("b1SearchText5")!=null)
    			getElementByIdOrByName("b1SearchText5").disabled=true;
    		if(getElementByIdOrByName("b2SearchText5")!=null)
    			getElementByIdOrByName("b2SearchText5").disabled=true;
    	   }
    	if(getElementByIdOrByName("SearchText6").value != ""){
    		if(getElementByIdOrByName("b1SearchText6")!=null)
    			getElementByIdOrByName("b1SearchText6").disabled=false;
    		if(getElementByIdOrByName("b2SearchText6")!=null)
    			getElementByIdOrByName("b2SearchText6").disabled=false;
    	   }else if(getElementByIdOrByName("SearchText6").value == ""){
    		if(getElementByIdOrByName("b1SearchText6")!=null)
    			getElementByIdOrByName("b1SearchText6").disabled=true;
    		if(getElementByIdOrByName("b2SearchText6")!=null)
    			getElementByIdOrByName("b2SearchText6").disabled=true;
    	   }
	}

	function displayTooltips() {
		$j(".sortable a").each(
				function(i) {
					var headerNm = $j(this).html();
					var sortSt = getElementByIdOrByName("sortSt") == null ? ""
							: getElementByIdOrByName("sortSt").value;
					var DEM102 = getElementByIdOrByName("DEM102") == null ? ""
							: getElementByIdOrByName("DEM102").value;
					var INV147 = getElementByIdOrByName("INV147") == null ? ""
							: getElementByIdOrByName("INV147").value;
					var INV100 = getElementByIdOrByName("SearchText1") == null ? ""
							: getElementByIdOrByName("SearchText1").value;
					var INV163 = getElementByIdOrByName("INV163") == null ? ""
							: getElementByIdOrByName("INV163").value;
					var INV150 = getElementByIdOrByName("INV150") == null ? ""
							: getElementByIdOrByName("INV150").value;
					var NOT118 = getElementByIdOrByName("NOT118") == null ? ""
							: getElementByIdOrByName("NOT118").value;
					var INV101 = getElementByIdOrByName("SearchText2") == null ? ""
							: getElementByIdOrByName("SearchText2").value;
					var INV102 = getElementByIdOrByName("SearchText3") == null ? ""
							: getElementByIdOrByName("SearchText3").value;
					var INV103 = getElementByIdOrByName("SearchText4") == null ? ""
							: getElementByIdOrByName("SearchText4").value;
					var INV104 = getElementByIdOrByName("SearchText5") == null ? ""
							: getElementByIdOrByName("SearchText5").value;
					var INV105 = getElementByIdOrByName("SearchText6") == null ? ""
							: getElementByIdOrByName("SearchText6").value;

					if (headerNm == 'Exception Text') {
						_handleTemplate(headerNm, $j(this), DEM102);
					} else if (headerNm == 'Processed Time') {
						_handleTemplate(headerNm, $j(this), INV147);
					} else if (headerNm == 'Event ID') {
						_setAttributes(headerNm, $j(this), INV100);
					} else if (headerNm == 'Action') {
						_setAttributes(headerNm, $j(this), INV150);
					} else if (headerNm == 'Algorithm Name') {
						_setAttributes(headerNm, $j(this), INV163);
					} else if (headerNm == 'Status') {
						_setAttributes(headerNm, $j(this), NOT118);
					}
					else if (headerNm == 'Message ID') {
						_setAttributes(headerNm, $j(this), INV101);
					}
					else if (headerNm == 'Reporting Facility') {
						_setAttributes(headerNm, $j(this), INV102);
					}
					else if (headerNm == 'Patient Name') {
						_setAttributes(headerNm, $j(this), INV103);
					}
					else if (headerNm == 'Accession#') {
						_setAttributes(headerNm, $j(this), INV104);
					}else if (headerNm == 'Observation ID') {
						_setAttributes(headerNm, $j(this), INV105);
					}
					
				});
	}
	
	 function _handleTemplate(headerNm, link, colId)
     {
      	var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
      	var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
      	
      	var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
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
		//var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();  
		var sortSt = getElementByIdOrByName("sortSt") == null ? "" : document
				.getElementById("sortSt").value;
		var orderCls = "SortAsc.gif";
		var altOrderCls = "Sort Ascending";
		var sortOrderCls = "FilterAndSortAsc.gif";
		var altSortOrderCls = "Filter Applied with Sort Ascending";
		if (sortSt != null && sortSt.indexOf("descending") != -1) {
			orderCls = "SortDesc.gif";
			altOrderCls = "Sort Descending";
			sortOrderCls = "FilterAndSortDesc.gif";
			altSortOrderCls = "Filter Applied with Sort Descending";
			
		}
		var filterCls = "Filter.gif";
		var altFilterCls = "Filter Applied";
		toolTip = colId == null ? "" : colId;
		if (colId != null && colId.length > 0) {
			link.attr("title", toolTip);
			imgObj.attr("src", filterCls);
			imgObj.attr("alt", altFilterCls);
			if (sortSt != null && sortSt.indexOf(headerNm) != -1) {
				imgObj.attr("src", sortOrderCls);
				imgObj.attr("alt", altSortOrderCls);	
			}
		} else {
			if (sortSt != null && sortSt.indexOf(headerNm) != -1) {
				imgObj.attr("src", orderCls);
				imgObj.attr("alt", altOrderCls);
			}
		}
	}

	function selectfilterCriteria() {
		document.forms[0].action = '/nbs/LoadDSMActivityLog.do?method=filterActivityLogSubmit';
		document.forms[0].submit();
	}

	function cancelFilter(key) {
		key1 = key.substring(key.indexOf("(") + 1, key.indexOf(")"));
		JDsmActivityLogForm.getAnswerArray(key1, function(data) {
			revertOldSelections(key, data);
		});
	}

	function revertOldSelections(name, value) {
		if (value == null) {
			$j("input[@name=" + name + "][type='checkbox']").attr('checked',
					true);
			$j("input[@name=" + name + "][type='checkbox']").parent().parent()
					.find('INPUT.selectAll').attr('checked', true);
			return;
		}

		//step1: clear all selections
		$j("input[@name=" + name + "][type='checkbox']").attr('checked', false);
		$j("input[@name=" + name + "][type='checkbox']").parent().parent()
				.find('INPUT.selectAll').attr('checked', false);

		//step2: check previous selections from the form
		for ( var i = 0; i < value.length; i++) {
			$j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr(
					'checked', true);
		}
		//step3: if all are checked, automatically check the 'select all' checkbox
		if (value.length == $j("input[@name=" + name + "][type='checkbox']")
				.parent().length)
			$j("input[@name=" + name + "][type='checkbox']").parent().parent()
					.find('INPUT.selectAll').attr('checked', true);
	}

	function clearFilter() {
		document.forms[0].action = '/nbs/LoadDSMActivityLog.do?method=searchActivityLogSubmit&initLoad=true';
		document.forms[0].submit();
	}
</script>
<style type="text/css">
div.messages {
	background: #E4F2FF;
	color: #000;
	padding: 0.5em;
	border-width: 1px 1px 1px 1px;
	border-color: #7AA6D5;
	border-style: solid;
	font-size: 95%;
}

div.popupButtonBar {
	text-align: right;
	width: 100%;
	background: #EEE;
	border-bottom: 1px solid #DDD;
}

.removefilter {
	background-color: #003470;
	width: 100%;
	height: 25px;
	line-height: 25px;
	float: right;
	text-align: right;
}

removefilerLink {
	vertical-align: bottom;
}

.hyperLink {
	font-size: 10pt;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	color: #FFFFFF;
	text-decoration: none;
}
</style>
</head>

<logic:notEmpty name="dsmActivityLogForm"
	property="attributeMap.SEARCHRESULT">
	<body
		onload="attachIcons();makeMSelects();showCount();displayTooltips();startCountdown();">
</logic:notEmpty>
<logic:empty name="dsmActivityLogForm"
	property="attributeMap.SEARCHRESULT">
	<body>
</logic:empty>
<div id="blockparent"></div>
<div id="doc3">
	<div id="bd">
		<!-- Top Page Nav -->
		<%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>
		<table role="presentation" style="width: 100%;">
			<tr>
				<td style="text-align: right" id="srtLink"><a id="manageLink"
					href="/nbs/SystemAdmin.do?focus=systemAdmin2"> Return to System
						Management Main Menu </a> <input type="hidden" id="actionMode"
					value="${SRTAdminManageForm.actionMode}" />
				</td>
			</tr>
			<tr>
				<td align="right" style="padding-top: 5px;"><i> <font
						class="boldTenRed"> * </font><font class="boldTenBlack">Indicates
							a Required Field </font> </i>
				</td>
			</tr>
		</table>
		<!-- Page Errors -->
		<logic:messagesPresent name="error_messages">
			<div class="infoBox errors" id="errorMessages">
				<ul>
					<html:messages id="msg" name="error_messages">
						<li><bean:write name="msg" />
						</li>
					</html:messages>
					<ul>
			</div>
		</logic:messagesPresent>

		<html:form>
			<nedss:container id="section0" name="Activity Log Filter Selection"
				classType="sect" includeBackToTopLink="false" displayImg="false"
				displayLink="false">
				<fieldset style="border-width: 0px;" id="search">
					<nedss:container id="subsec0" classType="subSect"
						displayImg="false">
						<tr>
							<td align="left"
								style="padding-top: 5px; padding-left: 7px; font-weight: bold">Time
								Frame of Event Record Processing</td>
							<td align="right"></td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><font class="boldTenRed">
									* </font><span>From:</span>
							</td>
							<td><html:text name="dsmActivityLogForm"
									property="activityLogClientVO.processingDateFrom"
									styleId="from_date" size="20" maxlength="10"
									title="Time Frame of Event Record Processing From" 
									onkeyup="DateMask(this,null,event)" /> <html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('from_date','from_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('from_date','from_dateIcon', event);"
									styleId="from_dateIcon"></html:img> <html:text
									styleId="srchLabResult" name="dsmActivityLogForm"
									property="activityLogClientVO.processingTimeFrom" size="10"
									title="Enter a From Time"
									maxlength="10" />
							</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><span></span>
							</td>
							<td>mm/dd/yyyy</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><font class="boldTenRed">
									* </font><span>To:</span>
							</td>
							<td><html:text name="dsmActivityLogForm"
									property="activityLogClientVO.processingDateTo"
									styleId="to_date" size="20" maxlength="10"
									title="Time Frame of Event Record Processing To" 
									onkeyup="DateMask(this,null,event)" /> <html:img src="calendar.gif" alt="Select a Date"
									onclick="getCalDate('to_date','to_dateIcon'); return false;"
									onkeypress ="showCalendarEnterKey('to_date','to_dateIcon', event);"
									styleId="to_dateIcon"></html:img> <html:text
									styleId="srchLabResult" name="dsmActivityLogForm"
									property="activityLogClientVO.processingTimeTo" size="10"
									title="Enter a To Time"
									maxlength="10" />
							</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><span></span>
							</td>
							<td>mm/dd/yyyy</td>
						</tr>
						<tr>
							<td class="fieldName" id="resCode"><font class="boldTenRed">
									* </font><span>Display records with status: </span>
							</td>
							<td>< html:multibox title="Success checkbox" name="dsmActivityLogForm"
								property="activityLogClientVO.processStatus"
								value="Success"/>Success &nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;< html:multibox
								name="dsmActivityLogForm" title="Failure checkbox" 
								property="activityLogClientVO.processStatus"
								value="Failure"/>Failure</td>
						</tr>
						<tr>
							<td align="left"></td>
							<td align="right" class="InputField"><input type="button"
								name="search" id="searchButton" value="Search"
								onClick="return searchActivityLog();" /> <input type="button"
								name="cancel" id="cancelButton" value="Cancel"
								onClick="return returnToManage();" /> &nbsp;</td>
						</tr>
					</nedss:container>
				</fieldset>
			</nedss:container>

			<!-- search results -->
			<logic:notEmpty name="dsmActivityLogForm"
				property="attributeMap.SEARCHRESULT">

				<tr>
					<td>&nbsp;</td>
				</tr>
				<!-- Top button bar -->

				<div class="grayButtonBar">
					<table role="presentation" width="100%">
						<tr>
							<td align="left">
								<!-- <input type="button" name="View Import/Export Activity Log" style="width: 190px" value="View Import/Export Activity Log" onclick="viewPageHistoryPopUp()"/>
							   	--> &nbsp;</td>
							<td align="right"><input type="button" value="Print" id=" "
								onclick="printQueue();" /> <input type="button"
								value="Download" id=" " onclick="exportQueue();" />
							</td>
						</tr>
					</table>
				</div>
				<logic:equal name="dsmActivityLogForm" property="docType" value="11648804">
				<div class="infoBox messages" style="text-align: left;">
					To access the legacy ELR Activity Log, <a
						href="/nbs/LoadELRActivityLog.do?"> <u>click here.</u>
					</a>
				</div>
				</logic:equal>
				<nedss:container id="section1" name="Activity Log Search Results"
					classType="sect" displayImg="false" displayLink="false"
					includeBackToTopLink="false">
					<fieldset style="border-width: 0px;" id="result">
						<html:hidden styleId="queueCnt" property="attributeMap.queueCount" />
						<div id="whitebar"
							style="background-color: #FFFFFF; width: 100%; height: 1px;"
							align="right"></div>
						<div class="removefilter" id="removeFilters">
							<a class="removefilerLink" href="javascript:clearFilter()"><font
								class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font>
							</a>
						</div>
						<logic:equal name="dsmActivityLogForm" property="docType" value="PHC236">
						<table role="presentation" width="98%">
							<tr>
								<td align="center"><display:table name="activityLogList"
										class="dtTable" style="margin-top:0em;" id="parent"
										pagesize="${dsmActivityLogForm.attributeMap.queueSize}"
										requestURI="/LoadDSMActivityLog.do?method=searchActivityLogSubmit&existing=true"
										sort="external" export="true"
										excludedParams="answerArray(PROCESSEDTIME) answerArray(SearchText1) answerArray(ACTION) answerArray(ALGORITHMNAME) answerArray(STATUS)  method">
										<display:setProperty name="export.csv.filename"
											value="ActivityLogLibrary.csv" />
										<display:setProperty name="export.pdf.filename"
											value="ActivityLogLibrary.pdf" />
										<display:column property="viewLink" title="Processed Time"
											media="html" format="{0,date,MM/dd/yyyy hh:mm:ss}"
											sortable="true" sortName="getRecordStatusTime"
											defaultorder="descending" style="width:13%;" />
										<display:column property="recordStatusTime"
											title="Processed Time" media="csv pdf" sortable="true"
											sortName="getRecordStatusTime" defaultorder="ascending"
											style="width:9%;" />
										<display:column property="targetUid" title="Event ID"
											sortable="true" sortName="getTargetUid"
											defaultorder="ascending" style="width:9%;" />
										<display:column property="algorithmAction" title="Action"
											media="html" sortable="true" sortName="getAlgorithmAction"
											defaultorder="ascending" style="width:14%;" />
										<display:column property="algorithmAction" title="Action"
											media="csv pdf" sortable="true" sortName="getAlgorithmAction"
											defaultorder="ascending" />
										<display:column property="algorithmName"
											title="Algorithm Name" sortable="true"
											sortName="getAlgorithmName" defaultorder="ascending"
											style="width:14%;" />
										<display:column property="recordStatusCdHtml" title="Status"
											media="html" sortable="true" sortName="getRecordStatusCd"
											defaultorder="ascending" style="width:9%;" />
										<display:column property="recordStatusCd" title="Status"
											media="csv pdf" sortable="true" sortName="getRecordStatusCd"
											defaultorder="ascending" style="width:9%;"/>
										<display:column property="exceptionShort"
											title="Exception Text" sortable="true"
											sortName="getException" defaultorder="ascending"
											style="width:38%;" />
										<display:setProperty name="basic.empty.showtable" value="true" />
									</display:table></td>
							</tr>
						</table>
						</logic:equal>
						<logic:equal name="dsmActivityLogForm" property="docType" value="11648804">
						<table role="presentation" width="98%">
							<tr>
								<td align="center"><display:table name="activityLogList"
										class="dtTable" style="margin-top:0em;" id="parent"
										pagesize="${dsmActivityLogForm.attributeMap.queueSize}"
										requestURI="/LoadDSMActivityLog.do?method=searchActivityLogSubmit&existing=true"
										sort="external" export="true"
										excludedParams="answerArray(PROCESSEDTIME) answerArray(SearchText1) answerArray(SearchText2) answerArray(SearchText3) answerArray(SearchText4) answerArray(SearchText5) answerArray(SearchText6) answerArray(ACTION) answerArray(ALGORITHMNAME) answerArray(STATUS)  method">
										<display:setProperty name="export.csv.filename"
											value="ActivityLogLibrary.csv" />
										<display:setProperty name="export.pdf.filename"
											value="ActivityLogLibrary.pdf" />
										<display:column property="viewLink" title="Processed Time"
											media="html" format="{0,date,MM/dd/yyyy hh:mm:ss}"
											sortable="true" sortName="getRecordStatusTime"
											defaultorder="descending" style="width:12%;" />
											
											<display:column property="recordStatusTime"
											title="Processed Time" media="csv pdf" sortable="true"
											sortName="getRecordStatusTime" defaultorder="ascending"
											style="width:9%;" />
											
										<display:column property="recordStatusCd" title="Status"
											media="csv pdf" sortable="true" sortName="getRecordStatusCd"
											defaultorder="ascending" style="width:5%;" />	
											
										<display:column property="recordStatusCdHtml" title="Status"
											media="html" sortable="true" sortName="getRecordStatusCd"
											defaultorder="ascending" style="width:6%;" />
												
										<display:column property="algorithmName"
											title="Algorithm Name" sortable="true"
											sortName="getAlgorithmName" defaultorder="ascending"
											style="width:10%;" />
										<display:column property="algorithmAction" title="Action"
											media="html" sortable="true" sortName="getAlgorithmAction"
											defaultorder="ascending" style="width:7%;" />
										<display:column property="algorithmAction" title="Action"
											media="csv pdf" sortable="true" sortName="getAlgorithmAction"
											defaultorder="ascending" />	
										

										<display:column property="messageId" title="Message ID"
											sortable="true" sortName="getMessageId"
											defaultorder="ascending" style="width:8%;" />

										<display:column property="srcName" title="Reporting Facility"
											sortable="true" sortName="getSrcName"
											defaultorder="ascending" style="width:12%;" />
											
										<display:column property="entityNm" title="Patient Name"
											sortable="true" sortName="getEntityNm"
											defaultorder="ascending" style="width:10%;" />

										<display:column property="businessObjLocalId" title="Observation ID"
											sortable="true" sortName="getBusinessObjLocalId"
											defaultorder="ascending" style="width:9%;" />


										<display:column property="accessionNbr" title="Accession#"
											sortable="true" sortName="getAccessionNbr"
											defaultorder="ascending" style="width:9%;" />
										
										<display:column property="exceptionShort"
											title="Exception Text" sortable="true"
											sortName="getException" defaultorder="ascending"
											style="width:43%;" />
										<display:setProperty name="basic.empty.showtable" value="true" />
									</display:table>
								</td>
							</tr>
						</table>
						</logic:equal>
					</fieldset>
				</nedss:container>
	</div>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<div class="grayButtonBar">
		<table role="presentation" width="100%">
			<tr>
				<td align="right"><input type="button" value="Print" id=" "
					onclick="printQueue();" /> <input type="button" value="Download"
					id=" " onclick="exportQueue();" />
				</td>
			</tr>
		</table>
	</div>

	<logic:equal name="dsmActivityLogForm" property="docType" value="PHC236">
	<html:select property="answerArray(SearchText1)" styleId="leventID"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	</logic:equal>
	<html:select property="answerArray(ACTION)" styleId="laction"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="action" value="key" label="value" />
	</html:select>
	<html:select property="answerArray(ALGORITHMNAME)"
		styleId="lalgorithmName" onchange="selectfilterCriteria()"
		multiple="true" size="4" style="width:180">
		<html:optionsCollection property="algorithmName" value="key"
			label="value" />
	</html:select>
	<html:select property="answerArray(STATUS)" styleId="lstatus"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="status" value="key" label="value" />
	</html:select>
	<logic:equal name="dsmActivityLogForm" property="docType" value="11648804">
	<html:select property="answerArray(SearchText2)" styleId="lmessageId"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	<html:select property="answerArray(SearchText3)" styleId="lsrcName"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	<html:select property="answerArray(SearchText4)" styleId="lentityNm"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	<html:select property="answerArray(SearchText5)" styleId="laccessionNbr"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	<html:select property="answerArray(SearchText6)" styleId="lobservationID"
		onchange="selectfilterCriteria()" multiple="true" size="4"
		style="width:180">
		<html:optionsCollection property="noDataArray" value="key"
			label="value" style="width:180" />
	</html:select>
	</logic:equal>

	<html:hidden styleId="sortSt"
		property="attributeMap.searchCriteria.sortSt" />
	<html:hidden styleId="DEM102"
		property="attributeMap.searchCriteria.DEM102" />
	<html:hidden styleId="INV147"
		property="attributeMap.searchCriteria.INV147" />
	<html:hidden styleId="INV163"
		property="attributeMap.searchCriteria.INV163" />
	<html:hidden styleId="SearchText1"
		property="attributeMap.searchCriteria.INV100" />
	<html:hidden styleId="INV150"
		property="attributeMap.searchCriteria.INV150" />
	<html:hidden styleId="NOT118"
		property="attributeMap.searchCriteria.NOT118" />
	<html:hidden styleId="SearchText2"
		property="attributeMap.searchCriteria.INV101" />
	<html:hidden styleId="SearchText3"
		property="attributeMap.searchCriteria.INV102" />
	<html:hidden styleId="SearchText4"
		property="attributeMap.searchCriteria.INV103" />
	<html:hidden styleId="SearchText5"
		property="attributeMap.searchCriteria.INV104" />
	<html:hidden styleId="SearchText6"
		property="attributeMap.searchCriteria.INV105" />

	</logic:notEmpty>
	
	</html:form>
	<%@ include file="/jsp/footer.jsp"%>
</div>
</div>
</body>
</html>