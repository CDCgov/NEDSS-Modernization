import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

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
    return <SystemManagementInfoCard id="security" title="Security" filter={filter} links={securityLinks} />;
};
