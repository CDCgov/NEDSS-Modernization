 var gBatchEntryUpdateSeq = "";
 var gAdvBatchEntryUpdateSeq = "";
 var gAdvInvBatchEntryUpdateSeq = "";
 var gElrBatchEntryUpdateSeq = "";
 var gBatchEntryFieldsDisabled = false;
 var gAdvBatchEntryFieldsDisabled = false;
 var gElrBatchEntryFieldsDisabled = false;
 var gAdvInvBatchEntryFieldsDisabled = false;
 var numericMask = "";$j("#frq").parent().find("span").css("color", "#666666");

// if Event Type is case Report, Action dropDown NBS_INV_ACTION, else NBS_EVENT_ACTION
function getActionDropdown(){
	var eventType = getElementByIdOrByName("EVENT_TY").value;
	if(eventType != null && selectedEventType != null && selectedEventType != ""){
		 var confirmMsg="Changing the Event Type will reset all data in the algorithm. " +
		 		"If you continue with this action, you will lose any information " +
		 		"you have entered. Select OK to continue, or Cancel to not continue.";
		  if (!confirm(confirmMsg)) {
			  setSelectedIndex(getElementByIdOrByName("EVENT_TY"), selectedEventType);
		  }else
		  {
			  if(eventType=='PHC236')
			  {
				  selectedEventType = eventType;

				  getElementByIdOrByName("selectedLaboratory").selectedIndex=-1;
				  getElementByIdOrByName('laboratory-selectedValues').innerHTML = "";
				  getElementByIdOrByName("laboratory").style.display = "none";
				  getElementByIdOrByName("sendingSystem").style.display = "";

				  initActionUI();
				  initIdAdvancedSubSection();
				  initElrIdAdvancedSubSection();
				  initIdSubSection();
				  enableActionList();
				  enableDisableInvLogicOnLoad();
				  getElementByIdOrByName("invCriteriaSubSection").style.display = "none";
			  }else if(eventType=='11648804')
			  {
				  selectedEventType = eventType;

				  getElementByIdOrByName("sendSys").selectedIndex=-1;
				  getElementByIdOrByName('sendSys-selectedValues').innerHTML = "";
				  getElementByIdOrByName("sendingSystem").style.display = "none";
				  getElementByIdOrByName("laboratory").style.display = "";
				  initActionUI();
				  initIdAdvancedSubSection();
				  initElrIdAdvancedSubSection();
				  initIdSubSection();
				  enableActionList();

			  }else
			  {
				  selectedEventType = eventType;
				  getElementByIdOrByName("selectedLaboratory").selectedIndex=-1;
				  getElementByIdOrByName("sendSys").selectedIndex=-1;
				  getElementByIdOrByName('laboratory-selectedValues').innerHTML = "";
				  getElementByIdOrByName('sendSys-selectedValues').innerHTML = "";

				  getElementByIdOrByName("laboratory").style.display = "none";
				  getElementByIdOrByName("sendingSystem").style.display = "none";

				  initActionUI();
				  initIdAdvancedSubSection();
				  initElrIdAdvancedSubSection();
				  initIdSubSection();
				  disableActionList();
			  }
			  JDecisionSupport.getEventActionDropdown("NBS_EVENT_ACTION", selectedEventType, function(data){
					DWRUtil.removeAllOptions("ActionList");
			   		DWRUtil.addOptions("ActionList", data, "key", "value" );
			   		resetActionTab();
				});

			  var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
				var aCollection = document.getElementsByTagName("a");
				for (var i=0; i<aCollection.length; i++) {
					if(aCollection[i].getAttributeNode('class')!=null && aCollection[i].getAttributeNode('class').nodeValue == "anchor" && getCorrectAttribute(aCollection[i].getAttributeNode('name'), "nodeValue",aCollection[i].getAttributeNode('name').nodeValue)=="section1") {
						if(StartsWith(actionMode, "Create"))
						{
							if(eventType=='11648804')
								aCollection[i].innerHTML="Add Algorithm - " + "Laboratory Report";
							else if (eventType=='PHC236')
								aCollection[i].innerHTML="Add Algorithm - " + "Case Report";
							else
								aCollection[i].innerHTML="Add Algorithm";
						}
						break;
					}
				}
		  }
	}else
	{
		  if(eventType=='PHC236')
		  {
			  selectedEventType = eventType;
			  getElementByIdOrByName("laboratory").style.display = "none";
			  getElementByIdOrByName("sendingSystem").style.display = "";

			  enableActionList();
		  }else if(eventType=='11648804')
		  {
			  selectedEventType = eventType;
			  getElementByIdOrByName("sendingSystem").style.display = "none";
			  getElementByIdOrByName("laboratory").style.display = "";

			  setSelectedIndex(getElementByIdOrByName("onfail"),"2");

			  enableActionList();
		  }else
		  {
			  selectedEventType = eventType;
			  getElementByIdOrByName("laboratory").style.display = "none";
			  getElementByIdOrByName("sendingSystem").style.display = "none";

			  disableActionList();
		  }

		  JDecisionSupport.getEventActionDropdown("NBS_EVENT_ACTION", selectedEventType, function(data){
				DWRUtil.removeAllOptions("ActionList");
		   		DWRUtil.addOptions("ActionList", data, "key", "value" );
		   		resetActionTab();
			});

		  var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
			var aCollection = document.getElementsByTagName("a");
			for (var i=0; i<aCollection.length; i++) {
				if(aCollection[i].getAttributeNode('class')!=null && aCollection[i].getAttributeNode('name')!=null)
				if(aCollection[i].getAttributeNode('class').nodeValue == "anchor" && aCollection[i].getAttributeNode('name').nodeValue=="section1") {
					if(StartsWith(actionMode, "Create"))
					{
						if(eventType=='11648804')
							aCollection[i].innerHTML="Add Algorithm - " + "Laboratory Report";
						else if (eventType=='PHC236')
							aCollection[i].innerHTML="Add Algorithm - " + "Case Report";
						else
							aCollection[i].innerHTML="Add Algorithm";
					}
					break;
				}
			}
	}

}

