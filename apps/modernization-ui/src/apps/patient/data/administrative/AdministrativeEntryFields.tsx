import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { TextAreaField } from 'design-system/input/text/TextAreaField';
import { AdministrativeEntry } from 'apps/patient/data/entry';

const AS_OF_DATE_LABEL = 'Information as of date';
const COMMENTS_LABEL = 'Comments';

export const AdministrativeEntryFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control } = useFormContext<{ administrative: AdministrativeEntry }>();

    return (
        <section>
            <Controller
                control={control}
                name="administrative.asOf"
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
                        sizing={sizing}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="administrative.comment"
                rules={maxLengthRule(2000, COMMENTS_LABEL)}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextAreaField
                        label={COMMENTS_LABEL}
                        orientation={orientation}
                        sizing={sizing}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        name={name}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
        </section>
    );
};
