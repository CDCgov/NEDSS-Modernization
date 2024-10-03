import sprite from '@uswds/uswds/img/sprite.svg';
import classNames from 'classnames';
import { ReactNode } from 'react';
import styles from './alert-message.module.scss';
import { resolveIcon, Type } from '../Message';
type Props = {
    title: string;
    type: Type;
    children: ReactNode | ReactNode[];
};
// This component is made to match the Figma design for the add patient extended error messages.
// The designs do not match the Message component. In the "near" future, it may be possible to consolidate this and the Message component
export const AlertMessage = ({ title, type, children }: Props) => {
    const icon = resolveIcon(type);

    return (
        <div
            className={classNames(styles.alertMessage, {
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
            <div className={styles.content}>
                <div className={styles.title}>{title}</div>
                {children}
            </div>
        </div>
    );
};
