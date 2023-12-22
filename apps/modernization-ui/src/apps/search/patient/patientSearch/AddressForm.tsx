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
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            type="text"
                            label="Street address"
                            defaultValue={value}
                            htmlFor="address"
                            id="address"
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="city"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="City"
                            htmlFor="city"
                            id="city"
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
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'state'}
                                    label="State"
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
