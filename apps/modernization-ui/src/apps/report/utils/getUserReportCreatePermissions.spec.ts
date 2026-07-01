import { getUserReportCreatePermissionsOptions } from './getUserReportCreatePermissions';

describe('getUserReportCreatePermissions', () => {
    it('should only return create report permissions', () => {
        const USER_PERMISSIONS = [
            'CREATEREPORTPRIVATE-REPORTING',
            'CREATEREPORTPUBLIC-REPORTING',
            'CREATEREPORTREPORTINGFACILITY-REPORTING',
            'CREATENEEDSAPPROVAL-NOTIFICATION',
            'EDITREPORTPRIVATE-REPORTING',
            'EDITREPORTPUBLIC-REPORTING',
            'EXPORTREPORT-REPORTING',
            'RUNREPORT-REPORTING',
            'VIEWREPORTPRIVATE-REPORTING',
            'VIEWREPORTPUBLIC-REPORTING',
            'VIEWREPORTTEMPLATE-REPORTING',
        ];
        const actual = getUserReportCreatePermissionsOptions(USER_PERMISSIONS);
        expect(actual).toEqual([
            {
                name: 'Public',
                value: 'PUBLIC',
            },
            {
                name: 'Private',
                value: 'PRIVATE',
            },
            {
                name: 'Reporting Facility',
                value: 'REPORTING_FACILITY',
            },
        ]);
    });

    it('should return an empty array if no matches', () => {
        const USER_PERMISSIONS = [
            'EDITREPORTPRIVATE-REPORTING',
            'EDITREPORTPUBLIC-REPORTING',
            'EXPORTREPORT-REPORTING',
            'RUNREPORT-REPORTING',
            'VIEWREPORTPRIVATE-REPORTING',
            'VIEWREPORTPUBLIC-REPORTING',
            'VIEWREPORTTEMPLATE-REPORTING',
        ];
        const actual = getUserReportCreatePermissionsOptions(USER_PERMISSIONS);
        expect(actual).toEqual([]);
    });
});
