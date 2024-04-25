import styles from './login-wrapper.module.scss';
import { ReactNode } from 'react';

type Props = {
    children: ReactNode;
};

const LoginWrapper = ({ children }: Props) => (
    <div className={styles.layout}>
        <header>
            <img src="/nbs-logo.png" height={40} alt="" />
        </header>
        {children}
    </div>
);

export { LoginWrapper };
