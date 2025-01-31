<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored ="false" %>
<script type="text/javascript" src="eCSStender.js"></script>
<jsp:useBean id="BaseForm" scope="session" class="gov.cdc.nedss.webapp.nbs.form.util.BaseForm" />
               
<!-- Page Meta data Block -->
 <table role="presentation" class="style">
   	<tr class="cellColor">
        <td class="border">
           <span class="valueTopLine"><c:out value="${BaseForm.attributeMap.AlgorithmName}"/></span> 
        </td>
        <td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
        	<span class="valueTopLine"> Algorithm ID: </span>
            <span style="font:16px;Arial; margin-left:0.2em;"><c:out value="${BaseForm.attributeMap.AlgorithmId}"/></span>
            <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
        </td>
    </tr>
     
    <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Event Type: </span>
		    <span class="value"><c:out value="${BaseForm.attributeMap.EventType}"/></span>
		</td>
		<td class="border3">
		    <span class="label">Status: </span>
		    <span class="value"><c:out value="${BaseForm.attributeMap.StatusCd}"/></span>
		</td> 
    </tr>
    
    <tr class="cellColor">
		<td class="border1">
		    <span class="label"> Action: </span>
		    <span class="value"><c:out value="${BaseForm.attributeMap.Action}"/></span>
		</td>
		<td class="border3">
		    <span class="label"> Date Last Run: </span>
            <span class="value"><c:out value="${BaseForm.attributeMap.DateLastRun}"/></span>
        </td>
    </tr>
    
</table>

   
   
