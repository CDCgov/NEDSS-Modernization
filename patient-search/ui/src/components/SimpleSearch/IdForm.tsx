import { Grid } from '@trussworks/react-uswds';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import { Controller } from 'react-hook-form';

export const IDForm = ({ control }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="idType"
                    render={({ field: { onChange } }) => (
                        <SelectInput onChange={onChange} htmlFor={'idType'} label="ID type" options={[]} />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="idNumber"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="ID number"
                            htmlFor="idNumber"
                            id="idNumber"
                        />
                    )}
                />
            </Grid>
        </>
    );
};
