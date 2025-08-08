import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { validateRequiredRule } from 'validation/entry';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { RaceCategoryValidator, RaceDemographic, labels } from '../race';
import { RaceOptions } from './useRaceOptions';

type RaceDemographicFieldsProps = {
    options: RaceOptions;
    categoryValidator: RaceCategoryValidator;
    entry?: RaceDemographic;
} & EntryFieldsProps;

const RaceDemographicFields = ({
    options,
    categoryValidator,
    entry,
    orientation = 'horizontal',
    sizing
}: RaceDemographicFieldsProps) => {
    const { control, setValue } = useFormContext<RaceDemographic>();

    const id = useWatch({ control, name: 'id' });

    const selectedCategory = useWatch({ control, name: 'race', defaultValue: entry?.race });

    useEffect(() => {
        if (selectedCategory?.value !== entry?.race?.value) {
            //  when the category differs from the entry, clear the details
            setValue('detailed', []);
        }
        options.selected(selectedCategory);
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
                        aria-description="This field defaults to today's date and can be changed if needed."
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
                        options={options.categories}
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
                        disabled={options.details.length === 0}
                        options={options.details}
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
export type { RaceDemographicFieldsProps };
