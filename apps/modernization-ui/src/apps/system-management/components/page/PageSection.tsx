import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const PageSection = ({ filter, links }: Props) => {
    return <SystemManagementInfoCard id="page" title="Page" filter={filter} links={links} />;
};
