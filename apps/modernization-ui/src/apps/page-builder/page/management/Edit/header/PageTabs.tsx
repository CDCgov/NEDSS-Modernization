import { KeyboardEvent, useEffect, useState } from 'react';
import styles from './PageTabs.module.scss';
import classNames from 'classnames';

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

    const handleKeyPress = (event: KeyboardEvent, tab: number) => {
        if (event.code === 'Space' || event.code === 'Enter') {
            setSelected(tab);
        }
    };

    return (
        <ul className={styles.tabs}>
            {tabs.map((tab, k) => (
                <li
                    onClick={() => setSelected(tab.id)}
                    onKeyDown={(e) => handleKeyPress(e, tab.id)}
                    tabIndex={0}
                    className={classNames(styles.tab, { [styles.selected]: selected === tab.id })}
                    key={k}>
                    {tab.name}
                </li>
            ))}
        </ul>
    );
};
