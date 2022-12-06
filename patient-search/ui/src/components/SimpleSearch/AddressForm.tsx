import { Grid } from '@trussworks/react-uswds';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { stateList } from '../../constant/states';

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
                <Controller
                    control={control}
                    name="state"
                    render={({ field: { onChange } }) => (
                        <SelectInput onChange={onChange} htmlFor={'state'} label="State" options={stateList} />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="zip"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="Zip code"
                            htmlFor="zip"
                            id="zip"
                        />
                    )}
                />
            </Grid>
        </>
    );
};
