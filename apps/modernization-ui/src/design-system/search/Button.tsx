import React, { ReactNode } from 'react';
import classNames from 'classnames';
import styles from './Button.module.scss';

type ButtonSize = 'SM' | 'MD' | 'LG';

type ButtonProps = {
    size?: ButtonSize;
    icon?: ReactNode;
    className?: string;
    children?: React.ReactNode;
};

export const Button = ({ size = 'MD', icon, className, children, ...props }: ButtonProps) => {
    const btnClass = classNames(styles.button, styles[`size-${size}`], className);

    return (
        <button className={btnClass} {...props}>
            {icon}
            {children}
        </button>
    );
};
