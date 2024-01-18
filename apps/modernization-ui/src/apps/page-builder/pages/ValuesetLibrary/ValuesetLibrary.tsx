import { RefObject, useContext, useEffect, useState } from 'react';
import { ValueSetsContext } from '../../context/ValueSetContext';
import './ValuesetLibrary.scss';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import './ValuesetTabs.scss';
import { fetchValueSet } from './useValuesetAPI';
import { authorization } from 'authorization';
import { ModalRef } from '@trussworks/react-uswds';

type Props = {
    modalRef?: RefObject<ModalRef>;
    createValueModalRef?: RefObject<ModalRef> | undefined;
    hideTabs?: boolean;
    types?: string;
};

export const ValuesetLibrary = ({ hideTabs, types, modalRef, createValueModalRef }: Props) => {
    const [activeTab, setActiveTab] = useState(types || 'local');
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(ValueSetsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);

    const handleTab = (tab: string) => {
        setActiveTab(tab);
    };

    useEffect(() => {
        const fetchContent = async () => {
            try {
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
            } catch (error) {
                console.error('Error fetching content', error);
            }
        };
        fetchContent();
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);

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
                        createValueModalRef={createValueModalRef}
                    />
                </div>
            </div>
        </>
    );

    if (hideTabs) return <div className="valueset-local-library">{renderValueSetList}</div>;

    return (
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
                {renderValueSetList}
            </div>
        </div>
    );
};
