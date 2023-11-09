import React, { useState } from 'react';
import './SideNavigation.scss';
import { NavigationEntry } from './NavigationEntry';

type NavigationEntryElement = typeof NavigationEntry;

type NavigationProps = {
    title: string;
    entries: React.ReactElement<NavigationEntryElement>[];
    active: React.Key;
    onActiveChange?: (key: React.Key) => void;
};
export const SideNavigation = (props: NavigationProps) => {
    const [active, setActive] = useState<React.Key>(props.active);
    const handleEntryClick = (key: React.Key) => {
        setActive(key);
        if (props.onActiveChange) {
            props.onActiveChange(key);
        }
    };
    return (
        <div className="navigation">
            <div className="heading">
                <h2>{props.title}</h2>
            </div>
            <div className="menu">
                {props.entries.map((entry, k) => (
                    <div
                        key={k}
                        className={`item ${active == entry.key ? 'active' : ''}`}
                        onClick={() => handleEntryClick(entry.key as React.Key)}>
                        <React.Fragment>{entry}</React.Fragment>
                    </div>
                ))}
            </div>
        </div>
    );
};
