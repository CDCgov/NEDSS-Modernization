/**
* This JS file contains all the methods that are used to manipulate the display of sections
* and subsections in a page (sections and subsections are HTML container classes that are
* defined in the common.css file present under the '/nbs/resource/style/recent' directory.

* The methods here use JQuery Javascript library's DOM manipulation APIs to 
* access HTML elements. '$j' is the short notation for accessing the JQuery library
* APIs. '$j' is declared in the '/nbs/jsp/resources.jsp' file.  
*/

/* Images that used to denote the collapsed/expanded 
    state of the information blocks */
var minusIcon = "<img src=\"minus_sign.gif\" alt=\"Collapse\" \/>"
var plusIcon = "<img src=\"plus_sign.gif\" alt=\"Expand\" \/>"

/**
* Expand the section 
*/
function gotoSection(sectionId)
{
    var sectionId = "#" + sectionId;
    var sectionHead = $j(sectionId).find("table.sectHeader").get(0);
    var sectionBody = $j(sectionId).find("div.sectBody").get(0);

    // expand/open the section if it is currently closed 
    if ($j(sectionBody).css("display") == "none") {
        $j(sectionBody).show();
        $j(sectionHead).find("a.toggleIconHref").html(minusIcon);
    }

    // update the section toggler handle to reflect the state of the 
    // sections contained in the view container.
    updateSectionsTogglerHandle("#" + $j(
                                        $j(
                                            sectionId                                 
                                        ).parent().get(0)
                                      ).attr("id"));
                                      
    // jump to the section that was currently opened
    window.location = sectionId;
}

/** 
* Control the hide/show of a section body.
* @param sectionId - unique Id of a section
*/
function toggleSectionDisplay(sectionId)
{
    var sectionId = "#" + sectionId;
    var sectionHead = $j(sectionId).find("table.sectHeader").get(0);
    var sectionBody = $j(sectionId).find("div.sectBody").get(0);

    if ($j(sectionBody).css("display") != "none") {
        $j(sectionBody).hide();
        $j(sectionHead).find("a.toggleIconHref").html(plusIcon);
    } 
    else {
        $j(sectionBody).show();
        $j(sectionHead).find("a.toggleIconHref").html(minusIcon);
    }
    
    updateSectionsTogglerHandle("#" + $j(
                                        $j(
                                            sectionId                                 
                                        ).parent().get(0)
                                      ).attr("id"));
    return false;
}
    
/** 
* Control the hide/show of body portion of all sections contained
*   within an element
* @param viewId - unique Id of element containing 1 or more sections 
*/  
function toggleAllSectionsDisplay(viewId)
{
    // get the parent that contains the sectionsToggler 
    // and all the sections to toggle
    var parentElt;
    if (viewId != null) {
        viewId = "#" + viewId;
        parentElt = $j(viewId);    
    }
    else {
        parentElt = $j("body");
    }
    
    // get the reference to the sections toggler
    var togglerTable = $j(parentElt).find("table.sectionsToggler").get(0);
    var togglerHandle = $j(togglerTable).find("a.toggleHref").get(0);
    
    // get all the sections inside the parent
    var sections = $(parentElt).find("div.sect");
    
    // hide/show all sections depending on the toggler handle value
    if (jQuery.trim($j(togglerHandle).html()) == 'Collapse Sections') {
        $j(togglerHandle).html('Expand Sections');
        for (i = 0; i < sections.length; i++) {
            var sbody = $j(sections[i]).find("div.sectBody").get(0);
            $j(sbody).hide();
            
            var shead = $j(sections[i]).find("table.sectHeader").get(0);
            $j(shead).find("a.toggleIconHref").html(plusIcon);
        }
    }
    else {
        $j(togglerHandle).html('Collapse Sections');
        for (i = 0; i < sections.length; i++) {
            var sbody = $j(sections[i]).find("div.sectBody").get(0);
            $j(sbody).show();
            
            var shead = $j(sections[i]).find("table.sectHeader").get(0);
            $j(shead).find("a.toggleIconHref").html(minusIcon);
        }
    }
}

/** 
* Control the hide/show of a single subsection. The handler to hide/show all
*       subsections at once is updated at the end of this operation.
* @param subSectionId - unique Id of a subsection 
*/
function toggleSubSectionDisplay(subSectionId)
{
    var subSectionId = "#" + subSectionId;
    var subSectionHead = $j(subSectionId).find("thead").get(0);
    var subSectionBody = $j(subSectionId).find("tbody").get(0);
    
    if (getSubSectionDisplayState(subSectionId) != "none") {
        $j(subSectionBody).hide();
        $j(subSectionHead).find("a.toggleIconHref").html(plusIcon);
    }
    else {
        $j(subSectionBody).show();
        $j(subSectionHead).find("a.toggleIconHref").html(minusIcon);
    }
    
    updateSubSectionsTogglerHandle("#" + $j(
                                            $j(
                                                $j(subSectionId).parent().get(0)
                                              ).parent().get(0)
                                           ).attr("id"));
    
    return false;
}

