import React, { useEffect, useState } from 'react';
import { ReadOnlyRepeatingBlock } from '../ReadOnlyRepeatingBlock/ReadOnlyRepeatingBlock';
import { MergeAddress, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { AddressId } from '../../../merge-review/model/PatientMergeForm';
import { toMMDDYYYY } from '../../utils/dateUtils';

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
        asOf: toMMDDYYYY(a.asOf),
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

    const columns = [
        { id: 'asOf', name: 'As of', render: (entry: AddressEntry) => entry.asOf || '---' },
        { id: 'type', name: 'Type', render: (entry: AddressEntry) => entry.type || '---' },
        { id: 'use', name: 'Use', render: (entry: AddressEntry) => entry.use || '---' },
        { id: 'address', name: 'Address 1', render: (entry: AddressEntry) => entry.address || '---' },
        { id: 'city', name: 'City', render: (entry: AddressEntry) => entry.city || '---' },
        { id: 'state', name: 'State', render: (entry: AddressEntry) => entry.state || '---' },
        { id: 'zipcode', name: 'ZIP Code', render: (entry: AddressEntry) => entry.zipcode || '---' },
        { id: 'county', name: 'County', render: (entry: AddressEntry) => entry.county || '---' },
        { id: 'country', name: 'Country', render: (entry: AddressEntry) => entry.country || '---' }
    ];

    return (
        <>
            <ReadOnlyRepeatingBlock<AddressEntry>
                id="addresses"
                title="Addresses"
                columns={columns}
                values={addresses}
            />
            {dirty && <p style={{ color: 'red' }}>You have unsaved changes</p>}
        </>
    );
};
