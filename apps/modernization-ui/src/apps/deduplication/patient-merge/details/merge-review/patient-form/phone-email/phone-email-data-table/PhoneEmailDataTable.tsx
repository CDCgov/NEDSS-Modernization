import { MergeCandidate, MergePhoneEmail } from 'apps/deduplication/api/model/MergeCandidate';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';
import { toDateDisplay } from '../../../../shared/toDateDisplay';
import { formatPhone } from '../../../../shared/formatPhone';
import styles from './phone-email-data-table.module.scss';

type Props = {
    patientData: MergeCandidate;
    selectedPhoneEmail?: MergePhoneEmail;
    onViewPhoneEmail: (phoneEmail: MergePhoneEmail) => void;
};
export const PhoneEmailDataTable = ({ patientData, selectedPhoneEmail, onViewPhoneEmail }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'phoneEmails'
    });

    const handlePhoneEmailSelection = (phoneEmail: MergePhoneEmail) => {
        const index = fields.findIndex((f) => f.locatorId === phoneEmail.id);
        if (index > -1) {
            remove(index);
        } else {
            append({ locatorId: phoneEmail.id });
        }
    };

    const columns: Column<MergePhoneEmail>[] = [
        {
            id: 'as-of',
            name: 'As of',
            className: styles['date-header'],
            render: (n) => toDateDisplay(n.asOf)
        },
        {
            id: 'type',
            name: 'Type',
            className: styles['text-header'],
            render: (n) => `${n.type}/${n.use}`
        },
        {
            id: 'phoneNumber',
            name: 'Phone number',
            render: (n) => formatPhone(n.phoneNumber)
        }
    ];
    return (
        <MergeDataTable<MergePhoneEmail>
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
