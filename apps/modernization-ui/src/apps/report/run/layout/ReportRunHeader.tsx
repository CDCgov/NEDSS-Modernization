import { ReactNode } from 'react';

import { ReportConfiguration } from 'generated';
import { Heading } from 'components/heading';

import styles from './layout.module.scss';

type ReportRunHeaderProps = {
    config: ReportConfiguration;
    actions: ReactNode;
};

export const ReportRunHeader = ({ config, actions }: ReportRunHeaderProps) => {
    return (
        <div className={styles.header}>
            <Heading level={1}>Run Report: {config.title}</Heading>
            <div>{actions}</div>
        </div>
    );
};
