function validateCreateNotification(){
	
	
	var notificationError = getElementByIdOrByName("NotificationError");
	if(notificationError.value=="false"){
		submitForm();
		var saveMsg = '<div class="submissionStatusMsg"> <div class="header"> Page Loading </div>' +  
        '<div class="body">Please wait...  The system is loading the requested page.</div> </div>';         
		$j.blockUI({  
		    message: saveMsg,  
		    css: {  
		        top:  ($j(window).height() - 500) /2 + 'px', 
		        left: ($j(window).width() - 500) /2 + 'px', 
		        width: '500px'
		    }  
		});
	}
	


}