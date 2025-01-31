<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, gov.cdc.nedss.util.PageConstants, gov.cdc.nedss.util.PropertyUtil" %>
<%@ page isELIgnored ="false" %>

	<meta http-equiv="cache-control" content="no-cache, no-store,must-revalidate, max-age=-1">
	<meta http-equiv="expires" content="-1">
	<meta http-equiv="pragma" content="no-cache, no-store">
	
	
<style>
</style>		

	<SCRIPT LANGUAGE="JavaScript">

	/*********************************************************
	 * The following methods are related to custom queues
	 *********************************************************/
	 
	 	/**
	 	* deleteCustomQueue: it deletes the custom queue if the current user created the queue. It will be deleted for everyone if public or for the current user if private.
	 	*/
	 	function deleteCustomQueue(){
	  		var errors = new Array();
	 	    var confirmMsg="Are you sure you want to delete the custom queue?";
	        if (confirm(confirmMsg))
	        {
	        	
	        	//Check if current user created the DB and if so, delete it from DB
	        	var queueName = $j("#queueName").val();
	        	
	        	 JPersonSearchForm.deleteCustomQueue(queueName,function(message) {
	        		 
	        		 
	        		 if(message == "0"){
							success="The Custom Queue was successfully deleted";
							
							if(success!=null && success.length>0)
								displaySuccess("customQueueSuccess", success);
							
							
							showHideSaveBox(false);
							$j("#removeFilters").hide();
							$j("#searchResultsTable").hide();
							$j(".pagebanner").hide();
							$j(".singlepagebanner").hide();							
							$j(".pagelinks").hide();
							
							$j("#nameQueue").val("");
							$j("#descriptionQueue").val("");
							var printExportImages= $j(".printexport").find("img");	
							for (var li = 0; li < printExportImages.length; li++) {
								var style = $j(printExportImages[li]).attr("style");
								$j(printExportImages[li]).attr("style", style+";pointer-events:none");//Firefox
					            $j(printExportImages[li]).css("cursor", "none");    
							}
						}else
							if(message == "1"){
								errors.push("The current user doesn't have permissions to delete public custom queues.");
								displayErrors("customQueueErrors", errors);
							//	$j("#nameQueue").focus();//Set focus to the Name field in order to be updated.
							}
						else
							if(message == "2"){
								errors.push("Error while inserting into the DB");
								displayErrors("customQueueErrors", errors);
								//$j("#nameQueue").focus();//Set focus to the Name field in order to be updated.
							}
					});
	        	 
	     			$j("div#addMarkAsReviewedBlock").hide();
	     			$j("div#msgBlock").hide();
	     			$j("div#errorBlock").hide();
	     			
	        	   	return true;
	        }
	        else {
	            return false;
	        }
		
		
		}
		/*
		* showHideSaveBox: if show = true, the save box is displayed, otherwise, it is hidden.
		*/
		function showHideSaveBox(show){
			if(show){
				$j("#saveQueueBox").show();
				getElementByIdOrByName("customQueueSuccess").hide();	
			}
			else{
				$j("#saveQueueBox").hide();
				getElementByIdOrByName("customQueueErrors").hide();//In case any error was shown, the error message needs to be hidden	
			}

		}
		
		/*
		* submitSaveQueue: submit the queue to be stored in the DB. It validates both values are entered and also clean the values and closes the box
		  after saving the custom query.
		*/
		function submitSaveQueue(type){
			
			var queueName = $j("#nameQueue").val();
			var queueDescription =$j("#descriptionQueue").val();
			var publicQueue = "false";
			if($j("#publicId")[0]!=null && $j("#publicId")[0]!="undefined")
				publicQueue=$j("#publicId")[0].checked;
			var errors = new Array();
			var success = "";

			
			
			
			if(queueName!=null && queueName!=undefined && queueName.replace(/ /g,"")!='' &&
					queueDescription!=null && queueDescription!=undefined && queueDescription.replace(/ /g,"")!=''){
					//dwr calls to store in the DB
				    //TODO: the form will depend on the queue we are, if type = DRSAQ, then usee JObservationSecurityAssnReviewForm. Can we use Base Form or something like that?? try!!
					var query = $j("#finalQueryString").val();
				    JPersonSearchForm.storeCustomQueue(queueName,queueDescription, type, publicQueue, query,function(message) {
	
						//For now, the messages that we are sending back is:
							//-Queue Saved = 0
							//-Queue Name Duplicated = 1
							//-But we should expand this in case there are other exceptions

						if(message == "0"){
							success="The Custom Queue was successfully created";
							
							if(success!=null && success.length>0)
								displaySuccess("customQueueSuccess", success);
							
							
							showHideSaveBox(false);
							$j("#nameQueue").val("");
							$j("#descriptionQueue").val("");
						}else
							if(message == "1"){
								errors.push("Queue name already exists in the table. Please, choose a different name");
								displayErrors("customQueueErrors", errors);
								$j("#nameQueue").focus();//Set focus to the Name field in order to be updated.
							}
						else
							if(message == "2"){
								errors.push("Error while inserting into the DB");
								displayErrors("customQueueErrors", errors);
								//$j("#nameQueue").focus();//Set focus to the Name field in order to be updated.
							}
					});
			}
			else{
				
					if(isEmpty(queueName))
						errors.push("Save queue as is required");
						errors.push("Description is required");	
				
				
			
				
				if(errors!=null && errors.length>0)
					displayErrors("customQueueErrors", errors);
				
				
			}
		}
			
		/*
		* isEmpty: returns true if the element is empty, undefined or null
		*/
		function isEmpty(element){
			
			if(element==null || element==undefined || element.replace(/ /g,"")=='')
				return true;
			else
				return false;
		}


	/*********************************************************
	 * End of methods related to custom queues
	 *********************************************************/	
    
		
	</SCRIPT>  	
	<!-- Validation error messages -->

										<div id="customQueueErrors" class="screenOnly infoBox errors" style="display:none; width:99%"> </div>
										<div id="customQueueSuccess" class="screenOnly infoBox success" style="display:none; width:99%"> </div>

													
					
  
							   <div id="saveQueueBox" class="boxed" style="display:none">
												   <table role="presentation" class="formTable" style=" width:100%; margin:0px;">

													<tr>
														   <td colspan="3" style="text-align:left;"><span style="color:black; font-weight:bold;">Please enter the name and description of the custom queue.</span><br/><br/></td>
													</tr>
												
												   <tr>
														<td colspan ="2" class="fieldName" style="text-align:right; width:50%;" id="nameId"> <span style="color:#CC0000; font-weight:bold;">*</span> Save queue as: </td>
														<td><input type="text" id="nameQueue" name="nameQueue" style="width:500px;vertical-align:middle;display: inline-block;" maxlength="100" title="Name of the custom queue" onkeyup="isSpecialCharEnteredForQueueName(this,event)"></td>
													</tr> 
													<tr>	
														<td colspan ="2" class="fieldName" style="text-align:right; width:50%;padding-bottom:15px;" id="descriptionId"> <span style="color:#CC0000; font-weight:bold;">*</span> Description: </td>
														<td><textarea style="WIDTH: 500px; HEIGHT: 100px;vertical-align:middle;" id="descriptionQueue" name="descriptionQueue" title="Description of the custom queue"  onkeyup="checkTextAreaLength(this, 1000);isSpecialCharEnteredForQueueName(this,event)"></textarea><br><br></td>
													</tr>
													<logic:equal value="true" name="permissionPublicQueue" scope="request">
														<tr>
															<td class="fieldName" colspan ="2"  style="text-align:right; width:50%"> Public/Private:</td>
															<td>
																<input type="radio" id = "publicId" name="publicPrivate" title="The queue will be visible by anyone" value="public"> Public </input>
																<input type="radio" checked="checked" id = "privateId" name="publicPrivate" title="The queue will be only visible by current user" value="private"> Private </input>
															</td>
														</tr>

													</logic:equal>
									
													<tr>
													  
														<td colspan="3" style="text-align:right; padding-bottom:5px;">
			
															<input type="button" value="Submit" title="Save as a custom queue" name="submitButton" onclick="return submitSaveQueue('${fn:escapeXml(queueType)}');"/>&nbsp;

															<input type="button" value="Cancel" title="Cancel" name="cancelButton" onclick="javascript:showHideSaveBox(false)"/>
														</td>
													</tr> 
	  
												</table>
												<p><span align='left' class='status-text' id='updateStatusMsg'></span>
												<div id="progressBar" style="display: none;">
													<div id="theMeter">
														<div id="progressBarText"></div>
														<div id="progressBarBox">
															<div id="progressBarBoxContent"></div>
														</div>
													</div>
												</div>
												</p>
								
										</div>  
