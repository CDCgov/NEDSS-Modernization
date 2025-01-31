<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ include file="../../jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Rules</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="/nbs/dwr/interface/JManageRulesForm.js"></script>
        
    </head>
        <%
            Long waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid"); 
        %>
         <script type="text/javascript">

         function addBusinessRule()
         {   
             document.forms[0].action ="/nbs/ManageRules.do?method=addBusinessRule";
             document.forms[0].submit();
             return true;
         }
         function viewBusinessRule(id)
         {             
             document.forms[0].action ="/nbs/ManageRules.do?method=viewBuinessRule&waRuleMetadataUid="+id;
             document.forms[0].submit();
             return true;
         }
         function editBusinessRule(id)
         {
             
             document.forms[0].action ="/nbs/ManageRules.do?method=editBusinessRule&waRuleMetadataUid="+id;
             document.forms[0].submit();
             return true;
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
            document.forms[0].action ='/nbs/ManageRules.do?method=filterPageLibrarySubmit';
            document.forms[0].submit();
         }            
         function clearFilter()
         {
             document.forms[0].action ="/nbs/ManageRules.do?method=viewRulesList&existing=true&initLoad=true";
             document.forms[0].submit();                                    
         }
        function cancelFilter(key) {                    
          key1 = key.substring(key.indexOf("(")+1, key.indexOf(")")); 
          JManageRulesForm.getAnswerArray(key1, function(data) {                      
              revertOldSelections(key, data);
          });         
        }
         
         function attachIcons() {    
             $j("#parent thead tr th a").each(function(i) {                 
                 if($j(this).html() == 'Function'){                       
                      $j(this).parent().append($j("#function"));                     
                 }
                 if($j(this).html() == 'Source Field'){                   
                      $j(this).parent().append($j("#sourceField"));
                 }                 
                 if($j(this).html() == 'Logic'){                          
              $j(this).parent().append($j("#logic"));
                 }
                 if($j(this).html() == 'Value(s)'){                           
              $j(this).parent().append($j("#values"));
                 }
                 if($j(this).html() == 'Target Field(s)'){  
              $j(this).parent().append($j("#targetField"));
                 }
                 }); 
             $j("#parent").before($j("#whitebar"));
             $j("#parent").before($j("#removeFilters"));
         }
         
         function makeMSelects() {                         
              $j("#function").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});              
              $j("#sourceField").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
              $j("#values").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
              $j("#logic").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
              $j("#targetField").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
              
         }
                 
         function displayTooltips() {        
             $j(".sortable a").each(function(i) {
             
                 var headerNm = $j(this).html();
                 var INV111 = getElementByIdOrByName("INV111") == null ? "" : getElementByIdOrByName("INV111").value;
                 var INV222 = getElementByIdOrByName("INV222") == null ? "" : getElementByIdOrByName("INV222").value;
                 var INV333 = getElementByIdOrByName("INV333") == null ? "" : getElementByIdOrByName("INV333").value;
                 var INV444 = getElementByIdOrByName("INV444") == null ? "" : getElementByIdOrByName("INV444").value;
                 var INV555 = getElementByIdOrByName("INV555") == null ? "" : getElementByIdOrByName("INV555").value;
                 var INV666 = getElementByIdOrByName("INV666") == null ? "" : getElementByIdOrByName("INV666").value;
                 
                  if(headerNm =='Function') {
                      _setAttributes(headerNm, $j(this), INV111);
                   } 
                  if(headerNm == 'Source Field') {                      
                      _setAttributes(headerNm, $j(this), INV222);
                   }
                  if(headerNm == 'Value(s)') {
                      _setAttributes(headerNm, $j(this), INV333);
                   }                  
                  if(headerNm == 'Logic') {
                      _setAttributes(headerNm, $j(this), INV444);
                   }
                  if(headerNm == 'Target Field(s)') {
                      _setAttributes(headerNm, $j(this), INV555);
                   }
                  if(headerNm =='ID') {
                      _showAtoZIcon(headerNm, $j(this), INV666);
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

                 if(sortFirstStr != null && sortFirstStr.indexOf(headerNm) != -1 ){
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
         function printQueue() {           
             window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
         }
         function exportQueue() {
                 window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
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
     <%    
          String messageInd =  (request.getAttribute("messageInd") == null) ? "" : ((String)request.getAttribute("messageInd"));     
          String templateType =(request.getAttribute("templateType") == null) ? "" : ((String)request.getAttribute("templateType")); //Published
      %> 
    <body  onload="attachIcons();makeMSelects();showCount();displayTooltips();">
        <div id="Add Rule"></div>
        <div id="blockparent"></div>

        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
                
          <html:form action="/ManageRules.do">
                <div id="bd" style="text-align:center;">
                    <!-- Top Nav Bar and top button bar -->
                    <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                                <div id="bd" style="text-align:center;">               
                                    <div align="right">
                                    <%if("TEMPLATE".equalsIgnoreCase(templateType)){ %>
                                        <a href="/nbs/PreviewTemplate.do?method=viewTemplate&templateUid=<%= waTemplateUid%>&method=viewPageLoad">Return to View Template </a>
                                   <% } else {%> 
                                     <a href="/nbs/PreviewPage.do?waTemplateUid=<%= waTemplateUid%>&method=viewPageLoad">Return to View Page </a>
                                     <%} %>
                                </div>                     
                                <div class="grayButtonBar" style="text-align: right;">
                                  <%if(!"Published".equalsIgnoreCase(templateType) && !"TEMPLATE".equalsIgnoreCase(templateType)){ %>
                                        <input type="button" name="Add New" value="Add New" onclick="addBusinessRule();"/>
                                   <% } %> 
                                  <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
                                  <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
                                </div>                        

                    <!-- Body contents -->
        <%@ include file="/jsp/feedbackMessagesBar.jsp" %>                    
        <%if("DELETE".equalsIgnoreCase(messageInd)){ %>
	        <div class="infoBox success" style="text-align: left;">
	            Rule <bean:write name="manageRulesForm" property="ruleId"  /> has been successfully deleted from the system.
	        </div>
        <% } %> 
                    <nedss:container id="section2" name="Rules" classType="sect" includeBackToTopLink="false" displayImg ="false" displayLink="false">
                        <fieldset style="border-width:0px;" id="result">
                            <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/> 
                         
                            <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right">
                            </div>
                             <div class="removefilter" id="removeFilters">
                                     <a class="removefilerLink" href="javascript:clearFilter()">
                                         <font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font>
                                     </a>
                             </div>                     
                            <table role="presentation" width="98%">
                                        <tr>
                                            <td align="center">
                                                <display:table name="manageFormList" class="dtTable"  pagesize="${manageRulesForm.attributeMap.queueSize}"  id="parent"
                                                 sort="external" export="true" requestURI="/ManageRules.do?method=viewRulesList&existing=true&initLoad=true"  
                                                 excludedParams="*">
                                                    <display:setProperty name="export.csv.filename" value="ManageRulesLibrary.csv"/>
			                    					<display:setProperty name="export.pdf.filename" value="ManageRulesLibrary.pdf"/>	
                                                    <display:column property="viewLink" title="<p style='display:none'>View</p>" media="html" style="width:3%;text-align:center;"/>
                                                     <%if(!"Published".equalsIgnoreCase(templateType) && !"TEMPLATE".equalsIgnoreCase(templateType)){ %>
                                                        <display:column property="editLink" title="<p style='display:none'>Edit</p>" media="html" style="width:3%;text-align:center;"/>
                                                     <% } %>                                              
                                                   <display:column property="ruleCd" sortName="getRuleCd" sortable="true" style="width:10%;text-align:left;" defaultorder="ascending" title="Function"/>
                                                   <display:column property="sourceField" sortName="getSourceField" style="width:25%;text-align:left;" sortable="true" defaultorder="ascending"  title="Source Field"/>
                                                   <display:column property="logicValues" sortName="getLogicValues" style="width:7.5%;text-align:left;" sortable="true" defaultorder="ascending" title="Logic"/>  
                                                   <display:column property="sourceValues" sortName="getSourceValues" style="width:15%;text-align:left;" sortable="true" defaultorder="ascending" title="Value(s)"/>  
                                                   <display:column property="targetField" sortName="getTargetField" media="html" style="width:25%;text-align:left;" sortable="true" defaultorder="ascending" title="Target Field(s)"/>  
                                                   <display:column property="targetFieldForPrint" sortName="getTargetField" media="csv pdf" sortable="true" defaultorder="ascending" title="Target Field(s)"/>
                                                   <display:column property="waRuleMetadataUid"  sortName="getWaRuleMetadataUid" sortable="true" style="width:7.5%;text-align:left;" defaultorder="ascending" title="ID" />                                                      
                                                  <display:setProperty name="basic.empty.showtable" value="true"/>
                                                </display:table>
                                            </td>
                                        </tr>
                             </table>
                        </fieldset>
                    </nedss:container>  
                  </div>  
                    <html:hidden styleId="sortSt" property="attributeMap.searchCriteria.sortSt"/>    
                    <html:hidden styleId="INV111" property="attributeMap.searchCriteria.INV111"/>
                    <html:hidden styleId="INV222" property="attributeMap.searchCriteria.INV222"/>   
                    <html:hidden styleId="INV333" property="attributeMap.searchCriteria.INV333"/>   
                    <html:hidden styleId="INV444" property="attributeMap.searchCriteria.INV444"/>   
                    <html:hidden styleId="INV555" property="attributeMap.searchCriteria.INV555"/>
                    <html:select property="answerArray(FUNCTION)" styleId = "function" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
                       <html:optionsCollection property="function" value="key" label="value" style="width:180"/>
                    </html:select>                  
                    <html:select property="answerArray(SOURCEFIELDS)" styleId = "sourceField" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
                        <html:optionsCollection property="sourceFields" value="key" label="value" style="width:180"/>
                    </html:select>
                      <html:select property="answerArray(SOURCEVALUES)" styleId = "values" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
                           <html:optionsCollection property="sourceValues" value="key" label="value" style="width:180"/>
                      </html:select>
                            <html:select property="answerArray(LOGICVALUES)" styleId = "logic" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
                           <html:optionsCollection property="logicValues" value="key" label="value" style="width:180"/>
                      </html:select>
                      <html:select property="answerArray(TARGETFIELDS)" styleId = "targetField" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
                           <html:optionsCollection property="targetFields" value="key" label="value" style="width:180"/>
                      </html:select>                  
                 </div>
            </html:form>
        </div>

         

        <!-- Footer div -->
        
        <!-- Bittom button bar -->   
                   <div class="grayButtonBar" style="text-align: right;">
                     <%if(!"Published".equalsIgnoreCase(templateType) && !"TEMPLATE".equalsIgnoreCase(templateType)){ %>
                          <input type="button" name="Add New" value="Add New" onclick="addBusinessRule();"/>
                     <% } %>
                  <input type="button"  value="Print" id=" " onclick="printQueue();"/> 
                  <input type="button"  value="Download" id=" " onclick="exportQueue();"/> 
                </div>  
            
    </body>
</html>