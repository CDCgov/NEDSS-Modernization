import { KeyboardEvent, useState } from 'react';
import styles from './PageTabs.module.scss';
import classNames from 'classnames';

type Props = {
    tabs: string[];
};
export const PageTabs = ({ tabs }: Props) => {
    const [selected, setSelected] = useState<string>(tabs[0]);

    const handleKeyPress = (event: KeyboardEvent, tab: string) => {
        if (event.code === 'Space' || event.code === 'Enter') {
            setSelected(tab);
        }
    };
    return (
        <ul className={styles.tabs}>
            {tabs.map((tab, k) => (
                <li
                    onClick={() => setSelected(tab)}
                    onKeyDown={(e) => handleKeyPress(e, tab)}
                    tabIndex={0}
                    className={classNames(styles.tab, { [styles.selected]: selected === tab })}
                    key={k}>
                    {tab}
                </li>
            ))}
        </ul>
    );
};