function enableActionList()
{
	getElementByIdOrByName("ActionList_textbox").value="";
	setSelectedIndex(getElementByIdOrByName("ActionList"), "");
	$j($j("#ActionList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#ActionList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#ActionList").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#ActionListL").parent().find("span").css("color", "#000000");
}

function disableActionList()
{
	getElementByIdOrByName("ActionList_textbox").value="";
	setSelectedIndex(getElementByIdOrByName("ActionList"), "");
	$j($j("#ActionList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#ActionList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#ActionList").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#ActionListL").parent().find("span").css("color", "#666666");
}

function clearSelectedAction(){

	getElementByIdOrByName("PublishedCondition_textbox").value="";
	setSelectedIndex(getElementByIdOrByName("PublishedCondition"), "");
	getElementByIdOrByName('conditionRelatedPage').innerHTML = "";
	//getElementByIdOrByName("onfailReview_textbox").value="";
	//setSelectedIndex(getElementByIdOrByName("onfailReview"), "");
	getElementByIdOrByName("relatedPage_textbox").value="";
	setSelectedIndex(getElementByIdOrByName("relatedPage"), "");
	getElementByIdOrByName("conditionList").options.length = 0;
	getElementByIdOrByName('conditionList-selectedValues').innerHTML = "";
	getElementByIdOrByName("onfail_textbox").value="";
	setSelectedIndex(getElementByIdOrByName("onfail"), "");
}

function clearSelectedNotification()
{
	getElementByIdOrByName("NotComment").value="";
}

function disableMarkAsReviewed()
{
	getElementByIdOrByName("onFailReviewLabel").style.display = "none";
	getElementByIdOrByName("onFailReviewField").style.display = "none";
}

function enableMarkAsReviewed()
{
	getElementByIdOrByName("onFailReviewLabel").style.display = "";
	getElementByIdOrByName("onFailReviewField").style.display = "";
	$j($j("#onfailReview").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#onfailReview").parent().parent()).find("img").attr("disabled", true);
	$j($j("#onfailReview").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#onFailReviewLabel").parent().find("span").css("color", "#666666");
}

function resetActionTab()
{

	disableAllAction();
}

function disableAllAction()
{
	clearSelectedAction();
	clearSelectedAdvancedQuestions('IdAdvancedSubSection', 'patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');
	clearSelectedElrAdvancedQuestions('ElrIdAdvancedSubSection', 'patternIdElrAdvancedSubSection','questionbodyIdElrAdvancedSubSection');
	clearSelectedDWRQuestions('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
	clearSelectedNotification();
	disableMarkAsReviewed();
	getElementByIdOrByName("Notes").value = "";
	getElementByIdOrByName("defaultidNote").style.display = "none";
	getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "none";
	getElementByIdOrByName("RelatedLabel").style.display = "none";
	getElementByIdOrByName("RelatedField").style.display = "none";
	getElementByIdOrByName("ConditionLabel").style.display = "none";
	getElementByIdOrByName("ConditionField").style.display = "none";

	getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
	getElementByIdOrByName("PublishedConditionField").style.display = "none";
	getElementByIdOrByName("conditionPageLabel").style.display = "none";
	getElementByIdOrByName("conditionPageField").style.display = "none";

	getElementByIdOrByName("onFailLabel").style.display = "none";
	getElementByIdOrByName("onFailField").style.display = "none";

	getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
	getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
	getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
	getElementByIdOrByName("IdSubSection").style.display = "none";
	getElementByIdOrByName("subsec6").style.display = "none";
}

function initActionUI()
{
	getElementByIdOrByName("RelatedLabel").style.color="black";
	getElementByIdOrByName("ConditionLabel").style.color="black";
	getElementByIdOrByName("onFailLabel").style.color="black";
	getElementByIdOrByName("PublishedConditionLabel").style.color="black";
	getElementByIdOrByName("onFailNotL").style.color="black";
	getElementByIdOrByName("NotCommentL").style.color="black";
	getElementByIdOrByName("EventDateTypeSelL").style.color = "black";
}

function getActionRelatedFields(){
	var eventType = getElementByIdOrByName("EVENT_TY").value;
	var actionType = getElementByIdOrByName("ActionList").value;
	if(actionType != null && actionType==''){
		 var confirmMsg="Selecting a blank Action will clear all data from this tab. " +
		 		"If you continue with this action, you will lose any information " +
		 		"you have entered. Select OK to continue, or Cancel to not continue.";
		  if (!confirm(confirmMsg)) {
			  setSelectedIndex(getElementByIdOrByName("ActionList"), selectedActionType);
		  } else {
			selectedActionType = actionType;
			clearSelectedAction();
			clearSelectedAdvancedQuestions('IdAdvancedSubSection', 'patternIdAdvancedSubSection','questionbodyIdAdvancedSubSection');
			clearSelectedAdvancedInvQuestions('IdAdvancedInvSubSection', 'patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');
			clearSelectedElrAdvancedQuestions('ElrIdAdvancedSubSection', 'patternIdElrAdvancedSubSection','questionbodyIdElrAdvancedSubSection');
			clearSelectedDWRQuestions('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
			clearSelectedNotification();

			initActionUI();
			disableMarkAsReviewed();
			initIdAdvancedSubSection();
			initElrIdAdvancedSubSection();
			initIdSubSection();

			getElementByIdOrByName("Notes").value = "";
	        getElementByIdOrByName("defaultidNote").style.display = "none";
	        getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "none";
			getElementByIdOrByName("RelatedLabel").style.display = "none";
			getElementByIdOrByName("RelatedField").style.display = "none";
			getElementByIdOrByName("ConditionLabel").style.display = "none";
			getElementByIdOrByName("ConditionField").style.display = "none";

			getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
			getElementByIdOrByName("PublishedConditionField").style.display = "none";
			getElementByIdOrByName("conditionPageLabel").style.display = "none";
			getElementByIdOrByName("conditionPageField").style.display = "none";

			getElementByIdOrByName("onFailLabel").style.display = "none";
			getElementByIdOrByName("onFailField").style.display = "none";

			//getElementByIdOrByName("updateLabel").style.display = "none";
			//getElementByIdOrByName("updateField").style.display = "none";

			getElementByIdOrByName("invCriteriaSubSection").style.display = "none";
			getElementByIdOrByName("timeFrameSubSection").style.display = "none";
			getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "none";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("IdSubSection").style.display = "none";
			getElementByIdOrByName("subsec6").style.display = "none";
		  }
	}else if(actionType != null && actionType=='1'){
		// display the investigation detail section and investigation default value section
		selectedActionType = actionType;

		clearSelectedNotification();

		JDecisionSupport.getPublishedConditionDropDown(function(data){
	    	DWRUtil.removeAllOptions("PublishedCondition");
	    	DWRUtil.addOptions("PublishedCondition", data, "key", "value" );
	    	getElementByIdOrByName("PublishedCondition_textbox").value="";
	    	selectCondition();
		});

		if(eventType == '11648804')
		{
			getElementByIdOrByName("PublishedConditionLabel").style.display = "";
			getElementByIdOrByName("PublishedConditionField").style.display = "";
			getElementByIdOrByName("conditionPageLabel").style.display = "";
			getElementByIdOrByName("conditionPageField").style.display = "";
			getElementByIdOrByName("onFailLabel").style.display = "";
			getElementByIdOrByName("onFailField").style.display = "";

			JDecisionSupport.setAnswer("OnFailureToCreateInvestigation","2",  function(data){
				var onFailValue = getSelectedIndexText(getElementByIdOrByName("onfail"), "2")
				getElementByIdOrByName("onfail_textbox").value=onFailValue;
				setSelectedIndex(getElementByIdOrByName("onfail"), "2");

				$j($j("#onfail").parent().parent()).find(":input").attr("disabled", true);
				$j($j("#onfail").parent().parent()).find("img").attr("disabled", true);
				$j($j("#onfail").parent().parent()).find("img").attr("tabIndex", "-1");
			    
				//$j("#onFailLabel").parent().find("span").css("color", "#666666");
				getElementByIdOrByName("onFailLabel").style.color="#666666";
			});

			getElementByIdOrByName("RelatedLabel").style.display = "none";
			getElementByIdOrByName("RelatedField").style.display = "none";
			getElementByIdOrByName("ConditionLabel").style.display = "none";
			getElementByIdOrByName("ConditionField").style.display = "none";

			getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
			
			getElementByIdOrByName("invCriteriaSubSection").style.display = "";
		} else
		{
			getElementByIdOrByName("RelatedLabel").style.display = "";
			getElementByIdOrByName("RelatedField").style.display = "";
			getElementByIdOrByName("ConditionLabel").style.display = "";
			getElementByIdOrByName("ConditionField").style.display = "";

			getElementByIdOrByName("onFailLabel").style.display = "";
			getElementByIdOrByName("onFailField").style.display = "";

			JDecisionSupport.setAnswer("OnFailureToCreateInvestigation","",  function(data){
				var onFailValue = getSelectedIndexText(getElementByIdOrByName("onfail"), "")
				getElementByIdOrByName("onfail_textbox").value=onFailValue;
				setSelectedIndex(getElementByIdOrByName("onfail"), "");

				$j($j("#onfail").parent().parent()).find(":input").attr("disabled", false);
				$j($j("#onfail").parent().parent()).find("img").attr("disabled", false);
				$j($j("#onfail").parent().parent()).find("img").attr("tabIndex", "0");
			    
				//$j("#onFailLabel").parent().find("span").css("color", "#000");
				getElementByIdOrByName("onFailLabel").style.color="#000";
			});

			getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
			getElementByIdOrByName("PublishedConditionField").style.display = "none";
			getElementByIdOrByName("conditionPageLabel").style.display = "none";
			getElementByIdOrByName("conditionPageField").style.display = "none";

			getElementByIdOrByName("IdAdvancedSubSection").style.display = "";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
		}

		disableMarkAsReviewed();
		getElementByIdOrByName("subsec6").style.display = "none";
		getElementByIdOrByName("NotComment").value="";
	}else if(actionType != null && actionType=='2')
	{
		// display the investigation detail section and investigation default value section
		selectedActionType = actionType;
		JDecisionSupport.getPublishedConditionDropDown(function(data){
	    	DWRUtil.removeAllOptions("PublishedCondition");
	    	DWRUtil.addOptions("PublishedCondition", data, "key", "value" );
	    	getElementByIdOrByName("PublishedCondition_textbox").value="";
	    	selectCondition();
		});
		if(eventType == '11648804')
		{
			getElementByIdOrByName("PublishedConditionLabel").style.display = "";
			getElementByIdOrByName("PublishedConditionField").style.display = "";
			getElementByIdOrByName("conditionPageLabel").style.display = "";
			getElementByIdOrByName("conditionPageField").style.display = "";
			getElementByIdOrByName("onFailLabel").style.display = "";
			getElementByIdOrByName("onFailField").style.display = "";

			JDecisionSupport.setAnswer("OnFailureToCreateInvestigation","2",  function(data){
				var onFailValue = getSelectedIndexText(getElementByIdOrByName("onfail"), "2")
				getElementByIdOrByName("onfail_textbox").value=onFailValue;
				setSelectedIndex(getElementByIdOrByName("onfail"), "2");

				$j($j("#onfail").parent().parent()).find(":input").attr("disabled", true);
				$j($j("#onfail").parent().parent()).find("img").attr("disabled", true);
				$j($j("#onfail").parent().parent()).find("img").attr("tabIndex", "-1");
			    
				//$j("#onFailLabel").parent().find("span").css("color", "#666666");
				getElementByIdOrByName("onFailLabel").style.color="#666666";
			});

			getElementByIdOrByName("RelatedLabel").style.display = "none";
			getElementByIdOrByName("RelatedField").style.display = "none";
			getElementByIdOrByName("ConditionLabel").style.display = "none";
			getElementByIdOrByName("ConditionField").style.display = "none";

			getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
			getElementByIdOrByName("invCriteriaSubSection").style.display = "";
		} else
		{
			getElementByIdOrByName("RelatedLabel").style.display = "";
			getElementByIdOrByName("RelatedField").style.display = "";
			getElementByIdOrByName("ConditionLabel").style.display = "";
			getElementByIdOrByName("ConditionField").style.display = "";

			getElementByIdOrByName("onFailLabel").style.display = "";
			getElementByIdOrByName("onFailField").style.display = "";

			JDecisionSupport.setAnswer("OnFailureToCreateInvestigation","",  function(data){
				var onFailValue = getSelectedIndexText(getElementByIdOrByName("onfail"), "")
				getElementByIdOrByName("onfail_textbox").value=onFailValue;
				setSelectedIndex(getElementByIdOrByName("onfail"), "");

				$j($j("#onfail").parent().parent()).find(":input").attr("disabled", false);
				$j($j("#onfail").parent().parent()).find("img").attr("disabled", false);
				$j($j("#onfail").parent().parent()).find("img").attr("tabIndex", "0");
			    
				//$j("#onFailLabel").parent().find("span").css("color", "#000");
				getElementByIdOrByName("onFailLabel").style.color="#000";
			});

			getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
			getElementByIdOrByName("PublishedConditionField").style.display = "none";
			getElementByIdOrByName("conditionPageLabel").style.display = "none";
			getElementByIdOrByName("conditionPageField").style.display = "none";

			getElementByIdOrByName("IdAdvancedSubSection").style.display = "";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
		}

		disableMarkAsReviewed();

		//getElementByIdOrByName("updateLabel").style.display = "";
		//getElementByIdOrByName("updateField").style.display = "";
		//getElementByIdOrByName("IdSubSection").style.display = "none";
		getElementByIdOrByName("subsec6").style.display = "";
	}else if(actionType != null && actionType=='3')
	{

		var isconfirm = true;
		if(selectedActionType != null && selectedActionType !="")
		{
			var confirmMsg="Changing the Action to Mark as Reviewed will clear all data, except Lab Criteria, from this tab. " +
			"If you continue with this action, you will lose any information " +
			"you have entered. Select OK to continue, or Cancel to not continue.";
			isconfirm = confirm(confirmMsg)
			if (!isconfirm)
				  setSelectedIndex(getElementByIdOrByName("ActionList"), selectedActionType);
		}
		if(isconfirm)
		{
			// display the investigation detail section and investigation default value section
			JDecisionSupport.getAllConditions(function(data){
		    	DWRUtil.removeAllOptions("PublishedCondition");
		    	DWRUtil.addOptions("PublishedCondition", data, "key", "value" );
		    	getElementByIdOrByName("PublishedCondition_textbox").value="";
		    	selectCondition();
			});
			selectedActionType = actionType;

			clearSelectedAction();
			clearSelectedDWRQuestions('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
			clearSelectedNotification();

			initActionUI();
			initIdSubSection();
			getElementByIdOrByName("Notes").value = "";
	        getElementByIdOrByName("defaultidNote").style.display = "none";
	        getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "none";
			getElementByIdOrByName("IdSubSection").style.display = "none";

			getElementByIdOrByName("RelatedLabel").style.display = "none";
			getElementByIdOrByName("RelatedField").style.display = "none";
			getElementByIdOrByName("ConditionLabel").style.display = "none";
			getElementByIdOrByName("ConditionField").style.display = "none";

			getElementByIdOrByName("onFailLabel").style.display = "none";
			getElementByIdOrByName("onFailField").style.display = "none";

			enableMarkAsReviewed();
			getElementByIdOrByName('PublishedCondition').value='';
			getElementByIdOrByName("PublishedCondition_textbox").value = '';
			getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
			getElementByIdOrByName("PublishedConditionField").style.display = "none";
			clearSelectedAdvancedInvQuestions('IdAdvancedInvSubSection', 'patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');
			getElementByIdOrByName("useInvLogicRadioNo").checked=true;
			getElementByIdOrByName("useInvLogicRadioYes").checked=false;
			getElementByIdOrByName("useInvLogic").value='N';
			
			getElementByIdOrByName("conditionPageLabel").style.display = "none";
			getElementByIdOrByName("conditionPageField").style.display = "none";
			getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "none";
			getElementByIdOrByName("timeFrameSubSection").style.display = "none";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
			getElementByIdOrByName("invCriteriaSubSection").style.display = "";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
			getElementByIdOrByName("IdSubSection").style.display = "none";
			getElementByIdOrByName("subsec6").style.display = "none";
		}
	}

}

function validateDSMRequiredFields()
{
    $j(".infoBox").hide();
    var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	if(getElementByIdOrByName("actionMode") == 'null'){
		actionMode ="";
	}
    var errors = new Array();
    var index = 0;
    var isError = false;
    var algorithNm = getElementByIdOrByName("AlgoNm");
    var eventType = getElementByIdOrByName("EVENT_TY");
    // Algorithm Criteria tab
	if(StartsWith(actionMode, "Create"))
	{
	    if( algorithNm != null && algorithNm.value == 0) {
	        errors[index++] = "Algorithm Name is a required field";
	        getElementByIdOrByName("algo").style.color="990000";
	        isError = true;
	    }
	    else {
	        getElementByIdOrByName("algo").style.color="black";
	    }

	    if( eventType != null && eventType.value == "") {
	        errors[index++] = "Event Type is a required field";
	        getElementByIdOrByName("eventT").style.color="990000";
	        isError = true;
	    }
	    else {
	        getElementByIdOrByName("eventT").style.color="black";
	    }
	}
	
// jayasudha has added to restrict the null value when action mode is edit 30-01-2017 .
	
	if(StartsWith(actionMode, "Edit"))
	{
	    if( algorithNm != null && algorithNm.value == 0) {
	        errors[index++] = "Algorithm Name is a required field";
	        getElementByIdOrByName("algo").style.color="990000";
	        isError = true;
	    }
	    else {
	        getElementByIdOrByName("algo").style.color="black";
	    }

	}
	
    var frequency = getElementByIdOrByName("Freq");

    if( frequency != null && frequency.value == "") {
        errors[index++] = "Frequency is a required field";
        getElementByIdOrByName("Freq").style.color="990000";
        isError = true;
    }
    else {
        getElementByIdOrByName("Freq").style.color="black";
    }

    var applyTo = getElementByIdOrByName("ApplyTo");
     if( applyTo != null && applyTo.value == "") {
         errors[index++] = "Apply to is a required field";
         getElementByIdOrByName("App").style.color="990000";
         isError = true;
     }
     else {
         getElementByIdOrByName("ApplyTo").style.color="black";
     }
     //Algorithm Action tab
     var actionValue = getElementByIdOrByName("ActionList");
     if( actionValue != null && actionValue.value == "") {
         errors[index++] = "Action is a required field";
         getElementByIdOrByName("ActionListL").style.color="990000";
         isError = true;
     }
     else {
         getElementByIdOrByName("ActionListL").style.color="black";
     }
     if(actionValue != null && actionValue.value != "")
     {
    	 if(actionValue.value=="3")
		 {
    		 var onFailure = getElementByIdOrByName("onfailReview");
    	     if( onFailure != null && onFailure.value == "") {
    	         errors[index++] = "On Failure to Mark as Reviewed is a required field";
    	         getElementByIdOrByName("onFailReviewLabel").style.color="990000";
    	         isError = true;
    	     }
    	     else {
    	         getElementByIdOrByName("onFailReviewLabel").style.color="black";
    	     }
    	     var condition = getElementByIdOrByName('PublishedCondition');
	    		 var useInvLogic =  getElementByIdOrByName('useInvLogic');
	    		   if( condition == null || condition.value == "" ) {
			    	 if (eventType != null && eventType.value == '11648804' && useInvLogic!=null && useInvLogic.value=='Y'){
			    		 errors[index++] = "Condition is a required field";
						 getElementByIdOrByName("PublishedConditionLabel").style.color="990000";
					         isError = true;
			    	 }
			    	 else{
			    		 getElementByIdOrByName("PublishedConditionLabel").style.color="black";
			    	 }
			     }
			 else {
				 getElementByIdOrByName("PublishedConditionLabel").style.color="black";
		 }
		 }
    	 else
    	 {
		     if(eventType != null && eventType.value == 'PHC236')
	    	 {
		    	 var relPage = getElementByIdOrByName("relatedPage");
			     if( relPage != null && relPage.value == "") {
			         errors[index++] = "Investigation Type(Related Page) is a required field";
			         getElementByIdOrByName("RelatedLabel").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("RelatedLabel").style.color="black";
			     }

			     var condition = getElementByIdOrByName('conditionList');
			     if( condition != null && condition.value == "") {
			         errors[index++] = "Conditions is a required field";
			         getElementByIdOrByName("ConditionLabel").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("ConditionLabel").style.color="black";
			     }

			     var onFailure = getElementByIdOrByName("onfail");
			     if( onFailure != null && onFailure.value == "") {
			         errors[index++] = "On Failure to Create Investigation is a required field";
			         getElementByIdOrByName("onFailLabel").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("onFailLabel").style.color="black";
			     }
	    	 }else if(eventType != null && eventType.value == '11648804'){
	    		 var condition = getElementByIdOrByName('PublishedCondition');
			     if( condition != null && condition.value == "") {
			    	 errors[index++] = "Condition is a required field";
			         getElementByIdOrByName("PublishedConditionLabel").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("PublishedConditionLabel").style.color="black";
			     }
			     var onFailure = getElementByIdOrByName("onfail");
			     if( onFailure != null && onFailure.value == "") {
			         errors[index++] = "On Failure to Create Investigation is a required field";
			         getElementByIdOrByName("onFailLabel").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("onFailLabel").style.color="black";
			     }



	}

	    	 }

		     if(actionValue != null && actionValue.value != "" && actionValue.value == "2")
		     {
			     var failNot = getElementByIdOrByName("onFailNot");
			     if( failNot != null && failNot.value == "") {
			         errors[index++] = "On Failure to Create Notification is a required field";
			         getElementByIdOrByName("onFailNotL").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("onFailNotL").style.color="black";
			     }
			     var notiComment = getElementByIdOrByName("NotComment");
			     if( notiComment != null && notiComment.value == "") {
			         errors[index++] = "Notification Comments is a required field";
			         getElementByIdOrByName("NotCommentL").style.color="990000";
			         isError = true;
			     }
			     else {
			         getElementByIdOrByName("NotCommentL").style.color="black";
			     }

			     if( onFailure != null && onFailure.value != "" &&  failNot != null && failNot.value != "")
			     {
			    	 if(onFailure.value == 'ROLL_BACK_ALL' && failNot.value != 'ROLL_BACK_ALL')
				    	{
				    		 errors[index++] = "The values you have selected for the On Failure to Create Investigation and On Failure to Create Notification are in conflict. Please update these values and re-submit.";
				    		 isError = true;
				    	}
			    	 if(onFailure.value == 'RETAIN_EVENT' && failNot.value == 'ROLL_BACK_ALL')
				    	{
				    		 errors[index++] = "The values you have selected for the On Failure to Create Investigation and On Failure to Create Notification are in conflict. Please update these values and re-submit.";
				    		 isError = true;
				    	}
			     }
		    }

    	 //Check Advanced Criteria Logic for Lab - Apply Timeframe fields if Yes
    	 if(eventType != null && eventType.value == '11648804'){
    	      			     var useEventDateLogicYesNode = getElementByIdOrByName('useEventDateLogicRadioYes');
	 	                     if (useEventDateLogicYesNode.checked) {
	 			    	 var theEventDate = $j('#EventDateTypeSel').val();
	 			     	 if (theEventDate == null || theEventDate == "") {
	 			         errors[index++] = "Event Date is required if Apply Timeframe Logic is Yes";
	 			         getElementByIdOrByName("EventDateTypeSelL").style.color="990000";
	 			         isError = true;
	 			     	}
	 			     	else {
	 			         	getElementByIdOrByName("EventDateTypeSelL").style.color="black";
	 			     	}

	 			         var theTimeframeOperator = $j('#TimeFrameOpSel :selected').val();
	 			         var theTimeFrameDays = $j('#TimeFrameDays').val();
	 			     	 if ((theTimeframeOperator  != null && theTimeframeOperator  == "") ||
	 			     	 	(theTimeFrameDays  != null && theTimeFrameDays  == "")) {
	 			     	 	if (theTimeframeOperator  == null || theTimeframeOperator  == "")
	 			         		errors[index++] = "Timeframe Operator (i.e. '<=' ) is required if Apply Timeframe Logic is Yes";
	 			         	if (theTimeFrameDays  != null && theTimeFrameDays  == "")
	 			         		errors[index++] = "Timeframe Days (i.e. '30' ) is required if Apply Timeframe Logic is Yes";
	 			         	getElementByIdOrByName("TimeFrameOpSelL").style.color="990000";
	 			         	isError = true;
	 			     	}
	 			     	else {
	 			         	getElementByIdOrByName("TimeFrameOpSelL").style.color="black";
			     	}
	 	     }
         }

     }
    if(isError) {
        displayGlobalErrorMessage(errors);
    }
    return isError;
}

function changeDarkBGColor(subsectionNm)
{
	 var t = getElementByIdOrByName(subsectionNm);
	 //t.style.display = "block";
	 for (var i = 0; i < 3; i++){
	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}
	 
	 if(subsectionNm=="IdSubSection")
		 changeHeaderBackgroundColor();
		 
}

function changeHeaderBackgroundColor(){
	getElementByIdOrByName("IdSubSection").getElementsByTagName("tr")[1].setAttribute("style","background-color:''")
}

function changeElrSectionDarkBGColor()
{
	 var t = getElementByIdOrByName("ElrIdAdvancedSubSection");
	 t.style.display = "";
	 for (var i = 0; i < 4; i++){
	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}
	 var numericResultTable = getElementByIdOrByName("numericResultTable");
	 numericResultTable.style.display = "";
	 $j(numericResultTable).find("tr").css("background-color","#BCD4F5");
	 
	 
	 var textResultTable = getElementByIdOrByName("textResultTable");
	 textResultTable.style.display = "";
	 $j(textResultTable).find("tr").css("background-color","#BCD4F5");
	 
/*	 if(document.getElementById("patternIdElrAdvancedSubSection1")!=undefined && document.getElementById("patternIdElrAdvancedSubSection1")!=null)	
		 document.getElementById("patternIdElrAdvancedSubSection1").setAttribute("style","background-color:''");*/
}

function changeElrSectionLightBGColor()
{
	 var t = getElementByIdOrByName("ElrIdAdvancedSubSection");
	 t.style.display = "";
	 for (var i = 0; i < 4; i++){
	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	
	
	 var numericResultTable = getElementByIdOrByName("numericResultTable");
	 numericResultTable.style.display = "";
	 $j(numericResultTable).find("tr").css("background-color","#DCE7F7");
	 
	 var textResultTable = getElementByIdOrByName("textResultTable");
	 textResultTable.style.display = "";
	 $j(textResultTable).find("tr").css("background-color","#DCE7F7");
}

function changeLightBGColorOfDefaultBatch(behVal, valueVal, questionVal)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	if(StartsWith(actionMode, "Create")){
		var queListNode = getElementByIdOrByName('questionList_textbox');
		var behaviorNode = getElementByIdOrByName('behavior_textbox');

		var behNode = jQuery.trim(behaviorNode.value);
	  	var queVal = jQuery.trim(queListNode.value);

		if(behVal.value != null && behVal.value != "" && behVal.value != 'undefined'){
			behNode= behVal.value;
		}else if(behVal.value != null && behVal.value == "" && behVal.value != 'undefined')
			behNode= behVal.value;
		if(questionVal.value != null && questionVal.value != "" && questionVal.value != 'undefined'){
			queVal= questionVal.value;
		}else if(questionVal.value != null && questionVal.value == "" && questionVal.value != 'undefined')
			queVal= questionVal.value;
	  	if ( (behNode == null || behNode == "") &&
	  		(queVal == null || queVal == "") ) {
	   	var t = getElementByIdOrByName("IdSubSection");
	  	 	t.style.display = "";
	   	for (var i = 0; i < 4; i++){
	   	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	   	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	    	}
	  	}
	}
}

function changeLightBGColorOfAdvancedBatch(logVal, valueVal, questionVal)
{
	var queListNode = getElementByIdOrByName('questionAdvList_textbox');
	var logicNode = getElementByIdOrByName('advLogicList_textbox');

	var logNode = jQuery.trim(logicNode.value);
  	var queVal = jQuery.trim(queListNode.value);

	if(logVal.value != null && logVal.value != "" && logVal.value != 'undefined'){
		logNode= logVal.value;
	}else if(logVal.value != null && logVal.value == "" && logVal.value != 'undefined')
		logNode= logVal.value;
	if(questionVal.value != null && questionVal.value != "" && questionVal.value != 'undefined'){
		queVal= questionVal.value;
	}else if(questionVal.value != null && questionVal.value == "" && questionVal.value != 'undefined')
		queVal= questionVal.value;
  	if ( (logNode == null || logNode == "") &&
  		(queVal == null || queVal == "") ) {
   	var t = getElementByIdOrByName("IdAdvancedSubSection");
  	 	t.style.display = "";
   	for (var i = 0; i < 4; i++){
   	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
   	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
    	}
  	}
}

function changeLightBGColorOfAdvancedInvBatch(logVal, valueVal, questionVal)
{
	var queListNode = getElementByIdOrByName('questionAdvInvList_textbox');
	var logicNode = getElementByIdOrByName('advInvLogicList_textbox');

	var logNode = jQuery.trim(logicNode.value);
  	var queVal = jQuery.trim(queListNode.value);

	if(logVal.value != null && logVal.value != "" && logVal.value != 'undefined'){
		logNode= logVal.value;
	}else if(logVal.value != null && logVal.value == "" && logVal.value != 'undefined')
		logNode= logVal.value;
	if(questionVal.value != null && questionVal.value != "" && questionVal.value != 'undefined'){
		queVal= questionVal.value;
	}else if(questionVal.value != null && questionVal.value == "" && questionVal.value != 'undefined')
		queVal= questionVal.value;
  	if ( (logNode == null || logNode == "") &&
  		(queVal == null || queVal == "") ) {
   	var t = getElementByIdOrByName("IdAdvancedInvSubSection");
  	 	t.style.display = "";
   	for (var i = 0; i < 4; i++){
   	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
   	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
    	}
  	}
}

function getDWRQuestions(selectElt)
{
	var formCd = getElementByIdOrByName("relatedPage").value;
    if (formCd != null && formCd != ""){
	    JDecisionSupport.getDwrQuestionsList(formCd, function(data) {
	    	DWRUtil.removeAllOptions("questionList");
	    	DWRUtil.addOptions("questionList", data, "key", "value" );
	    });
	    getElementByIdOrByName("questionList_textbox").disabled = false;
    	getElementByIdOrByName("questionList_button").disabled = false;
    	
    	JDecisionSupport.getAdvDwrQuestionsList(formCd, function(data) {
	    	DWRUtil.removeAllOptions("questionAdvList");
	    	DWRUtil.addOptions("questionAdvList", data, "key", "value" );
	    });
	    getElementByIdOrByName("questionAdvList_textbox").disabled = false;
    	getElementByIdOrByName("questionAdvList_button").disabled = false;
    	
   }else{
	   getElementByIdOrByName("questionList_textbox").disabled = true;
       getElementByIdOrByName("questionList_button").disabled = true;
       
       getElementByIdOrByName("questionAdvList_textbox").disabled = true;
   	   getElementByIdOrByName("questionAdvList_button").disabled = true;
   }


}

function getElrDWRQuestions(pageKey)
{
    if (pageKey != null && pageKey != ""){
	    JDecisionSupport.getDwrQuestionsList(pageKey, function(data) {
	    	DWRUtil.removeAllOptions("questionList");
	    	//DWRUtil.removeAllOptions("questionAdvList");
	    	DWRUtil.addOptions("questionList", data, "key", "value" );
	    	//DWRUtil.addOptions("questionAdvList", data, "key", "value" );
	    });
	    getElementByIdOrByName("questionList_textbox").disabled = false;
    	getElementByIdOrByName("questionList_button").disabled = false;
    	//getElementByIdOrByName("questionAdvList_textbox").disabled = false;
    	//getElementByIdOrByName("questionAdvList_button").disabled = false;
   }else{
	   getElementByIdOrByName("questionList_textbox").disabled = true;
       getElementByIdOrByName("questionList_button").disabled = true;
       //getElementByIdOrByName("questionAdvList_textbox").disabled = true;
       //getElementByIdOrByName("questionAdvList_button").disabled = true;
   }


}

function getConditionDropDown(relatedPage)
{
	getElementByIdOrByName('conditionList-selectedValues').innerHTML = "";
	if(relatedPage.value == null || relatedPage.value=="")
	{
		getElementByIdOrByName("conditionList").options.length = 0;
		getElementByIdOrByName('conditionList-selectedValues').innerHTML = "";
	}else{
		JDecisionSupport.getConditionDropDown(relatedPage.value, function(data){
			DWRUtil.removeAllOptions("conditionList");
	   		DWRUtil.addOptions("conditionList", data, "key", "value" );
		});
	}
}

function getConditionPageValue(publishedCondition)
{
	getElementByIdOrByName('conditionRelatedPage').innerHTML = "";
	if(publishedCondition.value == null || publishedCondition.value=="")
	{
		getElementByIdOrByName("relatedPage_textbox").value="";
		getElementByIdOrByName('conditionRelatedPage').innerHTML = "";
	}else{
		JDecisionSupport.getPublishedConditionPage(publishedCondition.value, function(data){
			var pageKey = data.key;
			var pageValue = data.value;
			getElementByIdOrByName("relatedPage_textbox").value=pageValue;
			setSelectedIndex(getElementByIdOrByName("relatedPage"), pageKey);
			getElementByIdOrByName("conditionRelatedPage").innerHTML = pageValue;
		});
	}
}

function getElrConditionPageValue(publishedCondition)
{
	getElementByIdOrByName('conditionRelatedPage').innerHTML = "";
	if(publishedCondition.value == null || publishedCondition.value=="")
	{
		getElementByIdOrByName("relatedPage_textbox").value="";
		getElementByIdOrByName('conditionRelatedPage').innerHTML = "";
	}else{
		JDecisionSupport.getPublishedConditionPage(publishedCondition.value, function(data){
			var pageKey = data.key;
			var pageValue = data.value;
			getElementByIdOrByName("relatedPage_textbox").value=pageValue;
			setSelectedIndex(getElementByIdOrByName("relatedPage"), pageKey);
			getElementByIdOrByName("conditionRelatedPage").innerHTML = pageValue;

			 getElrDWRQuestions(pageKey);
			 clearSelectedDWRQuestions('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
	         getElementByIdOrByName("Notes").value = "";
		});
	}
}

function getDWRValue(question){
   checkDuplicateDefault(question)
   var fieldType ="";
   var valueSet = "";
   if(question.value != null || question.value != "")
   {
	   JDecisionSupport.getDWRQuestionProperties(question.value, function(data){
		   fieldType = data;

		   if(data == 'Text'){
			   getElementByIdOrByName("Val_text").style.visibility="visible";
			   getElementByIdOrByName("text").style.visibility="visible";
			   getElementByIdOrByName("text").style.display = "";

			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";
			   //getElementByIdOrByName("Num_Structure").style.display = "none";
			   $j("#Val_text").parent().find(":input").attr("disabled", false);

			   $j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_date").parent().find(":input").val("");
			   $j($j("#valueList2_textbox").parent().parent()).find(":input").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j($j("#valueList1_textbox").parent().parent()).find(":input").val("");
			   $j("#Val_lit").parent().find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j("#Val_Str").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }else if(data == 'TextArea'){
			   getElementByIdOrByName("Val_textArea").style.visibility="visible";
			   getElementByIdOrByName("Val_textArea").style.display="";

			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";
			   $j("#Val_textArea").parent().find(":input").attr("disabled", false);

			   $j("#Val_text").parent().find(":input").val("");
			   $j("#Val_date").parent().find(":input").val("");
			   $j($j("#valueList2_textbox").parent().parent()).find(":input").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j($j("#valueList1_textbox").parent().parent()).find(":input").val("");
			   $j("#Val_lit").parent().find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }else if(data == 'Date'){
			 var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
				currentDateNode.checked=true;
			   getElementByIdOrByName("Val_date").style.visibility="visible";
			   //$j("#Val_date").children().show();
			   getElementByIdOrByName("date").style.visibility="visible";
			   getElementByIdOrByName("date").style.display = "";
			   getElementByIdOrByName("Val_dateIcon").style.visibility="visible";

			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";

			   $j("#Part_Per_Div").children().hide();
			   //getElementByIdOrByName("Part_Per_Div").style.display = "none";
			   //getElementByIdOrByName("PartPerText").style.visibility="hidden";
			   //getElementByIdOrByName("PartPerIcon").style.visibility="hidden";
			  // getElementByIdOrByName("PartPerCodeLookupButton").style.visibility="hidden";
			  // getElementByIdOrByName("clearPartPer").className="none";
			  // getElementByIdOrByName("selectedPartPer").className="none";
	        	  // getElementByIdOrByName("PartPerSearchControls").className="none";
			  // getElementByIdOrByName("PartPerS").className="none"; //white line

			   changeDarkBGColor('IdSubSection');



			  $j("#Val_date").parent().find(":input").attr("disabled", false);
			  $j("#Val_dateIcon").parent().find("img").attr("disabled", false);
			  $j("#Val_dateIcon").parent().find("img").attr("tabIndex", "0");
			  

			   //$j("#Val_text").parent().find(":input").val("");
			   $j("#Val_text").val("");
			   //$j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_textArea").val("");
			   //$j($j("#valueList2_textbox").parent().parent()).find(":input").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   //$j($j("#valueList1_textbox").parent().parent()).find(":input").val("");
			   //$j("#Val_lit").parent().find(":input").val("");
			     $j("#Val_lit").val("");
			   //$j("#Val_num").parent().find(":input").val("");
			  // $j("#Val_num").val("");
			     $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }else if(data.indexOf("Part")>=0){ //PartPer or PartOrg
			   getElementByIdOrByName("Part_Per_Div").style.display = "";

			   //dwr.util.setValue("PartPer", "");
			   //dwr.util.setValue("PartPerError", "");
			   $j("#Part_Per_Div").children().show();
			   $j("#PartPerSearchControls").children().show();
			   //getElementByIdOrByName("PartPerIcon").style.visibility="visible";

			   getElementByIdOrByName("clearPartPer").className="none";
			   getElementByIdOrByName("clearPartPer").setAttribute("class","none");
			   
			    $j("#PartPerCodeClearButton").hide();
			    $j("#PartPerS").children().hide();
			   //getElementByIdOrByName("PartPerSearchControls").className="visible";


			   $j("#PartPerIcon").val("Search");
			   $j("#PartPerIcon").css("color", "#000");
			   $j("#PartPerIcon").attr("disabled", false);
			   $j("#PartPerText").attr("disabled", false);
			   $j("#PartPerCodeLookupButton").val("Quick Code Lookup");
			   $j("#PartPerCodeLookupButton").css("color", "#000");
			   $j("#PartPerCodeLookupButton").attr("disabled", false);
			   $j("#PartEntityType").val(fieldType); //PartPer or PartOrg
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";

 			   $j("#Val_lit").val("");
			   $j("#Val_text").val("");
			   $j("#Val_textArea").val("");
			   $j("#Val_date").val("");
			  // $j($j("#valueList2").parent().parent()).find(":input").val("");
			  // getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			  // $j($j("#valueList1").parent().parent()).find(":input").val("");
			   //$j("#Val_num").parent().find(":input").val("");
			   //$j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   } else if(data.indexOf("MULTISELECT")>=0){
			   getElementByIdOrByName("m_sel").style.visibility="visible";
			   getElementByIdOrByName("m_sel").style.display = "";
			   getElementByIdOrByName("valueList2").style.visibility="visible";

			   $j("#Part_Per_Div").children().hide();
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";

			   $j($j("#valueList2").parent().parent()).find(":input").attr("disabled", false);
			   $j($j("#valueList2").parent().parent()).find("img").attr("disabled", false);
			   $j($j("#valueList2").parent().parent()).find("img").attr("tabIndex", "0");
			   

			   valueSet = data.substring(data.indexOf("=") + 1, data.length);

			   //$j("#Val_text").parent().find(":input").val("");
			   $j("#Val_text").val("");
			   //$j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_textArea").val("");
			   //$j("#Val_date").parent().find(":input").val("");
			   $j("#Val_date").val("");
			   $j($j("#valueList1_textbox").parent().parent()).find(":input").val("");
			   $j("#Val_lit").parent().find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }else if(data.indexOf("SINGLESELECT")>=0){
			   getElementByIdOrByName("s_sel").style.visibility="visible";
			   getElementByIdOrByName("valueList1").style.visibility="visible";
			   getElementByIdOrByName("s_sel").style.display = "";

			    $j("#Part_Per_Div").children().hide();
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";
			   $j($j("#valueList1").parent().parent()).find(":input").attr("disabled", false);
			   $j($j("#valueList1").parent().parent()).find("img").attr("disabled", false);
			   $j($j("#valueList1").parent().parent()).find("img").attr("tabIndex", "0");
			   
			   valueSet = data.substring(data.indexOf("=") + 1, data.length);

			   //$j("#Val_text").parent().find(":input").val("");
			   $j("#Val_text").val("");
			   //$j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_textArea").val("");
			   $j("#Val_date").parent().find(":input").val("");
			   $j($j("#valueList2_textbox").parent().parent()).find(":input").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j("#Val_lit").parent().find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");

		   }else if(data.indexOf("codedUnit")>=0){
			   getElementByIdOrByName("Num_Coded").style.visibility="visible";
			   getElementByIdOrByName("Val_code").style.visibility="visible";
			   getElementByIdOrByName("Num_Coded").style.display = "";

			   $j("#Part_Per_Div").children().hide();
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("Num_Literal").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   $j("#Val_num").parent().find(":input").attr("disabled", false);
			   $j($j("#Val_code").parent().parent()).find(":input").attr("disabled", false);
			   $j($j("#Val_code").parent().parent()).find("img").attr("disabled", false);
			   $j($j("#Val_code").parent().parent()).find("img").attr("tabIndex", "0");
			   

			   valueSet = data.substring(data.indexOf("=") + 1, data.length);

			   //$j("#Val_text").parent().find(":input").val("");
			   $j("#Val_text").val("");
			  // $j("#Val_textArea").parent().find(":input").val("");
			    $j("#Val_textArea").val("");
			  // $j("#Val_date").parent().find(":input").val("");
			   $j("#Val_date").val("");
			   $j($j("#valueList2").parent().parent()).find(":input").val("");
			   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j($j("#valueList1").parent().parent()).find(":input").val("");
			   $j("#Val_lit").parent().find(":input").val("");

		   }else if(data.indexOf("unitValue")>=0){
			   getElementByIdOrByName("Num_Literal").style.visibility="visible";
			   getElementByIdOrByName("Num_Literal").style.display = "";

			   $j("#Part_Per_Div").children().hide();
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   $j("#Val_lit").parent().find(":input").attr("disabled", false);

			   //$j("#Val_text").parent().find(":input").val("");
			   $j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_date").parent().find(":input").val("");
			   $j($j("#valueList2").parent().parent()).find(":input").val("");
			   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j($j("#valueList1").parent().parent()).find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }else if(data == 'Numeric'){

			   $j("#Part_Per_Div").children().hide();
			   getElementByIdOrByName("Num_Literal").style.display = "";
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   $j("#Val_lit").parent().find(":input").attr("disabled", false);

			   $j("#Val_text").parent().find(":input").val("");
			   $j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_date").parent().find(":input").val("");
			   $j($j("#valueList2").parent().parent()).find(":input").val("");
			   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j($j("#valueList1").parent().parent()).find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }else if(data.indexOf("nMask")>=0){
			   numericMask = data.substring(data.indexOf("=") + 1, data.length);
			   getElementByIdOrByName("Num_Literal").style.visibility="visible";
			   getElementByIdOrByName("Num_Literal").style.display = "";

			   $j("#Part_Per_Div").children().hide();
			   getElementByIdOrByName("text").style.display = "none";
			   getElementByIdOrByName("Val_textArea").style.display = "none";
			   getElementByIdOrByName("date").style.display = "none";
			   getElementByIdOrByName("m_sel").style.display = "none";
			   getElementByIdOrByName("Num_Coded").style.display = "none";
			   getElementByIdOrByName("s_sel").style.display = "none";
			   $j("#Val_lit").parent().find(":input").attr("disabled", false);

			   $j("#Val_text").parent().find(":input").val("");
			   $j("#Val_textArea").parent().find(":input").val("");
			   $j("#Val_date").parent().find(":input").val("");
			   $j($j("#valueList2").parent().parent()).find(":input").val("");
			   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
			   getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
			   $j($j("#valueList1").parent().parent()).find(":input").val("");
			   $j("#Val_num").parent().find(":input").val("");
			   $j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
		   }
		   
		   //if a date element was selected first, the checkbox continue checked even when the element is not a date. The element should be unchecked.
		   if(data != 'Date'){
				 var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
				 currentDateNode.checked=false;
			}
		   
		   if(valueSet.length >0){
			   JDecisionSupport.getDWRValueList(valueSet, function(data){
				   if(fieldType != null && fieldType.indexOf("SINGLESELECT")>=0){
					   DWRUtil.removeAllOptions("valueList1");
					   DWRUtil.addOptions("valueList1", data, "key", "value" );
				   }else if(fieldType != null && fieldType.indexOf("MULTISELECT")>=0){
					   DWRUtil.removeAllOptions("valueList2");
					   DWRUtil.addOptions("valueList2", data, "key", "value" );
				   }else if(fieldType != null && fieldType.indexOf("codedUnit")>=0){
					   DWRUtil.removeAllOptions("Val_code");
					   DWRUtil.addOptions("Val_code", data, "key", "value" );
				   }
			   });
		   }
	   });
   }

}



function onLoadFunction()
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode").value;
	if(getElementByIdOrByName("actionMode") == 'null'){
		actionMode ="";
	}
	// onload question dropdown should be null
	if(StartsWith(actionMode, "Create"))
	{
		var count = 0;
		for (i = 0; i < getElementByIdOrByName("conditionList").options.length; i++) {
			if (getElementByIdOrByName("conditionList").options[i].selected && getElementByIdOrByName("conditionList").options[i].text != "") {
				count++;
			}
		}
		// if related page DD is blank, make condition DD blank
		var relatedPageVal = getElementByIdOrByName("relatedPage").value;
		if(relatedPageVal == null || relatedPageVal == "" ){
			getElementByIdOrByName("conditionList").options.length = 0;
			getElementByIdOrByName('conditionList-selectedValues').innerHTML = "";
		}

		if(count == 0){
			getElementByIdOrByName("questionList_textbox").disabled = true;
			getElementByIdOrByName("questionList_button").disabled = true;
		}

		// Disable the Sending System.
		getElementByIdOrByName("sendingSystem").style.display = "none";
		getElementByIdOrByName("laboratory").style.display = "none";

		//Disable all fields from the Action tab
		$j($j("#ActionList").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#ActionList").parent().parent()).find("img").attr("disabled", true);
		$j($j("#ActionList").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#ActionListL").parent().find("span").css("color", "#666666");

		//Disable Event date on create/edit load
		$j($j("#EventDateTypeSel").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#EventDateTypeSel").parent().parent()).find("img").attr("tabIndex", "-1");
		$j($j("#EventDateTypeSel").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#EventDateTypeSelL").parent().find("span").css("color", "#666666");

		getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
		getElementByIdOrByName("PublishedConditionField").style.display = "none";
		getElementByIdOrByName("conditionPageLabel").style.display = "none";
		getElementByIdOrByName("conditionPageField").style.display = "none";
		getElementByIdOrByName("onFailReviewLabel").style.display = "none";
		getElementByIdOrByName("onFailReviewField").style.display = "none";
		getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
		getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";

		getElementByIdOrByName("RelatedLabel").style.display = "none";
		getElementByIdOrByName("RelatedField").style.display = "none";
		getElementByIdOrByName("ConditionLabel").style.display = "none";
		getElementByIdOrByName("ConditionField").style.display = "none";

		getElementByIdOrByName("onFailLabel").style.display = "none";
		getElementByIdOrByName("onFailField").style.display = "none";
		//getElementByIdOrByName("updateLabel").style.display = "none";
		//getElementByIdOrByName("updateField").style.display = "none";
		getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
		getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "none";
		getElementByIdOrByName("invCriteriaSubSection").style.display = "none";
		getElementByIdOrByName("timeFrameSubSection").style.display = "none";
		getElementByIdOrByName("Notes").value = "";
	    getElementByIdOrByName("defaultidNote").style.display = "none";
	    getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "none";
		getElementByIdOrByName("IdSubSection").style.display = "none";
		getElementByIdOrByName("subsec6").style.display = "none";

		// Disabling Basic Criteria Event Type, Freq, Apply To... fields
		//$j($j("#EVENT_TY").parent().parent()).find(":input").attr("disabled", true);
		//$j($j("#EVENT_TY").parent().parent()).find("img").attr("disabled", true);
		//$j("#eventT").parent().find("span").css("color", "#666666");

		$j($j("#Freq").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#Freq").parent().parent()).find("img").attr("disabled", true);
		$j($j("#Freq").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#frq").parent().find("span").css("color", "#555555");
		$j($j("#ApplyTo").parent().parent()).find(":input").attr("disabled", true);
		$j("#App").parent().find("span").css("color", "#555555");

		$j($j("#UpdateAct").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#UpdateAct").parent().parent()).find("img").attr("disabled", true);
		$j($j("#UpdateAct").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#updateLabel").parent().find("span").css("color", "#666666");

		$j("#queueAppLabel").parent().find("span").css("color", "#666666");

		getElementByIdOrByName("Val_text").style.visibility="visible";
		getElementByIdOrByName("Val_textArea").style.visibility="hidden";
		getElementByIdOrByName("Val_textArea").style.display = "none";
		getElementByIdOrByName("date").style.display = "none";
		getElementByIdOrByName("m_sel").style.display = "none";
		getElementByIdOrByName("s_sel").style.display = "none";
		getElementByIdOrByName("Num_Coded").style.display = "none";
		getElementByIdOrByName("Num_Literal").style.display = "none";

		$j($j("#onfailReview").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#onfailReview").parent().parent()).find("img").attr("disabled", true);
		$j($j("#onfailReview").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#onFailReviewLabel").parent().find("span").css("color", "#666666");
		$j($j("#onFailNot").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#onfailReview").parent().parent()).find("img").attr("disabled", true);
		$j($j("#onfailReview").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#onFailNotL").parent().find("span").css("color", "#666666");
		getElementByIdOrByName("advVal_text").style.visibility="visible";
		getElementByIdOrByName("advVal_textArea").style.visibility="hidden";
		getElementByIdOrByName("advVal_textArea").style.display = "none";
		getElementByIdOrByName("advDate").style.display = "none";
		getElementByIdOrByName("advM_sel").style.display = "none";
		getElementByIdOrByName("advS_sel").style.display = "none";
		getElementByIdOrByName("advNum_Coded").style.display = "none";
		getElementByIdOrByName("advNum_Literal").style.display = "none";
		
		getElementByIdOrByName("advInvVal_text").style.visibility="visible";
		getElementByIdOrByName("advInvVal_textArea").style.visibility="hidden";
		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
		getElementByIdOrByName("advInvDate").style.display = "none";
		getElementByIdOrByName("advInvM_sel").style.display = "none";
		getElementByIdOrByName("advInvS_sel").style.display = "none";
		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
		
		getElementByIdOrByName("numericResultHigh_div").style.visibility="hidden";
		getElementByIdOrByName("numericResultHigh_div").style.display = "none";
		

	}else if(StartsWith(actionMode, "Edit")){
		var actionType = getElementByIdOrByName("ActionList").value;
		var eventType = getElementByIdOrByName("EVENT_TY").value;

		if(eventType == "11648804")
		{
			getElementByIdOrByName("sendingSystem").style.display = "none";
			getElementByIdOrByName("laboratory").style.display = "";
	       // getElementByIdOrByName("defaultidNote").style.display = "";//Fixing issue ND-10174: removing Notes section from WDS Lab Report Default Values
	        getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "";
		 var useEventDateLogic = getElementByIdOrByName('useEventDateLogic');
	 	 enableDisableTimeLogic (useEventDateLogic);
			//Enable Event date
		$j($j("#EventDateTypeSel").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#EventDateTypeSel").parent().parent()).find("img").attr("disabled", true);
		$j($j("#EventDateTypeSel").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#EventDateTypeSelL").parent().find("span").css("color", "#666666");

		}else if(eventType == "PHC236")
		{
			getElementByIdOrByName("sendingSystem").style.display = "";
			getElementByIdOrByName("laboratory").style.display = "none";
			getElementByIdOrByName("Notes").value = "";
	        getElementByIdOrByName("defaultidNote").style.display = "none";
	        getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "none";
	        getElementByIdOrByName("invCriteriaSubSection").style.display = "none";
		}
		if(actionType != null)
		{
			if(actionType == "3")
			{
				JDecisionSupport.getAllConditions(function(data){
			    	DWRUtil.removeAllOptions("PublishedCondition");
			    	DWRUtil.addOptions("PublishedCondition", data, "key", "value" );
			    	selectCondition();
				});
				getElementByIdOrByName("onFailReviewLabel").style.display = "";
				getElementByIdOrByName("onFailReviewField").style.display = "";
				$j($j("#onfailReview").parent().parent()).find(":input").attr("disabled", true);
				$j($j("#onfailReview").parent().parent()).find("img").attr("disabled", true);
				$j($j("#onfailReview").parent().parent()).find("img").attr("tabIndex", "-1");
				
				$j("#onFailReviewLabel").parent().find("span").css("color", "#666666");
				var useEventDateLogicNoNode =  getElementByIdOrByName('useEventDateLogicRadioNo');
				if(eventType == "11648804" && useEventDateLogicNoNode.checked){
				getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
				getElementByIdOrByName("PublishedConditionField").style.display = "none";
			   }else{
				getElementByIdOrByName("PublishedConditionLabel").style.display = "";
				getElementByIdOrByName("PublishedConditionField").style.display = "";
			   }

				getElementByIdOrByName("RelatedLabel").style.display = "none";
				getElementByIdOrByName("RelatedField").style.display = "none";

				getElementByIdOrByName("ConditionLabel").style.display = "none";
				getElementByIdOrByName("ConditionField").style.display = "none";

				getElementByIdOrByName("conditionPageLabel").style.display = "none";
				getElementByIdOrByName("conditionPageField").style.display = "none";

				getElementByIdOrByName("onFailLabel").style.display = "none";
				getElementByIdOrByName("onFailField").style.display = "none";

				getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
				getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
				getElementByIdOrByName("IdSubSection").style.display = "none";
				getElementByIdOrByName("subsec6").style.display = "none";
				
				var useAdvInvCriteria = getElementByIdOrByName('useInvLogicRadioYes');
				if(eventType == "11648804" && useAdvInvCriteria.checked){
					getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "";
				}

			}
			else
			{
				JDecisionSupport.getPublishedConditionDropDown(function(data){
			    	DWRUtil.removeAllOptions("PublishedCondition");
			    	DWRUtil.addOptions("PublishedCondition", data, "key", "value" );
			    	selectCondition();
				});
				if(eventType == "11648804")
				{
					getElementByIdOrByName("PublishedConditionLabel").style.display = "";
					getElementByIdOrByName("PublishedConditionField").style.display = "";

					getElementByIdOrByName("RelatedLabel").style.display = "none";
					getElementByIdOrByName("RelatedField").style.display = "none";

					getElementByIdOrByName("ConditionLabel").style.display = "none";
					getElementByIdOrByName("ConditionField").style.display = "none";

					getElementByIdOrByName("conditionPageLabel").style.display = "";
					getElementByIdOrByName("conditionPageField").style.display = "";

					getElementByIdOrByName("onFailLabel").style.display = "";
					getElementByIdOrByName("onFailField").style.display = "";

					$j($j("#onfail").parent().parent()).find(":input").attr("disabled", false);
					$j($j("#onfail").parent().parent()).find("img").attr("disabled", false);
					$j($j("#onfail").parent().parent()).find("img").attr("tabIndex", "0");
					
					//$j("#onFailLabel").parent().find("span").css("color", "#666666");
					getElementByIdOrByName("onFailLabel").style.color="#000";

					getElementByIdOrByName("onFailReviewLabel").style.display = "none";
					getElementByIdOrByName("onFailReviewField").style.display = "none";

					getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
					getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
					getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
					getElementByIdOrByName("IdSubSection").style.display = "";
					if(actionType == "1")
						getElementByIdOrByName("subsec6").style.display = "none";
					else if (actionType == "2")
						getElementByIdOrByName("subsec6").style.display = "";
				}else if(eventType == "PHC236")
				{
					getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
					getElementByIdOrByName("PublishedConditionField").style.display = "none";

					getElementByIdOrByName("RelatedLabel").style.display = "";
					getElementByIdOrByName("RelatedField").style.display = "";

					getElementByIdOrByName("ConditionLabel").style.display = "";
					getElementByIdOrByName("ConditionField").style.display = "";

					getElementByIdOrByName("conditionPageLabel").style.display = "none";
					getElementByIdOrByName("conditionPageField").style.display = "none";

					getElementByIdOrByName("onFailLabel").style.display = "";
					getElementByIdOrByName("onFailField").style.display = "";

					getElementByIdOrByName("onFailReviewLabel").style.display = "none";
					getElementByIdOrByName("onFailReviewField").style.display = "none";

					getElementByIdOrByName("IdAdvancedSubSection").style.display = "";
					getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
					getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
					getElementByIdOrByName("IdSubSection").style.display = "";
					getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "none";
					getElementByIdOrByName("defaultidNote").style.display = "none";
					if(actionType == "1")
						getElementByIdOrByName("subsec6").style.display = "none";
					else if (actionType == "2")
						getElementByIdOrByName("subsec6").style.display = "";
				}
			}
			// Disabling Basic Criteria Event Type, Freq, Apply To... fields
			//$j($j("#EVENT_TY").parent().parent()).find(":input").attr("disabled", true);
			//$j($j("#EVENT_TY").parent().parent()).find("img").attr("disabled", true);
			//$j("#eventT").parent().find("span").css("color", "#666666");

			$j($j("#Freq").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#Freq").parent().parent()).find("img").attr("disabled", true);
			$j($j("#Freq").parent().parent()).find("img").attr("tabIndex", "-1");
			
			$j("#frq").parent().find("span").css("color", "#666666");
			$j($j("#ApplyTo").parent().parent()).find(":input").attr("disabled", true);
			$j("#App").parent().find("span").css("color", "#666666");

			$j($j("#UpdateAct").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#UpdateAct").parent().parent()).find("img").attr("disabled", true);
			$j($j("#UpdateAct").parent().parent()).find("img").attr("tabIndex", "-1");
			
			$j("#updateLabel").parent().find("span").css("color", "#666666");

			$j("#queueAppLabel").parent().find("span").css("color", "#666666");

			$j($j("#onFailNot").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#onFailNot").parent().parent()).find("img").attr("disabled", true);
			$j($j("#onFailNot").parent().parent()).find("img").attr("tabIndex", "-1");
		    
		    $j("#onFailNotL").parent().find("span").css("color", "#666666");
			
		}

		for (i = 0; i < getElementByIdOrByName("conditionList").options.length; i++) {
			if (getElementByIdOrByName("conditionList").options[i].selected && getElementByIdOrByName("conditionList").options[i].text != "") {
				getElementByIdOrByName('conditionList-selectedValues').innerHTML = getElementByIdOrByName('conditionList-selectedValues').innerHTML + getElementByIdOrByName("conditionList").options[i].text;
			}
		}
		displaySelectedOptions(getElementByIdOrByName("sendSys"), 'sendSys-selectedValues');
		displaySelectedOptions(getElementByIdOrByName("selectedLaboratory"), 'laboratory-selectedValues');

		var actionVal = getElementByIdOrByName("actionVal");
		 if (actionVal != null && actionVal != undefined && actionVal.value == '1') {

				 getElementByIdOrByName("subsec6").style.display = "none";
		 }else if(actionVal != null && actionVal != undefined && actionVal.value == '2')
		 {
			 getElementByIdOrByName("subsec6").style.display = "";
		 }

		fillDefaultTable();
		getElementByIdOrByName("Val_text").style.visibility="visible";
		getElementByIdOrByName("Val_textArea").style.visibility="hidden";
		getElementByIdOrByName("Val_textArea").style.display = "none";
		getElementByIdOrByName("date").style.display = "none";
		getElementByIdOrByName("m_sel").style.display = "none";
		getElementByIdOrByName("s_sel").style.display = "none";
		getElementByIdOrByName("Num_Coded").style.display = "none";
		getElementByIdOrByName("Num_Literal").style.display = "none";
		getElementByIdOrByName("Part_Per_Div").style.display = "none";
		
		
		//$j("#Part_Per_Div").children().hide();

		if(eventType == "11648804")
		{
			fillElrAdvancedCriteriaTable();
			clearTestedResult();
			fillAdvancedInvCriteriaTable();
			getElementByIdOrByName("advInvVal_text").style.visibility="visible";
			getElementByIdOrByName("advInvVal_textArea").style.visibility="hidden";
			getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			getElementByIdOrByName("advInvDate").style.display = "none";
			getElementByIdOrByName("advInvM_sel").style.display = "none";
			getElementByIdOrByName("advInvS_sel").style.display = "none";
			getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			getElementByIdOrByName("advInvNum_Literal").style.display = "none";
		}
		else if (eventType == "PHC236")
			fillAdvancedCriteriaTable();
		getElementByIdOrByName("advVal_text").style.visibility="visible";
		getElementByIdOrByName("advVal_textArea").style.visibility="hidden";
		getElementByIdOrByName("advVal_textArea").style.display = "none";
		getElementByIdOrByName("advDate").style.display = "none";
		getElementByIdOrByName("advM_sel").style.display = "none";
		getElementByIdOrByName("advS_sel").style.display = "none";
		getElementByIdOrByName("advNum_Coded").style.display = "none";
		getElementByIdOrByName("advNum_Literal").style.display = "none";
		getElementByIdOrByName("numericResultHigh_div").style.display = "none";
		}else if(StartsWith(actionMode, "View"))
	{
		 var actionVal = getElementByIdOrByName("actionVal");
		 var eventTypeVal = getElementByIdOrByName("eventTypeVal");
		 if (actionVal != null && actionVal != undefined && actionVal.value == '1') {
			 if (eventTypeVal != null && eventTypeVal != undefined && eventTypeVal.value == '11648804')
			 {
				getElementByIdOrByName("PublishedConditionLabel").style.display = "";
				getElementByIdOrByName("PublishedConditionField").style.display = "";

				getElementByIdOrByName("RelatedLabel").style.display = "";
				getElementByIdOrByName("RelatedField").style.display = "";

				getElementByIdOrByName("ConditionLabel").style.display = "none";
				getElementByIdOrByName("ConditionField").style.display = "none";

				//getElementByIdOrByName("conditionPageLabel").style.display = "";
				//getElementByIdOrByName("conditionPageField").style.display = "";

				getElementByIdOrByName("onFailLabel").style.display = "";
				getElementByIdOrByName("onFailField").style.display = "";

				getElementByIdOrByName("onFailReviewLabel").style.display = "none";
				getElementByIdOrByName("onFailReviewField").style.display = "none";

				getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
				getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
				getElementByIdOrByName("IdSubSection").style.display = "";
				//getElementByIdOrByName("defaultidNote").style.display = "";//Fixing issue ND-10174: removing Notes section from WDS Lab Report Default Values
			 }else if (eventTypeVal != null && eventTypeVal != undefined && eventTypeVal.value == 'PHC236')
			 {
				getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
				getElementByIdOrByName("PublishedConditionField").style.display = "none";

				getElementByIdOrByName("RelatedLabel").style.display = "";
				getElementByIdOrByName("RelatedField").style.display = "";

				getElementByIdOrByName("ConditionLabel").style.display = "";
				getElementByIdOrByName("ConditionField").style.display = "";

				//getElementByIdOrByName("conditionPageLabel").style.display = "none";
				//getElementByIdOrByName("conditionPageField").style.display = "none";

				getElementByIdOrByName("onFailLabel").style.display = "";
				getElementByIdOrByName("onFailField").style.display = "";

				getElementByIdOrByName("onFailReviewLabel").style.display = "none";
				getElementByIdOrByName("onFailReviewField").style.display = "none";

				getElementByIdOrByName("IdAdvancedSubSection").style.display = "";
				getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("IdSubSection").style.display = "";
				getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "none";
				getElementByIdOrByName("invCriteriaSubSection").style.display = "none";
				getElementByIdOrByName("timeFrameSubSection").style.display = "none";
			 }

			 getElementByIdOrByName("subsec6").style.display = "none";
		 }else if(actionVal != null && actionVal != undefined && actionVal.value == '2')
		 {
			 if (eventTypeVal != null && eventTypeVal != undefined && eventTypeVal.value == '11648804')
			 {
				getElementByIdOrByName("PublishedConditionLabel").style.display = "";
				getElementByIdOrByName("PublishedConditionField").style.display = "";

				getElementByIdOrByName("RelatedLabel").style.display = "";
				getElementByIdOrByName("RelatedField").style.display = "";

				getElementByIdOrByName("ConditionLabel").style.display = "none";
				getElementByIdOrByName("ConditionField").style.display = "none";

				//getElementByIdOrByName("conditionPageLabel").style.display = "";
				//getElementByIdOrByName("conditionPageField").style.display = "";

				getElementByIdOrByName("onFailLabel").style.display = "";
				getElementByIdOrByName("onFailField").style.display = "";

				getElementByIdOrByName("onFailReviewLabel").style.display = "none";
				getElementByIdOrByName("onFailReviewField").style.display = "none";

				getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
				getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
				getElementByIdOrByName("IdSubSection").style.display = "";
				//getElementByIdOrByName("defaultidNote").style.display = "";//Fixing issue ND-10174: removing Notes section from WDS Lab Report Default Values
			 }else if (eventTypeVal != null && eventTypeVal != undefined && eventTypeVal.value == 'PHC236')
			 {
				getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
				getElementByIdOrByName("PublishedConditionField").style.display = "none";

				getElementByIdOrByName("RelatedLabel").style.display = "";
				getElementByIdOrByName("RelatedField").style.display = "";

				getElementByIdOrByName("ConditionLabel").style.display = "";
				getElementByIdOrByName("ConditionField").style.display = "";

				//getElementByIdOrByName("conditionPageLabel").style.display = "none";
				//getElementByIdOrByName("conditionPageField").style.display = "none";

				getElementByIdOrByName("onFailLabel").style.display = "";
				getElementByIdOrByName("onFailField").style.display = "";

				getElementByIdOrByName("onFailReviewLabel").style.display = "none";
				getElementByIdOrByName("onFailReviewField").style.display = "none";

				getElementByIdOrByName("IdAdvancedSubSection").style.display = "";
				getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "none";
				getElementByIdOrByName("IdSubSection").style.display = "";
			 }
			 getElementByIdOrByName("subsec6").style.display = "";
		 }else if(actionVal != null && actionVal != undefined && actionVal.value == '3')
		 {
			//alert("Here "+eventTypeVal.value );
			var timelogic = $j("#useEventDateLogic").text();
			//alert(timelogic);
			if(eventTypeVal != null && eventTypeVal != undefined && eventTypeVal.value == '11648804' && timelogic == 'No '){
			getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
			getElementByIdOrByName("PublishedConditionField").style.display = "none";
		    }else{
			getElementByIdOrByName("PublishedConditionLabel").style.display = "";
			getElementByIdOrByName("PublishedConditionField").style.display = "";
			 }
			getElementByIdOrByName("RelatedLabel").style.display = "none";
			getElementByIdOrByName("RelatedField").style.display = "none";

			getElementByIdOrByName("ConditionLabel").style.display = "none";
			getElementByIdOrByName("ConditionField").style.display = "none";

			//getElementByIdOrByName("conditionPageLabel").style.display = "none";
			//getElementByIdOrByName("conditionPageField").style.display = "none";

			getElementByIdOrByName("onFailLabel").style.display = "none";
			getElementByIdOrByName("onFailField").style.display = "none";
			getElementByIdOrByName("onFailReviewLabel").style.display = "";
			getElementByIdOrByName("onFailReviewField").style.display = "";

			getElementByIdOrByName("IdAdvancedSubSection").style.display = "none";
			getElementByIdOrByName("ElrIdAdvancedSubSection").style.display = "";
			getElementByIdOrByName("ElrTheAdvancedSubSection").style.display = "";
			getElementByIdOrByName("IdSubSection").style.display = "none";
			getElementByIdOrByName("subsec6").style.display = "none";
		 }
	
	
}
}



function ValidateDefaultBatchFields()
{
	// question, value and behavior are required
	var errorLabels = new Array();
	var questionListTxtNode = getElementByIdOrByName('questionList_textbox');
	var questionList = jQuery.trim(questionListTxtNode.value);
	var valueNode = "";
	var valueNodeVal= "";

	if(getElementByIdOrByName("text").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("Val_text").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("Val_textArea").style.display != "none" ){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("Val_textArea").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("date").style.display != "none"){
		var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
		if(selectDateNode.checked){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("Val_date").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
		else if(!checkdate(valueNodeVal))
			errorLabels.push("Date must be in the format of mm/dd/yyyy. Please correct the data and try again.");
	}
	}else if(getElementByIdOrByName("m_sel").style.display != "none"){
		var valueNode = getElementByIdOrByName('valueList2');
		var showError="";
		valueNodeVal = valueNode.options.length;
		for (i=0; i < valueNodeVal; i++) {
			if(valueNode.options[i].value != ""){
				if (valueNode.options[i].selected == true){
					showError = "false";
				}
			}
		}
		if(showError != "false")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("s_sel").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("valueList1_textbox").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("Num_Coded").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("Val_num").value);
		codeNodeVal = jQuery.trim(getElementByIdOrByName("Val_code_textbox").value);
		if (valueNodeVal == 'null' || valueNodeVal == "" || codeNodeVal == 'null' || codeNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("Num_Literal").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("Val_lit").value);
		var questionCode = getElementByIdOrByName('questionList').value;
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
		else if(numericMask != "" && numericMask == 'NUM_SN')
		{
			if(!isStructuredNumeric(valueNodeVal))
				errorLabels.push("Numeric Result can contain whole numbers (up to 10 digits), decimal numbers (up to 5 decimal points), comparators (< , > , = , <= , >= , <>) and separators (+ , - , / , : ) in the following format &lt;comparator&gt; &lt;numeric 1&gt; &lt;separator&gt; &lt;numeric 2&gt;.  (Examples: 256, >25.5, 100-200, 4+, 3/25, 1:64). Please correct the data and try again.");
		}else if(numericMask != "" && numericMask == 'NUM_DD')
		{
			if(!isDayNumeric(valueNodeVal))
				errorLabels.push("Value must be a two digit number between 01 and 31.  Please correct the value and try again.");
		}
		else if(numericMask != "" && numericMask == 'NUM_EXT')
		{
			if(!isPhoneExtNumeric(valueNodeVal))
				errorLabels.push("Value must be between 0 and 99999999.  Please correct the value and try again.");
		}
		else if(numericMask != "" && numericMask == 'NUM_MM')
		{
			if(!isMonthNumeric(valueNodeVal))
				errorLabels.push("Value must be a two digit number between 01 and 12.  Please correct the value and try again.");
		}
		else if(numericMask != "" && numericMask == 'NUM_TEMP')
		{
			if(!isTempNumeric(valueNodeVal))
				errorLabels.push("Value must be in the following format ###.#.  Please correct the value and try again.");
		}
		else if(numericMask != "" && numericMask == 'NUM_YYYY')
		{
			if(!isYearNumeric(valueNodeVal))
				errorLabels.push("Value must be between 1875 and the current year.  Please correct the value and try again.");
		}
		else if(!isInteger(valueNodeVal) && !(questionCode == 'INV165') && !(questionCode == 'INV166'))
			errorLabels.push("Value need to be an Integer.");
		else if(questionCode == 'INV165' && (!isInteger(valueNodeVal) || !isWeek(valueNodeVal)))
			errorLabels.push("Value is not a valid MMWR Week.");
		else if(questionCode == 'INV166' && (!isInteger(valueNodeVal) || !isYear(valueNodeVal)))
			errorLabels.push("Value is not a valid MMWR Year.");
	}

	var behaviorTxtNode = getElementByIdOrByName('behavior_textbox');
	var behaviorTxt = jQuery.trim(behaviorTxtNode.value);

	if (questionList == 'null' || questionList == "")
		errorLabels.push("Question is a required field.");
	if (behaviorTxt == 'null' || behaviorTxt == "")
		errorLabels.push("Behavior is a required field.");
	if (errorLabels.length > 0) {
		displayErrors('IdSubSectionerrorMessages', errorLabels);
		return false;
	}
$j('#IdSubSectionerrorMessages').css("display", "none");
return true;
}

function isDayNumeric(valueNodeVal)
{
	var varPattern = new RegExp("(0[1-9]|[12][0-9]|3[01])");
	if (varPattern.test(valueNodeVal)) {
		return true;
	} else {
		return false;
	}
}

function isPhoneExtNumeric(valueNodeVal)
{
	if(!isInteger(valueNodeVal))
		return false;
	if(valueNodeVal > 99999999)
		return false;
	else
		return true;
}
function isMonthNumeric(valueNodeVal)
{
	var varPattern = /^(0[1-9]|1[0-2])$/
	if (varPattern.test(valueNodeVal)) {
		return true;
	} else {
		return false;
	}
}
function isTempNumeric(valueNodeVal)
{
	var varPattern = /^\d{1,3}(\.\d{1})?$/;
	if (varPattern.test(valueNodeVal)) {
		return true;
	} else {
		false
	}
}
function isYearNumeric(valueNodeVal)
{
	if(!isInteger(valueNodeVal))
		return false;

	var now = new Date();
	var currentYear = now.getFullYear();
	if (valueNodeVal == "") {
		return false;
	}
	else if (valueNodeVal < 1875) {
		return false;
	}
	else if (valueNodeVal > currentYear) {
		return false;
	}
	else
	{
		return true;
	}
}

function isStructuredNumeric(numericInput)
{
	var numericResultRE = /^([<>=]|[<][=]|[>][=]|[<][>])?(((((\d){0,10})|((\d){1,10})([\.]{1})((\d){1,5}))?)|(((((\d){1,10})([\.]{1})((\d){1,5})[+])|(((\d){1,10})[+]))|((((\d){1,10})|(((\d){1,10})([\.]{1})((\d){1,5})))[-:\/](((\d){1,10})|(((\d){1,10})([\.]{1})((\d){1,5})))))?)?$/;

	if (!numericResultRE.test(numericInput))
		return false;
	return true;
}


function writeBatchIdEntry( subSectNm,pattern,questionbody)
{
	var t = getElementByIdOrByName("IdSubSection");
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}

	var seqNum = 0;
	var questionType = "";
	var questionUid = getElementByIdOrByName("questionList").options[getElementByIdOrByName("questionList").selectedIndex].value;
	var textField = getElementByIdOrByName("Val_text").value;
	var textAreaField = getElementByIdOrByName("Val_textArea").value;
	var singleSelect = getElementByIdOrByName("valueList1").value;
	var multiselect = getElementByIdOrByName("valueList2").value;
	var participationPerText = $j("#PartPer").html();
	var participationPerUid = $j("#PartPerUid").val();
	var textDate = getElementByIdOrByName("Val_date").value;
	var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
	var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
	if (currentDateNode.checked){
		textDate="Current Date";
	}
	else if(selectDateNode.checked){
		textDate = getElementByIdOrByName("Val_date").value;
	}
	if(multiselect == null || multiselect == "")
	{
		var valueNode = getElementByIdOrByName('valueList2');
		var myListCount = valueNode.options.length;
		for (i=0; i < myListCount; i++) {
			if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
				multiselect = "AS";
				break;
			}
		}
	}
	var numericLiteral = getElementByIdOrByName("Val_lit").value;
	var numericCoded = getElementByIdOrByName("Val_code").value;
	if(textField!=null && textField!=""){
		questionType = "Text";
	}
	else if(textField!=null && textField!=""){
			questionType = "Text";
	}
	else if(textAreaField!=null && textAreaField!=""){
			questionType = "TextArea";
	}
	else if(textDate!=null && textDate!=""){
			questionType = "Date";
	}
	else if(singleSelect!=null && singleSelect!=""){
			questionType = "SINGLESELECT";
	}
	else if(multiselect!=null && multiselect!=""){
			questionType = "MULTISELECT";
	}
	else if(numericCoded!=null && numericCoded!=""){
			questionType = "NumericCoded";
	}
	else if(numericLiteral!=null && numericLiteral!=""){
			questionType = "NumericLiteral";
	}
	else if(participationPerText!=null && participationPerText!=""){
			var entityType = $j("#PartEntityType").val();
			if (entityType == "PartOrg")
				questionType = "ParticipationOrganization";
			else
				questionType = "ParticipationPerson";

	}

	dwr.engine.beginBatch();

	var ansMap = getBatchAnswerMapFromScreen(questionType);
	var batchentry = { subSectNm:subSectNm, answerMap:ansMap };
	JDecisionSupport.setBatchAnswer(batchentry);

	if(currentDateNode.checked==true){
	var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
	currentDateNode.checked=false;
	}
	if(selectDateNode.checked==true){
	var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
	selectDateNode.checked=false;
	}

	rewriteBatchIdHeader(subSectNm, pattern, questionbody);

	clearBatchEntryFields(subSectNm);

	dwr.engine.endBatch();

}
function pausecomp(millis)
{
	var date = new Date();
	var curDate = null;

	do { curDate = new Date(); }
	while(curDate-date < millis);
}
function clearBatchEntryFields (subSectNm)
{
	gBatchEntryUpdateSeq = "";
	$j($j("#questionList").parent().parent()).find(":input").val("");
	$j("#Val_text").parent().find(":input").val("");
	//$j("#Val_textArea").parent().find(":input").val("");
	$j("#Val_textArea").val("");
	//$j("#Val_date").parent().find(":input").val("");
	$j("#Val_date").val("");
	$j($j("#valueList2").parent().parent()).find(":input").val("");
	getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
	$j($j("#valueList1").parent().parent()).find(":input").val("");
	//$j("#Val_lit").parent().find(":input").val("");
	$j("#Val_lit").val("");
	//$j("#Val_num").parent().find(":input").val("");
	//$j("#Val_num").val("");
	$j($j("#Val_code_textbox").parent().parent()).find(":input").val("");
	$j($j("#behavior").parent().parent()).find(":input").val("");
	$j($j("#questionList").parent().parent()).find(":input").focus();
	$j("#PartPerText").val("");
	dwr.util.setValue("PartPer", "");
	$j("#Part_Per_Div").children().hide(); //participation
}

function setSelectedIndex(s, v) {
    for ( var i = 0; i < s.options.length; i++ ) {
    	var optionValue = s.options[i].value;
        if ( optionValue == v ) {
            s.options[i].selected = true;
            return;
        }
    }
}

function getSelectedIndexText(s, v) {
    for ( var i = 0; i < s.options.length; i++ ) {
        if ( s.options[i].value == v ) {
            return s.options[i].text;
        }
    }
}

var selectedInvestigationString = null;
function changePublishedCondition(publishedCondition){
    var eventType = getElementByIdOrByName("EVENT_TY").value;
    var actionVal = getElementByIdOrByName("ActionList")
	if(actionVal != null && actionVal != undefined && actionVal.value == '3'){
		return;
	}
	JDecisionSupport.getAllBatchAnswer("",function(answer) {
		var iRowCount = answer.length;

		if(iRowCount > 0)
		{
			 var confirmMsg="Changing the Condition will reset the Related Page and Investigation Default Values. " +
			 		"If you continue with this action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
		     if (confirm(confirmMsg)) {
		    	 selectedPublishedCondition = publishedCondition.value;
		    	 selectedInvestigationString = getSelectedIndexText(publishedCondition, selectedPublishedCondition);
		    	 getElrConditionPageValue(publishedCondition);
		    	 initIdSubSection();

		     } else {
		    	 setSelectedIndex(publishedCondition, selectedPublishedCondition);
		    	 selectedInvestigationString = getSelectedIndexText(publishedCondition, selectedPublishedCondition);
		    	 getElementByIdOrByName("PublishedCondition_textbox").value = selectedInvestigationString;
		     }
		}else
		{
			selectedPublishedCondition = publishedCondition.value;
			selectedInvestigationString = getSelectedIndexText(publishedCondition, selectedPublishedCondition);
			getElrConditionPageValue(publishedCondition);
			initIdSubSection();
		}

		if(selectedPublishedCondition != null && selectedPublishedCondition !="")
		{
		    getElementByIdOrByName("IdSubSection").style.display = "";
		    if(eventType=='11648804'){
		        getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "";
		        //getElementByIdOrByName("defaultidNote").style.display = "";//Fixing issue ND-10174: removing Notes section from WDS Lab Report Default Values
		    }

		}else
		{
		    getElementByIdOrByName("Notes").value = "";
			getElementByIdOrByName("IdSubSection").style.display = "none";

		}
	});
}
 function initIdSubSection()
 {
		$j('#IdSubSectionerrorMessages').css("display", "none");
		 var t = getElementByIdOrByName("IdSubSection");
		 t.style.display = "";
		 for (var i = 0; i < 4; i++){
		 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	 	}
 }

 function initElrIdAdvancedSubSection()
 {
		$j('#ElrIdAdvancedSubSectionerrorMessages').css("display", "none");
		 var t = getElementByIdOrByName("ElrIdAdvancedSubSection");
		 t.style.display = "";
		 for (var i = 0; i < 4; i++){
		 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	 	}

		 $j("#testSearchButton").parent().find(":input").attr("disabled", false);
		$j("#resultedTestL").parent().find("span[title]").css("color", "#000");
		$j("#testDescription").parent().find("span[title]").css("color", "#000");
		$j("#testResultNote").parent().find("span[title]").css("color", "#000");

		$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
		$j("#codeClearButton").parent().find(":input").attr("disabled", false);
		$j("#codeResultL").parent().find("span[title]").css("color", "#000");
		$j("#testDescription").parent().find("span[title]").css("color", "#000");

		//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
		//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background", "#FFF");
		//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);
		//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#000");

		$j("#numericResult_text").parent().find(":input").attr("disabled", false);
		$j("#numericResult_text").parent().find(":input").css("background", "#FFF");
		$j("#numericResultL").parent().find("span[title]").css("color", "#000");

		$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background", "#FFF");
		$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
		$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
		

		$j("#textResult_text").parent().find(":input").attr("disabled", false);
		$j("#textResult_text").parent().find(":input").css("background", "#FFF");
		$j("#textResultL").parent().find("span[title]").css("color", "#000");

 }
 function initIdAdvancedSubSection()
 {
		$j('#IdAdvancedSubSectionerrorMessages').css("display", "none");
		 var t = getElementByIdOrByName("IdAdvancedSubSection");
		 t.style.display = "";
		 for (var i = 0; i < 4; i++){
		 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	 	}
 }

var selectedInvestigationString = null;
function changeRelatedPage(relatedPage)
{
    var eventType = getElementByIdOrByName("EVENT_TY").value;
	JDecisionSupport.getAllBatchAnswer("",function(answer) {
		var iRowCount = answer.length;

		if(iRowCount > 0)
		{
			 var confirmMsg="Changing the Investigation Type (Related Page) will reset the Condition and Investigation Default Values. " +
			 		"If you continue with this action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
		     if (confirm(confirmMsg)) {
		    	 selectedInvestigationType = relatedPage.value;
		    	 selectedInvestigationString = getSelectedIndexText(relatedPage, selectedInvestigationType);
		    	 getConditionDropDown(relatedPage);
		    	 getDWRQuestions(relatedPage);
		    	 clearSelectedDWRQuestions('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
		    	 getElementByIdOrByName("Notes").value = "";
		    	 initIdSubSection();
		     } else {
		    	 setSelectedIndex(relatedPage, selectedInvestigationType);
		    	 selectedInvestigationString = getSelectedIndexText(relatedPage, selectedInvestigationType);
		    	 getElementByIdOrByName("relatedPage_textbox").value = selectedInvestigationString;
		     }
		}else
		{
			selectedInvestigationType = relatedPage.value;
			selectedInvestigationString = getSelectedIndexText(relatedPage, selectedInvestigationType);
			 getConditionDropDown(relatedPage);
	    	 getDWRQuestions(relatedPage);
	    	 clearSelectedDWRQuestions('IdSubSection','patternIdSubSection','questionbodyIdSubSection');
	    	 getElementByIdOrByName("Notes").value = "";
	    	 initIdSubSection();
		}
		if(selectedInvestigationType != null && selectedInvestigationType != ""){
			getElementByIdOrByName("IdSubSection").style.display = "";
			if(eventType=='11648804'){
		        getElementByIdOrByName("IdSubSectionInfoMessages").style.display = "";
		        //getElementByIdOrByName("defaultidNote").style.display = "";//Fixing issue ND-10174: removing Notes section from WDS Lab Report Default Values
		    }
			}
		else{
		    getElementByIdOrByName("Notes").value = "";
			getElementByIdOrByName("IdSubSection").style.display = "none";
			}
	});
}

function clearSelectedAdvancedQuestions(subSection, pattern, questionBody)
{
	JDecisionSupport.clearAdvancedBatchEntry();

	rewriteAdvancedCriteriaBatchIdHeader(subSection, pattern, questionBody);
	$j("#AddButtonToggleIdAdvancedSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedSubSection").hide();

	clearAdvancedCriteriaBatchEntryFields(subSection);
}

function clearSelectedAdvancedInvQuestions(subSection, pattern, questionBody)
{
	JDecisionSupport.clearAdvancedInvBatchEntry();

	rewriteAdvancedInvCriteriaBatchIdHeader(subSection, pattern, questionBody);
	$j("#AddButtonToggleIdAdvancedInvSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedInvSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedInvSubSection").hide();

	clearAdvancedInvCriteriaBatchEntryFields(subSection);
}

function clearSelectedElrAdvancedQuestions(subSection, pattern, questionBody)
{
	JDecisionSupport.clearAdvancedBatchEntry();

	rewriteElrAdvancedCriteriaBatchIdHeader(subSection, pattern, questionBody);
	$j("#AddButtonToggleIdELRAdvancedSubSection").show();
	$j("#AddNewButtonToggleIdELRAdvancedSubSection").hide();
	$j("#UpdateButtonToggleIdELRAdvancedSubSection").hide();

	clearElrAdvancedCriteriaBatchEntryFields(subSection);
}

function clearSelectedDWRQuestions(subSection, pattern, questionBody)
{
	JDecisionSupport.clearBatchAnswer();

	rewriteBatchIdHeader(subSection, pattern, questionBody);
	$j("#AddButtonToggleIdSubSection").show();
	$j("#AddNewButtonToggleIdSubSection").hide();
	$j("#UpdateButtonToggleIdSubSection").hide();

	clearBatchEntryFields (subSection);
}

function clearSelectedDWRQuestions(subSection, pattern, questionBody)
{
	JDecisionSupport.clearBatchAnswer();

	rewriteBatchIdHeader(subSection, pattern, questionBody);
	$j("#AddButtonToggleIdSubSection").show();
	$j("#AddNewButtonToggleIdSubSection").hide();
	$j("#UpdateButtonToggleIdSubSection").hide();

	clearBatchEntryFields (subSection);
}

function rewriteBatchIdHeader(subSectNm, pattern, questionbody)
	{
     //get all rows of data
    JDecisionSupport.getAllBatchAnswer(subSectNm,function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows(questionbody, { filter:function(tr) {
				return (tr.id != pattern);
		}});

		if (answer.length == 0) {
			//no rows - display 'No Data has been entered'
		 	//$('nopattern' +subSectNm).style.display = "block";
		 	return;
		 }

		for (var i = 0; i < answer.length; i++){
			if (i%2 == 0)
		      	rowClass="odd";
			else
				rowClass="even";
			ans = answer[i];
			id = ans.id;
			dwr.util.cloneNode(pattern, { idSuffix:id });
		      //pull the data for each row
		      for (var key in ans.answerMap) {
		      	if (key == 'queListTxt' ||key == 'valValueTxt' || key == 'behaviorTxt') {
		      		var val = ans.answerMap[key];
		      		if (key == 'valValueTxt')
		      			$j("#table" + key + id).html(val);
		      		else
					dwr.util.setValue("table" + key + id, val);
				}
		     }
		     //clear display = 'none'
		      $(pattern + id).style.display = "";
		     //hide No Data Entered
		     $('nopattern' +subSectNm).style.display = "none";
		     getElementByIdOrByName(pattern + id).setAttribute("className",rowClass);
			 getElementByIdOrByName(pattern + id).setAttribute("class",rowClass);
			 
		}
    }); //all rows of data

	}


function getBatchAnswerMapFromScreen(questionType)
{
	var ansMap = {};
	var questionListNode = getElementByIdOrByName('questionList');
	var questionListTxtNode = getElementByIdOrByName('questionList_textbox');
	if (questionListNode != null) {
		var queList = questionListNode.value;
		ansMap['questionList'] = queList;
		var queListTxt = questionListTxtNode.value;
		ansMap['queListTxt'] = queListTxt;
	}
	if(questionType != null && questionType=='Text'){
		var valueNode = getElementByIdOrByName('Val_text');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['valValueTxt']=value;
			ansMap['questionType']=questionType;
		}
	}else if(questionType != null && questionType=='TextArea'){
		var valueNode = getElementByIdOrByName('Val_textArea');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['valValueTxt']=value;
			ansMap['questionType']=questionType;
		}
	}else if(questionType != null && questionType=='Date'){
		var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
		var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
		if(currentDateNode.checked){
			var value="Current Date";
		}
		else if(selectDateNode.checked){
		var valueNode = getElementByIdOrByName('Val_date');
		if (valueNode != null) {
			var value = valueNode.value;
			}
		}
			ansMap['valValueTxt']=value;
			ansMap['questionType']=questionType;

	}else if(questionType != null && questionType=='SINGLESELECT'){
		var valueNode = getElementByIdOrByName('valueList1');
		var valueTxtNode = getElementByIdOrByName('valueList1_textbox');
		if (valueNode != null) {
			var valueList = valueNode.value;
			ansMap['valueList1']=valueList;
			var valueListTxt = valueTxtNode.value;
			ansMap['valValueTxt']= valueListTxt;
			ansMap['questionType']=questionType;
		}
	}else if(questionType != null && questionType=='MULTISELECT'){
		var valueNode = getElementByIdOrByName('valueList2');
		var optionsVal = "";
		var optionsTxt = "";
		var myListCount = valueNode.options.length;
		if (valueNode != null) {
			for (i=0; i < myListCount; i++) {
				if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
				optionsVal = optionsVal +"^^"+valueNode.options[i].value;
				optionsTxt = optionsTxt +"^Value^"+valueNode.options[i].text;
				}
			}
			var valueTxtNode = getElementByIdOrByName('valueList2-selectedValues').innerHTML;
			ansMap['valueList2']=optionsVal;
			var valueListTxt = valueTxtNode.substring(valueTxtNode.lastIndexOf(">")+1, valueTxtNode.length);
			ansMap['valValueTxt']=valueListTxt;
			ansMap['questionType']=questionType;
			getElementByIdOrByName("valueList2").options.length = 0;
			getElementByIdOrByName('valueList2-selectedValues').innerHTML = "";
		}
	}else if(questionType != null && questionType=='NumericLiteral'){
		var valueNode = getElementByIdOrByName('Val_lit');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['valValueTxt']=value;
			ansMap['questionType']=questionType;
			if(numericMask != null && numericMask != "")
				ansMap['numericMask']=numericMask;
		}
	}else if(questionType != null && questionType=='NumericCoded'){
		var valueNode1 = getElementByIdOrByName('Val_num');
		var valueNode2 = getElementByIdOrByName('Val_code');
		var valueTxtNode = getElementByIdOrByName('Val_code_textbox');

		var valueNode = valueNode1.value+"^"+valueNode2.value;
		if (valueNode != null) {
			var value = valueNode.value;

			ansMap['valueList3']=valueNode;
			var valueListTxt = valueTxtNode.value;
			ansMap['valValueTxt']= valueNode1.value+valueTxtNode.value;
			ansMap['valCodeTxt']= valueTxtNode.value;
			ansMap['questionType']=questionType;
		}
	}
	else if(questionType != null &&
		(questionType=='ParticipationPerson' || questionType=='ParticipationOrganization')){
		var dispHtml = $j("#PartPer").html();
		if (dispHtml.indexOf("<SPAN") > 0)
			dispHtml = dispHtml.substr(0,dispHtml.indexOf("<SPAN"));
		ansMap['participationSrchRslt'] = dispHtml;
		var dispStr = dispHtml;
		//now putting $j("#table" + key + id).html(val);
		//var regex1 = /<br\s*[\/]?>/gi;
		//dispStr= dispStr.replace(regex1, "\n"); //replace line breaks with spaces
		//var regex2 =  /<b\s*[\/]?>/gi; //replace bold with space
		//dispStr= dispStr.replace(regex2, " "); //replace bold with space
		//dispStr = dispStr.replace(/<br\>/gi, " "); //replace line breaks with spaces
		//alert("dispStr= " + dispStr);
		ansMap['valValueTxt'] = dispStr;
		ansMap['participationUid']  =  $j("#PartPerUid").val();
		ansMap['questionType']=questionType;
}
	var behaviorNode = getElementByIdOrByName('behavior');
	var behaviorTxtNode = getElementByIdOrByName('behavior_textbox');
	if (behaviorNode != null) {
		var behList = behaviorNode.value;
		ansMap['behavior'] = behList;
		var behListTxt = behaviorTxtNode.value;
		ansMap['behaviorTxt'] = behListTxt;
	}
	return ansMap;
}


function viewClicked( entryId,subSection,pattern,questionbody)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	 var t = getElementByIdOrByName("IdSubSection");
	 t.style.display = "";
	 for (var i = 0; i < 4; i++){
	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
 	}
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	var questionType ="";
//	if (gBatchEntryFieldsDisabled == true){
//		JDecisionSupport.getAllBatchAnswer(subSection,function(answer) {
//			for (var i = 0; i < answer.length; i++){
//				var ans = answer[i];
//				var id = ans.id;
//				if (id == beIdStr) {
//					questionType = ans.answerMap['questionType'];
//					enableBatchEntryFields(questionType);
//				}
//			}
//		});
//	}
    //get all rows of data

	JDecisionSupport.getAllBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
						questionType = ans.answerMap['questionType'];
						gBatchEntryUpdateSeq = id;
			        	var questionListNode = getElementByIdOrByName('questionList');
			        	var questionListTxtNode = getElementByIdOrByName('questionList_textbox');
			        	if (questionListNode != null) {
			        		questionListNode.value = ans.answerMap['questionList'];
			        		questionListTxtNode.value = ans.answerMap['queListTxt'];
			        	}
			        	if(ans.answerMap['questionType'] == 'Text')
			        	{
			        		getElementByIdOrByName("Val_text").style.visibility="visible";
			        		getElementByIdOrByName("text").style.visibility="visible";
			        		getElementByIdOrByName("text").style.display = "";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('Val_text');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['valValueTxt'];
			        		}
			        	}else if(ans.answerMap['questionType'] == 'TextArea')
			        	{
			        		getElementByIdOrByName("Val_textArea").style.visibility="visible";
			        		getElementByIdOrByName("Val_textArea").style.display="";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("text").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('Val_textArea');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['valValueTxt'];
			        		}
			        	}else if(ans.answerMap['questionType'] == 'Date')
			        	{
			        		getElementByIdOrByName("Val_date").style.visibility="visible";
			        		getElementByIdOrByName("date").style.visibility="visible";
			        		getElementByIdOrByName("date").style.display = "";
			        		getElementByIdOrByName("Val_dateIcon").style.visibility="visible";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("text").style.display = "none";
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";

			        		if(ans.answerMap['valValueTxt']=="Current Date"){
			        			var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
			        			currentDateNode.checked=true;
			        			getElementByIdOrByName("Val_date").value='';
			        		}else{
			        			var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
			        			selectDateNode.checked=true;
			        			var valueNode = getElementByIdOrByName('Val_date');
				        		if (valueNode != null) {
				        			valueNode.value = ans.answerMap['valValueTxt'];
				        		}
			        		}
			        	}else if(ans.answerMap['questionType'] == 'SINGLESELECT')
			        	{
			        		getElementByIdOrByName("s_sel").style.visibility="visible";
			        		getElementByIdOrByName("valueList1").style.visibility="visible";
			        		getElementByIdOrByName("s_sel").style.display = "";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("text").style.display = "none";
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";

			        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("valueList1");
							   	DWRUtil.addOptions("valueList1", data, "key", "value" );
			        		});
			        		var valueNode = getElementByIdOrByName('valueList1');
			        		var valueTxtNode = getElementByIdOrByName('valueList1_textbox');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['valueList1'];
			        			valueTxtNode.value = ans.answerMap['valValueTxt'];
			        		}
			        	}else if(ans.answerMap['questionType'] == 'MULTISELECT')
			        	{
			        		getElementByIdOrByName("m_sel").style.visibility="visible";
			        		getElementByIdOrByName("m_sel").style.display = "";
			        		getElementByIdOrByName("valueList2").style.visibility="visible";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("text").style.display = "none";
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";

			        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("valueList2");
							   	DWRUtil.addOptions("valueList2", data, "key", "value" );
			        		});
			        		var valueNode = getElementByIdOrByName('valueList2');
			        		var valueTxtNode = getElementByIdOrByName('valueList2-selectedValues');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['valueList2'];
			        			valueTxtNode.innerHTML ="<b> Selected Values: <\/b>"+ ans.answerMap['valValueTxt'];
			        		}
			        	}else if(questionType != null && questionType=='NumericLiteral'){
			        		getElementByIdOrByName("Num_Literal").style.visibility="visible";
			        		getElementByIdOrByName("Num_Literal").style.display = "";
			        		getElementByIdOrByName("Val_lit").style.visibility="visible";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("text").style.display = "none";
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";

			        		var valueNode = getElementByIdOrByName('Val_lit');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['valValueTxt'];
			        		}
			        	}else if(questionType != null && questionType=='NumericCoded'){
			        		getElementByIdOrByName("Num_Coded").style.visibility="visible";
			        		getElementByIdOrByName("Num_Coded").style.display = "";
			        		getElementByIdOrByName("Val_num").style.visibility="visible";
			        		getElementByIdOrByName("Val_code").style.visibility="visible";
			        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
			        		getElementByIdOrByName("text").style.display = "none";
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";

			        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("Val_code");
							   	DWRUtil.addOptions("Val_code", data, "key", "value" );
			        		});
			        		var valueNode1 = getElementByIdOrByName('Val_num');
			        		var valueNode2 = getElementByIdOrByName('Val_code');
			        		var valueTxtNode = getElementByIdOrByName('Val_code_textbox');
			        		var valueNode = valueNode1.value+"^"+valueNode2.value;
			        		if (valueNode != null) {
			        			var valueCoded = ans.answerMap['valueList3'];
			        			var mySplitResult = valueCoded.split("^");
			        			valueNode1.value = mySplitResult[0];
			        			valueNode2.value = mySplitResult[1];
			        			valueTxtNode.value = ans.answerMap['valCodeTxt'];
			        		}
			        	} else if(questionType != null &&
			        		(questionType=='ParticipationPerson' || questionType=='ParticipationOrganization')){
			        		getElementByIdOrByName("Val_text").style.visibility="hidden";
			        		getElementByIdOrByName("text").style.visibility="visible";
			        		getElementByIdOrByName("text").style.display = "";
			        		$j("#clearPartPer").children().hide();
			        		$j("#PartPerSearchControls").children().hide();
			        		getElementByIdOrByName("Part_Per_Div").style.display = "";
			        		getElementByIdOrByName("Part_Per_Div").style.visibility="visible";
			        		getElementByIdOrByName("PartPer").style.display = "";
			        		getElementByIdOrByName("PartPer").style.visibility="visible";
			        		var srchRslt = ans.answerMap['participationSrchRslt'];
			        		$j('#PartPer').html(srchRslt);
			        		getElementByIdOrByName("Val_textArea").style.display = "none";
			        		getElementByIdOrByName("date").style.display = "none";
			        		getElementByIdOrByName("m_sel").style.display = "none";
			        		getElementByIdOrByName("s_sel").style.display = "none";
			        		getElementByIdOrByName("Num_Coded").style.display = "none";
			        		getElementByIdOrByName("Num_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('Val_text');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['valValueTxt'];
			        		}
			    	     	}

			        	var behaviorNode = getElementByIdOrByName('behavior');
			        	var behaviorTxtNode = getElementByIdOrByName('behavior_textbox');
			        	if (behaviorNode != null) {
			        		behaviorNode.value = ans.answerMap['behavior'];
			        		behaviorTxtNode.value = ans.answerMap['behaviorTxt'];
			        	}

			        	disableBatchEntryFields(questionType);
			        	break;
			        	}
			}

	}); //all rows of data

}  //viewClicked

function enableBatchEntryFields (questionType)
{
  	gBatchEntryFieldsDisabled = false;
  	$j($j("#questionList").parent().parent()).find(":input").attr("disabled", false);
  	$j($j("#questionList").parent().parent()).find("img").attr("disabled", false);
  	$j($j("#questionList").parent().parent()).find("img").attr("tabIndex", "0");
  	
  	$j("#questionL").parent().find("span[title]").css("color", "#000");

  	if(questionType == 'Text')
	{
		$j("#Val_text").parent().find(":input").attr("disabled", false);
     	$j("#valueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType == 'TextArea'){

		$j("#Val_textArea").parent().find(":input").attr("disabled", false);
     	$j("#valueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType == 'Date'){

		$j("#Val_date").parent().find(":input").attr("disabled", false);
		$j("#Val_dateIcon").parent().find("img").attr("disabled", false);
		$j("#Val_dateIcon").parent().find("img").attr("tabIndex", "0");
		
     	$j("#valueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType.indexOf("MULTISELECT")>=0){

		$j($j("#valueList2").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#valueList2").parent().parent()).find("img").attr("disabled", false);
		$j($j("#valueList2").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#valueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType.indexOf("SINGLESELECT")>=0){
		$j($j("#valueList1").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#valueList1").parent().parent()).find("img").attr("disabled", false);
		$j($j("#valueList1").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#valueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType != null && questionType=='NumericLiteral'){
		$j("#Val_lit").parent().find(":input").attr("disabled", false);
		$j("#valueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType != null && questionType=='NumericCoded'){
		$j("#Val_num").parent().find(":input").attr("disabled", false);
		$j($j("#Val_code").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#Val_code").parent().parent()).find("img").attr("disabled", false);
		$j($j("#Val_code").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#valueL").parent().find("span[title]").css("color", "#000");
	}else if(questionType != null &&
	    (questionType=='ParticipationPerson' || questionType=='ParticipationOrganization')){
	     $j("#PartPerCodeClearButton").val("Clear/Reassign");
	     $j("#PartPerCodeClearButton").css("color", "#000");
	     $j("#PartPerCodeClearButton").attr("disabled", false);
	     $j("#PartPerIcon").val("Search");
	     $j("#PartPerIcon").css("color", "#000");
	     $j("#PartPerIcon").attr("disabled", false);
	     $j("#PartPerText").attr("disabled", false);
	     $j("#PartPerCodeLookupButton").val("Quick Code Lookup");
	     $j("#PartPerCodeLookupButton").css("color", "#000");
	     $j("#PartPerCodeLookupButton").attr("disabled", false);
	}


	$j($j("#behavior").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#behavior").parent().parent()).find("img").attr("disabled", false);
	$j($j("#behavior").parent().parent()).find("img").attr("tabIndex", "0");
  	
  	$j("#bahaviorL").parent().find("span[title]").css("color", "#000");

 	$j("#AddButtonToggleIdSubSection").show();
	$j("#AddNewButtonToggleIdSubSection").hide();
	$j("#UpdateButtonToggleIdSubSection").hide();
	clearBatchEntryFields ('idSubSection');
}
function disableBatchEntryFields (questionType)
{
	gBatchEntryFieldsDisabled = true;
	$j($j("#questionList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#questionList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#questionList").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#questionL").parent().find("span[title]").css("color", "#666666");

	if(questionType == 'Text')
	{
		$j("#Val_text").parent().find(":input").attr("disabled", true);
     	$j("#valueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType == 'TextArea'){

		$j("#Val_textArea").parent().find(":input").attr("disabled", true);
     	$j("#valueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType == 'Date'){

		$j("#Val_date").parent().find(":input").attr("disabled", true);
		$j("#Val_dateIcon").parent().find("img").attr("disabled", true);
		$j("#Val_dateIcon").parent().find("img").attr("tabIndex", "-1");
		
     	$j("#valueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType.indexOf("MULTISELECT")>=0){

		$j($j("#valueList2").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#valueList2").parent().parent()).find("img").attr("disabled", true);
		$j($j("#valueList2").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#valueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType.indexOf("SINGLESELECT")>=0){
		$j($j("#valueList1").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#valueList1").parent().parent()).find("img").attr("disabled", true);
		$j($j("#valueList1").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#valueL").parent().find("span[title]").css("color", "#666666");
	}else if(questionType != null && questionType=='NumericLiteral'){
		$j("#Val_lit").parent().find(":input").attr("disabled", true);
		$j("#valueL").parent().find("span[title]").css("color", "#666666");
	}else if(questionType != null && questionType=='NumericCoded'){
		$j("#Val_num").parent().find(":input").attr("disabled", true);
		$j($j("#Val_code").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#Val_code").parent().parent()).find("img").attr("disabled", true);
		$j($j("#Val_code").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#valueL").parent().find("span[title]").css("color", "#666666");
	} else if(questionType != null &&
		(questionType=='ParticipationPerson' || questionType=='ParticipationOrganization')) {
			$j("#Val_text").attr("disabled", true);
     			$j("#valueL").css("color", "#666666");
     	}

	$j($j("#behavior").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#behavior").parent().parent()).find("img").attr("disabled", true);
	$j($j("#behavior").parent().parent()).find("img").attr("tabIndex", "-1");
  	
  	$j("#bahaviorL").parent().find("span[title]").css("color", "#666666");

	$j("#AddButtonToggleIdSubSection").hide();
	$j("#AddNewButtonToggleIdSubSection").show();
	$j("#UpdateButtonToggleIdSubSection").hide();
}

function deleteClicked(entryId,subSection,pattern,questionBody)
{
	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	var t = getElementByIdOrByName(subSection);
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}

	JDecisionSupport.getAllBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				questionType = ans.answerMap['questionType'];
			}
		}
		enableBatchEntryFields(questionType);
		var batchentry = { subsecNm:subSection, id:beIdStr };
		JDecisionSupport.deleteBatchAnswer(batchentry, function()
		{
			rewriteBatchIdHeader(subSection, pattern, questionBody);
		});
	});

	$j("#AddButtonToggleIdSubSection").show();
	$j("#AddNewButtonToggleIdSubSection").hide();
	$j("#UpdateButtonToggleIdSubSection").hide();

	clearBatchEntryFields (subSection);

}
function updateBatchIdEntry( subSectNm,pattern,questionbody)
{
		var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	  	var t = getElementByIdOrByName("IdSubSection");
	 	t.style.display = "";

		for (var i = 0; i < 4; i++){
		 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
		}

		var seqNum = 0;
		var questionType = "";
		var questionUid = getElementByIdOrByName("questionList").options[getElementByIdOrByName("questionList").selectedIndex].value;
		var textField = getElementByIdOrByName("Val_text").value;
		var textAreaField = getElementByIdOrByName("Val_textArea").value;
		var textDate = getElementByIdOrByName("Val_date").value;
		var singleSelect = getElementByIdOrByName("valueList1").value;
		var multiselect = getElementByIdOrByName("valueList2").value;
		var participationPerText = $j("#PartPer").html();
		var participationPerUid = $j("#PartPerUid").val();

		var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
		var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
		if (currentDateNode.checked){
			textDate="Current Date";
		}
		else if(selectDateNode.checked){
			textDate = getElementByIdOrByName("Val_date").value;
		}

		if(multiselect == null || multiselect == "")
		{
			var valueNode = getElementByIdOrByName('valueList2');
			var myListCount = valueNode.options.length;
			for (i=0; i < myListCount; i++) {
				if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
					multiselect = "AS";
					break;
				}
			}
		}
		var numericLiteral = getElementByIdOrByName("Val_lit").value;
		var numericCoded = getElementByIdOrByName("Val_code").value;

		if(textField!=null && textField!=""){
			questionType = "Text";
		}
		else if(textField!=null && textField!=""){
				questionType = "Text";
		}
		else if(textAreaField!=null && textAreaField!=""){
				questionType = "TextArea";
		}
		else if(textDate!=null && textDate!=""){
				questionType = "Date";
		}
		else if(getElementByIdOrByName("s_sel").style.display==""){
				questionType = "SINGLESELECT";
		}
		else if(multiselect!=null && multiselect!=""){
				questionType = "MULTISELECT";
		}
		else if(numericCoded!=null && numericCoded!=""){
				questionType = "NumericCoded";
		}
		else if(numericLiteral!=null && numericLiteral!=""){
				questionType = "NumericLiteral";
		}
		else if(participationPerText!=null && participationPerText!=""){
			var entityType = $j("#PartEntityType").val();
			if (entityType == "PartOrg")
				questionType = "ParticipationOrganization";
			else
				questionType = "ParticipationPerson";
		}
		dwr.engine.beginBatch();
		var ansMap = getBatchAnswerMapFromScreen(questionType);
		var batchentry = { subSectNm:subSectNm, id:gBatchEntryUpdateSeq, answerMap:ansMap };
		JDecisionSupport.updateBatchAnswer(batchentry);
		rewriteBatchIdHeader(subSectNm, pattern, questionbody);

		if(currentDateNode.checked==true){
		var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
		currentDateNode.checked=false;
		}
		if(selectDateNode.checked==true){
		var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
		selectDateNode.checked=false;
		}

		clearBatchEntryFields(subSectNm);
		dwr.engine.endBatch();
		$j("#AddButtonToggleIdSubSection").show();
		$j("#AddNewButtonToggleIdSubSection").hide();
		$j("#UpdateButtonToggleIdSubSection").hide();
}

var edited = false;

function editClicked(entryId,subSection)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	var t = getElementByIdOrByName("IdSubSection");
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}
	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;
	if (gBatchEntryFieldsDisabled == true){
		JDecisionSupport.getAllBatchAnswer(subSection,function(answer) {
			for (var i = 0; i < answer.length; i++){
				var ans = answer[i];
				var id = ans.id;
				if (id == beIdStr) {
					questionType = ans.answerMap['questionType'];
					numericMask = ans.answerMap['numericMask'];
					enableBatchEntryFields(questionType);
				}
			}
		});
	}

	//get all rows of data
	JDecisionSupport.getAllBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				gBatchEntryUpdateSeq = id;
				questionType = ans.answerMap['questionType'];
				numericMask = ans.answerMap['numericMask'];
				var questionListNode = getElementByIdOrByName('questionList');
	        	var questionListTxtNode = getElementByIdOrByName('questionList_textbox');
	        	if (questionListNode != null) {
	        		questionListNode.value = ans.answerMap['questionList'];
	        		questionListTxtNode.value = ans.answerMap['queListTxt'];
					
					if(document.activeElement==getElementByIdOrByName("questionList_textbox"))
						edited=true;
					
	        	}

	        	if(ans.answerMap['questionType'] == 'Text')
	        	{
	        		getElementByIdOrByName("Val_text").style.visibility="visible";
	        		getElementByIdOrByName("text").style.visibility="visible";
	        		getElementByIdOrByName("text").style.display = "";
	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("Val_date").value="";
	        		getElementByIdOrByName("Val_textArea").value="";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('Val_text');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['valValueTxt'];
	        		}
	        	}else if(ans.answerMap['questionType'] == 'TextArea')
	        	{
	        		getElementByIdOrByName("Val_textArea").style.visibility="visible";
	        		getElementByIdOrByName("Val_textArea").style.display="";
	        		getElementByIdOrByName("Val_date").value="";
	        		getElementByIdOrByName("Val_text").value="";
	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('Val_textArea');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['valValueTxt'];
	        		}
	        	}else if(ans.answerMap['questionType'] == 'Date')
	        	{
	        		getElementByIdOrByName("Val_date").style.visibility="visible";
	        		getElementByIdOrByName("date").style.visibility="visible";
	        		getElementByIdOrByName("date").style.display = "";
	        		getElementByIdOrByName("Val_dateIcon").style.visibility="visible";
	        		getElementByIdOrByName("Val_text").value="";
	        		getElementByIdOrByName("Val_textArea").value="";
	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";

	        		if(ans.answerMap['valValueTxt']=="Current Date"){
	        			var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
	        			currentDateNode.checked=true;
	        			getElementByIdOrByName("Val_date").value='';
	        		}else{
	        			var selectDateNode = getElementByIdOrByName('SELECT_CURRENT_SELECT_DATE_LOGIC');
	        			selectDateNode.checked=true;
	        			var valueNode = getElementByIdOrByName('Val_date');
		        		if (valueNode != null) {
		        			valueNode.value = ans.answerMap['valValueTxt'];
		        		}
	        		}

	        	}else if(ans.answerMap['questionType'] == 'SINGLESELECT')
	        	{
	        		getElementByIdOrByName("s_sel").style.visibility="visible";
	        		getElementByIdOrByName("valueList1").style.visibility="visible";
	        		getElementByIdOrByName("s_sel").style.display = "";
	        		getElementByIdOrByName("Val_date").value="";
	        		getElementByIdOrByName("Val_textArea").value="";
	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName("valueList1");
	        		var valueTxtNode = getElementByIdOrByName("valueList1_textbox");
	        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("valueList1");
					   	DWRUtil.addOptions("valueList1", data, "key", "value" );
					   	if (valueNode != null) {
	        			valueNode.value = ans.answerMap['valueList1'];
	        			valueTxtNode.value = ans.answerMap['valValueTxt'];
	        		}
	        		});


	        	}else if(ans.answerMap['questionType'] == 'MULTISELECT')
	        	{
	        		getElementByIdOrByName("m_sel").style.visibility="visible";
	        		getElementByIdOrByName("m_sel").style.display = "";
	        		getElementByIdOrByName("valueList2").style.visibility="visible";
	        		getElementByIdOrByName("Val_date").value="";
	        		getElementByIdOrByName("Val_textArea").value="";
	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('valueList2');
	        		var valueTxtNode = getElementByIdOrByName('valueList2-selectedValues');
	        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("valueList2");
					   	DWRUtil.addOptions("valueList2", data, "key", "value" );
					   	if(StartsWith(actionMode, "Edit")){
							JDecisionSupport.getMultiselectValues("DefaultValue",function(data){
						   	   	var selectCondList = "";
						   		for (var j = 0; j < data.length; j++){
							   		var multiselectValueNode = getElementByIdOrByName('valueList2');
							   		var multiselectNodeVal = multiselectValueNode.options.length;
							   		for(var i=0; i < multiselectNodeVal; i++){
								   		if(multiselectValueNode.options[i].value ==  data[j])
								   		{
								   			multiselectValueNode.options[i].selected = true;
								   		}
							   		}
						   		}
						   		displaySelectedOptions(getElementByIdOrByName("valueList2"), 'valueList2-selectedValues');
						   	});
					   	}
					   	if (valueNode != null) {
	        			valueNode.value = ans.answerMap['valueList2'];
	        			var multiselValue = ans.answerMap['valueList2']
  	        			var multiselCode = multiselValue.substring(multiselValue.lastIndexOf("^") + 1, multiselValue.length);
  	        			valueNode.value = multiselCode;
  	        			var multiselectNodeVal = valueNode.options.length;
  	        			var mySplitResult = multiselValue.split("^^");
  	        			if(StartsWith(actionMode, "Create")){
  		        			for(j=1; j<multiselValue.split("^^").length; j++) {
  			        			for(var i=1; i < multiselectNodeVal; i++){
  							   		if( jQuery.trim(valueNode.options[i].value) ==  jQuery.trim(mySplitResult[j]))
  							   		{
  							   			getElementByIdOrByName('valueList2').options[i].selected = true;
  							   			//alert(getElementByIdOrByName('valueList2').options[i].selected);
  							   		}
  						   		}
  		        			}
  		        			displaySelectedOptions(getElementByIdOrByName("valueList2"), 'valueList2-selectedValues');
  	        			}
	        			//valueTxtNode.innerHTML ="<b> Selected Values: <\/b>"+ ans.answerMap['valValueTxt'];
	        		}
	        		});
	        	}else if(questionType != null && questionType=='NumericLiteral'){
	        		getElementByIdOrByName("Num_Literal").style.visibility="visible";
	        		getElementByIdOrByName("Num_Literal").style.display = "";
	        		getElementByIdOrByName("Val_lit").style.visibility="visible";

	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";

	        		var valueNode = getElementByIdOrByName('Val_lit');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['valValueTxt'];
	        		}

	        	}else if(questionType != null && questionType=='NumericCoded'){
	        		getElementByIdOrByName("Num_Coded").style.visibility="visible";
	        		getElementByIdOrByName("Num_Coded").style.display = "";
	        		getElementByIdOrByName("Val_num").style.visibility="visible";
	        		getElementByIdOrByName("Val_code").style.visibility="visible";
	        		
	        		getElementByIdOrByName("Part_Per_Div").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";

	        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("Val_code");
					   	DWRUtil.addOptions("Val_code", data, "key", "value" );
	        		});
	        		var valueNode1 = getElementByIdOrByName('Val_num');
	        		var valueNode2 = getElementByIdOrByName('Val_code');
	        		var valueTxtNode = getElementByIdOrByName('Val_code_textbox');
	        		var valueNode = valueNode1.value+"^"+valueNode2.value;
	        		if (valueNode != null) {
	        			var valueCoded = ans.answerMap['valueList3'];
	        			var mySplitResult = valueCoded.split("^");
	        			valueNode1.value = mySplitResult[0];
	        			valueNode2.value = mySplitResult[1];
	        			valueTxtNode.value = ans.answerMap['valCodeTxt'];
	        		}
	        	}else if(ans.answerMap['questionType'] == 'ParticipationPerson'
	        	      || ans.answerMap['questionType'] == 'ParticipationOrganization') {
	        		getElementByIdOrByName("Val_text").value="";
	        		getElementByIdOrByName("Val_date").value="";
	        		getElementByIdOrByName("Val_textArea").value="";
			   	getElementByIdOrByName("date").style.display = "none";
	        		getElementByIdOrByName("text").style.display = "none";
	        		getElementByIdOrByName("Val_textArea").style.display = "none";
	        		getElementByIdOrByName("m_sel").style.display = "none";
	        		getElementByIdOrByName("s_sel").style.display = "none";
	        		getElementByIdOrByName("Num_Coded").style.display = "none";
	        		getElementByIdOrByName("Num_Literal").style.display = "none";
				getElementByIdOrByName("Part_Per_Div").style.display = "";
	        		$j("#PartPerSearchControls").children().hide();
	     			$j("#PartPerCodeClearButton").val("Clear/Reassign");
	     			$j("#PartPerCodeClearButton").css("color", "#000");
	     			$j("#PartPerCodeClearButton").attr("disabled", false);

	     			$j("#valueL").parent().find("span[title]").css("color", "#000");
	     			getElementByIdOrByName("clearPartPer").className="";
					getElementByIdOrByName("clearPartPer").setAttribute("class","");
					
	     			$j("#clearPartPer").children().show();
	     			var srchRslt = ans.answerMap['participationSrchRslt'];
	        		$j('#PartPer').html(srchRslt);
	        		getElementByIdOrByName("PartPerS").style.display = "";
	        		$j("#PartPerS").children().show();
	        	}

	        	var behaviorNode = getElementByIdOrByName('behavior');
	        	var behaviorTxtNode = getElementByIdOrByName('behavior_textbox');
	        	if (behaviorNode != null) {
	        		behaviorNode.value = ans.answerMap['behavior'];
	        		behaviorTxtNode.value = ans.answerMap['behaviorTxt'];

	        	}

				$j("#AddButtonToggleIdSubSection").hide();
				$j("#AddNewButtonToggleIdSubSection").hide();
				$j("#UpdateButtonToggleIdSubSection").show();
				break;
			}
		}

	}); //all rows of data
	

}


function addNewBatchIdEntryFields(subSection)
{
	clearBatchEntryFields(subSection);
	$j($j("#questionList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#questionList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#questionList").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#questionL").parent().find("span[title]").css("color", "#000");

	$j("#Val_text").parent().find(":input").attr("disabled", false);
	$j("#valueL").parent().find("span[title]").css("color", "#000");

	$j("#Val_textArea").parent().find(":input").attr("disabled", true);
	$j("#Val_date").parent().find(":input").attr("disabled", true);
	$j("#Val_dateIcon").parent().find("img").attr("disabled", true);
	$j("#Val_dateIcon").parent().find("img").attr("tabIndex", "-1");
	
	$j($j("#valueList2").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#valueList2").parent().parent()).find("img").attr("disabled", true);
	$j($j("#valueList2").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j($j("#valueList1").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#valueList1").parent().parent()).find("img").attr("disabled", true);
	$j($j("#valueList1").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#Val_lit").parent().find(":input").attr("disabled", true);
	$j("#Val_num").parent().find(":input").attr("disabled", true);
	$j($j("#Val_code").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#Val_code").parent().parent()).find("img").attr("tabIndex", "-1");
	$j($j("#Val_code").parent().parent()).find("img").attr("tabIndex", "-1");
	

	getElementByIdOrByName("Val_text").style.visibility="visible";
	getElementByIdOrByName("text").style.visibility="visible";
	getElementByIdOrByName("text").style.display = "";

	getElementByIdOrByName("Val_textArea").style.display = "none";
	getElementByIdOrByName("date").style.display = "none";
	getElementByIdOrByName("m_sel").style.display = "none";
	getElementByIdOrByName("s_sel").style.display = "none";
	getElementByIdOrByName("Num_Coded").style.display = "none";
	getElementByIdOrByName("Num_Literal").style.display = "none";

	$j($j("#behavior").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#behavior").parent().parent()).find("img").attr("disabled", false);
	$j($j("#behavior").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#bahaviorL").parent().find("span[title]").css("color", "#000");

	$j("#AddButtonToggleIdSubSection").show();
	$j("#AddNewButtonToggleIdSubSection").hide();
	$j("#UpdateButtonToggleIdSubSection").hide();
}

function viewClickedOnViewPage(entryId,tableId)
{
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;
	dwr.engine.beginBatch();
	//get all rows of data
	JDecisionSupport.getAllBatchAnswer(tableId,function(answer) {
		if (answer.length > 0) {
			var ans = answer[beIdStr];
			var id = ans.id;
			gBatchEntryUpdateSeq = id;

			if(tableId == 'defaultTable'){
				$j("#defaultTable").show();
				dwr.util.setValue("question",ans.answerMap['queListTxt']);
				dwr.util.setValue("value",ans.answerMap['valValueTxt']);
				dwr.util.setValue("behavior",ans.answerMap['behaviorTxt']);
			}
		}
	}); //all rows of data
	dwr.engine.endBatch();
}  //viewClicked

function getDWRLogic(question)
{
	var fieldType ="";
	var valueSet = "";
	if(question.value != null || question.value != "")
	{
		JDecisionSupport.getDWRQuestionProperties(question.value, function(data){
			fieldType = data;
			if(data == 'Text'|| data == 'TextArea'|| data.indexOf("unitValue")>=0){
				valueSet="SEARCH_ALPHA";
			}else if(data == 'Date'){
				valueSet="SEARCH_NUM";
			}else if(data.indexOf("MULTISELECT")>=0 || data.indexOf("SINGLESELECT")>=0 || data.indexOf("codedUnit")>=0){
				valueSet="SEARCH_ALPHA";
			}else if (data == 'Numeric')
			{
				valueSet="SEARCH_NUM";
			}
			if(valueSet.length >0){
				JDecisionSupport.getDWRAdvancedValueList(valueSet, function(data){
					DWRUtil.removeAllOptions("advLogicList");
					DWRUtil.addOptions("advLogicList", data, "key", "value" );
					if ($j("#advLogicList option:first").val() != "") {
						$j("#advLogicList").prepend("<option value=''></option>");
						$j("#advLogicList option:first").attr("selected", "selected");
					}
				});
			}
		});
	}
}

function getDWRInvLogic(question)
{
	var fieldType ="";
	var valueSet = "";
	if(question.value != null || question.value != "")
	{
		JDecisionSupport.getDWRAdvInvQuestionProperties(question.value, function(data){
			fieldType = data;
			if(data == 'Text'|| data == 'TextArea'|| data.indexOf("unitValue")>=0){
				valueSet="SEARCH_ALPHA";
			}else if(data == 'Date'){
				valueSet="SEARCH_NUM";
			}else if(data.indexOf("MULTISELECT")>=0 || data.indexOf("SINGLESELECT")>=0 || data.indexOf("codedUnit")>=0){
				valueSet="SEARCH_ALPHA";
			}else if (data == 'Numeric')
			{
				valueSet="SEARCH_NUM";
			}
			if(valueSet.length >0){
				JDecisionSupport.getDWRAdvancedInvValueList(valueSet, function(data){
					DWRUtil.removeAllOptions("advInvLogicList");
					DWRUtil.addOptions("advInvLogicList", data, "key", "value" );
					if ($j("#advInvLogicList option:first").val() != "") {
						$j("#advInvLogicList").prepend("<option value=''></option>");
						$j("#advInvLogicList option:first").attr("selected", "selected");
					}
				});
			}
		});
	}
}

function getDWRAdvValue(question){
	   checkDuplicateAdvancedCriteria(question);
	   var fieldType ="";
	   var valueSet = "";
	   if(question.value != null && question.value != "")
	   {
		   JDecisionSupport.getDWRQuestionProperties(question.value, function(data){
			   fieldType = data;

			   if(data == 'Text'){
				   getElementByIdOrByName("advVal_text").style.visibility="visible";
				   getElementByIdOrByName("advText").style.visibility="visible";
				   getElementByIdOrByName("advText").style.display = "";

				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advNum_Literal").style.display = "none";
				   $j("#advVal_text").parent().find(":input").attr("disabled", false);

				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_lit").parent().find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data == 'TextArea'){
				   getElementByIdOrByName("advVal_textArea").style.visibility="visible";
				   getElementByIdOrByName("advVal_textArea").style.display="";

				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advNum_Literal").style.display = "none";
				   $j("#advVal_textArea").parent().find(":input").attr("disabled", false);

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_lit").parent().find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data == 'Date'){
				   var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
					currentDateNode.checked=true
				   getElementByIdOrByName("advVal_date").style.visibility="visible";
				   getElementByIdOrByName("advDate").style.visibility="visible";
				   getElementByIdOrByName("advDate").style.display = "";
				   getElementByIdOrByName("advVal_dateIcon").style.visibility="visible";
				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advNum_Literal").style.display = "none";
				   $j("#advVal_date").parent().find(":input").attr("disabled", false);
				   $j("#advVal_dateIcon").parent().find("img").attr("disabled", false);
				   $j("#advVal_dateIcon").parent().find("img").attr("tabIndex", "0");
				   

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_lit").parent().find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data.indexOf("MULTISELECT")>=0){
				   getElementByIdOrByName("advM_sel").style.visibility="visible";
				   getElementByIdOrByName("advM_sel").style.display = "";
				   getElementByIdOrByName("advValueList2").style.visibility="visible";

				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advNum_Literal").style.display = "none";

				   $j($j("#advValueList2").parent().parent()).find(":input").attr("disabled", false);
				   $j($j("#advValueList2").parent().parent()).find("img").attr("disabled", false);
				   $j($j("#advValueList2").parent().parent()).find("img").attr("tabIndex", "0");
				   

				   valueSet = data.substring(data.indexOf("=") + 1, data.length);

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_lit").parent().find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data.indexOf("SINGLESELECT")>=0){
				   getElementByIdOrByName("advS_sel").style.visibility="visible";
				   getElementByIdOrByName("advValueList1").style.visibility="visible";
				   getElementByIdOrByName("advS_sel").style.display = "";

				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advNum_Literal").style.display = "none";
				   $j($j("#advValueList1").parent().parent()).find(":input").attr("disabled", false);
				   $j($j("#advValueList1").parent().parent()).find("img").attr("disabled", false);
				   $j($j("#advValueList1").parent().parent()).find("img").attr("tabIndex", "0");
				   
				   valueSet = data.substring(data.indexOf("=") + 1, data.length);

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j("#advVal_lit").parent().find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");
			   }else if(data.indexOf("codedUnit")>=0){
				   getElementByIdOrByName("advNum_Coded").style.visibility="visible";
				   getElementByIdOrByName("advVal_code").style.visibility="visible";
				   getElementByIdOrByName("advNum_Coded").style.display = "";

				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Literal").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   $j("#advVal_num").parent().find(":input").attr("disabled", false);
				   $j($j("#advVal_code").parent().parent()).find(":input").attr("disabled", false);
				   $j($j("#advVal_code").parent().parent()).find("img").attr("disabled", false);
				   $j($j("#advVal_code").parent().parent()).find("img").attr("tabIndex", "0");
				   

				   valueSet = data.substring(data.indexOf("=") + 1, data.length);

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_lit").parent().find(":input").val("");

			   }else if(data.indexOf("unitValue")>=0){
				   getElementByIdOrByName("advNum_Literal").style.visibility="visible";
				   getElementByIdOrByName("advNum_Literal").style.display = "";

				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   $j("#advVal_lit").parent().find(":input").attr("disabled", false);

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");

			   }
			   else if(data == 'Numeric'){
				   getElementByIdOrByName("advVal_lit").style.visibility="visible";
				   getElementByIdOrByName("advNum_Literal").style.visibility="visible";
				   getElementByIdOrByName("advNum_Literal").style.display = "";

				   getElementByIdOrByName("advText").style.display = "none";
				   getElementByIdOrByName("advVal_textArea").style.display = "none";
				   getElementByIdOrByName("advDate").style.display = "none";
				   getElementByIdOrByName("advM_sel").style.display = "none";
				   getElementByIdOrByName("advNum_Coded").style.display = "none";
				   getElementByIdOrByName("advS_sel").style.display = "none";
				   $j("#advVal_lit").parent().find(":input").attr("disabled", false);

				   $j("#advVal_text").parent().find(":input").val("");
				   $j("#advVal_textArea").parent().find(":input").val("");
				   $j("#advVal_date").parent().find(":input").val("");
				   $j($j("#advValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
				   $j($j("#advValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advVal_num").parent().find(":input").val("");
				   $j($j("#advVal_code_textbox").parent().parent()).find(":input").val("");

			   }
			   if(valueSet.length >0){
				   JDecisionSupport.getDWRValueList(valueSet, function(data){
					   if(fieldType != null && fieldType.indexOf("SINGLESELECT")>=0){
						   DWRUtil.removeAllOptions("advValueList1");
						   DWRUtil.addOptions("advValueList1", data, "key", "value" );
					   }else if(fieldType != null && fieldType.indexOf("MULTISELECT")>=0){
						   DWRUtil.removeAllOptions("advValueList2");
						   DWRUtil.addOptions("advValueList2", data, "key", "value" );
					   }else if(fieldType != null && fieldType.indexOf("codedUnit")>=0){
						   DWRUtil.removeAllOptions("advVal_code");
						   DWRUtil.addOptions("advVal_code", data, "key", "value" );
					   }
				   });
			   }
		   });
	   }

	}

function getDWRAdvInvValue(question){
	   var fieldType ="";
	   var valueSet = "";
	   if(question.value != null && question.value != "")
	   {
		   checkDuplicateAdvancedInvCriteria(question);
		   JDecisionSupport.getDWRAdvInvQuestionProperties(question.value, function(data){
			   fieldType = data;

			   if(data == 'Text'){
				   getElementByIdOrByName("advInvVal_text").style.visibility="visible";
				   getElementByIdOrByName("advInvText").style.visibility="visible";
				   getElementByIdOrByName("advInvText").style.display = "";

				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "none";
				   $j("#advInvVal_text").parent().find(":input").attr("disabled", false);

				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_lit").parent().find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data == 'TextArea'){
				   getElementByIdOrByName("advInvVal_textArea").style.visibility="visible";
				   getElementByIdOrByName("advInvVal_textArea").style.display="";

				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "none";
				   $j("#advInvVal_textArea").parent().find(":input").attr("disabled", false);

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   //$j($j('valueList2-selectedValues').parent().parent())find(":div").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_lit").parent().find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data == 'Date'){
				   var currentDateNode = getElementByIdOrByName('CURRENT_CURRENT_SELECT_DATE_LOGIC');
					currentDateNode.checked=true
				   getElementByIdOrByName("advInvVal_date").style.visibility="visible";
				   getElementByIdOrByName("advInvDate").style.visibility="visible";
				   getElementByIdOrByName("advInvDate").style.display = "";
				   getElementByIdOrByName("advInvVal_dateIcon").style.visibility="visible";
				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "none";
				   $j("#advInvVal_date").parent().find(":input").attr("disabled", false);
				   $j("#advInvVal_dateIcon").parent().find("img").attr("disabled", false);
				   $j("#advInvVal_dateIcon").parent().find("img").attr("tabIndex", "0");
				   

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_lit").parent().find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data.indexOf("MULTISELECT")>=0){
				   getElementByIdOrByName("advInvM_sel").style.visibility="visible";
				   getElementByIdOrByName("advInvM_sel").style.display = "";
				   getElementByIdOrByName("advInvValueList2").style.visibility="visible";

				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "none";

				   $j($j("#advInvValueList2").parent().parent()).find(":input").attr("disabled", false);
				   $j($j("#advInvValueList2").parent().parent()).find("img").attr("disabled", false);
				   $j($j("#advInvValueList2").parent().parent()).find("img").attr("tabIndex", "0");
				   

				   valueSet = data.substring(data.indexOf("=") + 1, data.length);

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_lit").parent().find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");

			   }else if(data.indexOf("SINGLESELECT")>=0){
				   getElementByIdOrByName("advInvS_sel").style.visibility="visible";
				   getElementByIdOrByName("advInvValueList1").style.visibility="visible";
				   getElementByIdOrByName("advInvS_sel").style.display = "";

				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "none";
				   $j($j("#advInvValueList1").parent().parent()).find(":input").attr("disabled", false);
				   $j($j("#advInvValueList1").parent().parent()).find("img").attr("disabled", false);
				   $j($j("#advInvValueList1").parent().parent()).find("img").attr("tabIndex", "0");
				   
				   valueSet = data.substring(data.indexOf("=") + 1, data.length);

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j("#advInvVal_lit").parent().find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");
			   }else if(data.indexOf("codedUnit")>=0){
				   getElementByIdOrByName("advInvNum_Coded").style.visibility="visible";
				   getElementByIdOrByName("advInvVal_code").style.visibility="visible";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "";

				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   $j("#advInvVal_num").parent().find(":input").attr("disabled", false);
				   $j($j("#advInvVal_code").parent().parent()).find(":input").attr("disabled", false);
				   $j($j("#advInvVal_code").parent().parent()).find("img").attr("disabled", false);
				   $j($j("#advInvVal_code").parent().parent()).find("img").attr("tabIndex", "0");
				   

				   valueSet = data.substring(data.indexOf("=") + 1, data.length);

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_lit").parent().find(":input").val("");

			   }else if(data.indexOf("unitValue")>=0){
				   getElementByIdOrByName("advInvNum_Literal").style.visibility="visible";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "";

				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   $j("#advInvVal_lit").parent().find(":input").attr("disabled", false);

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");

			   }
			   else if(data == 'Numeric'){
				   getElementByIdOrByName("advInvVal_lit").style.visibility="visible";
				   getElementByIdOrByName("advInvNum_Literal").style.visibility="visible";
				   getElementByIdOrByName("advInvNum_Literal").style.display = "";

				   getElementByIdOrByName("advInvText").style.display = "none";
				   getElementByIdOrByName("advInvVal_textArea").style.display = "none";
				   getElementByIdOrByName("advInvDate").style.display = "none";
				   getElementByIdOrByName("advInvM_sel").style.display = "none";
				   getElementByIdOrByName("advInvNum_Coded").style.display = "none";
				   getElementByIdOrByName("advInvS_sel").style.display = "none";
				   $j("#advInvVal_lit").parent().find(":input").attr("disabled", false);

				   $j("#advInvVal_text").parent().find(":input").val("");
				   $j("#advInvVal_textArea").parent().find(":input").val("");
				   $j("#advInvVal_date").parent().find(":input").val("");
				   $j($j("#advInvValueList2_textbox").parent().parent()).find(":input").val("");
				   getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
				   $j($j("#advInvValueList1_textbox").parent().parent()).find(":input").val("");
				   $j("#advInvVal_num").parent().find(":input").val("");
				   $j($j("#advInvVal_code_textbox").parent().parent()).find(":input").val("");

			   }
			   if(valueSet.length >0){
				   JDecisionSupport.getDWRAdvancedInvValueList(valueSet, function(data){
					   if(fieldType != null && fieldType.indexOf("SINGLESELECT")>=0){
						   DWRUtil.removeAllOptions("advInvValueList1");
						   DWRUtil.addOptions("advInvValueList1", data, "key", "value" );
						   
					   }else if(fieldType != null && fieldType.indexOf("MULTISELECT")>=0){
						   DWRUtil.removeAllOptions("advInvValueList2");
						   DWRUtil.addOptions("advInvValueList2", data, "key", "value" );
					   }else if(fieldType != null && fieldType.indexOf("codedUnit")>=0){
						   DWRUtil.removeAllOptions("advInvVal_code");
						   DWRUtil.addOptions("advInvVal_code", data, "key", "value" );
					   }
				   });
			   }
		   });
	   }

	}


function writeAdvCriteriaBatchIdEntry( subSectNm,pattern,questionbody)
{
	var t = getElementByIdOrByName("IdAdvancedSubSection");
	t.style.display = "";
	for (var i = 0; i < 3; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}

	var seqNum = 0;
	var questionType = "";
	//var questionUid = getElementByIdOrByName("questionAdvList").options[getElementByIdOrByName("questionAdvList").selectedIndex].value;
	var textField = getElementByIdOrByName("advVal_text").value;
	var textAreaField = getElementByIdOrByName("advVal_textArea").value;
	var textDate = getElementByIdOrByName("advVal_date").value;
	var singleSelect = getElementByIdOrByName("advValueList1").value;
	var multiselect = getElementByIdOrByName("advValueList2").value;

	if(multiselect == null || multiselect == "")
	{
		var valueNode = getElementByIdOrByName('advValueList2');
		var myListCount = valueNode.options.length;
		for (i=0; i < myListCount; i++) {
			if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
				multiselect = "AS";
				break;
			}
		}
	}

	var numericLiteral = getElementByIdOrByName("advVal_lit").value;
	var numericCoded = getElementByIdOrByName("advVal_code").value;
	if(textField!=null && textField!=""){
		questionType = "Text";
	}
	else if(textField!=null && textField!=""){
			questionType = "Text";
	}
	else if(textAreaField!=null && textAreaField!=""){
			questionType = "TextArea";
	}
	else if(textDate!=null && textDate!=""){
			questionType = "Date";
	}
	else if(singleSelect!=null && singleSelect!=""){
			questionType = "SINGLESELECT";
	}
	else if(multiselect!=null && multiselect!=""){
			questionType = "MULTISELECT";
	}
	else if(numericCoded!=null && numericCoded!=""){
			questionType = "NumericCoded";
	}
	else if(numericLiteral!=null && numericLiteral!=""){
			questionType = "NumericLiteral";
	}

	dwr.engine.beginBatch();
	var ansMap = getAdvancedBatchAnswerMapFromScreen(questionType);
	var batchentry = { subSectNm:subSectNm, answerMap:ansMap };
	JDecisionSupport.setAdvancedCriteriaBatchAnswer(batchentry);
	rewriteAdvancedCriteriaBatchIdHeader(subSectNm, pattern, questionbody);
	clearAdvancedCriteriaBatchEntryFields(subSectNm);
	dwr.engine.endBatch();

}

function writeAdvInvCriteriaBatchIdEntry( subSectNm,pattern,questionbody)
{
	var t = getElementByIdOrByName("IdAdvancedInvSubSection");
	t.style.display = "";
	for (var i = 0; i < 3; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}

	var seqNum = 0;
	var questionType = "";
	//var questionUid = getElementByIdOrByName("questionAdvList").options[getElementByIdOrByName("questionAdvList").selectedIndex].value;
	var textField = getElementByIdOrByName("advInvVal_text").value;
	var textAreaField = getElementByIdOrByName("advInvVal_textArea").value;
	var textDate = getElementByIdOrByName("advInvVal_date").value;
	var singleSelect = getElementByIdOrByName("advInvValueList1").value;
	var multiselect = getElementByIdOrByName("advInvValueList2").value;

	if(multiselect == null || multiselect == "")
	{
		var valueNode = getElementByIdOrByName('advInvValueList2');
		var myListCount = valueNode.options.length;
		for (i=0; i < myListCount; i++) {
			if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
				multiselect = "AS";
				break;
			}
		}
	}

	var numericLiteral = getElementByIdOrByName("advInvVal_lit").value;
	var numericCoded = getElementByIdOrByName("advInvVal_code").value;
	if(textField!=null && textField!=""){
		questionType = "Text";
	}
	else if(textField!=null && textField!=""){
			questionType = "Text";
	}
	else if(textAreaField!=null && textAreaField!=""){
			questionType = "TextArea";
	}
	else if(textDate!=null && textDate!=""){
			questionType = "Date";
	}
	else if(singleSelect!=null && singleSelect!=""){
			questionType = "SINGLESELECT";
	}
	else if(multiselect!=null && multiselect!=""){
			questionType = "MULTISELECT";
	}
	else if(numericCoded!=null && numericCoded!=""){
			questionType = "NumericCoded";
	}
	else if(numericLiteral!=null && numericLiteral!=""){
			questionType = "NumericLiteral";
	}

	dwr.engine.beginBatch();
	var ansMap = getAdvancedInvBatchAnswerMapFromScreen(questionType);
	var batchentry = { subSectNm:subSectNm, answerMap:ansMap };
	JDecisionSupport.setAdvancedInvCriteriaBatchAnswer(batchentry);
	rewriteAdvancedInvCriteriaBatchIdHeader(subSectNm, pattern, questionbody);
	clearAdvancedInvCriteriaBatchEntryFields(subSectNm);
	dwr.engine.endBatch();

}

function writeElrAdvCriteriaBatchIdEntry( subSectNm,pattern,questionbody)
{	
	changeElrSectionLightBGColor();

	var seqNum = 0;
	var questionType = "";

	dwr.engine.beginBatch();
	var ansMap = getElrAdvancedBatchAnswerMapFromScreen();
	var batchentry = { subSectNm:subSectNm, answerMap:ansMap };
	JDecisionSupport.setElrAdvancedCriteriaBatchAnswer(batchentry);
	rewriteElrAdvancedCriteriaBatchIdHeader(subSectNm, pattern, questionbody);
	clearElrAdvancedCriteriaBatchEntryFields(subSectNm);
	enableElrAdvancedCriteriaBatchEntryFields();
	dwr.engine.endBatch();
}

function addNewElrAdvCriteriaBatchIdEntry(subSectNm)
{
	clearElrAdvancedCriteriaBatchEntryFields(subSectNm);
	enableElrAdvancedCriteriaBatchEntryFields();
}

function getElrAdvancedBatchAnswerMapFromScreen()
{
	var ansMap = {};

	var resultedTestCode = getElementByIdOrByName('testCodeId');
	var resultedTestName = getElementByIdOrByName('testDescriptionId');
	if (resultedTestCode != null && resultedTestCode.value != "") {
		var resultedTestCodeValue = resultedTestCode.value;
		ansMap['ResultedTestCode'] = resultedTestCodeValue;
		var resultedTestNameValue = resultedTestName.value;
		ansMap['ResultedTestName'] = resultedTestNameValue;
	}

	//var codedResultAdvList = getElementByIdOrByName('codedResultAdvList');
	//var codedResultAdvListTxtNode = getElementByIdOrByName('codedResultAdvList_textbox');
	var codedResultAdvList = getElementByIdOrByName('codeResultId');
	var codedResultAdvListTxtNode = getElementByIdOrByName('resultDescriptionId');
	if (codedResultAdvList != null && codedResultAdvList.value != "" && codedResultAdvList.value != "null") {
		var codedResultAdvList = codedResultAdvList.value;
		ansMap['CodedResultAdvList'] = codedResultAdvList;
		var codedResultAdvListTxt = codedResultAdvListTxtNode.value;
		ansMap['CodedResultAdvListTxt'] = codedResultAdvListTxt;
		ansMap['ResultNameTxt'] = codedResultAdvListTxt;
	}

	var numericResultNode = getElementByIdOrByName('numericResult_text');
	if (numericResultNode != null && numericResultNode.value != "") {
		var value = numericResultNode.value;
		ansMap['NumericResultTxt']=value;
		ansMap['ResultNameTxt'] = value;
	}

	var numericResultHighNode = getElementByIdOrByName('numericResultHigh_text');
	if (numericResultHighNode != null && numericResultHighNode.value != "") {
		ansMap['numericResultHighTxt'] = numericResultHighNode.value;
		ansMap['ResultNameTxt'] = ansMap['ResultNameTxt'] + "-" + numericResultHighNode.value;
	}
	
	var numericResultTypeListNode = getElementByIdOrByName('numericResultTypeList');
	var numericResultTypeListTxtNode = getElementByIdOrByName('numericResultTypeList_textbox');
	if (numericResultTypeListNode != null && numericResultTypeListNode.value != "") {
		var valueList = numericResultTypeListNode.value;
		ansMap['NumericResultTypeList']=valueList;
		var valueListTxt = numericResultTypeListTxtNode.value;
		ansMap['NumericResultTypeListTxt']= valueListTxt;

		ansMap['ResultNameTxt'] = ansMap['ResultNameTxt'] + " " + valueListTxt;
	}

	var textResultNode = getElementByIdOrByName('textResult_text');
	
	if (textResultNode != null && textResultNode.value != "") {
		var value = textResultNode.value;
		ansMap['TextResultTxt']=value;
		ansMap['ResultNameTxt']= value;
		//ansMap['ResultNameOperator']= resultOperatorList.value;
		//ansMap['ResultNameOperatorTxt']= resultOperatorListNode.value;
	}
	
	
	var resultOperatorList = getElementByIdOrByName('resultOperatorList');
	var resultOperatorListNode = getElementByIdOrByName('resultOperatorList_textbox');
	if(resultOperatorList!=null && resultOperatorList.value != ""){
		ansMap['ResultNameOperator']= resultOperatorList.value;
		ansMap['ResultNameOperatorTxt']= resultOperatorListNode.value;
	}
	
	var numericResultOperatorList = getElementByIdOrByName('numericResultOperatorList');
	var numericResultOperatorListNode = getElementByIdOrByName('numericResultOperatorList_textbox');
	if(numericResultOperatorList!=null && numericResultOperatorList.value != ""){
		ansMap['NumericResultNameOperator'] = numericResultOperatorList.value;
		ansMap['ResultNameOperatorTxt'] = numericResultOperatorListNode.value;
	}
	
	return ansMap;
}

function getAdvancedBatchAnswerMapFromScreen(questionType)
{
	var ansMap = {};
	var questionListNode = getElementByIdOrByName('questionAdvList');
	var questionListTxtNode = getElementByIdOrByName('questionAdvList_textbox');
	if (questionListNode != null) {
		var queList = questionListNode.value;
		ansMap['AdvQuestionList'] = queList;
		var queListTxt = questionListTxtNode.value;
		ansMap['AdvQueListTxt'] = queListTxt;
	}

	if(questionType != null && questionType=='Text'){
		var valueNode = getElementByIdOrByName('advVal_text');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvValValueTxt']=value;
			ansMap['AdvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='TextArea'){
		var valueNode = getElementByIdOrByName('advVal_textArea');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvValValueTxt']=value;
			ansMap['AdvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='Date'){
		var valueNode = getElementByIdOrByName('advVal_date');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvValValueTxt']=value;
			ansMap['AdvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='SINGLESELECT'){
		var valueNode = getElementByIdOrByName('advValueList1');
		var valueTxtNode = getElementByIdOrByName('advValueList1_textbox');
		if (valueNode != null) {
			var valueList = valueNode.value;
			ansMap['AdvValueList1']=valueList;
			var valueListTxt = valueTxtNode.value;
			ansMap['AdvValValueTxt']= valueListTxt;
			ansMap['AdvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='MULTISELECT'){
		var valueNode = getElementByIdOrByName('advValueList2');
		var optionsVal = "";
		var optionsTxt = "";
		var myListCount = valueNode.options.length;
		if (valueNode != null) {
			for (i=0; i < myListCount; i++) {
				if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
				optionsVal = optionsVal +"^^"+valueNode.options[i].value;
				optionsTxt = optionsTxt +"^Value^"+valueNode.options[i].text;
				}
			}
			var valueTxtNode = getElementByIdOrByName('advValueList2-selectedValues').innerHTML;
			ansMap['AdvValueList2']=optionsVal;
			var valueListTxt = valueTxtNode.substring(valueTxtNode.lastIndexOf(">")+1, valueTxtNode.length);;
			ansMap['AdvValValueTxt']=valueListTxt;
			ansMap['AdvQuestionType']=questionType;
			getElementByIdOrByName("advValueList2").options.length = 0;
			getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
		}
	}else if(questionType != null && questionType=='NumericLiteral'){
		var valueNode = getElementByIdOrByName('advVal_lit');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvValValueTxt']=value;
			ansMap['AdvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='NumericCoded'){
		var valueNode1 = getElementByIdOrByName('advVal_num');
		var valueNode2 = getElementByIdOrByName('advVal_code');
		var valueTxtNode = getElementByIdOrByName('advVal_code_textbox');

		var valueNode = valueNode1.value+"^"+valueNode2.value;
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvValueList3']=valueNode;
			var valueListTxt = valueTxtNode.value;
			ansMap['AdvValValueTxt']= valueNode1.value+valueTxtNode.value;
			ansMap['AdvValCodeTxt']= valueTxtNode.value;
			ansMap['AdvQuestionType']=questionType;
		}
	}
	var logicNode = getElementByIdOrByName("advLogicList");
	var logicTxtNode = getElementByIdOrByName("advLogicList_textbox");
	if (logicNode != null) {
		var LogList = logicNode.value;
		ansMap['AdvLogicList'] = LogList;
		var logListTxt = logicTxtNode.value;
		ansMap['AdvLogicTxt'] = logListTxt;
	}

	return ansMap;
}

function getAdvancedInvBatchAnswerMapFromScreen(questionType)
{
	var ansMap = {};
	var questionListNode = getElementByIdOrByName('questionAdvInvList');
	var questionListTxtNode = getElementByIdOrByName('questionAdvInvList_textbox');
	if (questionListNode != null) {
		var queList = questionListNode.value;
		ansMap['AdvInvQuestionList'] = queList;
		var queListTxt = questionListTxtNode.value;
		ansMap['AdvInvQueListTxt'] = queListTxt;
	}

	if(questionType != null && questionType=='Text'){
		var valueNode = getElementByIdOrByName('advInvVal_text');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvInvValValueTxt']=value;
			ansMap['AdvInvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='TextArea'){
		var valueNode = getElementByIdOrByName('advInvVal_textArea');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvInvValValueTxt']=value;
			ansMap['AdvInvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='Date'){
		var valueNode = getElementByIdOrByName('advInvVal_date');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvInvValValueTxt']=value;
			ansMap['AdvInvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='SINGLESELECT'){
		var valueNode = getElementByIdOrByName('advInvValueList1');
		var valueTxtNode = getElementByIdOrByName('advInvValueList1_textbox');
		if (valueNode != null) {
			var valueList = valueNode.value;
			ansMap['AdvInvValueList1']=valueList;
			var valueListTxt = valueTxtNode.value;
			ansMap['AdvInvValValueTxt']= valueListTxt;
			ansMap['AdvInvQuestionType']=questionType;

		}
	}else if(questionType != null && questionType=='MULTISELECT'){
		var valueNode = getElementByIdOrByName('advInvValueList2');
		var optionsVal = "";
		var optionsTxt = "";
		var myListCount = valueNode.options.length;
		if (valueNode != null) {
			for (i=0; i < myListCount; i++) {
				if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
				optionsVal = optionsVal +"^^"+valueNode.options[i].value;
				optionsTxt = optionsTxt +"^Value^"+valueNode.options[i].text;
				}
			}
			var valueTxtNode = getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML;
			ansMap['AdvInvValueList2']=optionsVal;
			var valueListTxt = valueTxtNode.substring(valueTxtNode.lastIndexOf(">")+1, valueTxtNode.length);;
			ansMap['AdvInvValValueTxt']=valueListTxt;
			ansMap['AdvInvQuestionType']=questionType;
			getElementByIdOrByName("advInvValueList2").options.length = 0;
			getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
		}
	}else if(questionType != null && questionType=='NumericLiteral'){
		var valueNode = getElementByIdOrByName('advInvVal_lit');
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvInvValValueTxt']=value;
			ansMap['AdvInvQuestionType']=questionType;
		}
	}else if(questionType != null && questionType=='NumericCoded'){
		var valueNode1 = getElementByIdOrByName('advInvVal_num');
		var valueNode2 = getElementByIdOrByName('advInvVal_code');
		var valueTxtNode = getElementByIdOrByName('advInvVal_code_textbox');

		var valueNode = valueNode1.value+"^"+valueNode2.value;
		if (valueNode != null) {
			var value = valueNode.value;
			ansMap['AdvInvValueList3']=valueNode;
			var valueListTxt = valueTxtNode.value;
			ansMap['AdvInvValValueTxt']= valueNode1.value+valueTxtNode.value;
			ansMap['AdvInvValCodeTxt']= valueTxtNode.value;
			ansMap['AdvInvQuestionType']=questionType;
		}
	}
	var logicNode = getElementByIdOrByName("advInvLogicList");
	var logicTxtNode = getElementByIdOrByName("advInvLogicList_textbox");
	if (logicNode != null) {
		var LogList = logicNode.value;
		ansMap['AdvInvLogicList'] = LogList;
		var logListTxt = logicTxtNode.value;
		ansMap['AdvInvLogicTxt'] = logListTxt;
	}
	return ansMap;
}

function rewriteElrAdvancedCriteriaBatchIdHeader(subSectNm, pattern, questionbody)
{
 //get all rows of data
JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSectNm,function(answer) {
	// Delete all the rows except for the "pattern" row
	dwr.util.removeAllRows(questionbody, { filter:function(tr) {
			return (tr.id != pattern);
	}});

	if (answer.length == 0) {
		//no rows - display 'No Data has been entered'
	 	//$('nopattern' +subSectNm).style.display = "block";
	 	return;
	 }

	for (var i = 0; i < answer.length; i++){
		if (i%2 == 0)
	      	rowClass="odd";
		else
			rowClass="even";
		ans = answer[i];
		id = ans.id;
		dwr.util.cloneNode(pattern, { idSuffix:id });
	      //pull the data for each row
	      for (var key in ans.answerMap) {
	      	if (key == 'ResultedTestName' ||key == 'ResultNameTxt' || key=='ResultNameOperatorTxt') {
	      		var val = ans.answerMap[key];
				dwr.util.setValue("table" + key + id, val);
			}
	     }
	     //clear display = 'none'
	      $(pattern + id).style.display = "";
	     //hide No Data Entered
	     $('nopattern' +subSectNm).style.display = "none";
	     getElementByIdOrByName(pattern + id).setAttribute("className",rowClass);
		  getElementByIdOrByName(pattern + id).setAttribute("class",rowClass);
		 
		 
	}

}); //all rows of data
}

function rewriteAdvancedCriteriaBatchIdHeader(subSectNm, pattern, questionbody)
{
 //get all rows of data
JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSectNm,function(answer) {
	// Delete all the rows except for the "pattern" row
	dwr.util.removeAllRows(questionbody, { filter:function(tr) {
			return (tr.id != pattern);
	}});

	if (answer.length == 0) {
		//no rows - display 'No Data has been entered'
	 	//$('nopattern' +subSectNm).style.display = "block";
	 	return;
	 }

	for (var i = 0; i < answer.length; i++){
		if (i%2 == 0)
	      	rowClass="odd";
		else
			rowClass="even";
		ans = answer[i];
		id = ans.id;
		dwr.util.cloneNode(pattern, { idSuffix:id });
	      //pull the data for each row
	      for (var key in ans.answerMap) {
	      	if (key == 'AdvQueListTxt' ||key == 'AdvValValueTxt' || key == 'AdvLogicTxt') {
	      		var val = ans.answerMap[key];
				dwr.util.setValue("table" + key + id, val);
			}
	     }
	     //clear display = 'none'
	      $(pattern + id).style.display = "";
	     //hide No Data Entered
	     $('nopattern' +subSectNm).style.display = "none";
	     getElementByIdOrByName(pattern + id).setAttribute("className",rowClass);
		  getElementByIdOrByName(pattern + id).setAttribute("class",rowClass);
		 
	}

}); //all rows of data

}



function rewriteAdvancedInvCriteriaBatchIdHeader(subSectNm, pattern, questionbody)
{
 //get all rows of data
JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer(subSectNm,function(answer) {
	// Delete all the rows except for the "pattern" row
	dwr.util.removeAllRows(questionbody, { filter:function(tr) {
			return (tr.id != pattern);
	}});

	if (answer.length == 0) {
		//no rows - display 'No Data has been entered'
	 	//$('nopattern' +subSectNm).style.display = "block";
	 	return;
	 }

	for (var i = 0; i < answer.length; i++){
		if (i%2 == 0)
	      	rowClass="odd";
		else
			rowClass="even";
		ans = answer[i];
		id = ans.id;
		dwr.util.cloneNode(pattern, { idSuffix:id });
	      //pull the data for each row
	      for (var key in ans.answerMap) {
	      	if (key == 'AdvInvQueListTxt' ||key == 'AdvInvValValueTxt' || key == 'AdvInvLogicTxt') {
	      		var val = ans.answerMap[key];
				dwr.util.setValue("table" + key + id, val);
			}
	     }
	     //clear display = 'none'
	      $(pattern + id).style.display = "";
	     //hide No Data Entered
	     $('nopattern' +subSectNm).style.display = "none";
	     getElementByIdOrByName(pattern + id).setAttribute("className",rowClass);
		  getElementByIdOrByName(pattern + id).setAttribute("class",rowClass);
		 
	}

}); //all rows of data

}


function clearAdvancedCriteriaBatchEntryFields (subSectNm)
{
	gAdvBatchEntryUpdateSeq = "";
	$j($j("#questionAdvList").parent().parent()).find(":input").val("");
	$j("#advVal_text").parent().find(":input").val("");
	$j("#advVal_textArea").parent().find(":input").val("");
	$j("#advVal_date").parent().find(":input").val("");
	$j($j("#advValueList2").parent().parent()).find(":input").val("");
	getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
	$j($j("#advValueList1").parent().parent()).find(":input").val("");
	$j($j("#advLogicList").parent().parent()).find(":input").val("");
	$j($j("#questionAdvList").parent().parent()).find(":input").focus();

}

function clearAdvancedInvCriteriaBatchEntryFields (subSectNm)
{
	gAdvInvBatchEntryUpdateSeq = "";
	$j($j("#questionAdvInvList").parent().parent()).find(":input").val("");
	$j("#advInvVal_text").parent().find(":input").val("");
	$j("#advInvVal_textArea").parent().find(":input").val("");
	$j("#advInvVal_date").parent().find(":input").val("");
	$j($j("#advInvValueList2").parent().parent()).find(":input").val("");
	getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
	$j($j("#advInvValueList1").parent().parent()).find(":input").val("");
	$j($j("#advInvLogicList").parent().parent()).find(":input").val("");
	$j($j("#questionAdvInvList").parent().parent()).find(":input").focus();

}

function clearElrAdvancedCriteriaBatchEntryFields (subSectNm)
{
	gElrBatchEntryUpdateSeq = "";
	var testDescription = getElementByIdOrByName("testDescription");
	var testCodeId = getElementByIdOrByName("testCodeId");
	var testDescriptionId = getElementByIdOrByName("testDescriptionId");

	testDescription.innerText= "";
	testDescription.textContent= "";
	testCodeId.value = null;
	testDescriptionId.value = null;

	var resultDescription = getElementByIdOrByName("resultDescription");
	var codeResultId = getElementByIdOrByName("codeResultId");
	var resultDescriptionId = getElementByIdOrByName("resultDescriptionId");
	resultDescription.innerText= "";
	resultDescription.textContent= "";
	codeResultId.value = null;
	resultDescriptionId.value = null;

	$j($j("#testDescription").parent().parent()).find(":input").val("");
	$j($j("#resultDescription").parent().parent()).find(":input").val("");
	$j($j("#numericResult_text").parent().parent()).find(":input").val("");
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").val("");
	$j($j("#numericResultTypeList").parent().parent()).find(":input").val("");
	$j($j("#textResult_text").parent().parent()).find(":input").val("");
}

function clearTestedResult()
{
	var testDescription = getElementByIdOrByName("testDescription");
	var testCodeId = getElementByIdOrByName("testCodeId");
	var testDescriptionId = getElementByIdOrByName("testDescriptionId");
	testDescription.innerText= "";
	testDescription.textContent= "";
	testCodeId.value = null;
	testDescriptionId.value = null;
	$j($j("#testDescription").parent().parent()).find(":input").val("");
}

function clearCodeResult()
{
	var resultDescription = getElementByIdOrByName("resultDescription");
	var resultCodeId = getElementByIdOrByName("codeResultId");
	var resultDescriptionId = getElementByIdOrByName("resultDescriptionId");
	resultDescription.innerText= "";
	resultDescription.textContent= "";
	resultCodeId.value = null;
	resultDescriptionId.value = null;
	$j($j("#resultDescription").parent().parent()).find(":input").val("");

	$j("#numericResult_text").parent().find(":input").attr("disabled", false);
	$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");
	$j("#numericResultL").parent().find("span[title]").css("color", "#000");

	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
	

	$j("#textResult_text").parent().find(":input").attr("disabled", false);
	$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
	$j("#textResultL").parent().find("span[title]").css("color", "#000");
	
	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
	$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	
	
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	
}

function clearAdvSelection(advQuestion)
{
	$j("#advVal_text").parent().find(":input").val("");
	$j("#advVal_textArea").parent().find(":input").val("");
	$j("#advVal_date").parent().find(":input").val("");
	$j($j("#advValueList2").parent().parent()).find(":input").val("");
	getElementByIdOrByName('advValueList2-selectedValues').innerHTML = "";
	$j($j("#advValueList1").parent().parent()).find(":input").val("");
	$j($j("#advLogicList").parent().parent()).find(":input").val("");
}

function clearAdvInvSelection(advQuestion)
{
	$j("#advInvVal_text").parent().find(":input").val("");
	$j("#advInvVal_textArea").parent().find(":input").val("");
	$j("#advInvVal_date").parent().find(":input").val("");
	$j($j("#advInvValueList2").parent().parent()).find(":input").val("");
	getElementByIdOrByName('advInvValueList2-selectedValues').innerHTML = "";
	$j($j("#advInvValueList1").parent().parent()).find(":input").val("");
	$j($j("#advInvLogicList").parent().parent()).find(":input").val("");
}

function updateAdvCriteriaBatchIdEntry( subSectNm,pattern,questionbody)
{
		var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	  	var t = getElementByIdOrByName("IdAdvancedSubSection");
	 	t.style.display = "";
		for (var i = 0; i < 4; i++){
		 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
		}

		var seqNum = 0;
		var questionType = "";
		var questionUid = getElementByIdOrByName("questionAdvList").options[getElementByIdOrByName("questionAdvList").selectedIndex].value;
		var textField = getElementByIdOrByName("advVal_text").value;
		var textAreaField = getElementByIdOrByName("advVal_textArea").value;
		var textDate = getElementByIdOrByName("advVal_date").value;
		var singleSelect = getElementByIdOrByName("advValueList1").value;
		var multiselect = getElementByIdOrByName("advValueList2").value;

		if(multiselect == null || multiselect == "")
		{
			var valueNode = getElementByIdOrByName('advValueList2');
			var myListCount = valueNode.options.length;
			for (i=0; i < myListCount; i++) {
				if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
					multiselect = "AS";
					break;
				}
			}
		}
		var numericLiteral = getElementByIdOrByName("advVal_lit").value;
		var numericCoded = getElementByIdOrByName("advVal_code").value;
		if(textField!=null && textField!=""){
			questionType = "Text";
		}
		else if(textField!=null && textField!=""){
				questionType = "Text";
		}
		else if(textAreaField!=null && textAreaField!=""){
				questionType = "TextArea";
		}
		else if(textDate!=null && textDate!=""){
				questionType = "Date";
		}
		else if(getElementByIdOrByName("advS_sel").style.display==""){
				questionType = "SINGLESELECT";
		}
		else if(multiselect!=null && multiselect!=""){
				questionType = "MULTISELECT";
		}
		else if(numericCoded!=null && numericCoded!=""){
				questionType = "NumericCoded";
		}
		else if(numericLiteral!=null && numericLiteral!=""){
				questionType = "NumericLiteral";
		}

		dwr.engine.beginBatch();
		var ansMap = getAdvancedBatchAnswerMapFromScreen(questionType);
		var batchentry = { subSectNm:subSectNm, id:gAdvBatchEntryUpdateSeq, answerMap:ansMap };

		JDecisionSupport.updateAdvancedCriteriaBatchAnswer(batchentry);
		rewriteAdvancedCriteriaBatchIdHeader(subSectNm, pattern, questionbody);
		clearAdvancedCriteriaBatchEntryFields(subSectNm);
		dwr.engine.endBatch();
		$j("#AddButtonToggleIdAdvancedSubSection").show();
		$j("#AddNewButtonToggleIdAdvancedSubSection").hide();
		$j("#UpdateButtonToggleIdAdvancedSubSection").hide();
}

function updateAdvInvCriteriaBatchIdEntry( subSectNm,pattern,questionbody)
{
		var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	  	var t = getElementByIdOrByName("IdAdvancedInvSubSection");
	 	t.style.display = "";
		for (var i = 0; i < 4; i++){
		 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
		}

		var seqNum = 0;
		var questionType = "";
		var questionUid = getElementByIdOrByName("questionAdvInvList").options[getElementByIdOrByName("questionAdvInvList").selectedIndex].value;
		var textField = getElementByIdOrByName("advInvVal_text").value;
		var textAreaField = getElementByIdOrByName("advInvVal_textArea").value;
		var textDate = getElementByIdOrByName("advInvVal_date").value;
		var singleSelect = getElementByIdOrByName("advInvValueList1").value;
		var multiselect = getElementByIdOrByName("advInvValueList2").value;

		if(multiselect == null || multiselect == "")
		{
			var valueNode = getElementByIdOrByName('advInvValueList2');
			var myListCount = valueNode.options.length;
			for (i=0; i < myListCount; i++) {
				if (valueNode.options[i].selected == true && valueNode.options[i].value != ""){
					multiselect = "AS";
					break;
				}
			}
		}
		var numericLiteral = getElementByIdOrByName("advInvVal_lit").value;
		var numericCoded = getElementByIdOrByName("advInvVal_code").value;
		if(textField!=null && textField!=""){
			questionType = "Text";
		}
		else if(textField!=null && textField!=""){
				questionType = "Text";
		}
		else if(textAreaField!=null && textAreaField!=""){
				questionType = "TextArea";
		}
		else if(textDate!=null && textDate!=""){
				questionType = "Date";
		}
		else if(getElementByIdOrByName("advInvS_sel").style.display==""){
				questionType = "SINGLESELECT";
		}
		else if(multiselect!=null && multiselect!=""){
				questionType = "MULTISELECT";
		}
		else if(numericCoded!=null && numericCoded!=""){
				questionType = "NumericCoded";
		}
		else if(numericLiteral!=null && numericLiteral!=""){
				questionType = "NumericLiteral";
		}
		dwr.engine.beginBatch();
		var ansMap = getAdvancedInvBatchAnswerMapFromScreen(questionType);
		var batchentry = { subSectNm:subSectNm, id:gAdvInvBatchEntryUpdateSeq, answerMap:ansMap };
		JDecisionSupport.updateAdvancedInvCriteriaBatchAnswer(batchentry);
		rewriteAdvancedInvCriteriaBatchIdHeader(subSectNm, pattern, questionbody);
		clearAdvancedInvCriteriaBatchEntryFields(subSectNm);
		dwr.engine.endBatch();
		$j("#AddButtonToggleIdAdvancedInvSubSection").show();
		$j("#AddNewButtonToggleIdAdvancedInvSubSection").hide();
		$j("#UpdateButtonToggleIdAdvancedInvSubSection").hide();
}

function updateElrAdvCriteriaBatchIdEntry( subSectNm,pattern,questionbody)
{
	changeElrSectionLightBGColor();

	 	var seqNum = 0;
		var questionType = "";

		dwr.engine.beginBatch();
		var ansMap = getElrAdvancedBatchAnswerMapFromScreen();
		var batchentry = { subSectNm:subSectNm, id:gElrBatchEntryUpdateSeq, answerMap:ansMap };
		JDecisionSupport.updateElrAdvancedCriteriaBatchAnswer(batchentry);
		rewriteElrAdvancedCriteriaBatchIdHeader(subSectNm, pattern, questionbody);
		clearElrAdvancedCriteriaBatchEntryFields(subSectNm);
		enableElrAdvancedCriteriaBatchEntryFields();
		dwr.engine.endBatch();

		$j("#AddButtonToggleIdELRAdvancedSubSection").show();
		$j("#AddNewButtonToggleIdELRAdvancedSubSection").hide();
		$j("#UpdateButtonToggleIdELRAdvancedSubSection").hide();
}

function checkElrAdvancedCriteriaBatchEntryFields()
{
	if(getElementByIdOrByName("resultDescription").innerText!= null && getElementByIdOrByName("resultDescription").innerText != "")
	{
		var testjack = getElementByIdOrByName("testCodeId");
		var testjackValue = getElementByIdOrByName("testCodeId").value;
		//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
	 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#FFF");
		//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);

		$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
		$j("#codeClearButton").parent().find(":input").attr("disabled", false);
		$j("#codeResultL").parent().find("span[title]").css("color", "#000");
		$j("#resultDescription").parent().find("span[title]").css("color", "#000");

		$j("#numericResult_text").parent().find(":input").attr("disabled", true);
		$j("#numericResult_text").parent().find(":input").css("background-color", "#666666");

	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#666666");
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
		

		$j("#textResult_text").parent().find(":input").attr("disabled", true);
		$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		
		$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
		
		
		$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
		$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", true);
		$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
		
	}else if(getElementByIdOrByName("numericResult_text").value!= null && getElementByIdOrByName("numericResult_text").value !="")
	{
		$j("#numericResult_text").parent().find(":input").attr("disabled", false);
		$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");

	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
		

		$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
	 	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
	 	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
	 	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	 	

	 	$j("#numericResultHigh_text").parent().find(":input").attr("disabled", false);
		$j("#numericResultHigh_text").parent().find(":input").css("background-color", "#FFF");
		
		//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
	 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#666666");
		//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("tabIndex", "-1");

		$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
		$j("#codeClearButton").parent().find(":input").attr("disabled", true);

		$j("#textResult_text").parent().find(":input").attr("disabled", true);
		$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		
		$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
		
		
	}else if(getElementByIdOrByName("textResult_text").value != null && getElementByIdOrByName("textResult_text").value != "")
	{
		$j("#textResult_text").parent().find(":input").attr("disabled", false);
		$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
		//disable value text box in the case of Not Null comparator
		if(getElementByIdOrByName("resultOperatorList").value != null && getElementByIdOrByName("resultOperatorList").value.indexOf("NOTNULL")==0){
			$j("#textResult_text").parent().find(":input").attr("disabled", true);
			$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		}
		//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
	 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#666666");
		//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", true);

		$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
		
		
		$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
		$j("#codeClearButton").parent().find(":input").attr("disabled", true);

		$j("#numericResult_text").parent().find(":input").attr("disabled", true);
		$j("#numericResult_text").parent().find(":input").css("background-color", "#666666");
		
		$j("#numericResultHigh_text").parent().find(":input").attr("disabled", true);
		$j("#numericResultHigh_text").parent().find(":input").css("background-color", "#666666");

	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#666666");
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", true);
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
		
		
		$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", true);
	 	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
	 	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", true);
	 	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
		
		
	} else if(getElementByIdOrByName("resultOperatorList").value != null && getElementByIdOrByName("resultOperatorList").value != "")
	{
		$j("#textResult_text").parent().find(":input").attr("disabled", true);
		$j("#textResult_text").parent().find(":input").css("background-color", "#666666");

		$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
		$j("#codeClearButton").parent().find(":input").attr("disabled", true);

		$j("#numericResult_text").parent().find(":input").attr("disabled", true);
		$j("#numericResult_text").parent().find(":input").css("background-color", "#666666");

		$j("#numericResultHigh_text").parent().find(":input").attr("disabled", true);
		$j("#numericResultHigh_text").parent().find(":input").css("background-color", "#666666");
		
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#666666");
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", true);
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
		
		
		$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", true);
	 	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
	 	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", true);
	 	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
		
	}else if(getElementByIdOrByName("numericResultOperatorList").value != null && getElementByIdOrByName("numericResultOperatorList").value != "")
	{
		$j("#numericResult_text").parent().find(":input").attr("disabled", false);
		$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");

		$j("#numericResultHigh_text").parent().find(":input").attr("disabled", false);
		$j("#numericResultHigh_text").parent().find(":input").css("background-color", "#FFF");
		
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
	 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
	 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
		

		$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
		$j("#codeClearButton").parent().find(":input").attr("disabled", true);

		$j("#textResult_text").parent().find(":input").attr("disabled", true);
		$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		
		$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
		$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
		
	}
	
}

function viewAdvCriteriaClicked( entryId,subSection,pattern,questionbody)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	 var t = getElementByIdOrByName("IdAdvancedSubSection");
	 t.style.display = "";
	 for (var i = 0; i < 4; i++){
	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
 	}

	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	var questionType ="";
//	if (gBatchEntryFieldsDisabled == true){
//		JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
//			for (var i = 0; i < answer.length; i++){
//				var ans = answer[i];
//				var id = ans.id;
//				if (id == beIdStr) {
//					questionType = ans.answerMap['AdvQuestionType'];
//					enableAdvancedCriteriaBatchEntryFields(questionType);
//				}
//			}
//		});
//
//	}
    //get all rows of data

	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
						questionType = ans.answerMap['AdvQuestionType'];
						gAdvBatchEntryUpdateSeq = id;
			        	var questionListNode = getElementByIdOrByName('questionAdvList');
			        	var questionListTxtNode = getElementByIdOrByName('questionAdvList_textbox');
			        	if (questionListNode != null) {
			        		questionListNode.value = ans.answerMap['AdvQuestionList'];
			        		questionListTxtNode.value = ans.answerMap['AdvQueListTxt'];
			        	}
			        	if(ans.answerMap['AdvQuestionType'] == 'Text')
			        	{
			        		getElementByIdOrByName("advVal_text").style.visibility="visible";
			        		getElementByIdOrByName("advText").style.visibility="visible";
			        		getElementByIdOrByName("advText").style.display = "";

			        		getElementByIdOrByName("advVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advDate").style.display = "none";
			        		getElementByIdOrByName("advM_sel").style.display = "none";
			        		getElementByIdOrByName("advS_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advNum_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('advVal_text');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvQuestionType'] == 'TextArea')
			        	{
			        		getElementByIdOrByName("advVal_textArea").style.visibility="visible";
			        		getElementByIdOrByName("advVal_textArea").style.display="";

			        		getElementByIdOrByName("advText").style.display = "none";
			        		getElementByIdOrByName("advDate").style.display = "none";
			        		getElementByIdOrByName("advM_sel").style.display = "none";
			        		getElementByIdOrByName("advS_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advNum_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('advVal_textArea');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvQuestionType'] == 'Date')
			        	{
			        		getElementByIdOrByName("advVal_date").style.visibility="visible";
			        		getElementByIdOrByName("advDate").style.visibility="visible";
			        		getElementByIdOrByName("advDate").style.display = "";
			        		getElementByIdOrByName("advVal_dateIcon").style.visibility="visible";

			        		getElementByIdOrByName("advText").style.display = "none";
			        		getElementByIdOrByName("advVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advM_sel").style.display = "none";
			        		getElementByIdOrByName("advS_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advNum_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('advVal_date');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvQuestionType'] == 'SINGLESELECT')
			        	{
			        		getElementByIdOrByName("advS_sel").style.visibility="visible";
			        		getElementByIdOrByName("advValueList1").style.visibility="visible";
			        		getElementByIdOrByName("advS_sel").style.display = "";

			        		getElementByIdOrByName("advText").style.display = "none";
			        		getElementByIdOrByName("advVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advDate").style.display = "none";
			        		getElementByIdOrByName("advM_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advNum_Literal").style.display = "none";

			        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("advValueList1");
							   	DWRUtil.addOptions("advValueList1", data, "key", "value" );
			        		});
			        		var valueNode = getElementByIdOrByName('advValueList1');
			        		var valueTxtNode = getElementByIdOrByName('advValueList1_textbox');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvValueList1'];
			        			valueTxtNode.value = ans.answerMap['AdvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvQuestionType'] == 'MULTISELECT')
			        	{
			        		getElementByIdOrByName("advM_sel").style.visibility="visible";
			        		getElementByIdOrByName("advM_sel").style.display = "";
			        		getElementByIdOrByName("advValueList2").style.visibility="visible";

			        		getElementByIdOrByName("advText").style.display = "none";
			        		getElementByIdOrByName("advVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advDate").style.display = "none";
			        		getElementByIdOrByName("advS_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advNum_Literal").style.display = "none";

			        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("advValueList2");
							   	DWRUtil.addOptions("advValueList2", data, "key", "value" );
			        		});
			        		var valueNode = getElementByIdOrByName('advValueList2');
			        		var valueTxtNode = getElementByIdOrByName('advValueList2-selectedValues');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvValueList2'];
			        			valueTxtNode.innerHTML ="<b> Selected Values: <\/b>"+ ans.answerMap['AdvValValueTxt'];
			        		}
			        	}else if(questionType != null && questionType=='NumericLiteral'){
			        		getElementByIdOrByName("advNum_Literal").style.visibility="visible";
			        		getElementByIdOrByName("advNum_Literal").style.display = "";
			        		getElementByIdOrByName("advVal_lit").style.visibility="visible";

			        		getElementByIdOrByName("advText").style.display = "none";
			        		getElementByIdOrByName("advVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advDate").style.display = "none";
			        		getElementByIdOrByName("advS_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advM_sel").style.display = "none";

			        		var valueNode = getElementByIdOrByName('advVal_lit');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvValValueTxt'];
			        		}
			        	}else if(questionType != null && questionType=='NumericCoded'){
			        		getElementByIdOrByName("advNum_Coded").style.visibility="visible";
			        		getElementByIdOrByName("advNum_Coded").style.display = "";
			        		getElementByIdOrByName("advVal_num").style.visibility="visible";
			        		getElementByIdOrByName("advVal_code").style.visibility="visible";

			        		getElementByIdOrByName("advText").style.display = "none";
			        		getElementByIdOrByName("advVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advDate").style.display = "none";
			        		getElementByIdOrByName("advS_sel").style.display = "none";
			        		getElementByIdOrByName("advNum_Literal").style.display = "none";
			        		getElementByIdOrByName("advM_sel").style.display = "none";

			        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("advVal_code");
							   	DWRUtil.addOptions("advVal_code", data, "key", "value" );
			        		});
			        		var valueNode1 = getElementByIdOrByName('advVal_num');
			        		var valueNode2 = getElementByIdOrByName('advVal_code');
			        		var valueTxtNode = getElementByIdOrByName('advVal_code_textbox');
			        		var valueNode = valueNode1.value+"^"+valueNode2.value;
			        		if (valueNode != null) {
			        			var valueCoded = ans.answerMap['AdvValueList3'];
			        			var mySplitResult = valueCoded.split("^");
			        			valueNode1.value = mySplitResult[0];
			        			valueNode2.value = mySplitResult[1];
			        			valueTxtNode.value = ans.answerMap['AdvValCodeTxt'];
			        		}
			        	}

			        	var logicNode = getElementByIdOrByName('advLogicList');
			        	var logicTxtNode = getElementByIdOrByName('advLogicList_textbox');
			        	if (logicNode != null) {
			        		logicNode.value = ans.answerMap['AdvLogicList'];
			        		logicTxtNode.value = ans.answerMap['AdvLogicTxt'];

			        		logicNode.value = ans.answerMap['AdvLogicList'];
			        		logicTxtNode.value = ans.answerMap['AdvLogicTxt'];
			        		if(questionType != null && (questionType == 'Text' || questionType == 'TextArea'|| questionType == 'SINGLESELECT'
			        			|| questionType == 'MULTISELECT' || questionType == 'NumericCoded' || questionType == 'NumericLiteral')){
			        			JDecisionSupport.getDWRAdvancedValueList("SEARCH_ALPHA", function(data){
			    					DWRUtil.removeAllOptions("advLogicList");
			    					DWRUtil.addOptions("advLogicList", data, "key", "value" );
			    				});
			        		}else if(questionType != null && questionType == 'Date'){
			        			JDecisionSupport.getDWRAdvancedValueList("SEARCH_NUM", function(data){
			    					DWRUtil.removeAllOptions("advLogicList");
			    					DWRUtil.addOptions("advLogicList", data, "key", "value" );
			    				});
			        		}
			        	}
			        	disableAdvancedCriteriaBatchEntryFields(questionType);
			        	break;
			        	}
			}

	}); //all rows of data

}  //viewClicked


function viewAdvInvCriteriaClicked( entryId,subSection,pattern,questionbody)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	 var t = getElementByIdOrByName("IdAdvancedInvSubSection");
	 t.style.display = "";
	 for (var i = 0; i < 4; i++){
	 	$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
	 	$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
 	}

	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	var questionType ="";
//	if (gBatchEntryFieldsDisabled == true){
//		JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
//			for (var i = 0; i < answer.length; i++){
//				var ans = answer[i];
//				var id = ans.id;
//				if (id == beIdStr) {
//					questionType = ans.answerMap['AdvQuestionType'];
//					enableAdvancedCriteriaBatchEntryFields(questionType);
//				}
//			}
//		});
//
//	}
    //get all rows of data

	JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
						questionType = ans.answerMap['AdvInvQuestionType'];
						gAdvBatchEntryUpdateSeq = id;
			        	var questionListNode = getElementByIdOrByName('questionAdvInvList');
			        	var questionListTxtNode = getElementByIdOrByName('questionAdvInvList_textbox');
			        	if (questionListNode != null) {
			        		questionListNode.value = ans.answerMap['AdvInvQuestionList'];
							//alert("question.value view "+questionListNode.value);
			        		questionListTxtNode.value = ans.answerMap['AdvInvQueListTxt'];
			        	}
			        	if(ans.answerMap['AdvInvQuestionType'] == 'Text')
			        	{
			        		getElementByIdOrByName("advInvVal_text").style.visibility="visible";
			        		getElementByIdOrByName("advInvText").style.visibility="visible";
			        		getElementByIdOrByName("advInvText").style.display = "";

			        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advInvDate").style.display = "none";
			        		getElementByIdOrByName("advInvM_sel").style.display = "none";
			        		getElementByIdOrByName("advInvS_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('advInvVal_text');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvInvQuestionType'] == 'TextArea')
			        	{
			        		getElementByIdOrByName("advInvVal_textArea").style.visibility="visible";
			        		getElementByIdOrByName("advInvVal_textArea").style.display="";

			        		getElementByIdOrByName("advInvText").style.display = "none";
			        		getElementByIdOrByName("advInvDate").style.display = "none";
			        		getElementByIdOrByName("advInvM_sel").style.display = "none";
			        		getElementByIdOrByName("advInvS_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('advInvVal_textArea');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvInvQuestionType'] == 'Date')
			        	{
			        		getElementByIdOrByName("advInvVal_date").style.visibility="visible";
			        		getElementByIdOrByName("advInvDate").style.visibility="visible";
			        		getElementByIdOrByName("advInvDate").style.display = "";
			        		getElementByIdOrByName("advInvVal_dateIcon").style.visibility="visible";

			        		getElementByIdOrByName("advInvText").style.display = "none";
			        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advInvM_sel").style.display = "none";
			        		getElementByIdOrByName("advInvS_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
			        		var valueNode = getElementByIdOrByName('advInvVal_date');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
			        		}
			        	}else if(ans.answerMap['AdvInvQuestionType'] == 'SINGLESELECT')
			        	{
			        		getElementByIdOrByName("advInvS_sel").style.visibility="visible";
			        		getElementByIdOrByName("advInvValueList1").style.visibility="visible";
			        		getElementByIdOrByName("advInvS_sel").style.display = "";

			        		getElementByIdOrByName("advInvText").style.display = "none";
			        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advInvDate").style.display = "none";
			        		getElementByIdOrByName("advInvM_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";

			        		JDecisionSupport.getAdvInvValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("advInvValueList1");
							   	DWRUtil.addOptions("advInvValueList1", data, "key", "value" );
								var valueNode = getElementByIdOrByName('advInvValueList1');
								var valueTxtNode = getElementByIdOrByName('advInvValueList1_textbox');
								if (valueNode != null) {
									valueNode.value = ans.answerMap['advInvValueList1'];
									valueTxtNode.value = ans.answerMap['AdvInvValValueTxt'];
								}
			        		});
			        		
			        	}else if(ans.answerMap['AdvInvQuestionType'] == 'MULTISELECT')
			        	{
			        		getElementByIdOrByName("advInvM_sel").style.visibility="visible";
			        		getElementByIdOrByName("advInvM_sel").style.display = "";
			        		getElementByIdOrByName("advInvValueList2").style.visibility="visible";

			        		getElementByIdOrByName("advInvText").style.display = "none";
			        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advInvDate").style.display = "none";
			        		getElementByIdOrByName("advInvS_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";

			        		JDecisionSupport.getAdvInvValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("advInvValueList2");
							   	DWRUtil.addOptions("advInvValueList2", data, "key", "value" );
			        		});
			        		var valueNode = getElementByIdOrByName('advInvValueList2');
			        		var valueTxtNode = getElementByIdOrByName('advInvValueList2-selectedValues');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['advInvValueList2'];
			        			valueTxtNode.innerHTML ="<b> Selected Values: <\/b>"+ ans.answerMap['AdvInvValValueTxt'];
			        		}
			        	}else if(questionType != null && questionType=='NumericLiteral'){
			        		getElementByIdOrByName("advNum_Literal").style.visibility="visible";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "";
			        		getElementByIdOrByName("advInvVal_lit").style.visibility="visible";

			        		getElementByIdOrByName("advInvText").style.display = "none";
			        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advInvDate").style.display = "none";
			        		getElementByIdOrByName("advInvS_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
			        		getElementByIdOrByName("advInvM_sel").style.display = "none";

			        		var valueNode = getElementByIdOrByName('advInvVal_lit');
			        		if (valueNode != null) {
			        			valueNode.value = ans.answerMap['advInvValValueTxt'];
			        		}
			        	}else if(questionType != null && questionType=='NumericCoded'){
			        		getElementByIdOrByName("advInvNum_Coded").style.visibility="visible";
			        		getElementByIdOrByName("advInvNum_Coded").style.display = "";
			        		getElementByIdOrByName("advInvVal_num").style.visibility="visible";
			        		getElementByIdOrByName("advInvVal_code").style.visibility="visible";

			        		getElementByIdOrByName("advInvText").style.display = "none";
			        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
			        		getElementByIdOrByName("advInvDate").style.display = "none";
			        		getElementByIdOrByName("advInvS_sel").style.display = "none";
			        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
			        		getElementByIdOrByName("advInvM_sel").style.display = "none";

			        		JDecisionSupport.getAdvInvValueSetDropDown(questionListNode.value,function(data) {
			        			DWRUtil.removeAllOptions("advInvVal_code");
							   	DWRUtil.addOptions("advInvVal_code", data, "key", "value" );
			        		});
			        		var valueNode1 = getElementByIdOrByName('advInvVal_num');
			        		var valueNode2 = getElementByIdOrByName('advInvVal_code');
			        		var valueTxtNode = getElementByIdOrByName('advInvVal_code_textbox');
			        		var valueNode = valueNode1.value+"^"+valueNode2.value;
			        		if (valueNode != null) {
			        			var valueCoded = ans.answerMap['advValueList3'];
			        			var mySplitResult = valueCoded.split("^");
			        			valueNode1.value = mySplitResult[0];
			        			valueNode2.value = mySplitResult[1];
			        			valueTxtNode.value = ans.answerMap['AdvInvValCodeTxt'];
			        		}
			        	}
						
						

			        	var logicNode = getElementByIdOrByName('advInvLogicList');
			        	var logicTxtNode = getElementByIdOrByName('advInvLogicList_textbox');
			        	if (logicNode != null) {
			        		logicNode.value = ans.answerMap['AdvInvLogicList'];
			        		logicTxtNode.value = ans.answerMap['AdvInvLogicTxt'];

			        		logicNode.value = ans.answerMap['AdvInvLogicList'];
			        		logicTxtNode.value = ans.answerMap['AdvInvLogicTxt'];
			        		if(questionType != null && (questionType == 'Text' || questionType == 'TextArea'|| questionType == 'SINGLESELECT'
			        			|| questionType == 'MULTISELECT' || questionType == 'NumericCoded' || questionType == 'NumericLiteral')){
			        			JDecisionSupport.getDWRAdvancedInvValueList("SEARCH_ALPHA", function(data){
			    					DWRUtil.removeAllOptions("advInvLogicList");
			    					DWRUtil.addOptions("advInvLogicList", data, "key", "value" );
			    				});
			        		}else if(questionType != null && questionType == 'Date'){
			        			JDecisionSupport.getDWRAdvancedInvValueList("SEARCH_NUM", function(data){
			    					DWRUtil.removeAllOptions("advInvLogicList");
			    					DWRUtil.addOptions("advInvLogicList", data, "key", "value" );
			    				});
			        		}
			        	}
			        	disableAdvancedInvCriteriaBatchEntryFields(questionType);
			        	break;
			        	}
			}

	}); //all rows of data

}  //viewClicked


function viewElrAdvCriteriaClicked( entryId,subSection,pattern,questionbody)
{
	changeElrSectionLightBGColor()

	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#FFF");
	$j("#codeResultId").parent().find(":input").css("background-color", "#FFF");
	//$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");
	$j("#numericResult_text").parent().find(":input").css("background-color", "");
	//$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background", "#FFF");
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background", "");
	//$j($j("#resultOperatorList").parent().parent()).find(":input").css("background", "#FFF");
	$j($j("#resultOperatorList").parent().parent()).find(":input").css("background", "");
	//$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "");
	//$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
	$j("#textResult_text").parent().find(":input").css("background-color", "");
	
//	if (gElrBatchEntryFieldsDisabled == true){
//		enableElrAdvancedCriteriaBatchEntryFields();
//	}
    //get all rows of data

	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				gElrBatchEntryUpdateSeq = id;
	        	var testCodeIdNode = getElementByIdOrByName('testCodeId');
	        	var testDescriptionIdNode = getElementByIdOrByName('testDescriptionId');
	        	var testDescriptionNode = getElementByIdOrByName('testDescription');
	        	if (testCodeIdNode != null && ans.answerMap['ResultedTestCode'] != null) {
	        		testCodeIdNode.value = ans.answerMap['ResultedTestCode'];
	        		testDescriptionIdNode.value = ans.answerMap['ResultedTestName'];
	        		testDescriptionNode.innerText = ans.answerMap['ResultedTestName'];
	        		testDescriptionNode.textContent = ans.answerMap['ResultedTestName'];
	        	}
	        	//var codedResultAdvListNode = getElementByIdOrByName('codedResultAdvList');
	        	//var codedResultAdvList_textboxNode = getElementByIdOrByName('codedResultAdvList_textbox');

	        	var numericResult_textNode = getElementByIdOrByName('numericResult_text');
	        	var numericResultTypeListNode = getElementByIdOrByName('numericResultTypeList');
	        	var numericResultTypeList_textboxNode = getElementByIdOrByName('numericResultTypeList_textbox');

	        	var textResult_textNode = getElementByIdOrByName('textResult_text');
				var resultOperatorListNode = getElementByIdOrByName('resultOperatorList');
				var resultOperatorListNode_textNode = getElementByIdOrByName('resultOperatorList_textbox');
			
				var numericResultOperatorListNode = getElementByIdOrByName('numericResultOperatorList');
				var numericResultOperatorListNode_textNode = getElementByIdOrByName('numericResultOperatorList_textbox');
				
	        	var codeResultIdNode = getElementByIdOrByName('codeResultId');
	        	var resultDescriptionIdNode = getElementByIdOrByName('resultDescriptionId');
	        	var resultDescriptionNode = getElementByIdOrByName('resultDescription');
	        	
	        	//Hiding numericResultHigh_div
	        	getElementByIdOrByName("numericResultHigh_div").style.visibility="hidden";
				getElementByIdOrByName("numericResultHigh_div").style.display = "none";
				
	        	if (codeResultIdNode != null && ans.answerMap['CodedResultAdvList'] != null
	        			&& codeResultIdNode != "null") {
	        		codeResultIdNode.value = ans.answerMap['CodedResultAdvList'];
	        		resultDescriptionIdNode.value = ans.answerMap['CodedResultAdvListTxt'];
	        		resultDescriptionNode.innerText = ans.answerMap['CodedResultAdvListTxt'];
	        		resultDescriptionNode.textContent = ans.answerMap['CodedResultAdvListTxt'];
	        		
	        		numericResult_textNode.value = "";
	        		numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		textResult_textNode.value = "";
	        		resultOperatorListNode.value = "";
	        		resultOperatorListNode_textNode.value = "";
	        		
	        		numericResultOperatorListNode.value = "";
	        		numericResultOperatorListNode_textNode.value = "";
	        	}

	        	//if (codedResultAdvListNode != null && ans.answerMap['CodedResultAdvList'] != null) {
	        	//	codedResultAdvListNode.value = ans.answerMap['CodedResultAdvList'];
	        	//	codedResultAdvList_textboxNode.value = ans.answerMap['CodedResultAdvListTxt'];

	        	//	numericResult_textNode.value = "";
	        	//	numericResultTypeListNode.value = "";
	        	//	numericResultTypeList_textboxNode.value = "";
	        	//	textResult_textNode.value = "";
	        	//}
	        	
	        	if (numericResultOperatorListNode != null && ans.answerMap['NumericResultNameOperator'] != null) {
	        		numericResultOperatorListNode.value = ans.answerMap['NumericResultNameOperator'];
	        		numericResultOperatorListNode_textNode.value = ans.answerMap['ResultNameOperatorTxt'];
					codeResultIdNode.value = "";
					resultDescriptionIdNode.value = "";
					resultDescriptionNode.innerText = "";
					numericResult_textNode.value = "";
					numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		resultOperatorListNode.value = "";
	        		resultOperatorListNode_textNode.value = "";
	        		
	        		if(ans.answerMap['NumericResultTxt'] == null){
	        			numericResultTypeList_textboxNode.value = "";
					}
	        	}
	        	
	        	if (numericResult_textNode != null && ans.answerMap['NumericResultTxt'] != null) {
	        		numericResult_textNode.value = ans.answerMap['NumericResultTxt'];

	        		numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		//codedResultAdvListNode.value = "";
	        		//codedResultAdvList_textboxNode.value = "";
	        		codeResultIdNode.value = "";
	        		resultDescriptionIdNode.value = "";
	        		resultDescriptionNode.innerText = "";
	        		textResult_textNode.value = "";
	        		resultOperatorListNode.value = "";
	        		resultOperatorListNode_textNode.value = "";
	        		
	        		if(ans.answerMap['NumericResultNameOperator']=='BET'){
	        			var numericResultHigh_textNode = getElementByIdOrByName('numericResultHigh_text');
	        			
	        			getElementByIdOrByName("numericResultHigh_div").style.visibility="visible";
						getElementByIdOrByName("numericResultHigh_div").style.display = "";
						
						if(ans.answerMap['numericResultHighTxt']==null || ans.answerMap['numericResultHighTxt']==''){
							var toSplit = ans.answerMap['NumericResultTxt'];
							var mySplitResult = toSplit.split("-");
							numericResult_textNode.value = mySplitResult[0];
							numericResultHigh_textNode.value = mySplitResult[1];
						}else{
							numericResult_textNode.value = ans.answerMap['NumericResultTxt'];
							numericResultHigh_textNode.value = ans.answerMap['numericResultHighTxt'];
						}
	        		}
	        	}
	        	if(numericResultTypeListNode != null && ans.answerMap['NumericResultTypeList'] != null){
	        		numericResultTypeListNode.value = ans.answerMap['NumericResultTypeList'];
	        		numericResultTypeList_textboxNode.value = ans.answerMap['NumericResultTypeListTxt'];
	        	}
	        	if (textResult_textNode != null && ans.answerMap['TextResultTxt'] != null) {
	        		textResult_textNode.value = ans.answerMap['TextResultTxt'];
					//resultOperatorListNode.value = ans.answerMap['ResultNameOperator'];
					//resultOperatorListNode_textNode.value = ans.answerMap['ResultNameOperatorTxt'];
	        		//codedResultAdvListNode.value = "";
	        		//codedResultAdvList_textboxNode.value = "";
	        		codeResultIdNode.value = "";
	        		resultDescriptionIdNode.value = "";
	        		resultDescriptionNode.innerText = "";
	        		numericResult_textNode.value = "";
	        		numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		numericResultOperatorListNode.value = "";
	        		numericResultOperatorListNode_textNode.value = "";
	       
	       
	        	}
	        	if (resultOperatorListNode != null && ans.answerMap['ResultNameOperator'] != null) {
	        		resultOperatorListNode.value = ans.answerMap['ResultNameOperator'];
					resultOperatorListNode_textNode.value = ans.answerMap['ResultNameOperatorTxt'];
					codeResultIdNode.value = "";
					resultDescriptionIdNode.value = "";
					resultDescriptionNode.innerText = "";
					numericResult_textNode.value = "";
					numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		numericResultOperatorListNode.value = "";
	        		numericResultOperatorListNode_textNode.value = "";
	        		
	        		if(ans.answerMap['TextResultTxt'] == null){
						textResult_textNode.value = "";
					}
	        	}
	        	disableElrAdvancedCriteriaBatchEntryFields();
		        break;
			}
		}

	}); // all rows of data

}  //viewClicked

