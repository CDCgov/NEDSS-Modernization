<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>


<html:hidden name="manageEventsForm" property="selectedcheckboxIds" styleId="chkboxIds"/>
<% 
int  subSectionIndex= (new Integer(request.getParameter("param2").toString())).intValue();
String tabId = request.getParameter("param3").toString();
request.setAttribute("finalTabID", tabId + (++subSectionIndex));
%>

<% String sectionId = "Treatment"; %>
<nedss:container id="${fn:escapeXml(finalTabID)}" name="Treatments" classType="subSect">     
    <tr>
	    <td colspan="2">
            <!-- Display tab table for listing all lab reports -->
            <display:table name="treatmentList" class="dtTable" id="treatlist" style="margin-top:0.15em;">
	            <display:column style="width:5%;" title="<p style='display:none'>Select/Deselect All</p>" >
	            	<div align="center" style="margin-top: 3px">
	            		<input type="checkbox"  title="Select/Deselect checkbox" value="${treatlist.isAssociated}" name="${treatlist.localId}" ${treatlist.checkBoxId}/>
	            	</div>
	            </display:column>
	            <display:column property="actionLink" title="Treatment Date" style="width:10%;"/>
	            <display:column property="customTreatmentNameCode" title="Treatment" style="width:60%;"/>
	            <display:column property="localId" title="Treatment ID" style="width:15%;"/>
	            <display:setProperty name="basic.empty.showtable" value="true"/>
	        </display:table>
	        <!-- Button to add new treatment -->
		    <%          
            if(((String)request.getAttribute("AddTreatPermission")).equals("true")) 
            { %> 
            <div style="text-align:right; margin-top:0.5em;">
                <input type="button" value="Add Treatment" onclick="treatmentAddButtn();">
            </div>        
            <% 
            } %>
	    </td>
    </tr>
</nedss:container>
<img style="background-color: #5F8DBF;width: 100%;" alt="" border="0" height="1" width="200" src="transparent.gif" alt="">
 
