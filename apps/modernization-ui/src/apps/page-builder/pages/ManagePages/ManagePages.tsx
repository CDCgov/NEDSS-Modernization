import { useContext, useState } from 'react';
import { ManagePagesTableContainer } from './ManagePagesTableContainer';
import './ManagePages.scss';
import { PageBuilderContext } from 'apps/page-builder/context/PageBuilderContext';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { PageProvider } from 'page';

export const ManagePages = () => {
    const { pages } = useContext(PageBuilderContext);
    const [pageSize] = useState(10);
    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            <PageProvider pageSize={pageSize}>
                                <ManagePagesTableContainer pages={pages} />
                            </PageProvider>
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