function disableElrAdvancedCriteriaBatchEntryFields ()
{
	gElrBatchEntryFieldsDisabled = true;

	$j("#testSearchButton").parent().find(":input").attr("disabled", true);
 	$j("#resultedTestL").parent().find("span[title]").css("color", "#666666");
 	$j("#testDescription").parent().find("span[title]").css("color", "#666666");
 	$j("#testResultNote").parent().find("span[title]").css("color", "#666666");

 	$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
 	$j("#codeClearButton").parent().find(":input").attr("disabled", true);
 	$j("#codeResultL").parent().find("span[title]").css("color", "#666666");
 	$j("#resultDescription").parent().find("span[title]").css("color", "#666666");

	//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
	//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", true);
	//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#666666");

	$j("#numericResult_text").parent().find(":input").attr("disabled", true);
 	$j("#numericResultL").parent().find("span[title]").css("color", "#666666");

 	$j("#numericResultHigh_text").parent().find(":input").attr("disabled", true);
 	$j("#numericResultHighL").parent().find("span[title]").css("color", "#666666");
 	
 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", true);
 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
	

	$j("#textResult_text").parent().find(":input").attr("disabled", true);
 	$j("#textResultL").parent().find("span[title]").css("color", "#666666");

 	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
 	$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
 	$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
	
	
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
	
	
	$j("#AddButtonToggleIdELRAdvancedSubSection").hide();
	$j("#AddNewButtonToggleIdELRAdvancedSubSection").show();
	$j("#UpdateButtonToggleIdELRAdvancedSubSection").hide();
}

