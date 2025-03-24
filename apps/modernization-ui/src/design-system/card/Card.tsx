import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import styles from './card.module.scss';
import classNames from 'classnames';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    subtext?: string;
    level?: HeadingLevel;
    className?: string;
    children: ReactNode;
};
export const Card = ({ id, title, info, subtext, children, className, level = 2 }: Props) => {
    return (
        <section id={id} className={classNames(styles.card, className)}>
            <header>
                <div className={styles.titleBlock}>
                    <Heading level={level}>{title}</Heading>
                    {subtext && <div className={styles.subtext}>{subtext}</div>}
                </div>
                {info && <div className={styles.info}>{info}</div>}
            </header>
            {children}
        </section>
    );
};
