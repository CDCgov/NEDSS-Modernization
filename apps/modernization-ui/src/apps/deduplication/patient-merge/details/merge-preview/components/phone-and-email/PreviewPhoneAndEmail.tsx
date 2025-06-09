import React, { useEffect, useState } from 'react';
import { ReadOnlyRepeatingBlock } from '../ReadOnlyRepeatingBlock/ReadOnlyRepeatingBlock';
import { MergePhoneEmail, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PhoneEmailId } from '../../../merge-review/model/PatientMergeForm';
import { toMMDDYYYY } from '../../utils/dateUtils';

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
        asOf: toMMDDYYYY(p.asOf),
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

    const columns = [
        { id: 'asOf', name: 'As of', render: (entry: PhoneEmailEntry) => entry.asOf || '---' },
        { id: 'type', name: 'Type', render: (entry: PhoneEmailEntry) => entry.type || '---' },
        { id: 'phoneNumber', name: 'Phone Number', render: (entry: PhoneEmailEntry) => entry.phoneNumber || '---' },
        { id: 'email', name: 'Email Address', render: (entry: PhoneEmailEntry) => entry.email || '---' },
        { id: 'comments', name: 'Comments', render: (entry: PhoneEmailEntry) => entry.comments || '---' }
    ];

    return (
        <>
            <ReadOnlyRepeatingBlock<PhoneEmailEntry>
                id="phoneEmails"
                title="Phone & email"
                columns={columns}
                values={phoneEmails}
            />
            {dirty && <p style={{ color: 'red' }}>You have unsaved changes</p>}
        </>
    );
};
