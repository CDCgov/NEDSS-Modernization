import React, { useEffect, useState } from 'react';
import { MergeAddress, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { AddressId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { SortableTableCard } from '../Card/SortableTableCard';
import { Column } from 'design-system/table/DataTable';

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
    const detailedAddresses: MergeAddress[] = selectedAddresses.flatMap(({ locatorId }) => {
        for (const candidate of mergeCandidates) {
            const found = candidate.addresses.find((addr) => addr.id === locatorId);
            if (found) return [found];
        }
        return [];
    });

    const initialAddresses: AddressEntry[] = detailedAddresses.map((a) => ({
        id: a.id,
        asOf: format(parseISO(a.asOf), 'MM/dd/yyyy'),
        type: a.type,
        use: a.use,
        address: a.address,
        city: a.city,
        state: a.state,
        zipcode: a.zipcode,
        county: a.county,
        country: a.country
    }));

    const [addresses] = useState(initialAddresses);
    const [dirty, setDirty] = useState(false);

    const deepEqual = (a: AddressEntry[], b: AddressEntry[]) => {
        if (a.length !== b.length) return false;
        return a.every((itemA, index) => {
            const itemB = b[index];
            return Object.keys(itemA).every((key) => (itemA as any)[key] === (itemB as any)[key]);
        });
    };

    useEffect(() => {
        setDirty(!deepEqual(addresses, initialAddresses));
    }, [addresses, initialAddresses]);

    const columns: Column<AddressEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            value: (entry) => entry.asOf,
            render: (entry) => entry.asOf || '---',
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            value: (entry) => entry.type,
            render: (entry) => entry.type || '---',
            sortable: true
        },
        {
            id: 'address',
            name: 'Address',
            value: (entry) => entry.address,
            render: (entry) => entry.address || '---',
            sortable: true
        },
        {
            id: 'city',
            name: 'City',
            value: (entry) => entry.city,
            render: (entry) => entry.city || '---',
            sortable: true
        },
        {
            id: 'state',
            name: 'State',
            value: (entry) => entry.state,
            render: (entry) => entry.state || '---',
            sortable: true
        },
        {
            id: 'zipcode',
            name: 'Zip',
            value: (entry) => entry.zipcode,
            render: (entry) => entry.zipcode || '---',
            sortable: true
        }
    ];

    return (
        <>
            <SortableTableCard<AddressEntry> id="address" title="Address" columns={columns} data={addresses} />
            {dirty && <p></p>}
        </>
    );
};
