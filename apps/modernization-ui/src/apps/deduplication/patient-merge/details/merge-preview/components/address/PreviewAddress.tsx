import React, { useState } from 'react';
import { MergeAddress, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { AddressId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergePreviewTableCard } from '../shared/preview-card-table/MergePreviewTableCard';
import { Column } from 'design-system/table';

type AddressEntry = {
    id: string;
    asOf: string;
    type: string;
    use: string;
    address?: string;
    city?: string;
    state?: string;
    zipcode?: string;
    county?: string;
    country?: string;
};

type AddressProps = {
    selectedAddresses: AddressId[];
    mergeCandidates: MergeCandidate[];
};

export const PreviewAddress = ({ selectedAddresses, mergeCandidates }: AddressProps) => {
    const detailedAddresses: MergeAddress[] = mergeCandidates
        .flatMap((m) => m.addresses)
        .filter((a) => selectedAddresses.find((sa) => sa.locatorId === a.id));

    const initialAddresses: AddressEntry[] = detailedAddresses
        .map((a) => ({
            ...a,
            asOf: format(parseISO(a.asOf), 'MM/dd/yyyy')
        }))
        .sort((a, b) => (parseISO(a.asOf) > parseISO(b.asOf) ? -1 : 1));

    const [addresses] = useState(initialAddresses);

    const columns: Column<AddressEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            value: (entry) => entry.asOf ?? '---',
            sortable: true
        },
        {
            id: 'typeUse',
            name: 'Type',
            value: (entry) => [entry.type, entry.use].filter(Boolean).join(' / ') || '',
            render: (entry) => [entry.type, entry.use].filter(Boolean).join(' / ') || '---',
            sortable: true
        },
        {
            id: 'address',
            name: 'Address',
            value: (entry) => entry.address ?? '---',
            sortable: true
        },
        {
            id: 'city',
            name: 'City',
            value: (entry) => entry.city ?? '---',
            sortable: true
        },
        {
            id: 'state',
            name: 'State',
            value: (entry) => entry.state ?? '---',
            sortable: true
        },
        {
            id: 'zipcode',
            name: 'Zip',
            value: (entry) => entry.zipcode ?? '---',
            sortable: true
        }
    ];

    return <MergePreviewTableCard<AddressEntry> id="address" title="Address" columns={columns} data={addresses} />;
};
