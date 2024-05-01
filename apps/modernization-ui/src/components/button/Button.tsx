import { Button as TrussworksButton } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './Button.module.scss';
import classNames from 'classnames';

type Props = {
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
    const classesAarray = classNames({
        [styles.destructive]: destructive,
        [styles.icon]: icon
    });

    return (
        <TrussworksButton
            className={classesAarray}
            {...defaultProps}
            type={type}
            unstyled={unstyled}
            size={icon && !children ? 'big' : undefined}
            outline={outline}>
            {labelPosition === 'left' && children && icon ? (
                <>
                    {children}
                    {icon}
                </>
            ) : null}
            {labelPosition === 'right' && children && icon ? (
                <>
                    {icon}
                    {children}
                </>
            ) : null}
            {isIconOnly ? icon : null}
            {children && !icon ? children : null}
        </TrussworksButton>
    );
};

export { Button };
