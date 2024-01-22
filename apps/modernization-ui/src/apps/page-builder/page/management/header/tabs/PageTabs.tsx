import { KeyboardEvent as ReactKeyboardEvent } from 'react';
import classNames from 'classnames';
import { PagesTab } from 'apps/page-builder/generated';
import styles from './page-tabs.module.scss';
import { usePageManagement } from '../../usePageManagement';
import { ManageTabs } from '../../edit/tabs/ManageTabs/ManageTabs';

type Props = {
    pageId: number;
    tabs: PagesTab[];
    onAddSuccess?: () => void;
};

export const PageTabs = ({ pageId, tabs, onAddSuccess }: Props) => {
    const { selected, select } = usePageManagement();

    const handleKeyPress = (selected: PagesTab) => (event: ReactKeyboardEvent) => {
        if (event.code === 'Enter') {
            select(selected);
        }
    };

    return (
        <ul className={styles.tabs}>
            {tabs.map((tab, k) => (
                <li
                    className={classNames({ [styles.selected]: selected?.id === tab.id })}
                    onClick={() => select(tab)}
                    onKeyDown={handleKeyPress(tab)}
                    tabIndex={0}
                    key={k}>
                    {tab.name}
                </li>
            ))}
            {onAddSuccess ? <ManageTabs pageId={pageId} tabs={tabs} onAddSuccess={onAddSuccess} /> : null}
        </ul>
    );
};