function changeElrResultSelection(componentId)
{
	/*if("codedResultAdvList" == componentId)
	{
		if(getElementByIdOrByName("codedResultAdvList").value== null || getElementByIdOrByName("codedResultAdvList").value =="")
		{
			$j("#numericResult_text").parent().find(":input").attr("disabled", false);
			$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#numericResultL").parent().find("span[title]").css("color", "#000");

			$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);

			$j("#textResult_text").parent().find(":input").attr("disabled", false);
			$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#textResultL").parent().find("span[title]").css("color", "#000");
		}else
		{
			$j("#numericResult_text").parent().find(":input").attr("disabled", true);
			$j("#numericResult_text").parent().find(":input").css("background-color", "#666666");
		 	//$j("#numericResultL").parent().find("span[title]").css("color", "#666666");

		 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
		 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#666666");
			$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", true);

			$j("#textResult_text").parent().find(":input").attr("disabled", true);
			$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		 	//$j("#textResultL").parent().find("span[title]").css("color", "#666666");
		}
	}else */if("numericResult_text" == componentId)
	{
		if((getElementByIdOrByName("numericResult_text").value== null || getElementByIdOrByName("numericResult_text").value =="")
			&& (getElementByIdOrByName("numericResultTypeList").value== null || getElementByIdOrByName("numericResultTypeList").value ==""))
		{
			$j("#codeResultL").parent().find("span[title]").css("color", "#000");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
			$j("#codeClearButton").parent().find(":input").attr("disabled", false);

			//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
			//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#FFF");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);

			$j("#textResult_text").parent().find(":input").attr("disabled", false);
			$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#textResultL").parent().find("span[title]").css("color", "#000");
			
			$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
		}else
		{
		 	//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#666666");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
			$j("#codeClearButton").parent().find(":input").attr("disabled", true);

		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#666666");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", true);

			$j("#textResult_text").parent().find(":input").attr("disabled", true);
			$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		 	//$j("#textResultL").parent().find("span[title]").css("color", "#666666");
		 	
		 	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
			
			
		}
	}else if("numericResultTypeList" == componentId)
	{
		if((getElementByIdOrByName("numericResultTypeList").value== null || getElementByIdOrByName("numericResultTypeList").value =="")
				&& (getElementByIdOrByName("numericResult_text").value== null || getElementByIdOrByName("numericResult_text").value ==""))
		{
			$j("#codeResultL").parent().find("span[title]").css("color", "#000");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
			$j("#codeClearButton").parent().find(":input").attr("disabled", false);

			//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#000");

			//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
			//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#FFF");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);

			$j("#textResult_text").parent().find(":input").attr("disabled", false);
			$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#textResultL").parent().find("span[title]").css("color", "#000");
			
			$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
		}else
		{
		 	//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#666666");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
			$j("#codeClearButton").parent().find(":input").attr("disabled", true);
		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#666666");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", true);

			$j("#textResult_text").parent().find(":input").attr("disabled", true);
			$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		 	//$j("#textResultL").parent().find("span[title]").css("color", "#666666");
		 	
		 	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
			
			
		}
	} else if("numericResultOperatorList" == componentId)
	{
		if((getElementByIdOrByName("numericResultOperatorList").value== null || getElementByIdOrByName("numericResultOperatorList").value =="")
				&& (getElementByIdOrByName("numericResult_text").value== null || getElementByIdOrByName("numericResult_text").value ==""))
		{
			$j("#codeResultL").parent().find("span[title]").css("color", "#000");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
			$j("#codeClearButton").parent().find(":input").attr("disabled", false);

			$j("#textResult_text").parent().find(":input").attr("disabled", false);
			$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#textResultL").parent().find("span[title]").css("color", "#000");
			
			$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
			
			getElementByIdOrByName("numericResultHigh_div").style.visibility="hidden";
			getElementByIdOrByName("numericResultHigh_div").style.display = "none";
			getElementByIdOrByName("numericResultHigh_text").value = "";
		}else
		{
			$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
			$j("#codeClearButton").parent().find(":input").attr("disabled", true);

			$j("#textResult_text").parent().find(":input").attr("disabled", true);
			$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
		 	
		 	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#resultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", true);
			$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
			
			
			if(getElementByIdOrByName("numericResultOperatorList").value == 'BET'){
				getElementByIdOrByName("numericResultHigh_div").style.visibility="visible";
				getElementByIdOrByName("numericResultHigh_div").style.display = "";
				$j("#numericResultHigh_text").parent().find(":input").attr("disabled", false);
				$j("#numericResultHigh_text").parent().find(":input").css("background", "#FFF");
			}else{
				getElementByIdOrByName("numericResultHigh_div").style.visibility="hidden";
				getElementByIdOrByName("numericResultHigh_div").style.display = "none";
				getElementByIdOrByName("numericResultHigh_text").value = "";
			}
		}
	}else if("textResult_text" == componentId)
	{
		if((getElementByIdOrByName("resultOperatorList").value== null || getElementByIdOrByName("resultOperatorList").value =="") &&
			(getElementByIdOrByName("textResult_text").value== null || getElementByIdOrByName("textResult_text").value ==""))
		{
			$j("#codeResultL").parent().find("span[title]").css("color", "#000");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
			$j("#codeClearButton").parent().find(":input").attr("disabled", false);
			//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#000");

			//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
			//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#FFF");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);

			$j("#numericResult_text").parent().find(":input").attr("disabled", false);
			$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#numericResultL").parent().find("span[title]").css("color", "#000");

			$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
		}else
		{
			$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
			$j("#codeClearButton").parent().find(":input").attr("disabled", true);
		 	//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#666666");

		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#666666");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", true);

			$j("#numericResult_text").parent().find(":input").attr("disabled", true);
			$j("#numericResult_text").parent().find(":input").css("background-color", "#666666");
		 	//$j("#numericResultL").parent().find("span[title]").css("color", "#666666");

		 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
		 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#666666");
		 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", true);
		 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", true);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
			
		}
	} else if("resultOperatorList" == componentId)
	{
		if((getElementByIdOrByName("resultOperatorList").value== null || getElementByIdOrByName("resultOperatorList").value =="") &&
				(getElementByIdOrByName("textResult_text").value== null || getElementByIdOrByName("textResult_text").value ==""))
		{
			$j("#codeResultL").parent().find("span[title]").css("color", "#000");
			$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
			$j("#codeClearButton").parent().find(":input").attr("disabled", false);
			//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#000");

			//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
			//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#FFF");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);

			$j("#numericResult_text").parent().find(":input").attr("disabled", false);
			$j("#numericResult_text").parent().find(":input").css("background-color", "#FFF");
			$j("#numericResultL").parent().find("span[title]").css("color", "#000");

			$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#FFF");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
			
		}else
		{
			$j("#codeSearchButton").parent().find(":input").attr("disabled", true);
			$j("#codeClearButton").parent().find(":input").attr("disabled", true);
		 	//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#666666");

		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", true);
		 	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background-color", "#666666");
			//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", true);

			$j("#numericResult_text").parent().find(":input").attr("disabled", true);
			$j("#numericResult_text").parent().find(":input").css("background-color", "#666666");
		 	//$j("#numericResultL").parent().find("span[title]").css("color", "#666666");

		 	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", true);
		 	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background-color", "#666666");
		 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", true);
		 	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "-1");
			
			
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", true);
			$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background-color", "#666666");
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", true);
			$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "-1");
			
		}
		
		if(getElementByIdOrByName("resultOperatorList").value != null && getElementByIdOrByName("resultOperatorList").value.indexOf("NOTNULL")==0){
			$j("#textResult_text").parent().find(":input").attr("disabled", true);
			$j("#textResult_text").parent().find(":input").css("background-color", "#666666");
			getElementByIdOrByName("textResult_text").value = "(any value)";
		}else{
			$j("#textResult_text").parent().find(":input").attr("disabled", false);
			$j("#textResult_text").parent().find(":input").css("background-color", "#FFF");
		}
	}
}
function enableDisableTimeLogic(fieldElement){
	var action = getElementByIdOrByName("ActionList");
	if(fieldElement!=null && fieldElement!='undefined' && fieldElement.value =='Y'){
	pgEnableElement('TimeFrameOpSel');
	pgEnableElement('TimeFrameDays');
	$j("#EventDateTypeSel").parent().parent().find(":input").val("SCD");
	autocompTxtValuesForJSPByElement("EventDateTypeSel");
	getElementByIdOrByName("useEventDateLogic").value='Y';
	}else{
	pgDisableElement('TimeFrameOpSel');
	pgDisableElement('TimeFrameDays');
	$j("#EventDateTypeSel").parent().parent().find(":input").val("");
	getElementByIdOrByName("useEventDateLogic").value='N';
	}
}


