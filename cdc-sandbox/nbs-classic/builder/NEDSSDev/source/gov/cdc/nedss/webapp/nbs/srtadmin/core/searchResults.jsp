<logic:notEqual name="SRTAdminManageForm" property="actionMode" value="Create">
    <logic:notEmpty name="SRTAdminManageForm" property="attributeMap.NORESULT">
        <table role="presentation" align="center" style="width:100%;">
            <tr>
                <td>
                    <div class="infoBox messages">
			            Your Search Criteria resulted in 0 possible matches. <br/>
                        Please refine your search or click Add button to add a new <%= name1%>.
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <input type="submit" name="submit" id="submit" value="<%= name2%>" onClick="add();"/>	            
                </td>
            </tr>		      
        </table>
    </logic:notEmpty>
</logic:notEqual>