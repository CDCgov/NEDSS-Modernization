import { ReactNode } from 'react';
import styles from './result.module.scss';

type Props = {
    children: ReactNode;
};

const Result = ({ children }: Props) => <div className={styles.result}>{children}</div>;

export { Result };
