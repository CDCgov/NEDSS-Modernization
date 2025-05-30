import { MergePatient, MergeRace } from 'apps/deduplication/api/model/MergePatient';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { RaceDataTable } from './race-data-table/RaceDataTable';
import { RaceDetails } from './race-details/RaceDetails';

type Props = {
    mergePatients: MergePatient[];
};
export const RaceSelection = ({ mergePatients }: Props) => {
    const [selectedRace, setSelectedRace] = useState(
        new Map<string, MergeRace | undefined>(mergePatients.map((p) => [p.personUid, undefined]))
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
                mergePatients={mergePatients}
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
                mergePatients={mergePatients}
                render={(p) => {
                    const race = selectedRace.get(p.personUid);
                    return race && <RaceDetails race={race} />;
                }}
            />
        </>
    );
};
