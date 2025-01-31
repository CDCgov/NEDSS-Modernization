<html lang="en">
<head>
<%@ page import="gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT"%>
<%@ page import="java.util.*, gov.cdc.nedss.webapp.nbs.form.place.*"%>
<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<%@ include file="/jsp/resources.jsp"%>
<title>${placeForm.pageTitle}</title>

<script src="place.js"></script> 
<style>
 table.dtTable1 {
   width: 95%;
   margin-top: 5px;
 }
 table.dtTable1 thead tr th {
    background: none repeat scroll 0 0 #EFEFEF;
    border: 1px solid #BBBBBB;
    font-weight: bold;
    padding: 0.1em 0 0.1em 0.1em;
    text-align: left;
    text-decoration: none; 
 }
 table.dtTable1 tr td {
    background: none repeat scroll 0 0 white;
    border: 1px solid #BBBBBB; 
    padding: 0.1em 0 0.1em 0.1em;
    text-align: left;
    text-decoration: none;
 } 
</style>
<script>
  $j(document).ready(function(){
    placeTypeOther({"value": "${placeForm.place.thePlaceDT.cd}"});
    viewModePlace();
    if( $j("#tblAddresses tbody tr").length > 0 ){
      showHide([], ["trAddrNoRecords"] );
    }
    if( $j("#tblPhones tbody tr").length > 0 ){
      showHide([], ["trTelNoRecords"] );
    }
    if( $j("#errorMessages ul").length > 0 ) 
      $j("#errorMessages").show(); 
    if( $j("#successMessages").html() != null && $j("#successMessages").html().length > 0 )
      $j("#successMessages").show();
  });
