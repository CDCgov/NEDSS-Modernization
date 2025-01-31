  function displayTooltips()
  {
    $j(".sortable a").each(function(i) {
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
	
    if(sortSt != null && sortSt.indexOf("descending") != -1) {
        orderCls = "SortDesc.gif";
        altOrderCls = "Sort Descending";
        sortOrderCls = "FilterAndSortDesc.gif";
    	altSortOrderCls = "Filter Applied with Sort Descending";
		
    }
    var filterCls = "Filter.gif";
    var altFilterCls = "Filter Applied";
    toolTip = (colId != undefined && coldId != null) ? (colId.html() == null ? "" : colId.html()) : "";
    if(toolTip.length > 0) {
        link.attr("title", toolTip);
        imgObj.attr("src", filterCls);
        imgObj.attr("alt", altFilterCls);
        if(sortSt != null && sortSt.indexOf(headerNm) != -1 ){
            imgObj.attr("src", sortOrderCls);
            imgObj.attr("alt", altSortOrderCls);
        }
    } else {
        if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
            imgObj.attr("src", orderCls);
            imgObj.attr("alt", altOrderCls);
        }
    }
  }

  function attachIcons()
  {
    $j("#parent thead tr th a").each(function(i) {
      var html = '<img class="multiSelect" src="combo_select.gif" alt="Filter Select" tabIndex="0" align="top" border="0" id="ig' ;
      var hdr = $j(this).html();
      if(colHdrFilterKeyMap[hdr]){
        var divid = colHdrFilterKeyMap[hdr];
        $j(this).parent().append(html + divid + '"/>');
        $j("#ig" + divid).click(function(e){
          if( $j("#blockparent").is(":visible") ){
            hideFilters();
          } else {
            showFilters(divid, e);
          }
        });

        $j("#ig" + divid).keydown(function(e){
        	if(e.keyCode==13){
	            if( $j("#blockparent").is(":visible") ){
	              hideFilters();
	            } else {
	              showFilters(divid, e);
	            }
        	}
	    });
        
        
        $j("#" + divid + " tr").not(".selectAll").click(function(e){
          if( !  ($j(e.target).is("input:checkbox"))  ){
            var st = $j(this).find("input:checkbox").is(":checked");
            $j(this).find("input:checkbox").attr("checked", ! st );
          }
          enableDisableOk(divid);
        });
        $j("#" + divid + " tr.selectAll").click(function(e){
          if( !  ($j(e.target).is("input:checkbox"))  ){
            $j("#" + divid + " input[type=checkbox]").attr("checked", !  $j(this).find("input:checkbox").is(":checked") );
          } else {
              $j("#" + divid + " input[type=checkbox]").attr("checked",   $j(this).find("input:checkbox").is(":checked") );
          }
          enableDisableOk(divid);
        });
        $j("#filterTopDiv tr").mouseover( function() {$j(this).addClass('hover');}).mouseout( function() {$j(this).removeClass('hover')} );
      }
    });

    $j("#parent td[class='condition-link'] a").each(function(i) {
        $j(this).click(function(e){
          submitViewInvestigation(this.href);
        });
    });
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
    $j("#initalFormMethod").val("loadQueue");
    $j("input[id='deleteMessageLogUid']").attr("disabled", true);
    document.forms["initialForm"].submit();
  }

  function selectfilterCriteria()
  {
    document.forms[0].action = "/nbs/LoadMessageQueue.do?method=loadQueue";
    document.forms[0].submit();
  }

  function markMessageRead(mid)
  {
    $j.ajax({
      type: "POST",
      url: "/nbs/LoadMessageQueue.do",
      data: { "messageLogVO.messageLogUid": mid, "method" : "markMessage" },
      dataType: "json",
      beforeSend: function(){
        $j("#blockparent").css("height", "100%");
        $j("#blockparent").show();
        $j("#processingRequest").show();
        $j("#processingRequest").css("left", $j(window).width()/2 );
        $j("#processingRequest").css("top", $j(window).height()/2 );
      },
      success:function(data){
        $j("#igMsgStatus" + data.messageLogUid).attr("src", "email-icon.gif").attr("alt", "Email").attr("title", "Email");
        $j("#igMsgStatus" + data.messageLogUid).parent().next().next().html("Read");
        $j("#blockparent").hide();
        $j("#processingRequest").hide();
      },
      error:function(){

      }
    });
  }

  function hideFilters()
  {
    $j("#blockparent").hide();
    $j("#filterTopDiv").hide();
    $j("#filterTopDiv div").hide();
    return false;
  }
  function showFilters(key, e)
  {
      $j('#filterTopDiv').css( 'position', 'absolute' );
      $j('#filterTopDiv').css( 'top', e.pageY );
      if( e.pageX < $j(window).width() - 250 ){
        $j('#filterTopDiv').css( 'left', e.pageX );
      } else {
        $j('#filterTopDiv').css( 'left', e.pageX - 260 );
      }
      if( key == 'sdate'){
        $j('#filterTopDiv').css( 'height', 220);
      } else {
          $j('#filterTopDiv').css( 'height', 140);
      }
      $j('#filterTopDiv').show();
      $j("#blockparent").css("height", "100%");
      $j("#blockparent").show();
      $j("#" + key).show();
      var _name = $j("#" + key + " input").not(".selectAll").attr("name");
      var myArr = searchSortMap[_name];
      for(var v  = 0; myArr != null && v < myArr.length; v++){
          $j("#" + key + " input[value=" + myArr[v] + "]").attr("checked", "checked");
      }

      enableDisableOk(key);
  }

  function submitDeleteForm(uid)
  {
    if( confirm("You are about to delete this from the queue. Are you sure you want to delete it?") ){
      $j("input[id='deleteMessageLogUid']").val(uid); // if name and id are different this is the way
      document.forms["initialForm"].submit();
      return false;
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
  function submitViewInvestigation(url)
  {
    blockUIDuringFormSubmissionNoGraphic();
    document.forms[0].action =url;
    document.forms[0].submit();   
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