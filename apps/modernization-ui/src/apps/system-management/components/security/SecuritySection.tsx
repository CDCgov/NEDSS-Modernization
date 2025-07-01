import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { permitsAll, Permitted } from '../../../../libs/permission';
import { RedirectHome } from '../../../../routes';

const securityLinks = [
    {
        text: 'Manage permission sets',
        href: '/nbs/loadPermissionSets.do?OperationType=152'
    },
    {
        text: 'Manage users',
        href: '/nbs/userList.do'
    }
];

type Props = {
    filter: string;
};

export const SecuritySection = ({ filter }: Props) => {
    return (
        <Permitted permission={permitsAll('REPORTADMIN-SYSTEM')} fallback={<RedirectHome />}>
            <SystemManagementInfoCard id="security" title="Security" filter={filter} links={securityLinks} />
        </Permitted>
    );
};
