import { ReactNode } from 'react';
import styles from './result-item-group.module.scss';

type Props = {
    children: ReactNode;
};

const ResultItemGroup = ({ children }: Props) => <div className={styles.group}>{children}</div>;

export { ResultItemGroup };
