
var vAddr = '<td>&nbsp;<img src="page_white_text.gif" tabIndex="0" alt = "View Address" title="View Address" onclick="viewAddr(_id)" onkeypress="if(isEnterKey(event)) viewAddr(_id)"/></td>';
var eAddr = '<td>&nbsp;<img src="page_white_edit.gif" tabIndex="0" alt = "Edit Address" title="Edit Address" onclick="editAddr(_id)" onkeypress="if(isEnterKey(event)) editAddr(_id)"/></td>';
var dAddr = '<td>&nbsp;<img src="cross.gif" tabIndex="0" alt="Delete Address" title="Delete Address" onclick="deleteAddr(_id)" onkeypress="if(isEnterKey(event)) deleteAddr(_id)"/></td>';
var sdAddr = '<td>&nbsp;Show Details</td>';

var vPhone = '<td>&nbsp;<img src="page_white_text.gif" tabIndex="0" alt="View Phone" title="View Phone" onclick="viewPhone(_id)" onkeypress="if(isEnterKey(event)) viewPhone(_id)"/></td>';
var ePhone = '<td>&nbsp;<img src="page_white_edit.gif" tabIndex="0" alt="Edit Phone" title="Edit Phone" onclick="editPhone(_id)" onkeypress="if(isEnterKey(event)) editPhone(_id)"/></td>';
var dPhone = '<td>&nbsp;<img src="cross.gif" tabIndex="0" alt="Delete Phone" title="Delete Phone" onclick="deletePhone(_id)" onkeypress="if(isEnterKey(event)) deletePhone(_id)"/></td>'

var savedQuickCode = "";
var oldSavedQuickEntry = "";

// bn: bean name, fn: field name, sn: select name
var addrElements = [
    {fn:"addrAsOf", bn: "asOfDate", "required": true},
    {fn:"addrType_textbox", bn:"cd", sn: "addrType", "required": true},
    {fn:"addrUse_textbox", bn:"useCd", sn: "addrUse", "required": true },
    {fn:"streetAddr1", bn:"thePostalLocatorDT.streetAddr1", fn1: "streetAddr2", bn1: "thePostalLocatorDT.streetAddr2"} ,
    {fn:"city", bn:"thePostalLocatorDT.cityDescTxt"},
    {fn:"state_textbox", bn:"thePostalLocatorDT.stateCd", sn: "state"},
    {fn:"zip", bn:"thePostalLocatorDT.zipCd"},
    {fn:"county_textbox", bn:"thePostalLocatorDT.cntyCd", sn: "county"},
    {fn:"country_textbox", bn:"thePostalLocatorDT.cntryCd", sn: "country", noTbl: true},
    {fn:"locatorDescTxt", bn:"locatorDescTxt", noTbl: true},
    {fn:"censusTract", bn:"thePostalLocatorDT.censusTract", noTbl: true}
];
var phoneElements = [
    {fn:"phoneAsOf", bn: "asOfDate", "required": true},
    {fn:"phoneType_textbox", bn:"cd", sn: "phoneType", "required": true},
    {fn:"phoneUse_textbox", bn:"useCd", sn: "phoneUse", "required": true},
    {fn:"cntryCd", bn: "theTeleLocatorDT.cntryCd", noTbl: true},
    {fn:"phone", bn:"theTeleLocatorDT.phoneNbrTxt", fn1:"phoneExt", bn1:"theTeleLocatorDT.extensionTxt"},
    {fn:"email", bn:"theTeleLocatorDT.emailAddress"},
    {fn:"urlAddress", bn: "theTeleLocatorDT.urlAddress"},
    {fn:"locatorDescTxt_p", bn:"locatorDescTxt"},
    {fn:"teleOtherDesc", bn: "cdDescTxt"}
];

function placeTypeOther(obj)
{
   if(obj.value == "O" || obj.value == "OTH"){
     showHide(["trPlaceTypeOther"], []);
   } else {
     showHide([], ["trPlaceTypeOther"]);
   }
}

