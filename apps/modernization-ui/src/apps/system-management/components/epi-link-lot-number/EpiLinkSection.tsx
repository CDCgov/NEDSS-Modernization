import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { Permitted } from '../../../../libs/permission';

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
        <Permitted permission={'EPILINKADMIN-SYSTEM'}>
            <SystemManagementInfoCard
                id="epi-link-lot-number"
                title="Epi-link (lot number)"
                filter={filter}
                links={epiLinkLinks}
            />
        </Permitted>
    );
};
