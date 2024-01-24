import React, { useContext, useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRuleContext } from '../../context/BusinessContext';
import { fetchBusinessRules } from './useBusinessRulesAPI';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { UserContext } from '../../../../providers/UserContext';
import { Breadcrumb } from 'breadcrumb';
import type { ViewRuleResponse } from '../../generated/models/ViewRuleResponse';
import { useGetPageDetails } from 'apps/page-builder/page/management';

export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(BusinessRuleContext);
    const [rules, setRules] = useState<ViewRuleResponse[]>([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);
    const { page } = useGetPageDetails();

    const getBusinessRules = async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);

        try {
            if (page) {
                const response = await fetchBusinessRules(token, searchQuery, page.id, sortBy, currentPage, pageSize);
                const { content, totalElements } = response;
                setRules(content);
                setTotalElements(totalElements);
                setIsLoading(false);
            }
        } catch (error) {
            console.error('Error', error);
        }
    };

    useEffect(() => {
        getBusinessRules();
    }, [searchQuery, currentPage, pageSize, sortBy, filter, page]);

    return (
        <>
            <header className="business-rule-header">
                <h2>Page builder</h2>
            </header>
            <div className="business-rules-library padding-top-3">
                {page && (
                    <div className="padding-left-3">
                        <Breadcrumb
                            start="../.."
                            currentPage="Rules library"
                            crumbs={[{ name: page.name, position: 1, to: '..' }]}>
                            Page library
                        </Breadcrumb>
                    </div>
                )}
                <div className="business-rules-library__container">
                    <div className="business-rules-library__table">
                        <BusinessRulesLibraryTable
                            summaries={rules}
                            qtnModalRef={modalRef}
                            pages={{ currentPage, pageSize, totalElements }}
                        />
                    </div>
                </div>
            </div>
        </>
    );
};
