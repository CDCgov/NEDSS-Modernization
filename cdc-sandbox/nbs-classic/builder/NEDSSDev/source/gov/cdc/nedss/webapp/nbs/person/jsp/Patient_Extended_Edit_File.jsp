    <?xml version="1.0" encoding="UTF-8"?>
	<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
	<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
	<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page isELIgnored ="false" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.Timestamp" %>
	<%@ page import="gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil" %>	
	<%@ page import="gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil" %>		
	<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>	
	<%@ page import="gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm" %>
	<%@ page import="gov.cdc.nedss.entity.person.vo.PatientSrchResultVO" %>
	<%@ page import="javax.servlet.http.HttpServletRequest" %>
	<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO" %>
	<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT" %>
	<%@ page import="gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT" %>
	<%@ page import="gov.cdc.nedss.locator.dt.PostalLocatorDT" %>
	<%@ page import="gov.cdc.nedss.locator.dt.TeleLocatorDT" %>
	<%@ page import="gov.cdc.nedss.entity.entityid.dt.EntityIdDT" %>
	<%@ page import="gov.cdc.nedss.entity.person.dt.PersonRaceDT" %>
	<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %>
	<%@ page import="gov.cdc.nedss.util.StringUtils" %>
	<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%> 
	
	   <% CompleteDemographicForm fform = (CompleteDemographicForm)request.getSession().getAttribute("DSPersonForm");
	   PatientSrchResultVO pVo = fform.getPatSrcResVO();
	   PersonVO vo = fform.getPerson();
	   SearchResultPersonUtil psutil = new SearchResultPersonUtil();
	   %>
		  <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JDSPersonForm.js"></SCRIPT>  
     <SCRIPT Language="JavaScript" Type="text/javascript" SRC="PersonSpecific.js"></SCRIPT> 
    
	     <script language="JavaScript"> 
	      var gBatchEntryUpdateSeq = "";	     
	       var gBatchEntryAddrUpdateSeq = "";
	        var gBatchEntryPhUpdateSeq = "";
	         var gBatchEntryIdenUpdateSeq = "";
	          var gBatchEntryRaceUpdateSeq = "";
          var gBatchEntryFieldsDisabled = false;
           var gBatchEntryAddrFieldsDisabled = false;
           var gBatchEntryPhFieldsDisabled = false;
            var gBatchEntryIdenFieldsDisabled = false;
              var gBatchEntryRaceFieldsDisabled = false;
        
        function handlebatchEntry(){
                dwr.engine.beginBatch(); 
	            rewriteBatchIdHeader('addrTable');
	             dwr.engine.endBatch();
	            dwr.engine.beginBatch(); 
	            rewriteBatchIdHeader('phoneTable');
	             dwr.engine.endBatch();
	             dwr.engine.beginBatch();
	           rewriteBatchIdHeader('idenTable');
	            dwr.engine.endBatch();
	            dwr.engine.beginBatch();
	           rewriteBatchIdHeader('nameTable');
	            dwr.engine.endBatch();
	            dwr.engine.beginBatch();
	            rewriteBatchIdHeader('raceTable');          
	           dwr.engine.endBatch();  	
        }
        
          function viewClicked(entryId,tableId)
          {
    	   
    	   if(tableId == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	        	}	
        	}else if(tableId == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	        	}	
        	}else if(tableId == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	        	}	
        	}else if(tableId == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	        	}	
        	}else if(tableId == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	        	}	
        	}
    	   
        	//alert(" View Batch Entry Id=" +entryId + " tableId=" + tableId); 
			var beIdStr = entryId.match(/\d+/)[0];
			if (beIdStr == 'null' || beIdStr == "")
				return;
				
			    if(tableId == 'nameTable'){
			    if (gBatchEntryFieldsDisabled == true) enableBatchEntryFields(tableId);
			    }else if(tableId == 'addrTable'){
			     if (gBatchEntryAddrFieldsDisabled == true) enableBatchEntryFields(tableId);
			    }else if(tableId == 'phoneTable'){
			     if (gBatchEntryPhFieldsDisabled == true) enableBatchEntryFields(tableId);
			    }else if(tableId == 'idenTable'){
			     if (gBatchEntryIdenFieldsDisabled == true) enableBatchEntryFields(tableId);
			    }else if(tableId == 'raceTable'){
			     if (gBatchEntryRaceFieldsDisabled == true) enableBatchEntryFields(tableId);
			    }
			    
			    
		           //get all rows of data
			    JDSPersonForm.getAllBatchAnswer4Table(tableId,function(answer) {
				for (var i = 0; i < answer.length; i++){		
					if (answer.length > 0) {
					//var ans = answer[beIdStr-1];
					var ans = answer[i];
					var id = ans.id;
					//alert(id);
					if (id == beIdStr) {
					
		
					//gBatchEntryUpdateSeq = id;
					if(tableId == 'addrTable'){	
					 
					    gBatchEntryAddrUpdateSeq = id;							  
					    
					    dwr.util.setValue("AddrAsOf",ans.answerMap['addrNameDate']);
					    dwr.util.setValue("AddrType",ans.answerMap['addrType']);
					    dwr.util.setValue("AddrType_textbox",ans.answerMap['addrTypeDesc']);
					    dwr.util.setValue("AddrUse",ans.answerMap['addrUse']);
					    dwr.util.setValue("AddrUse_textbox",ans.answerMap['addrUseDesc']);
					    dwr.util.setValue("AddrStreet1",ans.answerMap['addrStr1']); 
					    dwr.util.setValue("AddrStreet2",ans.answerMap['addrStr2']); 
					    dwr.util.setValue("AddrCity",ans.answerMap['addrCity']); 
					    dwr.util.setValue("AddrState",ans.answerMap['addrState']);
					    dwr.util.setValue("AddrState_textbox",ans.answerMap['addrStateDesc']);
					    dwr.util.setValue("AddrZip",ans.answerMap['addrZip']);
					    dwr.util.setValue("AddrCnty",ans.answerMap['addrCounty']);
					    dwr.util.setValue("AddrCnty_textbox",ans.answerMap['addrCountyDesc']);
					    dwr.util.setValue("AddrCensusTract",ans.answerMap['addrCensusTract']);
					    dwr.util.setValue("AddrCntry",ans.answerMap['addrCountry']);
					    dwr.util.setValue("AddrCntry_textbox",ans.answerMap['addrCountryDesc']);
					    dwr.util.setValue("AddrComments",ans.answerMap['addrComments']);					   
					    			
					}
					if(tableId == 'nameTable'){			
					    gBatchEntryUpdateSeq = id;
					    dwr.util.setValue("NmAsOf",ans.answerMap['nameDate']);
					    dwr.util.setValue("NmType",ans.answerMap['nameType']);
					    dwr.util.setValue("NmType_textbox",ans.answerMap['nameTypeDesc']);
					    dwr.util.setValue("NmPrefix",ans.answerMap['namePrefixDesc']);
					    dwr.util.setValue("NmPrefix_textbox",ans.answerMap['namePrefixDesc']);
					    dwr.util.setValue("NmLast",ans.answerMap['nameLast']); 
					    dwr.util.setValue("NmSecLast",ans.answerMap['nameSecLast']); 
					    dwr.util.setValue("NmFirst",ans.answerMap['nameFirst']); 
					    dwr.util.setValue("NmMiddle",ans.answerMap['nameMiddle']);
					    dwr.util.setValue("NmSecMiddle",ans.answerMap['nameSecMiddle']);
					    dwr.util.setValue("NmSuffix",ans.answerMap['nameSuffix']);
					    dwr.util.setValue("NmSuffix_textbox",ans.answerMap['nameSuffixDesc']);
					    dwr.util.setValue("NmDegree",ans.answerMap['nameDegree']);
					    dwr.util.setValue("NmDegree_textbox",ans.answerMap['nameDegreeDesc']);						   
					    			
					}
					if(tableId == 'phoneTable'){	
						 gBatchEntryPhUpdateSeq = id;	
						dwr.util.setValue("PhAsOf",ans.answerMap['phDate']);
					    dwr.util.setValue("PhType",ans.answerMap['phType']);
					    dwr.util.setValue("PhType_textbox",ans.answerMap['phTypeDesc']);
					    dwr.util.setValue("PhUse",ans.answerMap['phUse']); 
					    dwr.util.setValue("PhUse_textbox",ans.answerMap['phUseDesc']);
					    dwr.util.setValue("PhCntryCd",ans.answerMap['phCntryCd']); 
					    dwr.util.setValue("PhNum",ans.answerMap['phNum']); 
					    dwr.util.setValue("PhExt",ans.answerMap['phExt']); 
					    dwr.util.setValue("PhEmail",ans.answerMap['phEmail']);
					    dwr.util.setValue("PhUrl",ans.answerMap['phUrl']);
					    dwr.util.setValue("PhComments",ans.answerMap['phComments']);					   		   
					    			
					}
					if(tableId == 'idenTable'){	
						 gBatchEntryIdenUpdateSeq = id;				
						dwr.util.setValue("IdAsOf",ans.answerMap['idDate']);
					    dwr.util.setValue("IdType",ans.answerMap['idType']);
					    dwr.util.setValue("IdAssgn",ans.answerMap['idAssgn']);
					      dwr.util.setValue("IdType_textbox",ans.answerMap['idTypeDesc']);
					       dwr.util.setValue("IdAssgn_textbox",ans.answerMap['idAssgnDesc']);
					    dwr.util.setValue("IdValue",ans.answerMap['idValue']);					   	   
					    			
					}
					if(tableId == 'raceTable'){						
					 gBatchEntryRaceUpdateSeq = id;				
						dwr.util.setValue("RaceAsOf",ans.answerMap['raceDate']);
					    dwr.util.setValue("RaceType",ans.answerMap['raceType']);					  				    
					    dwr.util.setValue("RaceType_textbox",ans.answerMap['raceTypeDesc']);
					 	dwr.engine.beginBatch();   
					    JDSPersonForm.getSubRacesForType(ans.answerMap['raceType'], function(data) {
					        DWRUtil.removeAllOptions("RaceDetailCat");					     
					        DWRUtil.addOptions("RaceDetailCat", data, "key", "value" );					        
					          var  mulVal = ans.answerMap['raceDetailCat']; 
					            mulVal = mulVal + "|"; 
					          var selectedmulVal;                        
	                           var myList = getElementByIdOrByName("RaceDetailCat");
					           var myListCount = myList.options.length; 
							   while(mulVal.indexOf("|") !=  -1){
							     selectedmulVal= mulVal.substring(0,mulVal.indexOf("|"));
							     selectedmulVal =  selectedmulVal.replace(/^\s\s*/, '').replace(/\s\s*$/, '');						  	    
							  
							     mulVal  = mulVal.substring(mulVal.indexOf("|")+1); 
							     if(selectedmulVal != null && selectedmulVal != ''){
									for (i=0; i < myListCount; i++) {								    									    
											if (myList.options[i].value == selectedmulVal){												        
	                                        if(myList.options[i].value != '' && myList.options[i].value != ""){	                                      
											 myList.options[i].selected = true;											
											   }
											 }
									    }
									 } 	
							  
							    }//while ends
							   
						    if("RaceDetailCat-selectedValues" != null && getElementByIdOrByName("RaceDetailCat-selectedValues") != null){
								displaySelectedOptions(getElementByIdOrByName("RaceDetailCat"), "RaceDetailCat-selectedValues");
							}
					        
				      	});
				      	dwr.engine.endBatch();	
					  
					       
					  	
					}
				    		        	

				disableBatchEntryFields(tableId);
				break;
			 }
			}
		 }	//for loop ends	
	   }); //all rows of data
     
     }  //viewClicked	
     
      function unhideBatchImg(tableId)
     {
    	 
    	//alert(tableId);
    	 if(tableId == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	    	    //t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	    	    //t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	    	    //t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	    	    //t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}
     }
     
     function editClicked(entryId,tableId){
      if(tableId == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}else if(tableId == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	        	}	
        	}
      // alert(" View Batch Entry Id=" +entryId + " tableId=" + tableId); 
			var beIdStr = entryId.match(/\d+/)[0];
			if (beIdStr == 'null' || beIdStr == "")
				return;
				
		  if(tableId == 'nameTable'){
			  if (gBatchEntryFieldsDisabled == true) enableBatchEntryFields(tableId);
		   }else if(tableId == 'addrTable'){
			   if (gBatchEntryAddrFieldsDisabled == true) enableBatchEntryFields(tableId);
		  }else if(tableId == 'phoneTable'){
			     if (gBatchEntryPhFieldsDisabled == true) enableBatchEntryFields(tableId);
		  }else if(tableId == 'idenTable'){
			     if (gBatchEntryIdenFieldsDisabled == true) enableBatchEntryFields(tableId);
		   }else if(tableId == 'raceTable'){
			     if (gBatchEntryRaceFieldsDisabled == true) enableBatchEntryFields(tableId);
		   }	
	    
	      dwr.engine.beginBatch(); 
           //get all rows of data
		    JDSPersonForm.getAllBatchAnswer4Table(tableId,function(answer) {
				for (var i = 0; i < answer.length; i++){
					if (answer.length > 0) {
					//var ans = answer[beIdStr-1];
					var ans = answer[i];
					var id = ans.id;
					//alert(id);
					if (id == beIdStr) {
					
					if(tableId == 'nameTable'){	
						//alert(ans.answerMap['nameDate']);	
						 $j("#nameTable").show();
						 gBatchEntryUpdateSeq = id;								 
						dwr.util.setValue("NmAsOf",ans.answerMap['nameDate']);
					    dwr.util.setValue("NmType",ans.answerMap['nameType']);
					    dwr.util.setValue("NmType_textbox",ans.answerMap['nameTypeDesc']);
					    dwr.util.setValue("NmPrefix",ans.answerMap['namePrefix']);
					    dwr.util.setValue("NmPrefix_textbox",ans.answerMap['namePrefixDesc']);
					    dwr.util.setValue("NmLast",ans.answerMap['nameLast']); 
					    dwr.util.setValue("NmSecLast",ans.answerMap['nameSecLast']); 
					    dwr.util.setValue("NmFirst",ans.answerMap['nameFirst']); 
					    dwr.util.setValue("NmMiddle",ans.answerMap['nameMiddle']);
					    dwr.util.setValue("NmSecMiddle",ans.answerMap['nameSecMiddle']);
					    dwr.util.setValue("NmSuffix",ans.answerMap['nameSuffix']);
					    dwr.util.setValue("NmSuffix_textbox",ans.answerMap['nameSuffixDesc']);
					    dwr.util.setValue("NmDegree",ans.answerMap['nameDegree']);
					    dwr.util.setValue("NmDegree_textbox",ans.answerMap['nameDegreeDesc']); 
					  				   
					    $j("#AddButtonToggleIdSubSection").hide();
					    $j("#AddNewButtonToggleIdSubSection").hide();
		                $j("#UpdateButtonToggleIdSubSection").show();	
		                }
		                else if(tableId == 'addrTable'){
		                gBatchEntryAddrUpdateSeq =  i;
		                }
		                
		                if(tableId == 'addrTable'){	
					 
					    gBatchEntryAddrUpdateSeq = id;							  
					    
					    
					    
					    var stateCode = ans.answerMap['addrState'];
						JDSPersonForm.getDwrBirthCountiesForState(stateCode, function(data) {
					        DWRUtil.removeAllOptions("AddrCnty");
					        DWRUtil.addOptions("AddrCnty", data, "key", "value" );
					        
					        dwr.util.setValue("AddrAsOf",ans.answerMap['addrNameDate']);
						    dwr.util.setValue("AddrType",ans.answerMap['addrType']);
						    dwr.util.setValue("AddrType_textbox",ans.answerMap['addrTypeDesc']);
						    dwr.util.setValue("AddrUse",ans.answerMap['addrUse']);
						    dwr.util.setValue("AddrUse_textbox",ans.answerMap['addrUseDesc']);
						    dwr.util.setValue("AddrStreet1",ans.answerMap['addrStr1']); 
						    dwr.util.setValue("AddrStreet2",ans.answerMap['addrStr2']); 
						    dwr.util.setValue("AddrCity",ans.answerMap['addrCity']); 
						    dwr.util.setValue("AddrState",ans.answerMap['addrState']);
						    dwr.util.setValue("AddrState_textbox",ans.answerMap['addrStateDesc']);
						    dwr.util.setValue("AddrZip",ans.answerMap['addrZip']);
						    dwr.util.setValue("AddrCnty",ans.answerMap['addrCounty']);
						    dwr.util.setValue("AddrCnty_textbox",ans.answerMap['addrCountyDesc']);
						    dwr.util.setValue("AddrCensusTract",ans.answerMap['addrCensusTract']);
						    dwr.util.setValue("AddrCntry",ans.answerMap['addrCountry']);
						    dwr.util.setValue("AddrCntry_textbox",ans.answerMap['addrCountryDesc']);
						    dwr.util.setValue("AddrComments",ans.answerMap['addrComments']);
				      	});  
					    
					    $j("#AddAddrButtonToggleIdSubSection").hide();
					    $j("#AddNewAddrButtonToggleIdSubSection").hide();
		                $j("#UpdateAddrButtonToggleIdSubSection").show();   			
					}	
					
					 if(tableId == 'phoneTable'){	
					 
					    gBatchEntryPhUpdateSeq = id;							  
					    
					    dwr.util.setValue("PhAsOf",ans.answerMap['phDate']);
					    dwr.util.setValue("PhType",ans.answerMap['phType']);
					    //alert("phTypeDesc : "+ans.answerMap['phTypeDesc']);
					    dwr.util.setValue("PhType_textbox",ans.answerMap['phTypeDesc']);
					    dwr.util.setValue("PhUse",ans.answerMap['phUse']); 
					    dwr.util.setValue("PhUse_textbox",ans.answerMap['phUseDesc']);
					    dwr.util.setValue("PhCntryCd",ans.answerMap['phCntryCd']); 
					    dwr.util.setValue("PhNum",ans.answerMap['phNum']); 
					    dwr.util.setValue("PhExt",ans.answerMap['phExt']); 
					    dwr.util.setValue("PhEmail",ans.answerMap['phEmail']);
					    dwr.util.setValue("PhUrl",ans.answerMap['phUrl']);
					    dwr.util.setValue("PhComments",ans.answerMap['phComments']);				
					    
					    $j("#AddPhButtonToggleIdSubSection").hide();
					    $j("#AddNewPhButtonToggleIdSubSection").hide();
		                $j("#UpdatePhButtonToggleIdSubSection").show();   			
					}
					
					 if(tableId == 'idenTable'){	
					 
					    gBatchEntryIdenUpdateSeq = id;		  
					    
					    dwr.util.setValue("IdAsOf",ans.answerMap['idDate']);
					    dwr.util.setValue("IdType",ans.answerMap['idType']);
					   // alert("idTypeDesc : "+ans.answerMap['idTypeDesc']);
					    dwr.util.setValue("IdType_textbox",ans.answerMap['idTypeDesc']);
					    dwr.util.setValue("IdAssgn",ans.answerMap['idAssgn']); 
					    dwr.util.setValue("IdAssgn_textbox",ans.answerMap['idAssgnDesc']);
					    dwr.util.setValue("IdValue",ans.answerMap['idValue']); 
					   
					    $j("#AddIdenButtonToggleIdSubSection").hide();
					    $j("#AddNewIdenButtonToggleIdSubSection").hide();
		                $j("#UpdateIdenButtonToggleIdSubSection").show();   			
					}
					if(tableId == 'raceTable'){	
					 
					    gBatchEntryRaceUpdateSeq = id;		  
					    
					   dwr.util.setValue("RaceAsOf",ans.answerMap['raceDate']);
					    dwr.util.setValue("RaceType",ans.answerMap['raceType']);					  				    
					    dwr.util.setValue("RaceType_textbox",ans.answerMap['raceTypeDesc']);
					   dwr.engine.beginBatch();   
					    JDSPersonForm.getSubRacesForType(ans.answerMap['raceType'], function(data) {
					        DWRUtil.removeAllOptions("RaceDetailCat");					     
					        DWRUtil.addOptions("RaceDetailCat", data, "key", "value" );					        
					          var  mulVal = ans.answerMap['raceDetailCat']; 	
					             mulVal = mulVal + "|";                         
	                           var myList = getElementByIdOrByName("RaceDetailCat");
					           var myListCount = myList.options.length; 
							   while(mulVal.indexOf("|") !=  -1){
							     selectedmulVal= mulVal.substring(0,mulVal.indexOf("|"));
							     selectedmulVal =  selectedmulVal.replace(/^\s\s*/, '').replace(/\s\s*$/, '');						  	    
							  
							     mulVal  = mulVal.substring(mulVal.indexOf("|")+1); 
							     if(selectedmulVal != null && selectedmulVal != ''){
									for (i=0; i < myListCount; i++) {								    									    
											if (myList.options[i].value == selectedmulVal){												        
	                                        if(myList.options[i].value != '' && myList.options[i].value != ""){	                                      
											 myList.options[i].selected = true;											
											   }
											 }
									    }
									 } 	
							  
							    }//while ends
						    if("RaceDetailCat-selectedValues" != null && getElementByIdOrByName("RaceDetailCat-selectedValues") != null){
								displaySelectedOptions(getElementByIdOrByName("RaceDetailCat"), "RaceDetailCat-selectedValues");
							}
					        
				      	});
				      	dwr.engine.endBatch();	
					       
					   
					    $j("#AddRaceButtonToggleIdSubSection").hide();
					    $j("#AddNewRaceButtonToggleIdSubSection").hide();
		                $j("#UpdateRaceButtonToggleIdSubSection").show();   			
					}	 	   		   		

				//disableBatchEntryFields();
				break;
			 }
			}
		 }	//for loop ends	
	   }); //all rows of data
       dwr.engine.endBatch(); 
     }
     
      function deleteClicked(entryId,tableId)
        {
        	
        			
		  if(tableId == 'nameTable'){
			  if (gBatchEntryFieldsDisabled == true) enableBatchEntryFields();
		   }else if(tableId == 'addrTable'){
			 if (gBatchEntryAddrFieldsDisabled == true) enableBatchEntryFields();
		  }else if(tableId == 'phoneTable'){
			 if (gBatchEntryPhFieldsDisabled == true) enableBatchEntryFields();
		 }else if(tableId == 'idenTable'){
			 if (gBatchEntryIdenFieldsDisabled == true) enableBatchEntryFields();
		 }else if(tableId == 'raceTable'){
			 if (gBatchEntryIdenFieldsDisabled == true) enableBatchEntryFields();
		 }	
			 
        	
        	//alert("Delete Entry Id=" +entryId + " subSection=" + subSection + " pattern=" + pattern + "questionBody=" + questionBody); 
		var beIdStr = entryId.match(/\d+/)[0];
		
		if (beIdStr == 'null' || beIdStr == "")
			return;
		
		//alert("Deleting " + beIdStr);
		var batchentry = { subsecNm:tableId, id:beIdStr };
		JDSPersonForm.deleteBatchAnswer4Table(batchentry,tableId);
       // alert("1");
		// alert("2");
	     //	$j("#AddButtonToggleIdSubSection").show();
	     	
	    if(tableId == 'nameTable'){
		$j("#AddNewButtonToggleIdSubSection").hide();
		$j("#UpdateButtonToggleIdSubSection").hide();
		 $j("#AddButtonToggleIdSubSection").show();
		}else if(tableId == 'addrTable'){
		$j("#AddNewAddrButtonToggleIdSubSection").hide();
		$j("#UpdateAddrButtonToggleIdSubSection").hide();
		 $j("#AddAddrButtonToggleIdSubSection").show();
		}else if(tableId == 'phoneTable'){
			$j("#AddNewPhButtonToggleIdSubSection").hide();
		$j("#UpdatePhButtonToggleIdSubSection").hide();
		 $j("#AddPhButtonToggleIdSubSection").show();
		}else if(tableId == 'idenTable'){
			$j("#AddNewIdenButtonToggleIdSubSection").hide();
		$j("#UpdateIdenButtonToggleIdSubSection").hide();
		 $j("#AddIdenButtonToggleIdSubSection").show();
		}else if(tableId == 'raceTable'){
			$j("#AddNewRaceButtonToggleIdSubSection").hide();
		$j("#UpdateRaceButtonToggleIdSubSection").hide();
		 $j("#AddRaceButtonToggleIdSubSection").show();
		}
	    rewriteBatchIdHeader(tableId);
		clearBatchEntryFields (tableId);
		enableBatchEntryFields(tableId);	
		
		 if(tableId == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	          
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tableId == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tableId == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tableId == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tableId == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}
			

        } 
     
      function updateBatchIdEntry( tabNm)
       {   	  	
	      	var ansMap = getBatchAnswerMapFromScreen(tabNm);	
	      	var  batchentry;     
			dwr.engine.beginBatch();
			//alert(gBatchEntryUpdateSeq);
			if(tabNm == 'nameTable'){
			 batchentry = { subSectNm:tabNm, id: gBatchEntryUpdateSeq, answerMap:ansMap }; 
			}else if(tabNm == 'addrTable'){
			 batchentry = { subSectNm:tabNm, id: gBatchEntryAddrUpdateSeq, answerMap:ansMap }; 
			}else if(tabNm == 'phoneTable'){
			 batchentry = { subSectNm:tabNm, id: gBatchEntryPhUpdateSeq, answerMap:ansMap }; 
		    }else if(tabNm == 'idenTable'){
			 batchentry = { subSectNm:tabNm, id: gBatchEntryIdenUpdateSeq, answerMap:ansMap }; 
		    }else if(tabNm == 'raceTable'){
			 batchentry = { subSectNm:tabNm, id: gBatchEntryRaceUpdateSeq, answerMap:ansMap }; 
		    }			
			JDSPersonForm.updateBatchAnswer4Table(batchentry,tabNm);
			rewriteBatchIdHeader(tabNm);
			clearBatchEntryFields(tabNm);
			
	      	dwr.engine.endBatch();
	      	if(tabNm == 'nameTable'){
	      	$j("#AddButtonToggleIdSubSection").show();
			$j("#UpdateButtonToggleIdSubSection").hide(); 
			$j("#AddNewButtonToggleIdSubSection").hide();  
			}else if(tabNm == 'addrTable'){
			$j("#AddAddrButtonToggleIdSubSection").show();
			$j("#UpdateAddrButtonToggleIdSubSection").hide();  
			$j("#AddNewAddrButtonToggleIdSubSection").hide(); 
			}else if(tabNm == 'phoneTable'){
			$j("#AddPhButtonToggleIdSubSection").show();
			$j("#UpdatePhButtonToggleIdSubSection").hide();   
			$j("#AddNewPhButtonToggleIdSubSection").hide();
			}else if(tabNm == 'idenTable'){
			$j("#AddIdenButtonToggleIdSubSection").show();
			$j("#UpdateIdenButtonToggleIdSubSection").hide(); 
			$j("#AddNewIdenButtonToggleIdSubSection").hide();  
			}else if(tabNm == 'raceTable'){
			$j("#AddRaceButtonToggleIdSubSection").show();
			$j("#UpdateRaceButtonToggleIdSubSection").hide();
			$j("#AddNewRaceButtonToggleIdSubSection").hide();   
			}
			
			 if(tabNm == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	          
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}
			
       } 
     
      function addClicked( tabNm)
        {   	
        	var seqNum = 0;
        	var ansMap = getBatchAnswerMapFromScreen(tabNm);  
        	var batchName;      	       	
        	
         	dwr.engine.beginBatch();
         	var batchentry = {subSectNm:tabNm, answerMap:ansMap }; 
            //	alert(batchentry.answerMap['nameDate']);
         	//var batchentry = { answerMap:ansMap }; 
         	//alert(batchentry.answerMap['NmAsOf']);
         	if(tabNm == 'nameTable'){
         	batchName = 'Name';
         	}else if(tabNm == 'addrTable'){
         	batchName = 'Address';
         	}else if(tabNm == 'phoneTable'){
         	batchName = 'Phone';
         	}else if(tabNm == 'idenTable'){
         	batchName = 'Identification';
         	}else if(tabNm == 'raceTable'){
         	batchName = 'Race';
         	}
         	JDSPersonForm.setBatchAnswer(batchentry,batchName);     	
         	
         	rewriteBatchIdHeader(tabNm);         	
         	clearBatchEntryFields(tabNm);  
         	 if(tabNm == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	          
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}       	
        	dwr.engine.endBatch();        	
        }
        
        function getBatchAnswerMapFromScreen(tabNm)
        {
                var ansMap = {};
            if(tabNm == 'nameTable'){
                 //alert(getElementByIdOrByName('NmAsOf').value);
                   ansMap['nameDate'] = getElementByIdOrByName('NmAsOf').value;
                   ansMap['nameType'] = getElementByIdOrByName('NmType').value;
                   ansMap['nameTypeDesc'] = getElementByIdOrByName('NmType_textbox').value;                
                   ansMap['namePrefix'] = getElementByIdOrByName('NmPrefix').value;
                   ansMap['namePrefixDesc'] = getElementByIdOrByName('NmPrefix_textbox').value;
                   ansMap['nameLast'] = getElementByIdOrByName('NmLast').value;
                   ansMap['nameSecLast'] = getElementByIdOrByName('NmSecLast').value;
                   ansMap['nameFirst'] = getElementByIdOrByName('NmFirst').value;
                   ansMap['nameMiddle'] = getElementByIdOrByName('NmMiddle').value;
                   ansMap['nameSecMiddle'] = getElementByIdOrByName('NmSecMiddle').value;
                   ansMap['nameSuffix'] = getElementByIdOrByName('NmSuffix').value;
                   ansMap['nameSuffixDesc'] = getElementByIdOrByName('NmSuffix_textbox').value;
                   ansMap['nameDegree'] = getElementByIdOrByName('NmDegree').value; 
                   ansMap['nameDegreeDesc'] = getElementByIdOrByName('NmDegree_textbox').value;
                   //alert(ansMap['nameDegreeDesc']);
                         
            }else if(tabNm == 'addrTable'){
                 //alert(getElementByIdOrByName('NmAsOf').value);
                   ansMap['addrNameDate'] = getElementByIdOrByName('AddrAsOf').value;
                   ansMap['addrType'] = getElementByIdOrByName('AddrType').value;
                   ansMap['addrTypeDesc'] = getElementByIdOrByName('AddrType_textbox').value;                
                   ansMap['addrUse'] = getElementByIdOrByName('AddrUse').value;
                   ansMap['addrUseDesc'] = getElementByIdOrByName('AddrUse_textbox').value;
                   ansMap['addrStr1'] = getElementByIdOrByName('AddrStreet1').value;
                   ansMap['addrStr2'] = getElementByIdOrByName('AddrStreet2').value;
                   ansMap['addrCity'] = getElementByIdOrByName('AddrCity').value;
                   ansMap['addrState'] = getElementByIdOrByName('AddrState').value;
                   ansMap['addrStateDesc'] = getElementByIdOrByName('AddrState_textbox').value;
                   ansMap['addrZip'] = getElementByIdOrByName('AddrZip').value;
                   ansMap['addrCounty'] = getElementByIdOrByName('AddrCnty').value;
                   ansMap['addrCountyDesc'] = getElementByIdOrByName('AddrCnty_textbox').value;
                   ansMap['addrCensusTract'] = getElementByIdOrByName('AddrCensusTract').value;
                    ansMap['addrCountry'] = getElementByIdOrByName('AddrCntry').value;
                   ansMap['addrCountryDesc'] = getElementByIdOrByName('AddrCntry_textbox').value; 
                   ansMap['addrComments'] = getElementByIdOrByName('AddrComments').value;       
                         
            }else if(tabNm == 'phoneTable'){
                  // alert("PhUse_textbox : "+getElementByIdOrByName('PhUse_textbox').value);
                   ansMap['phDate'] = getElementByIdOrByName('PhAsOf').value;
                   ansMap['phType'] = getElementByIdOrByName('PhType').value;
                   ansMap['phTypeDesc'] = getElementByIdOrByName('PhType_textbox').value;                
                   ansMap['phUse'] = getElementByIdOrByName('PhUse').value;
                   ansMap['phUseDesc'] = getElementByIdOrByName('PhUse_textbox').value;
                   ansMap['phCntryCd'] = getElementByIdOrByName('PhCntryCd').value;
                   ansMap['phNum'] = getElementByIdOrByName('PhNum').value;
                   ansMap['phExt'] = getElementByIdOrByName('PhExt').value;
                   ansMap['phEmail'] = getElementByIdOrByName('PhEmail').value;
                   ansMap['phUrl'] = getElementByIdOrByName('PhUrl').value;
                   ansMap['phComments'] = getElementByIdOrByName('PhComments').value;                  
            }else if(tabNm == 'idenTable'){
                  // alert("PhUse_textbox : "+getElementByIdOrByName('PhUse_textbox').value);
                   ansMap['idDate'] = getElementByIdOrByName('IdAsOf').value;
                   ansMap['idType'] = getElementByIdOrByName('IdType').value;
                   ansMap['idTypeDesc'] = getElementByIdOrByName('IdType_textbox').value;                
                   ansMap['idAssgn'] = getElementByIdOrByName('IdAssgn').value;
                   ansMap['idAssgnDesc'] = getElementByIdOrByName('IdAssgn_textbox').value;
                   ansMap['idValue'] = getElementByIdOrByName('IdValue').value;
                                  
            }else if(tabNm == 'raceTable'){
                 
                   ansMap['raceDate'] = getElementByIdOrByName('RaceAsOf').value;
                   ansMap['raceType'] = getElementByIdOrByName('RaceType').value;
                   ansMap['raceTypeDesc'] = getElementByIdOrByName('RaceType_textbox').value;                
                 
                   
                   var myList = getElementByIdOrByName('RaceDetailCat');
                   var myListCount = myList.options.length;
                  var textToDisplay = "";
                  var textToDisplayDesc = "";
                  
				   for (i=0; i < myListCount; i++) {
				    if (myList.options[i].selected == true){
				     var optionsVal = myList.options[i].value;
				      var optionsValDesc = myList.options[i].text;
				       	       
			               textToDisplay += optionsVal  + " | ";
			               textToDisplayDesc  += optionsValDesc  + " | ";
				        }
		           }
		         ansMap['raceDetailCat'] = textToDisplay; 
		  
		         textToDisplayDesc = textToDisplayDesc.substring(0,textToDisplayDesc.lastIndexOf("|")-1);
		
		         ansMap['raceDetailCatDesc'] = textToDisplayDesc; 
                                  
            }              
        	return ansMap;
        }
        
         function validateBatchData(){
                  var flag = true;
                  var errorLabels = new Array();var errorLabels1 = new Array();var errorLabels2 = new Array();
                  var errorLabels3 = new Array();var errorLabels4 = new Array();
   
                   var asofDateNm  = getElementByIdOrByName('NmAsOf').value;                   
                   var typeNm = getElementByIdOrByName('NmType_textbox').value;           
                   var preNm = getElementByIdOrByName('NmPrefix_textbox').value;
                   var lastNm = getElementByIdOrByName('NmLast').value;
                   var secLastNm = getElementByIdOrByName('NmSecLast').value;
                   var firstNm = getElementByIdOrByName('NmFirst').value;
                   var middleNm = getElementByIdOrByName('NmMiddle').value;
                   var secMiddleNm = getElementByIdOrByName('NmSecMiddle').value;               
                   var SufNm = getElementByIdOrByName('NmSuffix_textbox').value;                   
                   var DegNm = getElementByIdOrByName('NmDegree_textbox').value;
                   
               if ( (asofDateNm != null && asofDateNm != "") || (typeNm != null && typeNm != "") ||(preNm != null && preNm != "") 
                     ||(lastNm != null && lastNm != "") ||(secLastNm != null && secLastNm != "") ||(firstNm != null && firstNm != "")
                      ||(middleNm != null && middleNm != "") ||(secMiddleNm != null && secMiddleNm != "")  ||(SufNm != null && SufNm != "")
                      ||(DegNm != null && DegNm != "") ) {
        		      errorLabels.push("Data has been entered in the  Name section. Please press Add  or clear the data and submit again.");
        		      if (errorLabels.length > 0) {
			        		displayErrors('NameerrorMessages', errorLabels); 			        		
			        		flag = false;
			        	}else{
					      $j('#NameerrorMessages').css("display", "none");
					   }
        	        }else{
        	          $j('#NameerrorMessages').css("display", "none");
        	        }
        	       
        	       var asofDateAddr = getElementByIdOrByName('AddrAsOf').value;                   
                  var typeAddr = getElementByIdOrByName('AddrType_textbox').value;         
                  var useAddr = getElementByIdOrByName('AddrUse_textbox').value;
                 var str1Addr = getElementByIdOrByName('AddrStreet1').value;
                 var str2Addr = getElementByIdOrByName('AddrStreet2').value;
                  var addrCity = getElementByIdOrByName('AddrCity').value;                
                    var stateAddr = getElementByIdOrByName('AddrState_textbox').value;
                  var zipAddr = getElementByIdOrByName('AddrZip').value;                 
                  var cntyAddr = getElementByIdOrByName('AddrCnty_textbox').value; 
                  var censusTractAddr = getElementByIdOrByName('AddrCensusTract').value;                  
                   var cntryAddr = getElementByIdOrByName('AddrCntry_textbox').value; 
                   var comAddr = getElementByIdOrByName('AddrComments').value;   
                   
               if ( (asofDateAddr != null && asofDateAddr != "") || (typeAddr != null && typeAddr != "") ||(useAddr != null && useAddr != "") 
                     ||(str1Addr != null && str1Addr != "") ||(str2Addr != null && str2Addr != "") ||(addrCity != null && addrCity != "")
                      ||(stateAddr != null && stateAddr != "") ||(zipAddr != null && zipAddr != "")  ||(cntyAddr != null && cntyAddr != "")
                      ||(cntryAddr != null && cntryAddr != "") ||(comAddr != null && comAddr != "") || (censusTractAddr!=null && censusTractAddr!="")) {
        		      errorLabels1.push("Data has been entered in the Address section. Please press Add  or clear the data and submit again.");
        		      if (errorLabels1.length > 0) {
				        		displayErrors('AddrerrorMessages', errorLabels1); 	
				        		
				        		flag = false;			        		
				       	}else{
						   $j('#AddrerrorMessages').css("display", "none");
						  }
        	        } else{
        	              $j('#AddrerrorMessages').css("display", "none");
        	        }  
        	        
        	          var asofDateph = getElementByIdOrByName('PhAsOf').value;                  
	                  var typePh = getElementByIdOrByName('PhType_textbox').value;              
	                  var usePh = getElementByIdOrByName('PhUse_textbox').value;
	                  var cntryPh = getElementByIdOrByName('PhCntryCd').value;
	                  var numPh = getElementByIdOrByName('PhNum').value;
	                  var extPh = getElementByIdOrByName('PhExt').value;
	                  var emailPh = getElementByIdOrByName('PhEmail').value;
	                  var urlPh = getElementByIdOrByName('PhUrl').value;
	                  var comPh = getElementByIdOrByName('PhComments').value;  
                   
                 if ( (asofDateph != null && asofDateph != "") || (typePh != null && typePh != "") ||(usePh != null && usePh != "") 
                     ||(cntryPh != null && cntryPh != "") ||(numPh != null && numPh != "") ||(extPh != null && extPh != "")
                      ||(emailPh != null && emailPh != "") ||(urlPh != null && urlPh != "")  ||(comPh != null && comPh != "")
                      ) {
        		      errorLabels2.push("Data has been entered in the Phone & Email section. Please press Add  or clear the data and submit again.");
        		      if (errorLabels2.length > 0) {
				        		displayErrors('PhoneerrorMessages', errorLabels2); 
				        		
				        		flag = false;			        		
				        	}else{
						     $j('#PhoneerrorMessages').css("display", "none");
						}
        	        }else{
        	              $j('#PhoneerrorMessages').css("display", "none");
        	        }  
        	        
        	        var asofDateId = getElementByIdOrByName('IdAsOf').value;                  
                   var typeId = getElementByIdOrByName('IdType_textbox').value;  
                   
                   var assgnId = getElementByIdOrByName('IdAssgn_textbox').value;
                  var valId = getElementByIdOrByName('IdValue').value; 
                   
                 if ( (asofDateId != null && asofDateId != "") || (typeId != null && typeId != "") ||(assgnId != null && assgnId != "") 
                     ||(valId != null && valId != "") ) {
        		      errorLabels3.push("Data has been entered in the Identification section. Please press Add  or clear the data and submit again.");
        		      if (errorLabels3.length > 0) {
				        		displayErrors('IdenerrorMessages', errorLabels3); 
				        	
				        		flag = false;		        		
				        	}else{
						 	   $j('#IdenerrorMessages').css("display", "none");
						 	}
        	        } else{        	        
        	         $j('#IdenerrorMessages').css("display", "none");
        	        } 
        	        
        	         var asofDateRc = getElementByIdOrByName('RaceAsOf').value;
                 
                   var typeRc = getElementByIdOrByName('RaceType_textbox').value;    
                   
                 if ( (asofDateRc != null && asofDateRc != "") || (typeRc != null && typeRc != "")  ) {
        		      errorLabels4.push("Data has been entered in the Race section. Please press Add  or clear the data and submit again.");
        		      if (errorLabels4.length > 0) {
				        	displayErrors('RaceerrorMessages', errorLabels4); 				        		
				        		flag = false;			        		
				        	}else{
						      $j('#RaceerrorMessages').css("display", "none");	 
						 } 
        	        } else{
        	           $j('#RaceerrorMessages').css("display", "none");	 
        	        } 
        	        
        	        if(!flag){
        	        return false;
        	        }else {
        	        return true;
        	        }                 
                   
   }

         function ValidateNonBatchDateFields()
         {
        	 var errorLabels = new Array();
        	 var errorElts = new Array();
        	 var dateElts = $j('img[id="AsofDateAdminIcon"],[id="AsOfDateEthIcon"],[id="AsofDateSBIcon"],[id="patientDOBIcon"],[id="AsofDateMorbIcon"],[id="DateOfDeathIcon"],[id="AsOfDateGenInfoIcon"]');
             var j = 0;
             for (var i = 0; i < dateElts.length; i++)
             	{
             	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
             	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
             	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
             	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
             	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
             	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
             	 //expression to match required date format
                dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
                if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
                     //highlight field label red if format error
                 	$j("#"+dateEltsLabelId).css("color", "990000");
                 	var theErrEleId = dateEltsLabelId;
 					//error message text
 					var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
 					errorLabels.push(errHref);
                }else if (dateEltsValue.match(dateFormat)) {
					var dtArray = dateEltsValue.match(dateFormat);
					var dtMonth = dtArray[1];
					var dtDay = dtArray[3];
					var dtYear = dtArray[5];
					if ((dtMonth < 1 || dtMonth > 12)
							|| (dtDay < 1 || dtDay > 31)
							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
						var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
						errorElts.push(dateEltsId);
						errorLabels.push(errHtmlStr);
						$j("#" + dateEltsLabelId).css("color", "990000");
					} else {
						$j("#" + dateEltsLabelId).css("color", "black");
					}
				}

                 	else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
                }

             if (errorLabels.length > 0) {
         		displayErrors('PatientSubmitErrorMessages', errorLabels); 
         		return false;
         	}
 		$j('#PatientSubmitErrorMessages').css("display", "none");
 		return true;
 		
             }
        function NameBatchValidateFunction() 
        {
        	// type and value are required, assigning authority is not
        	var errorLabels = new Array();
        	var asofDate = getElementByIdOrByName('NmAsOf').value;
        	var errorElts = new Array();
       	        	
        	var typeDescTxtNode = getElementByIdOrByName('NmType_textbox');
        	var typeId = jQuery.trim(typeDescTxtNode.value);        	
        	
        	if (asofDate == 'null' || asofDate == "")
        		errorLabels.push("Name as of Date is a required field.");
        	if (typeId == 'null' || typeId == "")
        		errorLabels.push("Type is a required field.");
        	
			//grabbing the calendar icon beside date field
        	var dateElts = $j('img[id="NmAsOfIcon"]');
            var j = 0;
            for (var i = 0; i < dateElts.length; i++)
            	{
            	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
            	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
            	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
            	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
            	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
            	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
            	 //expression to match required date format
                 dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
            	 if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
                    //highlight field label red if format error
                	$j("#"+dateEltsLabelId).css("color", "990000");
                	var theErrEleId = dateEltsLabelId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
            	 }else if (dateEltsValue.match(dateFormat)) {
 					var dtArray = dateEltsValue.match(dateFormat);
 					var dtMonth = dtArray[1];
 					var dtDay = dtArray[3];
 					var dtYear = dtArray[5];
 					if ((dtMonth < 1 || dtMonth > 12)
 							|| (dtDay < 1 || dtDay > 31)
 							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
 							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
 						var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
 						errorElts.push(dateEltsId);
 						errorLabels.push(errHtmlStr);
 						$j("#" + dateEltsLabelId).css("color", "990000");
 					} else {
 						$j("#" + dateEltsLabelId).css("color", "black");
 					}
 				}
                	else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
            	}
        	
        	
        	if (errorLabels.length > 0) {
        		displayErrors('NameerrorMessages', errorLabels); 
        		return false;
        	}
		$j('#NameerrorMessages').css("display", "none");
		return true;
		
        }
        
         function AddrBatchValidateFunction() 
        {
        	// type and value are required, assigning authority is not
        	var errorLabels = new Array();
        	var errorElts = new Array();
        	var asofDate = getElementByIdOrByName('AddrAsOf').value;
        	
        	var typeDescTxtNode = getElementByIdOrByName('AddrType_textbox');
        	var typeId = jQuery.trim(typeDescTxtNode.value);
        	
        	var idValueNode = getElementByIdOrByName('AddrUse_textbox');
        	var idValue = jQuery.trim(idValueNode.value);
        	
        	if (asofDate == 'null' || asofDate == "")
        		errorLabels.push("Address as of Date is a required field.");
        	if (typeId == 'null' || typeId == "")
        		errorLabels.push("Type is a required field.");
        	if (idValue == 'null' || idValue == "")
        		errorLabels.push("Use is a required field.");
        	var censusTract = getElementByIdOrByName('AddrCensusTract');
        	if(censusTract.value!=null && censusTract.value!=''){
	        	var re = /^(\d{4}|\d{4}.(?!99)\d{2})$/;
	        	if(!re.test(censusTract.value)){
	        		errorLabels.push("Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.");
         		}
         	}

        	//grabbing the calendar icon beside date field
        	var dateElts = $j('img[id="AddrAsOfIcon"]');
            var j = 0;
            for (var i = 0; i < dateElts.length; i++)
            	{
            	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
            	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
            	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
            	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
            	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
            	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
            	 //expression to match required date format
                  dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
                if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
                    //highlight field label red if format error
                	$j("#"+dateEltsLabelId).css("color", "990000");
                	var theErrEleId = dateEltsLabelId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
                }else if (dateEltsValue.match(dateFormat)) {
					var dtArray = dateEltsValue.match(dateFormat);
					var dtMonth = dtArray[1];
					var dtDay = dtArray[3];
					var dtYear = dtArray[5];
					if ((dtMonth < 1 || dtMonth > 12)
							|| (dtDay < 1 || dtDay > 31)
							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
						var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
						errorElts.push(dateEltsId);
						errorLabels.push(errHtmlStr);
						$j("#" + dateEltsLabelId).css("color", "990000");
					} else {
						$j("#" + dateEltsLabelId).css("color", "black");
					}
				}
                	else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
            	}
        	
        	if (errorLabels.length > 0) {
        		displayErrors('AddrerrorMessages', errorLabels); 
        		return false;
        	}
		$j('#AddrerrorMessages').css("display", "none");
		return true;
		
        }
        
         function PhoneBatchValidateFunction() 
        {
        	// type and value are required, assigning authority is not
        	var errorLabels = new Array();
        	var errorElts = new Array();
        	var asofDate = getElementByIdOrByName('PhAsOf').value;
        	
        	var typeDescTxtNode = getElementByIdOrByName('PhType_textbox');
        	var typeId = jQuery.trim(typeDescTxtNode.value);
        	
        	var idValueNode = getElementByIdOrByName('PhUse_textbox');
        	var idValue = jQuery.trim(idValueNode.value);
        	
        	if (asofDate == 'null' || asofDate == "")
        		errorLabels.push("Phone & Email as of Date is a required field.");
        	if (typeId == 'null' || typeId == "")
        		errorLabels.push("Type is a required field.");
        	if (idValue == 'null' || idValue == "")
        		errorLabels.push("Use is a required field.");

        	//grabbing the calendar icon beside date field
        	var dateElts = $j('img[id="PhAsOfIcon"]');
            var j = 0;
            for (var i = 0; i < dateElts.length; i++)
            	{
            	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
            	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
            	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
            	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
            	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
            	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
            	 //expression to match required date format
                  dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
                if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
                    //highlight field label red if format error
                	$j("#"+dateEltsLabelId).css("color", "990000");
                	var theErrEleId = dateEltsLabelId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
                }else if (dateEltsValue.match(dateFormat)) {
					var dtArray = dateEltsValue.match(dateFormat);
					var dtMonth = dtArray[1];
					var dtDay = dtArray[3];
					var dtYear = dtArray[5];
					if ((dtMonth < 1 || dtMonth > 12)
							|| (dtDay < 1 || dtDay > 31)
							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
						var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
						errorElts.push(dateEltsId);
						errorLabels.push(errHtmlStr);
						$j("#" + dateEltsLabelId).css("color", "990000");
					} else {
						$j("#" + dateEltsLabelId).css("color", "black");
					}
				}
                	else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
            	}
        	
        	if (errorLabels.length > 0) {
        		displayErrors('PhoneerrorMessages', errorLabels); 
        		return false;
        	}
		$j('#PhoneerrorMessages').css("display", "none");
		return true;
		
        }
        
          function IdenBatchValidateFunction() 
        {
        	// type and value are required, assigning authority is not
        	var errorLabels = new Array();
        	var errorElts = new Array();
        	var asofDate = getElementByIdOrByName('IdAsOf').value;
        	
        	var typeDescTxtNode = getElementByIdOrByName('IdType_textbox');
        	var typeId = jQuery.trim(typeDescTxtNode.value);
        	
        	var idValueNode = getElementByIdOrByName('IdValue');
        	var idValue = jQuery.trim(idValueNode.value);
        	
        	if (asofDate == 'null' || asofDate == "")
        		errorLabels.push("Identification as of Date is a required field.");
        	if (typeId == 'null' || typeId == "")
        		errorLabels.push("Type is a required field.");
        	if (idValue == 'null' || idValue == "")
        		errorLabels.push("ID Value is a required field.");

        	//grabbing the calendar icon beside date field
        	var dateElts = $j('img[id="IdAsOfIcon"]');
            var j = 0;
            for (var i = 0; i < dateElts.length; i++)
            	{
            	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
            	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
            	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
            	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
            	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
            	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
            	 //expression to match required date format
                 dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
                if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
                    //highlight field label red if format error
                	$j("#"+dateEltsLabelId).css("color", "990000");
                	var theErrEleId = dateEltsLabelId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
                }else if (dateEltsValue.match(dateFormat)) {
					var dtArray = dateEltsValue.match(dateFormat);
					var dtMonth = dtArray[1];
					var dtDay = dtArray[3];
					var dtYear = dtArray[5];
					if ((dtMonth < 1 || dtMonth > 12)
							|| (dtDay < 1 || dtDay > 31)
							|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
							|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
						var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
						errorElts.push(dateEltsId);
						errorLabels.push(errHtmlStr);
						$j("#" + dateEltsLabelId).css("color", "990000");
					} else {
						$j("#" + dateEltsLabelId).css("color", "black");
					}
				}
                	else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
            	}
        	
        	if (errorLabels.length > 0) {
        		displayErrors('IdenerrorMessages', errorLabels); 
        		return false;
        	}
		$j('#IdenerrorMessages').css("display", "none");
		return true;
		
        }
        
         function AddUpdateRaceBatch(opType) 
        {
        	
        	// type and value are required, assigning authority is not
        	var count = 0;
        	var errorLabels = new Array();
        	var errorElts = new Array();
        	var asofDate = getElementByIdOrByName('raceAsOf').value;
        	
        	var typeDescTxtNode = getElementByIdOrByName('RaceType_textbox');
        	var typeId = jQuery.trim(typeDescTxtNode.value);        	
        	dwr.engine.beginBatch();
        	if (asofDate == 'null' || asofDate == "")
        		errorLabels.push("Race as of Date is a required field.");
        	if (typeId == 'null' || typeId == "")
        		errorLabels.push("Type is a required field.");        	
        	
        	    JDSPersonForm.getAllBatchAnswer4Table("raceTable",function(answer) {     
				for (var i = 0; i < answer.length; i++){				
					ans = answer[i];
					//alert(ans.answerMap['raceTypeDesc']);
					if(opType == 'update' && ans.answerMap['raceTypeDesc']==typeId && gBatchEntryRaceUpdateSeq != ans.id){
					//alert(" ans.id "+ans.id + " gBatchEntryRaceUpdateSeq : "+gBatchEntryRaceUpdateSeq);
					errorLabels.push("Race "+typeId+ " has already been added to the repeating block.Please select another race to add ." ); 
					break;
					}else if(opType == 'add' && ans.answerMap['raceTypeDesc']==typeId){
					errorLabels.push("Race "+typeId+ " has already been added to the repeating block.Please select another race to add ." ); 
					break;
					}					
					}				
					if (errorLabels.length > 0) {
		        		displayErrors('RaceerrorMessages', errorLabels); 
		        		count = count+1;
		        	  }	
		        	    
					if(count==0){
						   $j('#RaceerrorMessages').css("display", "none");	  
						     if(opType == 'update')	{
						       updateBatchIdEntry("raceTable");
						       }else if(opType == 'add'){
						        addClicked("raceTable");
						       }
						       
						   } 	 									
				
			});
			 dwr.engine.endBatch();	 


	        	//grabbing the calendar icon beside date field
	        	var dateElts = $j('img[id="RaceAsOfIcon"]');
	            var j = 0;
	            for (var i = 0; i < dateElts.length; i++)
	            	{
	            	var dateEltsNode=$j(dateElts[i]).parent().children("input[type=text]");
	            	var dateEltsLabelNode=$j(dateElts[i]).parent().parent().find("span[class=InputFieldLabel]");
	            	var dateEltsLabelId=$j(dateElts[i]).parent().parent().find("span:first").attr("id");
	            	var dateEltsLabel=$j(dateElts[i]).parent().parent().find("span:first").text();
	            	var dateEltsId=$j(dateElts[i]).parent().children("input[type=text]").attr("id");
	            	var dateEltsValue=$j(dateElts[i]).parent().children("input[type=text]").attr("value");
	            	 //expression to match required date format
                   dateFormat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	                if(dateEltsValue != '' && !dateEltsValue.match(dateFormat)) {
	                    //highlight field label red if format error
	                	$j("#"+dateEltsLabelId).css("color", "990000");
	                	var theErrEleId = dateEltsLabelId;
						//error message text
						var errHref="<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must be in the format of mm/dd/yyyy.";
						errorLabels.push(errHref);
	                }else if (dateEltsValue.match(dateFormat)) {
						var dtArray = dateEltsValue.match(dateFormat);
						var dtMonth = dtArray[1];
						var dtDay = dtArray[3];
						var dtYear = dtArray[5];
						if ((dtMonth < 1 || dtMonth > 12)
								|| (dtDay < 1 || dtDay > 31)
								|| ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
								|| ((dtMonth == 2) && (dtDay > 29 || (dtDay == 29 && !(dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0)))))) {
							var errHtmlStr = "<a href=\"javascript:getElementByIdOrByName('"+dateEltsId+"').focus()\">"+dateEltsLabel+"</a> must have valid day and month combination for date value.";
							errorElts.push(dateEltsId);
							errorLabels.push(errHtmlStr);
							$j("#" + dateEltsLabelId).css("color", "990000");
						} else {
							$j("#" + dateEltsLabelId).css("color", "black");
						}
					}
	                	else $j("#"+dateEltsLabelId).css("color", "black");//highlight field label black if format correct
	            	}
	        	
		
        }
        
     function rewriteBatchIdHeader(tabNm) 
  	{
      var patternrow="pattern"+tabNm+"Row";
      var patternbody="pattern"+tabNm;
      
            //get all rows of data
	    JDSPersonForm.getAllBatchAnswer4Table(tabNm,function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows(patternbody, { filter:function(tr) {
				return (tr.id != patternrow);
		}});
		
	
		
		if (answer.length == 0) {
			//no rows - display 'No Data has been entered'
		 //	$('nopattern' +subSectNm).style.display = "block";
		 	return;
		 }
		
        //alert(answer.length);
		for (var i = 0; i < answer.length; i++){
		//alert("i ="+i);
			ans = answer[i];
			id = ans.id;
			
			
			dwr.util.cloneNode(patternrow, { idSuffix:id }); 
		      //pull the data for each row
		      for (var key in ans.answerMap) {
		      	if ( key != null || key != '' ) {
		      		var val = ans.answerMap[key];
		      		//alert("node=table" + key + id + ' val='+val);
		      	if(key == 'addrStr1' || key == 'addrStr2'){
		             if(ans.answerMap['addrStr2'] != null && ans.answerMap['addrStr2'] != '' ){
					        dwr.util.setValue("table" + "addrAddress" + id, ans.answerMap['addrStr1'] + ', '+ ans.answerMap['addrStr2']);
					  }else{
					        dwr.util.setValue("table" + "addrAddress" + id, ans.answerMap['addrStr1']);
					 }
		      	}else if(key == 'addrTypeDesc'){
		      	 dwr.util.setValue("table" + "addrTypeDesc" + id, ans.answerMap['addrTypeDesc'] + '/'+ ans.answerMap['addrUseDesc']);
		      	}else if(key == 'phTypeDesc'){
		      	 dwr.util.setValue("table" + "phTypeDesc" + id, ans.answerMap['phTypeDesc'] + '/'+ ans.answerMap['phUseDesc']);
		      	}else{
				dwr.util.setValue("table" + key + id, val);
				}
				 
			}
		     }
		     //clear display = 'none'
		     $(patternrow + id).style.display = ""; 
		     //hide No Data Entered
		     $('no' +patternrow).style.display = "none";
		}		
			
	    }); //all rows of data

    	}
        
       function disableBatchEntryFields (tabNm)
	{
	  	  if(tabNm == 'nameTable'){
			  	 // alert(tabNm);
				  	gBatchEntryFieldsDisabled = true;	
				  	$j($j("#NmAsOf").parent().parent()).find(":input").attr("disabled", true);
				  	$j($j("#NmAsOf").parent().parent()).find("img").attr("disabled", true);
				  	$j($j("#NmAsOf").parent().parent()).find("img").attr("tabIndex", "-1");
				  	
				  	$j("#NmAsOfL").parent().find("span[title]").css("color", "#666666");
					$j($j("#NmType").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#NmType").parent().parent()).find("img").attr("disabled", true);
					$j($j("#NmType").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#NmTypeL").parent().find("span[title]").css("color", "#666666");
					
					$j($j("#NmPrefix").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#NmPrefix").parent().parent()).find("img").attr("disabled", true);
					$j($j("#NmPrefix").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#NmPrefixL").parent().find("span[title]").css("color", "#666666");
				   
				   	$j("#NmLast").parent().find(":input").attr("disabled", true);
				   	$j("#NmLastL").parent().find("span[title]").css("color", "#666666");
				   	$j("#NmSecLast").parent().find(":input").attr("disabled", true);
				   	$j("#NmSecLastL").parent().find("span[title]").css("color", "#666666");
				   	$j("#NmFirst").parent().find(":input").attr("disabled", true);
				   	$j("#NmFirstL").parent().find("span[title]").css("color", "#666666");
				   	$j("#NmMiddle").parent().find(":input").attr("disabled", true);
				   	$j("#NmMiddleL").parent().find("span[title]").css("color", "#666666");
				   	$j("#NmSecMiddle").parent().find(":input").attr("disabled", true);
				   	$j("#NmSecMiddleL").parent().find("span[title]").css("color", "#666666");
				   	
				   	$j($j("#NmSuffix").parent().parent()).find(":input").attr("disabled", true);
				   	$j($j("#NmSuffix").parent().parent()).find("img").attr("disabled", true);
				   	$j($j("#NmSuffix").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#NmSuffixL").parent().find("span[title]").css("color", "#666666");  
				   
				   	$j($j("#NmDegree").parent().parent()).find(":input").attr("disabled", true);
				   	$j($j("#NmDegree").parent().parent()).find("img").attr("disabled", true);
				   	$j($j("#NmDegree").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#NmDegreeL").parent().find("span[title]").css("color", "#666666");   	
				    $j("#AddButtonToggleIdSubSection").hide();
				   	$j("#AddNewButtonToggleIdSubSection").show();
					$j("#UpdateButtonToggleIdSubSection").hide();
		}else if(tabNm == 'addrTable'){
			  	 // alert(tabNm);
				  	gBatchEntryAddrFieldsDisabled = true;	
				  	$j($j("#AddrAsOf").parent().parent()).find(":input").attr("disabled", true);
				  	$j($j("#AddrAsOf").parent().parent()).find("img").attr("disabled", true);
				  	$j($j("#AddrAsOf").parent().parent()).find("img").attr("tabIndex", "-1");
				  	
				  	$j("#AddrAsOfL").parent().find("span[title]").css("color", "#666666");
					$j($j("#AddrType").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#AddrType").parent().parent()).find("img").attr("disabled", true);
					$j($j("#AddrType").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#AddrTypeL").parent().find("span[title]").css("color", "#666666");
					
					$j($j("#AddrUse").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#AddrUse").parent().parent()).find("img").attr("disabled", true);
					$j($j("#AddrUse").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#AddrUseL").parent().find("span[title]").css("color", "#666666");
				   
				   	$j("#AddrStreet1").parent().find(":input").attr("disabled", true);
				   	$j("#AddrStreet1L").parent().find("span[title]").css("color", "#666666");
				   	$j("#AddrStreet2").parent().find(":input").attr("disabled", true);
				   	$j("#AddrStreet2L").parent().find("span[title]").css("color", "#666666");
				   	$j("#AddrCity").parent().find(":input").attr("disabled", true);
				   	$j("#AddrCityL").parent().find("span[title]").css("color", "#666666");
				   	$j("#AddrZip").parent().find(":input").attr("disabled", true);
				   	$j("#AddrZipL").parent().find("span[title]").css("color", "#666666");
				   	$j("#AddrComments").parent().find(":input").attr("disabled", true);
				   	$j("#AddrCommentsL").parent().find("span[title]").css("color", "#666666");
				   	
				   	$j($j("#AddrState").parent().parent()).find(":input").attr("disabled", true);
				   	$j($j("#AddrState").parent().parent()).find("img").attr("disabled", true);
				   	$j($j("#AddrState").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#AddrStateL").parent().find("span[title]").css("color", "#666666");  
				   
				   	$j($j("#AddrCnty").parent().parent()).find(":input").attr("disabled", true);
				   	$j($j("#AddrCnty").parent().parent()).find("img").attr("disabled", true);
				   	$j($j("#AddrCnty").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#AddrCntyL").parent().find("span[title]").css("color", "#666666");
					$j($j("#AddrCensusTract").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#AddrCensusTract").parent().parent()).find("img").attr("disabled", true);
					$j($j("#AddrCensusTract").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#AddrCensusTractL").parent().find("span[title]").css("color", "#666666");
					$j($j("#AddrCntry").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#AddrCntry").parent().parent()).find("img").attr("disabled", true);
					$j($j("#AddrCntry").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#AddrCntryL").parent().find("span[title]").css("color", "#666666"); 
					 	
				    $j("#AddAddrButtonToggleIdSubSection").hide();
				   	$j("#AddNewAddrButtonToggleIdSubSection").show();
					$j("#UpdateAddrButtonToggleIdSubSection").hide();
		}else if(tabNm == 'phoneTable'){
			  	 // alert(tabNm);
				  	gBatchEntryPhFieldsDisabled = true;	
				  	$j($j("#PhAsOf").parent().parent()).find(":input").attr("disabled", true);
				  	$j($j("#PhAsOf").parent().parent()).find("img").attr("disabled", true);
				  	$j($j("#PhAsOf").parent().parent()).find("img").attr("tabIndex", "-1");
				  	
				  	$j("#PhAsOfL").parent().find("span[title]").css("color", "#666666");
					$j($j("#PhType").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#PhType").parent().parent()).find("img").attr("disabled", true);
					$j($j("#PhType").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#PhTypeL").parent().find("span[title]").css("color", "#666666");
					
					$j($j("#PhUse").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#PhUse").parent().parent()).find("img").attr("disabled", true);
					$j($j("#PhUse").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#PhUseL").parent().find("span[title]").css("color", "#666666");
				   
				   	$j("#PhCntryCd").parent().find(":input").attr("disabled", true);
				   	$j("#PhCntryCdL").parent().find("span[title]").css("color", "#666666");
				   	$j("#PhNum").parent().find(":input").attr("disabled", true);
				   	$j("#PhNumL").parent().find("span[title]").css("color", "#666666");
				   	$j("#PhExt").parent().find(":input").attr("disabled", true);
				   	$j("#PhExtL").parent().find("span[title]").css("color", "#666666");
				   	$j("#PhEmail").parent().find(":input").attr("disabled", true);
				   	$j("#PhEmailL").parent().find("span[title]").css("color", "#666666");
				   	$j("#PhUrl").parent().find(":input").attr("disabled", true);
				   	$j("#PhUrlL").parent().find("span[title]").css("color", "#666666");			   
				   	$j("#PhComments").parent().find(":input").attr("disabled", true);
				   	$j("#PhCommentsL").parent().find("span[title]").css("color", "#666666");
					 	
				    $j("#AddPhButtonToggleIdSubSection").hide();
				   	$j("#AddNewPhButtonToggleIdSubSection").show();
					$j("#UpdatePhButtonToggleIdSubSection").hide();
		}else if(tabNm == 'idenTable'){
			  	 // alert(tabNm);
				  	gBatchEntryIdenFieldsDisabled = true;	
				  	$j($j("#IdAsOf").parent().parent()).find(":input").attr("disabled", true);
				  	$j($j("#IdAsOf").parent().parent()).find("img").attr("disabled", true);
				  	$j($j("#IdAsOf").parent().parent()).find("img").attr("tabIndex", "-1");
				  	
				  	$j("#IdAsOfL").parent().find("span[title]").css("color", "#666666");
					$j($j("#IdType").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#IdType").parent().parent()).find("img").attr("disabled", true);
					$j($j("#IdType").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#IdTypeL").parent().find("span[title]").css("color", "#666666");
					
					$j($j("#IdAssgn").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#IdAssgn").parent().parent()).find("img").attr("disabled", true);
					$j($j("#IdAssgn").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#IdAssgnL").parent().find("span[title]").css("color", "#666666");
				   
				   	$j("#IdValue").parent().find(":input").attr("disabled", true);
				   	$j("#IdValueL").parent().find("span[title]").css("color", "#666666");
				   	
					 	
				    $j("#AddIdenButtonToggleIdSubSection").hide();
				   	$j("#AddNewIdenButtonToggleIdSubSection").show();
					$j("#UpdateIdenButtonToggleIdSubSection").hide();
		}else if(tabNm == 'raceTable'){
			  	 // alert(tabNm);
				  	gBatchEntryRaceFieldsDisabled = true;	
				  	$j($j("#RaceAsOf").parent().parent()).find(":input").attr("disabled", true);
				  	$j($j("#RaceAsOf").parent().parent()).find("img").attr("disabled", true);
				  	$j($j("#RaceAsOf").parent().parent()).find("img").attr("tabIndex", "-1");
				  	
				  	$j("#RaceAsOfL").parent().find("span[title]").css("color", "#666666");
					$j($j("#RaceType").parent().parent()).find(":input").attr("disabled", true);
					$j($j("#RaceType").parent().parent()).find("img").attr("disabled", true);
					$j($j("#RaceType").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#RaceTypeL").parent().find("span[title]").css("color", "#666666");
					
					//$j($j("#RaceDetailCat").parent().parent()).find(":input").attr("disabled", true);
					getElementByIdOrByName("RaceDetailCat").disabled=true;
					$j($j("#RaceDetailCat").parent().parent()).find("img").attr("disabled", true);
					$j($j("#RaceDetailCat").parent().parent()).find("img").attr("tabIndex", "-1");
					
					$j("#RaceDetailCatL").parent().find("span[title]").css("color", "#666666");
				
					 	
				    $j("#AddRaceButtonToggleIdSubSection").hide();
				   	$j("#AddNewRaceButtonToggleIdSubSection").show();
					$j("#UpdateRaceButtonToggleIdSubSection").hide();
		}
     
        }
        
       function enableBatchEntryFields (tabNm)
	{
	  	 if(tabNm == 'nameTable'){
		  	gBatchEntryFieldsDisabled = false;	
		  	$j($j("#NmAsOf").parent().parent()).find(":input").attr("disabled", false);
		  	$j($j("#NmAsOf").parent().parent()).find("img").attr("disabled", false);
		  	$j($j("#NmAsOf").parent().parent()).find("img").attr("tabIndex", "0");
		  	
		  	$j("#NmAsOfL").parent().find("span[title]").css("color", "#000");
		  	
			$j($j("#NmType").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#NmType").parent().parent()).find("img").attr("disabled", false);
			$j($j("#NmType").parent().parent()).find("img").attr("tabIndex", "0");
			
			$j("#NmTypeL").parent().find("span[title]").css("color", "#000");
			
			$j($j("#NmPrefix").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#NmPrefix").parent().parent()).find("img").attr("disabled", false);
			$j($j("#NmPrefix").parent().parent()).find("img").attr("tabIndex", "0");
			
			$j("#NmPrefixL").parent().find("span[title]").css("color", "#000");		
	      	
	        $j("#NmLast").parent().find(":input").attr("disabled", false);
	     	$j("#NmLastL").parent().find("span[title]").css("color", "#000");
	     	$j("#NmSecLast").parent().find(":input").attr("disabled", false);
	     	$j("#NmSecLastL").parent().find("span[title]").css("color", "#000");
	     	$j("#NmFirst").parent().find(":input").attr("disabled", false);
	     	$j("#NmFirstL").parent().find("span[title]").css("color", "#000");	     	
	     	$j("#NmMiddle").parent().find(":input").attr("disabled", false);
	     	$j("#NmMiddleL").parent().find("span[title]").css("color", "#000");
	     	$j("#NmSecMiddle").parent().find(":input").attr("disabled", false);
		   	$j("#NmSecMiddleL").parent().find("span[title]").css("color", "#000");
	     	
	     	$j($j("#NmSuffix").parent().parent()).find(":input").attr("disabled", false);
	     	$j($j("#NmSuffix").parent().parent()).find("img").attr("disabled", false);
	     	$j($j("#NmSuffix").parent().parent()).find("img").attr("tabIndex", "0");
			
			$j("#NmSuffixL").parent().find("span[title]").css("color", "#000");	    	 
	     	
	     	$j($j("#NmDegree").parent().parent()).find(":input").attr("disabled", false);
	     	$j($j("#NmDegree").parent().parent()).find("img").attr("disabled", false);
	     	$j($j("#NmDegree").parent().parent()).find("img").attr("tabIndex", "0");
			
			$j("#NmDegreeL").parent().find("span[title]").css("color", "#000");	     	
		     	
		     //	$j("#AddButtonToggleIdSubSection").show();
			//$j("#AddNewButtonToggleIdSubSection").hide();
			//$j("#UpdateButtonToggleIdSubSection").hide();
			clearBatchEntryFields (tabNm);
		}else if(tabNm == 'addrTable'){
		  	gBatchEntryAddrFieldsDisabled = false;	
		  	$j($j("#AddrAsOf").parent().parent()).find(":input").attr("disabled", false);
		  	$j($j("#AddrAsOf").parent().parent()).find("img").attr("disabled", false);
		  	$j($j("#AddrAsOf").parent().parent()).find("img").attr("tabIndex", "0");
		  	
				  	$j("#AddrAsOfL").parent().find("span[title]").css("color", "#000");
					$j($j("#AddrType").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#AddrType").parent().parent()).find("img").attr("disabled", false);
					$j($j("#AddrType").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#AddrTypeL").parent().find("span[title]").css("color", "#000");
					
					$j($j("#AddrUse").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#AddrUse").parent().parent()).find("img").attr("disabled", false);
					$j($j("#AddrUse").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#AddrUseL").parent().find("span[title]").css("color", "#000");
				   
				   	$j("#AddrStreet1").parent().find(":input").attr("disabled", false);
				   	$j("#AddrStreet1L").parent().find("span[title]").css("color", "#000");
				   	$j("#AddrStreet2").parent().find(":input").attr("disabled", false);
				   	$j("#AddrStreet2L").parent().find("span[title]").css("color", "#000");
				   	$j("#AddrCity").parent().find(":input").attr("disabled", false);
				   	$j("#AddrCityL").parent().find("span[title]").css("color", "#000");
				   	$j("#AddrZip").parent().find(":input").attr("disabled", false);
				   	$j("#AddrZipL").parent().find("span[title]").css("color", "#000");
				   	$j("#AddrComments").parent().find(":input").attr("disabled", false);
				   	$j("#AddrCommentsL").parent().find("span[title]").css("color", "#000");
				   	
				   	$j($j("#AddrState").parent().parent()).find(":input").attr("disabled", false);
				   	$j($j("#AddrState").parent().parent()).find("img").attr("disabled", false);
				   	$j($j("#AddrState").parent().parent()).find("img").attr("tabIndex", false);
					
					$j("#AddrStateL").parent().find("span[title]").css("color", "#000");  
				   
				   	$j($j("#AddrCnty").parent().parent()).find(":input").attr("disabled", false);
				   	$j($j("#AddrCnty").parent().parent()).find("img").attr("disabled", false);
				   	$j($j("#AddrCnty").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#AddrCntyL").parent().find("span[title]").css("color", "#000");
					$j($j("#AddrCensusTract").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#AddrCensusTract").parent().parent()).find("img").attr("disabled", false);
					$j($j("#AddrCensusTract").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#AddrCensusTractL").parent().find("span[title]").css("color", "#000");  
					$j($j("#AddrCntry").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#AddrCntry").parent().parent()).find("img").attr("disabled", false);
					$j($j("#AddrCntry").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#AddrCntryL").parent().find("span[title]").css("color", "#000"); 
							     	
		     //	$j("#AddButtonToggleIdSubSection").show();
			//$j("#AddNewButtonToggleIdSubSection").hide();
			//$j("#UpdateButtonToggleIdSubSection").hide();
			clearBatchEntryFields (tabNm);
		}else if(tabNm == 'phoneTable'){
			  	 // alert(tabNm);
				  	gBatchEntryPhFieldsDisabled = false;	
				  	$j($j("#PhAsOf").parent().parent()).find(":input").attr("disabled", false);
				  	$j($j("#PhAsOf").parent().parent()).find("img").attr("disabled", false);
				  	$j($j("#PhAsOf").parent().parent()).find("img").attr("tabIndex", "0");
				  	
				  	$j("#PhAsOfL").parent().find("span[title]").css("color", "#000");
					$j($j("#PhType").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#PhType").parent().parent()).find("img").attr("disabled", false);
					$j($j("#PhType").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#PhTypeL").parent().find("span[title]").css("color", "#000");
					
					$j($j("#PhUse").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#PhUse").parent().parent()).find("img").attr("disabled", false);
					$j($j("#PhUse").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#PhUseL").parent().find("span[title]").css("color", "#000");
				   
				   	$j("#PhCntryCd").parent().find(":input").attr("disabled", false);
				   	$j("#PhCntryCdL").parent().find("span[title]").css("color", "#000");
				   	$j("#PhNum").parent().find(":input").attr("disabled", false);
				   	$j("#PhNumL").parent().find("span[title]").css("color", "#000");
				   	$j("#PhExt").parent().find(":input").attr("disabled", false);
				   	$j("#PhExtL").parent().find("span[title]").css("color", "#000");
				   	$j("#PhEmail").parent().find(":input").attr("disabled", false);
				   	$j("#PhEmailL").parent().find("span[title]").css("color", "#000");
				   	$j("#PhUrl").parent().find(":input").attr("disabled", false);
				   	$j("#PhUrlL").parent().find("span[title]").css("color", "#000");			   
				   	$j("#PhComments").parent().find(":input").attr("disabled", false);
				   	$j("#PhCommentsL").parent().find("span[title]").css("color", "#000");
					 	
				   clearBatchEntryFields (tabNm);
		}else if(tabNm == 'idenTable'){
			  	 // alert(tabNm);
				  	gBatchEntryIdenFieldsDisabled = false;	
				  	$j($j("#IdAsOf").parent().parent()).find(":input").attr("disabled", false);
				  	$j($j("#IdAsOf").parent().parent()).find("img").attr("disabled", false);
				  	$j($j("#IdAsOf").parent().parent()).find("img").attr("tabIndex", "0");
				  	
				  	$j("#IdAsOfL").parent().find("span[title]").css("color", "#000");
					$j($j("#IdType").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#IdType").parent().parent()).find("img").attr("disabled", false);
					$j($j("#IdType").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#IdTypeL").parent().find("span[title]").css("color", "#000");
					
					$j($j("#IdAssgn").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#IdAssgn").parent().parent()).find("img").attr("disabled", false);
					$j($j("#IdAssgn").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#IdAssgnL").parent().find("span[title]").css("color", "#000");
				   
				   	$j("#IdValue").parent().find(":input").attr("disabled", false);
				   	$j("#IdValueL").parent().find("span[title]").css("color", "#000");
					 	
				   clearBatchEntryFields (tabNm);
		}else if(tabNm == 'raceTable'){
			  	 // alert(tabNm);
				  	gBatchEntryRaceFieldsDisabled = false;	
				  	$j($j("#RaceAsOf").parent().parent()).find(":input").attr("disabled", false);
				  	$j($j("#RaceAsOf").parent().parent()).find("img").attr("disabled", false);
				  	$j($j("#RaceAsOf").parent().parent()).find("img").attr("tabIndex", "0");
				  	
				  	$j("#RaceAsOfL").parent().find("span[title]").css("color", "#000");
					$j($j("#RaceType").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#RaceType").parent().parent()).find("img").attr("disabled", false);
					$j($j("#RaceType").parent().parent()).find("img").attr("tabIndex", "0");
					
					$j("#RaceTypeL").parent().find("span[title]").css("color", "#000");
					
					$j($j("#RaceDetailCat").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#RaceDetailCat").parent().parent()).find("img").attr("tabIndex", "0");
			
					$j("#RaceDetailCatL").parent().find("span[title]").css("color", "#000");
				 
					 	
				   clearBatchEntryFields (tabNm);
		}   
		
	  }
        
       function clearBatchEntryFields (tabNm)
  	  {
  	      if(tabNm == 'nameTable'){
		  	      gBatchEntryUpdateSeq = "";
		  		  $j($j("#NmAsOf").parent().parent()).find(":input").val("");
				  $j($j("#NmType").parent().parent()).find(":input").val("");
				  $j($j("#NmPrefix").parent().parent()).find(":input").val("");     	 
		     	  $j($j("#NmLast").parent().parent()).find(":input").val("");
				  $j($j("#NmSecLast").parent().parent()).find(":input").val("");
				  $j($j("#NmFirst").parent().parent()).find(":input").val("");
		     	  //$j("#NmFirst").parent().find(":input").val("");
		     	  $j($j("#NmMiddle").parent().parent()).find(":input").val("");
				  $j($j("#NmSecMiddle").parent().parent()).find(":input").val("");
				  $j($j("#NmSuffix").parent().parent()).find(":input").val("");
				   // $j("#NmSuffix").parent().find(":input").val("");
				  $j($j("#NmDegree").parent().parent()).find(":input").val("");
		     	   // $j("#NmDegree").parent().find(":input").val("");
		     	  $j($j("#NmAsOf").parent().parent()).find(":input").focus();  	      
  	      }else if(tabNm == 'addrTable'){
		  	      gBatchEntryAddrUpdateSeq = "";
		  		  $j($j("#AddrAsOf").parent().parent()).find(":input").val("");
				  $j($j("#AddrType").parent().parent()).find(":input").val("");
				  $j($j("#AddrUse").parent().parent()).find(":input").val("");     	 
		     	  $j($j("#AddrStreet1").parent().parent()).find(":input").val("");
				  $j($j("#AddrStreet2").parent().parent()).find(":input").val("");
				  $j($j("#AddrCity").parent().parent()).find(":input").val("");
				   $j($j("#AddrZip").parent().parent()).find(":input").val("");
		     	  //$j("#AddrZip").parent().find(":input").val("");
		     	  $j($j("#AddrState").parent().parent()).find(":input").val("");
				  $j($j("#AddrCnty").parent().parent()).find(":input").val("");
				  $j($j("#AddrCensusTract").parent().parent()).find(":input").val("");
				  $j($j("#AddrCntry").parent().parent()).find(":input").val("");
				   // $j("#NmSuffix").parent().find(":input").val("");
				  $j($j("#AddrComments").parent().parent()).find(":input").val("");
		     	   // $j("#NmDegree").parent().find(":input").val("");
		     	  $j($j("#AddrAsOf").parent().parent()).find(":input").focus();  	      
  	      }else if(tabNm == 'phoneTable'){
		  	      gBatchEntryPhUpdateSeq = "";
		  		  $j($j("#PhAsOf").parent().parent()).find(":input").val("");
				  $j($j("#PhType").parent().parent()).find(":input").val("");
				  $j($j("#PhUse").parent().parent()).find(":input").val("");     	 
		     	           $j($j("#PhCntryCd").parent().parent()).find(":input").val("");
				  $j($j("#PhNum").parent().parent()).find(":input").val("");
				  $j($j("#PhExt").parent().parent()).find(":input").val("");
		     	          $j("#PhEmail").parent().find(":input").val("");
		     	          $j($j("#PhUrl").parent().parent()).find(":input").val("");
				  $j($j("#PhComments").parent().parent()).find(":input").val("");
				 
		     	          $j($j("#PhAsOf").parent().parent()).find(":input").focus();  	      
  	      }else if(tabNm == 'idenTable'){
		  	      gBatchEntryIdenUpdateSeq = "";
		  		  $j($j("#IdAsOf").parent().parent()).find(":input").val("");
				  $j($j("#IdType").parent().parent()).find(":input").val("");
				  $j($j("#IdAssgn").parent().parent()).find(":input").val("");     	 
		     	 $j($j("#IdValue").parent().parent()).find(":input").val("");	 
		         $j($j("#IdAsOf").parent().parent()).find(":input").focus();  	      
  	      }else if(tabNm == 'raceTable'){
		  	      gBatchEntryRaceUpdateSeq = "";
		  		  $j($j("#RaceAsOf").parent().parent()).find(":input").val("");
				  $j($j("#RaceType").parent().parent()).find(":input").val("");
				  $j($j("#RaceDetailCat").parent().parent()).find(":input").val(""); 
				   dwr.util.setValue("RaceDetailCat-selectedValues", "");
				    $j("#RaceDetailCat-selectedValues").val("");  
					 
		     	     
  	      }					
     		
        } 
        
       function clearBatchIdEntryFields(tabNm) 
       {
           if(tabNm == 'nameTable'){
	             enableBatchEntryFields(tabNm);
	       		clearBatchEntryFields(tabNm);
	       		$j("#AddButtonToggleIdSubSection").show();
	       		$j("#AddNewButtonToggleIdSubSection").hide();
		     	$j("#UpdateButtonToggleIdSubSection").hide();        
           
           } else if(tabNm == 'addrTable'){
	             enableBatchEntryFields(tabNm);
	       		clearBatchEntryFields(tabNm);
	       		$j("#AddAddrButtonToggleIdSubSection").show();
	       		$j("#AddNewAddrButtonToggleIdSubSection").hide();
		     	$j("#UpdateAddrButtonToggleIdSubSection").hide();        
           
           } else if(tabNm == 'phoneTable'){
	             enableBatchEntryFields(tabNm);
	       		clearBatchEntryFields(tabNm);
	       		$j("#AddPhButtonToggleIdSubSection").show();
	       		$j("#AddNewPhButtonToggleIdSubSection").hide();
		     	$j("#UpdatePhButtonToggleIdSubSection").hide();        
           
           }else if(tabNm == 'idenTable'){
	             enableBatchEntryFields(tabNm);
	       		clearBatchEntryFields(tabNm);
	       		$j("#AddIdenButtonToggleIdSubSection").show();
	       		$j("#AddNewIdenButtonToggleIdSubSection").hide();
		     	$j("#UpdateIdenButtonToggleIdSubSection").hide();        
           
           }else if(tabNm == 'raceTable'){
	             enableBatchEntryFields(tabNm);
	       		clearBatchEntryFields(tabNm);
	       		$j("#AddRaceButtonToggleIdSubSection").show();
	       		$j("#AddNewRaceButtonToggleIdSubSection").hide();
		     	$j("#UpdateRaceButtonToggleIdSubSection").hide();        
           
           }
           
           
			 if(tabNm == 'nameTable'){
	    	    var t = getElementByIdOrByName("patSearch6");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	          
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'addrTable'){
	    	    var t = getElementByIdOrByName("patSearch8");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 7; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'phoneTable'){
	    	    var t = getElementByIdOrByName("patSearch10");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 6; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'idenTable'){
	    	    var t = getElementByIdOrByName("patSearch12");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 4; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}else if(tabNm == 'raceTable'){
	    	    var t = getElementByIdOrByName("patSearch14");
	       	 	//t.style.display = "block";
	          	 for (var i = 0; i < 3; i++){
	       	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","white");
	       	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","white");
	        	}	
        	}
       		
	   }
	   
	   function showHideSpanishOrigin(){
	     $j('#SpanOriginTR').hide();
	     if($j('#INV2002').attr('value') != "" && $j('#INV2002').attr('value') == "2135-2"){
	       $j('#SpanOriginTR').show();
	       $j('#SpanOrigin').focus();	
	       displaySelectedOptions(getElementByIdOrByName("SpanOrigin"), 'SpanOrigin-selectedValues');      
	     }
	   }
          
     function displayCalcAge() {

	var dobNode = getElementByIdOrByName("patientDOB");
	var serverDate = getElementByIdOrByName("today") == null ? "" : getElementByIdOrByName("today").value;
	
	var calcDOBNode = getElementByIdOrByName("calcDOB");
	var reportedAgeNode = getElementByIdOrByName("hiddenReportedAge");
	var reportedAgeUnit = getElementByIdOrByName("hiddenReportedAgeUnit");
		
	var currentAgeNode = getElementByIdOrByName("currentAge");	
	var currentAgeUnitsNode = getElementByIdOrByName("currentAgeUnits");
	
	var currentAgeFileSummaryNode = getElementByIdOrByName("currentAgeFileSummary");	
	var currentAgeUnitsFileSummaryNode = getElementByIdOrByName("currentAgeUnitsFileSummary");
	       
         //alert("displayCalcAge dobNode " + dobNode.value + "  serverDate : " + serverDate.value + " calcDOBNode : " + calcDOBNode.value   );

		if(dobNode != null &&  (dobNode.value.length > 0) && (isDate(dobNode.value))){	
			calcDOBNode.value = dobNode.value;
		} 

	
	//figure out the current age and units, don't show if calc dob is empty

	if(calcDOBNode.value.length > 0)	{

		//alert('calc dob node is not empty');		
		
		var asOfDate = new Date(serverDate);
		var calcDOBDate = new Date(calcDOBNode.value);
		

		var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
		//alert('reportedAgeMilliSec' + reportedAgeMilliSec);
		
		
		if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec > 0 && CompareDateStrings(calcDOBNode.value, "12/31/1875") != -1){
			
			
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30.41; 
			var reportedAgeYears = reportedAgeMonths/12;
			
			if(isLeapYear(calcDOBDate.getFullYear())) reportedAgeMonths = Math.floor(reportedAgeDays)/30.5;

               //alert("age" + reportedAgeMonths  + "   " + reportedAgeDays + "   " + reportedAgeYears );
			if(Math.ceil(reportedAgeDays)<=28){
				currentAgeNode.innerText=Math.floor(reportedAgeDays);
				currentAgeUnitsNode.innerText="Days";
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=Math.floor(reportedAgeDays);
					currentAgeUnitsFileSummaryNode.innerText="Days";
				}
				
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
				// As per lisa if days are  greater than 28 and lessthan 31 it should be one month 
				if(Math.ceil(reportedAgeDays) > 28 && Math.ceil(reportedAgeDays) < 31)
				reportedAgeMonths = reportedAgeMonths + 1;
				
				currentAgeNode.innerText=Math.floor(reportedAgeMonths);
				currentAgeUnitsNode.innerText="Months";	
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=Math.floor(reportedAgeMonths);
					currentAgeUnitsFileSummaryNode.innerText="Months";
				}
				
			} else	{
				// get rough estimated year age
				var yearDiff = asOfDate.getFullYear() - calcDOBDate.getFullYear();
				//need to determine whether birthday has happened 
				if(asOfDate.getMonth()<calcDOBDate.getMonth())
					yearDiff = yearDiff-1;
				else if(asOfDate.getMonth()==calcDOBDate.getMonth()){
					
					if(asOfDate.getDate()<calcDOBDate.getDate())
						yearDiff = yearDiff-1;
				}
				currentAgeNode.innerText=yearDiff;//Math.floor(reportedAgeYears);
				currentAgeUnitsNode.innerText="Years";
				if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText=yearDiff;
					currentAgeUnitsFileSummaryNode.innerText="Years";
				}
                    
                    //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
				if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && reportedAgeYears > 1 && reportedAgeYears < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
				{
				    currentAgeNode.innerText="11";
				    currentAgeUnitsNode.innerText="Months";
				    if(currentAgeFileSummaryNode!=null){
						currentAgeFileSummaryNode.innerText="11";
						currentAgeUnitsFileSummaryNode.innerText="Months";
					}
				}				

			}
					
		} else {
			
			currentAgeNode.innerText="";
			currentAgeUnitsNode.innerText="";
		    if(!window.isNaN(reportedAgeMilliSec) && reportedAgeMilliSec == 0)
		    {
		      	currentAgeNode.innerText="0";
		      	currentAgeUnitsNode.innerText="Days";	
		      	if(currentAgeFileSummaryNode!=null){
					currentAgeFileSummaryNode.innerText="0";
					currentAgeUnitsFileSummaryNode.innerText="Days";
				}
		    }			
			
		}
	}

} //diaplayCalcAge()
   
   function SubmitPatient(){
    if(!saveExtendedAODs()){
    if(ValidateNonBatchDateFields()){
    if(validateBatchData()){
     blockUIDuringFormSubmission(); 	
     document.DSPersonForm.action ='${fn:escapeXml(formHref)}?ContextAction=Submit<%=vo.getThePersonDT().getPersonUid()==null?"":"&personUID="+vo.getThePersonDT().getPersonUid()%>';
     document.DSPersonForm.submit();
     return true; 
     }else{
     return false; 
      }
     }
    } 
   } 
   
  
   
  
   
   //when the State is changed, update the counties
    function getDWRPatientCounties(state, elementId)
    {
    	var state1 = state.value;
    	if(state1 == null)  state1= state;
        if(elementId == 'BirCnty'){
	     	JDSPersonForm.getDwrBirthCountiesForState(state1, function(data) {
		        DWRUtil.removeAllOptions(elementId);
		        getElementByIdOrByName(elementId + "_textbox").value="";
		        DWRUtil.addOptions(elementId, data, "key", "value" );
	      	});  
      	}else if(elementId == 'DeathCnty'){
	     	JDSPersonForm.getDwrDeathCountiesForState(state1, function(data) {
		        DWRUtil.removeAllOptions(elementId);
		        getElementByIdOrByName(elementId + "_textbox").value="";
		        DWRUtil.addOptions(elementId, data, "key", "value" );
	      	});  
      	}else{
      	   JDSPersonForm.getDwrCountiesForState(state1, function(data) {
	        DWRUtil.removeAllOptions(elementId);
	        getElementByIdOrByName(elementId + "_textbox").value="";
	        DWRUtil.addOptions(elementId, data, "key", "value" );
      	}); 
      	
      	}

     }
     
     function loadCounties(){
       if(getElementByIdOrByName("BirState") != null && getElementByIdOrByName("BirState").value != ''){
         getDWRPatientCounties(getElementByIdOrByName("BirState").value,'BirCnty');
        }
       if(getElementByIdOrByName("DeathState") != null && getElementByIdOrByName("DeathState").value != ''){
          getDWRPatientCounties(getElementByIdOrByName("DeathState").value,'DeathCnty');
       }
     
     }
     
     
     
     //when the Race Type is changed, update the race details
    function getDWRSubRaces(raceType, elementId)
    {
    	var type = raceType.value;
    	//alert(type);
    	if(type == null)  type= raceType;
    
     	JDSPersonForm.getSubRacesForType(type, function(data) {
	        DWRUtil.removeAllOptions(elementId);
	      //  getElementByIdOrByName(elementId + "_textbox").value="";
	        DWRUtil.addOptions(elementId, data, "key", "value" );
      	});  

     }
     function cancelEdit(){
	    document.DSPersonForm.action ="${fn:escapeXml(cancelButtonHref)}";
	    document.DSPersonForm.submit();
	    return true;  
	 }  
   
  
       
	     </script>
	<!-- ################### PAGE TAB ###################### - - -->
	  <style type="text/css">
	  table.bluebarsectionsToggler, table.bluebarsubSectionsToggler, table.bluebarsubSect {width:100%; margin:0 auto; border-width:0px; margin-top:0.0em; border-spacing:0px;}
	  table.bluebardtTable, table.bluebarprivateDtTable {width:100%; border:1px solid #666666; padding:0.0em; margin:0em auto; margin-top:0em;}
	   
	  </style>
	
	<%
	String tabId = "editCaseInfo1";
	tabId = tabId.replace("]","");
	tabId = tabId.replace("[","");
	tabId = tabId.replaceAll(" ", "");
	int subSectionIndex = 0;
	int sectionIndex = 0;
	String sectionId = "";
	String [] sectionNames  = {"Patient Summary","Reporting Information","Clinical","Epidemiologic","General Comments"};
	
	
	%>
	  
	<tr><td width="100%">
	    <div class="view" id="patDemo" >
         	<div class="showup" style="text-align:right;">
			      		   <a  href="javascript:toggleAllSectionsDisplayWorkup('patDemo',2)"/><font class="hyperLink"> Expand All</font></a>
			      		   | <a  href="javascript:toggleAllSectionsDisplayWorkup('patDemo',1)"/><font class="hyperLink"> Collapse All</font></a>
					 </div>
				<div> 
			         <table role="presentation"><tr><td height="05 px"></td></tr></table>  </div>
         <table role="presentation" width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			
			<tr bgcolor="#003470">
				<td height="25 px" valign="center" align="left"><font class="boldTwelveYellow">
				&nbsp;Patient Demographics</font>
				</td>
				<td align="right">
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2"></td>
			</tr>
	     </table>
	     <!-- Page Errors -->
          <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
		  <table role="presentation" width="100%"  border="0" align="center">
		 <tr>
		 <td width="100%">
		 <div class="infoBox errors" style="display: none;visibility: none;" id="PatientSubmitErrorMessages">
		 <b> <a name="PatientSubmitErrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
		 </div>
		 </td>
		 </tr></table>
	
	 <table role="presentation" class="sectionsToggler" style="width:100%;">
        <tr>
            <td>
                <ul class="horizontalList">
                    <li style="margin-right:5px;"><b>Go to: </b></li>                   
                    <li><a href="javascript:gotoSectionWorkup('patSearch3')">Administrative</a> </li>  
                      <li class="delimiter"> | </li>                 
                    <li><a href="javascript:gotoSectionWorkup('patSearch5')">Name</a> </li>
                      <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch7')">Address</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch9')">Phone & Email</a> </li>
                      <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch11')">Identification</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch13')">Race</a> </li>
                     <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch15')">Ethnicity</a> </li>
                      <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch17')">Sex & Birth</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch19')">Mortality</a> </li>
                     <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('patSearch21')">General Patient Information</a> </li>
                    
                     <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('LocalFieldsSection')">Custom Fields</a> </li>
                    <%}%>
                 </ul>
            </td>
        </tr>
        <tr>
            <td>
                <a class="toggleHref"></a>
            </td>
        </tr>
    </table>	
           <div> 
         <table role="presentation"><tr><td height="05 px"></td></tr></table></div>
	<div class="view" id="patientSearchByDetails" style="text-align:center;">
	
	<%  sectionIndex = 0; %>
		                    
			                    
			                        <nedss:bluebar id="patSearch3" name="Administrative" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch4" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                         <!--processing Date Question-->
									<tr><td class="fieldName">									
									<span class="InputFieldLabel" id="AsofDateAdminL" title="As of Date is the last known date for which the information is valid.">
									Administrative Information As of:</span>
									</td>
									<td>
									<html:text  name="DSPersonForm" styleClass="requiredInputField" title="Administrative Information As of" property="person.thePersonDT.asOfDateAdmin_s" maxlength="10" size="10" styleId="AsofDateAdmin" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AsofDateAdmin','AsofDateAdminIcon');return false;" onkeypress ="showCalendarEnterKey('AsofDateAdmin','AsofDateAdminIcon',event);" styleId="AsofDateAdminIcon"></html:img>
									</td> </tr>                  
			                         <tr> <td class="fieldName">
									<span title="General comments pertaining to the patient." id="GenCommentsL">
									General Comments:</span>
									</td>
									<td>
									<html:textarea title="Administrative General Comments" style="WIDTH: 500px; HEIGHT: 100px;" name="DSPersonForm" property="person.thePersonDT.description" styleId ="GenComments" onkeyup="checkTextAreaLength(this, 2000)"/>
									</td> </tr>     
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                  
			                    
			                     <nedss:bluebar id="patSearch5" name="Name" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch6" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" > 
			                    		          
			                      <tr> <td colspan="2" width="100%">	
			                       <div class="infoBox errors" style="display: none;visibility: none;" id="NameerrorMessages">
									 <b> <a name="NameerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
									 </div>										
									<table class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<td width="10%" style="background-color: #EFEFEF;border:1px solid #666666">  </td>
									<th width="15%"><b> As of </b> </th>
									<th width="15%"><b> Type </b></th>
									<th width="15%"><b> Last </b></th>
									<th width="15%"><b> First </b></th>
									<th width="15%"> <b>Middle </b></th>
									<th width="15%"> <b>Suffix </b> </th>
									</tr>							
									</thead>
									<tbody id="patternnameTable">
									        <tr id="patternnameTableRow" class="odd" style="display:none">
								            <td> 
								            <table role="presentation" width="100%">
								            <tr>
								            <td style="width:33%;text-align:center;">
											 <input id="viewIdSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'nameTable');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
											 </td><td style="width:33%;text-align:center;">
											 <input id="editIdSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'nameTable');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
											 </td><td style="width:33%;text-align:center;">
											 <input id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'nameTable');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
 											</td>	
 											</tr>
 											</table>
 											</td>
 															           
									            <td id="tablenameDate">
									            &nbsp;
									            </td>
									            <td id="tablenameTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tablenameLast">								            
									            &nbsp; 
									            </td >
									            <td id="tablenameFirst">
									             &nbsp;							            
									            </td >
									            <td id="tablenameMiddle">
									            &nbsp;								          
									            </td >
									            <td id="tablenameSuffixDesc">
									            &nbsp;								            
									            </td>									
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternnameTable">
									 <tr id="nopatternnameTableRow" class="odd">
									 <td colspan="21"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									
									      <tr>
									      <td class="fieldName" width="100%"> 
									      <span class=" InputFieldLabel" id="NmAsOfL" title="Name as of."> Name As of:
									      </span> </td>
									      <td id="nameDate" width="100%"> 
									         <html:text  name="DSPersonForm" styleClass="requiredInputField" title="Name As of" property="nmAsOf" maxlength="10" size="10" styleId="NmAsOf"  onkeyup="unhideBatchImg('nameTable');DateMask(this,null,event)"/>
									         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NmAsOf','NmAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('NmAsOf','NmAsOfIcon',event);" styleId="NmAsOfIcon"></html:img> 
									      </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="NmTypeL" title="Type of Name">  Type:</span> </td>
									      <td id="nameType"> 
									      <html:select title="The patient's name type" name="DSPersonForm" property="nmType" styleId="NmType" onchange ="unhideBatchImg('nameTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(P_NM_USE)" value="key" label="value" />
 	                                       </html:select> </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmPrefixL" title="Name Prefix"> Prefix: </span></td>
									      <td id="namePrefix"> 
									       <html:select title="The patient's prefix" name="DSPersonForm" property="nmPrefix" styleId="NmPrefix" onchange ="unhideBatchImg('nameTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(P_NM_PFX)" value="key" label="value" />
 	                                        </html:select> </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmLastL" title="Last Name "> Last:</span> </td>
									      <td id="nameLast"> <html:text title="The patient's last name" name="DSPersonForm" property="nmLast" styleId="NmLast" onkeyup ="unhideBatchImg('nameTable');" size="50" maxlength="50"/>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="NmSecLastL" title="Second Last Name ">  Second Last:</span> </td>
									      <td id="nameSecLast"> <html:text title="The patient's second name" name="DSPersonForm" property="nmSecLast" onkeyup ="unhideBatchImg('nameTable');" styleId="NmSecLast" size="50" maxlength="50"/>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmFirstL" title="First Name "> First:</span> </td>
									      <td id="nameFirst"> <html:text title="The patient's first name" name="DSPersonForm" property="nmFirst" onkeyup ="unhideBatchImg('nameTable');"  styleId="NmFirst" size="50" maxlength="50"/>  </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmMiddleL" title="Middle Name "> Middle:</span> </td>
									      <td id="nameMiddle"> <html:text  title="The patient's middle name" name="DSPersonForm" property="nmMiddle" onkeyup ="unhideBatchImg('nameTable');" styleId="NmMiddle" size="50" maxlength="50"/>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmSecMiddleL" title="Second Middle Name "> Second Middle: </span></td>
									      <td id="nameSecMiddle"> <html:text  title="The patient's second middle name" name="DSPersonForm" property="nmSecMiddle" onkeyup ="unhideBatchImg('nameTable');" styleId="NmSecMiddle" size="50" maxlength="50"/> </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmSuffixL" title="Name Suffix "> Suffix: </span></td>
									      <td id="nameSuffix"> 
									      <html:select name="DSPersonForm"  title="The patient's suffix"  property="nmSuffix" styleId="NmSuffix" onchange ="unhideBatchImg('nameTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(P_NM_SFX)" value="key" label="value" />
 	                                        </html:select> </td>	
									      </tr>
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="NmDegreeL" title="Name Degree "> Degree: </span></td>
									      <td id="nameDegree">
									        <html:select  title="The patient's degree"  name="DSPersonForm" property="nmDegree" styleId="NmDegree" onchange ="unhideBatchImg('nameTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(P_NM_DEG)" value="key" label="value" />
 	                                        </html:select>
 	                                         </td>	
									      </tr>
									      <tr id="AddButtonToggleIdSubSection">
											<td colspan="2" align="right">
											
											<input type="button" value="     Add      "  onclick="if (NameBatchValidateFunction()){addClicked('nameTable');return false}"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
										
											<tr id="UpdateButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Update   "    onclick="if (NameBatchValidateFunction())updateBatchIdEntry('nameTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										
											<tr id="AddNewButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Add New   "    onclick="clearBatchIdEntryFields('nameTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
									     
									      
									                                    
	                             </nedss:bluebar>
			                    </nedss:bluebar>	
			                    
			                    <nedss:bluebar id="patSearch7" name="Address" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch8" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                     
			                     <tr> <td colspan="2" width="100%">	
			                      <div class="infoBox errors" style="display: none;visibility: none;" id="AddrerrorMessages">
									 <b> <a name="AddrerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
									 </div>									
									<table class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<td width="10%" style="background-color: #EFEFEF;border:1px solid #666666">  </td>
									<th width="15%"><b> As of </b> </th>
									<th width="15%"><b> Type </b></th>
									<th width="15%"><b> Address </b></th>
									<th width="15%"><b> City </b></th>
									<th width="15%"> <b>State </b></th>
									<th width="15%"> <b>Zip </b></th>			
									</tr>							
									</thead>
									<tbody id="patternaddrTable">
									        <tr id="patternaddrTableRow" class="odd" style="display:none">
								            <td> 
								            <table role="presentation" width="100%">
								            <tr>
								            <td style="width:33%;text-align:center;">
											 <input id="viewIdSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'addrTable');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
											 </td><td style="width:33%;text-align:center;">
											 <input id="editIdSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'addrTable');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
											 </td><td style="width:33%;text-align:center;">
											 <input id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'addrTable');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
 											</td>	
 											</tr>
 											</table>
 											</td>
 															           
									            <td id="tableaddrNameDate">
									            &nbsp;
									            </td>
									            <td id="tableaddrTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tableaddrAddress">								            
									            &nbsp; 
									            </td >
									            <td id="tableaddrCity">
									             &nbsp;							            
									            </td >
									            <td id="tableaddrStateDesc">
									            &nbsp;								          
									            </td >
									            <td id="tableaddrZip">
									            &nbsp;								            
									            </td>									
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternaddrTable">
									 <tr id="nopatternaddrTableRow" class="odd">
									 <td colspan="21"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="AddrAsOfL" title="Address as of Date ">  Address As of: </span></td>
									      <td id="addressNameDate" width="100%">  <html:text  title="Address As of" name="DSPersonForm" styleClass="requiredInputField" property="addrAsOf" maxlength="10" size="10"  styleId="AddrAsOf" onkeyup="unhideBatchImg('addrTable');DateMask(this,null,event)"/>
									         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AddrAsOf','AddrAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('AddrAsOf','AddrAsOfIcon',event);" styleId="AddrAsOfIcon"></html:img>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="AddrTypeL" title="Address Type">  Type: </span>  </td>
									      <td id="addressType">  <html:select title="Address Type" name="DSPersonForm" property="addrType" styleId="AddrType" onchange ="unhideBatchImg('addrTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(EL_TYPE_PST_PAT)" value="key" label="value" />
 	                                       </html:select>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrUseL" title="Address Type"> Use: </span></td>
									      <td id="addressUse">  <html:select title="Address Use" name="DSPersonForm" property="addrUse" styleId="AddrUse" onchange ="unhideBatchImg('addrTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(EL_USE_PST_PAT)" value="key" label="value" />
 	                                       </html:select>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrStreet1L" title="Street Address 1 "> Street Address 1: </span></td>
									      <td id="addressStr1"> <html:text title="Line one of the address label." name="DSPersonForm" property="addrStreet1" onkeyup ="unhideBatchImg('addrTable');" styleId="AddrStreet1" size="30" maxlength="100"/>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrStreet2L" title="Street Address 2 "> Street Address 2: </span></td>
									      <td id="addressStr2"> <html:text title="Line two of the address label." name="DSPersonForm" property="addrStreet2" onkeyup ="unhideBatchImg('addrTable');" styleId="AddrStreet2" size="30" maxlength="100"/>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCityL" title="City "> City: </span></td>
									      <td id="addressCity"> <html:text title="The city for a postal location." name="DSPersonForm" property="addrCity" onkeyup ="unhideBatchImg('addrTable');" styleId="AddrCity" size="30" maxlength="100" />   </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrStateL" title="State">  State: </span> </td>
									      <td id="addressState"> <html:select title="The state for a postal location" name="DSPersonForm" property="addrState" styleId="AddrState" onchange="unhideBatchImg('addrTable');getDWRPatientCounties(this, 'AddrCnty');">
 	                                       <html:optionsCollection name="DSPersonForm" property="stateList" value="key" label="value" />
 	                                       </html:select>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrZipL" title="ZipCode ">  Zip: </span></td>
									      <td id="addressZip"> <html:text name="DSPersonForm" property="addrZip" styleId="AddrZip"  size="10" maxlength="10" 
									       title="The zip code of a residence of the case patient or entity." onkeyup="ZipMask(this,event);unhideBatchImg('addrTable');" />   </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCntyL" title="County "> County: </span> </td>
									      <td id="addressCounty"> <html:select title="The county for a postal location" name="DSPersonForm" property="addrCnty" styleId="AddrCnty" onchange ="unhideBatchImg('addrTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="dwrCounties" value="key" label="value" />
 	                                       </html:select>   </td>	
									      </tr>
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCensusTractL" title="Census Tract "> Census Tract: </span></td>
									      <td id="addressCensusTract"> <html:text title="The Census Tract for a postal location" name="DSPersonForm" property="addrCensusTract" onkeyup ="unhideBatchImg('addrTable');" styleId="AddrCensusTract" size="15" maxlength="100" />   </td>	
									      </tr>
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCntryL" title="Country "> Country: </span> </td>
									      <td id="addressCountry"> <html:select title="The Country for a postal location" name="DSPersonForm" property="addrCntry" styleId="AddrCntry" onchange ="unhideBatchImg('addrTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="countryList" value="key" label="value" />
 	                                       </html:select>    </td>	
									      </tr>
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="AddrCommentsL" title="Comments "> Address Comments: </span> </td>
									      <td id="addressComments"> <html:textarea title="The address comments for a postal location" style="WIDTH: 500px; HEIGHT: 100px;" name="DSPersonForm" 
									      property="addrComments" styleId ="AddrComments" onkeyup="checkTextAreaLength(this, 2000);unhideBatchImg('addrTable');"/>  </td>	
									      </tr>
									      <tr id="AddAddrButtonToggleIdSubSection">
											<td colspan="2" align="right">
											<input type="button" value="     Add      "  onclick="if (AddrBatchValidateFunction()){addClicked('addrTable');return false}"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
											<tr id="UpdateAddrButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Update   "    onclick="if (AddrBatchValidateFunction())updateBatchIdEntry('addrTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										
											<tr id="AddNewAddrButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Add New   "    onclick="clearBatchIdEntryFields('addrTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>	     
			                     
			                    
                                  </nedss:bluebar>
			                    </nedss:bluebar>
			                    
			                     <nedss:bluebar id="patSearch9" name="Phone & Email" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                    <nedss:bluebar id="patSearch10" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                      <tr> <td colspan="2" width="100%">
			                       <div class="infoBox errors" style="display: none;visibility: none;" id="PhoneerrorMessages">
									 <b> <a name="PhoneerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
									 </div>											
									<table class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<td width="10%" style="background-color: #EFEFEF;border:1px solid #666666">  </td>
									<th width="15%"><b> As of </b> </th>
									<th width="15%"><b> Type </b></th>
									<th width="15%"><b> Phone Number </b></th>
									<th width="15%"><b> Email Address </b></th>
									<th width="30%"> <b>Comments </b></th>				
									</tr>							
									</thead>
									<tbody id="patternphoneTable">
									        <tr id="patternphoneTableRow" class="odd" style="display:none">
								            <td> 
								            <table role="presentation" width="100%">
								            <tr>
								            <td style="width:33%;text-align:center;">
											 <input id="viewIdSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'phoneTable');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
											 </td><td style="width:33%;text-align:center;">
											 <input id="editIdSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'phoneTable');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
											 </td><td style="width:33%;text-align:center;">
											 <input id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'phoneTable');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
 											</td>	
 											</tr>
 											</table>
 											</td> 															           
									            <td id="tablephDate">
									            &nbsp;
									            </td>
									            <td id="tablephTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tablephNum">								            
									            &nbsp; 
									            </td >
									            <td id="tablephEmail">
									             &nbsp;							            
									            </td >
									            <td id="tablephComments">
									            &nbsp;								          
									            </td >									           								
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternphoneTable">
									 <tr id="nopatternphoneTableRow" class="odd">
									 <td colspan="21"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="PhAsOfL" title="Phone as of Date ">  Phone & Email As of: </span></td>
									      <td id="phoneNameDate" width="100%">  <html:text  name="DSPersonForm" title="Phone & Email As of" styleClass="requiredInputField" property="phAsOf" maxlength="10" size="10" styleId="PhAsOf" onkeyup="DateMask(this,null,event)"/>
									         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('PhAsOf','PhAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('PhAsOf','PhAsOfIcon',event);" styleId="PhAsOfIcon"></html:img>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="PhTypeL" title="Phone Type">  Type: </span>  </td>
									      <td id="phoneType">  <html:select title="Phone & Email Type" name="DSPersonForm" property="phType" styleId="PhType" onchange ="unhideBatchImg('phoneTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(EL_TYPE_TELE_PAT)" value="key" label="value" />
 	                                       </html:select>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhUseL" title="Address Type"> Use: </span></td>
									      <td id="phoneUse">  <html:select title="Phone & Email Use" name="DSPersonForm" property="phUse" styleId="PhUse" onchange ="unhideBatchImg('phoneTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(EL_USE_TELE_PAT)" value="key" label="value" />
 	                                       </html:select>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhCntryCdL" title="Country Code "> Country Code: </span></td>
									      <td id="phoneCntryCd"> <html:text title="Country Code" name="DSPersonForm" property="phCntryCd" styleId="PhCntryCd" onkeyup="unhideBatchImg('phoneTable');isNumericCharacterEntered(this)" />   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhNumL" title="Phone Number"> Phone Number: </span></td>
									      <td id="phoneNum"> <html:text title="Phone Number" name="DSPersonForm" property="phNum" styleId="PhNum"  onkeyup="unhideBatchImg('phoneTable');TeleMask(this,event)"/>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhExtL" title="Extension "> Extension: </span></td>
									      <td id="phoneExt"> <html:text title="Phone Extension" maxlength="20" name="DSPersonForm" property="phExt" styleId="PhExt" onkeyup="unhideBatchImg('phoneTable');isNumericCharacterEntered(this)" />   </td>	
									      </tr>	
									      <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhEmailL" title="Email">  Email: </span> </td>
									      <td id="phoneEmail"> <html:text title = "Email" name="DSPersonForm" property="phEmail" styleId="PhEmail" onblur="unhideBatchImg('phoneTable');checkEmail(this)"/>  </td>	
									      </tr>
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhUrlL" title="URL">  URL: </span> </td>
									      <td id="phoneUrl"> <html:text title = "URL" name="DSPersonForm" property="phUrl" styleId="PhUrl" onkeyup="unhideBatchImg('phoneTable');"/>  </td>	
									      </tr>										      
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="PhCommentsL" title="Comments "> Phone & Email Comments: </span> </td>
									      <td id="phoneComments"> <html:textarea title = "Phone & Email Comments" style="WIDTH: 500px; HEIGHT: 100px;" name="DSPersonForm" 
									      property="phComments" styleId ="PhComments" onkeyup="unhideBatchImg('phoneTable');checkTextAreaLength(this, 2000)"/>  </td>	
									      </tr>
									      <tr id="AddPhButtonToggleIdSubSection">
											<td colspan="2" align="right">
											
											<input type="button" value="     Add      "  onclick="if (PhoneBatchValidateFunction()){addClicked('phoneTable');return false}"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
										
											<tr id="UpdatePhButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Update   "    onclick="if (PhoneBatchValidateFunction())updateBatchIdEntry('phoneTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										
											<tr id="AddNewPhButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Add New   "    onclick="clearBatchIdEntryFields('phoneTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>	     
			                    
			                     
                                 </nedss:bluebar>
			                   </nedss:bluebar>
			                    
			                     <nedss:bluebar id="patSearch11" name="Identification" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch12" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                     
			                       <tr> <td colspan="2" width="100%">
			                       <div class="infoBox errors" style="display: none;visibility: none;" id="IdenerrorMessages">
									 <b> <a name="IdenerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
									 </div>									
									<table class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<td width="10%" style="background-color: #EFEFEF;border:1px solid #666666">  </td>
									<th width="15%"><b> As of </b> </th>
									<th width="25%"><b> Type </b></th>
									<th width="25%"><b> Authority </b></th>
									<th width="25%"><b> Value </b></th>			
									</tr>							
									</thead>
									<tbody id="patternidenTable">
									        <tr id="patternidenTableRow" class="odd" style="display:none">
								            <td> 
								            <table role="presentation" width="100%">
								            <tr>
								            <td style="width:33%;text-align:center;">
											 <input id="viewIdSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'idenTable');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
											 </td><td style="width:33%;text-align:center;">
											 <input id="editIdSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'idenTable');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
											 </td><td style="width:33%;text-align:center;">
											 <input id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'idenTable');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
 											</td>	
 											</tr>
 											</table>
 											</td> 															           
									            <td id="tableidDate">
									            &nbsp;
									            </td>
									            <td id="tableidTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tableidAssgnDesc">								            
									            &nbsp; 
									            </td >
									            <td id="tableidValue">
									             &nbsp;							            
									            </td >									            								           								
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternidenTable">
									 <tr id="nopatternidenTableRow" class="odd">
									 <td colspan="21"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="IdAsOfL" title="Identification as of Date ">  Identification As of: </span></td>
									      <td id="idenNameDate" width="100%">  <html:text  name="DSPersonForm" title="Identification As of" styleClass="requiredInputField" property="idAsOf" maxlength="10" size="10" styleId="IdAsOf" onkeyup="unhideBatchImg('idenTable');DateMask(this,null,event)"/>
									         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('IdAsOf','IdAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('IdAsOf','IdAsOfIcon',event);" styleId="IdAsOfIcon"></html:img>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"><span class=" InputFieldLabel" id="IdTypeL" title="Identification Type">  Type: </span>  </td>
									      <td id="idenType">  <html:select title="Identification Type" name="DSPersonForm" property="idType" styleId="IdType" onchange ="unhideBatchImg('idenTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(EI_TYPE_PAT)" value="key" label="value" />
 	                                       </html:select>  </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="IdAssgnL" title="Assigning Authority"> Assigning Authority: </span></td>
									      <td id="idenAssgn">  <html:select title="Identification Assigning Authority" name="DSPersonForm" property="idAssgn" styleId="IdAssgn" onchange ="unhideBatchImg('idenTable');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(EI_AUTH_PAT)" value="key" label="value" />
 	                                       </html:select>   </td>	
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="IdValueL" title="Identification Value"> ID Value: </span></td>
									      <td id="idenValue"> <html:text title="Identification ID Value"  name="DSPersonForm" property="idsValue" styleId="IdValue" onkeyup="unhideBatchImg('idenTable');"/>   </td>	
									      </tr>										      							      
									      
									      <tr id="AddIdenButtonToggleIdSubSection">
											<td colspan="2" align="right">
											
											<input type="button" value="     Add      "  onclick="if (IdenBatchValidateFunction()){addClicked('idenTable');return false}"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
										
											<tr id="UpdateIdenButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Update   "    onclick="if (IdenBatchValidateFunction())updateBatchIdEntry('idenTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										
											<tr id="AddNewIdenButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Add New   "    onclick="clearBatchIdEntryFields('idenTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>	       
			                  
                                 </nedss:bluebar>
			                    </nedss:bluebar>
			                    
			                     <nedss:bluebar id="patSearch13" name="Race" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch14" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                      <tr> <td colspan="2" width="100%">	
			                      <div class="infoBox errors" style="display: none;visibility: none;" id="RaceerrorMessages">
									 <b> <a name="RaceerrorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
									 </div>										
									<table class="bluebardtTable" align="center" >
									<thead>
									<tr> 
									<td width="10%" style="background-color: #EFEFEF;border:1px solid #666666">  </td>
									<th width="15%"><b>  As of: </b> </th>
									<th width="35%"><b> Race </b></th>
									<th width="40%"><b> Detailed Race </b></th>
										
									</tr>							
									</thead>
									<tbody id="patternraceTable">
									        <tr id="patternraceTableRow" class="odd" style="display:none">
								            <td> 
								            <table role="presentation" width="100%">
								            <tr>
								            <td style="width:33%;text-align:center;">
											 <input id="viewIdSubSection" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'raceTable');return false" name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
											 </td><td style="width:33%;text-align:center;">
											 <input id="editIdSubSection" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'raceTable');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
											 </td><td style="width:33%;text-align:center;">
											 <input id="deleteIdSubSection" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'raceTable');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
 											</td>	
 											</tr>
 											</table>
 											</td> 															           
									            <td id="tableraceDate">
									            &nbsp;
									            </td>
									            <td id="tableraceTypeDesc">
									            &nbsp;
									            </td>
									            <td id="tableraceDetailCatDesc">								            
									            &nbsp; 
									            </td >
									           							            								           								
									        </tr>     							
								       	
									 </tbody>	
									 <tbody id="patternraceTable">
									 <tr id="nopatternraceTableRow" class="odd">
									 <td colspan="21"> <span>&nbsp; No Data has been entered.
									 </span>
									 </td>
									 </tr>
									 </tbody>						
									</table>
									</td></tr>
									
									   <tr>
									      <td class="fieldName" width="100%"><span class=" InputFieldLabel" id="RaceAsOfL" title="Race as of Date ">  Race As of: </span></td>
									      <td id="raceNameDate" width="100%">  <html:text  title="Race As of" name="DSPersonForm" styleClass="requiredInputField" property="raceAsOf" maxlength="10" size="10" styleId="RaceAsOf" onkeyup="unhideBatchImg('raceTable');DateMask(this,null,event)"/>
									         <html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('RaceAsOf','RaceAsOfIcon');return false;" onkeypress ="showCalendarEnterKey('RaceAsOf','RaceAsOfIcon',event);" styleId="RaceAsOfIcon"></html:img>  </td>	
									      </tr>	
									       <tr>
									        <td class="fieldName"><span class=" InputFieldLabel" id="RaceTypeL" title="Race Type">  Race: </span>  </td>
									       <td>
									       
									       <html:select title="Race" name="DSPersonForm" property="raceType" styleId="RaceType" onchange="unhideBatchImg('raceTable');getDWRSubRaces(this, 'RaceDetailCat');">
 	                                       <html:optionsCollection name="DSPersonForm" property="codedValue(RACE_CALCULATED)" value="key" label="value" />
									       </html:select>
									       
											</td>								     
									    
									      </tr>	
									       <tr>
									      <td class="fieldName"> <span class=" InputFieldLabel" id="RaceDetailCatL" title="Race Details"> Detailed Race: </span></td>
									      <td>
									        <div class="multiSelectBlock">
											<i> (Use Ctrl to select more than one) </i> <br/>
											<html:select title="Detailed Race" name="DSPersonForm" property="raceDetailCat" styleId="RaceDetailCat" multiple="true" size="4"
											onchange="displaySelectedOptions(this, 'RaceDetailCat-selectedValues');unhideBatchImg('raceTable');" >
											<html:optionsCollection property="subRaces" value="key" label="value" /> </html:select>
											<div id="RaceDetailCat-selectedValues" style="margin:0.25em;">
											<b> Selected Values: </b>
											</div>
											</div>
											</td>	
									     
									      </tr>									      
									     
									        <tr id="AddRaceButtonToggleIdSubSection">
											<td colspan="2" align="right">
											
											<input type="button" value="     Add      "  onclick="AddUpdateRaceBatch('add')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
										
											<tr id="UpdateRaceButtonToggleIdSubSection"  style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Update   "    onclick="AddUpdateRaceBatch('update')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>
										
											<tr id="AddNewRaceButtonToggleIdSubSection"
											style="display:none">
											<td colspan="2" align="right">
											<input type="button" value="   Add New   "    onclick="clearBatchIdEntryFields('raceTable')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											</tr>   	           
									
                                 </nedss:bluebar>
			                    </nedss:bluebar>
			                    
			                        <nedss:bluebar id="patSearch15" name="Ethnicity" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch16" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                           <tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="AsOfDateEthL">Ethnicity Information As of:</span>
									</td><td>
									<html:text  title="Ethnicity Information As of" name="DSPersonForm" styleClass="requiredInputField" property="person.thePersonDT.asOfDateEthnicity_s" maxlength="10" size="10" styleId="AsOfDateEth" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AsOfDateEth','AsOfDateEthIcon');return false;" onkeypress ="showCalendarEnterKey('AsOfDateEth','AsOfDateEthIcon',event);" styleId="AsOfDateEthIcon"></html:img>
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Indicates if the patient is hispanic or not." id="INV2002L">Ethnicity:</span>
									</td>
									<td>
										<html:select title="Ethnicity" name="DSPersonForm" property="person.thePersonDT.ethnicGroupInd" styleId="INV2002" onchange="showHideSpanishOrigin();enableOrDisable('INV2002','ReasonUnknown','UNK');">
										<html:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
										</td>
									</tr>
									<tr><td class="fieldName">									
									<span title="Reason for unknown Ethnicity" id="ReasonUnknownL">Reason Unknown:</span>
									</td>
									<td>
										<html:select title="Reason unknown Ethnicity" name="DSPersonForm" property="person.thePersonDT.ethnicUnkReasonCd" styleId="ReasonUnknown">
										<html:optionsCollection property="codedValue(P_ETHN_UNK_REASON)" value="key" label="value" /></html:select>
										</td>
									</tr>
									<tr id="SpanOriginTR"><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="SpanOriginL">Spanish Origin:</span>
									</td>
									
									<td>
									        <div class="multiSelectBlock">
											<i> (Use Ctrl to select more than one) </i> <br/>
											<html:select title="Spanish Origin" name="DSPersonForm" property="spanOrigin" styleId="SpanOrigin" multiple="true" size="4"
											onchange="displaySelectedOptions(this, 'SpanOrigin-selectedValues');" >
											<html:optionsCollection property="codedValue(P_ETHN)" value="key" label="value" /> </html:select>
											
											<div id="SpanOrigin-selectedValues" style="margin:0.25em;" >
											
											<b> Selected Values: </b>
											</div>
											</div>
											</td>	
									</tr>                       
	                            </nedss:bluebar>
			                    </nedss:bluebar>                      
			                  
			                       <nedss:bluebar id="patSearch17" name="Sex and Birth" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                     <nedss:bluebar id="patSearch18" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	                               
	                                 <tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="AsofDateSBL">Sex and Birth Information As of:</span>
									</td><td>
									<html:text  title="Sex and Birth Information As of" name="DSPersonForm" styleClass="requiredInputField" property="person.thePersonDT.asOfDateSex_s" maxlength="10" size="10" styleId="AsofDateSB" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AsofDateSB','AsofDateSBIcon');return false;" onkeypress ="showCalendarEnterKey('AsofDateSB','AsofDateSBIcon',event);" styleId="AsofDateSBIcon"></html:img>
									</td></tr>
	                               <tr><td class="fieldName">									
									<span title="Reported date of birth of patient." id="patientDOBL">DOB:</span>
									</td><td>
									<html:text  name="DSPersonForm" title="DOB" styleClass="requiredInputField" property="person.thePersonDT.birthTime_s" maxlength="10" size="10" styleId="patientDOB" onchange="updatePatientDOBInfo(this);" onblur="updatePatientDOBInfo(this);" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('patientDOB','patientDOBIcon');return false;" onkeypress ="showCalendarEnterKey('patientDOB','patientDOBIcon',event);" styleId="patientDOBIcon"></html:img>	
									<html:hidden name="DSPersonForm" property="person.thePersonDT.birthTimeCalc_s" styleId="calcDOB"/>									
									<input id="today" type="hidden" value="<%=PersonUtil.formatCurrentDate()%>">				
																
									</td></tr>
									 <tr><td class="fieldName"><span title="The patient's current age." >Current Age:</span></td>
									 <td ><span id="currentAge"></span>
						              &nbsp;
						             <span id="currentAgeUnits"></span>
						             <SCRIPT Language="JavaScript" Type="text/javascript">displayCalcAge()</SCRIPT>
						             </td>
                                     </tr>
                                      <tr><td class="fieldName"><span title="Patient Current Sex" id="CurrSexL">Current Sex:</span></td>
									 <td >
						             <html:select title="The patient's curren sex" name="DSPersonForm" property="person.thePersonDT.currSexCd" styleId="CurrSex" onchange="enableOrDisable('CurrSex','UnknownSpecify','U');pgSelectNextFocus(this);">
										<html:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
						             </td>
                                     </tr>
                                     <tr><td class="fieldName"><span title="Unknown Reason" id="UnknownSpecifyL">Unknown Reason:</span></td>
									 <td >
						             <html:select title="Unknown reason for current sex" name="DSPersonForm" property="person.thePersonDT.sexUnkReasonCd" styleId="UnknownSpecify">
										<html:optionsCollection property="codedValue(SEX_UNK_REASON)" value="key" label="value" /></html:select>
									 </td>
                                     </tr>
                                     <tr><td class="fieldName"><span title="Transgender Information" id="TransgenderInformationL">Transgender Information:</span></td>
									 <td >
						             <html:select title="Transgender Information" name="DSPersonForm" property="person.thePersonDT.preferredGenderCd" styleId="TransgenderInformation">
										<html:optionsCollection property="codedValue(NBS_STD_GENDER_PARPT)" value="key" label="value" /></html:select>
									 </td>
                                     </tr>
                                     <tr><td class="fieldName"><span title="Additional Gender" id="AdditionalGenderL">Additional Gender:</span></td>
									 <td >
						             	<html:text title="Additional Gender" name="DSPersonForm"  property="person.thePersonDT.additionalGenderCd" maxlength="20" size="30" styleId="AdditionalGender" />
						             </td>
                                     </tr>
                                     <tr><td class="fieldName"><span title="Patient Birth Sex" id="BirSexL">Birth Sex:</span></td>
									 <td >
						              <html:select title="Patient Birth Sex" name="DSPersonForm" property="person.thePersonDT.birthGenderCd" styleId="BirSex">
										<html:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
						             </td>
                                     </tr>
                                      <tr><td class="fieldName"><span title="Multiple Birth" id="MulBirL">Multiple Birth:</span></td>
									 <td >
									 <html:select title="Multiple Birth" name="DSPersonForm" property="person.thePersonDT.multipleBirthInd" styleId="MulBir">
										<html:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>						            
						             </td>
                                     </tr>
                                     <tr><td class="fieldName"><span title="Birth Order" id="BirOrderL">Birth Order:</span></td>
									 <td >
									  <html:text title="Birth Order" name="DSPersonForm"  property="person.thePersonDT.birthOrderNbrStr" maxlength="5" size="30" styleId="BirOrder"  onkeyup="isNumericCharacterEntered(this)" />						 
						             </td>
                                     </tr>
                                      <tr><td class="fieldName"><span title="Birth City" id="BirCityL">Birth City:</span></td>
									 <td >
									    <html:text title="Birth City" name="DSPersonForm"  property="birthAddress.thePostalLocatorDT.cityDescTxt" maxlength="100" size="30" styleId="BirCity" />
						             </td>
                                     </tr>
                                      <tr>                                       
                                      <td class="fieldName"><span title="Birth State" id="BirStateL">Birth State:</span></td>
									 <td >
									 <html:select title="Birth State"  name="DSPersonForm" property="birthAddress.thePostalLocatorDT.stateCd" styleId="BirState" onchange="getDWRPatientCounties(this, 'BirCnty');">
										<html:optionsCollection property="stateList" value="key" label="value" /></html:select>									 									
						             </td>
                                     </tr>
                                      <tr><td class="fieldName"><span title="Birth County" id="BirCntyL">Birth County:</span></td>
									 <td >
									 <html:select title="Birth County" name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cntyCd"  styleId="BirCnty">
										<html:optionsCollection property="dwrBirthCounties" value="key" label="value" /></html:select>								 
						             </td>
                                     </tr>
                                     <tr><td class="fieldName"><span title="Birth Country" id="BirCntryL">Birth Country:</span></td>
									 <td >
									 <html:select title="Birth Country" name="DSPersonForm" property="birthAddress.thePostalLocatorDT.cntryCd" styleId="BirCntry">
	                                   <html:optionsCollection name="DSPersonForm" property="countryList" value="key" label="value"/></html:select>					
						             </td>
                                     </tr>	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>                 
			                    
			                        <nedss:bluebar id="patSearch19" name="Mortality" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch20" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                        <tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="AsofDateMorbL">Mortality Information As of:</span>
									</td><td>
									<html:text  title="Mortality Information As of" name="DSPersonForm" styleClass="requiredInputField" property="person.thePersonDT.asOfDateMorbidity_s" maxlength="10" size="10" styleId="AsofDateMorb" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AsofDateMorb','AsofDateMorbIcon');return false;" onkeypress ="showCalendarEnterKey('AsofDateMorb','AsofDateMorbIcon',event);" styleId="AsofDateMorbIcon"></html:img>
									</td></tr>
									
			                       <tr><td class="fieldName">									
									<span title="Is the patient deceased" id="PatDeceasedL">Is the patient Deceased:</span>
									</td><td>
									<html:select title="Is the patient deceased" name="DSPersonForm" property="person.thePersonDT.deceasedIndCd" styleId="PatDeceased">
										<html:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>	
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Date of Death" id="DateOfDeathL">Date of Death:</span>
									</td><td>
									<html:text  title="Date of Death" name="DSPersonForm" styleClass="requiredInputField" property="person.thePersonDT.deceasedTime_s" maxlength="10" size="10" styleId="DateOfDeath" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DateOfDeath','DateOfDeathIcon');return false;" onkeypress ="showCalendarEnterKey('DateOfDeath','DateOfDeathIcon',event);" styleId="DateOfDeathIcon"></html:img>							
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Death City" id="DeathCityL">Death City:</span>
									</td><td>
									<html:text title="Death City" name="DSPersonForm"  property="deceasedAddress.thePostalLocatorDT.cityDescTxt" maxlength="100" size="30" styleId="DeathCity"    />								
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Death State" id="DeathStateL">Death State:</span>
									</td><td>
									 <html:select title="Death State" name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.stateCd" styleId="DeathState" onchange="getDWRPatientCounties(this, 'DeathCnty');">
										<html:optionsCollection property="stateList" value="key" label="value" /></html:select>		
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Death County" id="DeathCntyL">Death County:</span>
									</td><td>
									 <html:select title="Death County" name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cntyCd" styleId="DeathCnty">
										<html:optionsCollection property="dwrDeathCounties" value="key" label="value" /></html:select>
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Death Country" id="DeathCntryL">Death Country:</span>
									</td><td>
									<html:select title="Death Country" name="DSPersonForm" property="deceasedAddress.thePostalLocatorDT.cntryCd" styleId="DeathCntry">
	                                   <html:optionsCollection name="DSPersonForm" property="countryList" value="key" label="value"/></html:select>					
									</td></tr>                               
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>			                   
			                    	
			                    	 
			                        <nedss:bluebar id="patSearch21" name="General Patient Information" defaultDisplay="F"   isHidden="F" classType="bluebarsect">
			                        <nedss:bluebar id="patSearch22" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			                    
			                        <tr><td class="fieldName">									
									<span title="As of Date is the last known date for which the information is valid." id="AsOfDateGenInfoL">General Information As of:</span>
									</td><td>
									<html:text  title="General Information As of" name="DSPersonForm" styleClass="requiredInputField" property="person.thePersonDT.asOfDateGeneral_s" maxlength="10" size="10" styleId="AsOfDateGenInfo" onkeyup="DateMask(this,null,event)"/>
									<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('AsOfDateGenInfo','AsOfDateGenInfoIcon');return false;" onkeypress ="showCalendarEnterKey('AsOfDateGenInfo','AsOfDateGenInfoIcon',event);"  styleId="AsOfDateGenInfoIcon"></html:img>
									</td></tr>
									
			                       <tr><td class="fieldName">									
									<span title="Marital Status" id="MarStatusL">Marital Status:</span>
									</td><td>
									 <html:select title="Marital Status" name="DSPersonForm" property="person.thePersonDT.maritalStatusCd" styleId="MarStatus">
										<html:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /></html:select>
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Mother's Maiden Name" id="MomMNmL">Mother's Maiden Name:</span>
									</td><td>
									  <html:text title="Mother's Maiden Name" name="DSPersonForm"  property="person.thePersonDT.mothersMaidenNm" maxlength="50" size="30" styleId="MomMNm"   />								
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Number of Adults in Residence" id="NumAdultsL">Number of Adults in Residence:</span>
									</td><td>
									<html:text title="Number of Adults in Residence"  name="DSPersonForm"  property="person.thePersonDT.adultsInHouseNbrStr" maxlength="5" size="30" styleId="NumAdults"  onkeyup="isNumericCharacterEntered(this)"  />								
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Number of Children in Residence" id="NumChildL">Number of Children in Residence:</span>
									</td><td>
										<html:text title="Number of Children in Residence" name="DSPersonForm"  property="person.thePersonDT.childrenInHouseNbrStr" maxlength="5" size="30" styleId="NumChild"  onkeyup="isNumericCharacterEntered(this)"  />										
									</td></tr>
									 <tr><td class="fieldName">									
									<span title="Primary Occupation" id="PriOccupL">Primary Occupation:</span>
									</td><td>
									 <html:select title="Primary Occupation" name="DSPersonForm" property="person.thePersonDT.occupationCd" styleId="PriOccup">
										<html:optionsCollection property="primaryOccupationCodes" value="key" label="value" /></html:select>
									
									</td></tr>
									<tr><td class="fieldName">									
									<span title="Highest Level of Education" id="HLevelEduL">Highest Level of Education:</span>
									</td><td>
									<html:select title="Highest Level of Education" name="DSPersonForm" property="person.thePersonDT.educationLevelCd" styleId="HLevelEdu">
										<html:optionsCollection property="codedValue(P_EDUC_LVL)" value="key" label="value" /></html:select>									
									</td></tr> 
									 <tr><td class="fieldName">									
									<span title="Primary Language" id="PriLangL">Primary Language:</span>
									</td><td>
									<html:select title="Primary Language" name="DSPersonForm" property="person.thePersonDT.primLangCd" styleId="PriLang">
										<html:optionsCollection property="languageCodes" value="key" label="value" /></html:select>									            
						             
									</td></tr> 
									<tr><td class="fieldName"><span title="Speaks English" id="SpeaksEnglishCdL">Speaks English:</span></td>
									 <td >
						              <html:select  title="Speaks English" name="DSPersonForm" property="person.thePersonDT.speaksEnglishCd" styleId="SpeaksEnglishCd">
										<html:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
						             </td>
                                     </tr>
                                     <logic:equal name="DSPersonForm" property="securityMap.hasHIVPermissions" value="T">
										<!-- State HIV Case ID -->
										<tr><td class="fieldName">
										<span class=" InputFieldLabel" id="eHARSIDL" title="State HIV Case ID.">
										State HIV Case ID:</span>
										</td>
										<td>
										<html:text title="State HIV Case ID." name="DSPersonForm" styleId="eHARSID" property="person.thePersonDT.eharsId" size="16" maxlength="16"/>
										</td></tr>				
									</logic:equal>                      
	            
	                             </nedss:bluebar>
			                    </nedss:bluebar>
			                   <!-- SECTION : Local Fields -->
							   <% if(request.getAttribute("NEWPAT_LDFS") != null) { %>
							    <nedss:bluebar id="LocalFieldsSection" name="Custom Fields" classType="bluebarsect">
							        <!-- SUB_SECTION :  Local Fields -->
							        <nedss:bluebar displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F" id="LocalFieldsSubSection" classType="bluebarsubSect" >
							        	<c:out value="${NEWPAT_LDFS}"/>
							        </nedss:bluebar>
							   </nedss:bluebar>
							    <%} %>
											    
						</div>
						</div> </td></tr>

        
        
        
        
             