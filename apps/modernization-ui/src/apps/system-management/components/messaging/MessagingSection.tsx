import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

const messagingLinks = [
    {
        text: 'Manage ELR activity log',
        href: '/nbs/LoadDSMActivityLog.do?method=searchActivityLog&param1=11648804'
    },
    {
        text: 'Manage PHCR activity log',
        href: '/nbs/LoadDSMActivityLog.do?method=searchActivityLog&param1=PHC236'
    },
    {
        text: 'Manage sending and receiving systems',
        href: '/nbs/ReceivingSystem.do?method=manageLoad&initLoad=true'
    },
    {
        text: 'Manage HIV partner services file',
        href: '/nbs/ManageHivPartnerServices.do?method=loadFileInfo&initLoad=true'
    }
];

type Props = {
    filter: string;
};

export const MessagingSection = ({ filter }: Props) => {
    return <SystemManagementInfoCard id="messaging" title="Messaging" filter={filter} links={messagingLinks} />;
};
