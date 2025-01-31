<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
	<tr>
	<logic:equal name="BaseForm" property="actionMode" value="View">
		<tr>
		<td align="left" width="500">
			<table role="presentation" class="viewpam">
				<tr>
					<td>&nbsp;&nbsp;</td>
			 		<td><b>Created:</b>&nbsp;</td>
			 		<td>${fn:escapeXml(createdDate)}&nbsp;&nbsp;</td>
			 		<td><b>by:</b>&nbsp;</td>
			 		<td>${fn:escapeXml(createdBy)}&nbsp;&nbsp;</td>
			 		<td><b>Updated:</b>&nbsp;</td>
			 		<td>${fn:escapeXml(updatedDate)}&nbsp;&nbsp;</td>
			 		<td><b>by:</b>&nbsp;</td>
			 		<td>${fn:escapeXml(updatedBy)}&nbsp;&nbsp;</td>
				</tr>
			</table>
		</td>		
		</tr>
</logic:equal>		
		<tr>
	 		<td align="left" width="500">
	 			<table role="presentation" class="viewpam">
	 				<tr>
	 					<td>&nbsp;&nbsp;</td>
				 		<td><b>Name:</b>&nbsp;</td>
				 		<td>
				 			<nedss:view name="BaseForm" property="pamClientVO.answer(TUB210)"/>&nbsp;
				 			<nedss:view name="BaseForm" property="pamClientVO.answer(TUB209)"/>&nbsp;&nbsp;</td>
				 		<td><b>DOB:</b>&nbsp;</td>
				 		<td><nedss:view name="BaseForm" property="pamClientVO.answer(TUB213)"/>&nbsp;</td>
				 		<td><b>Current Sex:</b>&nbsp;</td>
				 		<td><nedss:view name="BaseForm" property="pamClientVO.answer(TUB216)" codeSetNm="${BaseForm.formFieldMap.TUB216.codeSetNm}"/>&nbsp;</td>
	 				</tr>
	 			</table>
			</td>		
			</tr>
	</tr>
	<tr>
		<td align="center">
			<table role="presentation">
				<tr>
					<td align="center">					
						<b><span id="PamNotificationMessage"></span></b>
						<font class="boldTenRed"><b><span id="PamNotificationError"></span></b></font>						
					</td>				
				</tr>
			</table>
		</td>
	</tr>	
