import React, { useState } from 'react';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { useValuesetAPI } from './useValuesetAPI';

type Props = {
    activeTab: string;
};
export const ValuesetLibraryTableWrapper = ({ activeTab }: Props) => {
    const [sort, setSort] = useState<string | undefined>();
    const [searchString] = useState('');
    const summaries = useValuesetAPI(searchString || activeTab, sort);
    return <ValuesetLibraryTable summaries={summaries} sortChange={setSort}></ValuesetLibraryTable>;
};