function teleTypeOther(obj)
{
  if (obj.value == "OTH") {
    showHide([ "trTelTypeOther" ], []);
  } else {
    showHide([], [ "trTelTypeOther" ]);
  }
}

  function addAddress(obj)
  {
    if( !isMultiEntryDataRequired(addrElements) ){
      showMutliEntryErrorMessage("AddrErrorMessages", "Required fields are not entered");
      return;
    }
    var idx = $j("input[name='addrIndex']").val();
    if( obj.id == 'btnUpdateAddress'){
      $j("#tblAddresses tr[id=a" + idx + "]").remove();
    }
    updateAddress(idx);
    $j("input[name='addrIndex']").val(addrArr.length);
    $j("#btnUpdateAddress").hide();
    $j("#btnAddAddress").show();

  }

  function updateAddress(idx)
  {
    $j("#trAddrNoRecords").hide();
    var str = "<tr id='a" +  idx +"'>";
    if( $j("input[name='mode']").val() == 'view'){
      str += sdAddr.replace(/_id/g, idx);
    } else {
      str += vAddr.replace(/_id/g, idx);
      str += eAddr.replace(/_id/g, idx);
      str += dAddr.replace(/_id/g, idx);
    }
    var tdObj = createTDs(addrElements);
    str += (tdObj.str)  + "</tr>";
    var adr = tdObj.obj;
    if (addrArr[idx] == null || addrArr[idx] == undefined) {
      addrArr[idx] = adr;
      $j("#tblAddresses tbody tr:last").after(str);
    } else {
      merge(addrArr[idx], adr);
      clearEmptyAddressFields(addrArr[idx], adr);
      $j("#tblAddresses tbody tr:eq(" + idx + ")").after(str);
    }
    disableClearFields("subSec2", false, true);

    $j("#AddrErrorMessages").hide();
  }
  // 12-19-14 - A problem occurred that cleared fields reappeared. To get around this flaw, we send a
  // blank. The blank is converted to a null by the action class.
