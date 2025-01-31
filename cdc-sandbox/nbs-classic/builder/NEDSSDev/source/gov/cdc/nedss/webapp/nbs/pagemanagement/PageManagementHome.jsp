<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, 
                 gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, 
                 gov.cdc.nedss.util.PageConstants, 
                 gov.cdc.nedss.util.PropertyUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html lang="en">
    <head>
        <%@ include file="/jsp/resources.jsp" %>
        <script type="text/javascript">
            function getElem(id)
            {
                if (document.layers) return document.layers[id];
                else if (document.all) return document.all[id];
                else if (getElementByIdOrByName) return getElementByIdOrByName(id);
            }
            
            function swapFolder(img)
            {
                objImg=getElem(img);
                if(objImg.src.indexOf('tree.gif')>-1){
                    objImg.src='open.gif';
					objImg.alt='Open';
					objImg.title='Open';
                }
                else{
                    objImg.src='tree.gif';
                	objImg.alt='Open';
					objImg.title='Open';
                }
				swapSub('s'+img);
            }
            
            function swapText(img)
            {
                objImg=getElem(img);
                if(objImg.innerText.indexOf('+')>-1)
                    objImg.innerText='-';
                else
                    objImg.innerText='+';
                swapSub('s'+img);
            }
            
            function swapSub(img)
            {
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
	                <tr><td>&nbsp;</td></tr>
	                <!-- Page starts below -->
	                <% String confirmMsg= request.getAttribute("confirmMsg") == null ? "" : (String) request.getAttribute("confirmMsg");
	                if(! confirmMsg.equals("")) {
	                %>
	                <tr  align="center">
	                    <td align="center" style="border:2px solid blue;" width="100%">
		                    <% if(confirmMsg.indexOf("Failure") != -1) { %>
		                        <font class="boldTenRed">
		                    <%} else {%>    
		                        <font class="boldTenBlack">
				                    <%}%>   
			                        <span id="error1"><c:out value="${confirmMsg}"/></span>
	                            </font>
	                    </td>
	                </tr>
	                <%} %>
	                <tr>
	                    <td>
	                    <!-- Con, Pa, Ques, Tem, Value Sets -->    
	                    <nedss:container id="section1" name="Page Management" classType="sect" displayImg="false" defaultDisplay="collapse">
	                        <nedss:container id="subSec1" name="Page Management" classType="subSect" defaultDisplay="expand">
		                        <tr>
		                            <td style="padding-left:20px;">
		                                <ul>
		                                   <li> <a href="/nbs/ManageCondition.do?method=ViewConditionLib&actionMode=Manage&initLoad=true"> Manage Conditions </a></li>
		                                   <li> <a href="ManagePage.do?method=list"> Manage Pages </a></li>
		                                  <% if(nbsSecObj!=null){
			                                 if(nbsSecObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION))
			                                { %>
		                                   
		                                   <li> <a href="SearchManageQuestions.do?method=searchQuestionsLoad"> Manage Questions</a></li>
		                                   <% }}%>
		                                   <li> Manage Templates </li>
		                                   <li> Manage Value Sets </li>
		                                   		<ul>
      												<li><a href="/nbs/ManageCodeSet.do?method=searchCodeValGenLoad&nav=pageMgmt">Manage Code Sets</a></li>
      												<li><a href="/nbs/CodeValueGeneral.do?method=searchCodeValGenLoad&nav=pageMgmt">Manage Codes for Existing Code Set</a></li>
      											</ul>
		                                </ul>
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