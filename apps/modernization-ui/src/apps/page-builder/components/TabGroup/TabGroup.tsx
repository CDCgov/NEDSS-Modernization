import classNames from 'classnames';
import { useState } from 'react';
import styles from './tab-group.module.scss';

type Props = {
    initialSelection?: string | number;
    tabs: { name: string; id: string | number }[];
    onSelected: (id: string | number) => void;
};
export const TabGroup = ({ initialSelection, tabs, onSelected }: Props) => {
    const [selected, setSelected] = useState<string | number | undefined>(initialSelection);

    const handleSelectionChange = (id: number | string) => {
        setSelected(id);
        onSelected(id);
    };

    return (
        <ul className={styles.tabs}>
            {tabs.map((tab) => (
                <Tab
                    key={tab.id}
                    name={tab.name}
                    id={tab.id}
                    selected={tab.id === selected}
                    onSelected={handleSelectionChange}
                />
            ))}
        </ul>
    );
};

type TabProps = {
    name: string;
    id: string | number;
    onSelected: (id: string | number) => void;
    selected: boolean;
};
export const Tab = ({ name, id, selected, onSelected }: TabProps) => {
    return (
        <li className={classNames({ [styles.selected]: selected })}>
            <button onClick={() => onSelected(id)}>{name}</button>
        </li>
    );
};
