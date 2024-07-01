import { Input } from 'components/FormInputs/Input';
import { Control, Controller } from 'react-hook-form';
import { validate as validatePhoneNumber } from 'validation/search';
import styles from './contact.module.scss';
import { PatientCriteriaEntry } from '../criteria';

type Props = {
    control: Control<PatientCriteriaEntry>;
};

export const Contact = ({ control }: Props) => {
    return (
        <div className={styles.contact}>
            <Controller
                control={control}
                name="phoneNumber"
                rules={{
                    validate: {
                        properNumber: (value) => {
                            if (value) {
                                return validatePhoneNumber(value);
                            }
                        }
                    }
                }}
                render={({ field: { onChange, value, name } }) => (
                    <Input
                        type="text"
                        htmlFor={name}
                        id={name}
                        onChange={onChange}
                        label="Phone number"
                        defaultValue={value}
                        pattern="\d{3}-\d{3}-\d{0,4}|\d{3}-\d{0,3}|\d{0,3}"
                        error={
                            control._formState.errors &&
                            control._formState.errors.phoneNumber &&
                            'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
                        }
                    />
                )}
            />
            <Controller
                control={control}
                name="email"
                rules={{
                    pattern: {
                        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                        message: 'Please enter a valid email address (example: youremail@website.com)'
                    }
                }}
                render={({ field: { onChange, value, name } }) => (
                    <Input
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Email"
                        htmlFor={name}
                        id={name}
                        error={
                            control._formState.errors &&
                            control._formState.errors.email &&
                            control._formState.errors.email.message
                        }
                    />
                )}
            />
        </div>
    );
};
