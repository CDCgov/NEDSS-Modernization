<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="-1">

<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, 
                 gov.cdc.nedss.util.PageConstants, 
                 gov.cdc.nedss.util.PropertyUtil" %>

<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=7">
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <%@ include file="../../jsp/resources.jsp" %>
        <title>NBS: Manage Pages</title>
        
        <!-- scripts and styles -->
        <script src="PageBuilder.js" type="text/javascript"></script>
        <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
        <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
        <script src="/nbs/dwr/interface/JPageBuilder.js" type="text/javascript"></script>
        <script src="jquery-ui-1.6.custom.min.js" type="text/javascript"></script>
        <script src="ui.sortable.js" type="text/javascript"></script>
        <script src="jquery.jeditable.mini.js" type="text/javascript"></script>
        
        <style type="text/css">
            html body {text-align:center;}
            .pageElementHover {background:#EEE;}
            
            /* general */
            .noDisplay {display:none;}
            
            /* page header */
            div.pageHeader {margin:0.25em 0em 0em 0em; width:98%; color:#a46322; height:20px; line-height:20px; 
                    font-size:1.1em; border-bottom:2px solid #DDD; font-weight:bold; text-align:left;}
            html > body div.pageHeader {min-height:20px; height:auto;}
            div.pageTitle {margin:0.25em 0em 0.5em 0em; font-weight:bold; width:98%; color:#000000; height:20px; line-height:20px; text-align:left; background:#EEE; font-size: 1.1em;}
            html > body div.pageTitle {min-height:20px; height:auto;}
            
            /* canvas */
            div#canvas {width:98%; height:680px; margin:0 auto; background:#FFF; text-align:center;}
            div#canvas a {text-decoration:none;}
            html > body div#canvas {min-height:680px; height:auto;}
            
            /* form header */
            div.formHeader {height:35px; line-height:35px; width:100%; 
                background-image:url(FormBuilderHeaderBG.jpg); background-repeat:repeat-x; border-bottom:1px solid #4778BF;
                font-family:arial;
                font-size:16px;
                color:#FFF;
                text-align:left;
                font-weight:bold;
            }
            div.formHeader div.title, div.formHeader div.details {margin-left:5px; float:left;}
            div.formHeader div.details {color:yellow; font-size:13px;}
            
            /* section */
            div.section {width:96%; margin:0 auto; border:1px solid #B5C6DF; text-align:center; height:25px; margin-top:10px; background:#C5D7F1; margin-bottom:10px;}
            html > body div.section {min-height:25px; height:auto;}
            div.section div.header {height:25px; border-bottom:1px solid #B5C6DF;}
            
            /* subSection */
            div.subSection {width:96%; margin:0 auto; border:1px solid #BACEEE; height:25px; margin-top:10px; background:#DCE7F7; margin-bottom:15px;}
            html > body div.subSection {min-height:25px; height:auto;}
            div.subSection div.header {height:25px; border-bottom:1px solid #D1D7DF; margin-bottom:8px;}
                
            /* fieldRow */
            div.fieldRow {width:96%; margin:0 auto; border:1px solid #D1DFF4; background:#F3F7FC; margin-top:2px; height:25px;}
            html > body div.fieldRow {min-height:25px; height:auto;}
            div.fieldRow div.header {height:25px; margin-bottom:0px;}
            .placeHolder {border:1px dotted #DFD7B5; width:96%; margin-top:10px; height:35px; background:#EFE6C2;}
            html > body .placeHolder {min-height:15px; height:auto;}
            
            /* sortable styles for sections, subSections and fieldRows */
            ul.sortableSections, ul.sortableSubSections, ul.sortableGroupedFieldRows, ul.sortableFieldRows {margin: 0px 2px; height:5px; padding:2px;}
            ul.sortableSections li, ul.sortableGroupedFieldRows li, ul.sortableFieldRows li {list-style:none; margin:0px 12px;}
            ul.sortableSections {width:98%; margin:0px auto;}
            html > body ul.sortableSections, html > body ul.sortableSubSections, html > body ul.sortableGroupedFieldRows, html > body ul.sortableFieldRows {min-height:5px; height:auto;}
            
            span.ieVerticalAlignHelper {height:100%; vertical-align:middle; display:inline-block;}
            
            /* element headers */
            div.section div.header div.controls,
            div.subSection div.header div.controls,
            div.fieldRow div.header div.controls {
                height:25px; width:40%; float:left; text-align:right; padding-top:2px; overflow-y: hidden; 
            }
            
            html > body div.subSection div.header div.controls {overflow-y:visible;}
            
            div.section div.header div.content,
            div.subSection div.header div.content,
            div.fieldRow div.header div.content {
                padding-top:2px; line-height:25px; height:25px; width:59%; float:left; text-align:left; margin-left:3px; cursor:move; overflow:hidden;
            }
            html > body div.fieldRow div.header div.content {margin-top:0px;}
            div.fieldRow div.header div.content {color:#185394;}

            /* fieldRow formTable */
            div.fieldRow div.body div.formRow {width:98%; margin:0 auto; text-align:left; margin:5px 0px;}
            div.fieldRow div.body div.formRow div.label {width:200px; float:left; text-align:right; margin-right:3px; color:#185394;}
            div.fieldRow div.body div.formRow div.field {width:450px; float:left;}
            
            /*UI tabs*/
            div#tabContainer {margin:0.35em 1em;}
            
            .ui-tabs-nav {
                /*resets*/margin: 0; padding: 0; border: 0; outline: 0; text-decoration: none; font-size: 100%; list-style: none;
                font-family: Arial;
                /*float: left;*/
                position: relative;
                z-index: 1;
                bottom:-1px;
                height:0px;
            }
            /* IE-7 hack. Without this, tab handles were overlapping on tab bodies in IE7 browsers.
            Borrowed from http://www.evotech.net/blog/2007/04/ie7-only-css-hacks/ */
            html>body .ui-tabs-nav { *margin-bottom: 25px; }
            
            .ui-tabs-nav ul {
                /*resets*/margin: 0; padding: 0; border: 0; outline: 0; line-height: 1.3; text-decoration: none; font-size: 100%; list-style: none;
            }
            .ui-tabs-nav li {
                /*resets*/margin: 0; padding: 0; border: 0; outline: 0; line-height: 1.3; text-decoration: none; font-size: 100%; list-style: none;
                float: left;
                border: 1px solid #DFE9F7;
                border-bottom:0px;
                margin-left:8px;
            }
            .ui-tabs-nav li a {
                /*resets*/margin: 0; padding: 0; border: 0; outline: 0; line-height: 1.3; text-decoration: none; font-size: 100%; list-style: none;
                float: left;
                font-size: 1em;
                font-weight: bold;
                text-decoration: none;
                padding: .3em 1.7em;
                color: #595959;
                background: #C3D5F1;
            }
            .ui-tabs-nav li a:hover {
                background: #fdf5ce;
                color: #8a5004;
            }

            .ui-tabs-nav li.ui-tabs-selected a, .ui-tabs-nav li.ui-tabs-selected a:hover {
                background: #F6F6F6;
                color: #000000;
            }
            .ui-tabs-panel {
                /*resets*/margin: 0; padding: 0; border: 0; outline: 0; line-height: 1em; text-decoration: none; font-size: 100%; list-style: none;
                font-family: Arial;
                clear:left;
                border: 1px solid #DFE9F7;
                background: #f6f6f6;
                color: #333333;
                padding:0;   
                font-size: 1em;
            }
            .ui-tabs-hide {
                display: none;/* for accessible hiding: position: absolute; left: -99999999px*/;
            }
            
            /* div popup */
            div.relocatePopup {
                font-size:12px; 
                width: 250px;
                height: 250px;
                background: #EEE;
                border:1px solid #CCC;
                position: absolute;
                /*-moz-opacity:0.90;
                -khtml-opacity: 0.90;
                opacity: 0.90;
                filter:alpha(opacity=90);
                */
                text-align:left;
                color:#000;
                padding:3px;
                overflow-y:scroll;
                z-index:8888;
                line-height:17px;
            }
            div.relocatePopup a:hover {background:#8BADDF; color:#000;}
            
            /* Utils */
            div.expander {clear:both;}
            div.utilButton {height:20px; line-height:20px; text-align:right; margin:5px 5px 0px 0px;}
            a.editHeaderLink {
                padding-left:2px;
                /*background: transparent url(Create.gif) no-repeat center left;*/
                display:inline;
            }
            a.editHeaderLink form { margin-top:0px; display:inline;}
            a.editHeaderLink form button {height:25px; font-size:12px; line-height:20px;}
            span.hiddenEltIdSpan {display:none;}
            html > body div.ffClear {clear:both;}
        </style>
        
        <script type="text/javascript">
            var pageBuilder = null;
            window.onload = function() {
              
                var msg = '<div class="submissionStatusMsg"> <div class="header"> Page Builder </div>' +  
		            '<div class="body"> Loading Page Builder. Please wait... </div> </div>';         
		        $j.blockUI({  
		            message: msg,  
		            css: {  
		                top:  ($j(window).height() - 100) /2 + 'px', 
		                left: ($j(window).width() - 500) /2 + 'px', 
		                width: '500px'
		            }  
		        });
               
                // declare form builder
                pageBuilder = new PageBuilder($j("#canvas"));
                pageBuilder.initialize();
                
                $j("div#canvas").show();
                
                // tab container
                var tabContainer = $j("div#tabContainer > ul").tabs({
                    add: function(event, ui) {
                        tabContainer.tabs('select', '#' + ui.panel.id);
                        pageBuilder.addElement($j("#" + ui.panel.id), 'tabBody');
                    }
                });

                $j("a.addSection").click(function(){
                    pageBuilder.addElement($j(this), 'section');
                });
                  
                $j("ul.sortableSections").sortable
                ({
                    handle:'div.header > div.content', 
                    forcePlaceholderSize: true, 
                    dropOnEmpty: true, 
                    connectWith: ['ul.sortableSections'], 
                    placeholder: 'placeHolder',
                    update: function(event, ui) { 
                        //alert("ui.helper = " + ui.helper + "; ui.position = " + ui.item.prevAll().length + "; ui.offset = " + ui.offset + "; ui.item = " + ui.item + "; ui.placeholder = " + ui.placeholder + "; ui.sender = " + ui.sender);
                    }
                });

                startCountdown();
            }
            
            function addNewTab() {
                var tabContainer = $j("div#tabContainer");
                pageBuilder.showAddElementDialog(tabContainer, "tab");
            }
            
            function savePageAsDraft() {
            	if(checkNoteComponentWithoutRepeatingSubsection()){
                	pageBuilder.savePageAsDraft();
            	}
            }
				
				function submitPage() {
					//Before submitting the page, check if there's a subsection not grouped with a note (nbs_componet_uid = 1019)
					if(checkNoteComponentWithoutRepeatingSubsection()){
						var order = pageBuilder.getPageElementsOrder();
						document.pageBuilderForm.pageElementsInOrderCsv.value = order;
						document.pageBuilderForm.action = '/nbs/ManagePage.do?method=editPageBuilderSubmit';
						
						//alert("document.pageBuilderForm.action = " + document.pageBuilderForm.action);
						//alert("document.pageBuilderForm.pageElementsInOrderCsv.value = " + document.pageBuilderForm.pageElementsInOrderCsv.value);
					   
					   
						document.pageBuilderForm.submit();
						var msg = '<div class="submissionStatusMsg"> <div class="header"> Page Builder </div>' +  
						'<div class="body"> Please wait...  The system is loading the requested page. </div> </div>';         
					$j.blockUI({  
						message: msg,  
						css: {  
							top:  ($j(window).height() - 100) /2 + 'px', 
							left: ($j(window).width() - 500) /2 + 'px', 
							width: '500px'
						}  
					});
					
				}
			}

            function cancelPageChanges() {
	                  var msg = "Do you want to cancel all the changes you have made? Please click OK to " + 
	                          "proceed or Cancel to stay on this page.";
	                  var choice = confirm(msg);
	                  if (choice) {
	                     var context = '${fn:escapeXml(fromWhere)}';
	                      if(context  != null && context == 'V'){
	  	                    window.location = '/nbs/PreviewPage.do?method=viewPageLoad';                    
	  	             }else{
	                	    window.location = '/nbs/ManagePage.do?method=list&fromPage=returnToPage';                     
	  	             }        
	                  }
	              }
           
        </script>
    </head>
    <body>
        <div id="parentWindowDiv"></div>
        <div id="doc3">
            <div id="bd" style="text-align:center;">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>

                <!-- Top button bar -->
                <div class="grayButtonBar" style="margin-bottom:0px; border-bottom:0px">
                    <input type="button" style="width:150px" value="Save and Continue" name="Save and Continue" onclick="javascript:savePageAsDraft()" />
                    <input type="button" style="width:60px" value="Submit" name="Submit" onclick="javascript:submitPage()" />
                    <input type="button" style="width:60px" value="Cancel" name="Cancel" onclick="javascript:cancelPageChanges()"/>
                </div>

                <!-- Info messages -->
                <div id="pageBuilderInfoMessagesBlock" class="hiddenElement" style="width:98%;"></div>    

                <!-- Page title -->
                <div class="pageHeader"> Edit Page </div>
                <logic:present name="pageConditionTitle">
                    <div class="pageTitle">&nbsp; <bean:write name="busObjTypeDescTxt" />: <bean:write name="pageConditionTitle" /> </div>                        
                </logic:present>
                
                <div id="canvas" style="border-top-width:0px;">
                    <html:form action="/ManagePage.do?method=editPageBuilderSubmit">
                        <input type="hidden" name="pageElementsInOrderCsv" />
                    </html:form>
                    
                    <form name="pageForm" style="text-align:center;">
                        <input type="hidden" name="popupReturnValue" />
                        <div style="width:98%; text-align:right; margin:0 auto;">
                            <a href="javascript:addNewTab()"> <img src="add.gif" alt="Add New Tab" title = "Add New Tab" /> Add New Tab </a>
                        </div>
                        <div id="pageContainer"></div>
                    </form>
                </div>
                
                <!-- Bottom button bar -->
                <div class="grayButtonBar" style="margin-top:0px; border-bottom:0px">
                    <input type="button" value="Save and Continue" name="Save and Continue" onclick="javascript:savePageAsDraft()" />
                    <input type="button" value="Submit" name="Submit" onclick="javascript:submitPage()" />
                    <input type="button" value="Cancel" name="Cancel" onclick="javascript:cancelPageChanges()"/>
                </div>    
            </div>
        </div>
    </body>
</html>