import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const PersonMatchSection = ({ filter, links }: Props) => {
    return <SystemManagementInfoCard id="person-match" title="Person match" filter={filter} links={links} />;
};
