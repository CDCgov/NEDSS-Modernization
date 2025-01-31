<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

<html lang="en">
    <head>
    	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <base target="_self">
        <title>NBS: Manage Pages</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        
        <style type="text/css">
            table.nedssBlueBG {background:#DCE7F7;}
        </style>
        
        <script type="text/javascript">
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
			var unblock = true;
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                 // get reference to opener/parent  
if(unblock){			 
                    var opener = getDialogArgument();
                    var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                    if (grayOverlay == null) {
                        grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                    }
                    grayOverlay.style.display = "none";
				}
                    if ((closePopup == true) || (e != undefined && e != null && e.clientY < 0)) {
                        
                        
                        // pass the json to the parent
                        opener.manageElementDialogCallBack(document.jsonStringForm.jsonString.value);
                        
                        // close this window
                        self.close();
                        return true;
                    }
                }
            }
            
            function editQuestion()
            {	unblock=false;
                var pageEltId = '<nedss:view name ="pageElementVO" property="pageElementUid" />';
                document.pageElementForm.action ="/nbs/ManagePageElement.do?method=editLoad&eltType=fieldRow&pageElementUid=" + pageEltId;
                document.pageElementForm.submit();
            }
        </script>
    </head>
    <body class="popup" onunload="handlePageUnload(false, event);addRolePresentationToTabsAndSections(); return false;" style="text-align:center;">
        <div class="popupTitle">Manage Pages: View Question </div>
        
        <!-- Top button bar -->
        <div class="popupButtonBar">
        <logic:notEqual name ="pageElementVO" property="waUiMetadataDT.coinfectionIndCd" value="T">
            <input type="button" value="Edit" onclick="editQuestion()"></input>
        </logic:notEqual>
            <input type="button" value="Close" onclick="handlePageUnload(true, event)"></input>          
        </div>
        
        <!-- Error Messages using Action Messages-->
        <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
        
        <!-- Collection of form fields to edit a question -->
        <form name="jsonStringForm">
        	 <input type="hidden" name="jsonString" value="<bean:write name="elementsJson"/>" />
        </form>
        
        <!-- Form for bringing up the edit mode for this question -->
        <html:form action="/ManagePageElement.do">
        </html:form>
        
        <!-- Collection of form fields to edit a question -->
        <div id="editQuestionBlock">
            <div class="view"  style="text-align:center;">
                <!-- SECTION : Question --> 
                <nedss:container id="sect_question" name="View Question" classType="sect" displayImg="false" includeBackToTopLink="no">

                    <!-- SUBSECTION: Basic Info -->
                    <nedss:container id="subsect_basicInfo" name="Basic Information" classType="subSect" >
                        <tr>
                            <td class="fieldName">Question Type:</td>
                            <td>
                                <logic:present name ="pageElementVO" property="waUiMetadataDT.questionTypeDesc">
                                  <nedss:view name ="pageElementVO" property="waUiMetadataDT.questionTypeDesc"  />
                                </logic:present>  
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Unique ID:</td>
                            <td>
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.questionIdentifier"  />      
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Unique Name:</td>
                            <td>
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.questionNm"  />      
                            </td>
                        </tr>     
                        <tr>
                            <td class="fieldName">Subgroup:</td>
                            <td>
                                <nedss:view name="pageElementVO" property="waUiMetadataDT.subGroupNm" codeSetNm="<%= NEDSSConstants.PAGE_SUBGROUP %>"/>
                            </td>
                        </tr>
                              
                        <tr>
                            <td class="fieldName">Description:</td>
                            <td>
                                <nedss:view name ="pageElementVO"  property="waUiMetadataDT.descTxt"  />      
                            </td>
                        </tr>
                      
  
                           <tr>
                            <td class="fieldName">Co-Infection Question:</td>
                            <td>
                                <nedss:view name ="pageElementVO"  property="waUiMetadataDT.coinfectionIndCd"  />      
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName">Data Type:</td>
                            <td>
                                <nedss:view name="pageElementVO" property="waUiMetadataDT.dataType" 
                                               codeSetNm="<%= NEDSSConstants.PAGE_DATATYPE %>"/>   
                            </td>
                        </tr>
                          
                        <logic:equal name="pageElementVO" property="waUiMetadataDT.dataType" value="CODED">   
                            <tr>
                                <td class="fieldName">Value Set:</td>
                                <td>
                                    <nedss:view name="pageElementVO" property="waUiMetadataDT.codeSetGroupId" codeSetNm="<%= NEDSSConstants.CODE_SET_NMS %>"/>
                                </td>
                            </tr>  
                            <tr>
                                <td class="fieldName">Default Value:</td>
                                <td>
                                    <logic:present name="defaultValueDesc">
                                      <bean:write name="defaultValueDesc" />
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                            <td class="fieldName">Allow for Entry of Other Value:</td>
                            <td>
                                <logic:equal name ="pageElementVO"  property="waUiMetadataDT.otherValIndCd" value="<%= NEDSSConstants.TRUE %>">
                                       Yes
                                </logic:equal>
                                <logic:equal name ="pageElementVO"  property="waUiMetadataDT.otherValIndCd" value="<%= NEDSSConstants.FALSE %>">
                                    No                
                                </logic:equal>
                            </td>
                        </tr>


                            
                        </logic:equal>
                           
                        <logic:equal name="pageElementVO" property="waUiMetadataDT.dataType" value="DATE">   
                            <tr>
                                <td class="fieldName">Mask:</td>
                                <td>
                                    <logic:present name="maskDesc">
                                        <bean:write name ="maskDesc"/>    
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName"> Allow for Future Dates:</td>
                                <td>
                                    <logic:equal name ="pageElementVO"  property="waUiMetadataDT.futureDateIndCd" value="<%= NEDSSConstants.TRUE %>">
                                        Yes
                                    </logic:equal>
                                    <logic:equal name ="pageElementVO"  property="waUiMetadataDT.futureDateIndCd" value="<%= NEDSSConstants.FALSE %>">
                                        No                
                                    </logic:equal> 
                                </td>
                            </tr>    
                        </logic:equal>
                        
                        <logic:equal name="pageElementVO" property="waUiMetadataDT.dataType" value="DATETIME">   
                            <tr>
                                <td class="fieldName">Mask:</td>
                                <td>
                                    <logic:present name="maskDesc">
                                        <bean:write name ="maskDesc"/>    
                                    </logic:present>
                                </td>
                            </tr>  
                        </logic:equal>
                        
                        <logic:equal name="pageElementVO" property="waUiMetadataDT.dataType" value="NUMERIC">   
                            <tr>
                                <td class="fieldName">Mask:</td>
                                <td>
                                    <logic:present name="maskDesc">
                                        <bean:write name ="maskDesc"/>    
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">Field Length:</td>
                                <td>
                                    <nedss:view name ="pageElementVO"  property="waUiMetadataDT.fieldLength"/>
                                 </td>
                            </tr>
                            <tr>
                                <td class="fieldName">Default Value:</td>
                                <td>
                                    <nedss:view name ="pageElementVO"  property="waUiMetadataDT.defaultValue"/>
                                 </td>
                            </tr>  
                            <tr>
                                <td class="fieldName">Minimum Value:</td>
                                <td>
                                    <nedss:view name ="pageElementVO"  property="waUiMetadataDT.minValue"/>
                                 </td>
                            </tr>  
                            <tr>
                                <td class="fieldName">Maximum Value:</td>
                                <td>
                                    <nedss:view name ="pageElementVO"  property="waUiMetadataDT.maxValue"/>
                                 </td>
                            </tr>    
                            <tr>
                                <td class="fieldName">Related Units:</td>
                                <td>
                                    <logic:equal name ="pageElementVO"  property="waUiMetadataDT.relatedUnitInd" value="<%= NEDSSConstants.TRUE %>">
                                        Yes
                                    </logic:equal>
                                    <logic:equal name ="pageElementVO"  property="waUiMetadataDT.relatedUnitInd" value="<%= NEDSSConstants.FALSE %>">
                                        No                
                                    </logic:equal> 
                                </td>
                            </tr> 
                            <logic:equal name="pageElementVO" property="waUiMetadataDT.relatedUnitInd" value="<%= NEDSSConstants.TRUE %>">  
                                <tr>
                                    <td class="fieldName">Units Type:</td>
                                    <td>
                                        <logic:present name="unitTypeCdDesc">
                                            <bean:write name ="unitTypeCdDesc"/>    
                                        </logic:present>
                                    </td>
                                </tr>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.unitTypeCd" value="CODED">
                                    <tr>
                                        <td class="fieldName"> Related Units Value Set:</td>
                                        <td>
                                            <logic:present name="unitValueDesc">
                                                <bean:write name ="unitValueDesc"/>    
                                            </logic:present>
                                        </td>
                                    </tr>
                                </logic:equal>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.unitTypeCd" value="LITERAL">
                                    <tr>
                                        <td class="fieldName">Literal Units Value:</td>
                                        <td>
                                           <nedss:view name ="pageElementVO"  property="waUiMetadataDT.unitValue"/>  
                                        </td>
                                    </tr>
                                </logic:equal>
                            </logic:equal>
                        </logic:equal>
                             
                        <logic:equal name="pageElementVO" property="waUiMetadataDT.dataType" value="TEXT">   
                            <tr>
                                <td class="fieldName"> 
                                    <span title="Mask">Mask:</span>  
                                </td>
                                <td>
                                    <logic:present name="maskDesc">
                                        <bean:write name ="maskDesc"/>    
                                    </logic:present>
                                </td>
                            </tr>  
                            <tr>
                                <td class="fieldName"> 
                                    <span title="Field Length">Field Length:</span>  
                                </td>
                                <td>
                                    <nedss:view name ="pageElementVO"  property="waUiMetadataDT.fieldLength"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="fieldName">Default Value:</td>
                                <td>
                                    <nedss:view name ="pageElementVO"  property="waUiMetadataDT.defaultValue"/>
                                </td>
                            </tr>
                        </logic:equal>
                    </nedss:container>
                    
                    <!-- SUBSECTION: User Interface -->
                    <nedss:container id="subsect_userInterface" name="User Interface" classType="subSect" >
                        <tr>
                            <td class="fieldName">Label on Screen:</td>
                            <td>
                                <nedss:view name="pageElementVO" property="waUiMetadataDT.questionLabel" />      
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Tool Tip:</td>
                            <td>
                                <nedss:view name="pageElementVO" property="waUiMetadataDT.questionToolTip" />    
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Display Control:</td>
                            <td>
                                <logic:present name="defaultDisplayControlDesc">
                                  <bean:write name="defaultDisplayControlDesc" />
                                </logic:present>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Visible:</td>
                            <td>
                                <logic:present name="pageElementVO" property="waUiMetadataDT.displayInd">
                                    <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="<%= NEDSSConstants.TRUE %>">
                                        Yes
                                    </logic:equal>
                                    <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="<%= NEDSSConstants.FALSE %>">
                                        No
                                    </logic:equal>
                                </logic:present>
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName">Enabled:</td>
                            <td>
                                <logic:present name="pageElementVO" property="waUiMetadataDT.enableInd">
                                    <logic:equal name="pageElementVO" property="waUiMetadataDT.enableInd" value="<%= NEDSSConstants.TRUE %>">
                                        Yes
                                    </logic:equal>
                                    <logic:equal name="pageElementVO" property="waUiMetadataDT.enableInd" value="<%= NEDSSConstants.FALSE %>">
                                        No
                                    </logic:equal>
                                </logic:present>
                            </td>
                        </tr>
                        <tr>
                            <td class="fieldName">Required:</td>
                            <td>
                                <logic:present name="pageElementVO" property="waUiMetadataDT.requiredInd">
                                    <logic:equal name="pageElementVO" property="waUiMetadataDT.requiredInd" value="<%= NEDSSConstants.TRUE %>">
                                        Yes
                                    </logic:equal>
                                    <logic:equal name="pageElementVO" property="waUiMetadataDT.requiredInd" value="<%= NEDSSConstants.FALSE %>">
                                        No
                                    </logic:equal>
                                </logic:present>
                            </td>
                        </tr>
                                                
                    </nedss:container>
                    
                    <!-- SUBSECTION: Data Mart -->
                    <nedss:container id="subsect_dataMart" name="Data Mart" classType="subSect" >
                        <tr>
                            <td class="fieldName">Default Label in Report:</td>
                            <td>
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.reportLabel" />      
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Default RDB Table Name:</td>
                            <td>
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.rdbTableNm"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">RDB Column Name:</td>
                            <td>
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.rdbcolumnNm"/>      
                            </td>
                        </tr>  
						<tr>
                            <td class="fieldName">Data Mart Column Name:</td>
                            <td>
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.userDefinedColumnNm"/>      
                            </td>
                        </tr>  

						 						
                                            
                    </nedss:container>
                    
                    <!-- SUBSECTION: Messaging -->
                    <nedss:container id="subsect_messaging" name="Messaging" classType="subSect" >
                        <tr>
                            <td class="fieldName">Included in Message:</td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
                                    Yes
                                </logic:equal>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.nndMsgInd" value="<%= NEDSSConstants.FALSE %>">
                                    No
                                </logic:equal>
                            </td>               
                        </tr>
                                 
                        <tr>
                            <td class="fieldName">Message Variable ID:</td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
                                    <nedss:view name="pageElementVO" property="waUiMetadataDT.questionIdentifierNnd" />    
                                </logic:equal>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Label in Message:</td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
                                    <nedss:view name="pageElementVO"  property="waUiMetadataDT.questionLabelNnd"  />
                                </logic:equal>      
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Code System Name:</td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
                                    <nedss:view name="pageElementVO"  property="waUiMetadataDT.questionOidSystemTxt"  />
                                </logic:equal>      
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Required in Message:</td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.questionReqNnd" value="<%= NEDSSConstants.OPTIONAL %>">
                                    Optional
                                </logic:equal>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.questionReqNnd" value="<%= NEDSSConstants.REQUIRED %>">
                                    Required
                                </logic:equal>
                            </td>               
                        </tr>
                                 
                        <tr>
                            <td class="fieldName">HL7 Data Type:</td>
                            <td>
                                <logic:present name="questionDataTypeNndDesc">
                                    <bean:write name ="questionDataTypeNndDesc"/>
                                </logic:present>
                            </td>
                        </tr>      
                        
                        <tr>
                            <td class="fieldName">HL7 Segment:</td>
                            <td>
                                <logic:present name="hl7SegmentDesc">
                                    <bean:write name="hl7SegmentDesc" />
                                </logic:present>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="fieldName">Group Number(Order Group ID):</td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.nndMsgInd" value="<%= NEDSSConstants.TRUE %>">
                                    <nedss:view name="pageElementVO" property="waUiMetadataDT.orderGrpId"  />
                                </logic:equal>
                            </td>
                        </tr>                        
                    </nedss:container>
                    
                    <!-- SUBSECTION: Administrative Comments -->
                    <nedss:container id="subsect_administrative" name="Administrative" classType="subSect">
                        <tr>
                            <td class="fieldName"> 
                                <span id="adminCommentL" title="Administrative Comments">Administrative Comments:</span>
                            </td>
                            <td>                
                                <nedss:view name ="pageElementVO" property="waUiMetadataDT.adminComment" />    
                            </td>
                        </tr>
                    </nedss:container>
                </nedss:container>
            </div>
        </div>                                    
        
        <!-- Bottom button bar -->
        <div class="popupButtonBar">
            <input type="button" value="Edit" onclick="editQuestion()"></input>
            <input type="button" value="Close" onclick="handlePageUnload(true, event)"></input>          
        </div>
    </body>
</html>