import React, { useEffect, useState } from 'react';
import { ReadOnlyRepeatingBlock } from '../ReadOnlyRepeatingBlock/ReadOnlyRepeatingBlock';
import { MergeIdentification, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { IdentificationId } from '../../../merge-review/model/PatientMergeForm';
import { toMMDDYYYY } from '../../utils/dateUtils';

type IdentificationEntry = {
    asOf: string;
    type: string;
    assigningAuthority?: string;
    value: string;
};

type PreviewIdentificationProps = {
    selectedIdentifications: IdentificationId[];
    mergeCandidates: MergeCandidate[];
};

export const PreviewIdentification = ({ selectedIdentifications, mergeCandidates }: PreviewIdentificationProps) => {
    const detailedIdentifications: MergeIdentification[] = selectedIdentifications.flatMap(
        ({ personUid, sequence }) => {
            const candidate = mergeCandidates.find((mc) => mc.personUid === personUid);
            if (!candidate) return [];
            return candidate.identifications.filter((id) => id.sequence === sequence);
        }
    );

    const initialIdentifications: IdentificationEntry[] = detailedIdentifications.map((id) => ({
        asOf: toMMDDYYYY(id.asOf),
        type: id.type,
        assigningAuthority: id.assigningAuthority,
        value: id.value
    }));

    const [identifications] = useState(initialIdentifications);
    const [dirty, setDirty] = useState(false);

    const deepEqual = (a: IdentificationEntry[], b: IdentificationEntry[]) => {
        if (a.length !== b.length) return false;
        return a.every((itemA, index) => {
            const itemB = b[index];
            return Object.keys(itemA).every((key) => (itemA as any)[key] === (itemB as any)[key]);
        });
    };

    useEffect(() => {
        setDirty(!deepEqual(identifications, initialIdentifications));
    }, [identifications, initialIdentifications]);

    const columns = [
        { id: 'asOf', name: 'As of', render: (entry: IdentificationEntry) => entry.asOf || '---' },
        { id: 'type', name: 'Type', render: (entry: IdentificationEntry) => entry.type || '---' },
        {
            id: 'assigningAuthority',
            name: 'Assigning Authority',
            render: (entry: IdentificationEntry) => entry.assigningAuthority || '---'
        },
        { id: 'value', name: 'Value', render: (entry: IdentificationEntry) => entry.value || '---' }
    ];

    return (
        <>
            <ReadOnlyRepeatingBlock<IdentificationEntry>
                id="identifications"
                title="Identifications"
                columns={columns}
                values={identifications}
            />
            {dirty && <p style={{ color: 'red' }}>You have unsaved changes</p>}
        </>
    );
};
