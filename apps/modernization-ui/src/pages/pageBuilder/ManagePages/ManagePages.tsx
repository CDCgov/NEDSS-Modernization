import { useContext } from 'react';
import { ManagePagesTable } from './ManagePagesTable';
import './ManagePages.scss';
import { PageBuilderContext } from 'providers/PageBuilderContext';
import { PageBuilder } from '../PageBuilder';

export const ManagePages = () => {
    const { pages } = useContext(PageBuilderContext);
    return (
        <>
            <PageBuilder page="manage-pages">
                <div className="manage-pages">
                    <div className="manage-pages__container">
                        <div className="manage-pages__table">
                            <ManagePagesTable pages={pages} pageSize={50}></ManagePagesTable>
                        </div>
                    </div>
                </div>
            </PageBuilder>
        </>
    );
};
