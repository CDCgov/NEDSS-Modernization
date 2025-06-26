import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const MessagingSection = ({ filter, links }: Props) => {
    return <SystemManagementInfoCard id="messaging" title="Messaging" filter={filter} links={links} />;
};
