function listboxRight(o)
{
	var a = new Array(5);

	a = o.name.split("|");

	var s = a[0] + "|listbox|left";

	var listboxLeft = document.all[s];


	for(i=0; i<listboxLeft.length;i++) {
		if (listboxLeft.options[i].selected) {

			var listboxRight = document.all[a[0] + "|listbox|right"];
			var option = new Option(listboxLeft.options[i].text, listboxLeft.options[i].value,false,true);
			listboxRight.add(option);	
		}
	}
}
function listboxLeft(o)
{
	var a = new Array(5);

	a = o.name.split("|");

	var s = a[0] + "|listbox|right";

	var listboxRight = document.all[s];

	for(i=0; i<listboxRight.length;i++) {
		if (listboxRight.options[i].selected) {

			var listboxLeft = document.all[a[0] + "|listbox|left"];
			var option = new Option(listboxRight.options[i].text, listboxRight.options[i].value,false,true);
			listboxLeft.add(option);	
		}
	}
}
function listboxSelectAll(o)
{
	var a = new Array(5);

	a = o.name.split("|");

	var s = a[0] + "|listbox|left";

	var listboxLeft = document.all[s];
	for(i=0; i<listboxLeft.length;i++) {
		listboxLeft.options[i].selected=true;
	}				
}
function listboxRemoveAll(o)
{
	var a = new Array(5);

	a = o.name.split("|");

	var s = a[0] + "|listbox|left";

	var listboxLeft = document.all[s];
	listboxLeft.options.length=0;
}