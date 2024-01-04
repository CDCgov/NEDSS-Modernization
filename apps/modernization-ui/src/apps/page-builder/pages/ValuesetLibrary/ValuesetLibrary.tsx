import { useContext, useEffect, useState } from 'react';
import { ValueSetsContext } from '../../context/ValueSetContext';
import './ValuesetLibrary.scss';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import './ValuesetTabs.scss';
import { fetchValueSet } from './useValuesetAPI';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { authorization } from 'authorization';

export const ValuesetLibrary = ({ hideTabs, types, modalRef }: any) => {
    const [activeTab, setActiveTab] = useState(types || 'local');
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(ValueSetsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);

    const handleTab = (tab: string) => {
        setActiveTab(tab);
    };
    // @ts-ignore
    useEffect(async () => {
        setIsLoading(true);
        setSummaries([]);
        const { content, totalElements }: any = await fetchValueSet(
            authorization(),
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

    const handleUpdateSummariesCallback = async () => {
        const { content, totalElements }: any = await fetchValueSet(
            authorization(),
            searchQuery,
            sortBy,
            currentPage,
            pageSize,
            filter
        );
        setTotalElements(totalElements);
        setSummaries(content);
    };

    const renderValueSetList = (
        <>
            <div className="search-description-block">
                <p>Let’s find the right value set for your single choice question</p>
            </div>
            <div className="valueset-local-library__container">
                <div className="valueset-local-library__table">
                    <ValuesetLibraryTable
                        summaries={summaries}
                        pages={{ currentPage, pageSize, totalElements }}
                        labModalRef={modalRef}
                        updateCallback={handleUpdateSummariesCallback}
                    />
                </div>
            </div>
        </>
    );

    if (hideTabs) return <div className="valueset-local-library">{renderValueSetList}</div>;

    return (
        <PageBuilder nav>
            <div className="valueset-local-library">
                <div className="valueset-library__container">
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
            </div>
        </PageBuilder>
    );
};
