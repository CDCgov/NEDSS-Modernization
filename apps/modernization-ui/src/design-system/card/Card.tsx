import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import styles from './card.module.scss';
import classNames from 'classnames';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    level?: HeadingLevel;
    required?: boolean;
    className?: string;
    children: ReactNode;
};
export const Card = ({ id, title, info, required, children, className, level = 2 }: Props) => {
    return (
        <section id={id} className={classNames(styles.card, className)}>
            <header>
                <Heading level={level}>{title}</Heading>
                {required && <span className="required-before">Required</span>}
                {info}
            </header>
            {children}
        </section>
    );
};
