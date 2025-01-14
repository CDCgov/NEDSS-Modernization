import { Config } from 'config';
import { LoginForm } from './LoginForm';

const Login = () => {
    return Config.enableLogin ? <LoginForm /> : <>{(window.location.href = '/nbs/login')}</>;
};

export { Login };
