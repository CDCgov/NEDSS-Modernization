<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<tr>
    <td class="fieldName">Question Type:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.questionTypeDesc"  />      
    </td>
</tr>

<tr>
    <td class="fieldName">Unique ID:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.questionIdentifier"  />      
    </td>
</tr>

<tr>
    <td class="fieldName">Unique Name:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.questionNm"  />      
    </td>
</tr>

<tr>
    <td class="fieldName">Status:</td>
    <td>
        <nedss:view name ="manageQuestionsForm" property="selection.statusDesc"  />      
    </td>
</tr>
<%-- <tr>
    <td class="fieldName">Group:</td>
    <td>
        <nedss:view name ="manageQuestionsForm"  property="selection.groupDesc"/>
    </td>
</tr>
 --%>
<tr>
    <td class="fieldName">Subgroup:</td>
    <td>
        <nedss:view name ="manageQuestionsForm"  property="selection.subGroupDesc"/>
    </td>
</tr>
      
<tr>
    <td class="fieldName">Description:</td>
    <td>
        <nedss:view name ="manageQuestionsForm"  property="selection.description"  />      
    </td>
</tr>

<tr>
    <td class="fieldName">Data Type:</td>
    <td>
        <nedss:view name ="manageQuestionsForm"  property="selection.dataTypeDesc"/>
    </td>
</tr>
  
<logic:equal name="manageQuestionsForm" property="selection.dataType" value="CODED">   
    <tr>
        <td class="fieldName">Value Set:</td>
        <td>
            <nedss:view name="manageQuestionsForm" property="selection.codeSetGroupId" codeSetNm="<%= NEDSSConstants.CODE_SET_NMS %>"/>
        </td>
    </tr>  
    <tr>
        <td class="fieldName">Default Value:</td>
        <td>
            <bean:write name="defaultValueDesc"/> 
        </td>
    </tr>
    <tr>
    <td class="fieldName">Allow for Entry of Other Value:</td>
    <td>
        <logic:equal name ="manageQuestionsForm"  property="selection.otherValIndCd" value="<%= NEDSSConstants.TRUE %>">
               Yes
        </logic:equal>
        <logic:equal name ="manageQuestionsForm"  property="selection.otherValIndCd" value="<%= NEDSSConstants.FALSE %>">
            No                
        </logic:equal>
    </td>
</tr>
    


</logic:equal>
   
<logic:equal name="manageQuestionsForm" property="selection.dataType" value="DATE">   
    <tr>
        <td class="fieldName">Mask:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.maskDesc"/>
        </td>
    </tr>
    <tr>
        <td class="fieldName"> Allow for Future Dates:</td>
        <td>
            <logic:equal name ="manageQuestionsForm"  property="selection.futureDateIndCd" value="<%= NEDSSConstants.TRUE %>">
                Yes
            </logic:equal>
            <logic:equal name ="manageQuestionsForm"  property="selection.futureDateIndCd" value="<%= NEDSSConstants.FALSE %>">
                No                
            </logic:equal> 
        </td>
    </tr>    
</logic:equal>

<logic:equal name="manageQuestionsForm" property="selection.dataType" value="DATETIME">   
    <tr>
        <td class="fieldName">Mask:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.maskDesc"/>
        </td>
    </tr>  
</logic:equal>

