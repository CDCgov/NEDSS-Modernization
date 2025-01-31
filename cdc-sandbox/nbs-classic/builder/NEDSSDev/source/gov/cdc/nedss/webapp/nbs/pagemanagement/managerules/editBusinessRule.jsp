
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ include file="../../jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<html lang="en">
<head>
<title>Edit Rule</title>
<%@ include file="../../jsp/resources.jsp"%>
<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageRulesForm.js"></SCRIPT>
</head>
<%
	Long waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
%>
<script type="text/javascript">


         function submitRule()
         {
             var script = document.forms[0].seletedFunction.value;
             var errorMsgArray = new Array();
             
             if(script =="Date Compare"){            
             var  sourceDate         = getElementByIdOrByName("sourceDC").value;            
             if(sourceDate.length==0){
                 var msg = "Source is a required field." + "\n";
                 errorMsgArray.push(msg);
             }
             var  logicDate  = getElementByIdOrByName("logicDC").value;         
             if(logicDate.length==0){
                 var msg = "Logic/Comparator is a required field." + "\n";
                 errorMsgArray.push(msg);
             }
            
             len = getElementByIdOrByName("targetDC").length
             i = 0
             count = 0;
             chosen = ""
                 var error = true;
              for (i = 0; i < len; i++) {             
                    if (getElementByIdOrByName("targetDC")[i].selected) {
                       count++;
                         chosen = getElementByIdOrByName("targetDC")[i].value ;
                         if(trim(chosen).length == 0)
                             error=  true;
                         else
                             error=  false;                      
                         if(sourceDate.length>0 && sourceDate == chosen){
                                var msg = "The same value cannot be selected for the Source and Target." + "\n";
                                errorMsgArray.push(msg);
                         }           
                     } 
              }   
           if(count >10){
	  		    	   var msg = "You can not select more than 10 target fields. " + "\n";
	   		    	   errorMsgArray.push(msg);
	    }        
              if(error){
                    var msg = "Target is a required field." + "\n";
                    errorMsgArray.push(msg);
              }
         }else if(script =="Disable" || script =="Enable" ){
             
             var  sourceED           = getElementByIdOrByName("sourceED").value;
             if(sourceED.length==0){
                 var msg = "Source is a required field." + "\n";
                 errorMsgArray.push(msg);
             }           
                        
             var  logicED            = getElementByIdOrByName("logicED").value;
             if(logicED.length==0){
                 var msg = "Logic/Comparator is a required field." + "\n";
                 errorMsgArray.push(msg);
             }           

             // TargetType Value validation
             lenTargetType = getElementByIdOrByName("targetType").length
             iTargetType = 0
             chosenTargetType = "";    
             var errorTargetType=true;       
             for (iTargetType = 0; iTargetType < lenTargetType; iTargetType++) {     
                            
                     if (getElementByIdOrByName("targetType")[iTargetType].selected) {                    	 
                     	chosenTargetType = getElementByIdOrByName("targetType")[iTargetType].value ;                    	
                         if(trim(chosenTargetType).length == 0){
                         	errorTargetType=  true;                        	
                         }
                         else
                         	errorTargetType=  false;
                     } 
              }
             if(errorTargetType){
                 var msg = "Target Type is a required field." + "\n";
                 errorMsgArray.push(msg);
             }

             
             // Source Value validation
	     var  sourceEDAnyVal = $j("#anyValIndED").is(':checked');
             if (!sourceEDAnyVal) {
             	// Source Value validation
             	lenSource = getElementByIdOrByName("sourceValueED").length
             	iSource = 0
             	chosenSource = "";    
             	var errorSource=true;       
             	iCount = 0;
             	for (iSource = 0; iSource < lenSource; iSource++) {  
                     if (getElementByIdOrByName("sourceValueED")[iSource].selected) {                        
                         chosenSource = getElementByIdOrByName("sourceValueED")[iSource].value ;
                         iCount++;
                         if(trim(chosenSource).length == 0)
                                 errorSource=  true;
                         else
                                errorSource=  false;
                     } 
              	}
             	if(errorSource){
                 	var msg = "Source Value is a required field." + "\n";
                 	errorMsgArray.push(msg);
             	}
             	if(iCount >10){
	    		var msg = "You can not select more than 10 Source Value fields. " + "\n";
	    		errorMsgArray.push(msg);
	         } 
	     } //Source value validation
             // Target Validation            
             len = getElementByIdOrByName("targetED").length
             i = 0;
             count=0;
             chosen = "";    
             var error=true;    
             for (i = 0; i < len; i++) 
             {
              if (getElementByIdOrByName("targetED")[i].selected) {
              count++;
                 chosen = getElementByIdOrByName("targetED")[i].value ;
                 if(trim(chosen).length == 0)
                     error=  true;
                 else
                     error=  false;
                             
                 if(sourceED.length>0 && sourceED == chosen){
                       var msg = "The same value cannot be selected for the Source and Target." + "\n";
                       errorMsgArray.push(msg);
                 }           
              } 
             }
     	       if(count >10){
	          		    	   var msg = "You can not select more than 10 target fields. " + "\n";
		    		    	   errorMsgArray.push(msg);
	      }
	                     
             if(error){
                  var msg = "Target is a required field." + "\n";
                  errorMsgArray.push(msg);
             } 
        } else if(script =="Hide" || script =="Unhide" ){
            var  sourceHD           = getElementByIdOrByName("sourceHD").value;
            
            if(sourceHD.length==0){
                var msg = "Source is required." + "\n";
                errorMsgArray.push(msg);
            }           
            var  theSourceHDAnyVal = $j("#anyValIndHD").is(':checked');
            var  theLogicHD = getElementByIdOrByName("logicHD").value;
            if(theLogicHD.length==0 && !theSourceHDAnyVal){
                var msg = "Logic/Comparator is a required field." + "\n";
                errorMsgArray.push(msg);
            }           

            // TargetType Value validation
            var lenTargetType = getElementByIdOrByName("targetType").length
            var iTargetType = 0
            var chosenTargetType = "";    
            var errorTargetType=true;       
            for (iTargetType = 0; iTargetType < lenTargetType; iTargetType++) {  
                    if (getElementByIdOrByName("targetTypeHD")[iTargetType].selected) {                    	 
                    	chosenTargetType = getElementByIdOrByName("targetTypeHD")[iTargetType].value ;                    	
                        if(trim(chosenTargetType).length == 0){
                        	errorTargetType=  true;                        	
                        }
                        else
                        	errorTargetType=  false;
                    } 
             }
            if(errorTargetType){
                var msg = "Target Type is a required field." + "\n";
                errorMsgArray.push(msg);
            }
                        
            // Source Value validation
            if (!theSourceHDAnyVal) {
            	var lenSource = getElementByIdOrByName("sourceValueHD").length
            	var iSource = 0
            	var chosenSource = "";    
            	var errorSource=true;   
            	var iCount = 0;	    
            	for (iSource = 0; iSource < lenSource; iSource++) {                                
                    if (getElementByIdOrByName("sourceValueHD")[iSource].selected) {
                    	iCount++;                    	 
                    	chosenSource = getElementByIdOrByName("sourceValueHD")[iSource].value ;
                        if(trim(chosenSource).length == 0)
                                errorSource=  true;
                        else
                               errorSource=  false;
                    } 
             	}
            	if(errorSource){
               	 var msg = "Source Value is a required field." + "\n";
               	 errorMsgArray.push(msg);
            	}
            	if(iCount >10){
	    		   var msg = "You can not select more than 10 Source Value fields. " + "\n";
	    		   errorMsgArray.push(msg);
	      	}
	    } //not Any Source Value checked
	    
            // Target Validation            
            var tarHDlen = getElementByIdOrByName("targetHD").length
            var i = 0
	    	var chosen = "";	
	        var tarCount = 0;	
		    var error=true;	   
	    	for (i = 0; i < tarHDlen; i++) 
		    {
	    	 if (getElementByIdOrByName("targetHD")[i].selected) {
	    		 tarCount++;
	    		chosen = getElementByIdOrByName("targetHD")[i].value ;
	    		if(trim(chosen).length == 0)
	    			error=	true;
	    		else
	    	        error=  false;
                       	  	
	    		if(sourceHD.length>0 && sourceHD == chosen){
	    		      var msg = "The same value cannot be selected for the Source and Target." + "\n";
	    		      errorMsgArray.push(msg);
	    		}           
	    	 } 
		    }
		     if(tarCount >10){
	          		var msg = "You can not select more than 10 target fields. " + "\n";
		    		errorMsgArray.push(msg);
		      }

	  		if(error){
	  		   	 var msg = "Target is a required field." + "\n";
	             errorMsgArray.push(msg);
	  		}             
         }else if(script =="Require If" ){
             
             var  sourceRQ           	= getElementByIdOrByName("sourceRQ").value;
             if(sourceRQ.length==0){
                 var msg = "Source is a required field." + "\n";
                 errorMsgArray.push(msg);
             }           
                        
             var  logicRQ            = getElementByIdOrByName("logicRQ").value;
             if(logicRQ.length==0){
                 var msg = "Logic/Comparator is a required field." + "\n";
                 errorMsgArray.push(msg);
             }           


                          
             // Source Value validation
             var  sourceRQAnyVal = $j("#anyValIndRQ").is(':checked');
             if (!sourceRQAnyVal) {
             	lenSource = getElementByIdOrByName("sourceValueRQ").length
             	iSource = 0
             	chosenSource = "";    
             	var errorSource=true;       
             	iCount = 0;
             	for (iSource = 0; iSource < lenSource; iSource++) {  
                     if (getElementByIdOrByName("sourceValueRQ")[iSource].selected) {                        
                         chosenSource = getElementByIdOrByName("sourceValueRQ")[iSource].value ;
                         iCount++;
                         if(trim(chosenSource).length == 0)
                                 errorSource=  true;
                         else
                                errorSource=  false;
                     } 
              	}
             	if(errorSource){
                 	var msg = "Source Value is a required field." + "\n";
                 	errorMsgArray.push(msg);
             	}
             	if(iCount >10){
	    		   var msg = "You can not select more than 10 Source Value fields. " + "\n";
	    		   errorMsgArray.push(msg);
	         }
	     } 
             // Target Validation            
             len = getElementByIdOrByName("targetRQ").length
             i = 0;
             count=0;
             chosen = "";    
             var error=true;    
             for (i = 0; i < len; i++) 
             {
              if (getElementByIdOrByName("targetRQ")[i].selected) {
              count++;
                 chosen = getElementByIdOrByName("targetRQ")[i].value ;
                 if(trim(chosen).length == 0)
                     error=  true;
                 else
                     error=  false;
                             
                 if(sourceRQ.length>0 && sourceRQ == chosen){
                       var msg = "The same value cannot be selected for the Source and Target." + "\n";
                       errorMsgArray.push(msg);
                 }           
              } 
             }
     	       if(count >10){
	          		    	   var msg = "You can not select more than 10 target fields. " + "\n";
		    		    	   errorMsgArray.push(msg);
	      }
	                     
             if(error){
                  var msg = "Target is a required field." + "\n";
                  errorMsgArray.push(msg);
             }     
           }           
         
           if(errorMsgArray != null && errorMsgArray.length > 0){
             displayGlobalErrorMessage(errorMsgArray);
             }
           else {
             document.forms[0].action ="/nbs/ManageRules.do?method=updateBusinessRule";
             document.forms[0].submit();         
                     return true;
            } 
         
            return false;
         }

         function cancelRule()
         {
             <% String from = request.getAttribute("from")!=null?(String)request.getAttribute("from"):null;         	    
             if(from != null){
              %>	 
            	 document.forms[0].action ="/nbs/ManageRules.do?method=viewBuinessRule&existing=true&waRuleMetadataUid=<%= request.getAttribute("waRuleMetadataUid")%>";
             <% 	 
              }else{
              %>
                 document.forms[0].action ="/nbs/ManageRules.do?method=viewRulesList&existing=true&contect=ReturnToPage&waRuleMetadataUid=<%= request.getAttribute("waRuleMetadataUid")%>";
              <%}%>   
              document.forms[0].submit();
              return true;
         }

         function pageFunctionRule()
         {
        	var script =document.forms[0].seletedFunction.value;
            //alert("pageFunctionRule-" + script);
            getElementByIdOrByName("SaveBottom").disabled=false; 
            getElementByIdOrByName("SaveTop").disabled=false;  
            getElementByIdOrByName("DateCompare").style.display = "";      
            if(script=="Date Compare"){
                getElementByIdOrByName("DateCompare").style.display = "";
                getElementByIdOrByName("Enabled").style.display = "none";
                getElementByIdOrByName("RequireSection").style.display = "none";
                getElementByIdOrByName("HideShowSection").style.display = "none";
            }else if (script=="Require If"){
                getElementByIdOrByName("DateCompare").style.display = "none";
                getElementByIdOrByName("Enabled").style.display = "none";
                getElementByIdOrByName("RequireSection").style.display = "";
                getElementByIdOrByName("HideShowSection").style.display = "none";
    	    } else if (script=="Hide" || script=="Unhide") {
    	    	//alert("HideShowSection");
    	    	getElementByIdOrByName("HideShowSection").style.display = "";
    	        getElementByIdOrByName("RequireSection").style.display = "none";
    	        getElementByIdOrByName("DateCompare").style.display = "none";
                getElementByIdOrByName("Enabled").style.display = "none";                
            }else{
                getElementByIdOrByName("Enabled").style.display = "";
                getElementByIdOrByName("DateCompare").style.display = "none";
                getElementByIdOrByName("RequireSection").style.display = "none";
                getElementByIdOrByName("HideShowSection").style.display = "none";
                sourceSelected = $j("#sourceED").val();
                JManageRulesForm.getDWSourceIsRepeatBlock(sourceSelected, function(data){                
	                 if(data){                	  
	               	 	$j("#targetType").parent().parent().find(":input").attr("disabled", true);			 
	               	 $j("#targetType").parent().parent().find("img").attr("disabled", true);
	               	$j("#targetType").parent().parent().find("img").attr("tabIndex", "-1");
               	 	
	               	 	$j("#tagetTypeLabel").attr("disabled",true);
	                 }
						
		});                
            }
            return true;
         }

         
         function getDWRSourceValues(source, elementId)
         {
        	   	getElementByIdOrByName("selectedSourceValueED").innerHTML=""; 
               	getElementByIdOrByName("selectedTargetED").innerHTML=""; 
		  		if(source.value.length == 0){
		  			getElementByIdOrByName("sourceValueED").value="";
		  		 	getElementByIdOrByName("targetED").value="";            
		  		 	return false;
               	}
             
             var source1 = source.value;
             if(source1 == null) {
                 source1= source;
             }
             
             JManageRulesForm.getDwrSourceValues(source1, function(data) {
                 DWRUtil.removeAllOptions(elementId);                 
                 DWRUtil.addOptions(elementId, data, "key", "value" );
                 
             });    
             var targetType = $j("#targetType");             
             getDWRTargetTypeValues(targetType,"targetED"); 
             
             //New source - clear Any Checked if checked..
	     if($j('#anyValIndED').is(':checked')) {
	          $j('#anyValIndRED').removeAttr('checked');
	          handleAnySourceVal('anyValIndED','logicED','sourceValueED','selectedSourceValueED');
             }              
         }


         function getDWRHideShowSourceValues(source, elementId)
         {
         	
     	   	getElementByIdOrByName("selectedSourceValueHD").innerHTML=""; 
           	getElementByIdOrByName("selectedTargetHD").innerHTML=""; 
	  	if(source.value.length == 0){
	  			getElementByIdOrByName("sourceValueHD").value="";
	  		 	getElementByIdOrByName("targetHD").value="";            
	  		 	return false;
           	}
             
             var sourceValue = source.value;
             if(sourceValue == null) {
                 sourceValue = source;
             }
             
             JManageRulesForm.getDwrHideShowSourceValues(sourceValue, function(data) {
                 DWRUtil.removeAllOptions(elementId);                 
                 DWRUtil.addOptions(elementId, data, "key", "value" );
                 
             }); 

             var theTargetType = $j("#targetTypeHD");
             var theTargetTypeVal = $j('#targetTypeHD :selected').val();
             if (theTargetTypeVal == null || theTargetTypeVal == "") {
	           $j('#targetTypeHD').val("QUESTION"); //default to question
	            AutocompleteSynch('targetTypeHD_textbox', 'targetTypeHD');
	     }
             theTargetTypeVal = $j('#targetTypeHD :selected').val();
             getDWRTargetHideShowValues(theTargetType,"targetHD"); 
 
 	     //uncheck Any Source value if checked
             if($j('#anyValIndHD').is(':checked')) {
              	$j('#anyValIndHD').removeAttr('checked');
              	handleAnySourceVal('anyValIndHD','logicHD','sourceValueHD','selectedSourceValueHD');
             }             
         }

         function getDWRRequiredSourceValues(source, elementId)
         {
     	   	$j("#selectedSourceValueRQ").text("");
           	$j("#selectedTargetRQ").text("");
	  		if(source.value.length == 0){
	  			$j("#sourceValueRQ").val("");
	  		 	//$j("#targetRQ").val("");            
	  		 	return false;
           	}
             
             var source1 = source.value;
             if(source1 == null) {
                 source1= source;
             }
             
             JManageRulesForm.getDwrRequiredSourceValues(source1, function(data) {
                 DWRUtil.removeAllOptions(elementId);                 
                 DWRUtil.addOptions(elementId, data, "key", "value" );
                 
             }); 
            
             getDWRRequiredTargetTypeValues("targetRQ"); 
             
             //New source - clear Any Checked if checked..
	     if($j('#anyValIndRQ').is(':checked')) {
	          $j('#anyValIndRQ').removeAttr('checked');
	          handleAnySourceVal('anyValIndRQ','logicRQ','sourceValueRQ','selectedSourceValueRQ');
             }
         }

         function getDWRTargetTypeValues(targetTypeIn,  elementId)
         {
            sourceSelected = $j("#sourceED").val();
            if(sourceSelected != null && sourceSelected.length >0){             
	             JManageRulesForm.getDWSourceIsRepeatBlock(sourceSelected, function(data){                
	                 if(data){                	  
	               	 	$j("#targetType").parent().parent().find(":input").attr("disabled", true);			 
	               	    $j("#targetType").parent().parent().find("img").attr("disabled", true);
	                  	$j("#targetType").parent().parent().find("img").attr("tabInidex", "-1");
               	 	
	               	 	$j("#tagetTypeLabel").attr("disabled",true); 
	               	 	
			 			$j('#targetType').val("QUESTION");
			 			getElementByIdOrByName("targetType_textbox").value="Question";
			 			targetType =  getElementByIdOrByName("targetType_textbox").value;
						JManageRulesForm.getDWRTargetTypeValues(<%=waTemplateUid %>,targetType,sourceSelected,'Edit','<%= request.getAttribute("waRuleMetadataUid")%>' , function(data) {
							DWRUtil.removeAllOptions(elementId);                 
							DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
	
			     		});
			 	
	                 }else{
	                   	$j("#targetType").parent().parent().find(":input").attr("disabled", false);			  
	                   	$j("#targetType").parent().parent().find("img").attr("disabled", false);
	                   	if($j("#targetType").parent().parent().find(calendars)[0]!=undefined && $j("#targetType").parent().parent().find(calendars)[0]!=null)
                			
                    		$j("#targetType").parent().parent().find(calendars)[0].attr("tabIndex", "0");
		            	
	               	    $j("#tagetTypeLabel").attr("disabled",false);
	               	    targetType =  getElementByIdOrByName("targetType_textbox").value;
			    		JManageRulesForm.getDWRTargetTypeValues(<%=waTemplateUid %>,targetType,sourceSelected,'Edit','<%= request.getAttribute("waRuleMetadataUid")%>', function(data) {
			    			DWRUtil.removeAllOptions(elementId);                 
							DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
	                 	})
	                 }
	             });
             
          	}
                 
         }

         function getDWRTargetHideShowValues(targetTypeIn,  elementId)
         {
            var theSourceSelected = $j("#sourceHD").val();
            if(theSourceSelected != null && theSourceSelected.length >0){             
	             JManageRulesForm.getDWSourceIsRepeatBlock(theSourceSelected, function(data){ 
	                 if(data){                	  
	               	 	$j("#targetTypeHD").parent().parent().find(":input").attr("disabled", true);			 
	               	 $j("#targetTypeHD").parent().parent().find("img").attr("disabled", true);
	               	$j("#targetTypeHD").parent().parent().find("img").attr("tabIndex", "-1");
               	 	
	               	 	$j("#tagetTypeHDLabel").attr("disabled",true); 
	               	 	
			 	$j('#targetTypeHD').val("QUESTION");
			 	getElementByIdOrByName("targetTypeHD_textbox").value="Question";
			 	var theTargetType =  getElementByIdOrByName("targetTypeHD_textbox").value;
				JManageRulesForm.getDWRHideShowTargetTypeValues(<%=waTemplateUid %>,theTargetType,theSourceSelected,'Edit','<%= request.getAttribute("waRuleMetadataUid")%>', function(data) {
					DWRUtil.removeAllOptions(elementId);                 
					DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
			     	});			 	
	                 }else{
	                    	$j("#targetTypeHD").parent().parent().find(":input").attr("disabled", false);			  
	                    	$j("#targetTypeHD").parent().parent().find("img").attr("disabled", false);
	                    	var calendars = $j( "img[src*='calendar.gif']");
	                		if($j("#targetTypeHD").parent().parent().find(calendars)[0]!=undefined && $j("#targetTypeHD").parent().parent().find(calendars)[0]!=null)
	                			
	                    		$j("#targetTypeHD").parent().parent().find(calendars)[0].attr("tabIndex", "0");
			            	
	               	    	$j("#tagetTypeHDLabel").attr("disabled",false);
	               	    	var theTargetType =  getElementByIdOrByName("targetTypeHD_textbox").value;
			    	JManageRulesForm.getDWRHideShowTargetTypeValues(<%=waTemplateUid %>,theTargetType,theSourceSelected,'Edit','<%= request.getAttribute("waRuleMetadataUid")%>', function(data) {
			    		DWRUtil.removeAllOptions(elementId);                 
					DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
	                 	})
	                 }
	             });             
          	}    
         }  
         
        function getDWRRequiredTargetTypeValues(elementId)
         {
            sourceSelected = $j("#sourceRQ").val();
            if(sourceSelected != null && sourceSelected.length >0){             
	    	JManageRulesForm.getDWRRequiredTargetTypeValues(<%=waTemplateUid %>,"Question",sourceSelected,'Create','', function(data) {
			DWRUtil.removeAllOptions(elementId);                 
			DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
	    	})
	    }	   
         }         
         
         
         function getTargetOrderByAtoZ(elementId)
         { 
        	var from;
        	if(elementId == "targetDC"){
                 	from ="DateCompare";
                 	getElementByIdOrByName("selectedTargetDC").innerHTML="<b> Selected Values:</b>";
                } else if (elementId == "targetRQ") {
                	from = "RequireIf";
                	$j("#selectedTargetRQ").html("<b> Selected Values:</b>");
                } else if (elementId == "targetHD") {
                	from = "HideUnhide";
                	$j("#selectedTargetHD").html("<b> Selected Values:</b>");                
                } else {
                    from ="EnabledDisabled";
                    getElementByIdOrByName("selectedTargetED").innerHTML="<b> Selected Values:</b>";
                }
             JManageRulesForm.getTargetOrderByAtoZ(getElementByIdOrByName("hiddenFlag").value,from,function(data) {  
                 DWRUtil.removeAllOptions(elementId);
                 DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
             });    
             if(getElementByIdOrByName("hiddenFlag").value =="true"){
                 getElementByIdOrByName("hiddenFlag").value = "false";
               }
               else{
                getElementByIdOrByName("hiddenFlag").value = "true";
               }    
           
         }
         function getTargetOrderByPage(elementId)
         {
        	 var from;
        	 if(elementId == "targetDC"){
                 	from ="DateCompare";
                 	getElementByIdOrByName("selectedTargetDC").innerHTML="<b> Selected Values:</b>";
                 }else if(elementId == "targetRQ"){
                 	from ="RequireIf";
                 	$j("selectedTargetRQ").html("<b> Selected Values:</b>");
                } else if (elementId == "targetHD") {
                	from = "HideUnhide";
                	$j("#selectedTargetHD").html("<b> Selected Values:</b>");                 	
                }else{
                    from ="EnabledDisabled";
                    getElementByIdOrByName("selectedTargetED").innerHTML="<b> Selected Values:</b>";
                }            
             JManageRulesForm.getTargetOrderByPage(from,function(data) {         
                 DWRUtil.removeAllOptions(elementId);                 
                 DWRUtil.addOptions(elementId, data, "questionIdentifier", "questionLabelIdentifier" );
             });    
         }  
         
         function trim(str){
             while (str.charAt(0) == " "){
               // remove leading spaces
               str = str.substring(1);
             }
             while (str.charAt(str.length - 1) == " "){
               // remove trailing spaces
               str = str.substring(0,str.length - 1);
             } return str;
           }
           
         function handleAnySourceVal(checkboxId, logicId, sourceValId, selectedSourceValId)
         {
         	
         	if($j('#'+checkboxId).is(':checked')) {
         		
         		//alert("this Is checked");
         		//disable Logic/Comparator
         		$j("#" + logicId).parent().parent().find(":input").val("=");
         		$j("#" + logicId).parent().parent().find(":input").attr("disabled", true);
         		$j("#" + logicId).parent().parent().find("img").attr("disabled", true);
         		$j("#" + logicId).parent().parent().find("img").attr("tabIndex", "-1");
    			
			$j("#" + logicId+"L").attr('disabled', 'disabled');
			//disable Source Value(s) multiselect
			var selectBox = getElementByIdOrByName(sourceValId);
			getElementByIdOrByName(sourceValId).value="";
			//$j("#" + sourceValId).val("");
			$j("#" + selectedSourceValId).text("");
			$j("#" + sourceValId).parent().parent().find(":input").val("");
			$j("#" + sourceValId).parent().parent().find(":input").attr("disabled", true);
			$j("#" + sourceValId+"L").attr('disabled', 'disabled');
			$j("#" + sourceValId + "Comment").html("(Unselect <b>Any Source Value</b> checkbox to choose specific values)");
			JManageRulesForm.dwrResetSelectedSourceValues(function(data) {
				//alert("dwrReset " + data);
             		}); 
         	} else {
         		//alert("Is not checked");
         		//enable Logic/Comparator
         		$j("#" + logicId).parent().parent().find(":input").removeAttr('disabled');
         		$j("#" + logicId).parent().parent().find(":input").val("");
         		$j("#" +logicId+"L").removeAttr('disabled');
         		$j("#" + logicId).parent().parent().find("img").removeAttr('disabled');
         		//enable Source Value(s) multiselect
   			$j("#" + sourceValId).parent().parent().find(":input").removeAttr('disabled');
   			$j("#" + sourceValId+"L").removeAttr('disabled');         		
         		$j("#" + sourceValId + "Comment").html("(Use Crtl to select more than one value)");
         	}
         }           
           
         function checkIfAnySourceValueChecked()
         {
             var  sourceRQAnyVal = $j("#anyValIndRQ").is(':checked');
             if (sourceRQAnyVal)
             	handleAnySourceVal('anyValIndRQ','logicRQ','sourceValueRQ','selectedSourceValueRQ');
             	
             var  sourceEDAnyVal = $j("#anyValIndED").is(':checked');
             if (sourceEDAnyVal)
	        handleAnySourceVal('anyValIndED','logicED','sourceValueED','selectedSourceValueED');
		
             var  sourceHDAnyVal = $j("#anyValIndHD").is(':checked');
             if (sourceHDAnyVal)		
		handleAnySourceVal('anyValIndHD','logicHD','sourceValueHD','selectedSourceValueHD');
	     return;
         }
    </script>

