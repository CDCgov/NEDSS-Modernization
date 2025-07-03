import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { Permitted } from '../../../../libs/permission';

const decisionSupportLinks = [
    {
        text: 'Manage alerts',
        href: '/nbs/AlertUser.do?method=alertAdminUser'
    },
    {
        text: 'Manage user email',
        href: '/nbs/EmailAlert.do?method=loadEmail'
    },
    {
        text: 'Manage workflow decision support',
        href: '/nbs/ManageDecisionSupport.do?method=loadqueue&initLoad=true'
    }
];

type Props = {
    filter: string;
};

export const DecisionSupportSection = ({ filter }: Props) => {
    return (
        <Permitted permission={'DECISIONSUPPORTADMIN-SYSTEM'}>
            <SystemManagementInfoCard
                id="decision-support"
                title="Decision support"
                filter={filter}
                links={decisionSupportLinks}
            />
        </Permitted>
    );
};
