<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.ArrayList, 
                 gov.cdc.nedss.webapp.nbs.form.summary.AggregateSummaryForm,
                 gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.AggregateSummaryDT,
                 gov.cdc.nedss.util.NEDSSConstants,
                 gov.cdc.nedss.webapp.nbs.action.summary.util.CategoryTable" %>
                 
<script type="text/javascript">
    function cancelForm()
    {
        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg)) {
            document.aggregateSummaryForm.action ="${aggregateSummaryForm.attributeMap.cancel}";
            document.aggregateSummaryForm.submit();
        } else {
            return false;
        }
    }   

    function submitForm()
    {
        document.aggregateSummaryForm.action ="${aggregateSummaryForm.attributeMap.submit}";
        document.aggregateSummaryForm.submit();
    }
</script>

<style type="text/css">
    table.aggregateDataEntryTable a {color:#000; text-decoration:none;}
</style>


<!-- *************** MESSAGE BLOCK ***************  
Message blocks (both success and error messages) for 
    communicating messages that are the result of save, send notification,
    delete related activities -->
<!-- errror ActionMessages -->  
<div id="default"></div>
<logic:messagesPresent name="aggregateReport_errorMessages">
    <div class="infoBox errors" style="width:98%; margin-top:1.5em; text-align:left;">
        <b> <a></a> Please fix the following errors:</b> <br/>
        <ul>
            <html:messages id="msg" name="aggregateReport_errorMessages">
                <li> <bean:write name="msg" /> </li>
            </html:messages>
        <ul>
    </div>
</logic:messagesPresent>
<!-- client side validation errors messages -->
<div class="infoBox errors"  id="aggregateReportFormEntryErrors" style="display:none;width:98%; margin-top:1.5em;">
</div>

<!-- success ActionMessages -->
<logic:messagesPresent name="aggregateReport_successMessages">
    <div class="infoBox success" style="width:98%; margin-top:1.5em; text-align:left;">
        <html:messages id="msg" name="aggregateReport_successMessages">
            <bean:write name="msg" />
        </html:messages>
    </div>
</logic:messagesPresent>
<!-- ************ END OF MESSAGE BLOCK ************ -->

<!-- Create/Edit mode of Aggregate Report -->
<bean:define id="editableMode"
        value='<%= ((AggregateSummaryForm)session.getAttribute("aggregateSummaryForm")).getActionMode().equals(NEDSSConstants.CREATE) || 
        ((AggregateSummaryForm)session.getAttribute("aggregateSummaryForm")).getActionMode().equals(NEDSSConstants.EDIT) ? "true" : "false"  %>' />
<logic:equal name="aggregateSummaryForm" property="actionMode" value="<%= NEDSSConstants.CREATE %>">
	<bean:define id="sectionTitle" value="Add Aggregate Report" toScope="request"/>
</logic:equal>
<logic:equal name="aggregateSummaryForm" property="actionMode" value="<%= NEDSSConstants.EDIT %>">
    <bean:define id="sectionTitle" value="Edit Aggregate Report" toScope="request"/>
</logic:equal>

<logic:equal name="editableMode" value="true">
    <script type="text/javascript">
        // disable all input elts in the search and search results block
        $j("#searchBlock input").attr("disabled", true);
        $j("#searchBlock select").attr("disabled", true);        
        $j("#searchResultsBlock input").attr("disabled", true);
        
        // disable all the links and replace the image icons with disabled ones in the search results display tag table
        $j("#parent img[src='page_white_edit.gif']").attr("src", "page_white_edit_disabled.gif");
        $j("#parent img[src='page_white_edit_disabled.gif']").attr("alt", "Edit disabled");
        $j("#parent img[src='page_white_edit_disabled.gif']").attr("title", "Edit disabled");
        
        $j("#parent img[src='page_white_text.gif']").attr("src", "page_white_text_disabled.gif");
        $j("#parent img[src='page_white_text_disabled.gif']").attr("alt", "View disabled");
        $j("#parent img[src='page_white_text_disabled.gif']").attr("title", "View disabled");
        
        
        //$j("#parent img").attr("alt", "");
        
        $j("#parent a").css("cursor", "default");
        $j("#parent a").click(function() { return false; });
        
        $j("#searchResultsBlock .pagelinks a").css({'color':'#CCC','cursor':'default'});
        $j("#searchResultsBlock .pagelinks a").click(function() { return false; });

    </script>

    <fieldset style="border-width:0px;" id="aggregatesum">
	    <nedss:container id="editBlock" name='<%= String.valueOf(request.getAttribute("sectionTitle") == null ? "" : request.getAttribute("sectionTitle")) %>' classType="sect" displayImg ="false">
		    <nedss:container id="editBlock_metadata" name="Aggregate Report Details" classType="subSect">
	            <!-- Reporting Area -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM100.state.requiredIndClass}">*</span>
	                    <span id="SUM100L" style="${aggregateSummaryForm.formFieldMap.SUM100.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM100.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM100.label}
	                    </span> 
	                </td>
	                <td>
	                    <html:select title="${aggregateSummaryForm.formFieldMap.SUM100.label}" disabled ="${aggregateSummaryForm.formFieldMap.SUM100.state.disabled}" name="aggregateSummaryForm" property="answerMap(SUM100)" styleId = "SUM100">
								<html:optionsCollection property="dwrDefaultStateCounties" value="key" label="value"/>
	                    </html:select>
	                </td>
	            </tr>
	            <!-- MMWR Year -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM101.state.requiredIndClass}">*</span>
	                    <span id="SUM101L" style="${aggregateSummaryForm.formFieldMap.SUM101.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM101.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM101.label}
	                    </span> 
	                </td>
	                <td>
                      <html:select title="${aggregateSummaryForm.formFieldMap.SUM101.label}" disabled ="${aggregateSummaryForm.formFieldMap.SUM101.state.disabled}" name="aggregateSummaryForm" property="answerMap(SUM101)" styleId = "SUM101" onchange="populateMMWRYear('SUM101','SUM102')">
                            <html:optionsCollection property="mmwrYears" value="key" label="value"/>
                      </html:select>
	                </td>
	            </tr>
	            <!-- MMWR Week -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM102.state.requiredIndClass}">*</span>
	                    <span id="SUM102L" style="${aggregateSummaryForm.formFieldMap.SUM102.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM102.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM102.label}
	                    </span> 
	                </td>
	                <td>
	                    <html:select title="${aggregateSummaryForm.formFieldMap.SUM102.label}" disabled ="${aggregateSummaryForm.formFieldMap.SUM102.state.disabled}" name="aggregateSummaryForm" property="answerMap(SUM102)" styleId = "SUM102">
	                          <html:optionsCollection property="mmwrWeekList" value="key" label="value"/>
	                    </html:select>
	                </td>
	            </tr>                                
	            <!-- Condition -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM106.state.requiredIndClass}">*</span>
	                    <span id="SUM106L" style="${aggregateSummaryForm.formFieldMap.SUM106.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM106.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM106.label}
	                    </span> 
	                </td>
	                <td>
	                    <html:select title="${aggregateSummaryForm.formFieldMap.SUM106.label}" disabled ="${aggregateSummaryForm.formFieldMap.SUM106.state.disabled}" name="aggregateSummaryForm" property="answerMap(SUM106)" styleId = "SUM106">
	                          <html:optionsCollection property="conditions" value="key" label="value"/>
	                    </html:select>
	                </td>
	            </tr>	            
	            <!-- Surveillance Method -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM117.state.requiredIndClass}">*</span>
	                    <span id="SUM117L" style="${aggregateSummaryForm.formFieldMap.SUM117.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM117.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM117.label}
	                    </span> 
	                </td>
	                <td>
	                    <html:select title="${aggregateSummaryForm.formFieldMap.SUM117.label}" disabled ="${aggregateSummaryForm.formFieldMap.SUM117.state.disabled}" name="aggregateSummaryForm" property="answerMap(SUM117)" styleId = "SUM117">
	                          <html:optionsCollection property="codedValue(SUM117)" value="key" label="value"/>
	                    </html:select>
	                </td>
	            </tr>
	             <!-- Number of Hospitals Reporting -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM131.state.requiredIndClass}">*</span>
	                    <span id="SUM131L" style="${aggregateSummaryForm.formFieldMap.SUM131.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM131.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM131.label}
	                    </span> 
	                </td>
	                <td>
		                <html:text styleId="SUM131" 
	                        disabled ="${aggregateSummaryForm.formFieldMap.SUM131.state.disabled}"
	                        onkeydown="checkMaxLength(this);isNumericCharacterEntered(this)"  onkeyup="checkMaxLength(this);isNumericCharacterEntered(this)"
	                         title="${aggregateSummaryForm.formFieldMap.SUM131.label}" maxlength="4" 
	                        name="aggregateSummaryForm" property="answerMap(SUM131)"/>
	                </td>	                
	            </tr>	           
          
	            <!-- Comments -->
	            <tr>
	                <td class="fieldName">
	                    <span style="${aggregateSummaryForm.formFieldMap.SUM105.state.requiredIndClass}">*</span>
	                    <span id="SUM105L" style="${aggregateSummaryForm.formFieldMap.SUM105.errorStyleClass}" 
	                            title="${aggregateSummaryForm.formFieldMap.SUM105.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM105.label}
	                    </span> 
	                </td>
	                <td>
		                <html:textarea title="${aggregateSummaryForm.formFieldMap.SUM105.label}" style="WIDTH: 500px; HEIGHT: 100px;" styleId="SUM105" 
	                        disabled ="${aggregateSummaryForm.formFieldMap.SUM105.state.disabled}"
	                        onkeydown="checkMaxLength(this)"  onkeyup="checkMaxLength(this)"
	                        name="aggregateSummaryForm" property="answerMap(SUM105)"/>
	                </td>
	            </tr>
		    </nedss:container>
		    
		    <logic:notEmpty name="categoryTables">
			    <logic:iterate id="cTable" name="categoryTables" indexId="categoryIndex">
			    		<% String tableUid = String.valueOf(((CategoryTable)cTable).getNbsTableUid()); %>
	                <nedss:container id="editBlock_${cTable.nbsTableUid}" name="${cTable.tableName}" classType="subSect">
			          <bean:define id="tableId" value='<%= "Table_" + categoryIndex %>' />
			           <tr> <td style="padding-left:1em;">
		                    <table role="presentation" class="gridTable aggregateDataEntryTable" style="float:left; margin-left: 0px" id="<bean:write name="tableId" />">
		                        <thead>
		                            <tr>
		                               <logic:iterate id="headCell" name="cTable" property="headers" indexId="headColIndex">
		                                   <td style = "background-color: #EFEFEF; border:1px solid #BBB" class='<%= "h" + headColIndex %>'> <bean:write name="headCell" property="indicatorLabel" /> </td>
		                               </logic:iterate>
		                            </tr>
		                        </thead>
		                        <tbody>
		                            <bean:define id="totalRows" value="<%= String.valueOf(((CategoryTable)cTable).getRecords().size()) %>" />
		                            <logic:iterate id="tableRow" name="cTable" property="records" indexId="rowIndex">
		                                <bean:define id="rowIndexMod" value="<%= String.valueOf(rowIndex.intValue() % 2) %>" />
		                                <logic:equal name="rowIndexMod" value="0">
		                                    <bean:define id="cellClass" value="even" />
		                                </logic:equal>
		                                <logic:equal name="rowIndexMod" value="1">
		                                    <bean:define id="cellClass" value="odd" />
		                                </logic:equal>
		                                <tr>
		                                    <logic:iterate id="bodyCell" name="tableRow" indexId="index">
		                                        <bean:define id="cellToolTip" value="<%= ((AggregateSummaryDT)bodyCell).getAggregateToolTip() %>" />
		                                        <logic:equal name="index" value="0">
		                                            <td class='<%= "r" + rowIndex + "c" + index %> <bean:write name="cellClass" />' style="padding-left:5px;text-align:left;font-weight:bold;"> <a title="<bean:write name="cellToolTip" />"> <bean:write name="bodyCell" property="aggregateLabel" /> </a> </td>
		                                        </logic:equal>
		                                        <logic:notEqual name="index" value="0">
		                                            <td class='<%= "r" + rowIndex + "c" + index %> <bean:write name="cellClass" />'>
		                                                <bean:define id="tempVal" value="<%= String.valueOf(Integer.parseInt(totalRows) -1) %>" />
		                                                <logic:equal name="tempVal" value="<%= String.valueOf(rowIndex.intValue()) %>">
	                                                        <html:text title="<%= cellToolTip %>" 
	                                                               property="answer(${bodyCell.questionIdentifier}_${bodyCell.nbsTableMetaDataUid})" 
	                                                               styleId='<%=tableUid  + "_c" + index + "Total" %>'  size="3" value="${bodyCell.answerTxt}"/>
	                                                    </logic:equal>
	                                                    <logic:notEqual name="tempVal" value="<%= String.valueOf(rowIndex.intValue()) %>">
	                                                        <html:text title="<%= cellToolTip %>" 
	                                                               property="answer(${bodyCell.questionIdentifier}_${bodyCell.nbsTableMetaDataUid})" 
	                                                               styleClass='<%= "c" + index %>' styleId="" size="3" value="${bodyCell.answerTxt}"/>
	                                                    </logic:notEqual>                                              
		                                            </td>
		                                        </logic:notEqual>
		                                    </logic:iterate>
		                                </tr>
		                            </logic:iterate>
		                        </tbody>
		                    </table>
		                </td> </tr>
			       </nedss:container>
			    </logic:iterate>
		    </logic:notEmpty>
		    
		    
		    <table role="presentation" style="width:99%;margin-top:1em;">
		        <tr>
		            <td style="text-align:right;">
		                <logic:equal name="aggregateSummaryForm" property="actionMode" value="<%= NEDSSConstants.CREATE %>">
		                    <input type="button" value="Submit" title="Submit Aggregate Data" 
		                           onclick="javascript:submitAggregateData('<bean:write name="aggregateSummaryForm" 
		                           property="actionMode" />', null)"/>
		                    <input type="button"  ${aggregateSummaryForm.attributeMap.disabled} 
		                           value="Submit and Send Notification" title="Submit and Send Notification Data" 
		                           onclick="javascript:submitAggregateDataWithNotif('<bean:write name="aggregateSummaryForm" 
		                           property="actionMode" />', null)"/>
	                        <input type="button" value="Cancel" title="Cancel Aggregate Data" 
	                                onclick="javascript:cancelDataEntry('<bean:write name="aggregateSummaryForm" 
	                                property="actionMode" />', null)"/>
						</logic:equal>
						<logic:equal name="aggregateSummaryForm" property="actionMode" value="<%= NEDSSConstants.EDIT %>">
	                        <input type="button" value="Submit" title="Submit Aggregate Data" 
	                               onclick="javascript:submitAggregateData('<bean:write name="aggregateSummaryForm" 
	                               property="actionMode" />', '<bean:write name="phcUid" />')"/>
	                        <input type="button"  ${aggregateSummaryForm.attributeMap.disabled} 
	                               value="Submit and Send Notification" title="Submit and Send Notification Data" 
	                               onclick="javascript:submitAggregateDataWithNotif('<bean:write name="aggregateSummaryForm" 
	                               property="actionMode" />', '<bean:write name="phcUid" />')"/>
	                        <input type="button" value="Cancel" title="Cancel Aggregate Data" 
	                                onclick="javascript:cancelDataEntry('<bean:write name="aggregateSummaryForm" 
	                                property="actionMode" />', '<bean:write name="phcUid" />')"/>                       
						</logic:equal>
		            </td>
		        </tr>
	        </table>
		</nedss:container>
	</fieldset>
