import { PatientData, PatientPhoneEmail } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';
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
        }
    ];
    return (
        <MergeDataTable<PatientPhoneEmail>
            id={`phone-email-data${patientData.personUid}`}
            columns={columns}
            data={patientData.phoneEmails}
            rowId={(p) => p.id}
            isSelected={(p) => fields.some((f) => f.locatorId === p.id)}
            onSelect={(p) => handlePhoneEmailSelection(p)}
            isViewed={(p) => selectedPhoneEmail === p}
            onView={(p) => onViewPhoneEmail(p)}
        />
    );
};
