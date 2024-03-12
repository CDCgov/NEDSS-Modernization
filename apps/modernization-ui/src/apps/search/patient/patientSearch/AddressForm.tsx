import { Grid } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';

export const AddressForm = ({ control }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="address"
                    render={({ field: { onChange, value, name } }) => (
                        <Input
                            onChange={onChange}
                            type="text"
                            label="Street address"
                            defaultValue={value}
                            htmlFor={name}
                            id={name}
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="city"
                    render={({ field: { onChange, value, name } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="City"
                            htmlFor={name}
                            id={name}
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <SearchCriteriaContext.Consumer>
                    {({ searchCriteria }) => (
                        <Controller
                            control={control}
                            name="state"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    label="State"
                                    htmlFor={name}
                                    id={name}
                                    options={searchCriteria.states}
                                />
                            )}
                        />
                    )}
                </SearchCriteriaContext.Consumer>
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="zip"
                    rules={{
                        pattern: {
                            value: /^\d{1,5}(?:[-\s]\d{1,4})?$/,
                            message: 'Please enter a valid ZIP code (XXXXX) using only numeric characters (0-9).'
                        }
                    }}
                    render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                        <Input
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="Zip code"
                            htmlFor={name}
                            id={name}
                            error={error?.message}
                        />
                    )}
                />
            </Grid>
        </>
    );
};
