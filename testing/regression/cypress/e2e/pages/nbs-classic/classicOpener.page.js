class ClassicOpenerPage {
  submitNewTab() {
    function isUniqueElementName(eltType, eltName, eltId) {
      return true;
    }
    function submitForm() {
      var unblock = false;
      document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=section&waQuestionUId=" + Cypress.$("#pageElementUid").val();
      document.forms[0].submit();
    }
    var opener = {};
    opener.isUniqueElementName = isUniqueElementName;
    opener.submitForm = submitForm;
    cy.visit("/nbs/ManagePageElement.do?method=addLoad&eltType=tab");
    cy.get("#tabNameTd").type("NEWTAB");
    cy.window().then((win) => {
      win.opener = opener;
      cy.get('input[name="SubmitForm"]').eq(0).click();
      cy.visit("/nbs/ManagePage.do?method=editPageContentsLoad&fromWhere=V");
      cy.wait(500);
      cy.get('body').then(($body) => {
        if ($body.find('img[title="Add New Tab"]').length > 0) {
          cy.get('img[title="Add New Tab"]').eq(0).click().then(() => {
            cy.get('body').then(($body2) => {
              function handleTabBodyEvents(elt) {
                var aLink = Cypress.$(Cypress.$(elt).find("a.addSection")).get(0);
                Cypress.$(aLink).click(() => fbRef.showAddElementDialog(elt, 'section'));
                var tacLink = Cypress.$(Cypress.$(elt).find("a.toggleAllChildrenLink")).get(0);
                Cypress.$(tacLink).click(() => fbRef.toggleAllChildren(tacLink));
                var vtLink = Cypress.$(Cypress.$(elt).find("a.viewTabLink")).get(0);
                Cypress.$(vtLink).click(() => fbRef.showViewElementDialog(elt, "tab"));
                var elLink = Cypress.$(Cypress.$(elt).find("a.editTabLink")).get(0);
                Cypress.$(elLink).click(() => fbRef.showEditElementDialog(elt, "tab"));
                var dLink = Cypress.$(Cypress.$(elt).find("a.deleteLink")).get(0);
                Cypress.$(dLink).click(() => fbRef.deleteElement(elt, "tab"));
                Cypress.$(elt).mouseover(() => Cypress.$(elt).addClass("pageElementHover"))
                  .mouseout(() => Cypress.$(elt).removeClass("pageElementHover"));
              }

              function handleTabHandleEvents(elt) {
                var aLink = Cypress.$(Cypress.$(elt).find("a")).get(0);
                Cypress.$(aLink).click((ev) => {
                  var href = Cypress.$(aLink).attr("href");
                  var id = href.substring(href.lastIndexOf("/") + 1);
                  var jQueryBodyId = "div#" + id;
                  ev.preventDefault();
                  Cypress.$(Cypress.$(Cypress.$(aLink).parents("ul").get(0)).find("li.ui-tabs-selected").get(0)).removeClass("ui-tabs-selected");
                  Cypress.$(Cypress.$(aLink).parents("li").get(0)).addClass("ui-tabs-selected");
                  Cypress.$(Cypress.$("div#tabContainer").find("div.ui-tabs-panel").get(0)).removeClass("ui-tabs-panel").addClass("ui-tabs-hide");
                  Cypress.$(Cypress.$("div#tabContainer").find(jQueryBodyId).get(0)).addClass("ui-tabs-panel").removeClass("ui-tabs-hide");
                });
              }

              function handleEvents(elt, eltType) {
                if (eltType === 'tabHandle') handleTabHandleEvents(elt);
                if (eltType === 'tabBody') handleTabBodyEvents(elt);
              }

              function getTabBody(pageElt, isActive) {
                var tabBodyClass = isActive ? "ui-tabs-panel nbsPageElementCssClass" : "ui-tabs-hide nbsPageElementCssClass";
                var html = `<div id="${pageElt.pageElementUid}" class="${tabBodyClass}">
                              <div style="width:200px; text-align:left; margin-top:10px; float:left;">
                                <a class="toggleAllChildrenLink" title="Collapse All Sections " href="javascript:void(0)">
                                  &nbsp;&nbsp; <img src="CollapseAll.gif" alt="Collapse All Sections"/>
                                  <span class="text">Collapse All Sections</span>
                                </a>&nbsp;
                              </div>
                              <div style="width:200px; float:right;" class="utilButton">
                                <a title="Add Section" class="addSection" href="javascript:void(0)">
                                  <img src="add.gif" alt="Add Section"/>
                                </a>&nbsp;
                                <a title="View Tab" class="viewTabLink" href="javascript:void(0)">
                                  <img src="page_white_text.gif" alt="View Tab"/>
                                </a>&nbsp;
                                <a title="Edit Tab" class="editTabLink" href="javascript:void(0)">
                                  <img src="page_white_edit.gif" alt="Edit Tab"/>
                                </a>&nbsp;
                                <a title="Delete Tab" class="deleteLink" href="javascript:void(0)">
                                  <img src="cross.gif" alt="Delete Tab"/>
                                </a>
                              </div>
                              <div class="ffClear"></div>
                              <ul class="sortableSections"></ul>
                            </div>`;
                var elt = Cypress.$(html);
                handleEvents(elt, 'tabBody');
                return elt;
              }

              function getTabHandle(pageElt, isActive) {
                var tabHandleClass = isActive ? "ui-tabs-selected" : "";
                var html = `<li class="${tabHandleClass}">
                              <a href="${pageElt.pageElementUid}">${pageElt.elementLabel}</a>
                            </li>`;
                var elt = Cypress.$(html);
                handleEvents(elt, 'tabHandle');
                return elt;
              }

              var jsonObj = [{
                "pageElementUid": -2,
                "elementLabel": "Test Tabb",
                "elementType": "Tab",
                "isStandardized": "F",
                "isPublished": "F",
                "elementComponentType": "1010"
              }];

              jsonObj.forEach((elt) => {
                var tabHandle = getTabHandle(elt, true);
                var tabBody = getTabBody(elt, true);
                Cypress.$(".ui-tabs-nav").append(tabHandle);
                Cypress.$(".div#tabContainer").append(tabBody);
                tabHandle.find("a").get(0).click();
              });
            });
          });
        }
      });
    });
  }

  submitNewSection() {
    function isUniqueElementName(eltType, eltName, eltId) {
      return true;
    }

    function submitForm() {
      var unblock = false;
      document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=section&waQuestionUId=" + Cypress.$("#pageElementUid").val();
      document.forms[0].submit();
    }
    var opener = {};
    opener.isUniqueElementName = isUniqueElementName;
    opener.submitForm = submitForm;
    cy.visit("/nbs/ManagePageElement.do?method=addLoad&eltType=section");
    cy.window().then((win) => {
      win.opener = opener;
      cy.get("#tabNameTd").type("New Section");
      cy.get('input[type="button"][name="SubmitForm"]').eq(0).click()
    });
  }

  submitNewSubSection() {
    function isUniqueElementName(eltType, eltName, eltId) {
      return true;
    }

    function submitForm() {
      var unblock = false;
      document.forms[0].action = "/nbs/ManagePageElement.do?method=editSubmit&eltType=section&waQuestionUId=" + Cypress.$("#pageElementUid").val();
      document.forms[0].submit();
    }
    var opener = {};
    opener.isUniqueElementName = isUniqueElementName;
    opener.submitForm = submitForm;
    cy.visit("/nbs/ManagePageElement.do?method=addLoad&eltType=subSection");
    cy.window().then((win) => {
      win.opener = opener;
      cy.get("#tabNameTd").type("New Sub Section");
      cy.get('input[type="button"][name="SubmitForm"]').eq(0).click()
    });
  }

}

export default new ClassicOpenerPage();