function clearEmptyAddressFields(addrArrayObj, adrObj)
{
 	var street1 = false;
 	var street2 = false;
 	var zcntyCd = false;
 	var zcntryCd = false;
 	var zcntyDescTxt = false;
 	var zcensusTract = false;
 	var zstateCd = false;
 	var zzipCd = false;
 	var zcityDescTxt = false;
 	var zlocatorDescTxt = false;
 	
	for ( var p in adrObj) {
		if (p == "thePostalLocatorDT.streetAddr1")
			street1 = true;
		else if (p == "thePostalLocatorDT.streetAddr2")
			street2 = true;
		else if (p == "thePostalLocatorDT.cntyCd")
			zcntyCd = true;
		else if (p == "thePostalLocatorDT.cntryCd")
			zcntryCd = true;			
		else if (p == "thePostalLocatorDT.cntyDescTxt")
			zcntyDescTxt = true;
		else if (p == "thePostalLocatorDT.censusTract")
			zcensusTract = true;
		else if (p == "thePostalLocatorDT.stateCd")
			zstateCd = true;	
		else if (p == "thePostalLocatorDT.zipCd")
			zzipCd = true;	
		else if (p == "thePostalLocatorDT.cityDescTxt")
			zcityDescTxt = true;
		else if (p == "locatorDescTxt")
			zlocatorDescTxt = true;			
	}
	//if items not in update - replace master with blank
	for (var q in addrArrayObj) {
		if (q == "thePostalLocatorDT.streetAddr1" && street1 == false)
			addrArrayObj[q] = "";
		else if (q == "thePostalLocatorDT.streetAddr2" && street2 == false)
			addrArrayObj[q] = "";
		else if (q == "thePostalLocatorDT.cntyCd" && zcntyCd == false)
			addrArrayObj[q] = "";
		else if (q == "thePostalLocatorDT.cntryCd" && zcntryCd == false)
			addrArrayObj[q] = "";			
		else if (q == "thePostalLocatorDT.cntyDescTxt" && zcntyDescTxt == false)
			addrArrayObj[q] = "";
		else if (q == "thePostalLocatorDT.censusTract" && zcensusTract == false)
			addrArrayObj[q] = "";
		else if (q == "thePostalLocatorDT.stateCd" && zstateCd == false)
			addrArrayObj[q] = "";
		else if (q == "thePostalLocatorDT.zipCd" && zzipCd == false)
			addrArrayObj[q] = "";	
		else if (q == "thePostalLocatorDT.cityDescTxt" && zcityDescTxt == false)
			addrArrayObj[q] = "";
		else if (q == "locatorDescTxt" && zlocatorDescTxt == false)
			addrArrayObj[q] = "";				
	}
}

  function addPhone(obj)
  {
    if( !isMultiEntryDataRequired(phoneElements) ){
      showMutliEntryErrorMessage("PhoneErrorMessages", "Required fields are not entered");
      return;
    }
    var idx = $j("input[name='teleIndex']").val();
    if( obj.id == 'btnUpdateTelephone'){
      $j("#tblPhones tr[id=p" + idx + "]").remove();
    }
    updatePhone(idx);
    $j("input[name='teleIndex']").val(phoneArr.length);
    $j("#btnUpdateTelephone").hide();
    $j("#btnAddTelephone").show();
  }

  function updatePhone(idx)
  {
    $j("#trTelNoRecords").hide();
    var str = "<tr id='p" +  idx +"'>";
    str += vPhone.replace(/_id/g, idx);
    str += ePhone.replace(/_id/g, idx);
    str += dPhone.replace(/_id/g, idx);

    var myObj = createTDs(phoneElements)
    str += ( myObj.str +  "</tr>" );
    var adr = myObj.obj;
    if( phoneArr[idx] == null || phoneArr[idx] == undefined){
      phoneArr[idx] = (adr);
      $j("#tblPhones tbody tr:last").after(str);
    } else {
      merge(phoneArr[idx], adr);
      clearEmptyPhoneFields(phoneArr[idx], adr);
      $j("#tblPhones tbody tr:eq(" + idx + ")").after(str);
    }
    disableClearFields("subSec3", false, true);
  }
  
  // 12-19-14 - A problem occurred that cleared fields reappeared. To get around this flaw, we send a
  // blank. The blank is converted to a null by the action class.
  function clearEmptyPhoneFields(teleArrayObj, teleObj)
  {
   	var zemailAddress = false;
   	var zextensionTxt= false;
   	var zprogAreaCd = false;
   	var zphoneNbrTxt = false;
   	var zurlAddress = false;
   	var zcntryCd = false;

   	
  	for ( var p in teleObj) {
  		if (p == "theTeleLocatorDT.emailAddress")
  			zemailAddress = true;
  		else if (p == "theTeleLocatorDT.extensionTxt")
  			zextensionTxt = true;
  		else if (p == "theTeleLocatorDT.progAreaCd")
  			zprogAreaCd = true;
  		else if (p == "theTeleLocatorDT.phoneNbrTxt")
  			zphoneNbrTxt = true;			
  		else if (p == "theTeleLocatorDT.urlAddress")
  			zurlAddress = true;
  		else if (p == "theTeleLocatorDT.cntryCd")
  			zcntryCd = true;	
  	}
  	//if items not in update - replace master with blank
  	for (var q in teleArrayObj) {
  		if (q == "theTeleLocatorDT.emailAddress" && zemailAddress == false)
  			teleArrayObj[q] = "";
  		else if (q == "theTeleLocatorDT.extensionTxt" && zextensionTxt == false)
  			teleArrayObj[q] = "";
  		else if (q == "theTeleLocatorDT.progAreaCd" && zprogAreaCd == false)
  			teleArrayObj[q] = "";
  		else if (q == "theTeleLocatorDT.phoneNbrTxt" && zphoneNbrTxt == false)
  			teleArrayObj[q] = "";  			
  		else if (q == "theTeleLocatorDT.urlAddress" && zurlAddress == false)
  			teleArrayObj[q] = "";			
  		else if (q == "theTeleLocatorDT.cntryCd" && zcntryCd == false)
  			teleArrayObj[q] = "";
  	}
  }
  

  function createTDs( objElements)
  {
    var adr = {};
    var str = "";
    for (var i = 0; i < objElements.length; i++) {
      var obj = objElements[i];
      var txt = $j("[name=" + obj.fn + "]").val();
      var txt1 = (obj.fn1 ? ($j("input[name=" + obj.fn1 + "]").val()) : "");
      if (txt != null && txt.length > 0) {
          if (!obj.noTbl) {
            str += "<td>" + txt;
            if (txt1 != "") {
              str += "<br/>" + txt1;
              adr[obj.bn1] = txt1;
            }
            str += "</td>";
          }
          if (obj.sn) {
            adr[obj.bn] = $j("select[name=" + obj.sn + "]").val();
          } else {
            adr[obj.bn] = txt;
          }
      } else {
        if (!obj.noTbl) {
          str += "<td/>";
        }
      }
    }
    return { str : str , obj : adr };
  }

  function viewPhone(idx)
  {
    editPhone(idx);
    disableClearFields("subSec3", true, false);
    showHide([], ["btnUpdateTelephone","btnAddTelephone"] );
  }

  function editPhone(idx)
  {
    var data = phoneArr[idx];
    disableClearFields("subSec3", false, true);
    populateSectionData(phoneElements, data);
    $j("input[name='teleIndex']").val(idx);
    showHide(["btnUpdateTelephone"], ["btnAddTelephone"]);
    teleTypeOther({"value": data.cd} );
  }

  function viewAddr(idx)
  {
    editAddr(idx);
    disableClearFields("subSec2", true, false);
    showHide([], ["btnUpdateAddress", "btnAddAddress"]);
  }

  function showAddressDetails(idx)
  {
    $j("#tr" + idx + " td").each( function(i, obj){
      if( i > 0 ){
        $j("#trAddr" + i + " td:nth-child(2)").html($j(obj).html());
        $j("#trAddr" + i ).show();
      }
    });
  }

  function showPhoneDetails(idx)
  {
    $j("#trT" + idx + " td").each( function(i, obj){
      if( i > 0 ){
        $j("#trTel" + i + " td:nth-child(2)").html($j(obj).html());
        $j("#trTel" + i ).show();
      }
    });
  }

  function editAddr(idx)
  {
    var data = addrArr[idx];
    disableClearFields("subSec2", false, true);
    populateSectionData(addrElements, data);
    $j("input[name='addrIndex']").val(idx);
    showHide(["btnUpdateAddress"], ["btnAddAddress"]);
  }

  function deleteAddr(idx)
  {
    $j("#tblAddresses tr[id=a" + idx + "]").remove();
    addrArr[idx]["statusCd"] = "I";
    disableClearFields("subSec2", false, true);
    $j("#btnUpdateAddress").hide();
    $j("#btnAddAddress").show();
    if( $j("#tblAddresses tr").length == 3 ){
      $j("#trAddrNoRecords").show();
    }
  }

  function deletePhone(idx)
  {
    $j("#tblPhones tr[id=p" + idx + "]").remove();
    phoneArr[idx]["statusCd"] = "I";
    disableClearFields("subSec3", false, true);
    if( $j("#tblPhones tr").length == 3 ){
      $j("#trTelNoRecords").show();
    }
  }

  function disableClearFields(secId, dflag, cflag)
  {
    if( cflag ) {
      $j("#" + secId + " input[type=text]").val("");
      $j("#" + secId + " [type=textarea]").val("");
      $j("#" + secId + " select").each( function(i, obj) {
        obj.selectedIndex = -1;
      });
    }
    $j("#" + secId  + " input[type=text]").attr("disabled", dflag);
    $j("#" + secId  + " select").attr("disabled", dflag);
    $j("#" + secId  + " [type=textarea]").attr("disabled", dflag);
    $j("#" + secId  + " [type=radio]").attr("disabled", dflag);
    if(dflag==true)
    	$j("img[src=calendar.gif]").attr("tabIndex","-1");
    else
    	$j("img[src=calendar.gif]").attr("tabIndex","0");
  }

  function populateSectionData(formElements, data)
  {
    for (var i = 0; i < formElements.length; i++) {
      var obj = formElements[i];
      if (obj.sn && data[obj.bn]) {
        $j("select[name=" + obj.sn + "]").val(data[obj.bn]);
        $j("input[name=" + obj.fn + "]").val($j("select[name=" + obj.sn + "] option:selected").text());
      } else {
        $j("[name=" + obj.fn + "]").val(data[obj.bn] ? data[obj.bn] : "");
        if( obj.fn1 ){
          $j("[name=" + obj.fn1 + "]").val(data[obj.bn1] ? data[obj.bn1] : "");
        }
      }
    }
  }
  
    function checkReasonOnLoad(){
    	savedQuickCode  = $j("#quickedit").val();
    	if (savedQuickCode == null || savedQuickCode == "")
    		return
    	var re = $j("input:radio[name=reasonForEdit]:checked").val();
  	if( re == "new"  ){
  		$j("#quickedit").attr("disabled", false);
  	}else{
  		$j("#quickedit").attr("disabled", true);
        }
  }

  function checkReason(){
  	var re = $j("input:radio[name=reasonForEdit]:checked").val();
	if( re == "new"  ){
		oldSavedQuickEntry = savedQuickCode;
		savedQuickCode = "";
		$j("#quickedit").attr("disabled", false);
	}else if (savedQuickCode != null && savedQuickCode != "") {
		$j("#quickedit").attr("disabled", true);
        }
  }

  function checkMultiEntryData(formElements)
  {
    for (var i = 0; i < formElements.length; i++) {
      var obj = formElements[i];
      var fldVal = $j("[name=" + obj.fn + "]").val();
      var fldDisabled = $j("[name=" + obj.fn + "]").attr("disabled"); //if view will be disabled
      if( fldVal != "" && fldVal != null && fldVal != undefined && fldDisabled == false)
        return true;
    }
    return false;
  }

  function isMultiEntryDataRequired(formElements)
  {
    for (var i = 0; i < formElements.length; i++) {
      var obj = formElements[i];
      var fldVal = $j("[name=" + obj.fn + "]").val();
      if( obj.required  && ( fldVal == null || fldVal == undefined || $j.trim(fldVal).length == 0 ) )
        return false;
    }
    return true;
  }

  function updatePlace()
  {
    var _data = { "method": "update"};
    var re = $j("input:radio[name=reasonForEdit]:checked").val();
    if( re == undefined || re == null ){
      showErrorMessages("Please select a Reason for Edit before submitting your changes.");
      return false;
    } else if( re == 'new'){
      _data["method"] = "add";
      _data["reasonForEdit"] = re;
    }
    _data["place.thePlaceDT.placeUid"] = $j("input[name='place.thePlaceDT.placeUid']").val();
    addUpdatePlace(_data);
  }

  function addPlace()
  {
    var _data = {"method" : "add"};
    addUpdatePlace(_data);
  }

  function addUpdatePlace(_data)
  {
    if( checkMultiEntryData(addrElements)){
      var msg = "Data has been entered in the Address section. Please press " +_data["method"] +"  or clear the data and submit again.";
      showMutliEntryErrorMessage("AddrErrorMessages", msg);
      return false;
    } else {
      $j("#AddrErrorMessages").hide();
    }

    if( checkMultiEntryData(phoneElements)){
      var msg = "Data has been entered in the Phone & Email section. Please press " +_data["method"] +"  or clear the data and submit again.";
      showMutliEntryErrorMessage("PhoneErrorMessages", msg);
      return;
    }
    var nm = $j("input[name='place.thePlaceDT.nm']").val();
    if( nm == null || nm == undefined || $j.trim(nm).length == 0 ){
      showErrorMessages("Place Name is required");
      return;
    }
    // Validate Quick Code has not special chars
    var rtext = $j("input[name='quick.rootExtensionTxt']").val();
    if( rtext != null && $j.trim(rtext).length > 0 ){
    	      var res = validateQuickCode(rtext);
	      if( res != true ){
	        showErrorMessages(res);
	        //$j("input[name=" + qec + "]").val("");
	        return false;
      	      }
      } 

    _data["place.thePlaceDT.nm"] = nm;
    _data["quick.rootExtensionTxt"]= $j("input[name='quick.rootExtensionTxt']").val();
    _data["place.thePlaceDT.description"] = $j("[name='place.thePlaceDT.description']").val();
    _data["place.thePlaceDT.cd"] = $j("select[name='place.thePlaceDT.cd']").val();
     _data["place.thePlaceDT.cdDescTxt"] = $j("input[name='place.cdDescTxt']").val();

    var a = 0;

	    if (addrArr != null && addrArr != "") {
		for (var i = 0; i < addrArr.length; i++) {
			if (addrArr[i]) {
				var addr = addrArr[i];
				for ( var p in addr) {
					if (p == 'asOfDate')
						_data["ELP[" + a + "]." + p + "_s"] = addr[p];
					else
						_data["ELP[" + a + "]." + p] = addr[p];
				}
				_data["ELP[" + a + "].classCd"] = 'PST';
				a++;
			}
		}
	}
	
	if (phoneArr != null && phoneArr != "") {
		for (var i = 0; i < phoneArr.length; i++) {
			if (phoneArr[i]) {
				var obj = phoneArr[i];
				for ( var p in obj) {
					if (p == 'asOfDate') {
						_data["ELP[" + a + "]." + p + "_s"] = obj[p];
					} else {
						_data["ELP[" + a + "]." + p] = obj[p];
					}

				}
				_data["ELP[" + a + "].classCd"] = 'TELE';
				a++;
			}
		}
	}
    var changed;
    if( _data["reasonForEdit"] == 'new' ){
    	if( getElementByIdOrByName("btnSubmitB").value !="Save" || getElementByIdOrByName("btnSubmitT").value !="Save"){
       		var newQC = $j("#quickedit").val();
       		if (newQC != null && newQC != "" && newQC == oldSavedQuickEntry) {
      			if( confirm("If you continue with the Edit action, the quick entry will be cleared and you will have to enter new unique quick code. Click OK to continue or  Cancel to not continue")){
    	  	  getElementByIdOrByName("quickedit").value="";
    		  getElementByIdOrByName("btnSubmitB").value="Save";
    		  getElementByIdOrByName("btnSubmitT").value="Save";
    		  		return false;
      			} else {
        			return false;
      			}
      		}

    	}
    }
    

    var qId = $j("input[name='identifier']").val();
    //alert("identifier=" + identifier + " mn=" +nm + " qId=" + qId);
    if(qId!=null && qId!="" && qId!='null'){

    	var opener = getDialogArgument(); 
	getElementByIdOrByNameNode(qId, opener.document).value =nm;
    }
    
    
    var pform = document.forms["emptyPlaceForm"];
    addFormElements(pform, _data, "");
    pform.submit();
    
    if(qId!=null && qId!="" && qId!='null'){
    	self.close();
    }
  }

  function getCounties(obj, selId)
  {
    var _data = {
      "method" : "counties",
      "state" : obj.value
    };
    $j.ajax({
      type : "POST",
      url : "/nbs/Place.do",
      data : _data,
      dataType : "json",
      success : function(data)
      {
        var x = "";
        for (var i = 0; i < data.results.length; i++) {
          x += "<option value='" + data.results[i].key + "'>" + data.results[i].value + "</option>";
        }
        $j("#" + selId).html(x);
      },
      error : function()
      {

      }
    });
  }

  function getQuickEntryCode(qec, erId, resId, hId, sId)
  {

      var obj = $j("input[name=" + qec + "]").val();
      if(erId){
        $j("#" + erId ).hide();
      }

     // var res  = validateQuickCode(obj);
     // if( res != true ){
     //    alert(res);
     //    $j("input[name=" + qec + "]").val("");
     //   return false;
     //  }
      var _data = {
        "method" : "checkQuickEntryCode",
        "quick.rootExtensionTxt" : obj
      };
      var ca = $j("input[name=ContextAction]").val();
      if( ca != null && ca != undefined){
        _data.ContextAction = ca;
      }

      $j.ajax({
        type : "POST",
        url : "/nbs/Place.do",
        data : _data,
        dataType : "json",
        success : function(data)
        {
          if( data.results == 'exists' && ca == 'GlobalPlace'){
              var newVal  =  $j("input[name=" + qec + "]").val();
              if (newVal != null)
              	newVal = $j.trim(newVal)
              //alert("new val =" + newVal + " saved=" + savedQuickCode);
              if (savedQuickCode != newVal) {
              	$j("#" + erId ).show();
              	$j("input[name=" + qec + "]").val("");
              }
          } else if( data.results == 'exists'){
             $j("#" + resId).html( makePlaceSelectedStr(data) );
             showHide([ sId ], [ hId] );
          } else if( data.results == 'unique' && ca != 'GlobalPlace'){
            if (ca != 'null') {
                 //alert('ca=' +ca);  Gst- causing thin white line - added null check
           	 $j("#" + erId).html("'" + obj + "' cannot be found. Please modify the entry and try again or use the Search functionality");
           	 $j("#" + erId).show();
           }
          }
        },
        error : function()
        {
		// alert("Note: Unexpected Ajax error on Place json call checking Quick Entry code.");
        }
      });
  }

  function makePlaceSelectedStr(pdata)
  {
     var p = "";
     p += (pdata.name ? pdata.name + "<br/>" : "");
     var a = pdata.address;
     if (a != null) {
     	p += (a.streetAddr1 ? a.streetAddr1 + "<br/>" : "");
     	p += (a.city ? a.city + ", ": "");
     	p += (a.state ? a.state + " ": "");
     	p += (a.zip ? a.zip + " ": "");
     }
     return p;
  }

  function clearPlaceSelected()
  {

  }

  function validateQuickCode(field)
  {
    var valid = new RegExp(/^[a-z0-9-]+$/i);
    var temp;
    var msg1 = "Quick Code  must consist of the following characters: Alphabetic characters from 'A' through 'Z', numeric characters '0' through '9' or hyphen '-'.  Please correct the data and try again.\n";

    if (field != null && field.length <= 10) {
       if( !valid.test(field) ){
          return msg1;
       }
    }
    else {
      return msg1;
    }
    return true;
 }

 function showErrorMessages(msg)
 {
   $j("#errorMessages ul").remove();
   var x = $j("#errorMessages").html();
   x += "<ul><li>"  + msg + "</li></ul>";
   $j("#errorMessages").html(x);
   $j("#errorMessages").show();
   $j("#globalFeedbackMessagesBar").show();
   $j('html,body').animate({scrollTop: $j("#globalFeedbackMessagesBar").offset().top });
 }

 function showMutliEntryErrorMessage(id, msg)
 {
   if( $j("#" + id + " ul").length > 0 )
     $j("#" + id + " ul").html("");

   var x = $j("#" + id).html();
   x += "<ul><li>"  + msg + "</li></ul>";
   $j("#" + id).html(x);
   $j("#" + id).show();
   $j('html,body').animate({scrollTop: $j("#" + id).offset().top });
 }

 function viewModePlace()
 {
   showHide(  ["btnEditB", "btnEditT", "btnAddB", "btnAddT"] , ["btnSubmitB", "btnSubmitT", "trAddressButtons", "trTelephoneButtons"] );
   var myArr = multiInputArr();
   showHide([], myArr);
 }


 function editPlace()
 {
   $j("input[name=mode]").val("edit");
   document.forms["placeForm"].action="/nbs/Place.do";
   document.forms["placeForm"].submit();
 }

 function multiInputArr()
 {
   var myArr = [];
   for( var i = 1; i < 13; i++){
     myArr.push("trAddr" + i);
     myArr.push("trTel" + i);
   }
   return myArr;
 }

 function showHide(shArr, hdArr)
 {
   for(var i = 0; shArr && i < shArr.length; i++){
     $j("#" + shArr[i] ).show();
   }
   for(var i = 0; hdArr && i < hdArr.length; i++){
     $j("#" + hdArr[i] ).hide();
   }
 }
 function addFormElements(pform, obj, prefix)
 {
   for(var p in obj){
     if( obj[p] != null ){
       addFormElement(pform, prefix + p, obj[p])
     }
   }
 }

