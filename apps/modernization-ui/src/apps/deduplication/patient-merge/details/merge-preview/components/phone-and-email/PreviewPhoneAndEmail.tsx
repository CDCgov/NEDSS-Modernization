import React, { useEffect, useState } from 'react';
import { MergePhoneEmail, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PhoneEmailId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { SortableTableCard } from '../Card/SortableTableCard';
import { Column } from 'design-system/table/DataTable';

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
    const detailedPhoneEmails: MergePhoneEmail[] = selectedPhoneEmails.flatMap(({ locatorId }) => {
        for (const candidate of mergeCandidates) {
            const found = candidate.phoneEmails.find((pe) => pe.id === locatorId);
            if (found) return [found];
        }
        return [];
    });

    const initialPhoneEmails: PhoneEmailEntry[] = detailedPhoneEmails.map((p) => ({
        id: p.id,
        asOf: format(parseISO(p.asOf), 'MM/dd/yyyy'),
        type: p.type,
        phoneNumber: p.phoneNumber,
        email: p.email,
        comments: p.comments
    }));

    const [phoneEmails] = useState(initialPhoneEmails);
    const [dirty, setDirty] = useState(false);

    const deepEqual = (a: PhoneEmailEntry[], b: PhoneEmailEntry[]) => {
        if (a.length !== b.length) return false;
        return a.every((itemA, index) => {
            const itemB = b[index];
            return Object.keys(itemA).every((key) => (itemA as any)[key] === (itemB as any)[key]);
        });
    };

    useEffect(() => {
        setDirty(!deepEqual(phoneEmails, initialPhoneEmails));
    }, [phoneEmails, initialPhoneEmails]);

    const columns: Column<PhoneEmailEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: PhoneEmailEntry) => entry.asOf || '---',
            value: (entry: PhoneEmailEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            render: (entry: PhoneEmailEntry) => entry.type || '---',
            value: (entry: PhoneEmailEntry) => entry.type,
            sortable: true
        },
        {
            id: 'phoneNumber',
            name: 'Phone Number',
            render: (entry: PhoneEmailEntry) => entry.phoneNumber || '---',
            value: (entry: PhoneEmailEntry) => entry.phoneNumber,
            sortable: true
        },
        {
            id: 'email',
            name: 'Email Address',
            render: (entry: PhoneEmailEntry) => entry.email || '---',
            value: (entry: PhoneEmailEntry) => entry.email,
            sortable: true
        },
        {
            id: 'comments',
            name: 'Comments',
            render: (entry: PhoneEmailEntry) => entry.comments || '---',
            value: (entry: PhoneEmailEntry) => entry.comments,
            sortable: true
        }
    ];

    return (
        <>
            <SortableTableCard<PhoneEmailEntry>
                id="phoneEmails"
                title="Phone & email"
                columns={columns}
                data={phoneEmails}
            />
            {dirty && <p></p>}
        </>
    );
};
