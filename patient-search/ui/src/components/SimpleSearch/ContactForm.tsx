import { Grid } from '@trussworks/react-uswds';
import { Input } from '../FormInputs/Input';
import { Controller } from 'react-hook-form';

export const ContactForm = ({ control }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="phoneNumber"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            type="text"
                            label="Phone number"
                            defaultValue={value}
                            htmlFor="phoneNumber"
                            id="phoneNumber"
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="email"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="Email"
                            htmlFor="email"
                            id="email"
                        />
                    )}
                />
            </Grid>
        </>
    );
};
