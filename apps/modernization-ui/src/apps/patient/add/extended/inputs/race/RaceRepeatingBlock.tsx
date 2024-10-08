import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { useConceptOptions } from 'options/concepts';
import { RaceEntryFields, RaceEntry, initial } from 'apps/patient/data/race';
import { DetailedRaceDisplay } from './DetailedRaceDisplay';
import { RaceEntryView } from './RaceEntryView';
import { ReactNode } from 'react';

const columns: Column<RaceEntry>[] = [
    { id: 'race-as-of', name: 'As of', render: (v) => v.asOf },
    { id: 'race-name', name: 'Race', render: (v) => v.race.name },
    {
        id: 'race-detailed',
        name: 'Detailed race',
        render: (v) => <DetailedRaceDisplay entry={v} />
    }
];

type Props = {
    id: string;
    values?: RaceEntry[];
    onChange: (data: RaceEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};

const RaceRepeatingBlock = ({ id, values, errors, onChange, isDirty }: Props) => {
    const categories = useConceptOptions('P_RACE_CAT', { lazy: false }).options;

    const renderForm = () => <RaceEntryFields categories={categories} />;
    const renderView = (entry: RaceEntry) => <RaceEntryView entry={entry} />;

    return (
        <RepeatingBlock<RaceEntry>
            id={id}
            title="Race"
            columns={columns}
            defaultValues={initial()}
            values={values}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};

export { RaceRepeatingBlock };
