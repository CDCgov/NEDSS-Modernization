import { useMemo } from 'react';
import { useUser } from 'user';

type PatientProfilePermission = {
    delete: boolean;
};

const initial: PatientProfilePermission = {
    delete: false
};

const resovlePermissions = (permissions?: string[]) => {
    if (permissions) {
        return permissions.slice(0).reduce((existing, next) => {
            if (next === 'DELETE-PATIENT') {
                return { ...existing, delete: true };
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
