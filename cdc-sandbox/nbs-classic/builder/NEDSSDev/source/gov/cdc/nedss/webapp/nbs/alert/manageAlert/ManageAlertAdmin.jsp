<%@ include file="/jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
	    <title>Alert Administration</title>
	    <script type="text/javaScript" src="alertAdmin.js"> </script>
    </head>
    <body>
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
                <%@ include file="../manageAlert/alert-topbar.jsp" %>
                <div class="sect" id="alertModules">
					<h2 class="sectHeader"> 
						<a class="anchor">Alert Modules </a> 
					</h2>
                    <div class="sectBody">
                        <table role="presentation" align="left" cellspacing="10" style="width:100%;">
                            <tr>
                                <td align="left" valign="top" nowrap><a href="/nbs/EmailAlert.do?method=loadEmail">Manage User Email</a></td></tr>
                            </tr>
                            <tr>
                                <td align="left" valign="top" nowrap><a		href="/nbs/AlertUser.do?method=alertAdminUser">Manage Alerts</a>
                                </td>
                            </tr>
                        </table>
                    </div>  <!-- end of section body -->	
                </div>  <!-- end of section -->		
            </div> <!-- end of div id=bd -->
        </div> <!-- end of div id=doc3 -->
    </body>
</html>