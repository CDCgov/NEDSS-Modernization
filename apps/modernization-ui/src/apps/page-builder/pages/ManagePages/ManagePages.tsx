import { PageProvider } from 'page';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './ManagePages.scss';
import { ManagePagesTable } from './ManagePagesTable';
import { useState } from 'react';

export const ManagePages = () => {
    const [pageSize] = useState(10);
    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            <PageProvider pageSize={pageSize}>
                                <ManagePagesTable />
                            </PageProvider>
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
