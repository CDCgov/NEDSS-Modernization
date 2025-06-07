import { forwardRef, ReactNode } from 'react';
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

const Button = forwardRef<HTMLButtonElement, ButtonProps>(
    (
        {
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
        },
        ref
    ) => {
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
            <button className={classes} {...defaultProps} type={type} disabled={disabled} ref={ref}>
                {icon}
                {children}
            </button>
        );
    }
);

Button.displayName = 'Button';
export { Button };
export type { ButtonProps, StandardButtonProps };
