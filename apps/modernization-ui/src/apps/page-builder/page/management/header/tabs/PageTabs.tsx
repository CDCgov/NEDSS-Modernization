import { TabGroup } from 'apps/page-builder/components/TabGroup/TabGroup';
import { PagesTab } from 'apps/page-builder/generated';
import { ManageTabs } from '../../edit/tabs/ManageTabs/ManageTabs';
import { usePageManagement } from '../../usePageManagement';
import styles from './page-tabs.module.scss';

type Props = {
    pageId: number;
    tabs: PagesTab[];
    onAddSuccess?: () => void;
};

export const PageTabs = ({ pageId, tabs, onAddSuccess }: Props) => {
    const { selected, select } = usePageManagement();

    const handleSelectionChanged = (id: string | number) => {
        const tab = tabs.find((t) => t.id === id);
        if (tab) {
            select(tab);
        }
    };

    return (
        <div className={styles.pageTabs}>
            <TabGroup
                tabs={tabs.map((t) => {
                    return { id: t.id, name: t.name };
                })}
                onSelected={handleSelectionChanged}
                initialSelection={selected?.id}
            />
            {onAddSuccess ? <ManageTabs pageId={pageId} tabs={tabs} onAddSuccess={onAddSuccess} /> : null}
        </div>
    );
};
