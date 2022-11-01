import { Button, Fieldset, Form, FormGroup, Label, TextInput } from '@trussworks/react-uswds';
import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../providers/UserContext';
import './Login.scss';

export const Login = () => {
    const [showPassword, setShowPassword] = React.useState(false);
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [error, setError] = React.useState(false);
    const navigate = useNavigate();
    const { login } = useContext(UserContext);

    const mockSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
        // Do not append params to URL, do not refresh page
        event.preventDefault();

        const response = await login(username, password);
        if (response) {
            navigate('/search');
        } else {
            // invalid login
            setError(true);
        }
    };

    return (
        <div className="sign-in-wrapper">
            <Form onSubmit={mockSubmit} large className="sign-in-form">
                <Fieldset legend="Sign In" legendStyle="large">
                    <FormGroup error={error}>
                        <Label htmlFor="username">Username or email address</Label>
                        <TextInput
                            onChange={(e) => setUsername(e.target.value)}
                            id="username"
                            name="username"
                            type="text"
                            autoCapitalize="off"
                            autoCorrect="off"
                        />
                        <Label htmlFor="password-sign-in">Password</Label>
                        <TextInput
                            onChange={(e) => setPassword(e.target.value)}
                            id="password-sign-in"
                            name="password-sign-in"
                            type={showPassword ? 'text' : 'password'}
                        />
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

                    <Button type="submit">Sign in</Button>
                </Fieldset>
            </Form>
        </div>
    );
};