function enableDisableInvLogic(fieldElement){
	var eventType = getElementByIdOrByName("EVENT_TY").value;
	var action = getElementByIdOrByName("ActionList");
	if(fieldElement!=null && fieldElement!='undefined' && fieldElement.value =='Y' && eventType!=null && eventType=='11648804'){
		getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "";
		getElementByIdOrByName("timeFrameSubSection").style.display = "";
		getElementByIdOrByName("PublishedConditionLabel").style.display = "";
	    getElementByIdOrByName("PublishedConditionField").style.display = "";
	    getElementByIdOrByName("useInvLogic").value='Y';
	}else{
		getElementByIdOrByName("useInvLogicRadioNo").checked='true';
		pgDisableElement('TimeFrameOpSel');
		pgDisableElement('TimeFrameDays');
		clearSelectedAdvancedInvQuestions('IdAdvancedInvSubSection', 'patternIdAdvancedInvSubSection','questionbodyIdAdvancedInvSubSection');
		getElementByIdOrByName("useEventDateLogicRadioNo").checked='true';
		$j("#EventDateTypeSel").parent().parent().find(":input").val("");
		if (action != null && action.value == "3") {
			$j("#PublishedCondition").val("");
			getElementByIdOrByName("PublishedCondition_textbox").value = '';
			getElementByIdOrByName("PublishedConditionLabel").style.display = "none";
			getElementByIdOrByName("PublishedConditionField").style.display = "none";
		}
		getElementByIdOrByName("IdAdvancedInvSubSection").style.display = "none";
		getElementByIdOrByName("timeFrameSubSection").style.display = "none";
		getElementByIdOrByName("useInvLogic").value='N';
	}

}


