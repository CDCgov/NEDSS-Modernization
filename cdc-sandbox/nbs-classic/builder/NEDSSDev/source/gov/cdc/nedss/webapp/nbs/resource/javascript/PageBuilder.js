/**
 * PageBuilder class
*/

	var mapJson = new Object();
	
function PageBuilder(canvasElt)
{
    fbRef = this;
    this.canvas = canvasElt;
    this.nbsPageElementCssClass = "nbsPageElementCssClass";
    this.publishedEltClass = "published";
    this.standardizedEltClass = "standardized";
    this.notCaseAnswerLocEltClass = "notNbsCaseAnswer";

    
    this.activeContainerElt;
    this.activeClickedElt;
    this.activeClickedEltType;
    this.latestActionRequested;
    this.dragedFieldElement;
    
    this.setActiveContainerElt = function(elt) {
        fbRef.activeContainerElt = elt;
    }
    
    this.getActiveContainerElt = function() {
        return fbRef.activeContainerElt;
    }
    
    this.setActiveClickedElt = function(elt) {
        fbRef.activeClickedElt = elt;
    }
    
    this.getActiveClickedElt = function() {
        return fbRef.activeClickedElt;
    }
    
    this.setActiveClickedEltType = function(eltType) {
        fbRef.activeClickedEltType = eltType;
    }
    
    this.getActiveClickedEltType = function() {
        return fbRef.activeClickedEltType;
    }
    
    this.setLatestActionRequested = function(action) {
        fbRef.latestActionRequested = action;
    }
    
    this.getLatestActionRequested = function() {
        return fbRef.latestActionRequested;
    }
    
    this.setDragedFieldElement = function(elt) {
        fbRef.dragedFieldElement = elt;
    }
    
    this.getDragedFieldElement = function() {
        return fbRef.dragedFieldElement;
    }
    

    /**
     * Responsible for the following actions:
     * 1. Retrieve a JSON string (via DWR call) representing all the elements in this page.
     * 2. Rendering the initial DOM using the JSON object retrieved.
     */
    this.initialize = function() {
        //Load the xml and return JSon Object
        JPageBuilder.retrieveCurrentPage(function(data){
            if (data == null || jQuery.trim(data).length == 0) {
                alert("No page elements to intialize...");
            }
            else {
                $j(this.canvas).addClass("canvas");
                
                // initialize the UI based on the element type
                var currentElt = null, previousElt = null;
                var previousEltType = "", currentEltType = "";
                //var previousEltPublishInd = "";
                var batchUid = "";
                var jsonObj = eval('(' + data + ')');
                var tabIndex = 0;
                for (var i in jsonObj) {
                    currentElt = null;
		            if (jsonObj[i].elementType != undefined) {
						
						mapJson[jsonObj[i].questionIdentifier]=jsonObj[i];
						/*if((jsonObj[i].elementType).toLowerCase()=='action button'){
							alert("Here");
						}*/
						
                        switch ((jsonObj[i].elementType).toLowerCase()) {
                            case 'page':
                                currentElt = getPage(jsonObj[i]);
                                currentEltType = jsonObj[i].elementType;
                                $j($j(this.canvas).find("div#pageContainer").get(0)).append(currentElt);
                                break;

                            case 'tab':
                                var tabHandle = null, tabBody = null;
                                if (tabIndex == 0) {
                                    tabHandle = getTabHandle(jsonObj[i], true);
                                    tabBody = getTabBody(jsonObj[i], true);
                                    tabIndex++;
                                }
                                else {
                                    tabHandle = getTabHandle(jsonObj[i], false);
                                    tabBody = getTabBody(jsonObj[i], false);
                                }
                                currentElt = tabBody; 
                                
                                // insert the items in appropriate place
                                currentEltType = jsonObj[i].elementType;
                                $j($j(this.canvas).find("ul.ui-tabs-nav").get(0)).append(tabHandle);
                                $j($j(this.canvas).find("div#tabContainer").get(0)).append(tabBody);
                                
                                break;
                            
                            case 'section':
                                if (previousElt != null) {
                                    currentElt = getSection(jsonObj[i]);
                                    currentEltType = jsonObj[i].elementType;
                                    
                                    if (previousEltType == 'tab') {
                                        $j($j(previousElt).find("ul.sortableSections").get(0)).append(currentElt);
                                    }
                                    else {
                                        $j($j($j(previousElt).parents("ul.sortableSections")).get(0)).append(currentElt);
                                    }
                                }
                                
                                break;
                            
                            case 'subsection':
                                if (previousElt != null) {
                                    currentElt = getSubSection(jsonObj[i]);
                                    currentEltType = jsonObj[i].elementType;
                                    batchUid = jsonObj[i].questionGroupSeqNbr;
                                    var currentEtlId = 	jsonObj[i].pageElementUid;
                                    
                                    if (previousEltType == 'section') {
                                        $j($j(previousElt).find("ul.sortableSubSections").get(0)).append(currentElt);
                                    }
                                    else {
                                        $j($j($j(previousElt).parents("ul.sortableSubSections")).get(0)).append(currentElt);
                                    }
                                                                       
                                    if(batchUid != null){
                                    	
                                    	$j($j($j("#" + jsonObj[i].pageElementUid).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper").append('<a class="repeatingBlockLink" href="javascript:void(0)"><img alt="Repeating SubSection" title="Repeating SubSection" src="RepeatingBlock.gif"/>&nbsp;</a>&nbsp;');
                                   	    $j($j(currentElt).find("UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'parent');
                                   	    $j($j(currentElt).find("UL.sortableFieldRows").get(0)).addClass("batchonly");
                                   	    $j($j(currentElt).find("UL.sortableFieldRows").get(0)).removeClass("nonbatch");
                                        //$j($j(currentElt).find("UL.sortableFieldRows").get(0)).css("background","#BBFFA2");
                                   	    $j($j($j("#" + jsonObj[i].pageElementUid).parent()).find("div.subSection").get(0)).css("background","#EEF7BE");
                                   	    $j($j($j("#" + jsonObj[i].pageElementUid).parent()).find("div.subSection").get(0)).css("border","1px solid #699F09");
                                   	    $j($j(currentElt).find("UL.sortableFieldRows").get(0)).css("background","#DCE7F7");
                                        $j($j(currentElt).find("a.groupFieldsLink").get(0)).attr("title", "Un-Group all Fields Rows in SubSection");;
                                        $j($j($j(currentElt).find("a.groupFieldsLink").get(0)).find("img").get(0)).attr("src", "link_break.gif").attr("alt", "Un-Group all Fields Rows in SubSection").attr("title", "Un-Group all Fields Rows in SubSection");
                                   }else
                                   {
                                	   //$j($j($j(currentElt).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper a.repeatingBlockLink").remove();
                                   	   $j($j(currentElt).find("UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
                                       $j($j(currentElt).find("UL.sortableFieldRows").get(0)).css("background","#DCE7F7");
                                       $j($j(currentElt).find("UL.sortableFieldRows").get(0)).addClass("nonbatch");
                                       $j($j(currentElt).find("a.groupFieldsLink").get(0)).attr("title", "Group all Fields Rows in SubSection");;
                                       $j($j($j(currentElt).find("a.groupFieldsLink").get(0)).find("img").get(0)).attr("src", "link.gif").attr("alt", "Group all Fields Rows in SubSection").attr("title", "Group all Fields Rows in SubSection");;
                                   }
                                }
                                
                                //If a subsection has no question, it should not get the group link.
                                // If current subsection's previous element type is a subsection, that means the previous Subsection has no question attached to it
                                
                                break;
                            
                            case 'question': // synonymous to fieldRow
                            case 'comment': // synonymous to fieldRow
                            case 'hyperlink': // synonymous to fieldRow
                            case 'line separator': // synonymous to fieldRow
                            case 'participant list': // synonymous to fieldRow
                            case 'patient search': // synonymous to fieldRow
                            case 'action button': // synonymous to fieldRow
                            case 'set values button': // synonymous to fieldRow
                            case 'original electronic document list':// synonymous to fieldRow
                            	
                                if (previousElt != null) {
                                    currentElt = getFieldRow(jsonObj[i]);
                                                                   	
                                    // show the link field icon
                                    currentEltType = jsonObj[i].elementType;
                                    if (previousEltType.toLowerCase() == 'subsection') {
                                        $j($j(previousElt).find("ul.sortableFieldRows").get(0)).append(currentElt);
                                        $j($j($j($j(previousElt).get(0)).find("div.header div.controls a.groupFieldsLink")).get(0)).removeClass("noDisplay");

                                    }
                                    else {
                                        $j($j($j(previousElt).parents("ul.sortableFieldRows")).get(0)).append(currentElt);
                                    }
                                }
                                break;
                            
                        } // switch
                        previousElt = currentElt;
                        previousEltType = currentEltType.toLowerCase();
                        //previousEltPublishInd = currentEltType.isPublished;
		            } // if
		        } // for
		        
		        // attach sort property to tabs
		        $j($j("#tabContainer").find(".ui-tabs-nav").get(0)).sortable({axis:'x'});
            }
            
            // unblock the overlay after loading the page completely.
            handleDoubleBlockBackButton();
        });
    }
    

    /**
     * handleDoubleBlockBackButton: unblocking from page builder needs to be handled in a special way since back button feedback message has been introduced.
     * If the user clicks back button, and the page is unblocked, it will also unblock the blocked page related to the back button feedback message.
     * This is related to the defect ND-24480
     */
    function handleDoubleBlockBackButton(){
    	
    	if($j("#avoidBackButton :visible")==undefined || $j("#avoidBackButton :visible")==null || $j("#avoidBackButton :visible").length==0){
    		 setTimeout("$j.unblockUI()",3000);
    	}
    	else
    		{
    		if($j("#avoidBackButton :visible")!=undefined && $j("#avoidBackButton :visible")!=null && $j("#avoidBackButton :visible").length>0){
    			 setTimeout("$j.unblockUI(); var element = getElementFromPageJspOrLegacy();element.block({ message: null });",3000);    			
    			}
    		}
    	
    }
    
    /**
     * Save the page as a draft. Involves a DWR call that passes 
     * the current page elements order.
     */
    this.savePageAsDraft = function() {
        var eltsOrder = fbRef.getPageElementsOrder();
        JPageBuilder.savePageAsDraft(eltsOrder, function(data) {
            if (data != null) {
                displayStatusMsg(data);
            }
        });
    }

    /** Display the add element dialog. */
    this.showAddElementDialog = function(containerElt, eltTypeToAdd) {
        fbRef.setActiveContainerElt(containerElt);
        fbRef.setActiveClickedEltType(eltTypeToAdd);
        fbRef.setLatestActionRequested("add");
        var body = document.body;
        var divElt = getElementByIdOrByName("parentWindowDiv");
        divElt.style.display = "block";
        divElt.style.height = body.scrollHeight+"px";
  
        var URL = "/nbs/ManagePageElement.do?method=addLoad&eltType=" + eltTypeToAdd;
        if (eltTypeToAdd.toLowerCase() == "tab" || 
                eltTypeToAdd.toLowerCase() == "section" ||  
                eltTypeToAdd.toLowerCase() == "subsection") {
            displayModalDialog(URL, 300);    
        }
        else {
            displayModalDialog(URL, 600);
        }
    }
    
    /** Display the view element dialog. */
    this.showViewElementDialog = function(elt, eltTypeToView, eltSubType) {
        fbRef.setActiveClickedEltType(eltTypeToView);
        fbRef.setActiveClickedElt(elt);
        fbRef.setLatestActionRequested("view");
        
        var pageEltId = $j(elt).attr("id");
		var body = document.body;
        var divElt = getElementByIdOrByName("parentWindowDiv");
        divElt.style.display = "block";
        divElt.style.height = body.scrollHeight+"px";

        // show the popup window  
        var URL = "/nbs/ManagePageElement.do?method=viewLoad&eltType=" + eltTypeToView + "&pageElementUid=" + pageEltId;
        if (eltSubType != null && eltSubType != undefined && eltSubType == "question"){
            displayModalDialog(URL, 600);
        }
        else {
            displayModalDialog(URL, 300);
        }
    }
    
    /** Display the edit element dialog. */
    this.showEditElementDialog = function(elt, eltTypeToEdit, eltSubType) {
        fbRef.setActiveClickedEltType(eltTypeToEdit);
        fbRef.setActiveClickedElt(elt);
        fbRef.setLatestActionRequested("edit");
        
        var pageEltId = $j(elt).attr("id");
		var body = document.body;
        var divElt = getElementByIdOrByName("parentWindowDiv");
        divElt.style.display = "block";
        divElt.style.height = body.scrollHeight+"px";
        var URL ="";
        if(eltSubType != null && eltSubType != undefined && eltSubType.indexOf("batch")>= 0){
        	URL = "/nbs/ManagePageElement.do?method=editLoad&eltType=" + eltTypeToEdit + "&pageElementUid=" + pageEltId+ "&batch=" + eltSubType;
        }
        else{
        	URL = "/nbs/ManagePageElement.do?method=editLoad&eltType=" + eltTypeToEdit + "&pageElementUid=" + pageEltId;
        }
        
        if (eltSubType != null && eltSubType != undefined && eltSubType == "question"){
            displayModalDialog(URL, 600);
        }
        else if(eltSubType != null && eltSubType != undefined && eltSubType == "batch"){
        	displayModalDialog(URL, 600);
        }
        else {
            displayModalDialog(URL, 450);
        }
        
    }
    
   
    
    /** Delete an element. It involves:
     *  1. A DWR call to remove the element from the session object
     *  2. UI update to reflect the change.        
     */
    this.deleteElement = function(containerElt, eltType) {
        var childEltsCount = $j(containerElt).find("." + fbRef.nbsPageElementCssClass).length;
        
        if (childEltsCount > 0) {
            var msg = "The " + getDisplayName(eltType) +
                    " you are trying to delete contains other " + 
                    "elements within it. Please remove the child elements before deleting the " + 
                    getDisplayName(eltType) + "."; 
            alert(msg);
        }
        else if(eltType == "fieldRow"){
        	var csv = "";
        	//alert( $j($j($j($j($j(containerElt).parent()).parent()).parent()).parent()).parent().html());
        	var parentElt =$j($j($j($j($j(containerElt).parent()).parent()).parent()).parent()).parent();
        	var childEltsCount = $j(parentElt).find("." + fbRef.nbsPageElementCssClass).length;
        	
        	var eltId = $j(containerElt).attr("id");
        	eltLabel = getElementLabel(eltId, eltType);
        	JPageBuilder.checkRulesAccociation(eltId, function(data){
        		if (data != null) {
        			 var msg = "This element is currently referenced in " +
        			 		"the following business rule(s): " +
        			 		data +"."+
        			 		" The element must be removed from all business rules " +
        			 		"before it can be removed from a page." 
        			 
        			 alert(msg);
        		}else
        		{
        			 eltLabel = getElementLabel(eltId, eltType);
        			 var msg = "You have indicated that you would like to delete " +
                     getDisplayName(eltType) + ": " + eltLabel +  
                     " from the page. Select OK to continue or Cancel to " + 
                     "return to Edit Page.";
        			 var choice = confirm(msg);
        			 if (choice) {
        				 JPageBuilder.deletePageElement(eltId, function(data){
        					 if (data == "deleteSuccess") 
        						 $j(containerElt).parent("li").remove();  
        				 });
        				 /**This is for batch entry, if the subsection has all the elements deleted, than the subsection
        				  will be automatically delete and repeating block icon will no longer appear in the subsection*/
        				
        				 if(childEltsCount == 2){
        					 $j($j($j($j(parentElt).get(0)).find("div.header div.controls a.groupFieldsLink")).get(0)).addClass("noDisplay");
        					 
        					 $j($j($j($j(parentElt).get(0)).find("div.header div.controls a.groupFieldsLink")).get(0)).attr("title", "Group all Fields Rows in SubSection");
        					 $j($j($j($j($j(parentElt).get(0)).find("div.header div.controls a.groupFieldsLink")).get(0)).find("img").get(0)).attr("src", "link.gif").attr("alt", "Group all Fields Rows in SubSection").attr("title", "Group all Fields Rows in SubSection");;
        					 $j($j(parentElt).find("UL.sortableFieldRows").get(0)).removeClass("batchonly");
        					 $j($j(parentElt).find("UL.sortableFieldRows").get(0)).addClass("nonbatch");
        					  
        					 $j($j($j($j(parentElt).get(0)).find("div.subSection")).get(0)).css("background","#DCE7F7 none repeat scroll 0 0");
        					 $j($j($j($j(parentElt).get(0)).find("div.subSection")).get(0)).css("border","1px solid #BACEEE");
        					 $j($j($j($j(parentElt).get(0)).find("div.subSection div.header")).get(0)).find("div.content span.ieVerticalAlignHelper a.repeatingBlockLink").remove();
        					 if (childEltsCount> 0) {
        						 for (var i = 0; i < childEltsCount-1; i++) {
     			                	var fieldRowElt = $j($j(containerElt).parent()).find(".fieldRow").get(i)
									var fieldRowEltId = $j(fieldRowElt).attr("id");
     			                	csv += fieldRowEltId + ",";
     			                }
        			        } 
     			            csv += $j($j(parentElt).find(".subSection").get(0)).attr("id");
     			            JPageBuilder.updateBatchUidForUnBatch(csv, function(data){});
        				 }
        				 displayStatusMsg(getDisplayName(eltType) + " was deleted successfully.");
        			 }
        		}
        	});
        } else if(eltType == "subSection"){
        	
            	var eltId = $j(containerElt).attr("id");
            	// Check if any rule is attached to it.
            	JPageBuilder.checkRulesAccociation(eltId, function(data){
            		if (data != null) {
            			var msg = "This element is currently referenced in " +
        		 		"the following business rule(s): " +
        		 		data +"."+
        		 		" The element must be removed from all business rules " +
        		 		"before it can be removed from a page." 
        		 
        		 		alert(msg);
            		}else{
                        var eltId = $j(containerElt).attr("id");
                        var eltLabel = "";
                        if (eltType.toLowerCase() == "tab") {
                            eltLabel = $j($j("li.ui-tabs-selected").find("a").get(0)).html();
                        }
                        var msg = "You have indicated that you would like to delete " +
                                getDisplayName(eltType) + ": " + eltLabel +  
                                " from the page. Select OK to continue or Cancel to " + 
                                "return to Edit Page.";
                        var choice = confirm(msg);
                        if (choice) {
                            JPageBuilder.deletePageElement(eltId, function(data){
                                if (data == "deleteSuccess") {
                                    // handle tab element type
                                    if (eltType == "tab") {
                                       $j(containerElt).remove();
                                        // remove corresponding tab handle
                                        $j("li.ui-tabs-selected").remove();
                                        
                                        // show the first tab in this container
                                        $j($j($j(fbRef.canvas).find("ul.ui-tabs-nav").get(0)).find("li").get(0)).addClass("ui-tabs-selected");
                                        $j($j($j(fbRef.canvas).find("div#tabContainer").get(0)).find("." + fbRef.nbsPageElementCssClass).get(0)).removeClass("ui-tabs-hide").addClass("ui-tabs-panel");
                                    }
                                    else {
                                        $j(containerElt).parent("li").remove();                        
                                    }
                                }
                            });
                            displayStatusMsg(getDisplayName(eltType) + " was deleted successfully.");
                        }
            		}
            	});
            }
        else {
            var eltId = $j(containerElt).attr("id");
            var eltLabel = "";
            if (eltType.toLowerCase() == "tab") {
                eltLabel = $j($j("li.ui-tabs-selected").find("a").get(0)).html();
            }
            
            var msg = "You have indicated that you would like to delete " +
                    getDisplayName(eltType) + ": " + eltLabel +  
                    " from the page. Select OK to continue or Cancel to " + 
                    "return to Edit Page.";
            var choice = confirm(msg);
            if (choice) {
                JPageBuilder.deletePageElement(eltId, function(data){
                    if (data == "deleteSuccess") {
                        // handle tab element type
                        if (eltType == "tab") {
                           $j(containerElt).remove();
                            // remove corresponding tab handle
                            $j("li.ui-tabs-selected").remove();
                            
                            // show the first tab in this container
                            $j($j($j(fbRef.canvas).find("ul.ui-tabs-nav").get(0)).find("li").get(0)).addClass("ui-tabs-selected");
                            $j($j($j(fbRef.canvas).find("div#tabContainer").get(0)).find("." + fbRef.nbsPageElementCssClass).get(0)).removeClass("ui-tabs-hide").addClass("ui-tabs-panel");
                        }
                        else {
                            $j(containerElt).parent("li").remove();                        
                        }

                    }
                });
                
                displayStatusMsg(getDisplayName(eltType) + " was deleted successfully.");
            }
        }
    }
     
	 /**
     * checkNotesComponent: it returns true if the element with that questionIdentifier is of type 1019 (Multi-line Notes with User/Date Stamp)
     */
	 
	 function checkNotesComponent(questionIdentifier){
		 
		 var isNotesComponent = false;
		 
		 if(mapJson[questionIdentifier]!=undefined && mapJson[questionIdentifier].elementComponentType=="1019"){
			 isNotesComponent=true; 
		 }
		 
		 return isNotesComponent;
	 }
	 
	 /**
     * checkStandardBatch: it returns true if the element with that questionIdentifier is a standard question
     */
	 
	 function checkStandardBatch(questionIdentifier){
		 
		 var isStandardBatchQuestion = false;
		 
		 if(mapJson[questionIdentifier]!=undefined && mapJson[questionIdentifier].isStandardized=="T" && mapJson[questionIdentifier].questionGroupSeqNbr !=null && mapJson[questionIdentifier].questionGroupSeqNbr!=''){
			 isStandardBatchQuestion=true; 
		 }
		 
		 return isStandardBatchQuestion;
	 }
	 
	 /**
	  * getQuestionIdentifierFromText: returns the question identifier from the text
	  */
	 function getQuestionIdentifierFromText(innerText){
		 
		 var questionIdentifier = innerText.substring(innerText.indexOf("(")+1,innerText.indexOf(")"));
		 
		 return questionIdentifier;
		 
	 }
    /**
     * Display the popup that gives these 2 options to the user:
     * 1. Search for questions and import them.
     * 2. Create a static page element (i.e., hyperlink, horizontal bar, read-only comments etc...)
     */
    this.showImportQuestionsDialog = function(containerElt) {
		
		//alert($j(containerElt).html())
    	var subSectionEltId = $j(containerElt).attr("id");
		//alert(subSectionEltId);
		var childEltsCount = $j(containerElt).find("." + fbRef.nbsPageElementCssClass).length;
		var isNotes = false;
		var isStandardBatch = false;
		
		//verify if there's any question of type note (1019)
		for(var i=0; i<childEltsCount && !isNotes; i++){
			
			 var innerText = $j(containerElt).find("." + fbRef.nbsPageElementCssClass)[i].innerText;
			 var questionIdentifier = getQuestionIdentifierFromText(innerText);
			 
			 isNotes = checkNotesComponent(questionIdentifier);
		}
		
		for(var i=0; i<childEltsCount && !isStandardBatch; i++){
			
			 var innerText = $j(containerElt).find("." + fbRef.nbsPageElementCssClass)[i].innerText;
			 var questionIdentifier = getQuestionIdentifierFromText(innerText);
			 
			 isStandardBatch = checkStandardBatch(questionIdentifier);
		}
		if(isNotes)
			alert("‘Multi-line Notes with User/Date Stamp’ questions must exist alone in a repeating block subsection; additional questions cannot be moved into a subsection that contains a ‘Multi-line Notes with User/Date Stamp’ question, and a ‘Multi-line Notes with User/Date Stamp’ question cannot be added/moved to a subsection that contains other questions.");
		else if(isStandardBatch)
			alert("Additional questions cannot be moved into a subsection that contains a standard set of batch questions , and a question cannot be moved out of a subsection with standard set of batch questions.");

		
		else{ 
			fbRef.setActiveContainerElt(containerElt);
			fbRef.setActiveClickedEltType("question");
			fbRef.setLatestActionRequested("add");
			var body = document.body;
			var divElt = getElementByIdOrByName("parentWindowDiv");
			divElt.style.display = "block";
			divElt.style.height = body.scrollHeight+"px";
	  
			var URL = "/nbs/SearchManageQuestions.do?method=searchQuestionsLoadFromPageBuilder&SubsectionId="+subSectionEltId + "&childEltsCount=" + childEltsCount;;
			displayModalDialog(URL);   
			
        }
    }
    
    /** Collapse/Expand a given element. */
    this.toggleDisplay = function(linkElt, containerElt, eltType) {
        if ($j($j(containerElt).find("div.body").get(0)).css("display") == "none") {
            // preset field row data.
            if (eltType == 'fieldRow') {
                if ($j(containerElt).attr("class") != 'fieldRow') {
                    containerElt = $j(containerElt).find("div.fieldRow").get(0);
                }
                var qId = $j(containerElt).attr("id");
                if (qId != null && jQuery.trim(qId) != "") {
                    // remove any existing field row body
                    $j(containerElt).find("div.configFieldRows").remove();
                    
                    // TODO: set spinner here
                    $j(containerElt).append("<div class=\"spinner\" style=\"font-size:13px; margin:3px 0px; color:green; font-weight:bold; \"> Getting Input Fields. Please wait...</div>");

                    JPageBuilder.loadQuestion(qId, function(data){
                        var jsonFields = eval('(' + data + ')');
                        
                        // TODO: set the input type select box
                        $j(containerElt).find("div.baseQ").find("div.control").html(jsonFields.inputTypeDesc);
                        
                        $j(containerElt).find("div.spinner").remove();
                        $j(containerElt).find("form").append(elt);
                        $j(containerElt).find("div.body").show();
                    });
                }
            }
        
            $j($j(containerElt).find("div.body").get(0)).show();
            $j($j(linkElt).find("img").get(0)).attr("src", "minus_sign.gif").attr("alt", "Collapse");
        }
        else {
            $j($j(containerElt).find("div.body").get(0)).hide();
            $j($j(linkElt).find("img").get(0)).attr("src", "plus_sign.gif").attr("alt", "Expand");
        }
    }

    /** 
    * Expand All/Collase All elements within a given element.
    * @param thisElt Toggle icon link that was clicked     
    */
    this.toggleAllChildren = function(thisElt) {
        var currState = $j($j(thisElt).find("img")).attr("alt");
        var containerElt = $j($j($j(thisElt).parent()).parent()).parent();
        if (
                $j(containerElt).hasClass('tab') || 
                $j(containerElt).attr("id") == "tabContainer"
            ) {
            if (currState.indexOf("Collapse All") >= 0) {
                $j($j(thisElt).find("img")).attr("src", "ExpandAll.gif").attr("alt", "Expand All Sections").attr("title", "Expand All Sections");
                $j($j(thisElt).find("span.text").get(0)).html("Expand All Sections");
                $j($j(thisElt).parent().parent().find("div.section > div.body" )).hide();
                $j($j(thisElt).parent().parent().find("div.section > div.header > div.content > a.toggleLink > img" )).attr("src", "plus_sign.gif").attr("alt", "Expand");
            }
            else {
                $j($j(thisElt).find("img")).attr("src", "CollapseAll.gif").attr("alt", "Collapse All Sections").attr("title", "Collapse All Sections");
                $j($j(thisElt).find("span.text").get(0)).html("Collapse All Sections");
                $j($j(thisElt).parent().parent().find("div.section > div.body" )).show();
                $j($j(thisElt).parent().parent().find("div.section > div.header > div.content > a.toggleLink > img" )).attr("src", "minus_sign.gif").attr("alt", "Expand");
            }
        }
        else if ($j(containerElt).hasClass('section')) {
            if (currState.indexOf("Collapse All") >= 0) {
                $j($j(thisElt).find("img")).attr("src", "ExpandAll.gif").attr("alt", "Expand All Subsections").attr("title", "Expand All Subsections");
                $j($j(containerElt).find("div.subSection > div.body" )).hide();
                $j($j(containerElt).find("div.subSection > div.header > div.content > a.toggleLink > img" )).attr("src", "plus_sign.gif").attr("alt", "Expand");
            }
            else {
                $j($j(thisElt).find("img")).attr("src", "CollapseAll.gif").attr("alt", "Collapse All Subsections").attr("title", "Collapse All Subsections");
                $j($j(containerElt).find("div.subSection > div.body" )).show();
                $j($j(containerElt).find("div.subSection > div.header > div.content > a.toggleLink > img" )).attr("src", "minus_sign.gif").attr("alt", "Expand");
            }
        }
    } 
    
    /** Display a DIV element that display all the possible targets for a given element to move. */
    this.showRelocationOptions = function(event, srcElt, srcEltType, publishInd, batchUid, dataLoc) {
	
    	//verify if the question to relocate is of type note (1019)
		var innerText = srcElt.innerText;
		var questionIdentifier = getQuestionIdentifierFromText(innerText);
		 
		isNotes = checkNotesComponent(questionIdentifier);
		
		if(isNotes && srcElt.className.indexOf("fieldRow")!=-1){
			alert("‘Multi-line Notes with User/Date Stamp’ questions must exist alone in a repeating block subsection; additional questions cannot be moved into a subsection that contains a ‘Multi-line Notes with User/Date Stamp’ question, and a ‘Multi-line Notes with User/Date Stamp’ question cannot be added/moved to a subsection that contains other questions.");
		}else{
			
	        var optionsHTML = "";
	        switch (srcEltType) {
	            case 'fieldRow':
	                optionsHTML = getFieldRowRelocationOptionsHTML(srcElt,srcEltType, publishInd, batchUid, dataLoc);
	                break;
	                
	            case 'subSection':
	                optionsHTML = getSubSectionRelocationOptionsHTML();
	                break;
	                
	            case 'section':
	                optionsHTML = getSectionRelocationOptionsHTML();
	                break;
	                
	            default:
	                alert("No relocation option available for element type: " + srcEltType);
	                break;                        
	        }
	        if(srcEltType== 'fieldRow' && publishInd == 'T' && batchUid != null){
	        	//do nothing: If the question is batched and published it cannot be relocate to any other subsection
	        }else{
		        closePopup();
		        var popUpHTML = "<div class=\"relocatePopup\" style=\"display: none;\"><div style=\"width:100%; text-align:right;\"> <a href=\"#\" onclick=\"closePopup();\">Close</a> </div>" + optionsHTML + "</div>";
		        var popUpElt = jQuery(popUpHTML);            
		        $j(srcElt).append(popUpElt);
		        $j("iframe").css("display","inline"); 
		
		        var height = $j($j(srcElt).find("div.relocatePopup").get(0)).height();
		        var width = $j($j(srcElt).find("div.relocatePopup").get(0)).width();
		        leftVal=event.pageX-(width+10)+"px";
		        topVal=event.pageY-(height/3)+"px";
		        $j($j(srcElt).find("div.relocatePopup").get(0)).css({left:leftVal,top:topVal}).show();
		        handleEvents(popUpElt, 'relocatePopUp',publishInd,batchUid, dataLoc);
	        }
		}
    }
    
    /** 
    * Parse the entire DOM and generate a comma separated value (csv) string
    * of page elements as they appear in the DOM.  
    * */
    this.getPageElementsOrder = function() {
        var eltsCount = 0; 
        var csv = "";
        
        // page
        var page = $j("div#pageContainer").find("div.pageBuilderPage").get(0);
        csv += $j(page).attr("id") + ",";
        eltsCount++;
        
        // tabs and its contents
        var tabHandles = $j($j("div#tabContainer").find("ul.ui-tabs-nav").get(0)).find("li");
        if (tabHandles.length > 0) {
            for (var i = 0; i < tabHandles.length; i++) {
                var tabHandle = $j(tabHandles[i]);
                var tabRef = $j($j(tabHandle).find("a").get(0)).attr("href");
                tabRef = jQuery.trim(tabRef);
                var tabId = tabRef.substring(tabRef.lastIndexOf("/")+1, tabRef.length);
                
                // add Id to csv
                csv += tabId + ",";
                eltsCount++;
                
                // get all the elements in that tab.
                var tabContents = $j("#" + tabId).find("." + fbRef.nbsPageElementCssClass);
                for (var t = 0; t < tabContents.length; t++) {
                    csv += $j(tabContents[t]).attr("id") + ",";
                    eltsCount++;
                }
            }
        }
        
        return csv;
    }
    
    
    /* TODO: remove this comment to enable batch entry functionality*/
    this.toggleFieldGrouping = function (elt,eltType) {
    	/** To check if the subsection contains any questions that are not stored in case_answer table*/
        var childEltsCount = $j(elt).find("." + fbRef.nbsPageElementCssClass).length;
        var isFieldGrouping = 'T';
        var isFieldPublish = 'F';
        var hasRollingNote = 'F';
        
        // if a subsection contains a question not in NBS_CASE_ANSWER, don't let that subsection batch
        if (childEltsCount> 0){
        	 for (var i = 0; i < childEltsCount; i++) {
        		 var fieldRowElt = $j(elt).find("div.fieldRow div.header div.controls span.hiddenNotCaseAnswerFieldRowIdSpan").get(i);
        		 if(fieldRowElt != undefined)
        			 isFieldGrouping = 'F'; 
        	 }
        }
        if (childEltsCount> 0){
        	 for (var i = 0; i < childEltsCount; i++) {
        		 var fieldRowElt = $j(elt).find("div.fieldRow div.header div.controls span.hiddenRollingNoteRowIdSpan").get(i);
        		 if(fieldRowElt != undefined)
        			 hasRollingNote = 'T';
        	 }
        	 if(hasRollingNote == 'T' && childEltsCount > 1)
        	 	 isFieldGrouping = 'F';
        	 //alert("hasRollingNote = " + hasRollingNote);
        	 
        }        
        
        //// if a subsection contains a publish question, don't let that subsection batch
        if(isFieldGrouping == 'T')
        {
        	if (childEltsCount> 0){
           	 for (var i = 0; i < childEltsCount; i++) {
           		 var fieldRowElt = $j(elt).find("div.fieldRow div.header div.controls span.hiddenPublishFieldRowIdSpan").get(i);
           		 if(fieldRowElt != undefined){
           			 isFieldGrouping = 'F'; 
           			 isFieldPublish = 'T';
           		 }
           		 
           	 }
           }
        }
        var containment = $j($j(elt).find("UL.sortableFieldRows").get(0)).sortable('option', 'containment');
    	var subSectionEltId = $j(elt).attr("id");
    	var csv = "";
    	if(isFieldGrouping == 'T'){
	if (containment == undefined || containment == 'document' ||
	//for sections ungrouped with notes (1019)
	($j("#"+subSectionEltId+"").find(".repeatingBlockLink").length==0)) {
	            if (childEltsCount> 0) {
	                for (var i = 0; i < childEltsCount; i++) {
	                	var fieldRowElt = $j(elt).find(".fieldRow").get(i);
	                	var fieldRowEltId = $j(fieldRowElt).attr("id");
	                	csv += fieldRowEltId + ",";
	                }
	            } 
	            csv += subSectionEltId;
	            JPageBuilder.updateBatchUidForBatchEntry(csv, function(data){});
	            setTimeout(function() { fbRef.showEditElementDialog(elt, "subSection", "batch"+csv); }, 1000);
	            $j($j($j(elt).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper").append('<a class="repeatingBlockLink" href="javascript:void(0)"><img alt="Repeating SubSection" title="Repeating SubSection" src="RepeatingBlock.gif"/>&nbsp;</a>&nbsp;');
	            $j($j(elt).find("UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'parent');
	            
	            $j($j(elt).find("UL.sortableFieldRows").get(0)).addClass("batchonly");
	            $j($j(elt).find("UL.sortableFieldRows").get(0)).removeClass("nonbatch");
	            $j($j($j(elt).parent()).find("div.subSection").get(0)).css("background","#EEF7BE");
	            $j($j(elt).find("UL.sortableFieldRows").get(0)).css("background","#DCE7F7");
	            $j($j($j(elt).parent()).find("div.subSection").get(0)).css("border","1px solid #699F09");
	            $j($j(elt).find("a.groupFieldsLink").get(0)).attr("title", "Un-Group all Fields Rows in SubSection");;
	            $j($j($j(elt).find("a.groupFieldsLink").get(0)).find("img").get(0)).attr("src", "link_break.gif").attr("alt", "Un-Group all Fields Rows in SubSection").attr("title", "Un-Group all Fields Rows in SubSection");
	        }
	        else {
	        	eltLabel = getElementLabel(subSectionEltId, eltType);
	        	var msg = "You have indicated that you would like to ungroup the repeating block questions in the " 
	        		+ eltLabel +  
	            " subsection. Select OK to continue or Cancel to " + 
	            "return to Edit Page.";
	        	 var choice = confirm(msg);
	        	 if (choice) {
	        		$j($j($j(elt).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper a.repeatingBlockLink").remove(); 
		            $j($j(elt).find("UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
		            //$j($j(elt).find("UL.sortableFieldRows").get(0)).css("background","#DCE7F7");
		            $j($j($j(elt).parent()).find("div.subSection").get(0)).css("background","#DCE7F7 none repeat scroll 0 0");
		            $j($j($j(elt).parent()).find("div.subSection").get(0)).css("border","1px solid #BACEEE");
		            $j($j(elt).find("UL.sortableFieldRows").get(0)).removeClass("batchonly");
		            $j($j(elt).find("UL.sortableFieldRows").get(0)).addClass("nonbatch");
		            // make it
		            $j($j(elt).find("a.groupFieldsLink").get(0)).attr("title", "Group all Fields Rows in SubSection");;
		            $j($j($j(elt).find("a.groupFieldsLink").get(0)).find("img").get(0)).attr("src", "link.gif").attr("alt", "Group all Fields Rows in SubSection").attr("title", "Group all Fields Rows in SubSection");;
		            if (childEltsCount> 0) {
		                for (var i = 0; i < childEltsCount; i++) {
		                	var fieldRowElt = $j(elt).find(".fieldRow").get(i);
		                	var fieldRowEltId = $j(fieldRowElt).attr("id");
		                	csv += fieldRowEltId + ",";
		                }
		            } 
		            csv += subSectionEltId; 
				
		            JPageBuilder.updateBatchUidForUnBatch(csv, function(data){});
					
					//if ungrouping subsection with a note (1019), keep the corresponding behavior
					if (childEltsCount> 0) {
						for (var i = 0; i < childEltsCount; i++) {
							var fieldRowElt = $j(elt).find(".fieldRow").get(i);
							var innerText = fieldRowElt.innerText;
							var questionIdentifier = getQuestionIdentifierFromText(innerText);
							if(checkNotesComponent(questionIdentifier)){
								var subSectionId=$j(elt)[0].id;
								
								$j($j("#" +subSectionId).find("UL.sortableFieldRows").get(0)).addClass("batchonly");
								$j($j("#" +subSectionId).find("UL.sortableFieldRows").get(0)).removeClass("nonbatch");
								$j($j("#" +subSectionId).find("UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'parent');
							}
						}
					}
					
	        	 }
	        }
    	}
    	else if(hasRollingNote == 'T' && childEltsCount > 1)
	{
		    		var eltLabel = getElementLabel(subSectionEltId, eltType);
		    		var msg = eltLabel + " can not be made into a repeating block. " +
		    		"You can only have the Repeating Note field and no other fields in the repeating block subsection.";
		    		alert(msg);
    	}
    	else if(isFieldGrouping == 'F' && isFieldPublish == 'F')
    	{
    		var eltLabel = getElementLabel(subSectionEltId, eltType);
    		var msg = eltLabel + " cannot be made into a repeating block, because this subsection includes questions that are considered 'core' and cannot be included within a repeating block. Any question on the page that is not stored in an 'ANSWER_TXT' data location cannot be included in a repeating block. To see a list of questions that can/cannot be grouped into a repeating block, please access the page metadata by clicking on View Metadata from View Page. When viewing the metadata, filter the DATA_LOCATION column to show all records that contain ANSWER_TXT. All questions returned CAN be included within a repeating block; all other questions CANNOT. For more information on this constraint, please see Page Builder FAQs."
    		alert(msg);
    	}
    	else if(isFieldPublish == 'T')
    	{
    		var eltLabel = getElementLabel(subSectionEltId, eltType);
    		var msg = eltLabel + " subsection cannot be made a repeating block, as this " +
    		"subsection includes a question(s) that has already been published."
    		alert(msg);
    	}

    }
    

    
    //********** Helper methods ***************//
    
    /**
     * Generate a jQuery element that represents a field row. 
     * @param pageElt JSON element that holds information to generate a field row.
     */
    getFieldRow = function(pageElt) {
        var cssClass = "fieldRow" + " " + fbRef.nbsPageElementCssClass;
        var controlPart = "", defaultVal = "", qIconImg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        qIconImg1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        var publishInd = pageElt.isPublished;
        var coinfInd = pageElt.isCoInfection;
        var standardizedInd = pageElt.isStandardized;
        var batchUid = pageElt.questionGroupSeqNbr;
        var dataLocCase = pageElt.dataLocation;
        var dataLoc = "";   
        if(dataLocCase != undefined)
        	dataLoc = dataLocCase.toUpperCase();
        switch (pageElt.elementType.toLowerCase()) {
            case 'question':
                defaultVal = pageElt.elementLabel + " (" + pageElt.questionIdentifier + ")";
                if(pageElt.isCoInfection == 'T') {
                	qIconImg1 = "<img src=\"Ind_CoinfectionQ_small.gif\" alt=\"Co-Infection Indicator\" title =\"Co-Infection Indicator\" />";
	                controlPart = "<div class=\"controls\"> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\" alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" style=\"visibility:hidden\"class=\"editFieldRowLink\" href=\"javascript:void(0);return false;\"> <img src=\"page_white_edit_disabled.gif\" alt=\"Edit Question disabled\" title=\"Edit Question disabled\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Question\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\"/> </a> </div>";
		         }
                if (pageElt.isStandardized == 'T' && dataLoc.indexOf(".ANSWER_TXT") < 0) {
	                qIconImg = "<img src=\"StandardizedIcon.gif\" alt=\"Standard Question Indicator\" title =\"Standard Question Indicator\"/>";
	                controlPart = "<div class=\"controls\"> <span style=\"visibility:hidden\" class=\"hiddenNotCaseAnswerFieldRowIdSpan\">\"NoGroup\"</span> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> </div>";
	            }else if(pageElt.isStandardized == 'T' && dataLoc.indexOf(".ANSWER_TXT")>= 0)
	            {
	            	qIconImg = "<img src=\"StandardizedIcon.gif\" alt=\"Standard Question Indicator\" title =\"Standard Question Indicator\"/>";
	                controlPart = "<div class=\"controls\"> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\"  title=\"Relocate\"/> </a> </div>";
	            }
	            else if (pageElt.isPublished == 'T' && dataLoc.indexOf(".ANSWER_TXT")< 0) {
	                qIconImg = "<img src=\"PublishedIcon.gif\" alt=\"Published Indicator\" title =\"Published Indicator\" />";
	                controlPart = "<div class=\"controls\"> <span style=\"visibility:hidden\" class=\"hiddenNotCaseAnswerFieldRowIdSpan\">\"NoGroup\"</span> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\">  <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> </div>";
	            }
	            else if(pageElt.isPublished == 'T' && dataLoc.indexOf(".ANSWER_TXT") >= 0){
	            	qIconImg = "<img src=\"PublishedIcon.gif\" alt=\"Published Indicator\" title =\"Published Indicator\"/>";
	                controlPart = "<div class=\"controls\"> <span style=\"visibility:hidden\" class=\"hiddenPublishFieldRowIdSpan\">\"NoGroup\"</span> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> </div>";
	            }
	            else if(pageElt.isStandardized != 'T' && pageElt.isPublished != 'T' && dataLoc.indexOf(".ANSWER_TXT")< 0)
	            {
	            	 qIconImg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		             controlPart = "<div class=\"controls\"><span style=\"visibility:hidden\" class=\"hiddenNotCaseAnswerFieldRowIdSpan\">\"NoGroup\"</span> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Question\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\" title=\"Delete Question\"/> </a> </div>";
	            }
	            else if(pageElt.isStandardized != 'T' && pageElt.isPublished != 'T' && dataLoc.indexOf(".ANSWER_TXT")>= 0 && pageElt.elementComponentType == '1019') {
	                qIconImg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	                controlPart = "<div class=\"controls\"> <span style=\"visibility:hidden\" class=\"hiddenRollingNoteRowIdSpan\">\"RollingNote\"</span><a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Question\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\" title=\"Delete Question\"/> </a> </div>";
	            }else{
	                qIconImg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	                controlPart = "<div class=\"controls\"> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Question\" title=\"Edit Question\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Question\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\" title=\"Delete Question\"/> </a> </div>";
	            }
                break;
            
            case 'hyperlink':
                defaultVal = "Hyperlink:" + pageElt.elementLabel;
                controlPart = "<div class=\"controls\"> <a title=\"View Hyperlink\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Hyperlink\"/> </a> &nbsp; <a title=\"Edit Hyperlink\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Hyperlink\" title=\"Edit Hyperlink\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Hyperlink\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\" title=\"Delete Question\"/> </a></div>";
                break;
                
            case 'comment':
	            defaultVal = "Comment:" + pageElt.elementLabel;
	            controlPart = "<div class=\"controls\"> <a title=\"View Comment\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Comment\"/> </a> &nbsp; <a title=\"Edit Comment\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Comment\" title=\"Edit Comment\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Comment\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Comment\" title=\"Delete Comment\"/> </a></div>";
                break;
                
            case 'line separator':
                defaultVal = "Line Separator";
                controlPart = "<div class=\"controls\"> <a title=\"View Line Separator\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Line Separator\"/> </a> &nbsp; <a title=\"Edit Line Separator\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Line Separator\" title=\"Edit Line Separator\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Line Separator\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Line Separator\" title=\"Delete Line Separator\"/> </a></div>";
                break;  
            case 'patient search':
                defaultVal = "Patient Search";
                controlPart = "<div class=\"controls\"> <a title=\"View Patient Search\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Patient Search\"/> </a> &nbsp; <a title=\"Edit Patient Search\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Patient Search\" title=\"Edit Patient Search\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Patient Search\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Patient Search\" title=\"Delete Patient Search\"/> </a></div>";
                break; 
            case 'participant list':
                defaultVal = "Participant List";
                controlPart = "<div class=\"controls\"> <a title=\"View Participant List\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Participant List\"/> </a> &nbsp; <a title=\"Edit Participant List\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Participant List\" title=\"Edit Participant List\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Participant List\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Participant List\" title=\"Delete Participant List\"/> </a></div>";
                break;
            case 'action button':
                defaultVal = "Action Button";
                controlPart = "<div class=\"controls\"> <a title=\"View Action Button\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Action Button\"/> </a> &nbsp; <a title=\"Edit Action Button\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Action Button\" title=\"Edit Action Button\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Action Button\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Action Button\" title=\"Delete Action Button\"/> </a></div>";
                break;
            case 'set values button':
                defaultVal = "Set Values Button";
                controlPart = "<div class=\"controls\"> <a title=\"View Set Values Button\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Set Values Button\"/> </a> &nbsp; <a title=\"Edit Set Values Button\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Set Values Button\" title=\"Edit Set Values Button\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Set Values Button\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Set Values Button\" title=\"Delete Set Values Button\"/> </a></div>";
                break;
            case 'original electronic document list':
                defaultVal = "Original Electronic Document List";
                controlPart = "<div class=\"controls\"> <a title=\"View Original Electronic Document List\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"Original Electronic Document List\"/> </a> &nbsp; <a title=\"Edit Original Electronic Document List\" class=\"editFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Original Electronic Document List\" title=\"Edit Original Electronic Document List\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Original Electronic Document List\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Original Electronic Document List\" title=\"Delete Original Electronic Document List\"/> </a></div>";
                break; 
        }
    
        /*if (publishInd == 'T' && batchUid != null) {
        	cssClass += " " + fbRef.publishedEltClass;
        }
        else if (standardizedInd == 'T' && batchUid != null) {
        	cssClass += " " + fbRef.standardizedEltClass;
        }else if(dataLoc.indexOf(".ANSWER_TXT")< 0 && batchUid != null){
        	cssClass += " " + fbRef.notCaseAnswerLocEltClass;
        }
        */
        if(coinfInd == 'T' && publishInd != 'T') {
			  controlPart = "<div class=\"controls\"> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" style=\"visibility:hidden\"class=\"editFieldRowLink\" href=\"javascript:void(0);return false;\"> <img src=\"page_white_edit_disabled.gif\" alt=\"Edit Question disabled\" title=\"Edit Question disabled\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Question\"  class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\" title=\"Delete Question\"/> </a> </div>";
	} else if(coinfInd == 'T' && publishInd == 'T') {
			controlPart = "<div class=\"controls\"> <a title=\"View Question\" class=\"viewFieldRowLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Question\"/> </a> &nbsp; <a title=\"Edit Question\" style=\"visibility:hidden\"class=\"editFieldRowLink\" href=\"javascript:void(0);return false;\"> <img src=\"page_white_edit_disabled.gif\" alt=\"Edit Question disabled\" title=\"Edit Question disabled\"/> </a> &nbsp; <a class=\"relocateLink\" href=\"javascript:void(0)\" title=\"\Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a title=\"Delete Question\"  style=\"visibility:hidden\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Question\" title=\"Delete Question\"/> </a> </div>";
	}
        var html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\"> <div class=\"content\">"+ qIconImg1 + qIconImg + " <span class=\"ieVerticalAlignHelper\"></span> <span class=\"title\">" + defaultVal + "</span><span class=\"dataMartColumnNm\" style=\"display:none\">"+pageElt.dataMartColumnNm+"</span></div> " + controlPart + "</div> </li>";
        var elt = jQuery(html);
        var fieldRowElt = $j(elt).find(".fieldRow").get(0);
        handleEvents(fieldRowElt, 'fieldRow', publishInd, batchUid, dataLoc);
       
        return elt;
    }
    
    /**
     * Generate a jQuery element that represents a subsection. 
     * @param pageElt JSON element that holds information to generate a subsection.
     */
    getSubSection = function(pageElt) {
    	var cssClass = "subSection" + " " + fbRef.nbsPageElementCssClass;
        //FIXME: replace the line below for html to enable batch entry functionality 
        //var html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\"> <div class=\"content\"> <span class=\"ieVerticalAlignHelper\"></span> <a class=\"toggleLink\" href=\"javascript:void(0)\"><img src=\"minus_sign.gif\" alt=\"Collapse\"/></a> &nbsp; Subsection: <span class=\"title\">" + pageElt.elementLabel + "</span></div> <div class=\"controls\"> <a title=\"Edit Subsection\" class=\"editSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Subsection\"/> </a> &nbsp; <a class=\"importQuestionLink\" title=\"Add/Import Element(s)\" href=\"javascript:void(0)\"> <img src=\"import.gif\" alt=\"Add/Import Element(s)\"/> </a> &nbsp; <a class=\"groupFieldsLink\" title=\"Group All Questions in SubSection as Batch\" href=\"javascript:void(0)\"> <img src=\"link.gif\" alt=\"Group All Questions in SubSection as Batch\"/> </a> &nbsp; <a class=\"relocateSubSectionLink\" href=\"javascript:void(0)\"> <img src=\"doc_page.jpg\" alt=\"Move\"/> </a> &nbsp; <a title=\"Delete Subsection\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Subsection\"/> </a> </div> </div> <div class=\"body\"> <ul class=\"sortableFieldRows\">" + "</ul></div> </div> </li>";
    	var html = "";
    	var publishInd = pageElt.isPublished;
        var batchUid = pageElt.questionGroupSeqNbr;
    	/**The group Icon will only display unPublished subsections*/
    	if(pageElt.isPublished == 'T'){
    		html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\"> <div class=\"content\"> <span class=\"ieVerticalAlignHelper\"></span> <a class=\"toggleLink\" href=\"javascript:void(0)\"><img src=\"minus_sign.gif\" alt=\"Collapse\"/></a> &nbsp; Subsection: <span class=\"title\">" + pageElt.elementLabel + "</span><span class=\"blockName\" style=\"display:none\">"+pageElt.blockName+"</span></div> <div class=\"controls\">  &nbsp; <a class=\"importQuestionLink\" title=\"Add/Import Element(s)\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add/Import Element(s)\" title=\"Add/Import Element(s)\"/> </a> &nbsp; <a title=\"View Subsection\" class=\"viewSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Subsection\"/> </a> &nbsp; <a title=\"Edit Subsection\" class=\"editSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Subsection\" title=\"Edit Subsection\"/> </a> &nbsp; <a class=\"relocateSubSectionLink\" href=\"javascript:void(0)\" title=\"Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a class=\"groupFieldsLink noDisplay\" title=\"Group All Questions in Subsection as Batch\" href=\"javascript:void(0)\">  </a>&nbsp; <a title=\"Delete Subsection\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Subsection\" title=\"Delete Subsection\"/> </a> </div> </div> <div class=\"body\"> <ul class=\"sortableFieldRows\">" + "</ul></div> </div> </li>";
    	}
    	else{
    		html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\"> <div class=\"content\"> <span class=\"ieVerticalAlignHelper\"></span> <a class=\"toggleLink\" href=\"javascript:void(0)\"><img src=\"minus_sign.gif\" alt=\"Collapse\"/></a> &nbsp; Subsection: <span class=\"title\">" + pageElt.elementLabel + "</span><span class=\"blockName\" style=\"display:none\">"+pageElt.blockName+"</span></div> <div class=\"controls\">  &nbsp; <a class=\"importQuestionLink\" title=\"Add/Import Element(s)\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add/Import Element(s)\" title=\"Add/Import Element(s)\"/> </a> &nbsp; <a title=\"View Subsection\" class=\"viewSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Subsection\"/> </a> &nbsp; <a title=\"Edit Subsection\" class=\"editSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Subsection\" title=\"Edit Subsection\"/> </a> &nbsp; <a class=\"relocateSubSectionLink\" href=\"javascript:void(0)\" title=\"Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a class=\"groupFieldsLink noDisplay\" title=\"Group All Questions in Subsection as Batch\" href=\"javascript:void(0)\"> <img src=\"link.gif\" alt=\"Group All Questions in Subsection as Batch\"/> </a>&nbsp; <a title=\"Delete Subsection\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Subsection\" title=\"Delete Subsection\"/> </a> </div> </div> <div class=\"body\"> <ul class=\"sortableFieldRows\">" + "</ul></div> </div> </li>";
    	}
        var elt = jQuery(html);
        var subSectionElt = $j(elt).find(".subSection").get(0);
        handleEvents(subSectionElt, 'subSection', publishInd, batchUid);
        return elt;
    }
    
    /**
     * Generate a jQuery element that represents a section. 
     * @param pageElt JSON element that holds information to generate a section.
     */
    getSection = function(pageElt) {
        var cssClass = "section" + " " + fbRef.nbsPageElementCssClass;
        var html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\" > <div class=\"content\"> <span class=\"ieVerticalAlignHelper\"></span><a class=\"toggleLink\" href=\"javascript:void(0)\"><img src=\"minus_sign.gif\" alt=\"Collapse\"/></a> &nbsp; <a class=\"toggleAllChildrenLink\" title=\"Collapse All Subsections\" href=\"javascript:void(0)\"> <img src=\"CollapseAll.gif\" alt=\"Collapse All Subsections\" title=\"Collapse All Subsections\"/> </a> &nbsp;Section: <span class=\"title\">" + pageElt.elementLabel + "</span></div><div class=\"controls\"><span class=\"ieVerticalAlignHelper\"></span> <a class=\"addSubSection\" title=\"Add New Subsection to this Section\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add New Subsection\" title=\"Add New Subsection\"/> </a> &nbsp; <a title=\"View Section\" class=\"viewSectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Section\"/> </a> &nbsp; <a title=\"Edit Section\" class=\"editSectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Section\" title=\"Edit Section\"/> </a> &nbsp; <a class=\"relocateSectionLink\" href=\"javascript:void(0)\" title=\"Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a>&nbsp;<a title=\"Delete Section\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Section\" title=\"Delete Section\"/> </a></div></div><div class=\"body\"><ul class=\"sortableSubSections\">" 
        + "</ul></div></div> </li>";
        var elt = jQuery(html);
        var sectionElt = $j(elt).find(".section").get(0);
        handleEvents(sectionElt, 'section');
        return elt;
    }
    
    /**
     * Generate a jQuery element that represents a tab handle. 
     * @param pageElt JSON element that holds information to generate a tab handle.
     */
    getTabHandle = function(pageElt, isActive) {
        var tabHandleClass = "";
        if (isActive) {
            tabHandleClass = "ui-tabs-selected";
        }
        var html = "<li class=\"" + tabHandleClass + "\"><a href=\"" + pageElt.pageElementUid + "\">" + pageElt.elementLabel + "</a></li>";
        var elt = jQuery(html);
        handleEvents(elt, 'tabHandle');
        return elt;
    }
    
    /**
     * Generate a jQuery element that represents a tab body. 
     * @param pageElt JSON element that holds information to generate a tab body.
     */
    getTabBody = function(pageElt, isActive) {
        var tabBodyClass = "ui-tabs-hide";
        if (isActive) {
            tabBodyClass = "ui-tabs-panel";
        }
        tabBodyClass += " " + fbRef.nbsPageElementCssClass;
        var html = "<div id=\"" + pageElt.pageElementUid + "\" class=\"" + tabBodyClass + "\"><div style=\"width:200px; text-align:left; margin-top:10px; float:left;\"><a class=\"toggleAllChildrenLink\" title=\"Collapse All Sections \" href=\"javascript:void(0)\"> &nbsp;&nbsp; <img src=\"CollapseAll.gif\" alt=\"Collapse All Sections\" title=\"Collapse All Sections\"/> <span class=\"text\">Collapse All Sections </span></a> &nbsp;</div><div style=\"width:200px; float:right;\" class=\"utilButton\"> <a title=\"Add Section\" class=\"addSection\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add Section\" title=\"Add Section\"/> </a> &nbsp; <a title=\"View Tab\" class=\"viewTabLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Tab\"/> </a> &nbsp; <a title=\"Edit Tab\" class=\"editTabLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Tab\" title=\"Edit Tab\"/> </a> &nbsp; <a title=\"Delete Tab\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Tab\" title=\"Delete Tab\"/> </a></div> <div class=\"ffClear\"> </div> <ul class=\"sortableSections\"></ul></div>";
        var elt = jQuery(html);
        handleEvents(elt, 'tabBody');
        return elt;
    }

    /**
     * Generate a jQuery element that represents a page. 
     * @param pageElt JSON element that holds information to generate a page.
     */
    getPage = function(pageElt) {
        var html = "<div id=\"" + pageElt.pageElementUid + "\" class=\"pageBuilderPage\"> <div id=\"tabContainer\"><ul class=\"ui-tabs-nav\"></ul></div> </div>";
        return jQuery(html);
    }
    
    handleEvents = function(elt, eltType, publishInd, batchUid, dataLoc) {
        switch (eltType) {
            case 'fieldRow':
                handleFieldRowEvents(elt, publishInd, batchUid, dataLoc);
                break;
                
            case 'subSection':
                handleSubSectionEvents(elt, publishInd, batchUid);
                break;
                
            case 'section':
                handleSectionEvents(elt);
                break;
                
            case 'tabHandle':
                handleTabHandleEvents(elt);
                break;    
                
            case 'tabBody':
                handleTabBodyEvents(elt);
                break;                
                
            case 'relocatePopUp':
                $j($j(elt).find("a.moveTargetLink")).click(function(){
                   relocateElement(this,publishInd, dataLoc, elt);
                });
                break;
                
            default:
                alert("Element type " + eltType + "is not supported.");
                break;    
        }
    }
    
    handleTabHandleEvents = function(elt) {
        var aLink = $j($j(elt).find("a")).get(0);
        $j(aLink).click(function(ev){
            var href = $j(this).attr("href");
            var id = href.substring(href.lastIndexOf("/")+1, href.length);
            var jQueryBodyId = "div#" + id;
            ev.preventDefault();
           
            // update the handle
            $j($j($j(aLink).parents("ul").get(0)).find("li.ui-tabs-selected").get(0)).removeClass("ui-tabs-selected");
            $j($j(aLink).parents("li").get(0)).addClass("ui-tabs-selected");
           
            // update the body
            $j($j("div#tabContainer").find("div.ui-tabs-panel").get(0)).removeClass("ui-tabs-panel").addClass("ui-tabs-hide");
            $j($j("div#tabContainer").find(jQueryBodyId).get(0)).addClass("ui-tabs-panel").removeClass("ui-tabs-hide");
        });
    }

    handleTabBodyEvents = function(elt) {
        var aLink = $j($j(elt).find("a.addSection")).get(0);
        $j(aLink).click(function(){
           fbRef.showAddElementDialog(elt, 'section');
        });
        
        var tacLink = $j($j(elt).find("a.toggleAllChildrenLink")).get(0);
        $j(tacLink).click(function(){
            fbRef.toggleAllChildren(tacLink);
        });

        var vtLink = $j($j(elt).find("a.viewTabLink")).get(0);
        $j(vtLink).click(function(){
           fbRef.showViewElementDialog(elt, "tab");
        });
        
        var elLink = $j($j(elt).find("a.editTabLink")).get(0);
        $j(elLink).click(function(){
           fbRef.showEditElementDialog(elt, "tab");
        });
        
        var dLink = $j($j(elt).find("a.deleteLink")).get(0);
        $j(dLink).click(function(){
            fbRef.deleteElement(elt, "tab");
        });
        
        $j($j(elt).find("ul.sortableSections").get(0)).sortable
        ({
            handle:'div.header > div.content', 
            forcePlaceholderSize: true, 
            dropOnEmpty: true, 
            connectWith: ['ul.sortableSections'], 
            placeholder: 'placeHolder'
        });    
        
        $j(elt).mouseover(function(){
            $j(this).addClass("pageElementHover");
        }).mouseout(function(){
            $j(this).removeClass("pageElementHover");
        });
    }

    handleSectionEvents = function(elt) {
        // handle event handlers to items in menu box
        var tLink = $j($j(elt).find("a.toggleLink")).get(0);
        $j(tLink).click(function(){
            fbRef.toggleDisplay(tLink, elt, 'section');
        });
        
        var dLink = $j($j(elt).find("a.deleteLink")).get(0);
        $j(dLink).click(function(){
            fbRef.deleteElement(elt, "section");
        });

        var vsLink = $j($j(elt).find("a.viewSectionLink")).get(0);
        $j(vsLink).click(function(){
            fbRef.showViewElementDialog(elt, "section");            
        });
        
        var esLink = $j($j(elt).find("a.editSectionLink")).get(0);
        $j(esLink).click(function(){
            fbRef.showEditElementDialog(elt, "section");            
        });
        
        var tfLink = $j($j(elt).find("a.toggleAllChildrenLink")).get(0);
        $j(tfLink).click(function(){
            fbRef.toggleAllChildren(tfLink);
        });
            
        var aLink = $j($j(elt).find("a.addSubSection")).get(0);
        $j(aLink).click(function(){
           fbRef.showAddElementDialog(elt, 'subSection');
        });
        
        var rlLink = $j($j(elt).find("a.relocateSectionLink")).get(0);
        $j(rlLink).click(function(e){
            fbRef.showRelocationOptions(e, elt, "section");
        });
        
    
        $j($j(elt).find("ul.sortableSubSections").get(0)).sortable
        ({
            handle:'div.header > div.content', 
            forcePlaceholderSize: true, 
            dropOnEmpty: true, 
            connectWith: ['ul.sortableSubSections'], 
            placeholder: 'placeHolder',
            stop: function(event, ui)
                {
                   $j("body ul.sortableSections").sortable('enable');   
                },
            update: function(event, ui) { 
               // alert("ui.helper = " + ui.helper + "; ui.position = " + ui.item.prevAll().length + "; ui.offset = " + ui.offset + "; ui.item = " + ui.item + "; ui.placeholder = " + ui.placeholder + "; ui.sender = " + ui.sender);
            }
        });
        
        $j(elt).mouseover(function(){
            $j(this).addClass("pageElementHover");
        }).mouseout(function(){
            $j(this).removeClass("pageElementHover");
        });
    }
    
    handleSubSectionEvents = function(elt, publishInd, batchUid) {
        var tLink = $j($j(elt).find("a.toggleLink")).get(0);
        $j(tLink).click(function(){
            fbRef.toggleDisplay(tLink, elt, 'subSection');
        });
        
        var iQLink = $j($j(elt).find("a.importQuestionLink")).get(0);
        $j(iQLink).click(function(){
            fbRef.showImportQuestionsDialog(elt);
        });
        
        var vssLink = $j($j(elt).find("a.viewSubsectionLink")).get(0);
        $j(vssLink).click(function(){
            fbRef.showViewElementDialog(elt, "subSection");
        });

        var essLink = $j($j(elt).find("a.editSubsectionLink")).get(0);
        $j(essLink).click(function(){
            fbRef.showEditElementDialog(elt, "subSection");
        });
        
        var dLink = $j($j(elt).find("a.deleteLink")).get(0);
        $j(dLink).click(function(){
            fbRef.deleteElement(elt, "subSection");
        });
        
        /* TODO: remove this comment when batch entry functionality is required*/
        var gfLink = $j($j(elt).find("a.groupFieldsLink")).get(0);
         $j(gfLink).click(function(e){
             fbRef.toggleFieldGrouping(elt,"fieldRow");
         });

        
        var rlLink = $j($j(elt).find("a.relocateSubSectionLink")).get(0);
        $j(rlLink).click(function(e){
            fbRef.showRelocationOptions(e, elt, "subSection");
        });
        
        if(batchUid==null){
        
        $j($j(elt).find("ul.sortableFieldRows").get(0)).sortable
        ({
        	update: function(event, ui) { 
	            var eltId = $j(elt).attr("id");
	            var fieldElt = fbRef.getDragedFieldElement();
				
	            var fieldEltId = $j(fieldElt).attr("id");
	            var eltLabel = getElementLabel(fieldEltId, 'fieldRow');
	            var childEltsCount = $j(elt).find("." + fbRef.nbsPageElementCssClass).length;
	           	JPageBuilder.updateBatchUidForDraggedFieldRow(eltId,fieldEltId, function(data){});
            },
            handle:'div.header > div.content', 
            forcePlaceholderSize: true, 
            dropOnEmpty: true, 
            connectWith: ['ul.nonbatch'], 
            placeholder: 'placeHolder',
            stop: function(event, ui)
                {
	              $j("body ul.sortableSubSections").sortable('enable'); 
	              var eltId = $j(elt).attr("id");
	              var childEltsCount = $j(elt).find("." + fbRef.nbsPageElementCssClass).length;
	              /**If number of fieldrows changed to zero remove the grouplink**/
	              if(childEltsCount == 0)
			         {
			            $j($j($j("#" + eltId).find("div.header div.controls a.groupFieldsLink")).get(0)).addClass("noDisplay");
		            	$j($j($j("#" + eltId).parent()).find("div.subSection").get(0)).css("background","#DCE7F7 none repeat scroll 0 0");
			            $j($j($j("#" + eltId).parent()).find("div.subSection").get(0)).css("border","1px solid #BACEEE");
			            $j($j($j("#" + eltId).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper a.repeatingBlockLink").remove();
		            }
                },
            receive: function(event, ui) {
	                var eltId = $j(elt).attr("id");
		            var childEltsCount = $j(elt).find("." + fbRef.nbsPageElementCssClass).length;
		            /**If number of fieldrows changed form zero to more than 1 add the grouplink**/
		            if(childEltsCount == 1)
			         {
			            $j($j($j("#" + eltId).find("div.header div.controls a.groupFieldsLink")).get(0)).removeClass("noDisplay");
			            $j($j("#" +eltId).find("div.body UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
		            }
	           }

            
        }); 
        }else{
        $j($j(elt).find("ul.sortableFieldRows").get(0)).sortable
	        ({
	        	update: function(event, ui) { 
		            var eltId = $j(elt).attr("id");
		            var fieldElt = fbRef.getDragedFieldElement();
					
		            var fieldEltId = $j(fieldElt).attr("id");
		            var eltLabel = getElementLabel(fieldEltId, 'fieldRow');
		           	JPageBuilder.updateBatchUidForDraggedFieldRow(eltId,fieldEltId, function(data){});
	            },
	            sort: function(event, ui) {
	//            	alert("ui.left = " + 
	//            			ui.position.left + "ui.top = " + ui.position.top + 
	//            			"; $j($j(elt).parent()).position().left = " + $j($j(elt).parent()).position().left + 
	//            			"; $j($j(elt).parent()).position().top = " + $j($j(elt).parent()).position().top);
	            	
	            	if (ui.position.left <= $j($j(elt).parent()).position().left ||
	            			ui.position.top <= $j($j(elt).parent()).position().top) {
	            	}
	            },
	            handle:'div.header > div.content', 
	            forcePlaceholderSize: true, 
	            dropOnEmpty: true, 
	            connectWith: ['ul.batchonly'], 
	            placeholder: 'placeHolder',
	            stop: function(event, ui)
	                {
		              $j("body ul.sortableSubSections").sortable('enable'); 
	                },
	            receive: function(event, ui) {
		                var eltId = $j(elt).attr("id");
			            var childEltsCount = $j(elt).find("." + fbRef.nbsPageElementCssClass).length;
			            /**If number of fieldrows changed form zero to more than 1 add the grouplink**/
			            if(childEltsCount == 1)
				         {
				            $j($j($j("#" + eltId).find("div.header div.controls a.groupFieldsLink")).get(0)).removeClass("noDisplay");
				            $j($j("#" +eltId).find("div.body UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
			            }
		           }
	            
        }); 
        
        }
     
       
        
        var dragHandle = $j($j(elt).find("div.content")).get(0);
		
        $j(dragHandle).mouseover(function(){
            $j(this).parents("ul.sortableSections").sortable('disable');
        });
        $j(dragHandle).mouseout(function(){
            $j(this).parents("ul.sortableSections").sortable('enable');
        });
        
        $j(elt).mouseover(function(){
            $j(this).addClass("pageElementHover");
        }).mouseout(function(){
            $j(this).removeClass("pageElementHover");
        });
    }
    
    handleFieldRowEvents = function(elt, publishInd, batchUid, dataLoc) {
        var vFRLink = $j($j(elt).find("a.viewFieldRowLink")).get(0);
        $j(vFRLink).click(function(){
            if ($j(vFRLink).attr("title").toLowerCase().indexOf("question") >=0) {
                fbRef.showViewElementDialog(elt, "fieldRow", "question");    
            }
            else {
                fbRef.showViewElementDialog(elt, "fieldRow");
            }
        });
    
        var eFRLink = $j($j(elt).find("a.editFieldRowLink")).get(0);
        $j(eFRLink).click(function(){
            if ($j(eFRLink).attr("title").toLowerCase().indexOf("question") >=0) {
                fbRef.showEditElementDialog(elt, "fieldRow", "question");    
            }
            else {
                fbRef.showEditElementDialog(elt, "fieldRow");
            }
        });
        
        var eFRLink = $j($j(elt).find("a.editDisabledFieldRowLink")).get(0);
        $j(eFRLink).click(function(){
                 
        });
        
        var rlLink = $j($j(elt).find("a.relocateLink")).get(0);
        $j(rlLink).click(function(e){
            fbRef.showRelocationOptions(e, elt, "fieldRow", publishInd, batchUid, dataLoc);
            
        });
        
        var delLinks = $j(elt).find("a.deleteLink");
        if (delLinks.length > 0) {
	        var dLink = $j($j(elt).find("a.deleteLink")).get(0);
	        $j(dLink).click(function(){
	            fbRef.deleteElement(elt, "fieldRow");
	        });
        }

        var dragHandle = $j($j(elt).find("div.content")).get(0);
		
        $j(dragHandle).mouseover(function(){
        	fbRef.setDragedFieldElement(elt);
            $j(this).parents("ul.sortableGroupedFieldRows").sortable('disable');
            $j(this).parents("ul.sortableSubSections").sortable('disable');
            
           
            
            $j(this).parents("ul.sortableSections").sortable('disable');
            
        });
        $j(dragHandle).mouseout(function(){
        	fbRef.setDragedFieldElement(elt);
            $j(this).parents("ul.sortableGroupedFieldRows").sortable('enable');
            $j(this).parents("ul.sortableSubSections").sortable('enable');   
            $j(this).parents("ul.sortableSections").sortable('enable');
        });
        
        $j(elt).mouseover(function(){
            $j(this).addClass("pageElementHover");
        }).mouseout(function(){
            $j(this).removeClass("pageElementHover");
        });
    }

    getFieldRowRelocationOptionsHTML = function(srcElt,srcEltType, publishInd, batchUid, dataLoc) {
    	/**Get the parentSubsectionId of the fieldrow, so that we can check how many elements will be there
    	* after the relocate is done. If no. of questions for that subsection will be 0, than we have to remove the
    	* group icon.**/
    	var parentSubsectionElt = $($($j($j($j(srcElt).parent()).parent()).parent())).parent().parent();
        var parentSubsectionEltId = $j($j(parentSubsectionElt).find(".subSection").get(0)).attr("id");
        
    	var fieldrowEltId = $j(srcElt).attr("id");
    	var eltLabel = getElementLabel(fieldrowEltId, srcEltType);
    	if(publishInd == 'T' && batchUid != null){
    		var msg = eltLabel +
            " cannot be moved out of this repeating block, " + 
            " as this repeating question has already been published on this page with the inclusion of this field.";
    		alert(msg);
    	}
    	else{
	        var html = "";
	        var tabs = $j("div#tabContainer").find("ul.ui-tabs-nav > li > a");
	        for (var i = 0; i < tabs.length; i++) 
	        {
	            var href = $j(tabs[i]).attr("href");
	            var id = href.substring(href.lastIndexOf("/")+1, href.length);
	            var label = $j(tabs[i]).html();
	            
	            html += "<span style=\"font-size:14px; margin:5px 0px; text-decoration:underline;\">" + $j(tabs[i]).html() + "</span> <br/>";
	            var sections = $j("#" + id).find("div.section");
	            for (var s = 0; s < sections.length; s++) {
	                var singleSection = $j(sections).get(s);
	                html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	                        "<span style=\"font-size:13px; margin:5px 0px; font-style:italic;\">" + 
	                                $j($j(singleSection).find("div.header > div.content > span.title").get(0)).html() + 
	                                "</span> <br/>";
	                 
	                var subSections = $j(singleSection).find("div.subSection");
	                for (var ss = 0; ss < subSections.length; ss++) {
	                    var singleSubSection = $j(subSections).get(ss);
	                    html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	                            "<a class=\"moveTargetLink\" href=\"javascript:void(0)\">" + 
	                            "<span class=\"hiddenEltIdSpan\">subSection_" + 
	                            $j(singleSubSection).attr("id") + "</span>" + 
	                            $j($j(singleSubSection).find("div.header > div.content > span.title").get(0)).html() + 
	                            "<span style=\"visibility:hidden\" class=\"hiddenEltFieldRowIdSpan\">fR_" + $j(srcElt).attr("id")+":"+ parentSubsectionEltId + "</span>" +
	                            "</a><br/>";
	                }
	            }
	        }
	        return html;
    	}
    }
    
    getSubSectionRelocationOptionsHTML = function() {
        var html = "";
        var tabs = $j("div#tabContainer").find("ul.ui-tabs-nav > li > a");
        for (var i = 0; i < tabs.length; i++) 
        {
            var href = $j(tabs[i]).attr("href");
            var id = href.substring(href.lastIndexOf("/")+1, href.length);
            var label = $j(tabs[i]).html();
            
            html += "<span style=\"font-size:14px; margin:5px 0px; text-decoration:underline;\">" + $j(tabs[i]).html() + "</span> <br/>";
            
            var sections = $j("#" + id).find("div.section");
            for (var s = 0; s < sections.length; s++) {
                var singleSection = $j(sections).get(s); 
				
                html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<a class=\"moveTargetLink\" href=\"javascript:void(0)\">" + 
                    "<span class=\"hiddenEltIdSpan\">section_" + $j(singleSection).attr("id") + 
                    "</span>" + $j($j(singleSection).find("div.header > div.content > span.title").get(0)).html() + 
                    "</a><br/>";
            }
        }
        return html;
    }
	
	
    
    getSectionRelocationOptionsHTML = function() {
        var html = "";
        var tabs = $j("div#tabContainer").find("ul.ui-tabs-nav > li > a");
        for (var i = 0; i < tabs.length; i++) {
            var href = $j(tabs[i]).attr("href");
            var id = href.substring(href.lastIndexOf("/")+1, href.length);
            var label = $j(tabs[i]).html();
            
            html += "<a class=\"moveTargetLink\" href=\"javascript:void(0)\">" + 
                "<span class=\"hiddenEltIdSpan\">tab_" + id + "</span>" + label + "</a><br/>";
        }
        
        return html;
    }
    
    closePopup = function() {
        $j('div.relocatePopup').remove();
        $j("iframe").css("display","none");
    }
    
    relocateElement = function(targetEltLinkInPopUp,publishIndOfSource, dataLoc, containerElt) {
        var target = $j(targetEltLinkInPopUp).find("span.hiddenEltIdSpan").html();
        var targetEltType = target.substring(0, target.indexOf("_"));
        var targetEltId = target.substring(target.indexOf("_") + 1, target.length);
		var childEltsCount = $j("#"+targetEltId+"").find("." + fbRef.nbsPageElementCssClass).length;		
        var dataLocCase = "";
		var isStandardBatch = false;
		
		for(var i=0; i<childEltsCount && !isStandardBatch; i++){
			
			 var innerText = $j("#"+targetEltId+"").find("." + fbRef.nbsPageElementCssClass)[i].innerText;
			 var questionIdentifier = getQuestionIdentifierFromText(innerText);
			 
			 isStandardBatch = checkStandardBatch(questionIdentifier);
		}
		
		
		
        if(dataLoc != undefined)
        	dataLocCase = dataLoc.toUpperCase();
        /** If part: If the user attempt to add a publish question to a repeating block by relocate pop-up*/
        var containment = $j($j("#" + targetEltId).find("ul.sortableFieldRows").get(0)).sortable('option', 'containment');
		
		if(isStandardBatch){
        	alert("Additional questions cannot be moved into a subsection that contains a standard set of batch questions , and a question cannot be moved out of a subsection with standard set of batch questions.");
		}else if(publishIndOfSource == 'T' && targetEltType == 'subSection' && containment == 'parent'){
        	var sourceField = $j(targetEltLinkInPopUp).find("span.hiddenEltFieldRowIdSpan").html();
        	var sourceEltType = sourceField.substring(0, sourceField.indexOf("_"));
        	var sourceEltId = sourceField.substring(sourceField.indexOf("_") + 1, sourceField.indexOf(":"));
        	var eltLabel = getElementLabel(sourceEltId, "fieldRow");
        	var msg = eltLabel + 
            " cannot be moved into this repeating block, as this question was already " + 
            "published on this page as an individual field outside this repeating block."; 
        	alert(msg);
        }else if(dataLoc != undefined && dataLocCase.indexOf(".ANSWER_TXT") < 0 && targetEltType == 'subSection' && containment == 'parent'){
        	
        	var sourceField = $j(targetEltLinkInPopUp).find("span.hiddenEltFieldRowIdSpan").html();
        	var sourceEltType = sourceField.substring(0, sourceField.indexOf("_"));
        	var sourceEltId = sourceField.substring(sourceField.indexOf("_") + 1, sourceField.indexOf(":"));
        	var eltLabel = getElementLabel(sourceEltId, "fieldRow");
        	var msg = eltLabel + 
            " cannot be added to a repeating block, because this question is considered 'core' and cannot be included within a repeating block. Any question on the page that is not stored in an 'ANSWER_TXT' data location cannot be included in a repeating block. To see a list of questions that can/cannot be grouped into a repeating block, please access the page metadata by clicking on View Metadata from View Page. When viewing the metadata, filter the DATA_LOCATION column to show all records that contain ANSWER_TXT. All questions returned CAN be included within a repeating block; all other questions CANNOT. For more information on this constraint, please see Page Builder FAQs."; 
        	alert(msg);
        }
		else{//Verify the question to relocate is not of type note (nbs_component_uid = 1019)
			var relocationAllowed = true;
			if($j($j("#" + targetEltId).find("ul.sortableFieldRows").get(0))!=undefined && 
			$j($j("#" + targetEltId).find("ul.sortableFieldRows").get(0))[0]!=undefined){
				var outerText = $j($j("#" + targetEltId).find("ul.sortableFieldRows").get(0))[0].outerText;
				
				if(outerText==undefined)//Firefox
					outerText = $j($j("#" + targetEltId).find("ul.sortableFieldRows").get(0))[0].textContent;
				
				if(outerText!=null && outerText!=undefined){
					var questionIdentifier = outerText.substring(outerText.indexOf("(")+1,outerText.indexOf(")"))
					
					if(checkNotesComponent(questionIdentifier)){
						relocationAllowed=false;
						alert("‘Multi-line Notes with User/Date Stamp’ questions must exist alone in a repeating block subsection; additional questions cannot be moved into a subsection that contains a ‘Multi-line Notes with User/Date Stamp’ question, and a ‘Multi-line Notes with User/Date Stamp’ question cannot be added/moved to a subsection that contains other questions.");
					}
				}
			}
		if(relocationAllowed){
        	var choice = confirm("Please press OK to confirm the relocate operation.");
	        if (choice) {
	            var eltToMove = $j($j(targetEltLinkInPopUp).parents("li").get(0));
	            // for batch entry, on relocation of a fieldRow from batched subsection to unbatch subsection, or in the other way
	            var sourceEltId = "";
	            var source = $j(targetEltLinkInPopUp).find("span.hiddenEltFieldRowIdSpan").html();
	            if(source != null){
	             sourceEltId = source.substring(source.indexOf("_") + 1, source.indexOf(":"));
	            }
	            
	            switch (targetEltType) {
	                case 'subSection':
	                    var targetContainer = $j($j("#" + targetEltId).find("ul.sortableFieldRows").get(0));
	                    $j(targetContainer).append(eltToMove);
	                    
	                    
	                    /** After relocation if the target subsection is getting one fieldRow, append the group Icon to it. 
	                     * On the other hand if the source is having no questions remove the group icon from it**/
	                    var sourceField = $j(targetEltLinkInPopUp).find("span.hiddenEltFieldRowIdSpan").html();
	                    var sourceSubsectionEltId = sourceField.substring(sourceField.indexOf(":") + 1, sourceField.length);
	                    var sourceChildEltsCount = $j("#" + sourceSubsectionEltId).find("." + fbRef.nbsPageElementCssClass).length;
	                    if(sourceChildEltsCount == 0){
	                    	$j($j($j("#" + sourceSubsectionEltId).find("div.header div.controls a.groupFieldsLink")).get(0)).addClass("noDisplay");
	                    	$j($j($j("#" + sourceSubsectionEltId).find("div.header div.controls a.groupFieldsLink")).get(0)).attr("title", "Group all Fields Rows in SubSection");
        					$j($j($j($j("#" + sourceSubsectionEltId).find("div.header div.controls a.groupFieldsLink")).get(0)).find("img").get(0)).attr("src", "link.gif").attr("alt", "Group all Fields Rows in SubSection").attr("title", "Group all Fields Rows in SubSection");;
        					$j($j("#" + sourceSubsectionEltId).find("div.body UL.sortableFieldRows").get(0)).removeClass("batchonly");
        					$j($j("#" + sourceSubsectionEltId).find("div.body UL.sortableFieldRows").get(0)).addClass("nonbatch");
	                    	$j($j($j("#" + sourceSubsectionEltId).parent()).find("div.subSection").get(0)).css("background","#DCE7F7 none repeat scroll 0 0");
	    		            $j($j($j("#" + sourceSubsectionEltId).parent()).find("div.subSection").get(0)).css("border","1px solid #BACEEE");
	    		            $j($j($j("#" + sourceSubsectionEltId).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper a.repeatingBlockLink").remove();
	                    }
	                    JPageBuilder.updateBatchUidRelocatedFieldRow(sourceSubsectionEltId,sourceChildEltsCount,targetEltId,sourceEltId, function(data){});
	                    var targetChildEltsCount = $j("#" + targetEltId).find("." + fbRef.nbsPageElementCssClass).length;
	                    if(targetChildEltsCount == 1){
	                    	//($j("#" + targetEltId).find("div.header div.controls a.groupFieldsLink")).append('<img alt="Group all Fields Rows in SubSection" src="link.gif"/>&nbsp;')
	                    	$j($j($j("#" + targetEltId).find("div.header div.controls a.groupFieldsLink")).get(0)).removeClass("noDisplay");
	                    	$j($j("#" +targetEltId).find("div.body UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
	                    }
	                    break;
	                    
	                case 'section':
	                    var targetContainer = $j($j("#" + targetEltId).find("ul.sortableSubSections").get(0));
	                    $j(targetContainer).append(eltToMove);
	                    break;
	                    
	                case 'tab':
	                    var targetContainer = $j($j("#" + targetEltId).find("ul.sortableSections").get(0));
	                    $j(targetContainer).append(eltToMove);
	                    break;
	            }
	            closePopup();
	        }
	        
	        displayStatusMsg("Element was relocated successfully.")
        }
		}
    }
    
    importQuestionsDialogCallback = function(jsonQuestionsString,subSectionId,childEltsCount)
    {
    	var fieldRowCnt = 0;
    	var csv = "";
        var containerElt = fbRef.getActiveContainerElt();
        var jsonObj = eval('(' + jsonQuestionsString + ')');
		var notesComponent = false;
        for (var i in jsonObj) {
            if (jsonObj[i].pageElementUid != undefined) {
                $j($j(containerElt).find("ul.sortableFieldRows").get(0)).append(getFieldRow(jsonObj[i]));
                csv += jsonObj[i].pageElementUid + ",";
				mapJson[jsonObj[i].questionIdentifier]=jsonObj[i];
				//remove class nonbatch for not being able to drop a question when the question imported is of type 1019:
				if(jsonObj[i].elementComponentType=="1019"){
					$j($j("#" +subSectionId).find("div.body UL.sortableFieldRows").get(0)).removeClass("nonbatch");
					$j($j("#" +subSectionId).find("UL.sortableFieldRows").get(0)).addClass("batchonly");
					notesComponent=true;
				}
		
            }
        }
		
		
        JPageBuilder.updateBatchUidForImportedFieldRow(csv, subSectionId, function(data){});
        // If no of fieldRows was 0 and new fieldRows are added, append the group icon to the subsection.
        if(childEltsCount == 0){
        	$j($j($j("#" + subSectionId).find("div.header div.controls a.groupFieldsLink")).get(0)).removeClass("noDisplay");
        	$j($j("#" +subSectionId).find("div.body UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
        }
		if(notesComponent)//to avoid moving a question of type note (nbs_component_uid = 1019) outside of the subsection
			$j($j("#" +subSectionId).find("div.body UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'parent');
      
        
        displayStatusMsg("Questions were imported successfully.")
    }
    
    isUniqueElementName = function(eltType, eltName, eltId) {
        var isUnique = true;
        eltName = jQuery.trim(eltName.toLowerCase());
        
        switch(eltType.toLowerCase()) {
            case 'tab':
                var liElts = $j("ul.ui-tabs-nav").find("a");
                for (var i = 0; i < liElts.length; i++) {
                    var href = jQuery.trim($j(liElts[i]).attr("href"));
                    var id = href.substring(href.lastIndexOf("/")+1, href.length);
                    if (id != eltId && eltName == jQuery.trim($j(liElts[i]).html()).toLowerCase()) {
                        isUnique = false;
                        break;
                    }        
                }
                break;
            
            case 'section':
                var secElts = $j("div.section");
                for (var i = 0; i < secElts.length; i++) {
                    var sectionHeaderElt = $j(secElts[i]).find("div.header").get(0);
                    var title = $j($j(sectionHeaderElt).find("span.title").get(0)).html();
                    if ($j(secElts[i]).attr("id") != eltId && eltName == jQuery.trim(title.toLowerCase())) {
                        isUnique = false;
                        break;
                    }         
                } 
                break;
                
            case 'subsection':
                var subsecElts = $j("div.subSection");
                for (var i = 0; i < subsecElts.length; i++) {
                    var sectionHeaderElt = $j(subsecElts[i]).find("div.header").get(0);
                    var title = $j($j(sectionHeaderElt).find("span.title").get(0)).html();
                    if ($j(subsecElts[i]).attr("id") != eltId && eltName == jQuery.trim(title.toLowerCase())) {
                        isUnique = false;
                        break;
                    }         
                } 
                break;
                
            case 'blockname':
                var blockNameElts = $j("div.subSection");
                for (var i = 0; i < blockNameElts.length; i++) {
                	var sectionHeaderElt = $j(blockNameElts[i]).find("div.header").get(0);
                    var title = $j($j(sectionHeaderElt).find("span.blockName").get(0)).html();
                    if ($j(blockNameElts[i]).attr("id") != eltId && eltName == jQuery.trim(title.toLowerCase())) {
                        isUnique = false;
                        break;
                    }         
                } 
                break;
            case 'datamartcolumnname':
                var fieldRows = $j("div.fieldRow");
                for (var i = 0; i < fieldRows.length; i++) {
                	var sectionHeaderElt = $j(fieldRows[i]).find("div.header").get(0);
                    var title = $j($j(sectionHeaderElt).find("span.dataMartColumnNm").get(0)).html();
                    if ($j(fieldRows[i]).attr("id") != eltId && eltName == jQuery.trim(title.toLowerCase())) {
                        isUnique = false;
                        break;
                    }         
                } 
                break;  
                
                
                
            default:
                alert(eltType + " not supported");
        } // switch
        
        return isUnique;
    }
    
    manageElementDialogCallBack = function(eltsJson) {
        var action = fbRef.getLatestActionRequested();
        // update the page dispaly and feed backmessages for add and edit.
        if (action != "view") {
	        var containerElt = fbRef.getActiveContainerElt();
	        var clickedEltType = fbRef.getActiveClickedEltType();
	        var eltDisplayName = "";
	
	        var jsonObj = eval('(' + eltsJson + ')');
	        for (var i in jsonObj) {
	            if (jsonObj[i].elementLabel != null && jsonObj[i].elementLabel != undefined && 
	                   clickedEltType != undefined && clickedEltType != null ) {
	                eltDisplayName = getDisplayName(jsonObj[i].elementType);                   
	                switch (clickedEltType.toLowerCase()) {
	                    case 'tab':
	                        var tabHandle = null, tabBody = null;
	                        tabHandle = getTabHandle(jsonObj[i], true);
	                        tabBody = getTabBody(jsonObj[i], true);
	                        
	                        switch (action) {
	                            case 'add':
	                                // insert the items in appropriate place
	                                $j($j(this.canvas).find("ul.ui-tabs-nav").get(0)).append(tabHandle);
	                                $j($j(this.canvas).find("div#tabContainer").get(0)).append(tabBody);
	                                
	                                // activate this tab.
	                                $j($j(tabHandle).find("a").get(0)).click();
	                                break;
	                            
	                            case 'edit':
	                                $j($j("li.ui-tabs-selected").find("a").get(0)).html(jsonObj[i].elementLabel);
	                                break;
	                        }
	                        break;
	                    
	                    case 'section':
	                        switch (action) {
	                            case 'add':
	                                if (containerElt != null) {
	                                    var currentElt = getSection(jsonObj[i]);
	                                    $j($j(containerElt).find("ul.sortableSections").get(0)).append(currentElt);                                                                        
	                                }
	                                break;
	                           
	                            case 'edit':
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.title").get(0)).html(jsonObj[i].elementLabel);
	                                break;
	                        }
	                        break;
	                    
	                    case 'subsection':
	                        switch (action) {
	                            case 'add':
	                                if (containerElt != null) {
	                                    //var currentElt = getSubSection(jsonObj[i]);
	                                	
	                                	var pageElt = jsonObj[i];
	                                	var publishInd = pageElt.isPublished;
	                                    var batchUid = pageElt.questionGroupSeqNbr;
	                                	var cssClass = "subSection" + " " + fbRef.nbsPageElementCssClass;
	                                    var html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\"> <div class=\"content\"> <span class=\"ieVerticalAlignHelper\"></span> <a class=\"toggleLink\" href=\"javascript:void(0)\"><img src=\"minus_sign.gif\" alt=\"Collapse\"/></a> &nbsp; Subsection: <span class=\"title\">" + pageElt.elementLabel + "</span><span class=\"blockName\" style=\"display:none\">"+pageElt.blockName+"</span></div><div class=\"controls\">  &nbsp; <a class=\"importQuestionLink\" title=\"Add/Import Element(s)\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add/Import Element(s)\" title=\"Add/Import Element(s)\"/> </a> &nbsp; <a title=\"View Subsection\" class=\"viewSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Subsection\"/> </a> &nbsp; <a title=\"Edit Subsection\" class=\"editSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Subsection\" title=\"Edit Subsection\"/> </a> &nbsp; <a class=\"relocateSubSectionLink\" href=\"javascript:void(0)\" title=\"Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a class=\"groupFieldsLink noDisplay\" title=\"Group All Questions in Subsection as Batch\" href=\"javascript:void(0)\"> <img src=\"link.gif\" alt=\"Group All Questions in Subsection as Batch\" title=\"Group All Questions in Subsection as Batch\"/> </a> <a title=\"Delete Subsection\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Subsection\" title=\"Delete Subsection\"/> </a> </div> </div> <div class=\"body\"> <ul class=\"sortableFieldRows\">" + "</ul></div> </div> </li>";
	                                    var currentElt = jQuery(html);
	                                    var subSectionElt = $j(currentElt).find(".subSection").get(0);
	                                    handleEvents(subSectionElt, 'subSection',publishInd, batchUid);
	                                	
	                                    $j($j(containerElt).find("ul.sortableSubSections").get(0)).append(currentElt);
	                                    $j($j("#" + jsonObj[i].pageElementUid).find("div.body UL.sortableFieldRows").get(0)).addClass("nonbatch");
	                                }
	                                break;
	                           
	                            case 'edit':
	                            	//alert($j($j("#" + jsonObj[i].pageElementUid).find("div.body").get(0)).html());
	                            	
	                            	//$j($j("#" + jsonObj[i].pageElementUid).find("div.body UL.sortableFieldRows").get(0)).css("background","#BBBBBB");
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.title").get(0)).html(jsonObj[i].elementLabel);
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.blockName").get(0)).html(jsonObj[i].blockName);
	                                
	                                break;
	                        }
	                        break;
	                        
	                    case 'question':    
	                    case 'fieldrow': // static elements are also handled here..
	                        switch (action) {
	                            case 'add': // applies only to static elements and not to questions at this time.
	                                if (containerElt != null) {
	                                    var currentElt = getFieldRow(jsonObj[i]);
	                                    $j($j(containerElt).find("ul.sortableFieldRows").get(0)).append(currentElt);                                                                        
	                                }
	                                break;
	                           
	                            case 'edit':
	                                if (jsonObj[i].questionIdentifier != null && jsonObj[i].questionIdentifier != "") {
	                                    var labelPart = jsonObj[i].elementLabel + " (" + jsonObj[i].questionIdentifier + ")";    
	                                }
	                                else {
	                                    var labelPart = jsonObj[i].elementType + ": " + jsonObj[i].elementLabel;
	                                }
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.title").get(0)).html(labelPart);
	                                
	                                
	                                var dataMartColumnNm="";
	                                if (jsonObj[i].dataMartColumnNm != null && jsonObj[i].dataMartColumnNm != "") {
	                                    dataMartColumnNm = jsonObj[i].dataMartColumnNm;   
	                                }
	                                
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.dataMartColumnNm").get(0)).html(dataMartColumnNm);
	                                
	                                
	                                
									mapJson[jsonObj[i].questionIdentifier].elementComponentType=jsonObj[i].elementComponentType;
	                                break;
	                        }
	                        break;                    
	                } // switch
	            } // if
	        } // for
	
	        displayStatusMsg(eltDisplayName + " was " + action + "ed successfully.")
        }
    }
    
    displayModalDialog = function(url, winHeight) {
        var o = new Object();
        o.opener = self;
        var dialogFeatures = "";
        if (winHeight != null && winHeight != undefined) {
            var dialogFeatures = "dialogWidth:710px;dialogHeight:" + winHeight + "px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
        }
        else {
            var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
        }
      //  window.showModalDialog(url, o, dialogFeatures);
        
        var modWin = openWindow(url, o,dialogFeatures, null, "");
        
        return false;
    }
    
    displayStatusMsg = function(msg) {
        $j("div#pageBuilderInfoMessagesBlock").html(msg);
        $j("div#pageBuilderInfoMessagesBlock").removeClass("hiddenElement").addClass("infoBox").addClass("success");
        $j('div#pageBuilderInfoMessagesBlock').animate({
		    opacity: 0.25
		  }, 2500, function() {
            $j("div#pageBuilderInfoMessagesBlock").html("&nbsp;");
            $j("div#pageBuilderInfoMessagesBlock").addClass("hiddenElement").removeClass("infoBox").removeClass("success");
            $j("div#pageBuilderInfoMessagesBlock").css("opacity", 1);
		  });
    }
    
    getDisplayName = function(eltType) {
        switch(eltType.toLowerCase()) {
            case 'tab':
                return 'Tab';
                break;
            
            case 'section':
                return 'Section';
                break;

            case 'subsection':
                return 'Subsection';
                break;

            case 'question':
                return 'Question';
                break;
                
            case 'fieldrow':
                return 'Element';
                break;

            default:
                return eltType;
                break;
        }
    }
    
    getElementLabel = function(eltId, eltType) {
        var jQueryId = "#" + eltId;
        var retVal = "";
        switch (eltType.toLowerCase()) {
            case 'section':
            case 'subsection':
            case 'fieldrow':
                retVal = $j($j(jQueryId).find("span.title").get(0)).html();
                break;    
        }
        
        return retVal;
    } 
    
    manageRepeatingBlockCancel = function(eltsJson, elementIds) {
        var action = fbRef.getLatestActionRequested();
        // update the page dispaly and feed backmessages for add and edit.
        if (action != "view") {
	        var containerElt = fbRef.getActiveContainerElt();
	        var clickedEltType = fbRef.getActiveClickedEltType();
	        var eltDisplayName = "";
	
	        var jsonObj = eval('(' + eltsJson + ')');
	        for (var i in jsonObj) {
	            if (jsonObj[i].elementLabel != null && jsonObj[i].elementLabel != undefined && 
	                   clickedEltType != undefined && clickedEltType != null ) {
	                eltDisplayName = getDisplayName(jsonObj[i].elementType);                   
	                switch (clickedEltType.toLowerCase()) {
	                    case 'tab':
	                        var tabHandle = null, tabBody = null;
	                        tabHandle = getTabHandle(jsonObj[i], true);
	                        tabBody = getTabBody(jsonObj[i], true);
	                        
	                        switch (action) {
	                            case 'add':
	                                // insert the items in appropriate place
	                                $j($j(this.canvas).find("ul.ui-tabs-nav").get(0)).append(tabHandle);
	                                $j($j(this.canvas).find("div#tabContainer").get(0)).append(tabBody);
	                                
	                                // activate this tab.
	                                $j($j(tabHandle).find("a").get(0)).click();
	                                break;
	                            
	                            case 'edit':
	                                $j($j("li.ui-tabs-selected").find("a").get(0)).html(jsonObj[i].elementLabel);
	                                break;
	                        }
	                        break;
	                    
	                    case 'section':
	                        switch (action) {
	                            case 'add':
	                                if (containerElt != null) {
	                                    var currentElt = getSection(jsonObj[i]);
	                                    $j($j(containerElt).find("ul.sortableSections").get(0)).append(currentElt);                                                                        
	                                }
	                                break;
	                           
	                            case 'edit':
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.title").get(0)).html(jsonObj[i].elementLabel);
	                                break;
	                        }
	                        break;
	                    
	                    case 'subsection':
	                        switch (action) {
	                            case 'add':
	                                if (containerElt != null) {
	                                    //var currentElt = getSubSection(jsonObj[i]);
	                                	
	                                	var pageElt = jsonObj[i];
	                                	var publishInd = pageElt.isPublished;
	                                    var batchUid = pageElt.questionGroupSeqNbr;
	                                	var cssClass = "subSection" + " " + fbRef.nbsPageElementCssClass;
	                                    var html = "<li> <div class=\"" + cssClass + "\" id=\"" + pageElt.pageElementUid + "\"> <div class=\"header\"> <div class=\"content\"> <span class=\"ieVerticalAlignHelper\"></span> <a class=\"toggleLink\" href=\"javascript:void(0)\"><img src=\"minus_sign.gif\" alt=\"Collapse\"/></a> &nbsp; Subsection: <span class=\"title\">" + pageElt.elementLabel + "</span><span class=\"blockName\" style=\"display:none\">"+pageElt.blockName+"</span></div> <div class=\"controls\">  &nbsp; <a class=\"importQuestionLink\" title=\"Add/Import Element(s)\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add/Import Element(s)\" title=\"Add/Import Element(s)\"/> </a> &nbsp; <a title=\"View Subsection\" class=\"viewSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Subsection\"/> </a> &nbsp; <a title=\"Edit Subsection\" class=\"editSubsectionLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Subsection\" title=\"Edit Subsection\"/> </a> &nbsp; <a class=\"relocateSubSectionLink\" href=\"javascript:void(0)\" title=\"Relocate\"> <img src=\"doc_page.jpg\" alt=\"Relocate\" title=\"Relocate\"/> </a> &nbsp; <a class=\"groupFieldsLink\" title=\"Group All Questions in Subsection as Batch\" href=\"javascript:void(0)\">  </a> <a title=\"Delete Subsection\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Subsection\" title=\"Delete Subsection\"/> </a> </div> </div> <div class=\"body\"> <ul class=\"sortableFieldRows\">" + "</ul></div> </div> </li>";
	                                    var currentElt = jQuery(html);
	                                    var subSectionElt = $j(currentElt).find(".subSection").get(0);
	                                    handleEvents(subSectionElt, 'subSection', publishInd, batchUid);
	                                	
	                                    $j($j(containerElt).find("ul.sortableSubSections").get(0)).append(currentElt);                                                                        
	                                }
	                                break;
	                           
	                            case 'edit':
	                            	$j($j($j("#" + jsonObj[i].pageElementUid).parent()).find("div.subSection div.header").get(0)).find("div.content span.ieVerticalAlignHelper a.repeatingBlockLink").remove();
	                            	$j($j($j("#" + jsonObj[i].pageElementUid).parent()).find("div.subSection").get(0)).css("background","#DCE7F7 none repeat scroll 0 0");
	            		            $j($j($j("#" + jsonObj[i].pageElementUid).parent()).find("div.subSection").get(0)).css("border","1px solid #BACEEE");
	                            	$j($j($j("#" + jsonObj[i].pageElementUid).find("div.header div.controls a.groupFieldsLink").get(0)).find("img").get(0)).attr("src", "link.gif").attr("alt", "Un-Group all Fields Rows in SubSection").attr("title", "Un-Group all Fields Rows in SubSection");
	                            	$j($j("#" + jsonObj[i].pageElementUid).find("div.body UL.sortableFieldRows").get(0)).sortable('option', 'containment', 'document');
	                            	var csv = elementIds;
	                            	JPageBuilder.updateBatchUidForUnBatch(csv, function(data){});
	                                break;
	                        }
	                        break;
	                        
	                    case 'question':    
	                    case 'fieldrow': // static elements are also handled here..
	                        switch (action) {
	                            case 'add': // applies only to static elements and not to questions at this time.
	                                if (containerElt != null) {
	                                    var currentElt = getFieldRow(jsonObj[i]);
	                                    $j($j(containerElt).find("ul.sortableFieldRows").get(0)).append(currentElt);                                                                        
	                                }
	                                break;
	                           
	                            case 'edit':
	                                if (jsonObj[i].questionIdentifier != null && jsonObj[i].questionIdentifier != "") {
	                                    var labelPart = jsonObj[i].elementLabel + " (" + jsonObj[i].questionIdentifier + ")";    
	                                }
	                                else {
	                                    var labelPart = jsonObj[i].elementType + ": " + jsonObj[i].elementLabel;
	                                }
	                                $j($j("#" + jsonObj[i].pageElementUid).find("span.title").get(0)).html(labelPart);
	                                break;
	                        }
	                        break;                    
	                } // switch
	            } // if
	        } // for
	
	       // displayStatusMsg(eltDisplayName + " was " + action + "ed successfully.")
        }
 }
 
}

 /**
* CheckNoteComponentWithoutRepeatingSubsection:  this method is called on submit of an edit to warn the user
if there's any note component (1019) that it is not in a repeating section (batch_table_appear_ind_cd <> 'Y').
*/

function checkNoteComponentWithoutRepeatingSubsection(){

	var warnChoice = true;

	$j.each(mapJson, function( key, value ) {
		var value = key;

		var componentType = mapJson[key].elementComponentType;

		if(componentType =="1019"){

			var subSections = $j(".subSection");

			for(var i =0; i< subSections.length; i++){
				if($j(subSections[i]).html().indexOf(key)!=-1){
					if($j(subSections[i]).find('.repeatingBlockLink').length==0){
						var elementLabel = mapJson[key].elementLabel;
						var questionIdentifier = key;
						var message = "Please fix the following error. The subsection containing "+elementLabel+" ("+questionIdentifier+") is not indicated as a repeating block. ‘Multi-line Notes with User/Date Stamp’ questions must exist alone in a repeating block subsection.";
						warnChoice = false;
						alert(message);
						
						
					}
				}
			}
		}
	});

	return warnChoice;
}

