
    function markAsReviewed() {
    	var opener = getDialogArgument();
      var reason = null;
      if(getElementByIdOrByName("reviewReason")!=null)
    	  reason =  getElementByIdOrByName("reviewReason").value;
      if(getElementByIdOrByNameNode("markAsReviewReason", opener.document)!=null && reason!=null)
    	  getElementByIdOrByNameNode("markAsReviewReason", opener.document).value=reason;
      var markAsReviewButtonHref='';
      if(getElementByIdOrByNameNode("markAsReviewButtonHref", opener.document)!=null){
        markAsReviewButtonHref = getElementByIdOrByNameNode("markAsReviewButtonHref", opener.document).value;
      }
      if(checkRequired()){
        return false;
      }
      if(markAsReviewButtonHref!=''){
        opener.getPage(markAsReviewButtonHref+'&markAsReviewReason='+reason);
      }
       else{
    	   opener.markAsReviewed(reason);
      }
      var invest = getElementByIdOrByNameNode("blockparent", opener.document);
      if(invest==null || invest=='undefined')
    	  invest = getElementByIdOrByNameNode("pageview", opener.document);
      invest.style.display = "none";
      window.returnValue ="true";
      window.close();
    }

    function markProcessingDecisionCreateInv(type, typeDoc, condition) {
    	var opener = getDialogArgument();
		if (checkRequired()) {
			return false;
		}
		var reason = getElementByIdOrByName("reviewReason").value;

		if(typeDoc!=null && typeDoc=='CaseReport'){
			opener.document.forms[0].action = "/nbs/LoadViewDocument2.do?method=createInvestigation&ContextAction=CreateInvestigation&ConditionCd="+condition+
			"&markAsReviewReason=" + reason+ '&investigationType=' + type + '&typeDoc=' + typeDoc;
			opener.document.forms[0].submit();
			var invest = getElementByIdOrByNameNode("pageview", opener.document);

			if(invest!=null && invest!='undefined')//TODO: get the real block page for case report
				invest.style.display = "none";
		}else{

			getElementByIdOrByNameNode("markAsReviewReason", opener.document).value = reason;
			var creatInvestigationButtonHref='';
			if(getElementByIdOrByNameNode("creatInvestigationButtonHref", opener.document)!=null){
				creatInvestigationButtonHref = getElementByIdOrByNameNode("creatInvestigationButtonHref", opener.document).value;
			}
			if(checkRequired()){
			  return false;
			}
			if(creatInvestigationButtonHref!=''){
			  opener.getPage(creatInvestigationButtonHref+'&markAsReviewReason='+reason+'&investigationType='+type);
			}
			var invest = getElementByIdOrByNameNode("blockparent", opener.document);
        	invest.style.display = "none";
		}

		window.returnValue = "true";
		window.close();
    }

    function markProcessingDecisionSelectCondition(type) {
    	var opener = getDialogArgument();
        if(checkRequired()){
		    return false;
        }
        if (type!= 'LabAssociate') {
	        var reason = getElementByIdOrByName("reviewReason").value;
	        getElementByIdOrByNameNode("ProcessingDecision", opener.document).value=reason;
	        getElementByIdOrByNameNode("investigationType", opener.document).value=type;
	        //opener.submitForm();
	        //opener.document.forms[0].submit();
	        if(opener.submitDialog==undefined)//eICR > Choose Condition XSP page > select processing decision
				opener.document.forms[0].submit();
			else
				opener.submitDialog("submitFromProcessingDecision");
	    }
        else if(type == 'LabAssociate') {
        	var url = getElementByIdOrByNameNode("cancelURL", opener.document).value;
        	url = url+'&associateLab=T';
        	opener.window.location=url;
        	//location = url; opens up new page, there is no need to submit form again.
        	//opener.document.forms[0].submit();

        }
        var invest = getElementByIdOrByNameNode("blockparent", opener.document);
        invest.style.display = "none";
        window.returnValue ="true";
        window.close();
      }

    function markProcessingDecisionContactRecord(type) {
    	//no arguments..
        //alert("markProcessingDecisionContactRecord(" +type+ ")");
		var opener = getDialogArgument();
        getElementByIdOrByNameNode("investigationType", opener.document).value=type;
        opener.saveForm();
        window.returnValue = "true";
        window.close();
      }

    function markProcessingDecisionSubmitandCreateInv(action,type) {
    	var opener = getDialogArgument();
        if(checkRequired()){
		   return false;
        }
        if(type==null || type!='Associate'){
        var reason = getElementByIdOrByName("reviewReason").value;
        getElementByIdOrByNameNode("markAsReviewReason", opener.document).value=reason;
         }
      if(action=='MorbCreate'){
		  opener.document.getElementById("investigationType").value=type;
    	  if(type=='Associate'){
    		  opener.submitMorb();

    		  }
    	  else{
    		  opener.submitAndCreateInvestigation();

    	  }
      }else

  		if (action == 'CaseCreate') {
  			opener.setValueInAttributeMap('investigationType', type);
  			// getElementByIdOrByNameNode("investigationType", opener.document).value=type;
  			if (type == 'Associate') {
  				opener.saveContextForm();

  			} else {
  				opener.submitAndCreateInvestgationForm();
  			}
  		}
      else{

        opener.submitForm();
      }

        var invest = getElementByIdOrByNameNode("blockparent", opener.document);
        invest.style.display = "none";
        window.returnValue ="true";
        window.close();
      }

    function createInvestigationFromViewMorbidityReport(currentTask){

    	var opener = getDialogArgument();
        opener.submitMorbAndCreateInv(currentTask);
        var invest = getElementByIdOrByNameNode("blockparent", opener.document);
	        invest.style.display = "none";
	        window.returnValue ="true";
        window.close();
      }

    function checkMaxLength(sTxtBox) {
      maxlimit = 1000;
      if (sTxtBox.value.length > maxlimit){
        sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
      }
    }
    function trim(str)
    {
      while (str.charAt(0) == " ") {
        // remove leading spaces
        str = str.substring(1);
      }

      while (str.charAt(str.length - 1) == " "){
        // remove trailing spaces
        str = str.substring(0,str.length - 1);
      }
      return str;
    }
    function closeDialog() {
      self.close();
      var opener = getDialogArgument();
      if(getElementByIdOrByNameNode("markAsReviewReason", opener.document)!=null && getElementByIdOrByNameNode("markAsReviewReason", opener.document)!='undefined'){
	      		getElementByIdOrByNameNode("markAsReviewReason", opener.document).value="none";
    	}
      var invest = getElementByIdOrByNameNode("blockparent", opener.document);

	  if(invest==null)
		invest = getElementByIdOrByNameNode("pageview", opener.document);

      invest.style.display = "none";
    }
    function checkRequired()
    {
      if (getElementByIdOrByName("errorBlock") == null) //contact rcd
      	return false;

      getElementByIdOrByName("errorBlock").style.display="none";

      var errors = new Array();
      var index = 0;
      var isError = false;
      var reason = trim(getElementByIdOrByName("reviewReason").value);
      if( reason==null || (reason != null && reason.length == 0)) {
        errors[index++] =  "Processing Decision is required";
        getElementByIdOrByName("disReason").style.color="#CC0000";
        isError = true;
      }
      else {
        getElementByIdOrByName("disReason").style.color="black";
      }
      if(isError) {
        displayErrors("errorBlock", errors);
        return isError;
      }
    }
 // This function is getting called when marking STD lab or morbidity report(View Page) as reviewed
    function OpenMarkAsReviewed(event) {
    	var block = getElementByIdOrByName("blockparent");
    	block.style.display = "block";
    	var o = new Object();
    	o.opener = self;
    	var PDLogic = getElementByIdOrByName("PDLogic").value;
    	//var returnMessage = window.showModalDialog("/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PDLogic="+PDLogic+"&context=loadMAR", o, GetDialogFeatures(600, 350, false));


    	var URL = "/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PDLogic="+PDLogic+"&context=loadMAR";

    	var modWin = openWindow(URL, o,GetDialogFeatures(600, 350, false, true), block, "");

    	return false;
    }

 // This function is getting called when creating investigation from STD lab or morbidity report(View Page) by clicking 'Create Investigation' button
    function OpenProcessingDecisionForViewSubmit(event) {
		var block = getElementByIdOrByName("blockparent");

		block.style.display = "block";
		var o = new Object();
		o.opener = self;
		var PDLogic = getElementByIdOrByName("PDLogicCreateInv").value;
		var conditionObj  = getElementByIdOrByName("morbidityReport.theObservationDT.cd");
		var condition = "";
		if(conditionObj!=null){
			condition = conditionObj.value;
		}
		//var returnMessage = window.showModalDialog("/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PDLogic="+PDLogic+"&CONDITION_CD="+condition+"&context=loadProcessDecisionCreateInv", o, "dialogWidth: " + 900 + "px;dialogHeight: " +
		//    600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
	   //     (true ? "resizable: yes;" : ""));

		var URL ="/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PDLogic="+PDLogic+"&CONDITION_CD="+condition+"&context=loadProcessDecisionCreateInv&buttonName=createInvestigation";

		var dialogFeatures = "dialogWidth: " + 900 + "px;dialogHeight: " +
			600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
		(true ? "resizable: yes;" : "");

		var modWin = openWindow(URL, o,dialogFeatures, block, "");

		return false;
    }

    // This function is getting called when creating investigation from STD lab or morbidity report(Create page) by clicking 'Submit and Create Investigation' button
    function OpenProcessingDecisionForCreateSubmit(event) {
    	var pa = "";
    	if(event=='LabReport'){
    		pa = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;
    		//alert("pa: "+pa);
    	}
    	var con = "";
    	var stdFlag='false';
    	if(event=='MorbReport'){
    		con= getElementByIdOrByName("conditionCd").value;
    		var conandpa = getElementByIdOrByName("conditionCd").value.split("^");
    		con = conandpa[2];
    		pa =  conandpa[0];
    		//alert("con: "+con);
    		//alert("pa: "+pa);
    	}
        if(getElementByIdOrByName("STD_PA_LIST").value !=null){
        var stsdPAs = getElementByIdOrByName("STD_PA_LIST").value.split(",");
	        for (var i=0;i<stsdPAs.length;i++){
		         if(pa==stsdPAs[i]){
		        	 stdFlag = 'true';
		         }
	        }
        }
        //alert(stdFlag);
   	    if(stdFlag=='true'){
	    	var block = getElementByIdOrByName("blockparent");
	    	block.style.display = "block";
	    	var o = new Object();
	    	o.opener = self;
	    	//var returnMessage = window.showModalDialog("/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PROG_AREA_CD="+pa+"&CONDITION_CD="+con+"&context=loadProcessDecisionCreateInv", o, "dialogWidth: " + 900 + "px;dialogHeight: " +
            //600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
            //(true ? "resizable: yes;" : ""));


	    	var URL = "/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PROG_AREA_CD="+pa+"&CONDITION_CD="+con+"&context=loadProcessDecisionCreateInv&buttonName=SubmitAndCreateInvestigation";
	    	var dialogFeatures =  "dialogWidth: " + 900 + "px;dialogHeight: " +
            600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
            (true ? "resizable: yes;" : "");

	    	var modWin = openWindow(URL, o,dialogFeatures, block, "");

	    	return false;
   	    }
   	    else if(event=='MorbReport'){
   	    	submitAndCreateInvestigation();
   	    }
   	    else{
   	    	submitForm();
   	    }
    }
    // This function is getting called when creating investigation from select condition page
   	 function OpenProcessingDecisionForSelectCondition() {
   		 var CreateInvestigationFromSTDorHIVLab = getElementByIdOrByName("CreateInvestigationFromSTDorHIVLab").value;
   		 //alert(CreateInvestigationFromSTDorHIVLab);
   		 var con = getElementByIdOrByName("ccd").value;
   		 var stdConditions = getElementByIdOrByName("STDConditions").value;
   		 var isStdCond = conditionInStdList(stdConditions, con);
   		var referralBasisDocument = getElementByIdOrByName("referralBasisDocument").value;
  		 var referralBasisDoc = "";
  		var isCreateInvFromViewDocument = false;
  		if(getElementByIdOrByName("isCreateInvFromViewDocument")!=null) {
  			isCreateInvFromViewDocument = getElementByIdOrByName("isCreateInvFromViewDocument").value;
  		}

		//see if the referral basis has been defaulted to T2 because it is coming from the document
  		if(getElementByIdOrByName("referralBasis")==undefined && getElementByIdOrByName("referralBasis")==null ||
	    		getElementByIdOrByName("referralBasis").value==null || getElementByIdOrByName("referralBasis").value==""){

  			if((isStdCond=='T' || isStdCond == true) && (referralBasisDocument!=null && referralBasisDocument!=undefined && referralBasisDocument!=""))
  				referralBasisDoc = referralBasisDocument;
		}
	    //if referralBasisDoc has a value, that means it comes from the Document.
  		if(referralBasisDoc=="" && (getElementByIdOrByName("referralBasis")==null ||
 	    		getElementByIdOrByName("referralBasis").value==null || getElementByIdOrByName("referralBasis").value=="") &&
 	    		 isStdCond && CreateInvestigationFromSTDorHIVLab != 'T' )
   			 submitForm();//in order to show the validation message, Referral basis is required from create investigation
   		 else
	     if((getElementByIdOrByName("referralBasis")!=null &&
	    		getElementByIdOrByName("referralBasis").value!=null && getElementByIdOrByName("referralBasis").value!="") || ( referralBasisDoc!="" && (isStdCond == 'T' ||  isStdCond == true)) ||
	    		(CreateInvestigationFromSTDorHIVLab == 'T' && isStdCond)){
	    	var event = 'selectCondition';
	    	if(CreateInvestigationFromSTDorHIVLab == 'T' || CreateInvestigationFromSTDorHIVLab == 'LabReport' ){
	    		event = 'LabReport';
	    	}
 	    	var block = getElementByIdOrByName("blockparent");
 	    	block.style.display = "block";
 	    	var o = new Object();
 	    	o.opener = self;
 	    	//var returnMessage = window.showModalDialog("/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&FromLabReport="+CreateInvestigationFromSTDorHIVLab+"&event="+event+"&CONDITION_CD="+con+"&context=loadProcessDecisionCreateInv", o, "dialogWidth: " + 900 + "px;dialogHeight: " +
           // 600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
            //(true ? "resizable: yes;" : ""));


 	    	var URL = "/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&FromLabReport="+CreateInvestigationFromSTDorHIVLab+"&event="+event+"&CONDITION_CD="+con+"&context=loadProcessDecisionCreateInv&isCreateInvFromViewDocument="+isCreateInvFromViewDocument+"&referralBasisDocument="+referralBasisDocument;
 	    	var dialogFeatures =  "dialogWidth: " + 900 + "px;dialogHeight: " +
            600 + "px;status: no;unadorned: yes;scroll: no;help: no;" +
            (true ? "resizable: yes;" : "");

 	    	var modWin = openWindow(URL, o,dialogFeatures, block, "");

 	    	return false;
	    }
	    else{
	    	submitForm();
	    }
    }

   	function windowResize()
    {
  	  var width= getElementByIdOrByName("resizeDiv").offsetWidth ;
  	  var height= getElementByIdOrByName("resizeDiv").offsetHeight ;
  	  //alert(screen.width);
  	  //alert(height);
  	  window.dialogWidth = width+20+'px';
  	  window.dialogHeight = height+80+'px';
  	  //getElementByIdOrByName("resizeDiv").css('position', 'fixed');
  	  //window.resizeto(width+20,height+100);
    }

    function conditionInStdList(stdConditions, condToCheck)
    {
   	var splitConditions = stdConditions.split('|');
   	for(var i=0; i<splitConditions.length; i++) {
		 if (splitConditions[i] == condToCheck)
		         	return true;
    	}
	return false; //not in list
    }

function changeReferralBasis (referralBasisDropDown)
{
    var referralBasisDocumentHidden = document.getElementById("referralBasisDocument");
    referralBasisDocumentHidden.value = referralBasisDropDown.value;
}