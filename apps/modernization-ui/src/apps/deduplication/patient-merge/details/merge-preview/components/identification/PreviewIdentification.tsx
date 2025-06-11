import React, { useEffect, useState } from 'react';
import { MergeIdentification, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { IdentificationId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { SortableTableCard } from '../Card/SortableTableCard';
import { Column } from 'design-system/table/DataTable';

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
        asOf: format(parseISO(id.asOf), 'MM/dd/yyyy'),
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

    const columns: Column<IdentificationEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: IdentificationEntry) => entry.asOf || '---',
            value: (entry: IdentificationEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            render: (entry: IdentificationEntry) => entry.type || '---',
            value: (entry: IdentificationEntry) => entry.type,
            sortable: true
        },
        {
            id: 'assigningAuthority',
            name: 'Assigning Authority',
            render: (entry: IdentificationEntry) => entry.assigningAuthority || '---',
            value: (entry: IdentificationEntry) => entry.assigningAuthority,
            sortable: true
        },
        {
            id: 'value',
            name: 'Value',
            render: (entry: IdentificationEntry) => entry.value || '---',
            value: (entry: IdentificationEntry) => entry.value,
            sortable: true
        }
    ];

    return (
        <>
            <SortableTableCard<IdentificationEntry>
                id="identification"
                title="Identification"
                columns={columns}
                data={identifications}
            />
            {dirty && <p style={{ color: 'red' }}>You have unsaved changes</p>}
        </>
    );
};
