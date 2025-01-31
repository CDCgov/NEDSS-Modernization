    function loadInvestigation(link)
    {
        document.updatedNotificationsQueueForm.action = link;
        document.updatedNotificationsQueueForm.submit();
    }
    
    function updateSelectAllCheckBox()
    {
        var elts = $j("INPUT[class='isRemoved'][type='checkbox']");
        var allChecked = true;
        for (var i = 0; i < elts.length; i++) {
            if ($j(elts[i]).attr("checked") == false) {
                allChecked = false;
            }
        }
        if (allChecked == true) {
            $j("INPUT[name='selectAll'][type='checkbox']").attr('checked', true);
        } 
        else {
            $j("INPUT[name='selectAll'][type='checkbox']").attr('checked', false);
        }
    }

    function toggleNotificationSelections(toggleElt)
    {
        if (toggleElt.checked == true) {
            $j("INPUT[class='isRemoved'][type='checkbox']").attr('checked', true);
        }
        else {
            $j("INPUT[class='isRemoved'][type='checkbox']").attr('checked', false);
        }
    }

    function attachIcons()
    {
        $j("#filterDropDowns").show();
        
        // associate 'multi-select plugined' items to table column headers
        $j("#parent thead tr th a").each(function(i) {
            if($j(this).html() == 'Condition') {
                $j(this).parent().append($j("#testConditionMS"));
            }
            if($j(this).html() == 'Case Status') {
                $j(this).parent().append($j("#caseStatusMS"));
            }
            if($j(this).html() == 'Updated By') {
                $j(this).parent().append($j("#submittedByMS"));
            }
            if($j(this).html() == 'Type') {
                $j(this).parent().append($j("#notificationCodeMS"));
            }
            if($j(this).html() == 'Recipient') {
                $j(this).parent().append($j("#recipientMS"));
            }
            if($j(this).html() == 'Update Date') {
                $j(this).parent().append($j("#updateDateMS"));
            }
            if($j(this).html() == 'Patient'){
				$j(this).parent().append($j("#patient"));
            }
        });
        $j("#parent").before($j("#whitebar"));
        $j("#parent").before($j("#removeFilters"));
    }
    
    function _setAttributes(headerNm, link, colId) 
    {
        var imgObj = link.parent().find("img");
        var toolTip = "";
        var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
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
        toolTip = colId.html() == null ? "" : colId.html();
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
    
    function _handlePatient(headerNm, link) 
    {
        var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
        var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
        var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
        
        if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
            if(sortSt != null && sortSt.indexOf("descending") != -1) {
                link.after(htmlDesc);
            } else {
                link.after(htmlAsc);
            }
        }
    }
    
    function displayTooltips() {
        $j(".sortable a").each(function(i) {
            var headerNm = $j(this).html();
            if(headerNm == 'Update Date') {
                _setAttributes(headerNm, $j(this), $j("#INV147"));
            } else if(headerNm == 'Updated By') {
                _setAttributes(headerNm, $j(this), $j("#DEM102"));
            } else if(headerNm == 'Recipient') {
                _setAttributes(headerNm, $j(this), $j("#NOT119"));
            } else if(headerNm == 'Type') {
                _setAttributes(headerNm, $j(this), $j("#NOT118"));
            } else if(headerNm == 'Patient') {
            	 _setAttributes(headerNm, $j(this), $j("#PATIENT"));
            } else if(headerNm == 'Condition') {
                _setAttributes(headerNm, $j(this), $j("#INV169"));
            } else if(headerNm == 'Case Status') {
                _setAttributes(headerNm, $j(this), $j("#INV163"));
            }
        });
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
    
    function cancelFilter(key) {
        var key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));                     
        JUpdatedNotificationsQueueForm.getAnswerArray(key1, function(data) {                        
            revertOldSelections(key, data);
        });         
    }

    function clearFilter()
    {
        document.updatedNotificationsQueueForm.action ='/nbs/ManageUpdatedNotifications.do?method=loadQueue&initLoad=true&ContextAction=NNDUpdatedNotificationsAudit';
        document.updatedNotificationsQueueForm.submit();                                     
    }

    function selectfilterCriteria()
    {
        document.updatedNotificationsQueueForm.action ='/nbs/ManageUpdatedNotifications.do?method=filterNotifications';
        document.updatedNotificationsQueueForm.submit();         
    }
    
    /** Remove a single notification. */
    function removeSingleNotification(checkBoxToSelect)
    {
        $j("INPUT[name="+checkBoxToSelect+"][type='checkbox']").attr('checked', true);
        var frm = document.updatedNotificationsQueueForm;
        frm.action = '/nbs/ManageUpdatedNotifications.do?method=removeNotifications';
        frm.submit();
    }
    
    /** Handle removal of selected items from the updated notifications queue. Make
    sure atleast 1 item is selected before making a server request. */
    function removeUpdatedNotificationsInQueue()
    {
        var selectedCheckBoxElts = $j("#parent input.isRemoved[checked]");
        if (selectedCheckBoxElts.length > 0) {
            var frm = document.updatedNotificationsQueueForm;
            frm.action = '/nbs/ManageUpdatedNotifications.do?method=removeNotifications';
            frm.submit();
        }
        else {
            $j(".infoBox").hide();
            displayGlobalErrorMessage("Please select at least one Notification to be removed from the queue and try again.");
        }
        return false;
    }
    
    /** Print the updated notifications queue */
    function printQueue() {
        window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
    }
    
    /** Export the updated notifications queue */
    function exportQueue() {
        window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
    }