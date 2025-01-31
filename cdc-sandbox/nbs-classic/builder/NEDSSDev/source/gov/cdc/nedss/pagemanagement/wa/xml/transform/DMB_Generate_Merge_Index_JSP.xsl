<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>   
    
    <xsl:template match="Page" xml:space="preserve">
       <xsl:comment> ### DMB: BEGIN JSP PAGE GENERATE ###--</xsl:comment>
    <html lang="en">
    <head>
    <title>NBS:<xsl:value-of select="PageName"/></title>
    <xsl:text disable-output-escaping="yes"><![CDATA[<%@ include file="/jsp/tags.jsp" %>]]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[<%@ include file="/jsp/resources.jsp" %> ]]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[ <link href="compare.css" type="text/css" rel="stylesheet">]]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[<%@ page import="java.util.*" %>]]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>]]></xsl:text>
     <xsl:text disable-output-escaping="yes"><![CDATA[  <SCRIPT Language="JavaScript" Src="compareSpecific.js"></SCRIPT>]]></xsl:text>  
     
        <script language="JavaScript"> 
        <xsl:text disable-output-escaping="yes"><![CDATA[     
        
        	
	    document.onkeypress = function (e) {
		var attribute = getCorrectAttribute(document.activeElement, "type", document.activeElement.type);
		
		if(attribute!=null){
			if(attribute.toLowerCase()!="button")
				if (e!=null && e.which=='13')
					return false;
		}
		else
			return false;
		}
		
		document.onkeydown = function (e) {
			var attribute = getCorrectAttribute(document.activeElement, "type", document.activeElement.type);
			
			if(attribute==null){
			 if(e.which=='8')
				return false;
			}
			
			return preventF12(e);
		}
		
        function cancelForm()
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${PageForm.attributeMap.ReturnToFileEvents}";
            } else {
                return false;
            }
        }
        function saveForm() 
        {
          var method="${PageForm.attributeMap.method}";  
           
          if(method=="createSubmit"){
            if (pgCheckForErrorsOnSubmit() == true) {
              return false;
            } else {
              stdSpecialSubmitCheck(); //last min checks for STD
              var nbsSecurityJurisdictions = "${PageForm.attributeMap.NBSSecurityJurisdictions}";
              var valJuris =  validatePageJurisdiction("INV107",nbsSecurityJurisdictions);
              if (valJuris != null && valJuris == 'false')
              	return false; //user wants to edit jurisdiction
              if (valJuris != null && valJuris == 'true')
              	document.forms[0].action ="/nbs/PageAction.do?method=createSubmit&ContextAction=SubmitNoViewAccess";
              else              
                document.forms[0].action ="/nbs/PageAction.do?method=createSubmit&ContextAction=Submit";
              document.forms[0].submit();
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
             else   {       
            if (pgCheckForErrorsOnSubmit() == true) {
				 return false;
		    } else {
		      stdSpecialSubmitCheck(); //last min checks for STD
		      
		       var divElt = getElementByIdOrByName("parent");
		    	divElt.style.display = "block";		
	            var o = new Object();
	            o.opener = self;
	            
	          //  var URL =  "/nbs/PortPage.do?method=loadProdRunPopUp&selectedCondition="+selectedCondition+"&numberOfCasesToMigrate="+getElementByIdOrByName("numberOfCasesToMigrate").value;
	            var URL = "/nbs/jsp/MergeSubmitPopUp.jsp";
	            var modWin = openWindow(URL,o,GetDialogFeatures(600,360,false,false),divElt,"");
	            return false;
		/* 	  document.forms[0].action ="/nbs/PageAction.do?method=mergeSubmit&ContextAction=Submit";	
			  document.forms[0].submit();
			  var msg = '<div class="submissionStatusMsg"> <div class="header"> Page Builder </div>' +  
			        	'<div class="body"> Please wait...  The system is loading the requested page. </div> </div>';     */     
			 /*  $j.blockUI({  
				message: msg,  
				css: {  
					top:  ($j(window).height() - 100) /2 + 'px', 
					left: ($j(window).width() - 500) /2 + 'px', 
					width: '500px'
				}  
			  });   */ 
				/*  var divElt = getElementByIdOrByName("blockparent");
			    	divElt.style.display = "block";		
		            var o = new Object();
		            o.opener = self;
		            
		          //  var URL =  "/nbs/PortPage.do?method=loadProdRunPopUp&selectedCondition="+selectedCondition+"&numberOfCasesToMigrate="+getElementByIdOrByName("numberOfCasesToMigrate").value;
		            var URL = "/nbs/jsp/MergeSubmitPopUp.jsp";
		            var modWin = openWindow(URL,o,GetDialogFeatures(600,360,false,false),divElt,"");
		            */
		    	   
			}
          } 
        }
 
     ]]> </xsl:text>      
              
          
     <xsl:comment> =========Begin Javascript Functions for Dynamic Rules==========</xsl:comment>
     <xsl:for-each select="AddedJavaScriptFunction">
  <xsl:value-of select="." disable-output-escaping="yes"/>
      </xsl:for-each>
      
   
      
     function pgCheckForDynamicRuleErrorsOnSubmit() {
     	var errorElts = new Array();
    	var errorLabels = new Array();
    	var retVal;
     	<xsl:for-each select="OnSubmitFunction"><xsl:text disable-output-escaping="yes">retVal = </xsl:text><xsl:value-of select="FunctionName"/><xsl:text disable-output-escaping="yes">;</xsl:text>
     	   <xsl:text disable-output-escaping="yes"><![CDATA[if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); } ]]> </xsl:text>
        </xsl:for-each>
        <xsl:text disable-output-escaping="yes">return {elements : errorElts, labels : errorLabels};</xsl:text>

      }
      
      function pgCheckForFieldsToHighlightOnEdit() {
          //only check if we are in edit mode
          var actionMode =getElementByIdOrByName("actionMode") == null ? "" :getElementByIdOrByName("actionMode").value;
          if(actionMode != 'Edit') { 
              return;
          }
          <xsl:text disable-output-escaping="yes"><![CDATA[
          var strFields = "<%=request.getAttribute("field_list_to_hilight")%>";
          ]]> </xsl:text>
          if(strFields == "null") {
          	return;
          }
          pgProcessErrorFieldsToHilight(strFields);
          
      }      
      
      function pgCheckDynamicRulesOnLoad() {
           <xsl:if test="OnLoadFunctions">
           	<xsl:value-of select="OnLoadFunctions" disable-output-escaping="yes"/>
          </xsl:if>
       return;
      }

	<xsl:comment> === batch subsection add edit check functions (if any) follow ===</xsl:comment>
     	<xsl:for-each select="OnBatchAddFunction"><xsl:text disable-output-escaping="yes">function pg</xsl:text><xsl:value-of select="BatchSubsectionIdentifier"/><xsl:text disable-output-escaping="yes">BatchAddFunction()</xsl:text>
     	   <xsl:text disable-output-escaping="yes"><![CDATA[ { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			]]> </xsl:text>
     	        <xsl:for-each select="FunctionToCall">
     	   		<xsl:text disable-output-escaping="yes"><![CDATA[retRule=]]></xsl:text><xsl:value-of select="FunctionName"/><xsl:text disable-output-escaping="yes"><![CDATA[; ]]></xsl:text>
     	   		<xsl:text disable-output-escaping="yes"><![CDATA[if (retRule != null && retRule.labels != undefined) { errorLabels = errorLabels.concat(retRule.labels); } ]]></xsl:text>
        	</xsl:for-each>
        	
        	<xsl:text disable-output-escaping="yes"><![CDATA[retVal=pgCheckForErrorsOnBatchSubsection(']]></xsl:text><xsl:value-of select="BatchSubsectionIdentifier"/><xsl:text disable-output-escaping="yes"><![CDATA[');]]></xsl:text>
         	<xsl:text disable-output-escaping="yes"><![CDATA[if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } ]]></xsl:text>

        
        	<xsl:text disable-output-escaping="yes"><![CDATA[if (errorLabels.length > 0) {]]></xsl:text>
        	<xsl:text disable-output-escaping="yes"><![CDATA[displayErrors(']]></xsl:text><xsl:value-of select="BatchSubsectionIdentifier"/><xsl:text disable-output-escaping="yes"><![CDATA[errorMessages', errorLabels); return false;}]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[$j('#]]></xsl:text><xsl:value-of select="BatchSubsectionIdentifier"/><xsl:text disable-output-escaping="yes"><![CDATA[errorMessages').css("display", "none");]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[return true;]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[}]]></xsl:text>
        </xsl:for-each>
      
     </script>
     
      
