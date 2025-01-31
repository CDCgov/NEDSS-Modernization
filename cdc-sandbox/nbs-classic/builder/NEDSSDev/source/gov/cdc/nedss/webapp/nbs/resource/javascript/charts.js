function getChart() {
	$j("#charts").attr("disabled","disabled");
	$j("#lineListResults").parent().attr("style","height:300px; text-align:center;background: White url('	.gif') no-repeat center;");
	$j("#chartImg").show();
	$j("#lineListResults").hide();
	var cId = $j('#charts').val();
	var loadChart = document.getElementsByName("loadChart").value;	
	$j("#myCharts h2").html($j('#charts :selected').text());
	
	if(cId == 'C001')
		getElementByIdOrByName("chartImg").setAttribute("alt","Cases created - Last 7 days");
	else
		if(cId == 'C002')
			getElementByIdOrByName("chartImg").setAttribute("alt","Labs created - Last 7 days");
		else
			if(cId == 'C003')
				getElementByIdOrByName("chartImg").setAttribute("alt","Confirmed Cases Counts");
			else
				if(cId == 'C004')
					getElementByIdOrByName("chartImg").setAttribute("alt","Cases Assigned - Last 14 Days");
	
	if(cId != 'C003' && loadChart != 'C003') {
		$j("#chartImg").parent().attr("style","overflow:hidden;height:300px; text-align:center;background: White url('ajax-loader.gif') no-repeat center;");
		//$j("#chartImg").removeAttr("src");
		$j("#chartImg").attr("src","/nbs/chartServlet?charts=" + $j('#charts').val());
	} else {		
		if(loadChart == 'C003')
		  cId = loadChart;
		$j("#chartImg").hide();
		JFChart.getLineListings(cId, function(data){
			//alert(data);
			$j("#chartImg").hide();
			$j("#lineListResults").parent().attr("style","height:300px; text-align:center;overflow:none;");
			$j("#lineListResults").show();
			$j("#lineListResults").html(data);

		});
		
	}
	$j("#charts").removeAttr("disabled");
	$j("#charts").attr("enabled","enabled");	
}

function getPopupChart() {
	$j("#popupContact").css({"background" : "White url('ajax-loader.gif') no-repeat center"});
	$j("#chartImg1").show();
	$j("#contactArea table").remove();
	$j("#myCharts1").html($j('#charts :selected').text());
	var cId = $j('#charts').val();
	if(cId != 'C003') {
		//$j("#chartImg1").removeAttr("src");
		$j("#chartImg1").attr("src","/nbs/chartServlet?charts=" + $j('#charts').val() + "&popup=true");
	} else {
		$j("#chartImg1").hide();
		JFChart.getLineListings(cId, function(data){
			//alert(data);
			$j("#popupContact").css({'background' : 'White'});
			$j("#contactArea").show();
			$j("#contactArea").append(data);

		});
		
	}	
	
	
	
}//function

//0 means disabled; 1 means enabled;
var popupStatus = 0;
function loadPopup(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$j("#backgroundPopup").css({"opacity": "0.7"});
		$j("#backgroundPopup").fadeIn("slow");
		$j("#popupContact").fadeIn("slow");
		popupStatus = 1;
	}
}
function disablePopup(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$j("#backgroundPopup").fadeOut("slow");
		$j("#popupContact").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopup(){
	//request data for centering
	var windowWidth = document.body.clientWidth;	
	var windowHeight = document.body.clientHeight;
	//alert(windowWidth + ' ' + windowHeight);
	var popupHeight = $j("#popupContact").height();
	var popupWidth = $j("#popupContact").width();
	//centering
	$j("#popupContact").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	$j("#backgroundPopup").css({"height": windowHeight});
	
}
$j(document).ready(function(){
	$j("#chartitle").html($j('#charts :selected').text());
	$j("#magnify").click(function(){
		centerPopup();
		loadPopup();
		scrollTo(0,0);
	});
				
	$j("#popupContactClose").click(function(){
		disablePopup();
	});
	$j("#backgroundPopup").click(function(){
		disablePopup();
	});
	$j(document).keypress(function(e){
		if(e.keyCode==27 && popupStatus==1){
			disablePopup();
		}
	});

});