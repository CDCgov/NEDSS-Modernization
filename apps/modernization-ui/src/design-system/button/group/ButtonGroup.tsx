import { ReactNode } from 'react';

import styles from './button-group.module.scss';

interface ButtonGroupProps {
    children: ReactNode;
}

export const ButtonGroup = ({ children }: ButtonGroupProps) => {
    return <div className={styles.buttonGroup}>{children}</div>;
};
