import { ReactNode, useId } from 'react';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { resolveIcon } from 'design-system/message';
import { Button } from 'design-system/button';

import styles from './alert-message.module.scss';

type Children = ReactNode | ReactNode[];

type AlertMessageProps = {
    title?: string;
    type: 'information' | 'success' | 'warning' | 'error';
    children: Children;
    className?: string;
    iconless?: boolean;
    slim?: boolean;
    onClose?: () => void;
} & JSX.IntrinsicElements['div'];

const AlertMessage = ({
    title,
    type,
    children,
    className,
    slim = false,
    iconless = false,
    onClose,
    role = 'alert',
    ...props
}: AlertMessageProps) => {
    const icon = iconless ? undefined : resolveIcon(type);

    const messageId = useId();

    return (
        <div
            role={role}
            aria-describedby={messageId}
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
                <span id={messageId}>{children}</span>
            </div>
            {onClose && (
                <Button
                    type="button"
                    onClick={onClose}
                    icon="close"
                    tertiary
                    sizing="small"
                    aria-label="Close alert"
                    className={styles.closeButton}
                />
            )}
        </div>
    );
};

export { AlertMessage };
export type { AlertMessageProps };
