import { ReactNode } from 'react';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';

import styles from './Button.module.scss';

type StandardButtonProps = {
    className?: string;
    icon?: ReactNode;
    children?: ReactNode;
    active?: boolean;
    secondary?: boolean;
    destructive?: boolean;
    disabled?: boolean;
    sizing?: Sizing;
    tertiary?: boolean;
    labelPosition?: 'left' | 'right';
};

type ButtonProps = {
    /** Deprecated - replaced by secondary */
    outline?: boolean;
    /** Deprecated - replaced by tertiary */
    unstyled?: boolean;
    onClick?: () => void;
} & StandardButtonProps &
    JSX.IntrinsicElements['button'];

const Button = ({
    className,
    sizing,
    type = 'button',
    icon,
    labelPosition = 'right',
    active = false,
    disabled = false,
    tertiary = false,
    outline = false,
    secondary = false,
    destructive = false,
    unstyled = false,
    children,
    ...defaultProps
}: ButtonProps) => {
    const isIconOnly = Boolean(icon && !children);

    const classes = classNames(
        styles.button,
        {
            [styles.active]: active,
            [styles.secondary]: secondary || outline,
            [styles.destructive]: destructive,
            [styles.tertiary]: tertiary || unstyled,
            [styles.icon]: isIconOnly,
            [styles['icon-last']]: labelPosition === 'left',
            [styles.small]: sizing === 'small'
        },
        className
    );

    return (
        <button className={classes} {...defaultProps} type={type} disabled={disabled}>
            {icon}
            {children}
        </button>
    );
};

export { Button };
export type { ButtonProps, StandardButtonProps };
