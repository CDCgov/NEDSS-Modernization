

/**
 * Description:	function shows or hides rows under the mpr based on the id of the mpr
 *		nested rows will have attribute mpr with value of the mpr
 * param @sMpr	string that holds the id value of the mpr
 */

function searchResultsControlNestedRows(sMpr, oSwitchImage){
	
	var trNodes = searchResultsTable.getElementsByTagName("tr");
	
	//switch image
		if(oSwitchImage.src.search(/minus_sign.gif/)==-1){
			// flip the icon to minus
			oSwitchImage.src = "minus_sign.gif";
			oSwitchImage.alt = "Collapse";
	
		} else {
			
			// flip the icon to plus
			oSwitchImage.src = "plus_sign.gif";
			oSwitchImage.alt = "Expand";
		}
	
	for (var i=0; i < trNodes.length; i++) {
		if(getCorrectAttribute(trNodes[i],"mpr",trNodes[i].mpr) == sMpr){
			
			
			if(trNodes[i].className=="none"){
				trNodes[i].setAttribute("className", "visible");
				trNodes[i].setAttribute("class", "visible");
				
				// flip the icon to minus
				
				
			} else {
				trNodes[i].setAttribute("className", "none");
				trNodes[i].setAttribute("class", "none");
				
				// flip the icon to plus
				
			}
		}
		
	}
	
}

function searchResultsRevisionShowOrHide(sMpr, oSwitchImage,count){
	
	var trNodes = searchResultsTable.getElementsByTagName("tr");
	
	//switch image
		if(oSwitchImage.src.search(/minus_sign.gif/)==-1){
			// flip the icon to minus
			oSwitchImage.src = "minus_sign.gif";
			oSwitchImage.alt = "Collapse";
		} else {
			
			// flip the icon to plus
			oSwitchImage.src = "plus_sign.gif";
			oSwitchImage.alt = "Expand";
		}
	
	for (var i=0; i < trNodes.length; i++) {
		if(trNodes[i].mpr == sMpr){
			
			
			if(trNodes[i].className=="none"){
				trNodes[i].setAttribute("className", "visible");
				if(count ==1)
					trNodes[i].style.backgroundColor="#dce7f7";
				// flip the icon to minus
				
				
			} else {
				trNodes[i].setAttribute("className", "none");
				// flip the icon to plus
				
			}
		}
		
	}
	
}
 
