import { ReactNode } from 'react';
import { Shown } from 'conditional-render';
import { usePermissions } from './usePermissions';

type PermittedProps = {
    permission: string;
    children: ReactNode | ReactNode[];
};

const Permitted = ({ permission, children }: PermittedProps) => {
    const { allows } = usePermissions();

    const allowed = allows(permission);

    return <Shown when={allowed}>{children}</Shown>;
};

export { Permitted };
