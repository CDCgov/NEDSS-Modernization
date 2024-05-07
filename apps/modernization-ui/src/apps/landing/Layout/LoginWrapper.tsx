import styles from './login-wrapper.module.scss';
import { ReactNode } from 'react';

type Props = {
    children: ReactNode;
    header?: ReactNode;
};

const LoginWrapper = ({ children, header }: Props) => (
    <div className={styles.layout}>
        <header>
            <img src="/nbs-logo.png" height={40} alt="" />
            {header && header}
        </header>
        {children}
    </div>
);

export { LoginWrapper };
