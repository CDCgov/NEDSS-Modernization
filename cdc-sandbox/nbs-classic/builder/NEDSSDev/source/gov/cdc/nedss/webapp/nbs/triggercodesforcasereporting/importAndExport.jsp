<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head><title>${fn:escapeXml(PageTitle)}</title>
        <%@ include file="/jsp/resources.jsp" %>
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
		  <%@ include file="/jsp/errors.jsp" %>
		  <%@ include file="/importandexport/impAndExp-topbar.jsp" %>
		  	
<tr><td>&nbsp;</td></tr>
<!-- Page starts below -->
<tr>
	<td>
				<nedss:container id="section1" name="ImportExport" classType="sect" displayImg="false" defaultDisplay="collapse">
	               <nedss:container id="subSec1" name="Manage Code to Condition Library" classType="subSect" defaultDisplay="collapse">
					<tr>
						<td style="padding-left:20px;">
							<table role="presentation" class="srtmanage">
								<tr><td  valign="top"><td align="left" valign="top" nowrap>
								<a href="/nbs//ReceivingSystem.do?method=manageLoad&initLoad=true">Code Library</a></td></tr>
								
							</table>
						</td>
					</tr>
				 </nedss:container>
					               <!-- end of subsection1 -->
					    			    </nedss:container>
           </td>
       </tr> 
       </table>
       </div>
       </div>
  </body>
</html>
