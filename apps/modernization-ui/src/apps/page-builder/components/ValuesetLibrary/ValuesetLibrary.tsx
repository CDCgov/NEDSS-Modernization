import { PageProvider } from 'page';
import React, { useState } from 'react';
import './ValuesetLibrary.scss';
import './ValuesetTabs.scss';
import { ValuesetLibraryTableWrapper } from './ValuesetLibraryTableWrapper';
export const ValuesetLibrary = () => {
    const [pageSize] = useState(10);
    const [activeTab, setActiveTab] = useState('local');
    const handleTab = (tab: string) => {
        setActiveTab(tab);
    };

    return (
        <>
            <div className="valueset-local-library">
                <div className="margin-left-2em">
                    <h2>Add value set</h2>
                </div>
                <ul className="tabs">
                    <li className={activeTab == 'local' ? 'active' : ''} onClick={() => handleTab('local')}>
                        Search Local
                    </li>
                    <li className={activeTab == 'recent' ? 'active' : ''} onClick={() => handleTab('recent')}>
                        Recently Created
                    </li>
                </ul>
                <div className="search-description-block">
                    <p>Let’s find the right value set for your single choice question</p>
                </div>
                <div className="valueset-local-library__container">
                    <div className="valueset-local-library__table">
                        <PageProvider pageSize={pageSize}>
                            <ValuesetLibraryTableWrapper activeTab={activeTab?.toUpperCase()} />
                        </PageProvider>
                    </div>
                </div>
            </div>
        </>
    );
};
