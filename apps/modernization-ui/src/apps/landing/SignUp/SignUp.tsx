import { Button } from '@trussworks/react-uswds';
import { Controller, useForm } from 'react-hook-form';
import { validEmailRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { QuickNavigation } from 'apps/landing/QuickNavigation';
import { useSignUp } from './useSignUp';

import styles from './sign-up.module.scss';

type SignUpEntry = {
    email: string;
};

const SignUp = () => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm<SignUpEntry, Partial<SignUpEntry>>({ mode: 'onBlur' });

    const { signUp } = useSignUp();

    const onSubmit = (entry: SignUpEntry) => {
        signUp(entry.email);
    };

    return (
        <div className={styles['sign-up']}>
            <div>
                <h1>Sign up</h1>
                <p>
                    Upon approval, you will receive an email with login details and the demo site address. If you
                    haven't received it within 24 hours, please let us know by emailing{' '}
                    <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a>
                </p>
                <div className={styles.form}>
                    <Controller
                        control={control}
                        name="email"
                        rules={{
                            required: { value: true, message: 'An email address is required.' },
                            ...validEmailRule(255)
                        }}
                        render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                            <Input
                                onBlur={onBlur}
                                onChange={onChange}
                                defaultValue={value}
                                type="email"
                                label="Email address"
                                name="email"
                                htmlFor="email"
                                id="email"
                                error={error?.message}
                            />
                        )}
                    />
                    <Button type="button" onClick={handleSubmit(onSubmit)} disabled={!isValid}>
                        Sign up for demo access
                    </Button>
                </div>
            </div>
            <QuickNavigation>
                Existing user?
                <a href="/nbs/login" aria-label="Go to NBS Login">
                    Login
                </a>
            </QuickNavigation>
        </div>
    );
};

export { SignUp };
