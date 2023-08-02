import React, { useState } from 'react';
import { QuestionLibraryTable } from './QuestionLibraryTable';
// @ts-ignore
import { useQuestionAPI } from './useQuestionAPI';

type Props = {
    activeTab: string;
};
// eslint-disable-next-line @typescript-eslint/no-unused-vars
export const QuestionLibraryTableWrapper = ({ activeTab }: Props) => {
    const [sort, setSort] = useState<string | undefined>();
    const [searchString, setSearchString] = useState<string | undefined>('');
    const [filter, setFilter] = useState<any>([]);
    // @ts-ignore
    const summaries = useQuestionAPI(searchString, sort, filter);
    return (
        <QuestionLibraryTable
            summaries={summaries}
            sortChange={setSort}
            toSearch={(search, filter) => {
                setSearchString(search);
                setFilter(filter);
            }}
        />
    );
};
