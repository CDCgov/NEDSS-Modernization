import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { useRaceCategoryOptions } from 'options/race/useRaceCategoryOptions';
import { RaceEntryFields, RaceEntry, initial } from 'apps/patient/data/race';
import { DetailedRaceDisplay } from './DetailedRaceDisplay';
import { RaceEntryView } from './RaceEntryView';
import { ReactNode, useState } from 'react';
import { categoryValidator } from './categoryValidator';

const columns: Column<RaceEntry>[] = [
    { id: 'race-as-of', name: 'As of', render: (v) => v.asOf },
    { id: 'race-name', name: 'Race', render: (v) => v.race?.name },
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

const RaceRepeatingBlock = ({ id, values = [], errors, onChange, isDirty }: Props) => {
    const { categories } = useRaceCategoryOptions();
    const [isDirtyEdit, setDirtyEdit] = useState<boolean>(false);

    const renderForm = () => (
        <RaceEntryFields categories={categories} categoryValidator={categoryValidator(values)} isDirty={isDirtyEdit} />
    );
    const renderView = (entry: RaceEntry) => <RaceEntryView entry={entry} />;

    const isDirtyHandler = (isDirt: boolean) => {
        setDirtyEdit(isDirt);
        isDirty(isDirt);
    };

    return (
        <RepeatingBlock<RaceEntry>
            id={id}
            title="Race"
            columns={columns}
            defaultValues={initial()}
            values={values}
            onChange={onChange}
            isDirty={isDirtyHandler}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};

export { RaceRepeatingBlock };
