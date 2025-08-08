import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { Selectable } from 'options';
import { useDetailedRaceOptions } from 'options/race';
import { EntryFieldsProps } from 'design-system/entry';
import { validateRequiredRule } from 'validation/entry';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { RaceCategoryValidator, RaceDemographic, labels } from './race';

type RaceDemographicFieldsProps = {
    categories: Selectable[];
    categoryValidator: RaceCategoryValidator;
    entry?: RaceDemographic;
} & EntryFieldsProps;

const RaceDemographicFields = ({
    orientation = 'horizontal',
    sizing,
    categories,
    categoryValidator,
    entry
}: RaceDemographicFieldsProps) => {
    const { control, setValue } = useFormContext<RaceDemographic>();

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
        <>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validDateRule(labels.asOf), ...validateRequiredRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={labels.asOf}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        orientation={orientation}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />

            <Controller
                control={control}
                name="race"
                rules={{
                    ...validateRequiredRule(labels.race),
                    validate: (category) => categoryValidator(id, category)
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.race}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
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
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <MultiSelect
                        label={labels.detailed}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={detailedRaces}
                        error={error?.message}
                        sizing={sizing}
                        helperText="Use Crtl to select more than one"
                    />
                )}
            />
        </>
    );
};

export { RaceDemographicFields };
