import React, { useState } from 'react';
import { MergePhoneEmail, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PhoneEmailId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergePreviewTableCard } from '../shared/preview-card-table/MergePreviewTableCard';
import { Column } from 'design-system/table';

type PhoneEmailEntry = {
    id: string;
    asOf: string;
    type: string;
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
            render: (entry: PhoneEmailEntry) => entry.asOf ?? '---',
            value: (entry: PhoneEmailEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            render: (entry: PhoneEmailEntry) => entry.type ?? '---',
            value: (entry: PhoneEmailEntry) => entry.type,
            sortable: true
        },
        {
            id: 'phoneNumber',
            name: 'Phone Number',
            render: (entry: PhoneEmailEntry) => entry.phoneNumber ?? '---',
            value: (entry: PhoneEmailEntry) => entry.phoneNumber,
            sortable: true
        },
        {
            id: 'email',
            name: 'Email Address',
            render: (entry: PhoneEmailEntry) => entry.email ?? '---',
            value: (entry: PhoneEmailEntry) => entry.email,
            sortable: true
        },
        {
            id: 'comments',
            name: 'Comments',
            render: (entry: PhoneEmailEntry) => entry.comments ?? '---',
            value: (entry: PhoneEmailEntry) => entry.comments,
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
