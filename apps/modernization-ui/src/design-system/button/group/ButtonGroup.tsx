import React from 'react';
import styles from './button-group.module.scss';

interface ButtonGroupProps {
    children: React.ReactNode;
}

export const ButtonGroup = ({ children }: ButtonGroupProps) => {
    return <div className={styles.buttonGroup}>{children}</div>;
};
