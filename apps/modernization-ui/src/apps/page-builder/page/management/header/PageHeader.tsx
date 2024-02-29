import { ReactNode } from 'react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';
import { PageTabs } from './tabs/PageTabs';

import styles from './page-header.module.scss';

type PageHeaderProps = {
    page: PagesResponse;
    tabs: PagesTab[];
    onAddTabSuccess?: () => void;
    children: ReactNode;
};

const PageHeader = ({ page, tabs, onAddTabSuccess, children }: PageHeaderProps) => {
    return (
        <header className={styles.header}>
            <div>
                <div className={styles.title}>
                    <h2>{page.name}</h2>
                </div>
                {children}
            </div>
            <PageTabs pageId={page.id} tabs={tabs} onAddSuccess={onAddTabSuccess} />
        </header>
    );
};

export { PageHeader };
