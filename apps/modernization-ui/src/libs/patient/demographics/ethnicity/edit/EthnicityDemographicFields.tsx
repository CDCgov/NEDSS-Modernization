import { useEffect } from 'react';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { validateRequiredRule } from 'validation/entry';
import { HasEthnicityDemographic, labels } from '../ethnicity';
import { EthnicityOptions } from './useEthnicityOptions';

const UNKNOWN = 'UNK';
const HISPANIC = '2135-2';

type EthnicityDemographicsFieldsProps = {
    form: UseFormReturn<HasEthnicityDemographic>;
    options: EthnicityOptions;
} & EntryFieldsProps;

const EthnicityDemographicsFields = ({
    form,
    options,
    sizing,
    orientation = 'horizontal'
}: EthnicityDemographicsFieldsProps) => {
    const selectedEthnicity = useWatch({ control: form.control, name: 'ethnicity.ethnicGroup' });

    useEffect(() => {
        form.setValue('ethnicity.unknownReason', undefined);
        form.setValue('ethnicity.detailed', []);
    }, [selectedEthnicity]);

    return (
        <>
            <Controller
                control={form.control}
                name="ethnicity.asOf"
                rules={{ ...validateRequiredRule(labels.asOf), ...validDateRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={labels.asOf}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        error={error?.message}
                        required
                        sizing={sizing}
                        aria-description="This field defaults to today's date and can be changed if needed."
                    />
                )}
            />
            <Controller
                control={form.control}
                name="ethnicity.ethnicGroup"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.ethnicity}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        value={value}
                        options={options.ethnicGroups}
                        sizing={sizing}
                    />
                )}
            />

            {selectedEthnicity?.value === HISPANIC && (
                <Controller
                    control={form.control}
                    name="ethnicity.detailed"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <MultiSelect
                            label={labels.detailed}
                            orientation={orientation}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            value={value}
                            options={options.detailedEthnicities}
                            sizing={sizing}
                        />
                    )}
                />
            )}
            {selectedEthnicity?.value === UNKNOWN && (
                <Controller
                    control={form.control}
                    name="ethnicity.unknownReason"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <SingleSelect
                            label={labels.unknownReason}
                            orientation={orientation}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            options={options.ethnicityUnknownReasons}
                            sizing={sizing}
                        />
                    )}
                />
            )}
        </>
    );
};

export { EthnicityDemographicsFields };
