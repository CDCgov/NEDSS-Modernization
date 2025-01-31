<html lang="en">
<head>
<base target="_self">
<%@page import="gov.cdc.nedss.util.NEDSSConstants"%>
<%@ page import="gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.action.place.*, gov.cdc.nedss.entity.place.vo.*" %>
<%@ page import="java.util.*, gov.cdc.nedss.webapp.nbs.form.place.*,gov.cdc.nedss.webapp.nbs.form.util.BaseForm"%>
<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<%@ include file="/jsp/resources.jsp"%>
<title>${placeForm.pageTitle}</title>

<script src="place.js"></script>
<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JBaseForm.js"></SCRIPT>
<script>
  var addrArr = new Array();
  var phoneArr = new Array(); 
  <logic:notEqual value="GlobalPlace" parameter="ContextAction" scope="request"> 
    var identifier  = "<%=request.getParameter("identifier")%>"; 
  </logic:notEqual>
  <%
  StringBuffer addressAndPhone = new StringBuffer();
  PlaceForm placeform = (PlaceForm)request.getSession().getAttribute("placeForm");

  Collection col = placeform.getPlace().getTheEntityLocatorParticipationDTCollection();
  if( col != null )
  {
      Iterator iter = col.iterator();
      while(iter.hasNext())
      {
         EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.next();
         if( "PST".equalsIgnoreCase(elp.getClassCd()) && NEDSSConstants.STATUS_ACTIVE.equalsIgnoreCase(elp.getStatusCd()))
        	 addressAndPhone.append("addrArr.push(" + placeform.getJson( elp , "PST") + ");") ;
         else if( "TELE".equalsIgnoreCase(elp.getClassCd()) && NEDSSConstants.STATUS_ACTIVE.equalsIgnoreCase(elp.getStatusCd()) )
        	 addressAndPhone.append( "phoneArr.push(" + placeform.getJson( elp , "TELE") + ");") ;
      } 
  } 
    out.println(HTMLEncoder.sanitizeHtml(addressAndPhone.toString()));
  %>
  var refineSearch =  "<%=request.getAttribute("addJsonObject")%>"
  $j(document).ready(function()
  {  
    
    placeTypeOther({"value": "${placeForm.place.thePlaceDT.cd}"}); 
    $j("#placeType").click();
    if (addrArr && addrArr.length > 0) {
      for (var i = 0; i < addrArr.length; i++) {
        viewAddr(i);
        addAddress(i);
      } 
    } 
    if( phoneArr && phoneArr.length > 0 ){ 
      for (var i = 0; i < phoneArr.length; i++) {
        viewPhone(i);
        addPhone(i);
      } 
    } 
    disableClearFields("subSec2", false, true);
    disableClearFields("subSec3", false, true);
    showHide(["btnAddAddress", "btnAddTelephone"], ["btnUpdateAddress", "btnUpdateTelephone", "trTelTypeOther"]);
    for(var p in refineSearch){
      $j("#" + p).val(refineSearch[p]);
      $j("#" + p).click();
    }
    
    if( $j("#errorMessages li").length > 0 ) 
      $j("#errorMessages").show(); 
    if( $j("#successMessages").html() != null && $j("#successMessages").html().length > 0 )
      $j("#successMessages").show();
    <%if( request.getAttribute("newPlace") != null  ){%>
      selectPlace(<%=request.getAttribute("placeUid")%>, '<%=request.getAttribute("placeName")%>'); 
    <%}%>
  });
</script>
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
</head>
<c:set var="jsSubmit" value="addPlace()"/>
<logic:notEmpty  name="placeForm" property="place.placeUid">
  <c:set var="jsSubmit" value="updatePlace()"/>
</logic:notEmpty>

<c:set var="jsCancel" value="returntoSearchResult()"/>
<logic:notEmpty  name="placeForm" property="place.placeUid">
  <c:set var="jsCancel" value="cancelPlaceForm()" />
</logic:notEmpty>

