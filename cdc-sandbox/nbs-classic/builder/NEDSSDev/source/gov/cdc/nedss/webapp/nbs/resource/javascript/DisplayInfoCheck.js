function displayInfoCheck()
{
	
	var dispMsgExists = getElementByIdOrByName("DisplayInformationExists") == null ? "" : getElementByIdOrByName("DisplayInformationExists").value;
	if(dispMsgExists != ""){
		var error1Node = getElementByIdOrByName("error1");
		error1Node.className = "infoGreen";
		error1Node.innerText=dispMsgExists;

		//$j("#error2").innerText=dispMsgExists;
		// $j("#error").css("color", "green");
			$j("#error1").innerText=dispMsgExists;
		// $j("#error2").innerText=" TEST TEST TEST TEST TEST";
		// $j("#error2").css("color", "green");
		//alert("error test4");
	}
}