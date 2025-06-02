import { MergeCandidate, MergeIdentification } from 'apps/deduplication/api/model/MergeCandidate';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { IdentificationDataTable } from './identification-data-table/IdentificationDataTable';
import { IdentificationDetails } from './identification-details/IdentificationDetails';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const IdentificationSelection = ({ mergeCandidates }: Props) => {
    const [selectedIdentification, setSelectedIdentification] = useState(
        new Map<string, MergeIdentification | undefined>(mergeCandidates.map((p) => [p.personUid, undefined]))
    );

    const handleViewIdentification = (personUid: string, identification: MergeIdentification) => {
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
                mergeCandidates={mergeCandidates}
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
                mergeCandidates={mergeCandidates}
                render={(p) => {
                    const identification = selectedIdentification.get(p.personUid);
                    return identification && <IdentificationDetails identification={identification} />;
                }}
            />
        </>
    );
};
