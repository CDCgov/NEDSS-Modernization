import React, { useState } from 'react';
import { MergeAddress, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { AddressId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergePreviewTableCard } from '../Card/MergePreviewTableCard';
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

    const initialAddresses: AddressEntry[] = detailedAddresses.map((a) => ({
        ...a,
        asOf: format(parseISO(a.asOf), 'MM/dd/yyyy')
    }));

    const [addresses] = useState(initialAddresses);

    const columns: Column<AddressEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            value: (entry) => entry.asOf,
            render: (entry) => entry.asOf ?? '---',
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            value: (entry) => entry.type,
            render: (entry) => entry.type ?? '---',
            sortable: true
        },
        {
            id: 'address',
            name: 'Address',
            value: (entry) => entry.address,
            render: (entry) => entry.address ?? '---',
            sortable: true
        },
        {
            id: 'city',
            name: 'City',
            value: (entry) => entry.city,
            render: (entry) => entry.city ?? '---',
            sortable: true
        },
        {
            id: 'state',
            name: 'State',
            value: (entry) => entry.state,
            render: (entry) => entry.state ?? '---',
            sortable: true
        },
        {
            id: 'zipcode',
            name: 'Zip',
            value: (entry) => entry.zipcode,
            render: (entry) => entry.zipcode ?? '---',
            sortable: true
        }
    ];

    return <MergePreviewTableCard<AddressEntry> id="address" title="Address" columns={columns} data={addresses} />;
};
