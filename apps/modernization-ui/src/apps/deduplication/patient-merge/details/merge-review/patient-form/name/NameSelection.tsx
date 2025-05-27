import { PatientData, PatientName } from 'apps/deduplication/api/model/PatientData';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { NameDataTable } from './name-data/name-data-table/NameDataTable';
import { NameDetails } from './name-data/name-details/NameDetails';

type Props = {
    patientData: PatientData[];
};
export const NameSelection = ({ patientData }: Props) => {
    const [selectedNames, setSelectedNames] = useState(
        new Map<string, PatientName | undefined>(patientData.map((p) => [p.personUid, undefined]))
    );

    const handleNameSelection = (personUid: string, name: PatientName) => {
        const map = new Map(selectedNames);
        if (map.get(personUid) === name) {
            map.set(personUid, undefined);
        } else {
            map.set(personUid, name);
        }
        setSelectedNames(map);
    };

    return (
        <>
            <Section
                title="NAME"
                patientData={patientData}
                render={(p) => (
                    <NameDataTable
                        patientData={p}
                        onSelectName={(n) => handleNameSelection(p.personUid, n)}
                        selectedName={selectedNames.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-name"
                patientData={patientData}
                render={(p) => {
                    const name = selectedNames.get(p.personUid);
                    return name && <NameDetails name={name} />;
                }}
            />
        </>
    );
};
