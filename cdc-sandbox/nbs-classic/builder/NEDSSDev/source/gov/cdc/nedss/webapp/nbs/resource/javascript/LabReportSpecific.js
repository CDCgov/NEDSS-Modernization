//var ChildWindowHandle = null;
function trackIsolate(){
var  Q329 = getElementByIdOrByName("resultedTest[i].theTrackIsolate[0].theObservationDT.cd");
var  Q329a = getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code");
var Q337 =getElementByIdOrByName("Q337");
var Q345 = getElementByIdOrByName("Q345");
var Q351 = getElementByIdOrByName("Q351");
var Q331 = getElementByIdOrByName("Q331");
	if(Q329a.value =='Y'){
	Q329.value='LAB329';
	if(Q337!=null)
		Q337.className = "visible";
	if(Q345!=null)
		Q345.className = "visible";
	if(Q351!=null)
		Q351.className = "visible";
	if(Q331!=null)
		Q331.className = "visible";
	}
	else {
		if(Q329a.value =='N'){
			Q329.value='LAB329';
		}
	else if(Q337!=null && Q345!=null && Q351!=null)
		{
			Q329.value="NOLAB329ADDED";
	}	Q337.className = "none";
			Q345.className = "none";
			Q351.className = "none";
			Q331.className = "none";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[2].obsValueCodedDT_s[0].code").value="";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[2].obsValueCodedDT_s[0].code_textbox").value="";

			getElementByIdOrByName("resultedTest[i].theTrackIsolate[8].obsValueCodedDT_s[0].code").value="";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[8].obsValueCodedDT_s[0].code_textbox").value="";

			getElementByIdOrByName("resultedTest[i].theTrackIsolate[16].obsValueCodedDT_s[0].code").value="";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[16].obsValueCodedDT_s[0].code_textbox").value="";

			getElementByIdOrByName("resultedTest[i].theTrackIsolate[22].obsValueCodedDT_s[0].code").value="";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[22].obsValueCodedDT_s[0].code_textbox").value="";

			trackQuestions();
	}
	

}
function trackQuestions(){
	var Q332 = getElementByIdOrByName("Q332");
       	if(Q332!= null && Q332!="")
       		Q332.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code_textbox").value="";
       	
       	var Q333 = getElementByIdOrByName("Q333");
       	Q333.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[4].obsValueTxtDT_s[0].valueTxt").value="";
       		
       	var Q334 = getElementByIdOrByName("Q334");
       	Q334.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[5].obsValueDateDT_s[0].fromTime_s").value="";
       	
       	var Q335 = getElementByIdOrByName("Q335");
       	Q335.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[6].obsValueTxtDT_s[0].valueTxt").value="";

       	var Q336 = getElementByIdOrByName("Q336");
       	Q336.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code_textbox").value="";

       	var Q338 =getElementByIdOrByName("Q338");
       	Q338.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[9].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[9].obsValueCodedDT_s[0].code_textbox").value="";

	var Q339 =getElementByIdOrByName("Q339").className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[10].obsValueTxtDT_s[0].valueTxt").value="";
	var Q340 =getElementByIdOrByName("Q340").className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[11].obsValueTxtDT_s[0].valueTxt").value="";
	var Q341 =getElementByIdOrByName("Q341").className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[12].obsValueTxtDT_s[0].valueTxt").value="";
	var Q342 =getElementByIdOrByName("Q342").className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[13].obsValueTxtDT_s[0].valueTxt").value="";
	var Q343 =getElementByIdOrByName("Q343").className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[14].obsValueTxtDT_s[0].valueTxt").value="";
	var Q344 =getElementByIdOrByName("Q344").className = "none";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[15].obsValueTxtDT_s[0].valueTxt").value="";

	
       	var Q346 =getElementByIdOrByName("Q346");
       	Q346.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[17].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[17].obsValueCodedDT_s[0].code_textbox").value="";

       	var Q347 =getElementByIdOrByName("Q347");
       	Q347.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code_textbox").value="";
       	
       	var Q348 =getElementByIdOrByName("Q348");
       	Q348.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[19].obsValueTxtDT_s[0].valueTxt").value="";

       	var Q349 =getElementByIdOrByName("Q349");
       	Q349.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[20].obsValueDateDT_s[0].fromTime_s").value="";

       	var Q350 =getElementByIdOrByName("Q350");
       	Q350.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[21].obsValueDateDT_s[0].fromTime_s").value="";

	var Q352 =getElementByIdOrByName("Q352");
       	Q352.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[23].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[23].obsValueCodedDT_s[0].code_textbox").value="";
       	
       	
	var Q353 =getElementByIdOrByName("Q353");
       	Q353.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code_textbox").value="";
	
	var Q354 =getElementByIdOrByName("Q354");
       	Q354.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[25].obsValueTxtDT_s[0].valueTxt").value="";
	
	var Q355 =getElementByIdOrByName("Q355");
       	Q355.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code_textbox").value="";
	
	var Q356 =getElementByIdOrByName("Q356");
       	Q356.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s").value="";
	
	var Q357 =getElementByIdOrByName("Q357");
       	Q357.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s").value="";
	
	var Q358 =getElementByIdOrByName("Q358");
       	Q358.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code_textbox").value="";
	
	var Q359 =getElementByIdOrByName("Q359");
       	Q359.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code").value="";
	getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code_textbox").value="";
	
	var Q360 =getElementByIdOrByName("Q360");
       	Q360.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[31].obsValueTxtDT_s[0].valueTxt").value="";
	
	var Q361 =getElementByIdOrByName("Q361");
       	Q361.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s").value="";
	
	var Q362 =getElementByIdOrByName("Q362");
       	Q362.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[33].obsValueDateDT_s[0].fromTime_s").value="";
       	var Q335Text = getElementByIdOrByName("Q335Text");
	Q335Text.className = "none";
}



function isolateReceived(){
      		var Q335Text = getElementByIdOrByName("Q335Text");
      		Q335Text.className = "none";
	if(getElementByIdOrByName("resultedTest[i].theTrackIsolate[2].obsValueCodedDT_s[0].code").value != ""){
	var isIsolateReceived = getElementByIdOrByName("resultedTest[i].theTrackIsolate[2].obsValueCodedDT_s[0].code").value;
	
        if(isIsolateReceived=='Y'){
        	var Q332 = getElementByIdOrByName("Q332");
        	Q332.className = "none"; 
        	getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code_textbox").value="";
       	
        	var Q333 =getElementByIdOrByName("Q333");	
		Q333.className = "none"; 
 	     	getElementByIdOrByName("resultedTest[i].theTrackIsolate[4].obsValueTxtDT_s[0].valueTxt").value="";
       		
 	      	var Q334 = getElementByIdOrByName("Q334");
 	      	Q334.className = "visible";
 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[5].obsValueDateDT_s[0].fromTime_s").value="";
 	      	
 	       	var Q335 = getElementByIdOrByName("Q335");
	       	Q335.className = "visible";

 	       	var Q336 = getElementByIdOrByName("Q336");
       		Q336.className = "visible";
      		var Q335Text = getElementByIdOrByName("Q335Text");
      		Q335Text.className = "visible";
      		
	
	}
	else if(isIsolateReceived=='N'){
	        	var Q332 = getElementByIdOrByName("Q332");
	        	Q332.className = "visible";
	        	getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code").value="";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code_textbox").value="";

	        	var Q334 = getElementByIdOrByName("Q334");
			Q334.className = "none";
	 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[5].obsValueDateDT_s[0].fromTime_s").value="";
			
			var Q335 = getElementByIdOrByName("Q335");
			Q335.className = "none";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[6].obsValueTxtDT_s[0].valueTxt").value="";
	
			var Q336 = getElementByIdOrByName("Q336");
	       		Q336.className = "none";
	 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code").value="";
			getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code_textbox").value="";
	}
	else{
		var Q332 = getElementByIdOrByName("Q332");
		Q332.className = "none"; 
        	getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code_textbox").value="";

		var Q333 =getElementByIdOrByName("Q333");	
		Q333.className = "none"; 
 	     	getElementByIdOrByName("resultedTest[i].theTrackIsolate[4].obsValueTxtDT_s[0].valueTxt").value="";

		var Q334 = getElementByIdOrByName("Q334");
		Q334.className = "none";
 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[5].obsValueDateDT_s[0].fromTime_s").value="";

		var Q335 = getElementByIdOrByName("Q335");
		Q335.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[6].obsValueTxtDT_s[0].valueTxt").value="";

		var Q336 = getElementByIdOrByName("Q336");
		Q336.className = "none";
 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code_textbox").value="";
		var Q335Text = getElementByIdOrByName("Q335Text");

	}//for unknown
	}
	else{
		var Q332 = getElementByIdOrByName("Q332");
		Q332.className = "none"; 
        	getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code_textbox").value="";

		var Q333 =getElementByIdOrByName("Q333");	
		Q333.className = "none"; 
 	     	getElementByIdOrByName("resultedTest[i].theTrackIsolate[4].obsValueTxtDT_s[0].valueTxt").value="";

		var Q334 = getElementByIdOrByName("Q334");
		Q334.className = "none";
 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[5].obsValueDateDT_s[0].fromTime_s").value="";

		var Q335 = getElementByIdOrByName("Q335");
		Q335.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[6].obsValueTxtDT_s[0].valueTxt").value="";

		var Q336 = getElementByIdOrByName("Q336");
		Q336.className = "none";
 	      	getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[7].obsValueCodedDT_s[0].code_textbox").value="";
	}
}

