/**
 * This object contains the permissions structure used by the application.
 */
export const permissions = {
    global: {
        hivQuestion: 'HIVQUESTIONS-GLOBAL',
    },
    investigation: {
        add: 'ADD-INVESTIGATION',
        delete: 'DELETE-INVESTIGATION',
        edit: 'EDIT-INVESTIGATION',
        compare: 'MERGEINVESTIGATION-INVESTIGATION',
        view: 'VIEW-INVESTIGATION',
    },
    labReport: {
        add: 'ADD-OBSERVATIONLABREPORT',
        delete: 'DELETE-OBSERVATIONLABREPORT',
        edit: 'EDIT-OBSERVATIONLABREPORT',
        view: 'VIEW-OBSERVATIONLABREPORT',
    },
    morbidityReport: {
        add: 'ADD-OBSERVATIONMORBIDITYREPORT',
        delete: 'DELETE-OBSERVATIONMORBIDITYREPORT',
        edit: 'EDIT-OBSERVATIONMORBIDITYREPORT',
        view: 'VIEW-OBSERVATIONMORBIDITYREPORT',
    },
    birthRecord: {
        add: 'Add-BirthRecord',
        view: 'View-BirthRecord',
    },
    reports: {
        template: {
            view: 'VIEWREPORTTEMPLATE-REPORTING',
            selectFilterCriteria: 'SELECTFILTERCRITERIATEMPLATE-REPORTING',
            create: '', // only done via report admin
            edit: '', // only done via report admin
            delete: '', // only done via report admin
        },
        public: {
            view: 'VIEWREPORTPUBLIC-REPORTING',
            selectFilterCriteria: 'SELECTFILTERCRITERIAPUBLIC-REPORTING',
            create: 'CREATEREPORTPUBLIC-REPORTING',
            edit: 'EDITREPORTPUBLIC-REPORTING',
            delete: 'DELETEREPORTPUBLIC-REPORTING',
        },
        private: {
            view: 'VIEWREPORTPRIVATE-REPORTING',
            selectFilterCriteria: 'SELECTFILTERCRITERIAPRIVATE-REPORTING',
            create: 'CREATEREPORTPRIVATE-REPORTING',
            edit: 'EDITREPORTPRIVATE-REPORTING',
            delete: 'DELETEREPORTPRIVATE-REPORTING',
        },
        reportingFacility: {
            view: 'VIEWREPORTREPORTINGFACILITY-REPORTING',
            selectFilterCriteria: 'SELECTFILTERCRITERIAREPORTINGFACILITY-REPORTING',
            create: 'CREATEREPORTREPORTINGFACILITY-REPORTING',
            edit: 'EDITREPORTREPORTINGFACILITY-REPORTING',
            delete: 'DELETEREPORTREPORTINGFACILITY-REPORTING',
        },
        run: 'RUNREPORT-REPORTING',
        export: 'EXPORTREPORT-REPORTING',
    },
    summaryReports: {
        view: 'VIEW-SUMMARYREPORT',
        delete: 'DELETE-SUMMARYREPORT',
    },
    vaccination: {
        add: 'ADD-INTERVENTIONVACCINERECORD',
        view: 'VIEW-INTERVENTIONVACCINERECORD',
    },
    place: {
        manage: 'MANAGE-PLACE',
    },
    provider: {
        manage: 'MANAGE-PROVIDER',
    },
    organization: {
        manage: 'MANAGE-ORGANIZATION',
    },
    patient: {
        add: 'ADD-PATIENT',
        delete: 'DELETE-PATIENT',
        search: 'FIND-PATIENT',
        searchInactive: 'FINDINACTIVE-PATIENT',
        update: 'EDIT-PATIENT',
        view: 'VIEW-PATIENT',
        file: 'VIEWWORKUP-PATIENT',
        merge: 'MERGE-PATIENT',
    },
    system: {
        caseReport: 'SRTADMIN-SYSTEM',
        decisionSupport: 'ALERTADMIN-SYSTEM',
        epiLink: 'EPILINKADMIN-SYSTEM',
        messagingManagement: [
            'VIEWELRACTIVITY-OBSERVATIONLABREPORT',
            'VIEWPHCRACTIVITY-CASEREPORTING',
            'IMPORTEXPORTADMIN-SYSTEM',
        ],
        page: 'LDFADMINISTRATION-SYSTEM',
        personMatch: 'MERGE-PATIENT',
        report: 'REPORTADMIN-SYSTEM',
        security: ['ADMISTRATE-SYSTEM', 'ADMINISTRATE-SECURITY'],
    },
};
