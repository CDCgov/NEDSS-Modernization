import { PatientData, PatientName } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';

type Props = {
    patientData: PatientData;
    selectedName?: PatientName;
    onViewName: (name: PatientName) => void;
};
export const NameDataTable = ({ patientData, onViewName, selectedName }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({ control: form.control, name: 'names' });

    const handleNameSelection = (name: PatientName) => {
        if (fields.some((f) => f.personUid === name.personUid && f.sequence === name.sequence)) {
            remove(fields.findIndex((f) => f.personUid === name.personUid && f.sequence === name.sequence));
        } else {
            append({ personUid: name.personUid, sequence: name.sequence });
        }
    };

    const formatName = (name: PatientName) => {
        if (name.first == undefined && name.last == undefined) {
            return '---';
        }
        return `${name.last ?? '---'}, ${name.first ?? '---'}`;
    };

    const columns: Column<PatientName>[] = [
        {
            id: 'as-of',
            name: 'As of',
            render: (n) => format(parseISO(n.asOf), 'MM/dd/yyyy')
        },
        {
            id: 'type',
            name: 'Type',
            render: (n) => n.type
        },
        {
            id: 'name',
            name: 'Name',
            render: (n) => formatName(n)
        }
    ];
    return (
        <MergeDataTable<PatientName>
            id={`name-data${patientData.personUid}`}
            columns={columns}
            data={patientData.names}
            rowId={(n) => `name-${n.personUid}-${n.sequence}`}
            isSelected={(n) => fields.some((f) => f.personUid === n.personUid && f.sequence === n.sequence)}
            onSelect={(n) => handleNameSelection(n)}
            isViewed={(n) => selectedName === n}
            onView={(n) => onViewName(n)}
        />
    );
};
