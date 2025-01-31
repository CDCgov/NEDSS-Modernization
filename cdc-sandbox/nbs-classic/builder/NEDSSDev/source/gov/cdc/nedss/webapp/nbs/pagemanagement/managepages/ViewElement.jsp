<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants, gov.cdc.nedss.pagemanagement.util.PageMetaConstants" %>

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
            table.formTable td.fieldName {width:15em;}
        </style>
        
        <script type="text/javascript">
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
            function handlePageUnload(closePopup, e)
            {
                 // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                 // get reference to opener/parent           
                    var opener = getDialogArgument();
                    var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                    if (grayOverlay == null) {
                        grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                    }
                    grayOverlay.style.display = "none";
                    if ((closePopup == true) || (e != undefined && e != null && e.clientY < 0)) {
                        // pass the json to the parent
                        opener.manageElementDialogCallBack(document.jsonStringForm.jsonString.value);
                        // close this window
                        self.close();
                        return true;
                    }
                }
            }
            
            function editElement()
            {
                var pageEltId = '<nedss:view name ="pageElementVO" property="pageElementUid" />';
                document.pageElementForm.action ="/nbs/ManagePageElement.do?method=editLoad&eltType=fieldRow&pageElementUid=" + pageEltId;
                document.pageElementForm.submit();
            }
        </script>
    </head>
    <body class="popup" onunload="handlePageUnload(false, event);startCountdown();addRolePresentationToTabsAndSections(); return false;" style="text-align:center;">
        <div class="popupTitle">
            <logic:present name="pageTitle">
                <bean:write name="pageTitle" />
            </logic:present> 
        </div>
        
        <!-- Top button bar -->
        <div class="popupButtonBar">
            <!-- TODO: implement this edit button functionality 
            <input type="button" value="Edit" onclick="editElement()"></input>
             -->
            <input type="button" value="Close" onclick="handlePageUnload(true, event)"></input>          
        </div>
        
        <!-- Error Messages using Action Messages-->
        <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
        
        <!-- Collection of form fields to edit a question -->
        <form name="jsonStringForm">
        	<input type="hidden" name="jsonString" value="<bean:write name="elementsJson"/>" />
         </form>
        <div id="editPageSectionBlock">
            <nedss:container id="sect_section" name="View Element" displayLink="false"  classType="sect" displayImg="false" includeBackToTopLink="no">
	            <nedss:container id="subsect_basicInfo" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">
                    <!-- Tab -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aTAB) %>">
                        <tr>
                            <td class="fieldName"> Tab Name: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Visible: </td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="T">
                                    Yes
                                </logic:equal> 
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="F">
                                    No
                                </logic:equal>
                            </td>
                        </tr>
                    </logic:equal>
                    
                    <!-- Section -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aSECTION) %>">
                        <tr>
                            <td class="fieldName"> Section Name: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Visible: </td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="T">
                                    Yes
                                </logic:equal> 
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="F">
                                    No
                                </logic:equal>
                            </td>
                        </tr>
                    </logic:equal>
                    
                    <!-- Subsection -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aSUBSECTION) %>">
                        <tr>
                            <td class="fieldName"> Subsection Name: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Visible: </td>
                            <td>
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="T">
                                    Yes
                                </logic:equal> 
                                <logic:equal name="pageElementVO" property="waUiMetadataDT.displayInd" value="F">
                                    No
                                </logic:equal>
                            </td>
                        </tr>
                        <logic:notEmpty name="manageMap">
                        <tr>
                            <td class="fieldName"> Block Name: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.blockName" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Data Mart Repeat Number: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.dataMartRepeatNumber" /></td>
                        </tr>
                        </logic:notEmpty>
                        
                        <!-- This is for view of the repeating block -->
                        <logic:notEmpty name="manageMap">
	                      <nedss:container id="" name="Repeating block" classType="subSect" displayImg="false" includeBackToTopLink="no">
	                      
                        
	                      <tr><td align="center">
                              <table class="repeatingTable aggregateDataEntryTable" style="width:98%;">
		                        <thead>
		                            <tr>
		                               <th style="width:35%;"><u> Question </u></th>
	                                   <th style="width:20%;"><u> Appears in Table? </u></th>
	                                   <th style="width:20%;"><u> Label in Table </u></th>
	                                   <th style="width:25%;"><u> Table Column % </u></th>
		                            </tr>
		                        </thead>
		                        
		                        <tbody>
		                        	<logic:iterate id="mList" name="manageMap" indexId="index">
										  <tr>
										  	<td width="35%">
										        <bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).questionWithQuestionIdentifier" />
										    </td>
										    <td width="20%">
										        <bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).batchTableAppearIndCd" />
										    </td>
										    <td width="20%">
										        <bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).batchTableHeader" />
										    </td>
										    <td width="25%">
										        <bean:write name="pageElementForm" property="batchQuestionMap(${mList.key}).batchTableColumnWidth" />
										    </td>
										  </tr>
									</logic:iterate>
		                        </tbody>
		                     </table>
		                     </td></tr> 
	                        </nedss:container>
	                    </logic:notEmpty>
                    </logic:equal>
                    
                    <!-- Hyperlink -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aHYPERLINK) %>">
                        <tr>
                            <td class="fieldName"> Label: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Link URL: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.defaultValue" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>
                    
                    <!-- Read Only Comments -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aREADONLY) %>">
                        <tr>
                            <td class="fieldName"> Read Only Text: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>
                    
                    <!-- Action Button -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aACTIONBUTTON) %>">
                        <tr>
                            <td class="fieldName"> Read Only Text: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>
                    
                    <!-- Patient Search -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aPATIENTSEARCH) %>">
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>	 
                    
                    <!-- Set Value Comment -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aSETVALUEBUTTON) %>">
                        <tr>
                            <td class="fieldName"> Read Only Text: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.questionLabel" /></td>
                        </tr>
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td> <bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>
                    
                    <!-- Horizontal Bar -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aLINESEPARATOR) %>">
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>	 
                    <!-- Participant List -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aPARTICIPANTLIST) %>">
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>   
                    <!-- Participant List -->    
                    <logic:equal name="pageElementVO" property="waUiMetadataDT.nbsUiComponentUid" value="<%= String.valueOf(PageMetaConstants.aORIGDOCLIST) %>">
                        <tr>
                            <td class="fieldName"> Administrative Comments: </td>
                            <td><bean:write name="pageElementVO" property="waUiMetadataDT.adminComment" /></td>
                        </tr>    
                    </logic:equal>           
	            </nedss:container>
	        </nedss:container>              
        </div>
        
        <!-- Bottom button bar -->
        <div class="popupButtonBar">
            <!-- TODO: implement this edit button functionality 
            <input type="button" value="Edit" onclick="editElement()"></input>
             -->
            <input type="button" value="Close" onclick="handlePageUnload(true, event)"></input>          
        </div>
    </body>
</html>