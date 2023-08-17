import React, { useContext, useEffect, useState } from 'react';
import './QuestionLibrary.scss';
import './QuestionTabs.scss';
import { QuestionsContext } from '../../context/QuestionsContext';
import { fetchQuestion } from './useQuestionAPI';
import { QuestionLibraryTable } from './QuestionLibraryTable';
import { UserContext } from '../../../../providers/UserContext';
export const QuestionLibrary = ({ hideTabs }: any) => {
    // const [activeTab] = useState(types || 'recent');
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(QuestionsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const { state } = useContext(UserContext);

    // @ts-ignore
    useEffect(async () => {
        const token = `Bearer ${state.getToken()}`;
        setIsLoading(true);
        setSummaries([]);
        const { content, totalElements }: any = await fetchQuestion(
            token,
            searchQuery,
            sortBy,
            currentPage,
            pageSize,
            filter
        );
        setSummaries(content);
        setTotalElements(totalElements);
        setIsLoading(false);
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);
    return (
        <div className="question-local-library">
            {!hideTabs && (
                <div className="margin-left-2em">
                    <h2>Add question</h2>
                </div>
            )}
            <div className="search-description-block">
                <p>You can search for an existing question or create a new one</p>
            </div>
            <div className="question-local-library__container">
                <div className="question-local-library__table">
                    <QuestionLibraryTable summaries={summaries} pages={{ currentPage, pageSize, totalElements }} />
                </div>
            </div>
        </div>
    );
};
