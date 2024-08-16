import { Button as TrussworksButton } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './Button.module.scss';
import classNames from 'classnames';

type Props = {
    className?: string;
    icon?: ReactNode;
    children?: string;
    outline?: boolean;
    destructive?: boolean;
    disabled?: boolean;
    type?: 'button' | 'submit' | 'reset';
    unstyled?: boolean;
    labelPosition?: 'left' | 'right';
    onClick?: () => void;
} & JSX.IntrinsicElements['button'];

const Button = ({
    className,
    type = 'button',
    icon,
    children,
    outline = false,
    destructive = false,
    unstyled = false,
    labelPosition = 'left',
    ...defaultProps
}: Props) => {
    const isIconOnly = icon && !children;
    const classesAarray = classNames(className, {
        [styles.destructive]: destructive,
        [styles.icon]: icon
    });

    return (
        <TrussworksButton
            className={classNames(classesAarray, styles['button-component'])}
            {...defaultProps}
            type={type}
            unstyled={unstyled}
            size={icon && !children ? 'big' : undefined}
            outline={outline}>
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
