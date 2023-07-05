import React, { useState } from 'react';
import { ManagePagesTable } from './ManagePagesTable';
import { usePageSummaryAPI } from './usePageSummaryAPI';

export const ManagePagesTableWrapper = () => {
    const [sort, setSort] = useState<string | undefined>();
    const [searchString] = useState('');
    const summaries = usePageSummaryAPI(searchString, sort);

    return <ManagePagesTable summaries={summaries} sortChange={setSort}></ManagePagesTable>;
};
