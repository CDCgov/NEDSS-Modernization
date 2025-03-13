import { Button as TrussworksButton } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './Button.module.scss';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';

type Props = {
    className?: string;
    icon?: ReactNode;
    children?: ReactNode;
    outline?: boolean; // Deprecated - replaced by secondary
    secondary?: boolean;
    destructive?: boolean;
    disabled?: boolean;
    type?: 'button' | 'submit' | 'reset';
    sizing?: Sizing;
    unstyled?: boolean;
    unpadded?: boolean;
    labelPosition?: 'left' | 'right';
    onClick?: () => void;
} & JSX.IntrinsicElements['button'];

const Button = ({
    className,
    type = 'button',
    icon,
    unpadded,
    children,
    outline = false,
    secondary = false,
    destructive = false,
    unstyled = false,
    labelPosition = 'left',
    sizing,
    ...defaultProps
}: Props) => {
    const isIconOnly = icon && !children;
    const isSecondary = secondary || outline;
    const classesArray = classNames(className, sizing && styles[sizing], {
        [styles.destructive]: destructive && !isSecondary,
        [styles.secondaryDestructive]: destructive && isSecondary,
        [styles.icon]: isIconOnly,
        [styles.unpadded]: unpadded
    });

    return (
        <TrussworksButton
            className={classNames(classesArray, styles['button-component'])}
            {...defaultProps}
            type={type}
            unstyled={unstyled}
            size={isIconOnly ? 'big' : undefined}
            outline={isSecondary}>
            {labelPosition === 'left' && children && icon ? (
                <>
                    <span>{children}</span>
                    {icon}
                </>
            ) : null}
            {labelPosition === 'right' && children && icon ? (
                <>
                    {icon}
                    <span>{children}</span>
                </>
            ) : null}
            {isIconOnly ? icon : null}
            {children && !icon ? children : null}
        </TrussworksButton>
    );
};

export { Button };
