import { PatientIdentification } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { DetailsSection } from '../../shared/details-section/DetailsSection';

type Props = {
    identification: PatientIdentification;
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
