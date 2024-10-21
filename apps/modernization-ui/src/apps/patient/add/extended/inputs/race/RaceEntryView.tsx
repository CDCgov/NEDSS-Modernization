import { RaceEntry } from 'apps/patient/data/race';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: RaceEntry;
};
export const RaceEntryView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} required />
            <ValueView title="Race" value={entry.race?.name} required />
            <ValueView title="Detailed race" value={entry.detailed?.map((d) => d.name).join(', ')} />
        </>
    );
};