<xsl:text disable-output-escaping="yes"><![CDATA[ 
    <% 
        Map map = new HashMap();
        if(request.getAttribute("SubSecStructureMap") != null){
                          map =(Map)request.getAttribute("SubSecStructureMap");
     }
     
     Map map2 = new HashMap();
        if(request.getAttribute("SubSecStructureMap2") != null){
                          map2 =(Map)request.getAttribute("SubSecStructureMap2");
						  
						  
     }
     
     %>
  ]]> </xsl:text>
               
  <script language="JavaScript"> 
  <xsl:text disable-output-escaping="yes"><![CDATA[      

    var answerCache = { }; //global for batch records
    var answerCache2 = { }; //global for batch records
    
    var viewed = -1,count=0;   
                    
    function populateBatchRecords()
    {
       dwr.engine.beginBatch();
       var map,ans;          
       JPageForm.getBatchEntryMap(function(map) {
          for (var key in map) {
            count++;
            fillTable(key,"pattern"+key ,"questionbody"+key );
            fillTable2(key+"_2","pattern"+key+"_2" ,"questionbody"+key+"_2" );	
          } 		
           highlightBatchEntrySection(); 
        }); 	
        dwr.engine.endBatch();
    
    }		  

    function writeQuestion( subSecNm,pattern,questionbody) {	
	    var t =getElementByIdOrByName(subSecNm); 	
        var len=0;
	    //t.style.display = "block";
	    <% String[][]  batchrecinsert  =new String[20][7];  
	       if(map != null){ 
		     Iterator itLab1 = map.entrySet().iterator(); 
		     while(itLab1.hasNext()){  
			   Map.Entry pair = (Map.Entry)itLab1.next();
		%>
							     
	     if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecinsert  =  (String[][])pair.getValue();  
		 for(int i=0;i<batchrecinsert.length;i++){   
			String checknull1 = batchrecinsert[i][0]; 
			if(checknull1 != null && checknull1 != ""){%>
		var key =   "<%=batchrecinsert[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len =  len +1; 
		}
		<%} }%>
	    }
	<%} }%>
		for (var i = 0; i <len+1; i++){
		   $j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		   $j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
		}
		var map = {};	var emptyrow="yes";
		var code = "1013";
		dwr.engine.beginBatch(); 
		<% batchrecinsert  =new String[20][7];  
		if(map != null){ 
			Iterator itSSMap = map.entrySet().iterator();
			while(itSSMap.hasNext()){ 
				Map.Entry pair = (Map.Entry)itSSMap.next();%>
				if(subSecNm == "<%=pair.getKey().toString()%>"){
					<% batchrecinsert  =  (String[][])pair.getValue();
					   for(int i=0;i<batchrecinsert.length;i++){
				            String checkifnull  = batchrecinsert[i][0];					
					    if(checkifnull  != null && checkifnull  != "null" && checkifnull  != ""){ %>
						var qId= "<%=batchrecinsert[i][0]%>";
						var componentId = "<%=batchrecinsert[i][5]%>";
						if(qId != null && qId != "null" && qId != ' '){
							if(document.getElementById(qId) != null){
								map[qId] = getRepeatingBlockUtilDispText(qId, componentId);
								<%if( "1017".equalsIgnoreCase(batchrecinsert[i][5]) ){%>
                                    map[qId + "Disp"] = $j("#" + qId + "Disp").html();
                                <%}%>
								emptyrow = repeatingBlockCheckForEmptyRow(qId, emptyrow);
							}
			 			}
			<%} }%>
				} 
			<%} }%>
			var batchentry = { subsecNm:subSecNm, id:viewed,answerMap:map};  
			 if(emptyrow=="yes"){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, " At least one field must be entered when adding a repeating block.");
		            dwr.engine.endBatch();	
		            return false;
			 }  				

			JPageForm.setAnswer(batchentry,"<%=request.getSession()%>");
			fillTable(subSecNm,pattern,questionbody);
			<%  if(map != null){ 
				Iterator itSSMap = map.entrySet().iterator();
				while(itSSMap.hasNext()){ 
					Map.Entry pair = (Map.Entry)itSSMap.next();%>
			if(subSecNm == "<%=pair.getKey().toString()%>"){
				<% batchrecinsert  =  (String[][])pair.getValue();
					 for(int i=0;i<batchrecinsert.length;i++){  
					String checknull1 = batchrecinsert[i][0];
					if(checknull1 != null && checknull1 != ""){%> 
				var key =   "<%=batchrecinsert[i][0]%>";
				if(key != null &&getElementByIdOrByName(key) != null){
					dwr.util.setValue(key, "");
					if(key+"Oth" != null &&getElementByIdOrByName(key+"Oth") != null){
						dwr.util.setValue(key+"Oth", "");
					}
					if(key+"UNIT" != null &&getElementByIdOrByName(key+"UNIT") != null){
						dwr.util.setValue(key+"UNIT", "");
					}
					if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
						displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
					}
					var type = "<%=batchrecinsert[i][5]%>"; 
					if(type == "1007" || type == "1013"){	
						if(document.getElementById(key) != null){ 
							autocompTxtValuesForJSPByElement(key);
						}
					}
					if (type == '1017') {
						repeatingBlockClearParticipant(key);
					}
					if(document.getElementById(key+"UNIT") != null ) {
						autocompTxtValuesForJSPByElement(key+"UNIT"); 
		            }
		        }
				<%} }%> 
			 }
			<%} }%> 
			var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
			if(rowhide!=null){
			rowhide.style.display = '';
			}
			var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
			if(rowshow!=null){
			rowshow.style.display = 'none';
			}
			var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
			if(rowshow1!=null){
			rowshow1.style.display = 'none';
			}
			
			viewed = -1;
			dwr.engine.endBatch();
    }


    function editClicked(eleid,subSecNm) {
		unhideBatchImg(subSecNm);
		dwr.engine.beginBatch(); 
		clearClicked(subSecNm); 
		// id of the form "edit{id}", eg "edit42". We lookup the "42"
		var answer = answerCache[eleid.substring(4+subSecNm.length)];
		viewed = answer.id;	 
		var map = answer.answerMap;
		var mulVal;
		var partVal;
		var othVal;
		var selectedmulVal;
		var handlemulVal;
		var code = "1013";	
	  //Specific code for  country to state and state to county mapping
	var stateCode = answer.answerMap['INV503'];
	if(stateCode != null && stateCode != "" &&getElementByIdOrByName('INV505') != null){
		stateCode = stateCode.substring(0, stateCode.indexOf("$$"));
		JPageForm.getDwrCountiesForState(stateCode, function(data) {
		DWRUtil.removeAllOptions("INV505");
		DWRUtil.addOptions("INV505", data, "key", "value" );
		});
	}
	var countryCode = answer.answerMap['INV502'];
	if(countryCode != null && countryCode != "" &&getElementByIdOrByName('INV503') != null){
		countryCode = countryCode.substring(0, countryCode.indexOf("$$"));
		JPageForm.getfilteredStatesByCountry(countryCode, function(data) {
		DWRUtil.removeAllOptions("INV503");
		DWRUtil.addOptions("INV503", data, "key", "value" );
		});
	}
	<% String[][] batchrecedit  =new String[20][7]; 
	if(map != null) {
		Iterator  itLab2 = map.entrySet().iterator(); 
		while(itLab2.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab2.next();%>
			if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecedit  =  (String[][])pair.getValue();
		    for(int i=0;i<batchrecedit.length;i++){
			if(  null != batchrecedit[i][0]) { 
				 String str1 = batchrecedit[i][0] + "UNIT" ;  %>
			dwr.util.setValue( "<%=batchrecedit[i][0]%>","");
			<%  str1 = batchrecedit[i][0] + "UNIT" ;  %>
                        dwr.util.setValue( "<%=str1%>","");
                        dwr.util.setValue( "<%=batchrecedit[i][0]%>"+"Oth","");
			var type = "<%=batchrecedit[i][5]%>"; 
			if( type == "1007" || type == "1013"){
				if(document.getElementById("<%=batchrecedit[i][0]%>") != null){
					autocompTxtValuesForJSPByElement("<%=batchrecedit[i][0]%>");
				}
			}
			if (type == "1017") {
				repeatingBlockReadyParticipantEdit("<%=batchrecedit[i][0]%>");
			}
			<%  str1 = batchrecedit[i][0] + "UNIT"; %>
			if(document.getElementById("<%=str1%>") != null ) {
				   autocompTxtValuesForJSPByElement("<%=str1%>"); 
			}
			<%}}%>
			JPageForm.updateAnswer(answer,function(answer) { 
			
			   for (var key in answer.answerMap) { 
	    			var uiComponent = "";
	    			<% for(int i=0;i<batchrecedit.length;i++){
	    		    	if(  null != batchrecedit[i][0]) { %> 
					if(key == "<%=batchrecedit[i][0]%>" )
						uiComponent = "<%=batchrecedit[i][5]%>";
				<%}}%>					
			    if(answer.answerMap[key] != null && answer.answerMap[key] != '' &&  (uiComponent == "1013" || uiComponent == "1017")){
				<% for(int i=0;i<batchrecedit.length;i++){
					  if(  null != batchrecedit[i][0]) { %> 
				if(key == "<%=batchrecedit[i][0]%>" && code == "<%=batchrecedit[i][5]%>"){
					mulVal = answer.answerMap[key]; 
					repeatingBlockHandleMultiVal (mulVal, key);
				} 
				else if (key == "<%=batchrecedit[i][0]%>" && "1017" == "<%=batchrecedit[i][5]%>"){
					    			partVal = answer.answerMap[key];
					    			repeatingBlockHandleEditParticipant (partVal, key, answer.answerMap[key + "Disp"]);
	    			}
				if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
							displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
				}

			<% }}%>	
			}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf(":") !=  -1 &&getElementByIdOrByName(key+"Oth") != undefined){	
					var otherVal = answer.answerMap[key];			 
					dwr.util.setValue(key,otherVal.substring(0,otherVal.indexOf(":")));
					dwr.util.setValue(key+"Oth", otherVal.substring(otherVal.indexOf(":")+1));
					document.getElementById(key+"Oth").disabled=false;
			} else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf("$sn$") !=  -1){
					var fval = answer.answerMap[key];								 
					dwr.util.setValue(key,fval.substring(0,fval.indexOf("$sn$"))); 
					// alert(fval.substring(structVal.length+1,fval.length));
					dwr.util.setValue(key+"UNIT", fval.substring(fval.indexOf("$sn$")+4,fval.length));
			} else {    
				// alert( answer.answerMap[key] + "....");
				mulVal = answer.answerMap[key];
				if(mulVal  != null && mulVal.indexOf("$MulOth$") != -1){ 
					othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
			                mulVal = mulVal.substring(0,mulVal.indexOf("$MulOth$") );
			                if(mulVal  != null && mulVal != ''){	
			                       getElementByIdOrByName(key).value = mulVal ;
					}
                                        if(othVal != null && othVal != ''){	 
			                        getElementByIdOrByName(key+"Oth").value = othVal;
			                }
			          }else{
					if(answer.answerMap[key] != null && answer.answerMap[key] != ''){ 
						document.getElementById(key).value = answer.answerMap[key];
					}
			} 
			//getElementByIdOrByName(key).value=answer.answerMap[key];        
			// alert("id = " +key+ " val = "+document.getElementById(key));
		}
		if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
				displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
		}
	}
	for (var key in answer.answerMap) {
	     <% for(int i=0;i<batchrecedit.length;i++){
		  if(  null != batchrecedit[i][0]) { %> 
		var type = "<%=batchrecedit[i][5]%>"; 
		if(key == "<%=batchrecedit[i][0]%>" &&( type == "1007" || type == "1013")){
			if(document.getElementById(key) != null){
				autocompTxtValuesForJSPByElement(key);
			}
		}
		<% String str1 = batchrecedit[i][0] + "UNIT" ;  %>
		if(document.getElementById("<%=str1%>") != null &&getElementByIdOrByName("<%=str1%>").value != null &&getElementByIdOrByName("<%=str1%>").value != '') {
			autocompTxtValuesForJSPByElement("<%=str1%>"); 
		}
	<%}}%> 		
	}    
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';	
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = '';
	}
	<% if(batchrecedit != null && batchrecedit.length > 0 ){
	    for(int i=0;i<batchrecedit.length;i++){
		  if(null != batchrecedit[i][0]) {  
			 String str = batchrecedit[i][0] +"L"; 
			 String str1 = batchrecedit[i][0] +"Oth"; %>
		var key = "<%=batchrecedit[i][0]%>";
		var keyL =  "<%=str%>";
		var keyOth = "<%=str1%>";
		if(document.getElementById(key) != null){
			// alert(document.getElementById(key).value);
			document.getElementById(key).disabled = false;
			document.getElementById(keyL).disabled = false;
			$j("#"+key).parent().parent().find("img").attr("disabled", false);
			
			var calendars = $j( "img[src*='calendar.gif']");
    		if($j("#"+key).parent().parent().find(calendars)[0]!=undefined && $j("#"+key).parent().parent().find(calendars)[0]!=null)
				$j("#"+key).parent().parent().find(calendars)[0].attr("tabIndex", "0");
			$j("#"+key).parent().parent().find("input").attr("disabled", false);
		}
		if(document.getElementById(key+"Oth") != null){
			//alert(document.getElementById(key+"Oth").value);
			//alert(document.getElementById(key+"Oth").disabled);
			document.getElementById(key+"Oth").disabled = false;
			//alert(document.getElementById(key+"OthL").disabled);
			document.getElementById(key+"OthL").disabled = false;
			$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", false);
			enableOrDisableOther(key) ;
		}
		<%}}}%>
	  	});
     		}
    	<%}}%>
     	dwr.engine.endBatch(); 
	}
	
	function fillTable(subSecNm,pattern,questionbody) {
	  JPageForm.getAllAnswer(subSecNm,function(answer) {
		  // Delete all the rows except for the "pattern" row
		  dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});
          dwr.util.setEscapeHtml(false);
		  // Create a new set cloned from the pattern row
		  var ans, id,rowclass="";
		<% if(map !=  null){
			Iterator    itLab3 = map.entrySet().iterator(); 
			String[][] batchrecview  = null; 
			while(itLab3.hasNext()){  
				Map.Entry pair = (Map.Entry)itLab3.next();%>
		if(subSecNm == "<%=pair.getKey().toString()%>"){
			<% batchrecview =  (String[][])pair.getValue();%>  
		if(answer !=null && answer.length != 0){
			for (var i = 0; i < answer.length; i++){
				ans = answer[i];		; 
				id = ans.id;	     
				dwr.util.cloneNode(pattern, { idSuffix:id });
			  <% for(int i=0;i<batchrecview.length;i++){
				String checknull = batchrecview[i][0]; 
				if(checknull != null && checknull != ""){ %>
				for (var key in ans.answerMap) {
					if(!(key == null || key == 'null') && key == "<%=batchrecview[i][0]%>"){
					    var val = ans.answerMap[key];
						<%if( "1017".equalsIgnoreCase(batchrecview[i][5]) ){%>
					    val = ans.answerMap[key + "Disp"] ? ans.answerMap[key + "Disp"] : val ;
                        <%}%>
					    val = repeatingBlockFillValue(val);
					    dwr.util.setValue("table" + key + id, val);
					}
				}
		<%}}%>
		$(pattern + id).style.display = "";   
		answerCache[id] = ans;
		if(rowclass=="")
			rowclass="odd";	
			document.getElementById(pattern  + id).setAttribute("className",rowclass);
			// alert("Alert new "+document.getElementById(pattern  + id).getAttribute("className"));
			$j("#" + pattern  + id).css("background-color","white");
			// alert("Alert new "+document.getElementById(pattern  + id));
			if(rowclass=="odd"){
				rowclass = "odd";
			} else if(rowclass=="even"){
				rowclass = "odd";
			} 
		  }
		  $j("#no"+pattern).hide();
	    } else{	
		  $j("#no"+pattern).show(); 
	    }  //if else answer.length ==0 ends 
	  }
	<%}}%>
	    clearClicked(subSecNm); 
	     highlightBatchEntrySection();
	  });
    }


	function fillTable2(subSecNm,pattern,questionbody) {
	  JPageForm.getAllAnswer2(subSecNm,function(answer) {
		  // Delete all the rows except for the "pattern" row
		  dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});
          dwr.util.setEscapeHtml(false);
		  // Create a new set cloned from the pattern row
		  var ans, id,rowclass="";

		<% if(map2 !=  null){
			Iterator    itLab3 = map2.entrySet().iterator(); 
			String[][] batchrecview  = null; 
			while(itLab3.hasNext()){  
				Map.Entry pair = (Map.Entry)itLab3.next();%>
				
		if(subSecNm == "<%=pair.getKey().toString()%>"){
			<% batchrecview =  (String[][])pair.getValue();%>  
		if(answer !=null && answer.length != 0){
			for (var i = 0; i < answer.length; i++){
				ans = answer[i];		; 
				id = ans.id;	     
				dwr.util.cloneNode(pattern, { idSuffix:id });
			  <% for(int i=0;i<batchrecview.length;i++){
				String checknull = batchrecview[i][0]; 
				if(checknull != null && checknull != ""){ %>
				for (var key in ans.answerMap) {
					if(!(key == null || key == 'null') && key == "<%=batchrecview[i][0]%>"){
					    var val = ans.answerMap[key];
						<%if( "1017".equalsIgnoreCase(batchrecview[i][5]) ){%>
					    val = ans.answerMap[key + "Disp"] ? ans.answerMap[key + "Disp"] : val ;
                        <%}%>
					    val = repeatingBlockFillValue(val);
					    dwr.util.setValue("table" + key + id, val);
					}
				}
		<%}}%>
		$(pattern + id).style.display = "";   
		answerCache2[id] = ans;
		if(rowclass=="")
			rowclass="odd";	
			document.getElementById(pattern  + id).setAttribute("className",rowclass);
			// alert("Alert new "+document.getElementById(pattern  + id).getAttribute("className"));
			$j("#" + pattern  + id).css("background-color","white");
			// alert("Alert new "+document.getElementById(pattern  + id));
			if(rowclass=="odd"){
				rowclass = "odd";
			} else if(rowclass=="even"){
				rowclass = "odd";
			} 
		  }
		  $j("#no"+pattern).hide();
	    } else{	
		  $j("#no"+pattern).show(); 
	    }  //if else answer.length ==0 ends 
	  }
	<%}}%>
	    clearClicked(subSecNm); 
	     highlightBatchEntrySection();
	  });
    }
    
