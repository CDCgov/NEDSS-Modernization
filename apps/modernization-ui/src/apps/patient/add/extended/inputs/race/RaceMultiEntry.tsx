import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { DetailedRaceDisplay } from './DetailedRaceDisplay';
import { RaceEntryView } from './RaceEntryView';
import { RaceEntry } from 'apps/patient/data/entry';
import { RaceEntryFields } from 'apps/patient/data/race/RaceEntryFields';
import { RepeatingBlock } from 'design-system/entry/multi-value/RepeatingBlock';

const defaultValue: Partial<RaceEntry> = {
    asOf: internalizeDate(new Date()),
    race: undefined,
    detailed: undefined
};

type Props = {
    onChange: (data: RaceEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const RaceMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <RaceEntryFields />;
    const renderView = (entry: RaceEntry) => <RaceEntryView entry={entry} />;

    const columns: Column<RaceEntry>[] = [
        { id: 'raceAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'race', name: 'Race', render: (v) => v.race.name },
        {
            id: 'detailedRace',
            name: 'Detailed race',
            render: (v) => <DetailedRaceDisplay entry={v} />
        }
    ];
    return (
        <RepeatingBlock<RaceEntry>
            id="race"
            title="Race"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
        />
    );
};