</logic:equal>

<!-- View mode of Aggregate Report -->
<logic:equal name="aggregateSummaryForm" property="actionMode" value="<%= NEDSSConstants.VIEW %>">
    <fieldset style="border-width:0px;" id="aggregatesum">
	    <nedss:container id="viewBlock" name="View Aggregate Report" classType="sect" displayImg ="false">
	        <!-- view mode metadata -->
	        <nedss:container id="viewBlock_metadata" name="Aggregate Report Details" classType="subSect">
	            <!-- Reporting Area -->
	            <tr>
	                <td class="fieldName">
		                <span style="${aggregateSummaryForm.formFieldMap.SUM100.state.requiredIndClass}">*</span>
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM100.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM100.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM100)" methodNm="CountyCodesInclStateWide" methodParam="${aggregateSummaryForm.attributeMap.SUM100_STATE}"/>
	                </td>
	            </tr>
	            <!-- MMWR Year -->
	            <tr>
	                <td class="fieldName">
          		       <span style="${aggregateSummaryForm.formFieldMap.SUM101.state.requiredIndClass}">*</span>
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM101.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM101.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM101)"/>
	                </td>
	            </tr>
	            <!-- MMWR Week -->
	            <tr>
	                <td class="fieldName">
							<span style="${aggregateSummaryForm.formFieldMap.SUM102.state.requiredIndClass}">*</span>
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM102.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM102.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM102)"/>
	                </td>
	            </tr>
	            <!-- Condition -->
	            <tr>
	                <td class="fieldName">
		                <span style="${aggregateSummaryForm.formFieldMap.SUM106.state.requiredIndClass}">*</span>	                
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM106.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM106.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM106)" methodNm="AllConditions"/>
	                </td>
	            </tr>
	            <!-- Surveillance Method-->
	            <tr>
	                <td class="fieldName">
       		          <span style="${aggregateSummaryForm.formFieldMap.SUM117.state.requiredIndClass}">*</span>
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM117.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM117.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM117)" 
	                        codeSetNm="${aggregateSummaryForm.formFieldMap.SUM117.codeSetNm}"/>
	                </td>
	            </tr>
	            <!-- Number of Hospitals Reporting -->
	            <tr>
	                <td class="fieldName">
							<span style="${aggregateSummaryForm.formFieldMap.SUM131.state.requiredIndClass}">*</span>
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM131.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM131.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM131)"/>
	                </td>
	            </tr>
	            <!-- Comments -->
	            <tr>
	                <td class="fieldName">
							<span style="${aggregateSummaryForm.formFieldMap.SUM105.state.requiredIndClass}">*</span>
	                    <span title="${aggregateSummaryForm.formFieldMap.SUM105.tooltip}">
	                        ${aggregateSummaryForm.formFieldMap.SUM105.label}
	                    </span> 
	                </td>
	                <td>
	                    <nedss:view name="aggregateSummaryForm" property="answerMap(SUM105)"/>
	                </td>
	            </tr>
	            <html:hidden name="aggregateSummaryForm" property="attributeMap.NotificationExists" styleId="NotificationExists"/>
	        </nedss:container>
	
	        <!-- View mode of tables -->
	        <logic:present name="categoryTables">
	            <logic:iterate id="cTable" name="categoryTables">
	               <nedss:container id="viewBlock_${cTable.nbsTableUid}" name="${cTable.tableName}" classType="subSect">
	                   <tr> <td style="padding-left:1em;">
	                        <table role="presentation" class="gridTable aggregateDataEntryTable" style="float:left">
	                            <thead>
	                                <tr>
	                                   <logic:iterate id="headCell" name="cTable" property="headers">
	                                       <td style = "background-color: #EFEFEF; border:1px solid #BBB"> <bean:write name="headCell" property="indicatorLabel" /> </td>
	                                   </logic:iterate>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <logic:iterate id="tableRow" name="cTable" property="records" indexId="rowIndex">
	                                    <bean:define id="rowIndexMod" value="<%= String.valueOf(rowIndex.intValue() % 2) %>" />
	                                    <logic:equal name="rowIndexMod" value="0">
	                                        <bean:define id="cellClass" value="even" />
	                                    </logic:equal>
	                                    <logic:equal name="rowIndexMod" value="1">
	                                        <bean:define id="cellClass" value="odd" />
	                                    </logic:equal>
	                                    <tr>
	                                        <logic:iterate id="bodyCell" name="tableRow" indexId="index">
	                                            <bean:define id="cellToolTip" value="<%= ((AggregateSummaryDT)bodyCell).getAggregateToolTip() %>" />
	                                            <logic:equal name="index" value="0">
	                                                <td class='<%= "r" + rowIndex + "c" + index %> <bean:write name="cellClass" />' style="padding-left:5px;text-align:left;font-weight:bold;"> <a title="<bean:write name="cellToolTip" />"> <bean:write name="bodyCell" property="aggregateLabel" /> </a> </td>
	                                            </logic:equal>
	                                            <logic:notEqual name="index" value="0">
	                                                <td class='<%= "r" + rowIndex + "c" + index %> <bean:write name="cellClass" />'> <bean:write name="bodyCell" property="answerTxt" /></td>
	                                            </logic:notEqual>
	                                        </logic:iterate>
	                                    </tr>
	                                </logic:iterate>
	                            </tbody>
	                        </table>
	                    </td> </tr>
	               </nedss:container>
	            </logic:iterate>
	        </logic:present>
	    </nedss:container>
	    
	    <!-- view mode action buttons -->
	    <table role="presentation" style="width:99%;margin-top:1em;">
            <tr>
                <td style="text-align:right;">
	                <logic:equal name="aggregateSummaryForm" property="attributeMap(addButton)" value="true">
	                    	<input type="button" value="Edit" title="Edit Aggregate Report Data" 
                           	onclick="javascript:editAggregateReport('<bean:write name="phcUid" />')"/>	                
	                    	<input type="button"  ${aggregateSummaryForm.attributeMap.disabled} value="Send Notification" title="Send Notification" 
	                           onclick="javascript:sendNotification('<bean:write name="phcUid" />')"/>
	                </logic:equal>
	                  <logic:equal name="aggregateSummaryForm" property="attributeMap(deleteButton)" value="true">
                    		<input type="button" value="Delete" title="Delete Aggregate Report" 
                           	onclick="javascript:deleteAggregateReport('<bean:write name="phcUid" />')"/>
						</logic:equal>         
       
                </td>
            </tr>
        </table>
         
    </fieldset>       
