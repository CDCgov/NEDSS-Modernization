import { ReactNode } from 'react';

import { Heading } from 'components/heading';

import styles from './layout.module.scss';

type ReportHeaderProps = {
    title: string;
    actions: ReactNode;
};

export const ReportHeader = ({ title, actions }: ReportHeaderProps) => {
    return (
        <div className={styles.header}>
            <Heading level={1}>{title}</Heading>
            <div className={styles.actions}>{actions}</div>
        </div>
    );
};
