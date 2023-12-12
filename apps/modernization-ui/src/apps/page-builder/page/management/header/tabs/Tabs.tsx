import classNames from 'classnames';
import { KeyboardEvent, useEffect, useState } from 'react';
import styles from './Tabs.module.scss';

type Props = {
    tabs: { name: string; id: number }[];
    onTabSelect?: (tab: number) => void;
};
export const PageTabs = ({ tabs, onTabSelect }: Props) => {
    const [selected, setSelected] = useState<number>(tabs[0].id);

    useEffect(() => {
        if (onTabSelect) {
            onTabSelect(selected);
        }
    }, [selected]);

    const handleKeyPress = (tab: number) => (event: KeyboardEvent) => {
        if (event.code === 'Enter') {
            setSelected(tab);
        }
    };

    return (
        <ul className={styles.tabs}>
            {tabs.map((tab, k) => (
                <li
                    onClick={() => setSelected(tab.id)}
                    onKeyDown={handleKeyPress(tab.id)}
                    tabIndex={0}
                    className={styles.tab}
                    key={k}>
                    <a className={classNames(styles.tabLink, { [styles.selected]: selected === tab.id })}>{tab.name}</a>
                </li>
            ))}
        </ul>
    );
};
