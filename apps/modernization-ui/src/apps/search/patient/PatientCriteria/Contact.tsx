import { Input } from 'components/FormInputs/Input';
import { Controller, useFormContext } from 'react-hook-form';
import { PhoneNumberInput } from 'components/FormInputs/PhoneNumberInput/PhoneNumberInput';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { SearchCriteria } from 'apps/search/criteria';
import { EntryFieldsProps } from 'design-system/entry';

export const Contact = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    return (
        <SearchCriteria>
            <Controller
                control={control}
                name="phoneNumber"
                render={({ field: { onChange, value, onBlur }, fieldState: { error } }) => (
                    <PhoneNumberInput
                        sizing={sizing}
                        orientation={orientation}
                        placeholder="333-444-555"
                        onChange={onChange}
                        onBlur={onBlur}
                        label="Phone number"
                        defaultValue={value}
                        id="homePhone"
                        error={error?.message}
                        mask="___-___-____"
                        pattern="\d{3}-\d{3}-\d{4}"
                    />
                )}
            />
            <Controller
                control={control}
                name="email"
                rules={{
                    pattern: {
                        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                        message: 'Please enter a valid email address (example: youremail@website.com)'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Email"
                        htmlFor={name}
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                    />
                )}
            />
        </SearchCriteria>
    );
};
