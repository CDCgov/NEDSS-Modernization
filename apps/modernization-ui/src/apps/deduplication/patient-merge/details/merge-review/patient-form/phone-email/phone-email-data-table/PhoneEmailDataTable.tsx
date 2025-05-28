import { PatientAddress, PatientData, PatientPhoneEmail } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Button } from 'design-system/button';
import { Checkbox } from 'design-system/checkbox';
import { Icon } from 'design-system/icon';
import { Column, DataTable } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import styles from './phone-email-data-table.module.scss';
import { formatPhone } from '../formatPhone';

type Props = {
    patientData: PatientData;
    selectedPhoneEmail?: PatientPhoneEmail;
    onViewPhoneEmail: (phoneEmail: PatientPhoneEmail) => void;
};
export const PhoneEmailDataTable = ({ patientData, selectedPhoneEmail, onViewPhoneEmail }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'phoneEmails'
    });

    const handlePhoneEmailSelection = (phoneEmail: PatientPhoneEmail) => {
        const index = fields.findIndex((f) => f.locatorId === phoneEmail.id);
        if (index > -1) {
            remove(index);
        } else {
            append({ locatorId: phoneEmail.id });
        }
    };

    const columns: Column<PatientPhoneEmail>[] = [
        {
            id: 'selection',
            name: '',
            render: (a) => (
                <Checkbox
                    id={`phoneEmail-select:${a.id}`}
                    label=""
                    className={styles.checkBox}
                    onChange={() => handlePhoneEmailSelection(a)}
                    selected={fields.some((f) => f.locatorId === a.id)}
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
            render: (n) => `${n.type}/${n.use}`
        },
        {
            id: 'phoneNumber',
            name: 'Phone number',
            render: (n) => formatPhone(n.phoneNumber)
        },
        {
            id: 'view-icon',
            name: '',
            render: (n) => (
                <Button
                    unstyled
                    sizing="small"
                    icon={<Icon name="visibility" className={selectedPhoneEmail === n ? styles.selected : ''} />}
                    onClick={() => onViewPhoneEmail(n)}
                />
            )
        }
    ];
    return (
        <DataTable<PatientAddress>
            id={`address-data${patientData.personUid}`}
            className={styles.dataTable}
            sizing="small"
            columns={columns}
            data={patientData.phoneEmails ?? []}
        />
    );
};
