import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { validateRequiredRule } from 'validation/entry';
import { RaceCategoryValidator, RaceEntry } from './entry';
import { useDetailedRaceOptions } from 'options/race';
import { EntryFieldsProps } from 'design-system/entry';

const RACE_AS_OF_LABEL = 'Race as of';

type RaceEntryFieldsProps = {
    categories: Selectable[];
    categoryValidator: RaceCategoryValidator;
    entry?: RaceEntry;
} & EntryFieldsProps;

const RaceEntryFields = ({
    categories,
    categoryValidator,
    entry,
    orientation = 'horizontal',
    sizing = 'medium'
}: RaceEntryFieldsProps) => {
    const { control, setValue } = useFormContext<RaceEntry>();

    const id = useWatch({ control, name: 'id' });

    const selectedCategory = useWatch({ control, name: 'race.value', defaultValue: entry?.race?.value });
    const detailedRaces = useDetailedRaceOptions(selectedCategory);

    useEffect(() => {
        if (selectedCategory !== entry?.race?.value) {
            //  when the category differs from the entry, clear the details
            setValue('detailed', []);
        }
    }, [selectedCategory]);

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validateRequiredRule(RACE_AS_OF_LABEL), ...validDateRule(RACE_AS_OF_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={`races-${name}`}
                        required
                        label={RACE_AS_OF_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="race"
                rules={{
                    validate: (category) => categoryValidator(id, category)
                }}
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Race"
                        orientation={orientation}
                        required
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        id={`races-category-${name}`}
                        name={name}
                        options={categories}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />

            <Controller
                control={control}
                name="detailed"
                render={({ field: { onChange, value, name } }) => (
                    <MultiSelect
                        label="Detailed race"
                        orientation={orientation}
                        id={`races-detailed-${name}`}
                        name={name}
                        disabled={!selectedCategory}
                        value={value}
                        onChange={onChange}
                        options={detailedRaces}
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};

export { RaceEntryFields };

export type { RaceEntryFieldsProps };
