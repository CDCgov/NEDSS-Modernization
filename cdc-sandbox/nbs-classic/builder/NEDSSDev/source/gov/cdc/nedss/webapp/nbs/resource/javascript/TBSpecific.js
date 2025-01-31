// Method added for TIMS legacy case
function toggleLegacyCase(field)
{
    JPamForm.getLegacyTIMSCase(function(data){ });
}

function rvctCreateLoad(multiselects)
{          
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    if(actionMode == 'Preview') return;  

    checkRacesOptions();
    onLoadReportedAgeCalc();
    onLoadIllnessOnsetAgeCalc();

    // AutoComplete Stuff
    autocompTxtValuesForJSP();

    // update multi-select results span to display selected options
    var selectEltIdsArray = multiselects.split("|");
    for (var i = 0; i < selectEltIdsArray.length; i++) 
    {
        var selectElt = getElementByIdOrByName(selectEltIdsArray[i]); 
        if(selectElt != null && !(selectElt.isDisabled) && selectElt.type == 'select-multiple')
        {
            var valuesDisplaySpanId = selectEltIdsArray[i] + "-selectedValues";
            displaySelectedOptions(selectElt, valuesDisplaySpanId);    
        }
    }
    
    // get error tabs
    var errorTabsPresent = false;
    JPamForm.getErrorTabs(function(data) 
    {
        if (data.length > 0) 
        {
           errorTabsPresent = true;
           handleErrorTabs(data);
        }
        
        // if there are no error tabs, get the tab is selected in view mode 
	    // and automatically select it
	    if (errorTabsPresent == false) 
	    {
	        JPamForm.getTabId(function(data) {
	            if(data!=null && data!="" && data == '5') {
	                    data = '0'
	            }                               
	        
	            if(data!=null && data!="")
	            {
	                selectTab(0,pamTabCount(),data,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
	            }
	            else
	            {
	                selectTab(0,pamTabCount(),0,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
	            }
	        });
	    }    
    });
}
        
function createLegacyTIMS()
{
    if(getElementByIdOrByName("TUB258").checked)
    {
        getElementByIdOrByName("legacy").style.border="2px solid blue";
    }else
    {
        getElementByIdOrByName("legacy").style.border="none";
    }
}
        
function createLegacyTIMSBox()
{
    if(getElementByIdOrByName("TUB258").checked)
    {
        getElementByIdOrByName("LegacyTIMSView").className="visible";
    }
}
        
function checkInvestigatorLayout() {
    var pUid = getElementByIdOrByName("attributeMap.INV207Uid").value;
    if(pUid == null || pUid == "") {
        getElementByIdOrByName("INV207TD1").className="visible";
        getElementByIdOrByName("INV207TD2").className="visible";
    }
}

function toggleLegacyInfo(element){
    if(element.checked) {
        getElementByIdOrByName("rvctLegacyRow").className="visible";
        getElementByIdOrByName("LegacyTIMS").className="visible";
        getElementByIdOrByName("legacyCol").style.border="2px solid blue";
    } else {
        getElementByIdOrByName("TUB256").value="";
        getElementByIdOrByName("TUB257").value="";
        getElementByIdOrByName("rvctLegacyRow").className="none";
        getElementByIdOrByName("LegacyTIMS").className="none";
        getElementByIdOrByName("legacyCol").style.border="none";
    }
}
       
function populateStandardRegimen(eltIdToFocus)
{
    if(getElementByIdOrByName("TUB171").disabled==false)
    {
	    getElementByIdOrByName("TUB171").value="Y";
	    getElementByIdOrByName("TUB172").value="Y";
	    getElementByIdOrByName("TUB173").value="Y";
	    getElementByIdOrByName("TUB174").value="Y";
    
        autocompTxtValuesForJSP();
        
        fireRule('TUB171',getElementByIdOrByName('TUB171'));
        // the MoveFocusToNextField() call specified in the 
        // fireRule() method is overridden here. This separate call
        // is performed after a time delay for the fireRule() to complete.
        setTimeout(function()
                    {
                        $j("#" + eltIdToFocus).focus();
                    },200);
    }
}
     
function populateMarkRestNo(val)
{
    var markId= val;
    var initialDrugdoc = getElementByIdOrByName(markId);
   	var selNodes = initialDrugdoc.getElementsByTagName("select");
   	var element = null;
	
	for(var i=0; i < selNodes.length; i++)
	{
		element = selNodes[i];
		if(element.disabled== false && element. value=="")
		element. value = "N";	
        autocompTxtValuesForJSP();
	}
	
	if (element != null) {
	   MoveFocusToNextField(element);   
	}
}

function populateInitialMarkRestNo(val)
{
    var markId= val;
    var initialDrugdoc = getElementByIdOrByName(markId);
   	var selNodes = initialDrugdoc.getElementsByTagName("select");
   	var element = null;
	for( var i=0; i < selNodes.length; i++)
	{
		element = selNodes[i];
		if(element.disabled== false && element.value=="")
		{
			element. value = "385660001";
        }
	}

    autocompTxtValuesForJSP();
    fireRule('TUB202',getElementByIdOrByName('TUB202'));
    fireRule('TUB209',getElementByIdOrByName('TUB209'));
    fireRule('TUB212',getElementByIdOrByName('TUB212'));
    fireRule('TUB203',getElementByIdOrByName('TUB203'));
    fireRule('TUB208',getElementByIdOrByName('TUB208'));
    fireRule('TUB204',getElementByIdOrByName('TUB204'));
    fireRule('TUB206',getElementByIdOrByName('TUB206'));
    fireRule('TUB210',getElementByIdOrByName('TUB210'));
    fireRule('TUB213',getElementByIdOrByName('TUB213'));
    fireRule('TUB211',getElementByIdOrByName('TUB211'));
    fireRule('TUB214',getElementByIdOrByName('TUB214'));
    fireRule('TUB215',getElementByIdOrByName('TUB215'));
    fireRule('TUB205',getElementByIdOrByName('TUB205'));
    fireRule('TUB207',getElementByIdOrByName('TUB207'));
    fireRule('TUB216',getElementByIdOrByName('TUB216'));
    
    if (element != null) {
       MoveFocusToNextField(element);   
    }
}

function clearAll(val)
{
    var markId= val;
    var initialDrugdoc = getElementByIdOrByName(markId);
	var inputNodes = initialDrugdoc.getElementsByTagName("input");
   	var selNodes = initialDrugdoc.getElementsByTagName("select");
   	var element = null;
   for( var i=0; i < inputNodes.length; i++)
	{
		element = inputNodes[i];			
		if(element.getAttribute("name") != 'pamClientVO.answer(TUB170)'  && element.disabled== false && element.type != "button" ){
			element. value = "";		
				if(element.getAttribute("name") == 'pamClientVO.answer(TUB189)'    || element.getAttribute("name") == 'TUB190_textbox'   || element.getAttribute("name") == 'pamClientVO.answer(TUB191)' || element.getAttribute("name") == 'pamClientVO.answer(TUB217)'  ||  element.getAttribute("name") == 'TUB218_textbox'  || element.getAttribute("name") == 'pamClientVO.answer(TUB219)' ||element.getAttribute("name") == 'pamClientVO.answer(TUB263)'  || element.getAttribute("name") == 'TUB264_textbox'  || element.getAttribute("name") == 'pamClientVO.answer(TUB265)' ){
		 	 //	element.disabled=true;
				if(element.getAttribute("name") == 'pamClientVO.answer(TUB189)'){
                     getElementByIdOrByName('TUB188').value="";
					 fireRule('TUB188',getElementByIdOrByName('TUB188'));
					//getElementByIdOrByName("TUB191L").className='InputDisabledLabel';
			    //	getElementByIdOrByName("TUB190L").className='InputDisabledLabel';
				 //   getElementByIdOrByName("TUB189L").className='InputDisabledLabel';
				}
				if(element.getAttribute("name") == 'pamClientVO.answer(TUB217)'){
					  getElementByIdOrByName('TUB216').value="";
					 fireRule('TUB216',getElementByIdOrByName('TUB216'));
				//	getElementByIdOrByName("TUB217L").className='InputDisabledLabel';
				//getElementByIdOrByName("TUB218L").className='InputDisabledLabel';
				//getElementByIdOrByName("TUB219L").className='InputDisabledLabel';
				}
				if(element.getAttribute("name") == 'pamClientVO.answer(TUB263)'){
					  getElementByIdOrByName('TUB262').value="";
					 fireRule('TUB262',getElementByIdOrByName('TUB262'));
				//	getElementByIdOrByName("TUB263L").className='InputDisabledLabel';
			//	getElementByIdOrByName("TUB264L").className='InputDisabledLabel';
			//	getElementByIdOrByName("TUB265L").className='InputDisabledLabel';
				}
	     	}
		}
	}
	for( var i=0; i < selNodes.length; i++)
	{ 
		element = selNodes[i];
		if(element.disabled== false)
			element.value = "";			
	}

    autocompTxtValuesForJSP();
    // fireRule('TUB198',getElementByIdOrByName('TUB198'));
}

function populateFinalMarkRestNo(val)
{
    var markId= val;
    var finalDrugdoc = getElementByIdOrByName(markId);
   	var selNodes = finalDrugdoc.getElementsByTagName("select");
   	var element = null;
	for(var i=0; i < selNodes.length; i++) {
		element = selNodes[i];
		if(element.disabled== false && element. value=="")
			element. value = "385660001";
	}

    autocompTxtValuesForJSP();
    fireRule('TUB248',getElementByIdOrByName('TUB248'));
    fireRule('TUB255',getElementByIdOrByName('TUB255'));
    fireRule('TUB258',getElementByIdOrByName('TUB258'));
    fireRule('TUB249',getElementByIdOrByName('TUB249'));
    fireRule('TUB254',getElementByIdOrByName('TUB254'));
    fireRule('TUB250',getElementByIdOrByName('TUB250'));
    fireRule('TUB252',getElementByIdOrByName('TUB252'));
    fireRule('TUB256',getElementByIdOrByName('TUB256'));    
    fireRule('TUB259',getElementByIdOrByName('TUB259'));
    fireRule('TUB257',getElementByIdOrByName('TUB257'));
    fireRule('TUB260',getElementByIdOrByName('TUB260'));
    fireRule('TUB261',getElementByIdOrByName('TUB261'));
    fireRule('TUB251',getElementByIdOrByName('TUB251'));
    fireRule('TUB253',getElementByIdOrByName('TUB253'));
    fireRule('TUB262',getElementByIdOrByName('TUB262'));
    
    if (element != null) {
       MoveFocusToNextField(element);   
    }
}

function disableButtonsF1()
{
    if (getElementByIdOrByName("TUB156").value=="N" || (getElementByIdOrByName("TUB156").value=="UNK")){
        getElementByIdOrByName("fieldMarkRestNotDoneFirstButton").disabled = true;
        getElementByIdOrByName("fieldMarkSusceptsFirstButton").disabled = true;
        var textbox = getElementByIdOrByName("fieldInitialCommentsText");
        textbox.focus();
    }
    
    if (getElementByIdOrByName("TUB156").value=="Y"){
        var btn1 = getElementByIdOrByName("fieldMarkRestNotDoneFirstButton");
        var btn= getElementByIdOrByName("fieldMarkSusceptsFirstButton");
        btn.disabled = false;
        btn1.disabled = false;
        var txt = getElementByIdOrByName("TUB157");
        txt.disabled = false;
        txt.focus();

    }
}
        
function onLoadF1(){
    if (getElementByIdOrByName("TUB156").value=="" ) 
    {
        getElementByIdOrByName("fieldMarkRestNotDoneFirstButton").disabled = true;
        getElementByIdOrByName("fieldMarkSusceptsFirstButton").disabled = true;
    }
    if (getElementByIdOrByName("TUB156").value=="N" || (getElementByIdOrByName("TUB156").value=="UNK"))
    {
        getElementByIdOrByName("fieldMarkRestNotDoneFirstButton").disabled = true;
        getElementByIdOrByName("fieldMarkSusceptsFirstButton").disabled = true;
    }
    
}
        
function onLoadF2()
{
    if (getElementByIdOrByName("TUB182").value=="")
    {
        getElementByIdOrByName("fieldMarkRestNotDoneFinalButton").disabled = true;
        getElementByIdOrByName("fieldMarkSusceptsFinalButton").disabled = true;
    }
    if (getElementByIdOrByName("TUB182").value=="N" || (getElementByIdOrByName("TUB182").value=="UNK")){
        getElementByIdOrByName("fieldMarkRestNotDoneFinalButton").disabled = true;
        getElementByIdOrByName("fieldMarkSusceptsFinalButton").disabled = true;
    }
}
        
function disableButtonsF2()
{
    if (getElementByIdOrByName("TUB182").value=="N" || (getElementByIdOrByName("TUB182").value=="UNK")){
        getElementByIdOrByName("fieldMarkRestNotDoneFinalButton").disabled = true;
        getElementByIdOrByName("fieldMarkSusceptsFinalButton").disabled = true;
        var textbox = getElementByIdOrByName("fieldFinalCommentsText");
        textbox.focus();
    }
    if (getElementByIdOrByName("TUB182").value=="Y"){
        var btn1 = getElementByIdOrByName("fieldMarkRestNotDoneFinalButton");
        var btn= getElementByIdOrByName("fieldMarkSusceptsFinalButton");
        btn.disabled = false;
        btn1.disabled = false;
        var txt = getElementByIdOrByName("TUB183");
        txt.disabled = false;
        txt.focus();
    }
}
        
function populateInitialSusceptibility(eltIdToFocus)
{
    if(getElementByIdOrByName("TUB198").disabled==false)
    {
	    getElementByIdOrByName("TUB198").value="S";
	    getElementByIdOrByName("TUB199").value="S";
	    getElementByIdOrByName("TUB200").value="S";
	    getElementByIdOrByName("TUB201").value="S";
	    autocompTxtValuesForJSP();
	    
	    fireRule('TUB198',getElementByIdOrByName('TUB198'));
	    fireRule('TUB199',getElementByIdOrByName('TUB199'));
	    fireRule('TUB200',getElementByIdOrByName('TUB200'));
	    fireRule('TUB201',getElementByIdOrByName('TUB201'));
	    
	    $j("#" + eltIdToFocus).focus();
    }
}
        
function populateInitialSusceptibilityNotDone()
{
    if(getElementByIdOrByName("TUB166").disabled==false)
    {
        getElementByIdOrByName("TUB166").value="385660001";
        getElementByIdOrByName("TUB168").value="385660001";
        getElementByIdOrByName("TUB167").value="385660001";
        getElementByIdOrByName("TUB169").value="385660001";
        getElementByIdOrByName("TUB162").value="385660001";
        getElementByIdOrByName("TUB170").value="385660001";
        getElementByIdOrByName("TUB163").value="385660001";
        getElementByIdOrByName("TUB171").value="385660001";
        getElementByIdOrByName("TUB164").value="385660001";
        getElementByIdOrByName("TUB172").value="385660001";
        getElementByIdOrByName("TUB165").value="385660001";
        autocompTxtValuesForJSP();
    }
    
    MoveFocusToNextField(getElementByIdOrByName("TUB165"));
}

function populateFinalSusceptibility(eltIdToFocus)
{
	if(getElementByIdOrByName("TUB244").disabled==false)
	{
        getElementByIdOrByName("TUB244").value="S";			
        getElementByIdOrByName("TUB245").value="S";
        getElementByIdOrByName("TUB246").value="S";
        getElementByIdOrByName("TUB247").value="S";
        autocompTxtValuesForJSP();
        
        fireRule('TUB244',getElementByIdOrByName('TUB244'));
        fireRule('TUB245',getElementByIdOrByName('TUB245'));
        fireRule('TUB246',getElementByIdOrByName('TUB246'));
        fireRule('TUB247',getElementByIdOrByName('TUB247'));
        
        $j("#" + eltIdToFocus).focus();
        
        
    }
}
 
function populateFinalSusceptibilityNotDone()
{
    if(getElementByIdOrByName("TUB192").disabled==false)
    {
        getElementByIdOrByName("TUB192").value="385660001";
        getElementByIdOrByName("TUB194").value="385660001";
        getElementByIdOrByName("TUB193").value="385660001";
        getElementByIdOrByName("TUB195").value="385660001";
        getElementByIdOrByName("TUB188").value="385660001";
        getElementByIdOrByName("TUB196").value="385660001";
        getElementByIdOrByName("TUB189").value="385660001";
        getElementByIdOrByName("TUB197").value="385660001";
        getElementByIdOrByName("TUB190").value="385660001";
        getElementByIdOrByName("TUB198").value="385660001";
        getElementByIdOrByName("TUB191").value="385660001";
        autocompTxtValuesForJSP();
    }
    
    MoveFocusToNextField(getElementByIdOrByName("TUB191"));
}


function populateAsUserEntered()
{
    // getElementByIdOrByName('TUB288').value="T";
    // getElementByIdOrByName('TUB153').value="";
    // fireRule('TUB108',getElementByIdOrByName('TUB108').value);
    //  alert(getElementByIdOrByName('TUB266').value);
    var cVeri = getElementByIdOrByName('TUB266').value;
	 var cStatus = getElementByIdOrByName('TUB108').value;
	  var cDate = getElementByIdOrByName('TUB110').value;
	   var mmwrW = getElementByIdOrByName('INV165').value;
	    var mmwrY = getElementByIdOrByName('INV166').value;    

    if(cVeri=='PHC654' || cVeri=='PHC653' || cVeri=='PHC165' || cVeri=='PHC97' || cVeri=='PHC98'){
        getElementByIdOrByName('INV163').value='C';
		if(cVeri=='PHC165' && cStatus != null && cStatus != "" && cStatus == "PHC657") {           
		     getElementByIdOrByName('TUB110L').disabled=false;
           getElementByIdOrByName('INV165L').disabled=false;
		   getElementByIdOrByName('INV166L').disabled=false;
		}
	}
    else if(cVeri=='415684004'){
        getElementByIdOrByName('INV163').value='S';
		if(cStatus != null && cStatus != "" && cStatus == "PHC657") {
           getElementByIdOrByName('TUB110').value = "";
           getElementByIdOrByName('INV165').value= "";
		   getElementByIdOrByName('INV166').value="";
		    getElementByIdOrByName('TUB110').disabled=true;
           getElementByIdOrByName('INV165').disabled=true;
		   getElementByIdOrByName('INV166').disabled=true;
		     getElementByIdOrByName('TUB110L').disabled=true;
           getElementByIdOrByName('INV165L').disabled=true;
		   getElementByIdOrByName('INV166L').disabled=true;
		}
	}
    else if(cVeri=='PHC162'){
        getElementByIdOrByName('INV163').value='N';
		if(cStatus != null && cStatus != "" && cStatus == "PHC657") {
           getElementByIdOrByName('TUB110').value = "";
           getElementByIdOrByName('INV165').value= "";
		   getElementByIdOrByName('INV166').value="";
		    getElementByIdOrByName('TUB110L').disabled=true;
           getElementByIdOrByName('INV165L').disabled=true;
		   getElementByIdOrByName('INV166L').disabled=true;
		}
	}
	         getElementByIdOrByName('TUB109L').disabled=true;
		      getElementByIdOrByName('TUB109').value= "";
			   getElementByIdOrByName('TUB109').disabled=true;
    autocompTxtValuesForJSP();   
}

function enableFieldLabels()
{

	 var cStatus = getElementByIdOrByName('TUB108').value;
	  var cDate = getElementByIdOrByName('TUB110').value;
	   var mmwrW = getElementByIdOrByName('INV165').value;
	    var mmwrY = getElementByIdOrByName('INV166').value;   
		if(cStatus != null && cStatus != "" && cStatus == "PHC657") {           
		     getElementByIdOrByName('TUB110L').disabled=false;
           getElementByIdOrByName('INV165L').disabled=false;
		   getElementByIdOrByName('INV166L').disabled=false;
		} 
		if(cStatus != null && cStatus != "" && cStatus == "PHC660") {           
		     getElementByIdOrByName('TUB109L').disabled=false;
          
		} 

    autocompTxtValuesForJSP();   
}

function populateProviderDiagnosisDetails(trId)
{
    // getElementByIdOrByName('TUB288').value="T";
    // getElementByIdOrByName('TUB153').value="";
    // fireRule('TUB108',getElementByIdOrByName('TUB108').value);
    //  alert(getElementByIdOrByName('TUB266').value);
    var cVeri = getElementByIdOrByName('TUB266') == null ? "" : getElementByIdOrByName('TUB266').value;
    //var rowId = getElementByIdOrByName(trId);
    if(cVeri != null && cVeri != "")
    {
        var rowId = "#" + trId;
        var textAreaEltElts = $j(rowId).find("#TUB279");
        var singleTextAreaElt = null;
        if (textAreaEltElts.length > 0) {
            singleTextAreaElt = textAreaEltElts[0];
        }
        
        if(cVeri=='PHC654' || cVeri=='PHC653' || cVeri=='PHC165' || cVeri=='PHC97' || cVeri=='PHC98')
        {                       
            if(cVeri=='PHC165')
            {
                $j(rowId).css("display", "block"); 
                if (singleTextAreaElt != null) {
                    singleTextAreaElt.disabled = false;
                }         
            }
            else 
            {
                if (singleTextAreaElt != null) {
                    singleTextAreaElt.disabled = true;
                }         
                $j(rowId).css("display", "none");       
            }
        }
        else if(cVeri=='415684004')
        {
            if (singleTextAreaElt != null) {
                singleTextAreaElt.disabled = true;
            }         
            $j(rowId).css("display","none");
        }
        else if(cVeri=='PHC162')
        {           
            if (singleTextAreaElt != null) {
                singleTextAreaElt.disabled = true;
            }         
            $j(rowId).css("display","none");
        }
        autocompTxtValuesForJSP();   
    }    
}

function checkDateTherapy(){            
    if( getElementByIdOrByName("TUB132").value !="" || getElementByIdOrByName("TUB133").value !="" ||  getElementByIdOrByName("TUB134").value !="" ||  getElementByIdOrByName("TUB135").value !="" || getElementByIdOrByName("TUB136").value !="" || getElementByIdOrByName("TUB137").value !=""  || getElementByIdOrByName("TUB138").value !="" || getElementByIdOrByName("TUB139").value !="" || getElementByIdOrByName("TUB140").value !="" || getElementByIdOrByName("TUB141").value !="" || getElementByIdOrByName("TUB142").value !="" || getElementByIdOrByName("TUB143").value !="" || getElementByIdOrByName("TUB144").value !="" || getElementByIdOrByName("TUB145").value !=""  ||  getElementByIdOrByName("TUB146").value !=""  ) 
    {   
        var p = getElementByIdOrByName("TUB134").value;    
        getElementByIdOrByName("TUB134").value="Y";
        getElementByIdOrByName("TUB147").disabled=false;
        getElementByIdOrByName("TUB134").value = p;    
    }
    else{
    getElementByIdOrByName("TUB147").disabled=true;
    getElementByIdOrByName("TUB147").value="";
    }
}

function enableDateTherapy() 
{ 
    if(getElementByIdOrByName("TUB132").value !="" || getElementByIdOrByName("TUB133").value !="" ||  getElementByIdOrByName("TUB134").value !="" ||  getElementByIdOrByName("TUB135").value !="" || getElementByIdOrByName("TUB136").value !="" || getElementByIdOrByName("TUB137").value !=""  || getElementByIdOrByName("TUB138").value !="" || getElementByIdOrByName("TUB139").value !="" || getElementByIdOrByName("TUB140").value !="" || getElementByIdOrByName("TUB141").value !="" || getElementByIdOrByName("TUB142").value !="" || getElementByIdOrByName("TUB143").value !="" || getElementByIdOrByName("TUB144").value !="" || getElementByIdOrByName("TUB145").value !=""  ||  getElementByIdOrByName("TUB146").value !="") 
    {   
        var p = getElementByIdOrByName("TUB134").value;    
        getElementByIdOrByName("TUB134").value="Y";

        //alert(getElementByIdOrByName("TUB147").disabled);
        getElementByIdOrByName("TUB147").disabled=false;
        getElementByIdOrByName("TUB134").value = p;    
    } else {
		getElementByIdOrByName("TUB147").disabled=true;
		getElementByIdOrByName("TUB147").value="";
    }
}

function handleFollowUp1_DrugTestBtns(multiSelectEltId)
{
    var elt = getElementByIdOrByName(multiSelectEltId);
    var btn1 = "followUp1_StandardSusceptibilities";
    var btn2 = "followUp1_MarkRestNotDone";
    var btn3 = "followUp1_Clear"; 
    
    if (elt != null && elt.disabled == false && elt.style.display != 'none' && elt.selectedIndex != -1) {
        var selVal = elt.options[elt.selectedIndex].value;
        if (selVal == 'Y') {
            getElementByIdOrByName(btn1).disabled = false;    
            getElementByIdOrByName(btn2).disabled = false;
            getElementByIdOrByName(btn3).disabled = false;
        }
        else if (selVal == 'N' || selVal == 'UNK' || jQuery.trim(selVal).length == 0) {
            getElementByIdOrByName(btn1).disabled = true;    
            getElementByIdOrByName(btn2).disabled = true;
            getElementByIdOrByName(btn3).disabled = true;
        }    
    } 
    else if (elt.selectedIndex == -1) {
        getElementByIdOrByName(btn1).disabled = true;    
        getElementByIdOrByName(btn2).disabled = true;
        getElementByIdOrByName(btn3).disabled = true;
    }
}

function handleFollowUp2_DrugTestBtns(multiSelectEltId)
{
    var elt = getElementByIdOrByName(multiSelectEltId);
    var btn1 = "followUp2_StandardSusceptibilities";
    var btn2 = "followUp2_MarkRestNotDone";
    var btn3 = "followUp2_Clear";
    
    if (elt != null && elt.disabled == false && elt.style.display != 'none' && elt.selectedIndex != -1) {
        var selVal = elt.options[elt.selectedIndex].value;
        if (selVal == 'Y') {
            getElementByIdOrByName(btn1).disabled = false;    
            getElementByIdOrByName(btn2).disabled = false;
            getElementByIdOrByName(btn3).disabled = false;
        }
        else if (selVal == 'N' || selVal == 'UNK' || jQuery.trim(selVal).length == 0) {
            getElementByIdOrByName(btn1).disabled = true;    
            getElementByIdOrByName(btn2).disabled = true;
            getElementByIdOrByName(btn3).disabled = true;
        }
    }
    else if (elt.selectedIndex == -1){
        getElementByIdOrByName(btn1).disabled = true;    
        getElementByIdOrByName(btn2).disabled = true;
        getElementByIdOrByName(btn3).disabled = true;
    }
}

function getStrutsLayoutTabIdForTabOrderId(tabOrderId, actionMode)
{
    // CREATE MODE Tab order from left to right. 
    // Patient(1), Tuberculosis(2), Followup1(4), Followup2(5), LDF1(6)
    // Value in parantheses represents tab order id set for each tab in backend
    var createModeTabs = new Array();
    createModeTabs["1"] = 0;
    createModeTabs["2"] = 1;
    createModeTabs["4"] = 2;
    createModeTabs["5"] = 3;
    // if tab_order_id is greater than 5, return tab_order_id - 2;
    
    
    // EDIT MODE Tabs order from left to right
    // Patient(1), Tuberculosis(2), Case Verification(3), Followup1(4), Followup2(5), Supp Info, LDF1(6)
    // Value in parantheses represents tab order id set for each tab in backend

    var editModeTabs = new Array();
    editModeTabs["1"] = 0;
    editModeTabs["2"] = 1;
    editModeTabs["3"] = 2;
    editModeTabs["4"] = 3;
    editModeTabs["5"] = 4;
    // if tab_order_id is greater than 5, return tab_order_id
    
    if (actionMode == 'CREATE_SUBMIT') {
        if (tabOrderId <= 5) {
            return (createModeTabs["" + tabOrderId]); 
        }
        else {
            return (tabOrderId-2);
        }
    }
    else if (actionMode == 'EDIT_SUBMIT' || actionMode == 'Edit') {
        if (tabOrderId <= 5) {
            return (editModeTabs["" + tabOrderId]); 
        }
        else {
            return (tabOrderId);
        }
    }
    else {
        // unsupported action mode. return 0, the index to the first tab
        return 0; 
    }
}

function handleErrorTabs(data)
{
    var actionMode = getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
    if (data.length > 0) 
    {
        var firstErrorTab = data[0];
        for (var i = 0; i < data.length; i++)
        {
            // update the firstErrorTab if required 
            if (data[i] < firstErrorTab) {
                firstErrorTab = data[i];
            }
            
            // get the struts layout tab index & change the header background color 
            var index = getStrutsLayoutTabIdForTabOrderId(data[i], actionMode);
            var tabHeader = getElementByIdOrByName("tabs0head" + index);
            tabHeader.className='ongletTextErr';
        }
        
        // automatically select the first error tab.
        var firstTabIndex = getStrutsLayoutTabIdForTabOrderId(firstErrorTab, actionMode);
        selectTab(0,pamTabCount(),firstTabIndex,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
    }
}