function deleteClicked(eleid,subSecNm,pattern,questionbody) { 
                		
      var t =getElementByIdOrByName(subSecNm); 	
      var len=0;
      //t.style.display = "block";    
      <%    String[][]  batchrecinsert2  =new String[20][7];  
       if(map != null){ 
		Iterator itLab1 = map.entrySet().iterator(); 
		while(itLab1.hasNext()){ 		   
		Map.Entry pair = (Map.Entry)itLab1.next();%>
	   if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecinsert2  =  (String[][])pair.getValue();  
		 for(int i=0;i<batchrecinsert2.length;i++){   
		String checknull1 = batchrecinsert2[i][0];  
		if(checknull1 != null && checknull1 != ""){%> 
			var key =   "<%=batchrecinsert2[i][0]%>";
			if(key != null && key != 'undefined' && key != ''){
				len =  len +1;
			}
		<%} }%>
	   	}
	   <%} }%>
	for (var i = 0; i <len+1; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	for (var i = 0; i < len+1; i ++)   {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");	
	}		
        // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
        var answer = answerCache[eleid.substring(6+subSecNm.length)];
        if (confirm("You have indicated that you would like to delete this row. Would you like to continue with this action?")) {
             dwr.engine.beginBatch();
             JPageForm.deleteAnswer(answer);
             fillTable(subSecNm,pattern,questionbody);
         <%  String[][] batchrecdel  =new String[20][7];  
	     if(map != null){ 
		Iterator itLab8 = map.entrySet().iterator();
		while(itLab8.hasNext()){		   
		   Map.Entry pair = (Map.Entry)itLab8.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<% batchrecdel  =  (String[][])pair.getValue();
           for(int i=0;i<batchrecdel.length;i++){   
	   String delstr =  batchrecdel[i][0];
	   if(delstr != null && delstr != ""){ %>	
	   var key =   "<%=batchrecdel[i][0]%>";
	   if(key != null &&getElementByIdOrByName(key) != null){
		dwr.util.setValue(key, "");
		if(key+"Oth" != null &&getElementByIdOrByName(key+"Oth") != null){
			dwr.util.setValue(key+"Oth", "");
		}
		if(key+"UNIT" != null &&getElementByIdOrByName(key+"UNIT") != null){
			dwr.util.setValue(key+"UNIT", "");
		}	     
		if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
			displaySelectedOptions(document.getElementById(key), key+"-selectedValues")
		}  
		var type = "<%=batchrecdel[i][5]%>";
		if(type == "1007" || type == "1013"){
			autocompTxtValuesForJSPByElement(key);
		}
		if(document.getElementById(key+"UNIT") != null ) {
			autocompTxtValuesForJSPByElement(key+"UNIT"); 
		}
	}
	<%}}%>
	}
	<%}}%>
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide.style.display = '';
	}		                          
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none';
	}
	clearClicked(subSecNm);
	viewed = -1;
        dwr.engine.endBatch();
    }
} 


