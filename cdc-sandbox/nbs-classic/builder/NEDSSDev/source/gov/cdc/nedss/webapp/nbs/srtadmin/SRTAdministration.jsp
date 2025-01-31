<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
    	<title>Laboratory Management</title>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript" src="srtadmin.js"></script>
        <script type="text/javascript">
function getElem(id){
if (document.layers) return document.layers[id];
else if (document.all) return document.all[id];
else if (getElementByIdOrByName) return getElementByIdOrByName(id);
}
function swapFolder(img){
objImg=getElem(img);
if(objImg.src.indexOf('tree.gif')>-1){
objImg.src='open.gif';
objImg.alt='Open';
objImg.title='Open';
}
else
objImg.src='tree.gif';
swapSub('s'+img);
}
function swapText(img){
objImg=getElem(img);
if(objImg.innerText.indexOf('+')>-1)
objImg.innerText='-';
else
objImg.innerText='+';
swapSub('s'+img);
}
function swapSub(img){
elem=getElem(img);
objImg=elem.style;
if(objImg.display=='block')
objImg.display='none';
else
objImg.display='block';
}

function resetLabCache(element){
				var confirmMsg="Are you sure you want to reset Lab Mapping Cache?";
				if (confirm(confirmMsg)) {
					element.href ="/nbs/ResetCache.do?method=resetLabMappingCache";
				}     	
}
function resetCVGCache(element){
				var confirmMsg="Are you sure you want to reset Code Value General Cache?";
				if (confirm(confirmMsg)) {
					element.href ="/nbs/ResetCache.do?method=resetCodeValueGeneralCache";
				}
}	
		
</script>
<style type="text/css">
	/* In the common.css file, tr of a table of class 'subSect' is set to
	have a gray colored background. Reset it to #FFF (white) here */
	
	table.subSect tbody tr {background:#FFF;}
</style>
</head>
    <body onload="startCountdown();">
        <div id="doc3">
        <div id="bd">
		  <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
		  <!-- SECTIONS TOGGLER. Expand/Collapse all sections inside the 'bd' Div element  -->
		  <table role="presentation" cellpadding="0" cellspacing="0" border="0" align="left" style="width:100%;">
		  	<%@ include file="core/srtadmin-topbar.jsp" %>
		  	
<tr><td>&nbsp;</td></tr>
<!-- Page starts below -->
		
	<%
		String confirmMsg= request.getAttribute("confirmMsg") == null ? "" : (String) request.getAttribute("confirmMsg");
		if(! confirmMsg.equals("")) {
	%>
				
	<tr  align="center">
			      <td align="center" style="border:2px solid blue;" width="100%">
      			<% if(confirmMsg.indexOf("Failure") != -1) { %>
		      			<font class="boldTenRed">
	      		<%} else {%>	
	       			<font class="boldTenBlack">
	       	<%}%>	
       			<span id="error1">
		  				${fn:escapeXml(confirmMsg)}
		  			</span>
              </font>
		</td>
	</tr>
	<%} %>
<tr>
	<td>
					<nedss:container id="section1" name="Laboratory Management" classType="sect"  defaultDisplay="expand">
		               <nedss:container id="subSec1" name="Manage Laboratories" classType="subSect" defaultDisplay="expand">
						<tr>
							<td style="padding-left:20px;">
								<table role="presentation" class="srtmanage">
									<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs//Laboratories.do?method=searchLab">Manage Laboratories</a></td></tr>
								</table>
							</td>
						</tr>
					    </nedss:container>
					               <!-- end of subsection1 -->
					    <nedss:container id="subSec2" name="Manage Lab Tests" classType="subSect" defaultDisplay="expand">
						<tr>
							<td style="padding-left:20px;">
								<table role="presentation" class="srtmanage">
								<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/LDLabTests.do?method=searchLabLoad">Manage Lab Tests</a></td></tr>
								<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/ManageLoincs.do?method=manageLoincs">Manage LOINCs</a></td></tr>
								<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/LabTestLoincLink.do?method=searchLoincLoad">Manage Link between Lab Test and LOINC</a></td></tr>
								<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/LoinctoConditionLink.do?method=searchLoinctoCondLinkLoad">Manage Link between LOINC and Condition</a></td></tr>			
											
								</table>
							</td>
						</tr>
					    </nedss:container> 
					    <nedss:container id="subSec4" name="Manage Lab Results" classType="subSect" defaultDisplay="expand">
						<tr>
							<td style="padding-left:20px;">
								<table role="presentation" class="srtmanage">
									<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabLoad">Manage Results</a></td></tr>
									<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/SnomedCode.do?method=manageSnomedCodeLoad">Manage SNOMEDs</a></td></tr>
									<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/ExistingResultsSnomedLink.do?method=searchResultSnomedLinkLoad">Manage Link between Lab Result and SNOMED</a></td></tr>
									<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="/nbs/SnomedtoConditionLink.do?method=searchSnomedtoCondLinkLoad">Manage Link between SNOMED and Condition</a></td></tr>			
								</table>
							</td>
						</tr>
					    </nedss:container>  
					    <nedss:container id="subSec7" name=" Reset Cache" classType="subSect" defaultDisplay="expand">
						<tr>
							<td style="padding-left:20px;">
								<table role="presentation" class="srtmanage">
									<tr><td  valign="top"><img src="leaf.gif" alt="" title="">&nbsp;</td><td align="left" valign="top" nowrap><a href="#" onclick="resetLabCache(this)">Reset Lab Mapping Cache</a></td></tr>												
								</table>
							</td>
						</tr>
					    </nedss:container> 
			    </nedss:container>
           </td>
       </tr> 
       </table>
       </div>
       </div>
  </body>
</html>