function isolateNotRecdOther(){
	var Q333 =getElementByIdOrByName("Q333");
	var Q333Val = getElementByIdOrByName("resultedTest[i].theTrackIsolate[3].obsValueCodedDT_s[0].code");
	if(Q333Val.value!=null && Q333Val.value =='OTH')
	   Q333.className = "visible"; 
	else
	   Q333.className = "none"; 
}
function pulseNetIsolate(){
    var test = getElementByIdOrByName("resultedTest[i].theTrackIsolate[8].obsValueCodedDT_s[0].code");
    var pulseNetQuestions =  getElementByIdOrByName("Q338");
    if(test.value=='Y')
 	pulseNetQuestions.className = "visible"; 
    else{
  	pulseNetQuestions.className = "none"; 
    	getElementByIdOrByName("resultedTest[i].theTrackIsolate[9].obsValueCodedDT_s[0].code_textbox").value="";
    	getElementByIdOrByName("resultedTest[i].theTrackIsolate[9].obsValueCodedDT_s[0].code").value="";
    }
    pulseNetPFGE();
}
function  pulseNetPFGE(){
	var pulseNetVal = getElementByIdOrByName("resultedTest[i].theTrackIsolate[9].obsValueCodedDT_s[0].code");
	if(pulseNetVal.value=='Y')
	{
		var Q339 =getElementByIdOrByName("Q339");
		Q339.className = "visible";
		var Q340 =getElementByIdOrByName("Q340");
		Q340.className = "visible";
		var Q341 =getElementByIdOrByName("Q341");
		Q341.className = "visible";
		var Q342 =getElementByIdOrByName("Q342");
		Q342.className = "visible";
		var Q343 =getElementByIdOrByName("Q343");
		Q343.className = "visible";
		var Q344 =getElementByIdOrByName("Q344");
		Q344.className = "visible";
	}
	else{
		var Q339 =getElementByIdOrByName("Q339").className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[10].obsValueTxtDT_s[0].valueTxt").value="";
		var Q340 =getElementByIdOrByName("Q340").className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[11].obsValueTxtDT_s[0].valueTxt").value="";
		var Q341 =getElementByIdOrByName("Q341").className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[12].obsValueTxtDT_s[0].valueTxt").value="";
		var Q342 =getElementByIdOrByName("Q342").className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[13].obsValueTxtDT_s[0].valueTxt").value="";
		var Q343 =getElementByIdOrByName("Q343").className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[14].obsValueTxtDT_s[0].valueTxt").value="";
		var Q344 =getElementByIdOrByName("Q344").className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[15].obsValueTxtDT_s[0].valueTxt").value="";
	}
}
function NARMSSelected(){
 	var NARMSSelected = getElementByIdOrByName("resultedTest[i].theTrackIsolate[16].obsValueCodedDT_s[0].code");
 	if(NARMSSelected.value=='Y'){
 	       	var Q346 =getElementByIdOrByName("Q346");
	       	Q346.className = "visible";
	}
	else {
	       	var Q346 =getElementByIdOrByName("Q346");
	       	Q346.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[17].obsValueCodedDT_s[0].code_textbox").value="";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[17].obsValueCodedDT_s[0].code").value="";
	      	var Q347 =getElementByIdOrByName("Q347");
	       	Q347.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code_textbox").value="";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code").value="";
	      	var Q348 =getElementByIdOrByName("Q348");
	       	Q348.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[19].obsValueTxtDT_s[0].valueTxt").value="";
	       	var Q349 =getElementByIdOrByName("Q349");
	       	Q349.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[20].obsValueDateDT_s[0].fromTime_s").value="";
	       	var Q350 =getElementByIdOrByName("Q350");
	       	Q350.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[21].obsValueDateDT_s[0].fromTime_s").value="";
	
	}
}
function IsolateToNARMS(){
 	var IsolateToNARMS = getElementByIdOrByName("resultedTest[i].theTrackIsolate[17].obsValueCodedDT_s[0].code");
 	if(IsolateToNARMS.value =='Y'){
	       	var Q348 =getElementByIdOrByName("Q348");
	       	Q348.className = "visible";
	       	var Q349 =getElementByIdOrByName("Q349");
	       	Q349.className = "visible";
	       	var Q350 =getElementByIdOrByName("Q350");
	       	Q350.className = "visible";
	       	var Q347 =getElementByIdOrByName("Q347");
	       	Q347.className = "none";
 		getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code").value="";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code_textbox").value="";
	       		
	}
	else if(IsolateToNARMS.value =='N'){
	       	var Q347 =getElementByIdOrByName("Q347");
	       	Q347.className = "visible";
	       	var Q348 =getElementByIdOrByName("Q348");
	       	Q348.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[19].obsValueTxtDT_s[0].valueTxt").value="";
	       	var Q349 =getElementByIdOrByName("Q349");
	       	Q349.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[20].obsValueDateDT_s[0].fromTime_s").value="";
	       	var Q350 =getElementByIdOrByName("Q350");
	       	Q350.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[21].obsValueDateDT_s[0].fromTime_s").value="";
	}
	else{
	       	var Q347 =getElementByIdOrByName("Q347");
	       	Q347.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code").value="";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[18].obsValueCodedDT_s[0].code_textbox").value="";
	       	var Q348 =getElementByIdOrByName("Q348");
	       	Q348.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[19].obsValueTxtDT_s[0].valueTxt").value="";
	       	var Q349 =getElementByIdOrByName("Q349");
	       	Q349.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[20].obsValueDateDT_s[0].fromTime_s").value="";
	       	var Q350 =getElementByIdOrByName("Q350");
	       	Q350.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[21].obsValueDateDT_s[0].fromTime_s").value="";
	}

}
function EIPSelected(){
	var EIPSelected = getElementByIdOrByName("resultedTest[i].theTrackIsolate[22].obsValueCodedDT_s[0].code");
	if(EIPSelected.value=='Y'){
		 	var Q352 =getElementByIdOrByName("Q352");
	       		Q352.className = "visible";
	}else{
	 	var Q352 =getElementByIdOrByName("Q352");
       		Q352.className = "none";
       		getElementByIdOrByName("resultedTest[i].theTrackIsolate[23].obsValueCodedDT_s[0].code").value="";
       		getElementByIdOrByName("resultedTest[i].theTrackIsolate[23].obsValueCodedDT_s[0].code_textbox").value="";
       		var Q353 =getElementByIdOrByName("Q353");
		Q353.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code_textbox").value="";
		var Q354 =getElementByIdOrByName("Q354");
		Q354.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[25].obsValueTxtDT_s[0].valueTxt").value="";
		var Q355 =getElementByIdOrByName("Q355");
		Q355.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code_textbox").value="";
		var Q356 =getElementByIdOrByName("Q356");
		Q356.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s").value="";
		var Q357 =getElementByIdOrByName("Q357");
		Q357.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s").value="";
		var Q358 =getElementByIdOrByName("Q358");
		Q358.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code_textbox").value="";
		var Q359 =getElementByIdOrByName("Q359");
		Q359.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code_textbox").value="";
		var Q360 =getElementByIdOrByName("Q360");
		Q360.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[31].obsValueTxtDT_s[0].valueTxt").value="";
		var Q361 =getElementByIdOrByName("Q361");
		Q361.className = "none"; 
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s").value="";
		var Q362 =getElementByIdOrByName("Q362");
	       	Q362.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[33].obsValueDateDT_s[0].fromTime_s").value="";
	}
}
function furtherEIPTesting(){
	var Q354 =getElementByIdOrByName("Q354");
       	Q354.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[25].obsValueTxtDT_s[0].valueTxt").value= "";
  	
  	
	var furtherEIPTesting = getElementByIdOrByName("resultedTest[i].theTrackIsolate[23].obsValueCodedDT_s[0].code");
	if(furtherEIPTesting.value==''){

		var Q353 =getElementByIdOrByName("Q353");
		Q353.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code_textbox").value="";

		var Q354 =getElementByIdOrByName("Q354");
		Q354.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[25].obsValueTxtDT_s[0].valueTxt").value="";

		var Q355 =getElementByIdOrByName("Q355");
		Q355.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code_textbox").value="";

		var Q356 =getElementByIdOrByName("Q356");
		Q356.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s").value="";

		var Q357 =getElementByIdOrByName("Q357");
		Q357.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s").value="";

		var Q358 =getElementByIdOrByName("Q358");
		Q358.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code_textbox").value="";
	}
	if(furtherEIPTesting.value=='N'){
		var Q353 =getElementByIdOrByName("Q353");
		Q353.className = "visible";
		var Q355 =getElementByIdOrByName("Q355");
		Q355.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code_textbox").value="";
		var Q356 =getElementByIdOrByName("Q356");
		Q356.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s").value="";
		var Q357 =getElementByIdOrByName("Q357");
		Q357.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s").value="";
		var Q358 =getElementByIdOrByName("Q358");
		Q358.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code_textbox").value="";
		changeLAB358();
	}else if(furtherEIPTesting.value=='Y'|| furtherEIPTesting.value=='ISOLATNO'){
		var Q355 =getElementByIdOrByName("Q355");
		Q355.className = "visible";
		var Q356 =getElementByIdOrByName("Q356");
		Q356.className = "visible";
		var Q357 =getElementByIdOrByName("Q357");
		Q357.className = "visible";
		var Q358 =getElementByIdOrByName("Q358");
		Q358.className = "visible";

		var Quest =getElementByIdOrByName("Q353");
		Quest.className = "none";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code").value="";
		getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code_textbox").value="";
		
	}
	if(furtherEIPTesting.value=='ISOLATNO'){
		var Q352 =getElementByIdOrByName("Q353");
		Q352.className = "visible";
	}
	
}
function changeLAB358(){
  var LAB358 = getElementByIdOrByName("resultedTest[i].theTrackIsolate[29].obsValueCodedDT_s[0].code");
  if(LAB358.value=='Y'){
  	var Q359 =getElementByIdOrByName("Q359");
       	Q359.className = "visible";
  	var Q360 =getElementByIdOrByName("Q360");
       	Q360.className = "none";
  	var Q361 =getElementByIdOrByName("Q361");
       	Q361.className = "visible";
  	var Q362 =getElementByIdOrByName("Q362");
       	Q362.className = "visible";
  }
  else{
  	var Q359 =getElementByIdOrByName("Q359");
  	Q359.className = "none";
  	getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code").value= "";
  	getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code_textbox").value= "";
  	var Q360 =getElementByIdOrByName("Q360");
       	Q360.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[31].obsValueTxtDT_s[0].valueTxt").value= "";
  	var Q361 =getElementByIdOrByName("Q361");
       	Q361.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s").value= "";
  	var Q362 =getElementByIdOrByName("Q362");
       	Q362.className = "none";
       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[33].obsValueDateDT_s[0].fromTime_s").value= "";
  }
}
function checkOtherForLab360(){
	var val = getElementByIdOrByName("resultedTest[i].theTrackIsolate[30].obsValueCodedDT_s[0].code");
	if(val.value =='OTH'){
	  	var Q360 =getElementByIdOrByName("Q360");
	       	Q360.className = "visible";
	}
	else{
	  	var Q360 =getElementByIdOrByName("Q360");
	       	Q360.className = "none";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[31].obsValueTxtDT_s[0].valueTxt").value= "";;
	}
	

}
function otherResonsForLab353(){
var val = getElementByIdOrByName("resultedTest[i].theTrackIsolate[24].obsValueCodedDT_s[0].code");
	if(val.value =='OTH'){
	  	getElementByIdOrByName("Q355").className = "none";
	  	getElementByIdOrByName("Q354").className = "visible";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code").value= "";
	       	getElementByIdOrByName("resultedTest[i].theTrackIsolate[26].obsValueCodedDT_s[0].code_textbox").value= "";
	}
	else if(val.value !='OTH' && val.value!=""){
	  	getElementByIdOrByName("Q354").className = "none";
	 	getElementByIdOrByName("resultedTest[i].theTrackIsolate[25].obsValueTxtDT_s[0].valueTxt").value= "";
  	}
}

