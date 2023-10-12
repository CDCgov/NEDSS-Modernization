import React, { useContext, useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRuleContext } from '../../context/BusinessContext';
import { fetchBusinessRules } from './useBusinessRulesAPI';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { UserContext } from '../../../../providers/UserContext';
export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(BusinessRuleContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);

    // @ts-ignore
    useEffect(async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);
        setSummaries([]);
        const { content, totalElements }: any = await fetchBusinessRules(
            token,
            searchQuery,
            sortBy,
            currentPage,
            pageSize
        );
        setSummaries(content);
        setTotalElements(totalElements);
        setIsLoading(false);
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);
    return (
        <div className="business-rules-library">
            <div className="business-rules-library__container">
                <div className="business-rules-library__table">
                    <BusinessRulesLibraryTable
                        summaries={summaries}
                        qtnModalRef={modalRef}
                        pages={{ currentPage, pageSize, totalElements }}
                    />
                </div>
            </div>
        </div>
    );
};