function clearQuestion() {
    viewed = -1;
    dwr.util.setValues({subsecNm:"Others", id:viewed,answerMap:null });
}

function getDropDownValues(newValue)
{
    JPageForm.getDropDownValues(newValue, function(data) {
        dwr.util.removeAllOptions(newValue);  
        dwr.util.addOptions(newValue,data,"key","value"); 
    });
}
                  
function viewClicked2(eleid,subSecNm) {	
	var t =getElementByIdOrByName(subSecNm);
	var len=0;
	//t.style.display = "block";
     <% String[][]  batchrecinsert1_2  =new String[20][7];  
	if(map2 != null){
		Iterator itLab1 = map2.entrySet().iterator(); 
		while(itLab1.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab1.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
     <% batchrecinsert1_2  =  (String[][])pair.getValue();
        for(int i=0;i<batchrecinsert1_2.length;i++){   
            String checknull1 = batchrecinsert1_2[i][0]; 
            if(checknull1 != null && checknull1 != ""){%> 
		var key =   "<%=batchrecinsert1_2[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len =  len +1;
		}
    <%}}%>
        }
    <%}}%>
	for (var i = 0; i <len+1; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	for (var i = 0; i < len+1; i ++) {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");
	}
	var key;
	dwr.engine.beginBatch(); 
	clearClicked2(subSecNm); 
	// id of the form "edit{id}", eg "edit42". We lookup the "42"
	var answer = answerCache2[eleid.substring(4+subSecNm.length)];
	viewed = answer.id;	 
	var map = answer.answerMap;	 
	var mulVal;
	var partVal;
	var selectedmulVal;
	var handlemulVal;
	var code = "1013";	
	<% String[][] batchrecedit1_2  = null;
	if(map != null) {
		Iterator  itLab2 = map.entrySet().iterator(); 
		while(itLab2.hasNext()){  
		Map.Entry pair = (Map.Entry)itLab2.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"+"_2"){
	<% batchrecedit1_2  =  (String[][])pair.getValue(); 
	     for(int i=0;i<batchrecedit1_2.length;i++){
		 if(  null != batchrecedit1_2[i][0]) {  
		 	String str1 = batchrecedit1_2[i][0] + "UNIT" ;  %>
		dwr.util.setValue( "<%=batchrecedit1_2[i][0]%>"+"_2","");
		<%  str1 = batchrecedit1_2[i][0] + "UNIT" ;  %>
                dwr.util.setValue( "<%=str1%>"+"_2","");
                dwr.util.setValue( "<%=batchrecedit1_2[i][0]%>"+"_2"+"Oth","");
		var type = "<%=batchrecedit1_2[i][5]%>";
		if( type == "1007" || type == "1013"){
		    if(document.getElementById("<%=batchrecedit1_2[i][0]%>") != null){
			autocompTxtValuesForJSPByElement("<%=batchrecedit1_2[i][0]%>"+"_2");
		    }
		}
		<%  str1 = batchrecedit1_2[i][0] + "UNIT" ;  %>
		if(document.getElementById("<%=str1%>") != null ) {
			autocompTxtValuesForJSPByElement("<%=str1%>"+"_2"); 
		}
	<%}}%>
	JPageForm.updateAnswer(answer,function(answer) {
	    for (var key in answer.answerMap) {
	    	var uiComponent = "";
	    		<% for(int i=0;i<batchrecedit1_2.length;i++){
	    		    if(  null != batchrecedit1_2[i][0]) { %> 
				if(key == "<%=batchrecedit1_2[i][0]%>" )
					uiComponent = "<%=batchrecedit1_2[i][5]%>";
			<%}}%>		
		if(answer.answerMap[key] != null && answer.answerMap[key] != '' && (uiComponent == "1013" || uiComponent == "1017")){
			<% for(int i=0;i<batchrecedit1_2.length;i++){
			     if(  null != batchrecedit1_2[i][0]) { %> 
			if(key == "<%=batchrecedit1_2[i][0]%>" && code == "<%=batchrecedit1_2[i][5]%>"){
				mulVal = answer.answerMap[key]; 
				repeatingBlockHandleMultiVal (mulVal, key+"_2");
	    		} else if (key == "<%=batchrecedit1_2[i][0]%>" && "1017" == "<%=batchrecedit1_2[i][5]%>"){
	    			partVal = answer.answerMap[key];
	    			repeatingBlockHandleViewParticipant2 (partVal, key, answer.answerMap[key + "Disp"]);
	    		}
	    			
	    if(key+"_2"+"-selectedValues" != null &&getElementByIdOrByName(key+"_2"+"-selectedValues") != null){
		displaySelectedOptions(document.getElementById(key),key+"_2"+"-selectedValues")
	    }					
	<%}}%>					
	}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf(":") !=  -1 &&getElementByIdOrByName(key+"Oth") != undefined){	
		var otherVal = answer.answerMap[key];
		dwr.util.setValue(key+"_2",otherVal.substring(0,otherVal.indexOf(":")));
		dwr.util.setValue(key+"_2"+"Oth", otherVal.substring(otherVal.indexOf(":")+1));
		document.getElementById(key+"_2"+"Oth").disabled=false;
	}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf("$sn$") !=  -1){	
		var fval = answer.answerMap[key];
		dwr.util.setValue(key+"_2",fval.substring(0,fval.indexOf("$sn$"))); 
		dwr.util.setValue(key+"_2"+"UNIT", fval.substring(fval.indexOf("$sn$")+4,fval.length));
	}else {    
		mulVal = answer.answerMap[key]; 						
		if(mulVal != null && mulVal.indexOf("$MulOth$") != -1){
			othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
			mulVal = mulVal.substring(0,mulVal.indexOf("$MulOth$") );
			if(mulVal  != null && mulVal  != ''){	
			    getElementByIdOrByName(key+"_2").value  = othVal ;
			}
                 if(othVal != null && othVal != ''){
                       getElementByIdOrByName(key+"_2"+"Oth").value = othVal ;
		}
	}else{
		if(answer.answerMap[key] != null && answer.answerMap[key] != ''){
			document.getElementById(key+"_2").value  = answer.answerMap[key];
		}
	}

	}
	if(key+"_2"+"-selectedValues" != null && getElementByIdOrByName(key+"_2"+"-selectedValues") != null){
		displaySelectedOptions(document.getElementById(key+"_2"), key+"_2"+"-selectedValues");
	}
	}
	for (var key in answer.answerMap) {
		<% for(int i=0;i<batchrecedit1_2.length;i++){
		     if(  null != batchrecedit1_2[i][0]) { %>
	    var type = "<%=batchrecedit1_2[i][5]%>";
	    if(key == "<%=batchrecedit1_2[i][0]%>" &&( type == "1007" || type == "1013")){
		if(document.getElementById(key+"_2") != null){
			autocompTxtValuesForJSPByElement(key+"_2");
		}
	    }
	<% String str1 = batchrecedit1_2[i][0] + "UNIT" ;  %>
	if(document.getElementById("<%=str1%>") != null &&getElementByIdOrByName("<%=str1%>").value != null &&getElementByIdOrByName("<%=str1%>").value != '') {
		autocompTxtValuesForJSPByElement("<%=str1%>"+"_2"); 
	}	
	<%}}%> 	
	}
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = '';
	}
	<% if(batchrecedit1_2 != null && batchrecedit1_2.length > 0 ){
	     for(int i=0;i<batchrecedit1_2.length;i++){
		  if(null != batchrecedit1_2[i][0]) { 
			 String str = batchrecedit1_2[i][0] +"_2"+"L";
		 	String str1 = batchrecedit1_2[i][0] +"_2"+"Oth"; %>
		var key = "<%=batchrecedit1_2[i][0]%>"+"_2";
		var keyL =  "<%=str%>";
		var keyOth = "<%=str1%>";
		if(document.getElementById(key) != null){
			document.getElementById(key).disabled = false;
			document.getElementById(keyL).disabled = false;
			$j("#"+key).parent().parent().find("img").attr("disabled", false);
			var calendars = $j( "img[src*='calendar.gif']");
    		if($j("#"+key).parent().parent().find(calendars)[0]!=undefined && $j("#"+key).parent().parent().find(calendars)[0]!=null)
				$j("#"+key).parent().parent().find(calendars)[0].attr("tabIndex", "0");
			$j("#"+key).parent().parent().find("input").attr("disabled", false);
		}
		if(document.getElementById(key+"Oth") != null){					
			document.getElementById(key+"Oth").disabled = true;
			$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", false);
		}
	<%}}}%>		

	<% String[][] batchrecview  = null;
	if(map != null) {
		Iterator  itLab21 = map.entrySet().iterator(); 
		while(itLab21.hasNext()){  
			pair = (Map.Entry)itLab21.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"+"_2"){
	    <%  batchrecview  =  (String[][])pair.getValue(); 
	    for(int i=0;i<batchrecview.length;i++){
		 if(null != batchrecview[i][0]) {  
		     String str = batchrecview[i][0] +"_2"+"L"; 
		     String str1 = batchrecview[i][0] +"_2"+"Oth"; %>
	    key = "<%=batchrecview[i][0]%>"+"_2";
	    component = "<%=batchrecview[i][5]%>";
	    var keyL =  "<%=str%>";
	    var keyOth = "<%=str1%>";
	    if(document.getElementById(key) != null){
	    	if (component != "1019") 
			document.getElementById(key).disabled = true;
		document.getElementById(keyL).disabled = true;
		$j("#"+key).parent().parent().find("img").attr("disabled", true);
		$j("#"+key).parent().parent().find("img").attr("tabIndex", "-1");
		$j("#"+key).parent().parent().find("input").attr("disabled", true);
	    }
	if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){		
		document.getElementById(key+"-selectedValues").disabled = true;
	}
	if(document.getElementById(key+"Oth") != null){
		document.getElementById(key+"Oth").disabled = true;
		document.getElementById(key+"OthL").disabled = true;
		$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", 	true);
	}

	<%}}%>
	}		        
	<%}}%>				               
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';		
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = '';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none'; 
	}
	});
	}
	<%}}%>
	dwr.engine.endBatch();
        }      //viewClicked2
   function clearClicked2(subSecNm) {
	var key;
	var subsectionDisabled = $j("#"+subSecNm).hasClass("batchSubSectionDisabled");
	<% String[][] batchrecclear_2  = null;
	if(map != null) {
		Iterator  itLab5 = map.entrySet().iterator(); 
		while(itLab5.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab5.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"+"_2"){
	<%  batchrecclear_2  =  (String[][])pair.getValue();
	 for(int i=0;i<batchrecclear_2.length;i++){ 
	 	if (batchrecclear_2[i][0] == null) continue;
		String checknull1 = batchrecclear_2[i][0];%>    
	    var key =   "<%=batchrecclear_2[i][0]%>";
	    var componentType = "<%=batchrecclear_2[i][5]%>";
	<%if(checknull1 != null && checknull1 != "" ){%>
	    if(key != null &&getElementByIdOrByName(key) != null && !subsectionDisabled){
		repeatingBatchClearFields(key+"_2", componentType);
	}   
	<%}}%>
	viewed = -1;
	}
	<%}}%>
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide.style.display = ''; 
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';             
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none';
	}
    } //clearClicked2  
                  
                  
                  
