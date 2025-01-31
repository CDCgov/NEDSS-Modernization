<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Manage Templates </title>
        <base target="_self">
        <%@ include file="/jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JManageImportExportLogForm.js"></SCRIPT>
        <script type="text/javaScript" src="pagemanagementSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javaScript"> 
         var isFormSubmission = false;
        function closePopup()
		    {
			      
			     
	                self.close();	
	                var opener = getDialogArgument();	                 
	                var invest = null; 	                      
	                invest = getElementByIdOrByNameNode("pamview", opener.document)
	                if (invest == null) {
               			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
         			 }
         		
	                invest.style.display = "none"; 
	                
	                return true;     
	                         
	               
			    
			    } 
			    
	     function retActLog()
		    {		    
	                document.manageImportExportLogForm.action ="/nbs/ManageImpExpLog.do?method=manageImportExportLogLib&actionMode=Manage&initLoad=true"
                      document.manageImportExportLogForm.submit();
	               
			       
			    } 
		function printQueue(activityLogUid) {		

  	            var urlToOpen  ="/nbs/ManageImpExpLog.do?method=viewActivityLogDetails&mode=print&edxActivityLogUid="+activityLogUid;
           
        
		        // get the gray background element & activate it.
		        var divElt = getElementByIdOrByName("pamview");
		        if (divElt == null) {
		            divElt = getElementByIdOrByName("blockparent");
		        }
		        divElt.style.display = "block";
		        
		        // open a modal window        
		        var o = new Object();
		        o.opener = self;
		        var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
                //var modWin =    window.showModalDialog(urlToOpen, o, dialogFeatures);
		    
		      //  if (modWin == "windowClosed") {
		       //     divElt.style.display = "none";
		      //  }   	
		
		        var modWin = openWindow(urlToOpen, o, dialogFeatures, divElt, "");
		        
		    
		
    	 }  
    		 	    
		
        function exportQueue() {
        	 var activityLogUid = '${fn:escapeXml(edxActivityLogUid)}';
  	         document.manageImportExportLogForm.action  ="/nbs/ManageImpExpLog.do?method=saveAsTextFile&edxActivityLogUid="+activityLogUid;
  	          document.manageImportExportLogForm.submit();
        }   
        </script>  
        <style type="text/css">
         div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
         div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
        
		.removefilter{
			background-color:#003470; width:100%; height:25px;
			line-height:25px;float:right;text-align:right;
			}
			removefilerLink {vertical-align:bottom;  }
			.hyperLink
			{
			    font-size : 10pt;
			    font-family : Geneva, Arial, Helvetica, sans-serif;
			    color : #FFFFFF;
				text-decoration: none;
			}
		</style>
    </head>
      <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode")); %>
      <% if (printMode.equals("print")) {%>
        <body onload="autocompTxtValuesForJSP();startCountdown();" onunload="return closePopup();" > 
       <% }else{ %>
        <body onload="autocompTxtValuesForJSP();startCountdown();"  > 
        <% } %>
        <div id="blockparent"></div>
         <html:form action="/ManageImpExpLog.do">
            <div id="doc3">
                  <tr><td>
                  <!-- Body div -->
	                <div id="bd">
	                		 <!-- Top Nav Bar and top button bar -->
								<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
								 
							    <table role="presentation" style="width:100%;">
									<tr>
									    <td style="text-align:right"  id="srtLink"> 
									     <a href="#" onclick="return retActLog();" class="backToTopLink"> 									       
									            Return to Activity Log
									        </a>  
									        <input type="hidden" id="actionMode" value="${manageImportExportLogForm.actionMode}"/>
									    </td>
									</tr>
									
								</table>
								 <!-- top bar -->
							
						          <!-- Top button bar -->     

						   		<div class="grayButtonBar nonPrintPreviewModeOnly">
							   		<table role="presentation" width="100%"  >
								   		 <tr>
								   		 	<td align="left" >
								   		 		 <!-- <input type="button" name="View Import/Export Activity Log" style="width: 190px" value="View Import/Export Activity Log" onclick="viewPageHistoryPopUp()"/>
										   	-->  &nbsp;</td>
										 	<td align="right"> 							                   
								            	<input type="button"  value="Print" id=" " onclick="return printQueue('${fn:escapeXml(edxActivityLogUid)}')"/> 
								           		<input type="button"  value="Download" id=" " onclick="exportQueue();"/>             
							                </td>
							              </tr>
									</table>
					            </div> 
					            <div class="printerIconBlock screenOnly">
								    <table role="presentation" style="width:98%; margin:3px;">
								        <tr>
								            <td style="text-align:right; font-weight:bold;"> 
								                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
								            </td>
								        </tr>
								        
								    </table>   
								   
							     </div>	     
								
								 
								<div class="infoBox nedssLightYellowBg">
								    <table role="presentation" align="center" style="width:100%;">
								        <tr>
								            <!-- Name, Sex, DOB, Patient Id -->
								            <td>
								                <table role="presentation" align="left" style="width:100%;">
								                    <tr>
								                        <td>
								                            <span class="label">Template Name: </span>
								                            <span class="value"> ${manageImportExportLogForm.attributeMap.templateName} </span>
								                        </td>
								                        <td>
								                            <span class="label"> Processed Time: </span>
								                            <span class="value"> ${manageImportExportLogForm.attributeMap.processedTime} </span>
								                        </td>
								                     </tr>
								                     <tr>
								                        <td>
								                            <span class="label"> Source: </span>
								                            <span class="value"> ${manageImportExportLogForm.attributeMap.source} </span>
								                        </td>
								                        <td>
								                            <span class="label"> Status: </span>
								                            <span class="value"> Failure </span>
								                        </td>            
								                   </tr>
								                </table>
								            </td>
								        </tr>
								     </table>								
								</div>
						           
								 <!-- Container Div -->
	          				 
	
	                			<nedss:container id="id1" name="Activity Details" classType="sect" displayImg ="false" includeBackToTopLink="false">
  								 <fieldset style="border-width:0px;" id="codeset">
  								  <!-- SUB_SECTION : Investigation Details -->  								   
    						              
			                              <nedss:container id="subsec1" name="Failure Reason" classType="subSect" displayLink="false">
		                                    <tr>
			                                   
			                                    <% if(request.getAttribute("mode") != null && request.getAttribute("mode").toString().equalsIgnoreCase("print")){ %> 
			                                      <td >
			                                     <nedss:view  name="manageImportExportLogForm" property="selection.exception"/>	
			                                      </td>		                                        
			                                   <% }else{ %>		
			                                     <td align="center">	                                    
			                                        <html:textarea title="Failure Reason" property="selection.exception" style="overflow: scroll; overflow-y: scroll; overflow-x: hidden; overflow:-moz-scrollbars-vertical;" rows="20" cols="100" readonly="true"/>
			                                       </td>		
			                                   	<% } %>			                                 
			                                               
			                                       		                                      
			                                    
		                                    </tr>
		                                 </nedss:container>	
		                                </fieldset>
	                              </nedss:container>	
	                    	
        		   		<!-- top bar -->
							
			            	<div class="grayButtonBar nonPrintPreviewModeOnly">
						   		<table role="presentation" width="100%"  >
							   		 <tr>
								   		 	<td align="left" >
								   		 		
										   	</td>
										 	<td align="right"> 
							                  
								            	<input type="button"  value="Print" id=" " onclick="return printQueue('${fn:escapeXml(edxActivityLogUid)}');"/> 
								           		<input type="button"  value="Download" id=" " onclick="exportQueue();"/>             
							                </td>
						              </tr>
								</table>
					       </div>
					        <div class="printerIconBlock screenOnly">
						    <table role="presentation" style="width:98%; margin:3px;">
						        <tr>
						            <td style="text-align:right; font-weight:bold;"> 
						                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
						            </td>
						        </tr>
						    </table>
			               </div>          
						</td>
					</tr>
               </div>   
                		
               </html:form>
    </body>
</html>