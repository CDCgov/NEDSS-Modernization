import { Grid } from '@trussworks/react-uswds';
import { Input } from '../../../../components/FormInputs/Input';
import { Controller } from 'react-hook-form';
import { PhoneNumberInput } from '../../../../components/FormInputs/PhoneNumberInput/PhoneNumberInput';

export const ContactForm = ({ control }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="phoneNumber"
                    render={({ field: { onChange, value } }) => (
                        <PhoneNumberInput onChange={onChange} label="Phone number" defaultValue={value} />
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
