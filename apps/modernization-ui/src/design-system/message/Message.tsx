import { ReactNode, useId } from 'react';
import classNames from 'classnames';
import { Icon } from 'design-system/icon';
import { resolveIcon } from './resolveIcon';

import styles from './message.module.scss';

type Type = 'information' | 'success' | 'warning' | 'error';

type MessageProps = {
    type: Type;
    children: ReactNode | ReactNode[];
} & Omit<JSX.IntrinsicElements['div'], 'className'>;

const Message = ({ type, children, ...remaining }: MessageProps) => {
    const id = useId();
    const icon = resolveIcon(type);
    return (
        <div aria-label={type} className={styles.message} aria-describedby={id} {...remaining}>
            <div
                className={classNames(styles.badge, {
                    [styles.information]: type == 'information',
                    [styles.success]: type == 'success',
                    [styles.warning]: type == 'warning',
                    [styles.error]: type == 'error'
                })}>
                {icon && <Icon name={icon} sizing="large" />}
            </div>
            <div className={styles.content} id={id}>
                {children}
            </div>
        </div>
    );
};

export { Message };
export type { Type };
