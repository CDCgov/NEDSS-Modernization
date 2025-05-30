import { MergeAddress, MergePatient } from 'apps/deduplication/api/model/MergePatient';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { AddressDataTable } from './address-data-table/AddressDataTable';
import { AddressDetails } from './address-details/AddressDetails';

type Props = {
    mergePatients: MergePatient[];
};
export const AddressSelection = ({ mergePatients }: Props) => {
    const [selectedAddresses, setSelectedAddresses] = useState(
        new Map<string, MergeAddress | undefined>(mergePatients.map((p) => [p.personUid, undefined]))
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
                mergePatients={mergePatients}
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
                mergePatients={mergePatients}
                render={(p) => {
                    const address = selectedAddresses.get(p.personUid);
                    return address && <AddressDetails address={address} />;
                }}
            />
        </>
    );
};
