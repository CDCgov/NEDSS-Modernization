import { RaceEntry } from 'apps/patient/data/race';

type Props = {
    entry: RaceEntry;
};
export const DetailedRaceDisplay = ({ entry }: Props) => entry.detailed?.map((v) => v.name).join(', ');
