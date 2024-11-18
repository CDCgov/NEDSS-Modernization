import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { useDetailedRaceCodedValues } from 'coded/race';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Selectable } from 'options';
import { validateRequiredRule } from 'validation/entry';
import { RaceCategoryValidator, RaceEntry } from './entry';

type RaceEntryFieldsProps = {
    categories: Selectable[];
    categoryValidator: RaceCategoryValidator;
    entry?: RaceEntry;
};

const RaceEntryFields = ({ categories, categoryValidator, entry }: RaceEntryFieldsProps) => {
    const { control, setValue } = useFormContext<RaceEntry>();

    const id = useWatch({ control, name: 'id' });

    const selectedCategory = useWatch({ control, name: 'race.value', defaultValue: entry?.race?.value });
    const detailedRaces = useDetailedRaceCodedValues(selectedCategory);

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
                rules={{ ...validateRequiredRule('As of date') }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Race as of"
                        onBlur={onBlur}
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        name={`races-${name}`}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
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
                        orientation="horizontal"
                        required
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        id={`races-category-${name}`}
                        name={name}
                        options={categories}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="detailed"
                render={({ field: { onChange, value, name } }) => (
                    <MultiSelect
                        label="Detailed race"
                        orientation="horizontal"
                        id={`races-detailed-${name}`}
                        name={name}
                        disabled={!selectedCategory}
                        value={value}
                        onChange={onChange}
                        options={detailedRaces}
                    />
                )}
            />
        </section>
    );
};

export { RaceEntryFields };

export type { RaceEntryFieldsProps };