<logic:equal name="manageQuestionsForm" property="selection.dataType" value="NUMERIC">   
    <tr>
        <td class="fieldName">Mask:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.maskDesc"/>
        </td>
    </tr>
    <tr>
        <td class="fieldName">Field Length:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.fieldLength"/>
         </td>
    </tr>
    <tr>
        <td class="fieldName">Default Value:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.defaultValue"/>
         </td>
    </tr>  
    <tr>
        <td class="fieldName">Minimum Value:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.minValue"/>
         </td>
    </tr>  
    <tr>
        <td class="fieldName">Maximum Value:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.maxValue"/>
         </td>
    </tr>    
    <tr>
        <td class="fieldName">Related Units:</td>
        <td>
            <logic:equal name ="manageQuestionsForm"  property="selection.relatedUnitInd" value="<%= NEDSSConstants.TRUE %>">
                Yes
            </logic:equal>
            <logic:equal name ="manageQuestionsForm"  property="selection.relatedUnitInd" value="<%= NEDSSConstants.FALSE %>">
                No                
            </logic:equal> 
        </td>
    </tr> 
    <logic:equal name="manageQuestionsForm" property="selection.relatedUnitInd" value="T">  
        <tr>
            <td class="fieldName">Units Type:</td>
            <td>
                <nedss:view name ="manageQuestionsForm"  property="selection.unitTypeCdDesc"/>
            </td>
        </tr>
        <logic:equal name="manageQuestionsForm" property="selection.unitTypeCd" value="CODED">
            <tr>
                <td class="fieldName"> Related Units Value Set:</td>
                <td>
                    <nedss:view name ="manageQuestionsForm"  property="selection.unitValueDesc"/>
                </td>
            </tr>
        </logic:equal>
        <logic:equal name="manageQuestionsForm" property="selection.unitTypeCd" value="LITERAL">
            <tr>
                <td class="fieldName">Literal Units Value:</td>
                <td>
                   <nedss:view name ="manageQuestionsForm"  property="selection.unitValue"/>  
                </td>
            </tr>
        </logic:equal>
    </logic:equal>
</logic:equal>
     
<logic:equal name="manageQuestionsForm" property="selection.relatedUnitInd" value="Y">   
    <tr id="questionUnitIdentifiertr" >
        <td class="fieldName"> 
            <span title="Unique ID"  id="questionUnitIdentifierL">Unique ID:</span>  <font class="boldTenRed" > * </font>
        </td>
        <td>
            <nedss:view  name="manageQuestionsForm" property="selectionUnit.questionIdentifier"/>                          
        </td>             
    </tr>
       
    <tr id="questionnmUnittr">
        <td class="fieldName"> 
            <span title="Unique Name"  id="questionUnitnm">Unique Name:</span>  <font class="boldTenRed" > * </font>
        </td>
        <td>
            <nedss:view  name="manageQuestionsForm" property="selectionUnit.questionNm"/>                          
        </td>          
    </tr>
     
    <tr id="valSetforUnittr">
        <td class="fieldName"> 
            <span title="Value Set">Value Set:</span>  <font class="boldTenRed" >*</font>
        </td>
        <td>
            <nedss:view  name="manageQuestionsForm" property="selectionUnit.valSet"/>                           
        </td>
    </tr>
       
    <tr id="defValueUnittr">
        <td class="fieldName"> 
            <span title="Default Value">Default Value:</span>  <font class="boldTenRed" >*</font>
        </td>
        <td>
            <nedss:view  name="manageQuestionsForm" property="selectionUnit.defaultValue"/>         
        </td>
    </tr>   
        
    <tr id="dmcntrId">
        <td class="fieldName"> 
            <span title="Data Mart Column Name">Data Mart Column Name:</span>  <font class="boldTenRed" > * </font>
        </td>
        <td>
            <nedss:view  name="manageQuestionsForm" property="selectionUnit.datamartColumnNm"/>         
        </td>           
    </tr>
      
    <tr id="dlInRpttr">
        <td class="fieldName"> 
            <span title="Default Lable in Report">Default Label in Report:</span>  <font class="boldTenRed" > * </font>
        </td>
        <td id ="dlInRpttd">
            <nedss:view  name="manageQuestionsForm" property="selectionUnit.reportAdminColumnNm"/>                        
        </td>
    </tr>               
</logic:equal>
<logic:equal name="manageQuestionsForm" property="selection.dataType" value="TEXT">   
    <tr>
        <td class="fieldName"> 
            <span title="Mask">Mask:</span>  
        </td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.maskDesc"/>
        </td>
    </tr>
    <tr>
        <td class="fieldName"> 
            <span title="Field Length">Field Length:</span>  
        </td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.fieldLength"/>
        </td>
    </tr>
    <tr>
        <td class="fieldName">Default Value:</td>
        <td>
            <nedss:view name ="manageQuestionsForm"  property="selection.defaultValue"/>
        </td>
    </tr>  
</logic:equal>