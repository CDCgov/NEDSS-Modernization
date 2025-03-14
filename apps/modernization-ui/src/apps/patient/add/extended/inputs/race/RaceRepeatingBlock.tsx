import { ReactNode } from 'react';
import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { useRaceCategoryOptions } from 'options/race';
import { RaceEntryFields, RaceEntry, initial } from 'apps/patient/data/race';
import { RaceEntryView } from './RaceEntryView';
import { categoryValidator } from './categoryValidator';
import { Sizing } from 'design-system/field';

const columns: Column<RaceEntry>[] = [
    { id: 'race-as-of', name: 'As of', render: (v) => v.asOf },
    { id: 'race-name', name: 'Race', render: (v) => v.race?.name },
    {
        id: 'race-detailed',
        name: 'Detailed race',
        render: (v) => v.detailed?.map((v) => v.name).join(', ')
    }
];

type Props = {
    id: string;
    values?: RaceEntry[];
    onChange: (data: RaceEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
    sizing?: Sizing;
};

const RaceRepeatingBlock = ({ id, values = [], errors, onChange, isDirty, sizing }: Props) => {
    const categories = useRaceCategoryOptions();

    const renderForm = (entry?: RaceEntry) => (
        <RaceEntryFields
            categories={categories}
            categoryValidator={categoryValidator(values)}
            entry={entry}
            sizing={sizing}
        />
    );
    const renderView = (entry: RaceEntry) => <RaceEntryView entry={entry} sizing={sizing} />;

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
            sizing={sizing}
        />
    );
};

export { RaceRepeatingBlock };