function addFormElement(pform, name, val)
{
  var inp = document.createElement("input");
  inp.name = name;
  inp.value= val;
  inp.type='hidden';
  pform.appendChild(inp);
}

function merge(target, src)
{
  for ( var p in src) {
    target[p] = src[p];
  }
}

function showAddPlace()
{
  var myForm = document.forms["placeForm"];
  $j("input[name=mode]").val("add");
  myForm.action="/nbs/Place.do";
  myForm.elements["method"].value = "show";
  if( myForm.elements["place.thePlaceDT.placeUid"] != null ){
    myForm.elements["place.thePlaceDT.placeUid"].value = "";
    myForm.elements["place.thePlaceDT.placeUid"].disabled = true;
  }
  else
    addFormElements(myForm, refineSearch, "placeSearch.");
  myForm.submit();
}

function showSearch()
{
  document.forms["placeForm"].action="/nbs/Place.do";
  document.forms["placeForm"].elements["method"].value = "find";
  document.forms["placeForm"].submit();
}

function returntoSearchResult()
{
	 if( confirm("If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue") ){
  document.forms["placeForm"].action="/nbs/Place.do";
  document.forms["placeForm"].elements["method"].value = "findResults";
  document.forms["placeForm"].submit();
	 }
}

function showRefineSearch()
{
  var pform = document.forms["placeForm"];
  pform.elements["method"].value = "findRefine";
  addFormElements(pform, refineSearch, "placeSearch.");
  pform.submit();
}

