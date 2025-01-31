<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<html lang="en">
    <head>
	<title>Manage Workflow Decision Support: Algorithm Library</title>
        <%@ include file="/jsp/resources.jsp" %>
     
		<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<script type="text/javascript" src="/nbs/dwr/interface/JDecisionSupportLibraryForm.js"></script>
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript">
		
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
			
			
	function createLink(element, url)
	{
		// call the JS function to block the UI while saving is on progress.
		blockUIDuringFormSubmissionNoGraphic();
                document.forms[0].action= url;
                document.forms[0].submit();  
	}

	function attachIcons() {    
	            $j("#parent thead tr th a").each(function(i) {  
	                if($j(this).html() == 'Event Type'){         
	                     $j(this).parent().append($j("#fEventType"));
	                }
			if($j(this).html() == 'Algorithm Name'){         
	                     $j(this).parent().append($j("#fAlgorithmName"));
	                }
			if($j(this).html() == 'Related Condition(s)/Test(s)'){         
	                     $j(this).parent().append($j("#fRelatedConditions"));
	                }
			//if($j(this).html() == 'Advanced Criteria'){         
	        //             $j(this).parent().append($j("#fAdvancedCriteria"));
	        //        }
            if($j(this).html() == 'Action'){         
                 $j(this).parent().append($j("#fAction"));
            }
            if($j(this).html() == 'Last Updated'){         
                 $j(this).parent().append($j("#fLastUpdated"));
            }
            if($j(this).html() == 'Status'){
                 $j(this).parent().append($j("#fStatus"));
            }      
          
            }); 
	            $j("#parent").before($j("#whitebar"));
	            $j("#parent").before($j("#removeFilters"));
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
	             $j("#fEventType").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	             $j("#fAlgorithmName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	             $j("#fRelatedConditions").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	             $j("#fAdvancedCriteria").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	             $j("#fAction").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	             $j("#fLastUpdated").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	             $j("#fStatus").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
	}
		        
	function _showAtoZIcon(headerNm, link, colId) {            
            var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
            var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
            var sortSt =  getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;            
            var sortFirstStr = sortSt.substring(0,(sortSt.indexOf("@")-1));
            var sortSecondStr = sortSt.substring(sortSt.indexOf("@")+1);
            
            if(sortFirstStr != null && sortFirstStr==headerNm) {
                if(sortSecondStr != null && sortSecondStr.indexOf("descending") != -1) {
                    link.after(htmlDesc);
                } else {
                    link.after(htmlAsc);
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
        
        function displayToolTips() {        
             $j(".sortable a").each(function(i) {
             
                 var headerNm = $j(this).html();
                 var INV111 = getElementByIdOrByName("INV111") == null ? "" : getElementByIdOrByName("INV111").value;
                 var INV222 = getElementByIdOrByName("INV222") == null ? "" : getElementByIdOrByName("INV222").value;
                 var INV333 = getElementByIdOrByName("INV333") == null ? "" : getElementByIdOrByName("INV333").value;
                 var INV444 = getElementByIdOrByName("INV444") == null ? "" : getElementByIdOrByName("INV444").value;
                 var INV001 = getElementByIdOrByName("SearchText1") == null ? "" : getElementByIdOrByName("SearchText1").value;
                 var INV002 = getElementByIdOrByName("SearchText2") == null ? "" : getElementByIdOrByName("SearchText2").value;
                 
                  if(headerNm =='Event Type') {
                      _setAttributes(headerNm, $j(this), INV111);
                   } 
                    else if (headerNm =='Related Condition(s)/Test(s)') {
                          _setAttributes(headerNm, $j(this), INV002);
                   }                   
                   else if (headerNm =='Algorithm Name') {
                          _setAttributes(headerNm, $j(this), INV001);
                   }                
                   else if(headerNm == 'Action') {                      
                      _setAttributes(headerNm, $j(this), INV222);
                   }
                   else if(headerNm == 'Last Updated') {
                      _setAttributes(headerNm, $j(this), INV333);
                   }
                   else if(headerNm == 'Status') {
                      _setAttributes(headerNm, $j(this), INV444);
                   }                  
             });             
         } 
          
        function _setAttributes(headerNm, link, colId) {
            
            var imgObj = link.parent().find("img");
            var toolTip = "";   
            var sortSt =  getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
            //alert("setAttr " + headerNm + " sort is " + sortSt);
            var sortFirstStr = sortSt.substring(0,(sortSt.indexOf("@")-1));
            var sortSecondStr = sortSt.substring(sortSt.indexOf("@")+1);
            var orderCls = "SortAsc.gif";
			var altOrderCls = "Sort Ascending";
            var sortOrderCls = "FilterAndSortAsc.gif";
            var altSortOrderCls = "Filter Applied with Sort Ascending";
			
            
            if(sortSecondStr != null && sortSt.indexOf("descending") != -1) {
                orderCls = "SortDesc.gif";
                altOrderCls = "Sort Descending";
				
                sortOrderCls = "FilterAndSortDesc.gif";
            	altSortOrderCls = "Filter Applied with Sort Descending";
    			
            }   
            var filterCls = "Filter.gif";
            var altFilterCls = "Filter Applied";
            //toolTip = colId.html() == null ? "" : colId.html();
            if(colId != null && colId.length > 0) {
                link.attr("title", toolTip);
                imgObj.attr("src", filterCls);
                imgObj.attr("alt", altFilterCls);
                if(sortFirstStr != null && sortFirstStr == headerNm ){
                    imgObj.attr("src", sortOrderCls);    
               		imgObj.attr("alt", altSortOrderCls);		
                }
				
            } else {
            
                if(sortFirstStr != null && sortFirstStr==headerNm) {
                 
                    imgObj.attr("src", orderCls); 
                    imgObj.attr("alt", altOrderCls);
                    
					
                }           
            }
        }
	function selectfilterCriteria()
         {
            document.forms[0].action ='/nbs/ManageDecisionSupport.do?method=filterDSMLibrarySubmit';
            document.forms[0].submit();
         }  
         
    function clearFilter()
         {
             document.forms[0].action ="/nbs/ManageDecisionSupport.do?method=loadqueue&existing=true&initLoad=true";
             document.forms[0].submit();                                    
         }
         
    function cancelFilter(key) {  
        key1 = key.substring(key.indexOf("(")+1, key.indexOf(")")); 
        JDecisionSupportLibraryForm.getAnswerArray(key1, function(data) {                      
            revertOldSelections(key, data);
        });         
    }     
         
          
    function addAlgorithm()
    {
    	document.forms[0].action ="/nbs/DecisionSupport.do?method=createLoad";
        document.forms[0].submit();
    }  
    
    
    function printQueue() {
        window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
    }
    function exportQueue() {
        window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
    }    
    
    function viewAlgorithm(viewLink){
 	   blockUIDuringFormSubmissionNoGraphic();
 	   document.forms[0].action =viewLink;
	   document.forms[0].submit();
    }

    function editAlgorithm(editLink){
  	   blockUIDuringFormSubmissionNoGraphic();
  	   document.forms[0].action =editLink;
 	   document.forms[0].submit();
     }
    
    function showAddAttachmentBlock() {
        $j("body").find(":input").attr("disabled","disabled");
        $j("div#addAttachmentBlock").show();
        $j("div#infoBoxErrors").hide();
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
    
    function submitAttachment() {
	      var errors = new Array();
      var index = 0;
      var hasErrors = false;
      
      // file attached
      if (jQuery.trim(document.decisionSupportLibraryForm.importFile.value) == "") {
          errors[index++] = "<b>Choose File</b> is a required field.";
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
          if (parent && parent.resizeIframe) {         
        	  parent.resizeIframe(); 
          } 
          return false;
      }
	    
      else {
         blockUIDuringFormSubmissionNoGraphic();  
         document.decisionSupportLibraryForm.action="/nbs/DecisionSupport.do?method=importAlgorithm";           
         document.decisionSupportLibraryForm.submit();
         return true;
         }   
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
    
	function resetFileUploadForm() {
        // form elts
        
        document.decisionSupportLibraryForm.importFile.value = "";
        $j("form#fileAttachmentForm span#attachmentFileChosen").html("");
        document.decisionSupportLibraryForm.submitButton.disabled = false;
        document.decisionSupportLibraryForm.cancelButton.disabled = false;
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
	
    </script>
	<style type="text/css">
	   div#addAttachmentBlock {margin-top:10px; display:none; width:100%; text-align:center;}
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
    <body onload="attachIcons();makeMSelects();showCount();displayToolTips();startCountdown();">
		<div id="blockparent"></div>
		  <div id="doc3">
			<div id="bd">
		   <!-- Top Nav Bar and top button bar -->
		    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>

           <!-- Page Code Starts here -->
             <div  style="text-align:right; margin-bottom:8px;">
                 <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin1">
                     Return to System Management Main Menu
                 </a>
            </div>
            <!-- Top button bar -->
            <div class="grayButtonBar" style="text-align: right;">
              <input type="button" name="Submit" value="Add New" onclick="addAlgorithm();"/>
              <input type="button"  value="Import" onclick="showAddAttachmentBlock()"/>
              <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
              <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
            </div>
           <html:form action="/ManageDecisionSupport.do" method="post" enctype="multipart/form-data">
           <% if(request.getAttribute("error") != null) { %>
			    <div class="infoBox errors" id="infoBoxErrors">
			        <ul>
			            <li> ${fn:escapeXml(error)} </li>
			        </ul>
			    </div>    
			<% }%>
			 <logic:messagesPresent name="error_messages">
			    <div class="infoBox errors" id="infoBoxErrors">
			        <ul>
			            <html:messages id="msg" name="error_messages">
			                <li> <bean:write name="msg" filter="false" /> </li>
			            </html:messages>
			        <ul>
			    </div>
			</logic:messagesPresent>   
            <div id="addAttachmentBlock" class="boxed">
                <table role="presentation" class="formTable" style=" width:100%; margin:0px;">
                                              <h2 style="float:left"> &nbsp;&nbsp; Import Algorithm </h2>	       
		        <tr>
		            <td colspan="2">
		                <div id="errorBlock" class="screenOnly infoBox errors" style="display:none;"> </div>
		                <div id="msgBlock" class="screenOnly infoBox messages" style="display:none;"> </div>
		            </td>
		        </tr>		    
		        <tr>
		               <td colspan="2" style="text-align:left;"><span style="color:black; font-weight:normal;"> &nbsp;&nbsp;Please browse to the Decision Support Algorithm you would like to import.	</a></td>
		        </tr>
		        <!-- File Attachment -->
		        <tr>
		            <td class="fieldName" id="chooseFileLabel"> <span style="color:#CC0000; font-weight:bold;">*</span>Choose File:</td>
		            <td><html:file onkeypress="this.click();" title="Choose File" property="importFile" style="height: 1.8em;" maxlength="70" size= "70"> </html:file></td>	           
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
					                    
           <nedss:container id="section2" name="Algorithm Library" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="no">
              <fieldset style="border-width:0px;" id="result">
                <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
				<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
	            <div class="removefilter" id="removeFilters">
					<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
				</div>
	 	          <table role="presentation" width="100%">
		             <tr>
			             <td align="center">
							  	<display:table name="algorithmList" class="dtTable" style="margin-top:0em;" pagesize="20"  id="parent" requestURI="/ManageDecisionSupport.do?method=loadqueue&existing=true&initLoad=true" 
							  	   sort="external" export="true" excludedParams="attributeMap.ResultedTestDescriptionWithCode decisionSupportClientVO.answer(Event)= 
								   decisionSupportClientVO.answer(NNDComment) decisionSupportClientVO.answer(VALUE1) decisionSupportClientVO.answer(VALUE1) 
								   decisionSupportClientVO.answer(NUMERICRESULT)
								   decisionSupportClientVO.answer(NUMERICRESULT) advVal_code_textbox attributeMap.TestCode decisionSupportClientVO.answer(Event) 
								   Val_code_textbox=&approval1 decisionSupportClientVO.answer(OnFailureToCreateInvestigation) decisionSupportClientVO.answer(CriteriaValue) 
								   decisionSupportClientVO.answer(CriteriaValue) decisionSupportClientVO.answer(CriteriaValue) 
								   decisionSupportClientVO.answer(CriteriaValue) decisionSupportClientVO.answer(CriteriaValue) 
								   decisionSupportClientVO.answer(DefaultQuestion) attributeMap.PartPerUid onfail_textbox decisionSupportClientVO.answer(AlgorithmName)
								   decisionSupportClientVO.answer(Comment) attributeMap.ResultDescription decisionSupportClientVO.answer(DefaultValue)
								   decisionSupportClientVO.answer(DefaultValue) decisionSupportClientVO.answer(DefaultValue) decisionSupportClientVO.answer(DefaultValue)
								   PublishedCondition_textbox decisionSupportClientVO.answer(UseEventDateLogic)
								   answerArray(EVENTTYPE) answerArrayText(SearchText1) answerArrayText(SearchText2) answerArray(ACTION) answerArray(STATUS) answerArray(LASTUPDATED) method">
								  	<display:setProperty name="export.csv.filename" value="DecisionSupportAlgorithm.csv"/>
								  	<display:setProperty name="export.pdf.filename" value="DecisionSupportAlgorithm.pdf"/>
							  	    <display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:3%;text-align:center;"/>
                                    <display:column property="editLink" title="<p style='display:none'>Edit</p>" media="html" style="width:3%;text-align:center;"/>
									<display:column property="eventType" title="Event Type" sortable="true" sortName="getEventType" defaultorder="descending" style="width:10%;"/>
									<display:column property="algorithmName" title="Algorithm Name" sortable="true" sortName="getAlgorithmName" defaultorder="descending" style="width:16%;"/>
									<display:column property="relatedConditions" title="Related Condition(s)/Test(s)" media="html" sortable="true" sortName="getRelatedConditions" defaultorder="descending" style="width:22%;"/>
									<display:column property="relatedConditionsPrint" title="Related Condition(s)/Test(s)" media="pdf csv" sortable="true" sortName="getRelatedConditions" defaultorder="descending" style="width:22%;"/>
									<display:column property="action" title="Action" sortable="true" sortName="getAction" defaultorder="descending" style="width:14%;"/>
									<display:column property="lastChgTime" title="Last Updated" sortable="true" sortName="getLastChgTime" defaultorder="descending" style="width:14%;" format="{0,date,MM/dd/yyyy}"/>
									<display:column property="status" title="Status" sortable="true" sortName="getStatus" defaultorder="descending" style="width:8%;"/>
									<display:setProperty name="basic.empty.showtable" value="true"/>
						       </display:table>
					      </td>
				     </tr>
				  </table>
		 	 </fieldset>
           </nedss:container>
            <!-- bottom button bar -->
            <div class="grayButtonBar" style="text-align: right;">
              <input type="button" name="Submit" value="Add New" onclick="addAlgorithm();"/>
              <input type="button"  value="Import" onclick="showAddAttachmentBlock()"/>
              <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
              <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
            </div>	
		  <jsp:include page="DSMLibraryDropDown.jsp" />		   
		   <input type='hidden' id='SearchText1' value="${fn:escapeXml(FILTERBYUNIQUEALGORITHMNAME)}"/>
		   <input type='hidden' id='SearchText2' value="${fn:escapeXml(FILTERBYCONDITIONTEST)}"/>
		  
		   <input type='hidden' id='sortSt' value="${fn:escapeXml(SORTORDERPARAM)}"/>
		   <input type='hidden' id='INV111' value="${fn:escapeXml(SRCHCRITERIAEVENTTYPE)}"/>
		   <input type='hidden' id='INV222' value="${fn:escapeXml(SRCHCRITERIAACTION)}"/>
		   <input type='hidden' id='INV333' value="${fn:escapeXml(SRCHCRITERIALASTUPDATED)}"/>
		   <input type='hidden' id='INV444' value="${fn:escapeXml(SRCHCRITERIASTATUS)}"/>
	
	  </html:form>
	 </div>
    </div>
    <%@ include file="/jsp/footer.jsp" %>
  </body>
  <!--Note: INV111 is EventType, INV222 is Action, INV333 is Status -->
</html>