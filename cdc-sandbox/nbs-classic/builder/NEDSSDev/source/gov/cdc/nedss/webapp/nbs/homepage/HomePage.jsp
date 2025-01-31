<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, 
                 gov.cdc.nedss.util.PageConstants, 
                 gov.cdc.nedss.util.PropertyUtil" %>
<html lang="en">
    <head>
        <title> NBS Dashboard </title>
        <%@ include file="../../jsp/resources.jsp" %>
		<script src="jqueryDragSort-ui-1.6.js" type="text/javascript"></script>
		<script type="text/javascript" src="charts.js"></script> 
        
        <style type="text/css">
            /* dashlet */
            div.yui-u ul {margin-right:0px; padding:0px; margin-left:0px;}
            div.yui-u ul li {margin-right:0px; margin-left:0px; padding:0px; list-style:none;}
        
            div.dashboard div.dashlet {width:100%; margin:1em auto;}
            div.dashboard div.dashlet div.content {padding:0.5em;margin-left:0.35em; margin-right:0.35em; height:325px; overflow-y:auto; overflow-x:auto; background:#EEF2F6; text-align:left;}
            div.dashboard div.dashlet div.content ul {margin-right:0px; margin-top:5px;}
            div.dashboard div.dashlet div.content li {margin-right:0px; margin-left:15px;}
            div.dashboard div.dashlet a {text-decoration:none;}
            div.dashboard div.dashlet a:hover {text-decoration:underline;}
            div.dashboard div.dashlet table.subSect tbody tr, div.dashboard div.dashlet table.subSect tbody tr td {background:#EEF2F6}
            div.dashboard div.dashlet div.header {height:1.5em; padding:0em 0.25em 0em 0.25em; border-bottom:1px solid #D1DAE5;}
            div.dashboard div.dashlet div.header {cursor:move;}
            

            /* Spiffy corners */
			.spiffy{display:block}
			.spiffy *{
			  display:block;
			  height:1px;
			  overflow:hidden;
			  font-size:.01em;
			  background:#D1DAE5}
			.spiffy1{
			  margin-left:3px;
			  margin-right:3px;
			  padding-left:1px;
			  padding-right:1px;
			  border-left:1px solid #ebeff3;
			  border-right:1px solid #ebeff3;
			  background:#dce3eb}
			.spiffy2{
			  margin-left:1px;
			  margin-right:1px;
			  padding-right:1px;
			  padding-left:1px;
			  border-left:1px solid #fafbfc;
			  border-right:1px solid #fafbfc;
			  background:#d9e1e9}
			.spiffy3{
			  margin-left:1px;
			  margin-right:1px;
			  border-left:1px solid #d9e1e9;
			  border-right:1px solid #d9e1e9;}
			.spiffy4{
			  border-left:1px solid #ebeff3;
			  border-right:1px solid #ebeff3}
			.spiffy5{
			  border-left:1px solid #dce3eb;
			  border-right:1px solid #dce3eb}
			.spiffyfg{
			  background:#D1DAE5}
	   </style>
	   
	   <!--[if IE]>
			<style type="text/css">
			div.dashboard div.dashlet div.content {width:100%;}
			</style>
		<![endif]-->
		
	   <script type="text/javascript">
	   
	  //  function listenersToPreventBackButton(){};
	jQuery(document).ready(function(){
			
				if(localStorage.getItem("innerDocClick")=="false"){//If back button was pressed
											
											
								localStorage.setItem("innerDocClick","true");//DONT SHOW FEEDBACK
								listenersToPreventBackButton = function(){};
								$j(".navLink a")[0].click();//refresh Home page
				
					}else
					{
						localStorage.setItem("innerDocClick","true");//DONT SHOW FEEDBACK
					//	listenersToPreventBackButton = function(){};

					}
			
			
			
			
					});
	

	   //function listenersToPreventBackButton(){};//remove any listeners from this page so we have more control over the value of innerDocClick
	   
	   
	    $j(document).ready(function(){	 	
	     	 $j("#DEM102").focus(); //set starting focus to last name
	     	 
	     	//ND-32612: to start over and not error out if states dont use the Login page, or use an outside of NBS login page.
			//This means we can use the back button to come back to home page without showing any error.
	
	
	
			
			 //addTabIndexToReadOnly();
 	    });
	    
	    
	
	    
	   
            function toggleDashletDisplay(dashletId)
			{
                //alert("dashletId = " + dashletId );
	            var minusIcon = "<img src=\"minus_sign.gif\" alt=\"Collapse\" \/>"
	            var plusIcon = "<img src=\"plus_sign.gif\" alt=\"Expand\" \/>"
			    var dashletId = "#" + dashletId;
			    var dashletHead = $j(dashletId).find("div.header").get(0);
			    var dashletBody = $j(dashletId).find("div.content").get(0);

                //alert(dashletId + "; " + dashletHead + "; " + dashletBody + "; " + $j(dashletBody).css("display"));
			
			    if ($j(dashletBody).css("display") != "none") {
                    //alert("gonna hide it...");
                    //alert($j(dashletBody).html());
                    $j(dashletBody).css("display","none");
			        //$j(dashletBody).hide();
			        $j(dashletHead).find("a.toggleIconHref").html(plusIcon);
			    } 
			    else {
			        //alert("gonna show it...");
			        //alert($j(dashletBody).html()); 
			        //$j(dashletBody).show();
			        $j(dashletBody).css("display","block");
			        $j(dashletHead).find("a.toggleIconHref").html(minusIcon);
			    }
			    return false;
			}
			
			function displayLayoutChangeMessage()
			{
                $j("#layoutChangeMessage").show(); 
			}
			
			function addTabIndexToReadOnly(){
			
				//TabIndex added to the Notices section
				if(document.getElementById("ldfSection")!=null && document.getElementById("ldfSection").getElementsByTagName("tr").length>0)
					document.getElementById("ldfSection").setAttribute("tabIndex","0");
				else
					if(document.getElementById("ldfSection")!=null)//There are no Notices available
						document.getElementById("ldfSection").next().setAttribute("tabIndex","0");
						
				//TabIndex added to the News section	
				if(document.getElementById("li0_1")!=null && document.getElementById("li0_1").getElementsByClassName("content")!=null && 
				document.getElementById("li0_1").getElementsByClassName("content").length>0 && document.getElementById("li0_1").getElementsByClassName("content")[0].getElementsByTagName("b")!=null&&
				document.getElementById("li0_1").getElementsByClassName("content")[0].getElementsByTagName("b").length>0)
				
					document.getElementById("li0_1").getElementsByClassName("content")[0].getElementsByTagName("b")[0].setAttribute("tabIndex","0");
					
				//My Queues
				/*var lis = $j("#myQueues li");
				
				for(var i=0; i<lis.length; i++){
					if(lis[i].getElementsByTagName("a").length==0)
						lis[i].setAttribute("tabIndex","0");
				}*/
			}
	   </script>
    </head>
    <body onLoad="startCountdown();">
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
        
            <!-- Body div -->
            <div id="bd">
        		<html:form action="/HomePage.do">
	                <!-- Top Nav Bar and top button bar -->
	                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
	                
	                <!-- 3 columns -->
                    <div class="yui-gb dashboard">
                        <div id="layoutChangeMessage" class="infoBox success" style="display:none;">
                            Homepage layout has been modified. Do you wish to save this layout for future visits?
                            <input type="button" value="Save" /> &nbsp; <input type="button" value="Cancel"/>   
                        </div>
                        <logic:iterate id="mapItem" name="dashletOrderMap" indexId="colIndex">
                            <logic:equal name="colIndex" value="0">
                                <div class="yui-u first">
                            </logic:equal>
                            <logic:equal name="colIndex" value="1">
                                <div class="yui-u" style="width:31.9%">
                            </logic:equal>
                            <logic:greaterThan name="colIndex" value="1">
                                <div class="yui-u">
                            </logic:greaterThan>
                                <ul id="ul<bean:write name="colIndex" />" class="draglist">
		                            <logic:iterate id="dashletType" name="mapItem" property="value" indexId="rowIndex">
                                        <%
									        if (dashletType.toString().startsWith("PatientSearch")) {
									        	request.setAttribute("dashletHeight", dashletType.toString().substring(14));
									            if(nbsSecObj!=null && nbsSecObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.FIND)) { %>
									                <li class="list<bean:write name="colIndex" />" id="li<bean:write name="colIndex" />_<bean:write name="rowIndex" />">
									                 	<%@ include file="patient/SimplePatientSearch.jsp"%>
									                </li>
									            <% }
									           
									            
									        }
									        else if (dashletType.toString().startsWith("Feeds")) {
									        	request.setAttribute("dashletHeight", dashletType.toString().substring(6));%> 
									            <li class="list<bean:write name="colIndex" />" id="li<bean:write name="colIndex" />_<bean:write name="rowIndex" />">
									               <%@ include file="rss-feeds/Feeds.jsp"%>   
									            </li>
									        <% 
									        }
									        else if (dashletType.toString().startsWith("Notices")) { 
									        request.setAttribute("dashletHeight", dashletType.toString().substring(8));%>
									               <li class="list<bean:write name="colIndex" />" id="li<bean:write name="colIndex" />_<bean:write name="rowIndex" />">
									                   <%@ include file="ldfs/HomePage_Ldfs.jsp"%>
									               </li>
									            <%
									            }
									            
									        else if (dashletType.toString().startsWith("MyReports")) { 
									        	request.setAttribute("dashletHeight", dashletType.toString().substring(10));
									            if(nbsSecObj!=null) {
									                if ((nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE))
									                    || (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC))
									                    || (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE))
									                    || (nbsSecObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY)))
									                { %>
									                    <li class="list<bean:write name="colIndex" />" id="li<bean:write name="colIndex" />_<bean:write name="rowIndex" />">
									                       <%@ include file="reports/MyReports.jsp"%>
									                    </li>
									                <% 
									                }
									            }
									           
									        }
									        else if (dashletType.toString().startsWith("MyQueues")) { 
									        request.setAttribute("dashletHeight", dashletType.toString().substring(9));%> 
									           <li class="list<bean:write name="colIndex" />" id="li<bean:write name="colIndex" />_<bean:write name="rowIndex" />">
									               <%@ include file="queues/MyQueues.jsp"%>
									           </li>       
									        <% 
									        }
									        else if (dashletType.toString().startsWith("MyCharts")) { 
									        request.setAttribute("dashletHeight", dashletType.toString().substring(9));%>
									           <li class="list<bean:write name="colIndex" />" id="li<bean:write name="colIndex" />_<bean:write name="rowIndex" />">
									               <%@ include file="charts/Charts.jsp"%>
									           </li> 
									        <%
									        } 
                                        %>
		                            </logic:iterate>
	                            </ul>   
                            </div>                        
    	                </logic:iterate>
	                </div>
				 </html:form>
				<form name="RunReportForm">
	                <input type="hidden" name="mode" />
	                <input type="hidden" name="ObjectType" />
	                <input type="hidden" name="OperationType" />
	                <input type="hidden" name="DataSourceUID" />
	                <input type="hidden" name="ReportUID" />
	                
	            </form>
            </div> <!-- div id=bd -->
            
        </div> <!-- div id=doc3 -->
         <div id="backgroundPopup"></div>  
        
    </body>
</html>
<script type="text/javascript">
window.onload = function() {
	
    $j("ul.draglist").sortable
    ({
	handle:'div.header', 
	forcePlaceholderSize: true, 
	dropOnEmpty: true, 
	connectWith: ['ul.draglist'], 
	placeholder: 'placeHolder',
	update: function(event, ui) { 
	    //alert("ui.helper = " + ui.helper + "; ui.position = " + ui.item.prevAll().length + "; ui.offset = " + ui.offset + "; ui.item = " + ui.item + "; ui.placeholder = " + ui.placeholder + "; ui.sender = " + ui.sender);
	}
    });startCountdown();
}
</script>
