function addComment(observationType)
{
	
		var x = screen.availWidth;
		var y = screen.availHeight;

		
	        if(observationType == 1){
		
	        	ChildWindowHandle = window.open("/nbs/AddComment.do?ContextAction=Load&mode=new&observationType=Lab", "_blank", "left=100, top=100, width=700, height=300, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		}
		else if(observationType == 2){
		
			ChildWindowHandle = window.open("/nbs/AddComment.do?ContextAction=Load&mode=new&observationType=Morb", "_blank", "left=100, top=100, width=700, height=300, menubar=no,titlebar=no,toolbar=no,scrollbars=no,location=no");
		}
		ChildWindowHandle.focus();
	
		
}

function addCommentSubmit()
{

   	parent.opener.location.reload(true);
   	parent.self.close();


}