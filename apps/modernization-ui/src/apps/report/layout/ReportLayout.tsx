import { ReactNode, useId } from 'react';
import { SkipLink } from 'SkipLink';
import { ReportHeader, ReportHeaderProps } from './ReporHeader';

import styles from './layout.module.scss';

type ReportRunLayoutProps = ReportHeaderProps & {
    children?: ReactNode | ReactNode[];
};

const ReportLayout = ({ children, ...headerProps }: ReportRunLayoutProps) => {
    const headerId = useId();

    return (
        <div className={styles.page}>
            <SkipLink id={headerId} />
            <header id={headerId}>
                <ReportHeader {...headerProps} />
            </header>
            <main>{children}</main>
        </div>
    );
};

export { ReportLayout };
