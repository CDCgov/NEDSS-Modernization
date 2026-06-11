import { ReactNode } from 'react';

import { Heading } from 'components/heading';

import styles from './layout.module.scss';
import { Nbs6Breadcrumb } from 'breadcrumb';

export type ReportHeaderProps = {
    title: string;
    actions?: ReactNode;
    startHref?: string;
    startPage?: string;
};

export const ReportHeader = ({ title, actions, startHref, startPage }: ReportHeaderProps) => {
    return (
        <div className={styles.header}>
            <div className={styles.title}>
                <Heading level={1}>{title}</Heading>
                {startHref && startPage && <Nbs6Breadcrumb start={startHref}>Back to {startPage}</Nbs6Breadcrumb>}
            </div>
            <div className={styles.actions}>{actions}</div>
        </div>
    );
};