function currentSelectDateLogic(dateElement){
	if(dateElement.value =='CurrentDate'){
		getElementByIdOrByName("Val_date").disabled=true;
		getElementByIdOrByName("Val_dateIcon").disabled=true;
		getElementByIdOrByName("Val_date").value='';
	}else{
		getElementByIdOrByName("Val_date").disabled=false;
		getElementByIdOrByName("Val_dateIcon").disabled=false;
	}
}


function disableAdvancedCriteriaBatchEntryFields (questionType)
{
	gAdvBatchEntryFieldsDisabled = true;
	$j($j("#questionAdvList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#questionAdvList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#questionAdvList").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#questionAdvL").parent().find("span[title]").css("color", "#666666");

	if(questionType == 'Text')
	{
		$j("#advVal_text").parent().find(":input").attr("disabled", true);
     	$j("#advValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType == 'TextArea'){

		$j("#advVal_textArea").parent().find(":input").attr("disabled", true);
     	$j("#advValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType == 'Date'){

		$j("#advVal_date").parent().find(":input").attr("disabled", true);
		$j("#advVal_dateIcon").parent().find("img").attr("disabled", true);
		$j("#advVal_dateIcon").parent().find("img").attr("tabIndex", "-1");
		
     	$j("#advValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType.indexOf("MULTISELECT")>=0){

		$j($j("#advValueList2").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#advValueList2").parent().parent()).find("img").attr("disabled", true);
		$j($j("#advValueList2").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#advValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType.indexOf("SINGLESELECT")>=0){
		$j($j("#advValueList1").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#advValueList1").parent().parent()).find("img").attr("disabled", true);
		$j($j("#advValueList1").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#advValueL").parent().find("span[title]").css("color", "#666666");
	}else if(questionType != null && questionType=='NumericLiteral'){
		$j("#advVal_lit").parent().find(":input").attr("disabled", true);
		$j("#advValueL").parent().find("span[title]").css("color", "#666666");
	}else if(questionType != null && questionType=='NumericCoded'){
		$j("#advVal_num").parent().find(":input").attr("disabled", true);
		$j($j("#advVal_code").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#advVal_code").parent().parent()).find("img").attr("disabled", true);
		$j($j("#advVal_code").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#advValueL").parent().find("span[title]").css("color", "#666666");
	}

	$j($j("#advLogicList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advLogicList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advLogicList").parent().parent()).find("img").attr("tabIndex", "-1");
  	
  	$j("#advLogicL").parent().find("span[title]").css("color", "#666666");

	$j("#AddButtonToggleIdAdvancedSubSection").hide();
	$j("#AddNewButtonToggleIdAdvancedSubSection").show();
	$j("#UpdateButtonToggleIdAdvancedSubSection").hide();
}

function enableAdvancedCriteriaBatchEntryFields (questionType)
{
  	gAdvBatchEntryFieldsDisabled = false;
  	$j($j("#questionAdvList").parent().parent()).find(":input").attr("disabled", false);
  	$j($j("#questionAdvList").parent().parent()).find("img").attr("disabled", false);
  	$j($j("#questionAdvList").parent().parent()).find("img").attr("tabIndex", "0");
  	
  	
  	$j("#questionAdvL").parent().find("span[title]").css("color", "#000");

  	if(questionType == 'Text')
	{
		$j("#advVal_text").parent().find(":input").attr("disabled", false);
     	$j("#advValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType == 'TextArea'){

		$j("#advVal_textArea").parent().find(":input").attr("disabled", false);
     	$j("#advValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType == 'Date'){

		$j("#advVal_date").parent().find(":input").attr("disabled", false);
		$j("#advVal_dateIcon").parent().find("img").attr("disabled", false);
		$j("#advVal_dateIcon").parent().find("img").attr("tabIndex", "0");
		
     	$j("#advValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType.indexOf("MULTISELECT")>=0){

		$j($j("#advValueList2").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#advValueList2").parent().parent()).find("img").attr("disabled", false);
		$j($j("#advValueList2").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#advValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType.indexOf("SINGLESELECT")>=0){
		$j($j("#advValueList1").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#advValueList1").parent().parent()).find("img").attr("disabled", false);
		$j($j("#advValueList1").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#advValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType != null && questionType=='NumericLiteral'){
		$j("#advVal_lit").parent().find(":input").attr("disabled", false);
		$j("#advValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType != null && questionType=='NumericCoded'){
		$j("#advVal_num").parent().find(":input").attr("disabled", false);
		$j($j("#advVal_code").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#advVal_code").parent().parent()).find("img").attr("disabled", false);
		$j($j("#advVal_code").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#advValueL").parent().find("span[title]").css("color", "#000");
	}


  	$j($j("#advLogicList").parent().parent()).find(":input").attr("disabled", false);
  	$j($j("#advLogicList").parent().parent()).find("img").attr("disabled", false);
  	$j($j("#advLogicList").parent().parent()).find("img").attr("tabIndex", "0");
  	
  	$j("#advLogicL").parent().find("span[title]").css("color", "#000");


 	$j("#AddButtonToggleIdAdvancedSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedSubSection").hide();
	clearAdvancedCriteriaBatchEntryFields ('IdAdvancedSubSection');
}

function disableAdvancedInvCriteriaBatchEntryFields (questionType)
{
	gAdvInvBatchEntryFieldsDisabled = true;
	$j($j("#questionAdvInvList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#questionAdvInvList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#questionAdvInvList").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#questionAdvInvL").parent().find("span[title]").css("color", "#666666");

	if(questionType == 'Text')
	{
		$j("#advInvVal_text").parent().find(":input").attr("disabled", true);
     	$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType == 'TextArea'){

		$j("#advInvVal_textArea").parent().find(":input").attr("disabled", true);
     	$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType == 'Date'){

		$j("#advInvVal_date").parent().find(":input").attr("disabled", true);
		$j("#advInvVal_dateIcon").parent().find("img").attr("disabled", true);
		$j("#advInvVal_dateIcon").parent().find("img").attr("tabIndex", "-1");
		
     	$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType.indexOf("MULTISELECT")>=0){

		$j($j("#advInvValueList2").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#advInvValueList2").parent().parent()).find("img").attr("disabled", true);
		$j($j("#advInvValueList2").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");

	}else if(questionType.indexOf("SINGLESELECT")>=0){
		$j($j("#advInvValueList1").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#advInvValueList1").parent().parent()).find("img").attr("disabled", true);
		$j($j("#advInvValueList1").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");
	}else if(questionType != null && questionType=='NumericLiteral'){
		$j("#advInvVal_lit").parent().find(":input").attr("disabled", true);
		$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");
	}else if(questionType != null && questionType=='NumericCoded'){
		$j("#advInvVal_num").parent().find(":input").attr("disabled", true);
		$j($j("#advInvVal_code").parent().parent()).find(":input").attr("disabled", true);
		$j($j("#advInvVal_code").parent().parent()).find("img").attr("disabled", true);
		$j($j("#advInvVal_code").parent().parent()).find("img").attr("tabIndex", "-1");
		
		$j("#advInvValueL").parent().find("span[title]").css("color", "#666666");
	}

	$j($j("#advInvLogicList").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advInvLogicList").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advInvLogicList").parent().parent()).find("img").attr("tabIndex", "-1");
  	
  	$j("#advInvLogicL").parent().find("span[title]").css("color", "#666666");

	$j("#AddButtonToggleIdAdvancedInvSubSection").hide();
	$j("#AddNewButtonToggleIdAdvancedInvSubSection").show();
	$j("#UpdateButtonToggleIdAdvancedInvSubSection").hide();
}

function enableAdvancedInvCriteriaBatchEntryFields (questionType)
{
  	gAdvInvBatchEntryFieldsDisabled = false;
  	$j($j("#questionAdvInvList").parent().parent()).find(":input").attr("disabled", false);
  	$j($j("#questionAdvInvList").parent().parent()).find("img").attr("disabled", false);
  	$j($j("#questionAdvInvList").parent().parent()).find("img").attr("tabIndex", "0");
  	
  	
  	$j("#questionAdvInvL").parent().find("span[title]").css("color", "#000");

  	if(questionType == 'Text')
	{
		$j("#advInvVal_text").parent().find(":input").attr("disabled", false);
     	$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType == 'TextArea'){

		$j("#advInvVal_textArea").parent().find(":input").attr("disabled", false);
     	$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType == 'Date'){

		$j("#advInvVal_date").parent().find(":input").attr("disabled", false);
		$j("#advInvVal_dateIcon").parent().find("img").attr("disabled", false);
		$j("#advInvVal_dateIcon").parent().find("img").attr("tabIndex", "0");
		
     	$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType.indexOf("MULTISELECT")>=0){

		$j($j("#advInvValueList2").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#advInvValueList2").parent().parent()).find("img").attr("disabled", false);
		$j($j("#advInvValueList2").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType.indexOf("SINGLESELECT")>=0){
		$j($j("#advInvValueList1").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#advInvValueList1").parent().parent()).find("img").attr("disabled", false);
		$j($j("#advInvValueList1").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType != null && questionType=='NumericLiteral'){
		$j("#advInvVal_lit").parent().find(":input").attr("disabled", false);
		$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	}else if(questionType != null && questionType=='NumericCoded'){
		$j("#advInvVal_num").parent().find(":input").attr("disabled", false);
		$j($j("#advInvVal_code").parent().parent()).find(":input").attr("disabled", false);
		$j($j("#advInvVal_code").parent().parent()).find("img").attr("disabled", false);
		$j($j("#advInvVal_code").parent().parent()).find("img").attr("tabIndex", "0");
		
		$j("#advInvValueL").parent().find("span[title]").css("color", "#000");
	}


  	$j($j("#advInvLogicList").parent().parent()).find(":input").attr("disabled", false);
  	$j($j("#advInvLogicList").parent().parent()).find("img").attr("disabled", false);
  	$j($j("#advInvLogicList").parent().parent()).find("img").attr("tabIndex", "0");
  	
  	$j("#advInvLogicL").parent().find("span[title]").css("color", "#000");


 	$j("#AddButtonToggleIdAdvancedInvSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedInvSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedInvSubSection").hide();
	clearAdvancedInvCriteriaBatchEntryFields ('IdAdvancedInvSubSection');
}



function enableElrAdvancedCriteriaBatchEntryFields ()
{
	gElrBatchEntryFieldsDisabled = false;

	$j("#testSearchButton").parent().find(":input").attr("disabled", false);
	$j("#testClearButton").parent().find(":input").attr("disabled", false);

	$j("#resultedTestL").parent().find("span[title]").css("color", "#000");
	$j("#testDescription").parent().find("span[title]").css("color", "#000");
	$j("#testResultNote").parent().find("span[title]").css("color", "#000");

	$j("#codeSearchButton").parent().find(":input").attr("disabled", false);
	$j("#codeClearButton").parent().find(":input").attr("disabled", false);
	$j("#codeResultL").parent().find("span[title]").css("color", "#000");
	$j("#resultDescription").parent().find("span[title]").css("color", "#000");

	//$j($j("#codedResultAdvList").parent().parent()).find(":input").attr("disabled", false);
	//$j($j("#codedResultAdvList").parent().parent()).find(":input").css("background", "#FFF");
	//$j($j("#codedResultAdvList").parent().parent()).find("img").attr("disabled", false);
	//$j("#codedResultAdvL").parent().find("span[title]").css("color", "#000");

	$j("#numericResult_text").parent().find(":input").attr("disabled", false);
	$j("#numericResult_text").parent().find(":input").css("background", "#FFF");
	$j("#numericResultL").parent().find("span[title]").css("color", "#000");

	$j("#numericResultHigh_text").parent().find(":input").attr("disabled", false);
	$j("#numericResultHigh_text").parent().find(":input").css("background", "#FFF");
	$j("#numericResultHighL").parent().find("span[title]").css("color", "#000");

	$j($j("#numericResultTypeList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#numericResultTypeList").parent().parent()).find(":input").css("background", "#FFF");
	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#numericResultTypeList").parent().parent()).find("img").attr("tabIndex", "0");
	

	$j("#textResult_text").parent().find(":input").attr("disabled", false);
	$j("#textResult_text").parent().find(":input").css("background", "#FFF");
	$j("#textResultL").parent().find("span[title]").css("color", "#000");

	$j($j("#resultOperatorList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#resultOperatorList").parent().parent()).find(":input").css("background", "#FFF");
	$j($j("#resultOperatorList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#resultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	
	
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#numericResultOperatorList").parent().parent()).find(":input").css("background", "#FFF");
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#numericResultOperatorList").parent().parent()).find("img").attr("tabIndex", "0");
	
	
	$j("#AddButtonToggleIdELRAdvancedSubSection").show();
	$j("#UpdateButtonToggleIdELRAdvancedSubSection").hide();
	$j("#AddNewButtonToggleIdELRAdvancedSubSection").hide();
	clearElrAdvancedCriteriaBatchEntryFields ('ElrIdAdvancedSubSection');
}

function deleteAdvCriteriaClicked(entryId,subSection,pattern,questionBody)
{
	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	var t = getElementByIdOrByName(subSection);
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}

	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				questionType = ans.answerMap['AdvQuestionType'];
			}
		}
		enableAdvancedCriteriaBatchEntryFields(questionType);
		var batchentry = { subsecNm:subSection, id:beIdStr };
		JDecisionSupport.deleteAdvancedCriteriaBatchAnswer(batchentry,function(){
			rewriteAdvancedCriteriaBatchIdHeader(subSection, pattern, questionBody);
		});
	});

	$j("#AddButtonToggleIdAdvancedSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedSubSection").hide();

	clearAdvancedCriteriaBatchEntryFields (subSection);
}

function deleteAdvInvCriteriaClicked(entryId,subSection,pattern,questionBody)
{
	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	var t = getElementByIdOrByName(subSection);
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}

	JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				questionType = ans.answerMap['AdvInvQuestionType'];
			}
		}
		enableAdvancedInvCriteriaBatchEntryFields(questionType);
		var batchentry = { subsecNm:subSection, id:beIdStr };
		JDecisionSupport.deleteAdvancedInvCriteriaBatchAnswer(batchentry,function(){
			rewriteAdvancedInvCriteriaBatchIdHeader(subSection, pattern, questionBody);
		});
	});

	$j("#AddButtonToggleIdAdvancedInvSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedInvSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedInvSubSection").hide();

	clearAdvancedInvCriteriaBatchEntryFields (subSection);
}

function deleteElrAdvCriteriaClicked(entryId,subSection,pattern,questionBody)
{
	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	changeElrSectionLightBGColor();

	enableElrAdvancedCriteriaBatchEntryFields();
	var batchentry = { subsecNm:subSection, id:beIdStr };
	JDecisionSupport.deleteAdvancedCriteriaBatchAnswer(batchentry,function(){
		rewriteElrAdvancedCriteriaBatchIdHeader(subSection, pattern, questionBody);
	});

	$j("#AddButtonToggleIdELRAdvancedSubSection").show();
	$j("#AddNewButtonToggleIdELRAdvancedSubSection").hide();
	$j("#UpdateButtonToggleIdELRAdvancedSubSection").hide();

	clearElrAdvancedCriteriaBatchEntryFields (subSection);
}

function viewClickedOnAdvancedCriteriaViewPage(entryId,tableId)
{
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;
	dwr.engine.beginBatch();
	//get all rows of data
	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(tableId,function(answer) {
		if (answer.length > 0) {
			var ans = answer[beIdStr];
			var id = ans.id;
			gAdvBatchEntryUpdateSeq = id;

			if(tableId == 'advancedCriteriaTable'){
				$j("#advancedCriteriaTable").show();
				dwr.util.setValue("advQuestion",ans.answerMap['AdvQueListTxt']);
				dwr.util.setValue("advValue",ans.answerMap['AdvValValueTxt']);
				dwr.util.setValue("advLogic",ans.answerMap['AdvLogicTxt']);
			}
		}
	}); //all rows of data
	dwr.engine.endBatch();
}

function viewClickedOnAdvancedInvCriteriaViewPage(entryId,tableId)
{
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;
	dwr.engine.beginBatch();
	//get all rows of data
	JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer(tableId,function(answer) {
		if (answer.length > 0) {
			var ans = answer[beIdStr];
			var id = ans.id;
			gAdvInvBatchEntryUpdateSeq = id;

			if(tableId == 'advancedInvCriteriaTable'){
				$j("#advancedInvCriteriaTable").show();
				dwr.util.setValue("advInvQuestion",ans.answerMap['AdvInvQueListTxt']);
				dwr.util.setValue("advInvValue",ans.answerMap['AdvInvValValueTxt']);
				dwr.util.setValue("advInvLogic",ans.answerMap['AdvInvLogicTxt']);
			}
		}
	}); //all rows of data
	dwr.engine.endBatch();
}

function viewClickedOnElrAdvancedCriteriaViewPage(entryId,tableId)
{
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;
	dwr.engine.beginBatch();
	//get all rows of data
	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(tableId,function(answer) {
		if (answer.length > 0) {
			var ans = answer[beIdStr];
			var id = ans.id;
			gElrBatchEntryUpdateSeq = id;

			if(tableId == 'elrAdvancedCriteriaTable'){
				$j("#elrAdvancedCriteriaTable").show();
				if(ans.answerMap['ResultedTestName'] != null)
				{
					dwr.util.setValue("resultedTest",ans.answerMap['ResultedTestName']);
				}
				if(ans.answerMap['CodedResultAdvListTxt'] != null)
				{
					dwr.util.setValue("codedResult",ans.answerMap['CodedResultAdvListTxt']);
					dwr.util.setValue("numericResult","");
					dwr.util.setValue("textResult","");
					dwr.util.setValue("resultOperatorList","");
					dwr.util.setValue("numericResultOperatorList","");
				}
				if(ans.answerMap['NumericResultTxt'] != null)
				{
					if(ans.answerMap['NumericResultTypeListTxt'] != null)
						dwr.util.setValue("numericResult",ans.answerMap['NumericResultTxt'] + ans.answerMap['NumericResultTypeListTxt']);
					else
						dwr.util.setValue("numericResult",ans.answerMap['NumericResultTxt']);
					dwr.util.setValue("codedResult","");
					dwr.util.setValue("textResult","");
					dwr.util.setValue("resultOperatorList",ans.answerMap['ResultNameOperatorTxt']);
				}
				
				
				if(ans.answerMap['TextResultTxt'] != null)
				{
					dwr.util.setValue("resultOperatorList","");
					dwr.util.setValue("textResult",ans.answerMap['TextResultTxt']);
					dwr.util.setValue("codedResult","");
					dwr.util.setValue("numericResult","");
					dwr.util.setValue("numericResultOperatorList","");
				}
				if(ans.answerMap['ResultNameOperator'] != null)
				{
					if(ans.answerMap['ResultNameOperatorTxt'] != null)
						dwr.util.setValue("resultOperatorList", ans.answerMap['ResultNameOperatorTxt']);

					dwr.util.setValue("codedResult","");
					dwr.util.setValue("numericResult","");
					dwr.util.setValue("numericResultOperatorList","");
				}
			}
		}
	}); //all rows of data
	dwr.engine.endBatch();
}

function editAdvCriteriaClicked(entryId,subSection)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	var t = getElementByIdOrByName("IdAdvancedSubSection");
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}

	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	if (gAdvBatchEntryFieldsDisabled == true){
		JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
			for (var i = 0; i < answer.length; i++){
				var ans = answer[i];
				var id = ans.id;
				if (id == beIdStr) {
					questionType = ans.answerMap['AdvQuestionType'];
					enableAdvancedCriteriaBatchEntryFields(questionType);
				}
			}
		});
	}

	//get all rows of data
	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				gAdvBatchEntryUpdateSeq = id;
				questionType = ans.answerMap['AdvQuestionType'];
				var questionListNode = getElementByIdOrByName('questionAdvList');
	        	var questionListTxtNode = getElementByIdOrByName('questionAdvList_textbox');
	        	if (questionListNode != null) {
	        		questionListNode.value = ans.answerMap['AdvQuestionList'];
	        		questionListTxtNode.value = ans.answerMap['AdvQueListTxt'];
	        	}

	        	if(ans.answerMap['AdvQuestionType'] == 'Text')
	        	{
	        		getElementByIdOrByName("advVal_text").style.visibility="visible";
	        		getElementByIdOrByName("advText").style.visibility="visible";
	        		getElementByIdOrByName("advText").style.display = "";
	        		getElementByIdOrByName("advVal_date").value = "";
	        		getElementByIdOrByName("advVal_textArea").value= "";
	        		getElementByIdOrByName("advVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advDate").style.display = "none";
	        		getElementByIdOrByName("advM_sel").style.display = "none";
	        		getElementByIdOrByName("advS_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advNum_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('advVal_text');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvQuestionType'] == 'TextArea')
	        	{
	        		getElementByIdOrByName("advVal_textArea").style.visibility="visible";
	        		getElementByIdOrByName("advVal_textArea").style.display="";
	        		getElementByIdOrByName("advVal_date").value = "";
	        		getElementByIdOrByName("advVal_text").value= "";
	        		getElementByIdOrByName("advText").style.display = "none";
	        		getElementByIdOrByName("advDate").style.display = "none";
	        		getElementByIdOrByName("advM_sel").style.display = "none";
	        		getElementByIdOrByName("advS_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advNum_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('advVal_textArea');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvQuestionType'] == 'Date')
	        	{
	        		getElementByIdOrByName("advVal_date").style.visibility="visible";
	        		getElementByIdOrByName("advDate").style.visibility="visible";
	        		getElementByIdOrByName("advDate").style.display = "";
	        		getElementByIdOrByName("advVal_dateIcon").style.visibility="visible";
	        		getElementByIdOrByName("advVal_textArea").value= "";
	        		getElementByIdOrByName("advText").style.display = "none";
	        		getElementByIdOrByName("advVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advM_sel").style.display = "none";
	        		getElementByIdOrByName("advS_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advNum_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('advVal_date');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvQuestionType'] == 'SINGLESELECT')
	        	{
	        		getElementByIdOrByName("advS_sel").style.visibility="visible";
	        		getElementByIdOrByName("advValueList1").style.visibility="visible";
	        		getElementByIdOrByName("advS_sel").style.display = "";
	        		getElementByIdOrByName("advVal_date").value = "";
	        		getElementByIdOrByName("advVal_textArea").value= "";
	        		getElementByIdOrByName("advText").style.display = "none";
	        		getElementByIdOrByName("advVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advDate").style.display = "none";
	        		getElementByIdOrByName("advM_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advNum_Literal").style.display = "none";

	        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("advValueList1");
					   	DWRUtil.addOptions("advValueList1", data, "key", "value" );
	        		});
	        		var valueNode = getElementByIdOrByName("advValueList1");
	        		var valueTxtNode = getElementByIdOrByName("advValueList1_textbox");
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvValueList1'];
	        			valueTxtNode.value = ans.answerMap['AdvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvQuestionType'] == 'MULTISELECT')
	        	{
	        		getElementByIdOrByName("advM_sel").style.visibility="visible";
	        		getElementByIdOrByName("advM_sel").style.display = "";
	        		getElementByIdOrByName("advValueList2").style.visibility="visible";
	        		getElementByIdOrByName("advVal_date").value = "";
	        		getElementByIdOrByName("advVal_textArea").value= "";
	        		getElementByIdOrByName("advText").style.display = "none";
	        		getElementByIdOrByName("advVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advDate").style.display = "none";
	        		getElementByIdOrByName("advS_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advNum_Literal").style.display = "none";
	        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("advValueList2");
					   	DWRUtil.addOptions("advValueList2", data, "key", "value" );
						if(StartsWith(actionMode, "Edit")){
						   	JDecisionSupport.getMultiselectValues("CriteriaValue",function(data){
						   	   	var selectCondList = "";
						   		for (var j = 0; j < data.length; j++){
							   		var multiselectValueNode = getElementByIdOrByName('advValueList2');
							   		var multiselectNodeVal = multiselectValueNode.options.length;
							   		for(var i=0; i < multiselectNodeVal; i++){
								   		if(multiselectValueNode.options[i].value ==  data[j])
								   		{
								   			multiselectValueNode.options[i].selected = true;
								   		}
							   		}
						   		}
						   		displaySelectedOptions(getElementByIdOrByName("advValueList2"), 'advValueList2-selectedValues');
						   	});
						}
						var valueNode = getElementByIdOrByName("advValueList2");
	        		var valueTxtNode = getElementByIdOrByName("advValueList2-selectedValues");
	        		if (valueNode != null) {
	        			var multiselValue = ans.answerMap['AdvValueList2']
	        			var multiselCode = multiselValue.substring(multiselValue.lastIndexOf("^") + 1, multiselValue.length);
	        			//valueNode.value = multiselCode;
	        			var multiselectNodeVal = valueNode.options.length;
	        			var mySplitResult = multiselValue.split("^^");
	        			if(StartsWith(actionMode, "Create")){
		        			for(j=1; j<multiselValue.split("^^").length; j++) {
			        			for(var i=1; i < multiselectNodeVal; i++){
							   		if( jQuery.trim(valueNode.options[i].value) ==  jQuery.trim(mySplitResult[j]))
							   		{
							   			getElementByIdOrByName('advValueList2').options[i].selected = true;
							   		}
						   		}
		        			}
		        			displaySelectedOptions(getElementByIdOrByName("advValueList2"), 'advValueList2-selectedValues');
	        			}
	        		}
	        		});
	        	}else if(questionType != null && questionType=='NumericLiteral'){
	        		getElementByIdOrByName("advNum_Literal").style.visibility="visible";
	        		getElementByIdOrByName("advNum_Literal").style.display = "";
	        		getElementByIdOrByName("advVal_lit").style.visibility="visible";

	        		getElementByIdOrByName("advText").style.display = "none";
	        		getElementByIdOrByName("advVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advDate").style.display = "none";
	        		getElementByIdOrByName("advS_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advM_sel").style.display = "none";

	        		var valueNode = getElementByIdOrByName('advVal_lit');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvValValueTxt'];
	        		}

	        	}else if(questionType != null && questionType=='NumericCoded'){
	        		getElementByIdOrByName("advNum_Coded").style.visibility="visible";
	        		getElementByIdOrByName("advNum_Coded").style.display = "";
	        		getElementByIdOrByName("advVal_num").style.visibility="visible";
	        		getElementByIdOrByName("advVal_code").style.visibility="visible";

	        		getElementByIdOrByName("advText").style.display = "none";
	        		getElementByIdOrByName("advVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advDate").style.display = "none";
	        		getElementByIdOrByName("advS_sel").style.display = "none";
	        		getElementByIdOrByName("advNum_Literal").style.display = "none";
	        		getElementByIdOrByName("advM_sel").style.display = "none";

	        		JDecisionSupport.getValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("advVal_code");
					   	DWRUtil.addOptions("advVal_code", data, "key", "value" );
	        		});
	        		var valueNode1 = getElementByIdOrByName('advVal_num');
	        		var valueNode2 = getElementByIdOrByName('advVal_code');
	        		var valueTxtNode = getElementByIdOrByName('advVal_code_textbox');
	        		var valueNode = valueNode1.value+"^"+valueNode2.value;
	        		if (valueNode != null) {
	        			var valueCoded = ans.answerMap['AdvValueList3'];
	        			var mySplitResult = valueCoded.split("^");
	        			valueNode1.value = mySplitResult[0];
	        			valueNode2.value = mySplitResult[1];
	        			valueTxtNode.value = ans.answerMap['AdvValCodeTxt'];
	        		}
	        	}

	        	var logicNode = getElementByIdOrByName('advLogicList');
	        	var logicTxtNode = getElementByIdOrByName('advLogicList_textbox');
	        	if (logicNode != null) {

	        		if(ans.answerMap['AdvQuestionType'] != null && (ans.answerMap['AdvQuestionType'] == 'Text' || ans.answerMap['AdvQuestionType'] == 'TextArea'
	        				|| ans.answerMap['AdvQuestionType'] == 'SINGLESELECT' || ans.answerMap['AdvQuestionType'] == 'MULTISELECT'
	        					|| ans.answerMap['AdvQuestionType'] == 'NumericCoded')){
	        			JDecisionSupport.getDWRAdvancedValueList("SEARCH_ALPHA", function(data){
	        				//dwr.util.setEscapeHtml(false);
	    					DWRUtil.removeAllOptions("advLogicList");
	    					DWRUtil.addOptions("advLogicList", data, "key", "value");
	    					logicNode.value = ans.answerMap['AdvLogicList'];
	    					logicTxtNode.value = ans.answerMap['AdvLogicTxt'];
	    				});
	        		}else if(ans.answerMap['AdvQuestionType'] != null && (ans.answerMap['AdvQuestionType'] == 'Date' || ans.answerMap['AdvQuestionType'] == 'NumericLiteral')){
	        			JDecisionSupport.getDWRAdvancedValueList("SEARCH_NUM", function(data){
	        			//dwr.util.setEscapeHtml(false);
	    					DWRUtil.removeAllOptions("advLogicList");
	    					DWRUtil.addOptions("advLogicList", data, "key", "value");
	    					logicNode.value = ans.answerMap['AdvLogicList'];
	    					logicTxtNode.value = ans.answerMap['AdvLogicTxt'];
	    				});
	        		}
	        	}
				$j("#AddButtonToggleIdAdvancedSubSection").hide();
				$j("#UpdateButtonToggleIdAdvancedSubSection").show();
				$j("#AddNewButtonToggleIdAdvancedSubSection").hide();
				break;
			}
		}
	}); //all rows of data
}
var editedAdvInvCriteria=false;

function editAdvInvCriteriaClicked(entryId,subSection)
{
	var actionMode =getElementByIdOrByName("actionMode") == null ? "" : getElementByIdOrByName("actionMode") .value;
	var t = getElementByIdOrByName("IdAdvancedInvSubSection");
	t.style.display = "";
	for (var i = 0; i < 4; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}

	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	if (gAdvInvBatchEntryFieldsDisabled == true){
		JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer(subSection,function(answer) {
			for (var i = 0; i < answer.length; i++){
				var ans = answer[i];
				var id = ans.id;
				if (id == beIdStr) {
					questionType = ans.answerMap['AdvInvQuestionType'];
					enableAdvancedInvCriteriaBatchEntryFields(questionType);
				}
			}
		});
	}

	 editedAdvInvCriteria=true;
	//get all rows of data
	JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				gAdvInvBatchEntryUpdateSeq = id;
				questionType = ans.answerMap['AdvInvQuestionType'];
				var questionListNode = getElementByIdOrByName('questionAdvInvList');
	        	var questionListTxtNode = getElementByIdOrByName('questionAdvInvList_textbox');
	        	if (questionListNode != null) {
	        		questionListNode.value = ans.answerMap['AdvInvQuestionList'];
					//alert("question.value edit "+questionListNode.value);
	        		questionListTxtNode.value = ans.answerMap['AdvInvQueListTxt'];
	        	}

	        	if(ans.answerMap['AdvInvQuestionType'] == 'Text')
	        	{
	        		getElementByIdOrByName("advInvVal_text").style.visibility="visible";
	        		getElementByIdOrByName("advInvText").style.visibility="visible";
	        		getElementByIdOrByName("advInvText").style.display = "";

	        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advInvDate").style.display = "none";
	        		getElementByIdOrByName("advInvVal_date").value = "";
	        		getElementByIdOrByName("advInvVal_textArea").value= "";
	        		getElementByIdOrByName("advInvM_sel").style.display = "none";
	        		getElementByIdOrByName("advInvS_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('advInvVal_text');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvInvQuestionType'] == 'TextArea')
	        	{
	        		getElementByIdOrByName("advInvVal_textArea").style.visibility="visible";
	        		getElementByIdOrByName("advInvVal_textArea").style.display="";
	        		getElementByIdOrByName("advInvVal_date").value = "";
	        		getElementByIdOrByName("advInvVal_text").value= "";
	        		getElementByIdOrByName("advInvText").style.display = "none";
	        		getElementByIdOrByName("advInvDate").style.display = "none";
	        		getElementByIdOrByName("advInvM_sel").style.display = "none";
	        		getElementByIdOrByName("advInvS_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('advInvVal_textArea');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvInvQuestionType'] == 'Date')
	        	{
	        		getElementByIdOrByName("advInvVal_date").style.visibility="visible";
	        		getElementByIdOrByName("advInvDate").style.visibility="visible";
	        		getElementByIdOrByName("advInvDate").style.display = "";
	        		getElementByIdOrByName("advInvVal_dateIcon").style.visibility="visible";
	        		getElementByIdOrByName("advInvVal_text").value= "";
	        		getElementByIdOrByName("advInvVal_textArea").value= "";
	        		getElementByIdOrByName("advInvText").style.display = "none";
	        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advInvM_sel").style.display = "none";
	        		getElementByIdOrByName("advInvS_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
	        		var valueNode = getElementByIdOrByName('advInvVal_date');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
	        		}
	        	}else if(ans.answerMap['AdvInvQuestionType'] == 'SINGLESELECT')
	        	{
	        		getElementByIdOrByName("advInvS_sel").style.visibility="visible";
	        		getElementByIdOrByName("advInvValueList1").style.visibility="visible";
	        		getElementByIdOrByName("advInvS_sel").style.display = "";
	        		getElementByIdOrByName("advInvVal_date").value = "";
	        		getElementByIdOrByName("advInvVal_text").value= "";
	        		getElementByIdOrByName("advInvVal_textArea").value= "";
	        		getElementByIdOrByName("advInvText").style.display = "none";
	        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advInvDate").style.display = "none";
	        		getElementByIdOrByName("advInvM_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";

	        		JDecisionSupport.getAdvInvValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("advInvValueList1");
					   	DWRUtil.addOptions("advInvValueList1", data, "key", "value" );
						var valueNode = getElementByIdOrByName("advInvValueList1");
	        		var valueTxtNode = getElementByIdOrByName("advInvValueList1_textbox");
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvInvValueList1'];
						//alert(valueNode.value);
	        			valueTxtNode.value = ans.answerMap['AdvInvValValueTxt'];
	        		}
	        		});
	        		
	        	}else if(ans.answerMap['AdvInvQuestionType'] == 'MULTISELECT')
	        	{
	        		getElementByIdOrByName("advInvM_sel").style.visibility="visible";
	        		getElementByIdOrByName("advInvM_sel").style.display = "";
	        		getElementByIdOrByName("advInvValueList2").style.visibility="visible";
	        		getElementByIdOrByName("advInvVal_date").value = "";
	        		getElementByIdOrByName("advInvVal_text").value= "";
	        		getElementByIdOrByName("advInvVal_textArea").value= "";
	        		getElementByIdOrByName("advInvText").style.display = "none";
	        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advInvDate").style.display = "none";
	        		getElementByIdOrByName("advInvS_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
	        		JDecisionSupport.getAdvInvValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("advInvValueList2");
					   	DWRUtil.addOptions("advInvValueList2", data, "key", "value" );
						if(StartsWith(actionMode, "Edit")){
						   	JDecisionSupport.getMultiselectValues("AdvInvCriteriaValue",function(data){
						   	   	var selectCondList = "";
						   		for (var j = 0; j < data.length; j++){
							   		var multiselectValueNode = getElementByIdOrByName('advInvValueList2');
							   		var multiselectNodeVal = multiselectValueNode.options.length;
							   		for(var i=0; i < multiselectNodeVal; i++){
								   		if(multiselectValueNode.options[i].value ==  data[j])
								   		{
								   			multiselectValueNode.options[i].selected = true;
								   		}
							   		}
						   		}
						   		displaySelectedOptions(getElementByIdOrByName("advInvValueList2"), 'advInvValueList2-selectedValues');
						   	});
						}
						var valueNode = getElementByIdOrByName("advInvValueList2");
	        		var valueTxtNode = getElementByIdOrByName("advInvValueList2-selectedValues");
	        		if (valueNode != null) {
	        			var multiselValue = ans.answerMap['AdvInvValueList2']
	        			var multiselCode = multiselValue.substring(multiselValue.lastIndexOf("^") + 1, multiselValue.length);
	        			//valueNode.value = multiselCode;
	        			var multiselectNodeVal = valueNode.options.length;
	        			var mySplitResult = multiselValue.split("^^");
	        			if(StartsWith(actionMode, "Create")){
		        			for(j=1; j<multiselValue.split("^^").length; j++) {
			        			for(var i=1; i < multiselectNodeVal; i++){
							   		if( jQuery.trim(valueNode.options[i].value) ==  jQuery.trim(mySplitResult[j]))
							   		{
							   			getElementByIdOrByName('advInvValueList2').options[i].selected = true;
							   		}
						   		}
		        			}
		        			displaySelectedOptions(getElementByIdOrByName("advInvValueList2"), 'advInvValueList2-selectedValues');
	        			}
	        		}
	        		});
	        	}else if(questionType != null && questionType=='NumericLiteral'){
	        		getElementByIdOrByName("advInvNum_Literal").style.visibility="visible";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "";
	        		getElementByIdOrByName("advInvVal_lit").style.visibility="visible";
	        		getElementByIdOrByName("advInvVal_date").value = "";
	        		getElementByIdOrByName("advInvText").style.display = "none";
	        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advInvDate").style.display = "none";
	        		getElementByIdOrByName("advInvS_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	        		getElementByIdOrByName("advInvM_sel").style.display = "none";

	        		var valueNode = getElementByIdOrByName('advInvVal_lit');
	        		if (valueNode != null) {
	        			valueNode.value = ans.answerMap['AdvInvValValueTxt'];
	        		}

	        	}else if(questionType != null && questionType=='NumericCoded'){
	        		getElementByIdOrByName("advInvNum_Coded").style.visibility="visible";
	        		getElementByIdOrByName("advInvNum_Coded").style.display = "";
	        		getElementByIdOrByName("advInvVal_num").style.visibility="visible";
	        		getElementByIdOrByName("advInvVal_code").style.visibility="visible";
	        		getElementByIdOrByName("advInvVal_date").value = "";
	        		getElementByIdOrByName("advInvText").style.display = "none";
	        		getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	        		getElementByIdOrByName("advInvDate").style.display = "none";
	        		getElementByIdOrByName("advInvS_sel").style.display = "none";
	        		getElementByIdOrByName("advInvNum_Literal").style.display = "none";
	        		getElementByIdOrByName("advInvM_sel").style.display = "none";

	        		JDecisionSupport.getAdvInvValueSetDropDown(questionListNode.value,function(data) {
	        			DWRUtil.removeAllOptions("advInvVal_code");
					   	DWRUtil.addOptions("advInvVal_code", data, "key", "value" );
	        		});
	        		var valueNode1 = getElementByIdOrByName('advInvVal_num');
	        		var valueNode2 = getElementByIdOrByName('advInvVal_code');
	        		var valueTxtNode = getElementByIdOrByName('advInvVal_code_textbox');
	        		var valueNode = valueNode1.value+"^"+valueNode2.value;
	        		if (valueNode != null) {
	        			var valueCoded = ans.answerMap['AdvInvValueList3'];
	        			var mySplitResult = valueCoded.split("^");
	        			valueNode1.value = mySplitResult[0];
	        			valueNode2.value = mySplitResult[1];
	        			valueTxtNode.value = ans.answerMap['AdvInvValCodeTxt'];
	        		}
	        	}

	        	var logicNode = getElementByIdOrByName('advInvLogicList');
	        	var logicTxtNode = getElementByIdOrByName('advInvLogicList_textbox');
	        	if (logicNode != null) {

	        		if(ans.answerMap['AdvInvQuestionType'] != null && (ans.answerMap['AdvInvQuestionType'] == 'Text' || ans.answerMap['AdvInvQuestionType'] == 'TextArea'
	        				|| ans.answerMap['AdvInvQuestionType'] == 'SINGLESELECT' || ans.answerMap['AdvInvQuestionType'] == 'MULTISELECT'
	        					|| ans.answerMap['AdvInvQuestionType'] == 'NumericCoded')){
	        			JDecisionSupport.getDWRAdvancedInvValueList("SEARCH_ALPHA", function(data){
	        				//dwr.util.setEscapeHtml(false);
	    					DWRUtil.removeAllOptions("advInvLogicList");
	    					DWRUtil.addOptions("advInvLogicList", data, "key", "value");
	    					logicNode.value = ans.answerMap['AdvInvLogicList'];
	    					logicTxtNode.value = ans.answerMap['AdvInvLogicTxt'];
	    				});
	        		}else if(ans.answerMap['AdvInvQuestionType'] != null && (ans.answerMap['AdvInvQuestionType'] == 'Date' || ans.answerMap['AdvInvQuestionType'] == 'NumericLiteral')){
	        			JDecisionSupport.getDWRAdvancedInvValueList("SEARCH_NUM", function(data){
	        			//dwr.util.setEscapeHtml(false);
	    					DWRUtil.removeAllOptions("advInvLogicList");
	    					DWRUtil.addOptions("advInvLogicList", data, "key", "value");
	    					logicNode.value = ans.answerMap['AdvInvLogicList'];
	    					logicTxtNode.value = ans.answerMap['AdvInvLogicTxt'];
	    				});
	        		}
	        	}
				$j("#AddButtonToggleIdAdvancedInvSubSection").hide();
				$j("#UpdateButtonToggleIdAdvancedInvSubSection").show();
				$j("#AddNewButtonToggleIdAdvancedInvSubSection").hide();
				break;
			}
		}
	}); //all rows of data
}

function editElrAdvCriteriaClicked(entryId,subSection)
{
	changeElrSectionDarkBGColor();

	var questionType ="";
	var beIdStr = entryId.match(/\d+/)[0];
	if (beIdStr == 'null' || beIdStr == "")
		return;

	if (gElrBatchEntryFieldsDisabled == true){
		enableElrAdvancedCriteriaBatchEntryFields();
	}

	//get all rows of data
	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer(subSection,function(answer) {
		for (var i = 0; i < answer.length; i++){
			var ans = answer[i];
			var id = ans.id;
			if (id == beIdStr) {
				gElrBatchEntryUpdateSeq = id;

				var testCodeIdNode = getElementByIdOrByName('testCodeId');
	        	var testDescriptionIdNode = getElementByIdOrByName('testDescriptionId');
	        	var testDescriptionNode = getElementByIdOrByName('testDescription');
	        	if (testCodeIdNode != null && ans.answerMap['ResultedTestCode'] != null) {
	        		testCodeIdNode.value = ans.answerMap['ResultedTestCode'];
	        		testDescriptionIdNode.value = ans.answerMap['ResultedTestName'];
	        		testDescriptionNode.innerText = ans.answerMap['ResultedTestName'];
	        		testDescriptionNode.textContent = ans.answerMap['ResultedTestName'];
	        	}
	        	//var codedResultAdvListNode = getElementByIdOrByName('codedResultAdvList');
	        	//var codedResultAdvList_textboxNode = getElementByIdOrByName('codedResultAdvList_textbox');
	        	var numericResult_textNode = getElementByIdOrByName('numericResult_text');
	        	var numericResultTypeListNode = getElementByIdOrByName('numericResultTypeList');
	        	var numericResultTypeList_textboxNode = getElementByIdOrByName('numericResultTypeList_textbox');
	        	
	        	var textResult_textNode = getElementByIdOrByName('textResult_text');
				var resultOperatorListNode = getElementByIdOrByName('resultOperatorList');
				var resultOperatorListNode_textNode = getElementByIdOrByName('resultOperatorList_textbox');
				
				var numericResultOperatorListNode = getElementByIdOrByName('numericResultOperatorList');
				var numericResultOperatorListNode_textNode = getElementByIdOrByName('numericResultOperatorList_textbox');
				
	        	var codeResultIdNode = getElementByIdOrByName('codeResultId');
	        	var resultDescriptionIdNode = getElementByIdOrByName('resultDescriptionId');
	        	var resultDescriptionNode = getElementByIdOrByName('resultDescription');

	        	//Hiding numericResultHigh_div
	        	getElementByIdOrByName("numericResultHigh_div").style.visibility="hidden";
				getElementByIdOrByName("numericResultHigh_div").style.display = "none";
				
	        	if (codeResultIdNode != null && ans.answerMap['CodedResultAdvList'] != null) {
	        		codeResultIdNode.value = ans.answerMap['CodedResultAdvList'];	        		
	        		resultDescriptionIdNode.value = ans.answerMap['CodedResultAdvListTxt'];
				resultDescriptionNode.innerText = ans.answerMap['CodedResultAdvListTxt'];
	        		resultDescriptionNode.textContent = ans.answerMap['CodedResultAdvListTxt'];
	        		numericResult_textNode.value = "";
	        		numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		textResult_textNode.value = "";
	        		resultOperatorListNode.value = "";
	        		resultOperatorListNode_textNode.value = "";
	        		numericResultOperatorListNode.value = "";
	        		numericResultOperatorListNode_textNode.value = "";
	        	}

	        	//if (codedResultAdvListNode != null && ans.answerMap['CodedResultAdvList'] != null) {
	        	//	codedResultAdvListNode.value = ans.answerMap['CodedResultAdvList'];
	        	//	codedResultAdvList_textboxNode.value = ans.answerMap['CodedResultAdvListTxt'];
	        	//	numericResult_textNode.value = "";
	        	//	numericResultTypeListNode.value = "";
	        	//	numericResultTypeList_textboxNode.value = "";
	        	//	textResult_textNode.value = "";
	        	//}

	        	if (numericResultOperatorListNode != null && ans.answerMap['NumericResultNameOperator'] != null) {
					numericResultOperatorListNode.value = ans.answerMap['NumericResultNameOperator'];
		        	numericResultOperatorListNode_textNode.value = ans.answerMap['ResultNameOperatorTxt'];
	        		codeResultIdNode.value = "";
	        		resultDescriptionIdNode.value = "";
	        		resultDescriptionNode.innerText = "";
	        		textResult_textNode.value = "";
	        		resultOperatorListNode.value = "";
	        		resultOperatorListNode_textNode.value = "";
	        	}
	        	if (numericResult_textNode != null && ans.answerMap['NumericResultTxt'] != null) {
	        		numericResult_textNode.value = ans.answerMap['NumericResultTxt'];
	        		//codedResultAdvListNode.value = "";
	        		//codedResultAdvList_textboxNode.value = "";
	        		codeResultIdNode.value = "";
	        		resultDescriptionIdNode.value = "";
	        		resultDescriptionNode.innerText = "";
	        		textResult_textNode.value = "";
	        		resultOperatorListNode.value = "";
	        		resultOperatorListNode_textNode.value = "";
	        		
	        		if(ans.answerMap['NumericResultNameOperator']=='BET'){
	        			var numericResultHigh_textNode = getElementByIdOrByName('numericResultHigh_text');
	        			
	        			getElementByIdOrByName("numericResultHigh_div").style.visibility="visible";
						getElementByIdOrByName("numericResultHigh_div").style.display = "";
						
						if(ans.answerMap['numericResultHighTxt']==null || ans.answerMap['numericResultHighTxt']==''){
							var toSplit = ans.answerMap['NumericResultTxt'];
							var mySplitResult = toSplit.split("-");
							numericResult_textNode.value = mySplitResult[0];
							numericResultHigh_textNode.value = mySplitResult[1];
						}else{
							numericResult_textNode.value = ans.answerMap['NumericResultTxt'];
							numericResultHigh_textNode.value = ans.answerMap['numericResultHighTxt'];
						}
	        		}
	        	}
	        	if(numericResultTypeListNode != null && ans.answerMap['NumericResultTypeList'] != null){
	        		numericResultTypeListNode.value = ans.answerMap['NumericResultTypeList'];
	        		numericResultTypeList_textboxNode.value = ans.answerMap['NumericResultTypeListTxt'];
	        	}
	        	if (textResult_textNode != null && ans.answerMap['TextResultTxt'] != null) {
	        		textResult_textNode.value = ans.answerMap['TextResultTxt'];
	        		//resultOperatorListNode.value = ans.answerMap['ResultNameOperator'];
	        		//resultOperatorListNode_textNode.value = ans.answerMap['ResultNameOperatorTxt'];
				
	        		//codedResultAdvListNode.value = "";
	        		//codedResultAdvList_textboxNode.value = "";
	        		codeResultIdNode.value = "";
	        		resultDescriptionIdNode.value = "";
	        		resultDescriptionNode.innerText = "";

	        		numericResult_textNode.value = "";
	        		numericResultTypeListNode.value = "";
	        		numericResultTypeList_textboxNode.value = "";
	        		numericResultOperatorListNode.value = "";
	        		numericResultOperatorListNode_textNode.value = "";
	        	}
			if (resultOperatorListNode != null && ans.answerMap['ResultNameOperator'] != null) {
				//textResult_textNode.value = ans.answerMap['TextResultTxt'];
				resultOperatorListNode.value = ans.answerMap['ResultNameOperator'];
				resultOperatorListNode_textNode.value = ans.answerMap['ResultNameOperatorTxt'];

				//codedResultAdvListNode.value = "";
				//codedResultAdvList_textboxNode.value = "";
				codeResultIdNode.value = "";
				resultDescriptionIdNode.value = "";
				resultDescriptionNode.innerText = "";

				numericResult_textNode.value = "";
				numericResultTypeListNode.value = "";
				numericResultTypeList_textboxNode.value = "";
				numericResultOperatorListNode.value = "";
        		numericResultOperatorListNode_textNode.value = "";
				
				if(ans.answerMap['TextResultTxt'] == null){
					textResult_textNode.value = "";
				}
				
	        	}
	        	
	        	checkElrAdvancedCriteriaBatchEntryFields();

				$j("#AddButtonToggleIdELRAdvancedSubSection").hide();
				$j("#AddNewButtonToggleIdELRAdvancedSubSection").hide();
				$j("#UpdateButtonToggleIdELRAdvancedSubSection").show();
				break;
			}
		}
	}); //all rows of data
}

function addNewAdvancedBatchIdEntryFields(subSection)
{
	clearAdvancedCriteriaBatchEntryFields(subSection);
	$j($j("#questionAdvList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#questionAdvList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#questionAdvList").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#questionAdvL").parent().find("span[title]").css("color", "#000");

	$j("#advVal_text").parent().find(":input").attr("disabled", false);
	$j("#advValueL").parent().find("span[title]").css("color", "#000");

	$j("#advVal_textArea").parent().find(":input").attr("disabled", true);
	$j("#advVal_date").parent().find(":input").attr("disabled", true);
	$j("#advVal_dateIcon").parent().find("img").attr("disabled", true);
	$j("#advVal_dateIcon").parent().find("img").attr("tabIndex", "-1");
	
	$j($j("#advValueList2").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advValueList2").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advValueList2").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j($j("#advValueList1").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advValueList1").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advValueList1").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#advVal_lit").parent().find(":input").attr("disabled", true);
	$j("#advVal_num").parent().find(":input").attr("disabled", true);
	$j($j("#advVal_code").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advVal_code").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advVal_code").parent().parent()).find("img").attr("tabIndex", "-1");
	

	getElementByIdOrByName("advVal_text").style.visibility="visible";
	getElementByIdOrByName("advText").style.visibility="visible";
	getElementByIdOrByName("advText").style.display = "";

	getElementByIdOrByName("advVal_textArea").style.display = "none";
	getElementByIdOrByName("advDate").style.display = "none";
	getElementByIdOrByName("advM_sel").style.display = "none";
	getElementByIdOrByName("advS_sel").style.display = "none";
	getElementByIdOrByName("advNum_Coded").style.display = "none";
	getElementByIdOrByName("advNum_Literal").style.display = "none";

	$j($j("#advLogicList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#advLogicList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#advLogicList").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#advLogicL").parent().find("span[title]").css("color", "#000");

	$j("#AddButtonToggleIdAdvancedSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedSubSection").hide();
}

function addNewAdvancedInvBatchIdEntryFields(subSection)
{
	clearAdvancedInvCriteriaBatchEntryFields(subSection);
	$j($j("#questionAdvInvList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#questionAdvInvList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#questionAdvInvList").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#questionAdvInvL").parent().find("span[title]").css("color", "#000");

	$j("#advInvVal_text").parent().find(":input").attr("disabled", false);
	$j("#advInvValueL").parent().find("span[title]").css("color", "#000");

	$j("#advInvVal_textArea").parent().find(":input").attr("disabled", true);
	$j("#advInvVal_date").parent().find(":input").attr("disabled", true);
	$j("#advInvVal_dateIcon").parent().find("img").attr("disabled", true);
	$j("#advInvVal_dateIcon").parent().find("img").attr("tabIndex", "-1");
	
	$j($j("#advInvValueList2").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advInvValueList2").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advInvValueList2").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j($j("#advInvValueList1").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advInvValueList1").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advInvValueList1").parent().parent()).find("img").attr("tabIndex", "-1");
	
	$j("#advInvVal_lit").parent().find(":input").attr("disabled", true);
	$j("#advInvVal_num").parent().find(":input").attr("disabled", true);
	$j($j("#advInvVal_code").parent().parent()).find(":input").attr("disabled", true);
	$j($j("#advInvVal_code").parent().parent()).find("img").attr("disabled", true);
	$j($j("#advInvVal_code").parent().parent()).find("img").attr("tabIndex", "-1");
	

	getElementByIdOrByName("advInvVal_text").style.visibility="visible";
	getElementByIdOrByName("advInvText").style.visibility="visible";
	getElementByIdOrByName("advInvText").style.display = "";

	getElementByIdOrByName("advInvVal_textArea").style.display = "none";
	getElementByIdOrByName("advInvDate").style.display = "none";
	getElementByIdOrByName("advInvM_sel").style.display = "none";
	getElementByIdOrByName("advInvS_sel").style.display = "none";
	getElementByIdOrByName("advInvNum_Coded").style.display = "none";
	getElementByIdOrByName("advInvNum_Literal").style.display = "none";

	$j($j("#advInvLogicList").parent().parent()).find(":input").attr("disabled", false);
	$j($j("#advInvLogicList").parent().parent()).find("img").attr("disabled", false);
	$j($j("#advInvLogicList").parent().parent()).find("img").attr("tabIndex", "0");
	
	$j("#advInvLogicL").parent().find("span[title]").css("color", "#000");

	$j("#AddButtonToggleIdAdvancedInvSubSection").show();
	$j("#AddNewButtonToggleIdAdvancedInvSubSection").hide();
	$j("#UpdateButtonToggleIdAdvancedInvSubSection").hide();
}


function fillDefaultTable() {
	var rowClass = "";

	JDecisionSupport.getAllBatchAnswer("IdSubSection",function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows("questionbodyIdSubSection", { filter:function(tr) {
			return (tr.id != "patternIdSubSection");
		}});
		// Create a new set cloned from the pattern row
		var ans, id;
		if (answer.length == 0) {
		 	return;
		 }
		for (var i = 0; i < answer.length; i++) {
			if (i%2 == 0)
		      	rowClass="odd";
			else
				rowClass="even";
			ans= answer[i];
			id = ans.id;
			dwr.util.cloneNode('patternIdSubSection', { idSuffix:id });

			for (var key in ans.answerMap) {
				if (key == 'queListTxt' ||key == 'valValueTxt' || key == 'behaviorTxt') {
					var val = ans.answerMap[key];
		      		if (key == 'valValueTxt')
		      			$j("#table" + key + id).html(val);
		      		else
		      			dwr.util.setValue("table" + key + id, val);
				}
			}
			getElementByIdOrByName("patternIdSubSection"+id).style.display = "";
			//hide No Data Entered
			$('nopattern' +'IdSubSection').style.display = "none";
			getElementByIdOrByName("patternIdSubSection"+id).setAttribute("className",rowClass);
			getElementByIdOrByName("patternIdSubSection"+id).setAttribute("class",rowClass);
			
		}
	});
}

function fillAdvancedCriteriaTable() {

	var rowClass = "";

	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer("IdAdvancedSubSection",function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows("questionbodyIdAdvancedSubSection", { filter:function(tr) {
			return (tr.id != "patternIdAdvancedSubSection");
		}});
		// Create a new set cloned from the pattern row
		var ans, id;
		if (answer.length == 0) {
		 	return;
		 }
		for (var i = 0; i < answer.length; i++) {
			if (i%2 == 0)
		      	rowClass="odd";
			else
				rowClass="even";
			ans= answer[i];
			id = ans.id;
			dwr.util.cloneNode('patternIdAdvancedSubSection', { idSuffix:id });

			for (var key in ans.answerMap) {
				if (key == 'AdvQueListTxt' ||key == 'AdvValValueTxt' || key == 'AdvLogicTxt') {
		      		var val = ans.answerMap[key];
					dwr.util.setValue("table" + key + id, val);
				}
			}
			getElementByIdOrByName("patternIdAdvancedSubSection"+id).style.display = "";
			//hide No Data Entered
			$('nopattern' +'IdAdvancedSubSection').style.display = "none";
			getElementByIdOrByName("patternIdAdvancedSubSection"+id).setAttribute("className",rowClass);
			getElementByIdOrByName("patternIdAdvancedSubSection"+id).setAttribute("class",rowClass);

		}
	});
}

function fillAdvancedInvCriteriaTable() {

	var rowClass = "";

	JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer("IdAdvancedInvSubSection",function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows("questionbodyIdAdvancedInvSubSection", { filter:function(tr) {
			return (tr.id != "patternIdAdvancedInvSubSection");
		}});
		// Create a new set cloned from the pattern row
		var ans, id;
		if (answer.length == 0) {
		 	return;
		 }
		for (var i = 0; i < answer.length; i++) {
			if (i%2 == 0)
		      	rowClass="odd";
			else
				rowClass="even";
			ans= answer[i];
			id = ans.id;
			dwr.util.cloneNode('patternIdAdvancedInvSubSection', { idSuffix:id });

			for (var key in ans.answerMap) {
				if (key == 'AdvInvQueListTxt' ||key == 'AdvInvValValueTxt' || key == 'AdvInvLogicTxt') {
		      		var val = ans.answerMap[key];
					dwr.util.setValue("table" + key + id, val);
				}
			}
			getElementByIdOrByName("patternIdAdvancedInvSubSection"+id).style.display = "";
			//hide No Data Entered
			$('nopattern' +'IdAdvancedInvSubSection').style.display = "none";
			getElementByIdOrByName("patternIdAdvancedInvSubSection"+id).setAttribute("className",rowClass);
			getElementByIdOrByName("patternIdAdvancedInvSubSection"+id).setAttribute("class",rowClass);

		}
	});
}

function fillElrAdvancedCriteriaTable() {

	var rowClass = "";

	JDecisionSupport.getAllAdvancedCriteriaBatchAnswer("ElrIdAdvancedSubSection",function(answer) {
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows("questionbodyIdElrAdvancedSubSection", { filter:function(tr) {
			return (tr.id != "patternIdElrAdvancedSubSection");
		}});
		// Create a new set cloned from the pattern row
		var ans, id;
		if (answer.length == 0) {
		 	return;
		 }
		for (var i = 0; i < answer.length; i++) {
			if (i%2 == 0)
		      	rowClass="odd";
			else
				rowClass="even";
			ans= answer[i];
			id = ans.id;
			dwr.util.cloneNode('patternIdElrAdvancedSubSection', { idSuffix:id });

			for (var key in ans.answerMap) {
				if (key == 'ResultedTestName' || key == 'ResultNameTxt' || key == 'ResultNameOperatorTxt') {
		      		var val = ans.answerMap[key];
					dwr.util.setValue("table" + key + id, val);
				}
			}
			getElementByIdOrByName("patternIdElrAdvancedSubSection"+id).style.display = "";
			//hide No Data Entered
			$('nopattern' +'ElrIdAdvancedSubSection').style.display = "none";
			getElementByIdOrByName("patternIdElrAdvancedSubSection"+id).setAttribute("className",rowClass);
			getElementByIdOrByName("patternIdElrAdvancedSubSection"+id).setAttribute("class",rowClass);
			
		}
	});
}

function checkDuplicateDefault(question)
{
	if(!edited){
	if(question != null || question != "")
	   {
		JDecisionSupport.getAllBatchAnswer("IdSubSection",function(answer) {
			if (answer.length == 0) {
			 	return;
			 }
			for (var i = 0; i < answer.length; i++) {
				ans= answer[i];
				for (var key in ans.answerMap) {
					if (key == 'questionList') {
						var questionId = ans.answerMap[key];
						if(questionId == question.value)
						{
							alert("Default value for this question has been set. Please select another question.");
							//$j($j("#questionList").parent().parent()).find(":input").val("");
							clearBatchEntryFields("IdSubSection");
							return;
						}
					}
				}
			}
		});

	   }
	}
	   if(edited)
		   edited=false;
}

function checkDuplicateAdvancedCriteria(question)
{
	if(question.value != null || question.value != "")
	   {
		JDecisionSupport.getAllAdvancedCriteriaBatchAnswer("IdAdvancedSubSection",function(answer) {
			if (answer.length == 0) {
			 	return;
			 }
			for (var i = 0; i < answer.length; i++) {
				ans= answer[i];
				for (var key in ans.answerMap) {
					if (key == 'AdvQuestionList') {
						var questionId = ans.answerMap[key];
						if(questionId == question.value )
						{
							alert("Advanced Criteria for this question has been set. Please select another question.");
							$j($j("#questionAdvList").parent().parent()).find(":input").val("");
							return;
						}
					}
				}
			}
		});

	   }
}

function checkDuplicateAdvancedInvCriteria(question)
{ 
    if(!editedAdvInvCriteria){
		if(question.value != null || question.value != "")
		   {
			JDecisionSupport.getAllAdvancedInvCriteriaBatchAnswer("IdAdvancedInvSubSection",function(answer) {
				if (answer.length == 0) {
					return;
				 }
				for (var i = 0; i < answer.length; i++) {
					ans= answer[i];
					for (var key in ans.answerMap) {
						if (key == 'AdvInvQuestionList') {
							var questionId = ans.answerMap[key];
							if(questionId == question.value )
							{
								alert("Advanced Investigation Criteria for this question has been set. Please select another question.");
								$j($j("#questionAdvInvList").parent().parent()).find(":input").val("");
								return;
							}
						}
					}
				}
			});

		   }
	}
	if(editedAdvInvCriteria)
		editedAdvInvCriteria=false;
}


function ValidateElrAdvancedCriteriaBatchFields()
{
	var errorLabels = new Array();

	var testCodeIdNode = getElementByIdOrByName('testCodeId');
	var testCodeId = jQuery.trim(testCodeIdNode.value);

	if (testCodeId == 'null' || testCodeId == "")
	{
		errorLabels.push("Resulted Test is a required field.");
	}

	//var codedResultAdvListNode = getElementByIdOrByName('codedResultAdvList_textbox');
	var codedResultAdvListNode = getElementByIdOrByName('codeResultId');
	var codedResultAdvList = jQuery.trim(codedResultAdvListNode.value);

	var numericResultTextNode = getElementByIdOrByName('numericResult_text');
	var numericResult = jQuery.trim(numericResultTextNode.value);

	var textResultNode = getElementByIdOrByName('textResult_text');
	var textResult = jQuery.trim(textResultNode.value);

	var resultOperatorListNode = getElementByIdOrByName('resultOperatorList');
	var resultOperatorList = jQuery.trim(resultOperatorListNode.value);
	
	var numericResultOperatorListNode = getElementByIdOrByName('numericResultOperatorList');
	var numericResultOperatorList = jQuery.trim(numericResultOperatorListNode.value);
	
	var resultCount = 0;

	if(codedResultAdvList != "null" && codedResultAdvList != "")
	{
		resultCount++;
	}
	if(numericResult != "null" && numericResult != "")
	{
		resultCount++;
	}
	if(textResult != "null" && textResult != "")
	{
		resultCount++;
	}else if(resultOperatorList != "null" && resultOperatorList != "" && resultOperatorList.indexOf("NULL")>0)
	{
		resultCount++;
	}
	
	if(resultCount > 1)
	{
		errorLabels.push("Only one test Result can be chosen.");
	}
	else if(resultCount == 0)
	{
		errorLabels.push("One of the result value fields (Coded, Numeric, or Text) is required.");
	}

	var numericResultTypeTextNode = getElementByIdOrByName('numericResultTypeList_textbox');
	var numericResultType = jQuery.trim(numericResultTypeTextNode.value);
	var numericResultRE = /^(((\d){0,10})|((\d){1,10})([\.]{1})((\d){1,5}))\s*$/;

	
	
	if (numericResult == 'null' || numericResult == "")
	{
		if(numericResultType != 'null' && numericResultType != "")
			errorLabels.push("Numeric Result is a required field if Unit is not empty.");
	}
	else if (!numericResultRE.test(numericResult)) {
		errorLabels.push("Numeric Result can contain whole numbers (up to 10 digits), decimal numbers (up to 5 decimal points). Please correct the data and try again.");
	}

	var numericResultHighTextNode = getElementByIdOrByName('numericResultHigh_text');
	var numericResultHigh = jQuery.trim(numericResultHighTextNode.value);
	if(numericResultHigh != null || numericResultHigh!=""){
		if (!numericResultRE.test(numericResultHigh)) {
			errorLabels.push("Numeric Result can contain whole numbers (up to 10 digits), decimal numbers (up to 5 decimal points). Please correct the data and try again.");
		}
	}
	
	if(resultOperatorList == 'null' || resultOperatorList == ""){
		if(textResult != 'null' && textResult != "") {
			errorLabels.push("A text result requires an operator and a text value.");
			//dwr.util.setValue("resultOperatorList", "=");
			//dwr.util.setValue("resultOperatorList_textbox", "Equal");
		}
	}
	else if(textResult == 'null' || textResult == ""){
		if(resultOperatorList != 'null' && resultOperatorList != "" && resultOperatorList.indexOf("NULL")<0)
			errorLabels.push("A text result requires an operator and a text value.");
			//errorLabels.push("A text result requires a text value.");
	}
	
	//Validation to see if required value entered for numeric result
	if(numericResultOperatorList == 'null' || numericResultOperatorList == ""){
		if(numericResult != 'null' && numericResult != "") {
			errorLabels.push("A numeric result requires an operator and a numeric value.");
			//dwr.util.setValue("numericResultOperatorList", "=");
			//dwr.util.setValue("numericResultOperatorList_textbox", "Equal");
		}
	}
	else if(numericResult == 'null' || numericResult == ""){
			if(numericResultOperatorList != 'null' && numericResultOperatorList != "" && numericResultOperatorList.indexOf("NULL")<0)
				errorLabels.push("A numeric result requires an operator and a numeric value.");
				//errorLabels.push("A numeric result requires a numeric value.");
	}
	
	// Validation to see valid range entered if 'Between' operator selected
	if(numericResultOperatorList!=null && numericResultOperatorList == 'BET'){
		var numericResultHigh_textNode = getElementByIdOrByName('numericResultHigh_text');
		var numericResultHigh = jQuery.trim(numericResultHigh_textNode.value);
		if(numericResultHigh == 'null' || numericResultHigh == "" ){
			errorLabels.push("The numeric range you have entered is invalid.");
		}else if (numericResultHigh_textNode.value < numericResultTextNode.value){
			errorLabels.push("The numeric range you have entered is invalid.");
		}
	}
	
	if (errorLabels.length > 0) {
		displayErrors('ElrIdAdvancedSubSectionerrorMessages', errorLabels);

		return false;
	}
$j('#ElrIdAdvancedSubSectionerrorMessages').css("display", "none");
return true;
}


function ValidateAdvancedCriteriaBatchFields()
{
	// question, value and logic are required
	var errorLabels = new Array();
	var questionListTxtNode = getElementByIdOrByName('questionAdvList_textbox');
	var questionList = jQuery.trim(questionListTxtNode.value);
	if (questionList == 'null' || questionList == "")
		errorLabels.push("Question is a required field.");

	var logicTxtNode = getElementByIdOrByName('advLogicList_textbox');
	var logicTxt = jQuery.trim(logicTxtNode.value);
	if (logicTxt == 'null' || logicTxt == "")
		errorLabels.push("Logic is a required field.");

	var valueNode = "";
	var valueNodeVal= "";

	if(getElementByIdOrByName("advText").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advVal_text").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advVal_textArea").style.display != "none" ){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advVal_textArea").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advDate").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advVal_date").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
		else if(!checkdate(valueNodeVal))
			errorLabels.push("Date must be in the format of mm/dd/yyyy. Please correct the data and try again.");
	}else if(getElementByIdOrByName("advM_sel").style.display != "none"){
		var valueNode = getElementByIdOrByName('advValueList2');
		var showError="";
		valueNodeVal = valueNode.options.length;
		for (i=0; i < valueNodeVal; i++) {
			if(valueNode.options[i].value != ""){
				if (valueNode.options[i].selected == true){
					showError = "false";
				}
			}
		}
		if(showError != "false")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advS_sel").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advValueList1_textbox").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advNum_Coded").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advVal_num").value);
		codeNodeVal = jQuery.trim(getElementByIdOrByName("advVal_code_textbox").value);
		if (valueNodeVal == 'null' || valueNodeVal == "" || codeNodeVal == 'null' || codeNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advNum_Literal").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advVal_lit").value);
		var questionCode = getElementByIdOrByName('questionAdvList').value;
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
		else if(!isInteger(valueNodeVal) && !(questionCode == 'INV165') && !(questionCode == 'INV166'))
			errorLabels.push("Value need to be an Integer.");
		else if(questionCode == 'INV165' && (!isInteger(valueNodeVal) || !isWeek(valueNodeVal)))
			errorLabels.push("Value is not a valid MMWR Week.");
		else if(questionCode == 'INV166' && (!isInteger(valueNodeVal) || !isYear(valueNodeVal)))
			errorLabels.push("Value is not a valid MMWR Year.");
	}

	if (errorLabels.length > 0) {
		displayErrors('IdAdvancedSubSectionerrorMessages', errorLabels);

		return false;
	}
$j('#IdAdvancedSubSectionerrorMessages').css("display", "none");
return true;

}

function ValidateAdvancedInvCriteriaBatchFields()
{
	// question, value and logic are required
	var errorLabels = new Array();
	var questionListTxtNode = getElementByIdOrByName('questionAdvInvList_textbox');
	var questionList = jQuery.trim(questionListTxtNode.value);
	if (questionList == 'null' || questionList == "")
		errorLabels.push("Question is a required field.");

	var logicTxtNode = getElementByIdOrByName('advInvLogicList_textbox');
	var logicTxt = jQuery.trim(logicTxtNode.value);
	if (logicTxt == 'null' || logicTxt == "")
		errorLabels.push("Logic is a required field.");

	var valueNode = "";
	var valueNodeVal= "";

	if(getElementByIdOrByName("advInvText").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advInvVal_text").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advInvVal_textArea").style.display != "none" ){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advInvVal_textArea").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advInvDate").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advInvVal_date").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
		else if(!checkdate(valueNodeVal))
			errorLabels.push("Date must be in the format of mm/dd/yyyy. Please correct the data and try again.");
	}else if(getElementByIdOrByName("advInvM_sel").style.display != "none"){
		var valueNode = getElementByIdOrByName('advInvValueList2');
		var showError="";
		valueNodeVal = valueNode.options.length;
		for (i=0; i < valueNodeVal; i++) {
			if(valueNode.options[i].value != ""){
				if (valueNode.options[i].selected == true){
					showError = "false";
				}
			}
		}
		if(showError != "false")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advInvS_sel").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advInvValueList1_textbox").value);
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advInvNum_Coded").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advInvVal_num").value);
		codeNodeVal = jQuery.trim(getElementByIdOrByName("advInvVal_code_textbox").value);
		if (valueNodeVal == 'null' || valueNodeVal == "" || codeNodeVal == 'null' || codeNodeVal == "")
			errorLabels.push("Value is a required field.");
	}else if(getElementByIdOrByName("advInvNum_Literal").style.display != "none"){
		valueNodeVal = jQuery.trim(getElementByIdOrByName("advInvVal_lit").value);
		var questionCode = getElementByIdOrByName('questionAdvInvList').value;
		if (valueNodeVal == 'null' || valueNodeVal == "")
			errorLabels.push("Value is a required field.");
		else if(!isInteger(valueNodeVal) && !(questionCode == 'INV165') && !(questionCode == 'INV166'))
			errorLabels.push("Value need to be an Integer.");
		else if(questionCode == 'INV165' && (!isInteger(valueNodeVal) || !isWeek(valueNodeVal)))
			errorLabels.push("Value is not a valid MMWR Week.");
		else if(questionCode == 'INV166' && (!isInteger(valueNodeVal) || !isYear(valueNodeVal)))
			errorLabels.push("Value is not a valid MMWR Year.");
	}

	if (errorLabels.length > 0) {
		displayErrors('IdAdvancedInvSubSectionerrorMessages', errorLabels);

		return false;
	}
