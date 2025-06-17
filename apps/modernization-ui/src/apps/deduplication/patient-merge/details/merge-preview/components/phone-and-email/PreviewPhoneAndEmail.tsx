import React, { useState } from 'react';
import { MergePhoneEmail, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PhoneEmailId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergePreviewTableCard } from '../shared/preview-card-table/MergePreviewTableCard';
import { Column } from 'design-system/table';
import { formatPhone } from '../../../shared/formatPhone';

type PhoneEmailEntry = {
    id: string;
    asOf: string;
    type: string;
    use: string;
    phoneNumber?: string;
    email?: string;
    comments?: string;
};

type PreviewPhoneAndEmailProps = {
    selectedPhoneEmails: PhoneEmailId[];
    mergeCandidates: MergeCandidate[];
};

export const PreviewPhoneAndEmail = ({ selectedPhoneEmails, mergeCandidates }: PreviewPhoneAndEmailProps) => {
    const detailedPhoneEmails: MergePhoneEmail[] = mergeCandidates
        .flatMap((mc) => mc.phoneEmails)
        .filter((pe) => selectedPhoneEmails.some((sel) => sel.locatorId === pe.id));

    const initialPhoneEmails: PhoneEmailEntry[] = detailedPhoneEmails.map((p) => ({
        ...p,
        asOf: format(parseISO(p.asOf), 'MM/dd/yyyy')
    }));

    const [phoneEmails] = useState(initialPhoneEmails);

    const columns: Column<PhoneEmailEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            value: (e) => e.asOf ?? '---',
            sortable: true
        },
        {
            id: 'typeUse',
            name: 'Type',
            value: (e) => e.type ?? '---',
            sortable: true
        },
        {
            id: 'phoneNumber',
            name: 'Phone Number',
            value: (e) => formatPhone(e.phoneNumber) ?? '---',
            render: (e) => formatPhone(e.phoneNumber) ?? '---',
            sortable: true
        },
        {
            id: 'email',
            name: 'Email Address',
            value: (e) => e.email ?? '---',
            sortable: true
        },
        {
            id: 'comments',
            name: 'Comments',
            value: (e) => e.comments ?? '---',
            sortable: true
        }
    ];

    return (
        <MergePreviewTableCard<PhoneEmailEntry>
            id="phoneEmails"
            title="Phone & email"
            columns={columns}
            data={phoneEmails}
        />
    );
};
