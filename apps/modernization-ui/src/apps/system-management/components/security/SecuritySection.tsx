import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const SecuritySection = ({ filter, links }: Props) => {
    return <SystemManagementInfoCard id="security" title="Security" filter={filter} links={links} />;
};
