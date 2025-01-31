/**
 * Title:        double batch entry
 * Description:  does the client side presentation processing of nested data
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       10/12/2001 Jay Kim
 * @modified     10/12/2001 Jay Kim
 * @version      1.0.0, 10/12/2001
 */



/**
 * Description:	updates the
 *
 * param @type	name of of the nested type on the page
 */

function PopupPassTheInnerBack(){
	
	//check for unsubmitted fields
	var inputNodes = document.getElementsByTagName("input");
	var selectNodes = document.getElementsByTagName("select");
	var textareaNodes = document.getElementsByTagName("textarea");
	var nestedError = false;
			
			for(var i=0; i < inputNodes.length; i++)	{
				
					if(inputNodes.item(i).isNested && inputNodes.item(i).value!=""){
						nestedError=true;
					}
				
			}
	
			// Process all <select> elements. We need it for validating
				// ID Type drop down box.
			for(var i=0; i<selectNodes.length; i++ ) {
				
				if(selectNodes.item(i).isNested && selectNodes.item(i).value!=""){
					var def = selectNodes.item(i).getAttribute("default");
					if(def==null)
						nestedError=true;
				}
			}
			for(var i=0; i<textareaNodes.length; i++ ) {
				
				if(textareaNodes.item(i).isNested && textareaNodes.item(i).value!=""){
						nestedError=true;
				}
			}

	if(nestedError){
		var nestedErrorTD = getElementByIdOrByName("error2");
		if( nestedErrorTD ) 
			nestedErrorTD.setAttribute("className", "error");
		document.location.href="#top";	
	} else{
	//this is the success condition
		var parent = window.opener;
		var parentDoc = parent.document;

		var parentDataNode = parentDoc.getElementById("PopupDataResult");	
		var childDataNode = getElementByIdOrByName("nestedElementsHiddenFieldSusceptibility");
		var temp = parentDataNode.value;

		//augment the parent data node with data created in the child window
		parentDataNode.value = temp.replace(/\$\$\$/,childDataNode.value);
		//	need to update the history box of the parent

		parentDataNode.onfocus();
		//	then close the window
		window.close();
	}
}

function PopupCancelChildWindow(){
	var parent = window.opener;
	var parentDoc = parent.document;
	var parentDataNode = parentDoc.getElementById("PopupDataResult");	
	var temp = parentDataNode.value;
	var target = "";
	var confirmMsg;

	if(arguments.length > 0)
	target = arguments[0];
	if(arguments.length > 1)
	   confirmMsg = arguments[1];
	else
	   confirmMsg = "Are you sure you want to cancel?";

	if (confirm(confirmMsg)){
		parentDataNode.value = temp.replace(/\$\$\$/,popupBackupData);
		parentDataNode.onfocus();
		//	then close the window
		window.close();
	} else
		return;
}