<body onLoad="pageFunctionRule();autocompTxtValuesForJSP();checkIfAnySourceValueChecked();">
<div id="Edit Business Rule"></div>

<!-- Container Div: To hold top nav bar, button bar, body and footer -->
<div id="doc3">

<html:form action="/ManageRules.do">
<input type="hidden" name="hiddenFlag" value="true" id="hiddenFlag"/>
    <div id="bd" style="text-align: center;"><!-- Top Nav Bar and top button bar -->
    <%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>

    <!-- Body contents -->
    <div style="">
    <div style="text-align: right;"><i> <span class="boldRed">*</span> Indicates a Required Field</i></div>

    <div class="grayButtonBar" style="text-align: right;">
        <input type="button" size="250"  id="SaveTop" name="Submit" value="Submit" onclick="submitRule();" />  
        <input type="button" name="Cancel" value="Cancel" onclick="cancelRule();" />
     </div>
	<div id="globalFeedbackMessagesBar" class="screenOnly"> </div>
	<!-- Feedback bar -->	        	               
         <div>
     	    <logic:notEmpty name="manageRulesForm" property="errorList">
     	        <div class="infoBox errors" id="errorMessages">
     	            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
     	            <ul>
     	                <logic:iterate id="errors" name="manageRulesForm" property="errorList">
     	                         <li>${errors}</li>                    
     	                </logic:iterate>
     	            </ul>
     	        </div>    
		    </logic:notEmpty>
	     </div>
    <div>
        <nedss:container id="sect_question" name="Edit Rule"
        classType="sect" displayImg="false" includeBackToTopLink="no">
        <nedss:container id="question" name="Rule Description" classType="subSect" includeBackToTopLink="no">
           <tr>
            <td class="fieldName" title="The system-generated ID for this business rule.">ID:</td>
            <td>
            <bean:write name="manageRulesForm" property="ruleId"  />
            
            </td>
        </tr>
            <tr>
                <td class="fieldName" title="The function needed to support this business rule.">
                <span class="boldRed">*</span> Function:</td>
                <td>
                    <bean:write name="manageRulesForm" property="seletedFunction"  />  
                    <html:hidden styleId="seletedFunction" property="seletedFunction"/>               
                </td>
            </tr>
            <tr>
                <td class="fieldName" title="A description of the trigger and logic associated with this business rule (a textual representation of the business rule).">Description:</td>
                <td><html:textarea title="Description" property="ruleDescription" 
                    rows="4" cols="50" styleId="cCodeFld" onkeydown="checkMaxLength(this)" 
                        onkeyup="checkMaxLength(this)"/></td>
            </tr>

        </nedss:container>
       
        <nedss:container id="DateCompare" name="Rule Details" classType="subSect" includeBackToTopLink="no" isHidden="<%= NEDSSConstants.TRUE %>">
 
                <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule)."><span class="boldRed">*</span> Source:</td>
                <td>
                
            <html:select title="Source" property="seletedSourceDateComp" styleId="sourceDC">
                <html:optionsCollection property="sourceDateCompare" value="questionIdentifier" label="questionLabelIdentifier" />
            </html:select>
                
                </td>
            </tr>
            <tr>
                <td class="fieldName" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">
                <span class="boldRed">*</span> Logic/Comparator:</td>
                <td>
                    <html:select title="Logic/Comparator" property="seletedLogicDateComp" styleId="logicDC">
                                <html:optionsCollection property="logicDateCompare" value="key" label="value" />
                </html:select>
                </td>
            </tr>
            <tr>
                <td class="fieldName" title="The value(s) that should be removed from the target question if the 'trigger condition' is met." ><span class="boldRed">*</span> Target(s):</td>
                <td>
                <div class="multiSelectBlock">
                        Sort Order: <a href="#" onClick="javascript:getTargetOrderByAtoZ('targetDC')"> A-Z </a> &nbsp;|&nbsp;
                        <a href="#"  onClick="javascript:getTargetOrderByPage('targetDC')"> Page Order </a> <br />
                        <br/><i> (Use Crtl to select more than one value) </i> <br/><br/>         
                <html:select title="Target(s)" property="seletedTargetDateComp" styleId="targetDC" size="4" multiple="true" 
                             onchange="displaySelectedSourceVal(this, 'selectedTargetDC',' | ')">
                         <html:optionsCollection property="targetDateCompare" value="questionIdentifier" label="questionLabelIdentifier" />
                    </html:select>
                
                <br/>
                <div id="selectedTargetDC" style="margin:0.25em;">
                              <b> Selected Values:</b> <bean:write name="manageRulesForm" property="selectedTargetDC"  />
                </div>
                </div>
                </td>
            </tr>
        </nedss:container>
        
       
        <nedss:container id="Enabled" name="Rule Details" classType="subSect" 
            includeBackToTopLink="no" isHidden="<%= NEDSSConstants.TRUE %>">
            <tr>
                <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule).">
                <span class="boldRed">*</span> Source:</td>
                <td>
                    <html:select title="Source" property="seletedSourceDisOrEnabled" styleId="sourceED" 
                                    onchange="getDWRSourceValues(this, 'sourceValueED');">
                                <html:optionsCollection property="sourceDisOrEnabled" value="questionIdentifier" label="questionLabelIdentifier" />
                </html:select>
                </td>
            </tr>
            <tr>
            <td class="fieldName" title="Select the checkbox to indicate that any selected value will trigger the rule.">
            Any Source Value: </td><td><html:checkbox title="Any Source Value" name="manageRulesForm" property="anySourceValueIndDisOrEnabled" value="1" styleId="anyValIndED" onclick="handleAnySourceVal('anyValIndED','logicED','sourceValueED','selectedSourceValueED')" /></td>
            </tr>            
            <tr>
               <td class="fieldName"> 
                <span class="boldRed">*</span>
                <span class=" InputFieldLabel" id="logicEDL" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">
                Logic/Comparator:</span></td>
                <td>
                <html:select title="Logic/Comparator" property="seletedLogDisOrEnabled"
                            styleId="logicED">
                            <html:optionsCollection property="logicDisOrEnabled" value="key"
                                label="value" />
                </html:select>
                </td>
            </tr>
            <tr>
                <td class="fieldName">
                <span class="boldRed">*</span>
                <span class=" InputFieldLabel" id="sourceValueEDL" title="The value(s) that meet the 'trigger condition'." >
		Source Value(s):</span></td>
                <td>
                     <div class="multiSelectBlock">
                          <i> <span class="InputFieldLabel" id="sourceValueEDComment">(Use Crtl to select more than one value)</span> </i> <br/><br/>
                            <html:select title="Source Value(s)" property="seletedSourceValDisOrEnabled"
                                styleId="sourceValueED" size="4" multiple="true" 
                                onchange="displaySelectedSourceVal(this, 'selectedSourceValueED', ' | ')" >
                                <html:optionsCollection property="sourceValDisOrEnabled"
                                    value="key" label="value" />
                            </html:select>
                      <!-- Selected values -->
                            <div id="selectedSourceValueED" style="margin:0.25em;">
                               <b> Selected Values:</b> <bean:write name="manageRulesForm" property="selectedSourceValueED"  />
                            </div>
                   </div>                  
                </td>
            </tr>
            <tr>
                <td class="fieldName" title="The type of target (e.g., subsection, question, etc.)."><span class="boldRed">*</span> Target Type:</td>
                <td>
                	<html:select title="Target Type" property="selectedTargetType" styleId="targetType" onchange="getDWRTargetTypeValues(this, 'targetED');">
                    	<html:optionsCollection property="targetType" value="key" label="value" />
                	</html:select>                     
                </td>
            </tr>            
            <tr>
                <td class="fieldName" title="The fields to be enabled or disabled if the 'trigger condition' is met."><span class="boldRed">*</span> Target(s):</td>
                <td>
                <div class="multiSelectBlock">
                        Sort Order: <a href="#" onClick="javascript:getTargetOrderByAtoZ('targetED')"> A-Z </a> &nbsp;|&nbsp;
                        <a href="#"  onClick="javascript:getTargetOrderByPage('targetED')"> Page Order </a> <br />
                        <br/><i> (Use Crtl to select more than one value) </i> <br/><br/>
                        <html:select title="Target(s)" property="seletedTarDisOrEnabled" styleId="targetED"  size="4" multiple="true" 
                              onchange="displaySelectedSourceVal(this, 'selectedTargetED',' | ')">
                            <html:optionsCollection property="tarDisOrEnabled" value="questionIdentifier" label="questionLabelIdentifier" />
                        </html:select> 
                        <br />
                        <div id="selectedTargetED" style="margin:0.25em;">
                              <b> Selected Values:</b> <bean:write name="manageRulesForm" property="selectedTargetED"  />
                        </div>
                    </div>        
                </td>
            </tr>
        </nedss:container>
   
        <nedss:container id="RequireSection" name="Rule Details" classType="subSect" 
            includeBackToTopLink="no" isHidden="<%= NEDSSConstants.TRUE %>">
            <tr>
                <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule)."><span class="boldRed">*</span> Source:</td>
                <td>
                <html:select title="Source" property="selectedSourceRequired" styleId="sourceRQ" 
                        onchange="getDWRRequiredSourceValues(this, 'sourceValueRQ');">
                    <html:optionsCollection property="sourceRequired" value="questionIdentifier" label="questionLabelIdentifier" />
                </html:select></td>
            </tr>
            <tr>
            <td class="fieldName" title="Select the checkbox to indicate that any value selected will trigger the target(s) as required.">
            Any Source Value: </td><td><html:checkbox title="Any Source Value" name="manageRulesForm" property="anySourceValueInd" value="1" styleId="anyValIndRQ" onclick="handleAnySourceVal('anyValIndRQ','logicRQ','sourceValueRQ','selectedSourceValueRQ')" /></td>
            </tr>            
            <tr>
                <td class="fieldName"> 
                <span class="boldRed">*</span>
                <span class=" InputFieldLabel" id="logicRQL" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">
                Logic/Comparator:</span></td>
                <td><html:select title="Logic/Comparator" property="selectedLogicRequired"
                    styleId="logicRQ">
                    <html:optionsCollection property="logicRequired" value="key"
                        label="value" />
                </html:select></td>
            </tr>
            <tr>
                <td class="fieldName">
                <span class="boldRed">*</span>
                <span class=" InputFieldLabel" id="sourceValueRQL" title="The value(s) that meet the 'trigger condition'." >
		Source Value(s):</span></td>
                <td>
                    <div class="multiSelectBlock">
                          <i> <span class="InputFieldLabel" id="sourceValueRQComment">(Use Crtl to select more than one value)</span> </i> <br/><br/>
                            <html:select title="Source Value(s)" property="selectedSourceValRequired"
                                styleId="sourceValueRQ" size="4" multiple="true" 
                                onchange="displaySelectedSourceVal(this, 'selectedSourceValueRQ',' | ')" >
                                <html:optionsCollection property="sourceValRequired"
                                    value="key" label="value" />
                            </html:select>
                      <!-- Selected values -->
                            <div id="selectedSourceValueRQ" style="margin:0.25em;">
                               <b> Selected Values:</b> <bean:write name="manageRulesForm" property="selectedSourceValueRQ" />
                            </div>
                   </div>                  
                </td>
            </tr>
            <tr>
                <td class="fieldName" title="The fields that need to be required if the 'trigger condition' is met." ><span class="boldRed">*</span> Target(s):</td>
                <td>
                    <div class="multiSelectBlock">
                        Sort Order: <a href="#" onClick="javascript:getTargetOrderByAtoZ('targetRQ')"> A-Z </a> &nbsp;|&nbsp;
                        <a href="#"  onClick="javascript:getTargetOrderByPage('targetRQ')"> Page Order </a> <br />
                        <br/><i> (Use Crtl to select more than one value) </i> <br/><br/>
                        <html:select title="Target(s)" property="selectedTargetRequired" styleId="targetRQ"  size="4" multiple="true" 
                               onchange="displaySelectedSourceVal(this, 'selectedTargetRQ',' | ')">
                            <html:optionsCollection property="targetRequired" value="questionIdentifier" label="questionLabelIdentifier" />
                        </html:select> 
                        <br />
                        <div id="selectedTargetRQ" style="margin:0.25em;">
                              <b> Selected Values:</b> <bean:write name="manageRulesForm" property="selectedTargetRQ" />
                        </div>
                    </div>            
                </td>
            </tr>            
            
        </nedss:container>  
   
           <nedss:container id="HideShowSection" name="ShowHide Rule Details" classType="subSect" 
               includeBackToTopLink="no" isHidden="<%= NEDSSConstants.TRUE %>">
               <tr>
                   <td class="fieldName" title="The source question associated with this business rule (i.e., the field that 'triggers' the business rule)."><span class="boldRed">*</span> Source:</td>
                   <td>
                   <html:select title="Source" property="selectedSourceHideOrUnhide" styleId="sourceHD" 
                           onchange="getDWRHideShowSourceValues(this, 'sourceValueHD');">
                       <html:optionsCollection property="sourceHideOrUnhide" value="questionIdentifier" label="questionLabelIdentifier" />
                   </html:select></td>
               </tr>
               <tr>
               <td class="fieldName" title="Select the checkbox to indicate that any value selected will trigger the rule.">
               Any Source Value: </td><td><html:checkbox title="Any Source Value" name="manageRulesForm" property="anySourceValueIndHideOrUnhide" value="1" styleId="anyValIndHD" onclick="handleAnySourceVal('anyValIndHD','logicHD','sourceValueHD','selectedSourceValueHD')" /></td>
               </tr>       
               
               <tr>
                  <td class="fieldName"> 
                   <span class="boldRed">*</span>
                   <span class=" InputFieldLabel" id="logicHDL" title="The logic that should be applied to the source question to determine if the 'trigger condition' has been met.">
                   Logic/Comparator:</span></td>
                   <td><html:select title="Logic/Comparator" property="selectedLogicHideOrUnhide"
                       styleId="logicHD">
                       <html:optionsCollection property="logicHideOrUnhide" value="key"
                           label="value" />
                   </html:select></td>
               </tr>
               <tr>
                   <td class="fieldName">
                   <span class="boldRed">*</span>
                   <span class=" InputFieldLabel" id="sourceValueHDL" title="The value(s) that meet the 'trigger condition'." >
   		Source Value(s):</span></td>
                   <td>
                       <div class="multiSelectBlock">
   			<i> <span class=" InputFieldLabel" id="sourceValueHDComment">(Use Crtl to select more than one value)</span> </i> <br/><br/>
                               <html:select title="Source Value(s)" property="selectedSourceValHideOrUnhide"
                                   styleId="sourceValueHD" size="4" multiple="true" 
                                   onchange="displaySelectedSourceVal(this, 'selectedSourceValueHD',' | ')" >
                                   <html:optionsCollection property="sourceValHideOrUnhide"
                                       value="key" label="value" />
                               </html:select>
                         <!-- Selected values -->
                               <div id="selectedSourceValueHD" style="margin:0.25em;">
                                  <b> Selected Values:</b> <bean:write name="manageRulesForm" property="selectedSourceValueHD"  />
                               </div>
                      </div>                  
                   </td>
               </tr>
                           <tr>
                   <td class="fieldName" id="targetTypeHDLabel" title="The type of target (e.g., subsection, question, etc.)."><span class="boldRed">*</span> Target Type:</td>
                   <td>
                   	<html:select title="Target Type" property="selectedTargetTypeHD" styleId="targetTypeHD" onchange="getDWRTargetHideShowValues(this, 'targetHD');">
                       	<html:optionsCollection property="targetType" value="key" label="value" />
                   	</html:select>                     
                   </td>
               </tr>
               <tr>
                   <td class="fieldName" title="The value(s) that should be removed from the target question if the 'trigger condition' is met." ><span class="boldRed">*</span> Target(s):</td>
                   <td>
                       <div class="multiSelectBlock">
                           Sort Order: <a href="#" onClick="javascript:getTargetOrderByAtoZ('targetHD')"> A-Z </a> &nbsp;|&nbsp;
                           <a href="#"  onClick="javascript:getTargetOrderByPage('targetHD')"> Page Order </a> <br />
                           <br/><i> (Use Crtl to select more than one value) </i> <br/><br/>
                           <html:select title="Page Order" property="selectedTargetHideOrUnhide" styleId="targetHD"  size="4" multiple="true" 
                                  onchange="displaySelectedSourceVal(this, 'selectedTargetHD',' | ')">
                               <html:optionsCollection property="targetHideOrUnhide" value="questionIdentifier" label="questionLabelIdentifier" />
                           </html:select> 
                           <br />
                           <div id="selectedTargetHD" style="margin:0.25em;">
                                 <b> Selected Values:</b><bean:write name="manageRulesForm" property="selectedTargetHD"  />
                           </div>
                       </div>            
                   </td>
               </tr>
        </nedss:container>
   
    </nedss:container>
    
    </div>

    <div class="grayButtonBar" style="text-align: right;">
        <input type="button" size="250" id="SaveBottom" name="Submit" value="Submit"  onclick="submitRule();" />  
        <input type="button" name="Cancel" value="Cancel" onclick="cancelRule();" />
    </div>
  </div>
</div>
</html:form>

</div>
</body>
</html>