function dateValidation(element, futureTest){
var tdErrorCell = getTdErrorCell( element) ;
		var labelList = new Array();
		if (isblank(element.value) && (element.required && element.required=="true"))	{
		       	if( !getCorrectAttribute(element, "fieldLabel",element.fieldLabel))
			{
			   if(element.errorCode)
			      errorText = makeErrorMsg(element.errorCode, labelList);
			   else
			      errorText = makeErrorMsg('ERR001', labelList);
			}
			else
			{
			   if(element.errorCode)
			     errorText = makeErrorMsg(element.errorCode, labelList.concat(getCorrectAttribute(element, "fieldLabel",element.fieldLabel)));
			   else
			     errorText = makeErrorMsg('ERR001', labelList.concat(getCorrectAttribute(element, "fieldLabel",element.fieldLabel)));
			}

			if( tdErrorCell.innerText == "" )
				tdErrorCell.innerText = errorText;
			else {
				tdErrorCell.innerText = errorText;
			}
			tdErrorCell.className = "error";

			return false;

		} else if (isblank(element.value)){

			return true;

		} else {

			// First check if it is in mm/dd/yyyy format

			if (!isDate( element.value ) ) {

				if( !getCorrectAttribute(element, "fieldLabel",element.fieldLabel) )

					errorText = makeErrorMsg('ERR011', labelList);
				else {

					errorText = makeErrorMsg('ERR003', labelList.concat(getCorrectAttribute(element, "fieldLabel",element.fieldLabel)));
					//errorText = makeErrorMsg('ERR003', labelList.concat(element.fieldLabel, 'replacement1','replacement2', 'replacement3', 'replacement4', 'replacement5'));
				}


				if( tdErrorCell.innerText == "" )
				  tdErrorCell.innerText = errorText;
				else
				  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
				tdErrorCell.className = "error";

				return false;

			// if is in mm/dd/yyyy format, check if it in a right range.
			} else	if ((CompareDateStrings(element.value, "12/31/1875") == -1) ||
			             (!(futureTest=="true") && (CompareDateStringToToday(element.value) == 1)))  {

				errorText = makeErrorMsg('ERR004', labelList.concat(getCorrectAttribute(element, "fieldLabel",element.fieldLabel)));

				if( tdErrorCell.innerText == "" )
				  tdErrorCell.innerText = errorText;
				else
				  tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
				tdErrorCell.className = "error";

				return false;
		 }
		 
	}
 }
		 
function changeLAB361(element){
		var expecteReshipDate = getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s");
		if(trim(expecteReshipDate.value)!=""
				&& getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s")!=null 
				&& getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s")!=null
				&& getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s")!=null){
		     	var reshipDate =getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s");
		     	var shipdate =getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s");
		     	dateValidation(element, "true");
		     	if(expecteReshipDate.value<reshipDate.value){
				var tdErrorCell = getTdErrorCell( getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s"));
				 tdErrorCell.className = "error";
				 tdErrorCell.innerText = "The Expected Reship Date cannot be before Expected Ship Date or Actual ship Date";
				 return false;
			}
			if(expecteReshipDate.value<shipdate.value){
				var tdErrorCell = getTdErrorCell( getElementByIdOrByName("resultedTest[i].theTrackIsolate[32].obsValueDateDT_s[0].fromTime_s"));
				 tdErrorCell.className = "error";
				 tdErrorCell.innerText = "The Expected Reship Date cannot be before Expected Ship Date or Actual ship Date";
				 return false;
			}
		}
}
function changeLAB362(element){
     	var Value33 =getElementByIdOrByName("resultedTest[i].theTrackIsolate[33].obsValueDateDT_s[0].fromTime_s").value;
	if(trim(Value33)!=""
			&& getElementByIdOrByName("resultedTest[i].theTrackIsolate[33].obsValueDateDT_s[0].fromTime_s")!=null
			&& getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s")!=null){
		dateValidation(element, false);
	     	var Value28 =getElementByIdOrByName("resultedTest[i].theTrackIsolate[28].obsValueDateDT_s[0].fromTime_s").value;
		if(Value33 <Value28){
			var tdErrorCell = getTdErrorCell( getElementByIdOrByName("resultedTest[i].theTrackIsolate[33].obsValueDateDT_s[0].fromTime_s"));
			 tdErrorCell.className = "error";
			 tdErrorCell.innerText = "The Actual Reship Date cannot be before Actual ship Date";
		}
	}
}

function changeLAB356(element){
     	var Value356 =getElementByIdOrByName("resultedTest[i].theTrackIsolate[27].obsValueDateDT_s[0].fromTime_s").value;
	if(trim(Value356)!=""){
	dateValidation(element, "true");
	}
}
function changeLAB349(element){
     	var Value349 =getElementByIdOrByName("resultedTest[i].theTrackIsolate[20].obsValueDateDT_s[0].fromTime_s").value;
	if(trim(Value349)!=""){
	dateValidation(element, "true");
	}
}
/*function populateData(){
	var susceptiblityInd = getElementByIdOrByName("susceptiblityInd");
	if(organismInd.value=="Y")
	{
		susceptiblityInd.className = "visible";
	}
	resultedTest[i].theTrackIsolate.obsValueTxtDT_s[0].valueTxt = "true";
}*/

function copyData()
{
	var lab277 = getElementByIdOrByName("LAB277");
	var entityTable = getElementByIdOrByName("entity-table-Org-OrderingFacilityUID");
	var reportOrg ="";
	var result="";
	var UID="";
	var etable = getElementByIdOrByName("entity-table-Org-OrderingFacilityUID");
	var hiddenNodes = etable.getElementsByTagName("input");


	if(lab277.checked)
	{

		if(getElementByIdOrByName("Org-ReportingOrganizationUID-values")!= null)
		{
			reportOrg = (getElementByIdOrByName("Org-ReportingOrganizationUID-values")).value;
					reportOrg = reportOrg.substring(0, (reportOrg.lastIndexOf("^")-1));
			UID = (getElementByIdOrByName("Org-ReportingOrganizationUID")).value;
			var entitytable = getElementByIdOrByName("entity-table-Org-ReportingOrganizationUID");
			for(var i=0;i<hiddenNodes.length;i++)
			{
				if(hiddenNodes.item(i).type=="button")
				{
				  hiddenNodes.item(i).disabled=true;
				}
				if(hiddenNodes.item(i).type=="text")
				{
				  hiddenNodes.item(i).value="";
				  hiddenNodes.item(i).disabled=true;
				}
			}


			var spans = entitytable.getElementsByTagName("span");
			for(var i=0;i<spans.length;i++){
				if(spans.item(i).id=="entity.completeOrgSearchResult")
				{
					reportOrg = spans.item(i).innerHTML;
				}
			}
			copyDataGeneral(entityTable, reportOrg, UID);
		}
		else
		{
			var reportingOrg = getElementByIdOrByName("Org-ReportingOrganizationUIDHiddenValue").value;
			UID = getElementByIdOrByName("reportingOrganizationUID").value;

			copyDataGeneral(entityTable, reportingOrg, UID);


		}

	}
	else
	{
		reportOrg="";
		UID = "";
		for(var i=0;i<hiddenNodes.length;i++)
		{
			if(hiddenNodes.item(i).type=="button")
			{
			  hiddenNodes.item(i).disabled=false;
			}
			if(hiddenNodes.item(i).type=="text")
			{
			  hiddenNodes.item(i).disabled=false;
			}
		}
		//clearDoubleBatchEnteries();

	}


}
function copyDataGeneral(entityTable, reportOrg, UID)
{
	var orgSearchResult = "";



	var parseString = "";
	var spans = entityTable.getElementsByTagName("span");

	for(var i=0;i<spans.length;i++)
	{
		if(spans.item(i).id=="entity.completeOrgSearchResult")
		{
			orgSearchResult = reportOrg;
			spans.item(i).innerHTML = orgSearchResult;
			parseString += orgSearchResult;
		}
		// check for empty condition
		if(spans.item(i).innerHTML=="")
			spans.item(i).innerHTML ="&nbsp;&nbsp;";

	}


	var inputs = entityTable.getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++){
		if(inputs.item(i).getAttribute("type")=="hidden" && inputs.item(i).getAttribute("mode")=="uid")
		{
			inputs.item(i).value=UID;
		}
		else if(inputs.item(i).getAttribute("type")=="hidden")
		{
			inputs.item(i).value=parseString;
		}
	}

}
function getLabDataWait(id,target, parentID)
{

	window.anyObject2=id;
	var timeoutInt = window.setTimeout("getData(anyObject2" + ",'"+ target +"','"+ parentID + "')", 1000);
}

