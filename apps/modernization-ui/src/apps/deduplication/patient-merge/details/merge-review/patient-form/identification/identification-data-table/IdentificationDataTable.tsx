import { PatientData, PatientIdentification } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Button } from 'design-system/button';
import { Checkbox } from 'design-system/checkbox';
import { Icon } from 'design-system/icon';
import { Column, DataTable } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import styles from './identification-data-table.module.scss';

type Props = {
    patientData: PatientData;
    selectedIdentification?: PatientIdentification;
    onViewIdentification: (identification: PatientIdentification) => void;
};
export const IdentificationDataTable = ({ patientData, selectedIdentification, onViewIdentification }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'identifications'
    });

    const handleIdentificationSelection = (identification: PatientIdentification) => {
        const index = fields.findIndex(
            (f) => f.personUid === identification.personUid && f.sequence === identification.sequence
        );
        if (index > -1) {
            remove(index);
        } else {
            append({ personUid: identification.personUid, sequence: identification.sequence });
        }
    };

    const columns: Column<PatientIdentification>[] = [
        {
            id: 'selection',
            name: '',
            render: (i) => (
                <Checkbox
                    id={`identificaiton-select:${i.personUid}-${i.sequence}`}
                    label=""
                    className={styles.checkBox}
                    onChange={() => handleIdentificationSelection(i)}
                    selected={fields.some((f) => f.personUid === i.personUid && f.sequence === i.sequence)}
                />
            )
        },
        {
            id: 'as-of',
            name: 'As of',
            render: (i) => format(parseISO(i.asOf), 'MM/dd/yyyy')
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
        },
        {
            id: 'view-icon',
            name: '',
            render: (i) => (
                <Button
                    unstyled
                    sizing="small"
                    icon={<Icon name="visibility" className={selectedIdentification === i ? styles.selected : ''} />}
                    onClick={() => onViewIdentification(i)}
                />
            )
        }
    ];
    return (
        <DataTable<PatientIdentification>
            id={`identification-data${patientData.personUid}`}
            className={styles.dataTable}
            sizing="small"
            columns={columns}
            data={patientData.identifications ?? []}
        />
    );
};
