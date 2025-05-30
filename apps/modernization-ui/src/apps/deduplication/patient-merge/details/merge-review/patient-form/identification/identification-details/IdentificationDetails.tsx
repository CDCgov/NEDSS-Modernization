import { MergeIdentification } from 'apps/deduplication/api/model/MergePatient';
import { format, parseISO } from 'date-fns';
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
                    value: identification.asOf ? format(parseISO(identification.asOf), 'MM/dd/yyyy') : '---'
                },
                { label: 'Type', value: identification.type },
                { label: 'Assigning authority', value: identification.assigningAuthority },
                { label: 'ID value', value: identification.value }
            ]}
        />
    );
};
