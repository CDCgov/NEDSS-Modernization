import React, { useState } from 'react';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { useValuesetAPI } from './useValuesetAPI';

type Props = {
    activeTab: string;
    modalRef: any;
};
// eslint-disable-next-line @typescript-eslint/no-unused-vars
export const ValuesetLibraryTableWrapper = ({ activeTab, modalRef }: Props) => {
    const [sort, setSort] = useState<string | undefined>();
    const [searchString, setSearchString] = useState<string | undefined>('');
    const [filter, setFilter] = useState<any>([]);
    const summaries = useValuesetAPI(searchString, sort, filter);
    return (
        <ValuesetLibraryTable
            summaries={summaries}
            sortChange={setSort}
            labModalRef={modalRef}
            toSearch={(search, filter) => {
                setSearchString(search);
                setFilter(filter);
            }}
        />
    );
};
