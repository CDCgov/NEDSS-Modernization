import { MergeRace } from 'apps/deduplication/api/model/MergeCandidate';

import { toDateDisplay } from '../../../../shared/toDateDisplay';
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
                    value: toDateDisplay(race.asOf),
                },
                { label: 'Race', value: race.race },
                { label: 'Detailed race', value: race.detailedRaces },
            ]}
        />
    );
};
