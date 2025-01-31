<logic:notEqual name="triggerCodeForm" property="actionMode" value="Create">
    <logic:notEmpty name="triggerCodeForm" property="attributeMap.NORESULT">
        <table role="presentation" align="center" style="width:100%;">
            <tr>
                <td>
                    <div class="infoBox messages">
			            Your Search Criteria resulted in 0 possible matches. <br/>
                        Please refine your search or click the Add New Code button to add a new <%= name1%>.
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <input type="button" name="submit" id="addNew" title="<%=name2%>" value="<%=name2%>" onClick="addNewCode();"/>	            
                </td>
            </tr>		      
        </table>
    </logic:notEmpty>
</logic:notEqual>