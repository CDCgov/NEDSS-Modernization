import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { SearchCriteriaContext, SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import { SingleSelect } from 'design-system/select';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { OperatorInput } from 'design-system/input/operator';

export const Address = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    return (
        <SearchCriteriaProvider>
            <SearchCriteria>
                <Controller
                    control={control}
                    name="location.street"
                    render={({ field: { onChange, value, name } }) => (
                        <OperatorInput
                            id={name}
                            value={value}
                            label="Street address"
                            sizing="compact"
                            operationMode="alpha"
                            onChange={onChange}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="location.city"
                    render={({ field: { onChange, value, name } }) => (
                        <OperatorInput
                            id={name}
                            value={value}
                            label="City"
                            sizing="compact"
                            operationMode="alpha"
                            onChange={onChange}
                        />
                    )}
                />
                <SearchCriteriaContext.Consumer>
                    {({ searchCriteria }) => (
                        <Controller
                            control={control}
                            name="state"
                            render={({ field: { onChange, value, name } }) => (
                                <SingleSelect
                                    value={value}
                                    onChange={onChange}
                                    label="State"
                                    id={name}
                                    sizing="compact"
                                    options={searchCriteria.states.map((state) => ({
                                        name: state.name,
                                        label: state.name,
                                        value: state.value
                                    }))}
                                />
                            )}
                        />
                    )}
                </SearchCriteriaContext.Consumer>
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
                            sizing="compact"
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
        </SearchCriteriaProvider>
    );
};
