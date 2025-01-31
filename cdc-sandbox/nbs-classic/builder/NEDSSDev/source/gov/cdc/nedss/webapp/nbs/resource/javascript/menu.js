//---------------------------------------------------------------------------------------------//
// Global variables and event handlers
//---------------------------------------------------------------------------------------------//
var browser = new Browser();
var currentItem = null;

//---------------------------------------------------------------------------------------------//
// function Browser()
//---------------------------------------------------------------------------------------------//
function Browser()
{
	var ua = navigator.userAgent;

	this.isNS    = ((ua.indexOf("Netscape6/") >= 0) || (ua.indexOf("Gecko") >= 0));
	this.isIE    = ((ua.indexOf("MSIE") >= 0) && (!this.isNS));
}

//---------------------------------------------------------------------------------------------//
// function activateMenu(event, menuId)
//---------------------------------------------------------------------------------------------//
function activateMenu(event, menuId)
{
	var button = (browser.isIE) ? window.event.srcElement : event.currentTarget;
	button.blur();
	
	if (button == currentItem)
	{
		return false;
	}

  	if (button.menu == null)
	{
		button.menu = getElementByIdOrByName(menuId);
	}
	
	if (currentItem != null)
	{
    	resetMenu(currentItem);
	}

	if (button != currentItem)
	{
    	openSubMenu(button);
    	currentItem = button;
	}
	else
	{
    	currentItem = null;
	}
	
	return false;
}

//---------------------------------------------------------------------------------------------//
// function menuMouseover(event, menuId)
//---------------------------------------------------------------------------------------------//
function menuMouseover(event, menuId)
{
	var button = (browser.isIE) ? window.event.srcElement : button = event.currentTarget;
	
	if (currentItem != button)
	{
    	activateMenu(event, menuId);
	}
}

//---------------------------------------------------------------------------------------------//
// function openSubMenu(button)
//---------------------------------------------------------------------------------------------//
function openSubMenu(button)
{
	var x = getOffset(button, "left");
	var y = getOffset(button, "top") + button.offsetHeight;
	
	button.className += " GlobalNavOptionActive";

	if (browser.isIE)
	{
		x += button.offsetParent.clientLeft;
		y += button.offsetParent.clientTop;
	}

	button.menu.style.left = x + "px";
	button.menu.style.top  = y + "px";
	button.menu.style.visibility = "visible";
}

//---------------------------------------------------------------------------------------------//
// function resetMenu(button)
//---------------------------------------------------------------------------------------------//
function resetMenu(button)
{
	if (button.menu == null)
	{
		return;
	}
	
	button.className = (button.className != null) ? button.className.replace("GlobalNavOptionActive", '') : button.className;
	closeSubMenu(button.menu);
	button.menu.style.visibility = "hidden";
}

//---------------------------------------------------------------------------------------------//
// function closeMenu(menu)
//---------------------------------------------------------------------------------------------//
function closeSubMenu(menu)
{
	if (menu == null || menu.activeItem == null)
	{
		return;
	}
	
	if (menu.activeItem.subMenu != null)
	{
    	closeSubMenu(menu.activeItem.subMenu);
    	menu.activeItem.subMenu.style.visibility = "hidden";
    	menu.activeItem.subMenu = null;
	}
	
	menu.activeItem.className = (menu.activeItem.className != null) ? menu.activeItem.className.replace("SubMenuItemActive", '') : menu.activeItem.className;
	menu.activeItem = null;
}

//---------------------------------------------------------------------------------------------//
// function menuMouseout(event)
//---------------------------------------------------------------------------------------------//
function menuMouseout(event)
{
	var oElement = (browser.isIE) ? (window.event.toElement) : ((event.relatedTarget != null) ? ((event.relatedTarget.tagName) ? event.relatedTarget : event.relatedTarget.parentNode) : event.target.parentNode);
	
	if (currentItem == null)
	{
		return;
	}

	if (getParent(oElement, "SubMenu") == null)
	{
		resetMenu(currentItem);
		currentItem = null;
	}
}

//---------------------------------------------------------------------------------------------//
// function getParent(oElement, sElementClass)
//---------------------------------------------------------------------------------------------//
function getParent(oElement, sElementClass)
{
	while (oElement != null)
	{
		if (oElement.tagName != null && ((sElementClass == "SubMenu" && oElement.tagName == "DIV") || (sElementClass == "SubMenuItem" && oElement.tagName == "A")))
		{
			aClassName = oElement.className.split(" ");
			for (var x = 0; x < aClassName.length; x++)
			{
				if (aClassName[x] == sElementClass)
				{
					return oElement;
				}
			}
		}
		oElement = oElement.parentNode;
	}
	return oElement;
}

//---------------------------------------------------------------------------------------------//
// function getOffset(oElement, sDirection)
//---------------------------------------------------------------------------------------------//
function getOffset(oElement, sDirection)
{
	var iOffset = (sDirection == "top") ? oElement.offsetTop : oElement.offsetLeft;
	
	if (oElement.offsetParent != null)
	{
		iOffset += getOffset(oElement.offsetParent, sDirection);
	}
	
	return iOffset;
}

function dialog(url, height, width) 
{
    var features = new String();
    var chasm = screen.availWidth;
    var mount = screen.availHeight;
    
    features += "height=" + height + ",";
    features += "width=" + width + ",";
    features += "left=" + ((chasm - width - 10) * .5) + ",";
    features += "top=" + ((mount - height - 30) * .5) + ",";
    features += "center=yes,"
    features += ", scrollbars";
    features += ", resizable";
    
    var oPopUpWindow = window.open(url, "TrackerDialog", features);
    oPopUpWindow.focus();	
}