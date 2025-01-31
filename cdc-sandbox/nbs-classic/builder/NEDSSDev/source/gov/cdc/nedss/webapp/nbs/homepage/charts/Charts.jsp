<%@ page import = " gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartRenderer" %>
<%@ page import = " gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartElementDataSet" %>
<%@ page import = " gov.cdc.nedss.systemservice.util.DropDownCodeDT" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.Locale" %>
<script Language="JavaScript" Src="/nbs/dwr/interface/JFChart.js"></script>

<script>
   var chartId = '<%= (String) request.getAttribute("ChartId") %>'; 
   
   if( chartId == 'C003'){   
    document.getElementsByName("loadChart").value=chartId;     
    getChart(); 
   }

</script>

<logic:notEmpty name="homepageForm" property="chartList">
<div style="height:0px;width:0px;display:none;" id="crdiv">
    </div>
   
 <nedss:dashlet id="myCharts" name="${homepageForm.attributeMap.chartTitle}" 
	    isDraggable="true" headerClass="header dd-multi-handle-${colIndex}" 
	    contentStyle="height:${dashletHeight}px; text-align:center;background: White url('ajax-loader.gif') no-repeat center;"
	    headerId="dd-handle-${colIndex}_${rowIndex}a">
		<div id="lineListResults"></div>     
		<img id="chartImg" alt = "Cases created - Last 7 days" src="/nbs/chartServlet" width="285" height="250" border="0">
	<br/>
	<div  style="white-space: nowrap; display:inline-block">
		<div style="display:inline-block">
				<html:select name="homepageForm" property="attributeMap.currentChart" styleId="charts" onchange="getChart();" title="Select the chart to display">
					<html:optionsCollection property="chartList" value="key" label="value"/>
				</html:select>  
		</div>
			<div style="display:inline-block">
			<img id="magnify" src="magnify.jpg" title="Magnify" alt="Magnify" onclick="javascript:getPopupChart();" style="cursor: pointer;text-decoration:none; float:right"/>
		</div>
	</div>		
   </nedss:dashlet>
<div id="popupContact" style="text-align:center;background: White url('ajax-loader.gif') no-repeat center;">  
		  <img id="popupContactClose" src="close.gif" title="Close" alt="Close" style="cursor: pointer;text-decoration:none;"/>
         <h3 id="myCharts1">Nedss Charts</h3>  
         <p id="contactArea">  
				<img id="chartImg1" border="0" alt="">
         </p>  
     </div>     
</logic:notEmpty>
<input type='hidden' name='loadChart' value='<%= (String) request.getAttribute("ChartId") %>'/> 
<logic:empty name="homepageForm" property="chartList">
    <nedss:dashlet id="myCharts" name="My Charts" 
        isDraggable="true" headerClass="header dd-multi-handle-${colIndex}" 
        contentStyle="height:100px; text-align:left;"
        headerId="dd-handle-${colIndex}_${rowIndex}a">
            There are no metrics to display.
	</nedss:dashlet>
</logic:empty>


<script>

	if(document.getElementById("charts")!=null && document.getElementById("charts") !='undefined')
   	 document.getElementsByName("charts_textbox")[0].value=document.getElementById("charts").getElementsByTagName("option")[document.getElementById("charts").selectedIndex].textContent
    document.onkeyup = function (e){
			if((e.which==38 || e.which==40)){		
			getChart();
			}
		}

</script>
