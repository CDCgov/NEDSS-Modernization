import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext } from 'react-hook-form';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { PhoneEmailDemographic } from './phoneEmails';
import { usePhoneEmailCodedValues } from './usePhoneEmailCodedValues';
import { TextAreaField } from 'design-system/input/text/TextAreaField';

type PhoneEmailDemographicFieldsProps = {} & EntryFieldsProps;

const AS_OF_DATE_LABEL = 'Phone & email as of';
const TYPE_LABEL = 'Type';
const USE_LABEL = 'Use';
const COUNTRY_CODE_LABEL = 'Country label';
const PHONE_NUMBER_LABEL = 'Phone number';
const EXTENSION_LABEL = 'Extension';
const EMAIL_LABEL = 'Email';
const URL_LABEL = 'URL';
const COMMENTS_LABEL = 'Phone & email comments';

const PhoneEmailDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium'
}: PhoneEmailDemographicFieldsProps) => {
    const { control } = useFormContext<PhoneEmailDemographic>();

    const coded = usePhoneEmailCodedValues();

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
                name="use"
                rules={{ ...validateRequiredRule(USE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={USE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        options={coded.uses}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="countryCode"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={COUNTRY_CODE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={`name-${name}`}
                        name={`name-${name}`}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="phoneNumber"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={PHONE_NUMBER_LABEL}
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
            <Controller
                control={control}
                name="extension"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={EXTENSION_LABEL}
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
            <Controller
                control={control}
                name="email"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={EMAIL_LABEL}
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
            <Controller
                control={control}
                name="url"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={URL_LABEL}
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
            <Controller
                control={control}
                name="comment"
                rules={{ ...maxLengthRule(2000, COMMENTS_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextAreaField
                        label={COMMENTS_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
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

export { PhoneEmailDemographicFields };
