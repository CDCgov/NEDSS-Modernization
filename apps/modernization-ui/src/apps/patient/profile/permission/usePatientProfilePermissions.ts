import { useMemo } from 'react';
import { useUser } from 'user';

type PatientProfilePermission = {
    delete: boolean;
    compareInvestigation: boolean;
};

type PermissionKeyMap = {
    [key: string]: keyof PatientProfilePermission;
};

const initial: PatientProfilePermission = {
    delete: false,
    compareInvestigation: false
};

const permissionKeyMap: PermissionKeyMap = {
    'MERGEINVESTIGATION-INVESTIGATION': 'compareInvestigation',
    'DELETE-PATIENT': 'delete'
};

const resovlePermissions = (permissions?: string[]) => {
    if (permissions) {
        return permissions.slice(0).reduce((existing, next) => {
            if (permissionKeyMap[next]) {
                existing[permissionKeyMap[next]] = true;
            }
            return existing;
        }, initial);
    }

    return initial;
};

const usePatientProfilePermissions = (): PatientProfilePermission => {
    const {
        state: { user }
    } = useUser();

    const permissions = useMemo(() => resovlePermissions(user?.permissions), [user?.identifier]);

    return permissions;
};

export { usePatientProfilePermissions };
export type { PatientProfilePermission };
