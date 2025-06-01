import React, { ReactNode } from 'react';
import { Sizing } from 'design-system/field';
import { buttonClassnames } from './buttonClassNames';
import { Icon, Icons } from 'design-system/icon';

type StandardButtonProps = {
    className?: string;
    icon?: Icons;
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
} & StandardButtonProps &
    JSX.IntrinsicElements['button'];

const Button = ({
    className,
    sizing,
    type = 'button',
    icon,
    labelPosition = 'right',
    active,
    disabled,
    tertiary,
    secondary,
    destructive,
    outline,
    unstyled,
    children,
    ...defaultProps
}: ButtonProps) => {
    const classes = buttonClassnames({
        className,
        sizing,
        icon,
        labelPosition,
        active,
        tertiary: tertiary || unstyled,
        secondary: secondary || outline,
        destructive,
        children
    });

    return (
        <button className={classes} {...defaultProps} type={type} disabled={disabled}>
            {icon && <Icon name={icon} />}
            {children}
        </button>
    );
};

export { Button };
export type { ButtonProps, StandardButtonProps };
