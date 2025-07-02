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
        merge: 'MERGEINVESTIGATION-INVESTIGATION',
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
    patient: {
        add: 'ADD-PATIENT',
        delete: 'DELETE-PATIENT',
        search: 'FIND-PATIENT',
        searchInactive: 'FINDINACTIVE-PATIENT',
        update: 'EDIT-PATIENT',
        view: 'VIEW-PATIENT',
        merge: 'MERGE-PATIENT'
    }
};
