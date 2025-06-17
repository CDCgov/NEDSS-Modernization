import { MergeIdentification } from 'apps/deduplication/api/model/MergeCandidate';
import { DetailsSection } from '../../shared/details-section/DetailsSection';
import { toDateDisplay } from '../../../../shared/toDateDisplay';

type Props = {
    identification: MergeIdentification;
};
export const IdentificationDetails = ({ identification }: Props) => {
    return (
        <DetailsSection
            details={[
                {
                    label: 'As of date',
                    value: toDateDisplay(identification.asOf)
                },
                { label: 'Type', value: identification.type },
                { label: 'Assigning authority', value: identification.assigningAuthority },
                { label: 'ID value', value: identification.value }
            ]}
        />
    );
};
