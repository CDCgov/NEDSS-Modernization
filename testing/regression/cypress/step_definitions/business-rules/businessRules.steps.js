import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {businessRulesPage} from "@pages/business-rules/businessRules.page";

Then("User navigates to Business Rules and views the Business Rules", () => {
    businessRulesPage.navigateToBusinessRulesPage();
});

Then("User navigates to Business Rules and views the Business Rules", () => {
    businessRulesPage.navigateToBusinessRulesPage();
});

Then("User navigates to Business Rules and views the Business Rules", () => {
    businessRulesPage.navigateToBusinessRulesPage();
});

Then("Application will display the Business Rules landing page", () => {
    businessRulesPage.viewsBusinessRulesPage();
});

Then("Logic column is populated", () => {
    businessRulesPage.checkLogicColumnPopulated();
});

Then("Logic will display possible values as {string}", (logic) => {
    businessRulesPage.displayLogics(logic);
});
