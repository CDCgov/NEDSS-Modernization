import { MergeAddress, MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { AddressDataTable } from './address-data-table/AddressDataTable';
import { AddressDetails } from './address-details/AddressDetails';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const AddressSelection = ({ mergeCandidates }: Props) => {
    const [selectedAddresses, setSelectedAddresses] = useState(
        new Map<string, MergeAddress | undefined>(mergeCandidates.map((p) => [p.personUid, undefined]))
    );

    const handleViewAddress = (personUid: string, address: MergeAddress) => {
        const map = new Map(selectedAddresses);
        if (map.get(personUid) === address) {
            map.set(personUid, undefined);
        } else {
            map.set(personUid, address);
        }
        setSelectedAddresses(map);
    };

    return (
        <>
            <Section
                title="ADDRESS"
                mergeCandidates={mergeCandidates}
                render={(p) => (
                    <AddressDataTable
                        patientData={p}
                        onViewAddress={(n) => handleViewAddress(p.personUid, n)}
                        selectedAddress={selectedAddresses.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-address"
                mergeCandidates={mergeCandidates}
                render={(p) => {
                    const address = selectedAddresses.get(p.personUid);
                    return address && <AddressDetails address={address} />;
                }}
            />
        </>
    );
};
