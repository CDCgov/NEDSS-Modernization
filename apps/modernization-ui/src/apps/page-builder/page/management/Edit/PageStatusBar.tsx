import React from 'react';

import styles from './PageStatusBar.module.scss';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
type Props = {
    name?: string;
    pageStatus: string;
};
export const PageStatusBar = ({ name, pageStatus }: Props) => {
    return (
        <div className={styles.statusBar}>
            <PagesBreadcrumb currentPage={name} path="/page-builder/manage/pages" />
            <div className={styles.pageMode}>
                <span className={styles.text}>{pageStatus}</span>
            </div>
        </div>
    );
};
