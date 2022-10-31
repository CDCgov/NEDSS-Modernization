/// <reference types="cypress" />
import { Given, When, Then } from 'cypress-cucumber-preprocessor/steps';
import { SystemType } from '../../models/enums/SystemType';
import WorkflowAlgorithm from '../../models/WorkflowAlgorithm';
import AddAlgorithmPage from '../../pages/system-management/decision-support-management/AddAlgorithmPage';
import ManageAlgorithmsPage from '../../pages/system-management/decision-support-management/ManageAlgorithmsPage';
import WorkflowAlgorithmMother from '../../utils/WorkflowAlgorithmMother';

enum EditedSystemType {
    EDITED_CASE_REPORT = 'edited Case Report',
    EDITED_LABORATORY_REPORT = 'edited Laboratory Report'
}
Given(/I create a (.*) algorithm/, (systemType: SystemType) => {
    const algorithm = getAlgorithmBySystemType(systemType);
    const manageAlgorithmsPage = new ManageAlgorithmsPage();
    manageAlgorithmsPage.navigateTo();
    manageAlgorithmsPage.getDisplayedAlgorithms().then((displayedAlgorithms) => {
        const existingAlgorithm = displayedAlgorithms.find((a) => a.algorithmName === algorithm.algorithmName);
        if (existingAlgorithm) {
            // algorithm with name exists, lets update it to match default values
            manageAlgorithmsPage.clickEditAlgorithm(algorithm.algorithmName).then((editAlgorithmPage) => {
                editAlgorithmPage.updateAlgorithm(algorithm);
            });
        } else {
            // algorithm doesn't exists, lets create it
            const addAlgorithmPage = new AddAlgorithmPage();
            addAlgorithmPage.navigateTo();
            addAlgorithmPage.createAlgorithm(algorithm);
        }
    });
});

When(/I edit the (.*) algorithm/, (systemType: SystemType) => {
    const editedType =
        systemType === SystemType.CASE_REPORT
            ? EditedSystemType.EDITED_CASE_REPORT
            : EditedSystemType.EDITED_LABORATORY_REPORT;
    const algorithm = getAlgorithmBySystemType(editedType);
    const manageAlgorithmsPage = new ManageAlgorithmsPage();
    manageAlgorithmsPage.navigateTo();
    manageAlgorithmsPage.clickEditAlgorithm(algorithm.algorithmName).then((editAlgorithmPage) => {
        editAlgorithmPage.updateAlgorithm(algorithm);
    });
});

Then(/I can view the (.*) algorithm summary/, (systemType: SystemType | EditedSystemType) => {
    const algorithm = getAlgorithmBySystemType(systemType);
    const manageAlgorithmsPage = new ManageAlgorithmsPage();
    manageAlgorithmsPage.navigateTo();
    manageAlgorithmsPage.clickViewAlgorithm(algorithm.algorithmName).then((viewAlgorithmPage) => {
        const summaryTable = viewAlgorithmPage.getAlgorithmSummaryTable();
        summaryTable.should('contain', algorithm.eventType);
        summaryTable.should('contain', algorithm.action);
        summaryTable.should('contain', algorithm.algorithmName);
    });
});

