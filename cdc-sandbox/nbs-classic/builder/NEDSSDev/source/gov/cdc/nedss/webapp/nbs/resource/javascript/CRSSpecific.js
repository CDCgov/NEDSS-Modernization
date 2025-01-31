function OutsideConditionalCheck(me)
{
	var rubNode = getElementByIdOrByName(me);
	
	
	if(rubNode.value=="Y")
		return true;
	else 
		return false;
}




function CRS060(me)
{
	var trCRS064 = getElementByIdOrByName("trCRS064");
	var CRS060node =  getElementByIdOrByName(me);
	
	if(CRS060node.value == "Y")
	{
		trCRS064.setAttribute("className", "visible");
	} 
	else 
	{
		nestedElementsControllerClearInternalInputs(trCRS064);
		trCRS064.setAttribute("className", "none");							
	}
	
}