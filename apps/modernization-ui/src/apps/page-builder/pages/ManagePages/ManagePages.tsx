import { PageProvider } from 'page';
import { useState } from 'react';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './ManagePages.scss';
import { ManagePagesTableWrapper } from './ManagePagesTableWrapper';

export const ManagePages = () => {
    const [pageSize] = useState(10);
    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            <PageProvider pageSize={pageSize}>
                                <ManagePagesTableWrapper />
                            </PageProvider>
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
