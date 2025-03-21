import { FormEvent, useEffect, useState } from 'react';
import { Button, ErrorMessage, Fieldset, Form, FormGroup, Label, TextInput } from '@trussworks/react-uswds';
import { ApiError, LoginService } from 'generated';
import './Login.scss';
import { useNavigate } from 'react-router';

const LoginForm = () => {
    const [showPassword, setShowPassword] = useState(false);
    const [username, setUsername] = useState('');
    const [pending, setPending] = useState<boolean>(false);
    const [error, setError] = useState<string>();
    const navigate = useNavigate();

    useEffect(() => {
        if (pending) {
            LoginService.login({
                requestBody: { username: username }
            })
                .then(() => navigate('/'))
                .catch((error: ApiError) => {
                    if (error.status === 401) {
                        setError('Incorrect username or password');
                    } else {
                        setError('Login failed');
                    }
                    setPending(false);
                });
        }
    }, [pending, username, setError]);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        // Do not append params to URL, do not refresh page
        event.preventDefault();

        setPending(true);
    };

    return (
        <div className="sign-in-wrapper">
            <Form onSubmit={handleSubmit} large className="sign-in-form">
                <Fieldset legend="Sign In" legendStyle="large">
                    <FormGroup error={error !== undefined}>
                        <Label htmlFor="username">Username or email address</Label>
                        <TextInput
                            onChange={(e) => setUsername(e.target.value)}
                            id="username"
                            name="username"
                            type="text"
                            autoCapitalize="off"
                            autoCorrect="off"
                            disabled={pending}
                        />
                        <Label htmlFor="password-sign-in">Password</Label>
                        <TextInput
                            id="password-sign-in"
                            name="password-sign-in"
                            type={showPassword ? 'text' : 'password'}
                            disabled={pending}
                        />
                        {error && <ErrorMessage>{error}</ErrorMessage>}
                    </FormGroup>

                    <p className="usa-form__note">
                        <a
                            title="Show password"
                            className="usa-show-password"
                            aria-controls="password-sign-in"
                            onClick={(): void => setShowPassword((showPassword) => !showPassword)}>
                            {showPassword ? 'Hide password' : 'Show password'}
                        </a>
                    </p>

                    <Button type="submit" disabled={pending}>
                        Sign in
                    </Button>
                </Fieldset>
            </Form>
        </div>
    );
};

export { LoginForm };
