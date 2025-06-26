import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const DecisionSupportSection = ({ filter, links }: Props) => {
    return <SystemManagementInfoCard id="decision-support" title="Decision support" filter={filter} links={links} />;
};