function viewPlace(pid)
{
  var pform = document.forms["placeForm"];
  pform.elements["method"].value = "show";
  addFormElements(pform, {
    "placeUid" : pid
  }, "");
  pform.submit();
}

function selectPlace(nm,idfr)
{
  var returnedValue="";
  JBaseForm.getDwrPlaceDetailsByName(nm, function(retData){
	  returnedValue = retData;
	  var opener = getDialogArgument();
	  opener.handlePlaceData(returnedValue, idfr);
  	    self.close();
	  });
}

function selectPlaceByUid(uidStr,idfr)
{
  var returnedValue="";
  JBaseForm.getDwrPlaceDetailsByUid(uidStr, function(retData){
	  returnedValue = retData;
	  
	    var opener = getDialogArgument();
	    opener.handlePlaceData(returnedValue, idfr);
  		self.close();
	  });
}

function inactivatePlace()
{
  if( confirm("If you continue with the Inactivate action, you will not be able to view or access the Place data. Select OK to continue, or Cancel to not continue") ){
    document.forms["placeForm"].action="/nbs/Place.do";
    document.forms["placeForm"].method.value = "inactivate";
    document.forms["placeForm"].submit();
  }
}

function cancelPlaceForm()
{
  if( confirm("If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue") ){
	document.forms["placeForm"].action="/nbs/Place.do";
	document.forms["placeForm"].elements["method"].value = "show";
	document.forms["placeForm"].submit();
  }
}