/** 
* Control the hide/show of body portion of all subsections contained
*   within a section
* @param sectionId - unique Id of element containing 1 or more subsections 
*/  
function toggleAllSubSectionsDisplay(sectionId)
{
    // get the reference for the section and subsections toggler
    var sectionId = "#" + sectionId;
    var subSectionsTogglerTable = $j(sectionId).find("table.subSectionsToggler").get(0);
    var subSectionsTogglerHandle = $j(subSectionsTogglerTable).find("a.toggleHref").get(0);
    
    // get all subsections to be toggled.
    var subSections = $j(sectionId).find("table.subSect");
    
    // hide/show all subsections depending on the toggler handle value
    if (jQuery.trim($j(subSectionsTogglerHandle).html()) == 'Collapse Subsections') {
        $j(subSectionsTogglerHandle).html('Expand Subsections');
        for (i = 0; i < subSections.length; i++) {
            var tbody = $j(subSections[i]).find("tbody").get(0);
            $j(tbody).hide();
            
            var thead = $j(subSections[i]).find("thead").get(0);
            $j(thead).find("a.toggleIconHref").html(plusIcon);
        }
    }
    else {
        $j(subSectionsTogglerHandle).html('Collapse Subsections');
        for (i = 0; i < subSections.length; i++) {
            var tbody = $j(subSections[i]).find("tbody").get(0);
            $j(tbody).show();
            
            var thead = $j(subSections[i]).find("thead").get(0);
            $j(thead).find("a.toggleIconHref").html(minusIcon);    
        }
    }
    
    return false;
}

function updateSubSectionsTogglerHandle(sectionId) {
    // get all subsections to be toggled.
    var updateRequired = true;
    var subSections = $j(sectionId).find("table.subSect");
    var sectState = "";
    for (var i = 0; i < subSections.length; i++) {
        if (i == 0) {
            sectState = getSubSectionDisplayState("#" + $j(subSections.get(i)).attr("id"));
        }
        
        if (sectState != getSubSectionDisplayState("#" + $j(subSections.get(i)).attr("id"))) {
            updateRequired = false;
            break;
        }
    }
    
    if (updateRequired == true) {
        var subSectionsTogglerTable = $j(sectionId).find("table.subSectionsToggler").get(0);
        var subSectionsTogglerHandle = $j(subSectionsTogglerTable).find("a.toggleHref").get(0);
        
        if (sectState != "none") {
            $j(subSectionsTogglerHandle).html('Collapse Subsections');    
        }
        else {
            $j(subSectionsTogglerHandle).html('Expand Subsections');    
        }
    }
}

function updateSectionsTogglerHandle(viewId) {
    // get the parent that contains the sectionsToggler 
    // and all the sections to toggle
    var parentElt;
    if (viewId != null) {
        parentElt = $j(viewId);    
    }
    else {
        parentElt = $j("body");
    }
    
    // get all sections to be toggled.
    var updateRequired = true;
    var sections = $j(parentElt).find("div.sect");
    var sectState = "";
    for (var i = 0; i < sections.length; i++) {
        if (i == 0) {
            sectState = getSectionDisplayState("#" + $j(sections.get(i)).attr("id"));
        }
        
        if (sectState != getSectionDisplayState("#" + $j(sections.get(i)).attr("id"))) {
            updateRequired = false;
            break;
        }
    }

    if (updateRequired == true) {
        var togglerTable = $j(parentElt).find("table.sectionsToggler").get(0);
        var togglerHandle = $j(togglerTable).find("a.toggleHref").get(0);
        
        if (sectState != "none") {
            $j(togglerHandle).html('Collapse Sections');    
        }
        else {
            $j(togglerHandle).html('Expand Sections');    
        }
    }
}

function getSubSectionDisplayState(subSectionId)
{
    var disp = $j($j(subSectionId).find("tbody").get(0)).css("display");
    return (disp);
}

/**
* Return the display state of a section identied uniquely by sectionId.
* Possible values are "hide/block"     
*/
function getSectionDisplayState(sectionId)
{
    var disp = $j($j(sectionId).find("div.sectBody").get(0)).css("display"); 
    return(disp);
}

function toggleDTRows(dtTableId)
{
    var tBody = $j("#" + dtTableId).find("tbody").get(0);
    var bodyRows = $j(tBody).find("tr");
    var doExpand = true;
    
    if (bodyRows.length > 1) {
        // determine the display status of 2nd row in the table body
        if ($j(bodyRows[1]).css("display") != "none") {
            doExpand = false;
        }
        else {
            doExpand = true;
        }
    }
    
    if (bodyRows.length > 1) 
    {
        for (var i = 0; i < bodyRows.length; i++)
        {
            if (i != 0) {
                if (doExpand == true) {
                    $j(bodyRows[i]).show();
                }
                else {
                    $j(bodyRows[i]).hide();
                }    
            }
            else {
                var togglerDiv = $j(bodyRows[i]).find(".rowsToggler").get(0);
                var aElt = $j(togglerDiv).find("a").get(0);
                if (doExpand == true) {
                    $j(aElt).html(minusIcon);
                }
                else {
                    $j(aElt).html(plusIcon);
                }
            }
        }
    } 
    
    return false;
}