function viewClicked(eleid,subSecNm) {	
	var t =getElementByIdOrByName(subSecNm);
	var len=0;
	//t.style.display = "block";
     <% String[][]  batchrecinsert1  =new String[20][7];  
	if(map != null){
		Iterator itLab1 = map.entrySet().iterator(); 
		while(itLab1.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab1.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
     <% batchrecinsert1  =  (String[][])pair.getValue();
        for(int i=0;i<batchrecinsert1.length;i++){   
            String checknull1 = batchrecinsert1[i][0]; 
            if(checknull1 != null && checknull1 != ""){%> 
		var key =   "<%=batchrecinsert1[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len =  len +1;
		}
    <%}}%>
        }
    <%}}%>
	for (var i = 0; i <len+1; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	for (var i = 0; i < len+1; i ++) {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");
	}
	var key;
	dwr.engine.beginBatch(); 
	clearClicked(subSecNm); 
	// id of the form "edit{id}", eg "edit42". We lookup the "42"
	var answer = answerCache[eleid.substring(4+subSecNm.length)];
	viewed = answer.id;	 
	var map = answer.answerMap;	 
	var mulVal;
	var partVal;
	var selectedmulVal;
	var handlemulVal;
	var code = "1013";	
	<% String[][] batchrecedit1  = null;
	if(map != null) {
		Iterator  itLab2 = map.entrySet().iterator(); 
		while(itLab2.hasNext()){  
		Map.Entry pair = (Map.Entry)itLab2.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<% batchrecedit1  =  (String[][])pair.getValue(); 
	     for(int i=0;i<batchrecedit1.length;i++){
		 if(  null != batchrecedit1[i][0]) {  
		 	String str1 = batchrecedit1[i][0] + "UNIT" ;  %>
		dwr.util.setValue( "<%=batchrecedit1[i][0]%>","");
		<%  str1 = batchrecedit1[i][0] + "UNIT" ;  %>
                dwr.util.setValue( "<%=str1%>","");
                dwr.util.setValue( "<%=batchrecedit1[i][0]%>"+"Oth","");
		var type = "<%=batchrecedit1[i][5]%>";
		if( type == "1007" || type == "1013"){
		    if(document.getElementById("<%=batchrecedit1[i][0]%>") != null){
			autocompTxtValuesForJSPByElement("<%=batchrecedit1[i][0]%>");
		    }
		}
		<%  str1 = batchrecedit1[i][0] + "UNIT" ;  %>
		if(document.getElementById("<%=str1%>") != null ) {
			autocompTxtValuesForJSPByElement("<%=str1%>"); 
		}
	<%}}%>
	JPageForm.updateAnswer(answer,function(answer) {
	    for (var key in answer.answerMap) {
	    	var uiComponent = "";
	    		<% for(int i=0;i<batchrecedit1.length;i++){
	    		    if(  null != batchrecedit1[i][0]) { %> 
				if(key == "<%=batchrecedit1[i][0]%>" )
					uiComponent = "<%=batchrecedit1[i][5]%>";
			<%}}%>		
		if(answer.answerMap[key] != null && answer.answerMap[key] != '' && (uiComponent == "1013" || uiComponent == "1017")){
			<% for(int i=0;i<batchrecedit1.length;i++){
			     if(  null != batchrecedit1[i][0]) { %> 
			if(key == "<%=batchrecedit1[i][0]%>" && code == "<%=batchrecedit1[i][5]%>"){
				mulVal = answer.answerMap[key]; 
				repeatingBlockHandleMultiVal (mulVal, key);
	    		} else if (key == "<%=batchrecedit1[i][0]%>" && "1017" == "<%=batchrecedit1[i][5]%>"){
	    			partVal = answer.answerMap[key];
	    			repeatingBlockHandleViewParticipant (partVal, key, answer.answerMap[key + "Disp"]);
	    		}
	    			
	    if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
		displaySelectedOptions(document.getElementById(key), key+"-selectedValues")
	    }					
	<%}}%>					
	}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf(":") !=  -1 &&getElementByIdOrByName(key+"Oth") != undefined){	
		var otherVal = answer.answerMap[key];
		dwr.util.setValue(key,otherVal.substring(0,otherVal.indexOf(":")));
		dwr.util.setValue(key+"Oth", otherVal.substring(otherVal.indexOf(":")+1));
		document.getElementById(key+"Oth").disabled=false;
	}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf("$sn$") !=  -1){	
		var fval = answer.answerMap[key];
		dwr.util.setValue(key,fval.substring(0,fval.indexOf("$sn$"))); 
		dwr.util.setValue(key+"UNIT", fval.substring(fval.indexOf("$sn$")+4,fval.length));
	}else {    
		mulVal = answer.answerMap[key]; 						
		if(mulVal != null && mulVal.indexOf("$MulOth$") != -1){
			othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
			mulVal = mulVal.substring(0,mulVal.indexOf("$MulOth$") );
			if(mulVal  != null && mulVal  != ''){	
			    getElementByIdOrByName(key).value  = othVal ;
			}
                 if(othVal != null && othVal != ''){
                       getElementByIdOrByName(key+"Oth").value = othVal ;
		}
	}else{
		if(answer.answerMap[key] != null && answer.answerMap[key] != ''){
			document.getElementById(key).value  = answer.answerMap[key];
		}
	}

	}
	if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
		displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
	}
	}
	for (var key in answer.answerMap) {
		<% for(int i=0;i<batchrecedit1.length;i++){
		     if(  null != batchrecedit1[i][0]) { %>
	    var type = "<%=batchrecedit1[i][5]%>";
	    if(key == "<%=batchrecedit1[i][0]%>" &&( type == "1007" || type == "1013")){
		if(document.getElementById(key) != null){
			autocompTxtValuesForJSPByElement(key);
		}
	    }
	<% String str1 = batchrecedit1[i][0] + "UNIT" ;  %>
	if(document.getElementById("<%=str1%>") != null &&getElementByIdOrByName("<%=str1%>").value != null &&getElementByIdOrByName("<%=str1%>").value != '') {
		autocompTxtValuesForJSPByElement("<%=str1%>"); 
	}	
	<%}}%> 	
	}
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = '';
	}
	<% if(batchrecedit1 != null && batchrecedit1.length > 0 ){
	     for(int i=0;i<batchrecedit1.length;i++){
		  if(null != batchrecedit1[i][0]) { 
			 String str = batchrecedit1[i][0] +"L";
		 	String str1 = batchrecedit1[i][0] +"Oth"; %>
		var key = "<%=batchrecedit1[i][0]%>";
		var keyL =  "<%=str%>";
		var keyOth = "<%=str1%>";
		if(document.getElementById(key) != null){
			document.getElementById(key).disabled = false;
			document.getElementById(keyL).disabled = false;
			$j("#"+key).parent().parent().find("img").attr("disabled", false);
			var calendars = $j( "img[src*='calendar.gif']");
    		if($j("#"+key).parent().parent().find(calendars)[0]!=undefined && $j("#"+key).parent().parent().find(calendars)[0]!=null)
				$j("#"+key).parent().parent().find(calendars)[0].attr("tabIndex", "0");
			$j("#"+key).parent().parent().find("input").attr("disabled", false);
		}
		if(document.getElementById(key+"Oth") != null){					
			document.getElementById(key+"Oth").disabled = true;
			$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", false);
		}
	<%}}}%>		

	<% String[][] batchrecview  = null;
	if(map != null) {
		Iterator  itLab21 = map.entrySet().iterator(); 
		while(itLab21.hasNext()){  
			pair = (Map.Entry)itLab21.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	    <%  batchrecview  =  (String[][])pair.getValue(); 
	    for(int i=0;i<batchrecview.length;i++){
		 if(null != batchrecview[i][0]) {  
		     String str = batchrecview[i][0] +"L"; 
		     String str1 = batchrecview[i][0] +"Oth"; %>
	    key = "<%=batchrecview[i][0]%>";
	    component = "<%=batchrecview[i][5]%>";
	    var keyL =  "<%=str%>";
	    var keyOth = "<%=str1%>";
	    if(document.getElementById(key) != null){
	    	
			document.getElementById(key).disabled = true;
		document.getElementById(keyL).disabled = true;
		$j("#"+key).parent().parent().find("img").attr("disabled", true);
		$j("#"+key).parent().parent().find("img").attr("tabIndex", "-1");
		$j("#"+key).parent().parent().find("input").attr("disabled", true);
	    }
	if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){		
		document.getElementById(key+"-selectedValues").disabled = true;
	}
	if(document.getElementById(key+"Oth") != null){
		document.getElementById(key+"Oth").disabled = true;
		document.getElementById(key+"OthL").disabled = true;
		$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", 	true);
	}

	<%}}%>
	}		        
	<%}}%>				               
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';		
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = '';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none'; 
	}
	});
	}
	<%}}%>
	dwr.engine.endBatch();
        }      //viewClicked
        
   function clearClicked(subSecNm) {
	var key;
	var subsectionDisabled = $j("#"+subSecNm).hasClass("batchSubSectionDisabled");
	<% String[][] batchrecclear  = null;
	if(map != null) {
		Iterator  itLab5 = map.entrySet().iterator(); 
		while(itLab5.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab5.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<%  batchrecclear  =  (String[][])pair.getValue();
	 for(int i=0;i<batchrecclear.length;i++){ 
	 	if (batchrecclear[i][0] == null) continue;
		String checknull1 = batchrecclear[i][0];%>    
	    var key =   "<%=batchrecclear[i][0]%>";
	    var componentType = "<%=batchrecclear[i][5]%>";
	<%if(checknull1 != null && checknull1 != "" ){%>
	    if(key != null &&getElementByIdOrByName(key) != null && !subsectionDisabled){
		repeatingBatchClearFields(key, componentType);
	}   
	<%}}%>
	viewed = -1;
	}
	<%}}%>
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide.style.display = ''; 
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';             
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none';
	}
    } //clearClicked       

   function clearRepeatingblk(subSecNm)    {
	JPageForm.clearRepeatingblk(subSecNm);           		
	fillTable(subSecNm,"pattern"+subSecNm,"questionbody"+subSecNm);	
   }
   function unhideBatchImg(subSecNm)
   {
    	var t =getElementByIdOrByName(subSecNm); 	
        var len=0;
        
    	//t.style.display = "block";    
    	<% if(map != null){ 
		Iterator itLab1 = map.entrySet().iterator();
		while(itLab1.hasNext()){   
		Map.Entry pair = (Map.Entry)itLab1.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<% batchrecinsert  =  (String[][])pair.getValue();   
         for(int i=0;i<batchrecinsert.length;i++){  
	    String checknull1 = batchrecinsert[i][0];  
	    if(checknull1 != null && checknull1 != ""){%> 
		var key =   "<%=batchrecinsert[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len = len +1;
		}
	<%}}%>
	 }
	<%}}%>
	for (var i = 0; i < len+1; i ++)   {
		//alert($j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#95BAEF"));
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}
	//  	alert( $j("#" + "questionbody"  +subSecNm));
	for (var i = 0; i < len+1; i ++)   {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");	
	}					       
	$j("#" + "nopattern"  +subSecNm).css("background-color","white");
    } //unhide batch image		                  
                     	
   function rollingNoteSetUserDate(elementId) {
		             <%
		             String theUserName = "";
		               try {
		                   NBSSecurityObj so = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		                   if (so != null) {
		                         theUserName = so.getTheUserProfile().getTheUser().getFirstName() + " " + so.getTheUserProfile().getTheUser().getLastName();
		                   }   
		               }
		               catch (Exception e) {
		               }           
		            %>
		          	var currentUser = "<%=theUserName%>";
		          	dwr.util.setValue(elementId+"User",currentUser);
		    
		    
		    	var todayDT = new Date();
		    	thedd = todayDT.getDate().toString();
		    	if (parseInt(thedd) < 10) thedd = "0" + thedd;
		    	themm = todayDT.getMonth()+1;//January is 0!
		    	if (parseInt(themm) < 10) themm = "0" + themm;
		    	theyyyy = todayDT.getFullYear();
		    	var theDate = themm + "/" + thedd + "/" + theyyyy;
		    	var theMinutes = todayDT.getMinutes();
		    	if (theMinutes < 10){
				theMinutes = "0" + theMinutes;
			}
		    	var theTime = " "+ todayDT.getHours() + ":" + theMinutes;
		    	dwr.util.setValue(elementId+"Date",theDate+theTime);
      }
         
      function callAlignSectionsAndSubsections(){
		  
		  window.setTimeout(alignSectionsAndSubsections, 1000); 
		  
	  }                                    
      
                    
  ]]> </xsl:text>   
               
          </script>  
        
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
     </style>
    </head>
      
     <xsl:text disable-output-escaping="yes"><![CDATA[ 
     <% 
    int subSectionIndex = 0;

    String tabId = "";
      String [] sectionNames  = {]]></xsl:text><xsl:for-each select="descendant-or-self::*/SectionName"><xsl:text disable-output-escaping="yes">"</xsl:text><xsl:value-of select="."/><xsl:text disable-output-escaping="yes">"</xsl:text><xsl:if test="not(position() = last())"><xsl:text disable-output-escaping="yes">,</xsl:text></xsl:if></xsl:for-each><xsl:text disable-output-escaping="yes"><![CDATA[};]]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[ ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 

    <body class onload="startCountdown();autocompTxtValuesForJSP();pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}');pageCreateLoad2('${PageForm.attributeMap2.selectEltIdsArray}');
    populateBatchRecords();pgPopulateMMWR('INV165','INV166');pgPopulateInfoAsOf('NBS104');
    attachMoveFocusFunctionToTabKey();
    pgOnLoadCalcRptAge('DEM115', 'INV2001', 'INV2002', 'NBS096', 'NBS104');
    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();hideSurvivingRecordBox();disableSectionsAndSubSections();showAllShowDiffOnlyCSS();callAlignSectionsAndSubsections();pgPopulateCounties();">
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3" style="display: inline-block; min-width: 1300px;">
                <html:hidden name="PageForm" property="pageClientVO.answer(DEM165)" styleId="DEM165_Hidden"/>
				<html:hidden name="PageForm" property="pageClientVO.answer(DEM165_W)" styleId="DEM165_W_Hidden"/>
				<html:hidden name="PageForm" property="pageClientVO2.answer(DEM165)" styleId="DEM165_2_Hidden"/>
				<html:hidden name="PageForm" property="pageClientVO2.answer(DEM165_W)" styleId="DEM165_W_2_Hidden"/>
				<html:hidden name="PageForm" property="pageClientVO.answer(INV505)" styleId="INV505_Hidden"/>
         		<html:hidden name="PageForm" property="pageClientVO2.answer(INV505)" styleId="INV505_2_Hidden"/>
         
            <html:form action="/PageAction.do">
                <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>                 
                    <!-- For create/edit mode only -->
                    <logic:notEqual name="BaseForm" property="actionMode" value="Preview">
                       <logic:equal name="BaseForm" property="securityMap(editInv)" value="true">
                          
                         
									<div  class="grayButtonBar" style="text-align: right;"><input
										type="submit" name="submitCrSub" id="submitCrSub"
										value="Submit" onClick="return saveForm();" />  <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Cancel" onClick="return cancelForm();" /> &nbsp;</div>
								
                        </logic:equal>
                     
					   
                         <!-- Show differences button-->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenBlack">  
	                            <input type="radio" name="fullrecordOrDifferences" value="fullrecord" onclick="showAllShowDiffOnly(true, true);alignSectionsAndSubsections();" checked title="Show Full Record"> Show Full Record  &nbsp;
	 							<input type="radio" name="fullrecordOrDifferences" value="differencesOnly" onclick="showAllShowDiffOnly(false, true);alignSectionsAndSubsections();" title="Show Differences Only"> Show Differences Only 
 							</span>  
                        </div>
                    </logic:notEqual>  
                    
                  
				    
				       <div style="text-align:left; width:100%;">            
						<ul class="horizontalList">
	                   		<li style="margin-right:5px;"><b>Go to: </b></li>                           
						<%  for(int i =0; i<sectionNames.length;i++){%>
							<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
						<%if(i!=sectionNames.length-1) {%>
							<li class="delimiter"> | </li>
						<%	}  } %> </div>
              ]]>  </xsl:text>
                        <!-- Tab container -->
      <!-- <layout:tabs width="100%" styleClass="tabsContainer"> -->
     <xsl:comment> ################### PAGE TAB ###################### --</xsl:comment>
     <!-- Setup Include for both files to merge on the Page -->
	<xsl:text disable-output-escaping="yes"><![CDATA[ 
    <div id = "parent" style="width:100%; float: left; min-width:1300px;"> 
    <table role="presentation" style="float:left;  width: 48%;display:inline-block"> ]]></xsl:text>

	<xsl:text disable-output-escaping="yes"><![CDATA[<jsp:include page="merge.jsp"/>]]></xsl:text>
	<xsl:text disable-output-escaping="yes"><![CDATA[ 
		</table> <table role="presentation" id="superseadedTable" style="float:right;width: 48%; display:inline-block"> ]]>
	</xsl:text>
	<xsl:text disable-output-escaping="yes"><![CDATA[<jsp:include page="merge2.jsp"/>]]></xsl:text>

	<xsl:text disable-output-escaping="yes"><![CDATA[ 
    	</table> </div> 
    	<logic:equal name="BaseForm" property="securityMap(editInv)" value="true">
<div  class="grayButtonBar" style="text-align: right; display:inline-block"><input
										type="submit" name="submitCrSub" id="submitCrSub"
										value="Submit" onClick="return saveForm();" />  <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Cancel" onClick="return cancelForm();" /> &nbsp;</div>
								
        </logic:equal>

      </html:form>
      </div> <!-- Container Div -->
    </body> ]]>
    </xsl:text>
</html>
		

	</xsl:template>
</xsl:stylesheet>