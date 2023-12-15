import { ReactNode } from 'react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';
import { PageTabs } from './tabs/PageTabs';

import styles from './page-header.module.scss';

type PageHeaderProps = {
    page: PagesResponse;
    tabs: PagesTab[];
    children: ReactNode;
};

const PageHeader = ({ page, tabs, children }: PageHeaderProps) => {
    return (
        <header className={styles.header}>
            <div>
                <div className={styles.title}>
                    <h2>{page.name}</h2>
                    <p>{page.description}</p>
                </div>
                {children}
            </div>
            <PageTabs tabs={tabs} />
        </header>
    );
};

export { PageHeader };