function getLabData(id,target, parentID){

	var targetedValue = getElementByIdOrByName(target);





	if(targetedValue != null || targetedValue != "")
	{
	}




	var textBox =getElementByIdOrByName(parentID +"-textbox");

	if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	{
		var x = screen.availWidth;
		var y = screen.availHeight;

		var textBox =(getElementByIdOrByName(parentID +"-textbox")).value;

		if(textBox == "")
		{

		}

		
		       //purge target options
		        var node=targetedValue;
			var removeOpt = node.firstChild;
			while(removeOpt != null){
				var temp = removeOpt.nextSibling;
				node.removeChild(removeOpt);
				removeOpt= temp;
			}
			if(getElementByIdOrByName(node.name + "_textbox")!=null)     
				getElementByIdOrByName(node.name + "_textbox").value="";
			node.className="none";
		
		

		ChildWindowHandle = window.open("/nbs/labCodeLookupSelect?elementName="+target + '&amp;inputCd=' + textBox +'&amp;batchName=' + sParent,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		//self.focus();
		var updateNode = getElementByIdOrByName(target);
		id.focus();
	}
	else
		getLabDataWait(id,target, parentID);

}
function getLabTestDataWait(labCode, progAreaCode, labTestType, target)
{

	window.anyObject2=labCode;
	//var timeoutInt = window.setTimeout("getData(anyObject2" + ",'"+ labCode +"','"+ target + "')", 1000);
}

function getLabTestData(labCode, progAreaCode, labTestType, target){


	if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	{
		var x = screen.availWidth;
		var y = screen.availHeight;


		//purge target options
		var node=targeted;
		var removeOpt = node.firstChild;
		while(removeOpt != null){
			var temp = removeOpt.nextSibling;
			node.removeChild(removeOpt);
			removeOpt= temp;
		}
		if(getElementByIdOrByName(node.name + "_textbox")!=null)     
			getElementByIdOrByName(node.name + "_textbox").value="";
		node.className="none";
		
		ChildWindowHandle = window.open("/nbs/labTestsFilterServlet?labCode="+ labCode + '&amp;progAreaCode=' + progAreaCode +'&amp;labTestType=' + labTestType +'&amp;target=' + target,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		var updateNode = getElementByIdOrByName(target);

	}
	else
		getLabTestDataWait(labCode, progAreaCode, labTestType, target);


}


function labTestFilter()
{

	var code = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;
	var UID = getElementByIdOrByName("Org-ReportingOrganizationUID");
	var type = "ProgramAreaCode";
	var targetResulted = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";

	//takes care of ordered test Names
	resetBatchEntryInputElements("Test Result");

	//var targetResulted = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt\" header=\"Resulted Test Name\" parent=\"Test Result\" isNested=\"yes\" validate=\"required\" fieldLabel=\"Resulted Test Name\" errorCode=\"ERR001";

	var targetOrdered = "proxy.observationVO_s[0].theObservationDT.txt";
	//getLabTestData(labCode, progAreaCode,labTestType, target);
	var target = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";
	var labId= getElementByIdOrByName("labId").value;
	var batchInserted = getElementByIdOrByName("nestedElementsHiddenFieldTest Result");
	
	var node=getElementByIdOrByName(target);
	if(node!=null){
		node.className="none";
		var removeOpt = node.firstChild;
		while(removeOpt != null){
			var temp = removeOpt.nextSibling;
			node.removeChild(removeOpt);
			removeOpt= temp;
		}
		if(getElementByIdOrByName(node.name + "_textbox")!=null)     
			getElementByIdOrByName(node.name + "_textbox").value="";
		node.className="none";
	}
	//purge target options
	node=getElementByIdOrByName(targetOrdered);
	if(node!=null){
		node.className="none";
		var removeOpt = node.firstChild;
		while(removeOpt != null){
			var temp = removeOpt.nextSibling;
			node.removeChild(removeOpt);
			removeOpt= temp;
		}
		if(getElementByIdOrByName(node.name + "_textbox")!=null)     
			getElementByIdOrByName(node.name + "_textbox").value="";
		node.className="none";
	}
	//purge target options
	node=getElementByIdOrByName(targetResulted);
	if(node!=null){
		node.className="none";
		var removeOpt = node.firstChild;
		while(removeOpt != null){
			var temp = removeOpt.nextSibling;
			node.removeChild(removeOpt);
			removeOpt= temp;
		}
		if(getElementByIdOrByName(node.name + "_textbox")!=null)     
			getElementByIdOrByName(node.name + "_textbox").value="";
		node.className="none";
	}
		
	var isCallValid = "true";
	if(code ==""){
		isCallValid = false;
	}
	if(isCallValid)
	{
	if(labId != "")
		getLabCodeLookup(type, code, target, targetOrdered, targetResulted, labId);
	}
			

}

function labTestFilterFromMorb()
{

	var code =getElementByIdOrByName("morbidityReport.theObservationDT.progAreaCd").value;
	var conditionCodeHiddenNode = getElementByIdOrByName("morbidityReport.theObservationDT.cd").value;
	var UID = getElementByIdOrByName("Org-ReportingOrganizationUID");
	var type = "ProgramAreaCode";
	var targetResulted = "labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt";
	var targetOrdered =  "";//"proxy.observationVO_s[0].theObservationDT.txt\" onchange=\"getOrderedTestChange();";
	//getLabTestData(labCode, progAreaCode,labTestType, target);
	var target = "labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt";
	//var labId= getElementByIdOrByName("labId").value;
	var labId="DEFAULT";
		var node=getElementByIdOrByName(targetResulted);
		if(node!=null){
			node.className="none";
			var removeOpt = node.firstChild;
			while(removeOpt != null){
				var temp = removeOpt.nextSibling;
				node.removeChild(removeOpt);
				removeOpt= temp;
			}
			if(getElementByIdOrByName(node.name + "_textbox")!=null)     
				getElementByIdOrByName(node.name + "_textbox").value="";
			node.className="none";
	}
	if(conditionCodeHiddenNode !="")
		getLabCodeLookup(type, code, target, targetOrdered, targetResulted, labId, null, conditionCodeHiddenNode);

}




function test(resultedTestName){


	var testNode = getElementByIdOrByName("TestName");
	var codedNode = getElementByIdOrByName("Coded Result Value");

	var trNode = codedNode.parentNode;
	while(trNode.nodeName!="TR")
	{
			trNode = trNode.parentNode;
	}
	if(testNode.value=="Y"){
		//hide the entire tr
		trNode.className="none";
	}else{
		//show the entire tr
		trNode.className="visible";
	}

}
function codeLookup(button, type)
{
	
	var orgSearchResult = "";
	var parseString ="";
	var code = "";
	var labelList = new Array();
	var errorText = "";

	var errorNode = getTdErrorCell(button);
	var targetOrdered = "";
	var targetResulted="";
	var programAreaCode = "";
	
	setAttributeClass(errorNode, "error");
	var UID = "";
	var reportUID = "";
	var programAreaCode ="";
	var target ="";
	var morbType = getElementByIdOrByName("morbtype");
	var conditionCode = "";
	if(getElementByIdOrByName("conditionCode")!=null)
		conditionCode = getElementByIdOrByName("conditionCode").value;
	if(morbType != null)
	{
		target = "labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt";
 	}
	else
	{
		var TestNameText = getElementByIdOrByName("OrderedDisplay");
		if(TestNameText!= null)
		{
			var spanNodes = TestNameText.getElementsByTagName("span");
			for(var i=0;i<spanNodes.length;i++)
			{
				setText(spanNodes.item(i),"&nbsp;&nbsp;");
			}
			setAttributeClass(TestNameText, "none");
			TestNameText.value = "";
		}
		target = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";
		
		
	}
	var entityTable = getElementByIdOrByName(type);

	if(type == "entity-table-Org-ReportingOrganizationUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Org-ReportingOrganizationUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
		if(morbType != null)
		{
			targetResulted = "labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt";
			targetOrdered = "";
			programAreaCode = getElementByIdOrByName("morbidityReport.theObservationDT.progAreaCd").value;
			conditionCode = getElementByIdOrByName("morbidityReport.theObservationDT.cd").value;
		
		}
		else
		{
			var programAreaNode = getElementByIdOrByName("ProgramAreaCd1");
			if(programAreaNode!= null)
			{
				programAreaCode = programAreaNode.value;
			}
			else
			{
				programAreaCode = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;
			}
			targetResulted = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";
			targetOrdered = "proxy.observationVO_s[0].theObservationDT.txt";
		}
		var batchInserted = getElementByIdOrByName("nestedElementsHiddenFieldTest Result");

		
		if(batchInserted!= null)
		{	
			var valueofBatch = batchInserted.value;
			
			var deletedBatch= valueofBatch.replace(/statusCd~^/g, "statusCd"+ nvDelimiter+"I");
		
			var deletedBatch2 = deletedBatch.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");
			
			//Lab CSF Isolate specific code
			deleteIsolateTracks();
			
			batchInserted.value = deletedBatch2;
			updateBatchEntryHistoryBox("Test Result");
			resetBatchEntryInputElements("Test Result");
		}
		reportUID = (getElementByIdOrByName("Org-ReportingOrganizationUID")).value;
	}
	if(type == "entity-table-Org-HospitalUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Org-HospitalUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
	}
	else if(type == "entity-table-Org-OrderingFacilityUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Org-OrderingFacilityUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +13), type.length);
	}
	else if(type == "entity-table-Prov-entity.providerUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.providerUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
	}
	else if(type == "entity-table-Prov-entity.reporterUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.reporterUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
	}
	else if(type == "entity-table-Prov-entity.entityProvUID")
	{
		code = getElementByIdOrByName("entity-codeLookupText-Prov-entity.entityProvUID").value;
		UID = type.substring((type.lastIndexOf("entity-table-Org") +14), type.length);
	}

	var codeLength = code.length;
	var spans = entityTable.getElementsByTagName("span");
	for(var i=0;i<spans.length;i++)
	{
		if((spans.item(i).id=="entity.completeOrgSearchResult") ||
			(spans.item(i).id=="entity.completePersonSearchResult") ||
			(spans.item(i).id=="entity.reporterPersonSearchResult"))
		{
			if(codeLength==0)
			{
				if(code == getElementByIdOrByName("entity-codeLookupText-Org-ReportingOrganizationUID").value)
				{
					var etable = getElementByIdOrByName("entity-table-Org-OrderingFacilityUID");
					if (etable != null)
					{
						var spansOrd = etable.getElementsByTagName("span");
							for(var j1=0;j1<spansOrd.length;j1++)
							{
								if(spansOrd.item(j1).id=="entity.completeOrgSearchResult")
								{
										//spansOrd.item(j1).innerHTML = "There is no Reporting Facility selected.";
								}
							}
							var inputs = etable.getElementsByTagName("input");
							for(var j2=0;j2<inputs.length;j2++){
							if(inputs.item(j1).type=="hidden" && inputs.item(j1).mode=="uid")
							{
							   inputs.item(j1).value="";
							}
						}
					}
				}
				var uidToBeCleared = getElementByIdOrByName(UID);
				uidToBeCleared.value = "";
				setText(spans.item(i),"");
				setText(errorNode,makeErrorMsg('ERR101', labelList.concat("Code Lookup")));
				setAttributeClass(errorNode, "error");
			}
			else
			{
				setText(errorNode,"");
				getLabCodeLookup(type, code, target, targetOrdered,targetResulted, UID, reportUID, conditionCode, programAreaCode );
			}

		}
	}
}


function getLabCodeLookup(type, code, target, targetOrdered, targetResulted, UID, reportUID, conditionCode, programAreaCode){

	if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true))
	{
		var x = screen.availWidth;
		var y = screen.availHeight;
		
		//purge target options
		if(type == "entity-table-Org-ReportingOrganizationUID")
		{
			var node=getElementByIdOrByName(target);
			if(node!=null){
				node.className="none";
				var removeOpt = node.firstChild;
				while(removeOpt != null){
					var temp = removeOpt.nextSibling;
					node.removeChild(removeOpt);
					removeOpt= temp;
				}
				if(getElementByIdOrByName(node.name + "_textbox")!=null)     
					getElementByIdOrByName(node.name + "_textbox").value="";
				node.className="none";
			}
		//purge target options
			node=getElementByIdOrByName(targetOrdered);
			if(node!=null){
				node.className="none";
				var removeOpt = node.firstChild;
				while(removeOpt != null){
					var temp = removeOpt.nextSibling;
					node.removeChild(removeOpt);
					removeOpt= temp;
				}
				if(getElementByIdOrByName(node.name + "_textbox")!=null)     
					getElementByIdOrByName(node.name + "_textbox").value="";
				node.className="none";
			}
		//purge target options
			node=getElementByIdOrByName(targetResulted);
			if(node!=null){
				node.className="none";
				var removeOpt = node.firstChild;
				while(removeOpt != null){
					var temp = removeOpt.nextSibling;
					node.removeChild(removeOpt);
					removeOpt= temp;
				}
				if(getElementByIdOrByName(node.name + "_textbox")!=null)     
					getElementByIdOrByName(node.name + "_textbox").value="";
				node.className="none";
			}

		}
		var morbType = getElementByIdOrByName("morbtype");
		var dropdownChecker = "true";
		if(morbType == null)
		{
			if(programAreaCode =="" && conditionCode == ""){
				dropdownChecker = false;
		
		}
		
		}
		//ChildWindowHandle = window.open("/nbs/nedssCodeLookup?type="+ type + '&amp;code=' + code + '&amp;targetResulted=' + targetResulted + '&amp;targetOrdered=' + targetOrdered +'&amp;target=' + target + '&amp;reportUID=' + reportUID + '&amp;conditionCode=' + conditionCode + '&amp;programAreaCode=' + programAreaCode + '&amp;UID=' + UID,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		ChildWindowHandle = window.open("/nbs/nedssCodeLookup?type="+ type + '&amp;code=' + code + '&amp;targetResulted=' + targetResulted + '&amp;targetOrdered=' + targetOrdered +'&amp;target=' + target + '&amp;reportUID=' + reportUID + '&amp;conditionCode=' + conditionCode + '&amp;programAreaCode=' + programAreaCode + '&amp;UID=' + UID+'&amp;dropdownChecker='+dropdownChecker,"getData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		var updateNode = getElementByIdOrByName(targetResulted);
		var updateNodeOrdered = getElementByIdOrByName(targetOrdered);
	}
}


