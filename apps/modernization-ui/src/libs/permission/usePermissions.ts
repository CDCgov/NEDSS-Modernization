import { useCallback, useMemo } from 'react';
import { useUser } from 'user';
import { Predicate } from 'utils';
import { permits } from './permits';

type Interaction = {
    permissions: string[];
    allows: Predicate<string>;
};

const usePermissions = (): Interaction => {
    const {
        state: { user }
    } = useUser();

    const permissions = useMemo(() => user?.permissions ?? [], [user?.identifier]);
    const allows = useCallback((permission: string) => permits(permission)(permissions), [user?.identifier]);

    return { permissions, allows };
};

export { usePermissions };
