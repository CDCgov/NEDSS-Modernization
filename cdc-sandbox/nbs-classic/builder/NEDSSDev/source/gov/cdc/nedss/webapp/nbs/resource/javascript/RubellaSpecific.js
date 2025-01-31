function OutsideConditionalCheck(me)
{
	var rubNode = getElementByIdOrByName(me);
	
	
	if(rubNode.value=="Y")
		return true;
	else 
		return false;
}


function RUB044(me)
{
	var trRUB048 = getElementByIdOrByName("trRUB048");
	var RUB044node =  getElementByIdOrByName(me);
	
	if(RUB044node.value == "Y")
	{
		trRUB048.setAttribute("className", "visible");
	} 
	else 
	{
		nestedElementsControllerClearInternalInputs(trRUB048);
		trRUB048.setAttribute("className", "none");							
	}
	
}


function RUB049(me)
{
	var trRUB053 = getElementByIdOrByName("trRUB053");
	var RUB049node =  getElementByIdOrByName(me);
	
	if(RUB049node.value == "Y")
	{
		trRUB053.setAttribute("className", "visible");
	} 
	else 
	{
		nestedElementsControllerClearInternalInputs(trRUB053);
		trRUB053.setAttribute("className", "none");							
	}
	
}

function RUB054(me)
{
	var trRUB058 = getElementByIdOrByName("trRUB058");
	var RUB054node =  getElementByIdOrByName(me);
	
	if(RUB054node.value == "Y")
	{
		trRUB058.setAttribute("className", "visible");
	} 
	else 
	{
		nestedElementsControllerClearInternalInputs(trRUB058);
		trRUB058.setAttribute("className", "none");							
	}
	
}