import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext } from 'react-hook-form';
import { IdentificationDemographic } from './identifications';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { validateExtendedNameRule, validateRequiredRule } from 'validation/entry';
import { SingleSelect } from 'design-system/select';
import { useIdentificationCodedValues } from './useIdentificationCodedValues';
import { TextInputField } from 'design-system/input';

type IdentificationDemographicFieldsProps = {} & EntryFieldsProps;

const AS_OF_DATE_LABEL = 'Identification as of';
const TYPE_LABEL = 'Type';
const ASSIGNING_AUTHORITY_LABEL = 'Assigning authority';
const VALUE_LABEL = 'ID value';

const IdentificationDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium'
}: IdentificationDemographicFieldsProps) => {
    const { control } = useFormContext<IdentificationDemographic>();

    const coded = useIdentificationCodedValues();

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
                name="type"
                rules={{ ...validateRequiredRule(TYPE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={TYPE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="issuer"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={ASSIGNING_AUTHORITY_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.authorities}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="value"
                rules={{ ...validateExtendedNameRule(VALUE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={VALUE_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
        </section>
    );
};

export { IdentificationDemographicFields };
