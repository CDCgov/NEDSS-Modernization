class ClassicOpenerPage {

  submitNewTab() {
    
    function isUniqueElementName(eltType, eltName, eltId) { 
      return true;
    }
    function submitForm() {
      document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=section&waQuestionUId=" + $j("#pageElementUid").val();
      document.forms[0].submit();
    }
    var opener = {};
    opener.isUniqueElementName = isUniqueElementName;
    opener.submitForm = submitForm;
    cy.visit("https://app.demo.nbspreview.com/nbs/ManagePageElement.do?method=addLoad&eltType=tab");
    cy.get("#tabNameTd").type("NEWTAB");
    cy.window().then((win) => {            
        win.opener = opener;        
        cy.get('input[name="SubmitForm"]').eq(0).click()    
        cy.visit("https://app.demo.nbspreview.com/nbs/ManagePage.do?method=editPageContentsLoad&fromWhere=V");
        cy.wait(500);
        cy.get('body').then(($body) => {            
            if($body.find('img[title="Add New Tab"]').length > 0) {                
                cy.get('img[title="Add New Tab"]').eq(0).click().then(() => {
                  
                  cy.get('body').then(($body2) => {                     
                    function handleEvents(elt, eltType, publishInd, batchUid, dataLoc) { 
                      return true;
                    }

                    function getTabBody(pageElt, isActive) {
                        var tabBodyClass = "ui-tabs-hide";
                        if (isActive) {
                            tabBodyClass = "ui-tabs-panel";
                        }
                        tabBodyClass += " " + "nbsPageElementCssClass";
                        var html = "<div id=\"" + pageElt.pageElementUid + "\" class=\"" + tabBodyClass + "\"><div style=\"width:200px; text-align:left; margin-top:10px; float:left;\"><a class=\"toggleAllChildrenLink\" title=\"Collapse All Sections \" href=\"javascript:void(0)\"> &nbsp;&nbsp; <img src=\"CollapseAll.gif\" alt=\"Collapse All Sections\" title=\"Collapse All Sections\"/> <span class=\"text\">Collapse All Sections </span></a> &nbsp;</div><div style=\"width:200px; float:right;\" class=\"utilButton\"> <a title=\"Add Section\" class=\"addSection\" href=\"javascript:void(0)\"> <img src=\"add.gif\" alt=\"Add Section\" title=\"Add Section\"/> </a> &nbsp; <a title=\"View Tab\" class=\"viewTabLink\" href=\"javascript:void(0)\"> <img src=\"page_white_text.gif\"  alt=\"View Tab\"/> </a> &nbsp; <a title=\"Edit Tab\" class=\"editTabLink\" href=\"javascript:void(0)\"> <img src=\"page_white_edit.gif\"  alt=\"Edit Tab\" title=\"Edit Tab\"/> </a> &nbsp; <a title=\"Delete Tab\" class=\"deleteLink\" href=\"javascript:void(0)\"> <img src=\"cross.gif\"  alt=\"Delete Tab\" title=\"Delete Tab\"/> </a></div> <div class=\"ffClear\"> </div> <ul class=\"sortableSections\"></ul></div>";
                        var elt = Cypress.$(html);
                        handleEvents(elt, 'tabBody');
                        return elt;
                    }
        
                    function getTabHandle(pageElt, isActive) {
                        var tabHandleClass = "";
                        if (isActive) {
                            tabHandleClass = "ui-tabs-selected";
                        }
                        var html = "<li class=\"" + tabHandleClass + "\"><a href=\"" + pageElt.pageElementUid + "\">" + pageElt.elementLabel + "</a></li>";                        
                        var elt = Cypress.$(html);
                        handleEvents(elt, 'tabHandle');
                        return elt;
                    }

                    function getDisplayName(eltType) {
                        switch(eltType.toLowerCase()) {
                            case 'tab':
                                return 'Tab';
                                break;                            
                            case 'section':
                                return 'Section';
                                break;

                            case 'subsection':
                                return 'Subsection';
                                break;

                            case 'question':
                                return 'Question';
                                break;
                                
                            case 'fieldrow':
                                return 'Element';
                                break;

                            default:
                                return eltType;
                                break;
                        }
                    }

                    $body2.find('#parentWindowDiv')[0].remove();                                         
                    var eltsJson = '[{"pageElementUid":-2,"elementLabel":"Test Tabb","elementDescription":null,\n    "elementType":"Tab","questionIdentifier":null,"isStandardized":"F",\n    "isPublished":"F","questionGroupSeqNbr":null,"dataLocation":null,\n    "elementComponentType":"1010","isCoInfection":null,"blockName":null,\n    "dataMartRepeatNumber":null,"dataMartColumnNm":null}]\n';
                    var action = "add"; 

                    var jsonObj = eval('(' + eltsJson + ')');
                    if (action != "view") {                        
                        var clickedEltType = "tab"
                        var eltDisplayName = "";
                        var jsonObj = eval('(' + eltsJson + ')');
                        for (var i in jsonObj) {
                            if (jsonObj[i].elementLabel != null && jsonObj[i].elementLabel != undefined && 
                                   clickedEltType != undefined && clickedEltType != null ) {
                                eltDisplayName = getDisplayName(jsonObj[i].elementType);                   
                                switch (clickedEltType.toLowerCase()) {
                                    case 'tab':
                                        var tabHandle = null, tabBody = null;                                        
                                        tabHandle = getTabHandle(jsonObj[i], true);
                                        tabBody = getTabBody(jsonObj[i], true);                                        
                                        switch (action) {
                                            case 'add':                                                
                                                // insert the items in appropriate place
                                                Cypress.$(".ui-tabs-nav").append(tabHandle)
                                                // $j($j(this.canvas).find("ul.ui-tabs-nav").get(0)).append(tabHandle);
                                                Cypress.$(".div#tabContainer").append(tabBody)
                                                // $j($j(this.canvas).find("div#tabContainer").get(0)).append(tabBody);                                                
                                                // activate this tab.
                                                // $j($j(tabHandle).find("a").get(0)).click();
                                                break;
                                            
                                            case 'edit':
                                                // $j($j("li.ui-tabs-selected").find("a").get(0)).html(jsonObj[i].elementLabel);
                                                break;
                                        }
                                        break;                                                                       
                                } // switch
                            } // if
                        } // for                        
                    }
                  })
                });                
            }
        })


    })    

  }

}

export default new ClassicOpenerPage();