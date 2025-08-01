/**
 * This object contains the permissions structure used by the application.
 */
export const permissions = {
    global: {
        hivQuestion: 'HIVQUESTIONS-GLOBAL'
    },
    investigation: {
        add: 'ADD-INVESTIGATION',
        delete: 'DELETE-INVESTIGATION',
        edit: 'EDIT-INVESTIGATION',
        compare: 'MERGEINVESTIGATION-INVESTIGATION',
        view: 'VIEW-INVESTIGATION'
    },
    labReport: {
        add: 'ADD-OBSERVATIONLABREPORT',
        delete: 'DELETE-OBSERVATIONLABREPORT',
        edit: 'EDIT-OBSERVATIONLABREPORT',
        view: 'VIEW-OBSERVATIONLABREPORT'
    },
    morbidityReport: {
        add: 'ADD-OBSERVATIONMORBIDITYREPORT',
        delete: 'DELETE-OBSERVATIONMORBIDITYREPORT',
        edit: 'EDIT-OBSERVATIONMORBIDITYREPORT',
        view: 'VIEW-OBSERVATIONMORBIDITYREPORT'
    },
    vaccination: {
        add: 'ADD-INTERVENTIONVACCINERECORD',
        view: 'VIEW-INTERVENTIONVACCINERECORD'
    },
    patient: {
        add: 'ADD-PATIENT',
        delete: 'DELETE-PATIENT',
        search: 'FIND-PATIENT',
        searchInactive: 'FINDINACTIVE-PATIENT',
        update: 'EDIT-PATIENT',
        view: 'VIEW-PATIENT',
        file: 'VIEWWORKUP-PATIENT',
        merge: 'MERGE-PATIENT'
    },
    system: {
        caseReport: 'SRTADMIN-SYSTEM',
        decisionSupport: 'ALERTADMIN-SYSTEM',
        epiLink: 'EPILINKADMIN-SYSTEM',
        messagingManagement: [
            'VIEWELRACTIVITY-OBSERVATIONLABREPORT',
            'VIEWPHCRACTIVITY-CASEREPORTING',
            'IMPORTEXPORTADMIN-SYSTEM'
        ],
        page: 'LDFADMINISTRATION-SYSTEM',
        personMatch: 'MERGE-PATIENT',
        report: 'REPORTADMIN-SYSTEM',
        security: ['ADMISTRATE-SYSTEM', 'ADMINISTRATE-SECURITY']
    }
};
