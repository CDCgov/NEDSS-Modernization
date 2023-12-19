import React, { useContext, useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRuleContext } from '../../context/BusinessContext';
import { fetchBusinessRules } from './useBusinessRulesAPI';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { UserContext } from '../../../../providers/UserContext';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { useParams } from 'react-router-dom';
import { Breadcrumb } from '../../components/BreadCrumb/BreadCrumb';

export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(BusinessRuleContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);
    const { pageId } = useParams();
    console.log('pagesize', pageSize);

    // @ts-ignore
    useEffect(async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);
        try {
            if (pageId) {
                const response = await fetchBusinessRules(token, searchQuery, pageId, sortBy, currentPage, pageSize);
                console.log('response', response);
                const { content, totalElements } = response;
                setSummaries(content);
                setTotalElements(totalElements);
                setIsLoading(false);
            }
        } catch (error) {
            console.error('Error', error);
        }
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);

    return (
        <PageBuilder page="pages">
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
