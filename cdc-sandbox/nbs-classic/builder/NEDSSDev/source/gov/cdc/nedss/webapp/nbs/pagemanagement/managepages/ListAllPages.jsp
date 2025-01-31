<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Pages</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>        
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="/nbs/dwr/interface/JPageBuilder.js"></script>
        <script type="text/javascript">

        //To avoid double click on Edit investigation
        $j( document ).ready(function() {
 		   $j('a').click(function(){
 			   for (var i= document.links.length; i-->0;)
 					document.links[i].setAttribute('style', 'pointer-events: none;');
 			});
 		});

		/**
		* linksAvailable(): makes the links clickable
		*/
		
        function linksAvailable(){			
 			   for (var i= document.links.length; i-->0;)
 					document.links[i].setAttribute('style', 'pointer-events: cursor;');
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
		
		
		
		function onClickViewLink(url)
		{
			// call the JS function to block the UI while saving is on progress.
			blockUIDuringFormSubmissionNoGraphic();
			window.location=url;
		}
		function onClickEditLink(url)
		{
			// call the JS function to block the UI while saving is on progress.
			blockUIDuringFormSubmissionNoGraphic();
            document.forms[0].action= url;
            document.forms[0].submit();  
		}		
        function draftPopUp(inValue, uid){
             var message = ' You have indicated that you would like to edit the ' + inValue +' page. This page does not currently have a draft version. Click OK to create'+
                           ' a draft version of this page and to proceed to Edit Page. Click Cancel to return to Page Library.';
            if(confirm(message)){            	
               document.forms[0].action ="/nbs/ManagePage.do?method=createNewDraft&fromWhere='ND'&waTemplateUid="+uid;
               document.forms[0].submit();
             return true;
            
            }
			else
				linksAvailable();
                
        }              
        function addDiseaseForm()
        {
        	   document.forms[0].action ="/nbs/DiseaseFormBuilder.do?method=createDiseaseFormLoad";
        }
    
        function popUp(URL) {
                 day = new Date();
                 id = day.getTime();
                 eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=1,location=0,statusbar=1,menubar=0,width=800,height=700,left = 112,top = 34');");
        }

        function managePagePorting()
        {
        	document.forms[0].action ="/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true";
            document.forms[0].submit();
        }        
        function addPage()
        {
                      document.forms[0].action ="/nbs/ManagePage.do?method=addPageLoad";
                      document.forms[0].submit();
        }
                 
        function cancelForm()
        {
               document.forms[0].action ="/nbs/ManagePage.do?method=list";
               document.forms[0].submit();
               return true;
        }
        function printQueue() {           
                window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
                window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
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

        function selectfilterCriteria()
        {      
           document.forms[0].action ='/nbs/ManagePage.do?method=filterPageLibrarySubmit';
           document.forms[0].submit();
        }            
        function clearFilter()
        {
            document.forms[0].action ="/nbs/ManagePage.do?method=list&initLoad=true";
            document.forms[0].submit();                                    
        }

        function cancelFilter(key) {                    
          key1 = key.substring(key.indexOf("(")+1, key.indexOf(")")); 
          JPageBuilder.getAnswerArray(key1, function(data) {                      
              revertOldSelections(key, data);
          });         
        }
        function attachIcons() {    
            $j("#parent thead tr th a").each(function(i) {  
                if($j(this).html() == 'Last Updated'){         
                     $j(this).parent().append($j("#lastUpdated"));
                }
				if($j(this).html() == 'Event Type'){         
                     $j(this).parent().append($j("#busObjType"));
                }
                if($j(this).html() == 'Last Updated By'){         
                     $j(this).parent().append($j("#lastUpdatedBy"));
                }
                if($j(this).html() == 'Page State'){
                     $j(this).parent().append($j("#status"));
                }      
                if($j(this).html() == 'Page Name'){
                    $j(this).parent().append($j("#templateNm"));  
                }
                if($j(this).html() == 'Related Condition(s)'){
                    $j(this).parent().append($j("#relatedConditions"));  
                }                  
                }); 
            $j("#parent").before($j("#whitebar"));
            $j("#parent").before($j("#removeFilters"));
        }
        
        function makeMSelects() {
             $j("#busObjType").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
             $j("#lastUpdated").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
             $j("#lastUpdatedBy").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
             $j("#status").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
             $j("#templateNm").text({actionMode: '${fn:escapeXml(ActionMode)}'});
             $j("#relatedConditions").text({actionMode: '${fn:escapeXml(ActionMode)}'});
        }
                
        function displayTooltips() {        
            $j(".sortable a").each(function(i) {
            
                var headerNm = $j(this).html();
                var sortSt = getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
                var INV111 = getElementByIdOrByName("INV111") == null ? "" : getElementByIdOrByName("INV111").value;
                var INV222 = getElementByIdOrByName("INV222") == null ? "" : getElementByIdOrByName("INV222").value;
                var INV333 = getElementByIdOrByName("INV333") == null ? "" : getElementByIdOrByName("INV333").value;
                var INV444 = getElementByIdOrByName("INV444") == null ? "" : getElementByIdOrByName("INV444").value;
                var INV555 = getElementByIdOrByName("INV555") == null ? "" : getElementByIdOrByName("INV555").value;
                var INV666 = getElementByIdOrByName("INV666") == null ? "" : getElementByIdOrByName("INV666").value;
			    
                 if(headerNm =='Page Name') {
                	 _setAttributes(headerNm, $j(this), INV111);
                  }  
                  if(headerNm =='Related Condition(s)') {
                	 _setAttributes(headerNm, $j(this), INV555);
                  }                
                 if(headerNm =='Last Updated') {
                      _setAttributes(headerNm, $j(this), INV222);
                  } 
                 if(headerNm == 'Last Updated By') {
                      _setAttributes(headerNm, $j(this), INV333);
                  }                                                     
                 if(headerNm == 'Page State') {
                      _setAttributes(headerNm, $j(this), INV444);
                  } 
                  if(headerNm == 'Event Type') {
                      _setAttributes(headerNm, $j(this), INV666);
                  }  
     
            });             
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

        function _setAttributes(headerNm, link, colId) {
            
            var imgObj = link.parent().find("img");
            var toolTip = "";   
            var sortSt =  getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;
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
        </script>       
        <style type="text/css">
            table.dtTable {margin-top:0em;}
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
    <body onload="attachIcons();makeMSelects();showCount();displayTooltips();startCountdown();">    
    <div id="blockparent"></div>
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
               
            <div  style="text-align:right; margin-bottom:8px;">
                        <a id="manageLink" href="/nbs/SystemAdmin.do?focus=systemAdmin4">
                            Return to System Management Main Menu
                     </a>
                </div>
                <!-- Top button bar -->
                <div class="grayButtonBar" style="text-align: right;">
                  <input type="button" name="Submit" value="Page Porting" onclick="managePagePorting();"/>	
                  <input type="button" name="Submit" value="Add New" onclick="addPage();"/>
                  <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
                  <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
                </div>
                
                 <% if(request.getAttribute("deleteMsg") != null){%>
	                 <div class="infoBox success" style="text-align: left;">${fn:escapeXml(deleteMsg)}
<%-- 	                 <%= request.getAttribute("deleteMsg") %>  --%>
	                 </div>  
                 <%}%>
                 <div class="infoBox messages" style="text-align: left;">
                       To access the legacy Custom Fields Admin main menu, <a href='/nbs/LDFAdminLoad.do'> <u>click here.</u></a>
                 </div>    
                                
               <html:form action="/ManagePage.do">
                           
                <nedss:container id="section2" name="Page Library" classType="sect" displayImg ="false" displayLink="false" includeBackToTopLink="no">
                    <fieldset style="border-width:0px;" id="result">
                    <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
                          <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
                    <div class="removefilter" id="removeFilters">
                    <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
                </div>
                        <table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">                                                                         
                                    <display:table name="manageFormList" class="dtTable" pagesize="${pageBuilderForm.attributeMap.queueSize}"  id="parent" requestURI="/nbs/ManagePage.do?method=list&existing=true&initLoad=true" 
                                        sort="external" export="true" excludedParams="answerArray(LASTUPDATED) answerArray(LASTUPDATEDBY) answerArray(STATUS) answerArrayText(SearchText1) answerArrayText(SearchText2) answerArray(BUSOBJTYPE) method">
                                        <display:setProperty name="export.csv.filename" value="PageLibrary.csv"/>
                        <display:setProperty name="export.pdf.filename" value="PageLibrary.pdf"/>                                        
                                        <display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:3%;text-align:center;"/>
                                        <display:column property="editLink" title="<p style='display:none'>Edit</p>" media="html" style="width:3%;text-align:center;"/>
                                        <display:column property="busObjType" title="Event Type" 
                                                        sortable="true" sortName="getBusObjType" defaultorder="ascending"/>
                                        <display:column property="templateNm" title="Page Name" 
                                                        sortable="true" sortName="getTemplateNm" defaultorder="ascending"/>
                                        <display:column property="templateType" title="Page State" 
                                                        sortable="true" sortName="getTemplateType" defaultorder="ascending"/>
                                        <display:column title="Related Condition(s)" media="html"
                                                        sortable="true" sortName="getRelatedConditions" defaultorder="ascending">
							            	<div align="left" TITLE="${parent.relatedConditionsForPrint}" >
							                    ${parent.relatedConditions}
							                </div>
							            </display:column>
                                        <display:column property="relatedConditionsForPrint" sortName="getRelatedConditionsForPrint" media="csv pdf" 
                                        				sortable="true" defaultorder="ascending" title="Related Condition(s)"/>
                                        <display:column property="lastChgTime" title="Last Updated"  format="{0,date,MM/dd/yyyy}" 
                                                        sortable="true" sortName="getLastChgTime" defaultorder="ascending"/>
                                        <display:column property="firstLastName" title="Last Updated By" 
                                                        sortable="true" sortName="getFirstLastName" defaultorder="ascending"/>
                                         <display:setProperty name="basic.empty.showtable" value="true"/>
                                    </display:table>
                                </td>
                            </tr>                           
                     </table>
                    </fieldset>
                </nedss:container>

                    <!-- Bottom button bar -->
                <div class="grayButtonBar" style="text-align: right;">
                	<input type="button" name="Submit" value="Page Porting" onclick="managePagePorting();"/>
                    <input type="button" name="Submit" value="Add New" onclick="addPage();"/>
                    <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
                    <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
                </div>
                 <jsp:include page="ManagePageLibraryDropDown.jsp" />
                 <html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>	
                 <html:hidden styleId="INV111" property="attributeMap.searchCriteria.INV111"/>	
				<html:hidden styleId="INV222" property="attributeMap.searchCriteria.INV222"/>	
				<html:hidden styleId="INV333" property="attributeMap.searchCriteria.INV333"/>	
				<html:hidden styleId="INV444" property="attributeMap.searchCriteria.INV444"/>	
				<html:hidden styleId="INV555" property="attributeMap.searchCriteria.INV555"/>
				<html:hidden styleId="INV666" property="attributeMap.searchCriteria.INV666"/>
				<html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.INV111"/>
			    <html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.INV555"/>
               </html:form>
            </div>
        </div>

        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>