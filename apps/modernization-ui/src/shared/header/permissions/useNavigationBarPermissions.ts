import { useMemo } from 'react';
import { useUser } from 'user';
import { SYSTEM_MANAGEMENT_ACCESS } from './permissions';

type NavigationBarPermissions = {
    systemManagementAccess: boolean;
};

const initialPermissions: NavigationBarPermissions = {
    systemManagementAccess: false
};

const resolvePermissions = (permissions?: string[]): NavigationBarPermissions => {
    if (!permissions) return initialPermissions;

    return permissions.reduce(
        (existing, permission) => {
            if (SYSTEM_MANAGEMENT_ACCESS.includes(permission)) {
                existing.systemManagementAccess = true;
            }
            return existing;
        },
        { ...initialPermissions }
    );
};

const useNavigationBarPermissions = (): NavigationBarPermissions => {
    const {
        state: { user }
    } = useUser();

    return useMemo(() => resolvePermissions(user?.permissions), [user?.permissions]);
};

export { useNavigationBarPermissions };
