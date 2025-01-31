   function CheckIfCoinfectionsShouldBeAssociated() {
    // This function is getting called when creating treatment submit button
	
	var coInfectionExists =getElementByIdOrByName("COINFECTION_INV_EXISTS").value;
   	if(coInfectionExists != null && coInfectionExists=='true'){
   	    	var event = 'treatment';
	    	var block =getElementByIdOrByName("blockparent");
	    	block.style.display = "block";
	    	var o = new Object();
	    	o.opener = self;
	    	
	    	 var URL = "/nbs/LoadManageCoinfectionAssociation.do?method=associateToCoinfectionLoad&event="+event;
             var dialogFeatures = "dialogWidth: " + 900 + "px;dialogHeight: " + 600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + (true ? "resizable: yes;" : "");
             //window.showModalDialog(URL, o, dialogFeatures); 
             
             var modWin = openWindow(URL, o,dialogFeatures, block, "");
             
            	//alert("returning");
            	if (getElementByIdOrByName("coinfList").value != null &&getElementByIdOrByName("coinfList").value == null ||getElementByIdOrByName("coinfList").value == "")
            		return false;  //cancelled
	    	//submitForm();
   	 } else{
   	    //coInfection does not Exist
   	    	submitForm();
   	 }
    }

   function ManageInvestigationAssociations() 
   {
    	// This function is getting called from View Treatment - Manage Investigations button
	    var treatmentUid =getElementByIdOrByName("theTreatmentUid").value;
   	    var event = 'treatment';
	    var block =getElementByIdOrByName("blockparent");
	    block.style.display = "block";
	    var o = new Object();
	    o.opener = self;
	    
	    
   	 var URL = "/nbs/LoadManageCoinfectionAssociation.do?method=associateInvestigationsLoad&treatmentUID="+treatmentUid+"&ContextAction=Edit";
     var dialogFeatures = "dialogWidth: " + 900 + "px;dialogHeight: " + 600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + (true ? "resizable: yes;" : "");
     //window.showModalDialog(URL, o, dialogFeatures); 
     
     var modWin = openWindow(URL, o,dialogFeatures, block, "");
     
            //alert("returning");
            block.style.display = "none";
	    return false;

    }
function windowResize()
    {
  	  var width=getElementByIdOrByName("resizeDiv").offsetWidth ;
  	  var height=getElementByIdOrByName("resizeDiv").offsetHeight ;
  	  //alert(screen.width);
  	  //alert(height);
  	  window.dialogWidth = width+20+'px';
  	  window.dialogHeight = height+80+'px';
  	  //getElementByIdOrByName("resizeDiv").css('position', 'fixed');
  	  //window.resizeto(width+20,height+100);
    }

function CheckIfCoinfectionsShouldBeAssociatedToInterview() {
    // This function is getting called when creating treatment submit button
	
	var coInfectionExistsEle =getElementByIdOrByName("IXS190");
   	if(coInfectionExistsEle != null && coInfectionExistsEle.value =='T'){
   	    	var event = 'interview';
   	    	var interviewDate =getElementByIdOrByName("IXS101").value;
   	    	var interviewType =getElementByIdOrByName("IXS105").value;
	    	//var block =getElementByIdOrByName("blockparent");
	    	//block.style.display = "block";
	    	var o = new Object();
	    	o.opener = self;
	    	
	    	 var URL = "/nbs/LoadManageCoinfectionAssociation.do?method=associateInterviewToCoinfectionLoad&event="+event+"&InterviewDate="+interviewDate+"&InterviewType="+interviewType;
             var dialogFeatures = "dialogWidth: " + 900 + "px;dialogHeight: " + 600 + "px;status: no;unadorned: yes;scroll: yes;help: no;";
             //window.showModalDialog(URL, o, dialogFeatures); 
             
             var modWin = openWindow(URL, o,dialogFeatures, null, "");
            	//alert("returning");
            	if (getElementByIdOrByName("IXS191").value == null ||getElementByIdOrByName("IXS191").value == "")
            		return false;  //cancelled
	    	submitForm();
   	 } else{
   	    //coInfection does not Exist
   	    	submitForm();
   	 }
    }
    
