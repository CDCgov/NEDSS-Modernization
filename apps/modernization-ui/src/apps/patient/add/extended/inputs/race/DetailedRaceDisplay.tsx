import { RaceEntry } from 'apps/patient/data/entry';

type Props = {
    entry: RaceEntry;
};
export const DetailedRaceDisplay = ({ entry }: Props) => {
    return <div>{entry.detailed?.map((v) => v.name).join(', ')}</div>;
};
