import React, { useContext, useEffect, useState } from 'react';
import './Concept.scss';
import { PagesContext } from '../../context/PagesContext';
import { useConceptPI } from './useConceptAPI';
import { ConceptTable } from './ConceptTable';
import { UserContext } from '../../../../providers/UserContext';
export const Concept = () => {
    // const [activeTab] = useState(types || 'recent');
    const { searchQuery, sortBy, sortDirection, currentPage, pageSize, setIsLoading } = useContext(PagesContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);

    // @ts-ignore
    useEffect(async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);
        setSummaries([]);
        const sort = sortBy ? sortBy.toLowerCase() + ',' + sortDirection : '';
        const content: any = await useConceptPI(token, '', sort);
        setSummaries(content);
        setTotalElements(content?.length);
    }, [searchQuery, currentPage, pageSize, sortBy, sortDirection]);
    return (
        <div className="concept-local-library">
            <div className="concept-local-library__container">
                <div className="concept-local-library__table">
                    <ConceptTable summaries={summaries} pages={{ currentPage, pageSize, totalElements }} />
                </div>
            </div>
        </div>
    );
};
