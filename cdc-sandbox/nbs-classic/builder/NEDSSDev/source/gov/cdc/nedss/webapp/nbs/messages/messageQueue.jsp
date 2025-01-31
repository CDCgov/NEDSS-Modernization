<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>
<%@ page import="gov.cdc.nedss.proxy.ejb.queue.*"%>

<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>

<html lang="en">
  <head>
    <title>${fn:escapeXml(PageTitle)}</title>
    <%@ include file="/jsp/resources.jsp" %> 
    <script language="JavaScript" src="jquery.dimensions.js"></script>
    <script language="JavaScript" src="jquery-ui-1.6.custom.min.js"></script> 
    <script src="messageQ.js"></script>
    <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
      .removefilter{
        background-color:#003470; width:100%; height:25px;
        line-height:25px;float:right;text-align:right;
      }
      .removefilerLink {vertical-align:bottom;  }
      .hyperLink {
        font-size : 10pt;
        font-family : Geneva, Arial, Helvetica, sans-serif;
        color : #FFFFFF;
        text-decoration: none;
       }
       .w100 { width: 100% }
       .selectAll {
         border-bottom: dotted 1px #CCC;
       }
       .hover{ background-color: #CFCFCF }
       .multiTextOptions1 {
          position:absolute;
          z-index:230;
          margin-top: -1px;  
          overflow: auto;
          height:130px;
          text-align:left; 
          background-color: #FFFFFF;
          border: #E3E3E3 3px solid;     
       }
    </style>
    <script>
    
    blockEnterKey();
	
      var colHdrFilterKeyMap = { "Date": "sdate", "Patient": "patient", "Submitted By": "submittedBy", "New?": "messageStatus", "Condition": "testCond", "Message": "messageText"};
      var searchSortMap = {};
      <logic:iterate id="searchSort" name="messageLogForm" property="attributeMap.searchCriteria">
        searchSortMap["${searchSort.key}"] = "${searchSort.value}";
      </logic:iterate>
      <logic:iterate id="searchSort" name="messageLogForm" property="searchCriteriaArrayMap">
        searchSortMap["answerArray(${searchSort.key})"] = [];
        <logic:iterate id="searchSortObj"  collection="${searchSort.value}">
          searchSortMap["answerArray(${searchSort.key})"].push("${searchSortObj}");
        </logic:iterate> 
      </logic:iterate> 
      
      function checkAll() {
		     var checkboxes = document.getElementsByTagName('input');
		     var isCondition='${fn:escapeXml(CONDITIONFILTER)}';
		     for (var i = 0; i < checkboxes.length; i++) {
		        	if(checkboxes[i].type == "checkbox" && !checkboxes[i].disabled && isCondition !="True"){
		                 checkboxes[i].checked = true;
		        	}
		    	}
		   	} 
    </script>
  </head> 
  
  <body onload="attachIcons();displayTooltips();showCount();checkAll();">
    <div id="blockparent"></div>
    <div id="processingRequest" class="multiTextOptions" style="display: none">
      Processing your request...Please wait....
    </div>
    <div id="doc3">
      <html:form action="/LoadMessageQueue.do" >
        <input type="hidden" name="deleteMessageLogUid" id="deleteMessageLogUid" value=""/>
        <input type="hidden" name="ContextAction" value="MessageQueue"/>
        <div id="bd">
          <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
            <div class="printexport" id="printExport" align="right">
                <img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
                <img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
            </div>
            <%
              String apprRejMsg = request.getAttribute("confirmationMessage") == null ? "" : (String) request.getAttribute("confirmationMessage");
              if( apprRejMsg != "") { %>
                <div class="infoBox success">
                     <%= apprRejMsg %>
                </div>
              <%}%>

            <table role="presentation" class="w100">
              <tr> 
                <td align="center">
                  <display:table name="messageList" class="dtTable" style="margin-top:0em;" pagesize="${messageLogForm.attributeMap.queueSize}" id="parent" requestURI="/LoadMessageQueue.do?method=loadQueue&existing=true" sort="external" export="true" excludedParams="method">
                    <display:column title="<p style='display:none'>Email</p>" style="width:2%; text-align:center;" media="html">
                    
                      <logic:equal name="parent" property="messageLogDT.messageStatusCd" value="R">
                        <img src="email-icon.gif" alt="Email" title="Email" border="0" height="15" width="15" id="igMsgStatus${parent.messageLogDT.messageLogUid}"/>
                      </logic:equal>
                      <logic:notEqual name="parent" property="messageLogDT.messageStatusCd" value="R">
                        <img tabIndex="0" src="page_white_get.gif" border="0" height="15" width="15" id="igMsgStatus${parent.messageLogDT.messageLogUid}" class="cursorHand" title="Mark as Read" alt = "Mark as Read" onkeypress='markMessageRead(${parent.messageLogDT.messageLogUid})' onclick='markMessageRead(${parent.messageLogDT.messageLogUid})'/>
                      </logic:notEqual>
                    </display:column>
                    <display:column title="<p style='display:none'>Delete</p>" style="width:2%;text-align:center;"  media="html">
                      <img tabIndex="0" src="page_white_delete.gif" class="cursorHand" title="Delete" alt = "Delete" onkeypress="submitDeleteForm(${parent.messageLogDT.messageLogUid})" onclick="submitDeleteForm(${parent.messageLogDT.messageLogUid})" />
                    </display:column>
                    <display:column property="messageStatus" title="New?" defaultorder="descending" sortable="true" sortName= "getMessageStatus" style="width:11%;" />
                    <display:column property="messageLogDT.addTime" title="Date" defaultorder="ascending" sortable="true" sortName="getAddTime" format="{0,date,MM/dd/yyyy}"  style="width:12%;"/>
                    <display:column property="userFullName" title="Submitted By" defaultorder="descending" sortable="true" sortName="getUserFullName" style="width:12%;"/>
                    <display:column property="messageLogDT.messageTxt" title="Message" sortable="true" sortName="getMessageTxt"/>
                    <display:column property="fullName" title="Patient" defaultorder="descending" sortable="true" sortName="getFullName" style="width:12%;"/>
                    <display:column property="messageLogDT.conditionCd" title="Condition" 
                    url="/MessageQueueSubmit.do?ContextAction=InvestigationID"  
                                    paramId="publicHealthCaseUID" paramProperty="messageLogDT.eventUid" class="make-it-post"
                                     sortable="true" sortName="getConditionCd"/>
                    <display:setProperty name="basic.empty.showtable" value="true"/>
                  </display:table>
                </td>
              </tr>
            </table>
            <div class="printexport" id="printExport" align="right">
              <img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
              <img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
            </div>
            <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
            <div class="removefilter" id="removeFilters">
              <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
            </div>
          </div>
          <%-- Drop downs for search/filter of the column --%>
            <div id="filterTopDiv" style="display: none; width: 250" class="multiTextOptions1"> 
              <span style="display: block; text-align: center; background: #CFCFCF; white-space: nowrap; align: center; width: 100%; margin-top: 5px">
                <input id="b1" class="button" type="button" title="Apply Filter" style="width: 40%;" value=" OK " onclick="selectfilterCriteria()" />&nbsp;
                <input id="b2" class="button" type="button" title="Cancel Filter" style="width: 40%;" value="Cancel" onclick="hideFilters()" />
              </span> 
              <br/>
              <div id="messageStatus" style="z-index: 99999; display: none; width: 97%">
                <table role="presentation" style="width: 100%">
                  <tr class="selectAll"><td class="selectAll">&nbsp;<input title="Select/Deselect checkbox" class="selectAll" id="messageStatusSelectAll" type="checkbox"  name="messageStatusSelectAll" /><label>(Select All)</label></td></tr>
                  <logic:iterate id="iterObj" name="messageLogForm" property="statusList">
                    <tr><td>&nbsp;<input title="Select/Deselect checkbox" type="checkbox" name="answerArray(MESSAGESTATUS)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="sdate" style="z-index: 99999; display: none; width:97%; border-color: green; border: 1">
                <table role="presentation" style="width: 100%">
                  <tr class="selectAll"><td class="selectAll">&nbsp;<input title="Select/Deselect checkbox" type="checkbox" id="sdateSelectAll" class="selectAll" name="sdateSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="messageLogForm" property="dateFilterList" indexId="index">
                    <tr><td>&nbsp;<input title="Select/Deselect checkbox" type="checkbox" name="answerArray(STARTDATE)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="testCond" style="z-index: 99999; display: none;width:97%">
                <table role="presentation" style="width: 100%">
                  <tr class="selectAll"><td class="selectAll">&nbsp;<input title="Select/Deselect checkbox" type="checkbox" id="testCondSelectAll" class="selectAll" name="testCondSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="messageLogForm" property="conditionList">
                    <tr><td>&nbsp;<input title="Select/Deselect checkbox" type="checkbox" name="answerArray(CONDITION)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="patient" style="z-index: 99999; display:none;width:97%">
                <br/>        
                <label><b>Contains:</b></label>&nbsp;
                <label class="pText"><p style="display:none">Contains</p>
                  <input title="Contains" type="text" size="25" name="answerArray(PATIENT)" onKeyUp="enableDisableOk('patient')" value="${fn:escapeXml(PATIENT)}"/>
                </label>
              </div>
              <div id="submittedBy" style="z-index: 99999; display:none;width:97%">
                <br/>        
                <label><b>Contains:</b></label>&nbsp;
                <label class="pText"><p style="display:none">Contains</p>
                  <input title="Contains" type="text" size="25" name="answerArray(SUBMITEDBY)" onKeyUp="enableDisableOk('submittedBy')" value="<%=request.getAttribute("SUBMITEDBY")!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("SUBMITEDBY"))): ""%>"/>
                </label>
              </div>
              <div id="messageText" style="z-index: 99999; display:none;width:97%">
                <br/>        
                <label><b>Contains:</b></label>&nbsp;
                <label class="pText"><p style="display:none">Contains</p>
                  <input title="Contains" type="text" size="25" name="answerArray(MESSAGETEXT)" onKeyUp="enableDisableOk('messageText')" value="<%=request.getAttribute("MESSAGETEXT")!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("MESSAGETEXT"))): ""%>" />
                </label>
              </div>
              <br/>
              <span style="display:block; text-align:center; background:#CFCFCF; white-space:nowrap; align:center; width:100%">
                <input id="b3" class="button" type="button" title="Apply Filter" style="width:40%;" value=" OK " onclick="selectfilterCriteria()">
                <input id="b4" class="button" type="button" title="Cancel Filter" style="width:40%;" value="Cancel" onclick="hideFilters()">
              </span>
            </div>
             <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
      </html:form>
      <form name="initialForm" id="initialForm" action="/nbs/LoadMessageQueue.do" method="post">
          <input type="hidden" id="initalFormMethod" name="method" value="deleteMessage"/>
		  <input type="hidden" name="ContextAction" value="MessageQueue"/>
          <input type="hidden" name="initLoad" value="true"/>
          <input type="hidden" id="deleteMessageLogUid" name="messageLogVO.messageLogUid" value="-1"/>
      </form>
    </div> 
  </body>
</html>