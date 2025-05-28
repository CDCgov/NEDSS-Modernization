import { PatientData, PatientIdentification } from 'apps/deduplication/api/model/PatientData';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { IdentificationDataTable } from './identification-data-table/IdentificationDataTable';
import { IdentificationDetails } from './identification-details/IdentificationDetails';

type Props = {
    patientData: PatientData[];
};
export const IdentificationSelection = ({ patientData }: Props) => {
    const [selectedIdentification, setSelectedIdentification] = useState(
        new Map<string, PatientIdentification | undefined>(patientData.map((p) => [p.personUid, undefined]))
    );

    const handleViewIdentification = (personUid: string, identification: PatientIdentification) => {
        const map = new Map(selectedIdentification);
        if (map.get(personUid) === identification) {
            map.set(personUid, undefined);
        } else {
            map.set(personUid, identification);
        }
        setSelectedIdentification(map);
    };

    return (
        <>
            <Section
                title="IDENTIFICATION"
                patientData={patientData}
                render={(p) => (
                    <IdentificationDataTable
                        patientData={p}
                        onViewIdentification={(i) => handleViewIdentification(p.personUid, i)}
                        selectedIdentification={selectedIdentification.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-identification"
                patientData={patientData}
                render={(p) => {
                    const identification = selectedIdentification.get(p.personUid);
                    return identification && <IdentificationDetails identification={identification} />;
                }}
            />
        </>
    );
};
