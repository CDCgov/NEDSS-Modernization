import classNames from 'classnames';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { ReactNode, HTMLAttributes } from 'react';
import { resolveIcon } from '../message/Message';
import styles from './alert-message.module.scss';

type Children = ReactNode | ReactNode[] | string;

type Props = {
    title?: string;
    type: 'information' | 'success' | 'warning' | 'error';
    children: Children;
    className?: string;
    iconless?: boolean;
    slim?: boolean;
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
    'aria-label': ariaLabel,
    ...props
}: Props) => {
    const icon = iconless ? undefined : resolveIcon(type);
    const defaultAriaLabel = getDefaultAlertAriaLabel(title, children);

    const getAriaLabel = (): string | undefined => {
        if (!title && !ariaLabel) return undefined;
        if (title && ariaLabel) return `${title}. ${ariaLabel}`;
        return title ?? ariaLabel;
    };

    return (
        <div
            role="alert"
            aria-label={defaultAriaLabel || getAriaLabel()}
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
        </div>
    );
};