<body onload="checkReasonOnLoad();">
    <!-- Page title -->
    <div>
        <logic:equal value="GlobalPlace" name="placeForm" property="contextAction" > 
             <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
        </logic:equal>
    </div> 
    <!-- Top button bar -->
    <div class="grayButtonBar" style="margin-bottom: 4px;">
       <input type="button" value="Submit" id="btnSubmitT" onclick="${jsSubmit};" />&nbsp;&nbsp;
       <input type="button" value="Cancel" id="btnCancelT" onclick="${jsCancel};"/>
       &nbsp;&nbsp;
    </div>
     
    <div style="width: 100%; text-align: center;">
        <div style="width: 100%;">
            <div style="text-align: right; width: 100%;">
                <span class="boldTenRed">*</span><span class="boldTenBlack"> Indicates a Required Field </span>&nbsp;&nbsp;
            </div>

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
                    <div style="display:none">
                    <%if( request.getAttribute("newPlace") != null  ){ 
                        PlaceVO placeVO = (PlaceVO)request.getAttribute("newPlace");  
                    %>
                       <%=placeVO.getAddress() %>
                       <%=placeVO.getTelephone()%>
                    <%} %>
                    </div>
                </logic:messagesPresent>
            </div>

            <html:form method="post" styleId="placeForm">
                <input type="hidden" name="method" value="add"/> 
                <html:hidden name="placeForm" property="place.thePlaceDT.placeUid"/>
                <input type="hidden" name="identifier"  id="identifier" value="${fn:escapeXml(identifier)}"/>
                <logic:notEmpty  name="placeForm" property="place.placeUid">
                   <nedss:container id="reasonEdit" name="Reason for Edit" classType="sect" includeBackToTopLink="no"> 
                     <table role="presentation" class="subSect">
                      <tr>
                        <td class="fieldName"><span class="boldTenRed">* </span>Reason for Edit:</td>
                        <td><html:radio title="Typographical error correction or additional information" property='reasonForEdit' styleId="rfe" value='overwrite' onchange="checkReason();">
                           Typographical error correction or additional information<br/>
                           <i>&nbsp;(If you choose this option the information will be overwritten.)</i> </html:radio>
                        </td>
                      </tr>
                      <tr>
                        <td></td>
                        <td><html:radio title="A change to existing information for non typographical reasons" property='reasonForEdit' value='new'  onchange="checkReason();">
                           A change to existing information for non typographical reasons<br/>
                           <i>&nbsp;(If you choose this option a new Place will be created.)</i> </html:radio>
                        </td>
                      </tr>
                      </table>
                   </nedss:container>
                </logic:notEmpty>
                
                <nedss:container id="section1" name="Place Demographics" classType="sect" displayImg="false" includeBackToTopLink="no">
                    <nedss:container id="subSec0" name="Administrative" classType="subSect" defaultDisplay="expand">
					<logic:notEmpty  name="placeForm" property="place.placeUid">
                        <tr>
                            <td class="fieldName" id="qac">Quick Code:</td>
                            <td><html:text title="Quick Code" name="placeForm" property="quick.rootExtensionTxt" styleId="quickedit" maxlength="10"  onchange="getQuickEntryCode(this.name, 'trQcError')"/></td>
                        </tr>
						</logic:notEmpty>
						<logic:empty  name="placeForm" property="place.placeUid">
                        <tr>
                            <td class="fieldName" id="qac">Quick Code:</td>
                            <td><html:text title="Quick Code" name="placeForm" property="quick.rootExtensionTxt" maxlength="10" onchange="getQuickEntryCode(this.name, 'trQcError')"/></td>
                        </tr>
						</logic:empty>
                        <tr id="trQcError" style="display:none">
                          <td colspan='2' class="boldTenRed">
                            &nbsp;&nbsp;Quick Code must be a unique Code. The code you have entered already exists. Please enter a different code and try again
                          </td>
                        </tr>
                        <tr>
                            <td class="fieldName">Place Type:</td>
                            <td>
                                <html:select title="Place Type" name="placeForm" property="place.thePlaceDT.cd" styleId="placeType" onchange="placeTypeOther(this)">
                                    <html:optionsCollection name="placeForm" property="codedValue(PLACE_TYPE)" value="key" label="value" />
                                </html:select>
                            </td>
                        </tr>
                        <tr id="trPlaceTypeOther">
                            <td class="fieldName">Description:</td>
                            <td> <html:text title="Description" name="placeForm" property="place.cdDescTxt" maxlength="50" /> </td>
                        </tr>
                        <tr>
                            <td class="fieldName">General Comments:</td>
                            <td>
                                <html:textarea title="General Comments" name="placeForm" property="place.thePlaceDT.description" rows="5" cols="40"/> 
                            </td>
                        </tr>
                    </nedss:container>
                    
                    <nedss:container id="subSec1" name="Name" classType="subSect" defaultDisplay="expand">
                        <tr>
                            <td class="fieldName" id="LNM"><span class="boldTenRed">* </span> Place Name:</td>
                            <td><html:text title="Place Name" name="placeForm" property="place.thePlaceDT.nm" maxlength="48" /></td>
                        </tr>
                    </nedss:container>

                    <nedss:container id="subSec2" name="Address" classType="subSect" defaultDisplay="expand">
                        <input type="hidden" name="addrIndex" value="0"/>
                        <tr>
                          <td colspan="2" align="center">
                            <div class="infoBox errors" style="display: none;visibility: none;" id="AddrErrorMessages">
                              <b>Please fix the following errors:</b> <br/>
                            </div> 
                            <table role="presentation" id="tblAddresses" class="dtTable1">
                              <thead>
                               <tr>
                                <td style="width: 30px; background-color: #EFEFEF">&nbsp;</td>
                                <td style="width: 30px; background-color: #EFEFEF">&nbsp;</td>
                                <td style="width: 30px; background-color: #EFEFEF">&nbsp;</td>
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
                               <tr/>
                             </tbody>
                            </table>
                          </td> 
                        </tr>
                        <tr id="trAddr1">
                          <td class="fieldName">
                            <span class="boldTenRed">* </span>Address Information As Of:
                          </td>
                          <td id="addressAsOf">  
                            <html:text title="Address Information As Of" name="placeForm" styleClass="requiredInputField" property="addrAsOf" maxlength="10" size="10"  styleId="AddrAsOf" onkeyup="DateMask(this,null,event)"/>
                            <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AddrAsOf','AddrAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('AddrAsOf','AddrAsOfIcon',event);" styleId="AddrAsOfIcon"></html:img>
                          </td>    
                        </tr>
                        <tr id="trAddr2">
                          <td class="fieldName"><span class="boldTenRed">* </span><span class="InputFieldLabel" id="AddrTypeL" title="Address Type">Type:</span></td>
                          <td id="addressType">  
                            <html:select title="Address Type" name="placeForm" property="addrType" styleId="addrType">
                               <html:optionsCollection name="placeForm" property="codedValue(EL_TYPE_PST_PLC)" value="key" label="value" />
                            </html:select>  
                          </td>    
                        </tr> 
                        <tr id="trAddr3">
                          <td class="fieldName"><span class="boldTenRed">* </span><span class=" InputFieldLabel" id="AddrUseL" title="Address Type">Use:</span></td>
                          <td id="addressUse">  
                             <html:select title="Address Use" name="placeForm" property="addrUse" styleId="addrUse">
                              <html:optionsCollection name="placeForm" property="codedValue(EL_USE_PST_PLC)" value="key" label="value" />
                            </html:select>   
                          </td>   
                        </tr> 
                        <tr id="trAddr4">
                            <td class="fieldName">Street Address 1:</td>
                            <td><input title="Street Address 1" type="text" name="streetAddr1" maxlength="100" id="streetAddr1"/></td>
                        </tr>
                        <tr id="trAddr5">
                            <td class="fieldName">Street Address 2:</td>
                            <td><input title="Street Address 2" type="text" name="streetAddr2" maxlength="100" /></td>
                        </tr>
                        <tr id="trAddr6">
                            <td class="fieldName">City:</td>
                            <td><input title="City" type="text" name="city" maxlength="100" id="city"/></td>
                        </tr>
                        <tr id="trAddr7">
                            <td class="fieldName">State:</td>
                            <td><html:select title="State" name="placeForm" property="state" onchange="getCounties(this, 'county');" styleId="state">
                                    <html:optionsCollection property="stateList" value="key" label="value" />
                                </html:select>
                            </td>
                        </tr>
                        <tr id="trAddr8">
                            <td class="fieldName" id="ZP">Zip:</td>
                            <td><input title="Zip" type="text" name="zip" onkeyup="ZipMask(this, event)" id="zip"/></td>
                        </tr>
                        <tr id="trAddr9">
                            <td class="fieldName">County:</td>
                            <td> <html:select title="County" name="placeForm" property="county" styleId="county">
                                   <html:optionsCollection name="placeForm" property="dwrCounties" value="key" label="value" />
                                 </html:select>   
                            </td>
                        </tr>
                        <tr id="trAddr10">
                            <td class="fieldName">Census Tract:</td>
                            <td><input title="Census Tract" type="text" name="censusTract" title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." onblur="checkCensusTract(this)"></input></td>
                        </tr>
                        <tr id="trAddr11">
                            <td class="fieldName">Country:</td>
                            <td>
                                <html:select title="Country" name="placeForm" property="country" styleId="country">
                                    <html:optionsCollection name="placeForm" property="countryList" value="key" label="value" />
                                </html:select>
                            </td>
                        </tr>
                        <tr id="trAddr12">
                            <td class="fieldName">Address Comments:</td>
                            <td>
                              <textarea title="Address Comments" name="locatorDescTxt" rows="5" cols="40"></textarea>
                            </td>
                        </tr>
                        <tr id="trAddressButtons">
                            <td></td>
                            <td><input type='button' name='btnAddAddress' value='Add Address' onclick="addAddress(this)" id='btnAddAddress'/>
                                <input type='button' name='btnUpdateAddress' value='Update Address' onclick="addAddress(this)" id="btnUpdateAddress"/>
                            </td>
                        </tr>
                    </nedss:container>

                    <nedss:container id="subSec3" name="Phone, Email & URL" classType="subSect" defaultDisplay="expand"> 
                        <input type="hidden" name="teleIndex" value="0"/>
                        <tr>
                          <td colspan="2" align="center" width="100%"> 
                            <div class="infoBox errors" style="display: none;visibility: none;" id="PhoneErrorMessages">
                               <b>Please fix the following errors:</b><br/>
                            </div>
                            <table role="presentation" id="tblPhones" class="dtTable1">
                               <thead><tr>
                                    <td style="width: 30px; background-color: #EFEFEF">&nbsp;</td>
                                    <td style="width: 30px; background-color: #EFEFEF">&nbsp;</td>
                                    <td style="width: 30px; background-color: #EFEFEF">&nbsp;</td>
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
                              <tbody><tr/></tbody>
                            </table>
                          </td> 
                        </tr>
                        <tr id="trTel1">
                          <td class="fieldName"><span class="boldTenRed">*</span>Contact Information As Of:</td>
                          <td id="phoneAsOfTd">  
                            <html:text name="placeForm" title="Contact Information As Of" styleClass="requiredInputField" property="phoneAsOf" maxlength="10" size="10"  styleId="PhoneAsOf" onkeyup="DateMask(this,null,event)"/>
                            <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('PhoneAsOf','PhoneAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('PhoneAsOf','PhoneAsOfIcon',event);" styleId="PhoneAsOfIcon"></html:img>
                          </td>    
                        </tr>
                        <tr id="trTel2">
                          <td class="fieldName"><span class="boldTenRed">* </span>Type:</td>
                          <td id="addressType">  
                            <html:select title="Phone, Email & URL Type" name="placeForm" property="phoneType" styleId="phoneType" onchange='teleTypeOther(this)'>
                               <html:optionsCollection name="placeForm" property="codedValue(EL_TYPE_TELE_PLC)" value="key" label="value" />
                            </html:select>  
                          </td>    
                        </tr>
                        <tr id="trTelTypeOther" style="display: none">
                           <td class="fieldName">Description:</td>
                           <td>
                             <input title="Phone, Email & URL Description" type="text" name="teleOtherDesc" maxlength="50"/>
                           </td>
                        </tr> 
                        <tr id="trTel3">
                          <td class="fieldName"><span class="boldTenRed">* </span> <span class="InputFieldLabel" title="Phone Use">Use:</span></td>
                          <td id="phoneUseTd">  
                             <html:select title="Phone, Email & URL Use" name="placeForm" property="phoneUse" styleId="phoneUse">
                              <html:optionsCollection name="placeForm" property="codedValue(EL_USE_TELE_PLC)" value="key" label="value" />
                            </html:select>   
                          </td>   
                        </tr>
                        <tr id="trTel4">
                           <td class="fieldName">Country Code:</td>
                            <td><input title="Country Code" type="text" name="cntryCd" maxlength="3" size="5" onkeyup="isNumericCharacterEntered(this)"/></td>
                        </tr>
                        <tr id="trTel4">
                            <td class="fieldName">Telephone:</td>
                            <td><input title="Telephone" type="text" name="phone" maxlength="20" onkeyup="TeleMask(this, event)" id="phoneNbrTxt"/></td>
                        </tr>
                        <tr id="trTel5">
                            <td class="fieldName">Ext:</td>
                            <td><html:text title="Extension" name="placeForm" property="phoneExt" maxlength="10" size="10" onkeyup="isNumericCharacterEntered(this)" /></td>
                        </tr>
                        <tr id="trTel6">
                            <td class="fieldName">Email:</td>
                            <td><html:text title="Email" name="placeForm" property="email" maxlength="48" /></td>
                        </tr>
                        <tr id="trTel7">
                            <td class="fieldName">URL:</td>
                            <td><input title="URL" type="text" name="urlAddress" maxlength="100" /></td>
                        </tr>
                        <tr id="trTel8">
                            <td class="fieldName">Comments:</td>
                            <td><textarea title="Phone, Email & URL Comments" name="locatorDescTxt_p" rows="5" cols="40"></textarea></td>
                        </tr>
                        <tr id="trTelephoneButtons">
                            <td></td>
                            <td><input type='button' id='btnAddTelephone' value='Add Telephone' onclick="addPhone(this)"/>
                                <input type='button' id='btnUpdateTelephone' value='Update Telephone' onclick="addPhone(this)"/>
                            </td>
                        </tr>
                    </nedss:container>
                </nedss:container>
            </html:form>
        </div>
    </div>

    <%-- Bottom button bar --%>
    <div class="grayButtonBar" style="margin-top: 4px;">
        <input type="button" value="Submit" id="btnSubmitB" onclick="${jsSubmit};" />
        &nbsp;&nbsp;
        <input type="button" value="Cancel" id="btnCancelB" onclick="${jsCancel}" />
        &nbsp;&nbsp;
    </div>
    
    <form id="emptyPlaceForm" name="emptyPlaceForm" method="post" action="/nbs/Place.do">
       <input type="hidden" name="identifier" id="identifier" value="${fn:escapeXml(identifier)}"/>
       <logic:notEqual value="GlobalPlace" name="placeForm" property="contextAction" > 
       		<input type="hidden" name="ContextAction" value="<%=request.getParameter("ContextAction")%>"/>
       </logic:notEqual>
       <logic:equal value="GlobalPlace" name="placeForm" property="contextAction" > 
       		<input type="hidden" name="ContextAction" value="GlobalPlace"/>
       </logic:equal>       
       
    </form>
    
</body>
</html>