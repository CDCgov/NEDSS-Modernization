<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>

<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>

<html lang="en">
  <head>
    <title>${fn:escapeXml(PageTitle)}</title>
    <%@ include file="/jsp/resources.jsp" %> 
    <script language="JavaScript" src="jquery.dimensions.js"></script>
    <script language="JavaScript" src="jquery-ui-1.6.custom.min.js"></script> 
    <script src="supervisorReviewQ.js"></script>
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
       .hover{ background-color: #CFCFCF }
       #blockparent {
	     display:none;
	     position:absolute;
	     left:0px;
	     top:0px;
	     width:100%;
	     height:100%;
	     background:gray;
	     filter:alpha(Opacity=80);
	     opacity:0.5;
	     -moz-opacity:0.5;
	     -khtml-opacity:0.5;
	    }
	    .selectAll {
	      border-bottom: dotted 1px #CCC;
	      font-size : 9pt; 
        }
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
    
		function removeMargin(){
			document.getElementById("whitebar").style.marginTop="0px";
		}
		
		
      var colHdrFilterKeyMap = { "Submit Date": "submitDate", "Patient": "patient", "Investigator": "investigator", "Supervisor": "supervisor", "Condition": "textCond", "Referral Basis": "referralBasis", "Type": "activityType"};
      var searchSortMap = {};
      <logic:iterate id="searchSort" name="supervisorReviewForm" property="attributeMap.searchCriteria">
        searchSortMap["${searchSort.key}"] = "${searchSort.value}";
      </logic:iterate>
      <logic:iterate id="searchSort" name="supervisorReviewForm" property="searchCriteriaArrayMap">
        searchSortMap["answerArray(${searchSort.key})"] = [];
        <logic:iterate id="searchSortObj"  collection="${searchSort.value}">
          searchSortMap["answerArray(${searchSort.key})"].push("${searchSortObj}");
        </logic:iterate> 
      </logic:iterate>
      
    </script>
  </head> 
  
  <body onload="attachIcons();displayTooltips();showCount();removeMargin();">
    <div id="blockparent"></div>
    <div id="doc3">
       <html:form action="LoadSupervisorReviewQueue.do" >
        <input type="hidden" name="ContextAction" value="SupervisorReviewQueue"/>
        <input type="hidden" name="supervisorVO.publicHealthCaseUid" id="rejectPublicHealthCaseUid" value=""/>
        <input type="hidden" name="supervisorVO.caseReviewStatus" value=""/>
        <input type="hidden" name="method" value="loadQueue"/>
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
				<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;margin-top:1%" align="right"></div>
            
                  <display:table name="investigationList" class="dtTable" style="margin-top:0em;" pagesize="${supervisorReviewForm.attributeMap.queueSize}" id="parent"  requestURI="/LoadSupervisorReviewQueue.do?method=loadQueue&existing=true" sort="external" export="true" excludedParams="method existing initLoad">
                    <display:column title="<p style='display:none'>Approve</p>" style="width:2%; text-align:center;" media="html">
                      <img tabIndex="0" src="page_white_get.gif" class="cursorHand" title="Approve" alt="Approve" onkeypress="showRejectMessageDialog(${parent.publicHealthCaseUid}, 'Approval Notes')" onclick="showRejectMessageDialog(${parent.publicHealthCaseUid}, 'Approval Notes')"/>
                    </display:column>
                    <display:column title="<p style='display:none'>Reject</p>" style="width:2%;text-align:center;" media="html">
                      <img tabIndex="0" src="page_white_delete.gif" class="cursorHand" title="Reject" alt="Reject" onkeypress="showRejectMessageDialog(${parent.publicHealthCaseUid}, 'Reject Notes')" onclick="showRejectMessageDialog(${parent.publicHealthCaseUid}, 'Reject Notes')" />
                    </display:column>

                    <display:column property="submitDate" title="Submit Date" defaultorder="descending" sortable="true" 
                                    format="{0,date,MM/dd/yyyy}" style="width:12%;" sortName="getSubmitDate"/>
                    <display:column property="supervisorFullName" title="Supervisor" defaultorder="descending" sortable="true" sortName="getSupervisorFullName" style="width:12%;"/>
                    <display:column property="investigatorFullName" title="Investigator" sortable="true" sortName="getInvestigatorFullName" />
                    <display:column property="patientFullName" title="Patient" defaultorder="descending" sortable="true" sortName="getPatientFullName" 
                                    url="/LoadSupervisorReviewQueue.do?method=viewInvestigation&ContextAction=ViewFile"  
                                    paramId="MPRUid" paramProperty="MPRUid" class="make-it-post"
                                    style="width:12%;"/>
                    <display:column property="condition" title="Condition" sortable="true" class="make-it-post" sortName="getCondition"
                                    url="/LoadSupervisorReviewQueue.do?method=viewInvestigation&ContextAction=InvestigationID" 
                                    paramId="publicHealthCaseUID" paramProperty="publicHealthCaseUid"/>
                    <display:column property="referralBasisCd" title="Referral Basis" sortable="true" sortName="getReferralBasisCd"/>
                    <display:column property="activityType" title="Type" sortable="true" sortName="getActivityType"/>
                    <display:setProperty name="basic.empty.showtable" value="true"/>
                  </display:table>
                </td>
              </tr>
            </table>
            <div class="printexport" id="printExport" align="right">
              <img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
              <img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
            </div>
            <div class="removefilter" id="removeFilters">
              <a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
            </div>
          </div>
          <%-- Drop downs for search/filter of the column --%>
            <div id="filterTopDiv" style="width: 250; display: none" class="multiTextOptions1">
              <span style="display: block; text-align: center; background: #CFCFCF; white-space: nowrap; align: center; width: 100%">
                <input id="b1" class="button" type="button" title="Apply Filter" style="width: 40%;" value=" OK " onclick="selectfilterCriteria()" />&nbsp;
                <input id="b2" class="button" type="button" title="Cancel Filter" style="width: 40%;" value="Cancel" onclick="hideFilters()" />
              </span> 
              <br/>
              <div id="submitDate" style="z-index: 99999; display: none; width:98%">
                <table role="presentation" style="width: 100%">
                  <tr class="selectAll"><td class="selectAll">&nbsp;<input type="checkbox" title="Select/Deselect checkbox" id="submitDateSelectAll" class="selectAll" name="submitDateSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="supervisorReviewForm" property="dateFilterList">
                    <tr><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" name="answerArray(STARTDATE)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="textCond" style="z-index: 99999; display: none;width:98%">
                <table role="presentation" style="width: 100%">
                  <tr class="selectAll"><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" id="textCondSelectAll" class="selectAll" name="textCondSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="supervisorReviewForm" property="conditionList">
                    <tr><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" name="answerArray(CONDITION)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="patient" style="z-index: 99999; display:none;width:98%">
                <br/>        
                <label><b>Contains:</b></label>&nbsp;
                <label class="pText"><p style="display:none">Contains</p>
                  <input title="Contains" type="text" size="25" name="answerArray(PATIENT)" onkeyup="enableDisableOk('patient')" value="${fn:escapeXml(PATIENT)}"/>
                </label>
              </div>
              <div id="investigator" style="z-index: 99999; display:none;width:98%">
                <table role="presentation" style="width: 100%"> 
                  <tr class="selectAll"><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" id="investigatorSelectAll" class="selectAll" name="investigatorSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="supervisorReviewForm" property="investigatorNames">
                    <tr><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" name="answerArray(INVESTIGATOR)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="supervisor" style="z-index: 99999; display:none;width:98%">
                <table role="presentation" style="width: 100%"> 
                  <tr class="selectAll"><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" id="supervisorSelectAll" class="selectAll" name="supervisorSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="supervisorReviewForm" property="supervisorNames">
                    <tr><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" name="answerArray(SUPERVISOR)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="referralBasis" style="z-index: 99999; display:none;width:98%">
                <table role="presentation" style="width: 100%"> 
                  <tr class="selectAll"><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" id="referralBasisSelectAll" class="selectAll" name="referralBasisSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="supervisorReviewForm" property="referralBasisList">
                    <tr><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" name="answerArray(REFERRALBASIS)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <div id="activityType" style="z-index: 99999; display:none;width:98%">
                <table role="presentation" style="width: 100%"> 
                  <tr class="selectAll"><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" id="activityTypeSelectAll" class="selectAll" name="activityTypeSelectAll" />(Select All)</td></tr>
                  <logic:iterate id="iterObj" name="supervisorReviewForm" property="typeList">
                    <tr><td>&nbsp;<input type="checkbox" title="Select/Deselect checkbox" name="answerArray(TYPE)" value="${iterObj.key}"/>${iterObj.value}</td></tr>
                  </logic:iterate>
                </table>
              </div>
              <br/>
              <span style="display:block; text-align:center; background:#CFCFCF; white-space:nowrap; align:center; width:100%">
                <input id="b3" class="button" type="button" title="Apply Filter" style="width:40%;" value=" OK " onclick="selectfilterCriteria()">
                <input id="b4" class="button" type="button" title="Cancel Filter" style="width:40%;" value="Cancel" onclick="hideFilters()">
              </span>
            </div>
          <div id="rejectMessageDiv" class="multiTextOptions1" style="width:30%; height:150; display:none">
            <b><span id="supervisorCommentsHeading">Reject Reason:</span></b><br/>
            &nbsp;<textarea rows="4" title="Notes" name="supervisorVO.comments" style="width:95%"></textarea>
            <br/><br/>
            <span>&nbsp;<input class="button" type="button" name="ok" onclick="submitSupervisorNotes()" value="&nbsp;OK&nbsp;">&nbsp;&nbsp;
            <input type="button" class="button" name="cancel" value="Cancel" onclick="closeRejectMessageDialog()"></span>
          </div>
           <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
        </html:form>
        <form name="initialForm" id="initialForm" action="LoadSupervisorReviewQueue.do" method="post">
          <input type="hidden" name="method" value="loadQueue"/>
          <input type="hidden" name="ContextAction" value="SupervisorReviewQueue"/>
          <input type="hidden" name="initLoad" value="true"/>
        </form>
     </div> 
   </body>
</html>
