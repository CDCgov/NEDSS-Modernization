import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { validateRequiredRule } from 'validation/entry';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { RaceDemographic } from './race';
import { useRaceCodedValues } from './useRaceCodedValues';
import { useDetailedRaceOptions } from 'options/race';

type RaceDemographicFieldsProps = {} & EntryFieldsProps;

const AS_OF_DATE_LABEL = 'Race as of';
const RACE_LABEL = 'Race';
const DETAILED_LABEL = 'Detailed race';

const RaceDemographicFields = ({ orientation = 'horizontal', sizing = 'medium' }: RaceDemographicFieldsProps) => {
    const { control } = useFormContext<RaceDemographic>();

    const coded = useRaceCodedValues();
    const selectedCategory = useWatch({ control, name: 'race' });
    const detailed = useDetailedRaceOptions(selectedCategory.value);

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validDateRule(AS_OF_DATE_LABEL), ...validateRequiredRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={AS_OF_DATE_LABEL}
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
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={RACE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.race}
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
                        label={DETAILED_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={detailed}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};

export { RaceDemographicFields };