// to calculateReportedAge from lefNav

function calculateReportedAgeLeftNav(){

	var dobNode = getElementByIdOrByName("entity.DOB");
	var calcDOBNode = getElementByIdOrByName("calcDOB");

	var asOfDateNode = getElementByIdOrByName("entity.asofdate");

	var reportedAgeNode = getElementByIdOrByName("entity.age");
	var reportedAgeUnitsNode = getElementByIdOrByName("entity.ageUnit");
	//if dob node is not empty set the calc dob to dob
	if(dobNode.value!="" && isDate(dobNode.value)){
		calcDOBNode.value = dobNode.value;
		//calcDOB changed change the reported age
	}
	var asOfDate = new Date(asOfDateNode.value);
	var calcDOBDate = new Date(calcDOBNode.value);
	//check for error condition
	if(asOfDate<calcDOBDate)
		return;

	//figure out the reported age and units
	//don't show if calc dob is empty
	if (calcDOBNode!=null && calcDOBNode.value!="" && asOfDateNode!=null)	{

		//reported age = as of date - calc dob
		var reportedAgeMilliSec = asOfDate.getTime() - calcDOBDate.getTime();
		if(!window.isNaN(reportedAgeMilliSec)){
			var reportedAgeSeconds = reportedAgeMilliSec/1000;
			var reportedAgeMinutes = reportedAgeSeconds/60;
			var reportedAgeHours = reportedAgeMinutes/60;
			var reportedAgeDays = reportedAgeHours/24;
			var reportedAgeMonths = reportedAgeDays/30.41;
			var reportedAgeYears = reportedAgeMonths/12;

			if(isLeapYear(calcDOBDate.getFullYear())) reportedAgeMonths = Math.floor(reportedAgeDays)/30.5;

			if(Math.ceil(reportedAgeDays)<=28){
				reportedAgeNode.value=Math.floor(reportedAgeDays);
				reportedAgeUnitsNode.value="D";
			} else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)	{
				reportedAgeNode.value=Math.floor(reportedAgeMonths);
				reportedAgeUnitsNode.value="M";
			} else	{
				// get rough estimated year age
				var yearDiff = asOfDate.getFullYear() - calcDOBDate.getFullYear();
				//need to determine whether birthday has happened
				if(asOfDate.getMonth()<calcDOBDate.getMonth())
					yearDiff = yearDiff-1;
				else if(asOfDate.getMonth()==calcDOBDate.getMonth()){
					if(asOfDate.getDate()<calcDOBDate.getDate())
						yearDiff = yearDiff-1;
				}
				reportedAgeNode.value=yearDiff;//Math.floor(reportedAgeYears);
				reportedAgeUnitsNode.value="Y";
                    //this is only for leap year, if DOB is 02/29/YYYY and is leap year and is almost one year old, it should be 11 months
				if(calcDOBDate.getMonth() == 1 && calcDOBDate.getDate()==29 && reportedAgeYears > 1 && reportedAgeYears < 1.1 && isLeapYear(calcDOBDate.getFullYear()))
				{
				    currentAgeNode.innerText="11";
				    currentAgeUnitsNode.innerText="Months";
				}
			}

		} else {
			reportedAgeNode.value="";
			reportedAgeUnitsNode.value="";
		}
	}
	//clear reported age and units if calc dob is blank
	else	{
		reportedAgeNode.value="";
		reportedAgeUnitsNode.value="";
	}
	//update the type ahead textbox field
	reportedAgeUnitsNode.onclick();
}

