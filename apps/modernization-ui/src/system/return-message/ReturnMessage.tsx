import { ReactNode } from 'react';
import styles from './return-message.module.scss';

type Props = {
    title: string;
    children: ReactNode;
};

const ReturnMessage = ({ title, children }: Props) => (
    <div className={styles.return}>
        <h1>{title}</h1>
        <div className={styles.message}>{children}</div>
        <a href="/nbs/HomePage.do?method=loadHomePage">Return to NBS</a>
    </div>
);

export { ReturnMessage };
