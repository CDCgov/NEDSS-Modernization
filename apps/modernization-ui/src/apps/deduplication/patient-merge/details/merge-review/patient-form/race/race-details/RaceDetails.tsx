import { MergeRace } from 'apps/deduplication/api/model/MergePatient';
import { format, parseISO } from 'date-fns';
import { DetailsSection } from '../../shared/details-section/DetailsSection';

type Props = {
    race: MergeRace;
};
export const RaceDetails = ({ race }: Props) => {
    return (
        <DetailsSection
            details={[
                {
                    label: 'As of date',
                    value: race.asOf ? format(parseISO(race.asOf), 'MM/dd/yyyy') : '---'
                },
                { label: 'Race', value: race.race },
                { label: 'Detailed race', value: race.detailedRaces }
            ]}
        />
    );
};
