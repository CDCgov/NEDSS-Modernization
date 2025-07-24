import { SystemManagementInfoCard } from '../shared/SystemManagementInfoCard';
import { Permitted } from '../../../../libs/permission';

const personMatchLinks = [
    {
        text: 'Manage data elements',
        href: '/deduplication/data_elements'
    },
    {
        text: 'Manage pass configurations',
        href: '/deduplication/configuration'
    }
];

type Props = {
    filter: string;
};

export const PersonMatchSection = ({ filter }: Props) => {
    return (
        <Permitted permission={'MERGE-PATIENT'}>
            <SystemManagementInfoCard
                id="person-match"
                title="Person match"
                filter={filter}
                links={personMatchLinks}
                useNavigation
            />
        </Permitted>
    );
};
