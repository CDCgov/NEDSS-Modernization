import React, { useEffect, useState } from 'react';
import { MergeCandidate, MergeRace } from '../../../../../api/model/MergeCandidate';
import { RaceId } from '../../../merge-review/model/PatientMergeForm';
import { MergePreviewTableCard } from '../Card/MergePreviewTableCard';
import { Column } from 'design-system/table/DataTable';
import { format, parseISO } from 'date-fns';

type RaceEntry = {
    id: string;
    asOf: string;
    race: string;
    detailedRace?: string;
};

type PreviewRaceProps = {
    mergeCandidates: MergeCandidate[];
    selectedRaces: RaceId[];
};

export const PreviewRace = ({ selectedRaces, mergeCandidates }: PreviewRaceProps) => {
    const detailedRaces: MergeRace[] = selectedRaces.flatMap(({ personUid, raceCode }) => {
        for (const candidate of mergeCandidates) {
            if (candidate.personUid === personUid) {
                const found = candidate.races.find((r) => r.raceCode === raceCode);
                if (found) return [found];
            }
        }
        return [];
    });

    const initialRaceEntries: RaceEntry[] = detailedRaces.map((r, index) => ({
        id: `${r.personUid}-${r.raceCode}-${index}`,
        asOf: format(parseISO(r.asOf), 'MM/dd/yyyy'),
        race: r.race,
        detailedRace: r.detailedRaces
    }));

    const [races] = useState(initialRaceEntries);
    const [dirty, setDirty] = useState(false);

    const deepEqual = (a: RaceEntry[], b: RaceEntry[]) => {
        if (a.length !== b.length) return false;
        return a.every((itemA, index) => {
            const itemB = b[index];
            return Object.keys(itemA).every((key) => (itemA as any)[key] === (itemB as any)[key]);
        });
    };

    useEffect(() => {
        setDirty(!deepEqual(races, initialRaceEntries));
    }, [races, initialRaceEntries]);

    const columns: Column<RaceEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: RaceEntry) => entry.asOf || '---',
            value: (entry: RaceEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'race',
            name: 'Race',
            render: (entry: RaceEntry) => entry.race || '---',
            value: (entry: RaceEntry) => entry.race,
            sortable: true
        },
        {
            id: 'detailedRace',
            name: 'Detailed Race',
            render: (entry: RaceEntry) => entry.detailedRace || '---',
            value: (entry: RaceEntry) => entry.detailedRace,
            sortable: true
        }
    ];

    return (
        <>
            <MergePreviewTableCard<RaceEntry> id="race" title="Race" columns={columns} data={races} />
            {dirty && <p></p>}
        </>
    );
};
