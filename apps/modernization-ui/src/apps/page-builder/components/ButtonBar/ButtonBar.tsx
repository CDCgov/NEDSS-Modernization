import { ReactNode } from 'react';

import styles from './button-bar.module.scss';

type Props = {
    children: ReactNode[] | ReactNode;
};
export const ButtonBar = ({ children }: Props) => {
    return <div className={styles.buttonBar}>{children}</div>;
};
