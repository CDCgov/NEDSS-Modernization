import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';

const epiLinkLinks = [
    {
        text: 'Manage epi-link ID',
        href: '/nbs/EpiLinkAdmin.do?method=mergeEpilink'
    },
    {
        text: 'Manage epi-link ID activity log',
        href: '/nbs/EpiLinkLogAdmin.do?method=loadActivityLog'
    }
];

type Props = {
    filter: string;
};

export const EpiLinkSection = ({ filter }: Props) => {
    return (
        <SystemManagementInfoCard
            id="epi-link-lot-number"
            title="Epi-link (lot number)"
            filter={filter}
            links={epiLinkLinks}
        />
    );
};
