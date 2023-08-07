import { PageProvider } from 'page';
import React, { useState } from 'react';
import './ValuesetLibrary.scss';
import './ValuesetTabs.scss';
import { ValuesetLibraryTableWrapper } from './ValuesetLibraryTableWrapper';

export const ValuesetLibrary = ({ hideTabs, types, modalRef }: any) => {
    const [pageSize] = useState(10);
    const [activeTab, setActiveTab] = useState(types || 'local');
    const handleTab = (tab: string) => {
        setActiveTab(tab);
    };

    return (
        <>
            <div className="valueset-local-library">
                {!hideTabs && (
                    <div className="margin-left-2em">
                        <h2>Add value set</h2>
                    </div>
                )}
                {!hideTabs && (
                    <ul className="tabs">
                        <li className={activeTab == 'local' ? 'active' : ''} onClick={() => handleTab('local')}>
                            Search Local
                        </li>
                        <li className={activeTab == 'recent' ? 'active' : ''} onClick={() => handleTab('recent')}>
                            Recently Created
                        </li>
                    </ul>
                )}
                <div className="search-description-block">
                    <p>Let’s find the right value set for your single choice question</p>
                </div>
                <div className="valueset-local-library__container">
                    <div className="valueset-local-library__table">
                        <PageProvider pageSize={pageSize}>
                            <ValuesetLibraryTableWrapper modalRef={modalRef} activeTab={activeTab?.toUpperCase()} />
                        </PageProvider>
                    </div>
                </div>
            </div>
        </>
    );
};
