import styles from './login-wrapper.module.scss';
import { ReactNode } from 'react';

type Props = {
    children: ReactNode;
};

const LoginWrapper = ({ children }: Props) => (
    <div className={styles.layout}>
        <header>
            <img src="/nbs-logo.png" height={40} alt="" />
            <h1>Welcome to the NBS 7 demo site</h1>
        </header>
        {children}
    </div>
);

export { LoginWrapper };
