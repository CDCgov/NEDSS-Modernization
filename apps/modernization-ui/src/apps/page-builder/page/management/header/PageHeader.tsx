import { ReactNode } from 'react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';
import { PageTabs } from './tabs/PageTabs';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

import styles from './page-header.module.scss';

type PageHeaderProps = {
    page: PagesResponse;
    tabs: PagesTab[];
    onAddTabSuccess?: () => void;
    selected?: PagesTab;
    children: ReactNode;
};

const PageHeader = ({ page, tabs, onAddTabSuccess, selected, children }: PageHeaderProps) => {
    return (
        <header className={styles.header}>
            <div>
                <div className={styles.title}>
                    <h2>{page.name}</h2>
                    <p>{page.description}</p>
                </div>
                {children}
            </div>
            <DragDropProvider
                pageData={page}
                currentTab={page.tabs?.findIndex((x: PagesTab) => x.name === selected?.name) ?? 0}
                successCallBack={onAddTabSuccess}>
                <PageTabs pageId={page.id} tabs={tabs} onAddSuccess={onAddTabSuccess} />
            </DragDropProvider>
        </header>
    );
};

export { PageHeader };
