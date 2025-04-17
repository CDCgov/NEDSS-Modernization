import { ReactNode } from 'react';
import classNames from 'classnames';
import styles from './base-card.module.scss';

type BaseCardProps = {
    id: string;
    className?: string;
    header: ReactNode;
    children: ReactNode;
};

export const BaseCard = ({ id, header, children, className }: BaseCardProps) => {
    return (
        <section id={id} className={classNames(styles.card, className)}>
            <header>{header}</header>
            {children}
        </section>
    );
};
