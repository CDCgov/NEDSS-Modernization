import { useMemo } from 'react';
import { useUser } from 'user';

// expand as needed as search fields are shown/hidden based on permissions
type PatientSearchPermission = {
    add: boolean;
    searchInactive: boolean;
};

type PermissionKeyMap = {
    [key: string]: keyof PatientSearchPermission;
};

const initial: PatientSearchPermission = {
    add: false,
    searchInactive: false
};

const permissionKeyMap: PermissionKeyMap = {
    'FINDINACTIVE-PATIENT': 'searchInactive',
    'ADD-PATIENT': 'add'
};

const resolvePermissions = (permissions?: string[]) => {
    if (permissions) {
        return permissions.slice(0).reduce((existing, next) => {
            const key = permissionKeyMap[next];
            if (key) {
                return { ...existing, [key]: true };
            }
            return existing;
        }, initial);
    }

    return initial;
};

/**
 * Hook to resolve and return user permissions for patient search.
 * @return {PatientSearchPermission} The resolved permissions.
 */
const usePatientSearchPermissions = (): PatientSearchPermission => {
    const {
        state: { user }
    } = useUser();

    const permissions = useMemo(() => resolvePermissions(user?.permissions), [user?.identifier]);
    return permissions;
};

export { usePatientSearchPermissions };
export type { PatientSearchPermission };
