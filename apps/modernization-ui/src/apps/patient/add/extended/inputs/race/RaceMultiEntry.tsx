import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';
import { RaceEntryFields } from 'apps/patient/profile/race/RaceEntryFields';
import { useRaceCodedValues } from 'coded/race';
import { internalizeDate } from 'date';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { Column } from 'design-system/table';
import { DetailedRaceDisplay } from './DetailedRaceDisplay';
import { RaceEntryView } from './RaceEntryView';

const defaultValue: RaceEntry = {
    asOf: internalizeDate(new Date()),
    category: '',
    detailed: []
};

type Props = {
    onChange: (data: RaceEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const RaceMultiEntry = ({ onChange, isDirty }: Props) => {
    const categories = useRaceCodedValues();
    const renderForm = () => <RaceEntryFields />;
    const renderView = (entry: RaceEntry) => <RaceEntryView entry={entry} />;

    const columns: Column<RaceEntry>[] = [
        { id: 'raceAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'race', name: 'Race', render: (v) => categories.find((c) => c.value === v.category)?.name },
        {
            id: 'detailedRace',
            name: 'Detailed race',
            render: (v) => <DetailedRaceDisplay entry={v} />
        }
    ];
    return (
        <MultiValueEntry<RaceEntry>
            id="section-Race"
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
