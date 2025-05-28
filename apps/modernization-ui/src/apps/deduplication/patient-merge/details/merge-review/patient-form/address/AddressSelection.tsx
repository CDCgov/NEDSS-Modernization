import { PatientAddress, PatientData } from 'apps/deduplication/api/model/PatientData';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { AddressDataTable } from './address-data-table/AddressDataTable';
import { AddressDetails } from './address-details/AddressDetails';

type Props = {
    patientData: PatientData[];
};
export const AddressSelection = ({ patientData }: Props) => {
    const [selectedAddresses, setSelectedAddresses] = useState(
        new Map<string, PatientAddress | undefined>(patientData.map((p) => [p.personUid, undefined]))
    );

    const handleViewAddress = (personUid: string, address: PatientAddress) => {
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
                patientData={patientData}
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
                patientData={patientData}
                render={(p) => {
                    const address = selectedAddresses.get(p.personUid);
                    return address && <AddressDetails address={address} />;
                }}
            />
        </>
    );
};
