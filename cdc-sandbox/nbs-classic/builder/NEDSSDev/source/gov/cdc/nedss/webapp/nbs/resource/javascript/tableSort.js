/**
 * Description:	sorts table based on column
 *
 * param 
 */
function sortTableOnColumn(oLink){
	//alert(oLink.sortOrder);
	// check if this is ascending or decending
	if(oLink.sortOrder==null){
		var sortAttribute = document.createAttribute("sortOrder");
		sortAttribute.nodeValue = "ascending"
		oLink.setAttributeNode(sortAttribute);
	} else if(oLink.sortOrder=="ascending")
		oLink.setAttribute("sortOrder", "descending");
	else
		oLink.setAttribute("sortOrder", "ascending");
		
		
	var sortArray = new Array();
	// need to find the column we are sorting on
	var tdNode = oLink.parentNode;
	while(tdNode.nodeName!="TD")
	{
			tdNode = tdNode.parentNode;
	}
	var currentColumn = tdNode.columnNumber
	//alert(tdNode.columnNumber);
	
	
	
	var tableNode = oLink.parentNode;
	while(tableNode.nodeName!="TABLE")
	{
			tableNode = tableNode.parentNode;
	}
	
	
	
	//alert(tableNode.innerHTML);
	var tBodyNodeArr = tableNode.getElementsByTagName("TBODY");
	var tBodyNode = tBodyNodeArr[0];
	//alert(tBodyNodeArr[0].innerHTML);
	
	
	var trNodeArr = tBodyNode.getElementsByTagName("tr");
	for (var i=0; i < trNodeArr.length; i++) {
			
			var tdNodeArr = trNodeArr[i].getElementsByTagName("td");
			for (var j=0; j < tdNodeArr.length; j++) {
				
				if(tdNodeArr[j].columnNumber==currentColumn){
					//alert(tdNodeArr[j].firstChild.nodeValue);		
					//initialize array that will be sorted
					sortArray[i] = new Sortable(tdNodeArr[j].firstChild.nodeValue,trNodeArr[i]);
				}
				//need to check if the node is a hyperlink
				//alert(tdNodeArr[j].firstChild.nodeValue);
			}
	}
	
	
	// sort the array
	
	sortArray.sort(function(a,b){
					if(a.comparable < b.comparable)
					{
						return -1;
					}
					if(a.comparable > b.comparable)
					{
						return 1;
					}
					return 0;	
		
					});
	
	
	// clear the table
	var removeTR = tBodyNode.firstChild;
		while(removeTR != null){
		  var temp = removeTR.nextSibling;
		  tBodyNode.removeChild(removeTR);
		  removeTR= temp;
	}
	//	check the sort order and reverse the array if it is descending
	if(oLink.sortOrder=="descending")
		sortArray.reverse();
	
	//	add the sorted rows back	
	for (var i=0; i < sortArray.length; i++) {			
		tBodyNode.appendChild(sortArray[i].rowNode);		
	}
	
	
	
}

function Sortable(t, r){

	//use function from nedss.js to determine if comparable is a date type
	if(isDate(t))
		t = DateToSortableString(new Date(t));
	
	this.comparable = t;
	this.rowNode = r;
}


function quickSort(a, lo0, hi0) {
    var lo = lo0;
    var hi = hi0;
    
    if (lo >= hi) {
      return;
    }
    var mid = a[ (lo + hi) / 2];
    while (lo < hi) {
      while (lo < hi && a[lo] < mid) {
        lo++;
      }
      while (lo < hi && a[hi] > mid) {
        hi--;
      }
      if (lo < hi) {
        var T = a[lo];
        a[lo] = a[hi];
        a[hi] = T;
      }
      
    }
    if (hi < lo) {
      var T = hi;
      hi = lo;
      lo = T;
    }
    quickSort(a, lo0, lo);
    quickSort(a, lo == lo0 ? lo + 1 : lo, hi0);
  }
