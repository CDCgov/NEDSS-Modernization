		var INIT					= "reqMethod=INIT";
		var SORTBYDATE				= "reqMethod=SORTBYDATE";
		var SORTBYTEXT				= "reqMethod=SORTBYTEXT";
		var FILTERBYFUTUREDAYS		= "reqMethod=FILTERBYFUTUREDAYS";
		var FILTERBYTEXT			= "reqMethod=FILTERBYTEXT";
		var NEXTPAGE				= "reqMethod=NEXTPAGE";
		var PREVPAGE				= "reqMethod=PREVPAGE";
		var CLEARFILTER				= "reqMethod=CLEARFILTER";
		var CLEARALLFILTERSANDSORTS = "reqMethod=CLEARALLFILTERSANDSORTS";
		
		var STARTDTOFFSET	= "startDtOffset=";
		var ENDDTOFFSET		= "endDtOffset=";
		var FILTERTEXT		= "filterText=";		
		var GETPAGE			= "getPage=";
		var ROWSPERPAGE		= "rowsPerPage=";

		var REQCOL = "reqCol=";
		var SORTASCFLAG = new Array();
		
		var getPage = 1;
		var totalRows;;
		var filteredRows;


		function displayMsg() {
			var msgv = getElementByIdOrByName("msg");
			var topRow = ((getPage - 1) * rowsPerPage) +1;
			var bottomRow = (topRow +rowsPerPage) -1;
			if (filteredRows == 0) {
				topRow = 0;
			}

			if (bottomRow > filteredRows) {
				bottomRow = filteredRows;
			}
			if (totalRows == filteredRows) {
				msgv.innerHTML = "Displaying rows "+topRow+" to "+bottomRow+" of "+filteredRows;
			} else {
				msgv.innerHTML = "Displaying rows "+topRow+" to "+bottomRow+" of "+filteredRows+" (filtered from "+totalRows+")";
			}
		}

		function clearChildren(elem) {
			if(alertFunctions) alert("clearChildren");
			if ( elem.hasChildNodes() ) {
			    while (elem.childNodes.length >= 1 ) {
			        elem.removeChild(elem.firstChild );
			    }
			}
		}

		function formatDate(dt) {
			var d = new Date(dt);
			//alert(d);
			var cd = pad(d.getDate(), 2);
			var cm = pad(d.getMonth() +1, 2);
			var cy = d.getFullYear();
			return cm+"/"+cd+"/"+cy;
		}

		function pad(n, width, z) {
			z = z || '0';
			n = n + '';
			return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
		}
		function sortQCol(col, stype) {
			if(alertFunctions) alert("sortCol");
			var asc = SORTASCFLAG[col];
	    	if (typeof asc == "undefined") {
				asc = false;
			}
			if (asc) {
				asc = false;
			} else {
				asc = true;
			}
			for (i=0; i < SORTASCFLAG.length; i++) {
				SORTASCFLAG[i] = null;
			}
			SORTASCFLAG[col] = asc;

			stype+="&"+ASC+asc;
			stype+="&"+REQCOL+col;
			getData(stype);
		}
		
		function nextPage() {
			if(alertFunctions) alert("nextPage");
			++getPage;
			setPagingLinks();
			var parm=NEXTPAGE;
			getData(parm);
		}
		
		function prevPage() {
			if(alertFunctions) alert("nextPage");
			--getPage;
			if (getPage < 1) {
				getPage = 1;
			}
			setPagingLinks();
			var parm=PREVPAGE;
			getData(parm);
		}
		
		function goToPage(n) {
			if(alertFunctions) alert("goToPage");
			getPage = n;
			setPagingLinks();
			var parm=PREVPAGE;
			getData(parm);
		}
		
		function setPagingLinks() {
			var prevA = getElementByIdOrByName("prevPageA");
			var nextA = getElementByIdOrByName("nextPageA");
			var pgCh = getElementByIdOrByName("pageChoose");

			//debug
			//var pt = getElementByIdOrByName("pageTab");
			//end debug
			
			if (getPage > 1) {
				prevA.innerHTML = "<button type='button' id='butPrevPage' onClick='prevPage()' style='width:7em'>Previous</button>";
			} else {
				prevA.innerHTML = " ";
			}
			
			var curRows = rowsPerPage * getPage;
			if (curRows < filteredRows) {
				nextA.innerHTML = "<button type='button' id='butNextPage' onClick='nextPage()' style='width:7em'>Next</button>";
			} else {
				nextA.innerHTML = " ";
			}
			
			pgChHtml = "Go To Page ";
			pgChHtml += "<select id='pgSel' onChange='goToPage(this.value)'>";
			var numPgs = Math.ceil(filteredRows/rowsPerPage);
			for (i=1; i <= numPgs; i++) {
				pgChHtml += "<option value = '"+i+"'";
				if (i == getPage) {
					pgChHtml += " selected";
				}
				pgChHtml += ">"+i+"</option>";
			}
			pgChHtml += "</select>";
			pgCh.innerHTML = pgChHtml;
			
			//debug
			//alert(pt.innerHTML);
			//end debug
		}
		
	    function searchKeyPress(fld, e, subButton, clrButton) {
	        // look for window.event in case event isn't passed in
	        if (typeof e == 'undefined' && window.event) { 
	        	e = window.event; 
	        }
	        //alert("searchKeyPress: "+e.keyCode);
	        if (e.keyCode == 13) { // enter
	            subButton.click();
	        } else {
		        if (e.keyCode == 27) { // esc
		            fld.blur();
		            fld.value = "";
		            clrButton.click();
		            fld.focus();
		        }
		    }
	    }