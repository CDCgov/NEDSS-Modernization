import classNames from 'classnames';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { ReactNode, HTMLAttributes } from 'react';
import { resolveIcon } from '../message/Message';
import styles from './alert-message.module.scss';

type Props = {
    title?: string;
    type: 'information' | 'success' | 'warning' | 'error';
    children: ReactNode | ReactNode[];
    className?: string;
    iconless?: boolean;
    slim?: boolean;
} & Omit<HTMLAttributes<HTMLDivElement>, 'className'>;

export const AlertMessage = ({ title, type, children, className, slim = false, iconless = false, ...props }: Props) => {
    const icon = iconless ? undefined : resolveIcon(type);

    return (
        <div
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
