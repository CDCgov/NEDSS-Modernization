import React, { useContext, useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRuleContext } from '../../context/BusinessContext';
import { fetchBusinessRules } from './useBusinessRulesAPI';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { UserContext } from '../../../../providers/UserContext';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { useParams } from 'react-router-dom';
import { Breadcrumb } from '../../components/Breadcrumb/Breadcrumb';
export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(BusinessRuleContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);
    const { pageId } = useParams();

    // @ts-ignore
    useEffect(async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);
        const { content, totalElements }: any = await fetchBusinessRules(
            token,
            searchQuery,
            pageId ?? '1000315',
            sortBy,
            currentPage,
            pageSize
        );
        setSummaries(content);
        setTotalElements(totalElements);
        setIsLoading(false);
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);
    return (
        <PageBuilder>
            <div className="business-rules-library padding-top-3">
                <Breadcrumb header="Page Library" currentPage="Business Rules Library" />
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
        </PageBuilder>
    );
};
