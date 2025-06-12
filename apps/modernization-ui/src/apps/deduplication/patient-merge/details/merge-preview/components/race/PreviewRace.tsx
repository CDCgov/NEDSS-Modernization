import React, { useState } from 'react';
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
    const detailedRaces: MergeRace[] = mergeCandidates
        .flatMap((mc) => mc.races)
        .filter((race) => selectedRaces.some((sr) => sr.personUid === race.personUid && sr.raceCode === race.raceCode));

    const initialRaceEntries: RaceEntry[] = detailedRaces.map((r, index) => ({
        ...r,
        id: `${r.personUid}-${r.raceCode}-${index}`,
        asOf: format(parseISO(r.asOf), 'MM/dd/yyyy')
    }));

    const [races] = useState(initialRaceEntries);
    const columns: Column<RaceEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: RaceEntry) => entry.asOf ?? '---',
            value: (entry: RaceEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'race',
            name: 'Race',
            render: (entry: RaceEntry) => entry.race ?? '---',
            value: (entry: RaceEntry) => entry.race,
            sortable: true
        },
        {
            id: 'detailedRace',
            name: 'Detailed Race',
            render: (entry: RaceEntry) => entry.detailedRace ?? '---',
            value: (entry: RaceEntry) => entry.detailedRace,
            sortable: true
        }
    ];

    return <MergePreviewTableCard<RaceEntry> id="race" title="Race" columns={columns} data={races} />;
};
