import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

type Props = {
    filter: string;
    links: { text: string; href: string }[];
};

export const EpiLinkSection = ({ filter, links }: Props) => {
    return (
        <SystemManagementInfoCard
            id="epi-link-lot-number"
            title="Epi-link (lot number)"
            filter={filter}
            links={links}
        />
    );
};
