import { useContext, useEffect, useState } from 'react';
import { UserContext } from 'user';
import { ValueSetsContext } from '../../context/ValueSetContext';
import './ValuesetLibrary.scss';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import './ValuesetTabs.scss';
import { fetchValueSet } from './useValuesetAPI';

export const ValuesetLibrary = ({ hideTabs, types, modalRef }: any) => {
    const [activeTab, setActiveTab] = useState(types || 'local');
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(ValueSetsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);

    const handleTab = (tab: string) => {
        setActiveTab(tab);
    };
    // @ts-ignore
    useEffect(async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);
        setSummaries([]);
        const { content, totalElements }: any = await fetchValueSet(
            token,
            searchQuery,
            sortBy,
            currentPage,
            pageSize,
            filter
        );
        setTotalElements(totalElements);
        setIsLoading(false);
        setSummaries(content);
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);

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
                        <ValuesetLibraryTable
                            summaries={summaries}
                            pages={{ currentPage, pageSize, totalElements }}
                            labModalRef={modalRef}
                        />
                    </div>
                </div>
            </div>
        </>
    );
};
