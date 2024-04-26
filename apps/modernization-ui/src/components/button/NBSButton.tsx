import { Button } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './NBSButton.module.scss';

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

const NBSButton = ({
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
    const classesArray = [];
    if (destructive) {
        classesArray.push(styles.destructive);
    }

    if (isIconOnly) {
        classesArray.push(styles.icon);
    }

    return (
        <Button
            className={classesArray.join(' ')}
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
        </Button>
    );
};

export { NBSButton };
