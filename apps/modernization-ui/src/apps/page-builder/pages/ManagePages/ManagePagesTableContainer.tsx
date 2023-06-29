import { useEffect } from 'react';
import { usePage } from 'page';
import { ManagePagesTable } from './ManagePagesTable';

type Props = {
    pages: any;
};

export const ManagePagesTableContainer = ({ pages }: Props) => {
    const { firstPage } = usePage();

    useEffect(() => {
        if (pages) {
            firstPage();
        }
    }, [pages]);

    return <ManagePagesTable pages={pages} />;
};
