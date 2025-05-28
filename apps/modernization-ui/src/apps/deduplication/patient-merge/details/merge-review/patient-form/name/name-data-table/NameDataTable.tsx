import { PatientData, PatientName } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Button } from 'design-system/button';
import { Checkbox } from 'design-system/checkbox';
import { Icon } from 'design-system/icon';
import { Column, DataTable } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import styles from './name-data-table.module.scss';

type Props = {
    patientData: PatientData;
    selectedName?: PatientName;
    onViewName: (name: PatientName) => void;
};
export const NameDataTable = ({ patientData, onViewName, selectedName }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({ control: form.control, name: 'names' });

    const handleNameSelect = (name: PatientName) => {
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
            id: 'selection',
            name: '',
            render: (n) => (
                <Checkbox
                    id={`name-select:${n.personUid}-${n.sequence}`}
                    label=""
                    className={styles.checkBox}
                    onChange={() => handleNameSelect(n)}
                    selected={fields.some((f) => f.personUid === n.personUid && f.sequence === n.sequence)}
                />
            )
        },
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
        },
        {
            id: 'view-icon',
            name: '',
            render: (n) => (
                <Button
                    unstyled
                    sizing="small"
                    icon={<Icon name="visibility" className={selectedName === n ? styles.selected : ''} />}
                    onClick={() => onViewName(n)}
                />
            )
        }
    ];
    return (
        <DataTable<PatientName>
            id={`name-data${patientData.personUid}`}
            className={styles.dataTable}
            sizing="small"
            columns={columns}
            data={patientData.names ?? []}
        />
    );
};
