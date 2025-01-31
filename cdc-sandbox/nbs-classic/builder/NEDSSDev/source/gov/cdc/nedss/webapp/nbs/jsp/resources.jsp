	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
	<%@ page language="java" %>
	<%@ page import="gov.cdc.nedss.systemservice.nbscontext.NBSContext" %>
	<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
    <meta http-equiv="MSThemeCompatible" content="yes"/>
    <link rel="stylesheet" type="text/css" href="reference.css">
    <link href="default.css" type="text/css" rel="stylesheet">
    <link href="nedss.css" type="text/css" rel="stylesheet">
    <link href="nedss2.css" type="text/css" rel="stylesheet">
    <link href="displaytag.css" type="text/css" rel="stylesheet">
    <link href="alternative.css" type="text/css" rel="stylesheet">
    <!-- JQuery -->
    <script src="jquery-1.2.6.min.js" type="text/javascript"></script>
    <script src="jquery.tablesorter.js" type="text/javascript"></script>
    <script src="jquery.blockUI.js" type="text/javascript"></script>
    <script src="jquery.hotkeys-0.7.8-packed.js" type="text/javascript"></script>
    <script type="text/javascript">
        /* This resolves any conflicts JQuery has with other libraries.
           By doing this, jQuery will be referred by $j instead of the default $ symbol */
        var $j = jQuery.noConflict();
        
        // set default values for the blockUI JQuery plugin
        $j.blockUI.defaults.overlayCSS.opacity = '0.4';
        $j.blockUI.defaults.css.border = '0px';
    </script>
    
    <SCRIPT language="JavaScript" src="menu.js"></SCRIPT>
    <SCRIPT Language="JavaScript" Src="javascript.js"></SCRIPT>
    <SCRIPT Language="JavaScript" Src="validate.js"></SCRIPT>
    
	<SCRIPT Language="JavaScript" Src="CalendarPopup.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="AnchorPosition.js "></SCRIPT>
	<SCRIPT Language="JavaScript" Src="date.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="PopupWindow.js"></SCRIPT>
	
	<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
	<script type='text/javascript' src='/nbs/dwr/util.js'></script>
	<SCRIPT Language="JavaScript" Src="EntitySearch.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="globals.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="prototype.js "></SCRIPT>
	<SCRIPT Language="JavaScript" Src="scriptaculous.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="slider.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="effects.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="controls.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="sniffer.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="nedss.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="DMBSpecific.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="PageSpecific.js"></SCRIPT>
	<SCRIPT Language="JavaScript" Src="PageFixedRules.js"></SCRIPT>
	
    <SCRIPT Language="JavaScript" Src="PageSectionsControl.js"></SCRIPT>
    <SCRIPT Language="JavaScript" Src="StrutsLayoutTabControl.js"></SCRIPT>
    <SCRIPT Language="JavaScript" Src="ProcessingDecision.js"></SCRIPT>
    
    <script language="javascript" src="/nbs/dwr/interface/JBaseForm.js"></script>
	<script type="text/javascript">
		DWRUtil = dwr.util;
		DWREngine = dwr.engine;
	</script>
    <link rel="stylesheet" href="yui/yui.css" type="text/css">
    <link rel="stylesheet" href="recent/common.css" type="text/css" media="screen,print">
    <% String mode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
    if (mode.equals("print")) { %>
        <link rel="stylesheet" href="recent/print.css" type="text/css" media="screen,print">
        <style type="text/css">
            @media print {
                div.screenOnly {display:none;}
            }
        </style>
    <% } else { %>
        <link rel="stylesheet" href="recent/print.css" type="text/css" media="print">
        <style type="text/css">
            @media print {
                div.screenOnly {display:none;}
            }
        </style>
    <% } %>
	
    