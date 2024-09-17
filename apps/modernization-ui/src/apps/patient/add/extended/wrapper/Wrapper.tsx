import { Heading } from 'components/heading';
import classNames from 'classnames';
import styles from './Wrapper.module.scss';
import { ReactNode } from 'react';

export const Wrapper = ({ id, title, children }: { id: string; title: string; children: ReactNode }) => {
    return (
        <section id={id} className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
            </header>
            <div className={classNames(styles.form)}>{children}</div>
        </section>
    );
};