Then(/I can view the (.*) algorithm actions/, (systemType: SystemType) => {
    const algorithm = getAlgorithmBySystemType(systemType);
    const manageAlgorithmsPage = new ManageAlgorithmsPage();
    manageAlgorithmsPage.navigateTo();
    manageAlgorithmsPage.clickViewAlgorithm(algorithm.algorithmName).then((viewAlgorithmPage) => {
        // data present on both lab and case report algorithms
        viewAlgorithmPage.setActiveTab('Action');
        const actionDetails = viewAlgorithmPage.getActionDetailsTable();
        actionDetails.should('contain', algorithm.action);
        algorithm.conditions.forEach((c) => actionDetails.should('contain', c));
        actionDetails.should('contain', algorithm.investigationType);
        actionDetails.should('contain', algorithm.onFailureToCreate);

        // investigation default values
        if (algorithm.investigationDefaultValues) {
            const investigationDefaultValues = viewAlgorithmPage.getInvestigationDefaultsTable();
            algorithm.investigationDefaultValues.forEach((idv) => {
                // The Id of the question is not displayed in the table, hence the substring
                investigationDefaultValues.should('contain', idv.question.substring(0, idv.question.indexOf(':')));
                investigationDefaultValues.should('contain', idv.value);
                investigationDefaultValues.should('contain', idv.behvaior);
            });
        }
        // Case report specific data
        if (algorithm.eventType === SystemType.CASE_REPORT) {
            // advanced criteria
            const advancedCritera = viewAlgorithmPage.getAdvancedCriteriaTable();
            algorithm.advancedCritera.forEach((ac) => {
                const question = ac.question.substring(0, ac.question.indexOf(':'));
                advancedCritera.should('contain', question);
                advancedCritera.should('contain', ac.logic);
                advancedCritera.should('contain', ac.value);
                const detailsSection = viewAlgorithmPage.clickViewAdvancedCriteria(question);
                detailsSection.should('contain', question);
                detailsSection.should('contain', ac.logic);
                detailsSection.should('contain', ac.value);
            });
        } // Lab report specific data
        else if (algorithm.eventType === SystemType.LABORATORY_REPORT) {
            // lab criteria logic
            viewAlgorithmPage.getLabCriteriaLogic().should('contain', algorithm.logicMode);
            // lab criteria section
            const labCriteria = viewAlgorithmPage.getLabCriteriaTable();
            algorithm.labCriteria?.forEach((l) => {
                labCriteria.should('contain', l.resultedTest.description);
                labCriteria.should('contain', l.resultedTest.code);
                if (l.codedResult) {
                    labCriteria.should('contain', l.codedResult.code);
                    labCriteria.should('contain', l.codedResult.description);
                }
            });
            // investigation criteria
            viewAlgorithmPage
                .getInvestigationCriteria()
                .should('contain', algorithm.advancedCritera.length > 0 ? 'Yes' : 'No');

            // advanced investigation criteria
            const advancedInvestigationCritera = viewAlgorithmPage.getAdvancedInvestigationCriteriaTable();
            algorithm.advancedCritera.forEach((ac) => {
                let question: string;
                if (ac.question.indexOf(':') > -1) {
                    question = ac.question.substring(0, ac.question.indexOf(':'));
                } else {
                    question = ac.question.substring(0, ac.question.indexOf('('));
                }
                advancedInvestigationCritera.should('contain', question);
                advancedInvestigationCritera.should('contain', ac.logic);
                advancedInvestigationCritera.should('contain', ac.value);
                const detailsSection = viewAlgorithmPage.clickViewAdvancedInvestigationCriteria(question);
                detailsSection.should('contain', question);
                detailsSection.should('contain', ac.logic);
                detailsSection.should('contain', ac.value);
            });

            // time frame logic
            if (algorithm.timeFrame) {
                const timeFrameLogic = viewAlgorithmPage.getTimeFrameLogicTable();
                timeFrameLogic.should('contain', algorithm.timeFrame.operator);
                timeFrameLogic.should('contain', algorithm.timeFrame.value);
            }
        }
    });
});

function getAlgorithmBySystemType(systemType: SystemType | EditedSystemType): WorkflowAlgorithm {
    switch (systemType) {
        case SystemType.CASE_REPORT:
            return WorkflowAlgorithmMother.caseReportAlgorithm();
        case SystemType.LABORATORY_REPORT:
            return WorkflowAlgorithmMother.labReportAlgorithm();
        case EditedSystemType.EDITED_CASE_REPORT:
            return WorkflowAlgorithmMother.caseReportAlgorithm({
                administrativeComments: 'edited comments for case report algorithm ()\\&<>\'"',
                investigationType: 'Varicella',
                conditions: ['Varicella'],
                onFailureToCreate: 'Retain Event Record',
                advancedCritera: [
                    {
                        question: '10. Diagnosis Date: (INV136)',
                        logic: 'Greater Than',
                        value: '01012020',
                        valueInputType: 'date'
                    }
                ],
                investigationDefaultValues: [
                    {
                        question: '53. General Comments: (INV167)',
                        value: 'Edited comment ()\\&<>\'"',
                        valueInputType: 'textarea',
                        behvaior: 'Overwrite Existing Values'
                    }
                ]
            });
        case EditedSystemType.EDITED_LABORATORY_REPORT:
            return WorkflowAlgorithmMother.labReportAlgorithm({
                administrativeComments: 'edited comments for lab report algorithm ()\\&<>\'"',
                investigationType: 'Tuberculosis',
                conditions: ['Tuberculosis'],
                advancedCritera: [
                    {
                        question: '10. Diagnosis Date: (INV136)',
                        logic: 'Greater Than',
                        value: '01012020',
                        valueInputType: 'date'
                    }
                ],
                investigationDefaultValues: [
                    {
                        question: '53. General Comments: (INV167)',
                        value: 'Edited comment ()\\&<>\'"',
                        valueInputType: 'textarea',
                        behvaior: 'Overwrite Existing Values'
                    }
                ]
            });
        default:
            throw new Error('Unsupported system type');
    }
}