function ManageInterviewInvestigationAssociations(actUidStr) 
{
 	// This function is getting called from View Interview - Associate Investigations button
	    var event = 'interview';
	    var block =getElementByIdOrByName("blockparent");
	    if (block != null)
	    	block.style.display = "block";
	    var o = new Object();
	    o.opener = self;
	    //alert("actUidSt="+actUidStr);
	    
	   	 var URL = "/nbs/LoadManageCoinfectionAssociation.do?method=associateInterviewInvestigationsLoad&ContextAction=Edit&actUidStr="+actUidStr;
	     var dialogFeatures = "dialogWidth: " + 900 + "px;dialogHeight: " + 600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + (true ? "resizable: yes;" : "");
	     //window.showModalDialog(URL, o, dialogFeatures); 
	     
	     var modWin = openWindow(URL, o,dialogFeatures, block, "");
	     
         //alert("returning");
         if (block != null)
         	block.style.display = "none";
	    return false;

 } 
 
function ManageContactInvestigationAssociations(investigationUidStr) 
{
 	// This function is getting called from View Interview - Associate Investigations button
	    var event = 'Contact';
	    var block =getElementByIdOrByName("blockparent");
	    if (block != null)
	    	block.style.display = "block";
	    var o = new Object();
	    o.opener = self;
	    //alert("investigationUidStr="+investigationUidStr);
	    
   	 var URL = "/nbs/LoadManageCoinfectionAssociation.do?method=associateContactInvestigationsLoad&ContextAction=Edit&investigationUidStr="+investigationUidStr;
     var dialogFeatures = "dialogWidth: " + 900 + "px;dialogHeight: " +	600 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +(true ? "resizable: yes;" : "");
     //window.showModalDialog(URL, o, dialogFeatures); 
     
     var modWin = openWindow(URL, o,dialogFeatures, block, "");
     
         //alert("returning");
         if (block != null)
         	block.style.display = "none";
	    return false;

 }  

function getDWRInvestigatorForAssociateCoinfection(identifier)
{
 dwr.util.setValue(identifier, "");
 var code = $(identifier+"Text");
 var codeValue= code.value;
 JAssociateToCoinfectionForm.getDwrInvestigatorDetails(codeValue,identifier, function(data) {
       dwr.util.setEscapeHtml(false);
       if(data.indexOf('$$$$$') != -1) {
	         var code = $(identifier+"Text");
	         code.value = "";
	         dwr.util.setValue(identifier, "");
	         dwr.util.setValue(identifier+"Error", "");
	
	         dwr.util.setValue("investigator.personUid", data.substring(0,data.indexOf('$$$$$')));
	         dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$')+5));
	
	       getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
	        //getElementByIdOrByName("investigatorIcon").style.visibility="hidden";
	       getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
	       getElementByIdOrByName("clear"+identifier).className="";
	       getElementByIdOrByName(identifier+"SearchControls").className="none";
	        
	        // enable the date assigned to investigator field
	        if (identifier == "CON137") {
	            enabledDateAssignedToInvestigation();
	        }
       } else {
           dwr.util.setValue(identifier+"Error", data);
           getElementByIdOrByName(identifier+"Text").style.visibility="visible";
            //getElementByIdOrByName("investigatorIcon").style.visibility="visible";
           getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
           getElementByIdOrByName("clear"+identifier).className="none";
           getElementByIdOrByName(identifier+"SearchControls").className="visible";
       }
    });
}


function clearInvestigatorForAssociateCoinfection(identifier)
{
	var code = $(identifier+"Text");
	code.value = "";
	dwr.util.setValue(identifier, "");
	dwr.util.setValue(identifier+"Error", "");
	getElementByIdOrByName(identifier+"Text").style.visibility="visible";
	getElementByIdOrByName(identifier+"Icon").style.visibility="visible";
	getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
	getElementByIdOrByName("clear"+identifier).className="none";
	getElementByIdOrByName(identifier+"SearchControls").className="visible";

   // if the identifier is investigator 
   if (identifier == "CON137") {
       disabledDateAssignedToInvestigation();
   }	

   JAssociateToCoinfectionForm.clearDWRInvestigator(identifier);
}


function getInvestigatorForAssociateCoinfection(identifier) 
{
	//alert('before clear: ' + identifier);
	clearInvestigatorForAssociateCoinfection(identifier);
	//alert('After clear: ' + identifier);
	var urlToOpen = "/nbs/Provider.do?method=searchLoad&identifier="+identifier;
	var params="left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var contactTracing =getElementByIdOrByName("ContactTracingLoad");
	contactTracing.style.display = "block";	
	 var o = new Object();
	    o.opener = self;
	//var modWin = window.showModalDialog(urlToOpen,o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
    //       700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
    //       (true ? "resizable: yes;" : ""));
	    var modWin = openWindow(urlToOpen, o, "dialogWidth: " + 760 + "px;dialogHeight: " + 
	           700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" + 
	           (true ? "resizable: yes;" : ""), contactTracing, "");
	//newwindow.focus();
}