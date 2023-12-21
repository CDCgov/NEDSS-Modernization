import { Grid } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import { Controller } from 'react-hook-form';
import { validate as validatePhoneNumber } from 'validation/search';

export const ContactForm = ({ control, errors }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="phoneNumber"
                    rules={{
                        validate: {
                            properNumber: (value) => {
                                return validatePhoneNumber(value);
                                // return true;
                            }
                        }
                    }}
                    render={({ field: { onChange, value } }) => (
                        <Input
                            type="text"
                            name="phoneNumber"
                            htmlFor={'phoneNumber'}
                            id={'phoneNumber'}
                            onChange={onChange}
                            label="Phone number"
                            defaultValue={value}
                            mask="___-___-____"
                            // pattern="\d{3}-\d{3}-\d{0,4}|\d{3}-\d{0,3}|\d{0,3}"
                            error={
                                errors &&
                                errors.phoneNumber &&
                                'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
                            }
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="email"
                    rules={{
                        pattern: {
                            value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                            message: 'Please enter a valid email address (example: youremail@website.com)'
                        }
                    }}
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="Email"
                            htmlFor="email"
                            id="email"
                            error={errors && errors.email && errors.email.message}
                        />
                    )}
                />
            </Grid>
        </>
    );
};
