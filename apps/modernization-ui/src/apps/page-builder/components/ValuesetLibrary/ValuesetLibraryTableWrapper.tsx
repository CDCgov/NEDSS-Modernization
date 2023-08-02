import React, { useState } from 'react';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { useValuesetAPI } from './useValuesetAPI';

type Props = {
    activeTab: string;
    modalRef: any;
};
export const ValuesetLibraryTableWrapper = ({ activeTab, modalRef }: Props) => {
    const [sort, setSort] = useState<string | undefined>();
    const [searchString, setSearchString] = useState<string | undefined>('');
    const summaries = useValuesetAPI(searchString, sort);
    return (
        <ValuesetLibraryTable
            summaries={summaries}
            sortChange={setSort}
            labModalRef={modalRef}
            toSearch={(search) => {
                setSearchString(search || activeTab);
            }}
        />
    );
};
