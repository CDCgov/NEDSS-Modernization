import { MergeIdentification } from 'apps/deduplication/api/model/MergeCandidate';

import { toDateDisplay } from '../../../../shared/toDateDisplay';
import { DetailsSection } from '../../shared/details-section/DetailsSection';

type Props = {
    identification: MergeIdentification;
};
export const IdentificationDetails = ({ identification }: Props) => {
    return (
        <DetailsSection
            details={[
                {
                    label: 'As of date',
                    value: toDateDisplay(identification.asOf),
                },
                { label: 'Type', value: identification.type },
                { label: 'Assigning authority', value: identification.assigningAuthority },
                { label: 'ID value', value: identification.value },
            ]}
        />
    );
};
