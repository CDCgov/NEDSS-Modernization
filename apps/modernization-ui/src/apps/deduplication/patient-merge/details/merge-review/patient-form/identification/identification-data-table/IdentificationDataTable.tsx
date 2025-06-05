import { MergeCandidate, MergeIdentification } from 'apps/deduplication/api/model/MergeCandidate';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';
import { toDateDisplay } from '../../shared/toDateDisplay';

type Props = {
    patientData: MergeCandidate;
    selectedIdentification?: MergeIdentification;
    onViewIdentification: (identification: MergeIdentification) => void;
};
export const IdentificationDataTable = ({ patientData, selectedIdentification, onViewIdentification }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'identifications'
    });

    const handleIdentificationSelection = (identification: MergeIdentification) => {
        const index = fields.findIndex(
            (f) => f.personUid === identification.personUid && f.sequence === identification.sequence
        );
        if (index > -1) {
            remove(index);
        } else {
            append({ personUid: identification.personUid, sequence: identification.sequence });
        }
    };

    const columns: Column<MergeIdentification>[] = [
        {
            id: 'as-of',
            name: 'As of',
            render: (i) => toDateDisplay(i.asOf)
        },
        {
            id: 'type',
            name: 'Type',
            render: (i) => i.type
        },
        {
            id: 'value',
            name: 'ID value',
            render: (i) => i.value
        }
    ];
    return (
        <MergeDataTable<MergeIdentification>
            id={`identification-data${patientData.personUid}`}
            columns={columns}
            data={patientData.identifications}
            rowId={(i) => `identificiation-${i.personUid}-${i.sequence}`}
            isSelected={(i) => fields.some((f) => f.personUid === i.personUid && f.sequence === i.sequence)}
            onSelect={(i) => handleIdentificationSelection(i)}
            isViewed={(i) => selectedIdentification === i}
            onView={(i) => onViewIdentification(i)}
        />
    );
};