function isLeapYear(varyear)
{
       var leapyear = false;
       if((varyear% 4) == 0) leapyear = true;
       if((varyear% 100) == 0) leapyear = false;
       if((varyear% 400) == 0) leapyear = true;
       return leapyear;

}

function isOtherRaceChecked()
{
	//return true for "checked" return false for others
	var isChecked = getElementByIdOrByName("otherRaceCd").checked;
	var visibleFlag = getElementByIdOrByName("raceDescTxt");
	if (isChecked)
		//visibleFlag.disabled = false;
		visibleFlag.setAttribute("className", "visible");
	else
	{
	        visibleFlag.setAttribute("className", "none");
	}
}


function getOrganismIndicator()
{

	////////////////clear suscept, something wrong with index, net getting set
			if(getElementByIdOrByName("index")!= null){

				var index = getElementByIdOrByName("index").value;
				if(index!=""){
					var hiddenNode = getElementByIdOrByName("nestedElementsHiddenFieldSusceptibility");
					hiddenNode.value=hiddenNode.value.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");

					
					//initialize conditional
					
						if(getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")){
							getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
							getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
						}
						updateBatchEntryHistoryBox("Susceptibility");
					
				} else { // this is uncommited batch
				
					if(getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code")){
						getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
						getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
					}
					getElementByIdOrByName("nestedElementsHiddenFieldSusceptibility").value="";
					updateBatchEntryHistoryBox("Susceptibility");
				
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].lowRange").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].highRange").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.statusCd").value="";
				//getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.statusCd").value ="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueTxtDT_s[1].valueTxt").value="";
				//clear the type ahead text boxes
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName_textbox").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code_textbox").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd_textbox").value="";
				getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.statusCd_textbox").value="";
				getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code_textbox").value="";
var  Q329a = getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code");
getElementByIdOrByName("resultedTest[i].theTrackIsolate[1].obsValueCodedDT_s[0].code_textbox").value="";

