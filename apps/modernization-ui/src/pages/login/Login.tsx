import { Button, ErrorMessage, Fieldset, Form, FormGroup, Label, TextInput } from '@trussworks/react-uswds';
import React, { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from 'providers/UserContext';
import './Login.scss';

export const Login = () => {
    const [showPassword, setShowPassword] = React.useState(false);
    const [username, setUsername] = React.useState('');
    const navigate = useNavigate();
    const { state, login } = useContext(UserContext);

    const mockSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
        // Do not append params to URL, do not refresh page
        event.preventDefault();

        login(username);
    };

    useEffect(() => {
        if (state.isLoggedIn) {
            navigate('/advanced-search');
        }
    }, [state.isLoggedIn]);

    return (
        <div className="sign-in-wrapper">
            <Form onSubmit={mockSubmit} large className="sign-in-form">
                <Fieldset legend="Sign In" legendStyle="large">
                    <FormGroup error={state.error !== undefined}>
                        <Label htmlFor="username">Username or email address</Label>
                        <TextInput
                            onChange={(e) => setUsername(e.target.value)}
                            id="username"
                            name="username"
                            type="text"
                            autoCapitalize="off"
                            autoCorrect="off"
                            disabled={state.isLoginPending}
                        />
                        <Label htmlFor="password-sign-in">Password</Label>
                        <TextInput
                            id="password-sign-in"
                            name="password-sign-in"
                            type={showPassword ? 'text' : 'password'}
                            disabled={state.isLoginPending}
                        />
                        {state.error && <ErrorMessage>{state.error}</ErrorMessage>}
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

                    <Button type="submit" disabled={state.isLoginPending}>
                        Sign in
                    </Button>
                </Fieldset>
            </Form>
        </div>
    );
};
