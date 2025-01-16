import { RaceEntry } from 'apps/patient/data/race';
import { NoData } from 'components/NoData';

type Props = {
    entry: RaceEntry;
};
export const DetailedRaceDisplay = ({ entry }: Props) =>
    entry.detailed?.map((v) => v.name).join(', ') || <NoData display="dashes" />;
