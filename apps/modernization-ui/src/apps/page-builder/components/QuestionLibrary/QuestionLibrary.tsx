import { PageProvider } from 'page';
import React, { useState } from 'react';
import './QuestionLibrary.scss';
import './QuestionTabs.scss';
import { QuestionLibraryTableWrapper } from './QuestionLibraryTableWrapper';
export const QuestionLibrary = ({ hideTabs, types }: any) => {
    const [pageSize] = useState(10);
    const [activeTab] = useState(types || 'recent');

    return (
        <>
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
                        <PageProvider pageSize={pageSize}>
                            <QuestionLibraryTableWrapper activeTab={activeTab?.toUpperCase()} />
                        </PageProvider>
                    </div>
                </div>
            </div>
        </>
    );
};