Q329a.value ="";				
trackIsolate();
}
				
				
				
				
				
		}


		/////////////////////////////////////////////////////////////////////////////////


	getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").className="none";


	var testNameCondition = getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt").value;
	var hiddenCd= getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.hiddenCd").value;
	labClia = getElementByIdOrByName("labId").value;
	var labUid = "";
	if(getElementByIdOrByName("reportingOrganizationUID").value!= "")
	{
		labUid = getElementByIdOrByName("reportingOrganizationUID").value;
	}
	else
		labUid = getElementByIdOrByName("Org-ReportingOrganizationUID").value;
	
	if(getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd")!= null)
	{	progAreaCd = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.progAreaCd").value;

	}
	else
		progAreaCd = null;
	var organismInd = getElementByIdOrByName("organismInd").value;
	
	if(testNameCondition!="")
	{
		//don't need to have no pending childwindows to perform this action
		//if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true)) {
			var x = screen.availWidth;
			var y = screen.availHeight;
		window.open("/nbs/nedssOrganismIndicatorLookup?testNameCondition="+ testNameCondition + '&amp;labClia=' + labClia + '&amp;labUid=' + labUid + '&amp;progAreaCd=' + progAreaCd, "getIndicatorData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		//}
	}
}

// this function gets the Organism Indicator for Morbidity
function getOrganismIndicatorMorb()
{

	var testNameCondition = getElementByIdOrByName("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt").value;
	
	labClia = getElementByIdOrByName("labId").value;
	var morbType = getElementByIdOrByName("morbtype").value;
	if(getElementByIdOrByName("Org-ReportingOrganizationUID")== null)
	{
		labUid = getElementByIdOrByName("reportingOrganizationUID").value;
	}

	else
	{
		labUid = getElementByIdOrByName("Org-ReportingOrganizationUID").value;
	}

	progAreaCd = getElementByIdOrByName("morbidityReport.theObservationDT.progAreaCd").value;
	
	var organismInd = getElementByIdOrByName("organismInd").value;
	
	//if(ChildWindowHandle==null || (ChildWindowHandle!=null && ChildWindowHandle.closed==true)) {
		var x = screen.availWidth;
		var y = screen.availHeight;
		
		window.open("/nbs/nedssOrganismIndicatorLookup?testNameCondition="+ testNameCondition + '&amp;morbtype=' + morbType + '&amp;labClia=' + labClia + '&amp;labUid=' + labUid + '&amp;progAreaCd=' + progAreaCd, "getIndicatorData","left=" + x + ", top=" + y + ", width=10, height=10, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
	//}

}


function getOrgLoadOnCondition()
{
	var organismIndicator = getElementByIdOrByName("organismIndicator");
	var codedValueIndicator = getElementByIdOrByName("codedResultIndicator");
	var organismInd = getElementByIdOrByName("organismInd");
	var IsolateTrackInd= getElementByIdOrByName("IsolateTrackInd"); 
	var morbType = getElementByIdOrByName("morbtype");

	if(morbType!= null)
	{

	}
	else
	{
		var susceptiblityInd = getElementByIdOrByName("susceptiblityInd");
		if(organismInd.value=="Y")
		{
			susceptiblityInd.className = "visible";
		}
		else
		{
			susceptiblityInd.className = "none";
		}

	}


	if(organismInd.value=="Y")
	{
		organismIndicator.className = "visible";
		codedValueIndicator.className = "none";
		IsolateTrackInd.className = "visible";
	}
	else
	{
		organismIndicator.className = "none";
		codedValueIndicator.className = "visible";
		if(IsolateTrackInd!=null)
			IsolateTrackInd.className = "none";
	}
}



function testResultAtLeastOne(){

	var resultTestHidden = getElementByIdOrByName("nestedElementsHiddenFieldTest Result");
	
		if (resultTestHidden.value.search(/theSusceptibilityVO.theObservationDT.statusCd~A/)>-1 )
			return false;
	
	var labelList = new Array();

	var element  = getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt");
	var errorText = makeErrorMsg('ERR113',labelList.concat(getCorrectAttribute(element, "fieldLabel",element.fieldLabel)));

	var errorMessage = "";
	var tdErrorCell = getTdErrorCell( element) ;

		   if( tdErrorCell.innerText == "" )
		   	tdErrorCell.innerText = "One of the result value fields Resulted Test Name must contain data. Please enter a result value and try again."
		   else
		        tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
	   tdErrorCell.className = "error";
	return true;
}


function testResultCodedCheck(){

	var labelList = new Array();
	
	var argumentsCaller;
	
	if(arguments.caller==undefined)
		argumentsCaller=testResultCodedCheck.caller.arguments[0];
	else
		argumentsCaller=arguments.caller[0];

	
	if(argumentsCaller=="Test Result"){

		var node = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code_textbox");
		var codedFieldLabelElement = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code");
		var codedFieldLabel =getCorrectAttribute(codedFieldLabelElement,"fieldLabel",codedFieldLabelElement.fieldLabel);
		
		
		if(node!=null && node.offsetWidth!=0){

			if(node.value!="")
				return false;

			var NRVnode = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue");

            if(NRVnode.value!="")
				return false;

			
			var NRVnodeFieldLabel = getCorrectAttribute(NRVnode,"fieldLabel",NRVnode.fieldLabel);
			
			var TRVnode = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt");
			if(TRVnode.value!="")
				return false;



			var tdErrorCell = getTdErrorCell(node) ;
			if( tdErrorCell.innerText == "" ){
				tdErrorCell.innerText = makeErrorMsg('ERR113',labelList.concat(codedFieldLabel.concat(", ")).concat(NRVnodeFieldLabel.concat(", ")).concat(NRVnodeFieldLabel));
				if(tdErrorCell.textContent!=undefined)
					tdErrorCell.textContent = makeErrorMsg('ERR113',labelList.concat(codedFieldLabel.concat(", ")).concat(NRVnodeFieldLabel.concat(", ")).concat(NRVnodeFieldLabel));
				
			}
			else{
				tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
				if(tdErrorCell.textContent!=undefined)
					tdErrorCell.textContent = tdErrorCell.innerText + "\n" + errorText;
			}
			tdErrorCell.className = "error";
			return true;
		}
	}
		return false;
}

function testResultOrganismCheck(){
        var labelList = new Array();
		
	var argumentsCaller;
	
	if(arguments.caller==undefined)
		argumentsCaller=testResultOrganismCheck.caller.arguments[0];
	else
		argumentsCaller=arguments.caller[0];
	
	
	if(argumentsCaller=="Test Result"){
		var node = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName");
		if(node!=null && node.offsetWidth!=0){

			if(node.value!="")
				return false;

			var NRVnode = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue");
			if(NRVnode.value!="")
				return false;

			var TRVnode = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt");
			if(TRVnode.value!="")
				return false;


			var tdErrorCell = getTdErrorCell(node) ;
			if( tdErrorCell.innerText == "" )
				tdErrorCell.innerText = makeErrorMsg('ERR113',labelList.concat(getCorrectAttribute(node, "fieldLabel",node.fieldLabel)).concat(getCorrectAttribute(NRVnode, "fieldLabel",NRVnode.fieldLabel)).concat(getCorrectAttribute(TRVnode, "fieldLabel",TRVnode.fieldLabel)));
			else
				tdErrorCell.innerText = tdErrorCell.innerText + "\n" + errorText;
			tdErrorCell.className = "error";
			return true;
		}
	}

	return false
}


function getOrderedTestOnLoadCondition(){
	var testName= getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt");
	if(testName.value =="")
	{
		var labId = getElementByIdOrByName("labId").value;
			
		getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt_ac_table").className="visible";
		//testName.className= "none";
		var TestNameText = getElementByIdOrByName("OrderedDisplay");
		TestNameText.className ="visible";
		if(getElementByIdOrByName("displayFacility")!=null && getElementByIdOrByName("displayFacility").value=="true")
		{
			//do nothing
		}else if(getElementByIdOrByName("OrderedTestNameTest").value=="true")
		{
			if (TestNameText != null && TestNameText.innerText == "    ")
				getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt_ac_table").className="visible";
			else
				getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt_ac_table").className="none";
			//getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt_textbox").className= "none";
			TestNameText.className ="visible";
		}
	}
	else
	{
		getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt_ac_table").className="none";
		var TestNameText = getElementByIdOrByName("OrderedDisplay");
		TestNameText.className ="visible";

	}
}
function hideObjectsOnLoad(){
var codedResultIndicatorMessage = getElementByIdOrByName("codedResultIndicatorMessage");
	if(codedResultIndicatorMessage!=null)	
	codedResultIndicatorMessage.className= "none";
} 	
function getObjectHiddenOnLoad(){	
	var currentTask = "";
	if(getElementByIdOrByName("currentTask")!= null)
		currentTask = getElementByIdOrByName("currentTask").value;
	
	var labId = getElementByIdOrByName("labId").value;
	
	var orderedTestNameInd = getElementByIdOrByName("orderedTestNameInd");
	if(labId=="")
	{	
		orderedTestNameInd.className= "none";

	}var resultedTestNameInd = getElementByIdOrByName("resultedTestNameInd");
	
	var resultedTestNameIndVal = trim(getElementByIdOrByName("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt").value);
	if(orderedTestNameInd.className="none")
	{
	if(resultedTestNameIndVal=="")
		resultedTestNameInd.className= "none";
	}
	var specimenSource = getElementByIdOrByName("specimenSource");
	var specimenSourceVal = trim(getElementByIdOrByName("proxy.materialVO_s[0].theMaterialDT.cd").value);
	if(specimenSourceVal=="")
		specimenSource.className= "none";
	
	var specimentSite = getElementByIdOrByName("specimentSite");
	var specimentSiteVal = trim(getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.targetSiteCd").value);
	if(specimentSiteVal=="")
		specimentSite.className= "none";
		
	var codedResultIndicator = getElementByIdOrByName("codedResultIndicator");
	var codedResultIndicatorVal = trim(getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code").value);
	if(codedResultIndicatorVal=="")
		codedResultIndicator.className= "none";
	
	
	var susceptiblityInd = getElementByIdOrByName("susceptiblityInd");
	var numericResultVal = trim(getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.theObservationDT.cd").value);
	if(numericResultVal=="")
		numericResult.className= "none";

	var numericResult = getElementByIdOrByName("numericResult");
	var numericResultVal = trim(getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue").value);
	if(numericResultVal=="")
		numericResult.className= "none";
	
	var textResult = getElementByIdOrByName("textResult");
	var textResultVal = trim(getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt").value);
	if(textResultVal=="")
		textResult.className= "none";
	
	var referenceRange = getElementByIdOrByName("referenceRange");
	var referenceRangeValLow = trim(getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].lowRange").value);
	var referenceRangeValHigh = trim(getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].highRange").value);
	if(referenceRangeValLow=="" && referenceRangeValHigh=="")
		referenceRange.className= "none";
	
	var drugName = getElementByIdOrByName("drugName");
	var drugNameVal = trim(getElementByIdOrByName("resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").value);
	if(drugNameVal=="")
		drugName.className= "none";
	
	var numericResult = getElementByIdOrByName("numericResult");
	var numericResultVal = trim(getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue").value);
	if(numericResultVal=="")
		numericResultVal.className= "none";
	
	//var codedResultVal = getElementByIdOrByName("codedResultVal");
	//var specimenSourceVal = trim(getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueCodedDT_s[0].code").value);
	//if(specimenSourceVal=="")
	//codedResultVal.className= "none";
	
	var interpretiveFlag = getElementByIdOrByName("interpretiveFlag");
	var interpretiveFlagVal = trim(getElementByIdOrByName("resultedTest[i].susceptibility[j].observationInterpDT_s[0].interpretationCd").value);
	if(interpretiveFlagVal=="")
		interpretiveFlag.className= "none";
	
	

}

function displayCodeResultValue(){


	var testNode = getElementByIdOrByName("NumericResultVal");
	var codedNode = getElementByIdOrByName("codedResultVal");

	var testNodeWithVal = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue");
	var codedNodeWithVal = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueCodedDT_s[0].code");


	var temp = testNodeWithVal.value;
	var returnedVal = "";
	if(temp!= null)
	{
		returnedVal = trim(temp);
	}
	else
		returnedVal = temp;

	if(returnedVal !="")
	{

		codedNode.className ="none";
		codedNodeWithVal.value="NI";
	}
	else
	{

		codedNode.className ="visible";

	}


}

function trim(str){
  while (str.charAt(0) == " "){
    // remove leading spaces
    str = str.substring(1);
  }
  while (str.charAt(str.length - 1) == " "){
    // remove trailing spaces
    str = str.substring(0,str.length - 1);
  } return str;
}

function submittedSusceptFirstCheck(){

	    var labelList = new Array();

	var argumentsCaller;
	
	if(arguments.caller==undefined)
		argumentsCaller=submittedSusceptFirstCheck.caller.arguments[0];
	else
		argumentsCaller=arguments.caller[0];
	
		if(argumentsCaller=="Test Result" || argumentsCaller=="testResultCodedCheck"){
			var nestedError=null;
			var node = getElementByIdOrByName("nestedElementsTable|Susceptibility");
			var inputNodes = node.getElementsByTagName("input");
			var selectNodes = node.getElementsByTagName("select");
			for(var i=0; i < inputNodes.length; i++)	{
				if(inputNodes.item(i).isNested && inputNodes.item(i).type == "text" && inputNodes.item(i).value!=""){
					nestedError=true;
				}
			}
			for(var i=0; i<selectNodes.length; i++ ) {
				if(selectNodes.item(i).isNested && selectNodes.item(i).value!=""){
						nestedError=true;
				}
			}
			if(nestedError){
				var tdErrorCell = getTdErrorCell(getElementByIdOrByName("resultedTest[i].theSusceptibilityVO.theObservationDT.statusCd")) ;

				tdErrorCell.innerText = makeErrorMsg('ERR129',labelList);

				tdErrorCell.className = "error";
				return true;
			}
		}

	return false
}


/*
function numericResultValueCheck(){
	var valueNode = null;
	var tdErrorCell = null;
	var labelList = new Array();

	if(arguments.caller[0]=="Test Result"){
		valueNode = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue");
		tdErrorCell = getTdErrorCell(valueNode) ;
	}else if(arguments.caller[0]=="Susceptibility"){
		valueNode = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue");
		tdErrorCell = getTdErrorCell(valueNode) ;
	}
	if(valueNode!=null){

		tdErrorCell.innerText = "";

		if(valueNode.value=="")
			return false;
		//1 and 2
		if(valueNode.value.search(/^([<>=]|[<][=]|[>][=]|[<][>])?(\d{1,6})(\.\d{1,6})?$/)>=0)
			return false;
		//3 and 5
		if(valueNode.value.search(/^([<>=]|[<][=]|[>][=]|[<][>])?(\d{1,6})(\.\d{1,6})?[-:\/]{1}\d{1,6}(\.\d{1,6})?$/)>=0)
			return false;
		//4
		if(valueNode.value.search(/^(\d{1,6})(\.\d{1,6})?[+]{1}$/)>=0)
			return false;

		//handle error conditions

		if(valueNode.value.length>15){
			tdErrorCell.innerText = makeErrorMsg('ERR010',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}
		var wildcardArr = valueNode.value.match(/[-:+\/<>=]{1}/g);
		if(wildcardArr!=null&&wildcardArr.length>1){
			tdErrorCell.innerText = makeErrorMsg('ERR134',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}

		if(valueNode.value.search(/^(\d{1,6})(\.\d{1,6})?[^-:\/](\d{1,6})(\.\d{1,6})?$/)>=0){
			tdErrorCell.innerText = makeErrorMsg('ERR132',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}
		if(valueNode.value.search(/^(\d{1,6})(\.\d{1,6})?[-:\/]$/)>=0){
			tdErrorCell.innerText = makeErrorMsg('ERR132',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}
		if(valueNode.value.search(/^[^<>=](\d{1,6})(\.\d{1,6})?$/)>=0){
			tdErrorCell.innerText = makeErrorMsg('ERR131',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}
		if(valueNode.value.search(/^(\d{1,6})(\.\d{1,6})?[^-:+\/]$/)>=0){
			tdErrorCell.innerText = makeErrorMsg('ERR125',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}

		if(valueNode.value.search(/^[^-:+\/0-9<>=]*$/)>=0){
			tdErrorCell.innerText = makeErrorMsg('ERR126',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}

		if(valueNode.value.search(/^[^0-9]$/)>=0){
			tdErrorCell.innerText = makeErrorMsg('ERR133',labelList.concat(valueNode.fieldLabel));
			tdErrorCell.className = "error";
			return true;
		}




		tdErrorCell.innerText = "Numeric Result Value Error";
		tdErrorCell.className = "error";
		return true;
	} else
		return false;

}
*/

function hideCodedResultFilter(){


	var testNode = getElementByIdOrByName("NumericResultVal");
	var codedNode = getElementByIdOrByName("codedResultVal");

	var codedResultValue = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueCodedDT_s[0].code");
	var numericValueSus = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue");


	var temp = numericValueSus.value;
	var returnedVal = "";
	if(temp!= null)
	{
		returnedVal = trim(temp);
	}
	else
		returnedVal = temp;

	if(returnedVal !="")
	{
		codedNode.className ="none";
		codedResultValue.value="NI";

	}
	else
	{
		codedNode.className ="visible";

	}


}

function enableValueCd(node)
{
	
	var numericValueCd=null;
	var numericValueCdSelect=null;
	var numericValueCdButton=null;
	var numericValue=null;
	if (node.name=="resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue") {//resulted test	
		numericValueCd = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd_textbox");
		numericValueCdSelect = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd");
		numericValue = getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue");
		numericValueCdButton=getElementByIdOrByName("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd_button");
	}else if(node.name=="labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue"){//lab test on morb
		numericValueCd = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd_textbox");
		numericValueCdSelect = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd");
		numericValue = getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue");
		numericValueCdButton=getElementByIdOrByName("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd_button");
		
	} else {
		numericValueCd = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd_textbox");
		numericValueCdSelect = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd");
		numericValue = getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericValue");
		numericValueCdButton=getElementByIdOrByName("resultedTest[i].susceptibility[j].obsValueNumericDT_s[0].numericUnitCd_button");
	}
   
   if(trim(numericValue.value) == "")
   {
     numericValueCd.disabled=true;
     numericValueCd.value="";
     numericValueCdSelect.value="";
     numericValueCdButton.className="none";
   }
   else
   {
     numericValueCd.disabled=false;
     numericValueCdButton.className="visible";
     numericValueCdSelect.disabled=false;
   }
}

function getOrderedTestChange()
{
	var code = getElementByIdOrByName("proxy.observationVO_s[0].theObservationDT.txt").value;
	var labId = getElementByIdOrByName("labId").value;
	var targetResulted = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";
	var target = "resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt";
	var type = "OrderedTestChange";
	if(code != "")
		getLabCodeLookup(type, code, target, null, targetResulted, labId);
		
	var batchInserted = getElementByIdOrByName("nestedElementsHiddenFieldTest Result");

	if(batchInserted!= null)
	{
		var valueofBatch = batchInserted.value;
		var deletedBatch= valueofBatch.replace(/statusCd~^/g, "statusCd"+ nvDelimiter+"I");
		var deletedBatch2 = deletedBatch.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");

		batchInserted.value = deletedBatch2;
		updateBatchEntryHistoryBox("Test Result");
		resetBatchEntryInputElements("Test Result");
	}		

}

//var ChildWindowHandle = null;
function clearDropDowns(){
	var doc = window.document;
	var updateNode = doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.cdDescTxt");
	//purge target options
	
	var removeOpt = updateNode.firstChild;

		 while(removeOpt != null){
			  var temp = removeOpt.nextSibling;
			  updateNode.removeChild(removeOpt);
			  removeOpt= temp;
		 }
	
	getElementByIdOrByName(updateNode.name + "_textbox").value="";
	updateNode.className="none";
	
	var updateNodeOrdered = doc.getElementById("proxy.observationVO_s[0].theObservationDT.txt");
	removeOpt = updateNodeOrdered.firstChild;
	
	 while(removeOpt != null){
		  var temp = removeOpt.nextSibling;
		  updateNodeOrdered.removeChild(removeOpt);
		  removeOpt= temp;
	 }
	getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
	updateNodeOrdered.className="none";
clearResultedTestDependents();
}

function clearResultedTestDependents(){
	// Coded result
	var doc = window.document;
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code");
	if(updateNodeOrdered != null)
	{
	   getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
	   updateNodeOrdered.className="none";
	 }  

	// Organism
	if (doc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName") != null)
	{
		var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName");
		getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
		updateNodeOrdered.className="none";
		
			doc.getElementById("organismIndicator").className="none";
			doc.getElementById("codedResultIndicator").className="visible";
			doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").value="none";
			doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code").onchange();
			doc.getElementById("susceptiblityInd").className="none";


	}

	// Error message for soft required field
	if (doc.getElementById("codedResultIndicatorMessage") != null)
	{
		doc.getElementById("codedResultIndicatorMessage").className="none";

	}
	
	// Numeric Result value
	if(updateNodeOrdered !=null)
	{
	   var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericValue");
	   getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	}   

	// Units
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd");
	if(updateNodeOrdered !=null)
	{
	  getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
	  updateNodeOrdered.className="none";
	}  

	// Text Result
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueTxtDT_s[0].valueTxt");
	if(updateNodeOrdered != null)
	{
	  getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	}  

	// Reference Range Low
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].lowRange");
	if (updateNodeOrdered !=null)
	{
	  getElementByIdOrByName(updateNodeOrdered.name + "").value="";

	}
	// Reference Range High
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].highRange");
	if(updateNodeOrdered !=null)
	{
	  getElementByIdOrByName(updateNodeOrdered.name + "").value="";
	}  

	// Result Status
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theIsolateVO.theObservationDT.statusCd");
	if(updateNodeOrdered !=null)
	{
	   getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
	   updateNodeOrdered.className="none";
	}   

	// Result Status
	var updateNodeOrdered = doc.getElementById("resultedTest[i].theSusceptibilityVO.obsValueCodedDT_s[0].code");
	if(updateNodeOrdered !=null)
	{
	   getElementByIdOrByName(updateNodeOrdered.name + "_textbox").value="";
	   updateNodeOrdered.className="none";
	}   

}


function clearDoubleBatchEnteries()
{
	var batchInserted = getElementByIdOrByName("nestedElementsHiddenFieldTest Result");

	
	if(batchInserted!= null)
	{
		var valueofBatch = batchInserted.value;
		var deletedBatch= valueofBatch.replace(/statusCd~^/g, "statusCd"+ nvDelimiter+"I");
		var deletedBatch2 = deletedBatch.replace(/statusCd~A/g, "statusCd"+ nvDelimiter+"I");

		batchInserted.value = deletedBatch2;
		updateBatchEntryHistoryBox("Test Result");
	}
	clearDropDowns();
}

batchEntryValidationArray[1] = testResultCodedCheck;
batchEntryValidationArray[2] = submittedSusceptFirstCheck;
//batchEntryValidationArray[3] = numericResultValueCheck;

//batchEntryValidationArray.concat(testResultCodedCheck);
//batchEntryValidationArray.concat(submittedSusceptFirstCheck);
//batchEntryValidationArray[1] = testResultOrganismCheck;


validationArray[3] = testResultAtLeastOne;

