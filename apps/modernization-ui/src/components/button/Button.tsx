import { Button as TrussworksButton } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './Button.module.scss';
import classNames from 'classnames';

type Props = {
    icon?: ReactNode;
    label?: string;
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
    label,
    outline = false,
    destructive = false,
    unstyled = false,
    labelPosition = 'left',
    ...defaultProps
}: Props) => {
    const isIconOnly = icon && !label;
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
            size={icon && !label ? 'big' : undefined}
            outline={outline}>
            {labelPosition === 'left' && label && icon ? (
                <>
                    {label}
                    {icon}
                </>
            ) : null}
            {labelPosition === 'right' && label && icon ? (
                <>
                    {icon}
                    {label}
                </>
            ) : null}
            {isIconOnly ? icon : null}
            {label && !icon ? label : null}
        </TrussworksButton>
    );
};

export { Button };