</logic:equal>	

<script type="text/javascript">
    // Perform operations when the document is ready loaded completely and ready
    // to be manipulated.
    $j(document).ready(function() {

        // focus on the first valid element        
        var currentActionMode = "<bean:write name="aggregateSummaryForm" property="actionMode" />";
        if (currentActionMode != "<%= NEDSSConstants.VIEW %>") {
	        $j("body").find(':input:visible:enabled:first').focus();
        }
        
        // handle count level change
        $j("#countLevel").change(function(event){
            if ($j(this).val() == "totalCount") {
                $j("#editAggregateReportTable :input").attr("disabled", false);
            }
            else if ($j(this).val() == "ageRange") {
                $j("#editAggregateReportTable :input").attr("disabled", true);
            }
            else {
                alert("Unhandled selection...");
            }
        });
        

        // attach events to input elements in category table grids    
        <logic:present name="categoryTables">
            <logic:iterate id="cTable" name="categoryTables" indexId="categoryIndex">
                <bean:define id="tableId" value='<%= "Table_" + categoryIndex %>' />
                
                // validate entries in text boxes and update the total count simultaneously 
		        $j("#<bean:write name="tableId"/> input").keyup(function(event){
                    isNumericCharacterEntered(this);
                    
                    if (jQuery.trim($j(this).val()) != "" || isNumeric(this)) {
                        var ips = $j("#<bean:write name="tableId"/>").find("." + $j(this).attr("class"));
                        var colTotal = 0;
                        for (var i = 0; i < ips.length; i++) {
                            if (jQuery.trim($j(ips[i]).val()) != "") {
                                colTotal += parseInt($j(ips[i]).val());    
                            }
                        }
                        if (colTotal > 0) {
                            $j("#<bean:write name="cTable" property="nbsTableUid" />_" + $j(this).attr("class") + "Total").val(colTotal);    
                        }
                        else {
                            $j("#<bean:write name="cTable" property="nbsTableUid" />_" + $j(this).attr("class") + "Total").val("");    
                        }
                    } 
                    
		        });

                // highlight the corresponding row and column to which the input element
                // belongs. This is done to improve usability.
		        $j("#<bean:write name="tableId"/> input").focus(function(event){
                    var eltClass = jQuery.trim($j($j(this).parent().get(0)).attr("class"));
                    var rowPrefix = jQuery.trim(eltClass.substring(0,2));
                    var colPrefix = jQuery.trim(eltClass.substring(2,4));

                    $j($j("#<bean:write name="tableId"/>").find("td[class*='" + colPrefix + "']")).addClass("selected");
                    $j($j("#<bean:write name="tableId"/>").find("td[class*='" + rowPrefix + "']")).addClass("selected");
                    
                    var colIndex = colPrefix.replace("c","");
                    var colHeadClass = "h" + colIndex;
                    $j($j("#<bean:write name="tableId"/>").find("th[class*='" + colHeadClass + "']")).addClass("selected");
                });
                $j("#<bean:write name="tableId"/> input").blur(function(event){
                    var eltClass = jQuery.trim($j($j(this).parent().get(0)).attr("class"));
                    var rowPrefix = jQuery.trim(eltClass.substring(0,2));
                    var colPrefix = jQuery.trim(eltClass.substring(2,4));
                    
                    $j($j("#<bean:write name="tableId"/>").find("td[class*='" + colPrefix + "']")).removeClass("selected");
                    $j($j("#<bean:write name="tableId"/>").find("td[class*='" + rowPrefix + "']")).removeClass("selected");
                    
                    var colIndex = colPrefix.replace("c","");
                    var colHeadClass = "h" + colIndex;
                    $j($j("#<bean:write name="tableId"/>").find("th[class*='" + colHeadClass + "']")).removeClass("selected");
                });
            </logic:iterate>
        </logic:present>
    });
    
    /** Validate the form by checking for required fields */
    function validateDataEntryForm(context)
    {
        $j(".infoBox").hide();

        var errors = new Array();
        var index = 0;
        var isValid = true;
        
        var fieldIds = ["SUM100","SUM101","SUM102","SUM106","SUM117"];
        var fieldNames = ["Reporting Area", "MMWR Year", "MMWR Week", "Condition","Surveillance Method"];
        for (var i = 0; i < fieldIds.length; i++) {
           if (jQuery.trim($j("#" + fieldIds[i]).val()) == "") {
               isValid = false;
               
               // add to errors array
               errors[index++] = fieldNames[i] + " is required";
               
               // highlight the field label to red
               $j("#" + fieldIds[i] + "L").css("color", "#C00");
           }
           else {
               // highlight the field label to black
               $j("#" + fieldIds[i] + "L").css("color", "#000");
           }
        }
        
        // check to see if the grid tables have atleast 1 cell populated
        <logic:present name="categoryTables">
            <logic:iterate id="cTable" name="categoryTables" indexId="categoryIndex">
                <bean:define id="tableId" value='<%= "Table_" + categoryIndex %>' />
                var tableIpElts = $j("#<bean:write name="tableId" /> input");
                var isGridEmpty = true;
                for (var i = 0; i < tableIpElts.length; i++) {
                    if (jQuery.trim($j(tableIpElts[i]).val()) != "") {
                        isGridEmpty = false;
                    }
                }
                
                if (isGridEmpty == true && context != 'WithNotif') {
                    errors[index++] = "<bean:write name="cTable" property="tableName" /> should contain at least one value";
                    isValid = false;                                        
                }
                if(context == 'WithNotif' && isReadyForNND() == false) {
                    errors[index++] = "<bean:write name="cTable" property="tableName" /> should contain at least one value from Died or Hospitalized columns to send a Notification.";
                    isValid = false;                                                        
                }
                
            </logic:iterate>
        </logic:present>            
        
        if(isValid == false) {
            displayErrors("aggregateReportFormEntryErrors", errors);
        }
        
        return isValid;
    }
    
    /** Validate the form by checking for required fields */
    function validateforSendNotification()
    {
        $j(".infoBox").hide();
        var errors = new Array();
        var index = 0;
        var isValid = true;
       // check to see if the grid tables have atleast 1 cell populated
           <logic:present name="categoryTables">
            <logic:iterate id="cTable" name="categoryTables" indexId="categoryIndex">
                <bean:define id="tableId" value='<%= "Table_" + categoryIndex %>' />      
                if( isNotificationReadyForNND() == false) {
                   errors[index++] = "<bean:write name="cTable" property="tableName" /> should contain at least one value from Died or Hospitalized columns to send a Notification.";
                    isValid = false;                                                        
                }
                  </logic:iterate>
        </logic:present>                     
           
        if(isValid == false) {
            displayErrors("aggregateReportFormEntryErrors", errors);
        }
        
        return isValid;
    }
    
    
    /** Send notification */
    function sendNotification(phcUid) {
       if (validateforSendNotification() == true) {
        document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?phcUid=" + phcUid + "&method=sendNotification#default";  
        document.aggregateSummaryForm.submit();
        }
    }
    
    /** Edit the form data (including the grid) for processing */
    function editAggregateReport(phcUid)
    {
				var notificationCheck = getElementByIdOrByName("NotificationExists");
				if(notificationCheck !=null && notificationCheck.value=='true') {
					var confirmMsg="An NND notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
					if (confirm(confirmMsg)) {
				        document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?phcUid=" + phcUid + "&method=editSummaryData#default";  
				        document.aggregateSummaryForm.submit();			
					} else {
						return false;
					}					
				} else {
				        document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?phcUid=" + phcUid + "&method=editSummaryData#default";  
				        document.aggregateSummaryForm.submit();			
				}
    }
    
    /** Delete the aggregate report */
    function deleteAggregateReport(phcUid)
    {
        var confirmMsg="Are you sure you want to delete this Report? Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg)) {
            document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?phcUid=" + phcUid + "&method=deleteSummaryData#searchResultsBlock";  
            document.aggregateSummaryForm.submit();
        }
    }
    
    /** Submit the form data (including the grid) for processing */
    function submitAggregateData(actionMode, phcUid)
    {
        var editMode = "<%= NEDSSConstants.EDIT %>";
        var createMode = "<%= NEDSSConstants.CREATE %>";
        
        if (validateDataEntryForm() == true) {
            if (actionMode == editMode && phcUid != null) {
                document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?method=updateSummaryData&phcUid=" + phcUid + "#default";  
                document.aggregateSummaryForm.submit();
            }
            else if (actionMode == createMode) {
                document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?method=createSubmitSummaryData#default";
                document.aggregateSummaryForm.submit();
            }
        }
    }
    
    function isReadyForNND() {
		  //For NND atleast one value is required from  Col2 (Died) or Col3(Hospitalized)
		  var answered = false;
			var tds = $j("table.gridTable tbody tr td");			
			    for (var i = 0; i < tds.length; i++) {
			        var td = $j(tds[i]);
			        if(td.attr('class').indexOf('c2') != -1 || td.attr('class').indexOf('c3') != -1) {
			        	if(td.find(':input').attr('value') != '')
			        		answered = true;
			        }
			    }
		return answered;    
    }
    
     function isNotificationReadyForNND() {
          //For NND atleast one value is required from  Col2 (Died) or Col3(Hospitalized)
          var answered = false;
            var tds = $j("table.gridTable tbody tr td");            
                for (var i = 0; i < tds.length; i++) {
                    var tdElt = $j(tds[i]);
                    if(tdElt.attr('class').indexOf('c2') != -1 || tdElt.attr('class').indexOf('c3') != -1) {
                       // alert(jQuery.trim($j(tdElt).html()));
                        if(jQuery.trim($j(tdElt).html()) != '')
                            answered = true;
                    }
                }
        return answered;    
    }
    
    /** Submit the form data (including the grid) and send a notification */
    function submitAggregateDataWithNotif(actionMode, phcUid)
    {
        var editMode = "<%= NEDSSConstants.EDIT %>";
        var createMode = "<%= NEDSSConstants.CREATE %>";

        if (validateDataEntryForm('WithNotif') == true) {
            if (actionMode == editMode && phcUid != null) {
                document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?method=updateSummaryData&ContextAction=SubmitAndSendNotif&phcUid=" + phcUid + "#default";
                document.aggregateSummaryForm.submit();
            }
            else if (actionMode == createMode) {
                document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?method=createSubmitSummaryData&ContextAction=SubmitAndSendNotif#default";
                document.aggregateSummaryForm.submit();
            }
        }
    }
    
    /** Cancel data entry (i.e., record addition or edit) and return to appropriate view) */
    function cancelDataEntry(actionMode, phcUid)
    {
        var editMode = "<%= NEDSSConstants.EDIT %>";
        var createMode = "<%= NEDSSConstants.CREATE %>";

        var confirmMsg="Are you sure you want to cancel the changes? Select OK to continue, or Cancel to not continue.";
        if (confirm(confirmMsg)) {
            if (actionMode == editMode && phcUid != null) {
	            document.aggregateSummaryForm.action = "/nbs/AggregateReport.do?phcUid=" + phcUid + "&method=viewSummaryData&existing=true#default";
	            document.aggregateSummaryForm.submit();        
	        }
	        else if (actionMode == createMode) {
	            document.aggregateSummaryForm.action ="/nbs/AggregateReport.do?method=searchSummaryDataSubmit&existing=true#searchResultsBlock";
	            document.aggregateSummaryForm.submit();        
	        }
	        else {
	            alert("Unsupported action...");
	        }
        }
    }
</script>