import { ReactNode } from 'react';
import styles from './login-wrapper.module.scss';

type Props = {
    children: ReactNode;
    header?: ReactNode;
};

const LoginWrapper = ({ children, header }: Props) => (
    <div className={styles.layout}>
        <header>
            <span className={styles.logo}>NBS</span>
            {header && header}
        </header>
        {children}
    </div>
);

export { LoginWrapper };
