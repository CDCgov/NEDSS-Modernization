import React, { useState } from 'react';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { useValuesetAPI } from './useValuesetAPI';

type Props = {
    activeTab: string;
    modalRef: any;
};
export const ValuesetLibraryTableWrapper = ({ activeTab, modalRef }: Props) => {
    const [sort, setSort] = useState<string | undefined>();
    const [searchString] = useState('');
    const summaries = useValuesetAPI(searchString || activeTab, sort);
    return <ValuesetLibraryTable summaries={summaries} sortChange={setSort} labModalRef={modalRef} />;
};
