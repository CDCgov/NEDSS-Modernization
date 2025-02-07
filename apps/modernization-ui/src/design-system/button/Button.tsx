import { Button as TrussworksButton } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './Button.module.scss';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';

type Props = {
    className?: string;
    icon?: ReactNode;
    children?: ReactNode;
    outline?: boolean;
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
    destructive = false,
    unstyled = false,
    labelPosition = 'left',
    sizing = 'small',
    ...defaultProps
}: Props) => {
    const isIconOnly = icon && !children;
    const classesArray = classNames(className, sizing && styles[sizing], {
        [styles.destructive]: destructive,
        [styles.icon]: icon,
        [styles.unpadded]: unpadded
    });

    return (
        <TrussworksButton
            className={classNames(classesArray, styles['button-component'])}
            {...defaultProps}
            type={type}
            unstyled={unstyled}
            size={isIconOnly ? 'big' : undefined}
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
