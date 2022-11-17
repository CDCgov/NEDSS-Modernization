<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN JSP CONTACT PAGE GENERATE ###- - -->
    <html lang="en">
    <head>
    <title>NBS: Contact Tracing</title>
    <%@ include file="/jsp/tags.jsp" %>
    <%@ include file="/jsp/resources.jsp" %> 
    <%@ page import="java.util.*" %>
    <%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, gov.cdc.nedss.util.PageConstants, gov.cdc.nedss.util.PropertyUtil" %>
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JCTContactForm.js"></SCRIPT>  
    <script language="JavaScript" src="batch.js"></script> 
        <script language="JavaScript"> 
          
        
        var JPageForm = JCTContactForm;       
       
        function cancelForm(event)
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${contactTracingForm.attributeMap.Cancel}";
                handlePageUnload(true, event);
            } else {
                return false;
            }
        }
         var closeCalled = false;
        function handlePageUnload(closePopup, e)
        {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                    if (e.clientY < 0 || closePopup == true) {
	                    // pass control to parent's call back handler
	                    var opener = getDialogArgument(); 
                        if (opener != null) {
                        
                        
                        	// refresh parent's form
                       		opener.document.forms[0].action ="/nbs/LoadContactTracing.do?method=Cancel";
                        	opener.document.forms[0].submit();
                        }
	                window.returnValue = "windowClosed";
                        window.close();
	                }
                }
        }
        
        function OpenProcessingDecisionFromContactRecord() {
                if (pgCheckForErrorsOnSubmit() == true) {
	               return false;
                }
	        var event = 'contactRecord'
	        var mprUid = '<%=request.getParameter("MprUid")%>';
	        
	        var referralBasis=getElementByIdOrByName("CON144");
				var rbasis = '';
				if(referralBasis!=null){
					rbasis= referralBasis.value;
				}
				
				
	    	if(document.getElementById("CON145") != null &&getElementByIdOrByName("CON145").value == 'FF' && ((getElementByIdOrByName("coInfectionInv") != null && getElementByIdOrByName("coInfectionInv").value == 'true')
	    	)){
				var conditionObj  =getElementByIdOrByName("CONDITION_CD");
				var condition = "";
				if(conditionObj!=null){
					condition = conditionObj.value;
				}
				var o = new Object();
				o.opener = self;
				var url = "/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&CONDITION_CD="+condition+"&context=loadProcessDecisionContactRecord&MprUid="+mprUid+"&referralBasis="+rbasis;
				//var returnMessage = window.showModalDialog(url, o, "dialogWidth: "+900+"px;dialogHeight: "+600+"px;status: no;unadorned: yes;scroll: yes;help: no;resizable: yes;");
				var dialogFeatures = "dialogWidth: "+900+"px;dialogHeight: "+600+"px;status: no;unadorned: yes;scroll: yes;help: no;resizable: yes;";

				var modWin = openWindow(url, o,dialogFeatures, null, "");
				return false;
			}
			else{
			  	saveForm();
			}
    	}
        var saveWinCalled = false;
 function saveForm() 
        {
        
        var conditionObj  =getElementByIdOrByName("CONDITION_CD");
		var condition = "";
		if(conditionObj!=null){
					condition = conditionObj.value;
		}


		var referralBasis=getElementByIdOrByName("CON144");
		var rbasis = '';
		if(referralBasis!=null){
				rbasis= referralBasis.value;
		}
			var mprUid = '<%=request.getParameter("MprUid")%>';	
          var method="${contactTracingForm.attributeMap.method}";  
          if (saveWinCalled == false) { //don't submit twice
          	 saveWinCalled = true;
         	 if(method=="AddContactSubmit"){
                         if (pgCheckForErrorsOnSubmit() == true) {
                             saveWinCalled = false;
                             return false;
                         } else {
                              document.forms[0].action ="/nbs/ContactTracing.do?method=AddContactSubmit&CONDITION_CD="+condition+"&MprUid="+mprUid+"&referralBasis="+rbasis;
                             document.forms[0].submit();
                         }
          	} else   {       
                         if (pgCheckForErrorsOnSubmit() == true) {
                             saveWinCalled = false;
                             return false;
                         } else {
                             document.forms[0].action ="/nbs/ContactTracing.do?method=editContactSubmit";	
                             document.forms[0].submit();          	   
                         }
          	} 
          } //saveWinCalled
        }
        
      function conStdCheckReferralBasisOptions()
      {
    		var sourceDispositionCd = "${contactTracingForm.attributeMap.SourceDispositionCd}";
    		var sourceInterviewStatusCd = "${contactTracingForm.attributeMap.SourceInterviewStatusCd}";
    		var sourceCurrentSexCd = "${contactTracingForm.attributeMap.SourceCurrentSexCd}";
    		var sourceConditionCd = "${contactTracingForm.attributeMap.SourceConditionCd}";
    		crStdSetupReferralBasisOptions(sourceDispositionCd, sourceInterviewStatusCd, sourceCurrentSexCd, sourceConditionCd);
      }
      function conStdGetProcessingDecisionOptions()
      {
  	   var pdCodeset = "<%=request.getAttribute("StdCrProcessingDecisionCodeset")%>";
  	   var elementId = "CON145";
  	   if ("${contactTracingForm.actionMode}" == "Edit") {
	       	 var curSelVal = $j('#' + elementId + ' :selected').val();
	       	  //if SR or RSC or FF this should be the only option so remove all others..
	       	 if (curSelVal == 'RSC' || curSelVal == 'FF'  || curSelVal == 'SR' ) {
	       	 	if (curSelVal == 'RSC')
	       	 		$j('#' + elementId + ' option[value!="RSC"]').remove();
	       	 	else if (curSelVal == 'FF')
	       	 		$j('#' + elementId + ' option[value!="FF"]').remove();
	       	 	else if (curSelVal == 'SR')
	       	 		$j('#' + elementId + ' option[value!="SR"]').remove();
	       	 	return;
	       	 }
    	   }
  	   //alert("pdCodeset = " + pdCodeset);
  	   if (pdCodeset == "null")
  	   	pdCodeset = "STD_CR_PROC_DECISION_NOINV";
           crStdSetupProcessingDecisionOptions(pdCodeset, 'CON145');
      }   	
     function getOtherPersonPopUp(contactPatientEle)
    {
          
	var urlToOpen = "/nbs/PatientSearch.do?method=searchLoad&contactPatientEle="+contactPatientEle;
	var params="left=100, top=50, width=700, height=600, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var pview =getElementByIdOrByName("pageview");
	pview.style.display = "block";
	 var o = new Object();
		    o.opener = self;
		//var modWin = window.showModalDialog(urlToOpen,o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
	     //       700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
         //   (true ? "resizable: yes;" : ""));   
            
            var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " + 
	            700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
            (true ? "resizable: yes;" : "");
            
            var modWin = openWindow(urlToOpen, o,dialogFeatures, pview, "");  
    } 
            
              
          
     <!-- =========Begin Javascript Functions for Dynamic Rules==========-->
     
  function ruleEnDisDEM1277979()
{
 var foo = [];
$j('#DEM127 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('DEM128');
 } else { 
pgDisableElement('DEM128');
 }   
}
      
  function ruleEnDisCON1417980()
{
 var foo = [];
$j('#CON141 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('OTHPAT',foo) > -1) || ($j.inArray('Other infected patient'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableParticipationElement('CON142');
 } else { 
pgDisableParticipationElement('CON142');
 }   
}
      
  function ruleRequireIfCON1417981()
{
 var foo = [];
$j('#CON141 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('OTHPAT',foo) > -1)){
pgRequireElement('CON142L');
 } else { 
pgRequireNotElement('CON142L');
 }   
}
      
  function ruleRequireIfCON1417982()
{
 var foo = [];
$j('#CON141 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length>0 && foo[0] != '') {
pgRequireElement('CON143L');
 } else { 
pgRequireNotElement('CON143L');
 }   
}
      
  function ruleRequireIfCON1437983()
{
 var foo = [];
$j('#CON143 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length>0 && foo[0] != '') {
pgRequireElement('CON144L');
 } else { 
pgRequireNotElement('CON144L');
 }   
}
      
  function ruleEnDisCON1447984()
{
 var foo = [];
$j('#CON144 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('P1',foo) > -1) || ($j.inArray('P1 - Partner'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('P3',foo) > -1) || ($j.inArray(' Sex'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS118');
pgEnableElement('NBS120');
pgEnableElement('NBS119');
 } else { 
pgDisableElement('NBS118');
pgDisableElement('NBS120');
pgDisableElement('NBS119');
 }   
}
      
  function ruleEnDisCON1447985()
{
 var foo = [];
$j('#CON144 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('P2',foo) > -1) || ($j.inArray('P2 - Partner'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('P3',foo) > -1) || ($j.inArray(' Needle-Sharing'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS121');
pgEnableElement('NBS123');
pgEnableElement('NBS122');
 } else { 
pgDisableElement('NBS121');
pgDisableElement('NBS123');
pgDisableElement('NBS122');
 }   
}
      
  function ruleEnDisCON1447986()
{
 var foo = [];
$j('#CON144 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('A1',foo) > -1) || ($j.inArray('A1 - Associate 1'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('A2',foo) > -1) || ($j.inArray(' A2 - Associate 2'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('A3',foo) > -1) || ($j.inArray(' A3 - Associate 3'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('C1',foo) > -1) || ($j.inArray(' C1- Cohort'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('S1',foo) > -1) || ($j.inArray(' S1 - Social Contact 1'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('S2',foo) > -1) || ($j.inArray(' S2 - Social Contact 2'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('S3',foo) > -1) || ($j.inArray(' S3 - Social Contact 3'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('CON103');
 } else { 
pgDisableElement('CON103');
 }   
}
      
  function ruleRequireIfCON1447987()
{
 var foo = [];
$j('#CON144 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('A1',foo) > -1) || ($j.inArray('A2',foo) > -1) || ($j.inArray('A3',foo) > -1) || ($j.inArray('C1',foo) > -1) || ($j.inArray('S1',foo) > -1) || ($j.inArray('S2',foo) > -1) || ($j.inArray('S3',foo) > -1)){
pgRequireElement('CON103L');
 } else { 
pgRequireNotElement('CON103L');
 }   
}
      
  function ruleEnDisDEM1137988()
{
 var foo = [];
$j('#DEM113 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('U',foo) > -1) || ($j.inArray('Unknown'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS213');
pgEnableElement('NBS274');
pgEnableElement('NBS272');
 } else { 
pgDisableElement('NBS213');
pgDisableElement('NBS274');
pgDisableElement('NBS272');
 }   
}
      
  function ruleEnDisDEM1557989()
{
 var foo = [];
$j('#DEM155 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('UNK',foo) > -1) || ($j.inArray('unknown'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS273');
 } else { 
pgDisableElement('NBS273');
 }   
}
      
     function pgCheckForDynamicRuleErrorsOnSubmit() {
     	var errorElts = new Array();
    	var errorLabels = new Array();
    	var retVal;
     	retVal = conStdCheckSpecialSubmitRules();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        
        return {elements : errorElts, labels : errorLabels};

      }
      
      function pgCheckForFieldsToHighlightOnEdit() {
          //only check if we are in edit mode
          var actionMode =getElementByIdOrByName("actionMode") == null ? "" :getElementByIdOrByName("actionMode").value;
          if(actionMode != 'Edit') { 
              return;
          }
          
          var strFields = "<%=request.getAttribute("field_list_to_hilight")%>";
           
          if(strFields == "null") {
          	return;
          }
          pgProcessErrorFieldsToHilight(strFields);
          
      }      
      
      function pgCheckDynamicRulesOnLoad() {
           
           	conStdGetProcessingDecisionOptions();conStdGetNamedDuringInterview('CON143',JCTContactForm);ruleEnDisDEM1277979();ruleEnDisCON1417980();ruleEnDisCON1447984();ruleEnDisCON1447985();ruleEnDisCON1447986();ruleEnDisDEM1137988();ruleEnDisDEM1557989();ruleRequireIfCON1417981();ruleRequireIfCON1417982();ruleRequireIfCON1437983();ruleRequireIfCON1447987();
          
       return;
      }

	<!-- === batch subsection add edit check functions (if any) follow ===-->
     	
      
     </script>
     
      
 
    <% 
        Map map = new HashMap();
        if(request.getAttribute("SubSecStructureMap") != null){
           map =(Map)request.getAttribute("SubSecStructureMap");
        }
    %>
    
  <script language="JavaScript"> 
        
    var answerCache = { }; //global for batch records
    var viewed = -1,count=0;   
                    
    function populateBatchRecords()
    {
       dwr.engine.beginBatch();
       var map,ans;          
       JCTContactForm.getBatchEntryMap(function(map) {
          for (var key in map) {
            count++;
            fillTable(key,"pattern"+key ,"questionbody"+key );
          } 		 
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

			JCTContactForm.setAnswer(batchentry,"<%=request.getSession()%>");
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
		JCTContactForm.getDwrCountiesForState(stateCode, function(data) {
		DWRUtil.removeAllOptions("INV505");
		DWRUtil.addOptions("INV505", data, "key", "value" );
		});
	}
	var countryCode = answer.answerMap['INV502'];
	if(countryCode != null && countryCode != "" &&getElementByIdOrByName('INV503') != null){
		countryCode = countryCode.substring(0, countryCode.indexOf("$$"));
		JCTContactForm.getfilteredStatesByCountry(countryCode, function(data) {
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
			JCTContactForm.updateAnswer(answer,function(answer) { 
			
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
	  JCTContactForm.getAllAnswer(subSecNm,function(answer) {
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
             JCTContactForm.deleteAnswer(answer);
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
    JCTContactForm.getDropDownValues(newValue, function(data) {
        dwr.util.removeAllOptions(newValue);  
        dwr.util.addOptions(newValue,data,"key","value"); 
    });
}
                  
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
	JCTContactForm.updateAnswer(answer,function(answer) {
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
	JCTContactForm.clearRepeatingblk(subSecNm);           		
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
                                         
      
                    
      
               
          </script>  
        
     <style type="text/css">
            body.popup div.popupTitle {width:100%; background:#185394; padding:3px; color:#FFF; text-align:left; font-size:110%; font-weight:bold;}	
            body.popup div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
            table.searchTable {width:98%; margin:0 auto; margin-top:1em; border-spacing:4px; margin-bottom:5px; margin-top:5px;}
     </style>
    </head>
      
      
     <% 
    int subSectionIndex = 0;

    String tabId = "";
      String [] sectionNames  = {"Patient Information","Contact Record","Disposition","Contact Record Comments"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 

    <body class="popup" onunload="handlePageUnload(false, event); return false;" onload="startCountdown();autocompTxtValuesForJSP();pgOnLoadCalcRptAge('DEM115', 'INV2001', 'INV2002', 'NBS096', 'NBS104');pageCreateLoad('${contactTracingForm.attributeMap.selectEltIdsArray}');
    populateBatchRecords();
    attachMoveFocusFunctionToTabKey();
    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addRolePresentationToTabsAndSections();">
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
        <html:hidden name="contactTracingForm" property="cTContactClientVO.answer(DEM165)" styleId="DEM165_Hidden"/>
		<html:hidden name="contactTracingForm" property="cTContactClientVO.answer(DEM165_W)"  styleId="DEM165_W_Hidden"/>
            <html:form action="/ContactTracing.do">
                <!-- Page title -->
	        <div class="popupTitle"> ${BaseForm.pageTitle} <a name=pageTop></a></div>
            <%String phcUID = (String) request.getAttribute("phcUID");%>
	        <!-- Top button bar -->
	      	<div class="popupButtonBar">
	            <input type="button" name="Submit" value="Submit" onclick="OpenProcessingDecisionFromContactRecord();"/>
	            <input type="button" name="Cancel" value="Cancel" onclick="cancelForm(event)" />
	        </div>            
            <!-- Body div -->
            <div id="bd">
                       <!-- Page Errors -->
                       <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
					   
                        <!-- Patient summary -->
                         <%@ include file="/contacttracing/ContactTracingSummary.jsp" %> 
                                             
                        <!-- Required Field Indicator -->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenRed"> * </span>
                            <span class="boldTenBlack"> Indicates a Required Field </span>  
                        </div>

                     <!-- Error Messages using Action Messages-->
				    <div id="globalFeedbackMessagesBar" class="screenOnly">
				        <logic:messagesPresent name="error_messages">
				        <div class="infoBox errors" id="errorMessages">
				            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				            <ul>
				                <html:messages id="msg" name="error_messages">
				                    <li> <bean:write name="msg" /> </li>
				                </html:messages>
				            <ul>
				        </div>
				    </logic:messagesPresent>
				    </div> 
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer">
         		
         	
         	 
         	 
		 
      		 <layout:tab key="Contact">     
      		<jsp:include page="Contact.jsp"/>
             	</layout:tab>               	
             	
        		
         	
         	 
         	 
		 
      		 <layout:tab key="Contact Record">     
      		<jsp:include page="ContactRecord.jsp"/>
             	</layout:tab>               	
             	
                
      		
      		    <logic:equal name="contactTracingForm" property="stdContact" value="true">	
        		<logic:equal name="contactTracingForm" property="actionMode" value="Create">	    	
            			<layout:tab key="Follow-up Investigation">     
		      			<jsp:include page="/contacttracing/contactrecord/FollowupInvestigation.jsp"/>
             			</layout:tab>              
      	 		</logic:equal>   
		    </logic:equal>      		
			<layout:tab key="Supplemental Info">
			       <jsp:include page="/contacttracing/contactrecord/SupplementalInfo.jsp"/> 
			 </layout:tab>    
      </layout:tabs>  
	  
			 <!-- Bottom button bar -->
	  	    <div class="popupButtonBar">
		        <input type="button" name="Submit" value="Submit" onclick="OpenProcessingDecisionFromContactRecord()"/>
		        <input type="button" name="Cancel" value="Cancel" onclick="cancelForm(event)" />
		    </div>
	    	
               </html:form>
          </div> <!-- Container Div -->
    </body>
     
</html>
		

	