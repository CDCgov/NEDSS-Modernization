import classNames from 'classnames';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { ReactNode, HTMLAttributes, useState } from 'react';
import { resolveIcon } from '../message/Message';
import styles from './alert-message.module.scss';

type Children = ReactNode | ReactNode[];

type Props = {
    title?: string;
    type: 'information' | 'success' | 'warning' | 'error';
    children: Children;
    className?: string;
    iconless?: boolean;
    slim?: boolean;
    onClose?: () => void;
} & Omit<HTMLAttributes<HTMLDivElement>, 'className'>;

const getDefaultAlertAriaLabel = (title?: string, children?: Children): string | undefined => {
    if (!title && !children) return undefined;

    const childrenText = typeof children === 'string' ? children : '';
    return title ? `${title}. ${childrenText}` : childrenText;
};

export const AlertMessage = ({
    title,
    type,
    children,
    className,
    slim = false,
    iconless = false,
    onClose,
    'aria-label': ariaLabel,
    ...props
}: Props) => {
    const [visible, setVisible] = useState(true);

    const handleClose = () => {
        setVisible(false);
        onClose?.();
    };

    if (!visible) return null;
    const icon = iconless ? undefined : resolveIcon(type);

    const getAriaLabel = (): string | undefined => {
        if (!title && !ariaLabel) return undefined;
        if (title && ariaLabel) return `${title}. ${ariaLabel}`;
        return getDefaultAlertAriaLabel(title, children);
    };

    return (
        <div
            role="alert"
            aria-label={getAriaLabel()}
            className={classNames(
                styles.alertMessage,
                {
                    [styles.information]: type == 'information',
                    [styles.success]: type == 'success',
                    [styles.warning]: type == 'warning',
                    [styles.error]: type == 'error',
                    [styles.slim]: slim
                },
                className
            )}
            {...props}>
            {icon && <Icon name={icon} sizing={slim ? 'small' : 'medium'} />}
            <div className={styles.content}>
                {title && <Heading level={2}>{title}</Heading>}
                {children}
            </div>
            {onClose && (
                <button type="button" onClick={handleClose} className={styles.closeButton} aria-label="Close alert">
                    <Icon name="close" sizing="small" />
                </button>
            )}
        </div>
    );
};