$j('#IdAdvancedInvSubSectionerrorMessages').css("display", "none");
return true;

}

function isYear(varVal)
{
	if (varVal < 1875) {
		return false;
	}

	var now = new Date();
	var currentYear = now.getFullYear();
	if (varVal > currentYear) {
		return false;
	}
	return true;
}

function isWeek(varVal)
{
	if (varVal < 1) {
		return false;
	}
	if (varVal > 53) {
		return false;
	}
	return true;
}

function isInteger(pString)
{
    //  Establish a pattern:  one or more digits.
    var varPattern = /^\d+$/;
    //  Perform a regular expression match.
    var varMatch = pString.match(varPattern);
    if(varMatch == null)
    {
        //  The match failed.
        //  The string is not numeric.
        return false;
    }
    //  The match succeeded.
    //  The string is numeric.
    return true;
}

function checkdate(input){
	var validformat=/^\d{2}\/\d{2}\/\d{4}$/ //Basic check for format validity
	if (!validformat.test(input))
		return false;
	else
		return true;
}



function validateBatchData(){
	var eventType = getElementByIdOrByName("EVENT_TY").value;
   	var flag = true;
   	if(!validateActionBatchData()){
   		flag = false;
   	}
   	if(eventType=='PHC236')
   	{
	   	if(!validateAdvanceCriteriaBatchData()){
	   		flag = false;
	   	}
   	}else if(eventType=='11648804')
	{
   		if(!validateElrAdvanceCriteriaBatchData()){
	   		flag = false;
	   	}
   		if(!validateElrAdvanceInvCriteriaBatchData()){
	   		flag = false;
	   	}
	}

   	return flag;
}

