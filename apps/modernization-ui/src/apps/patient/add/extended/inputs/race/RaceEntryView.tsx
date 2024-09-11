import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';
import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: RaceEntry;
};
export const RaceEntryView = ({ entry }: Props) => {
    const categories = useRaceCodedValues();
    const detailedOptions = useDetailedRaceCodedValues(entry.category);

    return (
        <>
            <DataDisplay title="As of" value={entry.asOf} required />
            <DataDisplay title="Race" value={categories.find((e) => e.value === entry.category)?.name} required />
            <DataDisplay
                title="Detailed race"
                value={entry.detailed?.map((d) => detailedOptions.find((e) => e.value === d)?.name).join(', ')}
            />
        </>
    );
};
