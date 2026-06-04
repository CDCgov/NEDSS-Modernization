import { ReactNode, useId } from 'react';
import { SkipLink } from 'SkipLink';
import { ReportHeader } from './ReporHeader';

import styles from './layout.module.scss';

type ReportRunLayoutProps = {
    title: string;
    actions?: ReactNode;
    children?: ReactNode | ReactNode[];
};

const ReportLayout = ({ title, actions, children }: ReportRunLayoutProps) => {
    const headerId = useId();

    return (
        <div className={styles.page}>
            <SkipLink id={headerId} />
            <header id={headerId}>
                <ReportHeader title={title} actions={actions} />
            </header>
            <main>{children}</main>
        </div>
    );
};

export { ReportLayout };
