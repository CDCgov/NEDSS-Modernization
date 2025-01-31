var rejApprove = null;

function displayTooltips()
{
  $j(".sortable a").each(function(i)
  {
    var headerNm = $j(this).html();
    _setAttributes(headerNm, $j(this));
  });
}
function _setAttributes(headerNm, link, colId)
{
  var imgObj = link.parent().find("img");
  var toolTip = "";
  var sortSt = searchSortMap["sortSt"] == null ? "" : searchSortMap["sortSt"];
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
  toolTip = (colId != undefined && coldId != null) ? (colId.html() == null ? "" : colId.html()) : "";
  if (toolTip.length > 0) {
    link.attr("title", toolTip);
    imgObj.attr("src", filterCls);
    imgObj.attr("alt", altFilterCls);
    if (sortSt != null && sortSt.indexOf(headerNm) != -1){
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

function attachIcons()
{
  $j("#parent thead tr th a").each( 
    function(i) {
      var html = '<img class="multiSelect" src="combo_select.gif" alt="Filter Select" tabIndex="0" title="Filter Select" align="top" border="0" id="ig';
      var hdr = $j(this).html();
      if (colHdrFilterKeyMap[hdr]) {
        var divid = colHdrFilterKeyMap[hdr]; 
        $j(this).parent().append(html + divid + '"/>');
        $j("#ig" + divid).click(function(e)
        {
          if ($j("#blockparent").is(":visible")) {
            hideFilters();
          } else {
            showFilters(divid, e);
          }
        });
        $j("#ig" + divid).keydown(function(e)
                {
        	if(e.keyCode==13){
                  if ($j("#blockparent").is(":visible")) {
                    hideFilters();
                  } else {
                    showFilters(divid, e);
                  }
        	}
         });
        $j("#" + divid + " tr").not(".selectAll").click( function(e)
        {
          if( !  ($j(e.target).is("input:checkbox"))  ){
            var st = $j(this).find("input:checkbox").is(":checked");
            $j(this).find("input:checkbox").attr("checked", !st);
          }
          enableDisableOk(divid);
        });
        
        $j("#" + divid + " tr.selectAll").click( function(e)
        {
          if( !  ($j(e.target).is("input:checkbox"))  ){
            $j("#" + divid + " input[type=checkbox]").attr("checked",
                !  $j(this).find("input:checkbox").is(":checked"));
          } else {
            $j("#" + divid + " input[type=checkbox]").attr("checked",
                  $j(this).find("input:checkbox").is(":checked")); 
          }
          enableDisableOk(divid);
        });
        $j("#filterTopDiv tr").mouseover(function()
        {
          $j(this).addClass('hover');
        }).mouseout(function()
        {
          $j(this).removeClass('hover')
        });
      }
   }
  );
}

function showFilters(key, e)
{
  $j('#filterTopDiv').css('position', 'absolute');
  $j('#filterTopDiv').css('top', e.pageY);
  if (e.pageX < $j(window).width() - 250) {
    $j('#filterTopDiv').css('left', e.pageX);
  } else {
    $j('#filterTopDiv').css('left', e.pageX - 260);
  }
  if( key == 'submitDate'){
    $j('#filterTopDiv').css( 'height', 240);
    
    //$j('#filterTopDiv').css('left', $j("#parent tbody tr:first td:nth-child(3)").offset().left );
    //$j('#filterTopDiv').css('top', $j("#parent tbody tr:first td:nth-child(3)").offset().top );
    
  } else {
    $j('#filterTopDiv').css( 'height', 140);
  }
  $j('#filterTopDiv').show();
  $j("#blockparent").show();
  $j("#" + key).show();
  var _name = $j("#" + key + " input").not(".selectAll").attr("name");
  var myArr = searchSortMap[_name];
  for (var v = 0; myArr != null && v < myArr.length; v++) {
    if( $j("#" + key + " input[type=checkbox]").length > 0 )
       $j("#" + key + " input[value=" + myArr[v] + "]").attr("checked", "checked");
    else
        $j("#" + key + " input").val(myArr[v]);
  }

  enableDisableOk(key);
}

function onKeyUpValidate(obj)
{
  $j("#b1").attr("disabled", (obj.value == ""));
  $j("#b3").attr("disabled", (obj.value == ""));
}

function enableDisableOk(key)
{
  var chk = $j("#" + key + " input[type=checkbox]:checked").length;
  var inp = ( $j("#" + key + " input[type=text]").length > 0 ? $j("#" + key + " input[type=text]").attr("value").length : 0);
  if( chk == 0 && inp == 0 ){
    $j("#b1").attr("disabled", true);
    $j("#b3").attr("disabled", true);
  } else {
    $j("#b1").attr("disabled", false);
    $j("#b3").attr("disabled", false);
  }
  if( $j("#" + key + " input:checked").not(".selectAll").length == $j("#" + key + " input").not(".selectAll").length ){
    $j("#" + key + " input.selectAll").attr("checked", true);
  } else {
    $j("#" + key + " input.selectAll").attr("checked", false);
  }
}

function clearFilter()
{
  document.forms["initialForm"].submit();
}

function selectfilterCriteria()
{
  document.forms[0].action = "/nbs/LoadSupervisorReviewQueue.do";
  document.forms[0].submit();
}

 
function hideFilters()
{
  $j("#blockparent").hide();
  $j("#filterTopDiv").hide();
  $j("#filterTopDiv div").hide();
  return false;
}

function submitSupervisorNotes(uid)
{
  var rejMessage = $j("#rejectMessageDiv textarea").val();
  if( rejApprove == "reject" && $j.trim(rejMessage).length <= 0){
     alert( 'You need to provide reject notes.')
     return;
  }
  else { 
    document.forms[0].action = "/nbs/LoadSupervisorReviewQueue.do";
    document.forms[0].elements["supervisorVO.caseReviewStatus"].value = rejApprove;
    document.forms[0].elements["method"].value = "updateInvestigationClosure";
    document.forms[0].submit();
  }
}

function printQueue()
{
  window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" : $j(".exportlinks a:last").attr("href");
}

function exportQueue()
{
  window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}

function showRejectMessageDialog(uid, rea)
{
  $j("#blockparent").show();
  if( rea == "Approval Notes"){
    rejApprove = "accept"; 
  } else {
    rejApprove = "reject";
  }
  $j("#rejectPublicHealthCaseUid").val(uid);
  $j("#supervisorCommentsHeading").text(rea) ;
  $j("#rejectMessageDiv").css("left", $j(window).width()/2 - 125 );
  $j("#rejectMessageDiv").css("top", $j(window).height()/2 - 75 );
  $j("#rejectMessageDiv").show();
}

function closeRejectMessageDialog()
{
  rejApprove = null;
  $j("#blockparent").hide();
  $j("#rejectMessageDiv").hide();
}

function submitViewInvestigation(_link)
{
    blockUIDuringFormSubmissionNoGraphic();
    document.forms["initialForm"].action = _link.split("?")[0];
    $j("#initialForm input").attr("disabled", true);
    var params = _link.split("?")[1].split("&");
    $j(params).each( function(i, x){
        var n = x.split("=")[0];
        var v = x.split("=")[1];
        $j('#initialForm').append('<input type="hidden" name="' + n + '" value="' + v + '" />');  
    });
    document.forms["initialForm"].submit();   
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
