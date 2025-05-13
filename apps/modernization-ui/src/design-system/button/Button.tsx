import { ReactNode } from 'react';
import { Sizing } from 'design-system/field';
import { buttonClassnames } from './buttonClassNames';

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
    const classes = buttonClassnames({
        className,
        sizing,
        icon,
        labelPosition,
        tertiary: tertiary || unstyled,
        secondary: secondary || outline,
        destructive,
        children
    });

    return (
        <button className={classes} {...defaultProps} type={type} disabled={disabled}>
            {icon}
            {children}
        </button>
    );
};

export { Button };
export type { ButtonProps, StandardButtonProps };
