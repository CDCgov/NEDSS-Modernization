import { useMemo } from 'react';

const SYSTEM_MANAGEMENT_PERMISSIONS = [
    'EPILINKADMIN-SYSTEM',
    'VIEWELRACTIVITY-OBSERVATIONLABREPORT',
    'SRTADMIN-SYSTEM',
    'VIEWPHCRACTIVITY-CASEREPORTING',
    'IMPORTEXPORTADMIN-SYSTEM',
    'REPORTADMIN-SYSTEM',
    'ALERTADMIN-SYSTEM'
];

const useSystemManagementAccess = (permissions: string[] | undefined | null) => {
    const hasAccess = useMemo(() => {
        return permissions && permissions.some((permission) => SYSTEM_MANAGEMENT_PERMISSIONS.includes(permission));
    }, [permissions]);

    return hasAccess;
};

export { useSystemManagementAccess };
