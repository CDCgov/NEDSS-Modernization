<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
     	<%@ page import="java.util.*" %>
     	<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %> 
     	
     	<%@ include file="/jsp/tags.jsp" %>
     	<%@ include file="/jsp/resources.jsp" %> 
     	
     	
    	<title>NBS Print Patient File</title>
    
	    <style type="text/css">
			table.FORM {width:100%; margin-top:15em;}
	    </style>
    </head>
    
 
    
     
        <script language="JavaScript"> 
           function  handlePatExtendedBE(){
				$j("#addrTable").hide();
			 	$j("#nameTable").hide();
			  	$j("#phoneTable").hide();
			   	$j("#idenTable").hide();
			    $j("#raceTable").hide();
           }   
      
     </script>
     <script language="JavaScript"> 
		  var minusIcon1 = "<img src=\"section_collapse.gif\" alt=\"Collapse\" \/>";
		  var plusIcon1 = "<img src=\"section_expand.gif\" alt=\"Expand\" title=\"Expand Block\"\/>";
		  /** 
		  * Control the hide/show of a section body.
		  * @param sectionId - unique Id of a section
		  */
		  function toggleSectionDisplay1(sectionId)
		  {
		      var sectionId = "#" + sectionId;
		      var sectionHead = $j(sectionId).find("table.bluebarsectHeader").get(0);
		      var sectionBody = $j(sectionId).find("div.sectBody").get(0);
		  
		      if ($j(sectionBody).css("display") != "none") {
		          $j(sectionBody).hide();
		          $j(sectionHead).find("a.toggleIconHref").html(plusIcon1);
		      } 
		      else {
		          $j(sectionBody).show();
		          $j(sectionHead).find("a.toggleIconHref").html(minusIcon1);
		      }
		      
		      updateSectionsTogglerHandleWorkup("#" + $j(
		                                          $j(
		                                              sectionId                                 
		                                          ).parent().get(0)
		                                        ).attr("id"));
		      return false;
		}
		
		function updateSectionsTogglerHandleWorkup(viewId) {
		    // get the parent that contains the sectionsToggler 
		    // and all the sections to toggle
		    //alert("viewId "+viewId);
		    var parentElt;
		    if (viewId != null) {
		        parentElt = $j(viewId);    
		    }
		    else {
		        parentElt = $j("body");
		    }
		    
		    // get all sections to be toggled.
		    var updateRequired = true;
		    var sections = $j(parentElt).find("div.bluebarsect");
		    //alert("sections "+sections);
		    var sectState = "";
		    for (var i = 0; i < sections.length; i++) {
		       //alert("Were are here finaly: "+$j(sections.get(i)).attr("id"));
		        if (i == 0) {
		            sectState = getSectionDisplayState("#" + $j(sections.get(i)).attr("id"));
		        }
		        
		        if (sectState != getSectionDisplayState("#" + $j(sections.get(i)).attr("id"))) {
		            updateRequired = false;
		            break;
		        }
		        //alert("sectState "+sectState);
		    }
		
		}
		function gotoSectionWorkup(sectionId)
		{
		    var sectionId = "#" + sectionId;
		    var sectionHead = $j(sectionId).find("table.bluebarsectHeader").get(0);
		    var sectionBody = $j(sectionId).find("div.sectBody").get(0);
		    //alert(sectionId);
		    //alert("sectionHead " +sectionHead);
		    //alert("sectionBody " +sectionBody);
		    // expand/open the section if it is currently closed 
		    if ($j(sectionBody).css("display") == "none") {
		        $j(sectionBody).show();
		        $j(sectionHead).find("a.toggleIconHref").html(minusIcon1);
		    }

		    // update the section toggler handle to reflect the state of the 
		    // sections contained in the view container.
		    updateSectionsTogglerHandleWorkup("#" + $j(
		                                        $j(
		                                            sectionId                                 
		                                        ).parent().get(0)
		                                      ).attr("id"));
		                                      
		    // jump to the section that was currently opened
		    window.location = sectionId;
		}
		
		function toggleAllSectionsDisplayWorkup(viewId, id)
		{
		    // get the parent that contains the sectionsToggler 
		    // and all the sections to toggle
		    var parentElt;
		    if (viewId != null) {
			viewId = "#" + viewId;
			parentElt = $j(viewId);    
		    }
		    else {
			parentElt = $j("body");
		    }
		    

		    
		    
		    var sections = $(parentElt).find("div.bluebarsect");

		    // hide/show all sections depending on the toggler handle value
		    if (id ==1) {			
			for (i = 0; i < sections.length; i++) {
			    var sbody = $j(sections[i]).find("div.sectBody").get(0);
			    $j(sbody).hide();

			    var shead = $j(sections[i]).find("table.bluebarsectHeader").get(0);
			    $j(shead).find("a.toggleIconHref").html(plusIcon1);
			}
		    }
		    else {
			for (i = 0; i < sections.length; i++) {
			    var sbody = $j(sections[i]).find("div.sectBody").get(0);
			    $j(sbody).show();

			    var shead = $j(sections[i]).find("table.bluebarsectHeader").get(0);
			    $j(shead).find("a.toggleIconHref").html(minusIcon1);
			}
		    }
		}

		/** Popup a child window and load the page that is currently being 
	       *   viewed on the parent window. The call to load the page includes an additional 
	       *   parameter called 'mode' that has a value of print. This value is used to load
	       *   a seperate css file named 'print.css' when the page loads in the child window.
	       */
		function printPreview() {
	           
	           var o = new Object();
	           o.opener = self;
	           var URL = "/nbs/LoadViewFile1.do?method=ViewFile&ContextAction=print";
	           var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
	         
	           var modWin = openWindow(URL, o, dialogFeatures, null, "");
	           
	           return false;
	         }
        
    </script>
        
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
            div.grayButtonBar1 {width:100%; text-align:right; background:#EEE; }
            table.style{width:100%; margin:0 auto;border:1px solid #AFAFAF;}
			table.bluebarsectionsToggler, table.bluebarsubSectionsToggler, table.bluebarsubSect {width:100%; margin:0 auto; border-width:0px; margin-top:0.0em; border-spacing:0px;}
			table.bluebardtTable, table.bluebarprivateDtTable {width:100%; border:1px solid #666666; padding:0.0em; margin:0em auto; margin-top:0em;}
	</style>
     
     
    </head>
      
      
     <% 
    int subSectionIndex = 0;

    String tabId = "";
      String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Contact Investigation"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%>     
 <script language="JavaScript"> 
                    
    	function populateBatchRecords()
    	{
 
      
	}
	function toggleAllSectionsDisplayWorkupPrint(inPut)
		{
	
			if(inPut==1){
				toggleAllSectionsDisplayWorkup('viewWorkupSummary',1);
				toggleAllSectionsDisplayWorkup('viewWorkupEvents',1);
				toggleAllSectionsDisplayWorkup('patDemo',1);
			}else{		
				toggleAllSectionsDisplayWorkup('viewWorkupSummary',2);
				toggleAllSectionsDisplayWorkup('viewWorkupEvents',2);
				toggleAllSectionsDisplayWorkup('patDemo',2);
				
			}
	}	
                	  
</script>                	  


    <body class onload="handlebatchEntry();addRolePresentationToTabsAndSections();addRoleToPatientFile();">
    
           
            <div class="printerIconBlock screenOnly">
	    		    	<table role="presentation" style="width:98%; margin:3px;">
				    <tr bgcolor="#003470">
				    	<td style="text-align:left; font-weight:bold; height="25 px""> 
				    		<font color ="white" >View Patient File</font>
				    	</td>
				    	<td style="text-align:right; font-weight:bold; width='50%'"> 
				    		
				    		<a href="#" onclick="return printPage();"> <img src="printer.gif" alt="Print Page"/>
				     		<font color ="white" > Print &nbsp;&nbsp; </font></a> 
				     		
				    	</td>
				    </tr>
			    
				</table> 
	    		    
	    		    <table role="presentation" class="style">
			    					   <tr class="cellColor">
			    					        <td class="border" colspan="2">
			    					            <%
			    					            String name = (String) request.getAttribute("patientName") == null ? "---" :  (String) request.getAttribute("patientName");
			    					            name = name.trim(); 
			    					            if (name.length() != 0) {
			    					            	name = name;
			    					            }else
			    					            	name="---";
			    					            
			    					            String suffix = (String) request.getAttribute("legalSuffixName") == null ? "" : (String) request.getAttribute("legalSuffixName");
			    					            if (suffix.length() != 0)
			    					            {
			    					            	name = name + ", "+suffix;
			    					            }
			    					            
			    					            request.setAttribute("Name", name);
			    					            String currentSex =  request.getAttribute("currSexCd") == null ? "---" :  CachedDropDowns.getCodeDescTxtForCd((String) request.getAttribute("currSexCd"),"SEX");
			    					            currentSex = currentSex.trim(); 
			    					            if(currentSex.length() !=0){
			    					            	currentSex = currentSex;
			    					            }else
			    					            	currentSex="---";
			    					            request.setAttribute("Sex", currentSex);
			    					            String DOB = (String) request.getAttribute("birthTimeCalc") == null ? "---" :  (String) request.getAttribute("birthTimeCalc");
			    					            if(DOB.length() !=0){
			    					            	DOB = DOB;
			    					            }else
			    					            	DOB="---";
			    					            //DOB = currentsex.trim();
			    					            request.setAttribute("DOB1", DOB);
			    					         %>
			    					            <span class="valueTopLine">${fn:escapeXml(Name)}</span>
			    					            <span class="valueTopLine">|</span>
			    					            <span class="valueTopLine">${fn:escapeXml(Sex)}</span>
			    					            <span class="valueTopLine">|</span>
			    					            <%if(request.getAttribute("currentAgeUnitCd") != null){%>
			    					            <span class="valueTopLine">${fn:escapeXml(DOB1)} (<%= request.getAttribute("currentAge")!=null? HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("currentAge"))):""%><%=CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT")%>) </span>
			    					            <%}else{ %>
			    					            <span class="valueTopLine">${fn:escapeXml(DOB1)}</span>
			    					            <%} %>
			    					        </td>
			    					        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
			    					        	<span class="valueTopLine"> Patient ID: </span>
			    					            <span style="font:16px Arial; margin-left:0.2em;">${fn:escapeXml(personLocalID)}</span>
			    					            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
			    					        </td>
			    					    </tr>
			    					   
			    					    </table>
			    			<div  style="text-align:right;">
			    					   <table role="presentation" class="status">  
			    					   		<tr style="text-align:right">
											   <% if(request.getAttribute(NEDSSConstants.RECORDSTATUSCD)!=null && !request.getAttribute(NEDSSConstants.RECORDSTATUSCD).equals("")) {%>
											     <td style="text-align:right"><%=request.getAttribute(NEDSSConstants.RECORDSTATUSCD)%></td>
											    <%}else{ %>
											      <td height="5 px"></td>
											    <%} %>
											</tr> 
									</table>
							</div>		
			    			<div  style="text-align:right;">
			    				<a   href="javascript:toggleAllSectionsDisplayWorkupPrint(2)"/><font class="hyperLink"> Expand All</font></a>
			    				 | <a  href="javascript:toggleAllSectionsDisplayWorkupPrint(1)"/><font class="hyperLink"> Collapse All</font></a>
			</div>
		</div>
                   
        <div id="bd" style="width:98%;">
        
             <jsp:include page="WorkUp_Summary.jsp"/>
             <jsp:include page="File_Events.jsp"/>
             <jsp:include page="/person/jsp/Patient_Extended_View_File.jsp"/>
  		
  		</div> 
		
		
        </body>
         
</html>