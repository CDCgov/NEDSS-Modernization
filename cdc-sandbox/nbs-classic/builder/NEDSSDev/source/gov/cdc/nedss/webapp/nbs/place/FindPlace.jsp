<%@ include file="/jsp/tags.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page isELIgnored="false"%>

<html lang="en">
<head>
<base target="_self">
<meta http-equiv="MSThemeCompatible" content="yes" />
<style> 
  .operCol{
    background-color: #d1e5f6; 
    margin:0.05em; 
    padding:0.05em;"
  }
  
  .container{
    -ms-overflow-style: none;
    overflow: auto;
}

</style>

<title>Find Place</title>
<%@ include file="/jsp/resources.jsp"%>
<script>
  var flds = ["placeSearch.nm", "typeCd_textbox", "placeSearch.streetAddr1", "placeSearch.city", "state_textbox", "placeSearch.zip", "placeSearch.phoneNbrTxt"];
  function findPlace() {
    var isempty = true;
    $j(flds).each( function(i, obj){
      if( $j("input[name=" + obj + "]" ).val() != ""){
        isempty = false;
      }
    });
    if( isempty ){
      var x = $j("#errorMessages").html();
      x += "<ul><li>Please enter at least one item to search on and try again.</li></ul>";
      if( $j("#errorMessages ul").length == 0 )
         $j("#errorMessages").html(x);
      $j("#globalFeedbackMessagesBar").show();
      return;
    }
     
    document.forms["placeForm"].action="/nbs/Place.do";
    document.forms["placeForm"].submit();
  }
  $j(document).ready(function() {
    $j("select").each( function(i, sl){ 
      if( $j(sl).val() ){ 
        var si = sl.id;
        var sn = sl.name;
        $j("input[name=" + si + "_textbox]").val($j("select[name="+ sn + "] option:selected").text());
      }
    }); 
    $j("#globalFeedbackMessagesBar").hide();
  });
</script>
</head>

<body class="container">
    <logic:equal value="GlobalPlace" name="placeForm" property="contextAction" > 
             <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
    </logic:equal>
    <html:form  action="Place" method="post">
        <div class="grayButtonBar">
            <input type="button" name="Submit" value="Submit" onclick="findPlace()"/>&nbsp;&nbsp;&nbsp;
             <logic:notEqual value="GlobalPlace" name="placeForm" property="contextAction" > 
             	<input type="button" name="Cancel" value="Cancel" onclick="window.close();"/>&nbsp;&nbsp;&nbsp;
               	<input type='hidden' name='identifier' value="<%= request.getParameter("identifier")!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getParameter("identifier"))):null%>"/>
    		 </logic:notEqual>
        </div>
        <input type="hidden" name="method" value="findResults"/>
        <input type="hidden" name="ContextAction" value="${fn:escapeXml(ContextAction)}"/>
        <br/>
        <div id="globalFeedbackMessagesBar" class="screenOnly">
          <div class="infoBox errors" id="errorMessages">
            <b class="errorMessagesHref">Please fix the following errors:</b><br/>
            <logic:messagesPresent name="error_messages">
                <html:messages id="msg" name="error_messages">
                    <li><bean:write name="msg" /></li>
                </html:messages>
            </logic:messagesPresent>
          </div>
        </div>
        <table role="presentation" class="subSect" id="placeSearch">
            <thead>
                <tr>
                    <td colspan="2"></td>
                </tr>
            </thead>
            <tr>
                <td width="21%">&nbsp;</td>
                <td width="13%"><span><b>Operators</b></span></td>
                <td width="1%"></td>
                <td><span><b>Search Criteria</b></span></td>
            </tr>
            <tr>
                 <td class="fieldName" valign="middle">Name:</td>
                 <td class="operCol" style="min-width:200px">
                    <html:select title="Name operator" name="placeForm" property="placeSearch.nmOperator" styleId="nmoperator">
                         <html:optionsCollection property="codedValueNoBlnk(SEARCH_SNDX)" value="key" label="value" />
                    </html:select> 
                 </td>
                 <td width="1%"></td>
                 <td style="min-width:200px">
                     <html:text title="Enter a Name" name="placeForm"  property="placeSearch.nm"/>
                 </td>
            </tr>
            <tr>
                 <td class="fieldName" valign="middle">Type:</td>
                 <td class="operCol">&nbsp; </td>
                 <td width="1%"></td>
                 <td>
                     <html:select title="Select a Type" styleId="typeCd" name="placeForm"  property="placeSearch.typeCd">
                        <html:optionsCollection property="codedValue(PLACE_TYPE)" value="key" label="value" />
                     </html:select>
                 </td>
            </tr>
            <tr>
                 <td class="fieldName" valign="middle">Street Address::</td>
                 <td class="operCol">
                    <html:select title="Street Address operator" name="placeForm" property="placeSearch.streetAddr1Operator" styleId="saoperator">
                         <html:optionsCollection property="codedValueNoBlnk(SEARCH_ALPHA)" value="key" label="value" />
                    </html:select> 
                 </td>
                 <td width="1%"></td>
                 <td>
                     <html:text title="Enter a Street Address" name="placeForm" property="placeSearch.streetAddr1"/>
                 </td>
            </tr>
            <tr>
                 <td class="fieldName" valign="middle">City:</td>
                 <td class="operCol">
                    <html:select title="City operator" name="placeForm" property="placeSearch.cityOperator" styleId="cdoperator">
                         <html:optionsCollection property="codedValueNoBlnk(SEARCH_ALPHA)" value="key" label="value" />
                    </html:select> 
                 </td>
                 <td width="1%"></td>
                 <td>
                     <html:text title="Enter a City" styleId="city" name="placeForm"  property="placeSearch.city"/>
                 </td>
            </tr>
            <tr>
                <td class="fieldName" valign="middle">State:</td>
                <td class="operCol">&nbsp; </td>
                <td width="1%"></td>
                <td>
                    <html:select title="Select a State" name="placeForm" property="placeSearch.state"  styleId="state">
                        <html:optionsCollection property="stateList" value="key" label="value"/>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td class="fieldName" id="zipCodeLabel"> Zip: </td>
                <td class="operCol">&nbsp;</td>
                <td width="1%"></td>
                <td>
                    <html:text title="Enter a Zip" name="placeForm" property="placeSearch.zip" onkeyup="ZipMask(this, event)"/>
                </td> 
            </tr>
            <tr>
                <td class="fieldName" id="phoneNumberLabel">Telephone:</td>
                <td class="operCol">&nbsp;</td>
                <td width="1%"></td>
                <td><html:text title="Enter a Telephone" name="placeForm" property="placeSearch.phoneNbrTxt" onkeyup="TeleMask(this, event)" /></td>
            </tr>
        </table>
        <br/>
        <div class="grayButtonBar">
            <input type="button" name="Submit" value="Submit" onclick="findPlace()"/>&nbsp;&nbsp;&nbsp; 
            <logic:notEqual value="GlobalPlace" name="placeForm" property="contextAction" > 
             <input type="button" name="Cancel" value="Cancel" onclick="window.close();"/>&nbsp;&nbsp;&nbsp;
            </logic:notEqual>
        </div>
    </html:form>
</body>
</html>