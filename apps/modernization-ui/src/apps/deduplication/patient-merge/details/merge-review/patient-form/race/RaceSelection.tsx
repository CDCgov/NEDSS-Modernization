import { MergePatient, MergeRace } from 'apps/deduplication/api/model/MergePatient';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { RaceDataTable } from './race-data-table/RaceDataTable';
import { RaceDetails } from './race-details/RaceDetails';

type Props = {
    patientData: MergePatient[];
};
export const RaceSelection = ({ patientData }: Props) => {
    const [selectedRace, setSelectedRace] = useState(
        new Map<string, MergeRace | undefined>(patientData.map((p) => [p.personUid, undefined]))
    );

    const handleViewRace = (personUid: string, race: MergeRace) => {
        const map = new Map(selectedRace);
        if (map.get(personUid) === race) {
            map.set(personUid, undefined);
        } else {
            map.set(personUid, race);
        }
        setSelectedRace(map);
    };

    return (
        <>
            <Section
                title="RACE"
                patientData={patientData}
                render={(p) => (
                    <RaceDataTable
                        patientData={p}
                        onViewRace={(r) => handleViewRace(p.personUid, r)}
                        selectedRace={selectedRace.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-race"
                patientData={patientData}
                render={(p) => {
                    const race = selectedRace.get(p.personUid);
                    return race && <RaceDetails race={race} />;
                }}
            />
        </>
    );
};
