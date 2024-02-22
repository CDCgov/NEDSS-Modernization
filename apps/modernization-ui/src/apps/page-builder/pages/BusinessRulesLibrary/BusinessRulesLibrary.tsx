import React, { useContext, useEffect, useState } from 'react';
import './BusinessRulesLibrary.scss';
import { BusinessRuleContext } from '../../context/BusinessContext';
import { fetchBusinessRules } from './useBusinessRulesAPI';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { Breadcrumb } from 'breadcrumb';
import { Rule } from '../../generated';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { authorization } from 'authorization';
import { Status, usePage } from 'page';

export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(BusinessRuleContext);
    const { page: curPage, ready, firstPage, reload } = usePage();

    const [rules, setRules] = useState<Rule[]>([]);
    const [totalElements, setTotalElements] = useState(0);
    const { page } = useGetPageDetails();

    const getBusinessRules = async () => {
        const token = authorization();
        setIsLoading(true);

        try {
            if (page) {
                const response = await fetchBusinessRules(
                    token,
                    searchQuery,
                    page.id,
                    sortBy,
                    curPage.current,
                    curPage.pageSize
                );
                const { content, totalElements } = response;
                setRules(content || []);
                setTotalElements(totalElements || 0);
                setIsLoading(false);
            }
        } catch (error) {
            console.error('Error', error);
        }
    };

    useEffect(() => {
        if (curPage.status === Status.Requested) {
            getBusinessRules();
        }
    }, [curPage.status]);

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
                            currentPage="Business rules"
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