function validateAdvanceCriteriaBatchData(){
	var flag = true;
	var errorLabels = new Array();
	var advVal_num = getElementByIdOrByName('advVal_num').value;
	var advVal_code = getElementByIdOrByName('advVal_code').value;
	var advVal_lit = getElementByIdOrByName('advVal_lit').value;
	var advVal_text = getElementByIdOrByName('advVal_text').value;
	var advVal_textArea = getElementByIdOrByName('advVal_textArea').value;
	var advValueList1 = getElementByIdOrByName('advValueList1').value;
	var advValueList2 = getElementByIdOrByName('advValueList2').value;
	var questionAdvList = getElementByIdOrByName('questionAdvList').value;
    var advLogicList  = getElementByIdOrByName('advLogicList').value;
    var advVal_date = getElementByIdOrByName('advVal_date').value;
    if (((questionAdvList != null && questionAdvList != "")
    		|| (advLogicList != null && advLogicList != "")
    		|| (advVal_date != null && advVal_date != "")
    		|| (advVal_text != null && advVal_text != "")
    		|| (advVal_textArea != null && advVal_textArea != "")
    		|| (advValueList1 != null && advValueList1 != "")
    		|| (advValueList2 != null && advValueList2 != "")
    		|| (advVal_num != null && advVal_num != "")
    		|| (advVal_code != null && advVal_code != "")
    		|| (advVal_lit != null && advVal_lit != "")
    	)&& !isVisible(getElementByIdOrByName("AddNewButtonToggleIdAdvancedSubSection")))
 	{
    	errorLabels.push("Data has been entered in the Advanced Criteria section. Please press Add  or clear the data and submit again.");
    	if (errorLabels.length > 0) {
    		displayErrors('IdAdvancedSubSectionerrorMessages', errorLabels);
    		var tabElts = new Array();
    		var parentTab = getItsParentTab('IdAdvancedSubSectionerrorMessages');
    		tabElts.push(parentTab);
    		setErrorTabProperties(tabElts);
    		flag = false;
    	}else{
	      $j('#IdAdvancedSubSectionerrorMessages').css("display", "none");
	   }
 	}else{
      $j('#IdAdvancedSubSectionerrorMessages').css("display", "none");
    }

 	return flag;
}

function validateElrAdvanceCriteriaBatchData(){
	var flag = true;
	var errorLabels = new Array();
	var codedResultAdvList = getElementByIdOrByName('resultDescription').innerText;
	//var codedResultAdvList = getElementByIdOrByName("codedResultAdvList").value;
	var numericResult_text = getElementByIdOrByName("numericResult_text").value;
	var numericResultTypeList = getElementByIdOrByName("numericResultTypeList").value;
	var textResult_text = getElementByIdOrByName("textResult_text").value;
	var resultOperatorList = getElementByIdOrByName('resultOperatorList').value;
	var testDescription = getElementByIdOrByName("testDescription").innerText;
	var numericResultOperatorList = getElementByIdOrByName('numericResultOperatorList').value;

    if (((codedResultAdvList != null && codedResultAdvList != "")
    		|| (numericResult_text != null && numericResult_text != "")
    		|| (numericResultTypeList != null && numericResultTypeList != "")
    		|| (testDescription != null && testDescription != "")
    		|| (getElementByIdOrByName("numericResultOperatorList_textbox").value != "")
    		|| (getElementByIdOrByName("resultOperatorList_textbox").value != "")
    		|| (getElementByIdOrByName("textResult_text").value != ""))
    		&& !isVisible(getElementByIdOrByName("AddNewButtonToggleIdELRAdvancedSubSection"))
    	)
 	{
    	errorLabels.push("Data has been entered in the Lab Criteria section. Please press Add or clear the data and submit again.");
    	if (errorLabels.length > 0) {
    		displayErrors('ElrIdAdvancedSubSectionerrorMessages', errorLabels);
    		var tabElts = new Array();
    		var parentTab = getItsParentTab('ElrIdAdvancedSubSectionerrorMessages');
    		tabElts.push(parentTab);
    		setErrorTabProperties(tabElts);
    		flag = false;
    	}else{
	      $j('#ElrIdAdvancedSubSectionerrorMessages').css("display", "none");
	   }
 	}else{
      $j('#ElrIdAdvancedSubSectionerrorMessages').css("display", "none");
    }

 	return flag;
}

function validateElrAdvanceInvCriteriaBatchData(){
	var flag = true;
	var errorLabels = new Array();
	var advInvVal_num = getElementByIdOrByName('advInvVal_num').value;
	var advInvVal_code = getElementByIdOrByName('advInvVal_code').value;
	var advInvVal_lit = getElementByIdOrByName('advInvVal_lit').value;
	var advInvVal_text = getElementByIdOrByName('advInvVal_text').value;
	var advInvVal_textArea = getElementByIdOrByName('advInvVal_textArea').value;
	var advInvValueList1 = getElementByIdOrByName('advInvValueList1').value;
	var advInvValueList2 = getElementByIdOrByName('advInvValueList2').value;
	var questionAdvInvList = getElementByIdOrByName('questionAdvInvList').value;
    var advInvLogicList  = getElementByIdOrByName('advInvLogicList').value;
    var advInvVal_date = getElementByIdOrByName('advInvVal_date').value;
    if (((questionAdvInvList != null && questionAdvInvList != "")
    		|| (advInvLogicList != null && advInvLogicList != "")
    		|| (advInvVal_date != null && advInvVal_date != "")
    		|| (advInvVal_text != null && advInvVal_text != "")
    		|| (advInvVal_textArea != null && advInvVal_textArea != "")
    		|| (advInvValueList1 != null && advInvValueList1 != "")
    		|| (advInvValueList2 != null && advInvValueList2 != "")
    		|| (advInvVal_num != null && advInvVal_num != "")
    		|| (advInvVal_code != null && advInvVal_code != "")
    		|| (advInvVal_lit != null && advInvVal_lit != "")
    	)&& !isVisible(getElementByIdOrByName("AddNewButtonToggleIdAdvancedInvSubSection")))
 	{
    	errorLabels.push("Data has been entered in the Advanced Investigation Criteria section. Please press Add  or clear the data and submit again.");
    	if (errorLabels.length > 0) {
    		displayErrors('IdAdvancedInvSubSectionerrorMessages', errorLabels);
    		var tabElts = new Array();
    		var parentTab = getItsParentTab('IdAdvancedInvSubSectionerrorMessages');
    		tabElts.push(parentTab);
    		setErrorTabProperties(tabElts);
    		flag = false;
    	}else{
	      $j('#IdAdvancedInvSubSectionerrorMessages').css("display", "none");
	   }
 	}else{
      $j('#IdAdvancedInvSubSectionerrorMessages').css("display", "none");
    }

 	return flag;
}

function isVisible(elem)
{
	if(elem != null)
	{
		if (elem.offsetWidth <= 0 && elem.offsetHeight <= 0)
			return false;
		if (elem.style.display == "none")
			return false;
	}
	return true;
}

function getItsParentTab(containerId)
{
    // get the parent tab by its class name
    var parentTab = $j("#"+containerId).parents(activeTabClass).get(0);
    if (parentTab == null || parentTab == undefined) {
        parentTab = $j("#"+containerId).parents(hiddenTabClass).get(0);
    }

    if (parentTab == null || parentTab == undefined) {
        return null;
    }
    else {
        return parentTab;
    }
}

function validateActionBatchData(){
	var flag = true;
	var errorLabels = new Array();
	var Val_text = getElementByIdOrByName('Val_text').value;
	var Val_textArea = getElementByIdOrByName('Val_textArea').value;
	var valueList1 = getElementByIdOrByName('valueList1').value;
	var valueList2 = getElementByIdOrByName('valueList2').value;
	var Val_num = getElementByIdOrByName('Val_num').value;
	var Val_code = getElementByIdOrByName('Val_code').value;
	var Val_lit = getElementByIdOrByName('Val_lit').value;

	var questionList = getElementByIdOrByName('questionList').value;
    var Val_date  = getElementByIdOrByName('Val_date').value;
    var behavior = getElementByIdOrByName('behavior').value;
    if (((questionList != null && questionList != "")
    		|| (Val_date != null && Val_date != "")
    		|| (behavior != null && behavior != "")
    		|| (Val_text != null && Val_text != "")
    		|| (Val_textArea != null && Val_textArea != "")
    		|| (valueList1 != null && valueList1 != "")
    		|| (valueList2 != null && valueList2 != "")
    		|| (Val_num != null && Val_num != "")
    		|| (Val_code != null && Val_code != "")
    		|| (Val_lit != null && Val_lit != "")
    		)&& !isVisible(getElementByIdOrByName("AddNewButtonToggleIdSubSection")))
 	{
    	errorLabels.push("Data has been entered in the Investigation Default Values section. Please press Add  or clear the data and submit again.");
    	if (errorLabels.length > 0) {
    		displayErrors('IdSubSectionerrorMessages', errorLabels);
    		var tabElts = new Array();
    		var parentTab = getItsParentTab('IdSubSectionerrorMessages');
    		tabElts.push(parentTab);
    		setErrorTabProperties(tabElts);
    		flag = false;
    	}else{
	      $j('#IdSubSectionerrorMessages').css("display", "none");
	   }
 	}else{
      $j('#IdSubSectionerrorMessages').css("display", "none");
    }

 	return flag;
}

function clearPartPerson() {
    	     $j("#PartPerSearchControls").children().show();
    	     dwr.util.setValue("PartPer", "");
	     dwr.util.setValue("PartPerUid", "");
	     $j("#PartPerS").children().hide();
	     $j("#clearPartPer").children().hide();
}


function getDwrPartPerson(identifier) {

 dwr.util.setValue(identifier, "");
 var code = $(identifier+"Text");
 var codeValue= code.value;

 var entityType = $j("#PartEntityType").val();
 if (entityType == "PartPer") {

  	JDecisionSupport.getDwrInvestigatorDetails(codeValue, identifier, function(data) {
       dwr.util.setEscapeHtml(false);

       if(data.indexOf('$$$$$') != -1) {
	         var code = $(identifier+"Text");
	         code.value = "";
	         dwr.util.setValue(identifier, "");
	         dwr.util.setValue(identifier+"Error", "");


	     $j("#PartPerSearchControls").children().hide();
	     dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$')+5));
	     dwr.util.setValue(identifier+"Uid", data.substring(0,data.indexOf('$$$$$')));

 	    $j("#PartPerS").children().show();
	        //getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
	        //getElementByIdOrByName(identifier+"Icon").style.visibility="hidden";
	        //getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
	        //getElementByIdOrByName("clear"+identifier).className="";
	        //getElementByIdOrByName(identifier+"SearchControls").className="none";
	     $j("#PartPerCodeClearButton").val("Clear/Reassign");
	     $j("#PartPerCodeClearButton").css("color", "#000");
	     $j("#PartPerCodeClearButton").attr("disabled", false);
	     $j("#clearPartPer").children().show();


       } else {
           dwr.util.setValue(identifier+"Error", data);
            getElementByIdOrByName(identifier+"Text").style.visibility="visible";
            getElementByIdOrByName(identifier+"Icon").style.visibility="visible";
            getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
            getElementByIdOrByName("clear"+identifier).className="none";
            getElementByIdOrByName(identifier+"SearchControls").className="visible";
       }
    });

    } else if (entityType == "PartOrg") {
  	JDecisionSupport.getDwrOrganizationDetails(codeValue, identifier, function(data) {
       dwr.util.setEscapeHtml(false);

       if(data.indexOf('$$$$$') != -1) {
	         var code = $(identifier+"Text");
	         code.value = "";
	         dwr.util.setValue(identifier, "");
	         dwr.util.setValue(identifier+"Error", "");


	     $j("#PartPerSearchControls").children().hide();
	     dwr.util.setValue(identifier, data.substring(data.indexOf('$$$$$')+5));
	     dwr.util.setValue(identifier+"Uid", data.substring(0,data.indexOf('$$$$$')));

 	    $j("#PartPerS").children().show();
	        //getElementByIdOrByName(identifier+"Text").style.visibility="hidden";
	        //getElementByIdOrByName(identifier+"Icon").style.visibility="hidden";
	        //getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="hidden";
	        //getElementByIdOrByName("clear"+identifier).className="";

	        //getElementByIdOrByName(identifier+"SearchControls").className="none";
	     $j("#PartPerCodeClearButton").val("Clear/Reassign");
	     $j("#PartPerCodeClearButton").css("color", "#000");
	     $j("#PartPerCodeClearButton").attr("disabled", false);
	     $j("#clearPartPer").children().show();


       } else {
           dwr.util.setValue(identifier+"Error", data);
            getElementByIdOrByName(identifier+"Text").style.visibility="visible";
            getElementByIdOrByName(identifier+"Icon").style.visibility="visible";
            getElementByIdOrByName(identifier+"CodeLookupButton").style.visibility="visible";
            getElementByIdOrByName("clear"+identifier).className="none";
            getElementByIdOrByName(identifier+"SearchControls").className="visible";
       }
    });

    }
}
function getDwrEntitySearch() {
  var identifier=$j("#PartEntityType").val();
  if($j("#PartEntityType").val()=="PartPer"){
	var urlToOpen = "/nbs/Provider.do?method=searchLoad&identifier=PartPer";
	var params="left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var o = new Object();
	o.opener = self;						    
	var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " +
		            700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
            (true ? "resizable: yes;" : "");

	var modWin = openWindow(urlToOpen, o,dialogFeatures, null, "");

  }
  if($j("#PartEntityType").val()=="PartOrg"){
	  var urlToOpen = "/nbs/PamOrganization.do?method=searchOrganization&identifier=PartPer";
	var params="left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
	var o = new Object();
	o.opener = self;
	var dialogFeatures = "dialogWidth: " + 760 + "px;dialogHeight: " +
		            700 + "px;status: no;unadorned: yes;scroll: yes;help: no;" +
         (true ? "resizable: yes;" : "");

	var modWin = openWindow(urlToOpen, o,dialogFeatures, null, "");
  }
}
function selectCondition(){
  JDecisionSupport.getMultiselectValues("PublishedCondition",function(data){
		var selectCondList = "";
		if(data!=null){
			for (var j = 0; j < data.length; j++){
				var multiselectValueNode = getElementByIdOrByName('PublishedCondition');
				var multiselectNodeVal = multiselectValueNode.options.length;
				if(multiselectNodeVal!=null){
					for(var i=0; i < multiselectNodeVal; i++){
						if(multiselectValueNode.options[i].value ==  data[j])
						{
							multiselectValueNode.options[i].selected = true;
						}
					}
				}
			}
		}
		autocompTxtValuesForJSPByElement("PublishedCondition");
	 });
}