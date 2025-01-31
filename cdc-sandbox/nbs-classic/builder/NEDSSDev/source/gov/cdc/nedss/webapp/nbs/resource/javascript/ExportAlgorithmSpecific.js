function getTrigerLogicFromTriggerField(newValue)
{
    var newValueValue = "abcxyz";
    //alert("Here : "+newValue.value);
        if (newValue.value != null && newValue.value != "") {
            newValueValue = newValue.value;
        }               
    

    JExportAlgorithmForm.getTrigerLogicFromTriggerField(newValueValue, function(data) {
     	//alert("data.length Logic" + data.length);
     	dwr.util.removeAllOptions("triggerLogic"); 
        dwr.util.addOptions("triggerLogic",data,"key","value"); 
     
    });
}



function getTrigerFilterFromTriggerField(newValue)
{
   //alert("Here 1: "+newValue.value);
  
    var newValueValue = "abcxyz";
        if (newValue.value != null && newValue.value != "") {
            newValueValue = newValue.value;
	}         

    

    JExportAlgorithmForm.getTrigerFilterValues(newValueValue, function(data) {
     	//alert("data.length Filter " + data.length);
     	dwr.util.removeAllOptions("triggerFilter"); 
     	 //for (var i = 0; i < data.length; i++) {
     	 //      getElementByIdOrByName('triggerFilter').options[i] = new Option(data[i].value,data[i].key)
	//	getElementByIdOrByName('triggerFilter').options[i].selected = false;
     	 	
       // }
       
       dwr.util.addOptions("triggerFilter",data,"key","value"); 
       
    });
}

function noOp()
{return;}
    
    function init() {
  	fillTable();
   }
   
    var triggerCache = { };
    var viewed = -1;
    
function fillTable() {

      var rowClass = "";
     
     //alert("*********************** here in fillTable() now !!! ");
     
     JExportAlgorithmForm.getExTrDTset(function(data) {    
	    // Delete all the rows except for the "pattern" row
	   dwr.util.removeAllRows("triggerbody", { filter:function(tr) {
	      return (tr.id != "pattern");
	    }});
	    // Create a new set cloned from the pattern row
	    var exTrDT, id;
	    /*
	    exTrDT.sort(function(p1, p2) { 
	    	//alert(p1 + ' --> ' + p2);
	    	return p1.id.localeCompare(p2.id); 
	    });
            */
	    for (var i = 0; i < data.length; i++) {
	      exTrDT= data[i];
	      if (i%2 == 0)
	      	rowClass="even";
	      else
	      	rowClass="odd";

	      id = exTrDT.id;
	      dwr.util.cloneNode("pattern", { idSuffix:id });
	      dwr.util.setValue("tableTriggerField" + id, exTrDT.triggerFieldDesc);
	      dwr.util.setValue("tableTriggerLogic" + id, exTrDT.triggerLogicDesc);
	      dwr.util.setValue("tableTriggerFilter" + id, exTrDT.triggerFilterDesc);
	      dwr.util.byId("pattern" + id).style.display = ""; 
	      getElementByIdOrByName("pattern" + id).setAttribute("className",rowClass);
	      //alert($j("#pattern").attr("style"));
	      //$j("#pattern"+ id).attr("style",tableStyle)
	      //$j("#pattern" + id).style.display = ""; 
	     
	      triggerCache[id] = exTrDT;
	    }
  });
}

function editClicked(eleid) {
  // we were an id of the form "edit{id}", eg "edit42". We lookup the "42"
  var trigger = triggerCache[eleid.substring(4)];
  var triggerObj = new Object();
  triggerObj.value=trigger.triggerField;
  getTrigerLogicFromTriggerField(triggerObj);
  getTrigerFilterFromTriggerField(triggerObj);  
  dwr.util.setValues(trigger);
  autocompTxtValuesForJSPByElement('triggerField');
  autocompTxtValuesForJSPByElement('triggerLogic');
  autocompTxtValuesForJSPByElement('triggerFilter');
  
}

function deleteClicked(eleid) {
  var trigger = triggerCache[eleid.substring(6)];
    dwr.engine.beginBatch();
    JExportAlgorithmForm.deleteTrigger(trigger.id);
    fillTable();
    dwr.engine.endBatch();
}

function writeTrigger() {
  var exTr = { id:viewed, triggerField:null, triggerLogic:null, triggerFilter:null };
  dwr.util.getValues(exTr);
  
  //alert("exTr "+ exTr );

  dwr.engine.beginBatch();
  JExportAlgorithmForm.setExTrDT(exTr);
  fillTable();
  dwr.engine.endBatch();
  dwr.util.setValues({ id:-1,  triggerField:null, triggerLogic:null, triggerFilter:null });
  dwr.util.setValues({ id:-1,  triggerField_textbox:null, triggerLogic_textbox:null, triggerFilter_textbox:null });
  }


function clearTrigger() {
  viewed = -1;
  dwr.util.setValues({ id:-1,  triggerField:null, triggerLogic:null, triggerFilter:null });
  dwr.util.setValues({ id:-1,  triggerField_textbox:null, triggerLogic_textbox:null, triggerFilter_textbox:null });
}






function AlgorithmReqFields()
{
    $j(".infoBox").hide();
    
    var errors = new Array();
    var index = 0;
    var isError = false;

    var algthmName= getElementByIdOrByName("exADT.algorithmName");
    var docmntType= getElementByIdOrByName("exADT.documentType");
    var levOfRevw=getElementByIdOrByName("exADT.levelOfReview");	
    var recvngSystem = getElementByIdOrByName("exADT.receivingSystem");
    var recStatusCd= getElementByIdOrByName("exADT.recordStatusCd");
    var codeSet = getElementByIdOrByName("codeSetForm")
    
	if( algthmName != null && algthmName.value.length == 0) {
		errors[index++] =  "Algorithm Name is required";
		getElementByIdOrByName("algName").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("algName").style.color="black";
	}
	
	if( docmntType != null && docmntType.value.length == 0) {
        errors[index++] = "Document Type is required";
        getElementByIdOrByName("docType").style.color="990000";
        isError = true;     
    	}
    	else {
    	
        getElementByIdOrByName("docType").style.color="black";
    	}
	
	if( levOfRevw != null && levOfRevw.value.length == 0) {
        errors[index++] = "Level Of Review is required";
        getElementByIdOrByName("levelOfRev").style.color="990000";
		isError = true;		
	}
	else {
	   getElementByIdOrByName("levelOfRev").style.color="black";
	}
	if( recvngSystem != null && recvngSystem.value.length == 0) {
	        errors[index++] = "Receiving System is required";
	        getElementByIdOrByName("recSystem").style.color="990000";
			isError = true;		
		}
		else {
		   getElementByIdOrByName("recSystem").style.color="black";
	}
	
	if( recStatusCd != null && recStatusCd.value.length == 0) {
	        errors[index++] = "Status is required";
	        getElementByIdOrByName("recStatCd").style.color="990000";
			isError = true;		
		}
		else {
		   getElementByIdOrByName("recStatCd").style.color="black";
	}
		
	if(isError) {
		displayErrors("exportAlgthmErrors", errors);
	}
	return isError;	
}



