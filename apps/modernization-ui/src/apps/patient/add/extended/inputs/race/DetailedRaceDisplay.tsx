import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';
import { useDetailedRaceCodedValues } from 'coded/race';

type Props = {
    entry: RaceEntry;
};
export const DetailedRaceDisplay = ({ entry }: Props) => {
    const options = useDetailedRaceCodedValues(entry.category);
    return <div>{entry.detailed?.map((v) => options.find((o) => o.value === v)?.name).join(', ')}</div>;
};