</script>
</head>
<body> 
    <div>
      <logic:equal value="GlobalPlace" name="placeForm" property="contextAction" > 
             <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
        </logic:equal>
    </div> 
    <!-- Top button bar -->
    <div style="text-align: left;"><b>Place Id : ${placeForm.place.thePlaceDT.localId}</b> </div>
    <div class="grayButtonBar" style="margin-bottom: 4px;">
		<logic:equal value="GlobalPlace" name="placeForm" property="contextAction">
			<%
				if ("view".equalsIgnoreCase((String) request.getAttribute("mode"))) {
			%>
			<input type="button" value="Edit Place" id="btnEditT" onclick="editPlace();" />
			<input type="button" value="Add Place" id="btnAddT"	onclick="showAddPlace()" />
			<input type="button" value="Inactivate" id="btnInactivateT" onclick="inactivatePlace()" />
			<%
				} else {
			%>
			<input type="button" value="Submit" id="btnSubmitT"	onclick="addPlace();" />
			<%
				}
			%>
        	&nbsp;&nbsp;
         </logic:equal>
         <logic:notEqual value="GlobalPlace" name="placeForm" property="contextAction">
			<input type="button" value="select" id="btnSelectT" onclick="selectPlace('${placeForm.place.thePlaceDT.nm}','<%=request.getParameter("identifier")%>');" />
			&nbsp;&nbsp;
         </logic:notEqual>
	</div>
     
    <div style="width: 100%; text-align: center;">
        <div style="width: 100%;">
            <div id="globalFeedbackMessagesBar" class="screenOnly">
                <div class="infoBox errors" id="errorMessages" style="display:none">
                    <b class="errorMessagesHref">Please fix the following errors:</b><br />
                    <logic:messagesPresent name="error_messages">
                        <html:messages id="msg" name="error_messages">
                            <li><bean:write name="msg" /></li>
                        </html:messages>
                    </logic:messagesPresent>
                </div>
                <logic:messagesPresent name="success_messages">
                    <div class="infoBox success" id="successMessages" style="display:none"> 
                        <html:messages id="msg" name="success_messages">
                            <bean:write name="msg" filter="false" />
                        </html:messages>
                    </div>
                </logic:messagesPresent>
            </div>
            <html:form method="post" action="Place.do">
                <input type="hidden" name="method" value="show"/>
                <input type="hidden" name="mode" value="${fn:escapeXml(mode)}"/>
                <input type="hidden" name="ContextAction" value="${fn:escapeXml(ContextAction)}"/>
                <html:hidden name="placeForm" property="place.thePlaceDT.placeUid"/>
                
                <nedss:container id="section1" name="Place Demographics" classType="sect" displayImg="false" includeBackToTopLink="no">
                    <nedss:container id="subSec0" name="Administrative" classType="subSect" defaultDisplay="expand">
                        <tr>
                            <td class="fieldName" id="qac">Quick Access Code:</td>
                            <td><nedss:view name="placeForm" property="quick.rootExtensionTxt"/></td>
                        </tr> 
                        <tr>
                            <td class="fieldName">Type:</td>
                            <td>
                                <nedss:view name="placeForm" property="place.thePlaceDT.cd" codeSetNm="PLACE_TYPE"/> 
                            </td>
                        </tr>
                        <tr style="display:none" id="trPlaceTypeOther">
                            <td class="fieldName">Other Description:</td>
                            <td>
                                <nedss:view name="placeForm" property="place.thePlaceDT.cdDescTxt"/> 
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName">General Comments:</td>
                            <td>
                                <nedss:view name="placeForm" property="place.thePlaceDT.description"/> 
                            </td>
                        </tr>
                    </nedss:container>
                    
                    <nedss:container id="subSec1" name="Name" classType="subSect" defaultDisplay="expand">
                        <tr>
                            <td class="fieldName">Place Name:</td>
                            <td><nedss:view name="placeForm" property="place.thePlaceDT.nm"/></td>
                        </tr>
                    </nedss:container>

                    <nedss:container id="subSec2" name="Address" classType="subSect" defaultDisplay="expand">
                        <input type="hidden" name="addrIndex" value="0"/>
                        <tr>
                          <td colspan="2" align="center"> 
                            <table id="tblAddresses" class="dtTable1">
                              <thead>
                               <tr>
                                <td style="width:100px"></td> 
                                <th>As Of </th>
                                <th>Type</th>
                                <th>Use</th>
                                <th>Address</th>
                                <th>City</th>
                                <th>State</th>
                                <th>Zip</th>
                                <th>County</th>
                               </tr>
                               <tr id="trAddrNoRecords">
                                <td colspan='11'>No Data has been entered.</td>
                               </tr>
                             </thead>
                             <tbody> 
                             <logic:iterate name="placeForm" property="addressCollection" id="elp" indexId="id">
                               <tr id="tr${fn:escapeXml(id)}">
                                 <td>&nbsp;<a href="#" onclick="showAddressDetails(${fn:escapeXml(id)})">Details</a>&nbsp;</td>
                                 <td>${elp["asOfDate"]}</td>
                                 <td>${elp["cd"]}</td>
                                 <td>${elp["useCd"]}</td> 
                                 <td>${elp["thePostalLocatorDT.streetAddr1"]}</td>
                                 <td style="display:none">${elp["thePostalLocatorDT.streetAddr2"]}</td>
                                 <td>${elp["thePostalLocatorDT.cityDescTxt"]}</td>
                                 <td>${elp["thePostalLocatorDT.stateCd"]}</td>
                                 <td>${elp["thePostalLocatorDT.zipCd"]}</td>
                                 <td>${elp["thePostalLocatorDT.cntyCd"]}</td>
                                 <td style="display:none">${elp["thePostalLocatorDT.censusTract"]}</td>
                                 <td style="display:none">${elp["thePostalLocatorDT.cntryCd"]}</td>
                                 <td style="display:none">${elp["locatorDescTxt"]}</td>
                               </tr>
                             </logic:iterate> 
                             </tbody>
                            </table>
                          </td> 
                        </tr>
                        <tr id="trAddr1">
                          <td class="fieldName">Address Information As Of:</td>
                          <td id="addressAsOf"></td>    
                        </tr>
                        <tr id="trAddr2">
                          <td class="fieldName">Type:</td>
                          <td id="addressType"> 
                          </td>    
                        </tr> 
                        <tr id="trAddr3">
                          <td class="fieldName">Use:</td>
                          <td id="addressUse">
                          </td>   
                        </tr> 
                        <tr id="trAddr4">
                            <td class="fieldName">Street Address 1:</td>
                            <td></td>
                        </tr>
                        <tr id="trAddr5">
                            <td class="fieldName">Street Address 2:</td>
                            <td></td>
                        </tr>
                        <tr id="trAddr6">
                            <td class="fieldName">City:</td>
                            <td></td>
                        </tr>
                        <tr id="trAddr7">
                            <td class="fieldName">State:</td>
                            <td> </td>
                        </tr>
                        <tr id="trAddr8">
                            <td class="fieldName">Zip:</td>
                            <td></td>
                        </tr>
                        <tr id="trAddr9">
                            <td class="fieldName">County:</td>
                            <td>   
                            </td>
                        </tr>
                        <tr id="trAddr10">
                            <td class="fieldName">Census Tract:</td>
                            <td>   
                            </td>
                        </tr>
                        <tr id="trAddr11">
                            <td class="fieldName">Country:</td>
                            <td> 
                            </td>
                        </tr>
                        <tr id="trAddr12">
                            <td class="fieldName">Address Comments:</td>
                            <td>
                            </td>
                        </tr> 
                    </nedss:container>

                    <nedss:container id="subSec3" name="Phone, Email & URL" classType="subSect" defaultDisplay="expand"> 
                        <input type="hidden" name="teleIndex" value="0"/>
                        <tr>
                          <td colspan="2" align="center" width="100%"> 
                            <table id="tblPhones" class="dtTable1">
                               <thead><tr>
                                    <td style="width: 100px">&nbsp;</td>
                                    <th>As Of </th>
                                    <th>Type</th>
                                    <th>Use</th>
                                    <th>Phone Number</th>
                                    <th>Email Address</th>
									<th>URL</th>
                                    <th>Comments</th> 
                                </tr>
                                 <tr id="trTelNoRecords">
                                   <td colspan='11'>No Data has been entered.</td>
                                 </tr>
                              </thead>
                              <tbody>
                                 <logic:iterate name="placeForm" property="telephoneCollection" id="elp" indexId="id">
                                   <tr id="trT${fn:escapeXml(id)}">
                                     <td>&nbsp;<a href="#" onclick="showPhoneDetails(${fn:escapeXml(id)})">Details</a>&nbsp;</td>
                                     <td>${elp["asOfDate"]}</td>
                                     <td>${elp["cd"]}</td>
                                     <td>${elp["useCd"]}</td>  
                                     <td style="display:none">${elp["theTeleLocatorDT.cntryCd"]}</td>
                                     <td>${elp["theTeleLocatorDT.phoneNbrTxt"]}</td>
                                     <td style="display:none">${elp["theTeleLocatorDT.extensionTxt"]}</td>
                                     <td>${elp["theTeleLocatorDT.emailAddress"]}</td>
                                     <td>${elp["theTeleLocatorDT.urlAddress"]}</td>
                                     <td>${elp["locatorDescTxt"]}</td>
                                   </tr>
                                 </logic:iterate>
                              </tbody>
                            </table>
                          </td> 
                        </tr>
                        <tr id="trTel1">
                          <td class="fieldName"><span class="InputFieldLabel" id="PhoneAsOfL" title="Contact Information As Of">Contact Information As Of: </span>  </td>
                          <td id="phoneAsOf">   </td>    
                        </tr>
                        <tr id="trTel2">
                          <td class="fieldName">Type:</td>
                          <td id="addressType">   
                          </td>    
                        </tr> 
                        <tr id="trTel3">
                          <td class="fieldName">Use:</td>
                          <td id="addressUse">   
                          </td>   
                        </tr>
                        <tr id="trTel4">
                          <td class="fieldName">Country Code:</td>
                          <td></td>
                        </tr>
                        <tr id="trTel5">
                            <td class="fieldName">Telephone:</td>
                            <td></td>
                        </tr>
                        <tr id="trTel6">
                            <td class="fieldName">Ext:</td>
                            <td></td>
                        </tr>
                        <tr id="trTel7">
                            <td class="fieldName">Email:</td>
                            <td></td>
                        </tr> 
                         <tr id="trTel8">
                            <td class="fieldName">URL:</td>
                            <td></td>
                        </tr>
                        <tr id="trTel9">
                            <td class="fieldName">Comments:</td>
                            <td></td>
                        </tr>
                    </nedss:container>
                
                </nedss:container>
            </html:form>
        </div>
    </div>

    <%-- Bottom button bar --%>
    <div class="grayButtonBar" style="margin-top: 4px;"> 
     <logic:equal value="GlobalPlace" name="placeForm" property="contextAction">
       <input type="button" value="Edit Place" id="btnEditB" onclick="editPlace();" />
       <input type="button" value="Add Place" id="btnAddB" onclick="showAddPlace()"/>
       <input type="button" value="Inactivate" id="btnInactivateB" onclick="inactivatePlace()"/>
        &nbsp;&nbsp;
     </logic:equal>
     <logic:notEqual value="GlobalPlace" name="placeForm" property="contextAction">
       			<input type="button" value="select" id="btnSelectT" onclick="selectPlace('${placeForm.place.thePlaceDT.nm}','<%=request.getParameter("identifier")%>');" />
			&nbsp;&nbsp;
     </logic:notEqual>
    </div> 
</body>
</html>