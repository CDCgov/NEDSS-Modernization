<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ page import="gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil" %>
<%@ page import="gov.cdc.nedss.systemservice.nbscontext.NBSContextSystemInfo" %>
<%@ page import="gov.cdc.nedss.systemservice.nbscontext.NBSContext"%>
<%@ page import="java.io.*"%>
<%@ page isErrorPage="true" %>
<%@ page import="org.apache.struts.Globals" %>    
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>

<html lang="en">
	<head>
	 <title>NBS: Error page</title>
	    <%@ include file="/jsp/resources.jsp" %>	
          <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
	    <style type="text/css">
		    table tr td.fieldName {text-align:left; width:10%;  padding-right:0.20em; font-weight:bold;}	
		</style>
		<script language="JavaScript">
			function printError() {
				window.location="/nbs/nedssErrorPrint";
			}
			</script>
	</head>
    
    <body>
    <div id="doc3">
	  <%
	  	request.setAttribute("PageTitle", "Error Page");
	  %>
	        <!-- Body div -->
	        <div id="bd">
	            <!-- Top Nav Bar and top button bar -->
	            <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
	            <div class="printexport" id="printExport" align="right">
			   <img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Error to PDF" title="Print Error to PDF" onclick="printError();" onkeypress="if(isEnterKey(event)) printQueue();"/>			   
                  </div>		            
	           <table role="presentation">
	            <tr>
	                <td class="fieldName" colspan="2">
		                <span>
		                	The NBS encountered an error while processing your request. Please copy the information below this message (or save the message to .pdf using the print functionality) and forward it to your NBS Technical Administrator for review.		                </span>
	                </td>
                </tr>
                
                <tr>
	            	<td colspan="2">
	            		&nbsp;
	            	</td>
	            </tr>
                <tr>
	                <td class="fieldName"  colspan="2">
		                <span>
		                You will then need to return to the <a href="/nbs/HomePage.do?method=loadHomePage"> NBS Home Page</a> and re-process your transaction.  DO NOT use your browser's BACK or REFRESH buttons.
		                </span>
	                </td>
	            </tr>
	            <tr>
	            	<td colspan="2">
	            		&nbsp;
	            	</td>
	            </tr>
	            <tr>
	            	<td class="fieldName"  colspan="2">
	            		<HR WIDTH="100%" NOSHADE>
	            	</td>
	            </tr>
	            <tr>
	            	<td colspan="2">
	            		&nbsp;
	            	</td>
	            </tr>
	            <tr>
	                <td class="fieldName">
		                <span>
		                URL:
		                </span>
	                </td>
	                <%
	                	TreeMap NBSObjectStore = (TreeMap) request.getSession().getAttribute(NBSConstantUtil.OBJECT_STORE);
	                	NBSContextSystemInfo contextSystemInfo = null;
	                	if (NBSObjectStore != null) {
	                		contextSystemInfo = (NBSContextSystemInfo) NBSObjectStore.get(NBSConstantUtil.NBSCONTEXTINFO);
	                	}
	                	StringBuffer url = new StringBuffer();
	                	String completeUrl = request.getRequestURL().toString();
	                	int indexOfNbs = completeUrl.indexOf("/nbs/");
	                	String serverPath = completeUrl.substring(0, indexOfNbs);

	                	url.append(serverPath);
	                	String sCurrentTask = contextSystemInfo.getPrevTaskName();
	                	url.append("/nbs/");
	                	url.append(sCurrentTask);
	                	url.append(".do");

	                	request.getSession().setAttribute("errorUrl", url.toString());
	                %>
	                <td>
		                <span>
		                	<%=url%>
		                </span>
	                </td>
	            </tr>
	            <tr>
	            	<td colspan="2">
	            		&nbsp;
	            	</td>
	            </tr>
	            <tr>
	                <td class="fieldName">
		                <span>
		                Date/Time:  
		                </span>
	                </td>
	                <%
	                	Date creationTime = new Date(session.getCreationTime());

	                	Date lastAccessed = new Date(session.getLastAccessedTime());
	                %>
	                <td>
		                <span>
		                	<%=(lastAccessed)%>
		                </span>
	                </td>
	            </tr>
	            <tr>
	            	<td colspan="2">
	            		&nbsp;
	            	</td>
	            </tr>
	            <%
	            	StringBuffer tsb = new StringBuffer();
	            	Exception e2 = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
	            	String errorOutput = null;
	            	String[] errorOut;
	            	StringWriter stackTrace = null;
	            	PrintWriter pw = null;
	            	try {
	            		stackTrace = new StringWriter();

	            		pw = new PrintWriter(stackTrace);
	            		if (e2 != null) {
	            			e2.printStackTrace(pw);
	            			errorOutput = HTMLEncoder.encodeHtml(stackTrace.toString());
	            		}
	            	} catch (Exception ex) {
	            		errorOutput = "bad stack2string";
	            	} finally {
	            		try {
	            			stackTrace.close();
	            			pw.close();
	            		} catch (IOException ioEx) {
	            			errorOutput = "Cannot close the StringWriter in error.jsp";
	            		}
	            	}
	            	request.getSession().setAttribute("errorPageException", errorOutput);
	            	if (errorOutput != null) {
	            		errorOutput = errorOutput.replaceAll("\n", "<br>");
	            		errorOutput = errorOutput.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	            		tsb = new StringBuffer(errorOutput);
	            	}
	            %>
	            <tr>
	                <td class="fieldName" valign="top">
		                <span>
		                Exception:
		                </span>
	                </td>
	                <td>
	                	<span>
		         	  	<%=tsb.toString()%>
		                </span>
	                </td>
	            </tr>
	            </table>
	            </div> <!-- id=bd -->
                
                <!-- Footer div -->
                <!--
                <div id="ft">
                    NEDSS Base System
                </div>
                -->
           
        </div> <!-- Container Div -->
    </body>
</html>