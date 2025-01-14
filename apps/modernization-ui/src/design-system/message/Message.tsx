import sprite from '@uswds/uswds/img/sprite.svg';
import classNames from 'classnames';
import { ReactNode } from 'react';
import styles from './message.module.scss';

type Type = 'information' | 'success' | 'warning' | 'error';

type MessageProps = {
    type: Type;
    children: ReactNode;
};

const Message = ({ type, children }: MessageProps) => {
    const icon = resolveIcon(type);
    return (
        <div className={styles.message}>
            <div
                className={classNames(styles.badge, {
                    [styles.information]: type == 'information',
                    [styles.success]: type == 'success',
                    [styles.warning]: type == 'warning',
                    [styles.error]: type == 'error'
                })}>
                {icon && (
                    <svg width={'2rem'} height={'2rem'}>
                        <use xlinkHref={`${sprite}#${icon}`}></use>
                    </svg>
                )}
            </div>
            <div className={styles.content}>{children}</div>
        </div>
    );
};

const resolveIcon = (type: Type) => {
    if (type == 'information') {
        return 'info';
    } else if (type == 'success') {
        return 'check_circle';
    } else if (type == 'warning') {
        return 'warning';
    } else if (type == 'error') {
        return 'error';
    }
};

export { Message, resolveIcon };
export type { Type };
