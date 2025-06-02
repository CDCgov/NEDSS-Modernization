import { MergeCandidate, MergeName } from 'apps/deduplication/api/model/MergeCandidate';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { NameDataTable } from './name-data-table/NameDataTable';
import { NameDetails } from './name-details/NameDetails';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const NameSelection = ({ mergeCandidates }: Props) => {
    const [selectedNames, setSelectedNames] = useState(
        new Map<string, MergeName | undefined>(mergeCandidates.map((p) => [p.personUid, undefined]))
    );

    const handleNameSelection = (personUid: string, name: MergeName) => {
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
                mergeCandidates={mergeCandidates}
                render={(p) => (
                    <NameDataTable
                        patientData={p}
                        onViewName={(n) => handleNameSelection(p.personUid, n)}
                        selectedName={selectedNames.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-name"
                mergeCandidates={mergeCandidates}
                render={(p) => {
                    const name = selectedNames.get(p.personUid);
                    return name && <NameDetails name={name} />;
                }}
            />
        </>
    );
};
