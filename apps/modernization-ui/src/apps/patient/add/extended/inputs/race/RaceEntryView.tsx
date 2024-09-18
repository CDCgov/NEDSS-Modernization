import { RaceEntry } from 'apps/patient/data/entry';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: RaceEntry;
};
export const RaceEntryView = ({ entry }: Props) => {
    return (
        <>
            <DataDisplay title="As of" value={entry.asOf} required />
            <DataDisplay title="Race" value={entry.race.name} required />
            <DataDisplay title="Detailed race" value={entry.detailed?.map((d) => d.name).join(', ')} />
        </>
    );
};
