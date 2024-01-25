import styles from './button-bar.module.scss';
import { ReactNode } from 'react';

type Props = {
    children: ReactNode[];
};
export const ButtonBar = ({ children }: Props) => {
    return <div className={styles.buttonBar}>{children}</div>;
};
