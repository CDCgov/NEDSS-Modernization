import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { TextCriteriaField } from 'design-system/input/text/criteria';
import { EntryFieldsProps } from 'design-system/entry';
import { useStateCodedValues } from 'apps/patient/data/state/useStateCodedValues';

export const Address = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <SearchCriteria>
            <Controller
                control={control}
                name="location.street"
                render={({ field: { onChange, value, name } }) => (
                    <TextCriteriaField
                        id={name}
                        value={value}
                        label="Street address"
                        sizing={sizing}
                        orientation={orientation}
                        operationMode="alpha"
                        onChange={onChange}
                    />
                )}
            />
            <Controller
                control={control}
                name="location.city"
                render={({ field: { onChange, value, name } }) => (
                    <TextCriteriaField
                        id={name}
                        value={value}
                        label="City"
                        sizing={sizing}
                        orientation={orientation}
                        operationMode="alpha"
                        onChange={onChange}
                    />
                )}
            />
            <Controller
                control={control}
                name="state"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        label="State"
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        options={useStateCodedValues({ lazy: false }).options}
                    />
                )}
            />
            <Controller
                control={control}
                name="zip"
                rules={{
                    pattern: {
                        value: /^\d{1,5}(?:[-\s]\d{1,4})?$/,
                        message:
                            'Please enter a valid ZIP code (XXXXX or XXXXX-XXXX ) using only numeric characters (0-9).'
                    }
                }}
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <Input
                        sizing={sizing}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value?.toString()}
                        type="text"
                        label="Zip code"
                        htmlFor={name}
                        id={name}
                        mask="_____-____"
                        pattern="^\d{1,5}(?:[-\s]\d{1,4})?$"
                        error={error?.message}
                    />
                )}
            />
        </SearchCriteria>
    );
};
