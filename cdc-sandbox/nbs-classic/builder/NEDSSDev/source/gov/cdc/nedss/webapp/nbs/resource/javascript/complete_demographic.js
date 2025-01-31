/**
 * Description:	we need to create hidden input elements from the string generated from the batch entry mechanism
 *					currently being called from next SubmitCompleteDemographic in this js file
 *
 */
function BatchEntryCreateHiddenInputs() {
	var inputNodes = document.getElementsByTagName("input");
	var elementCount = 0;
	var raceCount = 0;

	for (var i=0; i < inputNodes.length; i++) {
		if (inputNodes.item(i).type == "hidden") {
			var hiddenNode = inputNodes.item(i);
			//	! check for a specific pattern in the id of the hidden field, batch entry hidden field containing all the name value pairs
			var patternBatchEntry = new RegExp("nestedElementsHiddenField");

			if (patternBatchEntry.test(hiddenNode.id))	{

				//	split up each entry
				var entries = hiddenNode.value.split("|");
				//	j will be the index for the struts index
				for (var j=0; j < entries.length-1; j++) {
					//	split up each element
					var elements = entries[j].split("&");


					for (var k=0; k < elements.length-1; k++) {
						var pair = elements[k].split("=");
						var name = pair[0];
						var value = pair[1];
						// need to change the index for struts
							//	create the correct index
								var index = "[" + elementCount + "]";
							//	get the corrected index for the hidden variable i'm about to create, use regular expression to replace
							var corrected = name.replace(/\[\w\]/, index);
						var hiddenNode = document.createElement("input");
						hiddenNode.setAttribute("type", "hidden");
						hiddenNode.setAttribute("value", value);
						hiddenNode.setAttribute("name", corrected);
						var formNode = getElementByIdOrByName("nedssForm");
						formNode.appendChild(hiddenNode);
					}
					elementCount++;

				}
			}
			//	need this to create the race codes
			var patternRaceEntry = new RegExp("RaceString");



						if (patternRaceEntry.test(hiddenNode.id))	{
							var entries = hiddenNode.value.split("|");
							for (var j=0; j < entries.length-1; j++) {
								alert(entries[j]);
								var elements = entries[j].split("&");
								for (var k=0; k < elements.length; k++) {
									alert(elements[k]);
									var pair = elements[k].split("=");
									var name = pair[0];
									var value = pair[1];
									//	create the inputs for the race codes in the struts format
									/*var strutsName =
									var hiddenNode = document.createElement("input");
									hiddenNode.setAttribute("type", "hidden");
									hiddenNode.setAttribute("value", value);
									hiddenNode.setAttribute("name", corrected);
									var formNode = getElementByIdOrByName("nedssForm");
									formNode.appendChild(hiddenNode);
									*/
								}
								raceCount++;
							}
						}


		}
	}

}

/**
 * Description:	we need to call the BatchEntryCreateHiddenInputs function before we go to the submitForm function for
 *					validaion, so call this function from the submit button then call the submitForm after Batch
 *
 * param
 */
function SubmitCompleteDemographic() {
		BatchEntryCreateHiddenInputs();
		submitForm();
}

