import { ReportConfiguration } from 'generated';
import { ReactNode, useId } from 'react';
import { SkipLink } from 'SkipLink';
import { ReportRunHeader } from './ReportRunHeader';

import styles from './layout.module.scss';

type ReportRunLayoutProps = {
    config: ReportConfiguration;
    actions?: ReactNode;
    children?: ReactNode | ReactNode[];
};

const ReportRunLayout = ({ config, actions, children }: ReportRunLayoutProps) => {
    const headerId = useId();

    return (
        <div className={styles.page}>
            <SkipLink id={headerId} />
            <header id={headerId}>
                <ReportRunHeader config={config} actions={actions} />
            </header>
            <main>{children}</main>
        </div>
    );
};

export { ReportRunLayout };
