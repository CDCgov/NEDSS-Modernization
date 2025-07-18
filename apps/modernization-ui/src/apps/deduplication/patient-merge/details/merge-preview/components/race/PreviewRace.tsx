import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { RaceId } from '../../../merge-review/model/PatientMergeForm';
import { MergePreviewTableCard } from '../shared/preview-card-table/MergePreviewTableCard';
import { Column } from 'design-system/table';
import { format, parseISO, isAfter } from 'date-fns';

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
    const raceMap = new Map<string, { latestDate: string; detailed: Set<string> }>();

    mergeCandidates
        .flatMap((c) => c.races)
        .filter((r) => selectedRaces.some((sel) => sel.personUid === r.personUid && sel.raceCode === r.raceCode))
        .forEach((r) => {
            const key = r.race;
            const existing = raceMap.get(key);
            const isNewer = !existing || isAfter(parseISO(r.asOf), parseISO(existing.latestDate));
            const details = r.detailedRaces && r.detailedRaces !== r.race ? [r.detailedRaces] : [];

            raceMap.set(key, {
                latestDate: isNewer ? r.asOf : (existing?.latestDate ?? r.asOf),
                detailed: new Set([...(existing?.detailed ?? []), ...details])
            });
        });

    const races: (RaceEntry & { latestDate: string })[] = [...raceMap.entries()]
        .map(([race, { latestDate, detailed }], i) => ({
            id: `race-${i}`,
            race,
            asOf: format(parseISO(latestDate), 'MM/dd/yyyy'),
            latestDate,
            detailedRace: detailed.size ? Array.from(detailed).join(', ') : undefined
        }))
        .sort((a, b) => (parseISO(a.latestDate) > parseISO(b.latestDate) ? -1 : 1));

    const columns: Column<RaceEntry>[] = [
        { id: 'asOf', name: 'As of', value: (e) => e.asOf, sortable: true },
        { id: 'race', name: 'Race', value: (e) => e.race, sortable: true },
        { id: 'detailedRace', name: 'Detailed Race', value: (e) => e.detailedRace ?? '---', sortable: true }
    ];

    return <MergePreviewTableCard<RaceEntry> id="race" title="Race" columns={columns} data={races} />;
};
