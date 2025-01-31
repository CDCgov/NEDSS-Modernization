/**
 *  implements the tab action to properly hide or display div tags holding content
 *
 *  @param pTabName		the name of the tab selected
 *
 *
 *
 *  @return nothing
 *
 */

function Toggle(pTabName) {

//var submitTime= getElementByIdOrByName("submitTime");
//var beforeXSPLoad = getElementByIdOrByName("beforeXSPLoad");

//	if(submitTime != null && beforeXSPLoad != null && submitTime.value != null && beforeXSPLoad.value != null)
//	{
//	  var timenow = new Date();
//	    var hittSubmitdiff = timenow.valueOf() - submitTime.value;
//	     var loadxspdiff =  timenow.valueOf() - beforeXSPLoad.value;
//	}
	
	if(CheckForChildWindow()){

		//activate functions in tabSwitchFunctionArray
		
		
				if(gXSLType=="input"){
					for (var i=0;i<tabSwitchFunctionArray.length;i++){
							if(tabSwitchFunctionArray[i]!=null){
								var temp = tabSwitchFunctionArray[i]();
							}
					}
				}



		//	hide all the divs
		var divNodes = document.getElementsByTagName("div");
		var tabPattern = /tabControl/i;
		//var buttonPattern = /buttonbar/i;

		for (var i=0; i < divNodes.length; i++)	{
			if (tabPattern.test(divNodes.item(i).id))	{
				divNodes.item(i).setAttribute("className", "none");
				divNodes.item(i).setAttribute("class", "none");
				
				//	change the attributes of the selected tab to inactive attributes
				if (getCorrectAttribute(divNodes.item(i),"selected") == "true") {
					//	need to get the stripped out name of the tab
					var selectedDivName = divNodes.item(i).id.replace(/tabControl/i, "");
					//	top tab bar
					var leftImgNode = getElementByIdOrByName("tabImgLefttop" + selectedDivName);
					leftImgNode.setAttribute("src","corner_left_lb.gif");
					leftImgNode.setAttribute("alt","");
					
					var rightImgNode = getElementByIdOrByName("tabImgRighttop" + selectedDivName);
					rightImgNode.setAttribute("src","corner_right_lb.gif");
					rightImgNode.setAttribute("alt","");
					
					var tdNode = getElementByIdOrByName("tabTdBgcolortop" + selectedDivName);
					tdNode.setAttribute("bgColor", "#3A6295");
					//	bottom tab bar
					var leftImgNodeBottom = getElementByIdOrByName("tabImgLeftbottom" + selectedDivName);
					leftImgNodeBottom.setAttribute("src","corner_left_lb_inverted.gif");
					leftImgNodeBottom.setAttribute("alt","");
					
					var rightImgNodeBottom = getElementByIdOrByName("tabImgRightbottom" + selectedDivName);
					rightImgNodeBottom.setAttribute("src","corner_right_lb_inverted.gif");
					rightImgNodeBottom.setAttribute("alt","");
					
					var tdNodeBottom = getElementByIdOrByName("tabTdBgcolorbottom" + selectedDivName);
					tdNodeBottom.setAttribute("bgColor", "#3A6295");



					divNodes.item(i).setAttribute("selected", "false");
				}

			}
			/*
			var buttonNode = getElementByIdOrByName("buttonbartop" + pTabName);
			if(buttonNode!=null) // don't hide button bar if this tab doesn't have button bar
				if (buttonPattern.test(divNodes.item(i).id))	{
					divNodes.item(i).setAttribute("className", "none");
				}
			*/
		}



		//	update the corners and the background of the new tab selected


		//	top tab bar
		var leftImgNode = getElementByIdOrByName("tabImgLefttop" + pTabName);
		leftImgNode.setAttribute("src","corner_left.gif");
		leftImgNode.setAttribute("alt","");
		
		var rightImgNode = getElementByIdOrByName("tabImgRighttop" + pTabName);
		rightImgNode.setAttribute("src","corner_right.gif");
		rightImgNode.setAttribute("alt","");
		
		var tdNode = getElementByIdOrByName("tabTdBgcolortop" + pTabName);
		tdNode.setAttribute("bgColor", "#003470");
		//	bottom tab bar
		var leftImgNodeBottom = getElementByIdOrByName("tabImgLeftbottom" + pTabName);
		leftImgNodeBottom.setAttribute("src","corner_left_inverted.gif");
		leftImgNodeBottom.setAttribute("alt","");
		
		var rightImgNodeBottom = getElementByIdOrByName("tabImgRightbottom" + pTabName);
		rightImgNodeBottom.setAttribute("src","corner_right_inverted.gif");
		rightImgNodeBottom.setAttribute("alt","");
		
		var tdNodeBottom = getElementByIdOrByName("tabTdBgcolorbottom" + pTabName);
		tdNodeBottom.setAttribute("bgColor", "#003470");


		var divNode = getElementByIdOrByName("tabControl" + pTabName);
		divNode.setAttribute("className", "visible");
		divNode.setAttribute("class", "visible");
		divNode.setAttribute("selected", "true");
		/*
		//	display the button bar for the displayed tab
		var buttonNode = getElementByIdOrByName("buttonbartop" + pTabName);
		if(buttonNode!=null){
			buttonNode.setAttribute("className", "visible");
			buttonNode.setAttribute("selected", "true");
			var buttonNode = getElementByIdOrByName("buttonbarbottom" + pTabName);
				buttonNode.setAttribute("className", "visible");
			buttonNode.setAttribute("selected", "true");
		}
		*/
		//control the display of anchor elements
		
		var tableNodes = document.getElementsByTagName("table");
		for (var i=0; i < tableNodes.length; i++)	{
			if(getCorrectAttribute(tableNodes[i],"anchor")) {
				if(getCorrectAttribute(tableNodes[i],"anchor")==pTabName){
					tableNodes[i].className="visible";
					tableNodes[i].setAttribute("class","visible");
					
				}
				else{
					tableNodes[i].className="none";	
					tableNodes[i].setAttribute("class","none");
				
				}
			}
		}
		
		
		
	}
}
